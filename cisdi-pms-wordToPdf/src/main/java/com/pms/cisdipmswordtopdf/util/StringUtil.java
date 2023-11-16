package com.pms.cisdipmswordtopdf.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * 获取字符串中的文件磁盘数字信息
     * @param str 字符串
     * @return
     */
    public static String getNum(String str) {
        String num = "";
        Pattern pattern = Pattern.compile("file\\d*");
        Matcher matcher = pattern.matcher(str);
        String fileN = "";
        if (matcher.find()){
            fileN = matcher.group();
        } else {
            return null;
        }
        Pattern pattern1 = Pattern.compile("\\d+");
        Matcher matcher1 = pattern1.matcher(fileN);
        if (matcher1.find()){
            num = matcher1.group();
        }
        return num;
    }
}
