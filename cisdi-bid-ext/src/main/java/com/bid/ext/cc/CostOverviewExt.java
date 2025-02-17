package com.bid.ext.cc;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class CostOverviewExt {


    /**
     * 重算成本统览
     */
    public static void recalculationCostOverview(String ccPrjCostOverviewId) {

        String parentNodeId = getParentNodeId(ccPrjCostOverviewId);

        recalculatePlanTotalCost(parentNodeId, 0);


    }

    /**
     * 获取父节点
     *
     * @param nodeId
     * @return
     */
    public static String getParentNodeId(String nodeId) {
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
     * 获取子节点
     *
     * @param parentId
     * @return
     */
    public static List<Map<String, Object>> getChildNodes(String parentId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        // 示例SQL查询，获取所有子节点
        String sql = "SELECT * FROM CC_PRJ_COST_OVERVIEW WHERE CC_PRJ_COST_OVERVIEW_PID = ?";
        return myJdbcTemplate.queryForList(sql, parentId);
    }


    /**
     * 递归地重算并更新节点的成本总览
     *
     * @param nodeId
     * @param amtIndex
     */
    public static void recalculatePlanTotalCost(String nodeId, int amtIndex) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        // 获取当前节点的直接子节点的PLAN_TOTAL_COST总和
        BigDecimal totalSum = BigDecimal.ZERO;
        List<Map<String, Object>> children = getChildNodes(nodeId);
        for (Map<String, Object> child : children) {
            Object childCostObject = child.get("CBS_AMT_" + amtIndex);
            BigDecimal childCost = childCostObject != null ? new BigDecimal(childCostObject.toString()) : BigDecimal.ZERO;
            totalSum = totalSum.add(childCost);
        }

        // 更新当前节点的PLAN_TOTAL_COST
        String updateSql = "UPDATE CC_PRJ_COST_OVERVIEW SET CBS_AMT_" + amtIndex + " = ? WHERE ID = ?";
        myJdbcTemplate.update(updateSql, totalSum, nodeId);

        // 递归更新父节点
        String parentId = getParentNodeId(nodeId);
        if (parentId != null) {
            recalculatePlanTotalCost(parentId, amtIndex);
        }
    }
}
