package com.bid.ext.cc;

import com.bid.ext.model.CcPrj;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.ad.login.PrincipalTypeE;
import com.qygly.shared.interaction.LoginExtResult;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LoginExt {
    /**
     * 登陆后为当前用户设置全局变量（即：初始化）。
     */
    public void setGlobalVars() {

//        @FCODES LIKE '%ql:prj:select_all%' OR EXISTS(SELECT 1 FROM CC_PRJ_MEMBER M WHERE M.CC_PRJ_ID=T.ID AND M.AD_USER_ID=@UID)

        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
//        Map<String, Object> globalVarMap = loginInfo.globalVarMap;

        // 安徽判断是否有选择所有项目的功能
        boolean anyMatch1 = loginInfo.currentPrincipalInfoList.stream().anyMatch(
                principalInfo -> principalInfo.type == PrincipalTypeE.AD_FUNC && "ql:prj:select_all".equals(principalInfo.code)
        );

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String cc_prj_ids = null;

        // 若满足其一则设置变量为查看所有项目
        if (anyMatch1) {
            String sql = "SELECT ID FROM CC_PRJ";
            List<Map<String, Object>> ids = myJdbcTemplate.queryForList(sql);
            if (ids != null) {
                List<String> lst = new ArrayList<>();
                for (Map<String, Object> id : ids) {
                    String string = JdbcMapUtil.getString(id, "ID");
                    lst.add(string);
                }
                cc_prj_ids = SharedUtil.strListToSplittedStr(lst);
            }

        } else {
            String UID = loginInfo.userInfo.id;
            String sql = "SELECT T.ID ID FROM CC_PRJ_MEMBER M, CC_PRJ T WHERE M.CC_PRJ_ID=T.ID AND M.AD_USER_ID= ?";
            List<Map<String, Object>> ids = myJdbcTemplate.queryForList(sql, UID);
            if (ids != null) {
                List<String> lst = new ArrayList<>();
                for (Map<String, Object> id : ids) {
                    String string = JdbcMapUtil.getString(id, "ID");
                    lst.add(string);
                }
                cc_prj_ids = SharedUtil.strListToSplittedStr(lst);
            }
        }

        LoginExtResult result = new LoginExtResult();
        result.changedGlobalVarMap = new LinkedHashMap<>();
        result.changedGlobalVarMap.put("P_CC_PRJ_IDS", cc_prj_ids);
        ExtJarHelper.setReturnValue(result);

    }
}
