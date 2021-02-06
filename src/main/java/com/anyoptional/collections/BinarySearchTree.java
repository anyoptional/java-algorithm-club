package com.anyoptional.collections;

import com.anyoptional.lang.Nullable;
import com.anyoptional.util.Assert;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

public class BinarySearchTree<K, V> {

    private int _size = 0;

    @Nullable
    private TreeNode<K, V> _root;

    @Nullable
    private Comparator<? super K> _comparator;

    public BinarySearchTree() {
    }

    public BinarySearchTree(Comparator<? super K> comparator) {
        Assert.notNull(comparator, "comparator is required");
        _comparator = comparator;
    }

    public int size() {
        return _size;
    }

    public boolean isEmpty() {
        return _root == null;
    }

    @Nullable
    public V search(K key) {
        Assert.notNull(key, "key is required");
        return null;
    }

    public void insert(K key, @Nullable V value) {
        Assert.notNull(key, "key is required");

    }

    public void addAll(Collection<? extends K> c) {
        Assert.notNull(c, "collection is required");
        for (K key : c) {
            insert(key, null);
        }
    }

    public void addAll(Map<K, V> map) {
        Assert.notNull(map, "map is required");
        for (Map.Entry<K, V> e : map.entrySet()) {
            insert(e.getKey(), e.getValue());
        }
    }

    @Nullable
    public V remove(K key) {
        Assert.notNull(key, "key is required");
        return null;
    }



}
