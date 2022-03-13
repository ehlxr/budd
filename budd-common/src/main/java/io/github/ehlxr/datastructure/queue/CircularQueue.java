/*
 * The MIT License (MIT)
 *
 * Copyright © 2020 xrv <xrv@live.com>
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
 * 数组实现循环队列
 * <p>
 * 一般情况下只需要 tail + 1 == head 即可判定队列已满，结合特殊情况（head == 0，tail == n - 1）
 * 可使用公式 (tail + 1) % size == head 判定队列已满，head == tail 判断队列为空，
 * 但这种公式判断循环队列会浪费一个数组的存储空间。
 * <p>
 * 另也可使用一个变量 n 记录当前队列中的数量（入队 +1，出队 -1），当 head == tail 时，
 * 如果 n == size，则队列已满，如果 n == 0 则队列为空。
 *
 * @author ehlxr
 * @since 2021-12-25 21:01.
 */
public class CircularQueue {
    private final Integer[] data;
    private int head = 0;
    private int tail = 0;
    private final int size;

    public CircularQueue(int size) {
        data = new Integer[size];
        this.size = size;
    }

    public static void main(String[] args) {
        CircularQueue queue = new CircularQueue(5);

        // 循环队列会浪费一个数组的存储空间。队列容量为 5，入队 5 个最后一个失败
        for (int i = 0; i < 5; i++) {
            System.out.println(queue.enqueue(i));
        }

        // 出队 2 个，然后还可以入队 2 个
        for (int i = 0; i < 2; i++) {
            System.out.println(queue.dequeue());
        }
        System.out.println(queue.enqueue(7));
        System.out.println(queue.enqueue(8));
        System.out.println(queue.enqueue(9));

        // 出队 6 个，最后一个失败
        for (int i = 0; i < 6; i++) {
            System.out.println(queue.dequeue());
        }
    }

    /**
     * 入队（从队尾添加元素）
     */
    public boolean enqueue(Integer item) {
        // 判断队列是否已满
        if ((tail + 1) % size == head) {
            return false;
        }
        data[tail] = item;
        tail = (tail + 1) % size;

        return true;
    }

    /**
     * 出队（从队首取出元素）
     */
    public Integer dequeue() {
        // 判断队列是否为空
        if (head == tail) {
            return null;
        }

        Integer no = data[head];
        head = (head + 1) % size;

        return no;
    }
}
