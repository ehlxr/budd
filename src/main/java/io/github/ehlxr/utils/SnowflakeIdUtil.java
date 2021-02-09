/*
 * The MIT License (MIT)
 *
 * Copyright © 2020 xrv <xrg@live.com>
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

package io.github.ehlxr.utils;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

/**
 * Twitter_Snowflake
 * <p>
 * SnowFlake 的结构如下 (每部分用 - 分开):
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
 * SnowFlake 的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生 id 碰撞 (由数据中心 id 和机器 id 作区分)
 *
 * @author ehlxr
 * @since 2020-12-23 11:26.
 */
public class SnowflakeIdUtil {
    /**
     * 开始时间截 (2020-12-23)
     */
    private final long twepoch = 1608652800000L;

    /**
     * 机器 id 所占的位数
     */
    private final long workerIdBits = 5L;

    /**
     * 数据标识 id 所占的位数
     */
    private final long datacenterIdBits = 5L;

    /**
     * 支持的最大机器 id，结果是 31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * 支持的最大数据标识 id，结果是 31
     */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /**
     * 序列在 id 中占的位数
     */
    private final long sequenceBits = 12L;

    /**
     * 机器 id 向左移 12 位
     */
    private final long workerIdShift = sequenceBits;

    /**
     * 数据标识 id 向左移 17 位 (12+5)
     */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间截向左移 22 位 (5+5+12)
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /**
     * 生成序列的掩码，这里为 4095 (0b111111111111=0xfff=4095)
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 工作机器 id (0~31)
     */
    private final long workerId;

    /**
     * 数据中心 id (0~31)
     */
    private final long datacenterId;

    /**
     * 毫秒内序列 (0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成 id 的时间截
     */
    private long lastTimestamp = -1L;

    /**
     * 构造函数
     *
     * @param workerId     工作 id (0~31)
     * @param datacenterId 数据中心 id (0~31)
     */
    private SnowflakeIdUtil(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 获得下一个 id (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    private synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        // 如果当前时间小于上一次 id 生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 阻塞到下一个毫秒，获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，毫秒内序列重置
            sequence = 0L;
        }

        // 上次生成 id 的时间截
        lastTimestamp = timestamp;

        //  移位并通过或运算拼到一起组成 64 位的 id
        return ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成 id 的时间截
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    private static SnowflakeIdUtil snowflakeIdUtil;

    public static void init(long workerId, long datacenterId) {
        snowflakeIdUtil = new SnowflakeIdUtil(workerId, datacenterId);
    }

    public static long id() {
        return snowflakeIdUtil.nextId();
    }

    public static void main(String[] args) {
        SnowflakeIdUtil.init(1, 2);
        Set<Long> ids = new CopyOnWriteArraySet<>();

        // int threads = 10000;
        // int rounds = 1;
        //
        // CountDownLatch latch = new CountDownLatch(threads * rounds);
        // ExecutorService executorService = Executors.newFixedThreadPool(10);
        //
        // long start = System.currentTimeMillis();
        // for (int i = 0; i < threads; i++) {
        //     executorService.execute(() -> {
        //         int j = 0;
        //         while (j < rounds) {
        //             long id = SnowflakeIdUtil.id();
        //             if (!ids.add(id)) {
        //                 System.out.println(id);
        //             }
        //             j++;
        //             latch.countDown();
        //         }
        //     });
        // }
        //
        // latch.await();
        // System.out.println("over: qps= " + ((long) threads * rounds * 1000 / (System.currentTimeMillis() - start + 1)));
        //
        // executorService.shutdown();
        // System.out.println(ids.size());

        // 并发数
        int count = 4000;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count);

        // ExecutorService pool = Executors.newFixedThreadPool(count);
        ForkJoinPool pool = new ForkJoinPool(count);
        long now = System.currentTimeMillis();

        IntStream.range(0, count).forEach(i ->
                pool.execute(() -> {
                    // 等待所有任务准备就绪
                    Try.of((Try.ThrowableRunnable) cyclicBarrier::await).trap(System.out::println).run();

                    long id = SnowflakeIdUtil.id();
                    if (!ids.add(id)) {
                        System.out.println(id);
                    }
                }));

        pool.shutdown();
        while (!pool.isTerminated()) {
            Try.of(() -> Thread.sleep(10)).trap(System.out::println).run();
        }
        long end = System.currentTimeMillis();
        System.out.println(ids.size() + " ids create at " + (end - now) + "ms.");
    }
}
