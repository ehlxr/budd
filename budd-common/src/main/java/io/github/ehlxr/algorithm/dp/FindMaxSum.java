/*
 * The MIT License (MIT)
 *
 * Copyright © 2020 xrv <xrv@live.com>
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

package io.github.ehlxr.algorithm.dp;

/**
 * 在一个数组 arr 中，找出一组不相邻的数字，使得最后的和最大
 * <p>
 * 图解 https://cdn.jsdelivr.net/gh/0vo/oss/images/dp.jpg
 *
 * @author ehlxr
 * @since 2021-10-31 22:46.
 */
public class FindMaxSum {
    public static int recOpt(int[] arr, int i) {
        if (i == 0) {
            return arr[0];
        } else if (i == 1) {
            return Math.max(arr[0], arr[1]);
        } else {
            int a = recOpt(arr, i - 2) + arr[i];
            int b = recOpt(arr, i - 1);
            return Math.max(a, b);
        }
    }

    public static int dpOpt(int[] arr) {
        int[] opt = new int[arr.length];
        opt[0] = arr[0];
        opt[1] = Math.max(arr[0], arr[1]);
        for (int i = 2; i < opt.length; i++) {
            int a = opt[i - 2] + arr[i];
            int b = opt[i - 1];
            opt[i] = Math.max(a, b);
        }
        return opt[arr.length - 1];
    }

    public static void main(String[] args) {
        int[] arr = {4, 1, 1, 9, 1};
        System.out.println(dpOpt(arr));
        System.out.println(recOpt(arr, arr.length - 1));
    }
}
