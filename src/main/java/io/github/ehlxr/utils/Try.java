package io.github.ehlxr.utils;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 异常处理，简化 try catch
 *
 * @author ehlxr
 * @since 2020-12-06 21:32.
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

    @SuppressWarnings("ConstantConditions")
    public static void main(String[] args) {

        String param = "10s";

        Long result = Try.<String, Long>of(Long::valueOf).get(param, -1L, Throwable::printStackTrace);

        System.out.println(result);

        // 有返回值，无入参
        System.out.println(Try.of(() -> Long.valueOf("s")).get(0L));
        System.out.println(Try.of(() -> Long.valueOf("21")).get(0L, e -> {
        }));

        // 有返回值，有入参
        System.out.println(Try.<String, Long>of(Long::valueOf).get("s", 0L, e -> {
        }));

        ArrayList<String> list = new ArrayList<>();

        // 无返回值，无入参
        Try.<String>of(e1 -> list.clear()).accept(null, e -> System.out.println("...." + e.getMessage()));

        // 无返回值，有入参
        Try.<String>
                of(v -> list.add(10, v))
                .accept("test", e -> System.out.println(e.getMessage()));
    }

    public static class C<T> {
        private final Consumer<? super T> consumer;

        C(Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer, "No value present");
            this.consumer = consumer;
        }

        /**
         * 如果有异常忽略，否则计算结果
         *
         * @param t 要计算的入参
         */
        public void accept(T t) {
            try {
                consumer.accept(t);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        /**
         * 如果有异常调用自定义异常处理表达式并返回默认值，否则返回计算结果
         *
         * @param t 要计算的入参
         * @param e 自定义异常处理 lambda 表达式
         */
        public void accept(T t, Consumer<? super Throwable> e) {
            try {
                consumer.accept(t);
            } catch (Throwable th) {
                e.accept(th);
            }
        }
    }

    public static class S<R> {
        private final Supplier<? extends R> supplier;

        S(Supplier<? extends R> supplier) {
            Objects.requireNonNull(supplier, "No value present");
            this.supplier = supplier;
        }

        /**
         * 如果有异常忽略并返回默认值，否则返回计算结果
         *
         * @param r 指定默认值
         * @return 实际值或默认值
         */
        public R get(R r) {
            try {
                return supplier.get();
            } catch (Throwable e) {
                e.printStackTrace();
                return r;
            }
        }

        /**
         * 如果有异常调用自定义异常处理表达式并返回默认值，否则返回计算结果
         *
         * @param r 指定默认值
         * @param e 自定义异常处理 lambda 表达式
         * @return 实际值或默认值
         */
        public R get(R r, Consumer<? super Throwable> e) {
            Objects.requireNonNull(supplier, "No supplier present");
            try {
                return supplier.get();
            } catch (Throwable th) {
                e.accept(th);
                return r;
            }
        }
    }

    public static class F<T, R> {
        private final Function<? super T, ? extends R> function;

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
                e.printStackTrace();
                return r;
            }
        }

        /**
         * 如果有异常调用自定义异常处理表达式并返回默认值，否则返回计算结果
         *
         * @param t 要计算的入参
         * @param r 指定默认值
         * @param e 自定义异常处理 lambda 表达式
         * @return 实际值或默认值
         */
        public R get(T t, R r, Consumer<? super Throwable> e) {
            Objects.requireNonNull(function, "No function present");
            try {
                return function.apply(t);
            } catch (Throwable th) {
                e.accept(th);
                return r;
            }
        }
    }
}