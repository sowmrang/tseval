package com.tevl.exp.eval.context.resolver;

import com.tevl.datasource.DatasourceProvider;

public abstract class ExpressionContextResolverBase implements ExpressionContextResolver {

    protected DatasourceProvider datasourceProvider;

    protected ExpressionContextResolverBase(DatasourceProvider datasourceProvider)
    {
        this.datasourceProvider = datasourceProvider;
    }



}
