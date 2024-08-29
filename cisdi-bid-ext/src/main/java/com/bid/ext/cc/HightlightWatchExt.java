package com.bid.ext.cc;

import com.bid.ext.model.CcPrjStructNode;
import com.bid.ext.model.FlFile;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HightlightWatchExt {
    public void getHighlightWatch() {
        String sqlHighlightWatch = "SELECT NAME, CC_PRJ_ID, CC_PRJ_STRUCT_NODE_ID, CC_ATTACHMENTS, HIGHLIGHT_DATE, HIGHLIGHT_WATCH_IMG, HIGHLIGHT_REMARK FROM CC_HIGHLIGHT_WATCH";
        List<Map<String, Object>> highlights = ExtJarHelper.getMyJdbcTemplate().queryForList(sqlHighlightWatch);
        List<Map<String, String>> highlightLst = new ArrayList<>();
        for (Map<String, Object> highlight : highlights) {
            String ccPrjId = JdbcMapUtil.getString(highlight, "CC_PRJ_ID");
            String ccPrjStructNodeId = JdbcMapUtil.getString(highlight, "CC_PRJ_STRUCT_NODE_ID");
            String ccAttachments = JdbcMapUtil.getString(highlight, "CC_ATTACHMENTS");
            String highlightDate = JdbcMapUtil.getString(highlight, "HIGHLIGHT_DATE");
            String highlightWatchImg = JdbcMapUtil.getString(highlight, "HIGHLIGHT_WATCH_IMG");
            String remark = JdbcMapUtil.getString(highlight,"HIGHLIGHT_REMARK");
            String name = JdbcMapUtil.getString(highlight, "NAME");


            CcPrjStructNode ccPrjStructNode = CcPrjStructNode.selectById(ccPrjStructNodeId);

            FlFile previewImg = FlFile.selectById(highlightWatchImg);

            Map<String, String> highlightItem = new HashMap<>();
            highlightItem.put("REMARK", remark==null?"":remark);
            highlightItem.put("HIGHLIGHT_DATE", highlightDate);
            highlightItem.put("HIGHLIGHT_IMG", previewImg.getFileInlineUrl());
//            highlightItem.put("HIGHLIGHT_IMG_ID", previewImg.getId());
            highlightItem.put("NAME", name);
            highlightItem.put("CC_PRJ_STRUCT_NODE", ccPrjStructNode.getName());
            highlightLst.add(highlightItem);
        }

        Map<String,Object> result = new HashMap<>();
        result.put("data", highlightLst);
//        result.put("fileIdList", fileIdList);
//        result.put("filePreviewIdList", filePreviewIdLst);
        ExtJarHelper.setReturnValue(result);
    }
//
//    String sqlPanoMonth = "SELECT DISTINCT (DATE_FORMAT( CC_DOC_DATE, '%Y-%m' )) CC_PANO_MONTH, CONCAT( YEAR ( CC_DOC_DATE ), '年', MONTH ( CC_DOC_DATE ), '月' ) CC_PANO_RET_MONTH FROM CC_DOC_FILE WHERE CC_DOC_DATE IS NOT NULL AND (IFNULL(@P_CC_PRJ_IDS, '0') LIKE CONCAT('%', CC_PRJ_ID, '%')) ORDER BY CC_PANO_MONTH DESC";
//    List<Map<String, Object>> ccPanoMonths = ExtJarHelper.getMyJdbcTemplate().queryForList(sqlPanoMonth);
//
//    List<Map<String, Object>> vrLst = new ArrayList<>();
//    List<String> fileIdList = new ArrayList<>();
//
//        for (Map<String, Object> mapPanoMonth : ccPanoMonths) {
//        String panoMonth = JdbcMapUtil.getString(mapPanoMonth, "CC_PANO_MONTH");
//        String sqlPanoLst = "SELECT `NAME`, ID, CC_DOC_DATE,  CONCAT( YEAR ( CC_DOC_DATE ), '年', MONTH ( CC_DOC_DATE ), '月' ) CC_YEAR_MONTH, CC_ATTACHMENT, CC_PREVIEW_ATTACHMENT FROM CC_DOC_FILE WHERE DATE_FORMAT(CC_DOC_DATE, '%Y-%m') LIKE ? AND (IFNULL(@P_CC_PRJ_IDS, '0') LIKE CONCAT('%', CC_PRJ_ID, '%')) ORDER BY CC_DOC_DATE DESC";
//        List<Map<String, Object>> ccVrs = ExtJarHelper.getMyJdbcTemplate().queryForList(sqlPanoLst, panoMonth);
//        List<Map<String, String>> panoLst = new ArrayList<>();
//
//        for (Map<String, Object> ccVr : ccVrs) {
//            String name = JdbcMapUtil.getString(ccVr, "NAME");
//            String vrDate = JdbcMapUtil.getString(ccVr, "CC_DOC_DATE");
//            String ccVrYearMonth = JdbcMapUtil.getString(ccVr, "CC_YEAR_MONTH");
//            String ccVrAttachment = JdbcMapUtil.getString(ccVr, "CC_ATTACHMENT");
//            String ccVrAttachmentPreview = JdbcMapUtil.getString(ccVr, "CC_PREVIEW_ATTACHMENT");
//            String ccDocFileId = JdbcMapUtil.getString(ccVr, "ID");
//
//            if (null == ccVrAttachment || null == ccVrAttachmentPreview) {
//                continue;
//            }
//
//            FlFile ccVrAttachmentFlFile = FlFile.selectById(ccVrAttachment);
//            FlFile ccVrAttachmentPreviewFlFile = FlFile.selectById(ccVrAttachmentPreview);
//
//            if (!checkFileExists(ccVrAttachmentFlFile.getPhysicalLocation()) || !checkFileExists(ccVrAttachmentPreviewFlFile.getPhysicalLocation())) {
//                continue;
//            }
//
//            Map<String, String> ccVrItem = new HashMap<>();
//            ccVrItem.put("NAME", name);
//            ccVrItem.put("VR_DATE", vrDate);
//            ccVrItem.put("CC_YEAR_MONTH", ccVrYearMonth);
//            ccVrItem.put("CC_VR_ATTACHMENT", ccVrAttachmentFlFile.getFileInlineUrl());
//            ccVrItem.put("CC_VR_ATTACHMENT_ID", ccVrAttachmentFlFile.getId());
//            ccVrItem.put("CC_VR_ATTACHMENT_PREVIEW", ccVrAttachmentPreviewFlFile.getFileInlineUrl());
//            ccVrItem.put("CC_VR_ATTACHMENT_PREVIEW_ID", ccVrAttachmentPreviewFlFile.getId());
//            ccVrItem.put("CC_DOC_FILE_ID", ccDocFileId);
//            panoLst.add(ccVrItem);
//            fileIdList.add(ccVrAttachmentFlFile.getId());
//            fileIdList.add(ccVrAttachmentPreviewFlFile.getId());
//        }
//
//        if (!panoLst.isEmpty()) {
//            Map<String, Object> ccPano= new HashMap<>();
//            ccPano.put("pano-month", JdbcMapUtil.getString(mapPanoMonth,"CC_PANO_RET_MONTH"));
//            ccPano.put("pano-list", panoLst);
//            vrLst.add(ccPano);
//        }
//    }
//
//    Map<String,Object> result = new HashMap<>();
//        result.put("data",vrLst);
//        result.put("fileIdList", fileIdList);
////        result.put("filePreviewIdList", filePreviewIdLst);
//        ExtJarHelper.setReturnValue(result);
}
