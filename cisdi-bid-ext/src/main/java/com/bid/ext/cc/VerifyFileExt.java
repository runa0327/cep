package com.bid.ext.cc;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class VerifyFileExt {
    EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
    String csCommId = entityRecord.csCommId;
    Map<String, Object> valueMap = entityRecord.valueMap;
    SevInfo sevInfo = ExtJarHelper.sevInfo.get();
    EntityInfo entityInfo = sevInfo.entityInfo;
    String entityCode = entityInfo.code;
    MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
    private Map<String, String> getFileStats() {
        String fileId = String.valueOf(valueMap.get("CC_ATTACHMENT"));
        String extSql = "select f.ext,f.dsp_name from fl_file f where id = ?";
        Map<String, Object> queryResult = myJdbcTemplate.queryForMap(extSql, fileId);
        String extName = queryResult.get("ext").toString();
        String fileName = queryResult.get("dsp_name").toString();

        Map<String, String> result = new HashMap<>();
        result.put("extName", extName);
        result.put("fileName", fileName);
        return result;
    }

    public void insertFileName(){
        Map<String, String> fileStats = getFileStats();
        String fileName = fileStats.get("fileName");
        int update = myJdbcTemplate.update("update " + entityCode + " t set t.NAME = ? where t.id=?", fileName, csCommId);
        log.info("Updated: {}", update);
    }

    public void verify() {
        Map<String, String> fileStats = getFileStats();
        String extName = fileStats.get("extName");
        String fileName = fileStats.get("fileName");

        Object filetypeIdObj = valueMap.get("CC_DOC_FILE_TYPE_ID");
        String filetypeId = filetypeIdObj != null ? filetypeIdObj.toString() : null;

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
                    break;
                }
            }

            if (matchedTypeId == null) {
                // 如果没有找到匹配项
                throw new BaseException("无法识别当前上传文件的类型（." + extName + "），请联系管理员添加所需的文件类型。");
            } else {
                //更新
                int update = myJdbcTemplate.update("update " + entityCode + " t set t.CC_DOC_FILE_TYPE_ID = ?,t.NAME = ? where t.id=?", matchedTypeId, fileName, csCommId);
                log.info("Updated: {}", update);
            }
        } else {
            String extTypeSql = "select ft.FILE_EXTENSION from CC_DOC_FILE_TYPE ft where id = ?";
            String[] fileExtension = myJdbcTemplate.queryForMap(extTypeSql, filetypeId).get("FILE_EXTENSION").toString().split(";");
            boolean isMatch = false;
            for (String extension : fileExtension) {
                if (extName.equals(extension.trim())) {
                    isMatch = true;
                    break;
                }
            }
            if (!isMatch) {
                // 如果没有找到匹配项
                throw new BaseException("资料文件类型与上传文件的类型（." + extName + "）不匹配，请选择正确的文件类型或者不选择文件类型。");
            }
        }
    }

}
