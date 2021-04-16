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

package io.github.ehlxr.datastructure.stack;

import java.util.Scanner;

/**
 * @author ehlxr
 * @since 2021-04-15 22:18.
 */
public class LinkedListStackDemo {
    public static void main(String[] args) {
        LinkedListStack stack = new LinkedListStack(4);
        String key;
        boolean loop = true; //控制是否退出菜单
        Scanner scanner = new Scanner(System.in);

        while (loop) {
            System.out.println("show: 表示显示栈");
            System.out.println("exit: 退出程序");
            System.out.println("push: 表示添加数据到栈(入栈)");
            System.out.println("pop: 表示从栈取出数据(出栈)");
            System.out.println("请输入你的选择");
            key = scanner.next();
            switch (key) {
                case "show":
                    stack.list();
                    break;
                case "push":
                    System.out.println("请输入一个数");
                    int value = scanner.nextInt();
                    stack.push(value);
                    break;
                case "pop":
                    try {
                        int res = stack.pop();
                        System.out.printf("出栈的数据是 %d\n", res);
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println(e.getMessage());
                    }
                    break;
                case "exit":
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }

        System.out.println("程序退出~~~");
    }

}

class LinkedListStack {
    private final int maxSize;
    private int top = -1;
    private final Node head = new Node(0);

    public LinkedListStack(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean isFull() {
        return maxSize == top + 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public void push(int value) {
        if (isFull()) {
            System.out.println("栈满");
            return;
        }
        Node temp = head;
        while (true) {
            if (temp.next == null) {
                temp.next = new Node(value);
                top++;
                break;
            }
            temp = temp.next;
        }
    }

    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈空");
        }

        Node temp = head;
        while (true) {
            // if (temp.next == null) {
            //     throw new RuntimeException("栈空~~~");
            // }
            if (temp.next.next == null) {
                top--;
                int id = temp.next.id;
                temp.next = null;
                return id;
            }
            temp = temp.next;
        }

    }

    public void list() {
        if (isEmpty()) {
            System.out.println("栈空");
            return;
        }

        revert();

        Node temp = head.next;
        while (temp != null) {
            System.out.println(temp);

            temp = temp.next;
        }

        revert();

    }

    /**
     * 反转单向链表
     */
    private void revert() {
        Node pre = null;
        Node cur = head.next;
        while (cur != null) {
            Node next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        head.next = pre;
    }
}

class Node {
    public int id;
    public Node next;

    public Node(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Node{" + id + "}";
    }
}