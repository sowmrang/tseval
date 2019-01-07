package com.tevl.datasource;

public interface DataSource {

    public Object getParameterData(String parameter);

    public Object getParametersData(String[] parameters);
}
