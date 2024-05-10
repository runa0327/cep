package com.cisdi.anhui.sso.controller;


import com.qygly.shared.ad.login.ThirdPartyUserInfo;
import com.qygly.shared.interaction.external.ThirdPartyLoginCodeValidationReqBody;
import com.qygly.shared.interaction.external.ThirdPartyLoginCodeValidationRespBody;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;


@RestController
@Slf4j
public class ThirdPartyLoginController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${ah.clientId}")
    private String clientId;

    @Value("${ah.secret}")
    private String secret;

    @Value("${ah.loginUserUrl}")
    private String loginUserUrl;

    /**
     * 统一认证登录
     *
     * @param requestBody
     * @return
     */
    @PostMapping("validate")
    public ThirdPartyLoginCodeValidationRespBody validate(@RequestBody ThirdPartyLoginCodeValidationReqBody requestBody) throws URISyntaxException {

        String loginCode = requestBody.loginCode;

        String url = loginUserUrl + "?token=" + loginCode + "&clientId=" + clientId + "&secret=" + secret;

        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(new URI(url), Map.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Map map = responseEntity.getBody();
            if (SharedUtil.notEmpty(map)) {
                Object logonName = map.get("logon_name");
                if (SharedUtil.notEmpty(logonName)) {
                    String logonNameString = logonName.toString();

                    ThirdPartyLoginCodeValidationRespBody succRespBody = new ThirdPartyLoginCodeValidationRespBody();
                    succRespBody.succ = true;
                    succRespBody.data = new ThirdPartyUserInfo();
                    succRespBody.data.identityCode = logonNameString;
                    succRespBody.data.code = logonNameString;
                    succRespBody.data.name = logonNameString;

                    return succRespBody;
                }
            }
        }

        ThirdPartyLoginCodeValidationRespBody failRespBody = new ThirdPartyLoginCodeValidationRespBody();
        failRespBody.succ = false;
        failRespBody.errMsg = "解析失败（token：" + loginCode + "）！";
        return failRespBody;
    }
}
