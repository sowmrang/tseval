package com.tevl.exp.eval.evaluator.ts;

import com.tevl.ds.TimeseriesDataset;
import com.tevl.exp.eval.EvaluationConfig;

import java.util.function.BiFunction;
import java.util.logging.Logger;

public class TSFunctionEvaluator {

    private static final Logger LOGGER = Logger.getLogger(TSFunctionEvaluator.class.getName());

    public Object evaluateWithBiNumberFunction(
            Object[] parametersDataset, BiFunction<Number,Number,Number> method, EvaluationConfig evaluationConfig)
    {
        boolean extrapolateInputsEnabled = evaluationConfig.isExtrapolateInputsEnabled();

            boolean allVariablesHaveDefaultValues = true;
            boolean variableHasInvalidValue = false;
            boolean validParameterTypes = false;
            for (int i = 0; i < parametersDataset.length; i++) {
                Object parameter = parametersDataset[i];
                if(parameter instanceof TimeseriesDataset) {
                    TimeseriesDataset<Number> variable = (TimeseriesDataset<Number>) parameter;
                    allVariablesHaveDefaultValues = allVariablesHaveDefaultValues && variable.getDefaultValue() != null;
                    if (variable.getDefaultValue() == null && variable.size() == 0) {
                        variableHasInvalidValue = true;
                        break;
                    }
                }
                validParameterTypes = validParameterTypes || (parameter instanceof TimeseriesDataset);
            }
            if(variableHasInvalidValue)
            {
                LOGGER.info("One of the variables  neither has a valid value nor is a constant. " +
                        "Cannot evaluate the expression");
                return TimeseriesDataset.Builder.<Number>instance().build();
            }
            if(!validParameterTypes)
            {
                LOGGER.severe("Invalid parameter types passed in expression. One of the parameters must be " +
                        "of type TimeseriesDataset");
                throw new RuntimeException("Cannot compute the expression ");
            }
            if(allVariablesHaveDefaultValues || extrapolateInputsEnabled)
            {
                LOGGER.info("Using union all strategy to evaluate expression");
                UnionAllStrategy unionAllStrategy = new UnionAllStrategy();
                return unionAllStrategy.evaluateWithBiNumberFunction(parametersDataset, method);
            }
            else {
                LOGGER.info("Using default strategy to evaluate expression");
                DefaultStrategy defaultStrategy = new DefaultStrategy();
                return defaultStrategy.evaluateWithNumberFunction(parametersDataset, method);
            }
    }


}
