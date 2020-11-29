package com.anyoptional.datastructures;

import com.anyoptional.util.Assert;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Heap<E> {

    private List<E> _storage;

    private final Comparator<E> _comparator;

    public Heap(Comparator<E> comparator) {
        Assert.notNull(comparator, "Comparator must not be null");
        _comparator = comparator;
        _storage = new ArrayList<>();
    }

    public boolean isEmpty() {
        return _storage.isEmpty();
    }

    public int size() {
        return _storage.size();
    }

    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return _storage.get(0);
    }

    public void insert(E element) {
        // 首先添加到列表末尾
        _storage.add(element);
        // 再看看节点是否需要上移
        sifting(_storage.size() - 1);
    }

    /**
     * shift up operation
     */
    private void sifting(int childIndex) {
        // 获取节点
        E child = _storage.get(childIndex);
        // 获取父节点索引
        int parentIndex = parentIndexOf(childIndex);
        // 如果逆序迭代到根节点为止
        while (childIndex > 0 &&
                _comparator.compare(child, _storage.get(parentIndex)) >= 0) {
            // 父节点下移
            _storage.set(childIndex, _storage.get(parentIndex));
            // 继续向上迭代
            childIndex = parentIndex;
            parentIndex = parentIndexOf(childIndex);
        }
        // 此时childIndex就是child该处的位置了
        _storage.set(childIndex, child);
    }

    /**
     * 计算索引为index的节点的父节点索引，-1表示根节点
     */
    private int parentIndexOf(int index) {
        return (index - 1) / 2;
    }

    /**
     * 计算索引为index的节点的左孩子索引
     */
    private int leftChildIndexOf(int index) {
        return 2 * index + 1;
    }

    /**
     * 计算索引为index的节点的右孩子索引
     */
    private int rightChildIndexOf(int index) {
        return leftChildIndexOf(index) + 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Heap<?> heap = (Heap<?>) o;
        return Objects.equals(_storage, heap._storage);
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
