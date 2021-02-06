package com.anyoptional.datastructures;

import com.anyoptional.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Stack<E> {

    private final List<E> _storage = new ArrayList<>();

    public boolean isEmpty() {
        return _storage.isEmpty();
    }

    public int size() {
        return _storage.size();
    }

    public void push(E element) {
        _storage.add(element);
    }

    @Nullable
    public E pop() {
        if (isEmpty()) {
            return null;
        }
        return _storage.remove(size() - 1);
    }

    @Nullable
    public E top() {
        if (isEmpty()) {
            return null;
        }
        return _storage.get(size() - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stack<?> stack = (Stack<?>) o;
        return Objects.equals(_storage, stack._storage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_storage);
    }

    @Override
    public String toString() {
        return _storage.toString();
    }

}
