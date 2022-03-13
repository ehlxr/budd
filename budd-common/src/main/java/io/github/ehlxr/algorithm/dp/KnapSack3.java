/*
 * The MIT License (MIT)
 *
 * Copyright © 2022 xrv <xrv@live.com>
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
 * 对于一组不同重量、不同价值、不可分割的物品，我们选择将某些物品装入背包，在满足背包最大重量限制的前提下，背包中可装入物品的总价值最大是多少呢？
 *
 * @author ehlxr
 * @since 2022-03-05 14:31.
 */
public class KnapSack3 {
    private final int[] weight = {2, 2, 4, 6, 3};  // 物品的重量
    private final int[] value = {3, 4, 8, 9, 6}; // 物品的价值
    private final int n = 5; // 物品个数
    private final int w = 9; // 背包承受的最大重量
    private int maxV = Integer.MIN_VALUE; // 结果放到 maxV 中

    /**
     * 动态规划方式
     *
     * @param weight 物品重量
     * @param value  物品的价值
     * @param n:     物品个数
     * @param w:     背包可承载重量
     * @return 最大价值
     */
    public static int knapsack3(int[] weight, int[] value, int n, int w) {
        int[][] states = new int[n][w + 1];
        for (int i = 0; i < n; ++i) { // 初始化 states,默认 -1
            for (int j = 0; j < w + 1; ++j) {
                states[i][j] = -1;
            }
        }
        states[0][0] = 0;
        if (weight[0] <= w) {
            states[0][weight[0]] = value[0];
        }
        for (int i = 1; i < n; ++i) { // 动态规划,状态转移
            for (int j = 0; j <= w; ++j) { // 不选择第 i 个物品
                if (states[i - 1][j] >= 0) {
                    // 复制上一层状态
                    states[i][j] = states[i - 1][j];
                }
            }
            // 下面的 j 表示重量
            for (int j = 0; j <= w - weight[i]; ++j) { // 选择第 i 个物品
                if (states[i - 1][j] >= 0) { // 表示上一层存在重量为 j 的状态
                    int v = states[i - 1][j] + value[i]; // 上一层重量为 j 的价值 + i 物品的价值
                    if (v > states[i][j + weight[i]]) { // states[i][j + weight[i]]：存储当前重量 j + 物品 i 的重量所在位置的价值
                        states[i][j + weight[i]] = v;
                    }
                }
            }
        }
        // 找出最大值
        int maxvalue = -1;
        for (int j = 0; j <= w; ++j) {
            if (states[n - 1][j] > maxvalue) {
                maxvalue = states[n - 1][j];
            }
        }
        return maxvalue;
    }

    /**
     * 回溯方式
     *
     * @param i  第几个物品
     * @param cw 物品的重量
     * @param cv 物品的价值
     */
    public void f(int i, int cw, int cv) { // 调用 f (0, 0, 0)
        if (cw == w || i == n) { //cw==w 表示装满了,i==n 表示物品都考察完了
            if (cv > maxV) {
                maxV = cv;
            }
            return;
        }
        f(i + 1, cw, cv); // 选择不装第 i 个物品
        if (cw + weight[i] <= w) {
            f(i + 1, cw + weight[i], cv + value[i]); // 选择装第 i 个物品
        }
    }


}
