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

package io.github.ehlxr;

import com.google.common.base.Stopwatch;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ehlxr
 * @since 2021-09-02 15:52.
 */
public class ManagedBlockerTest {
    static String threadDateTimeInfo() {
        return DateTimeFormatter.ISO_TIME.format(LocalTime.now()) + Thread.currentThread().getName();
    }

    static void test1() {
        List<RecursiveTask<String>> tasks = Stream.generate(() -> new RecursiveTask<String>() {
            private static final long serialVersionUID = 1303648247573881735L;

            @Override
            protected String compute() {
                System.out.println(threadDateTimeInfo() + ":simulate io task blocking for 2 seconds···");
                try {
                    //线程休眠2秒模拟IO调用阻塞
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    throw new Error(e);
                }
                return threadDateTimeInfo() + ": io blocking task returns successfully";
            }
        }).limit(28).collect(Collectors.toList());

        tasks.forEach(ForkJoinTask::fork);
        tasks.forEach(e -> {
            try {
                System.out.println(e.get());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        Stopwatch started = Stopwatch.createStarted();
        test4();
        System.out.println(started);
    }

    static void test2() {
        List<IoBlockerTask<String>> tasks = Stream.generate(() -> new IoBlockerTask<>(() -> {
            System.out.println(threadDateTimeInfo() + ":simulate io task blocking for 2 seconds···");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new Error(e);
            }
            return threadDateTimeInfo() + ": io blocking task returns successfully";
        })).limit(28).collect(Collectors.toList());

        tasks.forEach(ForkJoinTask::fork);
        tasks.forEach(e -> {
            try {
                System.out.println(e.get());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }

    static void test3() {
        List<IoBlockerAction> actions = Stream.generate(() -> new IoBlockerAction(() -> {
            System.out.println(threadDateTimeInfo() + ":simulate io action blocking for 2 seconds···");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new Error(e);
            }
            System.out.println(threadDateTimeInfo() + ": io blocking action successfully");
        })).limit(56).collect(Collectors.toList());

        //如果不使用 ManagedBlocker 耗时估算： 56/7（8 cpu - 1） = 8 * 2 =16s，实际 约等于 2s

        actions.forEach(ForkJoinTask::fork);
        actions.forEach(ForkJoinTask::join);
    }

    static void test4() {
        List<IoBlockerAction2<Integer>> actions = Stream.generate(() -> new IoBlockerAction2<>((x) -> {
            System.out.println(threadDateTimeInfo() + ":simulate io action blocking for 2 seconds···" + x);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new Error(e);
            }
            System.out.println(threadDateTimeInfo() + ": io blocking action successfully " + x);
        }, 1)).limit(56).collect(Collectors.toList());

        //如果不使用 ManagedBlocker 耗时估算： 56/7（8 cpu - 1） = 8 * 2 =16s，实际 约等于 2s

        actions.forEach(x -> {
            x.fork();
            System.out.println("fork {}"+ x);
        });
        actions.forEach(x -> {
            x.join();
            System.out.println("join {}"+ x);
        });
    }

}

class IoBlockerAction2<T> extends RecursiveAction {
    private static final long serialVersionUID = -4114798302790287199L;
    private final MyManagedBlockerImpl<T> blocker;

    public IoBlockerAction2(Consumer<T> consumer, T t) {
        this.blocker = new MyManagedBlockerImpl<>(consumer, t);
    }

    static class MyManagedBlockerImpl<T> implements ForkJoinPool.ManagedBlocker {
        private final Consumer<T> consumer;
        private final T t;

        public MyManagedBlockerImpl(Consumer<T> consumer, T t) {
            this.consumer = consumer;
            this.t = t;
        }

        @Override
        public boolean block() {
            consumer.accept(t);
            return true;
        }

        @Override
        public boolean isReleasable() {
            return false;
        }
    }

    @Override
    protected void compute() {
        try {
            ForkJoinPool.managedBlock(blocker);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class IoBlockerAction extends RecursiveAction {
    private static final long serialVersionUID = -4114798302790287199L;
    private final MyManagedBlockerImpl blocker;

    public IoBlockerAction(Runnable runnable) {
        this.blocker = new MyManagedBlockerImpl(runnable);
    }

    static class MyManagedBlockerImpl implements ForkJoinPool.ManagedBlocker {
        private final Runnable runnable;

        public MyManagedBlockerImpl(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public boolean block() {
            runnable.run();
            return true;
        }

        @Override
        public boolean isReleasable() {
            return false;
        }
    }

    @Override
    protected void compute() {
        try {
            ForkJoinPool.managedBlock(blocker);
            // setRawResult(blocker.result);
            // return getRawResult();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class IoBlockerTask<T> extends RecursiveTask<T> {
    private static final long serialVersionUID = -4114798302790287199L;
    private final MyManagedBlockerImpl<T> blocker;

    public IoBlockerTask(Supplier<T> supplier) {
        this.blocker = new MyManagedBlockerImpl<>(supplier);
    }

    static class MyManagedBlockerImpl<T> implements ForkJoinPool.ManagedBlocker {
        private final Supplier<T> supplier;
        private T result;

        public MyManagedBlockerImpl(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        @Override
        public boolean block() {
            result = supplier.get();
            return true;
        }

        @Override
        public boolean isReleasable() {
            return false;
        }
    }

    @Override
    protected T compute() {
        try {
            ForkJoinPool.managedBlock(blocker);
            setRawResult(blocker.result);
            return getRawResult();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
