package com.liyongquan.slidewindow;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 581. 最短无序连续子数组
 * <p>
 * 给你一个整数数组 nums ，你需要找出一个 连续子数组 ，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。
 * <p>
 * 请你找出符合题意的 最短 子数组，并输出它的长度。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [2,6,4,8,10,9,15]
 * 输出：5
 * 解释：你只需要对 [6, 4, 8, 10, 9] 进行升序排序，那么整个表都会变为升序排序。
 * 示例 2：
 * <p>
 * 输入：nums = [1,2,3,4]
 * 输出：0
 * 示例 3：
 * <p>
 * 输入：nums = [1]
 * 输出：0
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= nums.length <= 104
 * -105 <= nums[i] <= 105
 *  
 * <p>
 * 进阶：你可以设计一个时间复杂度为 O(n) 的解决方案吗？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/shortest-unsorted-continuous-subarray
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class FindUnsortedSubarray {
    /**
     * 先来个暴力解法
     * <p>
     * 时间复杂度O(n^3)
     * 空间复杂度O(1)
     * <p>
     * 超时
     *
     * @param nums
     * @return
     */
    public int findUnsortedSubarray(int[] nums) {
        int len = nums.length;
        if (len == 1) {
            return 0;
        }
        //先找到升序的左边界和右边界
        int l = 1, r = len - 2;
        while (l < len && nums[l] >= nums[l - 1]) {
            l++;
        }
        if (l >= len) {
            return 0;
        }
        while (r >= 0 && nums[r] <= nums[r + 1]) {
            r--;
        }
        if (l > r) {
            r = l;
        }
        int res = len + 1;
        //正确的边界范围只会比[l,r]要大，左边界需要穷举[0,l],右边界需要穷举[r,len-1]，找到满足条件的解
        for (int i = 0; i <= l; i++) {
            for (int j = r; j < len; j++) {
                //求[i,j]的max和min
                int max = -106, min = 106;
                for (int k = i; k <= j; k++) {
                    max = Math.max(max, nums[k]);
                    min = Math.min(min, nums[k]);
                }
                if ((i == 0 || min >= nums[i - 1]) && (j == len - 1 || max <= nums[j + 1])) {
                    res = Math.min(res, j - i + 1);
                }
            }
        }
        return res;
    }

    /**
     * 在暴力的基础上做一些小优化
     * <p>
     * 如何在窗口更新的时候快速更新最大值和最小值(使用两个双端队列来维护)
     * <p>
     * 我们先把左边界定义在0的位置,右边界定义在降序的最后一个节点。这样就能保证两个指针是同向移动的(方便使用双端队列来维护最大值和最小值)
     * <p>
     * 这样可以把时间复杂度降为O(n)
     * <p>
     * 证明？
     *
     * @param nums
     * @return
     */
    public int findUnsortedSubarray2(int[] nums) {
        int len = nums.length;
        if (len == 1) {
            return 0;
        }
        //找到开始的右端点(注意这里是开区间)
        int r = len - 2;
        while (r >= 0 && nums[r] <= nums[r + 1]) {
            r--;
        }
        if (r < 0) {
            return 0;
        }
        int l = 0;
        //双端队列初始化
        Deque<Integer> minQueue = new LinkedList<>(), maxQueue = new LinkedList<>();
        for (int i = 0; i < r; i++) {
            while (!minQueue.isEmpty() && nums[i] < minQueue.peekLast()) {
                minQueue.pollLast();
            }
            minQueue.offerLast(nums[i]);
            while (!maxQueue.isEmpty() && nums[i] > maxQueue.peekLast()) {
                maxQueue.pollLast();
            }
            maxQueue.offerLast(nums[i]);
        }
        //两个指针同向移动(套模板)
        int res = len;
        while (r < len) {
            //右指针移动
            //更新双端队列
            while (!minQueue.isEmpty() && nums[r] < minQueue.peekLast()) {
                minQueue.pollLast();
            }
            minQueue.offerLast(nums[r]);
            while (!maxQueue.isEmpty() && nums[r] > maxQueue.peekLast()) {
                maxQueue.pollLast();
            }
            maxQueue.offerLast(nums[r]);
            r++;
            if (r == len || maxQueue.peekFirst() <= nums[r]) {
                while (l < r && (l == 0 || minQueue.peekFirst() >= nums[l - 1])) {
                    res = Math.min(res, r - l);
                    //更新双端队列
                    if (minQueue.peekFirst() == nums[l]) {
                        minQueue.pollFirst();
                    }
                    if (maxQueue.peekFirst() == nums[l]) {
                        maxQueue.pollFirst();
                    }
                    l++;
                }
            }
        }
        return res;
    }
}
