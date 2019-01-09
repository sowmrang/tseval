package com.tevl.ds;

import java.util.Iterator;
import java.util.Map;

public class TSDatasetIterator<T> implements Iterator<Map.Entry<Long,T>> {

    private Iterator<Map.Entry<Long,T>> mapIterator;

    public TSDatasetIterator(Iterator<Map.Entry<Long,T>> tsMapIterator)
    {
        mapIterator = tsMapIterator;
    }

    @Override
    public boolean hasNext() {
        return mapIterator.hasNext();
    }

    @Override
    public Map.Entry<Long,T> next() {
        return mapIterator.next();
    }

}
