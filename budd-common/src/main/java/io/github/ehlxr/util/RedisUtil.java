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
import org.springframework.data.redis.core.script.DefaultRedisScript;
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
@SuppressWarnings("unused")
@Component
public class RedisUtil {
    /**
     * 解锁 lua 脚本
     */
    public static final String UNLOCK = "if (redis.call('hexists', KEYS[1], ARGV[1]) == 0) then " +
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