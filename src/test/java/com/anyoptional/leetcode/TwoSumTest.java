package com.anyoptional.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class TwoSumTest {

    @Test
    public void test() {
        assertArrayEquals(new int[]{1, 2}, TwoSum.solution(new int[]{3, 2, 4}, 6));
    }

}