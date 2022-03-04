package com.tzld.activity;

import io.github.ehlxr.semaphore.RedisSemaphore;
import io.github.ehlxr.semaphore.SemaphoreInfo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
class ServerApplicationTests {

    ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 10, 0, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testNonFair() throws InterruptedException {
        SemaphoreInfo semaphoreInfo = new SemaphoreInfo("NonFair", 5, 10);
        for (int i = 0; i < 10; i++) {
            String id = String.valueOf(i);
            RedisSemaphore semaphore = new RedisSemaphore(semaphoreInfo, redisTemplate, id);
            CompletableFuture.supplyAsync(semaphore::tryAcquire, pool).thenAcceptAsync((r) -> {
                if (r) System.out.println(id + "成功获取到信号量 (NonFair)~ ⭐⭐⭐");
                else System.out.println(id + "没有获取到信号量 (NonFair)");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                semaphore.release();
            }, pool);
        }
        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void testFair() throws ExecutionException, InterruptedException {
        SemaphoreInfo semaphoreInfo = new SemaphoreInfo("Fair", 5, 10, true);
        for (int i = 0; i < 10; i++) {
            String id = String.valueOf(i);
            RedisSemaphore semaphore = new RedisSemaphore(semaphoreInfo, redisTemplate, id);
            CompletableFuture.supplyAsync(semaphore::tryAcquire, pool).thenAcceptAsync((r) -> {
                if (r) System.out.println(id + "成功获取到信号量 (Fair)~~ ⭐⭐⭐");
                else System.out.println(id + "没有获取到信号量 (Fair)");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                semaphore.release();
            }, pool);
        }
        Thread.sleep(Long.MAX_VALUE);
    }

}
