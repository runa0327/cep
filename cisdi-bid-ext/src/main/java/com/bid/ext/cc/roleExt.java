package com.bid.ext.cc;

import com.bid.ext.model.AdRoleUser;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.interaction.DrivenInfo;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class roleExt {
    /**
     * 批量新增用户
     */
    public void batchInsertUser() {
        InvokeActResult invokeActResult = new InvokeActResult();
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String pAdUserIds = JdbcMapUtil.getString(varMap, "P_AD_USER_IDS");

        Map<String, List<DrivenInfo>> drivenInfosMap = ExtJarHelper.getDrivenInfosMap();
        List<DrivenInfo> drivenInfos = drivenInfosMap.get("1790346299274113024");
        String adRoleId = null;

        Optional<String> value = drivenInfos.stream()
                .filter(info -> "AD_ROLE_ID".equals(info.code))
                .map(info -> info.value)
                .findFirst();
        if (value.isPresent()) {
            adRoleId = value.get();
        }

        if (pAdUserIds != null && !pAdUserIds.isEmpty()) {
            List<String> userIdList = Arrays.asList(pAdUserIds.split(","));
            for (String userId : userIdList) {
                //查询用户是否已经是该角色
                List<AdRoleUser> adRoleUsers = AdRoleUser.selectByWhere(new Where().eq(AdRoleUser.Cols.AD_USER_ID, userId).eq(AdRoleUser.Cols.AD_ROLE_ID, adRoleId));
                if (SharedUtil.isEmpty(adRoleUsers)) {
                    //插入用户
                    AdRoleUser adRoleUser = AdRoleUser.newData();
                    adRoleUser.setAdUserId(userId);
                    adRoleUser.setAdRoleId(adRoleId);
                    adRoleUser.setStatus("AP");
                    adRoleUser.insertById();
                }
            }
        }
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }
}
