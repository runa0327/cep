package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.awt.Image;


@Slf4j
public class VrExt {

    /**
     * 生成VR缩略图
     */
    public void genVrPreview() throws IOException {
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
            CcVr ccVr = CcVr.selectById(id);


//            List<Map<String, Object>> imgs = new ArrayList<>();
            FlFile flFile = FlFile.selectById(ccVr.getCcVrAttachment()); // 获取文件对象
            if (null != flFile) {
                String fileInlineUrl = flFile.getFileInlineUrl(); // 获取文件 URL
                String flPathId = flFile.getFlPathId();
                String url = urlHead + fileInlineUrl + "&qygly-session-id=" + sessionId;

//                File inputFile = new File(url); // 输入图片文件
//                ImageIO.read(new URL(url););

                BufferedImage originalImage = ImageIO.read(new URL(url));
                int targetWidth = 300; // 目标宽度
                int targetHeight = 300; // 目标高度
                Image resizedImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
                BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
                outputImage.getGraphics().drawImage(resizedImage, 0, 0, null);

//
//                PictureRenderData pictureRenderData = Pictures.ofUrl(url).size(10, 10).create();
//                byte[] bytes1 = pictureRenderData.readPictureData();

                Where pathWhere = new Where();
                pathWhere.eq(FlPath.Cols.ID, flPathId);
                FlPath flPath = FlPath.selectOneByWhere(pathWhere);

                LocalDate now = LocalDate.now();
                int year = now.getYear();
                String month = String.format("%02d", now.getMonthValue());
                String day = String.format("%02d", now.getDayOfMonth());

//                String previewPath = flPath.getDir()+ ccVr.getId() + "_preview." + flFile.getExt();
                String previewPath = flPath.getDir() + year + "/" + month + "/" + day + "/" + ccVr.getId() + "_preview." + flFile.getExt();
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
                    attachmentPreview.setName(ccVr.getId() + "_preview");
                    attachmentPreview.setExt(flFile.getExt());
                    attachmentPreview.setDspName(ccVr.getId() + "_preview." + flFile.getExt());
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
                    ccVr.setCcVrAttachmentPreview(fileId);
                    ccVr.updateById();
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
     * API:获取所有VR全景
     */
    public void getAllVr() {
//        Map<String, Object> varMap = ExtJarHelper.getVarMap();
//        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
//        //全景id
//        String ccDocFileId = JdbcMapUtil.getString(varMap, "P_CC_PRJ_ID");
//        String ccPrjIds = JdbcMapUtil.getString(inputMap, "ccPrjId");
//        List<CcVr> ccVrs = CcVr.selectByWhere(new Where().eq(CcVr.Cols.CC_PRJ_ID, ccPrjIds));

        String sqlPanoMonth = "SELECT DISTINCT (DATE_FORMAT( CC_DOC_DATE, '%Y-%m' )) CC_PANO_MONTH, CONCAT( YEAR ( CC_DOC_DATE ), '年', MONTH ( CC_DOC_DATE ), '月' ) CC_PANO_RET_MONTH FROM CC_DOC_FILE WHERE CC_DOC_DATE IS NOT NULL AND (@P_CC_PRJ_IDS IS NULL OR @P_CC_PRJ_IDS LIKE CONCAT('%', CC_PRJ_ID, '%')) ORDER BY CC_PANO_MONTH DESC";
        List<Map<String, Object>> ccPanoMonths = ExtJarHelper.getMyJdbcTemplate().queryForList(sqlPanoMonth);

        List<Map<String, Object>> vrLst = new ArrayList<>();
        List<String> fileIdList = new ArrayList<>();

        for (Map<String, Object> mapPanoMonth : ccPanoMonths) {
            String panoMonth = JdbcMapUtil.getString(mapPanoMonth, "CC_PANO_MONTH");
            String sqlPanoLst = "SELECT `NAME`, ID, CC_DOC_DATE,  CONCAT( YEAR ( CC_DOC_DATE ), '年', MONTH ( CC_DOC_DATE ), '月' ) CC_YEAR_MONTH, CC_ATTACHMENT, CC_PREVIEW_ATTACHMENT FROM CC_DOC_FILE WHERE DATE_FORMAT(CC_DOC_DATE, '%Y-%m') LIKE ? AND (@P_CC_PRJ_IDS IS NULL OR @P_CC_PRJ_IDS LIKE CONCAT('%', CC_PRJ_ID, '%')) ORDER BY CC_DOC_DATE DESC";
            List<Map<String, Object>> ccVrs = ExtJarHelper.getMyJdbcTemplate().queryForList(sqlPanoLst, panoMonth);
            List<Map<String, String>> panoLst = new ArrayList<>();

            for (Map<String, Object> ccVr : ccVrs) {
                String name = JdbcMapUtil.getString(ccVr, "NAME");
                String vrDate = JdbcMapUtil.getString(ccVr, "CC_DOC_DATE");
                String ccVrYearMonth = JdbcMapUtil.getString(ccVr, "CC_YEAR_MONTH");
                String ccVrAttachment = JdbcMapUtil.getString(ccVr, "CC_ATTACHMENT");
                String ccVrAttachmentPreview = JdbcMapUtil.getString(ccVr, "CC_PREVIEW_ATTACHMENT");
                String ccDocFileId = JdbcMapUtil.getString(ccVr, "ID");

                if (null == ccVrAttachment || null == ccVrAttachmentPreview) {
                    continue;
                }

                FlFile ccVrAttachmentFlFile = FlFile.selectById(ccVrAttachment);
                FlFile ccVrAttachmentPreviewFlFile = FlFile.selectById(ccVrAttachmentPreview);

//                if (!checkFileExists(ccVrAttachmentFlFile.getPhysicalLocation()) || !checkFileExists(ccVrAttachmentPreviewFlFile.getPhysicalLocation())) {
//                    continue;
//                }

                Map<String, String> ccVrItem = new HashMap<>();
                ccVrItem.put("NAME", name);
                ccVrItem.put("VR_DATE", vrDate);
                ccVrItem.put("CC_YEAR_MONTH", ccVrYearMonth);
                ccVrItem.put("CC_VR_ATTACHMENT", ccVrAttachmentFlFile.getFileInlineUrl());
                ccVrItem.put("CC_VR_ATTACHMENT_ID", ccVrAttachmentFlFile.getId());
                ccVrItem.put("CC_VR_ATTACHMENT_PREVIEW", ccVrAttachmentPreviewFlFile.getFileInlineUrl());
                ccVrItem.put("CC_VR_ATTACHMENT_PREVIEW_ID", ccVrAttachmentPreviewFlFile.getId());
                ccVrItem.put("CC_DOC_FILE_ID", ccDocFileId);
                panoLst.add(ccVrItem);
                fileIdList.add(ccVrAttachmentFlFile.getId());
                fileIdList.add(ccVrAttachmentPreviewFlFile.getId());
            }

            if (!panoLst.isEmpty()) {
                Map<String, Object> ccPano= new HashMap<>();
                ccPano.put("pano-month", JdbcMapUtil.getString(mapPanoMonth,"CC_PANO_RET_MONTH"));
                ccPano.put("pano-list", panoLst);
                vrLst.add(ccPano);
            }
        }

        Map<String,Object> result = new HashMap<>();
        result.put("data",vrLst);
        result.put("fileIdList", fileIdList);
//        result.put("filePreviewIdList", filePreviewIdLst);
        ExtJarHelper.setReturnValue(result);
//        String sql = "SELECT `NAME`, VR_DATE,  CONCAT( YEAR ( VR_DATE ), '年', MONTH ( VR_DATE ), '月' ) CC_YEAR_MONTH, CC_VR_ATTACHMENT_PREVIEW, CC_VR_ATTACHMENT FROM CC_VR WHERE (@P_CC_PRJ_IDS IS NULL OR @P_CC_PRJ_IDS LIKE CONCAT('%', CC_PRJ_ID, '%')) ORDER BY VR_DATE DESC";
//        List<Map<String, Object>> ccVrs = ExtJarHelper.getMyJdbcTemplate().queryForList(sql);
//        List<Map<String, String>> vrLst = new ArrayList<>();

//        ExtJarHelper.setReturnValue(vrLst);
//        ExtJarHelper.setReturnValue();

//        年月
//        时间（年月日）
//        缩略图地址
//        全景图地址
//        全景图名称

//        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
//        //全景id
//        String ccDocFileId = JdbcMapUtil.getString(inputMap, "ccDocFileId");
//        //项目id
//        CcDocFile ccDocFile = CcDocFile.selectById(ccDocFileId);
//        String ccPrjId = ccDocFile.getCcPrjId();
//        //坐标
//        String position = JdbcMapUtil.getString(inputMap, "position");
//        //全景文件id
//        String ccAttachment = JdbcMapUtil.getString(inputMap, "ccAttachment");
//        //热点名
//        String name = JdbcMapUtil.getString(inputMap, "name");
//        CcDocFileHotPoint ccDocFileHotPoint = CcDocFileHotPoint.newData();
//        ccDocFileHotPoint.setCcDocFileId(ccDocFileId);
//        ccDocFileHotPoint.setPosition(position);
//        ccDocFileHotPoint.setCcAttachment(ccAttachment);
//        ccDocFileHotPoint.setName(name);
//        ccDocFileHotPoint.setCcPrjId(ccPrjId);
//        ccDocFileHotPoint.insertById();
//        String hotPointId = ccDocFileHotPoint.getId();
//        Map<String, Object> outputMap = new HashMap<>();
//        outputMap.put("hotPointId", hotPointId);
//        ExtJarHelper.setReturnValue(outputMap);
    }
}
