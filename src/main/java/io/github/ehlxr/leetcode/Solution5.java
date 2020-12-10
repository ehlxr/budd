/*
 * The MIT License (MIT)
 *
 * Copyright © 2020 xrv <xrg@live.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.ehlxr.leetcode;

import java.util.Arrays;
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
