package com.anyoptional.collections;

import com.anyoptional.lang.Nullable;
import com.anyoptional.lang.VisibleForTesting;
import com.anyoptional.util.Assert;
import com.anyoptional.util.Comparators;

import java.util.*;

/**
 * 堆在逻辑上等同于一颗完全二叉树，物理上使用数组来表示。
 * 得益于完全二叉树的紧奏性（唯一有可能缺失的是右侧的叶子节点），
 * 依据层次遍历的次序，对于数组中的每一个元素，它的
 *  parent = (index - 1) / 2
 *  left = 2 * index + 1
 *  right = 2 * index + 2
 *
 * 堆序性：任意一个节点，在优先级上都不会超过它的父节点
 *
 * @apiNote Heap do not permit null element.
 */
public class Heap<E> {

    @VisibleForTesting
    final List<E> _storage = new ArrayList<>();

    @Nullable
    private final Comparator<? super E> _comparator;

    public Heap() {
        _comparator = null;
    }

    public Heap(Comparator<? super E> comparator) {
        Assert.notNull(comparator, "comparator must not be null");
        _comparator = comparator;
    }

    public boolean isEmpty() {
        return _storage.isEmpty();
    }

    public int size() {
        return _storage.size();
    }

    /**
     * 读取顶端元素
     */
    @Nullable
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return _storage.get(0);
    }

    /**
     * 删除顶端元素
     */
    @Nullable
    public E remove() {
        if (isEmpty()) {
            return null;
        }
        if (size() == 1) {
            return _storage.remove(0);
        }
        E result = _storage.get(0);
        // 选取最后一个元素来充当根节点，如此不破坏整体结构性
        _storage.set(0, _storage.remove(size() - 1));
        // 看看是否需要下移
        shiftDown(0, size());
        return result;
    }

    /**
     * 删除指定位置的元素
     */
    @Nullable
    public E remove(int index) {
        if (index < 0 || index >= size()) return null; // out of range
        int size = size() - 1;
        if (index != size) {
            E element = _storage.set(index, _storage.remove(size));
            shiftUp(index);
            shiftDown(index, size());
            return element;
        }
        return _storage.remove(size);
    }

    /**
     * 替换指定位置的元素
     */
    @Nullable
    public E replace(int index, E element) {
        if (index < 0 || index >= size()) return null; // out of range
        Assert.notNull(element, "element is required");
        E result = remove(index);
        insert(element);
        return result;
    }

    /**
     * 插入一个元素
     */
    public void insert(E element) {
        Assert.notNull(element, "element is required");
        // 首先添加到列表末尾，如此不破坏整体结构性
        // 这等效于在完全二叉树的最底层空缺的部分扩充一个节点
        _storage.add(element);
        // 再看看节点是否需要上移
        shiftUp(_storage.size() - 1);
    }

    public void addAll(Collection<? extends E> c) {
        Assert.notNull(c, "collection is required");
        // 蛮力算法 O(nlogn)
//        for (E e : c) {
//            insert(e);
//        }
        // 弗洛伊德建堆算法 O(n)
        _storage.addAll(c);
        // 从最后一个内部节点开始，局部下滤调整
        // 每一次下滤调整都会获得一个局部有效的
        // 堆，直至下滤到根节点，整堆也必然有序
        for (int i = parentIndexOf(size() - 1); i >= 0; i--) {
            shiftDown(i, _storage.size());
        }
    }

    /**
     * 堆合并（左式堆可实现更高效的合并策略）
     */
    public void merge(Heap<E> another) {
        Assert.notNull(another, "another heap is required");
        addAll(another._storage);
    }

    /**
     * 上滤
     */
    private void shiftUp(int childIndex) {
        // 获取节点
        E child = _storage.get(childIndex);
        // 获取父节点索引
        int parentIndex = parentIndexOf(childIndex);
        // 如果逆序迭代到根节点为止
        while (childIndex > 0 &&
                Comparators.compare(child, _storage.get(parentIndex), _comparator) > 0) {
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
     * 下滤
     */
    private void shiftDown(int parentIndex, int endIndex) {
        // 找到左右节点的位置
        int leftChildIndex = leftChildIndexOf(parentIndex);
        int rightChildIndex = rightChildIndexOf(parentIndex);
        // 在parent、left child和right child三者中取优先级最大的做新的parent
        int greatestIndex = parentIndex;
        if (leftChildIndex < endIndex &&
                Comparators.compare(_storage.get(leftChildIndex), _storage.get(greatestIndex), _comparator) > 0) {
            greatestIndex = leftChildIndex;
        }
        if (rightChildIndex < endIndex &&
                Comparators.compare(_storage.get(rightChildIndex), _storage.get(greatestIndex), _comparator) > 0) {
            greatestIndex = rightChildIndex;
        }
        // 如果parent就是最大的，说明已经满足条件
        if (greatestIndex == parentIndex) return;
        // 否则将parent与新顶点交换位置
        E oldParent = _storage.set(parentIndex, _storage.get(greatestIndex));
        _storage.set(greatestIndex, oldParent);
        // 继续往下看看是否需要交换
        shiftDown(greatestIndex, endIndex);
    }

    /**
     * 计算索引为index的节点的父节点索引，-1表示根节点
     */
    @VisibleForTesting
    int parentIndexOf(int index) {
        return (index - 1) / 2;
    }

    /**
     * 计算索引为index的节点的左孩子索引
     */
    @VisibleForTesting
    int leftChildIndexOf(int index) {
        return 2 * index + 1;
    }

    /**
     * 计算索引为index的节点的右孩子索引
     */
    @VisibleForTesting
    int rightChildIndexOf(int index) {
        return leftChildIndexOf(index) + 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Heap<?> heap = (Heap<?>) o;
        return _storage.equals(heap._storage) &&
                Objects.equals(_comparator, heap._comparator);
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
