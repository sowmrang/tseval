package com.tevl.ds;

import com.tevl.ds.strategy.ExtrapolationStrategy;

import java.util.Map;
import java.util.Set;

//TODO why use generics, why not Number or better Double or event better double
public interface TimeseriesDataset<V> extends Iterable<Map.Entry<Long,V>> {
    //TODO try to make timeseries immutable
    public V addValue(long timestamp, V value);

    public V getValue(long timestamp);

    public V getValueBefore(long timestamp);

    public Set<Long> getTimestampSeries();

    public int size();

    public V getDefaultValue();


    public static class Builder<V> {
        private Map<Long, V> underlyingDataset;
        private boolean downsample;
        private long downsamplingFrequency;
        private boolean extrapolate;
        private ExtrapolationStrategy<V> extrapolationStrategy;
        private V defaultValue;

        public static <T> Builder<T> instance() {
            return new Builder<T>();
        }

        public Builder<V> withDataset(Map<Long, V> dataset) {
            underlyingDataset = dataset;
            return this;
        }

        public Builder<V> withDefaultValue(V value) {
            this.defaultValue = value;
            return this;
        }

        public Builder<V> withDownsamplingFrequency(boolean downsamplingEnabled, long downsamplingFrequency) {
            //TODO braces formatting is not consistent
            if (downsamplingEnabled) {
                this.downsample = true;
                this.downsamplingFrequency = downsamplingFrequency;
            }
            return this;
        }

        public Builder<V> dataExtrapolation(boolean enabled, ExtrapolationStrategy<V> strategy) {
            this.extrapolate = enabled;
            this.extrapolationStrategy = strategy;
            return this;
        }

        public TimeseriesDataset<V> build() {
            final TimeseriesDataset<V> valueHolder = new DefaultTimeseriesDataset<>(defaultValue);
            TimeseriesDataset<V> createdDataset;
            if (underlyingDataset != null) {
                underlyingDataset.forEach(valueHolder::addValue);
            }
            if (downsample) {
                createdDataset = new DownsampledTimeseriesDataset<>(downsamplingFrequency, valueHolder);
            } else if (extrapolate) {
                createdDataset = new AutoExtrapolatingDataset<>(valueHolder, extrapolationStrategy);
            } else {
                createdDataset = valueHolder;
            }
            return createdDataset;
        }

    }

}
