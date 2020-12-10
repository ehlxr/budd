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
 * 快速排序
 * <p>
 * 快速排序（Quicksort）是对冒泡排序的一种改进，借用了分治的思想，
 * 它的基本思想是：通过一趟排序将要排序的数据分割成独立的两部分，
 * 其中一部分的所有数据都比另外一部分的所有数据都要小，
 * 然后再按此方法对这两部分数据分别进行快速排序，
 * 整个排序过程可以递归进行，以此达到整个数据变成有序序列。
 *
 * @author ehlxr
 * @since 2020-10-01 16:40.
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {4, 9, 1, 8, 6, 2};
        sort(arr, 0, arr.length - 1);
    }

    /**
     * 快速排序使用分治策略来把一个序列（list）分为两个子序列（sub-lists）。
     * 步骤为：
     * ①. 从数列中挑出一个元素，称为 "基准"（pivot）。
     * ②. 重新排序数列，所有比基准值小的元素摆放在基准前面，所有比基准值大的元素摆在基准后面（相同的数可以到任一边）。
     * 在这个分区结束之后，该基准就处于数列的中间位置。这个称为分区（partition）操作。
     * ③. 递归地（recursively）把小于基准值元素的子数列和大于基准值元素的子数列排序。
     * <p>
     * 递归到最底部时，数列的大小是零或一，也就是已经排序好了。
     * 这个算法一定会结束，因为在每次的迭代（iteration）中，它至少会把一个元素摆到它最后的位置去。
     *
     * @param arr 待排序数组
     * @param l   左边界
     * @param r   右边界
     */
    public static void sort(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }

        if (arr == null || arr.length <= 0) {
            return;
        }

        int i = l, j = r,
                // 一般选择数组左边界作为基准
                k = l;
        while (l < r) {
            // 首先循环递减右边界，直到找到小于基准的元素，相互交换
            while (l < r && arr[r] >= arr[k]) {
                r--;
            }
            swap(arr, k, r);
            k = r;

            // 其次循环递增左边界，直到找到大于基准的元素，相互交换
            while (l < r && arr[l] <= arr[k]) {
                l++;
            }
            swap(arr, k, l);
            k = l;
            // 循环以上步骤，直到 l 和 r 相遇
        }
        // arr[k] = p;
        System.out.println("Sorting: " + Arrays.toString(arr));

        sort(arr, i, k - 1);
        sort(arr, k + 1, j);
    }

    /*
     * 左右指针法
     */
    // public static void sort(int[] arr, int low, int high) {
    //     if (arr == null || arr.length <= 0) {
    //         return;
    //     }
    //     if (low >= high) {
    //         return;
    //     }
    //
    //     int left = low;
    //     int right = high;
    //
    //     int key = arr[left];
    //
    //     while (left < right) {
    //         while (left < right && arr[right] >= key) {
    //             right--;
    //         }
    //         while (left < right && arr[left] <= key) {
    //             left++;
    //         }
    //         if (left < right) {
    //             swap(arr, left, right);
    //         }
    //     }
    //     swap(arr, low, left);
    //     System.out.println("Sorting: " + Arrays.toString(arr));
    //     sort(arr, low, left - 1);
    //     sort(arr, left + 1, high);
    // }

    /*
     * 挖坑法递归
     */
    // public static void sort2(int[] arr, int low, int high) {
    //     if (arr == null || arr.length <= 0) {
    //         return;
    //     }
    //     if (low >= high) {
    //         return;
    //     }
    //
    //     int left = low;
    //     int right = high;
    //     // 挖坑1：保存基准的值
    //     int temp = arr[left];
    //
    //     while (left < right) {
    //         while (left < right && arr[right] >= temp) {
    //             right--;
    //         }
    //         //坑2：从后向前找到比基准小的元素，插入到基准位置坑1中
    //         arr[left] = arr[right];
    //         while (left < right && arr[left] <= temp) {
    //             left++;
    //         }
    //         //坑3：从前往后找到比基准大的元素，放到刚才挖的坑2中
    //         arr[right] = arr[left];
    //     }
    //     //基准值填补到坑3中，准备分治递归快排
    //     arr[left] = temp;
    //     System.out.println("Sorting: " + Arrays.toString(arr));
    //     sort2(arr, low, left - 1);
    //     sort2(arr, left + 1, high);
    // }

    public static void swap(int[] arr, int low, int high) {
        int tmp = arr[low];
        arr[low] = arr[high];
        arr[high] = tmp;
    }
}