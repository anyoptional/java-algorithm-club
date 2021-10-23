package com.anyoptional.leetcode;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 二维递增子序列：信封嵌套问题
 * <p>
 * 很多算法问题都需要排序技巧，其难点不在于排序本身，而是需要
 * 巧妙地进行预处理，将算法问题进行转换，为之后的操作打下基础。
 * <p>
 * 问题描述：
 * 给出一些信封，每个信封用宽度和高度的整数对形式 (w, h) 表示。
 * 当一个信封A的宽度和高度都比另一个信封B大的时候，则B就可以放进A
 * 里。请计算最多有多少个信封能组成一组“俄罗斯套娃”信封（即最多能
 * 套几层）
 */
public class MaxEnvelopes {

    public static int solution(int[][] envelopes) {
        if (envelopes == null || envelopes.length == 0) {
            return 0;
        }

        // 进行排序，转换成 LIS 问题
        Arrays.sort(envelopes,
                // 先按照 w 进行升序排列
                // w 相同时再按照 h 进行降序排列
                // 如此，这些逆序的 h 中最多只有
                // 一个会被选择，从而保证了最终结
                // 果中不会出现 w 相同的情况
                (lhs, rhs) -> lhs[0] == rhs[0] ?
                        rhs[1] - lhs[1] :
                        lhs[0] - rhs[0]);
        // 对高度数组寻找 LIS
        int[] height = new int[envelopes.length];
        for (int i = 0; i < height.length; i++) {
            height[i] = envelopes[i][1];
        }

        return LongestIncreasingSubsequence.solution(height);
    }

}
