package com.zhoukp.signer.utils;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Administrator on 2018/3/12.
 */

public class CUtil {

    public static String getSemester(){
        if ((getCurrentMonth() >= 9) || (getCurrentMonth() < 3)){
            return "一";
        } else {
            return "二";
        }
    }

    public static String getBetweenYear(){
        if ((getCurrentMonth() >= 9) || (getCurrentMonth() < 3)){
            return getCurrentYear() + "-" + (getCurrentYear() + 1);
        } else {
            return (getCurrentYear() - 1) + "-" + getCurrentYear();
        }
    }

    /**
     * 获取今天的日期
     */
    public static String getToday() {

        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        return year + "-" + month + "-" + day;

    }

    /**
     * 获取当前年份
     */
    public static int getCurrentYear() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     */
    public static int getCurrentMonth() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前星期几
     */
    public static int getCurrentDayOfWeek(){
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return c.get(Calendar.DAY_OF_WEEK);
    }

}
