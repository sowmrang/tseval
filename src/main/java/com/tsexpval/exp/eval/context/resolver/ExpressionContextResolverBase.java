package com.tsexpval.exp.eval.context.resolver;

import com.tsexpval.datasource.DatasourceProvider;

public abstract class ExpressionContextResolverBase implements ExpressionContextResolver {

    protected DatasourceProvider datasourceProvider;

    public ExpressionContextResolverBase(DatasourceProvider datasourceProvider)
    {
        this.datasourceProvider = datasourceProvider;
    }



}
