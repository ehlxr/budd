package me.ehlxr.sort;

import java.util.Arrays;

/**
 * 堆排序
 * <p>
 * 完全二叉树：
 * 若设二叉树的深度为 h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数，第 h 层所有的结点都连续集中在最左边。
 * <p>
 * 数组存储二叉树：
 * 父节点 i 的左子节点在位置：(2*i+1);
 * 父节点 i 的右子节点在位置：(2*i+2);
 * 子节点 i 的父节点在位置：(i-1)/2;
 * <p>
 * 堆：堆是具有以下性质的完全二叉树。
 * 每个结点的值都大于或等于其左右孩子结点的值，称为大顶堆（arr [i] >= arr [2i+1] && arr [i] >= arr [2i+2]）
 * 每个结点的值都小于或等于其左右孩子结点的值，称为小顶堆（arr [i] <= arr [2i+1] && arr [i] <= arr [2i+2]）
 * <p>
 * 堆排序的基本思想是：
 * 将待排序序列构造成一个大（小）顶堆，此时，整个序列的最大（小）值就是堆顶的根节点。
 * 将其与末尾元素进行交换，此时末尾就为最大（小）值。
 * 然后将剩余 n-1 个元素重新构造成一个大（小）顶堆，这样会得到 n 个元素的次小（大）值。
 * 如此反复执行，便能得到一个有序序列了。
 *
 * @author ehlxr
 * @since 2020-10-17 22:17.
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] arr = {4, 9, 1, 8, 6, 2};
        sort(arr);
    }

    public static void sort(int[] arr) {

    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
