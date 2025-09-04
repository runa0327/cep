package com.cisdi.cisdipreview.controller;

import cn.hutool.core.util.IdUtil;
import com.cisdi.cisdipreview.interceptor.RequestHeaderContext;
import com.cisdi.cisdipreview.model.PanoData;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.JsonUtil;
import com.qygly.shared.util.SharedUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
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

@RestController
@RequestMapping(value = "/share")
public class ShareController {

    private static final Logger logger = LoggerFactory.getLogger(ShareController.class);

    @Resource
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/data")
    public Map<String, Object> getPanoramaData(@RequestBody PanoData panoData) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> dataList = new ArrayList<>();
        result.put("data", dataList);

        try {
            boolean allFile = panoData.isAllFile();


            String dataType = panoData.getDataType();
            String code = panoData.getOrgId();
            String shareId = panoData.getShareId();

            // 根据 orgId 查询数据库名
            String orgSql = "SELECT t.SERVICE_NAME FROM main.AD_ORG t WHERE t.CODE = ?";
            Map<String, Object> orgMap;
            try {
                orgMap = jdbcTemplate.queryForMap(orgSql, code);
            } catch (EmptyResultDataAccessException e) {
                logger.warn("未找到对应 orgId 的组织，orgId: {}", code);
                return Collections.singletonMap("data", new ArrayList<>());
            }

            String orgName = JdbcMapUtil.getString(orgMap, "SERVICE_NAME");
            if (SharedUtil.isEmpty(orgName)) {
                logger.warn("SERVICE_NAME 为空，orgId: {}", code);
                return Collections.singletonMap("data", new ArrayList<>());
            }

            // 获取分享内容的所属项目
            String shareSql = "SELECT t.SHARE_CONTEXT_DATA FROM " + sanitizeSchema(orgName) + ".AD_SHARE t WHERE t.id = ?";
            Map<String, Object> shareMap;
            try {
                shareMap = jdbcTemplate.queryForMap(shareSql, shareId);
            } catch (EmptyResultDataAccessException e) {
                logger.warn("未找到对应 shareId 的分享记录，shareId: {}", shareId);
                return Collections.singletonMap("data", new ArrayList<>());
            }

            String shareContextData = JdbcMapUtil.getString(shareMap, "SHARE_CONTEXT_DATA");
            if (SharedUtil.isEmpty(shareContextData)) {
                logger.warn("SHARE_CONTEXT_DATA 为空，shareId: {}", shareId);
                return Collections.singletonMap("data", new ArrayList<>());
            }

            Map<String, Object> shareMapData = JsonUtil.convertJsonToMap(shareContextData);
            String ccPrjId = JdbcMapUtil.getString(shareMapData, "prjId");
            List<String> selectedFolders = new ArrayList<>();
            Object foldersObj = shareMapData.get("selectedFolders");
            if (foldersObj instanceof List) {
                for (Object item : (List<?>) foldersObj) {
                    if (item instanceof String) {
                        selectedFolders.add((String) item);
                    }
                }
            }

            // 处理 prjId 为空的情况
            if (SharedUtil.isEmpty(ccPrjId)) {
                ccPrjId = RequestHeaderContext.getInstance().getpCcPrjIds();
            }

            // 拆分 ccPrjId 为列表
            List<String> ccPrjIds = parsePrjIds(ccPrjId);


            if (ccPrjIds.isEmpty()) {
                return result;
            }

            // 根据项目 id 查询全景
            String placeholders = String.join(", ", Collections.nCopies(ccPrjIds.size(), "?"));
            StringBuilder panoSql = new StringBuilder("SELECT * FROM ")
                    .append(sanitizeSchema(orgName))
                    .append(".CC_DOC_FILE t WHERE t.cc_prj_id IN (")
                    .append(placeholders)
                    .append(") AND t.CC_DOC_FILE_TYPE_ID = ?");

            // 动态构建参数列表
            List<Object> params = new ArrayList<>(ccPrjIds);
            params.add(dataType);

            // 添加文件夹过滤条件
            if (!allFile && !selectedFolders.isEmpty()) {
                logger.info("文件夹过滤！");
                String folderPlaceholders = String.join(", ",
                        Collections.nCopies(selectedFolders.size(), "?"));

                panoSql.append(" AND t.CC_DOC_DIR_ID IN (")
                        .append(folderPlaceholders)
                        .append(")");

                params.addAll(selectedFolders);
            }

            List<Map<String, Object>> panoList = jdbcTemplate.queryForList(
                    panoSql.toString(), params.toArray()
            );

            if (panoList.isEmpty()) {
                return result;
            }

            // 定义日期格式化器
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年M月");
            DateTimeFormatter parseFormatter = DateTimeFormatter.ofPattern("yyyy年M月");

            // 按月份分组
            Map<String, List<Map<String, Object>>> monthMap = new HashMap<>();
            for (Map<String, Object> map : panoList) {
                String ccDocFileId = JdbcMapUtil.getString(map, "ID");
                LocalDate vrDate = JdbcMapUtil.getLocalDate(map, "CC_DOC_DATE");

                if (vrDate == null) {
                    logger.warn("CC_DOC_DATE 为 null，ID: {}", ccDocFileId);
                    continue; // 跳过日期为空的记录
                }

                String ccYearMonth = vrDate.format(formatter); // 格式化日期

                String id = IdUtil.getSnowflakeNextIdStr();
                String previewId = IdUtil.getSnowflakeNextIdStr();
                LocalDateTime now = LocalDateTime.now();
                String ccVrAttachmentId = JdbcMapUtil.getString(map, "CC_ATTACHMENT");
                String ccVrAttachmentPreviewId = JdbcMapUtil.getString(map, "CC_PREVIEW_ATTACHMENT");

                // 如果不存在则插入 FL_FILE_SHARE
                insertFileShareIfNotExists(orgName, shareId, ccVrAttachmentId, id, now);
                insertFileShareIfNotExists(orgName, shareId, ccVrAttachmentPreviewId, previewId, now);

                // 根据附件ID查询文件URL
                String ccVrAttachment = getFileInlineUrl(orgName, ccVrAttachmentId);
                String ccVrAttachmentPreview = getFileInlineUrl(orgName, ccVrAttachmentPreviewId);

                String name = JdbcMapUtil.getString(map, "NAME");

                //判断字符串是否为json字符串
                if (name.startsWith("{")) {
                    Map<String, String> nameJson = JsonUtil.fromJson(name, Map.class);
                    name = nameJson.get("ZH_CN");//默认都显示中文
                }

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

                // 按月份分组
                monthMap.computeIfAbsent(ccYearMonth, k -> new ArrayList<>()).add(panoItem);
            }

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

                // 对 pano-list 按 VR_DATE 降序排序
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

        } catch (Exception e) {
            logger.error("处理全景数据失败: {}", e.getMessage(), e);
            return Collections.singletonMap("error", "服务器内部错误");
        }
    }

    /**
     * 将 prjId 字符串解析为项目 ID 列表。
     *
     * @param ccPrjId 以逗号分隔的项目 ID 字符串。
     * @return 去除空格后的项目 ID 列表。
     */
    private List<String> parsePrjIds(String ccPrjId) {
        if (SharedUtil.isEmpty(ccPrjId)) {
            return new ArrayList<>();
        }
        return Arrays.stream(ccPrjId.split(","))
                .map(String::trim)
                .filter(id -> !id.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * 如果不存在，则插入文件分享记录。
     *
     * @param orgName   组织模式名称。
     * @param shareId   分享 ID。
     * @param flFileId  文件 ID。
     * @param id        要插入的记录 ID。
     * @param timestamp 当前时间戳。
     */
    private void insertFileShareIfNotExists(String orgName, String shareId, String flFileId, String id, LocalDateTime timestamp) {
        if (SharedUtil.isEmpty(flFileId)) {
            return; // 无需插入
        }

        String flShareSql = "SELECT COUNT(*) FROM " + sanitizeSchema(orgName) + ".fl_file_share fs WHERE fs.AD_SHARE_ID = ? AND fs.fl_file_id = ?";
        Integer count = jdbcTemplate.queryForObject(flShareSql, Integer.class, shareId, flFileId);

        if (count != null && count == 0) {
            String insertSql = "INSERT INTO " + sanitizeSchema(orgName) + ".FL_FILE_SHARE (ID, VER, TS, CRT_DT, LAST_MODI_DT, STATUS, FL_FILE_ID, AD_SHARE_ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(insertSql, id, 1, timestamp, timestamp, timestamp, "AP", flFileId, shareId);
            logger.info("插入新的 FL_FILE_SHARE 记录，ID: {}", id);
        }
    }

    /**
     * 获取指定文件 ID 的 FILE_INLINE_URL。
     *
     * @param orgName 组织模式名称。
     * @param fileId  文件 ID。
     * @return FILE_INLINE_URL 或 null（如果未找到）。
     */
    private String getFileInlineUrl(String orgName, String fileId) {
        if (SharedUtil.isEmpty(fileId)) {
            return null;
        }

        String fileSql = "SELECT f.FILE_INLINE_URL FROM " + sanitizeSchema(orgName) + ".fl_file f WHERE f.id = ?";
        try {
            Map<String, Object> fileMap = jdbcTemplate.queryForMap(fileSql, fileId);
            return JdbcMapUtil.getString(fileMap, "FILE_INLINE_URL");
        } catch (EmptyResultDataAccessException e) {
            logger.warn("未找到对应 fileId 的文件，fileId: {}", fileId);
            return null;
        }
    }

    /**
     * 清理模式名称以防止 SQL 注入。
     * 根据您的应用需求实现此方法。
     *
     * @param schema 需要清理的模式名称。
     * @return 清理后的模式名称。
     */
    private String sanitizeSchema(String schema) {
        // 实现模式名称的清理逻辑
        // 例如，验证是否在允许的模式名称列表中
        // 此处假设模式名称是安全的，仅作为占位符
        return schema;
    }
}
