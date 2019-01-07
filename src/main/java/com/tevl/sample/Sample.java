package com.tevl.sample;

import com.tevl.datasource.DatasourceProvider;
import com.tevl.datasource.InMemoryDataSource;
import com.tevl.ds.DefaultTimeseriesDataset;
import com.tevl.ds.TimeseriesDataset;
import com.tevl.exp.SpelExpression;
import com.tevl.exp.beans.Variable;
import com.tevl.exp.eval.context.EvaluationContext;
import com.tevl.exp.eval.context.StandardEvaluationContext;
import com.tevl.exp.eval.context.resolver.DefaultExpressionContextResolver;
import com.tevl.exp.eval.context.resolver.ExpressionContextResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

//TODO move it to tests
public class Sample {

    private static final Logger LOGGER = Logger.getLogger(Sample.class.getName());

    public static void main(String[] args) {

        InMemoryDataSource dataSource = new InMemoryDataSource();
        //TODO this looks a bit weird, the same value being passed in two params, why not just one input
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

        LOGGER.info("------------------Input------------------------------------");
        LOGGER.info(""+datasetMap);
        LOGGER.info("--------------------------------------------------------------------");

        Variable output = expression.getValue(evaluationContext);
        TimeseriesDataset<Number> value = output.getValue();
        LOGGER.info("------------------------------Output--------------------------------");
        LOGGER.info(""+value);
        LOGGER.info("--------------------------------------------------------------------");


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
