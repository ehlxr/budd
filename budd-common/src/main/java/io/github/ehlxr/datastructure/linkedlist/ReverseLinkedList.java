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

import io.github.ehlxr.datastructure.Node;

/**
 * 反转链表
 *
 * @author ehlxr
 * @since 2021-12-23 14:40.
 */
public class ReverseLinkedList<T> {
    public static void main(String[] args) {
        ReverseLinkedList<Integer> reverseLinkedList = new ReverseLinkedList<>();
        Node<Integer> n5 = new Node<>(5, null);
        Node<Integer> n4 = new Node<>(4, n5);
        Node<Integer> n3 = new Node<>(3, n4);
        Node<Integer> n2 = new Node<>(2, n3);
        Node<Integer> n1 = new Node<>(1, n2);
        Node<Integer> head = new Node<>(0, n1);

        head.print();

        System.out.println("-------------");
        // head = reverse(head);
        head = reverseLinkedList.reverse2(head);
        // head = reverse3(head, null);
        head.print();
    }


    // 循环方式
    public Node<T> reverse(Node<T> head) {
        if (head == null || head.getNext() == null) {
            return head;
        }
        Node<T> pre = null;

        Node<T> cur = head;
        while (cur != null) {
            Node<T> next = cur.getNext();
            cur.setNext(pre);
            pre = cur;

            cur = next;
        }

        return pre;
    }

    // 递归一
    public Node<T> reverse2(Node<T> head) {
        if (head == null || head.getNext() == null) {
            return head;
        }

        Node<T> next = head.getNext();
        Node<T> node = reverse2(next);

        head.getNext().setNext(head);
        head.setNext(null);

        return node;
    }

    // 递归二
    public Node<T> reverse3(Node<T> head, Node<T> pre) {
        if (head == null) {
            return pre;
        }

        Node<T> next = head.getNext();
        Node<T> node = reverse3(next, head);

        head.setNext(pre);

        return node;
    }

}
