package com.cisdi.cisdipreview.controller;

import cn.hutool.core.util.IdUtil;
import com.cisdi.cisdipreview.interceptor.RequestHeaderContext;
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
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        // orgId查询数据库名
        String orgSql = "select t.SERVICE_NAME from main.AD_ORG t where t.CODE = ?";
        Map<String, Object> orgMap = jdbcTemplate.queryForMap(orgSql, code);
        String orgName = JdbcMapUtil.getString(orgMap, "SERVICE_NAME");

        // 获取分享内容的所属项目
        String shareSql = "select t.SHARE_CONTEXT_DATA from " + orgName + ".AD_SHARE t where t.id = ?";
        Map<String, Object> shareMap = jdbcTemplate.queryForMap(shareSql, shareId);
        String shareContextData = JdbcMapUtil.getString(shareMap, "SHARE_CONTEXT_DATA");

        Map<String, Object> sharemap = JsonUtil.convertJsonToMap(shareContextData);
        String ccPrjId = JdbcMapUtil.getString(sharemap, "prjId");

        // 处理 prjId 为空的情况
        if (SharedUtil.isEmpty(ccPrjId)) {
            ccPrjId = RequestHeaderContext.getInstance().getpCcPrjIds();
        }

        // 拆分 ccPrjId 为列表
        List<String> ccPrjIds = Arrays.stream(ccPrjId.split(","))
                .map(String::trim)
                .filter(id -> !id.isEmpty())
                .collect(Collectors.toList());

        if (ccPrjIds.isEmpty()) {
            // 如果没有有效的项目 ID，返回空数据或根据需求处理
            return Collections.singletonMap("data", new ArrayList<>());
        }

        // 根据项目 id 查询全景
        String placeholders = ccPrjIds.stream()
                .map(id -> "?")
                .collect(Collectors.joining(", "));
        String panoSql = "select * from " + orgName + ".CC_DOC_FILE t where t.cc_prj_id in (" + placeholders + ") and t.CC_DOC_FILE_TYPE_ID = ?";

        // 构建参数数组：项目 ID 列表 + dataType
        Object[] params = Stream.concat(ccPrjIds.stream(), Stream.of(dataType))
                .toArray();

        List<Map<String, Object>> panoList = jdbcTemplate.queryForList(panoSql, params);

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

            String id = IdUtil.getSnowflakeNextIdStr();
            String previewId = IdUtil.getSnowflakeNextIdStr();
            LocalDateTime now = LocalDateTime.now();
            String ccVrAttachmentId = JdbcMapUtil.getString(map, "CC_ATTACHMENT");
            String ccVrAttachmentPreviewId = JdbcMapUtil.getString(map, "CC_PREVIEW_ATTACHMENT");

            String flShareSql = "select * from " + orgName + ".fl_file_share fs where fs.AD_SHARE_ID = ? and fs.fl_file_id = ?";
            List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(flShareSql, shareId, ccVrAttachmentId);
            List<Map<String, Object>> queryForList1 = jdbcTemplate.queryForList(flShareSql, shareId, ccVrAttachmentPreviewId);

            String insertSql = "insert into " + orgName + ".FL_FILE_SHARE (ID,VER,TS,CRT_DT,LAST_MODI_DT,STATUS,FL_FILE_ID, AD_SHARE_ID) values (?,?,?,?,?,?,?,?)";

            if (SharedUtil.isEmpty(queryForList)) {
                jdbcTemplate.update(insertSql, id, 1, now, now, now, "AP", ccVrAttachmentId, shareId);
            }
            if (SharedUtil.isEmpty(queryForList1)) {
                jdbcTemplate.update(insertSql, previewId, 1, now, now, now, "AP", ccVrAttachmentPreviewId, shareId);
            }

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

        // 定义日期格式化器用于解析
        DateTimeFormatter parseFormatter = DateTimeFormatter.ofPattern("yyyy年M月");

        // 获取排序后的月份列表（按年月降序）
        List<String> sortedMonths = monthMap.keySet().stream()
                .sorted((m1, m2) -> {
                    YearMonth ym1 = YearMonth.parse(m1, parseFormatter);
                    YearMonth ym2 = YearMonth.parse(m2, parseFormatter);
                    return ym2.compareTo(ym1); // 降序
                })
                .collect(Collectors.toList());

        for (String month : sortedMonths) {
            List<Map<String, Object>> panoItems = monthMap.get(month);

            // 对pano-list按VR_DATE降序排序
            panoItems.sort((p1, p2) -> {
                LocalDate date1 = LocalDate.parse((String) p1.get("VR_DATE"));
                LocalDate date2 = LocalDate.parse((String) p2.get("VR_DATE"));
                return date2.compareTo(date1); // 降序
            });

            Map<String, Object> monthData = new HashMap<>();
            monthData.put("pano-month", month);
            monthData.put("pano-list", panoItems);
            dataList.add(monthData);
        }

        return result;
    }

}
