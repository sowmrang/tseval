package com.tevl.ds;

import java.util.Set;

public class DownsampledTimeseriesDataset<T> implements TimeseriesDataset<T> {



    public DownsampledTimeseriesDataset(long downsamplingFrequency,TimeseriesDataset<T> underlyingdataset)
    {

    }

    @Override
    public T addValue(long timestamp, T value) {
        return null;
    }

    @Override
    public T getValue(long timestamp) {
        return null;
    }

    @Override
    public T getValueBefore(long timestamp) {
        return null;
    }

    @Override
    public Set<Long> getTimestampSeries() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
