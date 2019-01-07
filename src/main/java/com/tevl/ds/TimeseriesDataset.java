package com.tevl.ds;

import com.tevl.ds.strategy.CarryForwardStrategy;

import java.util.Set;

//TODO why use generics, why not Number or better Double or event better double
public interface TimeseriesDataset<V>
{
    public V addValue(long timestamp, V value);

    public V getValue(long timestamp);

    public V getValueBefore(long timestamp);

    public Set<Long> getTimestampSeries();

    public int size();

    //TODO do you want people to use the builder or constructors of DefaultTimeseriesDataset, DownsampledTimeseriesDataset, AutoExtrapolatingDataset
    //do not provide both the options
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

        public Builder withDownsamplingFrequency(boolean downsamplingEnabled,long downsamplingFrequency)
        {
            //TODO braces formatting is not consistent
            if(downsamplingEnabled) {
                this.downsample = true;
                this.downsamplingFrequency = downsamplingFrequency;
            }
            return this;
        }

        //TODO shouldn't this take in Extrapolation Strategy
        //why hard code CarryForwardStrategy
        public Builder dataExtrapolation(boolean enabled)
        {
            this.extrapolate = enabled;
            return this;
        }

        //TODO name this method build
        public TimeseriesDataset<V> buildTimeseriesDataset()
        {
            TimeseriesDataset<V> createdDataset;
            if(downsample)
            {
                createdDataset = new DownsampledTimeseriesDataset<>(downsamplingFrequency, underlyingDataset);
            }
            else if(extrapolate)
            {
                createdDataset = new AutoExtrapolatingDataset<>(underlyingDataset, new CarryForwardStrategy<>());
            }
            else
            {
                createdDataset = underlyingDataset;
            }
            return createdDataset;
        }

    }

}
