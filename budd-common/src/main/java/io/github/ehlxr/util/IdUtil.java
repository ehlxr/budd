/*
 * The MIT License (MIT)
 *
 * Copyright © 2021 xrv <xrg@live.com>
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

package io.github.ehlxr.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自增 id 生成工具类
 *
 * @author ehlxr
 * @since 2021-08-29 18:32.
 */
@Component
public class IdUtil {
    private final static String REDIS_ID_KEY = "com.tzld.piaoquan.incentive_";
    /**
     * 每一毫秒生成的最大数
     */
    private static final int MAX_NUN = 99;
    private static final AtomicInteger ATOMIC_NUM = new AtomicInteger();
    private static RedisTemplate<String, String> redisTemplate;
    private static String cur_date;

    public static long redisId() {
        // 生成 17 位的时间戳(每毫秒使用新的时间戳当key)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String timeStamp = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
        // 获得 redis-key
        String newKey = String.format("%s:%s", REDIS_ID_KEY, timeStamp);
        // 获取自增值（时间戳 + 自定义 key）
        Long increment = redisTemplate.opsForValue().increment(newKey, 1);
        // 设置时间戳生成的 key 的有效期为 1 秒
        redisTemplate.expire(newKey, 1, TimeUnit.SECONDS);
        // 获取订单号，时间戳 + 唯一自增 Id(2 位数,不过前方补 0)
        return Long.parseLong(String.format("%s%02d", timeStamp, increment));
    }

    // @Autowired
    // public IdUtil(RedisTemplate<String, Object> redisTemplate) {
    //     IdUtil.redisTemplate = redisTemplate;
    // }

    /**
     * 生成 19 位数 id
     * <p>
     * 17 位日期 + 2 位自增数<br>
     * 一毫秒最多生成 MAX_NUN 个 id，同一毫秒 id 有序自增
     */
    public static long localId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String nowStr = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);

        // 如果当前时间不是  cur_date 时间 设置 cur_date 为当前时间 重新开始计数
        if (!nowStr.equals(cur_date)) {
            cur_date = nowStr;

            ATOMIC_NUM.set(0);
        }
        // 如果同一毫秒最大数大于 最大数 MAX_NUN 则等待 1 毫秒 重新计算
        if (ATOMIC_NUM.get() >= MAX_NUN) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {
            }

            nowStr = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
            ATOMIC_NUM.set(0);
        }

        // 线程安全的原子操作，所以此方法无需同步 调用 incrementAndGet 函数来进行自增操作
        return Long.parseLong(String.format("%s%02d", nowStr, ATOMIC_NUM.incrementAndGet()));
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        IdUtil.redisTemplate = redisTemplate;
    }
}