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

package io.github.ehlxr.sort;

import java.util.Arrays;

/**
 * 插入排序
 * <p>
 * 基本思想是：
 * 将数组中的所有元素依次跟前面已经排好的元素相比较，如果选择的元素比已排序的元素小，
 * 则交换，直到全部元素都比较过为止。
 *
 * @author ehlxr
 * @since 2020-09-28 22:30.
 */
public class InsertSort {

    /**
     * ①. 从第一个元素开始，该元素可以认为已经被排序
     * ②. 取出下一个元素（新元素），在已经排序的元素序列中从后向前扫描
     * ③. 如果该元素（已排序）大于新元素，将该元素移到下一位置
     * ④. 重复步骤 3，直到找到已排序的元素小于或者等于新元素的位置
     * ⑤. 将新元素插入到该位置后
     * ⑥. 重复步骤 ②~⑤
     */
    public static void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j + 1] < arr[j]) {
                    swap(arr, j, j + 1);
                } else {
                    break;
                }
            }
            System.out.println(Arrays.toString(arr));
        }
    }

    public static void swap(int[] arr, int i, int j) {
        // arr[i] = arr[i] + arr[j];
        // arr[j] = arr[i] - arr[j];
        // arr[i] = arr[i] - arr[j];

        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        sort(new int[]{4, 9, 1, 8, 6, 2});
    }
}
