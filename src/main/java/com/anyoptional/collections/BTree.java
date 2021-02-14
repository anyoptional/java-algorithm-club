package com.anyoptional.collections;

import com.anyoptional.lang.Nullable;
import com.anyoptional.lang.VisibleForTesting;
import com.anyoptional.util.Assert;
import com.anyoptional.util.Comparators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

/**
 * 所谓m阶BTree，是说它除根节点外，其它节点
 * 1. 至少有[ceil(m / 2 - 1)]个Entry
 * 2. 至多有[m - 1]个Entry
 * 对于根节点，无论是多少阶的BTree，它都可以只有
 * 一个节点。
 * @apiNote BTree do not permit null key.
 */
public class BTree<K, V> {

    private static final int MIN_ORDER = 3;

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
            // 新增一个词条后，可能导致hot溢出
            solveOverflow(hot);
        }
        _size += 1;
    }

    @Nullable
    public Entry<K, V> remove(K key) {
        // 找出待删除节点
        Tuple<Node<K, V>, Integer> tuple = searchNode(key);
        Node<K, V> node = tuple.first;
        Integer index = tuple.second;
        // 不存在即结束
        if (node == null || index == null) return null;
        // 如果待删除节点不是叶子节点
        if (!node.isLeaf()) {
            // 找到该节点的后继节点
            Node<K, V> successor = node.children.get(index + 1);
            while (!successor.isLeaf()) {
                successor = successor.children.get(0);
            }
            // 交换right和node的Entry
            Entry<K, V> temp = node.entries.get(index);
            node.entries.set(index, successor.entries.get(0));
            successor.entries.set(0, temp);
            // 进而问题退化为删除叶节点中的某个Entry
            index = 0;
            node = successor;
        }
        // 删除词条和多余的空孩子
        Entry<K, V> result = node.entries.remove(index.intValue());
        node.children.remove(index + 1);
        // 节点删除后，可能发生下溢
        solveUnderflow(node);
        // 更新规模
        _size -= 1;
        return result;
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
            if (index >= 0 && Comparators.compare(cur.entries.get(index).getKey(), key, _comparator) == 0) {
                return new Tuple<>(cur, index);
            }
            // non-hit
            int insertionPoint = insertionPointOf(index);
            cur = cur.children.get(insertionPoint);
        }
        return Tuple.empty();
    }

    private int binarySearch(List<Entry<K, V>> list, K key) {
        int low = 0;
        int high = list.size();
        while (low < high) {
            int mid = (high + low) / 2;
            if (Comparators.compare(list.get(mid).getKey(), key, _comparator) > 0) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return --low;
    }

    private int insertionPointOf(int index) {
        return index + 1;
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
            int index = lhs.parent.children.indexOf(lhs);
            lhs.parent.entries.add(index, midEntry);
            lhs.parent.children.add(index + 1, rhs);
            rhs.parent = lhs.parent;
            // 父节点新增一词条，依旧可能上溢
            node = lhs.parent;
        }
    }

    @SuppressWarnings("all")
    private void solveUnderflow(Node<K, V> node) {
        // 若node是根节点
        // 特殊对待一下，根节点可以只有一个孩子
        if (node.parent == null) {
            // 如果作为树根的node已不含任何词条
            // 则更新_root，整树高度下降一层
            if (node.entries.isEmpty()) {
                Node<K, V> child = node.children.get(0);
                _root = child;
                if (child != null) {
                    _root.parent = null;
                }
            }
            return;
        }
        // 若果真发生下溢
        if (node.entries.size() < (int) (Math.ceil(_order / 2.0) - 1)) {
            int index = node.parent.children.indexOf(node);
            Node<K, V> leftSibling = index > 0 ? node.parent.children.get(index - 1) : null;
            Node<K, V> rightSibling = node.parent.children.size() - 1 > index ? node.parent.children.get(index + 1) : null;
            // 尝试从左兄弟处借一个Entry
            if (leftSibling != null && leftSibling.entries.size() >= (int) Math.ceil(_order / 2.0)) {
                node.entries.add(0, node.parent.entries.get(index - 1));
                Node<K, V> child = leftSibling.children.remove(leftSibling.children.size() - 1);
                node.children.add(0, child);
                if (child != null) {
                    child.parent = node;
                }
                node.parent.entries.set(index - 1, leftSibling.entries.remove(leftSibling.entries.size() - 1));
                return;
            }
            // 尝试从右兄弟处借一个Entry
            if (rightSibling != null && rightSibling.entries.size() >= (int) Math.ceil(_order / 2.0)) {
                node.entries.add(node.parent.entries.get(index));
                Node<K, V> child = rightSibling.children.remove(0);
                node.children.add(child);
                if (child != null) {
                    child.parent = node;
                }
                node.parent.entries.set(index, rightSibling.entries.remove(0));
                return;
            }
            // 左、右兄弟均不存在或Entry数量不够
            // 实际上，左、右兄弟不可能同时为空
            if (leftSibling != null) {
                // 尝试和左兄弟合并
                // 1. 从父节点借一个Entry作为粘合剂
                leftSibling.entries.add(node.parent.entries.remove(index - 1));
                // 2. node所有Entry归入左兄弟
                leftSibling.entries.addAll(node.entries);
                // 3.1 node所有孩子归入左兄弟
                leftSibling.children.addAll(node.children);
                // 3.2 node的孩子节点更新父节点
                for (Node<K, V> child : node.children) {
                    if (child != null) {
                        child.parent = leftSibling;
                    }
                }
            } else {
                // 尝试和右兄弟合并
                rightSibling.entries.add(0, node.parent.entries.remove(index));
                rightSibling.entries.addAll(0, node.entries);
                rightSibling.children.addAll(0, node.children);
                for (Node<K, V> child : node.children) {
                    if (child != null) {
                        child.parent = rightSibling;
                    }
                }
            }
            // 4. 将node从其父节点中移出
            node.parent.children.remove(index);
            // node父节点移出元素后，也可能发生下溢
            solveUnderflow(leftSibling != null ? leftSibling.parent : rightSibling.parent);
        }
    }

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

        /**
         * 是否是叶节点
         */
        @SuppressWarnings("all")
        public boolean isLeaf() {
            Assert.notEmpty(children, "invalid node");
            return children.get(0) == null;
        }

        /**
         * 中序遍历
         */
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

}
