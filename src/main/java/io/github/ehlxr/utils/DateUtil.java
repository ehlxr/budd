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

package io.github.ehlxr.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author ehlxr
 * @since 2020-12-09 16:51.
 */
public class DateUtil {
    public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter FORMAT_1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
    public static final DateTimeFormatter FORMAT_2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter FORMAT_3 = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter FORMAT_4 = DateTimeFormatter.ofPattern("yyyy-MM");
    /**
     * "Asia/Shanghai"
     */
    private static final ZoneId ZONE_ID = ZoneId.of(ZoneId.SHORT_IDS.get("CTT"));

    private DateUtil() {
    }

    /**
     * 获取当前日期时间
     *
     * @return 当前日期时间 {@link LocalDateTime}
     */
    public static LocalDateTime currentDateTime() {
        return LocalDateTime.now(ZONE_ID);
    }

    /**
     * 获取当前日期时间字符串
     *
     * @return "yyyy-MM-dd HH:mm:ss" 格式字符串
     */
    public static String currentDateTimeStr() {
        return LocalDateTime.now(ZONE_ID).format(FORMAT);
    }

    /**
     * 获取当前日期字符串
     *
     * @return "yyyy-MM-dd" 格式字符串
     */
    public static String currentDateStr() {
        return LocalDate.now(ZONE_ID).toString();
    }

    /**
     * 计算当前时间以前几个月的时间
     *
     * @param amount 月份
     * @return "yyyy-MM-dd HH:mm:ss" 格式字符串
     */
    public static String pastMonthsDateTime(long amount) {
        return LocalDateTime.now(ZONE_ID).minus(amount, ChronoUnit.MONTHS).format(FORMAT);
    }

    /**
     * 计算当前时间以前几个月的时间
     *
     * @param amount 月份
     * @return "yyyy-MM-dd" 格式字符串
     */
    public static String pastMonthsDate(long amount) {
        return LocalDate.now(ZONE_ID).minus(amount, ChronoUnit.MONTHS).toString();
    }

    /**
     * 计算指定时间（yyyy-MM-dd HH:mm:ss 格式字符串）以前几个月的时间
     *
     * @param amount 月份
     * @return "yyyy-MM-dd HH:mm:ss" 格式字符串
     */
    public static String pastMonthsDate(String localDateTime, long amount) {
        return LocalDateTime.parse(localDateTime, FORMAT).minus(amount, ChronoUnit.MONTHS).format(FORMAT);
    }

    /**
     * 指定日期格式化，带星期
     */
    public static String asStringWithWeek(String date) {
        LocalDateTime localDateTime = LocalDateTime.parse(date, FORMAT);
        String[] weekDays = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
        int w = localDateTime.getDayOfWeek().getValue() - 1;
        return localDateTime.format(FORMAT) + " " + weekDays[w];
    }

