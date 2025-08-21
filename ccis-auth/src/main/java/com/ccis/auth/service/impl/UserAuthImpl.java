package com.ccis.auth.service.impl;

import cn.hutool.http.HttpRequest;
import com.ccis.auth.domain.UserLoginInfo;
import com.ccis.auth.domain.UserLoginResp;
import com.ccis.auth.service.UserAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserAuthImpl implements UserAuth {

    @Override
    public Map<String, Object> userLogin(UserLoginInfo userLoginInfo) {
        Map<String, Object> result = new HashMap<>();

        String url  =  "http://qtwins.cisdi.com.cn/prod-api/auth/loginUniAddress";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username",userLoginInfo.getUsername());
        requestBody.put("password",userLoginInfo.getPassword());

        HttpEntity<Map<String, Object>> httpEntityRequest = new HttpEntity<>(requestBody, headers);

        ResponseEntity<UserLoginResp> response = restTemplate.exchange(url, HttpMethod.POST, httpEntityRequest, UserLoginResp.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && response.getBody().getCode() == 200) {

            UserLoginResp body = response.getBody();

            log.info("data:"+body.getData()+",errMsg:"+body.getMsg());

            if(!body.getData()){
                result.put("succ",false);
                result.put("errMsg","用户名或密码错误");
            }else{
                result.put("succ",true);
            }

        } else {
            result.put("succ",false);
            result.put("errMsg","请求登录接口失败");
        }

        return result;
    }
}
