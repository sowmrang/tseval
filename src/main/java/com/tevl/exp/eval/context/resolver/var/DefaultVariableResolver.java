package com.tevl.exp.eval.context.resolver.var;

import com.tevl.datasource.DataSource;
import com.tevl.datasource.DatasourceProvider;
import com.tevl.ds.TimeseriesDataset;
import com.tevl.exp.beans.Variable;
import com.tevl.exp.eval.context.EvaluationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultVariableResolver implements VariableResolver {

    private final DatasourceProvider dataSourceProvider;

    public DefaultVariableResolver(DatasourceProvider dataSourceProvider)
    {
        this.dataSourceProvider = dataSourceProvider;
    }

    @Override
    public List<Variable> resolveVariable(String variableName, EvaluationContext evaluationContext) {
        DataSource runtimeDatasource = dataSourceProvider.getRuntimeDatasource();
        Map<String,TimeseriesDataset<Number>> parameterData =
                //TODO unchecked cast
                (Map<String,TimeseriesDataset<Number>>)runtimeDatasource.getParameterData(variableName);
        Variable variable = new Variable(variableName);
        TimeseriesDataset<Number> value = parameterData.get(variableName);
        variable.setValue(value);
        List<Variable> variableList = new ArrayList<>(1);
        variableList.add(variable);
        return variableList;
    }
}
