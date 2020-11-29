package com.anyoptional.datastructures;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class HeapTest {

    @Test
    public void testInsertAsc() {
       // [ 10, 7, 5, 2, 1 ]
        Heap<Integer> heap = new Heap<>(Comparator.comparingInt($0 -> $0));
        heap.insert(2);
        assertFalse(heap.isEmpty());
        assertEquals(1, heap.size());
        assertEquals(2, (int) heap.peek());
        heap.insert(10);
        assertEquals(10, (int) heap.peek());
        heap.insert(5);
        assertEquals(10, (int) heap.peek());
        heap.insert(7);
        assertEquals(10, (int) heap.peek());
        heap.insert(1);
        assertEquals(10, (int) heap.peek());
    }

    @Test
    public void testInsertDesc() {
        // [ 10, 7, 5, 2, 1 ]
        Heap<Integer> heap = new Heap<>(($0, $1) -> $1 - $0);
        heap.insert(2);
        assertFalse(heap.isEmpty());
        assertEquals(1, heap.size());
        assertEquals(2, (int) heap.peek());
        System.out.println(heap); // 2
        heap.insert(10);
        assertEquals(2, (int) heap.peek());
        System.out.println(heap); // 2 10
        heap.insert(5);
        assertEquals(2, (int) heap.peek());
        System.out.println(heap); // 2 10 5
        heap.insert(7);
        assertEquals(2, (int) heap.peek());
        System.out.println(heap); // 2 7 5 10
        heap.insert(1);
        assertEquals(1, (int) heap.peek());
        System.out.println(heap); // 1 2 5 10 7
    }



}