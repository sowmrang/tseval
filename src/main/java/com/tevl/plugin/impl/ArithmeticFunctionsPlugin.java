package com.tevl.plugin.impl;

import com.tevl.ds.TimeseriesDataset;
import com.tevl.exp.eval.evaluator.ts.TSFunctionEvaluator;
import com.tevl.plugin.PluginDescriptor;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.logging.Logger;

public class ArithmeticFunctionsPlugin extends FunctionPluginBase {

    private final DecimalFormat decimalFormat = new DecimalFormat(".######");

    private static Logger LOGGER = Logger.getLogger(ArithmeticFunctionsPlugin.class.getName());

    @Override
    protected PluginDescriptor init() {
        PluginDescriptor descriptor = new PluginDescriptor(ArithmeticFunctionsPlugin.class.getName());
        Map<String,Method> functions = new HashMap<>();
        try {
            Method add = ArithmeticFunctionsPlugin.class.getMethod("add", TimeseriesDataset.class, TimeseriesDataset.class);
            functions.put(add.getName(),add);
            Method subtract = ArithmeticFunctionsPlugin.class.getMethod("subtract", TimeseriesDataset.class, TimeseriesDataset.class);
            functions.put(subtract.getName(),subtract);
            Method multiply = ArithmeticFunctionsPlugin.class.getMethod("multiply", TimeseriesDataset.class, TimeseriesDataset.class);
            functions.put(multiply.getName(),multiply);
            Method divide = ArithmeticFunctionsPlugin.class.getMethod("divide", TimeseriesDataset.class, TimeseriesDataset.class);
            functions.put(divide.getName(),divide);
            Method modulus = ArithmeticFunctionsPlugin.class.getMethod("modulus", TimeseriesDataset.class, TimeseriesDataset.class);
            functions.put(divide.getName(),modulus);
            Method pow = ArithmeticFunctionsPlugin.class.getMethod("pow", TimeseriesDataset.class, TimeseriesDataset.class);
            functions.put(divide.getName(),pow);
            descriptor.setPluginName("basics");
            descriptor.setSupportedFunctions(functions);
        } catch (NoSuchMethodException e) {
            LOGGER.warning("Unable to find method for publishing "+e.getCause());
        }
        return descriptor;
    }

    public TimeseriesDataset<Number> add(TimeseriesDataset<Number> var1,TimeseriesDataset<Number> var2)
    {
        return computeFunction(var1, var2,
                (String p1Str,String p2Str) -> Long.parseLong(p1Str) + Long.parseLong(p2Str),
                (String p1Str,String p2Str) -> Double.parseDouble(
                        decimalFormat.format(Double.parseDouble(p1Str) + Double.parseDouble(p2Str))));
    }

    public TimeseriesDataset<Number> multiply(TimeseriesDataset<Number> var1,TimeseriesDataset<Number> var2)
    {
        return computeFunction(var1, var2,
                (String p1Str,String p2Str) -> Long.parseLong(p1Str) * Long.parseLong(p2Str),
                (String p1Str,String p2Str) -> Double.parseDouble(
                        decimalFormat.format(Double.parseDouble(p1Str) * Double.parseDouble(p2Str))));

    }

    public TimeseriesDataset<Number> subtract(TimeseriesDataset<Number> var1,TimeseriesDataset<Number> var2)
    {
        return computeFunction(var1, var2,
                (String p1Str,String p2Str) -> Long.parseLong(p1Str) - Long.parseLong(p2Str),
                (String p1Str,String p2Str) -> Double.parseDouble(
                        decimalFormat.format(Double.parseDouble(p1Str) - Double.parseDouble(p2Str))));

    }

    public TimeseriesDataset<Number> divide(TimeseriesDataset<Number> var1,TimeseriesDataset<Number> var2)
    {
        return computeFunction(var1, var2,
                (String p1Str,String p2Str) -> Long.parseLong(p1Str) / Long.parseLong(p2Str),
                (String p1Str,String p2Str) -> Double.parseDouble(
                        decimalFormat.format(Double.parseDouble(p1Str) / Double.parseDouble(p2Str))));

    }

    public TimeseriesDataset<Number> modulus(TimeseriesDataset<Number> var1,TimeseriesDataset<Number> var2)
    {
        return computeFunction(var1, var2,
                (String p1Str,String p2Str) -> Long.parseLong(p1Str) % Long.parseLong(p2Str),
                (String p1Str,String p2Str) -> Double.parseDouble(
                        decimalFormat.format(Double.parseDouble(p1Str) % Double.parseDouble(p2Str))));

    }

    public TimeseriesDataset<Number> pow(TimeseriesDataset<Number> var1,TimeseriesDataset<Number> var2)
    {
//        return computeFunction(var1, var2,
//                (String p1Str,String p2Str) -> Math.pow(Long.parseLong(p1Str),  Long.parseLong(p2Str));
//                (String p1Str,String p2Str) -> Double.parseDouble(
//                        decimalFormat.format(Math.pow(Double.parseDouble(p1Str),Double.parseDouble(p2Str)))));

        return null;
    }




    private TimeseriesDataset<Number> computeFunction(TimeseriesDataset<Number> var1, TimeseriesDataset<Number> var2,
                                    BiFunction<String,String,Long> biLongFunction,
                                    BiFunction<String,String,Double> biDoubleFunction)
    {
        TSFunctionEvaluator functionEvaluator = new TSFunctionEvaluator();
        TimeseriesDataset<Number>[] variables =  new TimeseriesDataset[]{var1,var2};
        return functionEvaluator.evaluateWithBiNumberFunction(
                variables, (p1, p2) -> {
                    String var1Str = p1.toString();
                    String var2Str = p2.toString();
                    boolean notanInteger = isNotAnInteger(var1Str, var2Str);

                    if (notanInteger) {
                        return biDoubleFunction.apply(var1Str, var2Str);
                    } else {
                        return biLongFunction.apply(var1Str,var2Str);
                    }

                }, evaluationConfig);

    }


    private static boolean isNotAnInteger(String var1Str, String var2Str) {
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
