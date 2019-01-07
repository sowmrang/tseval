package com.tsexpval.exp.eval.context.resolver.var;

import com.tsexpval.datasource.DataSource;
import com.tsexpval.datasource.DatasourceProvider;
import com.tsexpval.ds.TimeseriesDataset;
import com.tsexpval.exp.beans.Variable;
import com.tsexpval.exp.eval.context.EvaluationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultVariableResolver implements VariableResolver {

    private DatasourceProvider dataSourceProvider;

    public DefaultVariableResolver(DatasourceProvider dataSourceProvider)
    {
        this.dataSourceProvider = dataSourceProvider;
    }

    @Override
    public List<Variable> resolveVariable(String variableName, EvaluationContext evaluationContext) {
        DataSource runtimeDatasource = dataSourceProvider.getRuntimeDatasource();
        Map<String,TimeseriesDataset<Number>> parameterData =
                (Map<String,TimeseriesDataset<Number>>)runtimeDatasource.getParameterData(variableName);
        Variable variable = new Variable(variableName);
        variable.setValue(parameterData.get(variableName));
        List<Variable> variableList = new ArrayList<>(1);
        variableList.add(variable);
        return variableList;
    }
}
