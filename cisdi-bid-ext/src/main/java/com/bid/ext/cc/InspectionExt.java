package com.bid.ext.cc;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;

import java.time.LocalDate;
import java.util.Map;

public class InspectionExt {

    /**
     * 检测整改时间
     */
    public void checkRectifyTime() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            //整改时间
            LocalDate ccQsRectifyTime = LocalDate.parse(valueMap.get("CC_QS_RECTIFY_TIME").toString());
            //巡检时间
            LocalDate ccQsInspectionTime = LocalDate.parse(valueMap.get("CC_QS_INSPECTION_TIME").toString());
            if (ccQsRectifyTime.isBefore(ccQsInspectionTime)) {
                throw new BaseException("整改时间早于巡检时间！");
            }
        }
    }
}
