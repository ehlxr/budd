/*
 * The MIT License (MIT)
 *
 * Copyright © 2021 xrv <xrv@live.com>
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

package io.github.ehlxr.function;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;

/**
 * @author ehlxr
 * @since 2021-09-06 10:39.
 */
public class BlockerTaskFunction<T, R> extends RecursiveTask<R> {
    private static final long serialVersionUID = -4114798302790287199L;
    private final MyManagedBlockerImpl<T, R> blocker;

    public BlockerTaskFunction(Function<T, R> function, T t) {
        this.blocker = new MyManagedBlockerImpl<>(function, t);
    }

    @Override
    protected R compute() {
        try {
            ForkJoinPool.managedBlock(blocker);
            setRawResult(blocker.r);
            return getRawResult();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static class MyManagedBlockerImpl<T, R> implements ForkJoinPool.ManagedBlocker {
        private final Function<T, R> function;
        private final T t;
        private R r;
        private boolean done = false;

        public MyManagedBlockerImpl(Function<T, R> function, T t) {
            this.function = function;
            this.t = t;
        }

        @Override
        public boolean block() {
            r = function.apply(t);
            done = true;
            return true;
        }

        @Override
        public boolean isReleasable() {
            return done;
        }
    }
}
