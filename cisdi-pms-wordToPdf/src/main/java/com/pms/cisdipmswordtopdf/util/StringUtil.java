package com.pms.cisdipmswordtopdf.util;

public class StringUtil {

    /**
     *
     * @param str 原字符串
     * @param code1 旧字符
     * @param code2 新字符
     * @return
     */
    public static String replaceCode(String str, String code1, String code2) {
        int index = str.indexOf(code1);
        if (index != -1){
            str = str.replace(code1,code2);
        }
        return str;
    }
}
