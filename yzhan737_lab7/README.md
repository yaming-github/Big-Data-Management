# Lab 7

## Student information

* Full name:
```
Yaming Zhang
``` 
* E-mail:
```
yzhan737@ucr.edu
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

* (Q1) What are the top five crime types?
```
Theft, battery, criminal damage, narcotics and assault.
```

* (Q2) Compare the sizes of the CSV file and the resulting Parquet file? What do you notice? Explain.
```
CSV file is more than 3 times bigger than Parquet file.
Parquet stores data in column format. So, the columns can be encoded more efficient like using bit masks for null value 
or delta encoding. 
```

* (Q3) How many times do you see the schema the output? How does this relate to the number of files in the output directory? What do you make of that?
```
13 times. It is the same as the number of output files.
For each splitted files, the console will print out the schema.
```

* (Q4) How does the output look like? How many files were generated?
```
In the output directory, there are 32 sub-directories. Each of them is the partition of the district from 1 to 31. 
In each directory, there are 13 output files of the data under that partition, which is the same as the previous question.
```

* (Q5) Explain an efficient way to run this query on a column store.
```
Since we only need to query the column Case_Number, so we can query on Parquet file to query on the specific column Case_Number.
```

* (Q6) Which of the three input files you think will be processed faster using this operation?
```
I think the query on partitioned Parquet will be faster since we have already partitioned the Parquet file by District.
The query only need to focus on the specific District(directory).
```

| Command | Time on CSV | Time on non-partitioned Parquet | Time on partitioned Parquet |
| :----: | :----: | :----: | :----: |
| top-crime-types | 27.209780300000002 | 8.1699511 | 12.486343900000001 |		
| find | 41.5701115 | 7.5478804 | 8.419044301000001 |
| stats | 87.2907668 | 14.2054647 | 22.656706 |
| stats-district | 60.474585199 | 9.2520778 | 10.102270200000001 |
