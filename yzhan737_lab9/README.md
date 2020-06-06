# Lab 9

## Student information

* Full name:
```
Yaming Zhang
``` 
* E-mail:
```
yzhan737@cs.ucr.edu
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

* (Q1) In the README file, explain how you did it and include a code snippet that highlights the code that you added to make it work.
```scala
val assembler = new VectorAssembler()
  .setInputCols(Array("bedrooms", "bathrooms", "sqft_living", "sqft_lot"))
  .setOutputCol("features")

// We can only change the above code to change the features we use on the linear regression
```
* (Q2) Which one provided the best model for you?

| features | correlation |
| --- | --- |
| Array("bedrooms", "bathrooms", "sqft_living", "sqft_lot") | 0.7111964197654979 |
| Array("bedrooms", "bathrooms", "sqft_living", "sqft_lot", "condition", "waterfront", "view") | 0.7665519167983011 |
| Array("sqft_living", "sqft_lot", "sqft_above", "sqft_basement", "sqft_living15", "sqft_lot15") | 0.7167954498996727 |
| Array("bedrooms", "bathrooms", "condition", "waterfront", "view", "sqft_living", "sqft_lot", "sqft_above", "sqft_basement", "sqft_living15", "sqft_lot15") | 0.7463374649989146 |

```
According to the test, the best model is Array("bedrooms", "bathrooms", "sqft_living", "sqft_lot", "condition", "waterfront", "view")
```
