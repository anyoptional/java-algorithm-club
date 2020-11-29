package com.anyoptional.collections;

import com.anyoptional.lang.Nullable;

import java.util.Objects;

/**
 * @apiNote PriorityQueue do not permit null element.
 */
public class PriorityQueue<E> {

    private final Heap<E> _storage = new Heap<>();

    public boolean isEmpty() {
        return _storage.isEmpty();
    }

    public int size() {
        return _storage.size();
    }

    @Nullable
    public E peek() {
        return _storage.peek();
    }

    @Nullable
    public E dequeue() {
        return _storage.remove();
    }

    public void enqueue(E element) {
        _storage.insert(element);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriorityQueue<?> that = (PriorityQueue<?>) o;
        return _storage.equals(that._storage);
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
