package com.bid.ext.cc;

import cn.hutool.core.util.IdUtil;
import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.StatusE;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.bid.ext.cc.PrjExt.checkDates;

@Slf4j
public class StructNodeExt {

    /**
     * WBS套用模板
     */
    public void templateWbs() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        // 获取模板结构
        String pWbsTempateId = varMap.get("P_WBS_TEMPATE_ID").toString();
        Boolean includeRootNode = (Boolean) varMap.get("P_INCLUDE_ROOT_NODE");

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> topNode = getTopNode(entityRecord);

            List<Map<String, Object>> templateStruct = getTemplateStruct(pWbsTempateId, includeRootNode);
            List<Map<String, Object>> list = replaceIdsAndInsert(templateStruct);
            // 序号
            BigDecimal seqNo = BigDecimal.ZERO;
            // 对于每一个模板结构节点，将其作为子节点插入
            for (Map<String, Object> node : templateStruct) {
                insertWbsNode(node, entityRecord, seqNo, topNode);
                seqNo = seqNo.add(BigDecimal.ONE);
            }
        }
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    private Map<String, Object> getTopNode(EntityRecord entityRecord) {
        Map<String, Object> currentValueMap = entityRecord.valueMap;
        while (currentValueMap.get("CC_PRJ_STRUCT_NODE_PID") != null) {
            currentValueMap = Crud.from("CC_PRJ_STRUCT_NODE").where().eq("ID", currentValueMap.get("CC_PRJ_STRUCT_NODE_PID")).select().execForMap();
        }
        return currentValueMap;
    }


    /**
     * PBS套用模板
     */
    public void templatePbs() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        // 获取模板结构
        String pWbsTempateId = varMap.get("P_PBS_TEMPATE_ID").toString();
        Boolean includeRootNode = (Boolean) varMap.get("P_INCLUDE_ROOT_NODE");

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            List<Map<String, Object>> templateStruct = getTemplateStruct(pWbsTempateId, includeRootNode);
            List<Map<String, Object>> list = replaceIdsAndInsert(templateStruct);
            // 序号
            BigDecimal seqNo = BigDecimal.ZERO;
            // 对于每一个模板结构节点，将其作为子节点插入
            for (Map<String, Object> node : templateStruct) {
                insertPbsNode(node, entityRecord, seqNo);
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

        for (Map<String, Object> node : nodes) {
            node.put("COPY_FROM_PRJ_STRUCT_NODE_ID", node.get("ID"));
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
            // 新ID与旧ID映射表
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
     * 插入WBS节点
     *
     * @param nodeData
     * @param parentRecord
     */
    private void insertWbsNode(Map<String, Object> nodeData, EntityRecord parentRecord, BigDecimal seqNo, Map<String, Object> topNode) {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String ccPrjId = parentRecord.valueMap.get("CC_PRJ_ID").toString();
        String ccPrjWbsTypeId = nodeData.get("CC_PRJ_WBS_TYPE_ID").toString();

        Integer planFrDayNo = nodeData.get("PLAN_FR_DAY_NO") != null ? Integer.parseInt(nodeData.get("PLAN_FR_DAY_NO").toString()) : 1;
        Integer planToDayNo = nodeData.get("PLAN_TO_DAY_NO") != null ? Integer.parseInt(nodeData.get("PLAN_TO_DAY_NO").toString()) : 1;

        LocalDate topNodePlanFr = JdbcMapUtil.getLocalDate(topNode, "PLAN_FR");
        LocalDate fromDate = null;
        LocalDate toDate = null;
        BigDecimal planDays = BigDecimal.ZERO;
        if (topNodePlanFr != null) {
            fromDate = topNodePlanFr.plusDays(planFrDayNo - 1);
            toDate = topNodePlanFr.plusDays(planToDayNo - 1);
            planDays = BigDecimal.valueOf(planToDayNo - planFrDayNo + 1);
        }

        CcPrjStructNode ccPrjStructNode = new CcPrjStructNode();
        ccPrjStructNode.setCrtDt(LocalDateTime.now());
        ccPrjStructNode.setCrtUserId(loginInfo.userInfo.id);
        ccPrjStructNode.setLastModiUserId(loginInfo.userInfo.id);
        ccPrjStructNode.setCcPrjId(ccPrjId);
        ccPrjStructNode.setStatus("DR");

        String remark = nodeData.get("REMARK") != null ? nodeData.get("REMARK").toString() : null;
        ccPrjStructNode.setRemark(remark);

        Integer isMileStoneInt = (Integer) nodeData.get("IS_MILE_STONE");
        boolean isMileStone = isMileStoneInt != null && isMileStoneInt != 0;
        ccPrjStructNode.setIsMileStone(isMileStone);

        Integer isWbsInt = (Integer) nodeData.get("IS_WBS");
        boolean isWbs = isWbsInt != null && isWbsInt != 0;
        ccPrjStructNode.setIsWbs(isWbs);

        ccPrjStructNode.setId(nodeData.get("ID").toString());
        ccPrjStructNode.setName(nodeData.get("NAME").toString());
        ccPrjStructNode.setWbsChiefUserId(loginInfo.userInfo.id);
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
     * 插入PBS节点
     *
     * @param nodeData
     * @param parentRecord
     */
    private void insertPbsNode(Map<String, Object> nodeData, EntityRecord parentRecord, BigDecimal seqNo) {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String ccPrjId = parentRecord.valueMap.get("CC_PRJ_ID").toString();

        CcPrjStructNode ccPrjStructNode = new CcPrjStructNode();
        ccPrjStructNode.setCrtDt(LocalDateTime.now());
        ccPrjStructNode.setCrtUserId(loginInfo.userInfo.id);
        ccPrjStructNode.setLastModiUserId(loginInfo.userInfo.id);
        ccPrjStructNode.setPbsChiefUserId(loginInfo.userInfo.id);
        ccPrjStructNode.setCcPrjId(ccPrjId);
        ccPrjStructNode.setStatus("AP");

        String remark = nodeData.get("REMARK") != null ? nodeData.get("REMARK").toString() : null;
        ccPrjStructNode.setRemark(remark);

        Integer isPbsInt = (Integer) nodeData.get("IS_PBS");
        boolean isPbs = isPbsInt != null && isPbsInt != 0;
        ccPrjStructNode.setIsPbs(isPbs);

        Integer isMileStoneInt = (Integer) nodeData.get("IS_MILE_STONE");
        boolean isMileStone = isMileStoneInt != null && isMileStoneInt != 0;
        ccPrjStructNode.setIsMileStone(isMileStone);

        ccPrjStructNode.setId(nodeData.get("ID").toString());
        ccPrjStructNode.setName(nodeData.get("NAME").toString());
        ccPrjStructNode.setSeqNo(seqNo);  // 设置序号


        String parentNodeId = nodeData.get("CC_PRJ_STRUCT_NODE_PID") != null ? nodeData.get("CC_PRJ_STRUCT_NODE_PID").toString() : parentRecord.valueMap.get("ID").toString();
        ccPrjStructNode.setCcPrjStructNodePid(parentNodeId);

        ccPrjStructNode.setIsTemplate(false);
        ccPrjStructNode.insertById();
    }

    /**
     * 插入WBS节点(未审批)
     *
     * @param nodeData
     * @param parentRecord
     */
    private void insertWbsNodeUnapproved(Map<String, Object> nodeData, EntityRecord parentRecord, BigDecimal seqNo) {
        BigDecimal seqNoCopy = nodeData.get("SEQ_NO") != null ? new BigDecimal(nodeData.get("SEQ_NO").toString()) : null;
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String ccPrjId = parentRecord.valueMap.get("CC_PRJ_ID").toString();
        String ccPrjWbsTypeId = nodeData.get("CC_PRJ_WBS_TYPE_ID") != null ? nodeData.get("CC_PRJ_WBS_TYPE_ID").toString() : null;
        String wbsChiefUserId = nodeData.get("WBS_CHIEF_USER_ID") != null ? nodeData.get("WBS_CHIEF_USER_ID").toString() : null;
        String copyFromPrjStructNodeId = nodeData.get("COPY_FROM_PRJ_STRUCT_NODE_ID") != null ? nodeData.get("COPY_FROM_PRJ_STRUCT_NODE_ID").toString() : null;

        LocalDate planFr = nodeData.get("PLAN_FR") != null ? LocalDate.parse(nodeData.get("PLAN_FR").toString()) : null;
        LocalDate planTo = nodeData.get("PLAN_FR") != null ? LocalDate.parse(nodeData.get("PLAN_TO").toString()) : null;
        BigDecimal planDays = BigDecimal.ONE;
        if (planFr != null && planTo != null) {
            // 只有当两个日期都不是 null 时才计算天数差
            planDays = BigDecimal.valueOf(ChronoUnit.DAYS.between(planFr, planTo)).add(BigDecimal.ONE);
        }

        LocalDate actFr = nodeData.get("ACT_FR") != null ? LocalDate.parse(nodeData.get("ACT_FR").toString()) : null;
        LocalDate actTo = nodeData.get("ACT_TO") != null ? LocalDate.parse(nodeData.get("ACT_TO").toString()) : null;
        BigDecimal actDays = BigDecimal.ZERO; // 初始设置为 null
        if (actFr != null && actTo != null) {
            // 只有当两个日期都不是 null 时才计算天数差
            actDays = BigDecimal.valueOf(ChronoUnit.DAYS.between(actFr, actTo)).add(BigDecimal.ONE);
        }

        LocalDateTime progTime = nodeData.get("PROG_TIME") != null ? LocalDateTime.parse(nodeData.get("PROG_TIME").toString()) : null;
        Integer actWbsPct = JdbcMapUtil.getInt(nodeData, "ACT_WBS_PCT");
        String wbsProgressStatusId = JdbcMapUtil.getString(nodeData, "CC_WBS_PROGRESS_STATUS_ID");
        String ccWbsStatusId = nodeData.get("CC_WBS_STATUS_ID") != null ? nodeData.get("CC_WBS_STATUS_ID").toString() : null;
        String ccWbsRiskId = nodeData.get("CC_WBS_RISK_ID") != null ? nodeData.get("CC_WBS_RISK_ID").toString() : null;
        String ccRiskLvlId = nodeData.get("CC_RISK_LVL_ID") != null ? nodeData.get("CC_RISK_LVL_ID").toString() : null;


        CcPrjStructNode ccPrjStructNode = new CcPrjStructNode();
        ccPrjStructNode.setCrtDt(LocalDateTime.now());
        ccPrjStructNode.setCrtUserId(loginInfo.userInfo.id);
        ccPrjStructNode.setLastModiUserId(loginInfo.userInfo.id);
        ccPrjStructNode.setCcPrjId(ccPrjId);
        ccPrjStructNode.setProgTime(progTime);
        ccPrjStructNode.setActWbsPct(actWbsPct);
        ccPrjStructNode.setCcWbsProgressStatusId(wbsProgressStatusId);
        ccPrjStructNode.setCcWbsStatusId(ccWbsStatusId);
        ccPrjStructNode.setCcWbsRiskId(ccWbsRiskId);
        ccPrjStructNode.setCcRiskLvlId(ccRiskLvlId);


        String remark = nodeData.get("REMARK") != null ? nodeData.get("REMARK").toString() : null;
        ccPrjStructNode.setRemark(remark);

        Integer isMileStoneInt = (Integer) nodeData.get("IS_MILE_STONE");
        boolean isMileStone = isMileStoneInt != null && isMileStoneInt != 0;
        ccPrjStructNode.setIsMileStone(isMileStone);

        Integer isWbsInt = (Integer) nodeData.get("IS_WBS");
        boolean isWbs = isWbsInt != null && isWbsInt != 0;
        ccPrjStructNode.setIsWbs(isWbs);

        ccPrjStructNode.setId(nodeData.get("ID").toString());
        ccPrjStructNode.setName(nodeData.get("NAME").toString());
        ccPrjStructNode.setPlanFr(planFr);
        ccPrjStructNode.setPlanTo(planTo);
        ccPrjStructNode.setActFr(actFr);
        ccPrjStructNode.setActTo(actTo);
        ccPrjStructNode.setPlanDays(planDays);
        ccPrjStructNode.setActDays(actDays);
        ccPrjStructNode.setSeqNo(seqNoCopy);  // 设置序号
        ccPrjStructNode.setCcPrjWbsTypeId(ccPrjWbsTypeId);// 计划类型
        ccPrjStructNode.setWbsChiefUserId(wbsChiefUserId); // 进度负责人
        ccPrjStructNode.setStatus("DR"); // 数据状态改为草稿
        ccPrjStructNode.setCopyFromPrjStructNodeId(copyFromPrjStructNodeId);// 拷贝自项目结构节点


        String parentNodeId = nodeData.get("CC_PRJ_STRUCT_NODE_PID") != null ? nodeData.get("CC_PRJ_STRUCT_NODE_PID").toString() : parentRecord.valueMap.get("ID").toString();
        ccPrjStructNode.setCcPrjStructNodePid(parentNodeId);

        ccPrjStructNode.setIsTemplate(false);
        ccPrjStructNode.insertById();
    }

    /**
     * 重算计划
     */
    public void recalculationPlan() {
        InvokeActResult invokeActResult = new InvokeActResult();
        Set<String> updatedNodes = new HashSet<>();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            String csCommId = entityRecord.csCommId;
            String nodeId = (String) valueMap.get("ID");
            CcPrjStructNode ccPrjStructNode = CcPrjStructNode.selectById(csCommId);
            String ccPrjStructNodePid = ccPrjStructNode.getCcPrjStructNodePid();

            if (ccPrjStructNodePid != null) {
                updateDateRange(ccPrjStructNodePid, updatedNodes);  // 从父节点开始更新
            } else {
                updateDateRange(nodeId, updatedNodes);  // 如果没有父节点，直接从当前节点开始
            }
        }
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
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
        // 获取子节点
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

            // 更新风险等级
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
            // 子节点进展时间不为空且最大进展为空或子节点进展时间晚于最大进展时间
            if (childProgTime != null && (maxProgTime == null || childProgTime.isAfter(maxProgTime))) {
                maxProgTime = childProgTime; // 更新最大进展时间
            }
        }

        if (children.isEmpty()) {
            Map<String, Object> nodeData = myJdbcTemplate.queryForMap("SELECT PLAN_FR, PLAN_TO, ACT_FR, ACT_TO FROM cc_prj_struct_node WHERE ID = ?", nodeId);
            minDate = getDateFromSql((java.sql.Date) nodeData.get("PLAN_FR"));
            maxDate = getDateFromSql((java.sql.Date) nodeData.get("PLAN_TO"));
            minActDate = getDateFromSql((java.sql.Date) nodeData.get("ACT_FR"));
            maxActDate = getDateFromSql((java.sql.Date) nodeData.get("ACT_TO"));
        }

        // 如果任何一个子节点的实际完成日期为空，则父节点的实际完成日期也为空
        if (!allChildrenHaveActTo) {
            maxActDate = null;
        }

        // 最大进展时间不为空则更新
        if (maxProgTime != null) {
            myJdbcTemplate.update("UPDATE cc_prj_struct_node SET PROG_TIME = ? WHERE ID = ?", maxProgTime, nodeId);
        }

        // 确定新的进展状态
        String status = calculateStatus(maxDate, minActDate, maxActDate, today);
        myJdbcTemplate.update("UPDATE cc_prj_struct_node SET CC_WBS_PROGRESS_STATUS_ID = ? WHERE ID = ?", status, nodeId);

        // 确定新的状态
        String newStatus = determineNewStatus(childStatuses);
        if (newStatus != null && !newStatus.isEmpty()) {  // 确保状态不是null或空字符串
            myJdbcTemplate.update("UPDATE cc_prj_struct_node SET CC_WBS_STATUS_ID = ? WHERE ID = ?", newStatus, nodeId);
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
     * 进度风险计算
     *
     * @param planTo 计划完成日期
     * @param actTo  实际完成日期
     * @param today  当前日期
     * @return 进度风险
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
     * 进展风险计算
     *
     * @param planTo  计划完成日期
     * @param actFrom 实际开始日期
     * @param actTo   实际完成日期
     * @param today   当前日期
     * @return 进展风险
     */
    private String calculateStatus(LocalDate planTo, LocalDate actFrom, LocalDate actTo, LocalDate today) {
        if (planTo == null) {
            return "TODO";  // 如果没有计划完成日期，进展状态为未启动TODO
        }

        if (actFrom == null) {
            if (planTo.isBefore(today)) {
                return "OVER";
            } else {
                return "TODO";
            }
        }

        if (actTo == null) {
            if (today.isAfter(planTo)) {
                return "OVER";
            } else {
                return "DOING";
            }
        }

        return "DONE";
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
     * 递归查找给定节点的根节点ID
     *
     * @param nodeId 当前节点ID
     * @return 根节点ID
     */
    private String findRootNodeId(String nodeId) {
        String parentId = getParentNodeId(nodeId);
        if (parentId == null) {
            return nodeId;  // 如果没有父节点，当前节点即为根节点
        }
        return findRootNodeId(parentId);
    }


    /**
     * 预检测填报
     *
     * @throws Exception
     */
    public void preCheck() {
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
            throw new BaseException("所选计划不为最末级计划，所选计划至少包含一个最末级计划！");
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
        LocalDate pActFr = JdbcMapUtil.getLocalDate(varMap, "P_ACT_FR");
        LocalDateTime now = LocalDateTime.parse(progTimeStr, formatter);
        Integer actWbsPct = JdbcMapUtil.getInt(varMap, "P_ACT_WBS_PCT");
        if (actWbsPct > 100 || actWbsPct < 0) {
            throw new BaseException("“实际进度比例”超出数据范围!");
        }
        int curWbsPct = JdbcMapUtil.getInt(varMap, "P_CUR_WBS_PCT");
        if (actWbsPct < curWbsPct) {
            throw new BaseException("“实际进度比例”应不小于最新进展比例!");
        }
        String wbsStatusId = JdbcMapUtil.getString(varMap, "P_WBS_STATUS_ID");
        String wbsProgressStatusId = JdbcMapUtil.getString(varMap, "P_WBS_PROGRESS_STATUS_ID");
        String remark = varMap.get("P_REMARK") != null ? varMap.get("P_REMARK").toString() : "";
        String attachments = varMap.get("P_ATTACHMENTS") != null ? varMap.get("P_ATTACHMENTS").toString() : "";

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String id = IdUtil.getSnowflakeNextIdStr();
            Map<String, Object> valueMap = entityRecord.valueMap;
            String ccPrjId = valueMap.get("CC_PRJ_ID").toString();
            String nodeId = entityRecord.csCommId;
            List<Map<String, Object>> children = getChildNodes(nodeId); // 获取当前节点的子节点

            if (children.isEmpty()) { // 如果是叶子节点
                String insertSql = "INSERT INTO CC_PRJ_STRUCT_NODE_PROG (ID, CC_PRJ_ID, CC_PRJ_STRUCT_NODE_ID, CRT_USER_ID, LAST_MODI_USER_ID, SUMBIT_USER_ID, ACT_FR, PROG_TIME, ACT_WBS_PCT, CC_WBS_STATUS_ID, CC_WBS_PROGRESS_STATUS_ID, REMARK, CC_ATTACHMENTS, CRT_DT, LAST_MODI_DT,VER) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                myJdbcTemplate.update(insertSql, id, ccPrjId, nodeId, submitUserId, submitUserId, submitUserId, pActFr, now, actWbsPct, wbsStatusId, wbsProgressStatusId, remark, attachments, now, now, 1);

                CcPrjStructNode ccPrjStructNode = CcPrjStructNode.selectById(nodeId);
                ccPrjStructNode.setProgTime(now);
                ccPrjStructNode.setActWbsPct(actWbsPct);
                ccPrjStructNode.setCcWbsStatusId(wbsStatusId);
                ccPrjStructNode.setCcWbsProgressStatusId(wbsProgressStatusId);
                LocalDate actFr = ccPrjStructNode.getActFr();
                long actDays;

                // 根据不同的状态更新实际开始和结束日期
//                switch (wbsStatusId) {
//                    case "TODO":
//                        ccPrjStructNode.setActFr(null);
//                        ccPrjStructNode.setActTo(null);
//                        ccPrjStructNode.setActDays(BigDecimal.ZERO);
//                        break;
//                    case "DOING":
//                        if (actFr == null) {
//                            ccPrjStructNode.setActFr(now.toLocalDate());
//                        }
//                        ccPrjStructNode.setActTo(null);
//                        break;
//                    case "DONE":
//                        // 实际开始时间为空
//                        if (actFr == null || actFr.isAfter(now.toLocalDate())) {
//                            ccPrjStructNode.setActFr(now.toLocalDate());
//                        }
//                        ccPrjStructNode.setActTo(now.toLocalDate());
//                        break;
//                }
                //根据不同的状态更新实际开始和结束日期
                switch (wbsProgressStatusId) {
                    case "DOING":
                        if (actFr == null) {
                            ccPrjStructNode.setActFr(pActFr);
                        }
                        ccPrjStructNode.setActTo(null);
                        break;
                    case "DONE":
                        // 实际开始时间为空,更新实际开始时间
                        if (actFr == null || actFr.isAfter(now.toLocalDate())) {
                            ccPrjStructNode.setActFr(pActFr);
                        }
                        // 更新实际完成时间
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

    /**
     * 填报进展（湛江施工）
     */
    public void reportProgressConstruct() {
        InvokeActResult invokeActResult = new InvokeActResult();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        LocalDate pActFr = JdbcMapUtil.getLocalDate(varMap, "P_ACT_FR");
        String submitUserId = varMap.get("P_SUMBIT_USER_ID").toString();
        String progTimeStr = varMap.get("P_PROG_TIME").toString(); // 获取日期时间字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime now = LocalDateTime.parse(progTimeStr, formatter);

        String wbsProgressStatusId = JdbcMapUtil.getString(varMap, "P_WBS_PROGRESS_STATUS_ID");
        String remark = varMap.get("P_REMARK") != null ? varMap.get("P_REMARK").toString() : "";
        String attachments = varMap.get("P_ATTACHMENTS") != null ? varMap.get("P_ATTACHMENTS").toString() : "";

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String id = IdUtil.getSnowflakeNextIdStr();
            Map<String, Object> valueMap = entityRecord.valueMap;
            String ccPrjId = valueMap.get("CC_PRJ_ID").toString();
            String nodeId = entityRecord.csCommId;
            List<Map<String, Object>> children = getChildNodes(nodeId); // 获取当前节点的子节点
            if (children.isEmpty()) { // 如果是叶子节点
                String insertSql = "INSERT INTO CC_PRJ_STRUCT_NODE_PROG (ID, CC_PRJ_ID, CC_PRJ_STRUCT_NODE_ID, CRT_USER_ID, LAST_MODI_USER_ID, SUMBIT_USER_ID, ACT_FR, PROG_TIME, CC_WBS_PROGRESS_STATUS_ID, REMARK, CC_ATTACHMENTS, CRT_DT, LAST_MODI_DT,VER) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                myJdbcTemplate.update(insertSql, id, ccPrjId, nodeId, submitUserId, submitUserId, submitUserId, pActFr, now, wbsProgressStatusId, remark, attachments, now, now, 1);

                CcPrjStructNode ccPrjStructNode = CcPrjStructNode.selectById(nodeId);
                ccPrjStructNode.setProgTime(now);
                ccPrjStructNode.setCcWbsProgressStatusId(wbsProgressStatusId);
                LocalDate actFr = ccPrjStructNode.getActFr();
                long actDays;

                // 根据不同的状态更新实际开始和结束日期
//                switch (wbsStatusId) {
//                    case "TODO":
//                        ccPrjStructNode.setActFr(null);
//                        ccPrjStructNode.setActTo(null);
//                        ccPrjStructNode.setActDays(BigDecimal.ZERO);
//                        break;
//                    case "DOING":
//                        if (actFr == null) {
//                            ccPrjStructNode.setActFr(now.toLocalDate());
//                        }
//                        ccPrjStructNode.setActTo(null);
//                        break;
//                    case "DONE":
//                        // 实际开始时间为空
//                        if (actFr == null || actFr.isAfter(now.toLocalDate())) {
//                            ccPrjStructNode.setActFr(now.toLocalDate());
//                        }
//                        ccPrjStructNode.setActTo(now.toLocalDate());
//                        break;
//                }

                ccPrjStructNode.setActFr(pActFr);
                ccPrjStructNode.setActTo(now.toLocalDate());

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

    /**
     * 节点日期检查计算
     *
     * @throws Exception
     */
    public void nodeDateCheckCalculate() {
        EntityRecord entityRecord = ExtJarHelper.getEntityRecordList().get(0);
        Map<String, Object> valueMap = entityRecord.valueMap;
        Object planFr = valueMap.get("PLAN_FR");
        Object planTo = valueMap.get("PLAN_TO");

        Object planFrDayNoObj = valueMap.get("PLAN_FR_DAY_NO");
        Object planToDayNoObj = valueMap.get("PLAN_TO_DAY_NO");
        if (!SharedUtil.isEmpty(planFr) && !SharedUtil.isEmpty(planTo)) {
            checkDates((String) planFr, (String) planTo);

            LocalDate startDate = LocalDate.parse((String) planFr);
            LocalDate endDate = LocalDate.parse((String) planTo);

            BigDecimal daysBetween = BigDecimal.valueOf(ChronoUnit.DAYS.between(startDate, endDate)).add(BigDecimal.ONE);
            entityRecord.valueMap.put("PLAN_DAYS", daysBetween);
            if (SharedUtil.isEmpty(entityRecord.extraEditableAttCodeList)) {
                entityRecord.extraEditableAttCodeList = new ArrayList<>(1);
            }
            if (!entityRecord.extraEditableAttCodeList.contains("PLAN_DAYS")) {
                entityRecord.extraEditableAttCodeList.add("PLAN_DAYS");
            }
        }

        if (!SharedUtil.isEmpty(planFrDayNoObj) && !SharedUtil.isEmpty(planToDayNoObj)) {
            BigDecimal planFrDayNo = new BigDecimal(planFrDayNoObj.toString());
            BigDecimal planToDayNo = new BigDecimal(planToDayNoObj.toString());

            if (planFrDayNo.compareTo(planToDayNo) <= 0) {
                BigDecimal dayCount = planToDayNo.subtract(planFrDayNo).add(BigDecimal.ONE);
                entityRecord.valueMap.put("PLAN_DAYS", dayCount);
                if (SharedUtil.isEmpty(entityRecord.extraEditableAttCodeList)) {
                    entityRecord.extraEditableAttCodeList = new ArrayList<>(1);
                }
                if (!entityRecord.extraEditableAttCodeList.contains("PLAN_DAYS")) {
                    entityRecord.extraEditableAttCodeList.add("PLAN_DAYS");
                }
            } else {
                throw new BaseException("请检查并确保开始日期不晚于结束日期！");
            }
        }
    }


    /**
     * 天数预检测
     *
     * @throws Exception
     */
    public void nodeDayCheckCalculate() {
        EntityRecord entityRecord = ExtJarHelper.getEntityRecordList().get(0);
        Map<String, Object> valueMap = entityRecord.valueMap;
        Object planFrDayNoObj = valueMap.get("PLAN_FR_DAY_NO");
        Object planToDayNoObj = valueMap.get("PLAN_TO_DAY_NO");

        if (!SharedUtil.isEmpty(planFrDayNoObj) && !SharedUtil.isEmpty(planToDayNoObj)) {
            BigDecimal planFrDayNo = new BigDecimal(planFrDayNoObj.toString());
            BigDecimal planToDayNo = new BigDecimal(planToDayNoObj.toString());

            if (planFrDayNo.compareTo(planToDayNo) <= 0) {
                BigDecimal dayCount = planToDayNo.subtract(planFrDayNo).add(BigDecimal.ONE);

            } else {
                throw new BaseException("请检查并确保开始日期不晚于结束日期！");
            }
        }
    }

    /**
     * 更新匡算树时同步成本统览
     */
    public void syncCostTree0() {
        syncCostTree("CBS_0_AMT");
    }

    /**
     * 更新估算树时同步成本统览
     */
    public void syncCostTree1() {
        syncCostTree("CBS_1_AMT");
    }

    /**
     * 更新概算树时同步成本统览
     */
    public void syncCostTree2() {
        syncCostTree("CBS_2_AMT");
    }


    /**
     * 更新预算树时同步成本统览
     */
    public void syncCostTree3() {
        syncCostTree("CBS_3_AMT");
    }

    /**
     * 更新预计结算树时同步成本统览
     */
    public void syncCostTree11() {
        syncCostTree("CBS_11_AMT");
    }

    /**
     * 更新实际结算树时同步成本统览
     */
    public void syncCostTree12() {
        syncCostTree("CBS_12_AMT");
    }

    private void syncCostTree(String type) {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            Map<String, Object> valueMap = entityRecord.valueMap;
//            String planTotalCost = valueMap.get("PLAN_TOTAL_COST") != null ? valueMap.get("PLAN_TOTAL_COST").toString() : null;
//            BigDecimal planTotalCostBigDecimal = new BigDecimal(planTotalCost);
            BigDecimal planTotalCostBigDecimal = JdbcMapUtil.getBigDecimal(valueMap, "PLAN_TOTAL_COST");
            String ccPrjId = JdbcMapUtil.getString(valueMap, "CC_PRJ_ID");
            CcPrjStructNode ccPrjStructNode = CcPrjStructNode.selectById(csCommId);
            String copyFromPrjStructNodeId = ccPrjStructNode.getCopyFromPrjStructNodeId();
            List<CcPrjCostOverview> ccPrjCostOverviews = CcPrjCostOverview.selectByWhere(new Where().eq(CcPrjCostOverview.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, copyFromPrjStructNodeId).eq(CcPrjCostOverview.Cols.CC_PRJ_ID, ccPrjId));
            if (!SharedUtil.isEmpty(ccPrjCostOverviews)) {
                for (CcPrjCostOverview ccPrjCostOverview : ccPrjCostOverviews) {
                    String ccPrjCostOverviewPid = ccPrjCostOverview.getCcPrjCostOverviewPid();
                    switch (type) {
                        case "CBS_0_AMT":
                            ccPrjCostOverview.setCbs0Amt(planTotalCostBigDecimal);
                            ccPrjCostOverview.updateById();
                            recalculatePlanTotalCost(ccPrjCostOverviewPid, type);
                            break;
                        case "CBS_1_AMT":
                            ccPrjCostOverview.setCbs1Amt(planTotalCostBigDecimal);
                            ccPrjCostOverview.updateById();
                            recalculatePlanTotalCost(ccPrjCostOverviewPid, type);
                            break;
                        case "CBS_2_AMT":
                            ccPrjCostOverview.setCbs2Amt(planTotalCostBigDecimal);
                            ccPrjCostOverview.updateById();
                            recalculatePlanTotalCost(ccPrjCostOverviewPid, type);
                            break;
                        case "CBS_3_AMT":
                            ccPrjCostOverview.setCbs3Amt(planTotalCostBigDecimal);
                            ccPrjCostOverview.updateById();
                            recalculatePlanTotalCost(ccPrjCostOverviewPid, type);
                            break;
                        case "CBS_11_AMT":
                            ccPrjCostOverview.setCbs11Amt(planTotalCostBigDecimal);
                            ccPrjCostOverview.updateById();
                            recalculatePlanTotalCost(ccPrjCostOverviewPid, type);
                            break;
                        case "CBS_12_AMT":
                            ccPrjCostOverview.setCbs12Amt(planTotalCostBigDecimal);
                            ccPrjCostOverview.updateById();
                            recalculatePlanTotalCost(ccPrjCostOverviewPid, type);
                            break;
                    }
                }
            }

        }
    }


    /**
     * 重算成本估算
     */
    public void recalculationCostEstimation() {
        InvokeActResult invokeActResult = new InvokeActResult();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            String csCommId = entityRecord.csCommId;
            String parentNodeId = getParentNodeId(csCommId);
            if (parentNodeId != null) {
                recalculatePlanCostEstimation(parentNodeId);
            }
        }
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 递归地重算并更新节点的成本估算
     *
     * @param nodeId 当前节点ID
     */
    private void recalculatePlanCostEstimation(String nodeId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        // 获取当前节点的直接子节点的PLAN_TOTAL_COST总和
        BigDecimal totalSum = BigDecimal.ZERO;
        List<Map<String, Object>> children = getChildNodes(nodeId);
        for (Map<String, Object> child : children) {
            Object planTotalCost = child.get("PLAN_TOTAL_COST");
            BigDecimal childCost = planTotalCost != null ? new BigDecimal(planTotalCost.toString()) : BigDecimal.ZERO;
            totalSum = totalSum.add(childCost);
        }

        // 更新当前节点的PLAN_TOTAL_COST
        String updateSql = "UPDATE cc_prj_struct_node SET PLAN_TOTAL_COST = ? WHERE ID = ?";
        myJdbcTemplate.update(updateSql, totalSum, nodeId);

        // 递归更新父节点
        String parentId = getParentNodeId(nodeId);
        if (parentId != null) {
            recalculatePlanCostEstimation(parentId);
        }
    }


    /**
     * 获取成本父节点
     *
     * @param nodeId
     * @return
     */
    private String getCostParentNodeId(String nodeId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        try {
            String sql = "SELECT CC_PRJ_COST_OVERVIEW_PID FROM CC_PRJ_COST_OVERVIEW WHERE ID = ?";
            Map<String, Object> result = myJdbcTemplate.queryForMap(sql, nodeId);
            return result != null ? (String) result.get("CC_PRJ_COST_OVERVIEW_PID") : null;
        } catch (EmptyResultDataAccessException e) {
            return null;  // 父节点不存在，当前节点可能是根节点
        }
    }

    /**
     * 获取成本子节点
     *
     * @param parentId
     * @return
     */
    private List<Map<String, Object>> getCostChildNodes(String parentId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        // 示例SQL查询，获取所有子节点
        String sql = "SELECT * FROM CC_PRJ_COST_OVERVIEW WHERE CC_PRJ_COST_OVERVIEW_PID = ?";
        return myJdbcTemplate.queryForList(sql, parentId);
    }


    /**
     * 递归地重算并更新节点的成本总览
     *
     * @param nodeId
     * @param entCode
     */
    private void recalculatePlanTotalCost(String nodeId, String entCode) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        // 获取当前节点的直接子节点的PLAN_TOTAL_COST总和
        BigDecimal totalSum = BigDecimal.ZERO;
        List<Map<String, Object>> children = getCostChildNodes(nodeId);
        for (Map<String, Object> child : children) {
            Object childCostObject = child.get(entCode);
            BigDecimal childCost = childCostObject != null ? new BigDecimal(childCostObject.toString()) : BigDecimal.ZERO;
            totalSum = totalSum.add(childCost);
        }

        // 更新当前节点的金额
        String updateSql = "UPDATE CC_PRJ_COST_OVERVIEW SET " + entCode + " = ? WHERE ID = ?";
        myJdbcTemplate.update(updateSql, totalSum, nodeId);

        // 递归更新父节点
        String parentId = getCostParentNodeId(nodeId);
        if (parentId != null) {
            recalculatePlanTotalCost(parentId, entCode);
        }
    }


    /**
     * 招标数据同步成本总览
     *
     * @throws Exception
     */
    public void bidToCostOverview() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;

            // 本次招标项目，金额，成本科目
            CcBid ccBid = CcBid.selectById(csCommId);
            String ccPrjId = ccBid.getCcPrjId();

            BigDecimal priceLimit = ccBid.getPriceLimit();
            String ccPrjCbsTempalteNodeId = ccBid.getCcPrjCbsTempalteNodeId();
            BigDecimal bidAmtSum = new BigDecimal(priceLimit.toString());
            // 1.查询项目此成本科目已招标金额
            CcPrjCostOverview ccPrjCostOverview = CcPrjCostOverview.selectByWhere(new Where().eq(CcPrjCostOverview.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjCostOverview.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, ccPrjCbsTempalteNodeId)).get(0);
            BigDecimal bidAmt = ccPrjCostOverview.getBidAmt() != null ? ccPrjCostOverview.getBidAmt() : BigDecimal.ZERO;
            BigDecimal bidAmtSumBig = bidAmtSum.add(bidAmt);

            // 2.查询项目成本统览此成本科目的概算
            BigDecimal bidAmtInCbs = BigDecimal.ZERO;
            BigDecimal cbsAmt2 = ccPrjCostOverview.getCbs2Amt() != null ? ccPrjCostOverview.getCbs2Amt() : BigDecimal.ZERO;
            bidAmtInCbs = bidAmtInCbs.add(cbsAmt2);

            String ccPrjCostOverviewId = ccPrjCostOverview.getId();

            // 3.对比已招标金额和概算，若招标金额大于概算金额则提示
            // 比较priceLimitSumBD和bidAmtInCbs
            int comparisonResult = bidAmtSumBig.compareTo(bidAmtInCbs);
            if (comparisonResult > 0) {
                throw new BaseException("已招标金额大于概算金额！");
            }

            // 4.存储成本统览关联明细
            CcPrjCostOverviewToDtl ccPrjCostOverviewToDtl = CcPrjCostOverviewToDtl.insertData();
            ccPrjCostOverviewToDtl.setCcPrjCostOverviewId(ccPrjCostOverviewId);
            ccPrjCostOverviewToDtl.setTrxAmt(priceLimit);
            ccPrjCostOverviewToDtl.setEntCode("CC_BID");
            ccPrjCostOverviewToDtl.setEntityRecordId(csCommId);
            ccPrjCostOverviewToDtl.updateById();

            // 5.招标同步到成本统览已招标
            ccPrjCostOverview.setBidAmt(bidAmtSumBig);
            ccPrjCostOverview.updateById();

            String ccPrjCostOverviewPid = ccPrjCostOverview.getCcPrjCostOverviewPid();
            recalculatePlanTotalCost(ccPrjCostOverviewPid, "BID_AMT");
        }
    }

    /**
     * 更新招标
     */
    public void updateBid() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcBid ccBid = CcBid.selectById(csCommId);
            //项目
            String ccPrjId = ccBid.getCcPrjId();
            // 此次更新的招标金额
            BigDecimal priceLimit = ccBid.getPriceLimit();
            // 此次更新的成本科目
            String ccPrjCbsTempalteNodeId = ccBid.getCcPrjCbsTempalteNodeId();


            // 1.通过实体记录id查询此实体记录已招标金额
            List<CcPrjCostOverviewToDtl> ccPrjCostOverviewToDtls = CcPrjCostOverviewToDtl.selectByWhere(new Where().eq(CcPrjCostOverviewToDtl.Cols.ENTITY_RECORD_ID, csCommId));
            String ccPrjCostOverviewId = null;
            String entCode = null;
            String ccPrjCostOverviewPid = null;
            String ccPrjCostOverviewNowPid = null;
            for (CcPrjCostOverviewToDtl ccPrjCostOverviewToDtl : ccPrjCostOverviewToDtls) {
                ccPrjCostOverviewId = ccPrjCostOverviewToDtl.getCcPrjCostOverviewId();
                BigDecimal trxAmt = ccPrjCostOverviewToDtl.getTrxAmt();
                entCode = ccPrjCostOverviewToDtl.getEntCode();

                // 2.撤回此实体记录的招标金额
                // 2.1通过成本统览Id找到上次招标对应的成本统览记录
                CcPrjCostOverview ccPrjCostOverview = CcPrjCostOverview.selectById(ccPrjCostOverviewId);
                //上次更新的成本科目
                String copyFromPrjStructNodeId = ccPrjCostOverview.getCopyFromPrjStructNodeId();
                ccPrjCostOverviewPid = ccPrjCostOverview.getCcPrjCostOverviewPid();
                BigDecimal bidAmt = ccPrjCostOverview.getBidAmt(); // 上次已招标金额
                // 2.2减去先前已招标金额，获取原始已招标金额
                BigDecimal rawBidAmt = bidAmt.subtract(trxAmt); // 原始已招标金额
                ccPrjCostOverview.setBidAmt(rawBidAmt);
                ccPrjCostOverview.updateById();


                // 3.获取更新后此实体记录的招标金额,并更新
                CcPrjCostOverview ccPrjCostOverviewNow = CcPrjCostOverview.selectByWhere(new Where().eq(CcPrjCostOverview.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjCostOverview.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, ccPrjCbsTempalteNodeId)).get(0);
                String ccPrjCostOverviewNowId = ccPrjCostOverviewNow.getId();
                ccPrjCostOverviewNowPid = ccPrjCostOverviewNow.getCcPrjCostOverviewPid();
                BigDecimal bidAmt1 = ccPrjCostOverviewNow.getBidAmt() != null ? ccPrjCostOverviewNow.getBidAmt() : BigDecimal.ZERO;
                BigDecimal nowBidAmt = bidAmt1.add(priceLimit);
                ccPrjCostOverviewNow.setBidAmt(nowBidAmt);
                // 3.1比较已招标金额是否大于初设概算金额
                // 3.1.1查询项目成本统览此成本科目的概算
                BigDecimal bidAmtInCbs = BigDecimal.ZERO;
                BigDecimal cbsAmt2 = ccPrjCostOverviewNow.getCbs2Amt() != null ? ccPrjCostOverviewNow.getCbs2Amt() : BigDecimal.ZERO;
                bidAmtInCbs = bidAmtInCbs.add(cbsAmt2);

                int comparisonResult = nowBidAmt.compareTo(bidAmtInCbs);
                if (comparisonResult > 0) {
                    throw new BaseException("已招标金额大于概算金额！");
                }
                ccPrjCostOverviewNow.updateById();

                // 4.生成此次更新招标关联记录
                CcPrjCostOverviewToDtl newCcPrjCostOverviewToDtl = CcPrjCostOverviewToDtl.insertData();
                newCcPrjCostOverviewToDtl.setCcPrjCostOverviewId(ccPrjCostOverviewNowId);
                newCcPrjCostOverviewToDtl.setEntCode(entCode);
                newCcPrjCostOverviewToDtl.setEntityRecordId(csCommId);
                newCcPrjCostOverviewToDtl.setTrxAmt(priceLimit);
                newCcPrjCostOverviewToDtl.updateById();

                // 5.删除先前招标关联记录
                ccPrjCostOverviewToDtl.deleteById();
            }
            // 6.重算成本总览
            recalculatePlanTotalCost(ccPrjCostOverviewPid, "BID_AMT");
            recalculatePlanTotalCost(ccPrjCostOverviewNowPid, "BID_AMT");
        }
    }

    /**
     * 删除招标
     */
    public void deleteBid() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            Map<String, Object> valueMap = entityRecord.valueMap;
            // 1.通过实体记录id查询此实体记录已招标金额
            List<CcPrjCostOverviewToDtl> ccPrjCostOverviewToDtls = CcPrjCostOverviewToDtl.selectByWhere(new Where().eq(CcPrjCostOverviewToDtl.Cols.ENTITY_RECORD_ID, csCommId));
            String ccPrjCostOverviewId = null;
            String ccPrjCostOverviewPid = null;
            if (!SharedUtil.isEmpty(ccPrjCostOverviewToDtls)) {
                for (CcPrjCostOverviewToDtl ccPrjCostOverviewToDtl : ccPrjCostOverviewToDtls) {
                    ccPrjCostOverviewId = ccPrjCostOverviewToDtl.getCcPrjCostOverviewId();
                    BigDecimal trxAmt = ccPrjCostOverviewToDtl.getTrxAmt();

                    // 2.撤回此实体记录的招标金额
                    // 2.1通过成本统览Id找到此次招标对应的成本统览记录
                    CcPrjCostOverview ccPrjCostOverview = CcPrjCostOverview.selectById(ccPrjCostOverviewId);
                    ccPrjCostOverviewPid = ccPrjCostOverview.getCcPrjCostOverviewPid();
                    BigDecimal bidAmt = ccPrjCostOverview.getBidAmt(); // 已招标金额
                    // 2.2减去先前已招标金额，获取原始已招标金额,并更新
                    BigDecimal rawBidAmt = bidAmt.subtract(trxAmt); // 原始已招标金额

                    ccPrjCostOverview.setBidAmt(rawBidAmt);
                    ccPrjCostOverview.updateById();

                    // 3.删除先前招标关联记录
                    ccPrjCostOverviewToDtl.deleteById();
                }
            }
            // 4.重算成本总览
            recalculatePlanTotalCost(ccPrjCostOverviewPid, "BID_AMT");
        }
    }


    /**
     * 采购数据同步成本总览
     *
     * @throws Exception
     */
    public void poToCostOverview() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;

            // 本次合同项目，金额，成本科目
            CcPo ccPo = CcPo.selectById(csCommId);
            String ccPrjId = ccPo.getCcPrjId();
            BigDecimal trxAmt = ccPo.getTrxAmt();
            String ccPrjCbsTempalteNodeId = ccPo.getCcPrjCbsTempalteNodeId();

            // 0.填入初始合同金额
            ccPo.setTrxAmtInit(trxAmt);
            ccPo.updateById();

            BigDecimal purchaseAmtInBidSum = trxAmt;
            // 1.查询项目此成本科目已采购金额
            CcPrjCostOverview ccPrjCostOverview = CcPrjCostOverview.selectByWhere(new Where().eq(CcPrjCostOverview.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjCostOverview.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, ccPrjCbsTempalteNodeId)).get(0);
            BigDecimal PurchaseAmt = ccPrjCostOverview.getPurchaseAmt() != null ? ccPrjCostOverview.getPurchaseAmt() : BigDecimal.ZERO;
            purchaseAmtInBidSum = purchaseAmtInBidSum.add(PurchaseAmt);

            // 2.查询项目概算中的已招标额
            BigDecimal bidAmt = ccPrjCostOverview.getBidAmt() != null ? ccPrjCostOverview.getBidAmt() : BigDecimal.ZERO;

            String ccPrjCostOverviewId = ccPrjCostOverview.getId();

            // 3.对比已招标金额和已采购金额，若已采购金额大于已招标金额则提示
            // 比较PurchaseAmtSum和bidAmt
            int comparisonResult = purchaseAmtInBidSum.compareTo(bidAmt);
            if (comparisonResult > 0) {
                throw new BaseException("合同额大于已招标额！");
            }

            // 4.存储成本统览关联明细
            CcPrjCostOverviewToDtl ccPrjCostOverviewToDtl = CcPrjCostOverviewToDtl.insertData();
            ccPrjCostOverviewToDtl.setCcPrjCostOverviewId(ccPrjCostOverviewId);
            ccPrjCostOverviewToDtl.setTrxAmt(trxAmt);
            ccPrjCostOverviewToDtl.setEntCode("CC_PO");
            ccPrjCostOverviewToDtl.setEntityRecordId(csCommId);
            ccPrjCostOverviewToDtl.updateById();

            // 5.招标同步到成本统览已招标
            ccPrjCostOverview.setPurchaseAmt(purchaseAmtInBidSum);
            ccPrjCostOverview.updateById();

            String ccPrjCostOverviewPid = ccPrjCostOverview.getCcPrjCostOverviewPid();
            recalculatePlanTotalCost(ccPrjCostOverviewPid, "PURCHASE_AMT");
        }
    }

    /**
     * 更新采购
     */
    public void updatePo() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcPo ccPo = CcPo.selectById(csCommId);
            // 项目ID和成本科目
            String ccPrjId = ccPo.getCcPrjId();
            String ccPrjCbsTempalteNodeId = ccPo.getCcPrjCbsTempalteNodeId();
            // 此次更新的采购金额
            BigDecimal trxAmt = ccPo.getTrxAmt();

            // 0.更新初始合同金额
            ccPo.setTrxAmtInit(trxAmt);
            ccPo.updateById();

            // 1.通过实体记录id查询此实体记录已采购金额
            List<CcPrjCostOverviewToDtl> ccPrjCostOverviewToDtls = CcPrjCostOverviewToDtl.selectByWhere(new Where().eq(CcPrjCostOverviewToDtl.Cols.ENTITY_RECORD_ID, csCommId));
            String ccPrjCostOverviewId = null;
            String entCode = null;
            String ccPrjCostOverviewPid = null;
            String ccPrjCostOverviewNowPid = null;
            for (CcPrjCostOverviewToDtl ccPrjCostOverviewToDtl : ccPrjCostOverviewToDtls) {
                ccPrjCostOverviewId = ccPrjCostOverviewToDtl.getCcPrjCostOverviewId();
                BigDecimal rawTrxAmt = ccPrjCostOverviewToDtl.getTrxAmt();
                entCode = ccPrjCostOverviewToDtl.getEntCode();

                // 2.撤回此实体记录的招标金额
                // 2.1通过成本统览Id找到上次采购对应的成本统览记录
                CcPrjCostOverview ccPrjCostOverview = CcPrjCostOverview.selectById(ccPrjCostOverviewId);
                //上次更新的成本科目
                String copyFromPrjStructNodeId = ccPrjCostOverview.getCopyFromPrjStructNodeId();
                ccPrjCostOverviewPid = ccPrjCostOverview.getCcPrjCostOverviewPid();
                BigDecimal purchaseAmtInBid = ccPrjCostOverview.getPurchaseAmt(); // 上次已采购金额
                // 2.2减去先前已采购金额，获取原始已采购金额
                BigDecimal rawPurchaseAmt = purchaseAmtInBid.subtract(rawTrxAmt); // 原始已采购金额
                ccPrjCostOverview.setPurchaseAmt(rawPurchaseAmt);
                ccPrjCostOverview.updateById();


                // 3.获取更新后此实体记录的采购金额,并更新
                CcPrjCostOverview ccPrjCostOverviewNow = CcPrjCostOverview.selectByWhere(new Where().eq(CcPrjCostOverview.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjCostOverview.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, ccPrjCbsTempalteNodeId)).get(0);
                String ccPrjCostOverviewNowId = ccPrjCostOverviewNow.getId();
                ccPrjCostOverviewNowPid = ccPrjCostOverviewNow.getCcPrjCostOverviewPid();
                BigDecimal purchaseAmtInBid1 = ccPrjCostOverviewNow.getPurchaseAmt() != null ? ccPrjCostOverviewNow.getPurchaseAmt() : BigDecimal.ZERO;
                BigDecimal nowBidAmt = purchaseAmtInBid1.add(trxAmt);
                ccPrjCostOverviewNow.setPurchaseAmt(nowBidAmt);
                // 3.1比较已采购金额是否大于已招标金额
                // 3.1.1查询项目成本统览此成本科目的已招标金额
                BigDecimal bidAmt = ccPrjCostOverviewNow.getBidAmt() != null ? ccPrjCostOverviewNow.getBidAmt() : BigDecimal.ZERO;

                int comparisonResult = nowBidAmt.compareTo(bidAmt);
                if (comparisonResult > 0) {
                    throw new BaseException("合同额大于已招标金额！");
                }
                ccPrjCostOverviewNow.updateById();

                // 4.生成此次更新采购关联记录
                CcPrjCostOverviewToDtl newCcPrjCostOverviewToDtl = CcPrjCostOverviewToDtl.insertData();
                newCcPrjCostOverviewToDtl.setCcPrjCostOverviewId(ccPrjCostOverviewNowId);
                newCcPrjCostOverviewToDtl.setEntCode(entCode);
                newCcPrjCostOverviewToDtl.setEntityRecordId(csCommId);
                newCcPrjCostOverviewToDtl.setTrxAmt(trxAmt);
                newCcPrjCostOverviewToDtl.updateById();

                // 5.删除先前采购关联记录
                ccPrjCostOverviewToDtl.deleteById();
            }
            // 6.重算成本总览
            recalculatePlanTotalCost(ccPrjCostOverviewPid, "PURCHASE_AMT");
            recalculatePlanTotalCost(ccPrjCostOverviewNowPid, "PURCHASE_AMT");
        }
    }

    /**
     * 新增合同变更
     */
    public void poChange() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            CcPoChange ccPoChange = CcPoChange.selectById(csCommId);
            String ccPoChangePickId = ccPoChange.getCcPoChangePickId();// 变更的增减
            BigDecimal changeTrxAmt = ccPoChange.getTrxAmt();// 变更金额
            String ccPoId = ccPoChange.getCcPoId();// 合同ID
            CcPo ccPo = CcPo.selectById(ccPoId);
            BigDecimal poTrxAmt = ccPo.getTrxAmt(); //变更前的合同额
            BigDecimal nowTrxAmt = BigDecimal.ZERO; //现在的合同额
            if ("ADD".equals(ccPoChangePickId)) {
                nowTrxAmt = poTrxAmt.add(changeTrxAmt);
            } else if ("SUB".equals(ccPoChangePickId)) {
                nowTrxAmt = poTrxAmt.subtract(changeTrxAmt);
            }
            ccPo.setTrxAmt(nowTrxAmt);
            updatePoByEntity(ccPoId, nowTrxAmt);
            CcPoToDtl ccPoToDtl = CcPoToDtl.insertData();
            ccPoToDtl.setCcPoId(ccPoId);
            ccPoToDtl.setTrxAmt(changeTrxAmt);
            ccPoToDtl.setEntityRecordId(csCommId);
            ccPoToDtl.setCcPoChangePickId(ccPoChangePickId);
            ccPoToDtl.updateById();
        }
    }

    /**
     * 更新合同变更
     */
    public void updatePoChange() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            CcPoChange ccPoChange = CcPoChange.selectById(csCommId);

            String ccPoChangePickId = ccPoChange.getCcPoChangePickId();// 变更的增减
            BigDecimal changeTrxAmt = ccPoChange.getTrxAmt();// 变更金额
            String ccPoId = ccPoChange.getCcPoId();// 合同ID
            CcPo ccPo = CcPo.selectById(ccPoId);
            BigDecimal poTrxAmt = ccPo.getTrxAmt(); //变更前的合同额

            CcPoToDtl ccPoToDtl = CcPoToDtl.selectOneByWhere(new Where().eq(CcPoToDtl.Cols.CC_PO_ID, ccPoId).eq(CcPoToDtl.Cols.ENTITY_RECORD_ID, csCommId));
            BigDecimal recordTrxAmt = ccPoToDtl.getTrxAmt();//记录合同变更额
            String recordPoChangePickId = ccPoToDtl.getCcPoChangePickId();//记录变更的增减

            //撤回此合同变更
            BigDecimal nowTrxAmt = BigDecimal.ZERO; //现在的合同额
            if ("ADD".equals(recordPoChangePickId)) {
                nowTrxAmt = poTrxAmt.subtract(recordTrxAmt);
            } else if ("SUB".equals(recordPoChangePickId)) {
                nowTrxAmt = poTrxAmt.add(recordTrxAmt);
            }
            // 新的合同变更
            if ("ADD".equals(ccPoChangePickId)) {
                nowTrxAmt = nowTrxAmt.add(changeTrxAmt);
            } else if ("SUB".equals(ccPoChangePickId)) {
                nowTrxAmt = nowTrxAmt.subtract(changeTrxAmt);
            }
            ccPo.setTrxAmt(nowTrxAmt);
            updatePoByEntity(ccPoId, nowTrxAmt);


            //生成此次合同关联记录
            CcPoToDtl newCcPoToDtl = CcPoToDtl.insertData();
            newCcPoToDtl.setCcPoId(ccPoId);
            newCcPoToDtl.setTrxAmt(changeTrxAmt);
            newCcPoToDtl.setEntityRecordId(csCommId);
            newCcPoToDtl.setCcPoChangePickId(ccPoChangePickId);
            newCcPoToDtl.updateById();

            ccPoToDtl.deleteById();

        }
    }

    /**
     * 删除合同变更
     */
    public void deletePoChange() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            CcPoChange ccPoChange = CcPoChange.selectById(csCommId);

            String ccPoId = ccPoChange.getCcPoId();// 合同ID
            CcPo ccPo = CcPo.selectById(ccPoId);
            BigDecimal poTrxAmt = ccPo.getTrxAmt(); //变更前的合同额

            CcPoToDtl ccPoToDtl = CcPoToDtl.selectOneByWhere(new Where().eq(CcPoToDtl.Cols.CC_PO_ID, ccPoId).eq(CcPoToDtl.Cols.ENTITY_RECORD_ID, csCommId));
            BigDecimal recordTrxAmt = ccPoToDtl.getTrxAmt();//记录合同变更额
            String recordPoChangePickId = ccPoToDtl.getCcPoChangePickId();//记录变更的增减

            //撤回此合同变更
            BigDecimal nowTrxAmt = BigDecimal.ZERO; //现在的合同额
            if ("ADD".equals(recordPoChangePickId)) {
                nowTrxAmt = poTrxAmt.subtract(recordTrxAmt);
            } else if ("SUB".equals(recordPoChangePickId)) {
                nowTrxAmt = poTrxAmt.add(recordTrxAmt);
            }

            ccPo.setTrxAmt(nowTrxAmt);
            updatePoByEntity(ccPoId, nowTrxAmt);

            //删除合同关联记录
            ccPoToDtl.deleteById();

        }
    }

    /**
     * 根据实体记录和交易金额更新采购信息
     *
     * @param csCommId 实体记录ID(合同ID)
     * @param trxAmt   交易金额
     */
    private void updatePoByEntity(String csCommId, BigDecimal trxAmt) {
        CcPo ccPo = CcPo.selectById(csCommId);
        String ccPrjId = ccPo.getCcPrjId();
        String ccPrjCbsTempalteNodeId = ccPo.getCcPrjCbsTempalteNodeId();

        // 0.更新合同金额
        ccPo.setTrxAmt(trxAmt);
        ccPo.updateById();

        // 1.通过实体记录id查询此实体记录已采购金额
        List<CcPrjCostOverviewToDtl> ccPrjCostOverviewToDtls = CcPrjCostOverviewToDtl.selectByWhere(new Where().eq(CcPrjCostOverviewToDtl.Cols.ENTITY_RECORD_ID, csCommId));
        String ccPrjCostOverviewId = null;
        String entCode = null;
        String ccPrjCostOverviewPid = null;
        String ccPrjCostOverviewNowPid = null;
        for (CcPrjCostOverviewToDtl ccPrjCostOverviewToDtl : ccPrjCostOverviewToDtls) {
            ccPrjCostOverviewId = ccPrjCostOverviewToDtl.getCcPrjCostOverviewId();
            BigDecimal rawTrxAmt = ccPrjCostOverviewToDtl.getTrxAmt();
            entCode = ccPrjCostOverviewToDtl.getEntCode();

            // 2.撤回此实体记录的招标金额
            CcPrjCostOverview ccPrjCostOverview = CcPrjCostOverview.selectById(ccPrjCostOverviewId);
            String copyFromPrjStructNodeId = ccPrjCostOverview.getCopyFromPrjStructNodeId();
            ccPrjCostOverviewPid = ccPrjCostOverview.getCcPrjCostOverviewPid();
            BigDecimal purchaseAmtInBid = ccPrjCostOverview.getPurchaseAmt(); // 上次已采购金额
            BigDecimal rawPurchaseAmt = purchaseAmtInBid.subtract(rawTrxAmt); // 原始已采购金额
            ccPrjCostOverview.setPurchaseAmt(rawPurchaseAmt);
            ccPrjCostOverview.updateById();

            // 3.获取更新后此实体记录的采购金额,并更新
            CcPrjCostOverview ccPrjCostOverviewNow = CcPrjCostOverview.selectByWhere(new Where().eq(CcPrjCostOverview.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjCostOverview.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, ccPrjCbsTempalteNodeId)).get(0);
            String ccPrjCostOverviewNowId = ccPrjCostOverviewNow.getId();
            ccPrjCostOverviewNowPid = ccPrjCostOverviewNow.getCcPrjCostOverviewPid();
            BigDecimal purchaseAmtInBid1 = ccPrjCostOverviewNow.getPurchaseAmt() != null ? ccPrjCostOverviewNow.getPurchaseAmt() : BigDecimal.ZERO;
            BigDecimal nowBidAmt = purchaseAmtInBid1.add(trxAmt);
            ccPrjCostOverviewNow.setPurchaseAmt(nowBidAmt);

            int comparisonResult = nowBidAmt.compareTo(ccPrjCostOverviewNow.getBidAmt() != null ? ccPrjCostOverviewNow.getBidAmt() : BigDecimal.ZERO);
            if (comparisonResult > 0) {
                throw new BaseException("合同额大于已招标金额！");
            }
            ccPrjCostOverviewNow.updateById();

            // 4.生成此次更新采购关联记录
            CcPrjCostOverviewToDtl newCcPrjCostOverviewToDtl = CcPrjCostOverviewToDtl.insertData();
            newCcPrjCostOverviewToDtl.setCcPrjCostOverviewId(ccPrjCostOverviewNowId);
            newCcPrjCostOverviewToDtl.setEntCode(entCode);
            newCcPrjCostOverviewToDtl.setEntityRecordId(csCommId);
            newCcPrjCostOverviewToDtl.setTrxAmt(trxAmt);
            newCcPrjCostOverviewToDtl.updateById();

            // 5.删除先前采购关联记录
            ccPrjCostOverviewToDtl.deleteById();
        }
        // 6.重算成本总览
        recalculatePlanTotalCost(ccPrjCostOverviewPid, "PURCHASE_AMT");
        recalculatePlanTotalCost(ccPrjCostOverviewNowPid, "PURCHASE_AMT");
    }


    /**
     * 删除采购
     */
    public void deletePo() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            Map<String, Object> valueMap = entityRecord.valueMap;
            // 1.通过实体记录id查询此实体记录已采购金额
            List<CcPrjCostOverviewToDtl> ccPrjCostOverviewToDtls = CcPrjCostOverviewToDtl.selectByWhere(new Where().eq(CcPrjCostOverviewToDtl.Cols.ENTITY_RECORD_ID, csCommId));
            String ccPrjCostOverviewId = null;
            String ccPrjCostOverviewPid = null;
            if (!SharedUtil.isEmpty(ccPrjCostOverviewToDtls)) {
                for (CcPrjCostOverviewToDtl ccPrjCostOverviewToDtl : ccPrjCostOverviewToDtls) {
                    ccPrjCostOverviewId = ccPrjCostOverviewToDtl.getCcPrjCostOverviewId();
                    BigDecimal rawTrxAmt = ccPrjCostOverviewToDtl.getTrxAmt();

                    // 2.撤回此实体记录的采购金额
                    // 2.1通过成本统览Id找到此次采购对应的成本统览记录
                    CcPrjCostOverview ccPrjCostOverview = CcPrjCostOverview.selectById(ccPrjCostOverviewId);
                    ccPrjCostOverviewPid = ccPrjCostOverview.getCcPrjCostOverviewPid();
                    BigDecimal purchaseAmtInBid = ccPrjCostOverview.getPurchaseAmt(); // 已采购金额
                    // 2.2减去先前已采购金额，获取原始已采购金额
                    BigDecimal rawPurchaseAmt = purchaseAmtInBid.subtract(rawTrxAmt); // 原始已采购金额

                    ccPrjCostOverview.setPurchaseAmt(rawPurchaseAmt);
                    ccPrjCostOverview.updateById();

                    // 3.删除先前采购关联记录
                    ccPrjCostOverviewToDtl.deleteById();
                }
            }
            // 4.重算成本总览
            recalculatePlanTotalCost(ccPrjCostOverviewPid, "PURCHASE_AMT");
        }
    }

    /**
     * 完成产值数据同步成本总览
     *
     * @throws Exception
     */
    public void gdpToCostOverview() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;

            // 本次完成产值项目，金额，成本科目
            CcGdp ccGdp = CcGdp.selectById(csCommId);
            String ccPrjId = ccGdp.getCcPrjId();
            BigDecimal trxAmt = ccGdp.getTrxAmt();
            String ccPrjCbsTempalteNodeId = ccGdp.getCcPrjCbsTempalteNodeId();

            BigDecimal completeAmtInPoSum = trxAmt;
            // 1.查询项目此成本科目已完成产值金额
            CcPrjCostOverview ccPrjCostOverview = CcPrjCostOverview.selectByWhere(new Where().eq(CcPrjCostOverview.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjCostOverview.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, ccPrjCbsTempalteNodeId)).get(0);
            BigDecimal completeAmtInPo = ccPrjCostOverview.getCompleteAmt() != null ? ccPrjCostOverview.getCompleteAmt() : BigDecimal.ZERO;
            completeAmtInPoSum = completeAmtInPoSum.add(completeAmtInPo);

            // 2.查询项目采购中的已完成产值额
            BigDecimal purchaseAmtInBid = ccPrjCostOverview.getPurchaseAmt() != null ? ccPrjCostOverview.getPurchaseAmt() : BigDecimal.ZERO;

            String ccPrjCostOverviewId = ccPrjCostOverview.getId();

            // 3.对比已采购金额和已完成产值金额，若已完成产值金额大于已采购金额则提示
            // 比较completeAmtInPoSum和purchaseAmtInBid
            int comparisonResult = completeAmtInPoSum.compareTo(purchaseAmtInBid);
            if (comparisonResult > 0) {
                throw new BaseException("已完成产值金额大于合同额！");
            }

            // 4.存储成本统览关联明细
            CcPrjCostOverviewToDtl ccPrjCostOverviewToDtl = CcPrjCostOverviewToDtl.insertData();
            ccPrjCostOverviewToDtl.setCcPrjCostOverviewId(ccPrjCostOverviewId);
            ccPrjCostOverviewToDtl.setTrxAmt(trxAmt);
            ccPrjCostOverviewToDtl.setEntCode("CC_PO");
            ccPrjCostOverviewToDtl.setEntityRecordId(csCommId);
            ccPrjCostOverviewToDtl.updateById();

            // 5.招标同步到成本统览已完成产值
            ccPrjCostOverview.setCompleteAmt(completeAmtInPoSum);
            ccPrjCostOverview.updateById();

            String ccPrjCostOverviewPid = ccPrjCostOverview.getCcPrjCostOverviewPid();
            recalculatePlanTotalCost(ccPrjCostOverviewPid, "COMPLETE_AMT");
        }
    }

    /**
     * 更新产值
     */
    public void updateGdp() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcGdp ccGdp = CcGdp.selectById(csCommId);
            // 项目ID和成本科目
            String ccPrjId = ccGdp.getCcPrjId();
            String ccPrjCbsTempalteNodeId = ccGdp.getCcPrjCbsTempalteNodeId();
            // 此次更新的已完成产值金额
            BigDecimal trxAmt = ccGdp.getTrxAmt();

            // 通过实体记录id查询此实体记录已完成产值金额
            List<CcPrjCostOverviewToDtl> ccPrjCostOverviewToDtls = CcPrjCostOverviewToDtl.selectByWhere(new Where().eq(CcPrjCostOverviewToDtl.Cols.ENTITY_RECORD_ID, csCommId));
            String ccPrjCostOverviewPid = null;
            String ccPrjCostOverviewNowPid = null;
            String entCode = null;
            for (CcPrjCostOverviewToDtl ccPrjCostOverviewToDtl : ccPrjCostOverviewToDtls) {
                String ccPrjCostOverviewId = ccPrjCostOverviewToDtl.getCcPrjCostOverviewId();
                BigDecimal rawTrxAmt = ccPrjCostOverviewToDtl.getTrxAmt();
                entCode = ccPrjCostOverviewToDtl.getEntCode();

                // 2.撤回此实体记录的产值金额
                CcPrjCostOverview ccPrjCostOverview = CcPrjCostOverview.selectById(ccPrjCostOverviewId);
                String copyFromPrjStructNodeId = ccPrjCostOverview.getCopyFromPrjStructNodeId();
                ccPrjCostOverviewPid = ccPrjCostOverview.getCcPrjCostOverviewPid();
                BigDecimal completeAmtInPo = ccPrjCostOverview.getCompleteAmt(); // 上次已完成产值金额
                BigDecimal rawCompleteAmt = completeAmtInPo.subtract(rawTrxAmt); // 原始已完成产值金额
                ccPrjCostOverview.setCompleteAmt(rawCompleteAmt);
                ccPrjCostOverview.updateById();

                // 3.获取更新后此实体记录的已完成产值金额,并更新
                CcPrjCostOverview ccPrjCostOverviewNow = CcPrjCostOverview.selectByWhere(new Where().eq(CcPrjCostOverview.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjCostOverview.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, ccPrjCbsTempalteNodeId)).get(0);
                String ccPrjCostOverviewNowId = ccPrjCostOverviewNow.getId();
                ccPrjCostOverviewNowPid = ccPrjCostOverviewNow.getCcPrjCostOverviewPid();
                BigDecimal completeAmtInBid1 = ccPrjCostOverviewNow.getCompleteAmt() != null ? ccPrjCostOverviewNow.getCompleteAmt() : BigDecimal.ZERO;
                BigDecimal nowCompleteAmt = completeAmtInBid1.add(trxAmt);
                ccPrjCostOverviewNow.setCompleteAmt(nowCompleteAmt);
                // 3.1比较已完成产值金额是否大于已招标金额
                BigDecimal bidAmt = ccPrjCostOverviewNow.getBidAmt() != null ? ccPrjCostOverviewNow.getBidAmt() : BigDecimal.ZERO;

                int comparisonResult = nowCompleteAmt.compareTo(bidAmt);
                if (comparisonResult > 0) {
                    throw new BaseException("已完成产值金额大于已招标金额！");
                }
                ccPrjCostOverviewNow.updateById();

                // 4.生成此次更新产值关联记录
                CcPrjCostOverviewToDtl newCcPrjCostOverviewToDtl = CcPrjCostOverviewToDtl.insertData();
                newCcPrjCostOverviewToDtl.setCcPrjCostOverviewId(ccPrjCostOverviewNowId);
                newCcPrjCostOverviewToDtl.setEntCode(entCode);
                newCcPrjCostOverviewToDtl.setEntityRecordId(csCommId);
                newCcPrjCostOverviewToDtl.setTrxAmt(trxAmt);
                newCcPrjCostOverviewToDtl.updateById();

                // 5.删除先前产值关联记录
                ccPrjCostOverviewToDtl.deleteById();
            }
            // 6.重算成本总览
            recalculatePlanTotalCost(ccPrjCostOverviewPid, "COMPLETE_AMT");
            recalculatePlanTotalCost(ccPrjCostOverviewNowPid, "COMPLETE_AMT");
        }
    }


    /**
     * 删除产值
     */
    public void deleteGdp() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            Map<String, Object> valueMap = entityRecord.valueMap;
            // 1.通过实体记录id查询此实体记录已完成产值金额
            List<CcPrjCostOverviewToDtl> ccPrjCostOverviewToDtls = CcPrjCostOverviewToDtl.selectByWhere(new Where().eq(CcPrjCostOverviewToDtl.Cols.ENTITY_RECORD_ID, csCommId));
            String ccPrjCostOverviewId = null;
            String ccPrjCostOverviewPid = null;
            if (!SharedUtil.isEmpty(ccPrjCostOverviewToDtls)) {
                for (CcPrjCostOverviewToDtl ccPrjCostOverviewToDtl : ccPrjCostOverviewToDtls) {
                    ccPrjCostOverviewId = ccPrjCostOverviewToDtl.getCcPrjCostOverviewId();
                    BigDecimal rawTrxAmt = ccPrjCostOverviewToDtl.getTrxAmt();

                    // 2.撤回此实体记录的已完成产值
                    // 2.1通过成本统览Id找到此次已完成产值对应的成本统览记录
                    CcPrjCostOverview ccPrjCostOverview = CcPrjCostOverview.selectById(ccPrjCostOverviewId);
                    ccPrjCostOverviewPid = ccPrjCostOverview.getCcPrjCostOverviewPid();
                    BigDecimal completeAmtInPo = ccPrjCostOverview.getCompleteAmt(); // 已完成产值金额
                    // 2.2减去先前已完成产值金额，获取原始已完成产值金额
                    BigDecimal rawCompleteAmt = completeAmtInPo.subtract(rawTrxAmt); // 原始已完成产值金额

                    ccPrjCostOverview.setCompleteAmt(rawCompleteAmt);
                    ccPrjCostOverview.updateById();

                    // 3.删除先前已完成产值关联记录
                    ccPrjCostOverviewToDtl.deleteById();
                }
            }
            // 4.重算成本总览
            recalculatePlanTotalCost(ccPrjCostOverviewPid, "COMPLETE_AMT");
        }
    }

    /**
     * 同步支付申请数据到成本总览
     *
     * @throws Exception
     */
    public void payReqToCostOverview() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;

            // 本次支付申请项目，金额，成本科目
            CcPayReq ccPayReq = CcPayReq.selectById(csCommId);

            if ("DR".equals(ccPayReq.getStatus())) {
                continue;
            }

            String ccPrjId = ccPayReq.getCcPrjId();
            BigDecimal trxAmt = ccPayReq.getTrxAmt();
            String ccPrjCbsTempalteNodeId = ccPayReq.getCcPrjCbsTempalteNodeId();

            BigDecimal reqPayAmtInPoSum = trxAmt;
            // 1.查询项目此成本科目已申请支付金额
            CcPrjCostOverview ccPrjCostOverview = CcPrjCostOverview.selectByWhere(new Where().eq(CcPrjCostOverview.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjCostOverview.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, ccPrjCbsTempalteNodeId)).get(0);
            BigDecimal reqPayAmtInPo = ccPrjCostOverview.getReqPayAmt() != null ? ccPrjCostOverview.getReqPayAmt() : BigDecimal.ZERO;
            reqPayAmtInPoSum = reqPayAmtInPoSum.add(reqPayAmtInPo);

            // 2.查询项目采购中的已完成产值额
            BigDecimal purchaseAmtInBid = ccPrjCostOverview.getPurchaseAmt() != null ? ccPrjCostOverview.getPurchaseAmt() : BigDecimal.ZERO;

            // 3.对比已申请支付金额和已完成产值金额，若已申请支付金额大于已采购金额则提示
            int comparisonResult = reqPayAmtInPoSum.compareTo(purchaseAmtInBid);
            if (comparisonResult > 0) {
                throw new BaseException("此次申请金额>（合同额-已申请额）");
            }

            String ccPrjCostOverviewId = ccPrjCostOverview.getId();

            // 4.存储成本统览关联明细
            CcPrjCostOverviewToDtl ccPrjCostOverviewToDtl = CcPrjCostOverviewToDtl.insertData();
            ccPrjCostOverviewToDtl.setCcPrjCostOverviewId(ccPrjCostOverviewId);
            ccPrjCostOverviewToDtl.setTrxAmt(trxAmt);
            ccPrjCostOverviewToDtl.setEntCode("CC_PAY_REQ");
            ccPrjCostOverviewToDtl.setEntityRecordId(csCommId);
            ccPrjCostOverviewToDtl.updateById();

            // 5.更新成本统览已申请支付金额
            ccPrjCostOverview.setReqPayAmt(reqPayAmtInPoSum);
            ccPrjCostOverview.updateById();

            String ccPrjCostOverviewPid = ccPrjCostOverview.getCcPrjCostOverviewPid();
            recalculatePlanTotalCost(ccPrjCostOverviewPid, "REQ_PAY_AMT");
        }
    }


    /**
     * 更新支付申请
     */
    public void updatePayReq() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcPayReq ccPayReq = CcPayReq.selectById(csCommId);

            if ("DR".equals(ccPayReq.getStatus())) {
                continue;
            }

            // 项目ID和成本科目
            String ccPrjId = ccPayReq.getCcPrjId();
            String ccPrjCbsTempalteNodeId = ccPayReq.getCcPrjCbsTempalteNodeId();
            // 此次更新的支付申请金额
            BigDecimal trxAmt = ccPayReq.getTrxAmt();

            // 通过实体记录id查询此实体记录已申请支付金额
            List<CcPrjCostOverviewToDtl> ccPrjCostOverviewToDtls = CcPrjCostOverviewToDtl.selectByWhere(new Where().eq(CcPrjCostOverviewToDtl.Cols.ENTITY_RECORD_ID, csCommId));
            String ccPrjCostOverviewId = null;
            String entCode = null;
            String ccPrjCostOverviewPid = null;
            String ccPrjCostOverviewNowPid = null;
            for (CcPrjCostOverviewToDtl ccPrjCostOverviewToDtl : ccPrjCostOverviewToDtls) {
                ccPrjCostOverviewId = ccPrjCostOverviewToDtl.getCcPrjCostOverviewId();
                BigDecimal rawTrxAmt = ccPrjCostOverviewToDtl.getTrxAmt();
                entCode = ccPrjCostOverviewToDtl.getEntCode();

                // 2.撤回此实体记录的支付申请金额
                CcPrjCostOverview ccPrjCostOverview = CcPrjCostOverview.selectById(ccPrjCostOverviewId);
                String copyFromPrjStructNodeId = ccPrjCostOverview.getCopyFromPrjStructNodeId();
                ccPrjCostOverviewPid = ccPrjCostOverview.getCcPrjCostOverviewPid();
                BigDecimal purchaseAmtInBid = ccPrjCostOverview.getPurchaseAmt(); // 上次已采购金额
                BigDecimal rawPurchaseAmt = purchaseAmtInBid.subtract(rawTrxAmt); // 原始已采购金额
                ccPrjCostOverview.setPurchaseAmt(rawPurchaseAmt);
                ccPrjCostOverview.updateById();

                // 3.获取更新后此实体记录的支付申请金额,并更新
                // 定位当前的项目成本统览
                CcPrjCostOverview ccPrjCostOverviewNow = CcPrjCostOverview.selectByWhere(new Where().eq(CcPrjCostOverview.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjCostOverview.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, ccPrjCbsTempalteNodeId)).get(0);
                String ccPrjCostOverviewNowId = ccPrjCostOverviewNow.getId();
                ccPrjCostOverviewNowPid = ccPrjCostOverviewNow.getCcPrjCostOverviewPid();
                BigDecimal purchaseAmtInBid1 = ccPrjCostOverviewNow.getPurchaseAmt() != null ? ccPrjCostOverviewNow.getPurchaseAmt() : BigDecimal.ZERO;
                BigDecimal nowBidAmt = purchaseAmtInBid1.add(trxAmt);
                ccPrjCostOverviewNow.setPurchaseAmt(nowBidAmt);
                // 3.1比较已采购金额是否大于已招标金额
                BigDecimal bidAmt = ccPrjCostOverviewNow.getBidAmt() != null ? ccPrjCostOverviewNow.getBidAmt() : BigDecimal.ZERO;

                int comparisonResult = nowBidAmt.compareTo(bidAmt);
                if (comparisonResult > 0) {
                    throw new BaseException("合同额大于已招标金额！");
                }
                ccPrjCostOverviewNow.updateById();

                // 4.生成此次更新支付申请关联记录
                CcPrjCostOverviewToDtl newCcPrjCostOverviewToDtl = CcPrjCostOverviewToDtl.insertData();
                newCcPrjCostOverviewToDtl.setCcPrjCostOverviewId(ccPrjCostOverviewNowId);
                newCcPrjCostOverviewToDtl.setEntCode(entCode);
                newCcPrjCostOverviewToDtl.setEntityRecordId(csCommId);
                newCcPrjCostOverviewToDtl.setTrxAmt(trxAmt);
                newCcPrjCostOverviewToDtl.updateById();

                // 5.删除先前支付申请关联记录
                ccPrjCostOverviewToDtl.deleteById();
            }
            // 6.重算成本总览
            recalculatePlanTotalCost(ccPrjCostOverviewPid, "REQ_PAY_AMT");
            recalculatePlanTotalCost(ccPrjCostOverviewNowPid, "REQ_PAY_AMT");
        }
    }


    /**
     * 删除支付申请
     */
    public void deletePayReq() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            // 1.通过实体记录id查询此实体记录已申请支付金额
            List<CcPrjCostOverviewToDtl> ccPrjCostOverviewToDtls = CcPrjCostOverviewToDtl.selectByWhere(new Where().eq(CcPrjCostOverviewToDtl.Cols.ENTITY_RECORD_ID, csCommId));
            String ccPrjCostOverviewId = null;
            String ccPrjCostOverviewPid = null;
            if (!SharedUtil.isEmpty(ccPrjCostOverviewToDtls)) {
                for (CcPrjCostOverviewToDtl ccPrjCostOverviewToDtl : ccPrjCostOverviewToDtls) {
                    ccPrjCostOverviewId = ccPrjCostOverviewToDtl.getCcPrjCostOverviewId();
                    BigDecimal rawTrxAmt = ccPrjCostOverviewToDtl.getTrxAmt();

                    // 2.撤回此实体记录的已申请支付金额
                    CcPrjCostOverview ccPrjCostOverview = CcPrjCostOverview.selectById(ccPrjCostOverviewId);
                    ccPrjCostOverviewPid = ccPrjCostOverview.getCcPrjCostOverviewPid();
                    BigDecimal reqPayAmtInPo = ccPrjCostOverview.getReqPayAmt(); // 已申请支付金额
                    BigDecimal rawReqPayAmt = reqPayAmtInPo.subtract(rawTrxAmt); // 原始已申请支付金额

                    ccPrjCostOverview.setReqPayAmt(rawReqPayAmt);
                    ccPrjCostOverview.updateById();

                    // 3.删除先前支付申请关联记录
                    ccPrjCostOverviewToDtl.deleteById();
                }
            }
            // 4.重算成本总览
            recalculatePlanTotalCost(ccPrjCostOverviewPid, "REQ_PAY_AMT");
        }
    }

    /**
     * 同步支付记录数据到成本总览(安徽)
     *
     * @throws Exception
     */
    public void payRecordToCostOverviewAnhui() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;

            CcPay ccPay = CcPay.selectById(csCommId);
            String ccPrjId = ccPay.getCcPrjId();
            BigDecimal trxAmt = ccPay.getTrxAmt();
            String ccPayReqId = ccPay.getCcPayReqId();
            String ccPoId = ccPay.getCcPoId();
            String ccPrjCbsTempalteNodeId = null;
            if (ccPayReqId == null) {
                CcPo ccPo = CcPo.selectById(ccPoId);
                ccPrjCbsTempalteNodeId = ccPo.getCcPrjCbsTempalteNodeId();
                BigDecimal resPayAmt = new BigDecimal(ccPo.getTrxAmt().toString());
                List<CcPayReq> ccPayReqs = CcPayReq.selectByWhere(new Where().eq(CcPayReq.Cols.CC_PO_ID, ccPoId).eq(CcPayReq.Cols.STATUS, "AP"));
                for (CcPayReq ccPayReq : ccPayReqs) {
                    resPayAmt = resPayAmt.subtract(ccPayReq.getTrxAmt());
                }
                List<CcPay> ccPays = CcPay.selectByWhere(new Where().eq(CcPay.Cols.CC_PO_ID, ccPoId));
                for (CcPay pay : ccPays) {
                    resPayAmt = resPayAmt.subtract(pay.getTrxAmt());
                }
                // 插入后调用扩展，所以已经减去当前插入的记录值
                if (resPayAmt.compareTo(BigDecimal.ZERO) < 0) {
                    throw new BaseException("此次支付金额>所关联合同剩余合同额");
                }
            } else {
                CcPayReq ccPayReq = CcPayReq.selectById(ccPayReqId);
                ccPrjCbsTempalteNodeId = ccPayReq.getCcPrjCbsTempalteNodeId();
                BigDecimal resPayAmt = new BigDecimal(ccPayReq.getTrxAmt().toString());
                List<CcPay> ccPays = CcPay.selectByWhere(new Where().eq(CcPay.Cols.CC_PAY_REQ_ID, ccPayReqId));
                for (CcPay pay : ccPays) {
                    resPayAmt = resPayAmt.subtract(pay.getTrxAmt());
                }
                // 插入后调用扩展，所以已经减去当前插入的记录值
                if (resPayAmt.compareTo(BigDecimal.ZERO) < 0) {
                    throw new BaseException("此次支付金额>所关联支付申请剩余申请额");
                }
            }
            ccPay.setCcPrjCbsTempalteNodeId(ccPrjCbsTempalteNodeId);
            ccPay.updateById();

            BigDecimal payAmtInReqSum = trxAmt;
            // 1.查询项目此成本科目已支付金额
            CcPrjCostOverview ccPrjCostOverview = CcPrjCostOverview.selectByWhere(new Where().eq(CcPrjCostOverview.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjCostOverview.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, ccPrjCbsTempalteNodeId)).get(0);
            BigDecimal payAmtInReq = ccPrjCostOverview.getPayAmt() != null ? ccPrjCostOverview.getPayAmt() : BigDecimal.ZERO;
            payAmtInReqSum = payAmtInReqSum.add(payAmtInReq);

            // 2.查询项目已申请支付金额
