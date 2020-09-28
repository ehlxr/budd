package me.ehlxr.leetcode;

import java.util.Arrays;

/**
 * 直接插入排序的基本思想是：
 * 将数组中的所有元素依次跟前面已经排好的元素相比较，如果选择的元素比已排序的元素小，
 * 则交换，直到全部元素都比较过为止。
 *
 * @author ehlxr
 * @since 2020-09-28 22:30.
 */
public class InsertSort {

    public static void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                int tmp = arr[j];
                if (arr[j + 1] < arr[j]) {
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                } else {
                    break;
                }
            }
        }

        System.out.println(Arrays.toString(arr));
    }

    public static void main(String[] args) {
        sort(new int[]{4, 9, 1, 6, 8, 2});
    }
}
