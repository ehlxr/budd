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

package io.github.ehlxr.utils;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 异常处理，简化 try catch
 *
 * @author ehlxr
 * @since 2020-12-03 10:37.
 */
public interface Try {
    static <T> Try.C<T> of(Consumer<? super T> consumer) {
        return new Try.C<>(consumer);
    }

    static <R> Try.S<R> of(Supplier<? extends R> supplier) {
        return new Try.S<>(supplier);
    }

    static <T, R> Try.F<T, R> of(Function<? super T, ? extends R> function) {
        return new Try.F<>(function);
    }

    static Try.V of(CheckedRunnable0 v) {
        return new Try.V(v);
    }

    class V {
        private final CheckedRunnable0 checkedRunnable0;
        private Consumer<? super Throwable> throwableConsumer;
        private CheckedRunnable1 finallyRunnable;

        V(CheckedRunnable0 checkedRunnable0) {
            Objects.requireNonNull(checkedRunnable0, "No checkedRunnable0 present");
            this.checkedRunnable0 = checkedRunnable0;
        }

        /**
         * 计算结果
         */
        public void run() {
            try {
                checkedRunnable0.run();
            } catch (Throwable e) {
                Optional.ofNullable(throwableConsumer).ifPresent(c -> c.accept(e));
            } finally {
                Optional.ofNullable(finallyRunnable).ifPresent(CheckedRunnable1::run);
            }
        }

        /**
         * 如果有异常，调用自定义异常处理表达式
         *
         * @param throwableConsumer 自定义异常处理 lambda 表达式
         * @return {@link V}
         */
        public V trap(Consumer<? super Throwable> throwableConsumer) {
            Objects.requireNonNull(throwableConsumer, "No throwableConsumer present");
            this.throwableConsumer = throwableConsumer;
            return this;
        }

        /**
         * 自定义 finally 处理表达式
         *
         * @param finallyRunnable finally 处理 lambda 表达式
         * @return {@link V}
         */
        public V andFinally(CheckedRunnable1 finallyRunnable) {
            Objects.requireNonNull(finallyRunnable, "No finallyRunnable present");
            this.finallyRunnable = finallyRunnable;
            return this;
        }
    }

    class C<T> {
        private final Consumer<? super T> consumer;
        private Consumer<? super Throwable> throwableConsumer;
        private CheckedRunnable1 finallyRunnable;

        C(Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer, "No consumer present");
            this.consumer = consumer;
        }

        /**
         * 计算结果
         *
         * @param t 要计算的入参
         */
        public void accept(T t) {
            try {
                consumer.accept(t);
            } catch (Throwable e) {
                Optional.ofNullable(throwableConsumer).ifPresent(c -> c.accept(e));
            } finally {
                Optional.ofNullable(finallyRunnable).ifPresent(CheckedRunnable1::run);
            }
        }

        /**
         * 如果有异常，调用自定义异常处理表达式
         *
         * @param throwableConsumer 自定义异常处理 lambda 表达式
         * @return {@link C}
         */
        public C<T> trap(Consumer<? super Throwable> throwableConsumer) {
            Objects.requireNonNull(throwableConsumer, "No throwableConsumer present");
            this.throwableConsumer = throwableConsumer;
            return this;
        }

