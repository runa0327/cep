package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.util.Arrays;
import java.util.List;
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
            } else if (!isThreeDimensional) {
                ccDrawingManagement.setCcModelStatusId(null);
            }
            ccDrawingManagement.updateById();
        }
    }

    /**
     * 上传图纸
     */
    public void uploadDrawing() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            //套图信息
            CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectById(csCommId);
            String ccPrjStructNodeId = ccDrawingManagement.getCcPrjStructNodeId();
            String ccSteelOwnerDrawingId = ccDrawingManagement.getCcSteelOwnerDrawingId();

            String ccAttachment = JdbcMapUtil.getString(varMap, "P_CC_ATTACHMENTS");

            //套图版本
            CcStructDrawingVersion ccStructDrawingVersion = CcStructDrawingVersion.newData();
            ccStructDrawingVersion.setCcDrawingVersionId("A");
            ccStructDrawingVersion.setCcDrawingManagementId(csCommId);
            ccStructDrawingVersion.setCcPrjStructNodeId(ccPrjStructNodeId);
            ccStructDrawingVersion.setCcSteelOwnerDrawingId(ccSteelOwnerDrawingId);
            ccStructDrawingVersion.insertById();

            List<String> ccAttachmentList = Arrays.asList(ccAttachment.split(","));
            for (String attachmentId : ccAttachmentList) {
                FlFile flFile = FlFile.selectById(attachmentId);
                String dspName = flFile.getDspName();
                CcDrawingUpload ccDrawingUpload = CcDrawingUpload.newData();
                ccDrawingUpload.setCcAttachment(attachmentId);
                ccDrawingUpload.setCcDrawingVersionId("A");
                ccDrawingUpload.setCcDrawingManagementId(csCommId);
                ccDrawingUpload.setName(dspName);
                ccDrawingUpload.insertById();
            }
        }
    }

    /**
     * 升级图纸
     */
    public void upgradeDrawing() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            String ccAttachment = JdbcMapUtil.getString(varMap, "P_CC_ATTACHMENTS");

            String[] versionOrder = {"A", "B", "C", "D", "E", "F", "G"};
            String ccDrawingVersionId = null;

            // 依次判断版本的存在性
            for (String version : versionOrder) {
                List<CcDrawingUpload> ccDrawingUploads = CcDrawingUpload.selectByWhere(
                        new Where().eq(CcDrawingUpload.Cols.CC_DRAWING_VERSION_ID, version)
                                .eq(CcDrawingUpload.Cols.CC_DRAWING_MANAGEMENT_ID, csCommId)
                );
                if (SharedUtil.isEmpty(ccDrawingUploads)) {
                    ccDrawingVersionId = version;
                    break;
                }
            }

            //套图信息
            CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectById(csCommId);
            String ccPrjStructNodeId = ccDrawingManagement.getCcPrjStructNodeId();
            String ccSteelOwnerDrawingId = ccDrawingManagement.getCcSteelOwnerDrawingId();

            //套图版本
            CcStructDrawingVersion ccStructDrawingVersion = CcStructDrawingVersion.newData();
            ccStructDrawingVersion.setCcDrawingVersionId(ccDrawingVersionId);
            CcDrawingVersion ccDrawingVersion = CcDrawingVersion.selectById(ccDrawingVersionId);
            String ccDrawingVersionName = ccDrawingVersion.getName();
            ccStructDrawingVersion.setName(ccDrawingVersionName);
            ccStructDrawingVersion.setCcDrawingManagementId(csCommId);
            ccStructDrawingVersion.setCcPrjStructNodeId(ccPrjStructNodeId);
            ccStructDrawingVersion.setCcSteelOwnerDrawingId(ccSteelOwnerDrawingId);
            ccStructDrawingVersion.insertById();

            List<String> ccAttachmentList = Arrays.asList(ccAttachment.split(","));
            for (String attachmentId : ccAttachmentList) {
                FlFile flFile = FlFile.selectById(attachmentId);
                String dspName = flFile.getDspName();
                String pRemark = JdbcMapUtil.getString(varMap, "P_REMARK");
                CcDrawingUpload ccDrawingUpload = CcDrawingUpload.newData();
                ccDrawingUpload.setCcStructDrawingVersionId(ccStructDrawingVersion.getId());
                ccDrawingUpload.setCcAttachment(attachmentId);
                ccDrawingUpload.setCcDrawingVersionId(ccDrawingVersionId);
                ccDrawingUpload.setRemark(pRemark);
                ccDrawingUpload.setCcDrawingManagementId(csCommId);
                ccDrawingUpload.setName(dspName);
                ccDrawingUpload.insertById();
            }
        }
    }

    /**
     * 更新图纸时同步更新图纸版本的业主图号
     */
    public void updateDrawing() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectById(csCommId);
            //业主图号
            String ccSteelOwnerDrawingId = ccDrawingManagement.getCcSteelOwnerDrawingId();
            List<CcStructDrawingVersion> ccStructDrawingVersions = CcStructDrawingVersion.selectByWhere(new Where().eq(CcStructDrawingVersion.Cols.CC_DRAWING_MANAGEMENT_ID, csCommId));
            if (!SharedUtil.isEmpty(ccStructDrawingVersions)) {
                for (CcStructDrawingVersion ccStructDrawingVersion : ccStructDrawingVersions) {
                    ccStructDrawingVersion.setCcSteelOwnerDrawingId(ccSteelOwnerDrawingId);
                    ccStructDrawingVersion.updateById();
                }
            }
        }
    }

    /**
     * 设为默认图纸
     */
    public void setDefaultDrawing() {
        InvokeActResult invokeActResult = new InvokeActResult();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            //查询此合同该版本的套图
            CcDrawingUpload ccDrawingUpload = CcDrawingUpload.selectById(csCommId);
            //图纸
            String ccDrawingManagementId = ccDrawingUpload.getCcDrawingManagementId();
            //版本
            String ccDrawingVersionId = ccDrawingUpload.getCcDrawingVersionId();
            List<CcDrawingUpload> ccDrawingUploads = CcDrawingUpload.selectByWhere(new Where().eq(CcDrawingUpload.Cols.CC_DRAWING_MANAGEMENT_ID, ccDrawingManagementId).eq(CcDrawingUpload.Cols.CC_DRAWING_VERSION_ID, ccDrawingVersionId));
            if (!SharedUtil.isEmpty(ccDrawingUploads)) {
                for (CcDrawingUpload ccDrawingUpload1 : ccDrawingUploads) {
                    ccDrawingUpload1.setIsDefault(false);
                    ccDrawingUpload1.updateById();
                }
            }
            ccDrawingUpload.setIsDefault(true);
            ccDrawingUpload.updateById();
        }
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

}
