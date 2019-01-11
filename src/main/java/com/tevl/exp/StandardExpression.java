package com.tevl.exp;

import com.tevl.ds.TimeseriesDataset;
import com.tevl.exp.beans.Variable;
import com.tevl.exp.engine.impl.Evaluator;
import com.tevl.exp.eval.context.EvaluationContext;
import com.tevl.exp.eval.context.binding.RuntimeBindingContext;
import com.tevl.exp.eval.context.resolver.ExpressionContextResolver;
import nl.bigo.curta.*;

import java.io.StringReader;
import java.util.*;
import java.util.logging.Logger;

public class StandardExpression extends Expression {

    private List<String> variables;
    private ExpressionContextResolver expressionContextResolver;

    private static final Logger LOGGER = Logger.getLogger(StandardExpression.class.getName());


    StandardExpression(String expressionString) {
        super(expressionString);
        CurtaParser expressionParser = new CurtaParser(new StringReader(expressionString));
        List<String> variables = new ArrayList<>();
        try {
            findVariables(expressionParser.ast(), variables);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid expression " + expressionString, e);
        }
        this.variables = Collections.unmodifiableList(variables);
    }

    public void setExpressionContextResolver(ExpressionContextResolver expressionContextResolver) {
        this.expressionContextResolver = expressionContextResolver;
    }


    @Override
    public TimeseriesDataset<Number> getValue(EvaluationContext evaluationContext) {

        RuntimeBindingContext bindingContext = expressionContextResolver.resolveContext(this, evaluationContext);
        Map<String, Object> bindingVariables = bindingContext.getBindingVariables();
        String variableWithNullValue = null;

        Curta evaluatorInstance = Evaluator.newInstance(evaluationContext);
        System.out.println("variables = " + variables);
        for (String variableName : bindingVariables.keySet()) {
            TimeseriesDataset<Number> value = ((Variable) bindingVariables.get(variableName)).getValue();
            if (value == null) {
                variableWithNullValue = variableName;
                break;
            }
            evaluatorInstance.addVariable(variableName, value);
        }

        if (variableWithNullValue != null) {
            LOGGER.severe("Unable to compute expression. Value of variable " + variableWithNullValue + " is null");
            return null;
        }

        try {
            return (TimeseriesDataset<Number>) evaluatorInstance.eval(expressionString);

        } catch (ParseException e) {
            throw new RuntimeException("Invalid expression " + expressionString, e);
        }
    }

    @Override
    public List<String> getVariables() {
        return variables;
    }


    private void findVariables(CurtaNode node, List<String> variables) {
        if (node.treeType == CurtaParserTreeConstants.JJTID) {
            variables.add(node.jjtGetValue().toString());
        }

        int children = node.jjtGetNumChildren();

        if (children == 0) {
            return;
        }
        for (int i = 0; i < children; i++) {
            findVariables((CurtaNode) node.jjtGetChild(i), variables);
        }
    }

}
