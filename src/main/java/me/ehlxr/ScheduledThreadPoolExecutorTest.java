package me.ehlxr;

import cn.enncloud.ceres.utils.NamedThreadFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lixiangrong
 * @date 2018/8/27
 */
public class ScheduledThreadPoolExecutorTest {

    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("TestTimer", true));
    private ScheduledFuture<?> scheduledFuture;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private void start() {
        scheduledFuture = scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            System.out.println(scheduledThreadPoolExecutor.getQueue() + " " + Thread.currentThread().getName());

            scheduledFuture.cancel(false);
            // System.out.println(scheduledThreadPoolExecutor.remove(pingCommand));
            // System.out.println(scheduledThreadPoolExecutor.getRemoveOnCancelPolicy());
            // scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(true);
            System.out.println(scheduledThreadPoolExecutor.getQueue() + " " + Thread.currentThread().getName());
            // System.out.println(scheduledThreadPoolExecutor.getActiveCount());

            countDownLatch.countDown();
        }, 2, 5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        ScheduledThreadPoolExecutorTest test = new ScheduledThreadPoolExecutorTest();
        test.start();
        countDownLatch.await();
    }
}
