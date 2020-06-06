package edu.ucr.cs.cs167.yzhan737

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
 * @author Yaming Zhang
 */
object App {

  def main(args: Array[String]) {
    val command: String = args(0)
    val inputfile: String = args(1)

    val conf = new SparkConf
    if (!conf.contains("spark.master"))
      conf.setMaster("local[*]")
    println(s"Using Spark master '${conf.get("spark.master")}'")

    val spark = SparkSession
      .builder()
      .appName("CS167 Lab6")
      .config(conf)
      .getOrCreate()

    try {
      val input = spark.read.format("csv")
        .option("sep", "\t")
        .option("inferSchema", "true")
        .option("header", "true")
        .load(inputfile)

      val t1 = System.nanoTime
      command match {
        case "count-all" =>
          // TODO count total number of records in the file
          val count = input.count()
          println(s"Total count for file '${inputfile}' is $count")
        case "code-filter" =>
          // TODO Filter the file by response code, args(2), and print the total number of matching lines
          import spark.implicits._
          val count = input.filter($"response" === args(2).toInt).count()
          println(s"Total count for file '${inputfile}' with response code ${args(2)} is $count")
        case "time-filter" =>
          // TODO Filter by time range [from = args(2), to = args(3)], and print the total number of matching lines
          import spark.implicits._
          val count = input.filter($"time".between(args(2).toInt, args(3).toInt)).count()
          println(s"Total count for file '${inputfile}' in time range [${args(2)}, ${args(3)}] is $count")
        case "count-by-code" =>
          // TODO Group the lines by response code and count the number of records per group
          println(s"Number of lines per code for the file ${inputfile}")
          input.groupBy("response").count().show()
        case "sum-bytes-by-code" =>
          // TODO Group the lines by response code and sum the total bytes per group
          println(s"Total bytes per code for the file ${inputfile}")
          input.groupBy("response").sum("bytes").show()
        case "avg-bytes-by-code" =>
          // TODO Group the liens by response code and calculate the average bytes per group
          println(s"Average bytes per code for the file ${inputfile}")
          input.groupBy("response").avg("bytes").show()
        case "top-host" =>
          // TODO print the host the largest number of lines and print the number of lines
          import spark.implicits._
          val row = input.groupBy("host").count().orderBy($"count".desc).first()
          println(s"Top host in the file ${inputfile} by number of entries")
          println(s"Host: ${row.getAs[String]("host")}")
          println(s"Number of entries: ${row.getAs[Int]("count")}")
        case "comparison" =>
          // TODO Given a specific time, calculate the number of lines per response code for the
          // entries that happened before that time, and once more for the lines that happened at or after
          // that time. Print them side-by-side in a tabular form.
          val time = args(2).toInt
          import spark.implicits._
          val b4 = input.filter($"time" < time).groupBy("response").count().withColumnRenamed("count", "count_before")
          val after = input.filter($"time" >= time).groupBy("response").count().withColumnRenamed("count", "count_after")
          println(s"Comparison of the number of lines per code before and after $time on file ${inputfile}")
          b4.join(after, "response").show()
      }
      val t2 = System.nanoTime
      println(s"Command '${command}' on file '${inputfile}' finished in ${(t2 - t1) * 1E-9} seconds")
    } finally {
      spark.stop
    }
  }
}