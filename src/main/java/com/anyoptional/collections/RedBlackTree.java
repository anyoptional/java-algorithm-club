package com.anyoptional.collections;

import com.anyoptional.lang.Nullable;

/**
 *
 */
public class RedBlackTree<K, V> extends BinarySearchTree<K, V> {


    @Override
    public void insert(K key, @Nullable V value) {
        super.insert(key, value);

    }

    @Override
    protected BinaryNode<K, V> newBinaryNode(K key, @Nullable V value, @Nullable BinaryNode<K, V> parent) {
        if (isEmpty()) {
            // 根节点始终为黑色
            return new Node<>(key, value, Node.Color.BLACK, parent);
        }
        // 其它情况为红色，不会改变黑深度
        return new Node<>(key, value, Node.Color.RED, parent);
    }

    static class Node<K, V> extends BinaryNode<K, V> {

        enum Color {
            RED,
            BLACK
        }

        Color color;

        Node(K key, @Nullable V value, Color color, @Nullable  BinaryNode<K, V> parent) {
            super(key, value, parent);
            this.color = color;
        }

        public boolean isRed() {
            return color == Color.RED;
        }

        public boolean isBlack() {
            return color == Color.BLACK;
        }

        @Override
        void updateHeight() {
            int lh = (left == null || ((Node<K, V>) left).isBlack()) ? 1 : 0;
            int rh = (right == null || ((Node<K, V>) right).isBlack()) ? 1 : 0;
            height = Math.max(lh, rh) + (isBlack() ? 1 : 0);
        }
    }
    
}
