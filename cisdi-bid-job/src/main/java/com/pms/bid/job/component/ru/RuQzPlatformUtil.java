package com.pms.bid.job.component.ru;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.qygly.shared.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RuQzPlatformUtil {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String ,Object> redisTemplate;


    /**
     * 获取轻筑平台token
     * @return
     */
    public  String getQzToken(){

        String token = null;

//        Object qz_auth_token = redisTemplate.opsForValue().get("QZ_AUTH_TOKEN");

        Object qz_auth_token =null;

        if(qz_auth_token==null) {
            String requestUrl = "https://open.qingzhuyun.com/api/platform/token?appId=a0658b6f47f443729e28eafcbfccfad6&secret=958796e25fea43b2aa0574562ec0a3c1";

            Map<String, Object> requestMap = new HashMap<>();//请求参数，只包含
//            requestMap.put("appId", "a0658b6f47f443729e28eafcbfccfad6");//轻筑项目ID
//            requestMap.put("secret", "958796e25fea43b2aa0574562ec0a3c1");//巡检类型1质量，2安全

            HttpHeaders translateHeaders = new HttpHeaders();

            // 设置请求体
            HttpEntity<Map> translateEntity = new HttpEntity<>(requestMap, translateHeaders);

            ResponseEntity<String> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, translateEntity, String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {

                JSONObject entries = JSONUtil.parseObj(responseEntity.getBody());

                JSONObject data = entries.getJSONObject("data");
                token = data.getStr("authToken");

            } else {
                throw new BaseException("token获取失败" + responseEntity);
            }

//            redisTemplate.opsForValue().set("QZ_AUTH_TOKEN",token,7200, TimeUnit.SECONDS);

        }else{
            token = qz_auth_token.toString();
        }

        return token;
    }

}
