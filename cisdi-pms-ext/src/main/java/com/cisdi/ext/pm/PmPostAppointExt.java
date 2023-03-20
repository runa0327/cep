package com.cisdi.ext.pm;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.interaction.EntityRecord;

/**
 * 岗位指派流程-扩展
 */
public class PmPostAppointExt {

    /**
     * 部门负责人审批-扩展
     */
    public void chargeLeaderCheck(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String userId = ExtJarHelper.loginInfo.get().userId;
    }
}
