package com.anyoptional.leetcode;

/**
 * 最大子数组问题
 *
 * 问题描述：
 *  输入一个整数数组nums，请你在其中找到一个和最大的子数组，
 *  返回这个子数组的和
 *
 * 注意：子数组是连续的，子序列可以不连续。
 */
public class MaxSubArray {

    public static int solution(int[] nums) {
        if (nums == null ||
                nums.length == 0)
            return 0;
        if (nums.length == 1) return nums[0];

        int len = nums.length;

        // dp含义：以nums[i]结尾的“最大子数组和”为dp[i]
        int[] dp = new int[len];
        // base case: 第一个元素前面没有子数组
        dp[0] = nums[0];

        for (int i = 1; i < len; i++) {
            // dp[i] 有两种选择：
            // 1. 与前面的相邻子数组合并
            // 2. 自成一派，自己作为一个子数组
            // 既然是求“最大子数组和”，自然要选择最大的
            dp[i] = Math.max(nums[i], nums[i] + dp[i - 1]);
        }

        // 在这个dp定义下，返回dp中的最大者
        int result = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            result = Math.max(result, dp[i]);
        }
        return result;
    }

}
