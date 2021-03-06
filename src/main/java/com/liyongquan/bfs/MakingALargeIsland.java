package com.liyongquan.bfs;

import java.util.*;

//827. 最大人工岛
// 给你一个大小为 n x n 二进制矩阵 grid 。最多 只能将一格 0 变成 1 。
//
//返回执行此操作后，grid 中最大的岛屿面积是多少？
//
//岛屿 由一组上、下、左、右四个方向相连的 1 形成。
//
// 
//
//示例 1:
//
//输入: grid = [[1, 0], [0, 1]]
//输出: 3
//解释: 将一格0变成1，最终连通两个小岛得到面积为 3 的岛屿。
//示例 2:
//
//输入: grid = [[1, 1], [1, 0]]
//输出: 4
//解释: 将一格0变成1，岛屿的面积扩大为 4。
//示例 3:
//
//输入: grid = [[1, 1], [1, 1]]
//输出: 4
//解释: 没有0可以让我们变成1，面积依然为 4。
// 
//
//提示：
//
//n == grid.length
//n == grid[i].length
//1 <= n <= 500
//grid[i][j] 为 0 或 1
//
//来源：力扣（LeetCode）
//链接：https://leetcode-cn.com/problems/making-a-large-island
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
public class MakingALargeIsland {
    public static final int[][] DIR = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1},
    };

    /**
     * bfs
     * <p>
     * 时间复杂度O(n^2)
     * <p>
     * 超时
     *
     * @param grid
     * @return
     */
    public int largestIsland(int[][] grid) {
        int len = grid.length;
        int max = bfs(grid, len);
        //更新一个格子的场景
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (grid[i][j] == 0) {
                    grid[i][j] = 1;
                    int bfs = bfs(grid, len);
                    max = Math.max(max, bfs);
                    grid[i][j] = 0;
                }
            }
        }
        return max;
    }

    private int bfs(int[][] grid, int n) {
        int max = 0;
        int[][] visit = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0 || visit[i][j] == 1) {
                    continue;
                }
                Queue<int[]> queue = new LinkedList<>();
                queue.add(new int[]{i, j});
                visit[i][j] = 1;
                int cnt = 1;
                while (!queue.isEmpty()) {
                    int[] poll = queue.poll();
                    for (int[] dir : DIR) {
                        int nx = poll[0] + dir[0], ny = poll[1] + dir[1];
                        if (nx >= 0 && nx < n && ny >= 0 && ny < n && visit[nx][ny] == 0 && grid[nx][ny] == 1) {
                            queue.add(new int[]{nx, ny});
                            visit[nx][ny] = 1;
                            cnt++;
                        }
                    }
                }
                max = Math.max(max, cnt);
            }
        }
        return max;
    }

    /**
     * 标记每个连通分量的编号，然后再尝试增加一个陆地点，统计连通分量的总大小
     *
     * @param grid
     * @return
     */
    public int largestIsland2(int[][] grid) {
        int len = grid.length;
        Map<Integer, Integer> mark = mark(grid, len);
        //计算标记的最大值
        int max = 0;
        for (Integer value : mark.values()) {
            max = Math.max(max, value);
        }
        //更新一个格子的场景
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (grid[i][j] == 0) {
                    //查找相连的连通图进行合并
                    Set<Integer> set = new HashSet<>();
                    int sum = 1;
                    for (int[] dir : DIR) {
                        int nx = i + dir[0], ny = j + dir[1];
                        if (nx >= 0 && nx < len && ny >= 0 && ny < len
                                && grid[nx][ny] > 0 && !set.contains(grid[nx][ny])) {
                            set.add(grid[nx][ny]);
                            sum += mark.get(grid[nx][ny]);
                        }
                    }
                    max = Math.max(max, sum);
                }
            }
        }
        return max;
    }

    /**
     * 标记连通图-bfs
     *
     * @param grid
     * @param n
     * @return
     */
    private Map<Integer, Integer> mark(int[][] grid, int n) {
        Map<Integer, Integer> map = new HashMap<>();
        int idx = 2;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    Queue<int[]> queue = new LinkedList<>();
                    queue.add(new int[]{i, j});
                    grid[i][j] = idx;
                    int cnt = 1;
                    while (!queue.isEmpty()) {
                        int[] poll = queue.poll();
                        for (int[] dir : DIR) {
                            int nx = poll[0] + dir[0], ny = poll[1] + dir[1];
                            if (nx >= 0 && nx < n && ny >= 0 && ny < n && grid[nx][ny] == 1) {
                                queue.add(new int[]{nx, ny});
                                grid[nx][ny] = idx;
                                cnt++;
                            }
                        }
                    }
                    map.put(idx, cnt);
                    idx++;
                }
            }
        }
        return map;
    }
}
