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

package io.github.ehlxr.algorithm.match;

/**
 * 字符串匹配：暴力匹配
 *
 * @author ehlxr
 * @since 2022-03-12 13:36.
 */
public class BruteForce {
    public static void main(String[] args) {
        String s = "abdcfhjudfadf";
        String p = "dcf";

        System.out.println(bf(s, p));
    }

    public static int bf(String s, String p) {
        int m = s.length();
        int n = p.length();

        for (int i = 0; i <= m - n; i++) {
            int j = 0;
            for (; j < n; j++) {
                // if (s.charAt(i) == p.charAt(j)) {
                //     i++;
                // } else {
                //     break;
                // }
                // 如果主串与模式串不匹配，则主串向右移动一个字符,模式串从头开始匹配
                if (s.charAt(i + j) != p.charAt(j)) {
                    break;
                }
            }
            if (j == n) {
                // return i - n;
                return i;
            }
        }

        return -1;
    }
}
