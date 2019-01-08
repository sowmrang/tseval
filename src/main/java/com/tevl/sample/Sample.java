package com.tevl.sample;

import com.tevl.ds.TimeseriesDataset;
import com.tevl.exp.Expression;
import com.tevl.exp.beans.Variable;
import com.tevl.exp.eval.context.StandardEvaluationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

//TODO move it to tests
public class Sample {

    private static final Logger LOGGER = Logger.getLogger(Sample.class.getName());

    public static void main(String[] args) {

        TimeseriesDataset<Number> aVariableDataset = TimeseriesDataset.Builder.<Number>instance().build();
        populateDataset(System.currentTimeMillis()+5000,100,aVariableDataset);

        TimeseriesDataset<Number> bVariableDataset = TimeseriesDataset.Builder.<Number>instance().build();
        populateDataset(System.currentTimeMillis()+5010,100,bVariableDataset);

        Map<String,TimeseriesDataset<Number>> datasetMap = new HashMap<>();
        datasetMap.put("A",aVariableDataset);
        datasetMap.put("B",bVariableDataset);

        Variable output = Expression.Builder.instance("#A+(#B+2)").useInMemoryDataSource()
                .withDataSet(datasetMap).evaluate(new StandardEvaluationContext());
        TimeseriesDataset<Number> value = output.getValue();

        LOGGER.info("------------------Input------------------------------------");
        LOGGER.info(""+datasetMap);
        LOGGER.info("--------------------------------------------------------------------");

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
