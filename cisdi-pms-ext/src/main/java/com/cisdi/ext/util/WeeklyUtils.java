package com.cisdi.ext.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title WeeklyUtils
 * @package com.cisdi.pms.job.utils
 * @description
 * @date 2022/10/18
 */
public class WeeklyUtils {

    /**
     * 获取当前时间所在自然周的起止日期
     *
     * @return
     */
    public static Map<String, String> weekBeginningAndEnding() {
        Map<String, String> map = new HashMap<>();
        //获取当前自然周中每天的日期集合
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);        //这里设置一周开始时间是星期一
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        String beginTime = format.format(c.getTime());      //获取当前自然周的起始时间
        map.put("begin", beginTime);

        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        String endTime = format.format(c.getTime());        //当前自然周的截止时间
        map.put("end", endTime);
        return map;
    }

    /**
     * 获取时间所在自然周的起止日期
     *
     * @return
     */
    public static Map<String, String> weekBeginningAndEnding(Date paramDate) {
        Map<String, String> map = new HashMap<>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);        //这里设置一周开始时间是星期一
        c.setTime(paramDate);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        String beginTime = format.format(c.getTime());      //获取当前自然周的起始时间
        map.put("begin", beginTime);

        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        String endTime = format.format(c.getTime());        //当前自然周的截止时间
        map.put("end", endTime);
        return map;
    }

    /**
     * 获取开始和结束之间的每一天
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param type      返回列表中的时间格式
     * @return 返回日期字符串列表
     */
    public static List<String> weekDays(Date beginTime, Date endTime, String type) {
        DateFormat format = new SimpleDateFormat(type);
        //设置开始时间
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(beginTime);

        //设置结束时间
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(endTime);

        //返回的日期集合
        List<String> dateList = new ArrayList<String>();
        //每次循环给calStart日期加一天，直到calBegin.getTime()时间等于dEnd
        dateList.add(format.format(calStart.getTime()));
        while (endTime.after(calStart.getTime())) {
            //根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calStart.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(format.format(calStart.getTime()));
        }
        return dateList;
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
     * 获取当前时间在一年中是第几周
     *
     * @param paramDate
     * @return
     */
    public static int getWeekCount(Date paramDate) {
        Calendar calendar = Calendar.getInstance();
        //设置星期一为一周开始的第一天
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        //获得当前的时间戳
        calendar.setTime(paramDate);
        //获得当前日期属于今年的第几周
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static void main(String[] args) throws ParseException {
        String asd = "2021-12-01";
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sd.parse(asd);
//        Map<String, String> res = WeeklyUtils.weekBeginningAndEnding(date);
//        System.out.println(res);

//        Date res = WeeklyUtils.addDays(new Date(), 2);

        int res = WeeklyUtils.getWeekCount(date);
        System.out.println(res);
    }

}
