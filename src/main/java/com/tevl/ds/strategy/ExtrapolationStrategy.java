package com.tevl.ds.strategy;

import com.tevl.ds.TimeseriesDataset;

public interface ExtrapolationStrategy<T> {

    public T getValue(long timestamp,TimeseriesDataset<T> dataset);
}
