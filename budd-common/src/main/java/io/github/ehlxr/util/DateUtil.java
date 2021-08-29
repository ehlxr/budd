/*
 * The MIT License (MIT)
 *
 * Copyright © 2021 xrv <xrg@live.com>
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

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 日期操作
 *
 * @author ehlxr
 * @since 2021-08-29 18:32.
 */
public class DateUtil {
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String MM_DD = "MM-dd";
    public static final String HH_MM_SS = "HH:mm:ss";

    // private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
    // private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(YYYY_MM_DD);
    // private static final DateTimeFormatter DEFAULT_DATE_NO_YEAR_FORMATTER = DateTimeFormatter.ofPattern(MM_DD);
    // private static final DateTimeFormatter DEFAULT_TIME_FORMATTER = DateTimeFormatter.ofPattern(HH_MM_SS);
    private static final Map<String, DateTimeFormatter> PATTEN_FORMATTER_MAPPER = Collections.synchronizedMap(new WeakHashMap<>());

    // static {
    //     PATTEN_FORMATTER_MAPPER.put(YYYY_MM_DD_HH_MM_SS, DEFAULT_DATE_TIME_FORMATTER);
    //     PATTEN_FORMATTER_MAPPER.put(YYYY_MM_DD, DEFAULT_DATE_FORMATTER);
    //     PATTEN_FORMATTER_MAPPER.put(MM_DD, DEFAULT_DATE_NO_YEAR_FORMATTER);
    //     PATTEN_FORMATTER_MAPPER.put(HH_MM_SS, DEFAULT_TIME_FORMATTER);
    // }

