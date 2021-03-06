package com.liyongquan.linklist;

import java.util.PriorityQueue;

/**
 * 合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。
 * <p>
 * 示例:
 * <p>
 * 输入:
 * [
 * 1->4->5,
 * 1->3->4,
 * 2->6
 * ]
 * 输出: 1->1->2->3->4->4->5->6
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-k-sorted-lists
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MergeList2 {
    /**
     * 归并排序，尝试简化下代码
     * <p>
     * 时间复杂度 O(k*n)，假设有k个链表，每个链表的长度为n
     * 空间复杂度 O(n)
     * <p>
     * 代码简单一顿撸，性能击败5%...
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length <= 0) {
            return null;
        }
        //dummy结点，简化处理逻辑
        ListNode head = new ListNode(0);
        ListNode cur = head;
        while (true) {
            //计算最小值
            ListNode min = null;
            int idx = 0;
            for (int i = 0; i < lists.length; i++) {
                ListNode node = lists[i];
                if (min == null || (node != null && node.val < min.val)) {
                    min = node;
                    idx = i;
                }
            }
            //所有列表都已全部输出
            if (min == null) {
                break;
            }
            //指针移动
            lists[idx] = lists[idx].next;
            //往后插入结点
            cur.next = new ListNode(min.val);
            cur = cur.next;
        }
        return head.next;
    }

    /**
     * 上面还有性能优化的空间，这是因为每次选择一个数字都必须从k个数里面选择一个最小值，都需要比较k次。
     * 事实上我们每次的比较可以重复利用上次的结果，构建一个小顶堆，每次选择堆顶的元素，并且往后增加新元素
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists2(ListNode[] lists) {
        if (lists == null || lists.length <= 0) {
            return null;
        }
        //dummy结点，简化处理逻辑
        ListNode head = new ListNode(0);
        ListNode cur = head;
        //堆排
        PriorityQueue<ListNode> queue = new PriorityQueue<>(lists.length, (o1, o2) -> o1.val - o2.val);
        //初始化堆
        for (ListNode list : lists) {
            if (list != null) {
                queue.add(list);
            }
        }
        while (queue.size() > 0) {
            ListNode min = queue.poll();
            //更新最小堆
            if (min.next != null) {
                queue.add(min.next);
            }
            //往后插入结点
            cur.next = new ListNode(min.val);
            cur = cur.next;
        }
        return head.next;
    }
}
