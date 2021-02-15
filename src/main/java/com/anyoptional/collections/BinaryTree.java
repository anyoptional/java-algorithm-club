package com.anyoptional.collections;

import com.anyoptional.lang.Nullable;

import java.util.Collection;
import java.util.function.Consumer;

public interface BinaryTree<K, V> extends Iterable<Entry<K, V>> {

    /**
     * 树的规模
     */
    int size();

    /**
     * 是否非空
     */
    boolean isEmpty();

    /**
     * 查询树中是否包含指定key
     */
    boolean containsKey(K key);

    /**
     * 查询树中最高的、指定key对应的值
     */
    @Nullable
    V searchValue(K key);

    /**
     * 查询树中最高的、指定key对应的Entry
     */
    @Nullable
    Entry<K, V> search(K key);

    /**
     * 向树中插入一对键值对
     */
    void insert(K key, @Nullable V value);

    /**
     * 批量插入
     */
    void addAll(Collection<? extends Entry<K, V>> entries);

    /**
     * 删除树中`最高`的、拥有指定key的节点
     */
    @Nullable
    Entry<K, V> remove(K key);

    /**
     * 先序遍历
     */
    void traversePreOrder(Consumer<Entry<K, V>> consumer);

    /**
     * 中序遍历
     */
    void traverseInOrder(Consumer<Entry<K, V>> consumer);

    /**
     * 后序遍历
     */
    void traversePostOrder(Consumer<Entry<K, V>> consumer);

    /**
     * 层次遍历
     */
    void traverseLevel(Consumer<Entry<K, V>> consumer);

}
