package com.tevl.exp.engine.impl;

import com.tevl.ds.TimeseriesDataset;
import com.tevl.exp.beans.Variable;
import com.tevl.plugin.FunctionPlugin;
import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.MethodExecutor;
import org.springframework.expression.TypedValue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomMethodExecutor implements MethodExecutor {

    private final Method method;
    private final FunctionPlugin functionPlugin;

    public CustomMethodExecutor(String functionName,FunctionPlugin plugin)
    {

         method = plugin.getDescriptor().getMethod(functionName);
         this.functionPlugin = plugin;
    }



    @Override
    public TypedValue execute(EvaluationContext context, Object target, Object... arguments) throws AccessException {

        Object output;
        try {
            for (int i = 0; i < arguments.length; i++) {
                Object argument = arguments[i];
                if(!(argument instanceof Variable))
                {
                    TimeseriesDataset<Number> constTSDataset;
                    try {
                        constTSDataset = TimeseriesDataset.Builder.<Number>instance()
                                .withDefaultValue(Long.parseLong(argument.toString())).build();
                    }
                    catch (NumberFormatException exc)
                    {
                        constTSDataset = TimeseriesDataset.Builder.<Number>instance()
                                .withDefaultValue(Double.parseDouble(argument.toString())).build();
                    }
                    arguments[i] = constTSDataset;
                }
            }
            output = method.invoke(functionPlugin,arguments);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Unable to access method with name "+method.getName(),e);
        }
        return new TypedValue(output);
    }

}
