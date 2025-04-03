package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
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
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

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
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.getPreviewById");
            throw new BaseException(message);
        }
        FlFile flFile = FlFile.selectById(ccAttachment);
        String fileInlineUrl = flFile.getFileInlineUrl();
        Map<String, Object> outputMap = new HashMap<>();
        outputMap.put("fileInlineUrl", fileInlineUrl);
        String ccPreviewFileId = ccDocFile.getCcPreviewFileId();
        outputMap.put("ccPreviewFileId", ccPreviewFileId);
        String ccDocFileName = ccDocFile.getName();
        String tryGetInCurrentLang = I18nUtil.tryGetInCurrentLang(ccDocFileName);
        outputMap.put("name", tryGetInCurrentLang);
        ExtJarHelper.setReturnValue(outputMap);
    }

    /**
     * 收藏文件
     */
    public void addFavoriteFile() {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String originalUserId = loginInfo.userInfo.id; // 保留原始的用户 ID
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String userId = originalUserId; // 每次循环重置为原始用户 ID
            String csCommId = entityRecord.csCommId;
            CcDocFile ccDocFile = CcDocFile.selectById(csCommId);
            String ccFavoritesUserIds = ccDocFile.getCcFavoritesUserIds();

            if (!SharedUtil.isEmpty(ccFavoritesUserIds)) {
                if (!ccFavoritesUserIds.contains(userId)) {
                    userId = ccFavoritesUserIds + "," + userId;
                } else {
                    // 如果已经包含 userId，直接设置 userId 为当前的 ccFavoritesUserIds，跳过更新
                    userId = ccFavoritesUserIds;
                    continue;
                }
            }

            ccDocFile.setCcFavoritesUserIds(userId);
            ccDocFile.updateById();
        }
    }


    /**
     * 取消收藏文件
     */

    public void removeFavoriteFile() {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcDocFile ccDocFile = CcDocFile.selectById(csCommId);
            String ccFavoritesUserIds = ccDocFile.getCcFavoritesUserIds();

            if (!SharedUtil.isEmpty(ccFavoritesUserIds)) {
                // 如果 ccFavoritesUserIds 包含 userId，则将其移除
                if (ccFavoritesUserIds.contains(userId)) {
                    // 移除 userId
                    String updatedFavorites = Arrays.stream(ccFavoritesUserIds.split(","))
                            .filter(id -> !id.equals(userId))
                            .collect(Collectors.joining(","));

                    ccDocFile.setCcFavoritesUserIds(updatedFavorites);
                    ccDocFile.updateById();
                }
            }
        }
    }

    /**
     * 打包导出
     */
    public void exportPackage() throws IOException {

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

        // 创建ZIP文件夹和临时文件夹
        String pathDir = flPath.getDir() + year + "/" + month + "/" + day + "/";
        cn.hutool.core.io.FileUtil.mkdir(pathDir);
        // 指定 ZIP 文件保存位置
        String zipPath = pathDir + fileId + ".zip";
        String folderNameInZip = fileId + "/"; // 压缩包内文件夹的名称

        try (ZipArchiveOutputStream zipOut = new ZipArchiveOutputStream(new FileOutputStream(zipPath))) {
            for (Map<String, String> fileMap : mapList) {
                File file = new File(fileMap.get("path"));
                if (file.exists()) {
                    // 在压缩包中创建虚拟的文件夹路径
                    ZipArchiveEntry zipEntry = new ZipArchiveEntry(folderNameInZip + fileMap.get("name"));
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

        // 获取文件属性
        File file = new File(zipPath);
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
        flFile.setPhysicalLocation(zipPath);
        flFile.setOriginFilePhysicalLocation(zipPath);
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
//        Boolean isView = (Boolean) varMap.get("P_IS_VIEW");
        Boolean isView = JdbcMapUtil.getBoolean(varMap, "P_IS_VIEW");

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
    public void uploadVrFileBatch() {
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
            Where pathWhere = new Where();
            pathWhere.eq(FlPath.Cols.ID, flPathId);
            FlPath flPath = FlPath.selectOneByWhere(pathWhere);

            LocalDate now = LocalDate.now();
            int year = now.getYear();
            String month = String.format("%02d", now.getMonthValue());
            String day = String.format("%02d", now.getDayOfMonth());
            String previewPath = flPath.getDir() + year + "/" + month + "/" + day + "/" + ccDocFile.getId() + "_preview." + flFile.getExt();
            BufferedImage originalImage = null;
            BufferedImage outputImage = null;
            try {
                originalImage = ImageIO.read(new File(originFilePhysicalLocation));

                int targetWidth = 300; // 目标宽度
                int targetHeight = 300; // 目标高度
                Image resizedImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
                outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
                outputImage.getGraphics().drawImage(resizedImage, 0, 0, null);

                File outputFile = new File(previewPath); // 输出图片文件
                ImageIO.write(outputImage, flFile.getExt(), outputFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (originalImage != null) {
                    originalImage.getGraphics().dispose();
                }
                if (outputImage != null) {
                    outputImage.getGraphics().dispose();
                }
            }
//            String flPathId = flFile.getFlPathId();


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

            dspName = I18nUtil.tryGetInCurrentLang(dspName);
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
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("cisdi.gczx.ql.preCheckDocToFile");
            throw new BaseException(message);
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
            String sql = "SELECT IFNULL((SELECT ID FROM CC_DOC_FILE_TYPE WHERE FILE_EXTENSION like CONCAT('%', (SELECT EXT FROM FL_FILE WHERE ID = ?), '%')), 'OTHER') ID";
            Map<String, Object> map = ExtJarHelper.getMyJdbcTemplate().queryForMap(sql, attachmentId);

            String fileType = JdbcMapUtil.getString(map, "ID");

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

    /**
     * 生成打包脚本
     */
    public void generateLinuxCopyCommand() {

        List<Map<String, String>> mapList = new ArrayList<>();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            String ccAttachment = JdbcMapUtil.getString(valueMap, "CC_ATTACHMENT");
            FlFile flFile = FlFile.selectById(ccAttachment);
            String originFilePhysicalLocation = flFile.getOriginFilePhysicalLocation(); // 原始文件物理位置
            String dspName = flFile.getDspName(); // 显示名称
            Map<String, String> map = new HashMap<>();
            map.put("path", originFilePhysicalLocation);
            map.put("name", dspName);
            mapList.add(map);
        }

        // 获取当前日期
        LocalDate today = LocalDate.now();
        String datePath = today.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String targetBaseDir = "/data/qygly/qygly-zipped-file-for-download/ceecip/" + datePath;

        // 创建 tgz 文件名
        FlFile flFile = FlFile.newData();
        String fileId = flFile.getId(); // 生成的唯一文件 ID
        String tgzFilePath = targetBaseDir + "/" + fileId + ".tgz";

        StringBuilder commandBuilder = new StringBuilder();

        // 创建目标目录
        commandBuilder.append("mkdir -p ").append(targetBaseDir).append(" && ");

        // 开始创建 tar 命令，添加一次 --transform 选项来指定 {fileId} 目录
        commandBuilder.append("tar -zcvf ").append(tgzFilePath)
                .append(" --transform='s|^|").append(fileId).append("/|'");

        Map<String, Integer> fileNameCountMap = new HashMap<>();

        for (Map<String, String> fileMap : mapList) {
            String sourceFilePath = fileMap.get("path");
            String originalDspName = fileMap.get("name");

            // 处理文件名重复，通过序号后缀生成唯一的显示名称
            String dspName = originalDspName;
            if (fileNameCountMap.containsKey(originalDspName)) {
                int count = fileNameCountMap.get(originalDspName) + 1;
                fileNameCountMap.put(originalDspName, count);
                int dotIndex = originalDspName.lastIndexOf(".");
                if (dotIndex != -1) {
                    dspName = originalDspName.substring(0, dotIndex) + "-" + count + originalDspName.substring(dotIndex);
                } else {
                    dspName = originalDspName + "-" + count;
                }
            } else {
                fileNameCountMap.put(originalDspName, 1);
            }

            String sourceDir = sourceFilePath.substring(0, sourceFilePath.lastIndexOf('/'));
            String sourceFileName = sourceFilePath.substring(sourceFilePath.lastIndexOf('/') + 1);

            // 为每个文件添加 tar -C 和文件名的 --transform 参数
            commandBuilder.append(" -C ").append(sourceDir)
                    .append(" ").append(sourceFileName)
                    .append(" --transform='s|").append(sourceFileName).append("|").append(dspName).append("|'");
        }
        InvokeActResult invokeActResult = new InvokeActResult();
        String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.genCopyCommand");
        invokeActResult.msg = message + System.lineSeparator() + commandBuilder.toString();
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 预检测竣工验收文件批量上传
     */
    public void preCheckUploadFile() {
        Map<String, List<DrivenInfo>> drivenInfosMap = ExtJarHelper.getDrivenInfosMap();
        String ccDocDirId = null;
        for (Map.Entry<String, List<DrivenInfo>> entry : drivenInfosMap.entrySet()) {
            List<DrivenInfo> drivenInfos = entry.getValue();
            for (DrivenInfo drivenInfo : drivenInfos) {
                String code = drivenInfo.code;
                if ("CC_DOC_DIR_ID".equals(code)) {
                    ccDocDirId = drivenInfo.value;
                }
            }
        }

        CcDocDir ccDocDir = CcDocDir.selectById(ccDocDirId);
        String ccDocDirStatusId = ccDocDir.getCcDocDirStatusId();
        if ("lock".equals(ccDocDirStatusId)) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.preCheckUploadFile");
            throw new BaseException(message);
        }
    }
}
