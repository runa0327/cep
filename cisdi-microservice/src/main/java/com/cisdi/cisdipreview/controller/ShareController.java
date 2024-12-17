package com.cisdi.cisdipreview.controller;

import com.cisdi.cisdipreview.model.PanoData;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.JsonUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/share")
public class ShareController {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/data")
    public PanoData getPanoramaData(String shareId, String dataType, String orgId, Boolean isStatic) {
        // 获取分享内容的所属项目
        String shareSql = "select t.SHARE_CONTEXT_DATA from AD_SHARE t where t.id = ?";
        Map<String, Object> shareMap = jdbcTemplate.queryForMap(shareSql, shareId);
        String shareContextData = JdbcMapUtil.getString(shareMap, "SHARE_CONTEXT_DATA");
        Map<String, Object> sharemap = JsonUtil.convertJsonToMap(shareContextData);
        String ccPrjId = JdbcMapUtil.getString(sharemap, "CC_PRJ_ID");

        // 根据项目id查询全景
        String panoSql = "select * from CC_DOC_FILE t where t.cc_prj_id = ?";
        List<Map<String, Object>> panoList = jdbcTemplate.queryForList(panoSql, ccPrjId);

        PanoData panoData = new PanoData();
        // 定义日期格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年M月");

        for (Map<String, Object> map : panoList) {
            String ccDocFileId = JdbcMapUtil.getString(map, "ID");
            LocalDate vrDate = JdbcMapUtil.getLocalDate(map, "CC_DOC_DATE");
            String ccYearMonth = vrDate.format(formatter); // 格式化日期

            String ccVrAttachmentId = JdbcMapUtil.getString(map, "CC_ATTACHMENT");
            String ccVrAttachmentPreviewId = JdbcMapUtil.getString(map, "CC_PREVIEW_ATTACHMENT");

            // 根据附件ID查询文件URL
            String sql = "select * from fl_file f where f.id = ?";
            Map<String, Object> fileMap = jdbcTemplate.queryForMap(sql, ccVrAttachmentId);
            String ccVrAttachment = JdbcMapUtil.getString(fileMap, "FILE_INLINE_URL");

            Map<String, Object> previewFileMap = jdbcTemplate.queryForMap(sql, ccVrAttachmentPreviewId);
            String ccVrAttachmentPreview = JdbcMapUtil.getString(previewFileMap, "FILE_INLINE_URL");

            String name = JdbcMapUtil.getString(map, "NAME");

            // 创建 PanoListItem 并设置数据
            PanoData.PanoListItem panoListItem = new PanoData.PanoListItem();
            panoListItem.setCcVrAttachmentId(ccVrAttachmentId);
            panoListItem.setCcVrAttachmentPreview(ccVrAttachmentPreview);
            panoListItem.setCcDocFileId(ccDocFileId);
            panoListItem.setCcVrAttachment(ccVrAttachment);
            panoListItem.setCcYearMonth(ccYearMonth);
            panoListItem.setCcVrAttachmentPreviewId(ccVrAttachmentPreviewId);
            panoListItem.setVrDate(vrDate.toString());
            panoListItem.setName(name);

            // 检查 PanoData 中是否已经存在该月，如果没有则创建新的 PanoMonth
            PanoData.PanoMonth panoMonth = panoData.getData().stream()
                    .filter(pm -> pm.getPanoMonth().equals(ccYearMonth))
                    .findFirst()
                    .orElseGet(() -> {
                        PanoData.PanoMonth newMonth = new PanoData.PanoMonth();
                        newMonth.setPanoMonth(ccYearMonth);
                        newMonth.setPanoList(new ArrayList<>());
                        panoData.getData().add(newMonth);
                        return newMonth;
                    });

            // 将该全景项添加到对应的月份列表中
            panoMonth.getPanoList().add(panoListItem);
        }

        return panoData;
    }
}
