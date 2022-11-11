package com.cisdi.ext.util;

import com.qygly.shared.util.SharedUtil;

/**
 * 涉及数值的转换
 */

public class ConvertUtils {

    // 阿拉伯整数转数字大写整数
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

    // 阿拉伯整数转中文大写整数
    public static String convertChina(int number) {
        String[] num = {"壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String[] unit = {"", "拾", "佰", "仟", "萬", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "萬亿"};
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

    /** 金额角分处理 **/
    public static String convertFen(String str) {
        String[] DUNIT = {"角","分"};
        str = str.replaceAll("0+$", "");
        StringBuffer sb = new StringBuffer();
        if (SharedUtil.isEmptyString(str)){
            return "整";
        }
        if (0 == str.indexOf("0")){
            sb.append(transChinaMoney(str.replace("0",""))).append("分");
        } else {
            String[] arr = str.split("");
            for (int i = 0; i < arr.length; i++) {
                if (i == 0){
                    sb.append(transChinaMoney(arr[i])).append("角");
                }
                if (i == 1){
                    sb.append(transChinaMoney(arr[i])).append("分");
                }
            }
        }

        return sb.toString();
    }

    /** 金额角分处理 **/
    private static String transChinaMoney(String s) {
        if ("0".equals(s)){
            s = "零";
        } else if ("1".equals(s)){
            s = "壹";
        } else if ("2".equals(s)){
            s = "贰";
        } else if ("3".equals(s)){
            s = "叁";
        } else if ("4".equals(s)){
            s = "肆";
        } else if ("5".equals(s)){
            s = "伍";
        } else if ("6".equals(s)){
            s = "陆";
        } else if ("7".equals(s)){
            s = "柒";
        } else if ("8".equals(s)){
            s = "捌";
        } else if ("9".equals(s)){
            s = "玖";
        }
        return s;
    }
}
