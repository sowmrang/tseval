package com.tevl.exp.engine.impl.op;

import com.tevl.exp.eval.context.EvaluationContext;
import com.tevl.plugin.FunctionPlugin;
import com.tevl.plugin.FunctionPluginRegistry;
import nl.bigo.curta.CurtaNode;
import nl.bigo.curta.expression.Expression;
import nl.bigo.curta.function.Function;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class ArithmeticOperatorHandler extends Expression {

    private String operationName;
    private EvaluationContext evaluationContext;

    public ArithmeticOperatorHandler(String operationName, EvaluationContext evaluationContext) {
        this.operationName = operationName;
        this.evaluationContext = evaluationContext;
    }

    @Override
    public Object eval(CurtaNode curtaNode, Map<String, Object> variables,
                       Map<String, Function> functions, Map<Integer, Expression> expressions) {
        Object firstParam = super.evalChild(0,curtaNode, variables, functions, expressions);
        Object secondParam = super.evalChild(1,curtaNode, variables, functions, expressions);
        FunctionPlugin functionPlugin = FunctionPluginRegistry.INSTANCE.getFunctionPlugin(operationName, evaluationContext.getEvaluationConfig());
        Method method = functionPlugin.getDescriptor().getMethod(operationName);
        try {
            return method.invoke(functionPlugin,firstParam,secondParam);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Unable to access method with name "+method.getName(),e);
        }
    }
}
