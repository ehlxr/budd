package me.ehlxr.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 * <p>
 * 从无序序列头部开始，进行两两比较，根据大小交换位置，直到最后将最大（小）的数据元素交换到了无序队列的队尾，从而成为有序序列的一部分；
 * 下一次继续这个过程，直到所有数据元素都排好序。
 * <p>
 * 算法的核心在于每次通过两两比较交换位置，选出剩余无序序列里最大（小）的数据元素放到队尾。
 *
 * @author ehlxr
 * @since 2020-10-01 16:40.
 */
public class BubbleSort {

    /**
     * 冒泡排序算法的算法过程如下：
     * ①. 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
     * ②. 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，最后的元素会是最大的数。
     * ③. 针对所有的元素重复以上的步骤，除了最后一个。
     * ④. 持续每次对越来越少的元素重复上面的步骤①~③，直到没有任何一对数字需要比较。
     */
    public static void sort(int[] arr) {
        //外层：需要 length-1 次循环比较
        for (int i = 0; i < arr.length - 1; i++) {

            //内层：每次循环需要两两比较的次数，每次比较后，都会将当前最大的数放到最后位置，所以每次比较次数递减一次
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
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
