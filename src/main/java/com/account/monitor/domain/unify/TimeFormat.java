package com.account.monitor.domain.unify;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeFormat {
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss.SSS Z").withZoneUTC();


    public static final String formatTime(DateTime time) {
        return TIME_FORMATTER.print(time);
    }

    public static final DateTime parseTime(String time) {
        try {
            return TIME_FORMATTER.parseDateTime(time);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
