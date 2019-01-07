package com.tevl.exp.eval.context.resolver.var;

import com.tevl.exp.beans.Variable;
import com.tevl.exp.eval.context.EvaluationContext;

import java.util.List;

public interface VariableResolver {

    public List<Variable> resolveVariable(String variableName, EvaluationContext evaluationContext);
}
