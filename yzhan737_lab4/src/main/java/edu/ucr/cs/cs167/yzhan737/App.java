package edu.ucr.cs.cs167.yzhan737;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        final String inputfile = args[0];
        SparkConf conf = new SparkConf();
        if (!conf.contains("spark.master"))
            conf.setMaster("local[*]");
        System.out.printf("Using Spark master '%s'\n", conf.get("spark.master"));
        conf.setAppName("lab4");
        JavaSparkContext spark = new JavaSparkContext(conf);
        try {
            JavaRDD<String> logFile = spark.textFile(inputfile);
            System.out.printf("Number of lines in the log file %d\n", logFile.count());
        } finally {
            spark.close();
        }
    }
}
