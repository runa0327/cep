package com.cisdi.pms.job.utils;

import com.qygly.shared.BaseException;
import com.qygly.shared.SharedConstants;

import java.text.ParseException;
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
