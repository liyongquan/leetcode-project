package com.liyongquan.dp;

import lombok.extern.slf4j.Slf4j;

/**
 * 1473. 粉刷房子 III
 * <p>
 * 在一个小城市里，有 m 个房子排成一排，你需要给每个房子涂上 n 种颜色之一（颜色编号为 1 到 n ）。有的房子去年夏天已经涂过颜色了，所以这些房子不需要被重新涂色。
 * <p>
 * 我们将连续相同颜色尽可能多的房子称为一个街区。（比方说 houses = [1,2,2,3,3,2,1,1] ，它包含 5 个街区  [{1}, {2,2}, {3,3}, {2}, {1,1}] 。）
 * <p>
 * 给你一个数组 houses ，一个 m * n 的矩阵 cost 和一个整数 target ，其中：
 * <p>
 * houses[i]：是第 i 个房子的颜色，0 表示这个房子还没有被涂色。
 * cost[i][j]：是将第 i 个房子涂成颜色 j+1 的花费。
 * 请你返回房子涂色方案的最小总花费，使得每个房子都被涂色后，恰好组成 target 个街区。如果没有可用的涂色方案，请返回 -1 。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：houses = [0,0,0,0,0], cost = [[1,10],[10,1],[10,1],[1,10],[5,1]], m = 5, n = 2, target = 3
 * 输出：9
 * 解释：房子涂色方案为 [1,2,2,1,1]
 * 此方案包含 target = 3 个街区，分别是 [{1}, {2,2}, {1,1}]。
 * 涂色的总花费为 (1 + 1 + 1 + 1 + 5) = 9。
 * 示例 2：
 * <p>
 * 输入：houses = [0,2,1,2,0], cost = [[1,10],[10,1],[10,1],[1,10],[5,1]], m = 5, n = 2, target = 3
 * 输出：11
 * 解释：有的房子已经被涂色了，在此基础上涂色方案为 [2,2,1,2,2]
 * 此方案包含 target = 3 个街区，分别是 [{2,2}, {1}, {2,2}]。
 * 给第一个和最后一个房子涂色的花费为 (10 + 1) = 11。
 * 示例 3：
 * <p>
 * 输入：houses = [0,0,0,0,0], cost = [[1,10],[10,1],[1,10],[10,1],[1,10]], m = 5, n = 2, target = 5
 * 输出：5
 * 示例 4：
 * <p>
 * 输入：houses = [3,1,2,3], cost = [[1,1,1],[1,1,1],[1,1,1],[1,1,1]], m = 4, n = 3, target = 3
 * 输出：-1
 * 解释：房子已经被涂色并组成了 4 个街区，分别是 [{3},{1},{2},{3}] ，无法形成 target = 3 个街区。
 *  
 * <p>
 * 提示：
 * <p>
 * m == houses.length == cost.length
 * n == cost[i].length
 * 1 <= m <= 100
 * 1 <= n <= 20
 * 1 <= target <= m
 * 0 <= houses[i] <= n
 * 1 <= cost[i][j] <= 10^4
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/paint-house-iii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
@Slf4j
public class PaintHouse3 {
    /**
     * 回溯解法
     * <p>
     * 超时
     *
     * @param houses
     * @param cost
     * @param m
     * @param n
     * @param target
     * @return
     */
    public int minCost(int[] houses, int[][] cost, int m, int n, int target) {
        return backtrace(houses, cost, m, n, target, -1, 0);
    }

    private int backtrace(int[] houses, int[][] cost, int m, int n, int target, int pre, int idx) {
        if (idx == m) {
            return target == 0 ? 0 : -1;
        }
        if (houses[idx] != 0) {
            if (pre == houses[idx]) {
                return backtrace(houses, cost, m, n, target, houses[idx], idx + 1);
            } else {
                return backtrace(houses, cost, m, n, target - 1, houses[idx], idx + 1);
            }
        }
        int min = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            if (pre == i) {
                int sub = backtrace(houses, cost, m, n, target, i, idx + 1);
                if (sub != -1) {
                    min = Math.min(min, sub + cost[idx][i - 1]);
                }
            } else {
                int sub = backtrace(houses, cost, m, n, target - 1, i, idx + 1);
                if (sub != -1) {
                    min = Math.min(min, sub + cost[idx][i - 1]);
                }
            }
        }
        return min == Integer.MAX_VALUE ? -1 : min;
    }

    /**
     * dp解法，从上面演变过来
     * <p>
     * 时间复杂度O(m*n^2*target)
     *
     * 好复杂，哭
     *
     * @param houses
     * @param cost
     * @param m
     * @param n
     * @param target
     * @return
     */
    public int minCost2(int[] houses, int[][] cost, int m, int n, int target) {
        int[][][] dp = new int[m + 1][target + 1][n];
        //初始化 m=0
        for (int i = 0; i <= target; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0) {
                    dp[0][i][j] = 0;
                } else {
                    dp[0][i][j] = -1;
                }
            }
        }
        //初始化 target=0;
        for (int i = 1; i <= m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][0][j] = -1;
            }
        }
        //dp迭代
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= target; j++) {
                for (int k = 0; k < n; k++) {
                    if (i < j) {
                        //适当剪枝
                        dp[i][j][k] = -1;
                    } else if (houses[i - 1] != 0) {
                        //不需要填色
                        if (houses[i - 1] != k + 1) {
                            dp[i][j][k] = -1;
                        } else {
                            int min = Integer.MAX_VALUE;
                            for (int l = 0; l < n; l++) {
                                //跟上一个颜色相同的场景
                                if (l == k) {
                                    if (dp[i - 1][j][l] != -1) {
                                        min = Math.min(min, dp[i - 1][j][l]);
                                    }
                                } else {
                                    //跟上一个颜色不同的场景
                                    if (dp[i - 1][j - 1][l] != -1) {
                                        min = Math.min(min, dp[i - 1][j - 1][l]);
                                    }
                                }
                            }
                            dp[i][j][k] = min == Integer.MAX_VALUE ? -1 : min;
                        }
                    } else {
                        int min = Integer.MAX_VALUE;
                        //需要填色
                        for (int l = 0; l < n; l++) {
                            //跟上一个颜色相同的场景
                            if (l == k) {
                                if (dp[i - 1][j][l] != -1) {
                                    min = Math.min(min, dp[i - 1][j][l] + cost[i - 1][k]);
                                }
                            } else {
                                //跟上一个颜色不同的场景
                                if (dp[i - 1][j - 1][l] != -1) {
                                    min = Math.min(min, dp[i - 1][j - 1][l] + cost[i - 1][k]);
                                }
                            }
                        }
                        dp[i][j][k] = min == Integer.MAX_VALUE ? -1 : min;
                    }
                    //log.info("dp[{}][{}][{}]={}", i, j, k, dp[i][j][k]);
                }
            }
        }
        //计算最小值
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (dp[m][target][i] != -1) {
                min = Math.min(min, dp[m][target][i]);
            }
        }
        return min == Integer.MAX_VALUE ? -1 : min;
    }
}
