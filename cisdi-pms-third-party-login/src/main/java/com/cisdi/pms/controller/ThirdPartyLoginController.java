package com.cisdi.pms.controller;

import com.cisdi.pms.service.UnifiedLoginService;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.ThirdPartyUserInfo;
import com.qygly.shared.interaction.external.ThirdPartyLoginCodeValidationReqBody;
import com.qygly.shared.interaction.external.ThirdPartyLoginCodeValidationRespBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
public class ThirdPartyLoginController {
    @Autowired
    private UnifiedLoginService loginService;

    /**
     * 统一认证登录
     *
     * @param requestBody
     * @return
     */
    @PostMapping("validateCode")
    public ThirdPartyLoginCodeValidationRespBody validate(@RequestBody ThirdPartyLoginCodeValidationReqBody requestBody) {
        if (requestBody == null) {
            throw new BaseException("参数为空！");
        }
        String loginCode = requestBody.loginCode;
        String loginThirdParty = requestBody.loginThirdParty;
        String loginExtraInfo = requestBody.loginExtraInfo;
        String loginType = requestBody.loginType;
        ThirdPartyLoginCodeValidationRespBody responseBody = new ThirdPartyLoginCodeValidationRespBody();
        responseBody.succ = false;
        if ("YZW".equals(loginThirdParty)) {
            ThirdPartyUserInfo thirdPartyUserInfo = new ThirdPartyUserInfo();
            if ("PC".equals(loginType)) {
                thirdPartyUserInfo = loginService.UnifiedLogin(loginCode);
            } else if ("APP".equals(loginType)) {
                thirdPartyUserInfo = loginService.UnifiedAppLogin(loginCode, loginExtraInfo);
            }
            if (thirdPartyUserInfo != null) {
                responseBody.succ = true;
                responseBody.data = thirdPartyUserInfo;
            }
        }
        return responseBody;
    }

    @GetMapping("test")
    public String test(){
        return "测试！";
    }
}
