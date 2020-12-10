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

package io.github.ehlxr.proxy;

import java.lang.reflect.Proxy;

/**
 * @author ehlxr
 * @since 2019-06-28.
 */
public class Main {
    public static void main(String[] args) {
        Flyable proxy = (Flyable) Proxy.newProxyInstance(Flyable.class.getClassLoader(),
                new Class[]{Flyable.class}, new MyInvocationHandler(new Bird()));

        // 动态代理会生成类似以下的 Java 代码
        /*
        package me.ehlxr.proxy;

        import java.lang.reflect.InvocationHandler;
        import java.lang.reflect.Method;

        public class Proxy implements Flyable {
            private InvocationHandler handler;

            public Proxy(InvocationHandler handler) {
                this.handler = handler;
            }

            @Override
            public void fly() {
                try {
                    Method method = Flyable.class.getMethod("fly");
                    this.handler.invoke(this, method, null);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
         */

        proxy.fly();
    }
}
