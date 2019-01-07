package com.tevl.exp.engine.impl;

import com.tevl.exp.eval.context.EvaluationContext;
import org.springframework.expression.MethodResolver;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.List;

public class SpringElEvaluationContext extends StandardEvaluationContext
{

    private List<MethodResolver> methodResolvers = new ArrayList<>(1);

    private EvaluationContext additionalContext;


    public SpringElEvaluationContext()
    {
        this.methodResolvers.add(new FunctionPluginMethodResolver());
    }


    public void setAdditionalContext(EvaluationContext evaluationContext)
    {
        this.additionalContext = evaluationContext;
    }

    EvaluationContext getAdditionalContext() {
        return additionalContext;
    }



    @Override
    public List<MethodResolver> getMethodResolvers() {
        return methodResolvers;
    }



}
