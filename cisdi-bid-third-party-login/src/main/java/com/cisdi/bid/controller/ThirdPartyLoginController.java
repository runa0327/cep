package com.cisdi.bid.controller;


import com.alibaba.fastjson.JSON;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.ThirdPartyUserInfo;
import com.qygly.shared.interaction.external.ThirdPartyLoginCodeValidationReqBody;
import com.qygly.shared.interaction.external.ThirdPartyLoginCodeValidationRespBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

        String clientId = "20e25b82-bbe4-459f-af55-3275cfbed695";
        

        ThirdPartyLoginCodeValidationRespBody responseBody = new ThirdPartyLoginCodeValidationRespBody();

        responseBody.succ = true;
        responseBody.data = new ThirdPartyUserInfo();

        String loginCode = requestBody.loginCode;

        log.info("响应：" + JSON.toJSONString(responseBody));

        return responseBody;
    }
}
