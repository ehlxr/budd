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

package io.github.ehlxr.semaphore;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ZSetOperations;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

/**
 * @author ehlxr
 * @since 2022-03-03 11:25.
 */
public class RedisSemaphore implements DistributedSemaphore {
    private static final String SEMAPHORE_TIME_KEY = "budd:semaphore:time:";
    private static final String SEMAPHORE_OWNER_KEY = "budd:semaphore:owner:";
    private static final String SEMAPHORE_COUNTER_KEY = "budd:semaphore:counter:";
    private final RedisTemplate<String, String> redisTemplate;
    private final String timeKey;
    private final String ownerKey;
    private final String counterKey;
    // 信号量的信息
    private final SemaphoreInfo info;
    // 信号量实体
    private final DistributedSemaphore semaphore;
    // 身份证明
    private final String identification;

    public RedisSemaphore(SemaphoreInfo info, RedisTemplate<String, String> redisTemplate,
                          String identification) {
        this.info = info;
        this.redisTemplate = redisTemplate;
        this.timeKey = SEMAPHORE_TIME_KEY.concat(info.getSemaphoreName());
        this.ownerKey = SEMAPHORE_OWNER_KEY.concat(info.getSemaphoreName());
        this.counterKey = SEMAPHORE_COUNTER_KEY.concat(info.getSemaphoreName());
        this.semaphore = info.isFair() ? new FairSemaphore() : new NonfairSemaphore();
        this.identification = identification;
    }

    @Override
    public boolean tryAcquire() {
        return semaphore.tryAcquire();
    }

    @Override
    public void release() {
        semaphore.release();
    }

    private class NonfairSemaphore implements DistributedSemaphore {
        @Override
        public boolean tryAcquire() {
            ZSetOperations<String, String> zsetOps = redisTemplate.opsForZSet();
            long timeMillis = System.currentTimeMillis();
            // 先清除过期的信号量
            zsetOps.removeRangeByScore(timeKey, 0, timeMillis - TimeUnit.SECONDS.toMillis(info.getExpire()));
            // 尝试获取信号量并比较自身的排名，如果小于许可证的数量则表示获取成功 (redis rank 指令从 0 开始计数)
            zsetOps.add(timeKey, identification, timeMillis);
            Long rank = zsetOps.rank(timeKey, identification);
            if (rank != null && rank < info.getPermits()) {
                return true;
            }
            // 获取失败，删除掉上边添加的标识
            release();
            return false;
        }

        @Override
        public void release() {
            redisTemplate.opsForZSet().remove(timeKey, identification);
        }
    }

    private class FairSemaphore implements DistributedSemaphore {
        @Override
        public boolean tryAcquire() {
            long timeMillis = System.currentTimeMillis();
            // 用于获取信号量的计数
            Long counter = redisTemplate.opsForValue().increment(counterKey, 1);
            if (counter == null) {
                return false;
            }
            // 用流水线把这一堆命令用一次 IO 全部发过去
            redisTemplate.executePipelined(new SessionCallback<>() {
                @Override
                public Object execute(@Nonnull RedisOperations operations) throws DataAccessException {
                    ZSetOperations<String, Object> zsetOps = operations.opsForZSet();
                    // 利用 timeKey 来控制是否过期，ownerKey 控制是否超配额，
                    // 使用 zset 的 intersectAndStore 在获取信号量之前清理过期数据，同时清理排名集合中的数据
                    // 清除过期的信号量
                    zsetOps.removeRangeByScore(timeKey, 0, timeMillis - TimeUnit.SECONDS.toMillis(info.getExpire()));
                    zsetOps.intersectAndStore(timeKey, ownerKey, ownerKey);
                    // 尝试获取信号量
                    zsetOps.add(timeKey, identification, timeMillis);
                    zsetOps.add(ownerKey, identification, counter);
                    return null;
                }
            });
            // 这里根据 持有者集合 的分数来进行判断
            Long ownerRank = redisTemplate.opsForZSet().rank(ownerKey, identification);
            if (ownerRank != null && ownerRank < info.getPermits()) {
                return true;
            }
            release();
            return false;
        }

        @Override
        public void release() {
            redisTemplate.executePipelined(new SessionCallback<>() {
                @Override
                public Object execute(@Nonnull RedisOperations operations) throws DataAccessException {
                    ZSetOperations<String, Object> zetOps = operations.opsForZSet();
                    zetOps.remove(timeKey, identification);
                    zetOps.remove(ownerKey, identification);
                    return null;
                }
            });
        }
    }
}