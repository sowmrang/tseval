package com.tevl.exp.eval.evaluator.ts;

import com.tevl.ds.TimeseriesDataset;
import com.tevl.exp.beans.Variable;

import java.util.Collections;
import java.util.function.BiFunction;

public class DefaultStrategy {

    public TimeseriesDataset<Number> evaluateWithNumberFunction(Variable[] inputs,
                                                                BiFunction<Number,Number,Number> functionPluginMethod)
    {
        int minDatasetIndex = -1;
        int minDatasetSize = 0;
        for (int i = 0; i < inputs.length; i++) {
            Variable input = inputs[i];
            TimeseriesDataset<Number> value = input.getValue();
            if(value != null && !value.getTimestampSeries().isEmpty()) {

                if (minDatasetSize < value.size()) {
                    minDatasetIndex = i;
                    minDatasetSize = value.size();
                }
            }
        }
        TimeseriesDataset<Number> datasetToIterate = inputs[minDatasetIndex].getValue();
        TimeseriesDataset<Number> outputDataset = TimeseriesDataset.Builder.<Number>instance().build();
        datasetToIterate.getTimestampSeries().forEach(timestamp -> {
            boolean columnValueNull;
            TimeseriesDataset<Number> firstParamDataset = inputs[0].getValue();
            Number firstParamValue = (firstParamDataset == null || firstParamDataset.getValue(timestamp) == null)
                    ? inputs[0].getDefaultValue() : firstParamDataset.getValue(timestamp);
            TimeseriesDataset<Number> secondParamDataset = inputs[1].getValue();
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
