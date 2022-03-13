/*
 * The MIT License (MIT)
 *
 * Copyright © 2022 xrv <xrv@live.com>
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

package io.github.ehlxr.queue;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ehlxr
 * @since 2022-02-24 20:00.
 */
@Component
public class RedisDelayQueue {
    private static final Logger log = LoggerFactory.getLogger(RedisDelayQueue.class);
    /**
     * 延迟队列名称
     */
    private static final String DELAY_QUEUE_NAME = "budd:delayQueue";
    private static final ExecutorService POOL = new ThreadPoolExecutor(5, 200,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024), new ThreadFactoryBuilder()
            .setNameFormat("RedisDelayQueue-pool-%d").build(),
            new ThreadPoolExecutor.AbortPolicy());
    private final RedisTemplate<String, String> redisTemplate;

    public RedisDelayQueue(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 添加延迟任务
     *
     * @param msg       延迟任务消息
     * @param delayTime 延迟时间（毫秒）
     */
    public void addDelayTasks(String msg, long delayTime) {
        Boolean result = redisTemplate.opsForZSet()
                .add(DELAY_QUEUE_NAME, msg, System.currentTimeMillis() + delayTime);
        log.debug("add task {} result {} ", msg, result);
    }

    /**
     * 监听延迟消息
     */
    @PostConstruct
    public void listenDelayLoop() {
        //noinspection AlibabaAvoidManuallyCreateThread
        new Thread(() -> {
            while (true) {
                // 获取一个到点的消息
                Set<String> taskSet = redisTemplate.opsForZSet()
                        .rangeByScore(DELAY_QUEUE_NAME, 0, System.currentTimeMillis(), 0, 1);

                // 如果没有，就等等
                if (taskSet == null || taskSet.isEmpty()) {
                    try {
                        // log.debug("there is no task will sleep 1s.");
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        log.error("listen delay loop error.", e);
                    }
                    // 继续执行
                    continue;
                }

                for (String task : taskSet) {
                    // 任务认领成功
                    Long isRemove = redisTemplate.opsForZSet().remove(DELAY_QUEUE_NAME, task);
                    if (isRemove != null && isRemove > 0) {
                        log.info("will deal task {}", task);
                        // 拿到任务 后续处理
                        POOL.execute(() -> log.info("task {} deal done.", task));
                    } else {
                        log.warn("task {} has been handled by another instance", task);
                    }
                }
            }
        }).start();
    }
}

