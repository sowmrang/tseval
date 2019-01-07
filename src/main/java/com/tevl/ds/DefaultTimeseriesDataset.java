package com.tevl.ds;

import java.util.Collections;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

public class DefaultTimeseriesDataset<T> implements TimeseriesDataset<T> {

    private final NavigableMap<Long,T> tsDataset = new TreeMap<>();

    public DefaultTimeseriesDataset()
    {
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
}
