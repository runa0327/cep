package com.cisdi.ext.wf;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;

import java.util.ArrayList;
import java.util.List;

public class WfPrjReqExt {
    public void parsePrjEarlyUserIdByPrjReq() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            parseUserIdByPrjReq(entityRecord, "PRJ_EARLY_USER_ID", "前期岗");
        }
    }

    public void parsePrjDesignUserIdByPrjReq() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            parseUserIdByPrjReq(entityRecord, "PRJ_DESIGN_USER_ID", "设计岗");
        }
    }

    public void parsePrjCostUserIdByPrjReq() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            parseUserIdByPrjReq(entityRecord, "PRJ_COST_USER_ID", "成本岗");
        }
    }

    private void parseUserIdByPrjReq(EntityRecord entityRecord, String colName, String dspName) {
        String csCommId = entityRecord.csCommId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        String user_id = null;
        try {
            user_id = myJdbcTemplate.queryForMap("select t." + colName + " USER_ID from pm_prj_req t where t.id=?", csCommId).get("USER_ID").toString();
        } catch (Exception ex) {
            throw new BaseException("项目没有设置对应的" + dspName + "用户！");
        }

        List<String> userIdList = new ArrayList<>(1);
        userIdList.add(user_id);
        ExtJarHelper.returnValue.set(userIdList);
    }
}
