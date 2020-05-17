#!/usr/bin/env sh
mvn clean package
spark-submit --class edu.ucr.cs.cs167.yzhan737.Filter target/yzhan737_lab4-1.0-SNAPSHOT.jar nasa_19950630.22-19950728.12.tsv filter_output 302
spark-submit --class edu.ucr.cs.cs167.yzhan737.Aggregation target/yzhan737_lab4-1.0-SNAPSHOT.jar nasa_19950630.22-19950728.12.tsv aggregation_output.txt