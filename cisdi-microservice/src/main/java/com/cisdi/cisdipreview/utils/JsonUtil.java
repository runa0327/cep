package com.cisdi.cisdipreview.utils;

import com.cisdi.cisdipreview.domain.common.Internationalization;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.qygly.shared.BaseException;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Json工具。
 * 尽管线程安全，但每次new性能更佳。参考：https://blog.csdn.net/baichoufei90/article/details/102913336
 * https://blog.csdn.net/qq_42017395/article/details/107555339
 */
public class JsonUtil {

    public static String toJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            String valueAsString = objectMapper.writeValueAsString(object);
            return valueAsString;
        } catch (JsonProcessingException e) {
            throw new BaseException(e);
        }
    }

    public static <T> T fromJson(String content, Class<T> valueType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            //反序列化时,遇到未知属性会不会报错
            //true - 遇到没有的属性就报错 false - 没有的属性不会管，不会报错
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            T t = objectMapper.readValue(content, valueType);
            return t;
        } catch (JsonProcessingException e) {
            throw new BaseException(e);
        }
    }

    public static <T> T fromJson(String content, TypeReference<T> valueType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            T t = objectMapper.readValue(content, new TypeReference<T>() {
            });
            return t;
        } catch (JsonProcessingException e) {
            throw new BaseException(e);
        }
    }

    /**
     * 将Map转换为对象。便于接收ExtApi的参数。
     *
     * @param map
     * @param objectType
     * @param <T>
     * @return
     */
    public static <T> T convertMapToObject(Map<String, Object> map, Class<T> objectType) {
        return fromJson(toJson(map), objectType);
    }

    /**
     * 将对象转换为Map。便于设置ExtApi的返回值。
     *
     * @param object
     * @return
     */
    public static Map<String, Object> convertObjectToMap(Object object) {
        return fromJson(toJson(object), Map.class);
    }

    /**
     * 获取json中简体中文内容
     *
     * @param str 数据源字符串
     * @return 简体中文信息
     */
    public static String getCN(String str) {
        if (StringUtils.hasText(str)) {
            try {
                Internationalization internationalization = JsonUtil.fromJson(str, Internationalization.class);
                return internationalization.getCh_cn();
            } catch (Exception e) {
                return str;
            }
        } else {
            return null;
        }
    }
}
