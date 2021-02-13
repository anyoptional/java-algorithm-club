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
        solveDoubleRed((Node<K, V>) doInsert(key, value));
    }

    @Override
    public Entry<K, V> remove(K key) {
        return super.remove(key);
    }

    /**
     * 双红修正
     */
    private void solveDoubleRed(Node<K, V> v) {
        if (v.parent == null) return;
        if (((Node<K, V>) v.parent).isBlack()) return;
        // 若果真发生双红缺陷
        // p一定存在且为红色
        // 由定义1，g一定存在，因为红节点不能作为根节点
        // 由定义3，g一定为黑色
        // 并且g、p、v下属的四颗子树黑深度一定相等
        Node<K, V> p = (Node<K, V>) v.parent;
        Assert.isTrue(p != null && p.isRed(), "p must be red");
        Node<K, V> g = (Node<K, V>) v.grandParent();
        Assert.isTrue(g != null && g.isBlack(), "g must be black");
        Node<K, V> u = (Node<K, V>) v.uncle();
        // u如果是外部节点也视为黑节点
        if (u == null || u.isBlack()) {
            // 出现BRR或RRB两种非法情况
            // 根据p、v的相对位置进行处理
            if (p.isLeftChild() && v.isLeftChild()) {
                // zig
                g.zig();
                p.color = Node.Color.BLACK;
            } else if (p.isLeftChild() && v.isRightChild()) {
                // zag-zig
                p.zag();
                g.zig();
                v.color = Node.Color.BLACK;
            } else if (p.isRightChild() && v.isLeftChild()) {
                // zig-zag
                p.zig();
                g.zag();
                v.color = Node.Color.BLACK;
            } else {
                // zag
                g.zag();
                p.color = Node.Color.BLACK;
            }
            // g必由黑转红
            g.color = Node.Color.RED;
            g.updateHeightAbove();
            // 最后检查一下根节点是否需要变化
            if (p.parent == null) {
                // zig/zag变换会提升p
                _root = p;
            } else if (v.parent == null) {
                // zig-zag/zag-zig变换会提升v
                _root = v;
            }
        } else {
            // p、v、u均为红节点，提升变换以后
            // 的超级节点就包含有g、p、v、u
            // 四个关键码，站在BTree的角度
            // 就是发生了上溢
            // 此时只需要将g提升一级，p、u转为黑色
            // g如果是根节点就不需要重染色，此时整树
            // 黑高度提升一级，否则g转红
            if (!g.isRoot()) {
                g.color = Node.Color.RED;
            }
            p.color = Node.Color.BLACK;
            u.color = Node.Color.BLACK;
            u.updateHeight();
            p.updateHeight();
            g.updateHeightAbove();
            solveDoubleRed(g);
        }
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
            int lh = left != null ? left.height : 1;
            int rh = right != null ? right.height : 1;
            Assert.isTrue(lh == rh, "subtrees height do not equal");
            // height为黑深度
            height = lh + (isBlack() ? 1 : 0);
        }

        @Override
        protected String description() {
            return super.description() + (isBlack() ? "<B>" : "<R>");
        }

    }

}
