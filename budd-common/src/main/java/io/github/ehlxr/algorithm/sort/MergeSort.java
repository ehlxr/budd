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
 * @since 2021-12-27 19:06.
 */
public class MergeSort {
    public static void main(String[] args) {
        int[] arrs = new int[]{3, 2, 5, 7, 1, 9};
        System.out.println(Arrays.toString(sort(arrs)));
        System.out.println(Arrays.toString(sort2(arrs)));
    }

    public static int[] sort(int[] arrs) {
        if (arrs.length <= 1) {
            return arrs;
        }
        int[] a1 = sort(Arrays.copyOfRange(arrs, 0, arrs.length / 2));
        int[] a2 = sort(Arrays.copyOfRange(arrs, arrs.length / 2, arrs.length));

        return merge(a1, a2);
    }

    public static int[] merge(int[] a1, int[] a2) {
        int i = 0;
        int j = 0;
        int[] result = new int[a1.length + a2.length];
        int k = 0;
        while (i < a1.length || j < a2.length) {
            int temp;
            if (i >= a1.length) {
                temp = a2[j++];
            } else if (j >= a2.length) {
                temp = a1[i++];
            } else if (a1[i] <= a2[j]) {// 为了保证算法的稳定性，如果 a1、a2 有相同的元素，保证 a1 的元素在前面
                temp = a1[i++];
            } else {
                temp = a2[j++];
            }

            result[k] = temp;
            k++;
        }

        return result;
    }


    public static int[] sort2(int[] a) {
        if (a == null || a.length <= 1) {
            return a;
        }

        return merge2(sort2(Arrays.copyOfRange(a, 0, a.length / 2)),
                sort2(Arrays.copyOfRange(a, a.length / 2, a.length)));
    }

    public static int[] merge2(int[] a, int[] b) {
        int[] r = new int[a.length + b.length];

        int i = 0;
        int j = 0;
        int m = 0;
        while (i < a.length || j < b.length) {
            if (i >= a.length) {
                r[m++] = b[j++];
                continue;
            }

            if (j >= b.length) {
                r[m++] = a[i++];
                continue;
            }

            if (a[i] < b[j]) {
                r[m++] = a[i++];
            } else {
                r[m++] = b[j++];
            }
        }


        return r;
    }
}
