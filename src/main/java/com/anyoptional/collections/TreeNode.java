package com.anyoptional.collections;

import com.anyoptional.lang.Internal;
import com.anyoptional.lang.Nullable;
import com.anyoptional.util.Assert;

import java.util.function.Consumer;

/**
 * 代表一个二叉树节点，封装了一些常用操作
 *
 * @apiNote TreeNode do not permit null value.
 */
@Internal
class TreeNode<K, V> {

    final Entry<K, V> entry;

    @Nullable
    TreeNode<K, V> parent;

    @Nullable
    TreeNode<K, V> left;

    @Nullable
    TreeNode<K, V> right;

    /**
     * 树高，约定叶子节点为0，空树为-1
     */
    private int height = 0;

    /**
     * 初始化根节点
     */
    TreeNode(K key) {
        this(new Entry<>(key, null), null);
    }

    /**
     * 初始化一个叶节点，并指定其父节点
     */
    TreeNode(Entry<K, V> entry, @Nullable TreeNode<K, V> parent) {
        this.entry = entry;
        this.parent = parent;
    }

    public int getHeight() {
        return height;
    }

    /**
     * 以当前节点为根的子树规模
     */
    public int size() {
        int ls = left != null ? left.size() : 0;
        int rs = right != null ? right.size() : 0;
        return ls + rs + 1;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    @SuppressWarnings("all")
    public boolean isLeftChild() {
        return !isRoot() && parent.left == this;
    }

    @SuppressWarnings("all")
    public boolean isRightChild() {
        return !isRoot() && parent.right == this;
    }

    public boolean hasLeftChild() {
        return left != null;
    }

    public boolean hasRightChild() {
        return right != null;
    }

    public boolean hasAnyChild() {
        return hasLeftChild() || hasRightChild();
    }

    public boolean hasBothChildren() {
        return hasLeftChild() && hasRightChild();
    }

    /**
     * 当前节点的叔父节点，如果存在
     */
    @Nullable
    @SuppressWarnings("all")
    public TreeNode<K, V> uncle() {
        if (isRoot() || parent.isRoot()) {
            return null;
        }
        TreeNode<K, V> grandParent = parent.parent;
        return parent.isLeftChild() ? grandParent.right : grandParent.left;
    }

    /**
     * 当前节点的兄弟节点，如果存在
     */
    @Nullable
    @SuppressWarnings("all")
    public TreeNode<K, V> sibling() {
        if (isRoot()) {
            return null;
        }
        return isLeftChild() ? parent.right : parent.left;
    }

    /**
     * 当前节点的祖父，如果存在
     */
    @Nullable
    @SuppressWarnings("all")
    public TreeNode<K, V> grandParent() {
        if (isRoot()) {
            return null;
        }
        return parent.parent;
    }

    /**
     * 当前节点在中序遍历次序下的后继节点
     */
    @Nullable
    public TreeNode<K, V> successor() {
        return null;
    }

    /**
     * 当前节点在中序遍历次序下的前驱节点
     */
    @Nullable
    public TreeNode<K, V> predecessor() {
        return null;
    }

    /**
     * 更新当前节点的高度
     */
    public void updateHeight() {
        height = stature(this);
    }

    /**
     * 更新当前节点及历代祖先的高度
     */
    public void updateHeightAbove() {
        TreeNode<K, V> node = this.parent;
        while (node != null) {
            if (node.height == stature(node)) {
                break;
            }
            node.updateHeight();
            node = node.parent;
        }
    }

    /**
     * 计算节点高度，约定空节点高度为-1
     *  节点高度 = 左右子树中节点较高者 + 1
     */
    static <K, V> int stature(@Nullable TreeNode<K, V> node) {
        if (node == null) return -1;
        int lh = node.left != null ? node.left.height : -1;
        int rh = node.right != null ? node.right.height : -1;
        return Math.max(lh, rh) + 1;
    }

    /**
     * 先序遍历
     */
    public void traversePreOrder(Consumer<TreeNode<K, V>> consumer) {
        Assert.notNull(consumer, "consumer is required");

    }

    /**
     * 中序遍历
     */
    public void traverseInOrder(Consumer<TreeNode<K, V>> consumer) {
        Assert.notNull(consumer, "consumer is required");

    }

    /**
     * 后序遍历
     */
    public void traversePostOrder(Consumer<TreeNode<K, V>> consumer) {
        Assert.notNull(consumer, "consumer is required");

    }

    /**
     * 层次遍历
     */
    public void traverseLevel(Consumer<TreeNode<K, V>> consumer) {
        Assert.notNull(consumer, "consumer is required");

    }

}
