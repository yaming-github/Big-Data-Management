# Lab 5

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

* (Q1) What are these two arguments?
```
First is the command, it will determine what transformation or action we use.
Second is the name of our input file.
```
* (Q2) Bonus Answer
```
splitrdd.map(line => (line.split("\t")(5), (line.split("\t")(6).toLong, 1.0))).reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2))
.map(x => (x._1, x._2._1 / x._2._2)).collect().foreach(x => println(x._1 + "," + x._2))

In map function, we can set the value to be a tuple2(bytes, 1.0). bytes is the number of bytes of that line. 1.0 is used
for the count of the specific response code. In reduceByKey function, we add the bytes and the 1.0 seperatedly. So, we 
will get the total number of bytes of the response code and the count of that code. 
```
