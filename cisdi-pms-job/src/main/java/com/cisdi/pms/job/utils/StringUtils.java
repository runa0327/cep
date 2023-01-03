package com.cisdi.pms.job.utils;

import com.google.common.base.Strings;

import java.text.DecimalFormat;
import java.util.*;

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

    /**
     * 将将带T的时间字符串截取为日期
     */
    public static String getDateWithOutT(String value) {
        if (Strings.isNullOrEmpty(value)){
            return null;
        }
        String[] strings = value.split("T");
        return strings[0];
    }

    /**
     * 将字节数据转为带单位的字符串
     * @param fileSize 文件字节大小
     * @return 带单位的字符串
     */
    public static String formatFileSize(Long fileSize) {
        Long K_SIZE = 1024L;
        Long M_SIZE = K_SIZE * K_SIZE;
        Long G_SIZE = M_SIZE * K_SIZE;
        String fileSizeStr = "";
        if (Objects.isNull(fileSize)) {
            return fileSizeStr;
        }
        if (fileSize == 0L) {
            return "0".concat("B");
        }
        // 小数位数自定义
        DecimalFormat df = new DecimalFormat("#");
        if (fileSize < K_SIZE) {
            fileSizeStr = df.format((double) fileSize) + "B";
        } else if (fileSize < M_SIZE) {
            fileSizeStr = df.format((double) fileSize / K_SIZE) + "K";
        } else if (fileSize < G_SIZE) {
            fileSizeStr = df.format((double) fileSize / M_SIZE) + "M";
        } else {
            fileSizeStr = df.format((double) fileSize / G_SIZE) + "G";
        }
        return fileSizeStr;
    }
}
