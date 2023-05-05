package com.cisdi.pms.job.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author dlt
 * @date 2023/3/27 周一
 */
public class DateUtil {
    /**
     *
     * @param date
     * @return 获取"yyyy-MM-dd HH:mm:ss"形式日期字符串
     */
    public static String getNormalTimeStr(Date date){
        if (date == null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 获取当天周几
     */
    public static int getWeekDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekIdx = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekIdx;
    }

    /**
     * 格式化日期
     * @param date 日期
     * @return 字符串类型日期
     */
    public static String getTimeStrDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 日期加N天
     * @param date 指定时间
     * @param days 延期days天
     * @return 增加日期后的时间
     */
    public static Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTime();
    }
}
