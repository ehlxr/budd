package me.ehlxr;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lixiangrong on 2017/6/20.
 */
public class LambdaTest {
    public static void main(String[] args) {
        // @FunctionalInterface 函数式接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello thread");
            }
        }).start();
        new Thread(() -> System.out.println("hello lambda")).start();

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

        System.out.println("------------打印集合元素----old way----");
        for (Integer n : list) {
            System.out.println(n);
        }
        System.out.println("------------打印集合元素----new way----");
        // list.forEach(n -> System.out.println(n));// list.forEach((Integer n) -> {System.out.println(n)});
        list.forEach(System.out::println);

        System.out.println("------------求平方----old way------");
        for (Integer n : list) {
            int x = n * n;
            System.out.println(x);
        }
        System.out.println("------------求平方-----new way-----");
        // list.stream().map(n -> n * n).forEach(x -> System.out.println(x));
        list.stream().map(n -> n * n).forEach(System.out::println);

        System.out.println("------------求平方和----old way------");
        int sum = 0;
        for (Integer n : list) {
            int x = n * n;
            sum = sum + x;
        }
        System.out.println(sum);
        System.out.println("------------求平方和-----new way-----");
        System.out.println(list.stream().map(n -> n * n).reduce((x, y) -> x + y).get());

    }
}
