package com.anyoptional.collections;

import com.anyoptional.lang.Nullable;
import com.anyoptional.lang.VisibleForInternal;
import com.anyoptional.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 代表一个二叉搜索树的节点，封装了一些常用操作
 *
 * @apiNote BinaryNode do not permit null key.
 */
class BinaryNode<K, V> {

    @VisibleForInternal
    final Entry<K, V> entry;

    @Nullable
    @VisibleForInternal
    BinaryNode<K, V> parent;

    @Nullable
    @VisibleForInternal
    BinaryNode<K, V> left;

    @Nullable
    @VisibleForInternal
    BinaryNode<K, V> right;

    /**
     * 树高，约定叶子节点为0，空树为-1
     */
    private int height = 0;

    /**
     * 初始化根节点
     */
    @VisibleForInternal
    BinaryNode(K key) {
        this(key, null);
    }

    /**
     * 初始化根节点
     */
    @VisibleForInternal
    BinaryNode(K key, @Nullable V value) {
        this(key, value, null);
    }

    /**
     * 初始化一个叶节点，并指定其父节点
     */
    @VisibleForInternal
    BinaryNode(K key, @Nullable V value, @Nullable BinaryNode<K, V> parent) {
        this(new Entry<>(key, value), parent);
    }

    /**
     * 初始化一个叶节点，并指定其父节点
     */
    @VisibleForInternal
    BinaryNode(Entry<K, V> entry, @Nullable BinaryNode<K, V> parent) {
        this.entry = entry;
        this.parent = parent;
    }

    public int height() {
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
    public BinaryNode<K, V> uncle() {
        if (isRoot() || parent.isRoot()) {
            return null;
        }
        BinaryNode<K, V> grandParent = parent.parent;
        return parent.isLeftChild() ? grandParent.right : grandParent.left;
    }

    /**
     * 当前节点的兄弟节点，如果存在
     */
    @Nullable
    @SuppressWarnings("all")
    public BinaryNode<K, V> sibling() {
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
    public BinaryNode<K, V> grandParent() {
        if (isRoot()) {
            return null;
        }
        return parent.parent;
    }

    /**
     * 当前节点在中序遍历次序下的后继节点
     */
    @Nullable
    @SuppressWarnings("all")
    public BinaryNode<K, V> successor() {
        // 如果存在右子树
        // 后继节点必是右子树最左侧的那一个节点
        if (hasRightChild()) {
            BinaryNode<K, V> cur = right;
            while (cur.hasLeftChild()) {
                cur = cur.left;
            }
            return cur;
        }
        // 如果没有右子树
        // 后继节点是将当前节点包含于其左子树的最低祖先
        BinaryNode<K, V> cur = this;
        while (cur != null && cur.isRightChild()) {
            cur = cur.parent;
        }
        return cur != null ? cur.parent : null;
    }

    /**
     * 当前节点在中序遍历次序下的前驱节点
     */
    @Nullable
    @SuppressWarnings("all")
    public BinaryNode<K, V> predecessor() {
        // 如果存在左子树
        // 前驱节点必是左子树最右侧的那一个节点
        if (hasLeftChild()) {
            BinaryNode<K, V> cur = left;
            while (cur.hasRightChild()) {
                cur = cur.right;
            }
            return cur;
        }
        // 如果没有左子树
        // 前驱节点是将当前节点包含于其右子树的最低祖先
        BinaryNode<K, V> cur = this;
        while (cur != null && cur.isLeftChild()) {
            cur = cur.parent;
        }
        return cur != null ? cur.parent : null;
    }

    /**
     * 以当前节点为根的BST的最小节点
     */
    public BinaryNode<K, V> minimum() {
        BinaryNode<K, V> cur = this;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur;
    }

    /**
     * 以当前节点为根的BST的最大节点
     */
    public BinaryNode<K, V> maximum() {
        BinaryNode<K, V> cur = this;
        while (cur.right != null) {
            cur = cur.right;
        }
        return cur;
    }

    /**
     * 更新当前节点的高度，约定空节点高度为-1
     * 节点高度 = 左右子树中节点较高者 + 1
     */
    @VisibleForInternal
    void updateHeight() {
        int lh = left != null ? left.height : -1;
        int rh = right != null ? right.height : -1;
        height = Math.max(lh, rh) + 1;
    }

    /**
     * 更新当前节点及历代祖先的高度
     */
    @VisibleForInternal
    void updateHeightAbove() {
        BinaryNode<K, V> cur = this;
        while (cur != null) {
            int oldHeight = cur.height;
            cur.updateHeight();
            // 如果没变化
            // 那么历代祖先的高度也不会变
            if (cur.height == oldHeight) {
                break;
            }
            cur = cur.parent;
        }
    }

    /**
     * 先序遍历
     */
    @SuppressWarnings("all")
    public void traversePreOrder(Consumer<BinaryNode<K, V>> consumer) {
        Assert.notNull(consumer, "consumer is required");
        Stack<BinaryNode<K, V>> stack = new Stack<>();
        BinaryNode<K, V> cur = this;
        while (cur != null || !stack.isEmpty()) {
            // 尽可能向左，节点存在就访问
            if (cur != null) {
                stack.push(cur);
                consumer.accept(cur);
                cur = cur.left;
            } else {
                // 只有在向左无法继续深入时
                // 才尝试转向右边
                if (!stack.isEmpty()) {
                    BinaryNode<K, V> top = stack.pop();
                    cur = top.right;
                }
            }
        }
    }

    /**
     * 中序遍历
     */
    @SuppressWarnings("all")
    public void traverseInOrder(Consumer<BinaryNode<K, V>> consumer) {
        Assert.notNull(consumer, "consumer is required");
        Stack<BinaryNode<K, V>> stack = new Stack<>();
        BinaryNode<K, V> cur = this;
        while (cur != null || !stack.isEmpty()) {
            // 尽可能向左
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                // 只有在向左无法继续深入时
                // 才尝试转向右边
                // 转向时访问节点
                if (!stack.isEmpty()) {
                    BinaryNode<K, V> top = stack.pop();
                    consumer.accept(top);
                    cur = top.right;
                }
            }
        }
    }

    /**
     * 后序遍历
     */
    @SuppressWarnings("all")
    public void traversePostOrder(Consumer<BinaryNode<K, V>> consumer) {
        Assert.notNull(consumer, "consumer is required");
        Stack<BinaryNode<K, V>> stack = new Stack<>();
        BinaryNode<K, V> cur = this;
        // 记录上一次访问的节点
        BinaryNode<K, V> last = null;
        while (cur != null || !stack.isEmpty()) {
            // 尽可能向左
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                if (!stack.isEmpty()) {
                    BinaryNode<K, V> top = stack.top();
                    if (last == null || // 第一次访问
                            top.right == null || // 当前节点右子树为空
                            last == top.right) { // 上一次访问的是当前节点的右孩子
                        // 那么就访问它并记录下访问位置
                        last = top;
                        consumer.accept(stack.pop());
                    } else {
                        // 否则转向右子树
                        cur = top.right;
                    }
                }
            }
        }
    }

