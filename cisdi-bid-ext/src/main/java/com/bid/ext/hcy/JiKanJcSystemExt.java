package com.bid.ext.hcy;

import com.bid.ext.model.HcJkjcSystemUserConfig;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.DateTimeUtil;
import com.qygly.shared.util.JsonUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class JiKanJcSystemExt {


    public void login(){

        StringRedisTemplate stringRedisTemplate = ExtJarHelper.getStringRedisTemplate();

        String value = stringRedisTemplate.opsForValue().get("JKJC_TOKEN_STR");

        Where where = new Where();
        List<HcJkjcSystemUserConfig> hcJkjcSystemUserConfigs = HcJkjcSystemUserConfig.selectByWhere(where);


        if (SharedUtil.isEmpty(value)) {

            RestTemplate restTemplate = ExtJarHelper.getRestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type","application/json");

            Map<String, Object> body = new HashMap<>();

                body.put("username", hcJkjcSystemUserConfigs.get(0).getName());
                body.put("password", hcJkjcSystemUserConfigs.get(0).getRemark());

            HttpEntity<String> entity = new HttpEntity<>(JsonUtil.toJson(body),headers);

            ResponseEntity<String> response = restTemplate.exchange("https://cloud.sipenget.com/server/api/system/login", HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.hcy.getJKJCTokenFail");
                log.error(message + response);
                throw new BaseException(message);
            }

            String res = response.getBody();

            Map map = JsonUtil.fromJson(res, Map.class);
            Integer code = (Integer) map.get("code");
            if(code!= 200){
                throw new BaseException((String) map.get("message"));
            }

            Map data = (Map) map.get("data");
            String token = data.get("token").toString();

            Duration between = Duration.between(LocalDateTime.now(), LocalDateTime.now().plusHours(23));

            stringRedisTemplate.opsForValue().set("JKJC_TOKEN_STR", token, between);

            value = token;
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", value);//

        ExtJarHelper.setReturnValue(resultMap);

    }


}
