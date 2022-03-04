/*
 * The MIT License (MIT)
 *
 * Copyright © 2022 xrv <xrg@live.com>
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

/**
 * @author ehlxr
 * @since 2022-03-03 11:24.
 */
public final class SemaphoreInfo {
    // 信号量的名称
    private final String semaphoreName;
    // 许可证的数量
    private final int permits;
    // 信号量最大持有时间 (过期时间) 单位 s
    private final long expire;
    // 公平 or 非公平
    private final boolean fair;

    public SemaphoreInfo(String semaphoreName, int permits, long expire) {
        this(semaphoreName, permits, expire, false);
    }

    public SemaphoreInfo(String semaphoreName, int permits, long expire, boolean fair) {
        this.semaphoreName = semaphoreName;
        this.permits = permits;
        this.expire = expire;
        this.fair = fair;
    }

    public String getSemaphoreName() {
        return semaphoreName;
    }

    public int getPermits() {
        return permits;
    }

    public long getExpire() {
        return expire;
    }

    public boolean isFair() {
        return fair;
    }
}