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
import java.util.concurrent.RecursiveAction;

/**
 * @author ehlxr
 * @since 2020-12-30 17:59.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(sumOfSquares(ForkJoinPool.commonPool(), new double[]{1.2, 2.4, 3.5, 4.2, 5.0, 6, 7.6}));
    }

    static double sumOfSquares(ForkJoinPool pool, double[] array) {
        int n = array.length;
        Applyer a = new Applyer(array, 0, n, null);
        pool.invoke(a);
        return a.result;
    }

    static class Applyer extends RecursiveAction {
        final double[] array;
        final int lo, hi;
        double result;
        Applyer next; // keeps track of right-hand-side tasks

        Applyer(double[] array, int lo, int hi, Applyer next) {
            this.array = array;
            this.lo = lo;
            this.hi = hi;
            this.next = next;
        }

        double atLeaf(int l, int h) {
            double sum = 0;
            for (int i = l; i < h; ++i) {// perform leftmost base step}
                sum += array[i] * array[i];
            }
            return sum;
        }

        @Override
        protected void compute() {
            int l = lo;
            int h = hi;
            Applyer right = null;
            while (h - l > 1 && getSurplusQueuedTaskCount() <= 3) {
                int mid = (l + h) >>> 1;
                right = new Applyer(array, mid, h, right);
                right.fork();
                h = mid;
            }
            double sum = atLeaf(l, h);
            while (right != null) {
                if (right.tryUnfork()) {// directly calculate if not stolen}
                    sum += right.atLeaf(right.lo, right.hi);
                } else {
                    right.join();
                    sum += right.result;
                }
                right = right.next;
            }
            result = sum;
        }
    }
}
