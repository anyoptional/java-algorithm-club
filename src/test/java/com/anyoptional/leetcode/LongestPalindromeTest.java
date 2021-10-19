package com.anyoptional.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class LongestPalindromeTest {

    @Test
    public void test() {
        assertEquals("a", LongestPalindrome.solution("a"));
        assertEquals("aba", LongestPalindrome.solution("aba"));
        assertEquals("a", LongestPalindrome.solution("abc"));
        assertEquals("bb", LongestPalindrome.solution("abbd"));
        assertEquals("abcba", LongestPalindrome.solution("abcba"));
    }

}