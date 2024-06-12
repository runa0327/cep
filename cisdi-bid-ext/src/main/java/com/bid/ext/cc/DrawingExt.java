package com.bid.ext.cc;

import com.bid.ext.model.CcDrawingManagement;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.util.Map;

public class DrawingExt {

    /**
     * 变更图纸状态
     */
    public void updateDrawingStatus() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectById(csCommId);
            Map<String, Object> valueMap = entityRecord.valueMap;
            String actDate = JdbcMapUtil.getString(valueMap, "ACT_DATE");
            if (SharedUtil.isEmpty(actDate)) {
                ccDrawingManagement.setCcDrawingStatusId("TODO");
            } else {
                ccDrawingManagement.setCcDrawingStatusId("DONE");
            }
            String threeDActDate = JdbcMapUtil.getString(valueMap, "THREE_D_ACT_DATE");
            if (SharedUtil.isEmpty(threeDActDate)) {
                ccDrawingManagement.setCcModelStatusId("TODO");
            } else {
                ccDrawingManagement.setCcModelStatusId("DONE");
            }
            ccDrawingManagement.updateById();
        }
    }

}
