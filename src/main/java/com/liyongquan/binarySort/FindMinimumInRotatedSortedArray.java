package com.liyongquan.binarySort;

//已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。例如，原数组 nums = [0,1,2,4,5,6,7] 在变
//化后可能得到：
//
// 若旋转 4 次，则可以得到 [4,5,6,7,0,1,2]
// 若旋转 4 次，则可以得到 [0,1,2,4,5,6,7]
//
//
// 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2],
//..., a[n-2]] 。
//
// 给你一个元素值 互不相同 的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素 。
//
//
//
// 示例 1：
//
//
//输入：nums = [3,4,5,1,2]
//输出：1
//解释：原数组为 [1,2,3,4,5] ，旋转 3 次得到输入数组。
//
//
// 示例 2：
//
//
//输入：nums = [4,5,6,7,0,1,2]
//输出：0
//解释：原数组为 [0,1,2,4,5,6,7] ，旋转 4 次得到输入数组。
//
//
// 示例 3：
//
//
//输入：nums = [11,13,15,17]
//输出：11
//解释：原数组为 [11,13,15,17] ，旋转 4 次得到输入数组。
//
//
//
//
// 提示：
//
//
// n == nums.length
// 1 <= n <= 5000
// -5000 <= nums[i] <= 5000
// nums 中的所有整数 互不相同
// nums 原来是一个升序排序的数组，并进行了 1 至 n 次旋转
//
// Related Topics 数组 二分查找
// 👍 414 👎 0


public class FindMinimumInRotatedSortedArray {
    /**
     * 二分查找
     *
     * @param nums
     * @return
     */
    public int findMin(int[] nums) {
        int len = nums.length;
        int l = 0, r = len - 1;
        while (l < r) {
            if (r - l == 1) {
                return Math.min(nums[l], nums[r]);
            }
            int mid = l + (r - l) / 2;
            //左边是严格升序
            if (nums[mid] > nums[l]) {
                //右侧小于左侧,则最小值在右边
                if (nums[r] < nums[mid]) {
                    l = mid + 1;
                } else {
                    //严格升序
                    return nums[l];
                }
            } else {
                //右侧严格升序
                //这里是由于下一次的mid一定在r的左侧，区间一定会继续缩小，这里不会导致死循环
                r = mid;
            }
        }
        return nums[l];
    }

    //比较最右边端点其实会更简单
    public int findMin2(int[] nums) {
        int len = nums.length;
        int l = 0, r = len - 1;
        while (l < r) {
            int mid = l + (r - l) / 2;
            //左边是严格升序，最小值在右侧
            if (nums[mid] > nums[r]) {
                l = mid + 1;
            } else {
                //右侧严格升序
                //这里是由于下一次的mid一定在r的左侧，区间一定会继续缩小，这里不会导致死循环
                r = mid;
            }
        }
        return nums[l];
    }
}
