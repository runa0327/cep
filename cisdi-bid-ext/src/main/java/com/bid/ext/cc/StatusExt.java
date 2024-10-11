package com.bid.ext.cc;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class StatusExt {

    public void changeStatusToZG() {
        //待整改
        String STATE_ID = "1750797105878466560";
        changeStatus(STATE_ID);
    }

    public void changeStatusToFH() {
        //待复核
        String STATE_ID = "1750797109250686976";
        changeStatus(STATE_ID);
    }

    public void changeStatusToHG() {
        //整改合格
        String STATE_ID = "1750797111708549120";
        changeStatus(STATE_ID);
    }

    public void changeStatusToCSHG() {
        //合格
        String STATE_ID = "1768549220054958080";
        changeStatus(STATE_ID);
    }

    public void changeStatusToCG() {
        //草稿
        String STATE_ID = "1768549241596903424";
        changeStatus(STATE_ID);
    }

    private void changeStatus(String STATE_ID) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        SevInfo sevInfo = ExtJarHelper.getSevInfo();
        EntityInfo entityInfo = sevInfo.entityInfo;
        String entityCode = entityInfo.code;
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        if (entityRecordList != null) {
            for (EntityRecord entityRecord : entityRecordList) {
                String csCommId = entityRecord.csCommId;
//                Map<String, Object> valueMap = entityRecord.valueMap;
                int update = myJdbcTemplate.update("update " + entityCode + " t set t.CC_QS_CURRENT_STATE_ID = ? where t.id=?", STATE_ID, csCommId);
                log.info("已更新：{}", update);
            }
        }
    }

    /**
     * 竣工验收状态变更为“验收申请审批中”
     */
    public void changeStatusToApprove() {
        //待复核
        String status = "approve";
        changeAcceptanceStatus(status);
    }

    /**
     * 竣工验收状态变更为“验收中”
     */
    public void changeStatusToAcceptanceInProgress() {
        //待复核
        String status = "acceptance_in_progress";
        changeAcceptanceStatus(status);
    }

    /**
     * 竣工验收状态变更为“验收完成”
     */
    public void changeStatusToAccepted() {
        //待复核
        String status = "accepted";
        changeAcceptanceStatus(status);
    }

    /**
     * 变更验收状态
     *
     * @param status
     */
    private void changeAcceptanceStatus(String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        SevInfo sevInfo = ExtJarHelper.getSevInfo();
        EntityInfo entityInfo = sevInfo.entityInfo;
        String entityCode = entityInfo.code;
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        if (entityRecordList != null) {
            for (EntityRecord entityRecord : entityRecordList) {
                String csCommId = entityRecord.csCommId;
//                Map<String, Object> valueMap = entityRecord.valueMap;
                int update = myJdbcTemplate.update("update " + entityCode + " t set t.CC_ACCEPTANCE_STATUS_ID = ? where t.id=?", status, csCommId);
                log.info("已更新：{}", update);
            }
        }
    }

}
