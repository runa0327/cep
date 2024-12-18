package com.cisdi.cisdipreview.controller;

import com.cisdi.cisdipreview.model.PanoData;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.JsonUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/share")
public class ShareController {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/data")
    public Map<String, Object> getPanoramaData(@RequestBody PanoData panoData) {
        String dataType = panoData.getDataType();
        String code = panoData.getOrgId();
        String shareId = panoData.getShareId();
        //orgId查询数据库名
        String orgSql = "select t.SERVICE_NAME from main.AD_ORG t where t.CODE = ?";
        Map<String, Object> orgMap = jdbcTemplate.queryForMap(orgSql, code);
        String orgName = JdbcMapUtil.getString(orgMap, "SERVICE_NAME");
        // 获取分享内容的所属项目
        String shareSql = "select t.SHARE_CONTEXT_DATA from " + orgName + ".AD_SHARE t where t.id = ?";
        Map<String, Object> shareMap = jdbcTemplate.queryForMap(shareSql, shareId);
        String shareContextData = JdbcMapUtil.getString(shareMap, "SHARE_CONTEXT_DATA");

        Map<String, Object> sharemap = JsonUtil.convertJsonToMap(shareContextData);
        String ccPrjId = JdbcMapUtil.getString(sharemap, "prjId");

        // 根据项目id查询全景
        String panoSql = "select * from " + orgName + ".CC_DOC_FILE t where t.cc_prj_id = ? and t.CC_DOC_FILE_TYPE_ID = ?";
        List<Map<String, Object>> panoList = jdbcTemplate.queryForList(panoSql, ccPrjId, dataType);

        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> dataList = new ArrayList<>();
        result.put("data", dataList);

        // 定义日期格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年M月");

        // 按月份分组
        Map<String, List<Map<String, Object>>> monthMap = new HashMap<>();

        for (Map<String, Object> map : panoList) {
            String ccDocFileId = JdbcMapUtil.getString(map, "ID");
            LocalDate vrDate = JdbcMapUtil.getLocalDate(map, "CC_DOC_DATE");

            String ccYearMonth = vrDate.format(formatter); // 格式化日期

            String ccVrAttachmentId = JdbcMapUtil.getString(map, "CC_ATTACHMENT");
            String ccVrAttachmentPreviewId = JdbcMapUtil.getString(map, "CC_PREVIEW_ATTACHMENT");

            String insertSql = "insert into " + orgName + ".FL_FILE_SHARE (FL_FILE_ID, AD_SHARE_ID) values (?, ?)";
            jdbcTemplate.update(insertSql, ccVrAttachmentId, shareId);
            jdbcTemplate.update(insertSql, ccVrAttachmentPreviewId, shareId);

            // 根据附件ID查询文件URL
            String sql = "select * from " + orgName + ".fl_file f where f.id = ?";
            Map<String, Object> fileMap = jdbcTemplate.queryForMap(sql, ccVrAttachmentId);

            String ccVrAttachment = JdbcMapUtil.getString(fileMap, "FILE_INLINE_URL");

            String ccVrAttachmentPreview = null;
            if (!SharedUtil.isEmpty(ccVrAttachmentPreviewId)) {
                Map<String, Object> previewFileMap = jdbcTemplate.queryForMap(sql, ccVrAttachmentPreviewId);
                ccVrAttachmentPreview = JdbcMapUtil.getString(previewFileMap, "FILE_INLINE_URL");
            }

            String name = JdbcMapUtil.getString(map, "NAME");

            // 创建单个全景项
            Map<String, Object> panoItem = new HashMap<>();
            panoItem.put("CC_VR_ATTACHMENT_ID", ccVrAttachmentId);
            panoItem.put("CC_VR_ATTACHMENT_PREVIEW", ccVrAttachmentPreview);
            panoItem.put("CC_DOC_FILE_ID", ccDocFileId);
            panoItem.put("CC_VR_ATTACHMENT", ccVrAttachment);
            panoItem.put("CC_YEAR_MONTH", ccYearMonth);
            panoItem.put("CC_VR_ATTACHMENT_PREVIEW_ID", ccVrAttachmentPreviewId);
            panoItem.put("VR_DATE", vrDate.toString());
            panoItem.put("NAME", name);

            // 按照月份分组
            monthMap.computeIfAbsent(ccYearMonth, k -> new ArrayList<>()).add(panoItem);
        }

        // 将分组数据加入到结果中
        for (Map.Entry<String, List<Map<String, Object>>> entry : monthMap.entrySet()) {
            Map<String, Object> monthData = new HashMap<>();
            monthData.put("pano-month", entry.getKey());
            monthData.put("pano-list", entry.getValue());
            dataList.add(monthData);
        }

        return result;
    }

}
