package com.anyoptional.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class LongestIncreasingSubsequenceTest {

    @Test
    public void test() {
        assertEquals(4, LongestIncreasingSubsequence.solution(new int[]{10,9,2,5,3,7,101,18}));
        assertEquals(4, LongestIncreasingSubsequence.solution(new int[]{0,1,0,3,2,3}));
        assertEquals(1, LongestIncreasingSubsequence.solution(new int[]{7,7,7,7,7,7,7}));
        assertEquals(3, LongestIncreasingSubsequence.solution(new int[]{4,10,4,3,8,9}));
    }

}