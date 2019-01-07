package com.tsexpval.exp.eval.evaluator.ts;

import com.tsexpval.ds.TimeseriesDataset;
import com.tsexpval.exp.beans.Variable;
import com.tsexpval.exp.eval.EvaluationConfig;

import java.util.function.BiFunction;

public class TSFunctionEvaluator {

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
            return unionAllStrategy.evaluateWithBiNumberFunction(parametersDataset, method, evaluationConfig);
        }
        else {

            boolean allVariablesHaveDefaultValues = true;
            for (int i = 0; i < parametersDataset.length; i++) {
                Variable variable = parametersDataset[i];
                allVariablesHaveDefaultValues = allVariablesHaveDefaultValues && variable.getDefaultValue() != null;
            }
            if(allVariablesHaveDefaultValues)
            {
                UnionAllStrategy unionAllStrategy = new UnionAllStrategy();
                return unionAllStrategy.evaluateWithBiNumberFunction(parametersDataset, method, evaluationConfig);
            }
            else {
                DefaultStrategy defaultStrategy = new DefaultStrategy();
                return defaultStrategy.evaluateWithNumberFunction(parametersDataset, method);
            }
        }
    }


}
