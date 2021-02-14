package com.anyoptional.collections;

import com.anyoptional.lang.Nullable;
import com.anyoptional.util.Assert;

import java.util.Comparator;

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

    public RedBlackTree() {
    }

    public RedBlackTree(Comparator<? super K> comparator) {
        super(comparator);
    }

    @Override
    public void insert(K key, @Nullable V value) {
        solveDoubleRed((Node<K, V>) doInsert(key, value));
    }

    @Override
    public Entry<K, V> remove(K key) {
        Tuple3<Entry<K, V>, BinaryNode<K, V>, BinaryNode<K, V>> tuple3 = doRemove(key, false);
        Node<K, V> parent = (Node<K, V>) tuple3.second;
        Node<K, V> replacement = (Node<K, V>) tuple3.third;
        // 删除的是根节点
        if (parent == null) {
            // 若树非空
            Node<K, V> root = (Node<K, V>) _root;
            if (root != null) {
                // 只需给根节点重染色并更新高度
                root.color = Node.Color.BLACK;
                root.updateHeight();
            } // else 整树为空
        } else {
            // 若删除key对应的节点后
            // 其父节点高度仍平衡，则
            // 不必调整，否则...
            if (!parent.isBalanced()) {
                // 此时说明被删除的节点是黑色
                // 如果接替节点为红色，只需令其转黑
                if (replacement != null && replacement.isRed()) {
                    replacement.color = Node.Color.BLACK;
                    replacement.updateHeight();
                } else {
                    // 被删除节点和它的接替者均为黑色
                    // 被删除节点摘除后，站在BTree的角度
                    // 就是这个超级节点已经不含任何词条，换
                    // 言之，发生了下溢
                    solveDoubleBlack(replacement, parent);
                }
            } // else 被摘除节点必为红色，继任者必为黑色
        }
        return tuple3.first;
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
            Node<K, V> vertex = (Node<K, V>) rotateAt(g, p, v);
            // 新的顶点转为黑色
            vertex.color = Node.Color.BLACK;
            // g必由黑转红
            g.color = Node.Color.RED;
            g.updateHeightAbove();
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
    private void solveDoubleBlack(@Nullable Node<K, V> r, @Nullable Node<K, V> p) {
        Node<K, V> parent = r != null ? (Node<K, V>) r.parent : p;
        if (parent == null) return;
        Node<K, V> sibling = r == parent.left ?
                (Node<K, V>) parent.right :
                (Node<K, V>) parent.left;
        // 黑兄弟
        if (isBlack(sibling)) {
            // 没有红孩子
            // 此种情况在BTree中对应于兄弟节点没有足够的词条可以借出，需要合并
            if (sibling == null || (isBlack(sibling.left) && isBlack(sibling.right))) {
                if (sibling != null) {
                    sibling.color = Node.Color.RED;
                    sibling.updateHeight();
                }
                if (parent.isRed()) {
                    // parent转黑，但黑高度不变
                    // 这种情况也不会再次导致下溢
                    // 因为parent为红，那么此时
                    // 它的父节点必定为黑
                    parent.color = Node.Color.BLACK;
                    parent.updateHeight();
                } else {
                    // parent保持黑色，但黑高度下降
                    // 因为parent为黑色，它独立成为
                    // 一个BTree的超级节点，此时借出
                    // 后会继续发生下溢
                    parent.updateHeight();
                    // 递归上溯
                    solveDoubleBlack(parent, null);
                }
            } else {
                // 有红孩子
                // 同时为红时左孩子优先
                // 此种情况在BTree中对应于兄弟节点有足够的词条可以借出
                Node<K, V> redNephew;
                if (isRed(sibling.left)) {
                    redNephew = (Node<K, V>) sibling.left;
                } else {
                    redNephew = (Node<K, V>) sibling.right;
                }
                Assert.notNull(redNephew, "red-nephew must exists");
                Node.Color oldColor = parent.color;
                // parent、sibling、redNephew下的四颗子树黑高度都相等
                Node<K, V> vertex = (Node<K, V>) rotateAt(parent, sibling, redNephew);
                if (vertex.left != null) {
                    ((Node<K, V>) vertex.left).color = Node.Color.BLACK;
                    vertex.left.updateHeight();
                }
                if (vertex.right != null) {
                    ((Node<K, V>) vertex.right).color = Node.Color.BLACK;
                    vertex.right.updateHeight();
                }
                vertex.color = oldColor;
                vertex.updateHeight();
            }
        } else {
            // 红兄弟，也即parent必为黑
            // 由定义3，外部节点到根节点途中的黑节点数目相等
            // 又r、parent均为黑，因此sibling同侧必有节点
            Node<K, V> nephew = sibling.isLeftChild() ?
                    (Node<K, V>) sibling.left :
                    (Node<K, V>) sibling.right;
            Assert.notNull(nephew, "nephew must exists");
            // 此时只需令parent转红、sibling转黑
            parent.color = Node.Color.RED;
            sibling.color = Node.Color.BLACK;
            // 此时在BTree上拓扑结构不需要调整
            // 但对应在RBTree上却要做一次旋转
            rotateAt(parent, sibling, nephew);
            // 进而退化成 sibling为黑，有红孩子
            // 或sibling为黑，没有红孩子但parent为红的情况
            solveDoubleBlack(r, parent);
        }
    }

    private boolean isBlack(@Nullable BinaryNode<K, V> node) {
        // 外部节点为黑
        return node == null || ((Node<K, V>) node).isBlack();
    }

    private boolean isRed(@Nullable BinaryNode<K, V> node) {
        // 非黑即红
        return !isBlack(node);
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

        public boolean isBalanced() {
            int lh = left != null ? left.height : 1;
            int rh = right != null ? right.height : 1;
            return lh == rh && height == lh + (isBlack() ? 1 : 0);
//            if (lh != rh) {
//                boolean condition = height == Math.max(lh, rh) + (isBlack() ? 1 : 0);
//                Assert.isTrue(condition, "RedBlackTree did not update height correctly");
//                return false;
//            }
//            return true;
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
            height = Math.max(lh, rh) + (isBlack() ? 1 : 0);
//            Assert.isTrue(lh == rh, "subtrees height do not equal");
//            // height为黑深度
//            height = lh + (isBlack() ? 1 : 0);
        }

        @Override
        protected String description() {
            return super.description() + (isBlack() ? "<B>" : "<R>");
        }

    }

}
