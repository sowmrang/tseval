package com.tevl.exp.eval.evaluator.ts;

import com.tevl.ds.DefaultTimeseriesDataset;
import com.tevl.ds.TimeseriesDataset;
import com.tevl.exp.beans.Variable;

import java.util.function.BiFunction;

public class UnionAllStrategy {

    public TimeseriesDataset<Number> evaluateWithBiNumberFunction(Variable[] inputs,
                                                                  BiFunction<Number,Number,Number> functionPluginMethod)
    {
        TimeseriesDataset<Number> outputDataset = new DefaultTimeseriesDataset<>();
        Number[] parameterValues = new Number[2];
        for (int i = 0; i < inputs.length; i++) {
            TimeseriesDataset<Number> tsInput = inputs[i].getValue();
            if(tsInput != null)
            {
                tsInput.getTimestampSeries().forEach(timestamp -> {
                    TimeseriesDataset<Number> firstParameterTimeSeries = inputs[0].getValue();
                    TimeseriesDataset<Number> secondParameterTimeSeries = inputs[1].getValue();

                    parameterValues[0] = (firstParameterTimeSeries == null ||
                            firstParameterTimeSeries.getValue(timestamp) == null) ?
                            inputs[0].getDefaultValue() : firstParameterTimeSeries.getValue(timestamp);
                    parameterValues[0] = (secondParameterTimeSeries == null ||
                            secondParameterTimeSeries.getValue(timestamp) == null) ?
                            inputs[1].getDefaultValue() : secondParameterTimeSeries.getValue(timestamp);
                    if(parameterValues[0] != null && parameterValues[1] != null)
                    {
                        outputDataset.addValue(timestamp,
                                functionPluginMethod.apply(parameterValues[0],parameterValues[1]));
                    }
                });
            }
        }
        return outputDataset;
    }
}
