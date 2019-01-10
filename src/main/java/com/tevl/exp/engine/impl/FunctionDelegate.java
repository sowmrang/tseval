package com.tevl.exp.engine.impl;

import com.tevl.exp.eval.context.EvaluationContext;
import com.tevl.plugin.FunctionPlugin;
import com.tevl.plugin.FunctionPluginRegistry;
import nl.bigo.curta.function.Function;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FunctionDelegate extends Function {

    private EvaluationContext evaluationContext;

    public FunctionDelegate(String id,EvaluationContext evaluationContext) {
        super(id);
        this.evaluationContext = evaluationContext;
    }

    @Override
    public Object eval(Object... objects) {
        FunctionPlugin functionPlugin = FunctionPluginRegistry.INSTANCE.getFunctionPlugin(id, evaluationContext.getEvaluationConfig());
        Method method = functionPlugin.getDescriptor().getMethod(id);
        try {
            return method.invoke(functionPlugin,objects);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Unable to access method with name "+method.getName(),e);
        }
    }
}
