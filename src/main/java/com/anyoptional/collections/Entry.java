package com.anyoptional.collections;

import com.anyoptional.lang.Nullable;
import com.anyoptional.lang.VisibleForTesting;

import java.util.Map;
import java.util.Objects;

public class Entry<K, V> implements Map.Entry<K, V> {

    private K _key;

    @Nullable
    private V _value;

    @VisibleForTesting
    Entry(K key, @Nullable V value) {
        // assert key != null
        _key = key;
        _value = value;
    }

    @Override
    public K getKey() {
        return _key;
    }

    /**
     * Package visible for internal ops
     */
    @VisibleForTesting
    void setKey(K key) {
        _key = key;
    }

    @Override
    @Nullable
    public V getValue() {
        return _value;
    }

    @Override
    @Nullable
    public V setValue(V value) {
        V old = _value;
        _value = value;
        return old;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry<?, ?> entry = (Entry<?, ?>) o;
        return _key.equals(entry._key) &&
                Objects.equals(_value, entry._value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_key, _value);
    }

    @Override
    public String toString() {
        if (_value == null) {
            return "(" + _key + ")";
        }
        return "(" + _key + ", " + _value + ")";
    }

}
