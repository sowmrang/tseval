package com.tevl.exp;

import com.tevl.datasource.DataSource;
import com.tevl.datasource.DatasourceProvider;
import com.tevl.datasource.JdbcDataSource;
import com.tevl.exp.beans.Variable;
import com.tevl.exp.eval.context.EvaluationContext;
import com.tevl.exp.eval.context.resolver.DefaultExpressionContextResolver;
import com.tevl.exp.eval.context.resolver.ExpressionContextResolver;
import com.tevl.exp.eval.context.resolver.var.VariableResolver;

public class JdbcDataSourceBuilder extends ExpressionEvaluatorBuilder {

    private DataSource configDataSource;
    private DataSource runtimeDataSource;
    private VariableResolver variableResolver;

    public JdbcDataSourceBuilder(String expressionString)
    {
        super(expressionString);
    }

    public JdbcDataSourceBuilder withConfigDataSourceSetting(JdbcDataSource dataSource)
    {
        this.configDataSource = dataSource;
        return this;
    }

    public JdbcDataSourceBuilder withRuntimeDataSourceSetting(JdbcDataSource jdbcDataSource)
    {
        this.runtimeDataSource = jdbcDataSource;
        return this;
    }

    public JdbcDataSourceBuilder withVariableResolver(VariableResolver variableResolver)
    {
        this.variableResolver = variableResolver;
        return this;
    }

    public Variable evaluate(EvaluationContext evaluationContext)
    {
        SpelExpression spelExpression = new SpelExpression(expressionString);
        DatasourceProvider datasourceProvider = new DatasourceProvider(configDataSource, runtimeDataSource);
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
