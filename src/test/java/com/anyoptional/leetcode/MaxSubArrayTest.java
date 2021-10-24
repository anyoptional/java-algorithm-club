package com.anyoptional.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class MaxSubArrayTest {

    @Test
    public void test() {
        // 最大子数组[1,3,-1,2]
        assertEquals(5, MaxSubArray.solution(new int[]{-3,1,3,-1,2,-4,2}));
        // 最大子数组[4,-1,2,1]
        assertEquals(6, MaxSubArray.solution(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
        assertEquals(1, MaxSubArray.solution(new int[]{1}));
        assertEquals(23, MaxSubArray.solution(new int[]{5,4,-1,7,8}));
    }
}