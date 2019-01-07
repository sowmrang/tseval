package com.tevl.exp;

import com.tevl.datasource.DatasourceProvider;
import com.tevl.datasource.InMemoryDataSource;
import com.tevl.ds.DefaultTimeseriesDataset;
import com.tevl.ds.TimeseriesDataset;
import com.tevl.exp.beans.Variable;
import com.tevl.exp.eval.EvaluationConfig;
import com.tevl.exp.eval.context.EvaluationContext;
import com.tevl.exp.eval.context.StandardEvaluationContext;
import com.tevl.exp.eval.context.resolver.DefaultExpressionContextResolver;
import com.tevl.exp.eval.context.resolver.ExpressionContextResolver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestSpelExpression {

    @Test
    public void testBasicExpression()
    {
        String expression = "add(#A,2)";
        SpelExpression spelExpression = new SpelExpression(expression);
        EvaluationContext evaluationContext = new StandardEvaluationContext();

        TimeseriesDataset<Number> expectedDataset = new DefaultTimeseriesDataset<>();

        InMemoryDataSource dataSource = new InMemoryDataSource();
        TimeseriesDataset<Number> aVariableDataset = new DefaultTimeseriesDataset<>();
        aVariableDataset.addValue(1546626611976L,	0.7309677874);
        aVariableDataset.addValue(1546626611977L,	0.2405364157);
        aVariableDataset.addValue(1546626611978L,	0.6374174254);
        aVariableDataset.addValue(1546626611979L,	0.5504370051);
        aVariableDataset.addValue(1546626611980L,	0.5975452778);
        aVariableDataset.addValue(1546626611981L,	0.3332183995);
        aVariableDataset.addValue(1546626611982L,	0.3851891847);
        aVariableDataset.addValue(1546626611983L,	0.9848415402);
        aVariableDataset.addValue(1546626611984L,	0.8791825179);

        Map<String,TimeseriesDataset<Number>> datasetMap = new LinkedHashMap<>();
        datasetMap.put("A",aVariableDataset);
        dataSource.setDataMap(datasetMap);

        expectedDataset.addValue(1546626611976L,	2.7309677874);
        expectedDataset.addValue(1546626611977L,	2.2405364157);
        expectedDataset.addValue(1546626611978L,	2.6374174254);
        expectedDataset.addValue(1546626611979L,	2.5504370051);
        expectedDataset.addValue(1546626611980L,	2.5975452778);
        expectedDataset.addValue(1546626611981L,	2.3332183995);
        expectedDataset.addValue(1546626611982L,	2.3851891847);
        expectedDataset.addValue(1546626611983L,	2.9848415402);
        expectedDataset.addValue(1546626611984L,	2.8791825179);


        DatasourceProvider datasourceProvider = new DatasourceProvider(dataSource, dataSource);
        ExpressionContextResolver contextResolver = new DefaultExpressionContextResolver(datasourceProvider);

        spelExpression.setExpressionContextResolver(contextResolver);
        Variable value = spelExpression.getValue(evaluationContext);
        TimeseriesDataset<Number> outputDataset = value.getValue();


        Assert.assertTrue(assertEquals(expectedDataset, outputDataset));
    }

    @Test
    public void testTwoVariableFunctionExpression()
    {
        String expression = "add(#A,#B)";
        SpelExpression spelExpression = new SpelExpression(expression);
        EvaluationContext evaluationContext = new StandardEvaluationContext();

        TimeseriesDataset<Number> expectedDataset = new DefaultTimeseriesDataset<>();

        InMemoryDataSource dataSource = new InMemoryDataSource();
        TimeseriesDataset<Number> aVariableDataset = new DefaultTimeseriesDataset<>();
        aVariableDataset.addValue(1546626611976L,	0.7309677874);
        aVariableDataset.addValue(1546626611977L,	0.2405364157);
        aVariableDataset.addValue(1546626611978L,	0.6374174254);
        aVariableDataset.addValue(1546626611979L,	0.5504370051);
        aVariableDataset.addValue(1546626611980L,	0.5975452778);
        aVariableDataset.addValue(1546626611981L,	0.3332183995);
        aVariableDataset.addValue(1546626611982L,	0.3851891847);
        aVariableDataset.addValue(1546626611983L,	0.9848415402);
        aVariableDataset.addValue(1546626611984L,	0.8791825179);

        TimeseriesDataset<Number> bVariableDataset = new DefaultTimeseriesDataset<>();
        bVariableDataset.addValue(1546626611976L,	0.7309677874);
        bVariableDataset.addValue(1546626611977L,	0.2405364157);
        bVariableDataset.addValue(1546626611978L,	0.6374174254);
        bVariableDataset.addValue(1546626611979L,	0.5504370051);
        bVariableDataset.addValue(1546626611980L,	0.5975452778);
        bVariableDataset.addValue(1546626611981L,	0.3332183995);
        bVariableDataset.addValue(1546626611982L,	0.3851891847);
        bVariableDataset.addValue(1546626611983L,	0.9848415402);
        bVariableDataset.addValue(1546626611984L,	0.8791825179);

        Map<String,TimeseriesDataset<Number>> datasetMap = new LinkedHashMap<>();
        datasetMap.put("A",aVariableDataset);
        datasetMap.put("B",bVariableDataset);
        dataSource.setDataMap(datasetMap);

        expectedDataset.addValue(1546626611976L,	1.4619355748);
        expectedDataset.addValue(1546626611977L,	0.4810728314);
        expectedDataset.addValue(1546626611978L,	1.2748348508);
        expectedDataset.addValue(1546626611979L,	1.1008740102);
        expectedDataset.addValue(1546626611980L,	1.1950905556);
        expectedDataset.addValue(1546626611981L,	0.666436799);
        expectedDataset.addValue(1546626611982L,	0.7703783694);
        expectedDataset.addValue(1546626611983L,	1.9696830804);
        expectedDataset.addValue(1546626611984L,	1.7583650358);


        DatasourceProvider datasourceProvider = new DatasourceProvider(dataSource, dataSource);
        ExpressionContextResolver contextResolver = new DefaultExpressionContextResolver(datasourceProvider);

        spelExpression.setExpressionContextResolver(contextResolver);
        Variable value = spelExpression.getValue(evaluationContext);
        TimeseriesDataset<Number> outputDataset = value.getValue();

        Assert.assertTrue(assertEquals(expectedDataset, outputDataset));

    }

    @Test
    public void testTwoVariableExpressionWithExtrapolation()
    {
        String expression = "add(#A,#B)";
        SpelExpression spelExpression = new SpelExpression(expression);
        EvaluationContext evaluationContext = new StandardEvaluationContext();
        EvaluationConfig evaluationConfig = new EvaluationConfig();
        evaluationConfig.setExtrapolateInputsEnabled(true);
        evaluationContext.setEvaluationConfig(evaluationConfig);

        InMemoryDataSource dataSource = new InMemoryDataSource();

        TimeseriesDataset<Number> aVariableDataset = new DefaultTimeseriesDataset<>();
        aVariableDataset.addValue(1546626611976L,	0.7309677874);
        aVariableDataset.addValue(1546626611977L,	0.2405364157);
        aVariableDataset.addValue(1546626611978L,	0.6374174254);
        aVariableDataset.addValue(1546626611979L,	0.5504370051);
        aVariableDataset.addValue(1546626611980L,	0.5975452778);
        aVariableDataset.addValue(1546626611981L,	0.3332183995);
        aVariableDataset.addValue(1546626611982L,	0.3851891847);
        aVariableDataset.addValue(1546626611983L,	0.9848415402);
        aVariableDataset.addValue(1546626611984L,	0.8791825179);

        TimeseriesDataset<Number> bVariableDataset = new DefaultTimeseriesDataset<>();
        bVariableDataset.addValue(1546626611974L,	0.9848415402);
        bVariableDataset.addValue(1546626611983L,	0.8791825179);

        DatasourceProvider datasourceProvider = new DatasourceProvider(dataSource, dataSource);
        ExpressionContextResolver contextResolver = new DefaultExpressionContextResolver(datasourceProvider);

        TimeseriesDataset<Number> expectedDataset = new DefaultTimeseriesDataset<>();
        expectedDataset.addValue(1546626611976L,	1.715809328);
        expectedDataset.addValue(1546626611977L,	1.225377956);
        expectedDataset.addValue(1546626611978L,	1.622258966);
        expectedDataset.addValue(1546626611979L,	1.535278545);
        expectedDataset.addValue(1546626611980L,	1.582386818);
        expectedDataset.addValue(1546626611981L,	1.31805994);
        expectedDataset.addValue(1546626611982L,	1.370030725);
        expectedDataset.addValue(1546626611983L,	1.864024058);
        expectedDataset.addValue(1546626611984L,	1.758365036);



        spelExpression.setExpressionContextResolver(contextResolver);
        Variable value = spelExpression.getValue(evaluationContext);
        TimeseriesDataset<Number> outputDataset = value.getValue();
        assertEquals(expectedDataset, outputDataset);
    }


    private boolean assertEquals(TimeseriesDataset<Number> ds1,TimeseriesDataset<Number> ds2) {
        if (ds1 == null || ds2 == null) {
            return false;
        }
        return ds1.size() == ds2.size() &&
                ds1.getTimestampSeries().stream().allMatch(timestamp -> ds1.getValue(timestamp).equals(ds2.getValue(timestamp)));

    }


}
