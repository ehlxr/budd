package io.github.ehlxr.sort;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 堆排序
 * <p>
 * 完全二叉树：
 * 若设二叉树的深度为 h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数，第 h 层所有的结点都连续集中在最左边。
 * <p>
 * 数组存储二叉树：
 * 数组实现一个完全二叉树时，叶子节点可以按从上到下、从左到右的顺序依次添加到数组中
 * 父节点 i 的左子节点在位置：(2*i+1);
 * 父节点 i 的右子节点在位置：(2*i+2);
 * 子节点 i 的父节点在位置：(i-1)/2;
 * <p>
 * 堆：
 * 堆是具有以下性质的完全二叉树:
 * ① 每个结点的值都大于或等于其左右孩子结点的值，称为大顶堆（arr [i] >= arr [2i+1] && arr [i] >= arr [2i+2]）
 * ② 每个结点的值都小于或等于其左右孩子结点的值，称为小顶堆（arr [i] <= arr [2i+1] && arr [i] <= arr [2i+2]）
 * <p>
 * 堆排序的基本思想是：
 * ① 将待排序序列构造成一个大（小）顶堆，此时，整个序列的最大（小）值就是堆顶的根节点。
 * ② 将其与末尾元素进行交换，此时末尾就为最大（小）值。
 * ③ 然后将剩余 n-1 个元素重新构造成一个大（小）顶堆，这样会得到 n 个元素的次小（大）值。
 * ④ 如此反复执行，便能得到一个有序序列了。
 *
 * @author ehlxr
 * @since 2020-10-17 22:17.
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] arr = {4, 6, 8, 5, 9};
        sort(arr);
        System.out.println("results: " + Arrays.toString(arr));

        // PriorityQueue<Integer> queue = new PriorityQueue<>();
        // for (int val : arr) {
        //     queue.add(val);
        // }
        // System.out.println(Arrays.toString(queue.toArray()));
        //
        //
        arr = new int[]{4, 6, 8, 5, 9};
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(arr.length, (o1, o2) -> o2 - o1);
        for (int val : arr) {
            maxHeap.add(val);
        }
        System.out.println(Arrays.toString(maxHeap.toArray()));
    }

    public static void sort(int[] arr) {
        // 1. 将无序序列构建为一个堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            // 从最后一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(arr, i, arr.length);
        }
        System.out.println("max heap: " + Arrays.toString(arr));

        // 2. 将堆顶元素与末尾元素交换，将最大元素"沉"到数组末端
        // 3. 重新调整结构，使其满足堆定义，然后继续交换堆顶元素与当前末尾元素，反复执行调整 + 交换，直到整个序列有序
        for (int j = arr.length - 1; j > 0; j--) {
            swap(arr, 0, j);

            adjustHeap(arr, 0, j);
            System.out.println(Arrays.toString(arr));
        }
    }

    /**
     * 调整大顶堆（仅是调整过程，建立在大顶堆已构建的基础上）
     *
     * @param arr    调整的数组
     * @param i      非叶子结点在数组中的索引
     * @param length 对多少个元素进行调整，length 在逐渐减少
     */
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

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
