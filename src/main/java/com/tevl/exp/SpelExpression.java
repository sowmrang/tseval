package com.tevl.exp;

import com.tevl.ds.TimeseriesDataset;
import com.tevl.exp.beans.Variable;
import com.tevl.exp.engine.impl.CustomTypeConverter;
import com.tevl.exp.engine.impl.SpelOperatorOverloader;
import com.tevl.exp.engine.impl.SpringElEvaluationContext;
import com.tevl.exp.eval.context.EvaluationContext;
import com.tevl.exp.eval.context.binding.RuntimeBindingContext;
import com.tevl.exp.eval.context.resolver.ExpressionContextResolver;
import com.tevl.plugin.FunctionPluginRegistry;
import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.ast.VariableReference;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SpelExpression extends Expression {

    private ExpressionContextResolver expressionContextResolver;
    private final org.springframework.expression.Expression spelExpression;

    private final List<String> variables;

    private static final Logger LOGGER = Logger.getLogger(SpelExpression.class.getName());


    SpelExpression(String expressionString)
    {
        super(expressionString);

        LOGGER.info("Processing expression "+ expressionString);
        SpelExpressionParser expressionParser = new SpelExpressionParser();
        spelExpression = expressionParser.parseExpression(expressionString);


        SpelNode ast = ((org.springframework.expression.spel.standard.SpelExpression) spelExpression).getAST();
        variables = new ArrayList<>();
        findVariablesRecursively(ast, variables);
        LOGGER.info("Found variables in expression : "+variables);
    }


    private static void findVariablesRecursively(SpelNode ast,List<String> variableNames)
    {
        int childCount = ast.getChildCount();
        if(ast instanceof VariableReference)
        {
            String astName = ast.toStringAST();
            variableNames.add(astName.substring(astName.indexOf('#')+1));
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
    public TimeseriesDataset<Number> getValue(EvaluationContext evaluationContext) {
        RuntimeBindingContext bindingContext = expressionContextResolver.resolveContext(this, evaluationContext);
        SpringElEvaluationContext springElEvaluationContext = new SpringElEvaluationContext();
        Map<String,Object> bindingVariables = bindingContext.getBindingVariables();
        String variableWithNullValue = null;

        for (String variableName : bindingVariables.keySet()) {
            TimeseriesDataset<Number> value = ((Variable) bindingVariables.get(variableName)).getValue();
            if(value == null)
            {
                variableWithNullValue = variableName;
                break;
            }
            springElEvaluationContext.setVariable(variableName, value);
        }

        if(variableWithNullValue != null)
        {
            LOGGER.severe("Unable to compute expression. Value of variable "+variableWithNullValue+ " is null");
            return null;
        }

        springElEvaluationContext.setRootObject(FunctionPluginRegistry.INSTANCE);
        springElEvaluationContext.setOperatorOverloader(
                new SpelOperatorOverloader(evaluationContext.getEvaluationConfig()));
        springElEvaluationContext.setTypeConverter(new CustomTypeConverter());
        springElEvaluationContext.setAdditionalContext(evaluationContext);

        return (TimeseriesDataset<Number>)spelExpression.getValue(springElEvaluationContext,TimeseriesDataset.class);
    }

    @Override
    public List<String> getVariables() {
        return variables;
    }


}
