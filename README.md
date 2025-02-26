# Time series Expression Evaluator (tseval)

An expression evaluator that can evaluate time series datasets

A time series dataset in this project is essentially a collection of key value pairs where each key value pair 
is of the form

TS -> val

where TS is the timestamp and val is the corresponding value. val is of type java.lang.Number

## Getting Started

### Features
* Support for parsing and evaluation of expressions containing functions
* Support for resolving time series dataset inputs from an external datasource such as JDBC
* Support for adding new custom functions
* Support for different evaluation strategies involving inputs such as extrapolation of missing values, 
downsampling of values to a certain interval 

### Usage

Refer to the class com.tevl.sample.Sample under sources to understand how to use the library to evaluate an
expresion with time series datasets.

#### Step by step walkthrough

The code fragments below are extracted from com.tevl.sample.Sample. For full working code, refer to that class.

Evaluating an expression is very simple.

There are two implementations of datasources currently supported:
1. In memory
2. JDBC

* To evaluate an expression with an in memory dataset, use the following code,

````java
TimeseriesDataset<Number> aVariableDataset = TimeseriesDataset.Builder.<Number>instance().build();
populateDataset(System.currentTimeMillis()+5000,100,aVariableDataset);
...

Map<String,TimeseriesDataset<Number>> datasetMap = new HashMap<>();
datasetMap.put("A",aVariableDataset);
...

TimeseriesDataset<Number> output = Expression.Builder.instance("A+(B+2)").useInMemoryDataSource()
                .withDataSet(datasetMap).evaluate(new StandardEvaluationContext());
````
* To evaluate an expression with values of variables resolved from JDBC store, call `useJdbcDataSource`
````
Expression.Builder.instance("#A+(#B+2)").useJdbcDataSource();
````

EvaluationContext holds the configuration defined around the expression. It's possible to evaluate an expression using following strategies:
1. Extrapolate missing value - Currently, LAST function which would return previous value is being used for extrapolation. 
2. Downsample values to nearest time interval (WIP)

Output time series dataset would vary based on the configuration passed to the EvaluationContext. 

The default behavior is to evaluate the expression for all the matching timestamps across all the input variables.


### Prerequisites

* JDK 1.8

### Installing

It's a gradle project. You can check out the code and run gradlew.

To run the sample, use `exec` target


## License

This project is licensed under Apache 2.0 license - see the [LICENSE.md](LICENSE.md) file for details


