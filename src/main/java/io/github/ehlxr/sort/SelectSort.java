package io.github.ehlxr.sort;

import java.util.Arrays;

/**
 * 选择排序
 * <p>
 * 在未排序序列中找到最小（大）元素，存放到未排序序列的起始位置
 *
 * @author ehlxr
 * @since 2020-10-01 16:55.
 */
public class SelectSort {

    /**
     * ①. 从待排序序列中，找到关键字最小的元素；
     * ②. 如果最小元素不是待排序序列的第一个元素，将其和第一个元素互换；
     * ③. 从余下的 N - 1 个元素中，找出关键字最小的元素，重复①、②步，直到排序结束。
     */
    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            // for (int j = i + 1; j < arr.length; j++) {
            //     if (arr[i] > arr[j]) {
            //         swap(arr, i, j);
            //     }
            // }
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[min] > arr[j]) {
                    min = j;
                }
            }
            if (min != i) {
                swap(arr, min, i);
            }
            System.out.println("Sorting: " + Arrays.toString(arr));
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
        sort(new int[]{4, 9, 1, 6, 8, 2});
    }
}
