/*
 * The MIT License (MIT)
 *
 * Copyright © 2020 xrv <xrv@live.com>
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

import com.google.common.collect.ImmutableMap;
import io.github.ehlxr.common.Constant;
import org.slf4j.MDC;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * 预警信息发送工具类
 *
 * @author ehlxr
 * @since 2020-12-23 17:47.
 */
public class SendAlarmUtil {
    private static String dingtalkUrl;

    public static void init(String dingtalkUrl) {
        Objects.requireNonNull(dingtalkUrl, "dingtalkUrl not allow null");

        SendAlarmUtil.dingtalkUrl = dingtalkUrl;
    }

    /**
     * 异步发送预警信息
     *
     * @param message 预警信息
     * @param action  {@link BiConsumer} 完成后回调函数式接口
     */
    public static void send(String message, BiConsumer<? super String, ? super Throwable> action) {
        String msg = String.format("%s\ntraceid: %s", message, MDC.get(Constant.LOG_TRACE_ID));

        CompletableFuture.supplyAsync(() -> {
                    Objects.requireNonNull(dingtalkUrl, "SendAlarmUtil not init.");
                    return HttpUtil.post(dingtalkUrl,
                            JsonUtils.obj2String(ImmutableMap.of("msgtype", "text",
                                    "text", ImmutableMap.of("content", msg))));
                }

        ).whenCompleteAsync(action);
    }

    /**
     * 异步发送预警信息
     *
     * @param message 预警信息
     */
    public static void send(String message) {
        send(message, (u, t) -> {
        });
    }

    public static void main(String[] args) throws InterruptedException {
        Try.of(() -> SendAlarmUtil.init(dingtalkUrl))
                .trap(System.out::println)
                .run();

        send("hello",
                (t, u) -> System.out.println("send exception message to dingtalk result " + t + ". " + u));

        System.out.println("do other something....");

        Thread.sleep(2000);
    }
}
