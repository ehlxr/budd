/*
 * The MIT License (MIT)
 *
 * Copyright © 2021 xrv <xrv@live.com>
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.StaticScriptSource;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * redis 操作
 *
 * @author ehlxr
 * @since 2021-08-29 18:32.
 */
@Component
public class RedisUtil {
    private static final Logger log = LoggerFactory.getLogger(RedisUtil.class);

    /**
     * 解锁 lua 脚本
     */
    private static final String UNLOCK = "if (redis.call('hexists', KEYS[1], ARGV[1]) == 0) then " +
            "return nil; " +
            "end; " +
            "local counter = redis.call('hincrby', KEYS[1], ARGV[1], -1); " +
            "if (counter > 0) then " +
            "return 0; " +
            "else " +
            "redis.call('del', KEYS[1]); " +
            "return 1; " +
            "end; " +
            "return nil;";
    /**
     * 锁前缀
     */
    private static final String LOCK_PREFIX = "LOCK_";
    /**
     * 默认重试次数
     */
    private static final Integer DEFAULT_RETRIES = 1;
    /**
     * 默认 10毫秒
     */
    private static final Long DEFAULT_INTERVAL = 10L;
    /**
     * 加锁 lua 脚本
     */
    private static final String LOCK = "if (redis.call('exists', KEYS[1]) == 0) then " +
            "redis.call('hset', KEYS[1], ARGV[2], 1); " +
            "redis.call('pexpire', KEYS[1], ARGV[1]); " +
            "return 1; " +
            "end; " +
            "if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then " +
            "redis.call('hincrby', KEYS[1], ARGV[2], 1); " +
            "redis.call('pexpire', KEYS[1], ARGV[1]); " +
            "return 1; " +
            "end; " +
            "return 0;";
    private static RedisTemplate<String, String> redisTemplate;

    /**
     * 释放分布式锁时使用的 lua 脚本，保证原子性
     * <p>
     * if (redis.call('get', KEYS[1]) == ARGV[1])
     * then
     * return redis.call('del', KEYS[1])
     * else
     * return 0
     * end
     */
    private static final String RELEASE_LOCK_LUA = "if (redis.call('get', KEYS[1]) == ARGV[1]) then return redis.call('del', KEYS[1]) else return 0 end";

    /**
     * 滑动窗口限流使用的 lua 脚本，保证原子性
     * <p>
     * local key = KEYS[1];
     * local index = tonumber(ARGV[1]);
     * local time_window = tonumber(ARGV[2]);
     * local now_time = tonumber(ARGV[3]);
     * local far_time = redis.call('lindex', key, index);
     * if (not far_time)
     * then
     * redis.call('lpush', key, now_time);
     * redis.call('pexpire', key, time_window+1000);
     * return 1;
     * end
     * if (now_time - far_time > time_window)
     * then
     * redis.call('rpop', key);
     * redis.call('lpush', key, now_time);
     * redis.call('pexpire', key, time_window+1000);
     * return 1;
     * else
     * return 0;
     * end
     */
    private static final String SLIDE_WINDOW_LUA = "local key = KEYS[1];\n" + "local index = tonumber(ARGV[1]);\n" + "local time_window = tonumber(ARGV[2]);\n" + "local now_time = tonumber(ARGV[3]);\n" + "local far_time = redis.call('lindex', key, index);\n" + "if (not far_time)\n" + "then\n" + "  redis.call('lpush', key, now_time);\n" + "  redis.call('pexpire', key, time_window+1000);\n" + "  return 1;\n" + "end\n" + "\n" + "if (now_time - far_time > time_window)\n" + "then\n" + "  redis.call('rpop', key);\n" + "  redis.call('lpush', key, now_time);\n" + "  redis.call('pexpire', key, time_window+1000);\n" + "  return 1;\n" + "else\n" + "  return 0;\n" + "end";

