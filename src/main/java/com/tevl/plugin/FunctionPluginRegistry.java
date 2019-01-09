package com.tevl.plugin;

import com.tevl.exp.eval.EvaluationConfig;
import com.tevl.plugin.impl.ArithmeticFunctionsPlugin;
import com.tevl.plugin.impl.FunctionPluginBase;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class FunctionPluginRegistry {

    private static final Logger LOGGER = Logger.getLogger(FunctionPluginRegistry.class.getName());
    public static final FunctionPluginRegistry INSTANCE = new FunctionPluginRegistry();

    private final Map<String,PluginDescriptor> functionPluginMap = new LinkedHashMap<>();



    private FunctionPluginRegistry()
    {
        registerFunctionPlugin(new ArithmeticFunctionsPlugin());
    }

    public void registerFunctionPlugin(FunctionPlugin functionPlugin)
    {
        LOGGER.info("Registering function plugin "+functionPlugin.getDescriptor().getClassName());
        PluginDescriptor descriptor = functionPlugin.getDescriptor();
        Set<String> supportedFunctions = descriptor.getSupportedFunctions();
        supportedFunctions.forEach((functionName) ->
                functionPluginMap.put(functionName,descriptor));
    }

    public FunctionPlugin getFunctionPlugin(String pluginName,EvaluationConfig config)
    {
        PluginDescriptor descriptor = functionPluginMap.get(pluginName);
        String className = descriptor.getClassName();

        FunctionPluginBase functionPlugin = null;
        try {
            functionPlugin = (FunctionPluginBase) Class.forName(className).newInstance();
            functionPlugin.setEvaluationConfig(config);
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            throw new RuntimeException("Unable to instantiate plugin "+className,e);
        }
        return functionPlugin;
    }


}
