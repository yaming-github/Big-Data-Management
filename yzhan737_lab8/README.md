# Lab 8

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

* (Q1) Insert the sample JSON file into a new collection named contacts.
```

```

* (Q2) Retrieve all the users sorted by name.
```

```

* (Q3) List only the id and names sorted in reverse alphabetical order by name (Z-to-A).
```

```

* (Q4) Is the comparison of the attribute name case-sensitive? Show how you try this with the previous query and include your answer.
```

```

* (Q5) Repeat Q3 above but do not show the _id field.
```

```

* (Q6) Insert the following document to the collection.
```
{Name: {First: “David”, Last: “Bark”}}
```
Does MongoDB accept this document while the name field has a different type than other records?
```

```
Rerun Q3, which lists the records sorted by name
```

```

* (Q7) Where do you expect the new record to be located in the sort order? Verify the answer and explain.
```

```
Insert the following document into the users collection.
```
{Name: [“David”, “Bark”]}
```

* Repeat Q3. (Q8) Where do you expect the new document to appear in the sort order. Verify your answer and explain after running the query.
```

```

* Repeat Q3 again with all the objects that you inserted, but this time sort the name in ascending order. 
(Q9) Where do you expect the last inserted record, {Name: [“David”, “Bark”]} to appear this time? 
Does it appear in the same position relative to the other records? Explain why or why not.
```

```

* (Q10) Build an index on the Name field for the users collection. Is MongoDB able to build the index on that field with the different value types stored in the Name field?
```

```
