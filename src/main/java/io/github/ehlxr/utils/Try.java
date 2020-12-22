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

/**
 * 异常处理，简化 try catch
 *
 * @author ehlxr
 * @since 2020-12-03 10:37.
 */
public interface Try {
    /**
     * 构建消费型（有入参，无返回）Tryable 对象
     *
     * @param consumer {@link ThrowableConsumer} 类型函数式接口
     * @param <T>      入参类型
     * @return {@link TryConsumer}
     */
    static <T> TryConsumer<T> of(ThrowableConsumer<? super T> consumer) {
        return new TryConsumer<>(consumer);
    }

    /**
     * 构建供给型（无入参，有返回）Tryable 对象
     *
     * @param supplier {@link ThrowableSupplier} 类型函数式接口
     * @param <R>      返回值类型
     * @return {@link TrySupplier}
     */
    static <R> TrySupplier<R> of(ThrowableSupplier<? extends R> supplier) {
        return new TrySupplier<>(supplier);
    }

    /**
     * 构建功能型（有入参，有返回）Tryable 对象
     *
     * @param function {@link ThrowableFunction} 类型函数式接口
     * @param <T>      入参类型
     * @param <R>      返回值类型
     * @return {@link TryFunction}
     */
    static <T, R> TryFunction<T, R> of(ThrowableFunction<? super T, ? extends R> function) {
        return new TryFunction<>(function);
    }

    /**
     * 构建运行型（无入参，无返回）Tryable 对象
     *
     * @param runnable {@link ThrowableRunnable} 类型函数式接口
     * @return {@link TryRunnable}
     */
    static TryRunnable of(ThrowableRunnable runnable) {
        return new TryRunnable(runnable);
    }

    abstract class Tryable<C> {
        Consumer<? super Throwable> throwConsumer;
        ThrowableRunnable finallyRunnable;
        Consumer<? super Throwable> finallyThrowConsumer;
        C c;

        /**
         * 处理 finally
         */
        protected void doFinally() {
            Optional.ofNullable(finallyRunnable).ifPresent(r -> {
                try {
                    r.run();
                } catch (final Throwable t) {
                    Optional.ofNullable(finallyThrowConsumer).ifPresent(c -> c.accept(t));
                }
            });
        }

        /**
         * 如果有异常，调用自定义异常处理表达式
         *
         * @param throwableConsumer 自定义异常处理 lambda 表达式
         * @return {@link C}
         */
        public C trap(Consumer<? super Throwable> throwableConsumer) {
            Objects.requireNonNull(throwableConsumer, "No throwableConsumer present");
            this.throwConsumer = throwableConsumer;
            return c;
        }

        /**
         * 自定义 finally 处理表达式
         *
         * @param finallyRunnable finally 处理 lambda 表达式
         * @return {@link C}
         */
        public C andFinally(ThrowableRunnable finallyRunnable) {
            Objects.requireNonNull(finallyRunnable, "No finallyRunnable present");
            this.finallyRunnable = finallyRunnable;
            return c;
        }

        /**
         * 如果 finally 有异常，调用自定义异常处理表达式
         *
         * @param finallyThrowableConsumer 自定义异常处理 lambda 表达式
         * @return {@link C}
         */
        public C finallyTrap(Consumer<? super Throwable> finallyThrowableConsumer) {
            Objects.requireNonNull(finallyThrowableConsumer, "No finallyThrowableConsumer present");
            this.finallyThrowConsumer = finallyThrowableConsumer;
            return c;
        }
    }

    class TryRunnable extends Tryable<TryRunnable> {
        private final ThrowableRunnable runnable;

        protected TryRunnable(ThrowableRunnable runnable) {
            Objects.requireNonNull(runnable, "No runnable present");
            this.runnable = runnable;

            super.c = this;
        }

        /**
         * 计算结果
         */
        public void run() {
            try {
                runnable.run();
            } catch (final Throwable e) {
                Optional.ofNullable(throwConsumer).ifPresent(c -> c.accept(e));
            } finally {
                doFinally();
            }
        }
    }

    class TryConsumer<T> extends Tryable<TryConsumer<T>> {
        private final ThrowableConsumer<? super T> consumer;

        protected TryConsumer(ThrowableConsumer<? super T> consumer) {
            Objects.requireNonNull(consumer, "No consumer present");
            this.consumer = consumer;

            super.c = this;
        }

        /**
         * 计算结果
         *
         * @param t 要计算的入参
         */
        public void accept(T t) {
            try {
                Objects.requireNonNull(t, "No accept t present");

                consumer.accept(t);
            } catch (final Throwable e) {
                Optional.ofNullable(throwConsumer).ifPresent(c -> c.accept(e));
            } finally {
                doFinally();
            }
        }
    }

