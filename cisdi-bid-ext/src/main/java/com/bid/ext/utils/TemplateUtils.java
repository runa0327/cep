package com.bid.ext.utils;

import cn.hutool.core.util.IdUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateUtils {

    /**
     * 通用模板套用方法
     *
     * @param templateId      模板ID
     * @param tableName       表名
     * @param idFieldName     ID字段名
     * @param pidFieldName    父ID字段名
     * @param includeRootNode 是否包含根节点
     */
    public static List<Map<String, Object>> applyTemplate(String templateId, String tableName, String idFieldName, String pidFieldName, boolean includeRootNode) {
        // 获取模板结构
        List<Map<String, Object>> templateStruct = getTemplateStruct(templateId, tableName, idFieldName, pidFieldName, includeRootNode);
        List<Map<String, Object>> newStruct = replaceIdsAndInsert(templateStruct, idFieldName, pidFieldName);

        // 序号
        BigDecimal seqNo = BigDecimal.ZERO;
        List<Map<String, Object>> insertedNodes = new ArrayList<>(); // 存储插入的节点

        // 对于每一个模板结构节点，插入到目标表中
        for (Map<String, Object> node : newStruct) {
            Map<String, Object> insertedNode = insertNode(node, tableName, seqNo, idFieldName, pidFieldName);
            insertedNodes.add(insertedNode); // 将插入的节点添加到结果列表中
            seqNo = seqNo.add(BigDecimal.ONE);
        }

        return insertedNodes; // 返回插入后的结果列表
    }


    /**
     * 获取模板结构
     *
     * @param templateId      模板ID
     * @param tableName       表名
     * @param idFieldName     ID字段名
     * @param pidFieldName    父ID字段名
     * @param includeRootNode 是否包含根节点
     * @return 模板结构的节点列表
     */
    private static List<Map<String, Object>> getTemplateStruct(String templateId, String tableName, String idFieldName, String pidFieldName, boolean includeRootNode) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String sql;
        List<Map<String, Object>> nodes;

        if (includeRootNode) {
            // 包含根节点
            sql = String.format("WITH RECURSIVE Subtree AS (" +
                    "SELECT *, 1 AS depth FROM %s WHERE %s = ? " +
                    "UNION ALL " +
                    "SELECT n.*, s.depth + 1 FROM %s n JOIN Subtree s ON n.%s = s.%s WHERE s.depth < 100) " +
                    "SELECT * FROM Subtree", tableName, idFieldName, tableName, pidFieldName, idFieldName);

            nodes = myJdbcTemplate.queryForList(sql, templateId);
        } else {
            // 不包含根节点，但需要将直接子节点的父ID设为null
            sql = String.format("WITH RECURSIVE Subtree AS (" +
                    "SELECT * FROM %s WHERE %s = ? " +
                    "UNION ALL " +
                    "SELECT n.* FROM %s n JOIN Subtree s ON n.%s = s.%s) " +
                    "SELECT * FROM Subtree", tableName, pidFieldName, tableName, pidFieldName, idFieldName);
            nodes = myJdbcTemplate.queryForList(sql, templateId);

            // 设置直接子节点的父ID为null
            if (!nodes.isEmpty()) {
                String rootId = nodes.get(0).get(pidFieldName).toString();
                for (Map<String, Object> node : nodes) {
                    if (node.get(pidFieldName).equals(rootId)) {
                        node.put(pidFieldName, null);
                    }
                }
            }
        }

        // 设置 COPY_FROM_ID
        for (Map<String, Object> node : nodes) {
            node.put("COPY_FROM_ID", node.get(idFieldName));
        }

        return nodes;
    }

    /**
     * 替换ID并插入数据
     *
     * @param nodes        节点列表
     * @param idFieldName  ID字段名
     * @param pidFieldName 父ID字段名
     * @return 处理后的节点列表
     */
    private static List<Map<String, Object>> replaceIdsAndInsert(List<Map<String, Object>> nodes, String idFieldName, String pidFieldName) {
        Map<String, String> idMapping = new HashMap<>();

        // 第一步：为每个节点生成新的 ID 并更新映射表
        for (Map<String, Object> node : nodes) {
            String oldId = (String) node.get(idFieldName);
            String newId = IdUtil.getSnowflakeNextIdStr();
            // 新ID与旧ID映射表
            idMapping.put(oldId, newId);
            node.put(idFieldName, newId);
        }

        // 第二步：更新每个节点的父节点 ID
        for (Map<String, Object> node : nodes) {
            String oldParentId = (String) node.get(pidFieldName);
            if (oldParentId != null) {
                String newParentId = idMapping.get(oldParentId);
                if (newParentId != null) {
                    node.put(pidFieldName, newParentId);
                }
            }
        }
        return nodes;
    }

    /**
     * 插入节点
     *
     * @param nodeData     节点数据
     * @param tableName    表名
     * @param seqNo        序号
     * @param idFieldName  ID字段名
     * @param pidFieldName 父ID字段名
     * @return 插入后的节点数据
     */
    private static Map<String, Object> insertNode(Map<String, Object> nodeData, String tableName, BigDecimal seqNo, String idFieldName, String pidFieldName) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String userId = ExtJarHelper.getLoginInfo().userInfo.id;
        LocalDateTime currentTime = LocalDateTime.now();

        // 插入逻辑
        String sql = String.format("INSERT INTO %s (%s, %s, name, IS_TEMPLATE, COPY_FROM_ID, seq_no, VER, TS, CRT_DT, CRT_USER_ID, LAST_MODI_DT, LAST_MODI_USER_ID, STATUS) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", tableName, idFieldName, pidFieldName);

        myJdbcTemplate.update(sql,
                nodeData.get(idFieldName),                 // ID
                nodeData.get(pidFieldName),                // 父ID
                nodeData.get("name"),                      // 名称
                false,                                    // IS_TEMPLATE
                nodeData.get("COPY_FROM_ID"),             // COPY_FROM_ID
                seqNo,                                    // 序号
                1,                                        // VER
                currentTime,                              // TS
                currentTime,                              // CRT_DT
                userId,                                   // CRT_USER_ID
                currentTime,                              // LAST_MODI_DT
                userId,                                   // LAST_MODI_USER_ID
                "AP"                                      // STATUS
        );

        // 返回插入后的节点数据
        return nodeData;
    }


}
