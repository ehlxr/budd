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

import java.lang.reflect.Constructor;
import java.util.stream.IntStream;

/**
 * double check lock
 * <p>
 * 懒汉模式
 *
 * @author ehlxr
 * @since 2021-02-18 10:23.
 */
public class SingletonDcl {
    private SingletonDcl() {
    }

    private volatile static SingletonDcl INSTANCE;

    public static SingletonDcl getInstance() {
        if (INSTANCE == null) {
            synchronized (SingletonDcl.class) {
                if (INSTANCE == null) {
                    // 非原子性操作
                    // 有可能会出现 JVM 优化指令重排序
                    INSTANCE = new SingletonDcl();
                }
            }
        }

        return INSTANCE;
    }

    public static void main(String[] args) throws Exception {
        IntStream.range(0, 5).parallel().forEach(i -> System.out.println(Thread.currentThread().getName() + " => " + SingletonDcl.getInstance()));

        // 反射破坏
        Constructor<SingletonDcl> dc = SingletonDcl.class.getDeclaredConstructor();
        dc.setAccessible(true);

        System.out.println(dc.newInstance());
        System.out.println(dc.newInstance());
        System.out.println(dc.newInstance());
    }
}
