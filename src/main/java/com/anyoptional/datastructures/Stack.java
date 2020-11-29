package com.anyoptional.datastructures;

import com.anyoptional.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Stack<E> {

    private final List<E> storage = new ArrayList<>();

    public boolean isEmpty() {
        return storage.isEmpty();
    }

    public int size() {
        return storage.size();
    }

    public void push(E element) {
        storage.add(element);
    }

    @Nullable
    public E pop() {
        if (isEmpty()) {
            return null;
        }
        return storage.remove(size() - 1);
    }

    @Nullable
    public E top() {
        if (isEmpty()) {
            return null;
        }
        return storage.get(size() - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stack<?> stack = (Stack<?>) o;
        return Objects.equals(storage, stack.storage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storage);
    }

    @Override
    public String toString() {
        return storage.toString();
    }

}
