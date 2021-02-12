package com.anyoptional.collections;

import com.anyoptional.lang.Nullable;
import com.anyoptional.lang.VisibleForTesting;
import com.anyoptional.util.Assert;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 所谓m阶BTree，是说它除根节点外，其它节点
 * 1. 至少有[ceil(m / 2 - 1)]个Entry
 * 2. 至多有[m - 1]个Entry
 * 对于根节点，无论是多少阶的BTree，它都可以只有
 * 一个节点。
 */
public class BTree<K, V> {

    private static final int MIN_ORDER = 3;

    /**
     * BTree节点，满足entries.size() + 1 = children.size()
     */
    static class Node<K, V> {

        @Nullable
        Node<K, V> parent;

        final List<Entry<K, V>> entries = new ArrayList<>();

        final List<Node<K, V>> children = new ArrayList<>();

        /**
         * 创建一个不含任何词条的节点
         */
        Node() {
        }

        /**
         * 创建根节点（初始情况）
         */
        Node(K key, @Nullable V value) {
            entries.add(new Entry<>(key, value));
            children.add(null);
            children.add(null);
        }

        /**
         * 创建根节点（上溢情况）
         */
        Node(Entry<K, V> entry, Node<K, V> lhs, Node<K, V> rhs) {
            entries.add(entry);
            lhs.parent = this;
            children.add(lhs);
            rhs.parent = this;
            children.add(rhs);
        }

        public void traverseInOrder(Consumer<Entry<K, V>> consumer) {
            Assert.notNull(consumer, "consumer is required");
            for (int i = 0; i < entries.size(); i++) {
                Node<K, V> child = children.get(i);
                if (child != null) {
                    child.traverseInOrder(consumer);
                }
                consumer.accept(entries.get(i));
            }
            if (!children.isEmpty()) {
                Node<K, V> child = children.get(children.size() - 1);
                if (child != null) {
                    child.traverseInOrder(consumer);
                }
            }
        }

    }

    private int _size;

    @Nullable
    @VisibleForTesting
    Node<K, V> _root;

    /**
     * BTree的阶次，至少为3（2-4树）
     *
     * @apiNote 2-4树和红黑树有很深的渊源，下回分解
     */
    private final int _order;

    @Nullable
    private final Comparator<? super K> _comparator;

    public BTree() {
        this(MIN_ORDER);
    }

    public BTree(int order) {
        Assert.isTrue(order >= MIN_ORDER, "order is too small");
        _order = order;
        _comparator = null;
    }

    public BTree(Comparator<? super K> comparator) {
        this(MIN_ORDER, comparator);
    }

    public BTree(int order, Comparator<? super K> comparator) {
        Assert.isTrue(order >= MIN_ORDER, "order is too small");
        Assert.notNull(comparator, "comparator is required");
        _order = order;
        _comparator = comparator;
    }

    public int size() {
        return _size;
    }

    public boolean isEmpty() {
        return _root == null;
    }

    @Nullable
    public Entry<K, V> search(K key) {
        Tuple<Node<K, V>, Integer> tuple = searchNode(key);
        if (tuple.first == null || tuple.second == null) return null;
        return tuple.first.entries.get(tuple.second);
    }

    public void insert(K key, @Nullable V value) {
        Assert.notNull(key, "key is required");
        Node<K, V> cur = _root;
        // 待插入元素的节点
        Node<K, V> hot = null;
        // 沿根节点往下查找
        // 找到一个合适的叶节点执行插入
        while (cur != null) {
            hot = cur;
            int index = binarySearch(cur.entries, key);
            int insertionPoint = insertionPointOf(index);
            cur = cur.children.get(insertionPoint);
        }
        // 空树
        if (hot == null) {
            _root = new Node<>(key, value);
        } else {
            int index = binarySearch(hot.entries, key);
            int insertionPoint = insertionPointOf(index);
            hot.entries.add(insertionPoint, new Entry<>(key, value));
            hot.children.add(insertionPoint + 1, null);
            // 新增一个词条后，可能导致该节点溢出
            solveOverflow(hot);
        }
        _size += 1;
    }

    @Nullable
    public Entry<K, V> remove(K key) {

        return null;
    }

    @SuppressWarnings("all")
    public void traverseInOrder(Consumer<Entry<K, V>> consumer) {
        if (isEmpty()) return;
        _root.traverseInOrder(consumer);
    }

    @VisibleForTesting
    Tuple<Node<K, V>, Integer> searchNode(K key) {
        Assert.notNull(key, "key is required");
        Node<K, V> cur = _root;
        while (cur != null) {
            int index = binarySearch(cur.entries, key);
            // hit
            if (index >= 0) {
                return new Tuple<>(cur, index);
            }
            // non-hit
            int insertionPoint = insertionPointOf(index);
            cur = cur.children.get(insertionPoint);
        }
        return Tuple.empty();
    }

    private int binarySearch(List<Entry<K, V>> list, K key) {
        // Collections#binarySearch(...) 当key不存在时返回 -(insertionPoint) - 1
        return Collections.binarySearch(
                list.stream().map(Entry::getKey).collect(Collectors.toList()),
                key,
                _comparator
        );
    }

    private int insertionPointOf(int index) {
        return index >= 0 ? index + 1 : -(index + 1);
    }

    private void solveOverflow(Node<K, V> node) {
        // 若果真溢出
        while (node.entries.size() > _order - 1) {
            // 若果真溢出, 取中位数
            int mid = node.entries.size() / 2;
            // 去掉中位词条将整个节点一分为二
            // 此时这两个分裂出来的节点均不会打破BTree的语义约定
            Node<K, V> lhs = node;
            Node<K, V> rhs = new Node<>();
            ListIterator<Entry<K, V>> entryIter = lhs.entries.listIterator(mid + 1);
            while (entryIter.hasNext()) {
                rhs.entries.add(entryIter.next());
                entryIter.remove();
            }
            ListIterator<Node<K, V>> childIter = lhs.children.listIterator(mid + 1);
            while (childIter.hasNext()) {
                Node<K, V> child = childIter.next();
                rhs.children.add(child);
                if (child != null) {
                    child.parent = rhs;
                }
                childIter.remove();
            }
            // 取中位词条
            Entry<K, V> midEntry = lhs.entries.remove(mid);
            // 若此前lhs已是根节点
            if (lhs.parent == null) {
                _root = new Node<>(midEntry, lhs, rhs);
                return;
            }
            // 否则，中位词条提升到父节点
            int index = binarySearch(lhs.parent.entries, midEntry.getKey());
            int insertionPoint = insertionPointOf(index);
            lhs.parent.entries.add(insertionPoint, midEntry);
            lhs.parent.children.add(insertionPoint + 1, rhs);
            rhs.parent = lhs.parent;
            // 父节点新增一词条，依旧可能上溢
            node = lhs.parent;
        }
    }

    private void solveUnderflow(Node<K, V> node) {

    }

}
