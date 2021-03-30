/*
 * The MIT License (MIT)
 *
 * Copyright © 2020 xrv <xrg@live.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.ehlxr.leetcode;

/**
 * 反转链表
 * 假设链表为 1 → 2 → 3 → null，我们想要把它改成 null ← 1 ← 2 ← 3
 *
 * @author ehlxr
 * @since 2021-02-27 21:08.
 */
public class Solution206 {
    /*
    方法一：迭代
    https://leetcode-cn.com/problems/reverse-linked-list/solution/fan-zhuan-lian-biao-by-leetcode-solution-d1k2/
    */
    public static ListNode reverseList1(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            // 先存储当前节点的下一个节点
            ListNode next = head.next;

            // 将当前节点的下一个节点改为前一个节点
            head.next = prev;

            // 指针后移
            // 更改前一个节点为当前节点
            prev = head;
            // 更改当前节点为下一个节点
            head = next;
        }

        return prev;
    }

    /*
    方法一：递归
    https://leetcode-cn.com/problems/reverse-linked-list/solution/shi-pin-jiang-jie-die-dai-he-di-gui-hen-hswxy/
    */
    public static ListNode reverseList2(ListNode head) {
        // 递归终止条件
        if (head == null || head.next == null) {
            return head;
        }

        ListNode h = reverseList2(head.next);

        head.next.next = head;
        // 防止链表循环
        head.next = null;

        return h;
    }

    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);

        n1.next = n2;
        n2.next = n3;
        n3.next = n4;

        System.out.println(n1);

        System.out.println(reverseList2(n1));
    }
}
