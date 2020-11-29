package com.anyoptional.datastructures;

import org.junit.Test;

import static org.junit.Assert.*;

public class QueueTest {

    @Test
    public void testEmpty() {
        Queue<Integer> queue = new Queue<>();
        assertNull(queue.peek());
        assertNull(queue.dequeue());
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testOneElement() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(198);
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
        assertEquals(198, (int) queue.peek());
    }

    @Test
    public void testTwoElement() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(198);
        queue.enqueue(36);
        assertFalse(queue.isEmpty());
        assertEquals(2, queue.size());
        assertEquals(198, (int) queue.peek());
        queue.dequeue();
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
        assertEquals(36, (int) queue.peek());
    }

    @Test
    public void testMakeEmpty() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(198);
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
        assertEquals(198, (int) queue.peek());
        queue.enqueue(36);
        assertFalse(queue.isEmpty());
        assertEquals(2, queue.size());
        assertEquals(198, (int) queue.peek());
        queue.dequeue();
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
        assertEquals(36, (int) queue.peek());
        queue.dequeue();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertNull(queue.peek());
    }

}