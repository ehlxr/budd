package me.ehlxr.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ehlxr
 * @date 2016/12/22
 */
//@SuppressWarnings({"AlibabaRemoveCommentedCode", "Convert2Lambda", "UnnecessaryLocalVariable"})
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
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    //System.out.println(index);
                    System.out.println("Thread: " + Thread.currentThread().getName());
                }
            });
        }
    }
}
