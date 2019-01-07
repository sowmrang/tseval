package com.tsexpval.ds;

import java.util.Set;

public interface TimeseriesDataset<V>
{
    public V addValue(long timestamp, V value);

    public V getValue(long timestamp);

    public V getValueBefore(long timestamp);

    public Set<Long> getTimestampSeries();

    public int size();

    public static class Builder<V>
    {
        private TimeseriesDataset<V> underlyingDataset;
        private boolean downsample;
        private long downsamplingFrequency;
        private boolean extrapolate;

        public static Builder instance()
        {
            return new Builder();
        }
        public Builder withValue(TimeseriesDataset<V> dataset)
        {
            this.underlyingDataset = dataset;
            return this;
        }

        public Builder withDownsamplingFrequency(long downsamplingFrequency)
        {
            this.downsample = true;
            this.downsamplingFrequency = downsamplingFrequency;
            return this;
        }

        public Builder dataExtrapolation(boolean enabled)
        {
            this.extrapolate = enabled;
            return this;
        }

        public TimeseriesDataset<V> buildTimeseriesDataset()
        {
            TimeseriesDataset<V> createdDataset;
            if(downsample)
            {
                createdDataset = new DownsampledTimeseriesDataset<V>(downsamplingFrequency, underlyingDataset);
            }
            else if(extrapolate)
            {
                createdDataset = new AutoExtrapolatingDataset<V>(underlyingDataset);
            }
            else
            {
                createdDataset = new DefaultTimeseriesDataset<V>();
            }
            return createdDataset;
        }

    }

}
