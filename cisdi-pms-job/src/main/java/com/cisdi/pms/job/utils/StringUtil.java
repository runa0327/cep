package com.cisdi.pms.job.utils;

import com.google.common.base.Strings;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtil {

    /**
     * 将字符串根据某个字符转为list
     * @param str 原字符串
     * @param code 拆分依据字段
     * @return list
     */
    public static List<String> splitByCode(String str, String code) {
        List<String> list = new ArrayList<>();
        if (!str.contains(code)){
            list.add(str);
        } else {
            list = new ArrayList<>(Arrays.asList(str.split(code)));
        }
        return list;
    }

    /**
     * 字符串替换
     * @param str
     * @param s
     * @return s
     */
    public static String replaceCode(String str, String s) {
        str = str.trim().replace(" ","");
        if (",".equals(s)) {
            str = str.replace("，",",");
        }
        return str;
    }

    /**
     * 将cookies字符串转map
     */
    public static Map<String,String> cookieToMap(String value) {
        Map<String, String> map = new HashMap<String, String>();
        value = value.replace(" ", "");
        if (value.contains(";")) {
            String values[] = value.split(";");
            for (String val : values) {
                String vals[] = val.split("=");
                map.put(vals[0], vals[1]);
            }
        } else {
            String values[] = value.split("=");
            map.put(values[0], values[1]);
        }
        return map;
    }

    /**
     * 将将带T的时间字符串截取为日期
     */
    public static String getDateWithOutT(String value) {
        if (Strings.isNullOrEmpty(value)){
            return null;
        }
        String[] strings = value.split("T");
        return strings[0];
    }

    /**
     * 将字节数据转为带单位的字符串
     * @param fileSize 文件字节大小
     * @return 带单位的字符串
     */
    public static String formatFileSize(Long fileSize) {
        Long K_SIZE = 1024L;
        Long M_SIZE = K_SIZE * K_SIZE;
        Long G_SIZE = M_SIZE * K_SIZE;
        String fileSizeStr = "";
        if (Objects.isNull(fileSize)) {
            return fileSizeStr;
        }
        if (fileSize == 0L) {
            return "0".concat("B");
        }
        // 小数位数自定义
        DecimalFormat df = new DecimalFormat("#");
        if (fileSize < K_SIZE) {
            fileSizeStr = df.format((double) fileSize) + "B";
        } else if (fileSize < M_SIZE) {
            fileSizeStr = df.format((double) fileSize / K_SIZE) + "K";
        } else if (fileSize < G_SIZE) {
            fileSizeStr = df.format((double) fileSize / M_SIZE) + "M";
        } else {
            fileSizeStr = df.format((double) fileSize / G_SIZE) + "G";
        }
        return fileSizeStr;
    }


    public static boolean isDouble(String s) {
        if (null == s){
            return false;
        }
        Pattern pattern = Pattern.compile("[+-]?\\d+(.\\d+)?");
        return pattern.matcher(s).matches();
    }


    /**
     * 判断是否为手机号
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
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
     * 多字段自动拼接
     * @param start 连接符号
     * @param values 参数组
     * @return 拼接结果
     */
    public static String concat(String start, String ... values) {
        StringJoiner sb = new StringJoiner(start);
        Arrays.stream(values).filter(p->StringUtils.hasText(p)).forEach(p->sb.add(p));
        return sb.toString();
    }

}
