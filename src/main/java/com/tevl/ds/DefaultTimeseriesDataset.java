package com.tevl.ds;

import java.util.*;

class DefaultTimeseriesDataset<T> implements TimeseriesDataset<T> {

    private NavigableMap<Long,T> tsDataset = new TreeMap<>();
    private T defaultValue;

    public DefaultTimeseriesDataset()
    {
    }

    public DefaultTimeseriesDataset(T defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    @Override
    public T addValue(long timestamp, T value) {
        return tsDataset.put(timestamp, value);
    }

    @Override
    public T getValue(long timestamp) {
        return tsDataset.get(timestamp);
    }

    @Override
    public T getValueBefore(long timestamp) {
        Map.Entry<Long, T> lowerEntry = tsDataset.lowerEntry(timestamp);
        return (lowerEntry == null) ? null : lowerEntry.getValue();
    }

    @Override
    public T getDefaultValue() {
        return defaultValue;
    }

    @Override
    public Set<Long> getTimestampSeries() {
        return Collections.unmodifiableSet(tsDataset.keySet());
    }

    @Override
    public int size() {
        return tsDataset.size();
    }



    public String toString()
    {
        StringBuilder outputBuffer = new StringBuilder(100);
        for (Long key : tsDataset.keySet()) {
            outputBuffer.append(key).append(" -> ").append(tsDataset.get(key)).append("\n");
        }
        return outputBuffer.toString();
    }

    @Override
    public Iterator<Map.Entry<Long,T>> iterator() {
        return new TSDatasetIterator<>(tsDataset.entrySet().iterator());
    }

    public TimeseriesDataset<T> subRange(Long startTimestamp, Long endTimestamp) {
        Map<Long, T> subMap = tsDataset.subMap(startTimestamp, endTimestamp);
        DefaultTimeseriesDataset<T> subDataset = new DefaultTimeseriesDataset<>();
        subDataset.tsDataset = new TreeMap<>(subMap);
        return subDataset;
    }
}
