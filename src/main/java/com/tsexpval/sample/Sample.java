package com.tsexpval.sample;

import com.tsexpval.datasource.DatasourceProvider;
import com.tsexpval.datasource.InMemoryDataSource;
import com.tsexpval.ds.DefaultTimeseriesDataset;
import com.tsexpval.ds.TimeseriesDataset;
import com.tsexpval.exp.SpelExpression;
import com.tsexpval.exp.beans.Variable;
import com.tsexpval.exp.config.ExpressionConfig;
import com.tsexpval.exp.eval.EvaluationConfig;
import com.tsexpval.exp.eval.context.EvaluationContext;
import com.tsexpval.exp.eval.context.StandardEvaluationContext;
import com.tsexpval.exp.eval.context.resolver.DefaultExpressionContextResolver;
import com.tsexpval.exp.eval.context.resolver.ExpressionContextResolver;
import com.tsexpval.exp.eval.context.resolver.var.DefaultVariableResolver;
import com.tsexpval.exp.eval.context.resolver.var.VariableResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Sample {

    public static void main(String[] args) {

        InMemoryDataSource dataSource = new InMemoryDataSource();
        DatasourceProvider datasourceProvider = new DatasourceProvider(dataSource, dataSource);

        TimeseriesDataset<Number> aVariableDataset = new DefaultTimeseriesDataset<>();
        populateDataset(System.currentTimeMillis()+5000,100,aVariableDataset);

        TimeseriesDataset<Number> bVariableDataset = new DefaultTimeseriesDataset<>();
        populateDataset(System.currentTimeMillis()+5010,100,bVariableDataset);

        Map<String,TimeseriesDataset<Number>> datasetMap = new HashMap<>();
        datasetMap.put("A",aVariableDataset);
        datasetMap.put("B",bVariableDataset);
        dataSource.setDataMap(datasetMap);

        SpelExpression expression = new SpelExpression("add(#A,add(#B,2))");


        ExpressionContextResolver expressionContextResolver = new DefaultExpressionContextResolver(
                datasourceProvider);
        expression.setExpressionContextResolver(expressionContextResolver);

        EvaluationContext evaluationContext = new StandardEvaluationContext();



        System.out.println("aVariableDataset = " + aVariableDataset);

        Variable output = expression.getValue(evaluationContext);
        TimeseriesDataset<Number> value = output.getValue();
        System.out.println("value = " + value);


    }

    private  static void populateDataset(long startTimestamp,int count,TimeseriesDataset<Number> dataset)
    {
        Random random = new Random(0);
        for(int i=0; i< count; i++)
        {
            dataset.addValue(startTimestamp +i , random.nextDouble());
        }
    }
}
