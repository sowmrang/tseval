package com.tsexpval.exp.eval.evaluator.ts;

import com.tsexpval.ds.TimeseriesDataset;
import com.tsexpval.exp.beans.Variable;
import com.tsexpval.exp.eval.EvaluationConfig;

import java.util.function.BiFunction;

public class UnionAllStrategy {

    public TimeseriesDataset<Number> evaluateWithBiNumberFunction(Variable[] inputs, BiFunction<Number,Number,Number> functionPlugin, EvaluationConfig evaluationConfig)
    {
        //TODO:implement
        return null;
    }
}
