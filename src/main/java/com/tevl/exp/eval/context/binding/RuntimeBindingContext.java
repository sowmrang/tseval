package com.tevl.exp.eval.context.binding;

import com.tevl.exp.beans.Variable;
import com.tevl.exp.eval.context.EvaluationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RuntimeBindingContext
{
    private final Map<String,Object> variables = new HashMap<>();
    private EvaluationContext evaluationContext;

    public void addVariable(String variableName,Variable variable)
    {
        this.variables.put(variableName,variable);
    }

    public void addVariables(String variableName,List<Variable> variables)
    {
        Variable[] variableArray = new Variable[variables.size()];
        for (int i = 0; i < variableArray.length; i++) {
            Variable variable = variables.get(i);
            variableArray[i] = variable;
        }
        this.variables.put(variableName,variableArray);
    }

    public Map<String,Object> getBindingVariables()
    {
        return variables;
    }

    public void setEvaluationContext(EvaluationContext context)
    {
        this.evaluationContext = context;
    }

    public EvaluationContext getEvaluationContext() {
        return evaluationContext;
    }
}
