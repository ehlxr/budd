package me.ehlxr.sort;

import java.util.Arrays;

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

        System.out.println(Arrays.toString(arr));

        // 每次将 gap 折半减小，循环上述操作
        sort(arr, gap / 2);
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int[] arr = {4, 9, 1, 8, 6, 2};
        sort(arr, arr.length / 2);
        // sort(arr);
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
