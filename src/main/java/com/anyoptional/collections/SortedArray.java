package com.anyoptional.collections;

import com.anyoptional.lang.Nullable;
import com.anyoptional.util.Assert;
import com.anyoptional.util.CollectionUtils;
import com.anyoptional.util.Comparators;

import java.util.*;

/**
 * An ordered array. When you add a new item to this array,
 * it is inserted in sorted position.
 *
 * @apiNote SortedArray do not permit null element.
 */
public class SortedArray<E> implements Collection<E> {

    private final List<E> _storage;

    @Nullable
    private final Comparator<? super E> _comparator;

    public SortedArray() {
        _comparator = null;
        _storage = new ArrayList<>();
    }

    public SortedArray(Comparator<? super E> comparator) {
        Assert.notNull(comparator, "comparator must not be null");
        _comparator = comparator;
        _storage = new ArrayList<>();
    }

    public E get(int index) {
        return _storage.get(index);
    }

    public E delete(int index) {
        return _storage.remove(index);
    }

    public int insert(E element) {
        Assert.notNull(element, "element is required");
        int index = findInsertionPoint(element);
        _storage.add(index, element);
        return index;
    }

    @Override
    public boolean isEmpty() {
        return _storage.isEmpty();
    }

    @Override
    public int size() {
        return _storage.size();
    }

    @Override
    public void clear() {
        _storage.clear();
    }

    @Override
    public boolean add(E element) {
        insert(element);
        return true;
    }

    @Override
    public boolean contains(Object o) {
        return _storage.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return new DelegatingIterator<>(_storage.iterator());
    }

    @Override
    public Object[] toArray() {
        return _storage.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return _storage.toArray(a);
    }

    @Override
    public boolean remove(Object o) {
        return _storage.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return _storage.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (CollectionUtils.isEmpty(c)) return false;
        for (E e : c) {
            add(e);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return _storage.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return _storage.retainAll(c);
    }

    private int findInsertionPoint(E element) {
        int start = 0;
        int end = _storage.size();
        while (start < end) {
            int mid = (start + end) / 2;
            int order = Comparators.compare(_storage.get(mid), element, _comparator);
            if (order >= 0) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SortedArray<?> that = (SortedArray<?>) o;
        return _storage.equals(that._storage) &&
                Objects.equals(_comparator, that._comparator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_storage, _comparator);
    }

    @Override
    public String toString() {
        return _storage.toString();
    }

}
