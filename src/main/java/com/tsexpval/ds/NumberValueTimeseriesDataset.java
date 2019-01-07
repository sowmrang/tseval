package com.tsexpval.ds;

import java.util.Set;

public interface NumberValueTimeseriesDataset<E extends Number> extends TimeseriesDataset<E>
{
    public E addValue(long timestamp, E value);

    public E getValue(long timestamp);

    public E getValueBefore(long timestamp);

    public Set<Long> getTimestampSeries();

    public int size();

}
