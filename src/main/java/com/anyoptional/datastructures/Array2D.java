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

    private final int columns;

    private final int rows;

    private final List<E> storage;

    public Array2D(int columns, int rows) {
        this(columns, rows, null);
    }

    public Array2D(int columns, int rows, E initialValue) {
        Assert.isTrue(columns >= 0, "Columns must be positive");
        Assert.isTrue(rows >= 0, "Rows must be positive");
        this.columns = columns;
        this.rows = rows;
        int size = columns * rows;
        List<E> storage = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            storage.add(initialValue);
        }
        this.storage = storage;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public E get(int column, int row) {
        Assert.isTrue(column < columns, "Column " + column + " Index is out of range");
        Assert.isTrue(row < rows, "Row " + row + " Index is out of range");
        return storage.get(row * columns + column);
    }

    public void set(int column, int row, E newValue) {
        Assert.isTrue(column < columns, "Column " + column + " Index is out of range");
        Assert.isTrue(row < rows, "Row " + row + " Index is out of range");
        storage.set(row * columns + column, newValue);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < columns; ++i) {
            sb.append("[");
            for (int j = 0; j < rows; j++) {
                E e = get(i, j);
                sb.append(e == this ? "(this Array2D)" : e);
                if (j != rows - 1) {
                    sb.append(",").append(" ");
                }
            }
            sb.append("]");
            if (i != columns - 1) {
                sb.append(",").append(" ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
