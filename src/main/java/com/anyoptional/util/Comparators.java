package com.anyoptional.util;

import com.anyoptional.lang.Nullable;

import java.util.Comparator;

public abstract class Comparators {

    @SuppressWarnings("unchecked")
    public static <E> int compare(E lhs, E rhs, @Nullable Comparator<? super E> comparator) {
        Assert.notNull(lhs, "left hand side object is required");
        Assert.notNull(rhs, "right hand side object is required");
        if (comparator != null) {
            return comparator.compare(lhs, rhs);
        }
        return ((Comparable<? super E>) lhs).compareTo(rhs);
    }

}
