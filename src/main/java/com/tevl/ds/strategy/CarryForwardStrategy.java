package com.tevl.ds.strategy;

import com.tevl.ds.TimeseriesDataset;

public class CarryForwardStrategy<T> implements ExtrapolationStrategy<T> {


    @Override
    public T getValue(long timestamp,TimeseriesDataset<T> dataset) {

        return dataset.getValueBefore(timestamp);
    }
}
