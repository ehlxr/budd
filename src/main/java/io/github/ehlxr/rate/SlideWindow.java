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

package io.github.ehlxr.rate;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 滑动时间窗口限流工具
 * 本限流工具只适用于单机版，如果想要做全局限流，可以按本程序的思想，用redis的List结构去实现
 * https://www.cnblogs.com/dijia478/p/13807826.html#!comments
 *
 * @author dijia478
 * @date 2020-10-13 10:53
 */
public class SlideWindow {
    /**
     * 限制次数
     */
    private final int count;

    /**
     * 时间窗口大小(毫秒)
     */
    private final long timeWindow;

    /**
     * 限流队列
     */
    private final List<Long> list;

    public SlideWindow(int count, long timeWindow) {
        this.count = count;
        this.timeWindow = timeWindow;

        list = new LinkedList<>();
    }

    /**
     * 滑动时间窗口限流算法
     * 在指定时间窗口，指定限制次数内，是否允许通过
     *
     * @return 是否允许通过
     */
    public synchronized boolean acquire() {
        // 获取当前时间
        long nowTime = System.currentTimeMillis();
        // 如果队列还没满，则允许通过，并添加当前时间戳到队列开始位置
        if (list.size() < count) {
            // 把之前这个位置的数据给依次向后移动
            list.add(0, nowTime);
            return true;
        }

        // 队列已满（达到限制次数），则获取队列中最早添加的时间戳
        // 用当前时间戳 减去 最早添加的时间戳
        if (nowTime - list.get(count - 1) <= timeWindow) {
            // 若结果小于等于timeWindow，则说明在timeWindow内，通过的次数大于count
            // 不允许通过
            return false;
        } else {
            // 若结果大于timeWindow，则说明在timeWindow内，通过的次数小于等于count
            // 允许通过，并删除最早添加的时间戳，将当前时间添加到队列开始位置
            list.remove(count - 1);
            list.add(0, nowTime);
            return true;
        }
    }

    public synchronized void tryAcquire() throws InterruptedException {
        long nowTime = System.currentTimeMillis();
        if (list.size() < count) {
            list.add(0, nowTime);
            return;
        }

        long l = nowTime - list.get(count - 1);
        if (l <= timeWindow) {
            // 等待
            TimeUnit.MILLISECONDS.sleep(timeWindow - l);
            tryAcquire();
        } else {
            list.remove(count - 1);
            list.add(0, nowTime);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SlideWindow slideWindow = new SlideWindow(5, 1000);

        while (true) {
            // 任意1秒内，只允许5次通过
            // if (slideWindow.acquire()) {
            //     System.out.println(System.currentTimeMillis());
            // }
            slideWindow.tryAcquire();

            System.out.println(System.currentTimeMillis());

            // 睡眠0-10ms
            Thread.sleep(new Random().nextInt(10));
        }

    }

}