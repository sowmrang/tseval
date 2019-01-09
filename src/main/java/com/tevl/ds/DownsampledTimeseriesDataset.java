package com.tevl.ds;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

//TODO no implementation
class DownsampledTimeseriesDataset<T> implements TimeseriesDataset<T> {


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

    @Override
    public T getDefaultValue() {
        return null;
    }

    @Override
    public TimeseriesDataset<T> subRange(Long startTimestamp, Long endTimestamp) {
        return null;
    }

    @Override
    public Iterator<Map.Entry<Long,T>> iterator() {
        return null;
    }
}
