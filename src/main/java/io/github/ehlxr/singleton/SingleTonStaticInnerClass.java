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

package io.github.ehlxr.singleton;

import java.util.stream.IntStream;

/**
 * @author ehlxr
 * @since 2021-02-18 11:23.
 */
public class SingleTonStaticInnerClass {
    private SingleTonStaticInnerClass() {
    }

    private static class HandlerInstance {
        private static SingleTonStaticInnerClass instance = new SingleTonStaticInnerClass();
    }

    public static SingleTonStaticInnerClass getInstance() {
        return HandlerInstance.instance;
    }

    public static void main(String[] args) {
        IntStream.range(0, 5).parallel().forEach(i -> System.out.println(Thread.currentThread().getName() + " => " + SingleTonStaticInnerClass.getInstance()));
    }

}