package com.anyoptional.datastructures;

import com.anyoptional.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Two-dimensional array with a fixed number of rows and columns.
 * This is mostly handy for games that are played on a grid, such as chess.
 * Performance is always O(1).
 */
public class Array2D<E> {

    private final int _columns;

    private final int _rows;

    private final List<E> _storage;

    public Array2D(int columns, int rows) {
        this(columns, rows, null);
    }

    public Array2D(int columns, int rows, E initialValue) {
        Assert.isTrue(columns >= 0, "Columns must be positive");
        Assert.isTrue(rows >= 0, "Rows must be positive");
        _columns = columns;
        _rows = rows;
        int size = columns * rows;
        List<E> storage = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            storage.add(initialValue);
        }
        _storage = storage;
    }

    public int getColumns() {
        return _columns;
    }

    public int getRows() {
        return _rows;
    }

    public E get(int column, int row) {
        Assert.isTrue(column < _columns, "Column " + column + " Index is out of range");
        Assert.isTrue(row < _rows, "Row " + row + " Index is out of range");
        return _storage.get(row * _columns + column);
    }

    public void set(int column, int row, E newValue) {
        Assert.isTrue(column < _columns, "Column " + column + " Index is out of range");
        Assert.isTrue(row < _rows, "Row " + row + " Index is out of range");
        _storage.set(row * _columns + column, newValue);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < _columns; ++i) {
            sb.append("[");
            for (int j = 0; j < _rows; j++) {
                E e = get(i, j);
                sb.append(e == this ? "(this Array2D)" : e);
                if (j != _rows - 1) {
                    sb.append(",").append(" ");
                }
            }
            sb.append("]");
            if (i != _columns - 1) {
                sb.append(",").append(" ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
