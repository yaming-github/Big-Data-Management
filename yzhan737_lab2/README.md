# Lab 2

## Student information

* Full name:
```
Yaming Zhang
``` 
* E-mail:
```
zhangyaming0726@gmail.com
yzhan737@cs.ucr.edu
Sorry, I am a student from UCR Extension, so I do not have RMail.
```
* UCR NetID:
```
yzhan737
```
* Student ID:
```
X674002
```

## Answers

* (Q1) Verify the file size and record the running time.
```
Copied 2271210910 bytes from 'AREAWATER.csv' to 'hdfs.csv' in 21.662375 seconds
```
* (Q2) Record the running time of the copy command.
```
Measure-Command {copy AREAWATER.csv local.csv}
Seconds           : 11
Milliseconds      : 446
```
* (Q3) How do the two numbers compare?
```
The running times of copying the file through my program is about 50 percent slower than through
the OS. I think the reason is that my program involves the IO of loading the file into the file 
system and writing from the file system to local.
```
* (Q4) Does the program run after you change the default file system to HDFS? What is the error message, if any, what you get?
```
No, it dose not.
The error message is what I set in my progeam when the input file does not exist.
FileNotFoundException: AREAWATER.csv (No such file or directory)
The reason is that the HDFS does not have AREAWATER.csv file. It is on our local machine.
```
* (Q5) Use your program to test the following cases and record the running time for each case.<br>
1. Copy a file from local file system to HDFS
```
Copied 2271210910 bytes from 'file:///D:/edu.ucr.cs.cs167.yzhan737/yzhan737_lab2/AREAWATER.csv' 
to 'hdfs:///yzhan737/localToHDFS.csv' in 26.072398 seconds
```
2. Copy a file from HDFS to local file system.
```
Copied 2271211520 bytes from 'hdfs:///yzhan737/localToHDFS.csv' 
to 'file:///D:/edu.ucr.cs.cs167.yzhan737/yzhan737_lab2/HDFSToLocal.csv' in 39.461476 seconds
```
3. Copy a file from HDFS to HDFS.
```
Copied 2271211520 bytes from 'hdfs:///yzhan737/localToHDFS.csv' 
to 'hdfs:///yzhan737/HDFSToHDFS.csv' in 42.371552 seconds
```
* (Q6) Test your program on two files, one file stored on the local file system, and another file stored on HDFS. Compare the running times of both tasks. What do you observe?
```
local file system : 1.851494 s
HDFS              : 36.913691 s
The running time on HDFS is so slow. I think the reason is that the file is seperated into different blocks and stored on 
different data nodes, so the random read on the file will be slow due to the IO between different data nodes. 
```

## Table
|              | File System on program | Copy on local Machine | Copy from Local to HDFS | File System on program | File System on program | 
| ------------ |:----------------------:|:---------------------:|:-----------------------:|:----------------------:|:----------------------:|
| Running Time | 21.662375 s            | 11.446000 s           | 26.072398 s             | 39.461476 s            | 42.371552 s            |   