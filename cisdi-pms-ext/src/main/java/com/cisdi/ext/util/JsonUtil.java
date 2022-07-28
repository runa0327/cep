package com.cisdi.ext.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qygly.shared.BaseException;

import java.util.Map;

/**
 * Json工具。
 * 尽管线程安全，但每次new性能更佳。参考：https://blog.csdn.net/baichoufei90/article/details/102913336
 */
public class JsonUtil {

    public static String toJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String valueAsString = objectMapper.writeValueAsString(object);
            return valueAsString;
        } catch (JsonProcessingException e) {
            throw new BaseException(e);
        }
    }

    public static <T> T fromJson(String content, Class<T> valueType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
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

}
