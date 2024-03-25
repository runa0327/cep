package com.bid.ext.cc;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

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
    private void changeStatus(String STATE_ID){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        SevInfo sevInfo = ExtJarHelper.getSevInfo();
        EntityInfo entityInfo = sevInfo.entityInfo;
        String entityCode = entityInfo.code;
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        if (entityRecordList != null) {
            for (EntityRecord entityRecord : entityRecordList) {
                String csCommId = entityRecord.csCommId;
//                Map<String, Object> valueMap = entityRecord.valueMap;
                int update = myJdbcTemplate.update("update " + entityCode + " t set t.CC_QS_CURRENT_STATE_ID = ? where t.id=?",STATE_ID , csCommId);
                log.info("已更新：{}", update);
            }
        }
    }

}
