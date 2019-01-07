package com.tsexpval.ds;

import java.util.Set;

public class AutoExtrapolatingDataset<T> implements TimeseriesDataset<T> {

    private TimeseriesDataset<T> underlyingDataset;

    public AutoExtrapolatingDataset(TimeseriesDataset<T> dataset)
    {
        this.underlyingDataset = dataset;
    }


    @Override
    public T addValue(long timestamp,T v) {
        return underlyingDataset.addValue(timestamp, v);
    }

    @Override
    public T getValue(long timestamp) {
        T value = underlyingDataset.getValue(timestamp);

        if(value == null)
        {
            return underlyingDataset.getValueBefore(timestamp);
        }

        return value;
    }

    @Override
    public T getValueBefore(long timestamp) {
        return underlyingDataset.getValueBefore(timestamp);
    }

    @Override
    public Set<Long> getTimestampSeries() {
        return underlyingDataset.getTimestampSeries();
    }

    @Override
    public int size() {
        return underlyingDataset.size();
    }
}
