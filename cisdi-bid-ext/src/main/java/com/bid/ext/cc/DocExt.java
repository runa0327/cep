package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.EntityRecordUtil;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
     * 获取分类默认消息ID
     */
    public void getDefaultCommId() {
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        String type = JdbcMapUtil.getString(inputMap, "type");
        List<CcDocFile> ccDocFiles = CcDocFile.selectByWhere(new Where().eq(CcDocFile.Cols.CC_DOC_FILE_TYPE_ID, type).eq(CcDocFile.Cols.IS_DEFAULT, true));
        if (SharedUtil.isEmpty(ccDocFiles)) {
            throw new IllegalStateException("未设置默认展示文件，请设置");
        }
        String csCommId = ccDocFiles.get(0).getId();
        Map<String, Object> outputMap = new HashMap<>();
        outputMap.put("csCommId", csCommId);
        ExtJarHelper.setReturnValue(outputMap);
    }

    /**
     * 通过ID获取预览文件信息
     */
    public void getPreviewById() {
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        String csCommId = JdbcMapUtil.getString(inputMap, "csCommId");
        CcDocFile ccDocFile = CcDocFile.selectById(csCommId);
        String ccAttachment = ccDocFile.getCcAttachment();
        if (SharedUtil.isEmpty(ccAttachment)) {
            throw new IllegalStateException("未上传默认展示文件，请上传");
        }
        FlFile flFile = FlFile.selectById(ccAttachment);
        String fileInlineUrl = flFile.getFileInlineUrl();
        Map<String, Object> outputMap = new HashMap<>();
        outputMap.put("fileInlineUrl", fileInlineUrl);
        String ccPreviewFileId = ccDocFile.getCcPreviewFileId();
        outputMap.put("ccPreviewFileId", ccPreviewFileId);
        ExtJarHelper.setReturnValue(outputMap);
    }

    /**
     * 收藏文件
     */
    public void addFavoriteFile() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcDocFile ccDocFile = CcDocFile.selectById(csCommId);
            ccDocFile.setIsFavorites(true);
            ccDocFile.updateById();
        }
    }

    /**
     * 取消收藏文件
     */

    public void removeFavoriteFile() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcDocFile ccDocFile = CcDocFile.selectById(csCommId);
            ccDocFile.setIsFavorites(false);
            ccDocFile.updateById();
        }
    }

    /**
     * 打包导出
     */
    public void exportPackage() {

        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());

        List<Map<String, String>> mapList = new ArrayList<>();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            String ccAttachment = JdbcMapUtil.getString(valueMap, "CC_ATTACHMENT");
            FlFile flFile = FlFile.selectById(ccAttachment);
            String originFilePhysicalLocation = flFile.getOriginFilePhysicalLocation(); //原始文件物理位置
            String dspName = flFile.getDspName(); //显示名称
            Map<String, String> map = new HashMap<>();
            map.put("path", originFilePhysicalLocation);
            map.put("name", dspName);
            mapList.add(map);
        }

        FlFile flFile = FlFile.newData();
        String fileId = flFile.getId();

        // 获取属性：
        Where attWhere = new Where();
        attWhere.eq(AdAtt.Cols.CODE, CcDocFilePackage.Cols.CC_ATTACHMENT);
        AdAtt adAtt = AdAtt.selectOneByWhere(attWhere);

        // 获取路径：
        Where pathWhere = new Where();
        pathWhere.eq(FlPath.Cols.ID, adAtt.getFilePathId());
        FlPath flPath = FlPath.selectOneByWhere(pathWhere);

        // 指定ZIP文件保存位置
        String path = flPath.getDir() + year + "/" + month + "/" + day + "/" + fileId + ".zip";

        try (ZipArchiveOutputStream zipOut = new ZipArchiveOutputStream(new FileOutputStream(path))) {
            for (Map<String, String> fileMap : mapList) {
                File file = new File(fileMap.get("path"));
                if (file.exists()) {
                    ZipArchiveEntry zipEntry = new ZipArchiveEntry(file, fileMap.get("name"));
                    zipOut.putArchiveEntry(zipEntry);
                    try (FileInputStream fis = new FileInputStream(file)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = fis.read(buffer)) >= 0) {
                            zipOut.write(buffer, 0, length);
                        }
                    }
                    zipOut.closeArchiveEntry();
                }
            }
            zipOut.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取文件属性
        File file = new File(path);
        long bytes = file.length();
        double kilobytes = bytes / 1024.0;

        BigDecimal sizeKb = BigDecimal.valueOf(kilobytes).setScale(9, BigDecimal.ROUND_HALF_UP);
        String dspSize = String.format("%d KB", Math.round(kilobytes));
        flFile.setCrtUserId(loginInfo.userInfo.id);
        flFile.setLastModiUserId(loginInfo.userInfo.id);
        flFile.setFlPathId(flPath.getId());
        flFile.setCode(fileId);
        flFile.setName(fileId);
        flFile.setExt("zip");
        flFile.setDspName(fileId + ".zip");
        flFile.setFileInlineUrl(flPath.getFileInlineUrl() + "?fileId=" + fileId);
        flFile.setFileAttachmentUrl(flPath.getFileAttachmentUrl() + "?fileId=" + fileId);
        flFile.setSizeKb(sizeKb);
        flFile.setDspSize(dspSize);
        flFile.setUploadDttm(LocalDateTime.now());
        flFile.setPhysicalLocation(path);
        flFile.setOriginFilePhysicalLocation(path);
        flFile.setIsPublicRead(false);
        flFile.insertById();
        // 将文件ID设置到CcDocFilePackage上：
        CcDocFilePackage ccDocFilePackage = CcDocFilePackage.insertData();
        ccDocFilePackage.setName(fileId + ".zip");
        
        ccDocFilePackage.setCcAttachment(fileId);
        ccDocFilePackage.updateById();
    }

}
