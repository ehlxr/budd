/*
 * The MIT License (MIT)
 *
 * Copyright © 2021 xrv <xrv@live.com>
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

package io.github.ehlxr.datastructure.queue;

import io.github.ehlxr.datastructure.Node;

/**
 * 基于链表实现的队列
 * <p>
 * 入队时间复杂度 O(n)
 *
 * @author ehlxr
 * @since 2021-12-25 16:02.
 */
public class LinkedListQueue<T> {
    private Node<T> head;

    public static void main(String[] args) {
        LinkedListQueue<Integer> queue = new LinkedListQueue<>();
        for (int i = 0; i < 6; i++) {
            System.out.println(queue.enqueue(i));
        }

        for (int i = 0; i < 6; i++) {
            queue.getData().print();
            System.out.println(queue.dequeue());
        }

        System.out.println(queue.dequeue());
    }

    /**
     * 入队（从队尾添加元素）
     * 时间复杂度 O(n)
     */
    public boolean enqueue(T item) {
        Node<T> node = new Node<T>(item, null);
        if (head == null) {
            head = node;
            return true;
        }

        // 找到队尾
        Node<T> tail = head;
        while (tail.getNext() != null) {
            tail = tail.getNext();
        }

        tail.setNext(node);
        return true;
    }

    /**
     * 出队（从队首取出元素）
     */
    public T dequeue() {
        if (head == null) {
            return null;
        }

        T val = head.getVal();
        head = head.getNext();

        return val;
    }

    public Node<T> getData() {
        return head;
    }
}