    /**
     * 层次遍历
     */
    @SuppressWarnings("all")
    public void traverseLevel(Consumer<BinaryNode<K, V>> consumer) {
        Assert.notNull(consumer, "consumer is required");
        Queue<BinaryNode<K, V>> queue = new Queue<>();
        queue.enqueue(this);
        BinaryNode<K, V> cur = this;
        while (!queue.isEmpty()) {
            cur = queue.dequeue();
            consumer.accept(cur);
            if (cur.hasLeftChild()) {
                queue.enqueue(cur.left);
            }
            if (cur.hasRightChild()) {
                queue.enqueue(cur.right);
            }
        }
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        List<BinaryNode<K, V>> binaryNodes = new ArrayList<>();
        traverseInOrder(binaryNodes::add);
        StringBuilder sbuf = new StringBuilder();
        Map<BinaryNode<K, V>, Integer> indexMap = new HashMap<>();
        for (BinaryNode<K, V> node : binaryNodes) {
            int index = sbuf.length();
            sbuf.append(node.description());
            indexMap.put(node, index);
        }
        for (int i = 0; i < sbuf.length(); i++) {
            sbuf.replace(i, i + 1, " ");
        }

        StringBuilder sb = new StringBuilder();
        Queue<BinaryNode<K, V>> queue = new Queue<>();
        queue.enqueue(this);
        while (!queue.isEmpty()) {
            int currentLevelSize = queue.size();
            StringBuilder line0 = new StringBuilder(sbuf),
                    line1 = new StringBuilder(sbuf);
            for (int i = 1; i <= currentLevelSize; i++) {
                BinaryNode<K, V> node = queue.dequeue();
                int curIndex = indexMap.get(node);
                String curDesc = node.description();

                // 如果存在左孩子，那么在第二行对应位置打印'/'，在第一行补上'_'
                if (node.hasLeftChild()) {
                    queue.enqueue(node.left);
                    int leftIndex = indexMap.get(node.left) + 1;
                    line1.replace(leftIndex, leftIndex + 1, "/");
                    while (leftIndex++ < curIndex) {
                        line0.replace(leftIndex, leftIndex + 1, "_");
                    }
                }
                // 对应位置打印节点值
                line0.replace(curIndex, curIndex + curDesc.length(), curDesc);
                curIndex += curDesc.length();

                // 如果存在右孩子，那么在第二行对应位置打印'\'，在第一行补上'_'
                if (node.hasRightChild()) {
                    queue.enqueue(node.right);
                    int rightIndex = indexMap.get(node.right) - 1;
                    line1.replace(rightIndex, rightIndex + 1, "\\");
                    while (curIndex++ < rightIndex) {
                        line0.replace(curIndex - 1, curIndex, "_");
                    }
                }
            }
            sb.append(line0).append("\n");
            sb.append(line1).append("\n");
        }
        return sb.toString();
    }

    protected String description() {
        return entry.toString() + "<" + height + ">";
    }

}