//            BigDecimal reqPayAmtInPo = ccPrjCostOverview.getReqPayAmt() != null ? ccPrjCostOverview.getReqPayAmt() : BigDecimal.ZERO;
            // 3.对比已支付金额和已申请支付金额，若已支付金额大于已申请支付金额则提示
//            if (payAmtInReqSum.compareTo(reqPayAmtInPo) > 0) {
//                throw new BaseException("此次支付金额>（合同额-已支付额）");
//            }

            // 4.存储成本统览关联明细
            CcPrjCostOverviewToDtl ccPrjCostOverviewToDtl = CcPrjCostOverviewToDtl.insertData();
            ccPrjCostOverviewToDtl.setCcPrjCostOverviewId(ccPrjCostOverview.getId());
            ccPrjCostOverviewToDtl.setTrxAmt(trxAmt);
            ccPrjCostOverviewToDtl.setEntCode("CC_PAY");
            ccPrjCostOverviewToDtl.setEntityRecordId(csCommId);
            ccPrjCostOverviewToDtl.updateById();

            // 5.更新成本统览已支付金额
            ccPrjCostOverview.setPayAmt(payAmtInReqSum);
            ccPrjCostOverview.updateById();

            String ccPrjCostOverviewPid = ccPrjCostOverview.getCcPrjCostOverviewPid();
            recalculatePlanTotalCost(ccPrjCostOverviewPid, "PAY_AMT");
        }
    }


    /**
     * 同步支付记录数据到成本总览
     *
     * @throws Exception
     */
    public void payRecordToCostOverview() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;

            // 本次支付记录，金额，成本科目
            CcPay ccPay = CcPay.selectById(csCommId);
            String ccPrjId = ccPay.getCcPrjId();
            BigDecimal trxAmt = ccPay.getTrxAmt();
            String ccPrjCbsTempalteNodeId = ccPay.getCcPrjCbsTempalteNodeId();

            BigDecimal payAmtInReqSum = trxAmt;
            // 1.查询项目此成本科目已支付金额
            CcPrjCostOverview ccPrjCostOverview = CcPrjCostOverview.selectByWhere(new Where().eq(CcPrjCostOverview.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjCostOverview.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, ccPrjCbsTempalteNodeId)).get(0);
            BigDecimal payAmtInReq = ccPrjCostOverview.getPayAmt() != null ? ccPrjCostOverview.getPayAmt() : BigDecimal.ZERO;
            payAmtInReqSum = payAmtInReqSum.add(payAmtInReq);

            // 2.查询项目已申请支付金额
            BigDecimal reqPayAmtInPo = ccPrjCostOverview.getReqPayAmt() != null ? ccPrjCostOverview.getReqPayAmt() : BigDecimal.ZERO;

            // 3.对比已支付金额和已申请支付金额，若已支付金额大于已申请支付金额则提示
            if (payAmtInReqSum.compareTo(reqPayAmtInPo) > 0) {
                throw new BaseException("此次支付金额>（合同额-已支付额）");
            }

            // 4.存储成本统览关联明细
            CcPrjCostOverviewToDtl ccPrjCostOverviewToDtl = CcPrjCostOverviewToDtl.insertData();
            ccPrjCostOverviewToDtl.setCcPrjCostOverviewId(ccPrjCostOverview.getId());
            ccPrjCostOverviewToDtl.setTrxAmt(trxAmt);
            ccPrjCostOverviewToDtl.setEntCode("CC_PAY");
            ccPrjCostOverviewToDtl.setEntityRecordId(csCommId);
            ccPrjCostOverviewToDtl.updateById();

            // 5.更新成本统览已支付金额
            ccPrjCostOverview.setPayAmt(payAmtInReqSum);
            ccPrjCostOverview.updateById();

            String ccPrjCostOverviewPid = ccPrjCostOverview.getCcPrjCostOverviewPid();
            recalculatePlanTotalCost(ccPrjCostOverviewPid, "PAY_AMT");
        }
    }


    /**
     * 更新支付记录
     */
    public void updatePayRecord() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcPay ccPay = CcPay.selectById(csCommId);
            // 项目ID和成本科目
            String ccPrjId = ccPay.getCcPrjId();
            String ccPrjCbsTempalteNodeId = ccPay.getCcPrjCbsTempalteNodeId();
            // 此次更新的支付金额
            BigDecimal trxAmt = ccPay.getTrxAmt();

            // 通过实体记录id查询此实体记录已支付金额
            List<CcPrjCostOverviewToDtl> ccPrjCostOverviewToDtls = CcPrjCostOverviewToDtl.selectByWhere(new Where().eq(CcPrjCostOverviewToDtl.Cols.ENTITY_RECORD_ID, csCommId));
            String ccPrjCostOverviewPid = null; // 初始化放在循环外部
            String ccPrjCostOverviewNowPid = null;
            String entCode = null;
            for (CcPrjCostOverviewToDtl ccPrjCostOverviewToDtl : ccPrjCostOverviewToDtls) {
                String ccPrjCostOverviewId = ccPrjCostOverviewToDtl.getCcPrjCostOverviewId();
                BigDecimal rawTrxAmt = ccPrjCostOverviewToDtl.getTrxAmt();
                entCode = ccPrjCostOverviewToDtl.getEntCode();

                // 2.撤回此实体记录的支付金额
                CcPrjCostOverview ccPrjCostOverview = CcPrjCostOverview.selectById(ccPrjCostOverviewId);
                String copyFromPrjStructNodeId = ccPrjCostOverview.getCopyFromPrjStructNodeId();
                ccPrjCostOverviewPid = ccPrjCostOverview.getCcPrjCostOverviewPid();
                BigDecimal payAmtInReq = ccPrjCostOverview.getPayAmt(); // 上次已支付金额
                BigDecimal rawPayAmt = payAmtInReq.subtract(rawTrxAmt); // 原始已支付金额
                ccPrjCostOverview.setPayAmt(rawPayAmt);
                ccPrjCostOverview.updateById();

                // 3.获取更新后此实体记录的支付金额,并更新
                // 定位当前的项目成本统览
                CcPrjCostOverview ccPrjCostOverviewNow = CcPrjCostOverview.selectByWhere(new Where().eq(CcPrjCostOverview.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjCostOverview.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, ccPrjCbsTempalteNodeId)).get(0);
                String ccPrjCostOverviewNowId = ccPrjCostOverviewNow.getId();
                ccPrjCostOverviewNowPid = ccPrjCostOverviewNow.getCcPrjCostOverviewPid();
                BigDecimal payAmtInBid1 = ccPrjCostOverviewNow.getPayAmt() != null ? ccPrjCostOverviewNow.getPayAmt() : BigDecimal.ZERO;
                BigDecimal nowPayAmt = payAmtInBid1.add(trxAmt);
                ccPrjCostOverviewNow.setPayAmt(nowPayAmt);
                // 3.1比较已支付金额是否大于已申请支付金额
                BigDecimal reqPayAmt = ccPrjCostOverviewNow.getReqPayAmt() != null ? ccPrjCostOverviewNow.getReqPayAmt() : BigDecimal.ZERO;

                int comparisonResult = nowPayAmt.compareTo(reqPayAmt);
                if (comparisonResult > 0) {
                    throw new BaseException("已支付金额大于已申请支付金额！");
                }
                ccPrjCostOverviewNow.updateById();

                // 4.生成此次更新支付记录关联记录
                CcPrjCostOverviewToDtl newCcPrjCostOverviewToDtl = CcPrjCostOverviewToDtl.insertData();
                newCcPrjCostOverviewToDtl.setCcPrjCostOverviewId(ccPrjCostOverviewNowId);
                newCcPrjCostOverviewToDtl.setEntCode(entCode);
                newCcPrjCostOverviewToDtl.setEntityRecordId(csCommId);
                newCcPrjCostOverviewToDtl.setTrxAmt(trxAmt);
                newCcPrjCostOverviewToDtl.updateById();

                // 5.删除先前支付记录关联记录
                ccPrjCostOverviewToDtl.deleteById();
            }
            // 6.重算成本总览
            recalculatePlanTotalCost(ccPrjCostOverviewPid, "PAY_AMT");
            recalculatePlanTotalCost(ccPrjCostOverviewNowPid, "PAY_AMT");
        }
    }


    /**
     * 删除支付记录
     */
    public void deletePayRecord() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            // 1.通过实体记录id查询此实体记录已支付金额
            List<CcPrjCostOverviewToDtl> ccPrjCostOverviewToDtls = CcPrjCostOverviewToDtl.selectByWhere(new Where().eq(CcPrjCostOverviewToDtl.Cols.ENTITY_RECORD_ID, csCommId));
            String ccPrjCostOverviewId = null;
            String ccPrjCostOverviewPid = null;
            if (!SharedUtil.isEmpty(ccPrjCostOverviewToDtls)) {
                for (CcPrjCostOverviewToDtl ccPrjCostOverviewToDtl : ccPrjCostOverviewToDtls) {
                    ccPrjCostOverviewId = ccPrjCostOverviewToDtl.getCcPrjCostOverviewId();
                    BigDecimal rawTrxAmt = ccPrjCostOverviewToDtl.getTrxAmt();

                    // 2.撤回此实体记录的已支付金额
                    CcPrjCostOverview ccPrjCostOverview = CcPrjCostOverview.selectById(ccPrjCostOverviewId);
                    ccPrjCostOverviewPid = ccPrjCostOverview.getCcPrjCostOverviewPid();
                    BigDecimal payAmtInReq = ccPrjCostOverview.getPayAmt(); // 已支付金额
                    BigDecimal rawPayAmt = payAmtInReq.subtract(rawTrxAmt); // 原始已支付金额

                    ccPrjCostOverview.setPayAmt(rawPayAmt);
                    ccPrjCostOverview.updateById();

                    // 3.删除先前支付记录关联记录
                    ccPrjCostOverviewToDtl.deleteById();
                }
            }
            // 4.重算成本总览
            recalculatePlanTotalCost(ccPrjCostOverviewPid, "PAY_AMT");
        }
    }

    /**
     * 编制计划
     */
    public void makePlan() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            Map<String, Object> valueMap = entityRecord.valueMap;
            String ccPrjId = valueMap.get("CC_PRJ_ID").toString();
            String ccPrjWbsTypeId = JdbcMapUtil.getString(valueMap, "CC_PRJ_WBS_TYPE_ID");
            String typeName = JdbcMapUtil.getString(myJdbcTemplate.queryForMap("select name->>'$.ZH_CN' from CC_PRJ_WBS_TYPE where id = ?", ccPrjWbsTypeId), "name->>'$.ZH_CN'");
            if ("ALL".equals(ccPrjWbsTypeId)) {

                //获取项目编制中是否有对应类型计划
                List<CcPrjStructNode> ccPrjStructNodes = CcPrjStructNode.selectByWhere(new Where().eq(CcPrjStructNode.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjStructNode.Cols.CC_PRJ_STRUCT_NODE_PID, null).in(CcPrjStructNode.Cols.STATUS, "DR", "APING", "DN").eq(CcPrjStructNode.Cols.CC_PRJ_WBS_TYPE_ID, ccPrjWbsTypeId).neq(CcPrjStructNode.Cols.ID, csCommId));
                if (!SharedUtil.isEmpty(ccPrjStructNodes)) {
                    throw new BaseException("已存在" + typeName + "编制中计划");
                }

                //获取项目编制中类型计划
                String sql = "select * from cc_prj_struct_node n where n.cc_prj_struct_node_pid is null and n.is_template = 0 AND n.is_wbs = 1 and n.STATUS in ('APING','DR','DN') and n.CC_PRJ_ID = ? and n.id != ?";
                List<Map<String, Object>> queryForList = myJdbcTemplate.queryForList(sql, ccPrjId, csCommId);
                ArrayList<String> typeList = new ArrayList<>();
                for (Map<String, Object> map : queryForList) {
                    String ccPrjWbsTypeId1 = map.get("CC_PRJ_WBS_TYPE_ID").toString();
                    typeList.add(ccPrjWbsTypeId1);
                }

                String joinedTypeList = typeList.stream()
                        .map(s -> "'" + s + "'")
                        .collect(Collectors.joining(","));

                CcPrjStructNode ccPrjStructNode = CcPrjStructNode.selectOneByWhere(new Where().eq(CcPrjStructNode.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjStructNode.Cols.CC_PRJ_STRUCT_NODE_PID, null).eq(CcPrjStructNode.Cols.IS_TEMPLATE, false).eq(CcPrjStructNode.Cols.IS_WBS, true).eq(CcPrjStructNode.Cols.STATUS, "AP"));
                // 获取项目已发布计划根节点
                String rootNodeId = ccPrjStructNode.getId();
                // 获取项目已发布计划树（不包含已有类型）
                // List<Map<String, Object>> prjPlanTree = getTemplateStruct(rootNodeId, false);

                String apSql;
                if (typeList.isEmpty()) {
                    apSql = "WITH RECURSIVE Subtree AS (" +
                            "SELECT * FROM cc_prj_struct_node WHERE CC_PRJ_STRUCT_NODE_PID = ? " +
                            "UNION ALL " +
                            "SELECT n.* FROM cc_prj_struct_node n JOIN Subtree s ON n.CC_PRJ_STRUCT_NODE_PID = s.ID) " +
                            "SELECT * FROM Subtree";
                } else {
                    apSql = "WITH RECURSIVE Subtree AS (" +
                            "SELECT * FROM cc_prj_struct_node WHERE CC_PRJ_STRUCT_NODE_PID = ? " +
                            "AND CC_PRJ_WBS_TYPE_ID NOT IN (" + joinedTypeList + ") " +
                            "UNION ALL " +
                            "SELECT n.* FROM cc_prj_struct_node n JOIN Subtree s ON n.CC_PRJ_STRUCT_NODE_PID = s.ID " +
                            "WHERE n.CC_PRJ_WBS_TYPE_ID NOT IN (" + joinedTypeList + ")) " +
                            "SELECT * FROM Subtree";
                }

                List<Map<String, Object>> prjPlanTree = myJdbcTemplate.queryForList(apSql, rootNodeId);

                // 设置直接子节点的父ID为null
                if (!prjPlanTree.isEmpty()) {
                    String rootId = prjPlanTree.get(0).get("CC_PRJ_STRUCT_NODE_PID").toString();
                    for (Map<String, Object> node : prjPlanTree) {
                        if (node.get("CC_PRJ_STRUCT_NODE_PID").equals(rootId)) {
                            node.put("CC_PRJ_STRUCT_NODE_PID", null);
                        }
                    }
                }
                for (Map<String, Object> node : prjPlanTree) {
                    node.put("COPY_FROM_PRJ_STRUCT_NODE_ID", node.get("ID"));
                }

                // 替换ID
                replaceIdsAndInsert(prjPlanTree);
                // 序号
                BigDecimal seqNo = BigDecimal.ZERO;
                // 对于每一个模板结构节点，将其作为子节点插入
                for (Map<String, Object> node : prjPlanTree) {
                    insertWbsNodeUnapproved(node, entityRecord, seqNo);
                }

                // 设置编制中的各个类型任务根节点的父节点为全景计划节点
                for (Map<String, Object> map : queryForList) {
                    String typeRootId = JdbcMapUtil.getString(map, "ID");
                    CcPrjStructNode ccPrjTypeStructNode = CcPrjStructNode.selectById(typeRootId);
                    ccPrjTypeStructNode.setCcPrjStructNodePid(csCommId);
                    ccPrjTypeStructNode.updateById();
                }

            } else {
                //获取项目编制中是否有对应类型计划
//                List<CcPrjStructNode> ccPrjStructNodes = CcPrjStructNode.selectByWhere(new Where().eq(CcPrjStructNode.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjStructNode.Cols.CC_PRJ_STRUCT_NODE_PID, null).in(CcPrjStructNode.Cols.STATUS, "DR", "APING", "DN").eq(CcPrjStructNode.Cols.CC_PRJ_WBS_TYPE_ID, ccPrjWbsTypeId).neq(CcPrjStructNode.Cols.ID, csCommId));
                String checkSql = "select * from cc_prj_struct_node t where t.is_template = 0 AND t.is_wbs = 1 AND t.status IN ('DR', 'APING', 'DN') AND t.CC_PRJ_WBS_TYPE_ID = ? AND t.CC_PRJ_ID = ? AND (t.cc_prj_struct_node_pid IS NULL OR t.cc_prj_struct_node_pid IN (SELECT id FROM cc_prj_struct_node WHERE cc_prj_struct_node_pid IS NULL)) AND t.id != ?";
                List<Map<String, Object>> checkMap = myJdbcTemplate.queryForList(checkSql, ccPrjWbsTypeId, ccPrjId, csCommId);
                if (!SharedUtil.isEmpty(checkMap)) {
                    throw new BaseException("已存在" + typeName + "编制中计划");
                }

                //创建前期、设计等计划时，若有全景计划，则加入到全景计划下
                String checkAllSql = "select * from cc_prj_struct_node t where t.is_template = 0 AND t.is_wbs = 1 AND t.status IN ('DR', 'APING', 'DN') AND t.CC_PRJ_WBS_TYPE_ID = 'ALL' AND t.CC_PRJ_ID = ? AND (t.cc_prj_struct_node_pid IS NULL OR t.cc_prj_struct_node_pid IN (SELECT id FROM cc_prj_struct_node WHERE cc_prj_struct_node_pid IS NULL)) AND t.id != ?";
                List<Map<String, Object>> checkAllMap = myJdbcTemplate.queryForList(checkAllSql, ccPrjId, csCommId);
                if (!SharedUtil.isEmpty(checkAllMap)) {
                    for (Map<String, Object> map : checkAllMap) {
                        String pid = JdbcMapUtil.getString(map, "ID");
                        CcPrjStructNode ccPrjStructNode = CcPrjStructNode.selectById(csCommId);
                        ccPrjStructNode.setCcPrjStructNodePid(pid);
                        ccPrjStructNode.updateById();
                    }
                }


                //获取项目已发布对应类型计划
                String sql = "select * from cc_prj_struct_node n where n.cc_prj_struct_node_pid in (select id from cc_prj_struct_node n1 where n1.CC_PRJ_STRUCT_NODE_PID is null) and n.is_template = 0 AND n.is_wbs = 1 and n.STATUS = 'AP' and n.CC_PRJ_ID = ? and n.CC_PRJ_WBS_TYPE_ID = ?";
                List<Map<String, Object>> queryForList = myJdbcTemplate.queryForList(sql, ccPrjId, ccPrjWbsTypeId);
                if (!SharedUtil.isEmpty(queryForList)) {
                    for (Map<String, Object> queryForMap : queryForList) {
                        //第二层根节点
                        String rootNodeId = JdbcMapUtil.getString(queryForMap, "ID");
                        // 获取项目已发布类型计划树
                        List<Map<String, Object>> prjPlanTree = getTemplateStruct(rootNodeId, false);
                        // 替换ID
                        List<Map<String, Object>> list = replaceIdsAndInsert(prjPlanTree);
                        // 序号
                        BigDecimal seqNo = BigDecimal.ZERO;
                        // 对于每一个模板结构节点，将其作为子节点插入
                        for (Map<String, Object> node : prjPlanTree) {
                            insertWbsNodeUnapproved(node, entityRecord, seqNo);
                        }
                    }
                }
            }

        }
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 审核计划
     */
    public void reviewPlan() {
        InvokeActResult invokeActResult = new InvokeActResult();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcPrjStructNode ccPrjStructNode = CcPrjStructNode.selectById(csCommId);
            String ccPrjWbsTypeId = ccPrjStructNode.getCcPrjWbsTypeId(); //计划类型
            String ccPrjId = ccPrjStructNode.getCcPrjId();

            //查询编制计划下的直接子节点
            List<CcPrjStructNode> ccPrjStructChildNodeList = CcPrjStructNode.selectByWhere(new Where().eq(CcPrjStructNode.Cols.CC_PRJ_STRUCT_NODE_PID, csCommId).eq(CcPrjStructNode.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjStructNode.Cols.IS_WBS, true).eq(CcPrjStructNode.Cols.IS_TEMPLATE, false).in(CcPrjStructNode.Cols.STATUS, "DR", "APING", "DN"));
            //提交的全景计划下存在的计划类型
            ArrayList<String> typeList = new ArrayList<>();
            if (!SharedUtil.isEmpty(ccPrjStructChildNodeList)) {
                for (CcPrjStructNode ccPrjStructChildNode : ccPrjStructChildNodeList) {
                    String wbsTypeId = ccPrjStructChildNode.getCcPrjWbsTypeId();
                    typeList.add(wbsTypeId);
                }
            }

            String wbsTypeSql = "select * from cc_prj_wbs_type where id != 'ALL'";
            List<Map<String, Object>> queryForList = myJdbcTemplate.queryForList(wbsTypeSql);
            ArrayList<String> allTypeList = new ArrayList<>();
            for (Map<String, Object> map : queryForList) {
                String wbsTypeId = map.get("ID").toString();
                allTypeList.add(wbsTypeId);
            }
            // 移除 allTypeList 中所有在 typeList 出现的元素
            allTypeList.removeAll(typeList);

            List<String> residualTypeList = new ArrayList<>(allTypeList);
            String residualType = residualTypeList.stream()
                    .map(s -> "'" + s + "'")
                    .collect(Collectors.joining(","));

            String updateStatusSql;
            if (residualType.isEmpty()) {
                updateStatusSql = "WITH RECURSIVE Subtree AS (" +
                        "SELECT ID FROM cc_prj_struct_node WHERE ID = ? " +
                        "UNION ALL " +
                        "SELECT n.ID FROM cc_prj_struct_node n JOIN Subtree s ON n.CC_PRJ_STRUCT_NODE_PID = s.ID) " +
                        "UPDATE cc_prj_struct_node SET STATUS = ? WHERE ID IN (SELECT ID FROM Subtree)";
            } else {
                updateStatusSql = "WITH RECURSIVE Subtree AS (" +
                        "SELECT ID FROM cc_prj_struct_node WHERE ID = ? " +
                        "UNION ALL " +
                        "SELECT n.ID FROM cc_prj_struct_node n JOIN Subtree s ON n.CC_PRJ_STRUCT_NODE_PID = s.ID) " +
                        "UPDATE cc_prj_struct_node SET STATUS = ? WHERE ID IN (SELECT ID FROM Subtree) " +
                        "AND CC_PRJ_WBS_TYPE_ID NOT IN (" + residualType + ")";
            }
            String sql;
            if (residualType.isEmpty()) {
                sql = "WITH RECURSIVE Subtree AS (" +
                        "SELECT ID FROM cc_prj_struct_node WHERE ID = ? " +
                        "UNION ALL " +
                        "SELECT n.ID FROM cc_prj_struct_node n JOIN Subtree s ON n.CC_PRJ_STRUCT_NODE_PID = s.ID) " +
                        "SELECT * FROM cc_prj_struct_node WHERE ID IN (SELECT ID FROM Subtree) and is_wbs =1";
            } else {
                sql = "WITH RECURSIVE Subtree AS (" +
                        "SELECT ID FROM cc_prj_struct_node WHERE ID = ? " +
                        "UNION ALL " +
                        "SELECT n.ID FROM cc_prj_struct_node n JOIN Subtree s ON n.CC_PRJ_STRUCT_NODE_PID = s.ID) " +
                        "SELECT * FROM cc_prj_struct_node WHERE ID IN (SELECT ID FROM Subtree) AND is_wbs = 1 " +
                        "AND CC_PRJ_WBS_TYPE_ID NOT IN (" + residualType + ")";
            }


            List<CcPrjStructNode> ccPrjStructNodes = null;

            // 当 ccPrjWbsTypeId 为 "ALL" 时，使用当前逻辑
            if ("ALL".equals(ccPrjWbsTypeId)) {
                // 获取此项目已批准的计划根节点
                ccPrjStructNodes = CcPrjStructNode.selectByWhere(new Where().eq(CcPrjStructNode.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjStructNode.Cols.IS_WBS, 1).eq(CcPrjStructNode.Cols.STATUS, "AP").eq(CcPrjStructNode.Cols.CC_PRJ_STRUCT_NODE_PID, null));
                List<Map<String, Object>> nodes = null;
                if (!SharedUtil.isEmpty(ccPrjStructNodes)) {
                    for (CcPrjStructNode ccPrjStructNode0 : ccPrjStructNodes) {
                        String rootId = ccPrjStructNode0.getId();
                        // 获取旧计划树
                        nodes = myJdbcTemplate.queryForList(sql, rootId);
                        myJdbcTemplate.update(updateStatusSql, rootId, StatusE.VD.toString());
                    }
                }
                // 当前DR改成AP
                int update = myJdbcTemplate.update(updateStatusSql, csCommId, StatusE.AP.toString());
                String apTypeSql;
                for (String wbsType : residualTypeList) {
                    apTypeSql = "select * from cc_prj_struct_node n  where n.cc_prj_struct_node_pid in (select id from cc_prj_struct_node n1 where n1.CC_PRJ_STRUCT_NODE_PID is null) and n.is_template = 0 AND n.is_wbs = 1 and n.STATUS = 'AP' and n.CC_PRJ_ID = ? and n.CC_PRJ_WBS_TYPE_ID = ?";
                    List<Map<String, Object>> queryForList1 = myJdbcTemplate.queryForList(apTypeSql, ccPrjId, wbsType);
                    if (!queryForList1.isEmpty()) {
                        for (Map<String, Object> queryForMap : queryForList1) {
                            String id = JdbcMapUtil.getString(queryForMap, "ID");
                            CcPrjStructNode ccPrjStructNode1 = CcPrjStructNode.selectById(id);
                            ccPrjStructNode1.setCcPrjStructNodePid(csCommId);
                            ccPrjStructNode1.updateById();
                        }
                    }
                }

                if (!SharedUtil.isEmpty(ccPrjStructNodes)) {
                    // 进展明细从原来计划改到新计划
                    for (CcPrjStructNode ccPrjStructNode0 : ccPrjStructNodes) {
                        String rootId = ccPrjStructNode0.getId();

                        for (Map<String, Object> node : nodes) {
                            String id = node.get("ID").toString();
                            // 通过拷贝自项目结构节点ID获取新计划树
                            List<CcPrjStructNode> ccPrjStructNodes1 = CcPrjStructNode.selectByWhere(new Where().eq(CcPrjStructNode.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, id));

                            // 获取此条计划的进展
                            List<CcPrjStructNodeProg> ccPrjStructNodeProgs = CcPrjStructNodeProg.selectByWhere(new Where().eq(CcPrjStructNodeProg.Cols.CC_PRJ_STRUCT_NODE_ID, id));
                            if (!SharedUtil.isEmpty(ccPrjStructNodes1)) {
                                for (CcPrjStructNode ccPrjStructNode1 : ccPrjStructNodes1) {
                                    if (!SharedUtil.isEmpty(ccPrjStructNodeProgs)) {
                                        for (CcPrjStructNodeProg ccPrjStructNodeProg : ccPrjStructNodeProgs) {
                                            ccPrjStructNodeProg.setCcPrjStructNodeId(ccPrjStructNode1.getId());
                                            ccPrjStructNodeProg.updateById();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                //查询现行计划的全景计划根节点
                CcPrjStructNode ccPrjStructNodeRoot = CcPrjStructNode.selectOneByWhere(new Where().eq(CcPrjStructNode.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjStructNode.Cols.IS_WBS, 1).eq(CcPrjStructNode.Cols.STATUS, "AP").eq(CcPrjStructNode.Cols.CC_PRJ_STRUCT_NODE_PID, null));
                if (!SharedUtil.isEmpty(ccPrjStructNodeRoot)) {
                    String ccPrjStructNodeRootId = ccPrjStructNodeRoot.getId();
                    ccPrjStructNode.setCcPrjStructNodePid(ccPrjStructNodeRootId);
                    ccPrjStructNode.updateById();
                }

                // 当 ccPrjWbsTypeId 不为 "ALL" 时,将此类型的计划状态改为VD
                //先前的现行计划 改为VD
                ccPrjStructNodes = CcPrjStructNode.selectByWhere(new Where().eq(CcPrjStructNode.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjStructNode.Cols.IS_WBS, 1).eq(CcPrjStructNode.Cols.STATUS, "AP").eq(CcPrjStructNode.Cols.CC_PRJ_WBS_TYPE_ID, ccPrjWbsTypeId));
                if (!SharedUtil.isEmpty(ccPrjStructNodes)) {
                    for (CcPrjStructNode ccPrjStructNode0 : ccPrjStructNodes) {
                        ccPrjStructNode0.setStatus(StatusE.VD.toString());
                        ccPrjStructNode0.updateById();
                    }
                }
                // 当前编制计划DR改成AP,进展明细从原来计划改到新计划
                List<CcPrjStructNode> ccPrjStructNodes0 = CcPrjStructNode.selectByWhere(new Where().eq(CcPrjStructNode.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjStructNode.Cols.IS_WBS, 1).eq(CcPrjStructNode.Cols.STATUS, "DR").eq(CcPrjStructNode.Cols.CC_PRJ_WBS_TYPE_ID, ccPrjWbsTypeId));
                if (!SharedUtil.isEmpty(ccPrjStructNodes0)) {
                    for (CcPrjStructNode ccPrjStructNode1 : ccPrjStructNodes0) {
                        String copyFromPrjStructNodeId = ccPrjStructNode1.getCopyFromPrjStructNodeId();
                        // 获取此条计划的进展
                        List<CcPrjStructNodeProg> ccPrjStructNodeProgs = CcPrjStructNodeProg.selectByWhere(new Where().eq(CcPrjStructNodeProg.Cols.CC_PRJ_STRUCT_NODE_ID, copyFromPrjStructNodeId));
                        if (!SharedUtil.isEmpty(ccPrjStructNodeProgs)) {
                            for (CcPrjStructNodeProg ccPrjStructNodeProg : ccPrjStructNodeProgs) {
                                ccPrjStructNodeProg.setCcPrjStructNodeId(ccPrjStructNode1.getId());
                            }
                        }
                        ccPrjStructNode1.setStatus(StatusE.AP.toString());
                        ccPrjStructNode1.updateById();

                    }
                }

            }

            // 重算计划
            recalculationPlan();
        }
    }

    /**
     * 支付申请选择合同自动填入合同信息
     */
    public void payReqToPo() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            Map<String, Object> valueMap = entityRecord.valueMap;
            String ccPoId = JdbcMapUtil.getString(valueMap, "CC_PO_ID");
            CcPayReq ccPayReq = CcPayReq.selectById(csCommId);

            if (ccPoId == null) {
                continue;
            }

            CcPo ccPo = CcPo.selectById(ccPoId);
            ccPayReq.setCcPrjCbsTempalteNodeId(ccPo.getCcPrjCbsTempalteNodeId());
            valueMap.put("CC_PRJ_CBS_TEMPALTE_NODE_ID", ccPo.getCcPrjCbsTempalteNodeId());
            ccPayReq.setPartyA(ccPo.getPartyA());
            ccPayReq.setPartyAContactName(ccPo.getPartyAContactName());
            ccPayReq.setPartyAContactPhone(ccPo.getPartyAContactPhone());
            ccPayReq.setPartyB(ccPo.getPartyB());
            ccPayReq.setPartyBContactName(ccPo.getPartyBContactName());
            ccPayReq.setPartyBContactPhone(ccPo.getPartyBContactPhone());
            ccPayReq.updateById();
            ExtJarHelper.setReturnValue(valueMap);
        }
    }

    /**
     * 收藏计划
     */
    public void addFavorite() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcPrjStructNode ccPrjStructNode = CcPrjStructNode.selectById(csCommId);
            ccPrjStructNode.setIsFavorites(true);
            ccPrjStructNode.updateById();
        }
    }

    /**
     * 取消收藏
     */

    public void removeFavorite() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcPrjStructNode ccPrjStructNode = CcPrjStructNode.selectById(csCommId);
            ccPrjStructNode.setIsFavorites(false);
            ccPrjStructNode.updateById();
        }
    }


    public void newEdittingNode() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
