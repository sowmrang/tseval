package com.tevl.plugin;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class PluginDescriptor {

    private Map<String,Method> supportedFunctions;
    private String pluginName;
    private String className;

    public PluginDescriptor(String className)
    {
        this.className = className;
    }

    public Set<String> getSupportedFunctions() {
        return supportedFunctions.keySet();
    }

    public void setSupportedFunctions(Map<String,Method> supportedFunctions) {
        this.supportedFunctions = supportedFunctions;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getClassName() {
        return className;
    }

    public Method getMethod(String methodName)
    {
        return supportedFunctions.get(methodName);
    }

}
