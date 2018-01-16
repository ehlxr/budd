package me.ehlxr.reactive;


import rx.Observable;

/**
 * Created by lixiangrong on 2018/1/16.
 * 链式函数式处理
 */
public class TestRx01 {
    public static void main(String[] args) {
        hello(1, 2, 3, 4, 5);
    }

    private static void hello(Integer... integers) {
        Observable<Integer> workflow = Observable.from(integers)
                .filter(i -> (i < 10) && (i > 0))
                .map(i -> i * 2);
        workflow.subscribe(i -> System.out.print(i + "! "));
        System.out.println();
        workflow.subscribe(i -> System.out.print(i + "! "));
    }
}
