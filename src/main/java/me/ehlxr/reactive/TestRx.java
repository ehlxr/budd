package me.ehlxr.reactive;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by lixiangrong on 2018/1/16.
 */
public class TestRx {
    public static void main(String[] args) {
        TestRx testRx = new TestRx();
        testRx.testAsyncCompositeJoin();
        // testRx.testAsyncCompositeJoin();
    }

    private void testAsyncCompositeJoin() {
        System.out.println("Prepare for execution：Async Composite Join");
        long startTime = System.currentTimeMillis();

        // Tasks oa -> oc,  both in the same thread 1.
        Observable<String> oa = Observable.just("oa").observeOn(Schedulers.io()).flatMap(
                soa -> Observable.fromCallable(new TimeConsumingService("fa", 1000, new String[]{}))
        );
        Observable<String> oc = oa.flatMap(
                res -> {
                    System.out.println(res);
                    System.out.println("Executed At： " + (System.currentTimeMillis() - startTime) + "ms");
                    return Observable.fromCallable(
                            new TimeConsumingService("fc", 2000, new String[]{res}));
                });

        // tasks ob -> (od,oe),  ob, od, oe have special thread 2,3,4.
        Observable<String> ob = Observable.just("ob").observeOn(Schedulers.io()).flatMap(
                sob -> Observable.fromCallable(new TimeConsumingService("fb", 2000, new String[]{}))
        );
        Observable<String> od_oe = ob.flatMap(
                res -> {
                    System.out.println(res);
                    System.out.println("Executed At： " + (System.currentTimeMillis() - startTime) + "ms");
                    Observable<String> od = Observable.just("od").observeOn(Schedulers.io()).flatMap(
                            sod -> Observable.fromCallable(new TimeConsumingService("fd", 1000, new String[]{res}))
                    );
                    Observable<String> oe = Observable.just("oe").observeOn(Schedulers.io()).flatMap(
                            sod -> Observable.fromCallable(new TimeConsumingService("fe", 1000, new String[]{res}))
                    );
                    return Observable.merge(od, oe);
                });

        System.out.println("Observable build： " + (System.currentTimeMillis() - startTime) + "ms");

        // tasks join oc,(od_oe) and subscribe
        Observable.merge(oc, od_oe).toBlocking().subscribe(
                res -> {
                    System.out.println(res);
                    System.out.println("Executed At： " + (System.currentTimeMillis() - startTime) + "ms");
                });

        System.out.println("End executed: " + (System.currentTimeMillis() - startTime) + "ms");
    }
}
