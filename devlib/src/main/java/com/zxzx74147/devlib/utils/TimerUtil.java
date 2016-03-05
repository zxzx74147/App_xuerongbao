package com.zxzx74147.devlib.utils;

import java.util.Calendar;

/**
 * Created by zhengxin on 16/3/5.
 */
public class TimerUtil {

    public static int getCurrentYear() {
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        int year = mCalendar.get(Calendar.YEAR);
        return year;
    }
}
