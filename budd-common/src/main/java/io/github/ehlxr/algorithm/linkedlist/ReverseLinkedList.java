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
        Node node5 = new Node(5, null);
        Node node4 = new Node(4, node5);
        Node node3 = new Node(3, node4);
        Node node2 = new Node(2, node3);
        Node head = new Node(1, node2);

        // Node reverse = reverse(root);
        // reverse.print();

        // System.out.println(reverseToN(head, node3));
        // System.out.println(reverseToN(head, 2));
        System.out.println(reverseFm2N(head, 2, 4));

    }

    public static Node reverseToN(Node root, Node n) {
        Node pre = null, cur = root, tmp = root;
        while (cur != n) {
            tmp = cur.next;

            cur.next = pre;
            pre = cur;
            cur = tmp;
        }

        return pre;

    }

    static Node next = null;

    public static Node reverseToN(Node root, int n) {
        if (n == 1) {
            next = root.next;
            return root;
        }

        Node h = reverseToN(root.next, n - 1);
        root.next.next = root;
        root.next = next;
        return h;
    }

    public static Node reverseFm2N(Node root, int m, int n) {
        if (m == 1) {
            return reverseToN(root, n);
        }
        root.next = reverseFm2N(root.next, m - 1, n - 1);
        return root;
    }

    public static Node reverse(Node root) {

        Node pre = null;
        Node cur = root;
        while (cur != null) {
            Node tmp = cur.next;

            cur.next = pre;
            pre = cur;
            cur = tmp;
            // tmp = cur.next;
        }

        return pre;
    }
}

class Node {
    public Integer value;
    public Node next;

    public Node(Integer value, Node next) {
        this.next = next;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", next=" + next +
                '}';
    }
}