    private static DateTimeFormatter cacheFormatterAndGet(String patten) {
        DateTimeFormatter dateTimeFormatter = PATTEN_FORMATTER_MAPPER.get(patten);
        if (dateTimeFormatter == null) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(patten).withZone(ZoneId.of("+8"));
            PATTEN_FORMATTER_MAPPER.put(patten, dateTimeFormatter);
        }
        return dateTimeFormatter;
    }

    /**
     * @param localDateTime date time
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(cacheFormatterAndGet(YYYY_MM_DD_HH_MM_SS));
    }

    /**
     * @param localDateTime time
     * @param patten        yyyy-MM-dd HH:mm:ss
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime, String patten) {
        DateTimeFormatter dateTimeFormatter = cacheFormatterAndGet(patten);
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * @param localDate date
     * @param patten    only date patten
     * @return yyyy-MM-dd
     */
    public static String formatLocalDate(LocalDate localDate, String patten) {
        DateTimeFormatter dateTimeFormatter = cacheFormatterAndGet(patten);
        return localDate.format(dateTimeFormatter);
    }

    /**
     * @param localDate localDate
     * @return yyyy-MM-dd
     */
    public static String formatLocalDate(LocalDate localDate) {
        return localDate.format(cacheFormatterAndGet(YYYY_MM_DD));
    }

    /**
     * @param localTime localTime
     * @param patten    patten
     * @return HH:mm:ss
     */
    public static String formatLocalTime(LocalTime localTime, String patten) {
        DateTimeFormatter dateTimeFormatter = cacheFormatterAndGet(patten);
        return localTime.format(dateTimeFormatter);
    }

    /**
     * @param localTime localTime
     * @return HH:mm:ss
     */
    public static String formatLocalTime(LocalTime localTime) {
        return localTime.format(cacheFormatterAndGet(HH_MM_SS));
    }

    /**
     * @param date   date time
     * @param patten patten
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String format(Date date, String patten) {
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("+8"));
        return localDateTime.format(cacheFormatterAndGet(patten));
    }

    /**
     * @param date date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String format(Date date) {
        return format(date, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * @param date date
     * @return yyyy-MM-dd
     */
    public static String formatDate(Date date) {
        return format(date, YYYY_MM_DD);
    }

    /**
     * @param date date
     * @return HH:mm:ss
     */
    public static String formatTime(Date date) {
        return format(date, HH_MM_SS);
    }

    /**
     * @param mills mills
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatMills(long mills, String patten) {
        Instant instant = Instant.ofEpochMilli(mills);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("+8"));
        return formatLocalDateTime(localDateTime, patten);
    }

    /**
     * @param mills mills
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatMills(long mills) {
        return formatMills(mills, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * @param mills mills
     * @return yyyy-MM-dd
     */
    public static String formatMillsDate(long mills) {
        return formatMills(mills, YYYY_MM_DD);
    }

    /**
     * @param date date
     * @return HH:mm:ss
     */
    public static String formatMillsTime(long date) {
        return formatMills(date, HH_MM_SS);
    }

    /**
     * @param date yyyy-MM-dd HH:mm:ss
     * @return Date
     */
    public static Date parse(String date) {
        LocalDateTime localDateTime = parseToLocalDateTime(date);
        Instant instant = localDateTime.toInstant(OffsetDateTime.now().getOffset());
        return Date.from(instant);
    }

    /**
     * @param date   string date
     * @param patten formatter patten
     * @return LocalDateTime
     */
    public static LocalDateTime parseToLocalDateTime(String date, String patten) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(patten));
    }

    /**
     * @param date yyyy-MM-dd HH:mm:ss
     * @return LocalDateTime
     */
    public static LocalDateTime parseToLocalDateTime(String date) {
        return LocalDateTime.parse(date, cacheFormatterAndGet(YYYY_MM_DD_HH_MM_SS));
    }

    /**
     * @param date yyyy-MM-dd HH:mm:ss
     * @return milliseconds
     */
    public static Long parseToMillis(String date) {
        return parseToMillis(date, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * @param date   string date
     * @param patten formatter patten
     * @return milliseconds
     */
    public static Long parseToMillis(String date, String patten) {
        return toInstant(LocalDateTime.parse(date, cacheFormatterAndGet(patten))).toEpochMilli();
    }

    /**
     * @param date yyyy-MM-dd
     * @return LocalDate
     */
    public static LocalDate parseToLocalDate(String date) {
        return LocalDate.parse(date, cacheFormatterAndGet(YYYY_MM_DD));
    }

    /**
     * @param date HH:mm:ss
     * @return LocalTime
     */
    public static LocalTime parseToLocalTime(String date) {
        return LocalTime.parse(date, cacheFormatterAndGet(HH_MM_SS));
    }

    /**
     * @param dayStart date
     * @param dayEnd   date
     */
    public static Period betweenDays(Date dayStart, Date dayEnd) {
        LocalDateTime localDateTimeStart = LocalDateTime.ofInstant(dayStart.toInstant(), OffsetDateTime.now().getOffset());
        LocalDateTime localDateTimeEnd = LocalDateTime.ofInstant(dayEnd.toInstant(), OffsetDateTime.now().getOffset());
        return Period.between(localDateTimeStart.toLocalDate(), localDateTimeEnd.toLocalDate());
    }

    /**
     * @param dayStart date
     * @param dayEnd   date
     */
    public static Duration betweenTimes(Date dayStart, Date dayEnd) {
        LocalDateTime localDateTimeStart = LocalDateTime.ofInstant(dayStart.toInstant(), OffsetDateTime.now().getOffset());
        LocalDateTime localDateTimeEnd = LocalDateTime.ofInstant(dayEnd.toInstant(), OffsetDateTime.now().getOffset());
        return Duration.between(localDateTimeStart, localDateTimeEnd);
    }

    /**
     * @param dayStart date
     * @param dayEnd   date
     */
    public static Period betweenDays(String dayStart, String dayEnd) {
        return Period.between(parseToLocalDate(dayStart), parseToLocalDate(dayEnd));
    }

    /**
     * @param dayStart date
     * @param dayEnd   date
     */
    public static Duration betweenTimes(String dayStart, String dayEnd) {
        return Duration.between(parseToLocalDateTime(dayStart), parseToLocalDateTime(dayEnd));
    }

    /**
     * 每月开始、结束毫秒时间戳
     */
    public static ImmutablePair<Long, Long> firstAndLastMillisOfMonth() {
        LocalDate today = LocalDate.now();
        Long firstDay = toInstant(LocalDateTime.of(today.with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN)).toEpochMilli();
        Long lastDay = toInstant(LocalDateTime.of(today.with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX)).toEpochMilli();
        return ImmutablePair.of(firstDay, lastDay);
    }

    public static Instant toInstant(ChronoLocalDateTime<?> d) {
        return d.toInstant(ZoneOffset.of("+8"));
    }

    /**
     * 当前秒级别时间戳
     *
     * @return 10 位时间戳
     */
    public static Long currentTimeSecond() {
        return toInstant(LocalDateTime.now()).getEpochSecond();
    }

    /**
     * 之前的多少天
     *
     * @param days 之前的天数
     * @return yyyy-MM-dd
     */
    public static String beforeDay(long days) {
        return formatLocalDate(LocalDate.now().minusDays(days));
    }
}