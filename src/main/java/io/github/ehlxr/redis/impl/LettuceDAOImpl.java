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

package io.github.ehlxr.redis.impl;

import io.github.ehlxr.redis.RedisDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author ehlxr
 * @since 2021-07-15 22:51.
 */
public class LettuceDAOImpl implements RedisDAO {
    private final Logger log = LoggerFactory.getLogger(LettuceDAOImpl.class);

    @Autowired
    private RedisTemplate<String, String> rt;

    @Override
    public Boolean getDistributedLock(String key, String value, long expireTime) {
        Boolean set = false;
        try {
            set = rt.opsForValue().setIfAbsent(key, value, expireTime, TimeUnit.MILLISECONDS);
            log.debug("getLock redis key: {}, value: {}, expireTime: {}, result: {}", key, value, expireTime, set);
        } catch (Exception e) {
            log.error("getLock redis key: {}, value: {}, expireTime: {}", key, value, expireTime, e);
        }
        return set;
    }

    @Override
    public Boolean releaseDistributedLock(String key, String value) {
        Long execute = null;
        try {
            RedisScript<Long> redisScript = new DefaultRedisScript<>(RELEASE_LOCK_LUA, Long.class);
            execute = rt.execute(redisScript, Collections.singletonList(key), value);
            log.debug("releaseLock redis key: {}, value: {}, result: {}", key, value, execute);
        } catch (Exception e) {
            log.error("releaseLock redis key: {}, value: {}", key, value, e);
        }
        return Long.valueOf(1L).equals(execute);
    }

    @Override
    public synchronized Boolean slideWindow(String key, int count, long timeWindow) {
        try {
            long nowTime = System.currentTimeMillis();
            ListOperations<String, String> list = rt.opsForList();
            String farTime = list.index(key, count - 1);
            if (farTime == null) {
                list.leftPush(key, String.valueOf(nowTime));
                rt.expire(key, timeWindow + 1000L, TimeUnit.MILLISECONDS);
                return true;
            }
            if (nowTime - Long.parseLong(farTime) > timeWindow) {
                list.rightPop(key);
                list.leftPush(key, String.valueOf(nowTime));
                rt.expire(key, timeWindow + 1000L, TimeUnit.MILLISECONDS);
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("[logId:{}]", e);
            return false;
        }
    }

    @Override
    public Boolean slideWindowLua(String key, int count, long timeWindow) {
        if (count <= 0 || timeWindow <= 0) {
            return false;
        }
        Long execute = null;
        try {
            RedisScript<Long> redisScript = new DefaultRedisScript<>(SLIDE_WINDOW_LUA, Long.class);
            execute = rt.execute(redisScript, Collections.singletonList(key), String.valueOf(count - 1), String.valueOf(timeWindow), String.valueOf(System.currentTimeMillis()));
            log.debug("slideWindowLua redis key: {}, count: {}, timeWindow: {}, result: {}", key, count, timeWindow, execute);
        } catch (Exception e) {
            log.error("slideWindowLua redis key: {}, count: {}, timeWindow: {}", key, count, timeWindow, e);
        }
        return Long.valueOf(1L).equals(execute);
    }

}
