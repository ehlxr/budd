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

package io.github.ehlxr.algorithm;

/**
 * @author ehlxr
 * @since 2021-12-28 23:16.
 */
public class FindKthLargest {
    public static void main(String[] args) {
        int[] nums = new int[]{3, 2, 1, 5, 6, 4};
        System.out.println(findKthLargest(nums, 2));
    }

    public static int findKthLargest2(int[] nums, int k) {
        for (int i = nums.length / 2 - 1; i >= 0; i--) {
            // 从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(nums, i, nums.length);
        }

        for (int j = nums.length - 1; j >= 0; j--) {
            k--;

            if (k == 0) {
                return nums[0];
            }

            swap(nums, 0, j);
            adjustHeap(nums, 0, j);
        }

        return 0;
    }

    public static void adjustHeap(int[] arr, int i, int length) {
        // 先取出当前元素 i
        int temp = arr[i];
        // 从 i 结点的左子结点开始，也就是 2i+1 处开始
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            // 如果左子结点小于右子结点，k 指向右子结点
            if (k + 1 < length && arr[k] < arr[k + 1]) {
                k++;
            }
            // 如果子节点大于父节点，将子节点和父节点交换
            // if (arr[k] > arr[i]) {
            // swap(arr, k, i);
            if (arr[k] > temp) {
                // 不用进行交换
                arr[i] = arr[k];

                i = k;
            } else {
                break;
            }
        }

        // 将 temp 值放到最终的位置
        arr[i] = temp;
    }


    public static int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length < k) {
            return -1;
        }

        int q = partition(nums, 0, nums.length - 1);
        while (q + 1 != k) {
            if (k < q + 1) {
                q = partition(nums, 0, q - 1);
            } else {
                q = partition(nums, q + 1, nums.length - 1);
            }
        }

        return nums[q];
    }


    public static int partition(int[] a, int p, int r) {
        int pivot = a[r];
        int i = p;
        for (int j = p; j < r; j++) {
            if (a[j] >= pivot) {
                swap(a, i++, j);
            }
        }

        swap(a, i, r);
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        if (i == j) {
            return;
        }
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
