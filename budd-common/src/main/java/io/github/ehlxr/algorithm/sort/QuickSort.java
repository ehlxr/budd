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

package io.github.ehlxr.algorithm.sort;

import java.util.Arrays;

/**
 * @author ehlxr
 * @since 2021-12-28 19:57.
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] a = new int[]{3, 9, 5, 7, 1, 2};
        sort2(a, 0, a.length - 1);

        System.out.println(Arrays.toString(a));
    }

    public static void sort(int[] a, int p, int r) {
        if (p >= r) {
            return;
        }

        int q = partition(a, p, r);

        sort(a, p, q - 1);
        sort(a, q + 1, r);
    }

    /**
     * 获取分区点
     * <p>
     * 图示：https://cdn.jsdelivr.net/gh/0vo/oss/images/quick_sort.jpg
     * <p>
     * 通过游标 i 把 a[p...r] 分成两部分。
     * a[p...i-1] 的元素都是小于 pivot 的，我们暂且叫它 “已处理区间”，a[i...r] 是 “未处理区间”。
     * 我们每次都从未处理的区间 a[i...r] 中取一个元素 a[j]，与 pivot 对比，
     * 如果小于 pivot，则将其加入到已处理区间的尾部，也就是 a[i] 的位置
     * <p>
     * 只需要将 a[i] 与 a[j] 交换，就可以在 O(1) 时间复杂度内将 a[j] 放到下标为 i 的位置。
     */
    public static int partition(int[] a, int p, int r) {
        int i = p;
        int pivot = a[r];
        for (int j = p; j < r; j++) {
            if (a[j] < pivot) {
                swap(a, i++, j);
            }
        }

        swap(a, i, r);
        System.out.println("i=" + i + " " + Arrays.toString(a));

        return i;
    }

    public static void swap(int[] a, int i, int j) {
        if (i == j) {
            return;
        }
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void sort2(int[] a, int l, int r) {
        if (a == null || l >= r) {
            return;
        }

        int i = l, j = r;
        int p = l; // 选择最左边的元素为 pivot
        while (l < r) {
            // 如果选择 p = l 必须先从右边找到小于 a[p] 的第一个元素
            while (l < r && a[r] >= a[p]) {
                r--;
            }
            swap(a, r, p);
            p = r;

            // 从左边找到大于 a[p] 的第一个元素
            while (l < r && a[l] <= a[p]) {
                l++;
            }
            swap(a, l, p);
            p = l;
            System.out.println(Arrays.toString(a));

        }

        sort2(a, i, p - 1);
        sort2(a, p + 1, j);
    }
}
