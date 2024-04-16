package com.bid.ext.cc;

import com.bid.ext.model.CcDocFile;
import com.bid.ext.model.FlFile;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.EntityRecordUtil;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocExt {
    /**
     * 可视化引擎设为默认
     */
    public void setDefault() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        SevInfo sevInfo = ExtJarHelper.getSevInfo();
        EntityInfo entityInfo = sevInfo.entityInfo;
        String entityCode = entityInfo.code;
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String id = EntityRecordUtil.getId(entityRecord);
            CcDocFile ccDocFile = CcDocFile.selectById(id);
            Boolean isDefault = ccDocFile.getIsDefault();
            String type = ccDocFile.getCcDocFileTypeId();
            if (isDefault) {
                List<CcDocFile> ccDocFiles = CcDocFile.selectByWhere(new Where().eq(CcDocFile.Cols.CC_DOC_FILE_TYPE_ID, type));
                for (CcDocFile docFile : ccDocFiles) {
                    docFile.setIsDefault(false);
                    docFile.updateById();
                }
                int update = myJdbcTemplate.update("update " + entityCode + " t set t.IS_DEFAULT = ? where t.id=?", isDefault, id);
            }
        }
    }


    /**
     * 获取分类默认文件url
     */
    public void getFileUrlByType() {
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        String type = JdbcMapUtil.getString(inputMap, "type");
        List<CcDocFile> ccDocFiles = CcDocFile.selectByWhere(new Where().eq(CcDocFile.Cols.CC_DOC_FILE_TYPE_ID, type).eq(CcDocFile.Cols.IS_DEFAULT, true));
        if ("VR".equals(type)) {

            if (SharedUtil.isEmpty(ccDocFiles)) {
                throw new IllegalStateException("未设置默认展示文件，请设置");
            }
            String ccAttachment = ccDocFiles.get(0).getCcAttachment();
            if (SharedUtil.isEmpty(ccAttachment)) {
                throw new IllegalStateException("未上传默认展示文件，请上传");
            }
            FlFile flFile = FlFile.selectById(ccAttachment);
            String fileInlineUrl = flFile.getFileInlineUrl();

            Map<String, Object> outputMap = new HashMap<>();
            outputMap.put("fileInlineUrl", fileInlineUrl);
            ExtJarHelper.setReturnValue(outputMap);
        }
        if ("BIM".equals(type) || "CAD".equals(type)) {
            String ccPreviewFileId = ccDocFiles.get(0).getCcPreviewFileId();

            Map<String, Object> outputMap = new HashMap<>();
            outputMap.put("ccPreviewFileId", ccPreviewFileId);
            ExtJarHelper.setReturnValue(outputMap);
        }
    }

}
