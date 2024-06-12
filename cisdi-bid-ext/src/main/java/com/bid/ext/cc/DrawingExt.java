package com.bid.ext.cc;

import com.bid.ext.model.CcDrawingManagement;
import com.bid.ext.model.CcDrawingUpload;
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
            Boolean isThreeDimensional = ccDrawingManagement.getIsThreeDimensional();
            if (isThreeDimensional && SharedUtil.isEmpty(threeDActDate)) {
                ccDrawingManagement.setCcModelStatusId("TODO");
            } else if (isThreeDimensional && !SharedUtil.isEmpty(threeDActDate)) {
                ccDrawingManagement.setCcModelStatusId("DONE");
            }
            ccDrawingManagement.updateById();
        }
    }

    /**
     * 上传图纸
     */
    public void uploadDrawing() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String pCcDrawingVersionId = JdbcMapUtil.getString(varMap, "P_CC_DRAWING_VERSION_ID");
        String ccAttachment = JdbcMapUtil.getString(varMap, "P_CC_ATTACHMENT");
        CcDrawingUpload ccDrawingUpload = CcDrawingUpload.newData();
        ccDrawingUpload.setCcAttachment(ccAttachment);
        ccDrawingUpload.setCcDrawingVersionId(pCcDrawingVersionId);
        ccDrawingUpload.insertById();
    }

    /**
     * 升级图纸
     */
    public void upgradeDrawing() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String pCcDrawingVersionId = JdbcMapUtil.getString(varMap, "P_CC_DRAWING_VERSION_ID");
        String ccAttachment = JdbcMapUtil.getString(varMap, "P_CC_ATTACHMENT");
        String pRemark = JdbcMapUtil.getString(varMap, "P_REMARK");
        CcDrawingUpload ccDrawingUpload = CcDrawingUpload.newData();
        ccDrawingUpload.setCcAttachment(ccAttachment);
        ccDrawingUpload.setCcDrawingVersionId(pCcDrawingVersionId);
        ccDrawingUpload.setRemark(pRemark);
        ccDrawingUpload.insertById();
    }

}
