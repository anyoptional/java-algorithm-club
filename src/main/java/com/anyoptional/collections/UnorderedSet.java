package com.anyoptional.collections;

import java.util.AbstractSet;
import java.util.Iterator;

public class UnorderedSet<E> extends AbstractSet<E> {

    private static final Object NIL = new Object();

    private final Dictionary<E, Object> _storage;

    public UnorderedSet() {
        this(Dictionary.DEFAULT_CAPACITY);
    }

    public UnorderedSet(int capacity) {
        _storage = new Dictionary<>(capacity, Dictionary.DEFAULT_LOAD_FACTOR);
    }

    @Override
    public Iterator<E> iterator() {
        return _storage.keySet().iterator();
    }

    @Override
    public int size() {
        return _storage.size();
    }

    @Override
    public boolean isEmpty() {
        return _storage.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return _storage.containsKey(o);
    }

    @Override
    public boolean add(E e) {
        return _storage.put(e, NIL) == null;
    }

    @Override
    public boolean remove(Object o) {
        return _storage.remove(o) == NIL;
    }

    @Override
    public void clear() {
        _storage.clear();
    }

}
