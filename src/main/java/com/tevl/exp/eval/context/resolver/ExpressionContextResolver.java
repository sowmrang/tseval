package com.tevl.exp.eval.context.resolver;

import com.tevl.exp.Expression;
import com.tevl.exp.eval.context.EvaluationContext;
import com.tevl.exp.eval.context.binding.RuntimeBindingContext;

public interface ExpressionContextResolver
{

    public RuntimeBindingContext resolveContext(Expression expression, EvaluationContext evaluationContext);
}
