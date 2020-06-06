#!/usr/bin/env sh
mvn clean package
hadoop jar target/yzhan737_lab2-1.0-SNAPSHOT.jar edu.ucr.cs.cs167.yzhan737.App AREAWATER.csv programOutput.txt
Measure-Command { copy AREAWATER.csv localCopy.csv }
hadoop jar target/yzhan737_lab2-1.0-SNAPSHOT.jar edu.ucr.cs.cs167.yzhan737.App file:///D:/edu.ucr.cs.cs167.yzhan737/yzhan737_lab2/AREAWATER.csv hdfs:///yzhan737/localToHDFS.csv
hadoop jar target/yzhan737_lab2-1.0-SNAPSHOT.jar edu.ucr.cs.cs167.yzhan737.App hdfs:///yzhan737/localToHDFS.csv file:///D:/edu.ucr.cs.cs167.yzhan737/yzhan737_lab2/HDFSToLocal.csv
hadoop jar target/yzhan737_lab2-1.0-SNAPSHOT.jar edu.ucr.cs.cs167.yzhan737.App hdfs:///yzhan737/localToHDFS.csv hdfs:///yzhan737/HDFSToHDFS.csv
hadoop jar target/yzhan737_lab2-1.0-SNAPSHOT.jar edu.ucr.cs.cs167.yzhan737.AppB file:///D:/edu.ucr.cs.cs167.yzhan737/yzhan737_lab2/AREAWATER.csv
hadoop jar target/yzhan737_lab2-1.0-SNAPSHOT.jar edu.ucr.cs.cs167.yzhan737.AppB hdfs:///yzhan737/localToHDFS.csv