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
mongoimport --jsonArray --db cs167 --collection contacts --file ~/Downloads/contacts.json
2020-05-22T12:53:24.520+0800	connected to: localhost
2020-05-22T12:53:24.691+0800	imported 10 documents
```

* (Q2) Retrieve all the users sorted by name.
```
use cs167
switched to db cs167
db.contacts.find().sort({Name: 1}).pretty()
{
	"_id" : ObjectId("5ec75ac4bd5f78741c9c3192"),
	"Name" : "Aguirre Fox",
	"Address" : {
		"StreetNumber" : 540,
		"streetName" : "High Street",
		"city" : "Bloomington",
		"state" : "SC",
		"ZIPCode" : 29823
	},
	"Friends" : [
		"Glenn Mcbride",
		"Marlene Macias",
		"Constance Arnold",
		"Beard Dotson",
		"Hester Lowe"
	],
	"Active" : true,
	"DOB" : "Sat Mar 15 2014 06:04:01 GMT+0000 (UTC)",
	"Age" : 49
}
...
```

* (Q3) List only the id and names sorted in reverse alphabetical order by name (Z-to-A).
```
db.contacts.find({},{_id: 1,Name: 1}).sort({Name: -1}).pretty()
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3196"), "Name" : "Workman Holloway" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3197"), "Name" : "Susan Graham" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3193"), "Name" : "Sandy Oneil" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3195"), "Name" : "Patrick Thornton" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3198"), "Name" : "Levine Johnston" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3199"), "Name" : "Hayes Weaver" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3191"), "Name" : "Craft Parks" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3194"), "Name" : "Cooke Schroeder" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c319a"), "Name" : "Aimee Mcintosh" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3192"), "Name" : "Aguirre Fox" }

```

* (Q4) Is the comparison of the attribute name case-sensitive? Show how you try this with the previous query and include your answer.
```
db.contacts.insert({Name: "alex"})
WriteResult({ "nInserted" : 1 })

db.contacts.insert({Name: "bob"})
WriteResult({ "nInserted" : 1 })

db.contacts.find({},{_id: 1,Name: 1}).sort({Name: -1}).pretty()
{ "_id" : ObjectId("5ec75e41f4fd20fd97859f0b"), "Name" : "bob" }
{ "_id" : ObjectId("5ec75e15f4fd20fd97859f0a"), "Name" : "alex" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3196"), "Name" : "Workman Holloway" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3197"), "Name" : "Susan Graham" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3193"), "Name" : "Sandy Oneil" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3195"), "Name" : "Patrick Thornton" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3198"), "Name" : "Levine Johnston" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3199"), "Name" : "Hayes Weaver" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3191"), "Name" : "Craft Parks" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3194"), "Name" : "Cooke Schroeder" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c319a"), "Name" : "Aimee Mcintosh" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3192"), "Name" : "Aguirre Fox" }

Yes, the comparision of the attribute Name is case-sensitive. As we can see from the result, lowercases are ranked before uppercases in descending order.
```

* (Q5) Repeat Q3 above but do not show the _id field.
```
db.contacts.find({},{_id: 0,Name: 1}).sort({Name: -1}).pretty()
{ "Name" : "bob" }
{ "Name" : "alex" }
{ "Name" : "Workman Holloway" }
{ "Name" : "Susan Graham" }
{ "Name" : "Sandy Oneil" }
{ "Name" : "Patrick Thornton" }
{ "Name" : "Levine Johnston" }
{ "Name" : "Hayes Weaver" }
{ "Name" : "Craft Parks" }
{ "Name" : "Cooke Schroeder" }
{ "Name" : "Aimee Mcintosh" }
{ "Name" : "Aguirre Fox" }
```

* (Q6) Insert the following document to the collection.
```
{Name: {First: “David”, Last: “Bark”}}
```
Does MongoDB accept this document while the name field has a different type than other records?
```
db.contacts.insert({Name: {First: "David", Last: "Bark"}})
WriteResult({ "nInserted" : 1 })

