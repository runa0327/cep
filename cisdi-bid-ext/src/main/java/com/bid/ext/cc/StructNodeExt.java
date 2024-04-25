package com.bid.ext.cc;

import cn.hutool.core.util.IdUtil;
import com.bid.ext.model.CcPrj;
import com.bid.ext.model.CcPrjStructNode;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class StructNodeExt {


    /**
     * 套用模板
     */
    public void template() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        //获取模板结构
        String pWbsTempateId = varMap.get("P_WBS_TEMPATE_ID").toString();
        Boolean includeRootNode = (Boolean) varMap.get("P_INCLUDE_ROOT_NODE");

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            List<Map<String, Object>> templateStruct = getTemplateStruct(pWbsTempateId, includeRootNode);
            List<Map<String, Object>> list = replaceIdsAndInsert(templateStruct);

            BigDecimal seqNo = BigDecimal.ZERO;
            // 对于每一个模板结构节点，将其作为子节点插入
            for (Map<String, Object> node : templateStruct) {
                insertNode(node, entityRecord, seqNo);
                seqNo = seqNo.add(BigDecimal.ONE);
            }
        }
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 获取模板结构
     *
     * @param pWbsTemplateId
     * @param includeRootNode
     * @return
     */
    private List<Map<String, Object>> getTemplateStruct(String pWbsTemplateId, Boolean includeRootNode) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String sql;
        List<Map<String, Object>> nodes;

        if (includeRootNode) {
            // 包含根节点
            sql = "WITH RECURSIVE Subtree AS (" +
                    "SELECT * FROM cc_prj_struct_node WHERE ID = ? " +
                    "UNION ALL " +
                    "SELECT n.* FROM cc_prj_struct_node n JOIN Subtree s ON n.CC_PRJ_STRUCT_NODE_PID = s.ID) " +
                    "SELECT * FROM Subtree";
            nodes = myJdbcTemplate.queryForList(sql, pWbsTemplateId);
        } else {
            // 不包含根节点，但需要将直接子节点的父ID设为null
            sql = "WITH RECURSIVE Subtree AS (" +
                    "SELECT * FROM cc_prj_struct_node WHERE CC_PRJ_STRUCT_NODE_PID = ? " +
                    "UNION ALL " +
                    "SELECT n.* FROM cc_prj_struct_node n JOIN Subtree s ON n.CC_PRJ_STRUCT_NODE_PID = s.ID) " +
                    "SELECT * FROM Subtree";
            nodes = myJdbcTemplate.queryForList(sql, pWbsTemplateId);

            // 设置直接子节点的父ID为null
            if (!nodes.isEmpty()) {
                String rootId = nodes.get(0).get("CC_PRJ_STRUCT_NODE_PID").toString();
                for (Map<String, Object> node : nodes) {
                    if (node.get("CC_PRJ_STRUCT_NODE_PID").equals(rootId)) {
                        node.put("CC_PRJ_STRUCT_NODE_PID", null);
                    }
                }
            }
        }

        return nodes;
    }


    /**
     * 替换ID
     *
     * @param nodes
     */
    private List<Map<String, Object>> replaceIdsAndInsert(List<Map<String, Object>> nodes) {
        Map<String, String> idMapping = new HashMap<>();

        // 第一步：为每个节点生成新的 ID 并更新映射表
        for (Map<String, Object> node : nodes) {
            String oldId = (String) node.get("ID");
            String newId = IdUtil.getSnowflakeNextIdStr();
            idMapping.put(oldId, newId);
            node.put("ID", newId);
            System.out.println("Node " + oldId + " updated to " + newId);
        }

        // 第二步：更新每个节点的父节点 ID
        for (Map<String, Object> node : nodes) {
            String oldParentId = (String) node.get("CC_PRJ_STRUCT_NODE_PID");
            if (oldParentId != null) {
                String newParentId = idMapping.get(oldParentId);
                if (newParentId != null) {
                    node.put("CC_PRJ_STRUCT_NODE_PID", newParentId);
                }
            }
        }
        return nodes;
    }


    /**
     * 插入节点
     *
     * @param nodeData
     * @param parentRecord
     */
    private void insertNode(Map<String, Object> nodeData, EntityRecord parentRecord, BigDecimal seqNo) {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String ccPrjId = parentRecord.valueMap.get("CC_PRJ_ID").toString();
        String ccPrjWbsTypeId = nodeData.get("CC_PRJ_WBS_TYPE_ID").toString();

        CcPrj ccPrj = CcPrj.selectById(ccPrjId);

        // 安全地从 nodeData 获取 PLAN_FR_DAY_NO 和 PLAN_TO_DAY_NO，避免 NullPointerException
        Integer planFrDayNo = nodeData.get("PLAN_FR_DAY_NO") != null ? Integer.parseInt(nodeData.get("PLAN_FR_DAY_NO").toString()) : 0;
        Integer planToDayNo = nodeData.get("PLAN_TO_DAY_NO") != null ? Integer.parseInt(nodeData.get("PLAN_TO_DAY_NO").toString()) : 0;

        LocalDate fromDate = ccPrj.getFromDate().plusDays(planFrDayNo - 1);
        LocalDate toDate = ccPrj.getFromDate().plusDays(planToDayNo - 1);
        BigDecimal planDays = BigDecimal.valueOf(planToDayNo - planFrDayNo + 1);

        CcPrjStructNode ccPrjStructNode = new CcPrjStructNode();
        ccPrjStructNode.setCrtDt(LocalDateTime.now());
        ccPrjStructNode.setCrtUserId(loginInfo.userInfo.id);
        ccPrjStructNode.setLastModiUserId(loginInfo.userInfo.id);
        ccPrjStructNode.setCcPrjId(ccPrjId);
        ccPrjStructNode.setStatus("AP");

        Integer isMileStoneInt = (Integer) nodeData.get("IS_MILE_STONE");
        boolean isMileStone = isMileStoneInt != null && isMileStoneInt != 0;
        ccPrjStructNode.setIsMileStone(isMileStone);

        Integer isWbsInt = (Integer) nodeData.get("IS_WBS");
        boolean isWbs = isWbsInt != null && isWbsInt != 0;
        ccPrjStructNode.setIsWbs(isWbs);

        ccPrjStructNode.setId(nodeData.get("ID").toString());
        ccPrjStructNode.setName(nodeData.get("NAME").toString());
        ccPrjStructNode.setPlanFr(fromDate);
        ccPrjStructNode.setPlanTo(toDate);
        ccPrjStructNode.setPlanDays(planDays);
        ccPrjStructNode.setSeqNo(seqNo);  // 设置序号
        ccPrjStructNode.setCcPrjWbsTypeId(ccPrjWbsTypeId);


        String parentNodeId = nodeData.get("CC_PRJ_STRUCT_NODE_PID") != null ? nodeData.get("CC_PRJ_STRUCT_NODE_PID").toString() : parentRecord.valueMap.get("ID").toString();
        ccPrjStructNode.setCcPrjStructNodePid(parentNodeId);

        ccPrjStructNode.setIsTemplate(false);
        ccPrjStructNode.insertById();
    }


    /**
     * 获取子节点
     *
     * @param parentId
     * @return
     */
    public List<Map<String, Object>> getChildNodes(String parentId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        // 示例SQL查询，获取所有子节点
        String sql = "SELECT * FROM cc_prj_struct_node WHERE CC_PRJ_STRUCT_NODE_PID = ?";
        return myJdbcTemplate.queryForList(sql, parentId);
    }


    /**
     * 重算时期
     *
     * @param nodeId
     */
    public void updateDateRange(String nodeId, Set<String> updatedNodes) {
        if (updatedNodes.contains(nodeId)) {
            return;  // 避免重复处理节点
        }
        updatedNodes.add(nodeId);

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        //获取子节点
        List<Map<String, Object>> children = getChildNodes(nodeId);
        LocalDate minDate = null, maxDate = null, minActDate = null, maxActDate = null;
        Set<String> childStatuses = new HashSet<>();
        LocalDateTime maxProgTime = null; // 初始化最大进展时间为null
        LocalDate today = LocalDate.now();

        boolean allChildrenHaveActTo = true; // 假设所有子节点都有实际完成时间

        for (Map<String, Object> child : children) {
            updateDateRange((String) child.get("ID"), updatedNodes); // 递归更新子节点

            LocalDate childMinDate = getDateFromSql((java.sql.Date) child.get("PLAN_FR"));
            LocalDate childMaxDate = getDateFromSql((java.sql.Date) child.get("PLAN_TO"));
            LocalDate childMinActDate = getDateFromSql((java.sql.Date) child.get("ACT_FR"));
            LocalDate childMaxActDate = getDateFromSql((java.sql.Date) child.get("ACT_TO"));

            String riskLevel = determineRiskLevel(childMaxDate, childMaxActDate, today);
            myJdbcTemplate.update("UPDATE cc_prj_struct_node SET CC_WBS_RISK_ID = ? WHERE ID = ?", riskLevel, child.get("ID"));

            childStatuses.add((String) child.get("CC_WBS_STATUS_ID"));  // 收集子节点的状态

            // 计划日期处理
            minDate = (childMinDate != null && (minDate == null || childMinDate.isBefore(minDate))) ? childMinDate : minDate;
            maxDate = (childMaxDate != null && (maxDate == null || childMaxDate.isAfter(maxDate))) ? childMaxDate : maxDate;

            // 实际开始日期处理：选择最早的实际开始日期
            minActDate = (childMinActDate != null && (minActDate == null || childMinActDate.isBefore(minActDate))) ? childMinActDate : minActDate;

            // 实际完成日期处理：只有当所有子节点都有实际完成日期时，选择最晚的实际完成日期
            if (childMaxActDate == null) {
                allChildrenHaveActTo = false;
            } else if (allChildrenHaveActTo && (maxActDate == null || childMaxActDate.isAfter(maxActDate))) {
                maxActDate = childMaxActDate;
            }

            LocalDateTime childProgTime = child.get("PROG_TIME") != null ? LocalDateTime.parse(child.get("PROG_TIME").toString()) : null;
            if (childProgTime != null && (maxProgTime == null || childProgTime.isAfter(maxProgTime))) {
                maxProgTime = childProgTime; // 更新最大进展时间
            }
        }

        // 如果任何一个子节点的实际完成日期为空，则父节点的实际完成日期也为空
        if (!allChildrenHaveActTo) {
            maxActDate = null;
        }

        //最大进展时间不为空则更新
        if (maxProgTime != null) {
            myJdbcTemplate.update("UPDATE cc_prj_struct_node SET PROG_TIME = ? WHERE ID = ?", maxProgTime, nodeId);
        }

        // 确定新的状态
        String newStatus = determineNewStatus(childStatuses);
        if (newStatus != null && !newStatus.isEmpty()) {  // 确保状态不是null或空字符串
            myJdbcTemplate.update("UPDATE cc_prj_struct_node SET CC_WBS_STATUS_ID = ? WHERE ID = ?", newStatus, nodeId);
        }


        if (children.isEmpty()) {
            Map<String, Object> nodeData = myJdbcTemplate.queryForMap("SELECT PLAN_FR, PLAN_TO, ACT_FR, ACT_TO FROM cc_prj_struct_node WHERE ID = ?", nodeId);
            minDate = getDateFromSql((java.sql.Date) nodeData.get("PLAN_FR"));
            maxDate = getDateFromSql((java.sql.Date) nodeData.get("PLAN_TO"));
            minActDate = getDateFromSql((java.sql.Date) nodeData.get("ACT_FR"));
            maxActDate = getDateFromSql((java.sql.Date) nodeData.get("ACT_TO"));
        }

        updateNodeDates(nodeId, minDate, maxDate, minActDate, maxActDate, myJdbcTemplate);

        // 更新当前节点的风险等级
        Map<String, Object> nodeData = myJdbcTemplate.queryForMap("SELECT PLAN_TO, ACT_TO FROM cc_prj_struct_node WHERE ID = ?", nodeId);
        LocalDate nodePlanTo = getDateFromSql((java.sql.Date) nodeData.get("PLAN_TO"));
        LocalDate nodeActTo = getDateFromSql((java.sql.Date) nodeData.get("ACT_TO"));
        String nodeRiskLevel = determineRiskLevel(nodePlanTo, nodeActTo, today);
        myJdbcTemplate.update("UPDATE cc_prj_struct_node SET CC_WBS_RISK_ID = ? WHERE ID = ?", nodeRiskLevel, nodeId);

        String parentNodeId = getParentNodeId(nodeId);
        if (parentNodeId != null) {
            updateDateRange(parentNodeId, updatedNodes);
        }
    }

    /**
     * 风险计算
     *
     * @param planTo 计划完成日期
     * @param actTo  实际完成日期
     * @param today  当前日期
     * @return 风险等级
     */
    private String determineRiskLevel(LocalDate planTo, LocalDate actTo, LocalDate today) {
        if (planTo == null) {
            return "NONE";  // 如果没有计划完成日期，风险等级为"NONE"
        }

        boolean isPlanBeforeToday = planTo.isBefore(today);
        boolean isPlanTodayOrLater = !isPlanBeforeToday || planTo.equals(today);

        if (isPlanBeforeToday && (actTo == null || actTo.isAfter(planTo))) {
            return "MISSED";
        } else if (isPlanTodayOrLater && (actTo == null || actTo.isAfter(planTo))) {
            return "DELAYED";
        }

        return "NONE";
    }


    /**
     * 根据子节点状态决定父节点状态
     *
     * @param childStatuses 子节点状态集合
     * @return 父节点的状态
     */
    private String determineNewStatus(Set<String> childStatuses) {
        if (childStatuses == null || childStatuses.isEmpty()) {
            return null; // 如果集合为空，则无需更改
        }

        // 检查是否存在空状态
        boolean hasEmptyStatus = childStatuses.stream().anyMatch(status -> status == null || status.isEmpty());

        // 检查是否有 "DOING"
        if (childStatuses.contains("DOING")) {
            return "DOING";
        }

        // 检查是否所有非空状态都为 "DONE"
        boolean allDone = childStatuses.stream().allMatch(status -> Objects.equals(status, "DONE"));
        if (allDone && !hasEmptyStatus) {
            return "DONE";
        }

        // 检查是否所有非空状态都为 "TODO"
        boolean allTodo = childStatuses.stream().allMatch(status -> Objects.equals(status, "TODO"));
        if (allTodo && !hasEmptyStatus) {
            return "TODO";
        }

        // 如果存在空状态或状态混合，不能确定为 "DONE" 或 "TODO"
        return null;
    }


    /**
     * 日期处理
     *
     * @param sqlDate
     * @return
     */
    private LocalDate getDateFromSql(java.sql.Date sqlDate) {
        return sqlDate != null ? sqlDate.toLocalDate() : null;
    }

    /**
     * 天数计算
     *
     * @param nodeId
     * @param minDate
     * @param maxDate
     * @param minActDate
     * @param maxActDate
     * @param jdbcTemplate
     */
    private void updateNodeDates(String nodeId, LocalDate minDate, LocalDate maxDate, LocalDate minActDate, LocalDate maxActDate, MyJdbcTemplate jdbcTemplate) {
        long planDays = (minDate != null && maxDate != null) ? ChronoUnit.DAYS.between(minDate, maxDate) + 1 : 0;
        long actDays = (minActDate != null && maxActDate != null) ? ChronoUnit.DAYS.between(minActDate, maxActDate) + 1 : 0;

        jdbcTemplate.update("UPDATE cc_prj_struct_node SET PLAN_FR = ?, PLAN_TO = ?, PLAN_DAYS = ?, ACT_FR = ?, ACT_TO = ?, ACT_DAYS = ? WHERE ID = ?",
                minDate != null ? java.sql.Date.valueOf(minDate) : null,
                maxDate != null ? java.sql.Date.valueOf(maxDate) : null, planDays,
                minActDate != null ? java.sql.Date.valueOf(minActDate) : null,
                maxActDate != null ? java.sql.Date.valueOf(maxActDate) : null, actDays, nodeId);
    }


    /**
     * 获取父节点
     *
     * @param nodeId
     * @return
     */
    private String getParentNodeId(String nodeId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        try {
            String sql = "SELECT CC_PRJ_STRUCT_NODE_PID FROM cc_prj_struct_node WHERE ID = ?";
            Map<String, Object> result = myJdbcTemplate.queryForMap(sql, nodeId);
            return result != null ? (String) result.get("CC_PRJ_STRUCT_NODE_PID") : null;
        } catch (EmptyResultDataAccessException e) {
            return null;  // 父节点不存在，当前节点可能是根节点
        }
    }

    /**
     * 重算计划
     */
    public void recalculationPlan() {
        InvokeActResult invokeActResult = new InvokeActResult();
        Set<String> updatedNodes = new HashSet<>();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            String nodeId = (String) valueMap.get("ID");
            String parentNodeId = getParentNodeId(nodeId);
            if (parentNodeId != null) {
                updateDateRange(parentNodeId, updatedNodes);  // 从父节点开始更新
            } else {
                updateDateRange(nodeId, updatedNodes);  // 如果没有父节点，直接从当前节点开始
            }
        }
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 预检测填报
     *
     * @throws Exception
     */
    public void preCheck() throws Exception {
        boolean allHaveChildren = true; // 假设所有节点都有子节点

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String nodeId = entityRecord.csCommId;
            List<Map<String, Object>> children = getChildNodes(nodeId);

            if (children.isEmpty()) { // 如果有节点没有子节点，即它是叶子节点
                allHaveChildren = false;
                break;
            }
        }

        if (allHaveChildren) { // 如果所有节点都有子节点
            throw new Exception("所选计划不为最末级计划，所选计划至少包含一个最末级计划");
        }
    }

    /**
     * 填报进展
     */
    public void reportProgress() {
        InvokeActResult invokeActResult = new InvokeActResult();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        String submitUserId = varMap.get("P_SUMBIT_USER_ID").toString();
        String progTimeStr = varMap.get("P_PROG_TIME").toString(); // 获取日期时间字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.parse(progTimeStr, formatter);
        String wbsStatusId = varMap.get("P_WBS_STATUS_ID").toString();
        String remark = varMap.get("P_REMARK") != null ? varMap.get("P_REMARK").toString() : "";
        String attachments = varMap.get("P_ATTACHMENTS") != null ? varMap.get("P_ATTACHMENTS").toString() : "";

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String id = IdUtil.getSnowflakeNextIdStr();
            Map<String, Object> valueMap = entityRecord.valueMap;
            String ccPrjId = valueMap.get("CC_PRJ_ID").toString();
            String nodeId = entityRecord.csCommId;
            List<Map<String, Object>> children = getChildNodes(nodeId); // 获取当前节点的子节点
            if (children.isEmpty()) { // 如果是叶子节点
                String insertSql = "INSERT INTO CC_PRJ_STRUCT_NODE_PROG (ID, CC_PRJ_ID, CC_PRJ_STRUCT_NODE_ID, CRT_USER_ID, LAST_MODI_USER_ID, SUMBIT_USER_ID, PROG_TIME, CC_WBS_STATUS_ID, REMARK, CC_ATTACHMENTS, CRT_DT, LAST_MODI_DT) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                myJdbcTemplate.update(insertSql, id, ccPrjId, nodeId, submitUserId, submitUserId, submitUserId, now, wbsStatusId, remark, attachments, now, now);

                CcPrjStructNode ccPrjStructNode = CcPrjStructNode.selectById(nodeId);
                ccPrjStructNode.setProgTime(now);
                ccPrjStructNode.setCcWbsStatusId(wbsStatusId);
                LocalDate actFr = ccPrjStructNode.getActFr();
                LocalDate actTo = ccPrjStructNode.getActTo();
                long actDays;

                // 根据不同的状态更新实际开始和结束日期
                switch (wbsStatusId) {
                    case "TODO":
                        ccPrjStructNode.setActFr(null);
                        ccPrjStructNode.setActTo(null);
                        ccPrjStructNode.setActDays(BigDecimal.ZERO);
                        break;
                    case "DOING":
                        if (actFr == null) {
                            ccPrjStructNode.setActFr(now.toLocalDate());
                        }
                        ccPrjStructNode.setActTo(null);
                        break;
                    case "DONE":
                        //实际开始时间为空
                        if (actFr == null || actFr.isAfter(now.toLocalDate())) {
                            ccPrjStructNode.setActFr(now.toLocalDate());
                        }
                        ccPrjStructNode.setActTo(now.toLocalDate());
                        break;
                }

                // 计算实际工作天数
                if (ccPrjStructNode.getActFr() != null && ccPrjStructNode.getActTo() != null) {
                    actDays = ChronoUnit.DAYS.between(ccPrjStructNode.getActFr(), ccPrjStructNode.getActTo()) + 1;
                    ccPrjStructNode.setActDays(BigDecimal.valueOf(actDays));
                }
                ccPrjStructNode.updateById();
            }
        }

        recalculationPlan();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }


}
