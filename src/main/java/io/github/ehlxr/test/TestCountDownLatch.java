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

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class TestCountDownLatch {
    private static final int N = 10;

    private static final Map<String, Object> cache = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch doneSignal = new CountDownLatch(N);

        for (int i = 1; i <= N; i++) {
            new Thread(new Worker(i, doneSignal)).start();// 线程启动了
        }
        System.out.println("begin------------");
        doneSignal.await();// 等待所有的线程执行完毕
        System.out.println("Ok");

        System.out.println(cache.size());

        Set<String> keySet = cache.keySet();
        for (String key : keySet) {
            System.out.println(key + " = " + cache.get(key));
        }
    }

    static class Worker implements Runnable {
        private final CountDownLatch doneSignal;
        private int beginIndex;

        Worker(int beginIndex, CountDownLatch doneSignal) {
            this.beginIndex = beginIndex;
            this.doneSignal = doneSignal;
        }

        public void run() {
            beginIndex = (beginIndex - 1) * 10 + 1;
            for (int i = beginIndex; i <= beginIndex + 10; i++) {
                cache.put(i + "key", i);
            }
            doneSignal.countDown();
        }
    }
}