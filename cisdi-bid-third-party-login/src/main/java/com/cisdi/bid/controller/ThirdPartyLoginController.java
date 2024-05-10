package com.cisdi.bid.controller;


import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.ThirdPartyUserInfo;
import com.qygly.shared.interaction.external.ThirdPartyLoginCodeValidationReqBody;
import com.qygly.shared.interaction.external.ThirdPartyLoginCodeValidationRespBody;
import com.qygly.shared.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title UnifiedLoginController
 * @package com.cisdi.pms.controller
 * @description
 * @date 2022/12/16
 */
@RestController
@Slf4j
public class ThirdPartyLoginController {

    @Resource
    private RestTemplate restTemplate;

    @Value("${ah.clientId}")
    private String clientId;

    @Value("${ah.secret}")
    private String secret;

    @Value("${ah.getLoginUserUrl}")
    private String getLoginUserUrl;

    /**
     * 统一认证登录
     *
     * @param requestBody
     * @return
     */
    @PostMapping("validateCode")
    public ThirdPartyLoginCodeValidationRespBody validate(@RequestBody ThirdPartyLoginCodeValidationReqBody requestBody) {
        log.info("请求：" + JSON.toJSONString(requestBody));
        if (requestBody == null) {
            throw new BaseException("参数为空！");
        }

        ThirdPartyLoginCodeValidationRespBody responseBody = new ThirdPartyLoginCodeValidationRespBody();

        responseBody.succ = true;
        responseBody.data = new ThirdPartyUserInfo();


        String loginCode = requestBody.loginCode;

        if (getLoginUserUrl.indexOf('?') >= 0) {
            getLoginUserUrl += "&token=" + loginCode + "& clientId=" + clientId + "& secret =" + secret;
        } else {
            getLoginUserUrl += "?token=" + loginCode + "& clientId=" + clientId + "& secret =" + secret;
        }


        log.info("响应：" + JSON.toJSONString(responseBody));

        return responseBody;
    }
}
