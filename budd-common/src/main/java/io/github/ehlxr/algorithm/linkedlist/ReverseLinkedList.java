/*
 * The MIT License (MIT)
 *
 * Copyright © 2022 xrv <xrv@live.com>
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

package io.github.ehlxr.algorithm.linkedlist;

/**
 * 反转单链表
 *
 * @author ehlxr
 * @since 2022-03-14 07:05.
 */
public class ReverseLinkedList {
    public static void main(String[] args) {
        ListNode ListNode5 = new ListNode(5, null);
        ListNode ListNode4 = new ListNode(4, ListNode5);
        ListNode ListNode3 = new ListNode(3, ListNode4);
        ListNode ListNode2 = new ListNode(2, ListNode3);
        ListNode head = new ListNode(1, ListNode2);

        // ListNode reverse = reverse(root);
        // reverse.print();

        // System.out.println(reverseToN(head, ListNode3));
        // System.out.println(reverseToN(head, 2));
        System.out.println(reverseFm2N(head, 2, 4));

    }

    public static ListNode reverseToN(ListNode root, ListNode n) {
        ListNode pre = null, cur = root, tmp = root;
        while (cur != n) {
            tmp = cur.next;

            cur.next = pre;
            pre = cur;
            cur = tmp;
        }

        return pre;

    }

    static ListNode next = null;

    public static ListNode reverseToN(ListNode root, int n) {
        if (n == 1) {
            next = root.next;
            return root;
        }

        ListNode h = reverseToN(root.next, n - 1);
        root.next.next = root;
        root.next = next;
        return h;
    }

    public static ListNode reverseFm2N(ListNode root, int m, int n) {
        if (m == 1) {
            return reverseToN(root, n);
        }
        root.next = reverseFm2N(root.next, m - 1, n - 1);
        return root;
    }

    public static ListNode reverse(ListNode root) {

        ListNode pre = null;
        ListNode cur = root;
        while (cur != null) {
            ListNode tmp = cur.next;

            cur.next = pre;
            pre = cur;
            cur = tmp;
            // tmp = cur.next;
        }

        return pre;
    }
}
