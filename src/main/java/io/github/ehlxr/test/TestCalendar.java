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
