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

package io.github.ehlxr.reactive;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by ehlxr on 2018/1/16.
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
                soa -> {
                    System.out.println("oa Thread: " + Thread.currentThread().getName());
                    return Observable.fromCallable(new TimeConsumingService("fa", 2000, new String[]{soa}));
                }
        );
        Observable<String> oc = oa.flatMap(
                res -> {
                    System.out.println("oc Thread: " + Thread.currentThread().getName());
                    // System.out.println(res);
                    // System.out.println("Executed At： " + (System.currentTimeMillis() - startTime) + "ms");
                    return Observable.fromCallable(new TimeConsumingService("fc", 1000, new String[]{res}));
                });

        // tasks ob -> (od,oe),  ob, od, oe have special thread 2,3,4.
        Observable<String> ob = Observable.just("ob").observeOn(Schedulers.io()).flatMap(
                sob -> {
                    System.out.println("ob Thread: " + Thread.currentThread().getName());
                    return Observable.fromCallable(new TimeConsumingService("fb", 2000, new String[]{sob}));
                }
        );
        Observable<String> od_oe = ob.flatMap(
                res -> {
                    System.out.println("od_oe Thread: " + Thread.currentThread().getName());
                    // System.out.println(res);
                    // System.out.println("Executed At： " + (System.currentTimeMillis() - startTime) + "ms");
                    Observable<String> od = Observable.just("od").observeOn(Schedulers.io()).flatMap(
                            sod -> {
                                System.out.println("od Thread: " + Thread.currentThread().getName());
                                return Observable.fromCallable(new TimeConsumingService("fd", 1000, new String[]{sod}));
                            }
                    );
                    Observable<String> oe = Observable.just("oe").observeOn(Schedulers.io()).flatMap(
                            soe -> {
                                System.out.println("oe Thread: " + Thread.currentThread().getName());
                                return Observable.fromCallable(new TimeConsumingService("fe", 1000, new String[]{soe}));
                            }
                    );
                    return Observable.merge(od, oe);
                });

        System.out.println("Observable build： " + (System.currentTimeMillis() - startTime) + "ms");

        // tasks join oc,(od_oe) and subscribe
        Observable.merge(oc, od_oe).toBlocking().subscribe(
                res -> {
                    System.out.println("ss Thread: " + Thread.currentThread().getName());
                    // System.out.println(res);
                    // System.out.println("Executed At： " + (System.currentTimeMillis() - startTime) + "ms");
                });

        System.out.println("End executed: " + (System.currentTimeMillis() - startTime) + "ms");
    }
}
