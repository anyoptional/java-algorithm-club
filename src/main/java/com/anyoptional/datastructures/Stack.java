package com.anyoptional.datastructures;

import com.anyoptional.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

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

}
