package com.bid.ext.cc;

import com.bid.ext.model.CcConstructProgressPlan;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.ad.entity.StatusE;
import com.qygly.shared.interaction.EntityRecord;

public class ConstructProgressExt {

    /**
     * 施工进度计划更新
     */
    public void updateProgress() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcConstructProgressPlan ccConstructProgressPlan = CcConstructProgressPlan.selectById(csCommId);
            ccConstructProgressPlan.setCcConstructIsCommit(false);
            ccConstructProgressPlan.updateById();
        }
    }

    /**
     * 施工进度计划作废
     */
    public void DropProgress() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcConstructProgressPlan ccConstructProgressPlan = CcConstructProgressPlan.selectById(csCommId);
            ccConstructProgressPlan.setStatus(String.valueOf(StatusE.DN));
            ccConstructProgressPlan.updateById();
        }
    }

}
