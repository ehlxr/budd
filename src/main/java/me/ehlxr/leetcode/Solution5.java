package me.ehlxr.leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author ehlxr
 * @since 2020-10-20 17:54.
 */
public class Solution5 {

    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int val : nums) {
            queue.add(val);
            if (queue.size() > k) {
                queue.poll();
            }
        }
        return queue.peek();
    }

    public static void main(String[] args) {
        int[] arr = {4, 6, 8, 5, 9};
        // PriorityQueue<Integer> queue = new PriorityQueue<>();
        // for (int val : arr) {
        //     queue.add(val);
        // }
        // System.out.println(Arrays.toString(queue.toArray()));
        //
        //
        // arr = new int[]{4, 6, 8, 5, 9};
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(arr.length, (o1, o2) -> o2 - o1);
        for (int val : arr) {
            maxHeap.add(val);
        }
        System.out.println(Arrays.toString(maxHeap.toArray()));
    }
}
