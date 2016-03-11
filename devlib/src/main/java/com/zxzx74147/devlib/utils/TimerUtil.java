package com.zxzx74147.devlib.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhengxin on 16/3/5.
 */
public class TimerUtil {
    public static final String YMD = "yyyy-MM-dd";

    public static int getCurrentYear() {
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        int year = mCalendar.get(Calendar.YEAR);
        return year;
    }

    public static String getTimeFormat(long mils, String format) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(YMD, Locale.CHINA);
        Date now = new Date(mils * 1000);
        return sdfDate.format(now);
    }
}
