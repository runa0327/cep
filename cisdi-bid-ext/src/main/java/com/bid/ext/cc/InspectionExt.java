package com.bid.ext.cc;

import com.bid.ext.model.CcPrjMember;
import com.bid.ext.model.CcQsInspection;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;

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
}