//        // 获取模板结构
//        String pWbsTempateId = varMap.get("P_WBS_TEMPATE_ID").toString();
//        Boolean includeRootNode = (Boolean) varMap.get("P_INCLUDE_ROOT_NODE");

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;

            CcPrjStructNode ccPrjStructNode = CcPrjStructNode.newData();
//            ccPrjStructNode.setCrtUserId("");
            ccPrjStructNode.setCrtDt(LocalDateTime.now());
            ccPrjStructNode.setStatus("DR");
            ccPrjStructNode.setName(JdbcMapUtil.getString(varMap, "P_NAME"));
            ccPrjStructNode.setRemark(JdbcMapUtil.getString(varMap, "P_REMARK"));
            ccPrjStructNode.setCcPrjId(JdbcMapUtil.getString(valueMap, "CC_PRJ_ID"));
            ccPrjStructNode.setCcPrjStructNodePid(JdbcMapUtil.getString(valueMap, "ID"));

            ccPrjStructNode.setIsWbs(true);
            ccPrjStructNode.setIsPbs(false);
            ccPrjStructNode.setIsCbs(false);
            ccPrjStructNode.setWbsChiefUserId(JdbcMapUtil.getString(varMap, "P_WBS_CHIEF_USER_ID"));
            ccPrjStructNode.setPbsChiefUserId(JdbcMapUtil.getString(varMap, "P_WBS_CHIEF_USER_ID"));
            ccPrjStructNode.setCbsChiefUserId(JdbcMapUtil.getString(varMap, "P_WBS_CHIEF_USER_ID"));

            LocalDate pPlanFr = JdbcMapUtil.getLocalDate(varMap, "P_PLAN_FR");
            LocalDate pPlanTo = JdbcMapUtil.getLocalDate(varMap, "P_PLAN_TO");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate from = LocalDate.parse(JdbcMapUtil.getString(varMap, "P_PLAN_FR"), formatter);
            LocalDate to = LocalDate.parse(JdbcMapUtil.getString(varMap, "P_PLAN_TO"), formatter);
            if (from.isAfter(to)) {
                throw new BaseException("请检查并确保开始日期不晚于结束日期！");
            }

            ccPrjStructNode.setPlanFr(pPlanFr);
            ccPrjStructNode.setPlanTo(pPlanTo);
            log.info(pPlanFr.toString());
            log.info(pPlanTo.toString());

            ccPrjStructNode.setPlanDays(BigDecimal.valueOf(pPlanFr.until(pPlanTo).getDays()));

            log.info(JdbcMapUtil.getString(varMap, "P_IS_STONE"));

            ccPrjStructNode.setIsMileStone(Boolean.valueOf(JdbcMapUtil.getString(varMap, "P_IS_STONE")));
            ccPrjStructNode.setIsTemplate(false);
            ccPrjStructNode.setCcPrjWbsTypeId(JdbcMapUtil.getString(valueMap, "CC_PRJ_WBS_TYPE_ID"));


            ccPrjStructNode.insertById();
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    public void isEdittingNodeLegal() throws BaseException {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;

            String id = JdbcMapUtil.getString(valueMap, "ID");
            CcPrjStructNode ccPrjStructNode = CcPrjStructNode.selectById(id);

            String ccPrjStructNodePid = ccPrjStructNode.getCcPrjStructNodePid();

            if (ccPrjStructNodePid == null) {
                List<CcPrjStructNode> ccPrjStructNodes = CcPrjStructNode.selectByWhere(new Where()
                        .eq(CcPrjStructNode.Cols.IS_TEMPLATE, false)
                        .eq(CcPrjStructNode.Cols.IS_WBS, true)
                        .eq(CcPrjStructNode.Cols.CC_PRJ_STRUCT_NODE_PID, null)
                        .eq(CcPrjStructNode.Cols.CC_PRJ_WBS_TYPE_ID, ccPrjStructNode.getCcPrjWbsTypeId())
                        .eq(CcPrjStructNode.Cols.CC_PRJ_ID, ccPrjStructNode.getCcPrjId())
                );
                if (ccPrjStructNodes.size() > 1) {
                    throw new BaseException("仅能在第二层级及以下维护层级");
                }
            } else {
                CcPrjStructNode ccPrjStructNode1 = CcPrjStructNode.selectById(ccPrjStructNodePid);
                if (!ccPrjStructNode.getCcPrjWbsTypeId().equals(ccPrjStructNode1.getCcPrjWbsTypeId())) {
                    List<CcPrjStructNode> ccPrjStructNodes = CcPrjStructNode.selectByWhere(new Where()
                            .eq(CcPrjStructNode.Cols.IS_TEMPLATE, false)
                            .eq(CcPrjStructNode.Cols.IS_WBS, true)
                            .eq(CcPrjStructNode.Cols.CC_PRJ_STRUCT_NODE_PID, ccPrjStructNodePid)
                            .eq(CcPrjStructNode.Cols.CC_PRJ_WBS_TYPE_ID, ccPrjStructNode.getCcPrjWbsTypeId())
                            .eq(CcPrjStructNode.Cols.CC_PRJ_ID, ccPrjStructNode.getCcPrjId())
                    );
                    if (ccPrjStructNodes.size() > 1) {
                        throw new BaseException("仅能在第二层级及以下维护层级");
                    }
                }
            }
        }
    }
}
