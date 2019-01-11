package com.tevl.exp.eval.evaluator.ts;

import com.tevl.ds.TimeseriesDataset;

import java.util.function.BiFunction;
import java.util.logging.Logger;

public class DefaultStrategy {

    private static Logger LOGGER = Logger.getLogger(DefaultStrategy.class.getName());

    public TimeseriesDataset<Number> evaluateWithNumberFunction(Object[] inputs,
                                                                BiFunction<Number,Number,Number> functionPluginMethod)
    {
        int minDatasetIndex = -1;
        int minDatasetSize = 0;
        for (int i = 0; i < inputs.length; i++) {
            Object input = inputs[i];
            if(input != null && input instanceof TimeseriesDataset) {
                TimeseriesDataset<Number> value = (TimeseriesDataset<Number>) input;
                if ( !value.getTimestampSeries().isEmpty()) {

                    if (minDatasetSize < value.size()) {
                        minDatasetIndex = i;
                        minDatasetSize = value.size();
                    }
                }
            }
        }
        TimeseriesDataset<Number> datasetToIterate = (TimeseriesDataset<Number>) inputs[minDatasetIndex];
        TimeseriesDataset<Number> outputDataset = TimeseriesDataset.Builder.<Number>instance().build();



        datasetToIterate.getTimestampSeries().forEach(timestamp -> {
            boolean columnValueNull;
            Number firstParamValue;
            Number secondParamValue;
            if(inputs[0] instanceof TimeseriesDataset)
            {
                firstParamValue = ((TimeseriesDataset<Number>)inputs[0]).getValue(timestamp);
                if(firstParamValue == null)
                {
                    firstParamValue = ((TimeseriesDataset<Number>)inputs[0]).getDefaultValue();
                }
            }
            else
            {
                firstParamValue = (Number) inputs[0];
            }
            if(inputs[1] instanceof TimeseriesDataset)
            {
                secondParamValue = ((TimeseriesDataset<Number>)inputs[1]).getValue(timestamp);
                if(secondParamValue == null)
                {
                    secondParamValue = ((TimeseriesDataset<Number>)inputs[1]).getDefaultValue();
                }

            }
            else
            {
                secondParamValue = (Number) inputs[1];
            }

            columnValueNull = (firstParamValue == null || secondParamValue == null);

            if(!columnValueNull) {
                    outputDataset.addValue(timestamp, functionPluginMethod.apply(firstParamValue,secondParamValue));
            }
        });

        return outputDataset;
    }
}
