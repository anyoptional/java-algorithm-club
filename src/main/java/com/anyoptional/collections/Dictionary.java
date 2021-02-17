package com.anyoptional.collections;

import com.anyoptional.lang.Nullable;
import com.anyoptional.util.Assert;

import java.util.*;

/**
 * 词典（哈希表），使用拉链法解决冲突。
 *
 * @apiNote Dictionary do not permit null key.
 */
public class Dictionary<K, V> implements Map<K, V> {

    static final int DEFAULT_CAPACITY = 2;

    static final double DEFAULT_LOAD_FACTOR = 0.75;

    private int _size = 0;

    private int _capacity;

    private final double _loadFactor;

    private Bucket<K, V>[] _buckets;

    public Dictionary() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public Dictionary(int capacity, double loadFactor) {
        if (capacity < 2) {
            _capacity = DEFAULT_CAPACITY;
        } else {
            _capacity = capacity;
        }
        if (loadFactor < 0.5 || loadFactor > 1) {
            _loadFactor = DEFAULT_LOAD_FACTOR;
        } else {
            _loadFactor = loadFactor;
        }
        _buckets = (Bucket<K, V>[]) new Bucket[capacity];
    }

    public Dictionary(Map<K, V> map) {
        _capacity = map.size() * 2;
        _loadFactor = DEFAULT_LOAD_FACTOR;
        _buckets = (Bucket<K, V>[]) new Bucket[_capacity];
        putAll(map);
    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public boolean isEmpty() {
        return _size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int hashValue = hash(key);
        int index = indexOf(hashValue);
        Bucket<K, V> bucket = _buckets[index];
        while (bucket != null) {
            if (bucket.hash == hashValue && bucket.getKey().equals(key)) {
                return true;
            }
            bucket = bucket.next;
        }
        return false;
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        for (Bucket<K, V> head : _buckets) {
            while (head != null) {
                if (value == null) {
                    if (head.getValue() == null) {
                        return true;
                    }
                } else {
                    if (value.equals(head.getValue())) {
                        return true;
                    }
                }
                head = head.next;
            }
        }
        return false;
    }

    @Override
    @Nullable
    public V get(Object key) {
        int hashValue = hash(key);
        int index = indexOf(hashValue);
        Bucket<K, V> bucket = _buckets[index];
        while (bucket != null) {
            if (bucket.hash == hashValue && bucket.getKey().equals(key)) {
                return bucket.getValue();
            }
            bucket = bucket.next;
        }
        return null;
    }

    @Override
    @Nullable
    public V put(K key, @Nullable V value) {
        return putValue(key, value, _buckets);
    }

    @Override
    @Nullable
    public V remove(Object key) {
        int hashValue = hash(key);
        int index = indexOf(hashValue);
        Bucket<K, V> prev = null;
        Bucket<K, V> cur = _buckets[index];
        while (cur != null) {
            if (cur.hash == hashValue && cur.getKey().equals(key)) {
                break;
            }
            prev = cur;
            cur = cur.next;
        }
        // not found
        if (cur == null) return null;
        if (prev == null) {
            _buckets[index] = null;
        } else {
            prev.next = cur.next;
        }
        _size -= 1;
        return cur.getValue();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        _size = 0;
        Arrays.fill(_buckets, null);
    }

    @Override
    public Set<K> keySet() {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<V> values() {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Nullable
    private V putValue(K key, @Nullable V value, Bucket<K, V>[] buckets) {
        Assert.notNull(key, "key is required");
        V result = value;
        int hashValue = hash(key);
        int index = indexOf(hashValue);
        Bucket<K, V> prev = null;
        Bucket<K, V> cur = buckets[index];
        while (cur != null) {
            if (cur.hash == hashValue && cur.getKey().equals(key)) {
                break;
            }
            prev = cur;
            cur = cur.next;
        }
        // not found
        if (cur == null && prev == null) {
            if (buckets == _buckets) {
                _size += 1;
            }
            buckets[index] = new Bucket<>(key, value, hashValue);
        } else {
            if (cur != null) {
                result = cur.setValue(value);
            } else {
                if (buckets == _buckets) {
                    _size += 1;
                }
               prev.next = new Bucket<>(key, value, hashValue);
            }
        }
        if (_size > _capacity * _loadFactor) {
            rehash();
        }
        return result;
    }

    private static <K> int hash(K key) {
        // MAD
        return 31 * key.hashCode() + 7;
    }

    private int indexOf(int hashValue) {
        return hashValue % _capacity;
    }

    private void rehash() {
        _capacity <<= 1;
        Bucket<K, V>[] buckets = (Bucket<K, V>[]) new Bucket[_capacity];
        for (Bucket<K, V> head : _buckets) {
            while (head != null) {
                putValue(head.getKey(), head.getValue(), buckets);
                head = head.next;
            }
        }
        _buckets = buckets;
    }

    static class Bucket<K, V> extends com.anyoptional.collections.Entry<K, V> {

        final int hash;

        @Nullable
        Bucket<K, V> next;

        Bucket(K key, @Nullable V value, int hash) {
            super(key, value);
            this.hash = hash;
        }

        @Override
        public boolean equals(@Nullable Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Bucket<?, ?> entry = (Bucket<?, ?>) o;
            return hash == entry.hash;
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), hash);
        }

    }

}
