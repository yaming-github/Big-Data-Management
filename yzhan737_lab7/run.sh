#!/usr/bin/env sh
mvn clean package
spark-submit target/yzhan737_lab7-1.0-SNAPSHOT.jar write-parquet Crimes_-_2001_to_present.csv Crimes
spark-submit target/yzhan737_lab7-1.0-SNAPSHOT.jar write-parquet-partitioned Crimes_-_2001_to_present.csv Crimes2
spark-submit target/yzhan737_lab7-1.0-SNAPSHOT.jar top-crime-types Crimes_-_2001_to_present.csv
spark-submit target/yzhan737_lab7-1.0-SNAPSHOT.jar find Crimes_-_2001_to_present.csv HY413923
spark-submit target/yzhan737_lab7-1.0-SNAPSHOT.jar stats Crimes_-_2001_to_present.csv
spark-submit target/yzhan737_lab7-1.0-SNAPSHOT.jar stats-district Crimes_-_2001_to_present.csv 8
