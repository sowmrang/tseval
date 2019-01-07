package com.tsexpval.datasource;


public final class DatasourceProvider {

    private DataSource configDatasource;
    private DataSource runtimeDatasource;


    public DatasourceProvider(DataSource configDatasource,DataSource runtimeDatasource)
    {
        this.configDatasource = configDatasource;
        this.runtimeDatasource = runtimeDatasource;
    }

    public DataSource getConfigDatasource() {
        return configDatasource;
    }

    public DataSource getRuntimeDatasource() {
        return runtimeDatasource;
    }
}
