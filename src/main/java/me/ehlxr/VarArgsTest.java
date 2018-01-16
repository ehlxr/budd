package me.ehlxr;

/**
 * Created by lixiangrong on 2018/1/16.
 * 可变参数
 */
public class VarArgsTest {
    private static void m1(String s, String... ss) {
        for (String s1 : ss) {
            System.out.println(s1);
        }
    }

    public static void main(String[] args) {
        m1("");
        m1("aaa");
        m1("aaa", "bbb");
    }
}