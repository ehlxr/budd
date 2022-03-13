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

package io.github.ehlxr.datastructure.stack;

/**
 * @author ehlxr
 * @since 2021-12-25 22:10.
 */
public class ArrayStack {
    private final Integer[] data;
    private final int size;
    // 栈顶下标
    private int top = 0;

    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(5);
        for (int i = 0; i < 6; i++) {
            System.out.println(stack.push(i));
        }

        for (int i = 0; i < 6; i++) {
            System.out.println(stack.pop());
        }

    }

    public ArrayStack(int size) {
        data = new Integer[size];
        this.size = size;
    }

    /**
     * 入栈
     */
    public boolean push(Integer item) {
        // 判断栈是否已满
        if (top == size) {
            return false;
        }
        data[top++] = item;

        return true;
    }

    /**
     * 出栈
     */
    public Integer pop() {
        // 判断栈是否为空
        if (top == 0) {
            return null;
        }
        return data[--top];
    }
}
