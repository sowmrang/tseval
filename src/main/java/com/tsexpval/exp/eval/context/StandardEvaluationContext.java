package com.tsexpval.exp.eval.context;

import java.util.Map;

public class StandardEvaluationContext extends EvaluationContext {

    private Map<String,Object> contextMetadata;

    public StandardEvaluationContext()
    {

    }

    public StandardEvaluationContext(Map<String,Object> contextMetadata)
    {
        this.contextMetadata = contextMetadata;
    }

    public Map<String, Object> getContextMetadata() {
        return contextMetadata;
    }
}
