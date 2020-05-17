package edu.ucr.cs.cs167.yzhan737;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.io.*;
import java.util.Map;

public class Aggregation {
    public static void main(String[] args) throws IOException {
        final String inputfile = args[0];
        final String outputfile = args[1];
        SparkConf conf = new SparkConf();
        if (!conf.contains("spark.master"))
            conf.setMaster("local[*]");
        System.out.printf("Using Spark master '%s'\n", conf.get("spark.master"));
        conf.setAppName("lab4");
        JavaSparkContext spark = new JavaSparkContext(conf);
        BufferedWriter out = new BufferedWriter(new FileWriter(outputfile));
        try {
            JavaRDD<String> logFile = spark.textFile(inputfile);
            JavaPairRDD<String, Integer> lines = logFile.mapToPair(line -> new Tuple2<>(line.split("\t")[5], 1));
            Map<String, Long> count = lines.countByKey();
            for (Map.Entry<String, Long> entry : count.entrySet())
                out.write("Code '" + entry.getKey() + "' : number of entries " + entry.getValue() + "\n");
        } finally {
            spark.close();
            out.close();
        }
    }
}
