package com.tsexpval.exp.eval;

public class EvaluationConfig {

    private boolean downsampleEnabled;
    private long downsampleFrequency;
    private boolean extrapolateInputsEnabled;

    private long aggregationInterval;

    public boolean isExtrapolateInputsEnabled() {
        return extrapolateInputsEnabled;
    }

    public void setExtrapolateInputsEnabled(boolean extrapolateInputsEnabled) {
        this.extrapolateInputsEnabled = extrapolateInputsEnabled;
    }

    public boolean isDownsampleEnabled() {
        return downsampleEnabled;
    }

    public void setDownsampleEnabled(boolean downsampleEnabled) {
        this.downsampleEnabled = downsampleEnabled;
    }

    public long getDownsampleFrequency() {
        return downsampleFrequency;
    }

    public void setDownsampleFrequency(long downsampleFrequency) {
        this.downsampleFrequency = downsampleFrequency;
    }

    public long getAggregationInterval() {
        return aggregationInterval;
    }

    public void setAggregationInterval(long aggregationInterval) {
        this.aggregationInterval = aggregationInterval;
    }
}
