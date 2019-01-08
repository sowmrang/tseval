package com.tevl.exp;

public class ExpressionEvaluatorBuilder {
    String expressionString;

    ExpressionEvaluatorBuilder(String expressionString) {
        this.expressionString = expressionString;
    }

    static ExpressionEvaluatorBuilder instance(String expressionString) {
        return new ExpressionEvaluatorBuilder(expressionString);
    }

    public InmemoryDataSourceBuilder useInMemoryDataSource() {
        return new InmemoryDataSourceBuilder(expressionString);
    }

    public JdbcDataSourceBuilder useJdbcDataSource() {
        return new JdbcDataSourceBuilder(expressionString);
    }


}
