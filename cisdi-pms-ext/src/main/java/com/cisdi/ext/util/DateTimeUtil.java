package com.cisdi.ext.util;

import com.qygly.shared.BaseException;
import com.qygly.shared.SharedConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 *
 */
public class DateTimeUtil {

    /**
     * 将日期转换为字符串。
     */
    public static String dateToString(Object date) {
        if (date == null) {
            return null;
        } else if (date instanceof String) {
            // 若为字符串类型，则直接返回：
            // 通常，只出现在外部微服务通过insertSingleBySev方法传入字段的值，该值形成DrivenInfo对象，再传入newData方法，通过SELECT形成默认值（待插入），调用convertResultSetObjectToValue方法，再调用本方法时：
            return (String) date;
        } else {
            // 一般情况下，从数据库获得的都是Date类型：
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SharedConstants.DATE_FORMAT);
            return simpleDateFormat.format(date);
        }
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

    /**
     * 将时间转换为字符串。
     */
    public static String timeToString(Object time) {
        if (time == null) {
            return null;
        } else if (time instanceof String) {
            // 若为字符串类型，则直接返回：
            // 通常，只出现在外部微服务通过insertSingleBySev方法传入字段的值，该值形成DrivenInfo对象，再传入newData方法，通过SELECT形成默认值（待插入），调用convertResultSetObjectToValue方法，再调用本方法时：
            return (String) time;
        } else {
            // 一般情况下，从数据库获得的都是Date类型（或LocalDateTime类型）：
            if (time instanceof LocalDateTime) {
                time = localDateTimeToDate((LocalDateTime) time);
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SharedConstants.TIME_FORMAT);
            return simpleDateFormat.format(time);
        }
    }

    /**
     * 将字符串转换为时间。
     */
    public static Date stringToTime(String timeString) {
        if (timeString == null) {
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SharedConstants.TIME_FORMAT);
        try {
            return simpleDateFormat.parse(timeString);
        } catch (ParseException e) {
            throw new BaseException(e);
        }
    }

    /**
     * 将日期时间转换为字符串。
     */
    public static String dttmToString(Object dttm) {
        if (dttm == null) {
            return null;
        } else if (dttm instanceof String) {
            // 若为字符串类型，则直接返回：
            // 通常，只出现在外部微服务通过insertSingleBySev方法传入字段的值，该值形成DrivenInfo对象，再传入newData方法，通过SELECT形成默认值（待插入），调用convertResultSetObjectToValue方法，再调用本方法时：
            return (String) dttm;
        } else {
            // 一般情况下，从数据库获得的都是Date类型（或LocalDateTime类型）：
            if (dttm instanceof LocalDateTime) {
                dttm = localDateTimeToDate((LocalDateTime) dttm);
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SharedConstants.DTTM_FORMAT);
            return simpleDateFormat.format(dttm);
        }
    }

    /**
     * 将字符串转换为日期时间。
     */
    public static Date stringToDttm(String dttmString) {
        if (dttmString == null) {
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SharedConstants.DTTM_FORMAT);
        try {
            return simpleDateFormat.parse(dttmString);
        } catch (ParseException e) {
            throw new BaseException(e);
        }
    }

    /**
     * 将时间戳转换为精确到毫秒字符串。如：长整数：1529715181703，转换为字符串：2018-06-23 09:38:05.123。
     */
    public static String timeStampToMilliStr(Long timeStamp) {
        if (timeStamp == null) {
            return null;
        }

        Date date = new Date(timeStamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SharedConstants.MILLI_FORMAT);
        return simpleDateFormat.format(date);
    }

    /**
     * 将时间戳转换为精确到毫秒字符串。如：字符串：2018-06-23 09:38:05.123，转换为长整数：1529715181703。
     */
    public static Date milliStringToDate(String milliString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SharedConstants.MILLI_FORMAT);
        try {
            Date date = simpleDateFormat.parse(milliString);
            return date;
        } catch (ParseException e) {
            throw new BaseException(e);
        }
    }

    /**
     * 将日期转换为时间戳。如：字符串：2018-06-23 09:38:05.123，转换为长整数：1529715181703。
     */
    public static Long dateToTimeStamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }

    /**
     * 将日期转换为时间戳。如：字符串：2018-06-23 09:38:05.123，转换为长整数：1529715181703。
     */
    public static Date timeStampToDate(Long milli) {
        if (milli == null) {
            return null;
        } else {
            return new Date(milli);
        }
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return date;
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
     * 计算两个日期相差天数 日期格式 yyyy-MM-dd  格式带时分秒计算天数可能会不准
     *
     * @param maxDate
     * @param end
     * @return
     */
    public static int getTwoTimeDays(Date maxDate, Date end) {
        return Math.abs((int) ((maxDate.getTime() - end.getTime()) / (1000 * 3600 * 24)));
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
     * 　　 *字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }


    public static void main(String[] args) throws ParseException {
        int asd = DateTimeUtil.getTwoTimeDays(DateTimeUtil.stringToDate("2022-09-01"), DateTimeUtil.stringToDate("2022-09-10"));
        System.out.println(asd);
    }

    /** 计算两个时间字符串（yyyy-MM-dd)之间时间差 **/
    public static int getTwoTimeStringDays(String end, String start) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date smdate = sdf.parse(end);
        Date bdate = sdf.parse(start);
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time1 - time2) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

}
