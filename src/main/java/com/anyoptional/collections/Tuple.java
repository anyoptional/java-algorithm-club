package com.anyoptional.collections;

import com.anyoptional.lang.Nullable;
import com.anyoptional.util.ObjectUtils;

import java.util.Objects;

public class Tuple<A, B> {

    @Nullable
    public final A first;

    @Nullable
    public final B second;

    public Tuple(@Nullable A first, @Nullable B second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(first, tuple.first) &&
                Objects.equals(second, tuple.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "("
                + ObjectUtils.nullSafeToString(first)
                + ", "
                + ObjectUtils.nullSafeToString(second)
                + ")";
    }

}