    /**
     * LocalDate 转 Date
     *
     * @param localDate 要转换的 {@link LocalDate}
     * @return {@link Date}
     */
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZONE_ID).toInstant());
    }

    /**
     * LocalDateTime 转 Date
     *
     * @param localDateTime 要转换的 {@link LocalDateTime}
     * @return {@link Date}
     */
    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZONE_ID).toInstant());
    }

    /**
     * Date 转 LocalDate
     *
     * @param date 要转换的 {@link Date}
     * @return {@link LocalDate}
     */
    public static LocalDate asLocalDate(Date date) {
        return date.toInstant().atZone(ZONE_ID).toLocalDate();
        // return Instant.ofEpochMilli(date.getTime()).atZone(ZONE_ID).toLocalDate();
    }

    /**
     * Date 转 LocalDateTime
     *
     * @param date 要转换的 {@link Date}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime asLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZONE_ID);
        // return date.toInstant().atZone(ZONE_ID).toLocalDateTime();
        // return Instant.ofEpochMilli(date.getTime()).atZone(ZONE_ID).toLocalDateTime();
    }

    /**
     * 日期字符串转 LocalDateTime
     *
     * @param date 要转换的 yyyy-MM-dd HH:mm:ss 类型字符串
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime asLocalDateTime(String date) {
        return asLocalDateTime(date, FORMAT);
    }

    /**
     * 日期字符串转 LocalDateTime
     *
     * @param date    要转换的字符串
     * @param pattern 模式字符串，例如：yyyy-MM-dd HH:mm:ss
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime asLocalDateTime(String date, String pattern) {
        return asLocalDateTime(date, DateTimeFormatter.ofPattern(pattern));
    }

    private static LocalDateTime asLocalDateTime(String date, DateTimeFormatter formatter) {
        return LocalDateTime.parse(date, formatter);
    }

    public static Date parse(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(strDate);
    }


    /**
     * 出生日期换年龄
     *
     * @param birthDay
     * @return
     */
    public static int getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();
        //出生日期晚于当前时间，无法计算
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }
        //当前年份
        int yearNow = cal.get(Calendar.YEAR);
        //当前月份
        int monthNow = cal.get(Calendar.MONTH);
        //当前日期
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        //计算整岁数
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //当前日期在生日之前，年龄减一
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                //当前月份在生日之前，年龄减一
                age--;
            }
        }
        return age;
    }

    public static Integer getDifMonth(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startDate);
        end.setTime(endDate);
        int result = end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
        int month = (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12;
        return Math.abs(month + result);
    }

    public static void main(String[] args) {
        try {
            System.out.println(getDifMonth(DateUtil.parse("2020-11-02 15:43:37"), DateUtil.parse("2020-10-01 15:43:37")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(asStringWithWeek(currentDateTimeStr()));
        System.out.println("DateUtil.currentDateTime()：" + DateUtil.currentDateTimeStr());
        System.out.println("DateUtil.currentDate()：" + DateUtil.currentDateStr());
        System.out.println("DateUtil.passMonthsDateTime(3)：" + DateUtil.pastMonthsDateTime(3));
        System.out.println("DateUtil.passMonthsDate(3)：" + DateUtil.pastMonthsDate(3));

        // DateTimeFormatter t = new DateTimeFormatterBuilder()
        //         .parseCaseInsensitive()
        //         .append(ISO_LOCAL_DATE)
        //         .appendLiteral(' ')
        //         .append(new DateTimeFormatterBuilder()
        //                 .appendValue(HOUR_OF_DAY, 2)
        //                 .appendLiteral(':')
        //                 .appendValue(MINUTE_OF_HOUR, 2)
        //                 .optionalStart()
        //                 .appendLiteral(':')
        //                 .appendValue(SECOND_OF_MINUTE, 2).toFormatter(Locale.CHINA)).toFormatter(Locale.CHINA);
        //
        // System.out.println(LocalDateTime.now().format(t));


        System.out.println("FORMAT Pattern(\"yyyy-MM-dd HH:mm:ss\")：");
        System.out.println("FORMAT_1 Pattern(\"yyyy-MM-dd HH:mm:ss.S\")");
        System.out.println("LocalDateTime.parse(\"2019-07-25 17:15:23.0\", FORMAT_1)：" + LocalDateTime.parse("2019-07-25 17:15:23.0", FORMAT_1));
        System.out.println("passMonthsDate(\"2019-07-25 17:15:23\", 3)：" + pastMonthsDate("2019-07-25 17:15:23", 3));
        System.out.println("LocalDateTime.parse(\"2019-07-25 17:15:23.0\", FORMAT_1).format(FORMAT)：" + LocalDateTime.parse("2019-07-25 17:15:23.0", FORMAT_1).format(FORMAT));
        System.out.println("LocalDate.parse(\"2019-07-25 17:15:23.0\", FORMAT).toString()：" + LocalDate.parse("2019-07-25 17:15:23.0", FORMAT_1).toString());


        // Set<String> set = ZoneId.getAvailableZoneIds();
        // set.forEach(System.out::println);

        // createTime = "2019-07-25 17:15:23";
        // createTime = createTime.substring(0, 19);
        // System.out.println(createTime);

        Date date = new Date();
        System.out.println("当前时间 Date：" + date);
        System.out.println("当前时间 Date asLocalDate：" + DateUtil.asLocalDate(date));
        System.out.println("当前时间 Date asLocalDateTime：" + DateUtil.asLocalDateTime(date).format(FORMAT));

        LocalDateTime localDateTime = DateUtil.currentDateTime();
        System.out.println("当前时间 localDateTime：" + localDateTime);
        System.out.println("当前时间 LocalDateTime asDate：" + DateUtil.asDate(localDateTime));
        System.out.println("当前时间 LocalDate asDate：" + DateUtil.asDate(LocalDate.now()));

        System.out.println(ChronoUnit.YEARS.between(LocalDate.now(), LocalDate.parse("1991-08-30", FORMAT_2)));
    }
}