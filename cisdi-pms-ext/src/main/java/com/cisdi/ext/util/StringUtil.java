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
}
