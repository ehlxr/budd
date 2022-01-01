/*
 * The MIT License (MIT)
 *
 * Copyright © 2022 xrv <xrg@live.com>
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

package io.github.ehlxr.algorithm.search;

/**
 * @author ehlxr
 * @since 2022-01-01 17:06.
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] a = new int[]{1, 3, 5, 6, 7, 8, 9};
        System.out.println(search(a, a.length, 3));
        System.out.println(searchRec(a, a.length, 3));
        a = new int[]{1, 2, 2, 2, 2, 5, 6, 7, 8, 9};
        System.out.println(searchFirst(a, a.length, 2));
    }

    /**
     * 非递归二分查找
     *
     * @param a 要查找的数组
     * @param n 数组的长度
     * @param v 要查找的值
     * @return 要查找值在数组中的索引
     */
    public static int search(int[] a, int n, int v) {
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) >> 1;
            if (a[mid] == v) {
                return mid;
            }

            if (a[mid] < v) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 递归二分查找
     *
     * @param a 要查找的数组
     * @param n 数组的长度
     * @param v 要查找的值
     * @return 要查找值在数组中的索引
     */
    public static int searchRec(int[] a, int n, int v) {
        return searchRec(a, 0, n - 1, v);
    }

    /**
     * 递归二分查找
     */
    public static int searchRec(int[] a, int low, int high, int v) {
        if (low > high) {
            return -1;
        }

        int mid = low + (high - low) >> 1;
        if (a[mid] == v) {
            return mid;
        }

        if (a[mid] < v) {
            return searchRec(a, mid + 1, high, v);
        } else {
            return searchRec(a, low, mid - 1, v);
        }
    }

    /**
     * 查找第一个值等于给定值的元素
     *
     * @param a 要查找的数组
     * @param n 数组的长度
     * @param v 要查找的值
     * @return 要查找值在数组中的索引
     */
    public static int searchFirst(int[] a, int n, int v) {
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) >> 1;
            if (a[mid] == v) {
                if (a[mid - 1] != v) {
                    return mid;
                } else {
                    high = mid - 1;
                }
            }

            if (a[mid] < v) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}
