package com.anyoptional.collections;

import com.anyoptional.lang.Nullable;

import java.util.Comparator;

public class AVLTree<K, V> extends BinarySearchTree<K, V> {

    public AVLTree() {
    }

    public AVLTree(Comparator<? super K> comparator) {
       super(comparator);
    }

    @Override
    public void insert(K key, @Nullable V value) {
        BinaryNode<K, V> node = doInsert(key, value);
        for (BinaryNode<K, V> g = node; g != null; g = g.parent) {
            if (!g.isAvlBalanced()) {
                // 若果真在g节点处失衡
                // g至少是被插入节点的祖父
                rotateAt(g);
                return;
            }
        }
    }

    @Override
    public Entry<K, V> remove(K key) {
        Tuple<Entry<K, V>, BinaryNode<K, V>> tuple = doRemove(key);
        for (BinaryNode<K, V> g = tuple.second; g != null; g = g.parent) {
            if (!g.isAvlBalanced()) {
                // 若果真在g节点处失衡
                // 被删除的节点肯定处于g相对较矮的子树
                // 并且，如果g也属于其祖先中相对较矮的子树
                // 经过旋转重平衡后，因为高度降低了一级，因此
                // 又可能会导致更高层的祖先节点失衡
                rotateAt(g);
            }
        }
        return tuple.first;
    }

    @SuppressWarnings("all")
    private void rotateAt(BinaryNode<K, V> g) {
        // 若g失衡，p、v必然存在
        BinaryNode<K, V> p = g.highestChild();
        BinaryNode<K, V> v = p.highestChild();
        // 根据p、v
        if (p.isLeftChild() && v.isLeftChild()) {
            // zig
            g.zig();
            g.updateHeight();
            p.updateHeight();
            if (p.parent != null) {
                p.parent.updateHeightAbove();
            }
        } else if (p.isLeftChild() && v.isRightChild()) {
            // zag-zig
            p.zag();
            p.updateHeight();
            v.updateHeight();
            g.zig();
            g.updateHeight();
            v.updateHeight();
            if (v.parent != null) {
                v.parent.updateHeightAbove();
            }
        } else if (p.isRightChild() && v.isLeftChild()) {
            // zig-zag
            p.zig();
            p.updateHeight();
            v.updateHeight();
            g.zag();
            g.updateHeight();
            v.updateHeight();
            if (v.parent != null) {
                v.parent.updateHeightAbove();
            }
        } else {
            // zag
            g.zag();
            g.updateHeight();
            p.updateHeight();
            if (p.parent != null) {
                p.parent.updateHeightAbove();
            }
        }
        // 最后检查一下根节点是否需要变化
        if (p.parent == null) {
            // zig/zag变换会提升p
            _root = p;
        } else if (v.parent == null) {
            // zig-zag/zag-zig变换会提升v
            _root = v;
        }
    }

}