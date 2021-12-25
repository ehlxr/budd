/*
 * The MIT License (MIT)
 *
 * Copyright © 2021 xrv <xrg@live.com>
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

package io.github.ehlxr.datastructure.linkedlist;

/**
 * 反转链表
 *
 * @author ehlxr
 * @since 2021-12-23 14:40.
 */
public class ReverseLinkedList {
    public static void main(String[] args) {
        Node n5 = new Node(5, null);
        Node n4 = new Node(4, n5);
        Node n3 = new Node(3, n4);
        Node n2 = new Node(2, n3);
        Node n1 = new Node(1, n2);
        Node head = new Node(0, n1);

        head.print();

        System.out.println("-------------");
        // head = reverse(head);
        head = reverse2(head);
        // head = reverse3(head, null);
        head.print();
    }


    // 循环方式
    public static Node reverse(Node head) {
        if (head == null || head.getNext() == null) {
            return head;
        }
        Node pre = null;

        Node cur = head;
        while (cur != null) {
            Node next = cur.getNext();
            cur.setNext(pre);
            pre = cur;

            cur = next;
        }

        return pre;
    }

    // 递归一
    public static Node reverse2(Node head) {
        if (head == null || head.getNext() == null) {
            return head;
        }

        Node next = head.getNext();
        Node node = reverse2(next);

        head.getNext().setNext(head);
        head.setNext(null);

        return node;
    }

    // 递归二
    public static Node reverse3(Node head, Node pre) {
        if (head == null) {
            return pre;
        }

        Node next = head.getNext();
        Node node = reverse3(next, head);

        head.setNext(pre);

        return node;
    }

}
