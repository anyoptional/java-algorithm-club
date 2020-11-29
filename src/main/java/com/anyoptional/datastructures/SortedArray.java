package com.anyoptional.datastructures;

import com.anyoptional.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An ordered array. When you add a new item to this array,
 * it is inserted in sorted position.
 */
public class SortedArray<E extends Comparable<E>> {

    private final List<E> storage;

    public SortedArray() {
        this.storage = new ArrayList<>();
    }

    public SortedArray(List<E> list) {
        Assert.notNull(list, "List must not be null");
        list.sort(null); // sort by Comparable
        this.storage = new ArrayList<>(list); // ensure add(int, E) is supported
    }

    public boolean isEmpty() {
        return storage.isEmpty();
    }

    public int size() {
        return storage.size();
    }

    public E get(int index) {
        return storage.get(index);
    }

    public E remove(int index) {
        return storage.remove(index);
    }

    public void clear() {
        storage.clear();
    }

    public int add(E element) {
        int index = findInsertionPoint(element);
        storage.add(index, element);
        return index;
    }

    private int findInsertionPoint(E element) {
        int start = 0;
        int end = storage.size();
        while (start < end) {
            int mid = (start + end) / 2;
            int order = storage.get(mid).compareTo(element);
            if (order == 0) {
                return mid;
            } else if (order > 0) {
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
        return Objects.equals(storage, that.storage);
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
