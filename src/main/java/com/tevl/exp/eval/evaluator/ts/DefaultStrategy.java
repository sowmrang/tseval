package com.tevl.exp.eval.evaluator.ts;

import com.tevl.ds.DefaultTimeseriesDataset;
import com.tevl.ds.TimeseriesDataset;
import com.tevl.exp.beans.Variable;

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
            System.out.println("value = " + value);
            if(value != null && !value.getTimestampSeries().isEmpty()) {

                if (minDatasetSize < value.size()) {
                    minDatasetIndex = i;
                    minDatasetSize = value.size();
                }
            }
        }
        TimeseriesDataset<Number> datasetToIterate = inputs[minDatasetIndex].getValue();

        TimeseriesDataset<Number> outputDataset = new DefaultTimeseriesDataset<>();
        datasetToIterate.getTimestampSeries().forEach(timestamp -> {
            boolean columnValueNull;
            TimeseriesDataset<Number> firstParamDataset = inputs[0].getValue();
            Number firstParamValue = firstParamDataset.getValue(timestamp);
            TimeseriesDataset<Number> secondParamDataset = inputs[1].getValue();
            Number secondParamValue = secondParamDataset.getValue(timestamp);

            if(firstParamValue == null)
            {
                firstParamValue = inputs[0].getDefaultValue();
            }
            if(secondParamValue == null)
            {
                secondParamValue = inputs[1].getDefaultValue();
            }

            columnValueNull = (firstParamValue == null || secondParamValue == null);

            if(!columnValueNull) {
                    outputDataset.addValue(timestamp, functionPluginMethod.apply(firstParamValue,secondParamValue));
            }
        });

        return outputDataset;
    }
}
