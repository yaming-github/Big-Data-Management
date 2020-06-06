#!/usr/bin/env sh
mvn clean package
spark-submit target/yzhan737_lab6-1.0-SNAPSHOT.jar count-all 19950630.23-19950801.00.tsv
spark-submit target/yzhan737_lab6-1.0-SNAPSHOT.jar code-filter 19950630.23-19950801.00.tsv 302
spark-submit target/yzhan737_lab6-1.0-SNAPSHOT.jar time-filter 19950630.23-19950801.00.tsv 804955673 805590159
spark-submit target/yzhan737_lab6-1.0-SNAPSHOT.jar count-by-code 19950630.23-19950801.00.tsv
spark-submit target/yzhan737_lab6-1.0-SNAPSHOT.jar sum-bytes-by-code 19950630.23-19950801.00.tsv
spark-submit target/yzhan737_lab6-1.0-SNAPSHOT.jar avg-bytes-by-code 19950630.23-19950801.00.tsv
spark-submit target/yzhan737_lab6-1.0-SNAPSHOT.jar top-host 19950630.23-19950801.00.tsv
spark-submit target/yzhan737_lab6-1.0-SNAPSHOT.jar comparison 19950630.23-19950801.00.tsv 805383872
