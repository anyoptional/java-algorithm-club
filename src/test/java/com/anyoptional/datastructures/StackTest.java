package com.anyoptional.datastructures;

import org.junit.Test;

import static org.junit.Assert.*;

public class StackTest {

    @Test
    public void testEmpty() {
        Stack<Integer> stack = new Stack<>();
        assertNull(stack.top());
        assertNull(stack.pop());
        assertEquals(0, stack.size());
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testOneElement() {
        Stack<Integer> stack = new Stack<>();
        stack.push(198);
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.size());
        assertEquals(198, (int) stack.top());
    }

    @Test
    public void testTwoElement() {
        Stack<Integer> stack = new Stack<>();
        stack.push(198);
        stack.push(36);
        assertFalse(stack.isEmpty());
        assertEquals(2, stack.size());
        assertEquals(36, (int) stack.top());
        stack.pop();
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.size());
        assertEquals(198, (int) stack.top());
    }

    @Test
    public void testMakeEmpty() {
        Stack<Integer> stack = new Stack<>();
        stack.push(198);
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.size());
        assertEquals(198, (int) stack.top());
        stack.push(36);
        assertFalse(stack.isEmpty());
        assertEquals(2, stack.size());
        assertEquals(36, (int) stack.top());
        stack.pop();
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.size());
        assertEquals(198, (int) stack.top());
        stack.pop();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
        assertNull(stack.top());
    }

}