package com.tevl.exp;

import com.tevl.exp.beans.Variable;
import com.tevl.exp.eval.context.EvaluationContext;

import java.util.List;

public abstract class Expression {

    protected String expressionString;

    protected Expression(String expressionString)
    {
        this.expressionString = expressionString;
    }

    public abstract Variable getValue(EvaluationContext evaluationContext);

    public abstract List<String> getVariables();

    public String getExpressionString() {
        return expressionString;
    }
}
