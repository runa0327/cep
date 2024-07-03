package com.bid.ext.cc;

import com.bid.ext.model.CcPrjMember;
import com.bid.ext.model.CcQsInspection;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class InspectionExt {

    /**
     * 检测整改日期
     */
    public void checkRectifyDate() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            // 巡检时间
            LocalDate ccQsInspectionTime = LocalDate.parse(valueMap.get("CC_QS_INSPECTION_TIME").toString());

            // 检查整改时间是否为空
            Object rectifyTimeObject = valueMap.get("CC_QS_RECTIFY_TIME");
            if (rectifyTimeObject == null) {
                throw new BaseException("请从工作台提交整改！");
            }

            // 整改时间
            LocalDate ccQsRectifyTime = LocalDate.parse(rectifyTimeObject.toString());

            // 检查整改时间是否早于巡检时间
            if (ccQsRectifyTime.isBefore(ccQsInspectionTime)) {
                throw new BaseException("整改时间早于巡检时间！");
            }
        }
    }


    /**
     * 检测计划日期
     */
    public void checkPlanDate() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            //从
            LocalDate planFr = LocalDate.parse(valueMap.get("PLAN_FR").toString());
            //到
            LocalDate planTo = LocalDate.parse(valueMap.get("PLAN_TO").toString());
            if (planTo.isBefore(planFr)) {
                throw new BaseException("计划结束时间早于计划开始时间！");
            }
        }
    }

    /**
     * 获取巡检责任人
     */
    public void getInspectionDutyUser() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            CcQsInspection ccQsInspection = CcQsInspection.selectById(csCommId);
            String ccQsDutyUser = ccQsInspection.getCcQsDutyUser();
            if (ccQsDutyUser != null && !ccQsDutyUser.isEmpty()) {
                String[] memberIds = ccQsDutyUser.split(",");
                ArrayList<String> memberIdList = new ArrayList<>(Arrays.asList(memberIds));
                ArrayList<String> userIdList = new ArrayList<>();
                for (String memberId : memberIdList) {
                    CcPrjMember ccPrjMember = CcPrjMember.selectById(memberId);
                    String adUserId = ccPrjMember.getAdUserId();
                    userIdList.add(adUserId);
                }
                ExtJarHelper.setReturnValue(userIdList);
            }
        }
    }

    /**
     * 根据巡检类型更改流程名
     */
    public void updateProcessNameByType() {
        SevInfo sevInfo = ExtJarHelper.getSevInfo();
        EntityInfo entityInfo = sevInfo.entityInfo;
        String entityCode = entityInfo.code;
        EntityRecord entityRecord = ExtJarHelper.getEntityRecordList().get(0);
        String csCommId = entityRecord.csCommId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        Map<String, Object> valueMap = entityRecord.valueMap;
        String ccQsInspectionTypeId = JdbcMapUtil.getString(valueMap, "CC_QS_INSPECTION_TYPE_ID");
        String processName = null;
        switch (ccQsInspectionTypeId) {
            case "1750796211883540480":
                processName = "质量巡检";
                break;
            case "1750796258457092096":
                processName = "安全巡检";
                break;
        }
        myJdbcTemplate.update("UPDATE wf_process_instance pi JOIN cc_qs_inspection t ON pi.ENTITY_RECORD_ID = t.id SET pi.NAME = JSON_SET(pi.NAME, '$.ZH_CN', CONCAT(?, SUBSTRING(JSON_UNQUOTE(JSON_EXTRACT(pi.NAME, '$.ZH_CN')), CHAR_LENGTH('质安巡检') + 1))) WHERE t.id = ?", processName, csCommId);
    }
}
