package com.tsexpval.exp.beans;

import com.tsexpval.ds.AutoExtrapolatingDataset;
import com.tsexpval.ds.DefaultTimeseriesDataset;
import com.tsexpval.ds.TimeseriesDataset;

public class Variable
{


    private String variableName;
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
        if(downsampleValues)
        {
            this.value = TimeseriesDataset.Builder.<Number>instance().withValue(value)
                    .withDownsamplingFrequency(downsamplingFrequency).dataExtrapolation(extrapolate).buildTimeseriesDataset();
        }
        else if(extrapolate)
        {
            this.value = new AutoExtrapolatingDataset<>(value);
        }
        else
        {
            this.value = value;
        }
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
