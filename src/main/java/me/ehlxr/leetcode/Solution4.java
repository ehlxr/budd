package me.ehlxr.leetcode;

/**
 * 4. 寻找两个正序数组的中位数
 *
 * @author ehlxr
 * @since 2020-09-27 21:12.
 */
public class Solution4 {
    /**
     * 解法一
     * 简单粗暴，先将两个数组合并，两个有序数组的合并也是归并排序中的一部分。然后根据奇数，还是偶数，返回中位数。
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] result = new int[nums1.length + nums2.length];

        int x = 0, y = 0, z = 0;
        while (x < nums1.length || y < nums2.length) {
            if (x >= nums1.length) {
                result[z] = nums2[y];
                y++;
                z++;
                continue;
            }

            if (y >= nums2.length) {
                result[z] = nums1[x];
                x++;
                z++;
                continue;
            }

            if (nums1[x] < nums2[y]) {
                result[z] = nums1[x];
                x++;
            } else {
                result[z] = nums2[y];
                y++;
            }
            z++;
        }

        int m = result.length / 2;
        if (result.length % 2 == 0) {
            return (result[m] + result[m - 1]) / 2.0;
        } else {
            return result[m];
        }
    }

    /**
     * 用 len 表示合并后数组的长度，
     * 如果是奇数，我们需要知道第 （len+1）/2 个数就可以了，如果遍历的话需要遍历 int(len/2 ) + 1 次。
     * 如果是偶数，我们需要知道第 len/2 和 len/2+1 个数，也是需要遍历 len/2+1 次。
     *
     * 所以遍历的话，奇数和偶数都是 len/2+1 次。
     *
     * https://leetcode-cn.com/problems/median-of-two-sorted-arrays/solution/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by-w-2/
     */
    public static double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int l1 = nums1.length, l2 = nums2.length,
                left = 0, right = 0,
                len = l1 + l2,
                x = 0, y = 0;

        for (int i = 0; i <  len/2 +1; i++) {
            left = right;

            if (x >= nums1.length) {
                right = nums2[y];
                y++;
                continue;
            }

            if (y >= nums2.length) {
                right = nums1[x];
                x++;
                continue;
            }

            if (nums1[x] < nums2[y]) {
                right = nums1[x];
                x++;
            } else {
                right = nums2[y];
                y++;
            }
        }

        if (len % 2 == 0) {
            return (left + right) / 2.0;
        } else {
            return right;
        }
    }

    public static void main(String[] args) {
        System.out.println(findMedianSortedArrays(new int[]{}, new int[]{2, 3}));
        System.out.println(findMedianSortedArrays2(new int[]{1, 4}, new int[]{2, 3}));
        System.out.println(findMedianSortedArrays2(new int[]{3}, new int[]{1, 4, 6}));
    }
}
