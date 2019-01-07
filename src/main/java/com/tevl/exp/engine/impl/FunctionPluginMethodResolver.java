package com.tevl.exp.engine.impl;

import com.tevl.plugin.FunctionPlugin;
import com.tevl.plugin.FunctionPluginRegistry;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.MethodExecutor;
import org.springframework.expression.MethodResolver;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.logging.Logger;

public class FunctionPluginMethodResolver implements MethodResolver {

    private final Logger LOGGER = Logger.getLogger(FunctionPluginMethodResolver.class.getName());

    public FunctionPluginMethodResolver()
    {
    }

    @Nullable
    @Override
    public MethodExecutor resolve(EvaluationContext context, Object targetObject, String name, List<TypeDescriptor> argumentTypes) throws AccessException {
        FunctionPlugin functionPlugin = FunctionPluginRegistry.INSTANCE.getFunctionPlugin(
                name,((SpringElEvaluationContext)context).getAdditionalContext().getEvaluationConfig());
        LOGGER.info("Method "+name + " resolved in plugin "+functionPlugin.getDescriptor().getClassName());
        return new CustomMethodExecutor(name,functionPlugin);
    }

}
