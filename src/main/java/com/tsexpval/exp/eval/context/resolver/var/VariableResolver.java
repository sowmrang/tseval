package com.tsexpval.exp.eval.context.resolver.var;

import com.tsexpval.exp.beans.Variable;
import com.tsexpval.exp.eval.context.EvaluationContext;

import java.util.List;

public interface VariableResolver {

    public List<Variable> resolveVariable(String variableName, EvaluationContext evaluationContext);
}
