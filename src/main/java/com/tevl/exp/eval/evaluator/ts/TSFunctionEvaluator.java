package com.tevl.exp.eval.evaluator.ts;

import com.tevl.ds.TimeseriesDataset;
import com.tevl.exp.beans.Variable;
import com.tevl.exp.eval.EvaluationConfig;

import java.util.function.BiFunction;
import java.util.logging.Logger;

public class TSFunctionEvaluator {

    private static final Logger LOGGER = Logger.getLogger(TSFunctionEvaluator.class.getName());

    public TimeseriesDataset<Number> evaluateWithBiNumberFunction(
            Variable[] parametersDataset, BiFunction<Number,Number,Number> method, EvaluationConfig evaluationConfig)
    {
        //TODO:resolve parameter types
//        if(functionPlugin instanceof AggregateFunctionPlugin)
//        {
//            AggregateFunctionPlugin aggregateFunction = (AggregateFunctionPlugin) functionPlugin;
//            TimeseriesDataset<Number>[] inputs = new TimeseriesDataset[parametersDataset.length];
//            for (int i = 0; i < parametersDataset.length; i++) {
//                Variable variable = parametersDataset[i];
//                inputs[i] = variable.getValue();
//            }
//            return aggregateFunction.invoke(evaluationConfig.getAggregationInterval(),inputs);
//        }


        boolean extrapolateInputsEnabled = evaluationConfig.isExtrapolateInputsEnabled();
        if(extrapolateInputsEnabled) {
            UnionAllStrategy unionAllStrategy = new UnionAllStrategy();
            return unionAllStrategy.evaluateWithBiNumberFunction(parametersDataset, method);
        }
        else {

            boolean allVariablesHaveDefaultValues = true;
            for (int i = 0; i < parametersDataset.length; i++) {
                Variable variable = parametersDataset[i];
                allVariablesHaveDefaultValues = allVariablesHaveDefaultValues && variable.getDefaultValue() != null;
            }
            if(allVariablesHaveDefaultValues)
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


}
