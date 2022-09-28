package com.cisdi.ext.util;

import java.math.BigDecimal;

public class DoubleUtil {
    public static Double add(Double... doubles) {
        if (doubles == null || doubles.length == 0) {
            return 0d;
        }

        for (int i = 0; i < doubles.length; i++) {
            if (doubles[i] == null) {
                doubles[i] = 0d;
            }
        }

        BigDecimal sum = new BigDecimal(doubles[0]);
        for (int i = 1; i < doubles.length; i++) {
            sum = sum.add(new BigDecimal(doubles[i]));
        }
        return sum.doubleValue();
    }

    public static Double multiply(Double d1, Double d2) {
        if (d1 == null || d2 == null) {
            return 0d;
        }

        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        BigDecimal multiply = b1.multiply(b2);
        return multiply.doubleValue();
    }
}
