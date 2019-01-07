package com.tsexpval.exp.eval.context.resolver;

import com.tsexpval.exp.Expression;
import com.tsexpval.exp.eval.context.EvaluationContext;
import com.tsexpval.exp.eval.context.binding.RuntimeBindingContext;

public interface ExpressionContextResolver
{

    public RuntimeBindingContext resolveContext(Expression expression, EvaluationContext evaluationContext);
}
