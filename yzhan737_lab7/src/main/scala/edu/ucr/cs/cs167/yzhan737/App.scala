package edu.ucr.cs.cs167.yzhan737

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.SparkConf

/**
 * @author Yaming Zhang
 */
object App {

  def main(args: Array[String]) {
    val operation: String = args(0)
    val inputfile: String = args(1)

    val conf = new SparkConf
    if (!conf.contains("spark.master"))
      conf.setMaster("local[*]")
    println(s"Using Spark master '${conf.get("spark.master")}'")

    val spark = SparkSession
      .builder()
      .appName("CS167 Lab7")
      .config(conf)
      .getOrCreate()

    try {
      import spark.implicits._
      // TODO Load input
      val input = spark.read.format("csv")
        .option("sep", ",")
        .option("inferSchema", "true")
        .option("header", "true")
        .load(inputfile)
        .withColumnRenamed("Case Number", "Case_Number")
        .withColumnRenamed("Primary Type", "Primary_Type")
        .withColumnRenamed("Location Description", "Location_Description")
        .withColumnRenamed("Community Area", "Community_Area")
        .withColumnRenamed("FBI Code", "FBI_Code")
        .withColumnRenamed("X Coordinate", "X_Coordinate")
        .withColumnRenamed("Y Coordinate", "Y_Coordinate")
        .withColumnRenamed("Updated On", "Updated_On")

      // input.show()
      // input.printSchema()

      val t1 = System.nanoTime
      operation match {
        case "write-parquet" =>
          // TODO Write the input dataset to a parquet file. The file name is passed in args(2)
          input.write.parquet(args(2))
        case "write-parquet-partitioned" =>
          // TODO Write the input dataset to a partitioned parquet file by District. The file name is passed in args(2)
          input.write.partitionBy("District").parquet(args(2))
        case "top-crime-types" =>
          // TODO Print out the top five crime types by count "Primary_Type"

          // input.createTempView("csvView")
          // spark.sql("SELECT Primary_Type FROM csvView GROUP BY Primary_Type ORDER BY COUNT(*) DESC LIMIT 5").show()

          // I only show the result of using non-partitioned Parquet file
          val inputParquet = spark.read.parquet("Crimes")
          inputParquet.createTempView("parquetView")
          spark.sql("SELECT Primary_Type FROM parquetView GROUP BY Primary_Type ORDER BY COUNT(*) DESC LIMIT 5").show()

          // val inputParquet2 = spark.read.parquet("Crimes2")
          // inputParquet2.createTempView("parquetView2")
          // spark.sql("SELECT Primary_Type FROM parquetView2 GROUP BY Primary_Type ORDER BY COUNT(*) DESC LIMIT 5").show()

        case "find" =>
          // TODO Find a record by Case_Number in args(2)

          // input.filter($"Case_Number" === args(2)).show()

          // I only show the result of using non-partitioned Parquet file
          val inputParquet = spark.read.parquet("Crimes")
          inputParquet.filter($"Case_Number" === args(2)).show()

          // val inputParquet2 = spark.read.parquet("Crimes2")
          // inputParquet2.filter($"Case_Number" === args(2)).show()

        case "stats" =>
          // TODO Compute the number of arrests, domestic crimes, and average beat per district.
          // Save the output to a new parquet file named "stats.parquet"

          // input.createTempView("csvView")
          // spark.sql("SELECT COUNT(*) FROM csvView WHERE Arrest = TRUE").withColumnRenamed("count(1)", "Number_of_arrests").write.mode("overwrite").parquet("stats.parquet")
          // spark.sql("SELECT COUNT(*) FROM csvView WHERE Domestic = TRUE").withColumnRenamed("count(1)", "Number_of_domestic_crimes").write.mode("append").parquet("stats.parquet")
          // spark.sql("SELECT AVG(Beat) FROM csvView GROUP BY District").withColumnRenamed("avg(Beat)", "Average_beat_per_district").write.mode("append").parquet("stats.parquet")

          // I only show the result of using non-partitioned Parquet file
          val inputParquet = spark.read.parquet("Crimes")
          inputParquet.createTempView("parquetView")
          spark.sql("SELECT COUNT(*) FROM parquetView WHERE Arrest = TRUE").withColumnRenamed("count(1)", "Number_of_arrests").write.mode("overwrite").parquet("stats.parquet")
          spark.sql("SELECT COUNT(*) FROM parquetView WHERE Domestic = TRUE").withColumnRenamed("count(1)", "Number_of_domestic_crimes").write.mode("append").parquet("stats.parquet")
          spark.sql("SELECT AVG(Beat) FROM parquetView GROUP BY District").withColumnRenamed("avg(Beat)", "Average_beat_per_district").write.mode("append").parquet("stats.parquet")

          // val inputParquet2 = spark.read.parquet("Crimes2")
          // inputParquet2.createTempView("parquetView2")
          // spark.sql("SELECT COUNT(*) FROM parquetView2 WHERE Arrest = TRUE").withColumnRenamed("count(1)", "Number_of_arrests").write.mode("overwrite").parquet("stats.parquet")
          // spark.sql("SELECT COUNT(*) FROM parquetView2 WHERE Domestic = TRUE").withColumnRenamed("count(1)", "Number_of_domestic_crimes").write.mode("append").parquet("stats.parquet")
          // spark.sql("SELECT AVG(Beat) FROM parquetView2 GROUP BY District").withColumnRenamed("avg(Beat)", "Average_beat_per_district").write.mode("append").parquet("stats.parquet")

        case "stats-district" =>
          // TODO Compute number of arrests, domestic crimes, and average beat for one district (args(2))
          // Write the result to the standard output

          // val arrest = input.filter($"District" === args(2).toInt).filter($"Arrest" === true).count()
          // val domestic = input.filter($"District" === args(2).toInt).filter($"Domestic" === true).count()
          // println(s"Number of arrests in District ${args(2)} : $arrest")
          // println(s"Number of domestic crimes in District ${args(2)} : $domestic")
          // input.filter($"District" === args(2).toInt).groupBy("District").avg("Beat").show()

          val inputParquet = spark.read.parquet("Crimes")
          val arrest = inputParquet.filter($"District" === args(2).toInt).filter($"Arrest" === true).count()
          val domestic = inputParquet.filter($"District" === args(2).toInt).filter($"Domestic" === true).count()
          println(s"Number of arrests in District ${args(2)} : $arrest")
          println(s"Number of domestic crimes in District ${args(2)} : $domestic")
          inputParquet.filter($"District" === args(2).toInt).groupBy("District").avg("Beat").show()

          // val inputParquet2 = spark.read.parquet("Crimes2")
          // val arrest = inputParquet2.filter($"District" === args(2).toInt).filter($"Arrest" === true).count()
          // val domestic = inputParquet2.filter($"District" === args(2).toInt).filter($"Domestic" === true).count()
          // println(s"Number of arrests in District ${args(2)} : $arrest")
          // println(s"Number of domestic crimes in District ${args(2)} : $domestic")
          // inputParquet2.filter($"District" === args(2).toInt).groupBy("District").avg("Beat").show()
      }
      val t2 = System.nanoTime
      println(s"Operation $operation on file '$inputfile' finished in ${(t2 - t1) * 1E-9} seconds")
    } finally {
      spark.stop
    }
  }
}