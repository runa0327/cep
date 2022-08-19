package com.cisdi.ext.util;

public class StringUtil {

    public static String codeToSplit(String str) {
        int index = str.indexOf(",");
        if (index != -1){
            str = str.replace(",","','");
        }
        return str;
    }
}
