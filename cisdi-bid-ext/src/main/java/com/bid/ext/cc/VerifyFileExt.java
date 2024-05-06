package com.bid.ext.cc;

import com.bid.ext.model.CcDocFile;
import com.bid.ext.model.FlFile;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class VerifyFileExt {

    /**
     * 自动添加名称
     */
    public void insertFileName() {
        EntityRecord entityRecord = ExtJarHelper.getEntityRecordList().get(0);
        String csCommId = entityRecord.csCommId;
        Map<String, Object> valueMap = entityRecord.valueMap;
        SevInfo sevInfo = ExtJarHelper.getSevInfo();
        EntityInfo entityInfo = sevInfo.entityInfo;
        String entityCode = entityInfo.code;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String attachmentId = valueMap.get("CC_ATTACHMENT").toString();
        FlFile flFile = FlFile.selectById(attachmentId);
        String dspSize = flFile.getDspSize();
        CcDocFile ccDocFile = CcDocFile.selectById(csCommId);
        ccDocFile.setCcPreviewDspSize(dspSize);
        ccDocFile.updateById();
        Object name = valueMap.get("NAME");
        if (SharedUtil.isEmpty(name)) {
            String fileId = String.valueOf(valueMap.get("CC_ATTACHMENT"));
            String extSql = "select f.ext,f.dsp_name from fl_file f where id = ?";
            String fileName = myJdbcTemplate.queryForMap(extSql, fileId).get("dsp_name").toString();
            int update = myJdbcTemplate.update("update " + entityCode + " t set t.NAME = ? where t.id=?", fileName, csCommId);
        }
    }

    /**
     * 校验拓展名
     */
    public void verify() {
        EntityRecord entityRecord = ExtJarHelper.getEntityRecordList().get(0);
        String csCommId = entityRecord.csCommId;
        Map<String, Object> valueMap = entityRecord.valueMap;
        SevInfo sevInfo = ExtJarHelper.getSevInfo();
        EntityInfo entityInfo = sevInfo.entityInfo;
        String entityCode = entityInfo.code;
        String fileId = String.valueOf(valueMap.get("CC_ATTACHMENT"));
        Object filetypeIdObj = valueMap.get("CC_DOC_FILE_TYPE_ID");
        String filetypeId = filetypeIdObj != null ? filetypeIdObj.toString() : null;

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String extSql = "select f.ext,f.dsp_name from fl_file f where id = ?";
        String extName = myJdbcTemplate.queryForMap(extSql, fileId).get("ext").toString();
        String fileName = myJdbcTemplate.queryForMap(extSql, fileId).get("dsp_name").toString();

        if (filetypeId == null || filetypeId.isEmpty()) {
            String allFileTypeSql = "select id, FILE_EXTENSION from CC_DOC_FILE_TYPE";
            List<Map<String, Object>> fileTypeList = myJdbcTemplate.queryForList(allFileTypeSql);
            String matchedTypeId = null;
            for (Map<String, Object> fileType : fileTypeList) {
                String[] fileExtensions = fileType.get("FILE_EXTENSION").toString().split(";");
                for (String extension : fileExtensions) {
                    if (extName.equals(extension.trim())) {
                        matchedTypeId = fileType.get("id").toString();
                        break;
                    }
                }
                if (matchedTypeId != null) {
                    break; // 找到匹配项后退出循环
                }
            }

            if (matchedTypeId == null) {
                // 如果没有找到匹配项
                throw new BaseException("不能识别当前上传文件类型(." + extName + ")，请联系管理员添加所需文件类型！");
            } else {
                int update = myJdbcTemplate.update("update " + entityCode + " t set t.CC_DOC_FILE_TYPE_ID = ?,t.NAME = ? where t.id=?", matchedTypeId, fileName, csCommId);
                log.info("已更新：{}", update);
            }
        } else {
            // filetypeId不为空的处理逻辑
            String extTypeSql = "select ft.FILE_EXTENSION from CC_DOC_FILE_TYPE ft where id = ?";
            String[] fileExtension = myJdbcTemplate.queryForMap(extTypeSql, filetypeId).get("file_extension").toString().split(";");
            boolean isMatch = false;
            for (String extension : fileExtension) {
                if (extName.equals(extension.trim())) {
                    isMatch = true;
                    break; // 找到匹配项后退出循环
                }
            }

            if (!isMatch) {
                // 如果没有找到匹配项
                throw new BaseException("资料文件类型与上传文件类型(." + extName + ")不匹配，请选择正确的文件类型或不选择文件类型！");
            }
        }
    }
}
