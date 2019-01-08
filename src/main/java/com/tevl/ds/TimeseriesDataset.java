package com.tevl.ds;

import com.tevl.ds.strategy.ExtrapolationStrategy;

import java.util.Map;
import java.util.Set;

//TODO why use generics, why not Number or better Double or event better double
public interface TimeseriesDataset<V>
{
    //TODO try to make timeseries immutable
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
        private ExtrapolationStrategy<V> extrapolationStrategy;

        public static <T>  Builder<T> instance()
        {
            return new Builder<T>();
        }
        public  Builder<V> withDataset(Map<Long,V> dataset)
        {
            underlyingDataset = new DefaultTimeseriesDataset<>();
            dataset.forEach((ts,value) -> underlyingDataset.addValue(ts, value));
            return this;
        }

        public Builder<V> withDownsamplingFrequency(boolean downsamplingEnabled,long downsamplingFrequency)
        {
            //TODO braces formatting is not consistent
            if(downsamplingEnabled) {
                this.downsample = true;
                this.downsamplingFrequency = downsamplingFrequency;
            }
            return this;
        }

        public Builder<V> dataExtrapolation(boolean enabled, ExtrapolationStrategy<V> strategy)
        {
            this.extrapolate = enabled;
            this.extrapolationStrategy = strategy;
            return this;
        }

        public TimeseriesDataset<V> build()
        {
            TimeseriesDataset<V> createdDataset;
            if(downsample)
            {
                createdDataset = new DownsampledTimeseriesDataset<>(downsamplingFrequency, underlyingDataset);
            }
            else if(extrapolate)
            {
                createdDataset = new AutoExtrapolatingDataset<>(underlyingDataset, extrapolationStrategy);
            }
            else
            {
                if(underlyingDataset == null)
                {
                    createdDataset = new DefaultTimeseriesDataset<>();
                }
                else
                {
                    createdDataset = underlyingDataset;
                }
            }
            return createdDataset;
        }

    }

}
