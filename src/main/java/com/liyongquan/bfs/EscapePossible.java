package com.liyongquan.bfs;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

//
// 1036. 逃离大迷宫
// 在一个 106 x 106 的网格中，每个网格上方格的坐标为 (x, y) 。
//
//现在从源方格 source = [sx, sy] 开始出发，意图赶往目标方格 target = [tx, ty] 。数组 blocked 是封锁的方格列表，其中每个 blocked[i] = [xi, yi] 表示坐标为 (xi, yi) 的方格是禁止通行的。
//
//每次移动，都可以走到网格中在四个方向上相邻的方格，只要该方格 不 在给出的封锁列表 blocked 上。同时，不允许走出网格。
//
//只有在可以通过一系列的移动从源方格 source 到达目标方格 target 时才返回 true。否则，返回 false。
//
// 
//
//示例 1：
//
//输入：blocked = [[0,1],[1,0]], source = [0,0], target = [0,2]
//输出：false
//解释：
//从源方格无法到达目标方格，因为我们无法在网格中移动。
//无法向北或者向东移动是因为方格禁止通行。
//无法向南或者向西移动是因为不能走出网格。
//示例 2：
//
//输入：blocked = [], source = [0,0], target = [999999,999999]
//输出：true
//解释：
//因为没有方格被封锁，所以一定可以到达目标方格。
// 
//
//提示：
//
//0 <= blocked.length <= 200
//blocked[i].length == 2
//0 <= xi, yi < 106
//source.length == target.length == 2
//0 <= sx, sy, tx, ty < 106
//source != target
//题目数据保证 source 和 target 不在封锁列表内
//
//来源：力扣（LeetCode）
//链接：https://leetcode-cn.com/problems/escape-a-large-maze
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

@Slf4j
public class EscapePossible {
    public static final int[][] DIRS = {
            {-1, 0},
            {1, 0},
            {0, 1},
            {0, -1},
    };

    public static final int LEN = 1000000;

    public boolean isEscapePossible(int[][] blocked, int[] source, int[] target) {
        Set<Position> block = new HashSet<>(blocked.length);
        for (int[] b : blocked) {
            block.add(new Position(b[0], b[1]));
        }
        return bfs(block, source, target) && bfs(block, target, source);
    }

    private boolean bfs(Set<Position> block, int[] source, int[] target) {
        log.info("start");
        Queue<Position> queue = new LinkedList<>();
        queue.add(new Position(source[0], source[1]));
        int maxSize = block.size() * (block.size() - 1) / 2;
        Set<Position> visit = new HashSet<>(maxSize);
        visit.add(new Position(source[0], source[1]));
        while (!queue.isEmpty()) {
            Position pos = queue.poll();
            for (int[] dir : DIRS) {
                int x = pos.x + dir[0], y = pos.y + dir[1];
                Position nPos = new Position(x, y);
                if (x >= 0 && x < LEN && y >= 0 && y < LEN && !visit.contains(nPos) && !block.contains(nPos)) {
                    //log.info("[{},{}]", x, y);
                    if (x == target[0] && y == target[1]) {
                        return true;
                    }
                    queue.add(nPos);
                    visit.add(nPos);
                    //关键点
                    if (visit.size() > maxSize) {
                        //log.info("true");
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private static class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(new int[]{x, y});
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }
    }
}
