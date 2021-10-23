package com.anyoptional.leetcode;

import java.util.Arrays;

/**
 * 最长递增子序列
 *
 * 输入一个无序整数数组，请你找到其中最长递增子序列的长度
 *
 * 示例：
 *  输入 nums = [10,9,2,5,3,7,101,18]，其中最长的递增子序列是[2,3,7,11]，
 *  所以算法输出 4
 */
public class LongestIncreasingSubsequence {

    public static int solution(int[] nums) {
        int len = nums.length;
        if (len == 0) return 0;
        if (len == 1) return 1;

        // dp含义：以nums[i]结尾的子序列的LIS
        int[] dp = new int[len];
        // base case: LIS至少包含自身
        Arrays.fill(dp, 1);

        // 假设dp[0...i-1]已知，如何推导出dp[i]?

        // 外层循环遍历dp数组
        for (int i = 0; i < len; i++) {
            // 内层循环求dp数组每个元素的值
            for (int j = 0; j < i; j++) {
                // 既然是递增子序列，只要找到前面那些
                // 结尾比3小的子序列，然后把nums[i]接到后面
                if (nums[i] > nums[j]) {
                    // 不过可能有很多个这样的子序列，只能选最长的
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        // LIS 应该是dp数组中的最大值
        int result = 0;
        for (int i = 0; i < len; i++) {
            result = Math.max(result, dp[i]);
        }
        return result;
    }

}
