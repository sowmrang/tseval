package com.tsexpval.plugin.impl;

import com.tsexpval.ds.TimeseriesDataset;
import com.tsexpval.exp.beans.Variable;
import com.tsexpval.exp.eval.evaluator.ts.TSFunctionEvaluator;
import com.tsexpval.plugin.PluginDescriptor;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class BasicFunctionsPlugin extends FunctionPluginBase {

    private DecimalFormat decimalFormat = new DecimalFormat(".##########");

    public BasicFunctionsPlugin()
    {
    }

    @Override
    protected PluginDescriptor init() {
        PluginDescriptor descriptor = new PluginDescriptor(BasicFunctionsPlugin.class.getName());
        Map<String,Method> functions = new HashMap<>();
        try {
            Method add = BasicFunctionsPlugin.class.getMethod("add", Variable.class, Variable.class);
            functions.put(add.getName(),add);
            Method subtract = BasicFunctionsPlugin.class.getMethod("subtract", Variable.class, Variable.class);
            functions.put(subtract.getName(),subtract);
            Method multiply = BasicFunctionsPlugin.class.getMethod("multiply", Variable.class, Variable.class);
            functions.put(multiply.getName(),multiply);
            Method divide = BasicFunctionsPlugin.class.getMethod("divide", Variable.class, Variable.class);
            functions.put(divide.getName(),divide);
            descriptor.setPluginName(BasicFunctionsPlugin.class.getName());
            descriptor.setSupportedFunctions(functions);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return descriptor;
    }

    public Variable add(Variable var1,Variable var2)
    {
        return computeFunction(var1, var2,
                (String p1Str,String p2Str) -> Long.parseLong(p1Str) + Long.parseLong(p2Str),
                (String p1Str,String p2Str) -> Double.parseDouble(
                        decimalFormat.format(Double.parseDouble(p1Str) + Double.parseDouble(p2Str))));
    }

    public Variable multiply(Variable var1,Variable var2)
    {
        return computeFunction(var1, var2,
                (String p1Str,String p2Str) -> Long.parseLong(p1Str) * Long.parseLong(p2Str),
                (String p1Str,String p2Str) -> Double.parseDouble(
                        decimalFormat.format(Double.parseDouble(p1Str) * Double.parseDouble(p2Str))));

    }

    public Variable subtract(Variable var1,Variable var2)
    {
        return computeFunction(var1, var2,
                (String p1Str,String p2Str) -> Long.parseLong(p1Str) - Long.parseLong(p2Str),
                (String p1Str,String p2Str) -> Double.parseDouble(
                        decimalFormat.format(Double.parseDouble(p1Str) - Double.parseDouble(p2Str))));

    }

    public Variable divide(Variable var1,Variable var2)
    {
        return computeFunction(var1, var2,
                (String p1Str,String p2Str) -> Long.parseLong(p1Str) / Long.parseLong(p2Str),
                (String p1Str,String p2Str) -> Double.parseDouble(
                        decimalFormat.format(Double.parseDouble(p1Str) / Double.parseDouble(p2Str))));

    }



    private Variable computeFunction(Variable var1, Variable var2,
                                    BiFunction<String,String,Long> biLongFunction,
                                    BiFunction<String,String,Double> biDoubleFunction)
    {
        TSFunctionEvaluator functionEvaluator = new TSFunctionEvaluator();
        TimeseriesDataset<Number> output = functionEvaluator.evaluateWithBiNumberFunction(
                new Variable[]{var1, var2}, (p1, p2) -> {
                    String var1Str = p1.toString();
                    String var2Str = p2.toString();
                    boolean notanInteger = isNotAnInteger(var1Str, var2Str);

                    if (notanInteger) {
                        return biDoubleFunction.apply(var1Str, var2Str);
                    } else {
                        return biLongFunction.apply(var1Str,var2Str);
                    }

                }, evaluationConfig);
        Variable variable = new Variable("out");
        variable.setValue(output);
        return variable;

    }


    private boolean isNotAnInteger(String var1Str, String var2Str) {
        boolean notanInteger = false;
        try {
            Long.parseLong(var1Str);
            Long.parseLong(var2Str);
        } catch (NumberFormatException exc) {
            notanInteger = true;
        }
        return notanInteger;
    }


}