    class TrySupplier<R> extends Tryable<TrySupplier<R>> {
        private final ThrowableSupplier<? extends R> supplier;

        protected TrySupplier(ThrowableSupplier<? extends R> supplier) {
            Objects.requireNonNull(supplier, "No supplier present");
            this.supplier = supplier;

            super.c = this;
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
            } catch (final Throwable e) {
                Optional.ofNullable(throwConsumer).ifPresent(c -> c.accept(e));
                return r;
            } finally {
                doFinally();
            }
        }

        /**
         * 如果有异常返回 null，否则返回计算结果
         *
         * @return 实际值或 null
         */
        public R get() {
            try {
                return supplier.get();
            } catch (final Throwable e) {
                Optional.ofNullable(throwConsumer).ifPresent(c -> c.accept(e));
                return null;
            } finally {
                doFinally();
            }
        }
    }

    class TryFunction<T, R> extends Tryable<TryFunction<T, R>> {
        private final ThrowableFunction<? super T, ? extends R> function;
        private T t;

        protected TryFunction(ThrowableFunction<? super T, ? extends R> function) {
            Objects.requireNonNull(function, "No function present");
            this.function = function;

            super.c = this;
        }

        /**
         * 传入要计算的入参
         *
         * @param t 要计算的入参
         * @return {@link TryFunction}
         */
        public TryFunction<T, R> apply(T t) {
            Objects.requireNonNull(t, "Apply t should not null");

            this.t = t;
            return this;
        }

        /**
         * 如果有异常返回默认值，否则返回计算结果
         *
         * @param r 指定默认值
         * @return 实际值或默认值
         */
        public R get(R r) {
            try {
                Objects.requireNonNull(function, "No apply t present");

                return function.apply(t);
            } catch (final Throwable e) {
                Optional.ofNullable(throwConsumer).ifPresent(c -> c.accept(e));
                return r;
            } finally {
                doFinally();
            }
        }

        /**
         * 如果有异常返回 null，否则返回计算结果
         *
         * @return 实际值或 null
         */
        public R get() {
            try {
                Objects.requireNonNull(t, "No apply t present");

                return function.apply(t);
            } catch (final Throwable e) {
                Optional.ofNullable(throwConsumer).ifPresent(c -> c.accept(e));
                return null;
            } finally {
                doFinally();
            }
        }
    }

    @FunctionalInterface
    interface ThrowableRunnable {
        /**
         * Performs this operation
         *
         * @throws Throwable throwable
         */
        void run() throws Throwable;
    }

    @FunctionalInterface
    interface ThrowableConsumer<T> {
        /**
         * Performs this operation on the given argument.
         *
         * @param t the input argument
         * @throws Throwable throwable
         */
        void accept(T t) throws Throwable;
    }

    @FunctionalInterface
    interface ThrowableSupplier<T> {
        /**
         * Gets a result.
         *
         * @return a result
         * @throws Throwable throwable
         */
        T get() throws Throwable;
    }

    @FunctionalInterface
    interface ThrowableFunction<T, R> {
        /**
         * Applies this function to the given argument.
         *
         * @param t the function argument
         * @return the function result
         * @throws Throwable throwable
         */
        R apply(T t) throws Throwable;
    }

    @SuppressWarnings({"ConstantConditions", "Convert2MethodRef"})
    static void main(String[] args) {
        // 有返回值，无入参
        String param = "s";
        Long result = Try.of(() -> Long.valueOf(param)).get(0L);
        System.out.println("Long.valueOf 1: " + result);

        result = Try.of(() -> Long.valueOf(param)).get();
        System.out.println("Long.valueOf 2: " + result);

        // 有返回值，有入参
        result = Try.<String, Long>of(s -> Long.valueOf(s))
                .apply(param)
                .trap((e) -> System.out.println("Long.valueOf exception: " + e.getMessage()))
                .andFinally(() -> System.out.println("Long.valueOf finally run code."))
                .finallyTrap((e) -> System.out.println("Long.valueOf finally exception: " + e.getMessage()))
                .get();
        System.out.println("Long.valueOf 3: " + result);

        ArrayList<String> list = null;

        // 无返回值，无入参
        Try.of(() -> Thread.sleep(-1L))
                .andFinally(() -> list.clear())
                // .andFinally(list::clear) //https://stackoverflow.com/questions/37413106/java-lang-nullpointerexception-is-thrown-using-a-method-reference-but-not-a-lamb
                .run();

        // 无返回值，有入参
        Try.<String>of(v -> list.add(0, v)).accept("test");
    }
}
