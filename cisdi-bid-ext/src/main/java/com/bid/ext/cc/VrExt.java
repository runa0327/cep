package com.bid.ext.cc;

import com.bid.ext.config.FlPathConfig;
import com.bid.ext.model.CcVr;
import com.bid.ext.model.FlFile;
import com.bid.ext.model.FlPath;
import com.bid.ext.utils.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
                    attachmentPreview.setFileInlineUrl(FlPathConfig.FILE_INLINE_URL_FIX + "?fileId=" + fileId);
                    attachmentPreview.setFileAttachmentUrl(FlPathConfig.FILE_ATTACHMENT_URL_FIX + "?fileId=" + fileId);
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

        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String ccPrjId = JdbcMapUtil.getString(inputMap, "ccPrjId");
        String sqlPanoMonth;
        String sqlPanoLst;

        String currentLangId = ExtJarHelper.getLoginInfo().currentLangId;//当前语言
        //国际化去掉中文
//        if (SharedUtil.isEmpty(ccPrjId)) {
//            sqlPanoMonth = "SELECT DISTINCT (DATE_FORMAT( CC_DOC_DATE, '%Y-%m' )) CC_PANO_MONTH, CONCAT( YEAR ( CC_DOC_DATE ), '年', MONTH ( CC_DOC_DATE ), '月' ) CC_PANO_RET_MONTH FROM CC_DOC_FILE WHERE CC_DOC_DATE IS NOT NULL AND (IFNULL(@P_CC_PRJ_IDS, '0') LIKE CONCAT('%', CC_PRJ_ID, '%')) ORDER BY CC_PANO_MONTH DESC";
//            sqlPanoLst = "SELECT `NAME`, ID, CC_DOC_DATE,  CONCAT( YEAR ( CC_DOC_DATE ), '年', MONTH ( CC_DOC_DATE ), '月' ) CC_YEAR_MONTH, CC_ATTACHMENT, CC_PREVIEW_ATTACHMENT FROM CC_DOC_FILE WHERE DATE_FORMAT(CC_DOC_DATE, '%Y-%m') LIKE ? AND (IFNULL(@P_CC_PRJ_IDS, '0') LIKE CONCAT('%', CC_PRJ_ID, '%')) ORDER BY CC_DOC_DATE DESC";
//        } else {
//            sqlPanoMonth = "SELECT DISTINCT (DATE_FORMAT( CC_DOC_DATE, '%Y-%m' )) CC_PANO_MONTH, CONCAT( YEAR ( CC_DOC_DATE ), '年', MONTH ( CC_DOC_DATE ), '月' ) CC_PANO_RET_MONTH FROM CC_DOC_FILE WHERE CC_DOC_DATE IS NOT NULL AND CC_PRJ_ID = ? ORDER BY CC_PANO_MONTH DESC";
//            sqlPanoLst = "SELECT `NAME`, ID, CC_DOC_DATE,  CONCAT( YEAR ( CC_DOC_DATE ), '年', MONTH ( CC_DOC_DATE ), '月' ) CC_YEAR_MONTH, CC_ATTACHMENT, CC_PREVIEW_ATTACHMENT FROM CC_DOC_FILE WHERE DATE_FORMAT(CC_DOC_DATE, '%Y-%m') LIKE ? AND CC_PRJ_ID = ? ORDER BY CC_DOC_DATE DESC";
//        }
        if (SharedUtil.isEmpty(ccPrjId)) {
            // 去掉中文，改成纯数字格式
            sqlPanoMonth = "SELECT DISTINCT DATE_FORMAT(CC_DOC_DATE, '%Y-%m') AS CC_PANO_MONTH, "
                    + "CONCAT(YEAR(CC_DOC_DATE), '-', LPAD(MONTH(CC_DOC_DATE), 2, '0')) AS CC_PANO_RET_MONTH "
                    + "FROM CC_DOC_FILE "
                    + "WHERE CC_DOC_DATE IS NOT NULL "
                    + "  AND (IFNULL(@P_CC_PRJ_IDS, '0') LIKE CONCAT('%', CC_PRJ_ID, '%')) "
                    + "ORDER BY CC_PANO_MONTH DESC";

            sqlPanoLst = "SELECT `NAME`, ID, CC_DOC_DATE, "
                    + "CONCAT(YEAR(CC_DOC_DATE), '-', LPAD(MONTH(CC_DOC_DATE), 2, '0')) AS CC_YEAR_MONTH, "
                    + "CC_ATTACHMENT, CC_PREVIEW_ATTACHMENT "
                    + "FROM CC_DOC_FILE "
                    + "WHERE DATE_FORMAT(CC_DOC_DATE, '%Y-%m') LIKE ? "
                    + "  AND (IFNULL(@P_CC_PRJ_IDS, '0') LIKE CONCAT('%', CC_PRJ_ID, '%')) "
                    + "ORDER BY CC_DOC_DATE DESC";
        } else {
            // 同理，去掉中文
            sqlPanoMonth = "SELECT DISTINCT DATE_FORMAT(CC_DOC_DATE, '%Y-%m') AS CC_PANO_MONTH, "
                    + "CONCAT(YEAR(CC_DOC_DATE), '-', LPAD(MONTH(CC_DOC_DATE), 2, '0')) AS CC_PANO_RET_MONTH "
                    + "FROM CC_DOC_FILE "
                    + "WHERE CC_DOC_DATE IS NOT NULL AND CC_PRJ_ID = ? "
                    + "ORDER BY CC_PANO_MONTH DESC";

            sqlPanoLst = "SELECT `NAME`, ID, CC_DOC_DATE, "
                    + "CONCAT(YEAR(CC_DOC_DATE), '-', LPAD(MONTH(CC_DOC_DATE), 2, '0')) AS CC_YEAR_MONTH, "
                    + "CC_ATTACHMENT, CC_PREVIEW_ATTACHMENT "
                    + "FROM CC_DOC_FILE "
                    + "WHERE DATE_FORMAT(CC_DOC_DATE, '%Y-%m') LIKE ? "
                    + "  AND CC_PRJ_ID = ? "
                    + "ORDER BY CC_DOC_DATE DESC";
        }

        List<Map<String, Object>> ccPanoMonths;
        if (SharedUtil.isEmpty(ccPrjId)) {
            ccPanoMonths = myJdbcTemplate.queryForList(sqlPanoMonth);
        } else {
            ccPanoMonths = myJdbcTemplate.queryForList(sqlPanoMonth, ccPrjId);
        }

        List<Map<String, Object>> vrLst = new ArrayList<>();
        List<String> fileIdList = new ArrayList<>();

        for (Map<String, Object> mapPanoMonth : ccPanoMonths) {
            String panoMonth = JdbcMapUtil.getString(mapPanoMonth, "CC_PANO_MONTH");
            List<Map<String, Object>> ccVrs;
            if (SharedUtil.isEmpty(ccPrjId)) {
                ccVrs = myJdbcTemplate.queryForList(sqlPanoLst, panoMonth);
            } else {
                ccVrs = myJdbcTemplate.queryForList(sqlPanoLst, panoMonth, ccPrjId);
            }
            List<Map<String, String>> panoLst = new ArrayList<>();

            for (Map<String, Object> ccVr : ccVrs) {
                String name = JdbcMapUtil.getString(ccVr, "NAME");
                //解析json字符串
                //判断字符串是否为json字符串
                if (name.startsWith("{")) {
                    Map<String, String> nameJson = JsonUtil.fromJson(name, Map.class);
                    name = nameJson.get(currentLangId);
                }

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

                if (!checkFileExists(ccVrAttachmentFlFile.getPhysicalLocation()) || !checkFileExists(ccVrAttachmentPreviewFlFile.getPhysicalLocation())) {
                    continue;
                }

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
                Map<String, Object> ccPano = new HashMap<>();
                ccPano.put("pano-month", JdbcMapUtil.getString(mapPanoMonth, "CC_PANO_RET_MONTH"));
                ccPano.put("pano-list", panoLst);
                vrLst.add(ccPano);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("data", vrLst);
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

    /**
     * API:获取文件夹VR全景
     */
    public void getSelectedVr() {
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String ccPrjId = JdbcMapUtil.getString(inputMap, "ccPrjId");
        String currentLangId = ExtJarHelper.getLoginInfo().currentLangId;//当前语言

        // 新增：获取 selectedFolders 参数（字符串列表）
        List<String> selectedFolders = null;
        Object foldersObj = inputMap.get("selectedFolders");
        if (foldersObj instanceof List) {
            selectedFolders = new ArrayList<>();
            for (Object item : (List<?>) foldersObj) {
                if (item instanceof String) {
                    selectedFolders.add((String) item);
                }
            }
        }

        // 判断是否有选中的文件夹
        boolean hasSelectedFolders = selectedFolders != null && !selectedFolders.isEmpty();

        String sqlPanoMonth;
        String sqlPanoLst;
        if (SharedUtil.isEmpty(ccPrjId)) {
            sqlPanoMonth = "SELECT DISTINCT DATE_FORMAT(CC_DOC_DATE, '%Y-%m') AS CC_PANO_MONTH, "
                    + "CONCAT(YEAR(CC_DOC_DATE), '-', LPAD(MONTH(CC_DOC_DATE), 2, '0')) AS CC_PANO_RET_MONTH "
                    + "FROM CC_DOC_FILE "
                    + "WHERE CC_DOC_DATE IS NOT NULL "
                    + "  AND (IFNULL(@P_CC_PRJ_IDS, '0') LIKE CONCAT('%', CC_PRJ_ID, '%')) "
                    // 新增文件夹过滤条件
                    + (hasSelectedFolders ? "  AND cc_doc_dir_id IN (" + buildInClause(selectedFolders) + ") " : "")
                    + "ORDER BY CC_PANO_MONTH DESC";

            sqlPanoLst = "SELECT `NAME`, ID, CC_DOC_DATE, "
                    + "CONCAT(YEAR(CC_DOC_DATE), '-', LPAD(MONTH(CC_DOC_DATE), 2, '0')) AS CC_YEAR_MONTH, "
                    + "CC_ATTACHMENT, CC_PREVIEW_ATTACHMENT "
                    + "FROM CC_DOC_FILE "
                    + "WHERE DATE_FORMAT(CC_DOC_DATE, '%Y-%m') LIKE ? "
                    + "  AND (IFNULL(@P_CC_PRJ_IDS, '0') LIKE CONCAT('%', CC_PRJ_ID, '%')) "
                    // 新增文件夹过滤条件
                    + (hasSelectedFolders ? "  AND cc_doc_dir_id IN (" + buildInClause(selectedFolders) + ") " : "")
                    + "ORDER BY CC_DOC_DATE DESC";
        } else {
            sqlPanoMonth = "SELECT DISTINCT DATE_FORMAT(CC_DOC_DATE, '%Y-%m') AS CC_PANO_MONTH, "
                    + "CONCAT(YEAR(CC_DOC_DATE), '-', LPAD(MONTH(CC_DOC_DATE), 2, '0')) AS CC_PANO_RET_MONTH "
                    + "FROM CC_DOC_FILE "
                    + "WHERE CC_DOC_DATE IS NOT NULL AND CC_PRJ_ID = ? "
                    // 新增文件夹过滤条件
                    + (hasSelectedFolders ? "  AND cc_doc_dir_id IN (" + buildInClause(selectedFolders) + ") " : "")
                    + "ORDER BY CC_PANO_MONTH DESC";

            sqlPanoLst = "SELECT `NAME`, ID, CC_DOC_DATE, "
                    + "CONCAT(YEAR(CC_DOC_DATE), '-', LPAD(MONTH(CC_DOC_DATE), 2, '0')) AS CC_YEAR_MONTH, "
                    + "CC_ATTACHMENT, CC_PREVIEW_ATTACHMENT "
                    + "FROM CC_DOC_FILE "
                    + "WHERE DATE_FORMAT(CC_DOC_DATE, '%Y-%m') LIKE ? "
                    + "  AND CC_PRJ_ID = ? "
                    // 新增文件夹过滤条件
                    + (hasSelectedFolders ? "  AND cc_doc_dir_id IN (" + buildInClause(selectedFolders) + ") " : "")
                    + "ORDER BY CC_DOC_DATE DESC";
        }

        List<Map<String, Object>> ccPanoMonths;
        if (SharedUtil.isEmpty(ccPrjId)) {
            // ================ 修改2：处理带文件夹参数的情况 ================
            if (hasSelectedFolders) {
                // 如果ccPrjId为空但有文件夹参数
                ccPanoMonths = myJdbcTemplate.queryForList(sqlPanoMonth);
            } else {
                ccPanoMonths = myJdbcTemplate.queryForList(sqlPanoMonth);
            }
        } else {
            // ================ 修改3：处理带文件夹参数的情况 ================
            if (hasSelectedFolders) {
                // 同时有ccPrjId和文件夹参数
                ccPanoMonths = myJdbcTemplate.queryForList(sqlPanoMonth, ccPrjId);
            } else {
                ccPanoMonths = myJdbcTemplate.queryForList(sqlPanoMonth, ccPrjId);
            }
        }

        List<Map<String, Object>> vrLst = new ArrayList<>();
        List<String> fileIdList = new ArrayList<>();

        for (Map<String, Object> mapPanoMonth : ccPanoMonths) {
            String panoMonth = JdbcMapUtil.getString(mapPanoMonth, "CC_PANO_MONTH");
            List<Map<String, Object>> ccVrs;

            // ================ 修改4：处理带文件夹参数的查询 ================
            if (SharedUtil.isEmpty(ccPrjId)) {
                if (hasSelectedFolders) {
                    ccVrs = myJdbcTemplate.queryForList(sqlPanoLst, panoMonth);
                } else {
                    ccVrs = myJdbcTemplate.queryForList(sqlPanoLst, panoMonth);
                }
            } else {
                if (hasSelectedFolders) {
                    ccVrs = myJdbcTemplate.queryForList(sqlPanoLst, panoMonth, ccPrjId);
                } else {
                    ccVrs = myJdbcTemplate.queryForList(sqlPanoLst, panoMonth, ccPrjId);
                }
            }
            List<Map<String, String>> panoLst = new ArrayList<>();

            for (Map<String, Object> ccVr : ccVrs) {
                String name = JdbcMapUtil.getString(ccVr, "NAME");
                //解析json字符串
                //判断字符串是否为json字符串
                if (name.startsWith("{")) {
                    Map<String, String> nameJson = JsonUtil.fromJson(name, Map.class);
                    name = nameJson.get(currentLangId);
                }
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

                if (!checkFileExists(ccVrAttachmentFlFile.getPhysicalLocation()) || !checkFileExists(ccVrAttachmentPreviewFlFile.getPhysicalLocation())) {
                    continue;
                }

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
                Map<String, Object> ccPano = new HashMap<>();
                ccPano.put("pano-month", JdbcMapUtil.getString(mapPanoMonth, "CC_PANO_RET_MONTH"));
                ccPano.put("pano-list", panoLst);
                vrLst.add(ccPano);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("data", vrLst);
        result.put("fileIdList", fileIdList);
        ExtJarHelper.setReturnValue(result);
    }


    private String buildInClause(List<String> values) {
        if (values == null || values.isEmpty()) {
            return "";
        }
        return "'" + String.join("','", values) + "'";
    }
}
