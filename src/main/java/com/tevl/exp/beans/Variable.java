package com.tevl.exp.beans;

import com.tevl.ds.TimeseriesDataset;
import com.tevl.ds.strategy.CarryForwardStrategy;

import java.util.Map;

public class Variable
{
    private final String variableName;
    private TimeseriesDataset<Number> value;
    private Number defaultValue;

    public Variable(String variableName) {
        this.variableName = variableName;
    }

    public void setValue(TimeseriesDataset<Number> value)
    {
        this.value = value;
    }

    public void setValue(Map<Long,Number> values, boolean downsampleValues, long downsamplingFrequency, boolean extrapolate)
    {
        this.value = TimeseriesDataset.Builder.<Number>instance().withDataset(values)
                .withDownsamplingFrequency(downsampleValues,downsamplingFrequency)
                .dataExtrapolation(extrapolate,new CarryForwardStrategy<>()).build();
    }



    public TimeseriesDataset<Number> getValue()
    {
        return value;
    }

    public String getVariableName() {
        return variableName;
    }

    public Number getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Number defaultValue) {
        this.defaultValue = defaultValue;
    }
}
