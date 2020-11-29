package com.anyoptional.datastructures;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SortedArrayTest {

    @Test
    public void testAdd() {
        SortedArray<Integer> sortedArray = new SortedArray<>(Arrays.asList(3, 1, 7));
        assertEquals(sortedArray.size(), 3);
        assertEquals(2, sortedArray.add(4)); // 1 3 4 7
        assertEquals(0, sortedArray.add(0)); // 0 1 3 4 7
        assertEquals(5, sortedArray.add(8)); // 0 1 3 4 7 8
        assertEquals(4, sortedArray.add(6)); // 0 1 3 4 6 7 8
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