package com.tevl.datasource;

import com.tevl.ds.TimeseriesDataset;

import java.util.Map;

public interface DataSource {


    public Map<String,TimeseriesDataset<Number>> getParametersData(String... parameters);
}
