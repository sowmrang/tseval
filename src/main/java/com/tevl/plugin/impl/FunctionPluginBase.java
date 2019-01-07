package com.tevl.plugin.impl;

import com.tevl.exp.eval.EvaluationConfig;
import com.tevl.plugin.FunctionPlugin;
import com.tevl.plugin.PluginDescriptor;

public abstract class FunctionPluginBase implements FunctionPlugin
{
    protected EvaluationConfig evaluationConfig;
    private final PluginDescriptor pluginDescriptor;


    protected FunctionPluginBase()
    {
        this.pluginDescriptor = init();
    }

    @Override
    public final PluginDescriptor getDescriptor() {
        return pluginDescriptor;
    }

    protected abstract PluginDescriptor init();

    public void setEvaluationConfig(EvaluationConfig evaluationConfig) {
        this.evaluationConfig = evaluationConfig;
    }
}
