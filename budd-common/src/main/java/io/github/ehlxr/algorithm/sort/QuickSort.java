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

package io.github.ehlxr.algorithm.sort;

import java.util.Arrays;

/**
 * @author ehlxr
 * @since 2021-12-28 19:57.
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arrs = new int[]{3, 2, 5, 7, 1, 9};
        sort(arrs, 0, arrs.length - 1);

        System.out.println(Arrays.toString(args));
    }

    public static void sort(int[] arrs, int p, int r) {
        if (p >= r) {
            return;
        }

        int k = partition(arrs, p, r);

        sort(arrs, p, k - 1);
        sort(arrs, k + 1, r);
    }

    public static int partition(int[] arrs, int p, int r) {
        int i = p;
        int pivot = arrs[r];
        for (int j = p; j < r; j++) {
            if (arrs[j] < pivot) {
                if (j != i) {
                    swap(arrs, i, j);
                }
                i++;
            }
        }

        swap(arrs, i, r);
        System.out.println("i=" + i);

        return i;
    }

    public static void swap(int[] arrs, int i, int j) {
        int temp = arrs[i];
        arrs[i] = arrs[j];
        arrs[j] = temp;
    }


}
