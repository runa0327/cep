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
import java.util.*;

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
            String ccPrjId = ccDocFile.getCcPrjId();
            if (isDefault) {
                List<CcDocFile> ccDocFiles = CcDocFile.selectByWhere(new Where().eq(CcDocFile.Cols.CC_DOC_FILE_TYPE_ID, type).eq(CcDocFile.Cols.CC_PRJ_ID, ccPrjId));
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

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
//        String pCcPrjIds = "1790672761571196928,1790697187691937792";
        String sql = "SELECT DISTINCT M.CC_PRJ_ID, F.ID, JSON_UNQUOTE(JSON_EXTRACT(p.NAME, '$.ZH_CN')) AS NAME FROM CC_PRJ_MEMBER M LEFT JOIN CC_DOC_FILE F ON F.CC_PRJ_ID = M.CC_PRJ_ID AND F.cc_doc_file_type_id = ? AND F.is_default = ? LEFT JOIN CC_PRJ P ON P.ID = M.CC_PRJ_ID WHERE m.AD_USER_ID = @UID AND (@P_CC_PRJ_IDS IS NULL OR @P_CC_PRJ_IDS LIKE CONCAT('%', m.cc_prj_id, '%'));";
        List<Map<String, Object>> resultMapList = myJdbcTemplate.queryForList(sql, type, true);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("results", resultMapList);
        ExtJarHelper.setReturnValue(resultMap);

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

    /**
     * 资料文件权限
     */
    public void docFileAuth() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String pCcDocMemberIds = JdbcMapUtil.getString(varMap, "P_CC_DOC_MEMBER_IDS");
        Boolean isView = (Boolean) varMap.get("P_IS_VIEW");

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            if (pCcDocMemberIds != null && !pCcDocMemberIds.isEmpty()) {

                List<String> memberIdList = Arrays.asList(pCcDocMemberIds.split(","));

                for (String memberId : memberIdList) {
                    CcPrjMember ccPrjMember = CcPrjMember.selectById(memberId);
                    String adUserId = ccPrjMember.getAdUserId();
                    CcDocFileAuth ccDocFileAuth = CcDocFileAuth.newData();
                    ccDocFileAuth.setCcDocFileId(csCommId);
                    ccDocFileAuth.setAdUserId(adUserId);
                    ccDocFileAuth.setIsView(isView);
                    ccDocFileAuth.insertById();
                }
            }

        }
    }

    /**
     * 创建文档时初始化文档权限（查看）
     */
    public void initDocAuth() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
            String userId = loginInfo.userInfo.id;
            String csCommId = entityRecord.csCommId;
            CcDocFileAuth ccDocFileAuth = CcDocFileAuth.newData();
            ccDocFileAuth.setCcDocFileId(csCommId);
            ccDocFileAuth.setIsView(true);
            ccDocFileAuth.setAdUserId(userId);
            ccDocFileAuth.insertById();
        }
    }

}
