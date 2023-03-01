package com.cisdi.ext.util;
import java.lang.reflect.Field;
import java.util.*;

public class CommonUtils {

    /**
     * 实体转对象
     * @param object 实体
     * @return map
     */
    public static Map<String, Object> convertToMap(Object object) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {

            field.setAccessible(true);
            String value = field.get(object) != null ? field.get(object).toString() : "";
            map.put(field.getName(), value);
        }
        return map;
    }
}