    /**
     * 获取分布式锁
     *
     * @param key        key
     * @param value      value，需要保证全局唯一，用来删除分布式锁时判断身份使用
     * @param expireTime 锁过期时间，毫秒，防止业务崩溃未删除锁，导致死锁
     * @return 是否获取成功锁
     */
    public static boolean getDistributedLock(String key, String value, long expireTime) {
        boolean result = false;
        try {
            Boolean set = redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, TimeUnit.MILLISECONDS);
            result = set == null || set;
            log.info("getLock redis key: {}, value: {}, expireTime: {}, result: {}", key, value, expireTime, result);
        } catch (Exception e) {
            log.error("getLock redis key: {}, value: {}, expireTime: {}", key, value, expireTime, e);
        }
        return result;
    }

    /**
     * 释放分布式锁
     *
     * @param key   key
     * @param value value，需要和获取锁时传入的一致
     * @return 是否释放成功锁
     */
    public static boolean releaseDistributedLock(String key, String value) {
        Long execute = null;
        try {
            RedisScript<Long> redisScript = new DefaultRedisScript<>(RELEASE_LOCK_LUA, Long.class);
            execute = redisTemplate.execute(redisScript, Collections.singletonList(key), value);
            log.debug("releaseLock redis key: {}, value: {}, result: {}", key, value, execute);
        } catch (Exception e) {
            log.error("releaseLock redis key: {}, value: {}", key, value, e);
        }
        return Long.valueOf(1L).equals(execute);
    }

    /**
     * 分布式限流队列，在时间窗口内（包含该时间点），判断是否达到限流的阀值
     * 本接口实现的方法通过加锁避免并发问题，性能不高。只是为了说明限流逻辑如何实现
     *
     * @param key        key
     * @param count      限流阀值
     * @param timeWindow 限流时间窗口
     * @return 是否允许通过（通过即不进行限流）
     */
    public static synchronized boolean slideWindow(String key, int count, long timeWindow) {
        try {
            long nowTime = System.currentTimeMillis();
            ListOperations<String, String> list = redisTemplate.opsForList();
            String farTime = list.index(key, count - 1);
            if (farTime == null) {
                list.leftPush(key, String.valueOf(nowTime));
                redisTemplate.expire(key, timeWindow + 1000L, TimeUnit.MILLISECONDS);
                return true;
            }
            if (nowTime - Long.parseLong(farTime) > timeWindow) {
                list.rightPop(key);
                list.leftPush(key, String.valueOf(nowTime));
                redisTemplate.expire(key, timeWindow + 1000L, TimeUnit.MILLISECONDS);
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }

    /**
     * 分布式限流队列，在时间窗口内（包含该时间点），判断是否达到限流的阀值
     * 本接口实现的方法通过 Lua 脚本避免并发问题，性能较高。
     *
     * @param key        key
     * @param count      限流阀值
     * @param timeWindow 限流时间窗口
     * @return 是否允许通过（通过即不进行限流）
     */
    public static boolean slideWindowLua(String key, int count, long timeWindow) {
        if (count <= 0 || timeWindow <= 0) {
            return false;
        }
        Long execute = null;
        try {
            RedisScript<Long> redisScript = new DefaultRedisScript<>(SLIDE_WINDOW_LUA, Long.class);
            execute = redisTemplate.execute(redisScript, Collections.singletonList(key), String.valueOf(count - 1), String.valueOf(timeWindow), String.valueOf(System.currentTimeMillis()));
            log.debug("slideWindowLua redis key: {}, count: {}, timeWindow: {}, result: {}", key, count, timeWindow, execute);
        } catch (Exception e) {
            log.error("slideWindowLua redis key: {}, count: {}, timeWindow: {}", key, count, timeWindow, e);
        }
        return Long.valueOf(1L).equals(execute);
    }

    /**
     * 加锁 不可重入
     *
     * @param timeout 毫秒
     */
    public static Boolean lock(String key, long timeout) {
        return redisTemplate.opsForValue().setIfAbsent(LOCK_PREFIX + key, "1", timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 解锁 不可重入
     */
    public static Boolean unlock(String key) {
        return redisTemplate.delete(LOCK_PREFIX + key);
    }

    /**
     * 加锁
     *
     * @param reentrantId 重入Id
     *                    超时时间 毫秒ms
     */
    public static Boolean lock(String key, String reentrantId, long timeout) {
        return lock(key, reentrantId, timeout, DEFAULT_RETRIES);
    }

    /**
     * 加锁
     *
     * @param reentrantId 重入Id
     *                    超时时间 毫秒ms
     * @param retries     重试次数
     */
    public static Boolean lock(String key, String reentrantId, long timeout, int retries) {
        return lock(key, reentrantId, timeout, retries, DEFAULT_INTERVAL);
    }

    /**
     * 加锁
     *
     * @param reentrantId 重入Id
     *                    超时时间 毫秒ms
     * @param retries     重试次数
     * @param interval    每次重试间隔时间 毫秒
     */
    public static Boolean lock(String key, String reentrantId, long timeout, int retries, long interval) {
        String lockKey = LOCK_PREFIX + key;
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setScriptSource(new StaticScriptSource(LOCK));
        for (int i = 0; i < retries; i++) {
            Object result = redisTemplate.execute(script, Collections.singletonList(lockKey), String.valueOf(timeout), reentrantId);
            if (Objects.nonNull(result) && Objects.equals(1L, Long.valueOf(result.toString()))) {
                return true;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        }
        return false;
    }

    /**
     * 解锁
     *
     * @param reentrantId 重入ID
     */
    public static Boolean unlock(String key, String reentrantId) {
        String lockKey = LOCK_PREFIX + key;
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setScriptSource(new StaticScriptSource(UNLOCK));
        Object result = redisTemplate.execute(script, Collections.singletonList(lockKey), reentrantId);
        if (Objects.isNull(result)) {
            return null;
        }
        return Objects.equals(1L, Long.valueOf(result.toString()));
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }
}