package com.tsexpval.exp;

import com.tsexpval.exp.beans.Variable;
import com.tsexpval.exp.engine.impl.SpringElEvaluationContext;
import com.tsexpval.exp.eval.context.EvaluationContext;
import com.tsexpval.exp.eval.context.binding.RuntimeBindingContext;
import com.tsexpval.exp.eval.context.resolver.DefaultExpressionContextResolver;
import com.tsexpval.exp.eval.context.resolver.ExpressionContextResolver;
import com.tsexpval.exp.eval.context.resolver.var.DefaultVariableResolver;
import com.tsexpval.plugin.FunctionPluginRegistry;
import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.ast.VariableReference;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpelExpression extends Expression {

    private ExpressionContextResolver expressionContextResolver;
    private org.springframework.expression.Expression spelExpression;

    private List<String> variables;


    public SpelExpression(String expressionString)
    {
        super(expressionString);

        SpelExpressionParser expressionParser = new SpelExpressionParser();
        spelExpression = expressionParser.parseExpression(expressionString);

        SpelNode ast = ((org.springframework.expression.spel.standard.SpelExpression) spelExpression).getAST();

        variables = new ArrayList<>();
        findVariablesRecursively(ast, variables);
    }

    private static void findVariablesRecursively(SpelNode ast,List<String> variableNames)
    {
        int childCount = ast.getChildCount();
        if(ast instanceof VariableReference)
        {
            String astName = ast.toStringAST();
            variableNames.add(astName.substring(astName.indexOf("#")+1));
        }
        if(childCount == 0)
        {
            return;
        }

        for(int i = 0; i< childCount; i++)
        {
            findVariablesRecursively(ast.getChild(i),variableNames);
        }
    }



    public void setExpressionContextResolver(ExpressionContextResolver expressionContextResolver)
    {
        this.expressionContextResolver = expressionContextResolver;
    }

    @Override
    public Variable getValue(EvaluationContext evaluationContext) {
        RuntimeBindingContext bindingContext = expressionContextResolver.resolveContext(this, evaluationContext);
        SpringElEvaluationContext springElEvaluationContext = new SpringElEvaluationContext();
        Map<String,Object> bindingVariables = bindingContext.getBindingVariables();
        bindingVariables.forEach(springElEvaluationContext::setVariable);
        springElEvaluationContext.setRootObject(FunctionPluginRegistry.INSTANCE);
        springElEvaluationContext.setAdditionalContext(evaluationContext);
        Object value = spelExpression.getValue(springElEvaluationContext);
        return (Variable) value;
    }

    @Override
    public List<String> getVariables() {
        return variables;
    }


}
