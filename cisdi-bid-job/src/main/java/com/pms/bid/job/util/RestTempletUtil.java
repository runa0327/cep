package com.pms.bid.job.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class RestTempletUtil {


    public static  <T>T getHttpDate(String url, HttpMethod httpMethod, HttpEntity httpEntity, Class<T> contentType){



      return   JsonUtil.fromJson("",contentType);
    }


}
