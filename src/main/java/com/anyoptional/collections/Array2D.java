package com.anyoptional.collections;

import com.anyoptional.lang.Nullable;
import com.anyoptional.util.Assert;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * Two-dimensional array with a fixed number of rows and columns.
 * This is mostly handy for games that are played on a grid, such as chess.
 * Performance is always O(1).
 *
 * @apiNote Array2D permit null element.
 */
public class Array2D<E> implements Iterable<E> {

    /** 列数 */
    private final int _columns;

    /** 行数 */
    private final int _rows;

    private final Object[] _storage;

    /**
     * 初始化一个指定行、列数的二维数组，数组中的元素用null填充。
     *
     * @param columns 列数
     * @param rows 行数
     */
    public Array2D(int rows, int columns) {
        this(rows, columns, null);
    }

    /**
     * 初始化一个指定行、列数的二维数组，数组中的元素用指定值填充。
     *
     * @param columns 列数
     * @param rows 行数
     * @param initialValue 数组元素初始值
     */
    public Array2D(int rows, int columns, @Nullable E initialValue) {
        Assert.isTrue(rows >= 0, "rows[{}] must be positive", rows);
        Assert.isTrue(columns >= 0, "columns[{}] must be positive", columns);
        _columns = columns;
        _rows = rows;
        int size = columns * rows;
        Object[] storage = new Object[size];
        Arrays.fill(storage, initialValue);
        _storage = storage;
    }

    public int size() {
        return _storage.length;
    }

    public int getColumns() {
        return _columns;
    }

    public int getRows() {
        return _rows;
    }

    /**
     * 获取某个位置的值
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public E get(int row, int column) {
        Assert.isTrue(row < _rows, "row[{}] out of range", row);
        Assert.isTrue(column < _columns, "column[{}] out of range", column);
        return (E) _storage[row * _columns + column];
    }

    /**
     * 设置某个位置的值
     */
    public void set(int row, int column, @Nullable E newValue) {
        Assert.isTrue(row < _rows, "row[{}] out of range", row);
        Assert.isTrue(column < _columns, "column[{}] out of range", column);
        _storage[row * _columns + column] = newValue;
    }

    /**
     * 返回一个逐行输出的迭代器
     */
    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Array2D<?> array2D = (Array2D<?>) o;
        return _columns == array2D._columns &&
                _rows == array2D._rows &&
                Arrays.equals(_storage, array2D._storage);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(_columns, _rows);
        result = 31 * result + Arrays.hashCode(_storage);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < _rows; ++i) {
            sb.append("[");
            for (int j = 0; j < _columns; j++) {
                E e = get(i, j);
                sb.append(e == this ? "(this Array2D)" : e);
                if (j != _columns - 1) {
                    sb.append(",").append(" ");
                }
            }
            sb.append("]");
            if (i != _rows - 1) {
                sb.append(",").append(" ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private class Itr implements Iterator<E> {

        private int curIndex = 0;

        @Override
        public boolean hasNext() {
            return curIndex < size();
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            return (E) _storage[curIndex++];
        }
    }

}
