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

/**
 * 希尔排序
 * <p>
 * 先将整个待排序的记录序列分割成为若干子序列分别进行直接插入排序，
 * 待整个序列中的记录 “基本有序” 时，再对全体记录进行依次直接插入排序。
 *
 * @author ehlxr
 * @since 2020-10-01 16:40.
 */
public class ShellSort {
    /**
     * ① 将待排序数组按照步长 gap 进行分组
     * ② 然后将每组的元素利用直接插入排序的方法进行排序
     * ③ 每次再将 gap 折半减小，循环上述 ①~② 操作；直到 gap=1 时，完成排序。
     * <p>
     * 可以看到步长的选择是希尔排序的重要部分。只要最终步长为 1 任何步长序列都可以工作。
     * 一般来说最简单的步长取值是初次取数组长度的一半为增量，之后每次再减半，直到增量为 1
     */
    public static void sort(int[] arr, int gap) {
        if (arr == null || arr.length <= 0 || gap < 1) {
            return;
        }
        for (int i = 0; i < gap; i++) {
            // 对按照 gap 进行分组的元素进行插入排序
            for (int j = gap + i; j < arr.length; j += gap) {
                for (int k = j - gap; k >= 0; k -= gap) {
                    if (arr[k] > arr[k + gap]) {
                        swap(arr, k, k + gap);
                    } else {
                        break;
                    }
                }
            }
        }

        // System.out.println(Arrays.toString(arr));

        // 每次将 gap 折半减小，循环上述操作
        sort(arr, gap / 2);
    }

    public static void sort2(int[] arr, int gap) {
        if (arr == null || arr.length <= 0 || gap < 1) {
            return;
        }
        for (int i = 0; i < gap; i++) {
            // 对按照 gap 进行分组的元素进行插入排序
            for (int j = gap + i; j < arr.length; j += gap) {
                int k = j - gap;
                int temp = arr[j];
                for (; k >= 0; k -= gap) {
                    if (arr[k] > temp) {
                        arr[k + gap] = arr[k];
                    } else {
                        break;
                    }
                }

                if (k + gap != j) {
                    arr[k + gap] = temp;
                }
            }
        }
        // System.out.println(Arrays.toString(arr));

        // 每次将 gap 折半减小，循环上述操作
        sort2(arr, gap / 2);
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int[] arr = new int[800_000];
        for (int i = 0; i < 800_000; i++) {
            arr[i] = (int) (Math.random() * 80_000_000); //生成一个[0, 8000000) 数
        }
        // int[] arr = {4, 9, 1, 8, 6, 2};

        long startTime = System.currentTimeMillis();
        sort2(arr, arr.length / 2);
        System.out.printf("排序花费时间 %dms.", System.currentTimeMillis() - startTime);
    }

    // public static void sort(int[] arr) {
    //     int gap = arr.length / 2;
    //     for (; gap > 0; gap = gap / 2) {
    //         //不断缩小gap，直到1为止
    //         for (int j = 0; (j + gap) < arr.length; j++) {
    //             //使用当前gap进行组内插入排序
    //             for (int k = 0; (k + gap) < arr.length; k += gap) {
    //                 //交换操作
    //                 if (arr[k] > arr[k + gap]) {
    //                     arr[k] = arr[k] + arr[k + gap];
    //                     arr[k + gap] = arr[k] - arr[k + gap];
    //                     arr[k] = arr[k] - arr[k + gap];
    //                 }
    //             }
    //         }
    //         System.out.println("    Sorting:  " + Arrays.toString(arr));
    //
    //     }
    // }
}
