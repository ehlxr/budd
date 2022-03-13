/*
 * The MIT License (MIT)
 *
 * Copyright © 2020 xrv <xrv@live.com>
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

package io.github.ehlxr.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.Map;
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
     * @param <P>      入参类型
     * @return {@link TryConsumer}
     */
    static <P> TryConsumer<P> of(ThrowableConsumer<? super P> consumer) {
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
     * @param <P>      入参类型
     * @param <R>      返回值类型
     * @return {@link TryFunction}
     */
    static <P, R> TryFunction<P, R> of(ThrowableFunction<? super P, ? extends R> function) {
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

    @SuppressWarnings({"ConstantConditions", "Convert2MethodRef"})
    static void main(String[] args) {
        System.out.println("------------有返回值，无入参----------------");
        // 有返回值，无入参
        String param = "hello";
        Long result = Try.of(() -> Long.valueOf(param)).get(0L);
        System.out.println("Long.valueOf 1: " + result);

        result = Try.of(() -> Long.valueOf(param)).get();
        System.out.println("Long.valueOf 2: " + result);

        System.out.println("------------有返回值，有入参----------------");
        // 有返回值，有入参
        result = Try.<Map<String, String>, Long>of(s -> Long.valueOf(s.get("k1")))
                .apply(ImmutableMap.of("k1", param))
                .trap(e -> System.out.println("Long.valueOf exception: " + e.getMessage()))
                .andFinally(() -> System.out.println("This message will ignore."))
                .andFinally(s -> {
                    Map<String, Object> returnMap = JsonUtils.string2Obj(JsonUtils.obj2String(s), new TypeReference<Map<String, Object>>() {
                    });
                    System.out.println("Long.valueOf finally run code." + s);
                    // 演示抛异常
                    String k2 = returnMap.get("k2").toString();
                    System.out.println(k2);
                })
                .finallyTrap(e -> System.out.println("Long.valueOf finally exception: " + e.getMessage()))
                .get();
        System.out.println("Long.valueOf 3: " + result);

        ArrayList<String> list = null;

        System.out.println("-----------无返回值，无入参-----------------");
        // 无返回值，无入参
        Try.of(() -> Thread.sleep(-1L))
                .andFinally(() -> list.clear())
                // .andFinally(list::clear) //https://stackoverflow.com/questions/37413106/java-lang-nullpointerexception-is-thrown-using-a-method-reference-but-not-a-lamb
                .run();

        System.out.println("--------------无返回值，有入参--------------");
        // 无返回值，有入参
        Try.<String>of(v -> list.add(0, v))
                .andFinally(s -> System.out.println(s))
                .accept("hello");
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
                Optional.ofNullable(throwConsumer).ifPresent(tc -> tc.accept(e));
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
                Optional.ofNullable(throwConsumer).ifPresent(tc -> tc.accept(e));
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
                Optional.ofNullable(throwConsumer).ifPresent(tc -> tc.accept(e));
                return null;
            } finally {
                doFinally();
            }
        }
    }

    @FunctionalInterface
    interface ThrowableConsumer<P> {
        /**
         * Performs this operation on the given argument.
         *
         * @param p the input argument
         * @throws Throwable throwable
         */
        void accept(P p) throws Throwable;
    }

    @FunctionalInterface
    interface ThrowableSupplier<R> {
        /**
         * Gets a result.
         *
         * @return a result
         * @throws Throwable throwable
         */
        R get() throws Throwable;
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
    interface ThrowableFunction<P, R> {
        /**
         * Applies this function to the given argument.
         *
         * @param p the function argument
         * @return the function result
         * @throws Throwable throwable
         */
        R apply(P p) throws Throwable;
    }

    abstract class Tryable<C> {
        Consumer<? super Throwable> throwConsumer;
        ThrowableRunnable finallyRunnable;
        ThrowableConsumer<? super Object> finallyConsumer;
        Consumer<? super Throwable> finallyThrowConsumer;
        C c;

        /**
         * 处理 finally
         */
        protected void doFinally() {
            Optional.ofNullable(finallyRunnable).ifPresent(r -> {
                try {
                    r.run();
                } catch (final Throwable e) {
                    Optional.ofNullable(finallyThrowConsumer).ifPresent(tc -> tc.accept(e));
                }
            });
        }

        /**
         * 处理带参数类型 finally
         *
         * @param p   入参
         * @param <P> 入参类型
         */
        protected <P> void doFinally(P p) {
            if (Objects.nonNull(finallyConsumer)) {
                try {
                    finallyConsumer.accept(p);
                } catch (final Throwable e) {
                    Optional.ofNullable(finallyThrowConsumer).ifPresent(tc -> tc.accept(e));
                }
            } else {
                doFinally();
            }
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
         * <p>
         * 注意：如果类型为 {@link TryConsumer}、{@link TryFunction} 并且已经调用 {@link #andFinally(ThrowableConsumer)} 则忽略
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
         * 自定义 finally 处理表达式
         * <p>
         * 注意：只会对 {@link TryConsumer}、{@link TryFunction} 类型对象起作用
         *
         * @param finallyConsumer finally 处理 lambda 表达式
         * @return {@link C}
         */
        public C andFinally(ThrowableConsumer<? super Object> finallyConsumer) {
            Objects.requireNonNull(finallyConsumer, "No finallyConsumer present");
            this.finallyConsumer = finallyConsumer;
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

    class TryConsumer<P> extends Tryable<TryConsumer<P>> {
        private final ThrowableConsumer<? super P> consumer;

        protected TryConsumer(ThrowableConsumer<? super P> consumer) {
            Objects.requireNonNull(consumer, "No consumer present");
            this.consumer = consumer;

            super.c = this;
        }

        /**
         * 计算结果
         *
         * @param p 要计算的入参
         */
        public void accept(P p) {
            try {
                Objects.requireNonNull(p, "No accept param present");

                consumer.accept(p);
            } catch (final Throwable e) {
                Optional.ofNullable(throwConsumer).ifPresent(tc -> tc.accept(e));
            } finally {
                // doFinally();
                doFinally(p);
            }
        }
    }

    class TryFunction<P, R> extends Tryable<TryFunction<P, R>> {
        private final ThrowableFunction<? super P, ? extends R> function;
        private P p;

        protected TryFunction(ThrowableFunction<? super P, ? extends R> function) {
            Objects.requireNonNull(function, "No function present");
            this.function = function;

            super.c = this;
        }

        /**
         * 传入要计算的入参
         *
         * @param p 要计算的入参
         * @return {@link TryFunction}
         */
        public TryFunction<P, R> apply(P p) {
            Objects.requireNonNull(p, "Apply param should not null");

            this.p = p;
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
                Objects.requireNonNull(function, "No apply param present");

                return function.apply(p);
            } catch (final Throwable e) {
                Optional.ofNullable(throwConsumer).ifPresent(tc -> tc.accept(e));
                return r;
            } finally {
                // doFinally();
                doFinally(p);
            }
        }

        /**
         * 如果有异常返回 null，否则返回计算结果
         *
         * @return 实际值或 null
         */
        public R get() {
            try {
                Objects.requireNonNull(p, "No apply param present");

                return function.apply(p);
            } catch (final Throwable e) {
                Optional.ofNullable(throwConsumer).ifPresent(tc -> tc.accept(e));
                return null;
            } finally {
                // doFinally();
                doFinally(p);
            }
        }
    }
}
