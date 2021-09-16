/*
 * The MIT License (MIT)
 *
 * Copyright © 2021 xrv <xrg@live.com>
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
import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;

/**
 * @author ehlxr
 * @since 2021-09-06 10:48.
 */
public class BlockerActionConsumer<T> extends RecursiveAction {
    private static final long serialVersionUID = 4942349095463615123L;
    private final MyManagedBlockerImpl<T> blocker;

    public BlockerActionConsumer(Consumer<T> consumer, T t) {
        this.blocker = new MyManagedBlockerImpl<>(consumer, t);
    }

    @Override
    protected void compute() {
        try {
            ForkJoinPool.managedBlock(blocker);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static class MyManagedBlockerImpl<T> implements ForkJoinPool.ManagedBlocker {
        private final Consumer<T> consumer;
        private final T t;
        private boolean done = false;

        public MyManagedBlockerImpl(Consumer<T> consumer, T t) {
            this.consumer = consumer;
            this.t = t;
        }

        @Override
        public boolean block() {
            consumer.accept(t);
            done = true;
            return true;
        }

        @Override
        public boolean isReleasable() {
            return done;
        }
    }
}
