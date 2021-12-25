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

package io.github.ehlxr.datastructure.stack;

import io.github.ehlxr.datastructure.Node;

/**
 * @author ehlxr
 * @since 2021-12-25 22:28.
 */
public class LinkedListStack<T> {
    private Node<T> head;

    public Node<T> getData() {
        return head;
    }

    public static void main(String[] args) {
        LinkedListStack<Integer> stack = new LinkedListStack<>();
        for (int i = 0; i < 6; i++) {
            System.out.println(stack.push(i));
        }

        for (int i = 0; i < 6; i++) {
            stack.getData().print();
            System.out.println(stack.pop());
        }

    }

    /**
     * 入栈
     */
    public boolean push(T item) {
        Node<T> node = new Node<>(item, null);
        if (head == null) {
            head = node;
            return true;
        }

        node.setNext(head);
        head = node;
        return true;
    }

    /**
     * 出栈
     */
    public T pop() {
        if (head == null) {
            return null;
        }

        T val = head.getVal();
        head = head.getNext();
        return val;
    }

    public void clear() {
        head = null;
    }
}
