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

package io.github.ehlxr.datastructure.queue;

/**
 * 数组实现动态扩容队列
 *
 * @author ehlxr
 * @since 2021-12-25 15:19.
 */
public class DynamicArrayQueue {
    private final String[] data;
    private final int size;
    private int head = 0;
    private int tail = 0;

    public DynamicArrayQueue(int size) {
        data = new String[size];
        this.size = size;
    }

    public static void main(String[] args) {
        DynamicArrayQueue queue = new DynamicArrayQueue(5);

        // 队列容量为 5，入队 6 个最后一个失败
        for (int i = 0; i < 6; i++) {
            System.out.println(queue.enqueue("" + i));
        }

        // 出队 2 个，然后还可以入队 2 个
        for (int i = 0; i < 2; i++) {
            System.out.println(queue.dequeue());
        }
        System.out.println(queue.enqueue("a"));
        System.out.println(queue.enqueue("b"));
        System.out.println(queue.enqueue("c"));

        // 出队 6 个，最后一个失败
        for (int i = 0; i < 6; i++) {
            System.out.println(queue.dequeue());
        }
    }

    /**
     * 入队（从队尾添加元素）
     */
    public boolean enqueue(String item) {
        // 判断队列是否已满
        if (tail == size) {
            if (head == 0) {
                return false;
            }

            // 如果 tail == size，而 head != 0，0 与 head 之间还有空位，向前移动 head 与 tail 之间的数据
            // for (int i = head; i < tail; i++) {
            //     data[i - head] = data[i];
            // }
            if (tail - head >= 0) {
                System.arraycopy(data, head, data, 0, tail - head);
            }

            tail -= head;
            head = 0;
        }

        data[tail++] = item;
        return true;
    }

    /**
     * 出队（从队首取出元素）
     */
    public String dequeue() {
        // 判断队列是否为空
        if (head == tail) {
            return null;
        }

        return data[head++];
    }
}
