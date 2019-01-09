package com.tevl.exp;

import com.tevl.ds.TimeseriesDataset;
import com.tevl.exp.eval.context.EvaluationContext;

import java.util.List;

public abstract class Expression {


    protected String expressionString;

    protected Expression(String expressionString)
    {
        this.expressionString = expressionString;
    }

    public abstract TimeseriesDataset<Number> getValue(EvaluationContext evaluationContext);

    public abstract List<String> getVariables();

    public String getExpressionString() {
        return expressionString;
    }

    public static class Builder
    {
        public static ExpressionEvaluatorBuilder instance(String expressionString)
        {
            return ExpressionEvaluatorBuilder.instance(expressionString);
        }
    }

}
