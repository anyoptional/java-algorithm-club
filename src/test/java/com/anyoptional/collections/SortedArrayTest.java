package com.anyoptional.collections;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class SortedArrayTest {

    @Test
    public void testToArray() {
        SortedArray<Integer> s0 = new SortedArray<>();
        s0.addAll(Arrays.asList(3, 1, 7));
        Integer[] a0 = {1, 3, 7};
        assertArrayEquals(a0, s0.toArray(new Integer[0]));

        SortedArray<String> s1 = new SortedArray<>();
        s1.addAll(Arrays.asList("b", "c", "a"));
        String[] a1 = {"a", "b", "c"};
        assertArrayEquals(a1, s1.toArray(new String[0]));
    }

    @Test
    public void testAdd() {
        SortedArray<Integer> sortedArray = new SortedArray<>();
        sortedArray.addAll(Arrays.asList(3, 1, 7));
        assertEquals(sortedArray.size(), 3);
        assertEquals(2, sortedArray.insert(4)); // 1 3 4 7
        assertEquals(0, sortedArray.insert(0)); // 0 1 3 4 7
        assertEquals(5, sortedArray.insert(8)); // 0 1 3 4 7 8
        assertEquals(4, sortedArray.insert(6)); // 0 1 3 4 6 7 8
    }

    @Test
    public void testRandomAdd() {
        SortedArray<Integer> sortedArray = new SortedArray<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            sortedArray.add(random.nextInt(10000));
            assertTrue(isSorted(sortedArray));
        }
    }

    @Test
    public void testRandomRemove() {
        SortedArray<Integer> sortedArray = new SortedArray<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            sortedArray.add(random.nextInt(500));
            assertTrue(isSorted(sortedArray));
        }

        for (int i = 0; i < 10000; i++) {
            sortedArray.remove(random.nextInt(500));
            assertTrue(isSorted(sortedArray));
        }
    }

    private <E extends Comparable<E>> boolean isSorted(SortedArray<E> sortedArray) {
        for (int i = 0; i < sortedArray.size(); i++) {
            for (int j = i + 1; j < sortedArray.size(); j++) {
                if (sortedArray.get(i).compareTo(sortedArray.get(j)) > 0) {
                    return false;
                }
            }
        }
        return true;
    }

}