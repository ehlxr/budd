package me.ehlxr.reactive;


import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.CountDownLatch;

/**
 * Created by lixiangrong on 2018/1/16.
 * 链式函数式处理
 */
public class TestRx01 {
    public static void main(String[] args) throws Exception {
        // hello(1, 2, 3, 4, 5);
        testRxJavaWithoutBlocking(10000);

        Observable.create((Observable.OnSubscribe<Integer>) observer -> {
            try {
                if (!observer.isUnsubscribed()) {
                    for (int i = 1; i < 5; i++) {
                        observer.onNext(i);
                    }
                    observer.onCompleted();
                }
            } catch (Exception e) {
                observer.onError(e);
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onNext(Integer item) {
                System.out.println("Next: " + item);
            }

            @Override
            public void onError(Throwable error) {
                System.err.println("Error: " + error.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }
        });
    }

    private static void hello(Integer... integers) {
        Observable<Integer> workflow = Observable.from(integers)
                .filter(i -> (i < 10) && (i > 0))
                .map(i -> i * 2).reduce((x, y) -> x + y);
        workflow.subscribe(i -> System.out.print(i + "! "));
    }

    private static void testRxJavaWithoutBlocking(int count) throws Exception {
        CountDownLatch finishedLatch = new CountDownLatch(1);
        long t = System.nanoTime();

        Observable.range(0, count)
                .map(i -> 200)
                .subscribe(s -> {
                }, Throwable::printStackTrace, finishedLatch::countDown);

        finishedLatch.await();
        t = (System.nanoTime() - t) / 1000000; //ms
        System.out.println("RxJavaWithoutBlocking TPS: " + count * 1000 / t);
    }
}
