package com.tevl.datasource;

public interface DataSource {

    //TODO why does this method return Object when the only use of the method expects Map<String,TimeseriesDataset<Number>>
    //TODO why not have just one methos with varargs
    public Object getParameterData(String parameter);

    public Object getParametersData(String[] parameters);
}
