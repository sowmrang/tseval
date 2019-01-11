package com.tevl.exp.eval.evaluator.ts;

import com.tevl.ds.TimeseriesDataset;

import java.util.function.BiFunction;

public class UnionAllStrategy {

    public TimeseriesDataset<Number> evaluateWithBiNumberFunction(Object[] inputs,
                                                                  BiFunction<Number,Number,Number> functionPluginMethod)
    {
        TimeseriesDataset<Number> outputDataset =  TimeseriesDataset.Builder.<Number>instance().build();
        Number[] parameterValues = new Number[2];
        for (int i = 0; i < inputs.length; i++) {
            Object tsInput = inputs[i];
            if(tsInput != null && tsInput instanceof TimeseriesDataset)
            {
                ((TimeseriesDataset<Number>)tsInput).getTimestampSeries().forEach(timestamp -> {
                    boolean columnValueNull = false;

                    if(inputs[0] instanceof TimeseriesDataset)
                    {
                        parameterValues[0] = ((TimeseriesDataset<Number>)inputs[0]).getValue(timestamp);
                        if(parameterValues[0] == null)
                        {
                            parameterValues[0] = ((TimeseriesDataset<Number>)inputs[0]).getDefaultValue();
                        }
                    }
                    else
                    {
                        parameterValues[0] = (Number) inputs[0];
                    }
                    if(inputs[1] instanceof TimeseriesDataset)
                    {
                        parameterValues[1] = ((TimeseriesDataset<Number>)inputs[1]).getValue(timestamp);
                        if(parameterValues[1] == null)
                        {
                            parameterValues[1] = ((TimeseriesDataset<Number>)inputs[1]).getDefaultValue();
                        }

                    }
                    else
                    {
                        parameterValues[1] = (Number) inputs[1];
                    }

                    columnValueNull = (parameterValues[0] == null || parameterValues[1] == null);
                    if(!columnValueNull) {
                        outputDataset.addValue(timestamp,
                                functionPluginMethod.apply(parameterValues[0],parameterValues[1]));
                    }
                });
            }
        }
        return outputDataset;
    }
}
