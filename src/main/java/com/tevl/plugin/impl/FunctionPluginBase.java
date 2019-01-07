package com.tevl.plugin.impl;

import com.tevl.exp.eval.EvaluationConfig;
import com.tevl.plugin.FunctionPlugin;
import com.tevl.plugin.PluginDescriptor;

import java.util.logging.Logger;

public abstract class FunctionPluginBase implements FunctionPlugin
{
    protected EvaluationConfig evaluationConfig;
    private PluginDescriptor pluginDescriptor;


    public FunctionPluginBase()
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
