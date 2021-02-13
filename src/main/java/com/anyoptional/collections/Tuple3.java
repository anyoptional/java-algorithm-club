package com.anyoptional.collections;

import com.anyoptional.lang.Nullable;
import com.anyoptional.util.ObjectUtils;

import java.util.Objects;

public class Tuple3<A, B, C> {

    private static final Tuple3<?, ?, ?> EMPTY = new Tuple3<>(null, null, null);

    @Nullable
    public final A first;

    @Nullable
    public final B second;

    @Nullable
    public final C third;

    public Tuple3(@Nullable A first, @Nullable B second, @Nullable C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @SuppressWarnings("unchecked")
    public static <A, B, C> Tuple3<A, B, C> empty() {
        return (Tuple3<A, B, C>) EMPTY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple3<?, ?, ?> tuple3 = (Tuple3<?, ?, ?>) o;
        return Objects.equals(first, tuple3.first) &&
                Objects.equals(second, tuple3.second) &&
                Objects.equals(third, tuple3.third);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third);
    }

    @Override
    public String toString() {
        return "("
                + ObjectUtils.nullSafeToString(first)
                + ", "
                + ObjectUtils.nullSafeToString(second)
                + ", "
                + ObjectUtils.nullSafeToString(third)
                + ")";
    }

}
