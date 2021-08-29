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
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.params.SetParams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * redis lock/滑动时间窗口限流
 *
 * @author ehlxr
 * @since 2021-07-15 22:44.
 */
// @Component("jedisDAOImpl")
public class JedisDAOImpl implements RedisDAO {
    private final Logger log = LoggerFactory.getLogger(JedisDAOImpl.class);

    // @Autowired
    private JedisCluster jc;

    @Override
    public Boolean getDistributedLock(String key, String value, long expireTime) {
        String set = null;
        try {
            set = jc.set(key, value, SetParams.setParams().nx().px(expireTime));
            log.debug("getLock redis key: {}, value: {}, expireTime: {}, result: {}", key, value, expireTime, set);
        } catch (Exception e) {
            log.error("getLock redis key: {}, value: {}, expireTime: {}", key, value, expireTime, e);
        }
        return "OK".equals(set);
    }

    @Override
    public Boolean releaseDistributedLock(String key, String value) {
        Object eval = null;
        try {
            eval = jc.eval(RELEASE_LOCK_LUA, Collections.singletonList(key), Collections.singletonList(value));
            log.debug("releaseLock redis key: {}, value: {}, result: {}", key, value, eval);
        } catch (Exception e) {
            log.error("releaseLock redis key: {}, value: {}", key, value, e);
        }
        return Long.valueOf(1L).equals(eval);
    }

    @Override
    public synchronized Boolean slideWindow(String key, int count, long timeWindow) {
        if (count <= 0 || timeWindow <= 0) {
            return false;
        }
        try {
            // 获取当前时间
            long nowTime = System.currentTimeMillis();
            // 获取队列中，达到限流数量的位置，存储的时间戳
            String farTime = jc.lindex(key, count - 1);
            // 如果为空，说明限流队列还没满，则允许通过，并添加当前时间戳到队列开始位置
            if (farTime == null) {
                jc.lpush(key, String.valueOf(nowTime));
                // 给限流队列增加过期时间，防止长时间不用导致内存一直占用
                jc.pexpire(key, timeWindow + 1000L);
                return true;
            }

            // 队列已满（达到限制次数），用当前时间戳 减去 最早添加的时间戳
            if (nowTime - Long.parseLong(farTime) > timeWindow) {
                // 若结果大于 timeWindow，则说明在 timeWindow 内，通过的次数小于等于 count
                // 允许通过，并删除最早添加的时间戳，将当前时间添加到队列开始位置
                jc.rpop(key);
                jc.lpush(key, String.valueOf(nowTime));
                // 给限流队列增加过期时间，防止长时间不用导致内存一直占用
                jc.pexpire(key, timeWindow + 1000L);
                return true;
            }
            // 若结果小于等于 timeWindow，则说明在 timeWindow 内，通过的次数大于 count
            // 不允许通过
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
        Object eval = null;
        try {
            List<String> argvList = new ArrayList<>();
            argvList.add(String.valueOf(count - 1));
            argvList.add(String.valueOf(timeWindow));
            argvList.add(String.valueOf(System.currentTimeMillis()));
            eval = jc.eval(SLIDE_WINDOW_LUA, Collections.singletonList(key), argvList);
            log.debug("slideWindowLua redis key: {}, count: {}, timeWindow: {}, result: {}", key, count, timeWindow, eval);
        } catch (Exception e) {
            log.error("slideWindowLua redis key: {}, count: {}, timeWindow: {}", key, count, timeWindow, e);
        }
        return Long.valueOf(1L).equals(eval);
    }
}
