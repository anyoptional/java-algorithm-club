package com.anyoptional.leetcode;

/**
 * 最长回文子串
 *
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 *
 * 示例：
 *
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 */
public class LongestPalindrome {

    /**
     * 1 <= s.length <= 1000
     * s 仅由数字和英文字母（大写和/或小写）组成
     */
    public static String solution(String s) {
        int len = s.length();
        // 只有一个字符的情况
        if (len < 2) {
            return s;
        }

        // 假设 s[i,j]是回文串，那么s[i+1, j-1]也必然是回文串
        // 也就是说，回文串去掉首尾两个字符后，仍然是回文串，比如 xabcx -> abc
        // 对任意的s[i,j]，它可能是回文串，也可能不是。如果不是，那么
        // 1. s[i,j]本身不是回文串
        // 2. i 大于 j，构造的子串不合法
        // 按照这个思路，最长回文子串就是符合s[i,j]为回文串，且j-i+1最大的那一个

        // dp数组的含义：dp[i,j]表示s[i,j]是不是回文子串
        boolean[][] dp = new boolean[len][len];
        // base case: 长度为 1 的都是回文串
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }

        int maxLen = 1;
        int begin = 0;
        char[] chars = s.toCharArray();

        // 外层循环 dp 数组表示的含义出发，也就是枚举子串长度
        for (int l = 2; l <= len; l++) {
            // 内层循环枚举子串左边界
            for (int i = 0; i < len; i++) {
                // 计算右边界
                // 由 l 和 i 可以确定右边界，即 j - i + 1 = l 得
                int j = l + i - 1;
                // 右边界越界
                if (j >= len) break;

                // 左、右边界字符不同，肯定不是回文串
                if (chars[i] != chars[j]) {
                    dp[i][j] = false;
                } else {
                    if (j - i <= 2) {
                        // 只有1个或2个字符
                        // 而这两个字符又相同
                        dp[i][j] = true;
                    } else {
                        // 否则的话要看去掉首尾字符后的子串是不是回文串
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }

                // 只要 dp[i][j] == true 成立，就表示子串 s[i,j] 是回文
                // 此时记录回文长度和起始位置
                if (dp[i][j] && j - i + 1 > maxLen) {
                    begin = i;
                    maxLen = j - i + 1;
                }
            }
        }

        return s.substring(begin, begin + maxLen);
    }

}
