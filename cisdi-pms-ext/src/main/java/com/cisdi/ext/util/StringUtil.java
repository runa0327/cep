package com.cisdi.ext.util;

import com.google.common.base.Strings;
import com.qygly.shared.util.SharedUtil;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     *
     * @param b
     * @return 获取数字百分数
     */
    public static String getPercentStr(BigDecimal b){
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(2);
        return percent.format(b);
    }
    /**
     * 转字符串
     * @param t
     * @return
     */
    public static <T>  String toString(T t){
        if (Objects.isNull(t)){
            return null;
        }
        return String.valueOf(t);
    }

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

    /**
     * 中文标点符号转英文符号
     * @param str 原始字符串
     * @param code 需要转换成的标点
     * @return
     */
    public static String chineseCodeToEnCode(String str, String code) {
        if (",".equals(code)){
            int index = str.indexOf("，");
            if (index != -1){
                str = str.replace("，",",");
            }
        }
        return str;
    }

    public static int getNum(String data){
        if (Strings.isNullOrEmpty(data)){
            return 0;
        }
        //正则表达式以匹配字符串中的数字
        String regex = "\\d+";
        //创建一个模式对象
        Pattern pattern = Pattern.compile(regex);
        //创建一个Matcher对象
        Matcher matcher = pattern.matcher(data);
        while(matcher.find()) {
            return Integer.valueOf(matcher.group());
        }
        return 0;
    }

    public static BigDecimal getBigDecimal(String value){
        if (Strings.isNullOrEmpty(value)){
            return null;
        }
        return new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     *
     * @param str 原字符串
     * @param code1 旧字符
     * @param code2 新字符
     * @return
     */
    public static String replaceCode(String str, String code1, String code2) {
        int index = str.indexOf(code1);
        if (index != -1){
            str = str.replace(code1,code2);
        }
        return str;
    }

    /**
     * 字符串格式化长度不足补0
     * @param str
     * @param strLength
     * @return
     */
    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);// 左补0
                // sb.append(str).append("0");//右补0
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    /**
     * 将字符串根据某个字符转为list
     * @param str 原字符串
     * @param code 拆分依据字段
     * @return list
     */
    public static List<String> getStrToList(String str, String code) {
        List<String> list = new ArrayList<>();
        if (!str.contains(code)){
            list.add(str);
        } else {
            list = new ArrayList<>(Arrays.asList(str.split(code)));
        }
        return list;
    }

    /**
     * 截取字符串中某个字符第一次出现前的内容
     * @param str 原字符串
     * @param code 判断字符
     * @return 字符串
     */
    public static String getFirstStr(String str, String code) {
        if (!str.contains(code)){
            return str;
        } else {
            int index = str.indexOf(code);
            str = str.substring(0,index);
            return str;
        }
    }

    /**
     * 计算字符串表达式
     *
     * @param elString
     * @return
     */
    public static Boolean doExpression(String elString) {
        SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
        return spelExpressionParser.parseExpression(elString).getValue(Boolean.class);
    }

    public static void main(String[] args) {
        System.out.println(StringUtil.doExpression("100<20"));
    }

    /**
     * 字符串控制判断 结果转 BigDecimal 类型
     * @param oldStr 原字符串
     * @return BigDecimal值
     */
    public static BigDecimal valueNullToBig(String oldStr) {
        BigDecimal decimal = new BigDecimal(0);
        if (!SharedUtil.isEmptyString(oldStr)){
            decimal = new BigDecimal(oldStr);
        }
        return decimal;
    }

    /**
     * 字符串数字控制判断
     * @param oldStr 原字符串
     * @return BigDecimal值
     */
    public static String valueNullToStrNumber(String oldStr) {
        if (SharedUtil.isEmptyString(oldStr)){
            oldStr = "0";
        }
        return oldStr;
    }

    /**
     * 多字符串拼接
     * @param code 字符串间连接符号
     * @param values 字符串信息
     * @return 拼接结果
     */
    public static String concatStr(String code,String...values) {
        StringJoiner sb = new StringJoiner(code);
        Arrays.stream(values).filter(p->!SharedUtil.isEmptyString(p)).forEach(p->sb.add(p));
        return sb.toString();
    }
}
