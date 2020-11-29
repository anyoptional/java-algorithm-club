package com.anyoptional.datastructures;

import com.anyoptional.lang.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Queue<E> {

    private final List<E> storage = new LinkedList<>();

    public boolean isEmpty() {
        return storage.isEmpty();
    }

    public int size() {
        return storage.size();
    }

    public void enqueue(E element) {
        storage.add(element);
    }

    @Nullable
    public E dequeue() {
        if (isEmpty()) {
            return null;
        }
        return storage.remove(0);
    }

    @Nullable
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return storage.get(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Queue<?> stack = (Queue<?>) o;
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
