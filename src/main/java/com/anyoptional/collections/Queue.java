package com.anyoptional.collections;

import com.anyoptional.lang.Nullable;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @apiNote Queue permit null element.
 */
public class Queue<E> implements Iterable<E> {

    private final List<E> _storage = new LinkedList<>();

    public boolean isEmpty() {
        return _storage.isEmpty();
    }

    public int size() {
        return _storage.size();
    }

    /**
     * 入队
     */
    public void enqueue(@Nullable E element) {
        _storage.add(element);
    }

    /**
     * 出队
     */
    @Nullable
    public E dequeue() {
        if (isEmpty()) {
            return null;
        }
        return _storage.remove(0);
    }

    /**
     * 查看队首元素，不出队
     */
    @Nullable
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return _storage.get(0);
    }

    @Override
    public Iterator<E> iterator() {
        return new DelegatingIterator<>(_storage.iterator());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Queue<?> queue = (Queue<?>) o;
        return Objects.equals(_storage, queue._storage);
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
