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

package io.github.ehlxr.test;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by ehlxr on 2016/8/11.
 */
public class TestCalendar {

    public static void main(String[] args) {
        long date = 1470844800000l;
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        TimeZone timeZone = calendar.getTimeZone();
        System.out.println(timeZone);
        calendar.setTimeInMillis(date);

        calendar.set(Calendar.HOUR_OF_DAY, 0);

        calendar.set(Calendar.MINUTE, 0);

        calendar.set(Calendar.SECOND, 0);
        Long startDate = calendar.getTimeInMillis();
        System.out.println(startDate);

        calendar.setTimeInMillis(date);

        calendar.set(Calendar.HOUR_OF_DAY, 23);

        calendar.set(Calendar.MINUTE, 59);

        calendar.set(Calendar.SECOND, 59);
        Long endDate = calendar.getTimeInMillis();
        System.out.println(endDate);
    }
}
