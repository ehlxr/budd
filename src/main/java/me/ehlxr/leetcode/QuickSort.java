package me.ehlxr.leetcode;

import java.util.Arrays;

public class QuickSort {
    /**
     * 快速排序（左右指针法）
     *
     * @param arr  待排序数组
     * @param low  左边界
     * @param high 右边界
     */
    public static void sort(int[] arr, int low, int high) {
        if (arr == null || arr.length <= 0) {
            return;
        }
        if (low >= high) {
            return;
        }

        int left = low;
        int right = high;

        int key = arr[left];

        while (left < right) {
            while (left < right && arr[right] >= key) {
                right--;
            }
            while (left < right && arr[left] <= key) {
                left++;
            }
            if (left < right) {
                swap(arr, left, right);
            }
        }
        swap(arr, low, left);
        System.out.println("Sorting: " + Arrays.toString(arr));
        sort(arr, low, left - 1);
        sort(arr, left + 1, high);
    }

    public static void swap(int[] arr, int low, int high) {
        int tmp = arr[low];
        arr[low] = arr[high];
        arr[high] = tmp;
    }

    /**
     * 快速排序（挖坑法递归）
     *
     * @param arr  待排序数组
     * @param low  左边界
     * @param high 右边界
     */
    public static void sort2(int[] arr, int low, int high) {
        if (arr == null || arr.length <= 0) {
            return;
        }
        if (low >= high) {
            return;
        }

        int left = low;
        int right = high;
        int temp = arr[left]; //挖坑1：保存基准的值

        while (left < right) {
            while (left < right && arr[right] >= temp) {
                right--;
            }
            arr[left] = arr[right]; //坑2：从后向前找到比基准小的元素，插入到基准位置坑1中
            while (left < right && arr[left] <= temp) {
                left++;
            }
            arr[right] = arr[left]; //坑3：从前往后找到比基准大的元素，放到刚才挖的坑2中
        }
        arr[left] = temp; //基准值填补到坑3中，准备分治递归快排
        System.out.println("Sorting: " + Arrays.toString(arr));
        sort2(arr, low, left - 1);
        sort2(arr, left + 1, high);
    }

    public static void main(String[] args) {
        int[] arrs = {2, 4, 7, 5};
        sort3(arrs, 0, arrs.length - 1);
    }

    public static void sort3(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }

        if (arr == null || arr.length <= 0) {
            return;
        }

        int i = l, j = r, x = l;
        int k = arr[x];
        while (l < r) {
            while (l < r && arr[r] >= k) {
                r--;
            }
            arr[x] = arr[r];
            x = r;

            while (l < r && arr[l] <= k) {
                l++;
            }
            arr[x] = arr[l];
            x = l;
        }
        arr[x] = k;
        System.out.println("Sorting: " + Arrays.toString(arr));

        sort3(arr, i, x - 1);
        sort3(arr, x + 1, j);
    }
}