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

package io.github.ehlxr.test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Created by ehlxr on 2016/12/15.
 */
public class Test {
    public static void main(String[] args) {
        // String s0 = "kvill";
        // String s1 = "kvill";
        // String s2 = "kvill";
        // System.out.println(s0 == s1);
        // System.out.println("**********");
        // s1.intern();
        // s2 = s2.intern(); //把常量池中"kvill"的引用赋给s2
        // System.out.println(s0 == s1);
        // System.out.println(s0 == s1.intern());
        // System.out.println(s0 == s2);


        // LinkedHashMap
        System.out.println("*****LinkedHashMap*****");

        LinkedHashMap<Object, Object> map = new LinkedHashMap<>();
        map.put("a", "111");
        map.put("b", "222");
        map.put("c", "333");
        map.put("d", "444");
        map.put("a", "555");

        System.out.println(map);


        // The String "[George:Sally:Fred]" may be constructed as follows:

        StringJoiner sj = new StringJoiner(":", "[", "]");
        sj.add("George").add("Sally").add("Fred");
        System.out.println(sj);
        // A StringJoiner may be employed to create formatted output from a java.util.stream.Stream using java.util.stream.Collectors.joining(CharSequence). For example:

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        String commaSeparatedNumbers = numbers.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        System.out.println(commaSeparatedNumbers);


        System.out.println("------------" + (-1 >>> Integer.numberOfLeadingZeros(3)));

        String s = new String("1");
        String t = new String("1");
        String s1 = s.intern();
        String s2 = "1";
        System.out.println(s == s1);
        System.out.println(s1 == s2);
        System.out.println(s == t);   // false
        System.out.println(s.intern() == t.intern());   // true
    }
}
