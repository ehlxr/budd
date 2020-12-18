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

package io.github.ehlxr.test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * https://colobu.com/2016/02/29/Java-CompletableFuture/
 *
 * @author ehlxr
 * @since 2020-12-18 10:12.
 */
public class TestCompletableFuture {
    // public static void main(String[] args) throws ExecutionException, InterruptedException {
    //     ExecutorService es = Executors.newFixedThreadPool(10);
    //     Future<Integer> f = es.submit(() -> {
    //         // 长时间的异步计算
    //         Thread.sleep(3000);
    //         // 然后返回结果
    //         return 100;
    //     });
    //     // while (!f.isDone());
    //     System.out.println(f.get());
    // }

    // public static CompletableFuture<Integer> compute() {
    //     return new CompletableFuture<>();
    // }
    //
    // public static void main(String[] args) throws Exception {
    //     final CompletableFuture<Integer> f = compute();
    //     class Client extends Thread {
    //         final CompletableFuture<Integer> f;
    //
    //         Client(String threadName, CompletableFuture<Integer> f) {
    //             super(threadName);
    //             this.f = f;
    //         }
    //
    //         @Override
    //         public void run() {
    //             try {
    //                 System.out.println(this.getName() + ": " + f.get());
    //             } catch (InterruptedException | ExecutionException e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     }
    //     new Client("Client1", f).start();
    //     new Client("Client2", f).start();
    //     System.out.println("waiting");
    //     // 完成，客户端 get() 阻塞通过
    //     f.complete(100);
    //     //f.completeExceptionally(new Exception());
    // }


    private static final long t = System.currentTimeMillis();

    static int getMoreData() {
        System.out.println("begin to start compute");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("end to start compute. passed " + (System.currentTimeMillis() - t) / 1000 + " seconds");
        return 1000;
    }

    public static void main(String[] args) throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(TestCompletableFuture::getMoreData);
        Future<Integer> f = future.whenComplete((v, e) -> {
            System.out.println("the result is " + v);
            e.printStackTrace();
        });
        // System.out.println(f.get());

        System.out.println("wait for result.....");


        CompletableFuture<Void> f1 = future.thenAccept((x) -> System.out.println("thenAccept " + x));
        // System.out.println(f1.get());


        CompletableFuture<Void> f2 = future.thenAcceptBoth(CompletableFuture.completedFuture(5), (x, y) -> System.out.println("thenAcceptBoth " + x * y));
        // System.out.println(f2.get());

        Random rand = new Random();
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000 + rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 200;
        });
        CompletableFuture<String> f3 = future.applyToEither(future2, Object::toString);
        System.out.println("applyToEither " + f3.get());


        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000 + rand.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });
        CompletableFuture<Void> f4 = CompletableFuture.allOf(future, future2, future3);
        // CompletableFuture<Object> f4 = CompletableFuture.anyOf(future, future2, future3);
        // System.out.println("anyOf " + f4.get());

        System.in.read();
    }


}
