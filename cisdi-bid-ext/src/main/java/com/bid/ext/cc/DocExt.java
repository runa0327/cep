package com.bid.ext.cc;

import cn.hutool.core.io.FileUtil;
import com.bid.ext.model.*;
import com.bid.ext.utils.TemplateUtils;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.DrivenInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.EntityRecordUtil;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.*;

@Slf4j
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

        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String pCcPrjIds = JdbcMapUtil.getString(varMap, "P_CC_PRJ_IDS");
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> resultMapList = null;
        if (null != pCcPrjIds) {
            MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
//        String pCcPrjIds = "1790672761571196928,1790697187691937792";
            String sql = "SELECT DISTINCT P.ID AS CC_PRJ_ID, F.ID, JSON_UNQUOTE(JSON_EXTRACT(P.NAME, '$.ZH_CN')) AS NAME FROM CC_PRJ P LEFT JOIN CC_PRJ_MEMBER M ON P.ID = M.CC_PRJ_ID AND M.AD_USER_ID = @UID LEFT JOIN CC_DOC_FILE F ON F.CC_PRJ_ID = P.ID AND F.cc_doc_file_type_id = ? AND F.is_default = ? WHERE @P_CC_PRJ_IDS IS NOT NULL AND @P_CC_PRJ_IDS LIKE CONCAT('%', P.ID, '%') UNION SELECT DISTINCT P.ID AS CC_PRJ_ID, F.ID, JSON_UNQUOTE(JSON_EXTRACT(P.NAME, '$.ZH_CN')) AS NAME FROM CC_PRJ P LEFT JOIN CC_DOC_FILE F ON F.CC_PRJ_ID = P.ID AND F.cc_doc_file_type_id = ? AND F.is_default = ? WHERE @P_CC_PRJ_IDS IS NOT NULL AND @RCODES LIKE '%ADMIN%' AND @P_CC_PRJ_IDS LIKE CONCAT('%', P.ID, '%')";
            resultMapList = myJdbcTemplate.queryForList(sql, type, true, type, true);
            resultMap.put("anyPrjs", "1");
            log.info(resultMapList.toString());
        } else {
            resultMap.put("anyPrjs", "0");
        }
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
        outputMap.put("name", ccDocFile.getName());
        outputMap.put("ccDocFileId", csCommId);
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

        // 创建ZIP文件夹
        String pathDir = flPath.getDir() + year + "/" + month + "/" + day + "/";
        FileUtil.mkdir(pathDir);
        // 指定ZIP文件保存位置
        String path = pathDir + fileId + ".zip";

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
            throw new BaseException(e);
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

    /**
     * 批量上传项目文档
     */
    public void uploadDocFileBatch() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        String ccAttachment = JdbcMapUtil.getString(varMap, "P_CC_ATTACHMENTS");
        String ccPrjId = JdbcMapUtil.getString(varMap, "P_PRJ_ID");
        String ccDocDirId = JdbcMapUtil.getString(varMap, "P_DOC_DIR_ID");
        List<String> ccAttachmentList = Arrays.asList(ccAttachment.split(","));

        for (String attachmentId : ccAttachmentList) {
            FlFile flFile = FlFile.selectById(attachmentId);
            String dspName = flFile.getDspName();
            String name = flFile.getName();
            String dspSize = flFile.getDspSize();

            CcDocFile ccDocFile = CcDocFile.newData();
            ccDocFile.setCcPrjId(ccPrjId);
            ccDocFile.setName(name);
            ccDocFile.setCcPreviewDspSize(dspSize);

            ccDocFile.setIsDefault(false);
            ccDocFile.setIsFavorites(false);

            String sql = "SELECT IFNULL((SELECT ID FROM CC_DOC_FILE_TYPE WHERE FILE_EXTENSION like CONCAT('%', (SELECT EXT FROM FL_FILE WHERE ID = ?), '%')), 'OTHER') ID";
            Map<String, Object> map = ExtJarHelper.getMyJdbcTemplate().queryForMap(sql, attachmentId);

//            dspName = dspName.toLowerCase();
            String fileType = JdbcMapUtil.getString(map, "ID");
//            if (dspName.endsWith("rar") || dspName.endsWith("7z") || dspName.endsWith("tar") || dspName.endsWith("gzip") || dspName.endsWith("bzip2") || dspName.endsWith("iso")) {
//                fileType = "ARCHIVE";
//            } else if (dspName.endsWith("rvt") || dspName.endsWith("ifc") || dspName.endsWith("bimx") || dspName.endsWith("pln")) {
//                fileType = "BIM";
//            } else if (dspName.endsWith("dwg") || dspName.endsWith("dxf") || dspName.endsWith("cad") || dspName.endsWith("stl")) {
//                fileType = "CAD";
//            } else if (dspName.endsWith("xls") || dspName.endsWith("xlsx") || dspName.endsWith("doc") || dspName.endsWith("docx")) {
//                fileType = "DOC";
//            } else if (dspName.endsWith("mp4") || dspName.endsWith("avi") || dspName.endsWith("mov") || dspName.endsWith("wmv") || dspName.endsWith("flv") || dspName.endsWith("mkv") || dspName.endsWith("webm") || dspName.endsWith("wp3") || dspName.endsWith("wav")) {
//                fileType = "MEDIA";
//            } else if (dspName.endsWith("vrml") || dspName.endsWith("obj") || dspName.endsWith("fbx") || dspName.endsWith("dae")) {
//                fileType = "VR"; // todo 从资料文档批量上传VR的时候没有生成缩略图
//            } else {
//                continue; // todo 确认非法格式的逻辑是什么，前端过滤还是后端过滤，文件类型如何设置
//            }

            ccDocFile.setCcDocFileTypeId(fileType);
            ccDocFile.setCcDocDirId(ccDocDirId);
            ccDocFile.setCcAttachment(attachmentId);
            ccDocFile.insertById();

        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);

    }

    /**
     * 批量上传VR全景，格式要求jpg和png
     */
    public void uploadVrFileBatch() throws IOException {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        String ccAttachment = JdbcMapUtil.getString(varMap, "P_CC_ATTACHMENTS");
        String ccPrjId = JdbcMapUtil.getString(varMap, "P_PRJ_ID");
        String ccDocDirId = JdbcMapUtil.getString(varMap, "P_DOC_DIR_ID");
        String ccDocDate = JdbcMapUtil.getString(varMap, "P_DOC_DATE");
        List<String> ccAttachmentList = Arrays.asList(ccAttachment.split(","));

        for (String attachmentId : ccAttachmentList) {
            FlFile flFile = FlFile.selectById(attachmentId);
            String name = flFile.getName();
            String dspSize = flFile.getDspSize();

            CcDocFile ccDocFile = CcDocFile.newData();
            ccDocFile.setCcPrjId(ccPrjId);
            ccDocFile.setName(name);
            ccDocFile.setCcPreviewDspSize(dspSize);

            ccDocFile.setIsDefault(false);
            ccDocFile.setIsFavorites(false);
            ccDocFile.setCcDocDate(LocalDate.parse(ccDocDate));

            String fileType = "VR";
            String fileInlineUrl = flFile.getFileInlineUrl(); // 获取文件 URL
            String flPathId = flFile.getFlPathId();
            String sql = "select name from CC_QS_IMG_PREVIEW_URL LIMIT 1";
            MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
            Map<String, Object> map = myJdbcTemplate.queryForMap(sql);
            String urlHead = JdbcMapUtil.getString(map, "name");
            String sessionId = ExtJarHelper.getLoginInfo().sessionId;
            LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
//            String url = urlHead + fileInlineUrl + "&qygly-session-id=" + sessionId;

//            BufferedImage originalImage = ImageIO.read(new URL(url));

            String originFilePhysicalLocation = flFile.getOriginFilePhysicalLocation();
            BufferedImage originalImage = ImageIO.read(new File(originFilePhysicalLocation));
//            String flPathId = flFile.getFlPathId();


            int targetWidth = 300; // 目标宽度
            int targetHeight = 300; // 目标高度
            Image resizedImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
            outputImage.getGraphics().drawImage(resizedImage, 0, 0, null);

            Where pathWhere = new Where();
            pathWhere.eq(FlPath.Cols.ID, flPathId);
            FlPath flPath = FlPath.selectOneByWhere(pathWhere);

            LocalDate now = LocalDate.now();
            int year = now.getYear();
            String month = String.format("%02d", now.getMonthValue());
            String day = String.format("%02d", now.getDayOfMonth());

            String previewPath = flPath.getDir() + year + "/" + month + "/" + day + "/" + ccDocFile.getId() + "_preview." + flFile.getExt();
            File outputFile = new File(previewPath); // 输出图片文件
            ImageIO.write(outputImage, flFile.getExt(), outputFile);

            if (checkFileExists(previewPath)) {
                FlFile attachmentPreview = FlFile.newData();
                String fileId = attachmentPreview.getId();

                File file = new File(previewPath);
                long bytes = file.length();
                double kilobytes = bytes / 1024.0;

                BigDecimal sizeKb = BigDecimal.valueOf(kilobytes).setScale(9, BigDecimal.ROUND_HALF_UP);
                String previewDspSize = String.format("%d KB", Math.round(kilobytes));
                attachmentPreview.setCrtUserId(loginInfo.userInfo.id);
                attachmentPreview.setLastModiUserId(loginInfo.userInfo.id);
                attachmentPreview.setFlPathId(flPath.getId());
                attachmentPreview.setCode(fileId);
                attachmentPreview.setName(ccDocFile.getId() + "_preview");
                attachmentPreview.setExt(flFile.getExt());
                attachmentPreview.setDspName(ccDocFile.getId() + "_preview." + flFile.getExt());
                attachmentPreview.setFileInlineUrl(flPath.getFileInlineUrl() + "?fileId=" + fileId);
                attachmentPreview.setFileAttachmentUrl(flPath.getFileAttachmentUrl() + "?fileId=" + fileId);
                attachmentPreview.setSizeKb(sizeKb);
                attachmentPreview.setDspSize(previewDspSize);
                attachmentPreview.setUploadDttm(LocalDateTime.now());
                attachmentPreview.setPhysicalLocation(previewPath);
                attachmentPreview.setOriginFilePhysicalLocation(previewPath);
//                flFile.setIsPublicRead(flPath.getIsPublicRead());
                attachmentPreview.setIsPublicRead(false);
                attachmentPreview.setIsPublicRead(true);
                attachmentPreview.insertById();
//                    attachmentPreview.url;
                ccDocFile.setCcPreviewAttachment(fileId);

                ccDocFile.setCcDocFileTypeId(fileType);
                ccDocFile.setCcDocDirId(ccDocDirId);
                ccDocFile.setCcAttachment(attachmentId);
                ccDocFile.setCcDocFileFrom(1);
                ccDocFile.insertById();
            }
        }
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 批量上传CAD图纸
     */
    public void uploadVrOrCadFileBatch() throws IOException {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        String ccAttachment = JdbcMapUtil.getString(varMap, "P_CC_ATTACHMENTS");
        String ccPrjId = JdbcMapUtil.getString(varMap, "P_PRJ_ID");
        String ccDocDirId = JdbcMapUtil.getString(varMap, "P_DOC_DIR_ID");
        List<String> ccAttachmentList = Arrays.asList(ccAttachment.split(","));

        for (String attachmentId : ccAttachmentList) {
            FlFile flFile = FlFile.selectById(attachmentId);
            String dspName = flFile.getDspName();
            String name = flFile.getName();
            String dspSize = flFile.getDspSize();

            CcDocFile ccDocFile = CcDocFile.newData();
            ccDocFile.setCcPrjId(ccPrjId);
            ccDocFile.setName(name);
            ccDocFile.setCcPreviewDspSize(dspSize);

            ccDocFile.setIsDefault(false);
            ccDocFile.setIsFavorites(false);

            dspName = dspName.toLowerCase();
            String fileType = null;
            if (dspName.endsWith("dwg")) {
                fileType = "CAD";
            } else if (dspName.endsWith("jpg") || dspName.endsWith("png")) {
                fileType = "VR";
                String fileInlineUrl = flFile.getFileInlineUrl(); // 获取文件 URL
                String flPathId = flFile.getFlPathId();
                String sql = "select name from CC_QS_IMG_PREVIEW_URL LIMIT 1";
                MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
                Map<String, Object> map = myJdbcTemplate.queryForMap(sql);
                String urlHead = JdbcMapUtil.getString(map, "name");
                String sessionId = ExtJarHelper.getLoginInfo().sessionId;
                LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
                String url = urlHead + fileInlineUrl + "&qygly-session-id=" + sessionId;

                BufferedImage originalImage = ImageIO.read(new URL(url));
                int targetWidth = 300; // 目标宽度
                int targetHeight = 300; // 目标高度
                Image resizedImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
                BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
                outputImage.getGraphics().drawImage(resizedImage, 0, 0, null);

                Where pathWhere = new Where();
                pathWhere.eq(FlPath.Cols.ID, flPathId);
                FlPath flPath = FlPath.selectOneByWhere(pathWhere);

                LocalDate now = LocalDate.now();
                int year = now.getYear();
                String month = String.format("%02d", now.getMonthValue());
                String day = String.format("%02d", now.getDayOfMonth());

                String previewPath = flPath.getDir() + year + "/" + month + "/" + day + "/" + ccDocFile.getId() + "_preview." + flFile.getExt();
                File outputFile = new File(previewPath); // 输出图片文件
                ImageIO.write(outputImage, flFile.getExt(), outputFile);

//                saveWordToFile(bytes1, previewPath);


                if (checkFileExists(previewPath)) {
                    FlFile attachmentPreview = FlFile.newData();
                    String fileId = attachmentPreview.getId();

                    File file = new File(previewPath);
                    long bytes = file.length();
                    double kilobytes = bytes / 1024.0;

                    BigDecimal sizeKb = BigDecimal.valueOf(kilobytes).setScale(9, BigDecimal.ROUND_HALF_UP);
                    String previewDspSize = String.format("%d KB", Math.round(kilobytes));
                    attachmentPreview.setCrtUserId(loginInfo.userInfo.id);
                    attachmentPreview.setLastModiUserId(loginInfo.userInfo.id);
                    attachmentPreview.setFlPathId(flPath.getId());
                    attachmentPreview.setCode(fileId);
                    attachmentPreview.setName(ccDocFile.getId() + "_preview");
                    attachmentPreview.setExt(flFile.getExt());
                    attachmentPreview.setDspName(ccDocFile.getId() + "_preview." + flFile.getExt());
                    attachmentPreview.setFileInlineUrl(flPath.getFileInlineUrl() + "?fileId=" + fileId);
                    attachmentPreview.setFileAttachmentUrl(flPath.getFileAttachmentUrl() + "?fileId=" + fileId);
                    attachmentPreview.setSizeKb(sizeKb);
                    attachmentPreview.setDspSize(previewDspSize);
                    attachmentPreview.setUploadDttm(LocalDateTime.now());
                    attachmentPreview.setPhysicalLocation(previewPath);
                    attachmentPreview.setOriginFilePhysicalLocation(previewPath);
//                flFile.setIsPublicRead(flPath.getIsPublicRead());
                    attachmentPreview.setIsPublicRead(false);
                    attachmentPreview.setIsPublicRead(true);
                    attachmentPreview.insertById();
//                    attachmentPreview.url;
                    ccDocFile.setCcPreviewAttachment(fileId);
                }
            } else {
                continue;
            }
            ccDocFile.setCcDocFileTypeId(fileType);
            ccDocFile.setCcDocDirId(ccDocDirId);
            ccDocFile.setCcAttachment(attachmentId);
            ccDocFile.setCcDocFileFrom(2);
            ccDocFile.insertById();
        }
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 当上传的文件是VR时生成VR缩略图
     */
    public void genVrDocPreview() throws IOException {
        InvokeActResult invokeActResult = new InvokeActResult();
//        Map<String, Object> varMap = ExtJarHelper.getVarMap();
//        String ccAttachment = JdbcMapUtil.getString(varMap, "P_CC_PRJ_IDS");

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String sql = "select name from CC_QS_IMG_PREVIEW_URL LIMIT 1";
        Map<String, Object> map = myJdbcTemplate.queryForMap(sql);
        String urlHead = JdbcMapUtil.getString(map, "name");
        String sessionId = ExtJarHelper.getLoginInfo().sessionId;
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            String id = JdbcMapUtil.getString(valueMap, "ID");
            CcDocFile ccDocFile = CcDocFile.selectById(id);
            if ("DR".equals(ccDocFile.getStatus()) || !"VR".equals(ccDocFile.getCcDocFileTypeId())) {
                return;
            }

            FlFile flFile = FlFile.selectById(ccDocFile.getCcAttachment()); // 获取文件对象
            if (null != flFile) {
                String originFilePhysicalLocation = flFile.getOriginFilePhysicalLocation();
                BufferedImage originalImage = ImageIO.read(new File(originFilePhysicalLocation));
                String flPathId = flFile.getFlPathId();

//                String fileInlineUrl = flFile.getFileInlineUrl(); // 获取文件 URL
//                String url = urlHead + fileInlineUrl + "&qygly-session-id=" + sessionId;
//                BufferedImage originalImage = ImageIO.read(new URL(url));

//                BufferedImage originalImage = ImageIO.read(new URL(url));
                int targetWidth = 300; // 目标宽度
                int targetHeight = 300; // 目标高度
                Image resizedImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
                BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
                outputImage.getGraphics().drawImage(resizedImage, 0, 0, null);

                Where pathWhere = new Where();
                pathWhere.eq(FlPath.Cols.ID, flPathId);
                FlPath flPath = FlPath.selectOneByWhere(pathWhere);

                LocalDate now = LocalDate.now();
                int year = now.getYear();
                String month = String.format("%02d", now.getMonthValue());
                String day = String.format("%02d", now.getDayOfMonth());

                String previewPath = flPath.getDir() + year + "/" + month + "/" + day + "/" + ccDocFile.getId() + "_preview." + flFile.getExt();
                File outputFile = new File(previewPath); // 输出图片文件
                ImageIO.write(outputImage, flFile.getExt(), outputFile);

//                saveWordToFile(bytes1, previewPath);


                if (checkFileExists(previewPath)) {
                    FlFile attachmentPreview = FlFile.newData();
                    String fileId = attachmentPreview.getId();

                    File file = new File(previewPath);
                    long bytes = file.length();
                    double kilobytes = bytes / 1024.0;

                    BigDecimal sizeKb = BigDecimal.valueOf(kilobytes).setScale(9, BigDecimal.ROUND_HALF_UP);
                    String dspSize = String.format("%d KB", Math.round(kilobytes));
                    attachmentPreview.setCrtUserId(loginInfo.userInfo.id);
                    attachmentPreview.setLastModiUserId(loginInfo.userInfo.id);
                    attachmentPreview.setFlPathId(flPath.getId());
                    attachmentPreview.setCode(fileId);
                    attachmentPreview.setName(ccDocFile.getId() + "_preview");
                    attachmentPreview.setExt(flFile.getExt());
                    attachmentPreview.setDspName(ccDocFile.getId() + "_preview." + flFile.getExt());
                    attachmentPreview.setFileInlineUrl(flPath.getFileInlineUrl() + "?fileId=" + fileId);
                    attachmentPreview.setFileAttachmentUrl(flPath.getFileAttachmentUrl() + "?fileId=" + fileId);
                    attachmentPreview.setSizeKb(sizeKb);
                    attachmentPreview.setDspSize(dspSize);
                    attachmentPreview.setUploadDttm(LocalDateTime.now());
                    attachmentPreview.setPhysicalLocation(previewPath);
                    attachmentPreview.setOriginFilePhysicalLocation(previewPath);
//                flFile.setIsPublicRead(flPath.getIsPublicRead());
                    attachmentPreview.setIsPublicRead(false);
                    attachmentPreview.setIsPublicRead(true);
                    attachmentPreview.insertById();
//                    attachmentPreview.url;
                    ccDocFile.setCcPreviewAttachment(fileId);
                    ccDocFile.updateById();
                }
            }
        }

        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    public static boolean checkFileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 资料目录套用模板
     */
    public void docDirToTemplate() {
        InvokeActResult invokeActResult = new InvokeActResult();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String ccPrjId = JdbcMapUtil.getString(varMap, "P_CC_PRJ_ID");
        String ccDocFolderTypeId = JdbcMapUtil.getString(varMap, "P_CC_DOC_FOLDER_TYPE_ID");
        String ccDocDirAcceptanceTemplateType = JdbcMapUtil.getString(varMap, "P_CC_DOC_DIR_ACCEPTANCE_TEMPLATE_TYPE_ID");
        //查询该类型下所有模板的根节点
        String sql = "select t.id from cc_doc_dir t where t.CC_DOC_DIR_ACCEPTANCE_TEMPLATE_TYPE_ID = ? and t.CC_DOC_DIR_PID is null and t.CC_DOC_FOLDER_TYPE_ID = 'acceptance' and t.IS_TEMPLATE = 1";
        List<Map<String, Object>> queryForList = myJdbcTemplate.queryForList(sql, ccDocDirAcceptanceTemplateType);
        for (Map<String, Object> map : queryForList) {
            String templateRootId = JdbcMapUtil.getString(map, "ID");
            //套用模板

            List<Map<String, Object>> insertedNodes = TemplateUtils.applyTemplate(templateRootId, "CC_DOC_DIR", "ID", "CC_DOC_DIR_PID", true);
            // 对插入后的节点进行统一处理
            for (Map<String, Object> node : insertedNodes) {
                String id = node.get("ID").toString();
                String updateSql = "UPDATE cc_doc_dir SET CC_PRJ_ID = ?, CC_DOC_FOLDER_TYPE_ID = ?, CC_DOC_DIR_ACCEPTANCE_TEMPLATE_TYPE_ID = ?, CC_DOC_DIR_STATUS_ID = 'unlock' WHERE id = ?";
                myJdbcTemplate.update(updateSql, ccPrjId, ccDocFolderTypeId, ccDocDirAcceptanceTemplateType, id);
            }
        }
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 锁定资料目录下文件
     */
    public void lockDoc() {
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;
            CcDocDir ccDocDir = CcDocDir.selectById(csCommId);
            ccDocDir.setCcDocDirStatusId("lock");
            ccDocDir.updateById();
        }
    }

    /**
     * 解锁资料目录下文件
     */
    public void unlockDoc() {
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;
            CcDocDir ccDocDir = CcDocDir.selectById(csCommId);
            ccDocDir.setCcDocDirStatusId("unlock");
            ccDocDir.updateById();
        }
    }

    /**
     * 资料清单关联文件预检测
     */
    public void preCheckDocToFile() {
        Map<String, List<DrivenInfo>> drivenInfosMap = ExtJarHelper.getDrivenInfosMap();
        if (drivenInfosMap.size() != 1) {
            throw new BaseException("请勿选择多个目录");
        }

    }


    /**
     * 批量上传竣工资料
     */
    public void uploadDocFileAcceptance() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        String ccAttachment = JdbcMapUtil.getString(varMap, "P_CC_ATTACHMENTS");
        String ccPrjId = JdbcMapUtil.getString(varMap, "P_PRJ_ID");
        String ccDocDirId = JdbcMapUtil.getString(varMap, "P_DOC_DIR_ID");
        List<String> ccAttachmentList = Arrays.asList(ccAttachment.split(","));

        for (String attachmentId : ccAttachmentList) {
            FlFile flFile = FlFile.selectById(attachmentId);
            String dspName = flFile.getDspName();
            String name = flFile.getName();
            String dspSize = flFile.getDspSize();

            CcDocFile ccDocFile = CcDocFile.newData();
            ccDocFile.setCcPrjId(ccPrjId);
            ccDocFile.setName(name);
            ccDocFile.setCcPreviewDspSize(dspSize);

            ccDocFile.setIsDefault(false);
            ccDocFile.setIsFavorites(false);

            ccDocFile.setCcDocFileFrom(4);
            ccDocFile.setCcDocFileTypeId("ACCEPTANCE");
            ccDocFile.setCcDocDirId(ccDocDirId);
            ccDocFile.setCcAttachment(attachmentId);
            ccDocFile.insertById();

        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);

    }

    /**
     * 新建竣工资料模板
     */
    public void creatAcceptanceTemplate() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String pName = JdbcMapUtil.getString(varMap, "P_NAME");
        String pApplyZone = JdbcMapUtil.getString(varMap, "P_APPLY_ZONE_ID");
        CcDocDirAcceptanceTemplateType ccDocDirAcceptanceTemplateType = CcDocDirAcceptanceTemplateType.newData();
        ccDocDirAcceptanceTemplateType.setCcApplyZoneId(pApplyZone);
        ccDocDirAcceptanceTemplateType.setName(pName);
        ccDocDirAcceptanceTemplateType.insertById();
    }
}
