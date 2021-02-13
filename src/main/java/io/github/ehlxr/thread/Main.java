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

package io.github.ehlxr.thread;

/**
 * @author ehlxr
 * @since 2021-02-13 17:29.
 */
public class Main {
    private static volatile boolean flag = false;

    /**
     * 1、子线程阻塞主线程将会结束
     * 2、JVM 会等待待所有子线程结束
     * <p>
     * When a Java Virtual Machine starts up, there is usually a single
     * non-daemon thread (which typically calls the method named main of some
     * designated class). The Java Virtual Machine continues to execute
     * threads until either of the following occurs:
     * <p>
     * The exit method of class Runtime has been called and the security manager has permitted the exit operation to take place.
     * All threads that are not daemon threads have died, either by returning from the call to the run method or by throwing an exception
     * that propagates beyond the run method.
     */
    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            System.out.println("wating data...." + Thread.currentThread().getName());
            while (!flag) {
            }

            System.out.println("complete!" + Thread.currentThread().getName());

        }).start();

        Thread.sleep(2000);

        new Thread(() -> {
            System.out.println("prepare data..." + Thread.currentThread().getName());
            flag = true;

            System.out.println("prepare data end..." + Thread.currentThread().getName());
        }).start();
    }
}
