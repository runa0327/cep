package com.cisdi.pms.util;

import com.qygly.shared.util.SharedUtil;

public class StringUtilsNew {

    /**
     * 根据特点字符替换
     * @param str 原字符串
     * @param code1 需要被替换的字符
     * @param code2 需要被替换成的字符
     * @return
     */
    public static String replaceByCode(String str, String code1, String code2) {
        if (!SharedUtil.isEmptyString(str)){
            int index = str.indexOf(code1);
            if (index != -1){
                str = str.replace(code1,code2);
            }
        }
        return str;
    }
}
