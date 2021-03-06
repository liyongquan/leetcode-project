package com.liyongquan.weeklycontest.wc240;

import java.util.Deque;
import java.util.LinkedList;

public class MaxSumMinProduct {
    /**
     * 看题目的数据量应该用滑窗，但是没想好如何证明，先暴力
     * <p>
     * 超时
     *
     * @param nums
     * @return
     */
    public int maxSumMinProduct(int[] nums) {
        int len = nums.length;
        long[] preSum = new long[len + 1];
        for (int i = 0; i < len; i++) {
            preSum[i + 1] = preSum[i] + nums[i];
        }
        long res = 0;
        for (int i = 0; i < len; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = i; j < len; j++) {
                min = Math.min(min, nums[j]);
                long r = (preSum[j + 1] - preSum[i]) * min;
                res = Math.max(res, r);
            }
        }
        return (int) (res % 1000000007);
    }

    /**
     * 还是超时
     *
     * @param nums
     * @return
     */
    public int maxSumMinProduct2(int[] nums) {
        int len = nums.length;
        //前缀和
        long[] preSum = new long[len + 1];
        for (int i = 0; i < len; i++) {
            preSum[i + 1] = preSum[i] + nums[i];
        }
        long res = 0;
        int idx = 0;
        while (idx < len) {
            int l = idx - 1, r = idx + 1;
            while (l >= 0 && nums[l] >= nums[idx]) {
                l--;
            }
            while (r < len && nums[r] >= nums[idx]) {
                r++;
            }
            long c = (preSum[r] - preSum[l + 1]) * nums[idx];
            res = Math.max(res, c);
            idx++;
        }
        return (int) (res % 1000000007);
    }

    public int maxSumMinProduct3(int[] nums) {
        int len = nums.length;
        //前缀和
        long[] preSum = new long[len + 1];
        for (int i = 0; i < len; i++) {
            preSum[i + 1] = preSum[i] + nums[i];
        }
        //求下一个更小的值的下标(单调栈)
        int[] right = new int[len], left = new int[len];
        Deque<Integer> deque = new LinkedList<>();
        for (int i = len - 1; i >= 0; i--) {
            while (!deque.isEmpty() && nums[deque.peekFirst()] >= nums[i]) {
                deque.pollFirst();
            }
            right[i] = deque.isEmpty() ? -1 : deque.peekFirst();
            deque.offerFirst(i);
        }
        deque = new LinkedList<>();
        for (int i = 0; i < len; i++) {
            while (!deque.isEmpty() && nums[deque.peekFirst()] >= nums[i]) {
                deque.pollFirst();
            }
            left[i] = deque.isEmpty() ? -1 : deque.peekFirst();
            deque.offerFirst(i);
        }
        long res = 0;
        int idx = 0;
        while (idx < len) {
            int r = right[idx] == -1 ? len : right[idx];
            int l = left[idx] == -1 ? 0 : (left[idx] + 1);
            long c = (preSum[r] - preSum[l]) * nums[idx];
            res = Math.max(res, c);
            idx++;
        }
        return (int) (res % 1000000007);
    }
}
