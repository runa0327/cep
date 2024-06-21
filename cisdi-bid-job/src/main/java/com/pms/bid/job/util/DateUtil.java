package com.pms.bid.job.util;

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
}
