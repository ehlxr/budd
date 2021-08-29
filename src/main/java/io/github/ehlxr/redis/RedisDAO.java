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

package io.github.ehlxr.redis;

/**
 * redis lock/滑动时间窗口限流
 *
 * @author ehlxr
 * @since 2021-07-15 22:42.
 */
public interface RedisDAO {

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
    String RELEASE_LOCK_LUA = "if (redis.call('get', KEYS[1]) == ARGV[1]) then return redis.call('del', KEYS[1]) else return 0 end";

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
    String SLIDE_WINDOW_LUA = "local key = KEYS[1];\n" + "local index = tonumber(ARGV[1]);\n" + "local time_window = tonumber(ARGV[2]);\n" + "local now_time = tonumber(ARGV[3]);\n" + "local far_time = redis.call('lindex', key, index);\n" + "if (not far_time)\n" + "then\n" + "  redis.call('lpush', key, now_time);\n" + "  redis.call('pexpire', key, time_window+1000);\n" + "  return 1;\n" + "end\n" + "\n" + "if (now_time - far_time > time_window)\n" + "then\n" + "  redis.call('rpop', key);\n" + "  redis.call('lpush', key, now_time);\n" + "  redis.call('pexpire', key, time_window+1000);\n" + "  return 1;\n" + "else\n" + "  return 0;\n" + "end";

    /**
     * 获取分布式锁
     *
     * @param key        key
     * @param value      value，需要保证全局唯一，用来删除分布式锁时判断身份使用
     * @param expireTime 锁过期时间，毫秒，防止业务崩溃未删除锁，导致死锁
     * @return 是否获取成功锁
     */
    Boolean getDistributedLock(String key, String value, long expireTime);

    /**
     * 释放分布式锁
     *
     * @param key   key
     * @param value value，需要和获取锁时传入的一致
     * @return 是否释放成功锁
     */
    Boolean releaseDistributedLock(String key, String value);

    /**
     * 分布式限流队列，在时间窗口内（包含该时间点），判断是否达到限流的阀值
     * 本接口实现的方法通过加锁避免并发问题，性能不高。只是为了说明限流逻辑如何实现
     *
     * @param key        key
     * @param count      限流阀值
     * @param timeWindow 限流时间窗口
     * @return 是否允许通过（通过即不进行限流）
     */
    Boolean slideWindow(String key, int count, long timeWindow);

    /**
     * 分布式限流队列，在时间窗口内（包含该时间点），判断是否达到限流的阀值
     * 本接口实现的方法通过 Lua 脚本避免并发问题，性能较高。
     *
     * @param key        key
     * @param count      限流阀值
     * @param timeWindow 限流时间窗口
     * @return 是否允许通过（通过即不进行限流）
     */
    Boolean slideWindowLua(String key, int count, long timeWindow);

}


