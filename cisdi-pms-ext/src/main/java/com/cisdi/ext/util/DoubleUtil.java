package com.cisdi.ext.util;

import java.math.BigDecimal;

public class DoubleUtil {
    public static Double add(Double... doubles) {
        if (doubles == null || doubles.length == 0) {
            return 0d;
        }

        BigDecimal sum = new BigDecimal(doubles[0]);
        for (int i = 1; i < doubles.length; i++) {
            sum = sum.add(new BigDecimal(doubles[i]));
        }
        return sum.doubleValue();
    }
}
