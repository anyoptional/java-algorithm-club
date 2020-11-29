package com.anyoptional.collections;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class Array2DTest {

    @Test
    public void testArrayWithoutInitialValue() {
        Array2D<Integer> array = new Array2D<>(2, 3);
        assertEquals(3, array.getColumns());
        assertEquals(2, array.getRows());
        assertEquals(6, array.size());
        for (int i = 0; i < array.getRows(); i++) {
            for (int j = 0; j < array.getColumns(); j++) {
                assertNull(array.get(i, j));
            }
        }
    }

    @Test
    public void testArray2DEquals() {
        Array2D<Integer> a0 = new Array2D<>(2, 3);
        Array2D<Integer> a1 = new Array2D<>(2, 3);
        Array2D<Integer> a2 = new Array2D<>(3, 2);
        Array2D<Integer> a3 = new Array2D<>(2, 3, 1);
        Array2D<Integer> a4 = new Array2D<>(2, 3, 2);
        Array2D<Integer> a5 = new Array2D<>(2, 3, 1);

        assertEquals(a0, a1);
        assertNotEquals(a0, a2);
        assertNotEquals(a1, a2);
        assertNotEquals(a0, a3);
        assertNotEquals(a3, a4);
        assertEquals(a3, a5);
    }

    @Test
    public void testIntegerArrayWithPositiveRowsAndColumns() {
        Array2D<Integer> array = new Array2D<>(2, 3, 0);
        assertEquals(3, array.getColumns());
        assertEquals(2, array.getRows());
        assertEquals(6, array.size());
        for (int i = 0; i < array.getRows(); i++) {
            for (int j = 0; j < array.getColumns(); j++) {
                assertEquals(0, (int) array.get(i, j));
            }
        }
    }

    @Test
    public void testIterator() {
        Array2D<Integer> array = new Array2D<>(3, 2);
        assertEquals(2, array.getColumns());
        assertEquals(3, array.getRows());
        assertEquals(6, array.size());
        for (int i = 0; i < array.getRows(); i++) {
            for (int j = 0; j < array.getColumns(); j++) {
                array.set(i, j, i * array.getColumns() + j);
            }
        }
        System.out.println(array);

        int j = 0;
        for (int i : array) {
            assertEquals(j++, i);
        }
    }

    @Test
    public void testStringArrayWithPositiveRowsAndColumns() {
        Array2D<String> array = new Array2D<>(2, 3, "empty");
        assertEquals(3, array.getColumns());
        assertEquals(2, array.getRows());
        assertEquals(6, array.size());
        for (int i = 0; i < array.getRows(); i++) {
            for (int j = 0; j < array.getColumns(); j++) {
                assertEquals("empty", array.get(i, j));
            }
        }
    }

    @Test
    public void testCustomClassArrayWithPositiveRowsAndColumns() {
        Array2D<TestElement> array = new Array2D<>(2, 3, new TestElement("pepe"));
        assertEquals(3, array.getColumns());
        assertEquals(2, array.getRows());
        assertEquals(6, array.size());
        for (int i = 0; i < array.getRows(); i++) {
            for (int j = 0; j < array.getColumns(); j++) {
                assertEquals(new TestElement("pepe"), array.get(i, j));
            }
        }

    }

    private static class TestElement {

        private final String identifier;

        TestElement(String identifier) {
            this.identifier = identifier;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestElement that = (TestElement) o;
            return Objects.equals(identifier, that.identifier);
        }

        @Override
        public int hashCode() {
            return Objects.hash(identifier);
        }

        @Override
        public String toString() {
            return identifier;
        }
    }

}