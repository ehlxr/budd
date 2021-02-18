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

package io.github.ehlxr.rate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 按比例控制流量
 *
 * @author ehlxr
 * @since 2019-07-19.
 */
public class RateBarrier {
    private final AtomicInteger op = new AtomicInteger(0);
    private List<Integer> source;
    private int base;

    public int rate() {
        return source.get(op.incrementAndGet() % base);
    }

    private RateBarrier() {
    }

    public RateBarrier(int base) {
        this.base = base;

        source = new ArrayList<>(base);
        for (int i = 0; i < base; i++) {
            source.add(i);
        }

        // 打乱集合顺序
        Collections.shuffle(source);
    }

    public static void main(String[] args) {
        RateBarrier rateBarrier = new RateBarrier(10);

        IntStream.range(0, 20).parallel().forEach(i -> {
            int rate = rateBarrier.rate();
            if (rate < 2) {
                System.out.println("this is on 2");
            } else if (rate < 5) {
                System.out.println("this is on 3");
            } else {
                System.out.println("this is on 5");
            }
        });

        // final Thread[] threads = new Thread[20];
        // for (int i = 0; i < threads.length; i++) {
        //     threads[i] = new Thread(() -> {
        //         if (rateBarrier.allow()) {
        //             System.out.println("this is on 3");
        //         } else {
        //             System.out.println("this is on 7");
        //         }
        //     });
        //     threads[i].start();
        // }
        //
        // for (Thread t : threads) {
        //     try {
        //         t.join();
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // }

    }
}
