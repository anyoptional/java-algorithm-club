package com.anyoptional.collections;

import com.anyoptional.lang.Nullable;
import com.anyoptional.util.Assert;

/**
 * 红黑树是由红、黑两类节点组成的BST。实现上统一增设外部节点，
 * 使之成为真二叉树（实际上这些外部节点可以是假想出来的，并不
 * 一定要有真实的物理表示）。对于红黑树，它满足
 *  1. 树根必须为黑色
 *  2. 外部节点均为黑色
 *  3. 其余节点，若为红，则只能有黑孩子（红之子、之父必黑）
 *  4. 外部节点到根节点途中的黑节点数目相等（黑深度）
 *
 * 红黑树的定义并不是那么直观，借助于树形结构的一种等价变换——提升变换，
 * 将每一个红节点都向上提升与它的父节点平齐，可以得到一颗4阶{@link BTree}。
 * 由于红之子、之父必黑，经过提升替换以后必然不会出现两个红节点两两相邻的情况。
 *
 *              ____B____                               (R, B, R)
 *            /          \                            /   |  |   \
 *         __R__        __R__      lifting ->       (B,R) B  B  (R,B)
 *       /      \     /      \
 *      B__      B   B      __B
 *         \              /
 *          R            R
 * 可以看到，经过提升变换之后，本来高度参差不齐的红黑树变成了所有底层节点沿同一水平线平齐的结构，
 * 毫无疑问，这是一颗(2,4)树。
 *
 * @apiNote RedBlackTree do not permit null key.
 */
public class RedBlackTree<K, V> extends BinarySearchTree<K, V> {

    @Override
    public void insert(K key, @Nullable V value) {
        Node<K, V> node = (Node<K, V>) doInsert(key, value);
        if (node.isBlack()) return;
        solveDoubleRed(node);
    }

    @Override
    public Entry<K, V> remove(K key) {
        return super.remove(key);
    }

    /**
     * 双红修正
     */
    private void solveDoubleRed(Node<K, V> v) {
        
    }

    /**
     * 双黑修正
     */
    private void solveDoubleBlack(Node<K, V> node) {

    }

    @Override
    protected BinaryNode<K, V> newBinaryNode(K key, @Nullable V value, @Nullable BinaryNode<K, V> parent) {
        if (isEmpty()) {
            // 根节点始终为黑色
            return new Node<>(key, value, Node.Color.BLACK, parent);
        }
        // 其它情况为红色，不会改变黑深度
        // 满足定义1，2，4，但不见得满足3，比如parent也为红的话
        return new Node<>(key, value, Node.Color.RED, parent);
    }

    static class Node<K, V> extends BinaryNode<K, V> {

        enum Color {
            RED,
            BLACK
        }

        Color color;

        Node(K key, @Nullable V value, Color color, @Nullable BinaryNode<K, V> parent) {
            super(key, value, parent);
            this.color = color;
        }

        public boolean isRed() {
            return color == Color.RED;
        }

        public boolean isBlack() {
            return color == Color.BLACK;
        }

        /**
         * 红黑树的高度只取决于当前节点
         * 到根节点的通路中黑节点的个数
         */
        @Override
        void updateHeight() {
            // 外部节点为黑色
            int lh = (left == null || ((Node<K, V>) left).isBlack()) ? 1 : 0;
            int rh = (right == null || ((Node<K, V>) right).isBlack()) ? 1 : 0;
            // height为黑深度
            height = Math.max(lh, rh) + (isBlack() ? 1 : 0);
        }
    }

}
