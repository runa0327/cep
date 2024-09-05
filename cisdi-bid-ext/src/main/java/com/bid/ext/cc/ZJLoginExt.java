package com.bid.ext.cc;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.ad.login.PrincipalTypeE;
import com.qygly.shared.interaction.LoginExtResult;

import java.util.LinkedHashMap;

public class ZJLoginExt {
    /**
     * 登陆后为当前用户设置全局变量（即：初始化全局变量）。
     * 用于湛江。
     */
    public void setGlobalVars() {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();

        // 对于有代码为zj:default_prj_to_zj的功能的用户，则默认项目ID为湛江项目ID（1790672761571196928）：
        boolean anyMatch = loginInfo.currentPrincipalInfoList.stream().anyMatch(principalInfo -> principalInfo.type == PrincipalTypeE.AD_FUNC && "zj:default_prj_to_zj".equals(principalInfo.code));
        if (anyMatch) {
            LoginExtResult result = new LoginExtResult();
            result.changedGlobalVarMap = new LinkedHashMap<>();
            result.changedGlobalVarMap.put("P_CC_PRJ_IDS", "1790672761571196928");
            ExtJarHelper.setReturnValue(result);
        }
    }
}
