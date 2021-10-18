package com.anyoptional.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class FibTest {

    @Test
    public void testFib() {
        int a = Fib.fib(3);
        int b = Fib.fib(4);
        int c = Fib.fib(5);
        int d = Fib.fib(6);

        assertEquals(2, a);
        assertEquals(3, b);
        assertEquals(5, c);
        assertEquals(8, d);
    }

    @Test
    public void testFibOptimalTopDown() {
        int a = Fib.fibOptimalTopDown(3);
        int b = Fib.fibOptimalTopDown(4);
        int c = Fib.fibOptimalTopDown(5);
        int d = Fib.fibOptimalTopDown(6);

        assertEquals(2, a);
        assertEquals(3, b);
        assertEquals(5, c);
        assertEquals(8, d);
    }

    @Test
    public void testFibOptimalDownTop() {
        int a = Fib.fibOptimalDownTop(3);
        int b = Fib.fibOptimalDownTop(4);
        int c = Fib.fibOptimalDownTop(5);
        int d = Fib.fibOptimalDownTop(6);

        assertEquals(2, a);
        assertEquals(3, b);
        assertEquals(5, c);
        assertEquals(8, d);
    }

    @Test
    public void testFibOptimalDownTopCompressed() {
        int a = Fib.fibOptimalDownTopCompressed(3);
        int b = Fib.fibOptimalDownTopCompressed(4);
        int c = Fib.fibOptimalDownTopCompressed(5);
        int d = Fib.fibOptimalDownTopCompressed(6);

        assertEquals(2, a);
        assertEquals(3, b);
        assertEquals(5, c);
        assertEquals(8, d);
    }

}