package com.cisdi.bid.Controller;


import com.alibaba.fastjson.JSON;
import com.cisdi.bid.model.LoginExtraInfo;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.ThirdPartyUserInfo;
import com.qygly.shared.interaction.external.ThirdPartyLoginCodeValidationReqBody;
import com.qygly.shared.interaction.external.ThirdPartyLoginCodeValidationRespBody;
import com.qygly.shared.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
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

        ThirdPartyLoginCodeValidationRespBody responseBody = new ThirdPartyLoginCodeValidationRespBody();

        responseBody.succ = true;
        responseBody.data = new ThirdPartyUserInfo();


        responseBody.succ = false;

        String json = requestBody.loginExtraInfo;
        if (StringUtils.hasText(json)) {

            LoginExtraInfo loginExtraInfo = JsonUtil.fromJson(json, LoginExtraInfo.class);
            String loginCode = requestBody.loginCode;

            String loginThirdParty = loginExtraInfo.getLoginThirdParty();
            String loginExtra = loginExtraInfo.getLoginExtraInfo();
            String loginType = loginExtraInfo.getLoginType();

            ThirdPartyUserInfo thirdPartyUserInfo = new ThirdPartyUserInfo();

            if (thirdPartyUserInfo != null) {
                responseBody.succ = true;
                responseBody.data = thirdPartyUserInfo;
            }
            log.info("响应：" + JSON.toJSONString(responseBody));
        }

        return responseBody;
    }
}
