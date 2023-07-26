package com.pms.cisdipmswordtopdf.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /**
     * Date时间转String
     * @param date 日期
     * @return 字符串格式日期
     */
    public static String getNowDateStr(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
