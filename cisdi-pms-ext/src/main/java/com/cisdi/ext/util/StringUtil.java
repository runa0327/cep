package com.cisdi.ext.util;

import com.google.common.base.Strings;
import com.qygly.shared.util.SharedUtil;

public class StringUtil {

    public static String codeToSplit(String str) {
        if (SharedUtil.isEmptyString(str)){
            return null;
        }
        int index = str.indexOf(",");
        if (index != -1) {
            str = str.replace(",", "','");
        }
        return str;
    }

    // 去掉时间字符串的T
    public static String withOutT(String dateStr) {
        if (Strings.isNullOrEmpty(dateStr)) {
            return null;
        }
        String result = dateStr.replaceAll("T", " ");
        return result;
    }

    /**
     * 替换字符串中的某些符号
     * @param oldStr 原字符串
     * @param code 替换字符
     * @return
     */
    public static String codeToUseSql(String oldStr, String code) {
        if ("、".equals(code)){
            oldStr = oldStr.replace("、","','");
        }
        return oldStr;
    }
}
