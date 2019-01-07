package com.tsexpval.plugin;

import com.tsexpval.exp.eval.EvaluationConfig;
import com.tsexpval.plugin.impl.BasicFunctionsPlugin;
import com.tsexpval.plugin.impl.FunctionPluginBase;

import java.util.*;

public class FunctionPluginRegistry {

    public static final FunctionPluginRegistry INSTANCE = new FunctionPluginRegistry();

    private Map<String,PluginDescriptor> functionPluginMap = new LinkedHashMap<>();



    private FunctionPluginRegistry()
    {
        registerFunctionPlugin(new BasicFunctionsPlugin());
    }

    public void registerFunctionPlugin(FunctionPlugin functionPlugin)
    {
        PluginDescriptor descriptor = functionPlugin.getDescriptor();
        Set<String> supportedFunctions = descriptor.getSupportedFunctions();
        supportedFunctions.forEach((functionName) ->
                functionPluginMap.put(functionName,descriptor));
    }

    public FunctionPlugin getFunctionPlugin(String functionName,EvaluationConfig config)
    {
        PluginDescriptor descriptor = functionPluginMap.get(functionName);
        String className = descriptor.getClassName();

        FunctionPluginBase functionPlugin = null;
        try {
            functionPlugin = (FunctionPluginBase) Class.forName(className).newInstance();
            functionPlugin.setEvaluationConfig(config);
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return functionPlugin;
    }


}
