package me.ehlxr.proxy;

import java.lang.reflect.Proxy;

/**
 * @author lixiangrong
 * @since 2019-06-28.
 */
public class Main {
    public static void main(String[] args) {
        Flyable proxy = (Flyable) Proxy.newProxyInstance(Flyable.class.getClassLoader(),
                new Class[]{Flyable.class}, new MyInvocationHandler(new Bird()));

        // 动态代理会生成类似以下的 Java 代码
        /*
        package me.ehlxr.proxy;

        import java.lang.reflect.InvocationHandler;
        import java.lang.reflect.Method;

        public class Proxy implements Flyable {
            private InvocationHandler handler;

            public Proxy(InvocationHandler handler) {
                this.handler = handler;
            }

            @Override
            public void fly() {
                try {
                    Method method = Flyable.class.getMethod("fly");
                    this.handler.invoke(this, method, null);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
         */

        proxy.fly();
    }
}
