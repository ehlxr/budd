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

package io.github.ehlxr.algorithm.match;

import java.util.Arrays;

/**
 * 字符串匹配算法 KPM
 *
 * @author ehlxr
 * @since 2022-03-13 10:06.
 */
public class Kmp {
    public static void main(String[] args) {
        String s = "bacbababaabcbab";
        String p = "abab";

        System.out.println(Arrays.toString(getNexts(p)));
        System.out.println(kmp(s, p));
    }

    public static int kmp(String s, String p) {
        char[] scs = s.toCharArray();
        char[] pcs = p.toCharArray();

        int m = s.length();
        int n = p.length();

        int[] next = getNexts(p);

        for (int i = 0; i <= m - n; i++) {
            int j = 0;
            while (j < n) {
                if (scs[i] == pcs[j]) {
                    i++;
                    j++;
                } else {
                    // 当模式串与主串不匹配时，如果**不匹配字符**对应模式串下标大于 j > 0 (非首个模式串字符)，
                    // 并且此字符前一个字符对应字符串部分匹配表中的值 next[j - 1] 也大于 0，
                    // 模式串后移到 next[j - 1] 位置
                    if (j > 0 && next[j - 1] > 0) {
                        j = next[j - 1];
                    } else {
                        break;
                    }
                }
            }
            if (j == n) {
                return i - n;
            }
        }

        return -1;
    }

    private static int[] getNexts(String p) {
        int m = p.length();
        char[] b = p.toCharArray();
        int[] next = new int[m];

        next[0] = 0;
        int k = 0; // 表示前后缀相匹配的最大长度

        for (int i = 1; i < m; ++i) {
            // k 为 b[0, i-1] 子串最大匹配前、后缀长度
            // b[0, k] 为 b[0, i-1] 子串最大匹配前缀子串
            while (k != 0 && b[k] != b[i]) {
                // 若：b[k] != b[i]，则求 b[0, i] 子串最大匹配前、后缀长度问题转换成了求 b[0, k] 子串最大匹配前、后缀长度问题
                k = next[k];
            }
            if (b[k] == b[i]) {
                // 若：b[k] == b[i]，则 b[0, i] 子串最大匹配前、后缀长度为 k + 1
                ++k;
            }
            next[i] = k;
        }
        return next;
    }
}
