package com.tevl.exp;

import com.tevl.datasource.DatasourceProvider;
import com.tevl.datasource.InMemoryDataSource;
import com.tevl.ds.TimeseriesDataset;
import com.tevl.exp.beans.Variable;
import com.tevl.exp.eval.context.EvaluationContext;
import com.tevl.exp.eval.context.resolver.DefaultExpressionContextResolver;
import com.tevl.exp.eval.context.resolver.ExpressionContextResolver;
import com.tevl.exp.eval.context.resolver.var.VariableResolver;

import java.util.Collections;
import java.util.Map;

public class InmemoryDataSourceBuilder extends ExpressionEvaluatorBuilder {

    private Map<String,TimeseriesDataset<Number>> dataset;
    private VariableResolver variableResolver;

    InmemoryDataSourceBuilder(String expressionString)
    {
        super(expressionString);
    }

    public InmemoryDataSourceBuilder withDataSet(Map<String,TimeseriesDataset<Number>> dataSet)
    {
        this.dataset = dataSet;
        return this;
    }

    public InmemoryDataSourceBuilder withVariableResolver(VariableResolver variableResolver)
    {
        this.variableResolver = variableResolver;
        return this;
    }

    public Variable evaluate(EvaluationContext evaluationContext)
    {
        SpelExpression spelExpression = new SpelExpression(expressionString);
        InMemoryDataSource dataSource = new InMemoryDataSource();
        if(dataset != null)
        {
            dataSource.setDataMap(dataset);
        }
        else
        {
            dataSource.setDataMap(Collections.emptyMap());
        }
        DatasourceProvider datasourceProvider = new DatasourceProvider(dataSource, dataSource);
        ExpressionContextResolver expressionContextResolver;
        if(variableResolver == null)
        {
            expressionContextResolver = new DefaultExpressionContextResolver(datasourceProvider);
        }
        else
        {
            expressionContextResolver = new DefaultExpressionContextResolver(datasourceProvider,variableResolver);
        }
        spelExpression.setExpressionContextResolver(expressionContextResolver);
        return spelExpression.getValue(evaluationContext);
    }

}
