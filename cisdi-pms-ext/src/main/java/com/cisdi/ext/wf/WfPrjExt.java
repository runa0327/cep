package com.cisdi.ext.wf;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class WfPrjExt {
    public void parsePrjEarlyUserIdByPrj() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            parseUserIdByPrj(entityRecord, "PROJECT_EARLY_USER_ID", "前期岗");
        }
    }

    public void parsePrjDesignUserIdByPrj() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            parseUserIdByPrj(entityRecord, "PROJECT_DESIGN_USER_ID", "设计岗");
        }
    }

    public void parsePrjCostUserIdByPrj() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            parseUserIdByPrj(entityRecord, "PROJECT_COST_USER_ID", "成本岗");
        }
    }

    private void parseUserIdByPrj(EntityRecord entityRecord, String colName, String dspName) {
        String csCommId = entityRecord.csCommId;
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;

        Object pm_prj_id = jdbcTemplate.queryForMap("select t.pm_prj_id from " + entCode + " t where t.id=?", csCommId).get("pm_prj_id");
        String user_id = null;
        try {
            user_id = jdbcTemplate.queryForMap("select t." + colName + " USER_ID from pm_prj t where t.id=?", pm_prj_id).get("USER_ID").toString();
        } catch (Exception ex) {
            throw new BaseException("项目没有设置对应的" + dspName + "用户！");
        }
        List<String> userIdList = new ArrayList<>(1);
        userIdList.add(user_id);
        ExtJarHelper.returnValue.set(userIdList);
    }
}
