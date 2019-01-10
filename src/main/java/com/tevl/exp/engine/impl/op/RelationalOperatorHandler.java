package com.tevl.exp.engine.impl.op;

import com.tevl.ds.TimeseriesDataset;
import com.tevl.exp.util.NumberComparator;
import nl.bigo.curta.CurtaNode;
import nl.bigo.curta.Operator;
import nl.bigo.curta.expression.Expression;
import nl.bigo.curta.function.Function;

import java.util.Map;
import java.util.function.Predicate;

public class RelationalOperatorHandler extends Expression
{
    private Operator operator;

    public RelationalOperatorHandler(Operator operator)
    {
        this.operator = operator;
    }

    @Override
    public Object eval(CurtaNode curtaNode, Map<String, Object> variables,
                       Map<String, Function> functions, Map<Integer, Expression> expressions) {
        Object firstParam = super.evalChild(0,curtaNode, variables, functions, expressions);
        Object secondParam = super.evalChild(1,curtaNode, variables, functions, expressions);
        TimeseriesDataset<Number> outputDataset = TimeseriesDataset.Builder.<Number>instance().build();
        NumberComparator numberComparator = new NumberComparator();
        if(TimeseriesDataset.class.isAssignableFrom(
                firstParam.getClass()))
        {
            TimeseriesDataset<Number> firstDataset = (TimeseriesDataset<Number>) firstParam;
            if(TimeseriesDataset.class.isAssignableFrom(secondParam.getClass()))
            {
                TimeseriesDataset<Number> secondDataset = (TimeseriesDataset<Number>) secondParam;
                Predicate<Long>  condition;
                condition = getPredicate(numberComparator, firstDataset, secondDataset);
                populateOutputDataset(firstDataset,
                        condition,
                        (Long ts) -> outputDataset.addValue(ts,firstDataset.getValue(ts)));
            }
            else if(Number.class.isAssignableFrom(secondParam.getClass()))
            {
                populateOutputDataset(firstDataset,
                                getPredicate(numberComparator, firstDataset, (Number) secondParam),
                        (Long ts) -> outputDataset.addValue(ts, firstDataset.getValue(ts)));
            }
            else
            {
                throw new RuntimeException("Unsupported type in the second argument for GT"+secondParam.getClass());
            }
        }
        else if(Number.class.isAssignableFrom(
                firstParam.getClass()))
        {
            if(Number.class.isAssignableFrom(secondParam.getClass()))
            {
                return numberComparator.compare((Number) firstParam,(Number) secondParam);
            }
        }
        else
        {
            throw new RuntimeException("Unsupported type in the second argument for GT"+secondParam.getClass());
        }
        return outputDataset;
    }

    private Predicate<Long> getPredicate(NumberComparator numberComparator, TimeseriesDataset<Number> firstDataset, TimeseriesDataset<Number> secondDataset) {
        Predicate<Long> condition;
        switch (operator)
        {
            case GreaterThan:
                condition = (ts) -> numberComparator.compare(
                        firstDataset.getValue(ts),secondDataset.getValue(ts)) > 0;
                break;
            case LessThan:
                condition = (ts) -> numberComparator.compare(
                        firstDataset.getValue(ts),secondDataset.getValue(ts)) < 0;
                break;
            case Equals:
                condition = (ts) -> numberComparator.compare(
                        firstDataset.getValue(ts),secondDataset.getValue(ts)) == 0;
                break;
            case GreaterThanEqual:
                condition = (ts) -> numberComparator.compare(
                        firstDataset.getValue(ts),secondDataset.getValue(ts)) >= 0;
                break;
            case LessThanEqual:
                condition = (ts) -> numberComparator.compare(
                        firstDataset.getValue(ts),secondDataset.getValue(ts)) <= 0;
                break;
            default:
                throw new UnsupportedOperationException("Not supported");
        }
        return condition;
    }

    private Predicate<Long> getPredicate(NumberComparator numberComparator,
                                         TimeseriesDataset<Number> firstDataset, Number secondDataset) {
        Predicate<Long> condition;
        switch (operator)
        {
            case GreaterThan:
                condition = (ts) -> numberComparator.compare(
                        firstDataset.getValue(ts),secondDataset) > 0;
                break;
            case LessThan:
                condition = (ts) -> numberComparator.compare(
                        firstDataset.getValue(ts),secondDataset) < 0;
                break;
            case Equals:
                condition = (ts) -> numberComparator.compare(
                        firstDataset.getValue(ts),secondDataset) == 0;
                break;
            case GreaterThanEqual:
                condition = (ts) -> numberComparator.compare(
                        firstDataset.getValue(ts),secondDataset) >= 0;
                break;
            case LessThanEqual:
                condition = (ts) -> numberComparator.compare(
                        firstDataset.getValue(ts),secondDataset) <= 0;
                break;
            default:
                throw new UnsupportedOperationException("Not supported");
        }
        return condition;
    }

    private void populateOutputDataset(TimeseriesDataset<Number> dataset,
                                       Predicate<Long> predicate,
                                       java.util.function.Function<Long,?> populationFunction)
    {
        dataset.getTimestampSeries().stream().filter(
                predicate)
                .map(populationFunction).count();

    }
}
