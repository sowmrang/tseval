package com.tevl.datasource;

import com.tevl.ds.TimeseriesDataset;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InMemoryDataSource implements DataSource {

    private Map<String,TimeseriesDataset<Number>> inputDatasetMap = new HashMap<>();

    public void setDataMap(Map<String,TimeseriesDataset<Number>> dataset)
    {
        this.inputDatasetMap = dataset;
    }


    @Override
    public Object getParameterData(String parameter) {
        return getParametersData(new String[]{parameter});
    }

    @Override
    public Object getParametersData(String[] parameters) {

        Map<String,TimeseriesDataset<Number>> datasetMap = new HashMap<>();
        Arrays.stream(parameters).forEach(variable -> {
            datasetMap.put(variable,inputDatasetMap.get(variable));
        });

        return datasetMap;
    }
}
