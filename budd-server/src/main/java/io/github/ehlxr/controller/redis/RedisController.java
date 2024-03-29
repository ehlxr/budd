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

package io.github.ehlxr.controller.redis;

import io.github.ehlxr.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ehlxr
 * @since 2021-08-30 10:08.
 */
@RestController
@Api(tags = "Redis 测试接口")
@RequestMapping("/redis")
public class RedisController {
    private static final Logger log = LoggerFactory.getLogger(RedisController.class);


    /**
     * Redis 锁测试
     */
    @ApiOperation(value = "Redis 锁", notes = "Redis 锁测试")
    @GetMapping("/lock/test")
    public void lockTest() {
        Runnable runnable = () -> {
            String uuid = UUID.randomUUID().toString();
            log.info("{} retry lock...", Thread.currentThread().getName());
            boolean lock = RedisUtil.getDistributedLock("lock", uuid, 30_000);
            if (lock) {
                log.info("{} get lock!", Thread.currentThread().getName());

                try {
                    TimeUnit.SECONDS.sleep(1);
                    // ...
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Boolean locak = RedisUtil.releaseDistributedLock("lock", uuid);
                    log.info("{} release lock {}", Thread.currentThread().getName(), locak);
                }
            }
        };
        new Thread(runnable, "t1").start();
        new Thread(runnable, "t2").start();
    }
}
