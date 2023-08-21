package com.cisdi.pms.job.utils;

import com.qygly.shared.BaseException;
import com.qygly.shared.SharedConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
     * 日期加N天
     *
     * @param paramDate
     * @param days
     * @return
     */
    public static Date addDays(Date paramDate, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(paramDate);
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTime();
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
     * 非星期五情况下，推断当天所属周的
     * @param week 当天周几。0为周天
     * @param date 当天日期
     * @return 周几集合
     */
    public static Map<String, String> getDateMap(int week, Date date) {
        Map<String,String> map = new HashMap<>();
        Date start = date;
        Date end = date;
        if (week == 1){
            start = DateUtil.addDays(date,-3);
            end = DateUtil.addDays(date,3);
        } else if ( week == 2){
            start = DateUtil.addDays(date,-4);
            end = DateUtil.addDays(date,2);
        } else if ( week == 3){
            start = DateUtil.addDays(date,-5);
            end = DateUtil.addDays(date,1);
        } else if ( week == 4){
            start = DateUtil.addDays(date,-6);
        } else if ( week == 5){
            end = DateUtil.addDays(date,6);
        } else if ( week == 6){
            start = DateUtil.addDays(date,-1);
            end = DateUtil.addDays(date,5);
        } else if ( week == 0){
            start = DateUtil.addDays(date,-2);
            end = DateUtil.addDays(date,4);
        }
        map.put("startDate",DateUtil.getTimeStrDay(start));
        map.put("endDate",DateUtil.getTimeStrDay(end));
        return map;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 将字符串转换为日期。
     */
    public static Date stringToDate(String dateString) {
        if (dateString == null) {
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SharedConstants.DATE_FORMAT);
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new BaseException(e);
        }
    }

}
