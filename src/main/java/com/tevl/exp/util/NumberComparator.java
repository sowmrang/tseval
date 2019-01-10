package com.tevl.exp.util;

import java.util.Comparator;

public class NumberComparator implements Comparator<Number> {

    @Override
    public int compare(Number left, Number right) {

        if (left == null) {
            return (right == null ? 0 : -1);
        }
        else if (right == null) {
            return 1;
        }

        if (left instanceof Double || right instanceof Double) {
            return Double.compare(left.doubleValue(), right.doubleValue());
        }
        else if (left instanceof Float || right instanceof Float) {
            return Float.compare(left.floatValue(), right.floatValue());
        }
        else if (left instanceof Long || right instanceof Long) {
            return Long.compare(left.longValue(), right.longValue());
        }

        return 0;
    }
}
