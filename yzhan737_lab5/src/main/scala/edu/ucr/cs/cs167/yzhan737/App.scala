package edu.ucr.cs.cs167.yzhan737

/**
 * @author Yaming Zhang
 */

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object App {

  def main(args: Array[String]) {
    val command: String = args(0)
    val inputfile: String = args(1)

    val conf = new SparkConf
    if (!conf.contains("spark.master"))
      conf.setMaster("local[*]")
    println(s"Using Spark master '${conf.get("spark.master")}'")
    conf.setAppName("lab5").set("spark.driver.host", "localhost")
    val sparkContext = new SparkContext(conf)
    try {
      val inputRDD: RDD[String] = sparkContext.textFile(inputfile)
      // TODO Parse the input file using the tab separator and skip the first line
      val splitrdd: RDD[String] = inputRDD.filter(line => !line.startsWith("host\tlogname"))
      val t1 = System.nanoTime
      command match {
        case "count-all" =>
          // TODO count total number of records in the file
          val count = splitrdd.count()
          println(s"Total count for file '${inputfile}' is $count")
        case "code-filter" =>
          // TODO Filter the file by response code, args(2), and print the total number of matching lines
          val code = splitrdd.filter(line => line.split("\t")(5).equals(args(2))).count()
          println(s"Total count for file '${inputfile}' with response code ${args(2)} is $code")
        case "time-filter" =>
          // TODO Filter by time range [from = args(2), to = args(3)], and print the total number of matching lines
          val time = splitrdd.filter(line => line.split("\t")(2).toLong >= args(2).toLong && line.split("\t")(2).toLong <= args(3).toLong).count()
          println(s"Total count for file '${inputfile}' in time range [${args(2)}, ${args(3)}] is $time")
        case "count-by-code" =>
          // TODO Group the lines by response code and count the number of records per group
          println(s"Number of lines per code for the file ${inputfile}")
          println("Code,Count")
          splitrdd.map(line => (line.split("\t")(5), 1)).countByKey().foreach(x => println(x._1 + "," + x._2))
        case "sum-bytes-by-code" =>
          // TODO Group the lines by response code and sum the total bytes per group
          println(s"Total bytes per code for the file ${inputfile}")
          println("Code,Sum(bytes)")
          splitrdd.map(line => (line.split("\t")(5), line.split("\t")(6).toLong)).reduceByKey((x, y) => x + y).collect().foreach(x => println(x._1 + "," + x._2))
        case "avg-bytes-by-code" =>
          // TODO Group the liens by response code and calculate the average bytes per group
          println(s"Average bytes per code for the file ${inputfile}")
          println("Code,Avg(bytes)")
          val count = splitrdd.map(line => (line.split("\t")(5), 1)).countByKey()
          splitrdd.map(line => (line.split("\t")(5), line.split("\t")(6).toLong)).reduceByKey((x, y) => x + y).map(x => (x._1, x._2 / count(x._1).toDouble)).foreach(x => println(x._1 + "," + x._2))
        // B
        // splitrdd.map(line => (line.split("\t")(5), (line.split("\t")(6).toLong, 1.0))).reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2)).map(x => (x._1, x._2._1 / x._2._2)).collect().foreach(x => println(x._1 + "," + x._2))
        case "top-host" =>
          // TODO print the host the largest number of lines and print the number of lines
          println(s"Top host in the file ${inputfile} by number of entries")
          val host = splitrdd.map(line => (line.split("\t")(0), 1)).reduceByKey((x, y) => x + y).sortBy(x => x._2, false).first()
          println(s"Host: ${host._1}")
          println(s"Number of entries: ${host._2}")
        case "comparison" =>
          // TODO Given a specific time, calculate the number of lines per response code for the
          // entries that happened before that time, and once more for the lines that happened at or after
          // that time. Print them side-by-side in a tabular form.
          println(s"Comparison of the number of lines per code before and after ${args(2)} on file ${inputfile}")
          println(s"Code,Count before,Count after")
          val b4 = splitrdd.filter(line => line.split("\t")(2) < args(2)).map(line => (line.split("\t")(5), 1)).countByKey()
          splitrdd.filter(line => line.split("\t")(2) >= args(2)).map(line => (line.split("\t")(5), 1)).countByKey().foreach(x => println(x._1 + "," + b4.getOrElse(x._1, 0) + "," + x._2))
      }
      val t2 = System.nanoTime
      println(s"Command '${command}' on file '${inputfile}' finished in ${(t2 - t1) * 1E-9} seconds")
    }
    finally {
      sparkContext.stop
    }
  }
}