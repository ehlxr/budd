package osc.git.eh3.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lixiangrong on 2016/12/22.
 */
public class ThreadPoolExecutorTest {
    public static void main(String[] args) {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            //try {
            //    Thread.sleep(index * 1000);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
            cachedThreadPool.execute (new Runnable() {
                public void run() {
                    //System.out.println(index);
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }
    }
}
