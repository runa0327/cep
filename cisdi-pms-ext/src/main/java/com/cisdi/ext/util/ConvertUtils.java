package com.cisdi.ext.util;

/**
 * 涉及数值的转换
 */

public class ConvertUtils {

    // 阿拉伯整数转中文整数
    public static String convert(int number) {
        String[] num = {"一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] unit = {"", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千", "万亿"};
        String result = String.valueOf(number);
        char[] ch = result.toCharArray();
        String str = "";
        int length = ch.length;
        for (int i = 0; i < length; i++) {
            int c = (int) ch[i] - 48;
            if (c != 0) {
                str += num[c - 1] + unit[length - i - 1];
            }
        }
        return str;
    }
}
