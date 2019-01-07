package com.tevl.exp.eval.context.resolver;

import com.tevl.datasource.DatasourceProvider;
import com.tevl.exp.Expression;
import com.tevl.exp.beans.Variable;
import com.tevl.exp.eval.context.EvaluationContext;
import com.tevl.exp.eval.context.binding.DefaultRuntimeBindingContext;
import com.tevl.exp.eval.context.binding.RuntimeBindingContext;
import com.tevl.exp.eval.context.resolver.var.DefaultVariableResolver;
import com.tevl.exp.eval.context.resolver.var.VariableResolver;

import java.util.List;

public class DefaultExpressionContextResolver extends ExpressionContextResolverBase
{
    private VariableResolver variableResolver;

    public DefaultExpressionContextResolver(DatasourceProvider datasourceProvider)
    {
        super(datasourceProvider);
        this.variableResolver = new DefaultVariableResolver(datasourceProvider);
    }

    public DefaultExpressionContextResolver(DatasourceProvider datasourceProvider,VariableResolver variableResolver)
    {
        super(datasourceProvider);
        this.variableResolver = variableResolver;
    }

    @Override
    public RuntimeBindingContext resolveContext(Expression expression, EvaluationContext evaluationContext)
    {
        List<String> variables = expression.getVariables();
        RuntimeBindingContext runtimeContext = new DefaultRuntimeBindingContext();
        for (String variable : variables) {
            List<Variable> list = variableResolver.resolveVariable(variable, evaluationContext);

            if(list.size() == 1) {
                runtimeContext.addVariable(variable, list.get(0));
            }
            //TODO:handle multiple variables
        }

        runtimeContext.setEvaluationContext(evaluationContext);
        return runtimeContext;
    }
}
