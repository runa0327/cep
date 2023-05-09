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
