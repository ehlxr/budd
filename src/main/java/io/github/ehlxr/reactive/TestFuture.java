package io.github.ehlxr.reactive;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author ehlxr
 * @date 2018/1/16
 * <p>
 * Future<V> 是一个泛型接口，如果一个可运行的函数（实现 Callable 或 Runable 的类）在一个线程中运行，
 * 利用 Future<V> 可以用它的　get() 方法返回 V 类型的结果。 注意， get() 会阻塞当前线程
 * <p>
 * 问题
 * get() 顺序会影响出结果时间，关键 get 的阻塞；
 * 如果能按这些线程出结果的时间序列，把数据结果交给后面的线程并行加工处理，CPU就不用阻塞在 get() 了。但编程无疑会很复杂。
 */
public class TestFuture {
    // https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Executor.html
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        TestFuture test = new TestFuture();
        test.testTaskRunning("fa", 300);
        test.testAsyncTaskRunning();
        executor.shutdown();
    }

    private void testTaskRunning(String name, Integer waiting) {
        System.out.println("Prepare for execution：" + name);
        long startTime = System.currentTimeMillis();

        Future<String> fa = executor.submit(
                () -> {
                    try {
                        Thread.sleep(waiting);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return String.format("service exec time: %d", waiting);
                });

        System.out.println("Start execute： " + (System.currentTimeMillis() - startTime) + "ms");

        try {
            String s = fa.get(); // Future to Blocked
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("End execute： " + (System.currentTimeMillis() - startTime) + "ms");

    }

    private void testAsyncTaskRunning() {
        System.out.println("Prepare for execution： composite task");
        long startTime = System.currentTimeMillis();

        Future<String> fa = executor.submit(new TimeConsumingService("fa", 200, new String[]{}));
        Future<String> fb = executor.submit(new TimeConsumingService("fb", 400, new String[]{}));

        System.out.println("Start execute： " + (System.currentTimeMillis() - startTime) + "ms");

        try {
            // What will happen when change line fc and fd ?
            Future<String> fc = executor.submit(new TimeConsumingService("fc", 400, new String[]{fa.get()}));
            Future<String> fd = executor.submit(new TimeConsumingService("fd", 200, new Object[]{fb.get()}));
            Future<String> fe = executor.submit(new TimeConsumingService("fe", 200, new Object[]{fb.get()}));
            System.out.println(fc.get());
            System.out.println(fd.get());
            System.out.println(fe.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("End execute： " + (System.currentTimeMillis() - startTime) + "ms");
    }


}