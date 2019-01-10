package com.tevl.exp.engine.impl;

import com.tevl.exp.engine.impl.op.ArithmeticOperatorHandler;
import com.tevl.exp.engine.impl.op.RelationalOperatorHandler;
import com.tevl.exp.eval.context.EvaluationContext;
import com.tevl.plugin.FunctionPluginRegistry;
import nl.bigo.curta.Curta;
import nl.bigo.curta.Operator;

import java.util.Set;

public class Evaluator {

    private Evaluator()
    {

    }

    public static Curta newInstance(EvaluationContext evaluationContext)
    {
        Curta evaluatorInstance = new Curta();
        Set<String> registerdPluginMethods = FunctionPluginRegistry.INSTANCE.getRegisterdPluginMethods();
        registerdPluginMethods.forEach(pluginMethod ->
                evaluatorInstance.addFunction(new FunctionDelegate(pluginMethod, evaluationContext)));
        evaluatorInstance.setExpression(Operator.GreaterThan,
                new RelationalOperatorHandler(Operator.GreaterThan));
        evaluatorInstance.setExpression(Operator.LessThan,new RelationalOperatorHandler(Operator.LessThan));
        evaluatorInstance.setExpression(Operator.LessThanEqual,new RelationalOperatorHandler(Operator.LessThanEqual));
        evaluatorInstance.setExpression(Operator.GreaterThanEqual,
                new RelationalOperatorHandler(Operator.GreaterThanEqual));
        evaluatorInstance.setExpression(Operator.Equals,
                new RelationalOperatorHandler(Operator.Equals));
        evaluatorInstance.setExpression(Operator.Add,
                new ArithmeticOperatorHandler("add",evaluationContext));
        evaluatorInstance.setExpression(Operator.Subtract,
                new ArithmeticOperatorHandler("subtract",evaluationContext));
        evaluatorInstance.setExpression(Operator.Multiply,
                new ArithmeticOperatorHandler("multiply",evaluationContext));
        evaluatorInstance.setExpression(Operator.Division,
                new ArithmeticOperatorHandler("divide",evaluationContext));
        evaluatorInstance.setExpression(Operator.Modulus,
                new ArithmeticOperatorHandler("modulus",evaluationContext));


        return evaluatorInstance;
    }
}
