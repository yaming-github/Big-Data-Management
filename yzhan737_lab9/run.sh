#!/usr/bin/env sh
mvn clean package
spark-submit target/yzhan737_lab9-1.0-SNAPSHOT.jar kc_house_data.csv regression
spark-submit target/yzhan737_lab9-1.0-SNAPSHOT.jar sentiment.csv classification