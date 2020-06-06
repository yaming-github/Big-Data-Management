package edu.ucr.cs.cs167.yzhan737

/**
 * @author Yaming Zhang
 */

import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.ml.classification.{LogisticRegression, LogisticRegressionModel}
import org.apache.spark.ml.evaluation.{BinaryClassificationEvaluator, RegressionEvaluator}
import org.apache.spark.ml.feature.{HashingTF, StringIndexer, Tokenizer, VectorAssembler, Word2Vec}
import org.apache.spark.ml.regression.{LinearRegression, LinearRegressionModel}
import org.apache.spark.ml.stat.Correlation
import org.apache.spark.ml.tuning.{CrossValidator, ParamGridBuilder, TrainValidationSplit}

object App {

  def main(args: Array[String]) {
    if (args.length < 2) {
      println("Usage <input file> <algorithm>")
      println("<input file> path to a CSV file input")
      println("<algorithm> is either regression or classification")
    }
    val inputfile = args(0)
    val method = args(1)
    val conf = new SparkConf
    if (!conf.contains("spark.master"))
      conf.setMaster("local[*]")
    println(s"Using Spark master '${conf.get("spark.master")}'")

    val spark = SparkSession
      .builder()
      .appName("CS167 Lab9")
      .config(conf)
      .getOrCreate()

    val t1 = System.nanoTime
    try {
      if (method.equals("regression")) {
        // TODO apply regression model on house prices
        val input = spark.read.format("csv")
          .option("sep", ",")
          .option("inferSchema", "true")
          .option("header", "true")
          .load(inputfile)

        val assembler = new VectorAssembler()
          .setInputCols(Array("bedrooms", "bathrooms", "sqft_living", "sqft_lot", "condition", "waterfront", "view"))
          .setOutputCol("features")

        val lr = new LinearRegression()
          // .setMaxIter(10)
          // .setRegParam(0.3)
          // .setElasticNetParam(0.8)
          .setFeaturesCol("features")
          .setLabelCol("price")

        // val model = lr.fit(assembler.transform(input))

        // println(model.coefficients)

        val pipeline = new Pipeline()
          .setStages(Array(assembler, lr))

        val paramGrid = new ParamGridBuilder()
          .addGrid(lr.regParam, Array(0.01, 0.1, 0.3))
          .addGrid(lr.elasticNetParam, Array(0.0, 0.3, 0.8, 1.0))
          .build()

        val cv = new CrossValidator()
          .setEstimator(pipeline)
          .setEvaluator(new RegressionEvaluator().setLabelCol("price"))
          .setEstimatorParamMaps(paramGrid)
          .setNumFolds(5)
          .setParallelism(2)

        val Array(trainingData, testData) = input.randomSplit(Array(0.8, 0.2))

        val model = cv.fit(trainingData)

        val chosenModel = model.bestModel.asInstanceOf[PipelineModel].stages(1).asInstanceOf[LinearRegressionModel]
        println(s"ElasticNetParam: ${chosenModel.getElasticNetParam}")
        println(s"regParam: ${chosenModel.getRegParam}")
        println(s"coefficients: ${chosenModel.coefficients}")

        val predictions = model.transform(testData)
        predictions.select("price", "prediction").show(5)

        val rmse = new RegressionEvaluator()
          .setLabelCol("price")
          .setPredictionCol("prediction")
          .setMetricName("rmse")
          .evaluate(predictions)
        println(s"RMSE on test set is $rmse")

        val corr = Correlation.corr(new VectorAssembler()
          .setInputCols(Array("prediction", "price"))
          .setOutputCol("features2")
          .transform(predictions), "features2").head
        println(s"Correlation is $corr")
      } else if (method.equals("classification")) {
        // TODO process the sentiment data
        val input = spark.read.format("csv")
          .option("header", "true")
          .option("quote", "\"")
          .option("escape", "\"")
          .load(inputfile)

        val tokenizer = new Tokenizer()
          .setInputCol("text")
          .setOutputCol("words")

        val hashingTF = new HashingTF()
          .setInputCol("words")
          .setOutputCol("features")

        val indexer = new StringIndexer()
          .setInputCol("sentiment")
          .setOutputCol("label")
          .setHandleInvalid("skip")

        val lr = new LogisticRegression()

        val pipeline = new Pipeline()
          .setStages(Array(tokenizer, hashingTF, indexer, lr))

        val paramGrid = new ParamGridBuilder()
          .addGrid(hashingTF.numFeatures, Array(10, 100, 1000))
          .addGrid(lr.regParam, Array(0.01, 0.1, 0.3, 0.8))
          .build()

        val cv = new TrainValidationSplit()
          .setEstimator(pipeline)
          .setEvaluator(new BinaryClassificationEvaluator())
          .setEstimatorParamMaps(paramGrid)
          .setTrainRatio(0.8)
          .setParallelism(2)

        val Array(trainingData, testData) = input.randomSplit(Array(0.8, 0.2))

        val logisticModel = cv.fit(trainingData)

        val numFeatures = logisticModel.bestModel.asInstanceOf[PipelineModel].stages(1).asInstanceOf[HashingTF].getNumFeatures
        val regParam = logisticModel.bestModel.asInstanceOf[PipelineModel].stages(3).asInstanceOf[LogisticRegressionModel].getRegParam
        println(s"numFeatures: $numFeatures")
        println(s"regParam: $regParam")

        val predictions = logisticModel.transform(testData)
        predictions.select("text", "label", "prediction", "probability").show()

        val binaryClassificationEvaluator = new BinaryClassificationEvaluator()
          .setLabelCol("label")
          .setRawPredictionCol("prediction")

        val accuracy = binaryClassificationEvaluator.evaluate(predictions)
        println(s"Accuracy of the test set is $accuracy")
      } else {
        println(s"Unknown algorithm ${method}")
        System.exit(1)
      }
      val t2 = System.nanoTime
      println(s"Applied algorithm $method on input $inputfile in ${(t2 - t1) * 1E-9} seconds")
    } finally {
      spark.stop
    }
  }
}