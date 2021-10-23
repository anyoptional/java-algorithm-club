package com.anyoptional.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class MaxEnvelopesTest {

    @Test
    public void test() {
        assertEquals(1, MaxEnvelopes.solution(new int[][]{{1,1},{1,1},{1,1},{1,1}}));
        assertEquals(3, MaxEnvelopes.solution(new int[][]{{5,4},{6,4},{6,7},{2,3}}));
    }

}