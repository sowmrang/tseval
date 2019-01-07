package com.tsexpval.exp.eval.context;

import com.tsexpval.exp.eval.EvaluationConfig;

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
