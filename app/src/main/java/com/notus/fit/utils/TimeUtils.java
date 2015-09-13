package com.notus.fit.utils;

import com.facebook.AppEventsConstants;
import com.notus.fit.BuildConfig;
import com.notus.fit.models.RequestTime;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Project: NotusFit
 * Created by Gamunu Balagalla
 * Last Modified: 9/9/2015 8:16 PM
 */
public class TimeUtils {
    public static final LocalDateTime JAN_1_1970 = new LocalDateTime(1970, 1, 1, 0, 0);

    public static RequestTime getWeekRequest() {
        LocalDateTime now = LocalDateTime.now();
        return new RequestTime(getMillisFromLocalDateTime(now.withTime(0, 0, 0, 0).withDayOfWeek(1)), getMillisFromLocalDateTime(now.withTime(23, 59, 59, 999)));
    }

    public static String addZero(int num) {
        return num > 9 ? BuildConfig.FLAVOR + num : AppEventsConstants.EVENT_PARAM_VALUE_NO + num;
    }

    public static List<String> getFitbitWeekRequest() {
        LocalDate now = LocalDate.now();
        List<String> dates = new ArrayList<>();
        LocalDate startDate = now.withDayOfWeek(1);
        while (startDate.isBefore(now)) {
            dates.add(startDate.getYear() + "-" + startDate.getMonthOfYear() + "-" + startDate.getMonthOfYear());
            startDate.plusDays(1);
        }
        return dates;
    }

    public static RequestTime getPreviousWeekRequest() {
        return new RequestTime(getMillisFromLocalDateTime(LocalDateTime.now().withTime(0, 0, 0, 0).withDayOfWeek(1).minusWeeks(1)), getMillisFromLocalDateTime(LocalDateTime.now().withTime(23, 59, 59, 999).withDayOfWeek(7).minusWeeks(1)));
    }

    public static long getMillisFromLocalDateTime(LocalDateTime localDateTime) {
        Date date = localDateTime.toDate();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    public static LocalDate convertFromJawboneDate(Integer date) {
        String jawboneDate = String.valueOf(date);
        String year = jawboneDate.substring(0, 4);
        String month = jawboneDate.substring(4, 6);
        return LocalDate.parse(year + "-" + month + "-" + jawboneDate.substring(6, 8));
    }

    public static String sanitizeDate(long d) {
        LocalDate localDate = new LocalDate(d);
        String month = addZero(localDate.getMonthOfYear());
        return localDate.getYear() + "-" + month + "-" + addZero(localDate.getDayOfMonth());
    }
}

