package me.ehlxr.reactive;


import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lixiangrong on 2018/1/16.
 */
public class SchedulerTest {
    public static void main(String[] args) throws InterruptedException {
        Flowable.fromCallable(() -> {
            Thread.sleep(1000); //  imitate expensive computation
            return "Done";
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(System.out::println, Throwable::printStackTrace);

        Thread.sleep(2000); // <--- wait for the flow to finish
    }
}
