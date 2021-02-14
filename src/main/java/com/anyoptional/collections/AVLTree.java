package com.anyoptional.collections;

import com.anyoptional.lang.Nullable;

import java.util.Comparator;

/**
 * 自平衡的二叉搜索树，左右子树的高度差不超过1。
 *
 * @apiNote AVLTree do not permit null key.
 */
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
            if (!((Node<K, V>) g).isBalanced()) {
                // 若果真在g节点处失衡
                // g至少是被插入节点的祖父
                rotateAt(g);
                return;
            }
        }
    }

    @Override
    public Entry<K, V> remove(K key) {
        Tuple3<Entry<K, V>, BinaryNode<K, V>, BinaryNode<K, V>> tuple3 = doRemove(key, true);
        for (BinaryNode<K, V> g = tuple3.second; g != null; g = g.parent) {
            if (!((Node<K, V>) g).isBalanced()) {
                // 若果真在g节点处失衡
                // 被删除的节点肯定处于g相对较矮的子树
                // 并且，如果g也属于其祖先中相对较矮的子树
                // 经过旋转重平衡后，因为高度降低了一级，因此
                // 又可能会导致更高层的祖先节点失衡
                rotateAt(g);
            }
        }
        return tuple3.first;
    }

    @SuppressWarnings("all")
    private void rotateAt(BinaryNode<K, V> g) {
        // 若g失衡，p、v必然存在
        BinaryNode<K, V> p = g.highestChild();
        BinaryNode<K, V> v = p.highestChild();
        rotateAt(g, p, v);
        p.updateHeight();
        g.updateHeightAbove();
    }

    @Override
    protected BinaryNode<K, V> newBinaryNode(K key, @Nullable V value, @Nullable BinaryNode<K, V> parent) {
        return new Node<>(key, value, parent);
    }

    static class Node<K, V> extends BinaryNode<K, V> {

        Node(K key, @Nullable V value, @Nullable BinaryNode<K, V> parent) {
            super(key, value, parent);
        }

        /**
         * AVL平衡当且仅当左、右子树高度差不超过1
         */
        @SuppressWarnings("all")
        boolean isBalanced() {
            int lh = left != null ? left.height : -1;
            int rh = right != null ? right.height : -1;
            return Math.abs(lh - rh) <= 1;
        }

    }

}
