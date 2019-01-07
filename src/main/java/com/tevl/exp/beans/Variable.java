package com.tevl.exp.beans;

import com.tevl.ds.DefaultTimeseriesDataset;
import com.tevl.ds.TimeseriesDataset;

public class Variable
{
    private final String variableName;
    private TimeseriesDataset<Number> value = new DefaultTimeseriesDataset<>();
    private Number defaultValue;

    public Variable(String variableName) {
        this.variableName = variableName;
    }

    public void setValue(TimeseriesDataset<Number> value)
    {
        this.setValue(value,false,-1,false);
    }

    public void setValue(TimeseriesDataset<Number> value, boolean downsampleValues, long downsamplingFrequency, boolean extrapolate)
    {
        this.value = TimeseriesDataset.Builder.<Number>instance().withValue(value)
                .withDownsamplingFrequency(downsampleValues,downsamplingFrequency)
                .dataExtrapolation(extrapolate).buildTimeseriesDataset();
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
