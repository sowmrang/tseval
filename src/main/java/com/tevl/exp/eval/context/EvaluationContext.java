package com.tevl.exp.eval.context;

import com.tevl.exp.eval.EvaluationConfig;

public abstract class EvaluationContext
{

    private EvaluationConfig evaluationConfig = new EvaluationConfig();

    public EvaluationContext()
    {
    }

    public EvaluationConfig getEvaluationConfig() {
        return evaluationConfig;
    }

    public void setEvaluationConfig(EvaluationConfig evaluationConfig) {
        this.evaluationConfig = evaluationConfig;
    }
}
