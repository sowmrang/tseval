package com.tevl.exp.eval.evaluator.ts;

import com.tevl.ds.TimeseriesDataset;

import java.util.function.BiFunction;

public class DefaultStrategy {

    public TimeseriesDataset<Number> evaluateWithNumberFunction(TimeseriesDataset<Number>[] inputs,
                                                                BiFunction<Number,Number,Number> functionPluginMethod)
    {
        int minDatasetIndex = -1;
        int minDatasetSize = 0;
        for (int i = 0; i < inputs.length; i++) {
            TimeseriesDataset<Number> value = inputs[i];;
            if(value != null && !value.getTimestampSeries().isEmpty()) {

                if (minDatasetSize < value.size()) {
                    minDatasetIndex = i;
                    minDatasetSize = value.size();
                }
            }
        }
        TimeseriesDataset<Number> datasetToIterate = inputs[minDatasetIndex];
        TimeseriesDataset<Number> outputDataset = TimeseriesDataset.Builder.<Number>instance().build();
        datasetToIterate.getTimestampSeries().forEach(timestamp -> {
            boolean columnValueNull;
            TimeseriesDataset<Number> firstParamDataset = inputs[0];
            Number firstParamValue = (firstParamDataset == null || firstParamDataset.getValue(timestamp) == null)
                    ? inputs[0].getDefaultValue() : firstParamDataset.getValue(timestamp);
            TimeseriesDataset<Number> secondParamDataset = inputs[1];
            Number secondParamValue = (secondParamDataset == null || secondParamDataset.getValue(timestamp) == null)
                    ? inputs[1].getDefaultValue() : secondParamDataset.getValue(timestamp);

            columnValueNull = (firstParamValue == null || secondParamValue == null);

            if(!columnValueNull) {
                    outputDataset.addValue(timestamp, functionPluginMethod.apply(firstParamValue,secondParamValue));
            }
        });

        return outputDataset;
    }
}
