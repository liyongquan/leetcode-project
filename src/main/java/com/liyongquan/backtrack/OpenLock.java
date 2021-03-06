package com.liyongquan.backtrack;

import java.util.*;

/**
 * 752. 打开转盘锁
 * <p>
 * 你有一个带有四个圆形拨轮的转盘锁。每个拨轮都有10个数字： '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' 。每个拨轮可以自由旋转：例如把 '9' 变为 '0'，'0' 变为 '9' 。每次旋转都只能旋转一个拨轮的一位数字。
 * <p>
 * 锁的初始数字为 '0000' ，一个代表四个拨轮的数字的字符串。
 * <p>
 * 列表 deadends 包含了一组死亡数字，一旦拨轮的数字和列表里的任何一个元素相同，这个锁将会被永久锁定，无法再被旋转。
 * <p>
 * 字符串 target 代表可以解锁的数字，你需要给出解锁需要的最小旋转次数，如果无论如何不能解锁，返回 -1 。
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入：deadends = ["0201","0101","0102","1212","2002"], target = "0202"
 * 输出：6
 * 解释：
 * 可能的移动序列为 "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202"。
 * 注意 "0000" -> "0001" -> "0002" -> "0102" -> "0202" 这样的序列是不能解锁的，
 * 因为当拨动到 "0102" 时这个锁就会被锁定。
 * 示例 2:
 * <p>
 * 输入: deadends = ["8888"], target = "0009"
 * 输出：1
 * 解释：
 * 把最后一位反向旋转一次即可 "0000" -> "0009"。
 * 示例 3:
 * <p>
 * 输入: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"], target = "8888"
 * 输出：-1
 * 解释：
 * 无法旋转到目标数字且不被锁定。
 * 示例 4:
 * <p>
 * 输入: deadends = ["0000"], target = "8888"
 * 输出：-1
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= deadends.length <= 500
 * deadends[i].length == 4
 * target.length == 4
 * target 不在 deadends 之中
 * target 和 deadends[i] 仅由若干位数字组成
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/open-the-lock
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class OpenLock {
    /**
     * 回溯解法
     *
     * @param deadends
     * @param target
     * @return
     */
    public int openLock(String[] deadends, String target) {
        Set<Integer> set = new HashSet<>();
        for (String deadend : deadends) {
            set.add(Integer.valueOf(deadend));
        }
        return backtrace(Integer.parseInt(target), set, new HashMap<>());
    }

    private int backtrace(int target, Set<Integer> dead, Map<Integer, Integer> map) {
        if (dead.contains(target)) {
            return -1;
        }
        if (target == 0) {
            return 0;
        }
        if (map.containsKey(target)) {
            return map.get(target);
        }
        //转换成数组
        int[] arr = toArr(target);
        //8种场景
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            //上下拨
            int tmp = arr[i];
            arr[i] = (tmp + 1) % 10;
            int s1 = backtrace(toNum(arr), dead, map);
            if (s1 >= 0) {
                min = Math.min(s1 + 1, min);
            }
            arr[i] = (tmp - 1) % 10;
            int s2 = backtrace(toNum(arr), dead, map);
            if (s2 >= 0) {
                min = Math.min(s2 + 1, min);
            }
            arr[i] = tmp;
        }
        int res = min == Integer.MAX_VALUE ? -1 : min;
        map.put(target, res);
        return res;
    }

    private int toNum(int[] arr) {
        int idx = 3;
        int num = 0, plus = 1;
        while (idx >= 0) {
            num += arr[idx--] * plus;
            plus *= 10;
        }
        return num;
    }

    /**
     * BFS求最短路径
     *
     * @param deadends
     * @param target
     * @return
     */
    public int openLock2(String[] deadends, String target) {
        if ("0000".equals(target)) {
            return 0;
        }
        int len = 10000;
        int[] visit = new int[len];
        Queue<Integer> queue = new LinkedList();
        Queue<Integer> depth = new LinkedList();
        queue.add(0);
        depth.add(0);
        //把deadends放到visit里面，这样就少一层判断
        for (String deadend : deadends) {
            visit[Integer.parseInt(deadend)] = 1;
        }
        if (visit[0] == 1) {
            return -1;
        }
        visit[0] = 1;
        while (!queue.isEmpty()) {
            Integer poll = queue.poll();
            Integer dep = depth.poll();
            //8种场景
            int[] arr = toArr(poll);
            for (int i = 0; i < 4; i++) {
                int tmp = arr[i];
                arr[i] = (tmp + 1) % 10;
                int s1 = toNum(arr);
                if (s1 == Integer.parseInt(target)) {
                    return dep + 1;
                }
                if (visit[s1] == 0) {
                    queue.add(s1);
                    depth.add(dep + 1);
                    visit[s1] = 1;
                }
                arr[i] = tmp == 0 ? 9 : tmp - 1;
                int s2 = toNum(arr);
                if (s2 == Integer.parseInt(target)) {
                    return dep + 1;
                }
                if (visit[s2] == 0) {
                    queue.add(s2);
                    depth.add(dep + 1);
                    visit[s2] = 1;
                }
                arr[i] = tmp;
            }
        }
        return -1;
    }

    private int[] toArr(int num) {
        int[] arr = new int[4];
        int idx = 3;
        while (idx >= 0 && num > 0) {
            arr[idx--] = num % 10;
            num /= 10;
        }
        return arr;
    }
}
