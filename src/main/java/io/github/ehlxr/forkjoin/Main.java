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

package io.github.ehlxr.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * @author ehlxr
 * @since 2020-12-30 17:59.
 */
public class Main {

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        // Future<Integer> future = forkJoinPool.submit(new RecursiveTaskDemo(arr, 0, arr.length));
        // System.out.println("计算出来的总和="+future.get());


        int[] arr = IntStream.range(0, 1000).toArray();
        Integer integer = forkJoinPool.invoke(new RecursiveTaskDemo(arr, 0, arr.length));
        System.out.println("计算出来的总和=" + integer);

        // 关闭线程池
        forkJoinPool.shutdown();

        System.out.println(IntStream.range(0, 1000).parallel().reduce((i, j) -> i + j));
        System.out.println(IntStream.range(0, 1000).parallel().reduce(Integer::sum));
        System.out.println(IntStream.range(0, 1000).parallel().sum());
    }

    static class RecursiveTaskDemo extends RecursiveTask<Integer> {
        /**
         * 每个"小任务"最多只打印70个数
         */
        private static final int MAX = 100;
        private static final long serialVersionUID = -134954066152987391L;
        private final int[] arr;
        private final int start;
        private final int end;

        public RecursiveTaskDemo(int[] arr, int start, int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int sum = 0;
            // 当end-start的值小于MAX时候，开始打印
            if ((end - start) < MAX) {
                for (int i = start; i < end; i++) {
                    sum += arr[i];
                }
                return sum;
            } else {
                System.err.println("=====任务分解======");
                // 将大任务分解成两个小任务
                int middle = (start + end) / 2;
                RecursiveTaskDemo left = new RecursiveTaskDemo(arr, start, middle);
                RecursiveTaskDemo right = new RecursiveTaskDemo(arr, middle, end);
                // 并行执行两个小任务
                left.fork();
                right.fork();
                // 把两个小任务累加的结果合并起来
                return left.join() + right.join();
            }
        }

    }
}