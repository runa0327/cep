package com.cisdi.ext.wf;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WfPrjExt {
    public void parsePrjEarlyUserIdByPrj() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            parseUserIdByPrj(entityRecord, "PRJ_EARLY_USER_ID", "前期岗");
        }
    }

    public void parsePrjDesignUserIdByPrj() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            parseUserIdByPrj(entityRecord, "PRJ_DESIGN_USER_ID", "设计岗");
        }
    }

    public void parsePrjCostUserIdByPrj() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            parseUserIdByPrj(entityRecord, "PRJ_COST_USER_ID", "成本岗");
        }
    }

    private void parseUserIdByPrj(EntityRecord entityRecord, String colName, String dspName) {
        String csCommId = entityRecord.csCommId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;

        Object pm_prj_id = myJdbcTemplate.queryForMap("select t.pm_prj_id from " + entCode + " t where t.id=?", csCommId).get("pm_prj_id");
        String user_id = null;
        try {
            user_id = myJdbcTemplate.queryForMap("select t." + colName + " USER_ID from pm_prj t where t.id=?", pm_prj_id).get("USER_ID").toString();
        } catch (Exception ex) {
            throw new BaseException("项目没有设置对应的" + dspName + "用户！");
        }
        List<String> userIdList = new ArrayList<>(1);
        userIdList.add(user_id);
        ExtJarHelper.returnValue.set(userIdList);
    }

    public void parseAgentUserIdByBid() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            parseUserIdByBid(entityRecord);
        }
    }

    private void parseUserIdByBid(EntityRecord entityRecord) {
        String csCommId = entityRecord.csCommId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String user_id = myJdbcTemplate.queryForMap("select BID_USER_ID from po_public_bid_req where id=?", csCommId).get(
                "BID_USER_ID").toString();
        ArrayList<Object> userIdList = new ArrayList<>(1);
        userIdList.add(user_id);
        ExtJarHelper.returnValue.set(userIdList);

    }

    //招标文件经办人
    public void pmBidApprovalReqByBid() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;
            MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
            String user_id = myJdbcTemplate.queryForMap("select AD_USER_ID from PM_BID_APPROVAL_REQ where id=?", csCommId).get(
                    "AD_USER_ID").toString();
            ArrayList<Object> userIdList = new ArrayList<>(1);
            userIdList.add(user_id);
            ExtJarHelper.returnValue.set(userIdList);
        }
    }

    //招标文件经办人所在部门负责人
    public void getBidFileDeptUser() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;
            MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

            String procInstId = ExtJarHelper.procInstId.get();
            String START_USER_ID = myJdbcTemplate.queryForMap("select AD_USER_ID from PM_BID_APPROVAL_REQ where id=?", csCommId).get(
                    "AD_USER_ID").toString();

            List<Map<String, Object>> list = myJdbcTemplate.queryForList("select d.chief_user_id from hr_dept_user du join " +
                    "hr_dept d on du.HR_DEPT_ID=d.id and du.AD_USER_ID=?", START_USER_ID);
            if (CollectionUtils.isEmpty(list) || list.get(0).get("chief_user_id") == null) {
                throw new BaseException("该经办人没有对应的部门负责人！");
            } else if (list.size() > 1) {
                throw new BaseException("该经办人不能对应" + list.size() + "个部门负责人！");
            }

            String chief_user_id = list.get(0).get("chief_user_id").toString();

            ArrayList<Object> userIdList = new ArrayList<>(1);
            userIdList.add(chief_user_id);
            ExtJarHelper.returnValue.set(userIdList);
        }
    }

}
