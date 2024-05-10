package com.cisdi.bid.controller;


import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.ThirdPartyUserInfo;
import com.qygly.shared.interaction.external.ThirdPartyLoginCodeValidationReqBody;
import com.qygly.shared.interaction.external.ThirdPartyLoginCodeValidationRespBody;
import com.qygly.shared.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ThirdPartyLoginController {
    /**
     * 统一认证登录
     *
     * @param requestBody
     * @return
     */
    @PostMapping("validateCode")
    public ThirdPartyLoginCodeValidationRespBody validate(@RequestBody ThirdPartyLoginCodeValidationReqBody requestBody) {
        log.info("请求：" + JsonUtil.toJson(requestBody));
        if (requestBody == null) {
            throw new BaseException("参数为空！");
        }

        String clientId = "20e25b82-bbe4-459f-af55-3275cfbed695";


        ThirdPartyLoginCodeValidationRespBody responseBody = new ThirdPartyLoginCodeValidationRespBody();

        responseBody.succ = true;
        responseBody.data = new ThirdPartyUserInfo();

        String loginCode = requestBody.loginCode;

        log.info("响应：" + JsonUtil.toJson(responseBody));

        return responseBody;
    }
}
