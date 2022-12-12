package com.cisdi.ext.util;

import com.qygly.shared.BaseException;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title BigDecimalUtil
 * @package com.cisdi.ext.util
 * @description
 * @date 2022/12/12
 */
public class BigDecimalUtil {


    /**
     * 保留2位小数
     *
     * @param bigDecimal
     * @param round
     * @return
     */
    public static BigDecimal setBigDecimalScale(BigDecimal bigDecimal, int round) {
        return bigDecimal.setScale(round, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 保留2位小数
     *
     * @param param
     * @param round
     * @return
     */
    public static BigDecimal setStringScale(String param, int round) {
        if (param == null) {
            return BigDecimal.ZERO;
        } else {
            return new BigDecimal(param).setScale(round, BigDecimal.ROUND_HALF_UP);
        }
    }


    /**
     * 加法默认保留2位小数
     *
     * @param param1
     * @param param2
     * @return
     */
    public static BigDecimal add(String param1, String param2) {
        return add(param1, param2, 2);
    }

    /**
     * 加
     *
     * @param param1
     * @param param2
     * @return
     */
    public static BigDecimal add(String param1, String param2, int round) {
        BigDecimal add1 = BigDecimal.ZERO;
        BigDecimal add2 = BigDecimal.ZERO;
        if (!StringUtils.isEmpty(param1)) {
            add1 = new BigDecimal(param1);
        }
        if (!StringUtils.isEmpty(param2)) {
            add2 = new BigDecimal(param2);
        }

        return add1.add(add2).setScale(round, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 加法默认保留2位小数
     *
     * @param bigDecimals
     * @return
     */
    public static BigDecimal add(BigDecimal... bigDecimals) {
        return add(2, bigDecimals);
    }

    /**
     * 加法
     *
     * @param round
     * @param bigDecimals
     * @return
     */
    public static BigDecimal add(int round, BigDecimal... bigDecimals) {
        if (bigDecimals == null || bigDecimals.length == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 1; i < bigDecimals.length; i++) {
            BigDecimal item = bigDecimals[i];
            if (item == null) {
                item = BigDecimal.ZERO;
            }
            sum = sum.add(item);
        }
        return sum.setScale(round, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 加法默认保留2位小数
     *
     * @param decimalList
     * @return
     */
    public static BigDecimal add(List<BigDecimal> decimalList) {
        return add(decimalList, 2);
    }

    /**
     * 加法
     *
     * @param decimalList
     * @param round
     * @return
     */
    public static BigDecimal add(List<BigDecimal> decimalList, int round) {
        return decimalList
                .stream()
                .map(BigDecimalUtil::bigDecimalNullToZero)
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(round, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 减法默认保留2位小数
     *
     * @param param1 减数
     * @param param2 被减数
     * @return
     */
    public static BigDecimal sub(String param1, String param2) {
        return sub(param1, param2, 2);
    }

    /**
     * 减法
     *
     * @param param1
     * @param param2
     * @param round
     * @return
     */
    public static BigDecimal sub(String param1, String param2, int round) {
        BigDecimal reduction = BigDecimal.ZERO;
        BigDecimal subtraction = BigDecimal.ZERO;
        if (!StringUtils.isEmpty(param1)) {
            reduction = new BigDecimal(param1);
        }
        if (!StringUtils.isEmpty(param2)) {
            subtraction = new BigDecimal(param2);
        }

        return reduction.subtract(subtraction).setScale(round, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 减法默认保留2位小数
     *
     * @param reduction
     * @param bigDecimals
     * @return
     */
    public static BigDecimal sub(BigDecimal reduction, BigDecimal... bigDecimals) {
        return sub(reduction, 2, bigDecimals);
    }

    /**
     * @param reduction
     * @param round
     * @param bigDecimals
     * @return
     */
    public static BigDecimal sub(BigDecimal reduction, int round, BigDecimal... bigDecimals) {
        if (bigDecimals == null || bigDecimals.length == 0) {
            return BigDecimal.ZERO;
        }
        if (reduction == null) {
            reduction = BigDecimal.ZERO;
        }
        BigDecimal res = reduction;
        for (int i = 1; i < bigDecimals.length; i++) {
            BigDecimal item = bigDecimals[i];
            if (item == null) {
                item = BigDecimal.ZERO;
            }
            res = reduction.subtract(item);
        }
        return res.setScale(round, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 减法默认保留2位小数
     *
     * @param reduction    减数
     * @param subtractions 被减数集合
     * @return
     */
    public static BigDecimal sub(BigDecimal reduction, List<BigDecimal> subtractions) {
        return sub(reduction, subtractions, 2);
    }


    /**
     * 减法
     *
     * @param reduction
     * @param subtractions
     * @param round
     * @return
     */
    public static BigDecimal sub(BigDecimal reduction, List<BigDecimal> subtractions, int round) {
        if (subtractions == null || subtractions.size() == 0) {
            return BigDecimal.ZERO;
        }
        if (reduction == null) {
            reduction = BigDecimal.ZERO;
        }
        BigDecimal res = reduction;
        for (int i = 0; i < subtractions.size(); i++) {
            BigDecimal item = subtractions.get(i);
            if (item == null) {
                item = BigDecimal.ZERO;
            }
            res = res.subtract(item);
        }
        return res.setScale(round, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 乘法默认保留2位小数
     *
     * @param param1
     * @param param2
     * @return
     */
    public static BigDecimal multiply(String param1, String param2) {
        return multiply(param1, param2, 2);
    }

    /**
     * 乘法
     *
     * @param param1
     * @param param2
     * @return
     */
    public static BigDecimal multiply(String param1, String param2, int round) {
        BigDecimal bigDecimal1 = BigDecimal.ZERO;
        BigDecimal bigDecimal2 = BigDecimal.ZERO;
        if (StringUtils.isEmpty(param1)) {
            return BigDecimal.ZERO;
        } else {
            bigDecimal1 = new BigDecimal(param1);
        }
        if (StringUtils.isEmpty(param2)) {
            return BigDecimal.ZERO;
        } else {
            bigDecimal2 = new BigDecimal(param2);
        }
        return bigDecimal1.multiply(bigDecimal2).setScale(round, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 乘法默认保留2位小数
     *
     * @param bigDecimal
     * @param bigDecimals
     * @return
     */
    public static BigDecimal multiply(BigDecimal bigDecimal, BigDecimal... bigDecimals) {
        return multiply(bigDecimal, 2, bigDecimals);
    }

    /**
     * 乘法
     *
     * @param bigDecimal
     * @param round
     * @param bigDecimals
     * @return
     */
    public static BigDecimal multiply(BigDecimal bigDecimal, int round, BigDecimal... bigDecimals) {
        if (bigDecimal == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal res = bigDecimal;
        for (int i = 0; i < bigDecimals.length; i++) {
            BigDecimal item = bigDecimals[i];
            if (item == null) {
                return BigDecimal.ZERO;
            }
            res = res.multiply(item);
        }
        return res.setScale(round, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 乘法默认保留2位小数
     *
     * @param bigDecimal
     * @param bigDecimals
     * @return
     */
    public static BigDecimal multiply(BigDecimal bigDecimal, List<BigDecimal> bigDecimals) {
        return multiply(bigDecimal, bigDecimals, 2);
    }

    /**
     * 乘法
     *
     * @param bigDecimal
     * @param bigDecimals
     * @param round
     * @return
     */
    public static BigDecimal multiply(BigDecimal bigDecimal, List<BigDecimal> bigDecimals, int round) {
        if (bigDecimal == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal res = bigDecimal;
        for (int i = 0; i < bigDecimals.size(); i++) {
            BigDecimal item = bigDecimals.get(i);
            if (item == null) {
                return BigDecimal.ZERO;
            }
            res = res.multiply(item);
        }
        return res.setScale(round, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 除法默认保留2位小数
     *
     * @param param1 除数
     * @param param2 被除数
     * @return
     */
    public static BigDecimal divide(String param1, String param2) {
        return divide(param1, param2, 2);
    }

    /**
     * 除法
     *
     * @param param1 除数
     * @param param2 被除数
     * @param round  保留小数点位数
     * @return
     */
    public static BigDecimal divide(String param1, String param2, int round) {
        BigDecimal bigDecimal1 = BigDecimal.ZERO;
        BigDecimal bigDecimal2 = BigDecimal.ZERO;
        if (!StringUtils.isEmpty(param1)) {
            bigDecimal1 = new BigDecimal(param1);
        }
        if (StringUtils.isEmpty(param2)) {
            return BigDecimal.ZERO;
        } else {
            bigDecimal2 = new BigDecimal(param2);
        }
        if (bigDecimal2.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return bigDecimal1.divide(bigDecimal2, round, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 除法默认保留2位小数
     *
     * @param bigDecimal  除数
     * @param bigDecimals 被除数
     * @return
     */
    public static BigDecimal divide(BigDecimal bigDecimal, BigDecimal... bigDecimals) {
        return divide(bigDecimal, 2, bigDecimals);
    }

    /**
     * 除法
     *
     * @param bigDecimal  除数
     * @param round       保留小数点位数
     * @param bigDecimals 被除数
     * @return
     */
    public static BigDecimal divide(BigDecimal bigDecimal, int round, BigDecimal... bigDecimals) {
        if (bigDecimal == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal res = bigDecimal;
        for (int i = 0; i < bigDecimals.length; i++) {
            BigDecimal item = bigDecimals[i];
            if (item == null) {
                return BigDecimal.ZERO;
            }
            res = res.divide(item, round, BigDecimal.ROUND_HALF_UP);
        }
        return res;
    }

    /**
     * 除法默认保留2位小数
     *
     * @param bigDecimal  除数
     * @param bigDecimals 被除数
     * @return
     */
    public static BigDecimal divide(BigDecimal bigDecimal, List<BigDecimal> bigDecimals) {
        return divide(bigDecimal, bigDecimals, 2);
    }

    /**
     * 除法
     *
     * @param bigDecimal  除数
     * @param bigDecimals 被除数
     * @param round       保留小数点为数
     * @return
     */
    public static BigDecimal divide(BigDecimal bigDecimal, List<BigDecimal> bigDecimals, int round) {
        if (bigDecimal == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal res = bigDecimal;
        for (int i = 0; i < bigDecimals.size(); i++) {
            BigDecimal item = bigDecimals.get(i);
            if (item == null) {
                return BigDecimal.ZERO;
            }
            res = res.divide(item, round, BigDecimal.ROUND_HALF_UP);
        }
        return res;
    }


    /**
     * 将字符串转换成BigDecimal对象
     *
     * @param param
     * @return
     */
    public static BigDecimal stringToBigDecimal(String param) {
        if (StringUtils.isEmpty(param)) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(param).setScale(2, 4);
        } catch (Exception e) {
            throw new BaseException("BigDecimal转换字符串失败！");
        }
    }

    /**
     * 将null转换为0
     *
     * @param bigDecimal
     * @return
     */
    public static BigDecimal bigDecimalNullToZero(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return BigDecimal.ZERO;
        } else {
            return bigDecimal;
        }
    }

    /**
     * 比较大小
     *
     * @param param1 大
     * @param param2 小
     * @return
     */
    public static boolean compare(BigDecimal param1, BigDecimal param2) {
        boolean res = false;
        int result = param1.compareTo(param2);
        if (result == 1) {
            res = true;
        }
        return res;
    }

    /**
     * 判断相等
     *
     * @param param1
     * @param param2
     * @return
     */
    public static boolean compareEquals(BigDecimal param1, BigDecimal param2) {
        boolean res = false;
        if (param1.compareTo(param2) == 0) {
            res = true;
        }
        return res;
    }

    /**
     * 取为 String 类型的结果
     *
     * @param bigDecimal
     * @return
     */
    public static String getStringVal(BigDecimal bigDecimal) {
        return bigDecimal.toString();
    }

    /**
     * 取为 String 类型的结果 可以指定 保留的小数点
     *
     * @param bigDecimal
     * @param scale
     * @return
     */
    public static String getStringVal(BigDecimal bigDecimal, int scale) {
        if (scale == 0) {
            throw new IllegalArgumentException("除数不能为0");
        }
        return bigDecimal.divide(BigDecimal.valueOf(1), scale, BigDecimal.ROUND_HALF_EVEN).toString();
    }

    /**
     * 取为 Double 类型的结果
     *
     * @param bigDecimal
     * @return
     */
    public static Double getDoubleVal(BigDecimal bigDecimal) {
        return bigDecimal.doubleValue();
    }

    /**
     * 取为 Double 类型的结果 可以指定 保留的小数点
     *
     * @param bigDecimal
     * @param scale
     * @return
     */
    public static Double getDoubleVal(BigDecimal bigDecimal, int scale) {
        if (scale == 0) {
            throw new IllegalArgumentException("除数不能为0");
        }
        return bigDecimal.divide(BigDecimal.valueOf(1), scale, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }


    /**
     * 取为 Integer 类型的结果
     *
     * @param bigDecimal
     * @return
     */
    public static Integer getIntegerVal(BigDecimal bigDecimal) {
        return bigDecimal.intValue();
    }

    /**
     * 取为 Float 类型的结果
     *
     * @param bigDecimal
     * @return
     */
    public static Float getFloatValue(BigDecimal bigDecimal) {
        return bigDecimal.floatValue();
    }

    /**
     * 取为 Long 类型的结果
     *
     * @return
     */
    public static Long getLongValue(BigDecimal bigDecimal) {
        return bigDecimal.longValue();
    }


    public static void main(String[] args) {

        List<BigDecimal> param = new ArrayList<>();
        param.add(new BigDecimal("4.4444"));
        param.add(new BigDecimal("3.3333"));
        param.add(new BigDecimal("2.2222"));

        BigDecimal bigDecimal = setBigDecimalScale(new BigDecimal("1.116666"), 2);
        System.out.println(bigDecimal);

        BigDecimal bigDecimal1 = setStringScale("1.227777", 2);
        System.out.println(bigDecimal1);

        BigDecimal add = add("1.11", "2.22");
        System.out.println("add:" + add);

        BigDecimal add1 = add("1.111111", "2.222222", 4);
        System.out.println("add1:" + add1);

        BigDecimal add2 = add(new BigDecimal("1.1"), new BigDecimal("2.22"), new BigDecimal("3.33"));
        System.out.println("add2:" + add2);

        BigDecimal add3 = add(param);
        System.out.println("add3:" + add3);

        BigDecimal add4 = add(param, 3);
        System.out.println("add4" + add4);

        BigDecimal sub = sub("2.222", "1.11");
        System.out.println("sub:" + sub);

        BigDecimal sub1 = sub("2.222", "1.11", 4);
        System.out.println("sub1:" + sub1);

        BigDecimal sub2 = sub(new BigDecimal("2.22"), new BigDecimal("1.11"));
        System.out.println("sub2:" + sub2);

        BigDecimal sub3 = sub(new BigDecimal("4.444"), new BigDecimal("2.22"), new BigDecimal("1.11"));
        System.out.println("sub3:" + sub3);

        BigDecimal sub4 = sub(new BigDecimal("7.7777"), param, 4);
        System.out.println("sub4:" + sub4);

        BigDecimal multiply = multiply("1.23", "2.33");
        System.out.println("multiply:" + multiply);

        BigDecimal multiply1 = multiply("1.23", "2.33", 5);
        System.out.println("multiply1:" + multiply1);

        BigDecimal multiply2 = multiply(new BigDecimal("1.11"), param);
        System.out.println("multiply2:" + multiply2);

        BigDecimal multiply3 = multiply(new BigDecimal("1.11"), new BigDecimal("2.222"), new BigDecimal("3.333"));
        System.out.println("multiply3:" + multiply3);

        BigDecimal divide = divide("4.444", "1.11");
        System.out.println("divide:" + divide);

        BigDecimal divide1 = divide(new BigDecimal("10.111"), param);
        System.out.println("divide1:" + divide1);

        BigDecimal divide2 = divide(new BigDecimal("10.111"), new BigDecimal("1.22"), new BigDecimal("2.222"));
        System.out.println("divide2:" + divide2);

        BigDecimal divide3 = divide(new BigDecimal("10.111"), 5, new BigDecimal("1.22"), new BigDecimal("2.222"));
        System.out.println("divide3:" + divide3);

        BigDecimal bigDecimal2 = stringToBigDecimal("2.111111");
        System.out.println("bigDecimal2:" + bigDecimal2);

        boolean compare = compare(new BigDecimal("2.122"), new BigDecimal("1.111"));
        System.out.println("compare:" + compare);

        boolean b1 = compareEquals(new BigDecimal("1.00"), new BigDecimal("1"));
        System.out.println("b1:" + b1);

        String stringVal = getStringVal(new BigDecimal("1.11"));
        System.out.println("stringVal:" + stringVal);

        String stringVal1 = getStringVal(new BigDecimal("1.11"), 1);
        System.out.println("stringVal1:" + stringVal1);

        Double doubleVal = getDoubleVal(new BigDecimal("1.23123"));
        System.out.println("doubleVal:" + doubleVal);

        Double doubleVal1 = getDoubleVal(new BigDecimal("1.23123"), 2);
        System.out.println("doubleVal1:" + doubleVal1);

        Integer integerVal = getIntegerVal(new BigDecimal("1.1111"));
        System.out.println("integerVal:" + integerVal);

        Float floatValue = getFloatValue(new BigDecimal("2.22222"));
        System.out.println("floatValue:" + floatValue);

        Long longValue = getLongValue(new BigDecimal("3.33333333"));
        System.out.println("longValue:" + longValue);
    }
}
