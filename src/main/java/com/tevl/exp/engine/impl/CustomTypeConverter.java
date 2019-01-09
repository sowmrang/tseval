package com.tevl.exp.engine.impl;

import com.tevl.ds.TimeseriesDataset;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.spel.support.StandardTypeConverter;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

public class CustomTypeConverter extends StandardTypeConverter
{
    @Override
    public boolean canConvert(@Nullable TypeDescriptor sourceType, TypeDescriptor targetType) {
        return super.canConvert(sourceType, targetType);
    }

    @Nullable
    @Override
    public Object convertValue(@Nullable Object value, @Nullable TypeDescriptor sourceType, TypeDescriptor targetType) {
        if(TimeseriesDataset.class.isAssignableFrom(targetType.getType())) {
            if (sourceType!= null && List.class.isAssignableFrom(sourceType.getType()))
            {
                List sourceValue = (List) value;
                if(sourceValue == null || sourceValue.isEmpty())
                {
                    return null;
                }
                Object o = sourceValue.get(0);
                TimeseriesDataset<Number> dataset = TimeseriesDataset.Builder.<Number>instance().build();

                if(Map.Entry.class.isAssignableFrom(o.getClass()))
                {
                    sourceValue.forEach(entry -> {
                        dataset.addValue((Long)((Map.Entry)entry).getKey()
                                ,(Number)((Map.Entry)entry).getValue());
                    });
                    return dataset;
                }

            }
        }

        return super.convertValue(value, sourceType, targetType);
    }
}