Yes, it does.
```
Rerun Q3, which lists the records sorted by name
```
db.contacts.find({},{_id: 1,Name: 1}).sort({Name: -1}).pretty()
```

* (Q7) Where do you expect the new record to be located in the sort order? Verify the answer and explain.
```
The new record should be located at very first since the name of the new document is Object rather than String.

{
	"_id" : ObjectId("5ec75f58f4fd20fd97859f0c"),
	"Name" : {
		"First" : "David",
		"Last" : "Bark"
	}
}
{ "_id" : ObjectId("5ec75e41f4fd20fd97859f0b"), "Name" : "bob" }
{ "_id" : ObjectId("5ec75e15f4fd20fd97859f0a"), "Name" : "alex" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3196"), "Name" : "Workman Holloway" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3197"), "Name" : "Susan Graham" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3193"), "Name" : "Sandy Oneil" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3195"), "Name" : "Patrick Thornton" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3198"), "Name" : "Levine Johnston" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3199"), "Name" : "Hayes Weaver" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3191"), "Name" : "Craft Parks" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3194"), "Name" : "Cooke Schroeder" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c319a"), "Name" : "Aimee Mcintosh" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3192"), "Name" : "Aguirre Fox" }

As is seen, Object is sorted before String in descending order.
```

Insert the following document into the users collection.
```
{Name: [“David”, “Bark”]}

db.contacts.insert({Name: ["David", "Bark"]})
WriteResult({ "nInserted" : 1 })
```

* Repeat Q3. (Q8) Where do you expect the new document to appear in the sort order. Verify your answer and explain after running the query.
```
It will be located between "Hayes Weaver" and "Craft Parks" since the name of the new document is array. In descending order, we will order array via its maximum value.

db.contacts.find({},{_id: 1,Name: 1}).sort({Name: -1}).pretty()
{
	"_id" : ObjectId("5ec75f58f4fd20fd97859f0c"),
	"Name" : {
		"First" : "David",
		"Last" : "Bark"
	}
}
{ "_id" : ObjectId("5ec75e41f4fd20fd97859f0b"), "Name" : "bob" }
{ "_id" : ObjectId("5ec75e15f4fd20fd97859f0a"), "Name" : "alex" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3196"), "Name" : "Workman Holloway" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3197"), "Name" : "Susan Graham" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3193"), "Name" : "Sandy Oneil" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3195"), "Name" : "Patrick Thornton" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3198"), "Name" : "Levine Johnston" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3199"), "Name" : "Hayes Weaver" }
{
	"_id" : ObjectId("5ec76061f4fd20fd97859f0d"),
	"Name" : [
		"David",
		"Bark"
	]
}
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3191"), "Name" : "Craft Parks" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3194"), "Name" : "Cooke Schroeder" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c319a"), "Name" : "Aimee Mcintosh" }
{ "_id" : ObjectId("5ec75ac4bd5f78741c9c3192"), "Name" : "Aguirre Fox" }
```

* Repeat Q3 again with all the objects that you inserted, but this time sort the name in ascending order. 
(Q9) Where do you expect the last inserted record, {Name: [“David”, “Bark”]} to appear this time? 
Does it appear in the same position relative to the other records? Explain why or why not.
```
It will be located between "Aimee Mcintosh" and "Cooke Schroeder" since the name of the new document is array. In ascending order, we will order array via its minimum value.

No, since this time we are sorting in ascending order, and the array will be ordered via the minimum value "Bark".
```

* (Q10) Build an index on the Name field for the users collection. Is MongoDB able to build the index on that field with the different value types stored in the Name field?
```
db.contacts.createIndex({Name: 1})
{
	"createdCollectionAutomatically" : false,
	"numIndexesBefore" : 1,
	"numIndexesAfter" : 2,
	"ok" : 1
}

db.contacts.getIndexes()
[
	{
		"v" : 2,
		"key" : {
			"_id" : 1
		},
		"name" : "_id_",
		"ns" : "cs167.contacts"
	},
	{
		"v" : 2,
		"key" : {
			"Name" : 1
		},
		"name" : "Name_1",
		"ns" : "cs167.contacts"
	}
]

Yes, we can build the index on the field with the different value types stored in the Name field.
```
