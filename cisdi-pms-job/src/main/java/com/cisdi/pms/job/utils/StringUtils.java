package com.cisdi.pms.job.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    //
    public static List<String> splitByCode(String str, String s) {
        int index = str.indexOf(s);
        List<String> res = new ArrayList<>();
        if (index != 0){
            String[] arr = str.split(s);
            for (String tmp : arr) {
                tmp = tmp.substring(tmp.indexOf("1"),tmp.length()).replace(" ","");
                res.add(tmp);
            }
            return res;
        } else {
            res.add(str);
            return res;
        }

    }

    /**
     * 字符串替换
     * @param str
     * @param s
     * @return s
     */
    public static String replaceCode(String str, String s) {
        str = str.trim().replace(" ","");
        if (",".equals(s)) {
            str = str.replace("，",",");
        }
        return str;
    }
}
