package com.pms.bid.job.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /**
     *
     * @param date 日期
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
     * 根据字符串获取日期
     * @param date
     * @return
     */
    public static Date getDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 将字符串时间戳转为指定格式的字符串日期格式
     * @param timestampStr 时间戳
     * @param dateFormat 格式化要求
     * @return 格式化后日期
     */
    public static String convertTimestampToDateString(String timestampStr, String dateFormat) {
        long timestamp = Long.parseLong(timestampStr);
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    public static Date  convertStringToDate(String dateString,String  formatStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
        Date date = null;
        date = formatter.parse(dateString);
        return date;
    }
}
