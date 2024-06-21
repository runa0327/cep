package com.pms.bid.job.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pms.bid.job.domain.json.Internationalization;
import com.qygly.shared.BaseException;
import org.springframework.util.StringUtils;

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
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            T t = objectMapper.readValue(content, valueType);
            return t;
        } catch (JsonProcessingException e) {
            throw new BaseException(e);
        }
    }

    /**
     * 获取json中简体中文内容
     * @param str 数据源字符串
     * @return 简体中文信息
     */
    public static String getCN(String str) {
        if (StringUtils.hasText(str)){
            try {
                Internationalization internationalization = JsonUtil.fromJson(str,Internationalization.class);
                return internationalization.getCh_cn();
            } catch (Exception e){
                return str;
            }
        } else {
            return null;
        }
    }
}
