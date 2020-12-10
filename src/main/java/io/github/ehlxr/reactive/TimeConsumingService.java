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

package io.github.ehlxr.reactive;

import java.util.Arrays;
import java.util.concurrent.Callable;

/**
 * Created by ehlxr on 2018/1/16.
 */
public class TimeConsumingService implements Callable<String> {

    private final String service_name;
    private final int wait_ms;
    private final Object[] depandencies;

    TimeConsumingService(String name, Integer waiting, Object[] depandencies) {
        this.service_name = name;
        this.wait_ms = waiting;
        this.depandencies = depandencies;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(wait_ms);
        return String.format("service %s exec time is: %d, depandencies: %s", service_name, wait_ms, Arrays.toString(depandencies));
    }
}