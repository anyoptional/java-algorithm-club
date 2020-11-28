package com.anyoptional.datastructures;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class Array2DTest {

    @Test
    public void testArrayWithoutInitialValue() {
        Array2D<Integer> array = new Array2D<>(3, 2);
        assertEquals(3, array.getColumns());
        assertEquals(2, array.getRows());
        assertNull(array.get(2, 1));
    }

    @Test
    public void testIntegerArrayWithPositiveRowsAndColumns() {
        Array2D<Integer> array = new Array2D<>(3, 2, 0);
        assertEquals(3, array.getColumns());
        assertEquals(2, array.getRows());
        assertEquals(0, (int) array.get(2, 0));
    }

    @Test
    public void testStringArrayWithPositiveRowsAndColumns() {
        Array2D<String> array = new Array2D<>(3, 2, "empty");
        assertEquals(3, array.getColumns());
        assertEquals(2, array.getRows());
        assertEquals("empty", array.get(2, 0));
    }

    @Test
    public void testCustomClassArrayWithPositiveRowsAndColumns() {
        Array2D<TestElement> array = new Array2D<>(3, 2, new TestElement("pepe"));
        assertEquals(3, array.getColumns());
        assertEquals(2, array.getRows());
        assertEquals(new TestElement("pepe"), array.get(2, 1));
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