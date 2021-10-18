package com.anyoptional.leetcode;

import java.util.Arrays;

/**
 * 斐波那契数列
 *
 * 自顶向下，一般是递归形式，表现为从一个较大规模的原问题，比如 fib(8)，向下
 * 逐层分解规模，直至 fib(1)、fib(2) 这两个 base case，然后逐层返回答案。
 *
 * 自底向上，一般是迭代形式，表现为从最下面、最简单、问题规模最小的 fib(1)、fib(2)
 * 往上推，直到推到我们想要的答案，这就是动态规划的思路。
 *
 * 状态转移方程，描述问题结构的数学形式。
 *  将 fib(n) 想作一个状态 n，这个状态 n 是由状态 n-1 和状态 n-2 相加转移而来，这就叫状态转移。
 *
 *         { 1, n = 1、2
 *  fib(n) {
 *         { f(n-1) + f(n-2), n > 2
 *
 *  可以看到，状态转移方程直接对应着蛮力解法。
 */
public class Fib {

    /**
     * 递归算法注意画一下递归树
     *
     * 递归算法的时间复杂度 = 子问题的个数（递归树中节点的个数） x 解决一个子问题需要的时间
     *
     * 对于本例，递归树中节点总数 2^n；解决子问题需要 fib(n - 1) + fib(n - 2) -> O(1)，常数时间，
     * 因此时间复杂度为O(2^n)
     */
    public static int fib(int n) {
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;
        // 存在大量重叠子问题
        // 效率极其低下
        return fib(n - 1) + fib(n - 2);
    }



    /**
     * 自顶向下
     *
     * 带备忘录的递归，就是把一颗存在大量冗余的递归树通过`剪枝`去除冗余，
     * 这样做可以极大减少子问题的数量
     *
     * 本例中，剪枝以后，节点个数和输入规模 n 成正比，因此时间复杂度为 O(n)
     */
    public static int fibOptimalTopDown(int n) {
        if (n == 0) return 0;
        // 使用一个备忘录
        int[] memo = new int[n + 1];
        // 默认初始化为 0
        Arrays.fill(memo, 0);
        return fibOptimalTopDown0(n, memo);
    }

    private static int fibOptimalTopDown0(int n, int[] memo) {
        // 递归基
        if (n == 1 || n == 2) return 1;
        // 首先检查备忘录
        if (memo[n] != 0) return memo[n];
        // 备忘录没有则进行计算，然后缓存起来
        memo[n] = fibOptimalTopDown0(n - 1, memo) + fibOptimalTopDown0(n - 2, memo);
        return memo[n];
    }



    /**
     * 自底向上
     */
    public static int fibOptimalDownTop(int n) {
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;
        int[] memo = new int[n + 1];
        Arrays.fill(memo, 0);
        // base case
        memo[1] = memo[2] = 1;
        // 自底向上，逐个相加
        for (int i = 3; i <= n; i++) {
            memo[i] = memo[i - 1] + memo[i - 2];
        }
        return memo[n];
    }



    /**
     * 自底向上，空间压缩
     */
    public static int fibOptimalDownTopCompressed(int n) {
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;
        int prev = 1, cur = 1;
        for (int i = 3; i <= n; i++) {
           int sum = prev + cur;
           prev = cur;
           cur = sum;
        }
        return cur;
    }

}
