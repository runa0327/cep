package com.cisdi.pms.job.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 将cookies字符串转map
     */
    public static Map<String,String> cookieToMap(String value) {
        Map<String, String> map = new HashMap<String, String>();
        value = value.replace(" ", "");
        if (value.contains(";")) {
            String values[] = value.split(";");
            for (String val : values) {
                String vals[] = val.split("=");
                map.put(vals[0], vals[1]);
            }
        } else {
            String values[] = value.split("=");
            map.put(values[0], values[1]);
        }
        return map;
    }
}
