package com.liyongquan.backtrack;

import lombok.extern.slf4j.Slf4j;

/**
 * //由空地和墙组成的迷宫中有一个球。球可以向上下左右四个方向滚动，但在遇到墙壁前不会停止滚动。当球停下时，可以选择下一个方向。
 * //
 * // 给定球的起始位置，目的地和迷宫，判断球能否在目的地停下。
 * //
 * // 迷宫由一个0和1的二维数组表示。 1表示墙壁，0表示空地。你可以假定迷宫的边缘都是墙壁。起始位置和目的地的坐标通过行号和列号给出。
 * //
 * //
 * //
 * // 示例 1:
 * //
 * // 输入 1: 迷宫由以下二维数组表示
 * //
 * //0 0 1 0 0
 * //0 0 0 0 0
 * //0 0 0 1 0
 * //1 1 0 1 1
 * //0 0 0 0 0
 * //
 * //输入 2: 起始位置坐标 (rowStart, colStart) = (0, 4)
 * //输入 3: 目的地坐标 (rowDest, colDest) = (4, 4)
 * //
 * //输出: true
 * //
 * //解析: 一个可能的路径是 : 左 -> 下 -> 左 -> 下 -> 右 -> 下 -> 右。
 * //
 * //
 * //
 * // 示例 2:
 * //
 * // 输入 1: 迷宫由以下二维数组表示
 * //
 * //0 0 1 0 0
 * //0 0 0 0 0
 * //0 0 0 1 0
 * //1 1 0 1 1
 * //0 0 0 0 0
 * //
 * //输入 2: 起始位置坐标 (rowStart, colStart) = (0, 4)
 * //输入 3: 目的地坐标 (rowDest, colDest) = (3, 2)
 * //
 * //输出: false
 * //
 * //解析: 没有能够使球停在目的地的路径。
 * //
 * //
 * //
 * //
 * //
 * // 注意:
 * //
 * //
 * // 迷宫中只有一个球和一个目的地。
 * // 球和目的地都在空地上，且初始时它们不在同一位置。
 * // 给定的迷宫不包括边界 (如图中的红色矩形), 但你可以假设迷宫的边缘都是墙壁。
 * // 迷宫至少包括2块空地，行数和列数均不超过100。
 * //
 * // Related Topics 深度优先搜索 广度优先搜索
 * // 👍 93 👎 0
 */
@Slf4j
public class Maze {
    public static final int[][] DIR = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1},
    };

    /**
     * 回溯解法
     * 超时，为啥呢？
     * @param maze
     * @param start
     * @param destination
     * @return
     */
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        if (maze.length == 0) {
            return false;
        }
        int row = maze.length, col = maze[0].length;
        int[][] visit = new int[row][col];
        visit[start[0]][start[1]] = 1;
        return backTrace(maze, start, destination, row, col, new int[row][col]);
    }

    private boolean backTrace(int[][] maze, int[] cur, int[] destination, int row, int col, int[][] visit) {
        if (cur[0] == destination[0] && cur[1] == destination[1]) {
            return true;
        }
        //遍历4个方向
        for (int[] dir : DIR) {
            log.info("当前节点:[{},{}],移动方向:[{},{}]", cur[0], cur[1], dir[0], dir[1]);
            //移动到尽头
            int[] point = {cur[0], cur[1]};
            while (point[0] + dir[0] >= 0 && point[0] + dir[0] < row && point[1] + dir[1] >= 0
                    && point[1] + dir[1] < col && maze[point[0] + dir[0]][point[1] + dir[1]] == 0) {
                point[0] += dir[0];
                point[1] += dir[1];
            }
            //避免死循环
            if (visit[point[0]][point[1]] == 1) {
                continue;
            }
            visit[point[0]][point[1]] = 1;
            if (backTrace(maze, point, destination, row, col, visit)) {
                return true;
            }
            //回溯
            visit[point[0]][point[1]] = 0;
        }
        return false;
    }
}
