package com.cisdi.ext.util;

public class AmtUtil {
    public static void checkAmt(StringBuilder sbErr, Double amt, String amtName) {
        if (amt == null) {
            sbErr.append(amtName + "不能为空！");
        } else if (amt < 0) {
            sbErr.append(amtName + "不能小于0！");
        }
    }
}
