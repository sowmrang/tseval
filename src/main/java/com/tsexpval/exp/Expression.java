package com.tsexpval.exp;

import com.tsexpval.exp.beans.Variable;
import com.tsexpval.exp.eval.context.EvaluationContext;

import java.util.List;

public abstract class Expression {

    protected String expressionString;

    public Expression(String expressionString)
    {
        this.expressionString = expressionString;
    }

    public abstract Variable getValue(EvaluationContext evaluationContext);

    public abstract List<String> getVariables();

    public String getExpressionString() {
        return expressionString;
    }
}
