mvn clean package
hadoop jar target/yzhan737_lab3-1.0-SNAPSHOT.jar edu.ucr.cs.cs167.yzhan737.Filter file:///%cd%/nasa_19950801.tsv file:///%cd%/filter_output.tsv 200
hadoop jar target/yzhan737_lab3-1.0-SNAPSHOT.jar edu.ucr.cs.cs167.yzhan737.Aggregation file:///%cd%/19950630.23-19950801.00.tsv file:///%cd%/aggregation_output.tsv