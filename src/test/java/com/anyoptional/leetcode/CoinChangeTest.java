package com.anyoptional.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class CoinChangeTest {

    @Test
    public void testCoinChange() {
        assertEquals(3, CoinChange.coinChange(new int[]{1, 2, 5}, 11));
    }

    @Test
    public void testCoinChangeTopDown() {
        assertEquals(3, CoinChange.coinChangeTopDown(new int[]{1, 2, 5}, 11));
    }

    @Test
    public void testCoinChangeDownTop() {
        assertEquals(3, CoinChange.coinChangeDownTop(new int[]{1, 2, 5}, 11));
    }

}