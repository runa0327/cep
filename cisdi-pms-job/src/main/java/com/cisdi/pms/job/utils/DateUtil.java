package com.cisdi.pms.job.utils;

import java.text.SimpleDateFormat;
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

}
