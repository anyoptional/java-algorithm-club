package com.anyoptional.collections;

import com.anyoptional.util.Assert;

import java.util.Iterator;

public class DelegatingIterator<E> implements Iterator<E> {

    private final Iterator<E> _adaptee;

    public DelegatingIterator(Iterator<E> adaptee) {
        Assert.notNull(adaptee, "adaptee is required");
        _adaptee = adaptee;
    }

    @Override
    public boolean hasNext() {
        return _adaptee.hasNext();
    }

    @Override
    public E next() {
        return _adaptee.next();
    }

}