        /**
         * 自定义 finally 处理表达式
         *
         * @param finallyRunnable finally 处理 lambda 表达式
         * @return {@link C}
         */
        public C<T> andFinally(CheckedRunnable1 finallyRunnable) {
            Objects.requireNonNull(finallyRunnable, "No finallyRunnable present");
            this.finallyRunnable = finallyRunnable;
            return this;
        }
    }

    class S<R> {
        private final Supplier<? extends R> supplier;
        private Consumer<? super Throwable> throwableConsumer;
        private CheckedRunnable1 finallyRunnable;

        S(Supplier<? extends R> supplier) {
            Objects.requireNonNull(supplier, "No supplier present");
            this.supplier = supplier;
        }

        /**
         * 如果有异常返回默认值，否则返回计算结果
         *
         * @param r 指定默认值
         * @return 实际值或默认值
         */
        public R get(R r) {
            try {
                return supplier.get();
            } catch (Throwable e) {
                Optional.ofNullable(throwableConsumer).ifPresent(c -> c.accept(e));
                return r;
            } finally {
                Optional.ofNullable(finallyRunnable).ifPresent(CheckedRunnable1::run);
            }
        }

        public R get() {
            try {
                return supplier.get();
            } catch (Throwable e) {
                Optional.ofNullable(throwableConsumer).ifPresent(c -> c.accept(e));
                return null;
            } finally {
                Optional.ofNullable(finallyRunnable).ifPresent(CheckedRunnable1::run);
            }
        }

        /**
         * 如果有异常，调用自定义异常处理表达式
         *
         * @param throwableConsumer 自定义异常处理 lambda 表达式
         * @return {@link S}
         */
        public S<R> trap(Consumer<? super Throwable> throwableConsumer) {
            Objects.requireNonNull(throwableConsumer, "No throwableConsumer present");
            this.throwableConsumer = throwableConsumer;
            return this;
        }

        /**
         * 自定义 finally 处理表达式
         *
         * @param finallyRunnable finally 处理 lambda 表达式
         * @return {@link S}
         */
        public S<R> andFinally(CheckedRunnable1 finallyRunnable) {
            Objects.requireNonNull(finallyRunnable, "No finallyRunnable present");
            this.finallyRunnable = finallyRunnable;
            return this;
        }
    }

    class F<T, R> {
        private final Function<? super T, ? extends R> function;
        private Consumer<? super Throwable> throwableConsumer;
        private CheckedRunnable1 finallyRunnable;

        F(Function<? super T, ? extends R> function) {
            Objects.requireNonNull(function, "No function present");
            this.function = function;
        }

        /**
         * 如果有异常忽略并返回默认值，否则返回计算结果
         *
         * @param t 要计算的入参
         * @param r 指定默认值
         * @return 实际值或默认值
         */
        public R get(T t, R r) {
            try {
                return function.apply(t);
            } catch (Throwable e) {
                Optional.ofNullable(throwableConsumer).ifPresent(c -> c.accept(e));
                return r;
            } finally {
                Optional.ofNullable(finallyRunnable).ifPresent(CheckedRunnable1::run);
            }
        }

        public R get(T t) {
            try {
                return function.apply(t);
            } catch (Throwable e) {
                Optional.ofNullable(throwableConsumer).ifPresent(c -> c.accept(e));
                return null;
            } finally {
                Optional.ofNullable(finallyRunnable).ifPresent(CheckedRunnable1::run);
            }
        }

        /**
         * 如果有异常，调用自定义异常处理表达式
         *
         * @param throwableConsumer 自定义异常处理 lambda 表达式
         * @return {@link F}
         */
        public F<T, R> trap(Consumer<? super Throwable> throwableConsumer) {
            Objects.requireNonNull(throwableConsumer, "No throwableConsumer present");
            this.throwableConsumer = throwableConsumer;
            return this;
        }

        /**
         * 自定义 finally 处理表达式
         *
         * @param finallyRunnable finally 处理 lambda 表达式
         * @return {@link F}
         */
        public F<T, R> andFinally(CheckedRunnable1 finallyRunnable) {
            Objects.requireNonNull(finallyRunnable, "No finallyRunnable present");
            this.finallyRunnable = finallyRunnable;
            return this;
        }
    }

    @FunctionalInterface
    interface CheckedRunnable0 {
        void run() throws Throwable;
    }

    @FunctionalInterface
    interface CheckedRunnable1 {
        void run();
    }

    @SuppressWarnings("ConstantConditions")
    static void main(String[] args) {
        // 有返回值，无入参
        System.out.println(Try.of(() -> Long.valueOf("s")).trap(System.out::println).get(0L));
        System.out.println(Try.of(() -> Long.valueOf("2w1")).get());

        // 有返回值，有入参
        System.out.println(Try.<String, Long>of(Long::valueOf).trap(e -> System.out.println("exception is: " + e.getMessage())).get("s"));

        ArrayList<String> list = null;

        // 无返回值，无入参
        Try.of(() -> Thread.sleep(-1L)).andFinally(() -> System.out.println("ddf")).trap(System.out::println).run();

        // 无返回值，有入参
        Try.<String>
                of(v -> list.add(0, v))
                .trap(e -> System.out.println("222222" + e.getMessage()))
                .accept("test");
    }
}
