package com.anyoptional.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;

/**
 * 凑零钱问题:
 *  给你 k 中面值的硬币，面值分别为 c1,c2,...ck，每种硬币的数量无限。
 *  再给一个总金额 amount，问你最少需要几枚硬币凑出这个金额，如果不可
 *  能凑出，返回 -1
 */
public class CoinChange {

    /**
     * 1. base case: amount < 0 时，凑不出来; amount = 0 时不需要凑了
     * 2. 状态：amount，只有 amount 是不断向 base case 靠近的
     * 3. 状态转移：随着选定一个个硬币，需要凑的金额越来越少
     *                          { -1, amount < 0
     * 4. 状态转移方程：f(amount) { 0, amount = 0
     *                         { min(1 + f(amount - c) | c ∈ coins), amount > 0
     *    一般来说函数的参数就是状态转移中的变量，函数的返回值就是要求的最值。
     */
    public static int coinChange(int[] coins, int amount) {
        // dp函数的定义：输入一个金额 n，至少要 dp(n) 枚硬币
        IntFunction<Integer> dp = new IntFunction<Integer>() {
            @Override
            public Integer apply(int n) {
                // base case
                if (n == 0) return 0;
                if (n < 0) return -1;

                int result = Integer.MAX_VALUE;
                // 做选择，也就是状态转移
                for (int c : coins) {
                    int sub = apply(n - c);
                    // 子问题无解
                    if (sub == -1) {
                        continue;
                    }
                    result = Math.min(sub + 1, result);
                }
                return result == Integer.MAX_VALUE ? -1 : result;
            }
        };

        return dp.apply(amount);
    }

    public static int coinChangeTopDown(int[] coins, int amount) {
        if (amount == 0) return 0;
        if (amount < 0) return -1;

        Map<Integer, Integer> memo = new HashMap<>();

        int result = Integer.MAX_VALUE;
        for (int c : coins) {
            Integer sub = memo.get(amount - c);
            if (sub == null) {
                sub = coinChange(coins, amount - c);
                memo.put(amount - c, sub + 1);
            }
            result = Math.min(sub + 1, result);
        }
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    public static int coinChangeDownTop(int[] coins, int amount) {
        // dp数组的定义: 当目标金额为i时，至少需要dp[i]枚硬币才能凑出
        int[] dp = new int[amount + 1];
        // 总额 amount 最多需要 amount 枚硬币即可凑出，对应硬币
        // 全部为1元的情况，初始化为 amount + 1 就可以表示无穷大，方便取最小值
        Arrays.fill(dp, amount + 1);
        // base case !!
        dp[0] = 0;
        // 外层 for 循环遍历所有状态的所有取值
        for (int i = 0; i < dp.length; i++) {
            // 内层 for 循环求所有选择的最小值
            for (int c : coins) {
                // 子问题无解，跳过
                if (i - c < 0) continue;
                dp[i] = Math.min(dp[i], dp[i - c] + 1);
            }
        }
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }

}
