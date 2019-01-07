package com.tsexpval.exp.engine.impl;

import com.tsexpval.plugin.FunctionPlugin;
import com.tsexpval.plugin.FunctionPluginRegistry;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.MethodExecutor;
import org.springframework.expression.MethodResolver;
import org.springframework.lang.Nullable;

import java.util.List;

public class FunctionPluginMethodResolver implements MethodResolver {

    public FunctionPluginMethodResolver()
    {
    }

    @Nullable
    @Override
    public MethodExecutor resolve(EvaluationContext context, Object targetObject, String name, List<TypeDescriptor> argumentTypes) throws AccessException {
        FunctionPlugin functionPlugin = FunctionPluginRegistry.INSTANCE.getFunctionPlugin(
                name,((SpringElEvaluationContext)context).getAdditionalContext().getEvaluationConfig());
        return new CustomMethodExecutor(name,functionPlugin);
    }

}
