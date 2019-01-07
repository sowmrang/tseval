package com.tsexpval.exp.engine.impl;

import com.tsexpval.exp.eval.context.EvaluationContext;
import com.tsexpval.plugin.FunctionPlugin;
import com.tsexpval.plugin.FunctionPluginRegistry;
import com.tsexpval.plugin.PluginDescriptor;
import org.springframework.expression.MethodResolver;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.lang.Nullable;

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

    public EvaluationContext getAdditionalContext() {
        return additionalContext;
    }



    @Override
    public List<MethodResolver> getMethodResolvers() {
        return methodResolvers;
    }



}
