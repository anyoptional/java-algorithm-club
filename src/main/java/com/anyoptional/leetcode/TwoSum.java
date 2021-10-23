package com.anyoptional.leetcode;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {

    public static int[] solution(int[] nums, int target) {
        if (nums == null || nums.length < 2) return new int[]{};

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            int remaining = target - nums[i];
            Integer idx = map.get(remaining);
            if (idx != null && idx != i) {
                return new int[]{i, idx};
            }
        }
        return new int[]{};
    }
    
}
