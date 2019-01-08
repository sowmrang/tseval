package com.tevl.exp.engine.impl;

import com.tevl.exp.beans.Variable;
import com.tevl.exp.eval.EvaluationConfig;
import com.tevl.plugin.FunctionPlugin;
import com.tevl.plugin.FunctionPluginRegistry;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Operation;
import org.springframework.expression.OperatorOverloader;
import org.springframework.lang.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class SpelOperatorOverloader implements OperatorOverloader{

    private static Logger LOGGER = Logger.getLogger(SpelOperatorOverloader.class.getName());

    private EvaluationConfig evaluationConfig;

    public SpelOperatorOverloader(EvaluationConfig evaluationConfig)
    {
        this.evaluationConfig = evaluationConfig;
    }


    @Override
    public boolean overridesOperation(Operation operation, @Nullable Object leftOperand, @Nullable Object rightOperand) throws EvaluationException {
        switch (operation)
        {
            case ADD:
            case DIVIDE:
            case MULTIPLY:
            case POWER:
            case SUBTRACT:
            case MODULUS:
                boolean supported = (leftOperand != null && (Variable.class.isAssignableFrom(leftOperand.getClass()) ||
                        Number.class.isAssignableFrom(getOperandType(leftOperand)))) && (rightOperand != null &&
                        (Variable.class.isAssignableFrom(rightOperand.getClass()) ||
                                Number.class.isAssignableFrom(getOperandType(rightOperand))));
                LOGGER.info("Operator  "+operation + " supported? "+supported);
                return supported;

            default:
                return false;
        }
    }

    private  Class<?> getOperandType(Object operand)
    {
        try
        {
            Double.parseDouble(operand.toString());
            return Double.class;
        }
        catch (NumberFormatException e)
        {
            try {
                Long.parseLong(operand.toString());
                return Long.class;
            }
            catch (NumberFormatException exc)
            {
                return Object.class;
            }

        }
    }

    @Override
    public Object operate(Operation operation, @Nullable Object leftOperand, @Nullable Object rightOperand) throws EvaluationException {
        String functionPluginMethodName = null;
        try {
            switch (operation) {
                case ADD:
                    functionPluginMethodName = "add";
                    break;
                case MULTIPLY:
                    functionPluginMethodName = "multiply";
                    break;
                case SUBTRACT:
                    functionPluginMethodName = "subtract";
                    break;
                case DIVIDE:
                    functionPluginMethodName = "divide";
                    break;
                case MODULUS:
                    functionPluginMethodName = "modulus";
                    break;
                case POWER:
                    functionPluginMethodName = "pow";
                    break;
                default:
                    throw new RuntimeException("Unknown operation. This should not have happened");

            }
            LOGGER.info("Operator "+operation + " resolved to method name "+functionPluginMethodName);
            FunctionPlugin fpObj = getMethodPluginContainer(functionPluginMethodName);
            Method method = getMethod(fpObj, functionPluginMethodName, evaluationConfig);
            Variable firstParameter = asVariable(leftOperand);
            Variable secondParameter = asVariable(rightOperand);
            return method.invoke(fpObj, firstParameter, secondParameter);

        }
        catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Unable to invoke the plugin method name "+functionPluginMethodName,e);
        }
    }

    private Variable asVariable(@Nullable Object leftOperand) {
        Variable firstParameter = null;
        if (Variable.class.isAssignableFrom(leftOperand.getClass())) {
            firstParameter = (Variable) leftOperand;
        } else if (Number.class.isAssignableFrom(leftOperand.getClass())) {
            Variable variable = new Variable("const");
            variable.setDefaultValue((Number)leftOperand);
            firstParameter = variable;
        }
        return firstParameter;
    }

    private FunctionPlugin getMethodPluginContainer(String functionPluginMethodName)
    {
        return FunctionPluginRegistry.INSTANCE.getFunctionPlugin(functionPluginMethodName, evaluationConfig);
    }

    private Method getMethod(FunctionPlugin functionPluginObject,String functionPluginMethodName,EvaluationConfig evaluationConfig)
    {
        return functionPluginObject.getDescriptor().getMethod(functionPluginMethodName);
    }

}
