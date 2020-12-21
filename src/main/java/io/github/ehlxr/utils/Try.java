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
public class Try {
    public static <T> Try.C<T> of(Consumer<? super T> consumer) {
        return new Try.C<>(consumer);
    }

    public static <R> Try.S<R> of(Supplier<? extends R> supplier) {
        return new Try.S<>(supplier);
    }

    public static <T, R> Try.F<T, R> of(Function<? super T, ? extends R> function) {
        return new Try.F<>(function);
    }

    public static Try.V of(Void v) {
        return new Try.V(v);
    }

    public static class V {
        private final Void v;
        private Consumer<? super Throwable> th;

        V(Void v) {
            Objects.requireNonNull(v, "No value present");
            this.v = v;
        }

        /**
         * 计算结果
         */
        public void exec() {
            try {
                v.exec();
            } catch (Throwable e) {
                Optional.ofNullable(th).ifPresent(c -> c.accept(e));
            }
        }

        /**
         * 如果有异常，调用自定义异常处理表达式
         *
         * @param th 自定义异常处理 lambda 表达式
         * @return {@link V}
         */
        public V trap(Consumer<? super Throwable> th) {
            Objects.requireNonNull(th, "No value present");
            this.th = th;
            return this;
        }
    }

    public static class C<T> {
        private final Consumer<? super T> consumer;
        private Consumer<? super Throwable> th;

        C(Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer, "No value present");
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
                Optional.ofNullable(th).ifPresent(c -> c.accept(e));
            }
        }

        /**
         * 如果有异常，调用自定义异常处理表达式
         *
         * @param th 自定义异常处理 lambda 表达式
         * @return {@link C}
         */
        public C<T> trap(Consumer<? super Throwable> th) {
            Objects.requireNonNull(th, "No value present");
            this.th = th;
            return this;
        }
    }

    public static class S<R> {
        private final Supplier<? extends R> supplier;
        private Consumer<? super Throwable> th;

        S(Supplier<? extends R> supplier) {
            Objects.requireNonNull(supplier, "No value present");
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
                Optional.ofNullable(th).ifPresent(c -> c.accept(e));
                return r;
            }
        }

        public R get() {
            try {
                return supplier.get();
            } catch (Throwable e) {
                Optional.ofNullable(th).ifPresent(c -> c.accept(e));
                return null;
            }
        }

        /**
         * 如果有异常，调用自定义异常处理表达式
         *
         * @param th 自定义异常处理 lambda 表达式
         * @return {@link S}
         */
        public S<R> trap(Consumer<? super Throwable> th) {
            Objects.requireNonNull(th, "No value present");
            this.th = th;
            return this;
        }
    }

    public static class F<T, R> {
        private final Function<? super T, ? extends R> function;
        private Consumer<? super Throwable> th;

        F(Function<? super T, ? extends R> function) {
            Objects.requireNonNull(function, "No value present");
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
                Optional.ofNullable(th).ifPresent(c -> c.accept(e));
                return r;
            }
        }

        public R get(T t) {
            try {
                return function.apply(t);
            } catch (Throwable e) {
                Optional.ofNullable(th).ifPresent(c -> c.accept(e));
                return null;
            }
        }

        /**
         * 如果有异常，调用自定义异常处理表达式
         *
         * @param th 自定义异常处理 lambda 表达式
         * @return {@link F}
         */
        public F<T, R> trap(Consumer<? super Throwable> th) {
            Objects.requireNonNull(th, "No value present");
            this.th = th;
            return this;
        }
    }

    @FunctionalInterface
    public interface Void {
        void exec() throws Throwable;
    }

    @SuppressWarnings("ConstantConditions")
    public static void main(String[] args) {
        // 有返回值，无入参
        System.out.println(Try.of(() -> Long.valueOf("s")).trap(System.out::println).get(0L));
        System.out.println(Try.of(() -> Long.valueOf("2w1")).get());

        // 有返回值，有入参
        System.out.println(Try.<String, Long>of(Long::valueOf).trap(e -> System.out.println("exception is: " + e.getMessage())).get("s"));

        ArrayList<String> list = null;

        // 无返回值，无入参
        Try.of(() -> Thread.sleep(-1L)).exec();

        // 无返回值，有入参
        Try.<String>
                of(v -> list.add(0, v))
                .trap(e -> System.out.println("222222" + e.getMessage()))
                .accept("test");
    }
}
