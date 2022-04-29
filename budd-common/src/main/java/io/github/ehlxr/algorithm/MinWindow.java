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

package io.github.ehlxr.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ehlxr
 * @since 2022-04-29 11:07.
 */
public class MinWindow {
    public static void main(String[] args) {
        System.out.println(minWindow("cabwefgewcwaefgcf", "cae"));
        System.out.println(minWindow("bba", "ab"));
        System.out.println(minWindow("ADOBECODEBANC", "ABC"));
        System.out.println(minWindow("a", "a"));
        System.out.println(minWindow("a", "b"));
        System.out.println(minWindow("", "a"));
        System.out.println(minWindow("", ""));
        System.out.println(minWindow("a", "aa"));
        System.out.println(minWindow("aa", "a"));
    }

    public static String minWindow(String s, String t) {
        Map<Character, Integer> windows = new HashMap<>();
        Map<Character, Integer> needs = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            needs.put(t.charAt(i), needs.getOrDefault(t.charAt(i), 0) + 1);
        }

        int left = 0, right = 0, valid = 0, start = -1, len = Integer.MAX_VALUE;
        while (right < s.length()) {
            char r = s.charAt(right);

            if (needs.containsKey(r)) {
                windows.put(r, windows.getOrDefault(r, 0) + 1);
                if (windows.get(r).equals(needs.get(r))) {
                    valid++;
                }
            }
            right++;

            while (valid == needs.size()) {
                if (right - left < len) {
                    len = right - left;
                    start = left;
                }

                char l = s.charAt(left);
                left++;

                if (windows.containsKey(l)) {
                    if (windows.get(l).equals(needs.get(l))) {
                        valid--;
                    }
                    windows.put(l, windows.get(l) - 1);
                }
            }
        }

        return start > -1 ? s.substring(start, start + len) : "";
    }
}
