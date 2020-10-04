package me.ehlxr.sort;

import java.util.Arrays;

/**
 * 归并排序
 * <p>
 * 归并排序算法是将两个（或两个以上）有序表合并成一个新的有序表，即把待排序序列分为若干个子序列，
 * 每个子序列是有序的。然后再把有序子序列合并为整体有序序列。
 *
 * @author ehlxr
 * @since 2020-10-04 21:09.
 */
public class MergeSort {
    public static void main(String[] args) {
        int[] arr = {4, 9, 1, 8, 6, 2};
        // merge(new int[]{1, 4, 9}, new int[]{2, 6, 8});
        System.out.println(Arrays.toString(sort(arr)));
    }

    /**
     * ①. 将序列每相邻两个数字进行归并操作，形成 floor (n/2) 个序列，排序后每个序列包含两个元素；
     * ②. 将上述序列再次归并，形成 floor (n/4) 个序列，每个序列包含四个元素；
     * ③. 重复步骤②，直到所有元素排序完毕
     */
    public static int[] sort(int[] arr) {
        if (arr.length <= 1) {
            return arr;
        }
        // arr.length/2
        int num = arr.length >> 1;
        int[] left = Arrays.copyOfRange(arr, 0, num);
        int[] right = Arrays.copyOfRange(arr, num, arr.length);
        return merge(sort(left), sort(right));
    }

    /**
     * 将两个有序的数组进行归并排序操作
     */
    public static int[] merge(int[] arr1, int[] arr2) {
        int l1 = 0;
        int l2 = 0;
        int i = 0;

        int[] r = new int[arr1.length + arr2.length];
        while (l1 < arr1.length || l2 < arr2.length) {
            if (l1 >= arr1.length) {
                r[i++] = arr2[l2];
                l2++;
                continue;
            }

            if (l2 >= arr2.length) {
                r[i++] = arr1[l1];
                l1++;
                continue;
            }

            if (arr1[l1] < arr2[l2]) {
                r[i++] = arr1[l1];
                l1++;
            } else {
                r[i++] = arr2[l2];
                l2++;
            }
        }
        return r;
    }
}
