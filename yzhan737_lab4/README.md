# Lab 4

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

* (Q1) Do you think it will use your local cluster? Why or why not?
```
No, it will not. Because in our program we configure local as our master node instead of the cluster on Spark.
```
* (Q2) Does the application use the cluster that you started? How did you find out?
```
Yes. We can find a completed application, finished during 10 seconds on the WebUI.
```
* (Q3) What is the Spark master printed on the standard output on IntelliJ IDEA?
```
Using Spark master 'local[*]'
```
* (Q4) What is the Spark master printed on the standard output on the terminal?
```
Using Spark master 'local[*]'
```
* (Q5) For the previous command that prints the number of matching lines, list all the processed input splits.
```
INFO HadoopRDD: Input split: file:/D:/edu.ucr.cs.cs167.yzhan737/yzhan737_lab4/nasa_19950801.tsv:1169610+1169610
INFO HadoopRDD: Input split: file:/D:/edu.ucr.cs.cs167.yzhan737/yzhan737_lab4/nasa_19950801.tsv:0+1169610
```
* (Q6) For the previous command that counts the lines and prints the output, how many splits were generated?
```
4 splits were generated.
```
* (Q7) Compare this number to the one you got earlier.
```
Previously, when we do not need to write the result to the file, there are 2 splits. This time, we have 4 splits. 
```
* (Q8) Explain why we get these numbers.
```
Because previously we only have 1 action on the RDD, which is count(). So, we only need to read the file and write to the disk once.
However, this time we write 2 actions, which are count() and saveAsTextFile(). So, we need to read the file and write to the disk twice.
```
* (Q9) What can you do to the current code to ensure that the file is read only once?
```
We can write matchingLines.cache(); before we print the result and write to the output file. By doing that, we cache the 
intermedia file in the memory, so that both the count() and saveAsTextFile() functions can retrieve the file from the memory.
```