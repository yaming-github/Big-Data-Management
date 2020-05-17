# Lab 1

## Student information
* Full name: <br>

      Yaming Zhang 
* E-mail: <br>
            
      zhangyaming0726@gmail.com
      yzhan737@cs.ucr.edu
      Sorry, I am a student from UCR Extension, so I do not have RMail.
* UCR NetID: <br>

      yzhan737
* Student ID: <br>

      X674002

## Answers

* (Q1) What is the name of the created directory?
<br>

      yzhan737_lab1


* (Q2) What do you see at the console output?
<br>

      Hello World!

* (Q3) What do you see at the output?
<br>

      Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 0 out of bounds for length 0
	        at edu.ucr.cs.cs167.yzhan737.App.main(App.java:56)

* (Q4) What is the output that you see at the console?
<br>

      Process finished with exit code 0

* (Q5) Does it run? Why or why not?
<br>

      Exception in thread "main" java.lang.NoClassDefFoundError: org/apache/hadoop/conf/Configuration
              at edu.ucr.cs.cs167.yzhan737.App.main(App.java:48)
      Caused by: java.lang.ClassNotFoundException: org.apache.hadoop.conf.Configuration
              at java.net.URLClassLoader.findClass(Unknown Source)
              at java.lang.ClassLoader.loadClass(Unknown Source)
              at sun.misc.Launcher$AppClassLoader.loadClass(Unknown Source)
              at java.lang.ClassLoader.loadClass(Unknown Source)
              ... 1 more
             
      We have to run the WordCount program on Hadoop using
      hadoop jar target/<JARFile> input.txt output.txt