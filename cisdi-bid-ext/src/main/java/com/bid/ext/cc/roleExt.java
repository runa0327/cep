package com.bid.ext.cc;

import com.bid.ext.model.AdRoleUser;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class roleExt {
    /**
     * 批量新增用户
     */
    public void batchInsertUser() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String pAdUserIds = JdbcMapUtil.getString(varMap, "P_AD_USER_IDS");
        String adRoleId = JdbcMapUtil.getString(varMap, "P_AD_ROLE_ID");
        if (pAdUserIds != null && !pAdUserIds.isEmpty()) {
            List<String> userIdList = Arrays.asList(pAdUserIds.split(","));
            for (String userId : userIdList) {
                //查询用户是否已经是该角色
                AdRoleUser adRoleUser0 = AdRoleUser.selectById(userId);
                if (SharedUtil.isEmpty(adRoleUser0)) {
                    //插入用户
                    AdRoleUser adRoleUser = AdRoleUser.newData();
                    adRoleUser.setAdUserId(userId);
                    adRoleUser.setAdRoleId(adRoleId);
                    adRoleUser.setStatus("AP");
                }
            }
        }

    }
}
