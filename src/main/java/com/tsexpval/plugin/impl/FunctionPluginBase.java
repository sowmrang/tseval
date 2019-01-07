package com.tsexpval.plugin.impl;

import com.tsexpval.exp.eval.EvaluationConfig;
import com.tsexpval.plugin.FunctionPlugin;
import com.tsexpval.plugin.PluginDescriptor;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class FunctionPluginBase implements FunctionPlugin
{
    protected EvaluationConfig evaluationConfig;
    private PluginDescriptor pluginDescriptor;

    private static Logger LOGGER = Logger.getLogger(FunctionPluginBase.class.getName());

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
