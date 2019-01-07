*WIP*
# Project Title

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


* Create an expression using SpelExpression. Specify the expression to be processed in the form of String.
SpelExpression uses Spring EL internally. You can refer to the Spring EL library to understand the syntax of expressions supported.

````java
SpelExpression expression = new SpelExpression("add(#A,add(#B,2))");

````
* Create an instance of DatasourceProvider with appropriate references of datasource. If configuration and runtime data are managed separately, you can
initialize with the appropriate datasource instances. There are two implementations of datasources currently supported:
1. In memory
2. JDBC

Below code fragment uses an in memory datasource. You need to initialize it with data for all the variables defined in the expression.

````java
InMemoryDataSource dataSource = new InMemoryDataSource();
TimeseriesDataset<Number> aVariableDataset = new DefaultTimeseriesDataset<>();
...
TimeseriesDataset<Number> bVariableDataset = new DefaultTimeseriesDataset<>();
Map<String,TimeseriesDataset<Number>> datasetMap = new HashMap<>();
datasetMap.put("A",aVariableDataset);
datasetMap.put("B",bVariableDataset);
dataSource.setDataMap(datasetMap);

DatasourceProvider datasourceProvider = new DatasourceProvider(dataSource, dataSource); 

````
* Create an instance of ExpressionContextResolver and pass the reference of DatasourceProvider to it.
ExpressionContextResolver resolves various elements such as variables defined in the expression and binds respective 
values to the evaluation runtime

````java
ExpressionContextResolver expressionContextResolver = new DefaultExpressionContextResolver(
        datasourceProvider);
expression.setExpressionContextResolver(expressionContextResolver);

````

* Finally, call the getValue of Expression to obtain the calculated value. You need to create an instance 
of EvaluationContext. EvaluationContext can hold any additional metadata that may supplement the evaluation at runtime

````java
Variable output = expression.getValue(evaluationContext);

````
EvaluationContext holds the configuration defined around the expression. It's possible to evaluate an expression using following strategies:
1. Extrapolate missing value - Currently, LAST function which would return previous value is being used for extrapolation. (WIP)
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


