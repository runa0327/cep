package com.bid.ext.cc;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;

import java.time.LocalDate;
import java.util.Map;

public class InspectionExt {

    /**
     * 检测整改日期
     */
    public void checkRectifyDate() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            //巡检时间
            LocalDate ccQsInspectionTime = LocalDate.parse(valueMap.get("CC_QS_INSPECTION_TIME").toString());
            //整改时间
            LocalDate ccQsRectifyTime = LocalDate.parse(valueMap.get("CC_QS_RECTIFY_TIME").toString());
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
}
