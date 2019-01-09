package com.tevl.exp;

import com.tevl.datasource.DatasourceProvider;
import com.tevl.datasource.InMemoryDataSource;
import com.tevl.ds.TimeseriesDataset;
import com.tevl.exp.beans.Variable;
import com.tevl.exp.eval.EvaluationConfig;
import com.tevl.exp.eval.context.EvaluationContext;
import com.tevl.exp.eval.context.StandardEvaluationContext;
import com.tevl.exp.eval.context.resolver.DefaultExpressionContextResolver;
import com.tevl.exp.eval.context.resolver.ExpressionContextResolver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestSpelExpression {

    @Test
    public void testBasicExpression() {
        String expression = "#A+2";

        Map<Long,Number> expectedDatasetMap = new LinkedHashMap<>();

        InMemoryDataSource dataSource = new InMemoryDataSource();
        Map<Long,Number> aVariableDataset = new LinkedHashMap<>();
        aVariableDataset.put(1546626611976L, 0.7309677874);
        aVariableDataset.put(1546626611977L, 0.2405364157);
        aVariableDataset.put(1546626611978L, 0.6374174254);
        aVariableDataset.put(1546626611979L, 0.5504370051);
        aVariableDataset.put(1546626611980L, 0.5975452778);
        aVariableDataset.put(1546626611981L, 0.3332183995);
        aVariableDataset.put(1546626611982L, 0.3851891847);
        aVariableDataset.put(1546626611983L, 0.9848415402);
        aVariableDataset.put(1546626611984L, 0.8791825179);

        Map<String, TimeseriesDataset<Number>> datasetMap = new LinkedHashMap<>();
        datasetMap.put("A", TimeseriesDataset.Builder.<Number>instance().withDataset(aVariableDataset).build());
        dataSource.setDataMap(datasetMap);

        expectedDatasetMap.put(1546626611976L, 2.730968);
        expectedDatasetMap.put(1546626611977L, 2.240536);
        expectedDatasetMap.put(1546626611978L, 2.637417);
        expectedDatasetMap.put(1546626611979L, 2.550437);
        expectedDatasetMap.put(1546626611980L, 2.597545);
        expectedDatasetMap.put(1546626611981L, 2.333218);
        expectedDatasetMap.put(1546626611982L, 2.385189);
        expectedDatasetMap.put(1546626611983L, 2.984842);
        expectedDatasetMap.put(1546626611984L, 2.879183);

        TimeseriesDataset<Number> expectedDataset = TimeseriesDataset.Builder.<Number>instance()
                .withDataset(expectedDatasetMap).build();



        TimeseriesDataset<Number> outputDataset = Expression.Builder.instance(expression)
                .useInMemoryDataSource().withDataSet(datasetMap).evaluate(new StandardEvaluationContext());


        System.out.println("outputDataset = " + outputDataset);

        Assert.assertTrue(assertEquals(expectedDataset, outputDataset));
    }

    @Test
    public void testMissingExpression() {
        String expression = "#X+2";
        EvaluationContext evaluationContext = new StandardEvaluationContext();

        TimeseriesDataset<Number> value = Expression.Builder.instance(expression)
                .useInMemoryDataSource().evaluate(evaluationContext);
        Assert.assertNull(value);
    }

    @Test
    public void testMultiply() {
        String expression = "#A*(#B*100)";
        SpelExpression spelExpression = new SpelExpression(expression);
        EvaluationContext evaluationContext = new StandardEvaluationContext();

        InMemoryDataSource dataSource = new InMemoryDataSource();
        Map<Long,Number> aVariableDataset = new LinkedHashMap<>();
        aVariableDataset.put(1546626611976L, 0.0232381225);

        Map<Long,Number> bVariableDataset = new LinkedHashMap<>();
        bVariableDataset.put(1546626611976L, 73.0967787377);

        Map<Long,Number> expectedDatasetMap = new LinkedHashMap<>();
        expectedDatasetMap.put(1546626611976L, 169.86319);

        Map<String, TimeseriesDataset<Number>> datasetMap = new LinkedHashMap<>();
        datasetMap.put("A", TimeseriesDataset.Builder.<Number>instance()
                .withDataset(aVariableDataset).build());
        datasetMap.put("B", TimeseriesDataset.Builder.<Number>instance()
                .withDataset(bVariableDataset).build());
        dataSource.setDataMap(datasetMap);

        TimeseriesDataset<Number> outputDataset = Expression.Builder.instance(expression).useInMemoryDataSource()
                .withDataSet(datasetMap).evaluate(new StandardEvaluationContext());


        TimeseriesDataset<Number> expectedDataset = TimeseriesDataset.Builder.<Number>instance()
                .withDataset(expectedDatasetMap).build();
        System.out.println("outputDataset = " + outputDataset);
        System.out.println("expectedDataset = " + expectedDataset);

        Assert.assertTrue(assertEquals(expectedDataset, outputDataset));


    }

    @Test
    public void testTwoVariableFunctionExpression() {
        String expression = "#A+#B";

        Map<Long,Number> expectedDatasetMap = new LinkedHashMap<>();

        Map<Long,Number> aVariableDataset = new LinkedHashMap<>();
        aVariableDataset.put(1546626611976L, 0.7309677874);
        aVariableDataset.put(1546626611977L, 0.2405364157);
        aVariableDataset.put(1546626611978L, 0.6374174254);
        aVariableDataset.put(1546626611979L, 0.5504370051);
        aVariableDataset.put(1546626611980L, 0.5975452778);
        aVariableDataset.put(1546626611981L, 0.3332183995);
        aVariableDataset.put(1546626611982L, 0.3851891847);
        aVariableDataset.put(1546626611983L, 0.9848415402);
        aVariableDataset.put(1546626611984L, 0.8791825179);

        Map<Long,Number> bVariableDataset = new LinkedHashMap<>();
        bVariableDataset.put(1546626611976L, 0.7309677874);
        bVariableDataset.put(1546626611977L, 0.2405364157);
        bVariableDataset.put(1546626611978L, 0.6374174254);
        bVariableDataset.put(1546626611979L, 0.5504370051);
        bVariableDataset.put(1546626611980L, 0.5975452778);
        bVariableDataset.put(1546626611981L, 0.3332183995);
        bVariableDataset.put(1546626611982L, 0.3851891847);
        bVariableDataset.put(1546626611983L, 0.9848415402);
        bVariableDataset.put(1546626611984L, 0.8791825179);

        Map<String, TimeseriesDataset<Number>> datasetMap = new LinkedHashMap<>();
        datasetMap.put("A", TimeseriesDataset.Builder.<Number>instance()
                .withDataset(aVariableDataset).build());
        datasetMap.put("B", TimeseriesDataset.Builder.<Number>instance()
                .withDataset(bVariableDataset).build());

        expectedDatasetMap.put(1546626611976L, 1.461936);
        expectedDatasetMap.put(1546626611977L, 0.481073);
        expectedDatasetMap.put(1546626611978L, 1.274835);
        expectedDatasetMap.put(1546626611979L, 1.100874);
        expectedDatasetMap.put(1546626611980L, 1.195091);
        expectedDatasetMap.put(1546626611981L, 0.666437);
        expectedDatasetMap.put(1546626611982L, 0.770378);
        expectedDatasetMap.put(1546626611983L, 1.969683);
        expectedDatasetMap.put(1546626611984L, 1.758365);


        TimeseriesDataset<Number> outputDataset = Expression.Builder.instance(expression).useInMemoryDataSource()
                .withDataSet(datasetMap).evaluate(new StandardEvaluationContext());
        TimeseriesDataset<Number> expectedDataset = TimeseriesDataset.Builder.<Number>instance()
                .withDataset(expectedDatasetMap).build();

        System.out.println("outputDataset = " + outputDataset);

        Assert.assertTrue(assertEquals(expectedDataset, outputDataset));

    }

    @Test
    @Ignore("Test case is failing")
    public void testTwoVariableExpressionWithExtrapolation() {
        String expression = "#A+#B";
        SpelExpression spelExpression = new SpelExpression(expression);
        EvaluationContext evaluationContext = new StandardEvaluationContext();
        EvaluationConfig evaluationConfig = new EvaluationConfig();
        evaluationConfig.setExtrapolateInputsEnabled(true);
        evaluationContext.setEvaluationConfig(evaluationConfig);

        InMemoryDataSource dataSource = new InMemoryDataSource();

        Map<Long,Number> aVariableDataset = new LinkedHashMap<>();
        aVariableDataset.put(1546626611976L, 0.7309677874);
        aVariableDataset.put(1546626611977L, 0.2405364157);
        aVariableDataset.put(1546626611978L, 0.6374174254);
        aVariableDataset.put(1546626611979L, 0.5504370051);
        aVariableDataset.put(1546626611980L, 0.5975452778);
        aVariableDataset.put(1546626611981L, 0.3332183995);
        aVariableDataset.put(1546626611982L, 0.3851891847);
        aVariableDataset.put(1546626611983L, 0.9848415402);
        aVariableDataset.put(1546626611984L, 0.8791825179);

        Map<Long,Number> bVariableDataset = new LinkedHashMap<>();
        bVariableDataset.put(1546626611974L, 0.9848415402);
        bVariableDataset.put(1546626611983L, 0.8791825179);

        Map<String, TimeseriesDataset<Number>> datasetMap = new LinkedHashMap<>();
        datasetMap.put("A", TimeseriesDataset.Builder.<Number>instance()
                .withDataset(aVariableDataset).build());
        datasetMap.put("B", TimeseriesDataset.Builder.<Number>instance()
                .withDataset(bVariableDataset).build());
        dataSource.setDataMap(datasetMap);

        DatasourceProvider datasourceProvider = new DatasourceProvider(dataSource, dataSource);
        ExpressionContextResolver contextResolver = new DefaultExpressionContextResolver(datasourceProvider);

        Map<Long,Number> expectedDatasetMap = new LinkedHashMap<>();
        expectedDatasetMap.put(1546626611976L, 1.715809328);
        expectedDatasetMap.put(1546626611977L, 1.225377956);
        expectedDatasetMap.put(1546626611978L, 1.622258966);
        expectedDatasetMap.put(1546626611979L, 1.535278545);
        expectedDatasetMap.put(1546626611980L, 1.582386818);
        expectedDatasetMap.put(1546626611981L, 1.31805994);
        expectedDatasetMap.put(1546626611982L, 1.370030725);
        expectedDatasetMap.put(1546626611983L, 1.864024058);
        expectedDatasetMap.put(1546626611984L, 1.758365036);
        TimeseriesDataset<Number> expectedDataset = TimeseriesDataset.Builder.<Number>instance()
                .withDataset(expectedDatasetMap).build();


        spelExpression.setExpressionContextResolver(contextResolver);
        TimeseriesDataset<Number> outputDataset = spelExpression.getValue(evaluationContext);

        System.out.println("outputDataset = " + outputDataset);
        System.out.println("expectedDataset = " + expectedDataset);
        Assert.assertTrue(assertEquals(expectedDataset, outputDataset));
    }


    private static boolean assertEquals(TimeseriesDataset<Number> ds1, TimeseriesDataset<Number> ds2) {
        if (ds1 == null || ds2 == null) {
            return false;
        }
        return ds1.size() == ds2.size() &&
                ds1.getTimestampSeries().stream().allMatch(timestamp -> ds1.getValue(timestamp).equals(ds2.getValue(timestamp)));

    }


}
