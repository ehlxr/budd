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

package io.github.ehlxr.algorithm.binarytree.heap;

import java.util.Arrays;

/**
 * @author ehlxr
 * @since 2022-03-15 07:40.
 */
public class Heap {
    private static void createHeap(int[] a) {
        int n = a.length - 1; // 最后一个叶子节点下标
        buildHeap(a, n);
    }

    private static void buildHeap(int[] a, int n) {
        // (n - 1) / 2 表示最后一个叶子节点父节点，即为最后一个非叶子节点
        for (int i = (n - 1) / 2; i >= 0; --i) {
            heapify(a, n, i);
        }
    }

    /**
     * 堆化
     *
     * @param a 数组
     * @param n 最后一个元素下标
     * @param i 需要调整的父节点下标
     */
    private static void heapify(int[] a, int n, int i) {
        while (true) {
            int max = i;

            // 从当前节点和左右叶子节点中找出最大的
            int l = 2 * i + 1;
            if (l <= n && a[l] > a[max]) {
                max = l;
            }

            int r = 2 * i + 2;
            if (r <= n && a[r] > a[max]) {
                max = r;
            }

            // 当前节点最大时退出
            if (max == i) {
                break;
            }

            swap(a, i, max);

            // 最大节点和当前节点交换后，继续以当前节点为父节点堆化
            i = max;
        }
    }

    /**
     * 堆排序
     */
    private static void sort(int[] a) {
        for (int i = a.length - 1; i > 0; i--) {
            buildHeap(a, i);
            // 堆顶元素和最后一个元素交换，除过最后一个元素外其它元素再次构建大顶堆
            swap(a, 0, i);
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6};
        createHeap(arr);
        System.out.println(Arrays.toString(arr));

        sort(arr);
        System.out.println(Arrays.toString(arr));

    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
