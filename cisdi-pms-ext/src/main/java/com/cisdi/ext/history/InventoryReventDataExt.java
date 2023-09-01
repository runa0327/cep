package com.cisdi.ext.history;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title HistoricalReventDataExt
 * @package com.cisdi.ext.history
 * @description 资料清单刷新节点数据
 * @date 2023/8/24
 */
public class InventoryReventDataExt {

    public static final String COMPLETED = "0099799190825106802";//已完成

    /**
     * 刷新数据
     */
    public void reventData() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String projectIds = JdbcMapUtil.getString(map, "projectIds");
        StringBuilder sb = new StringBuilder();
        sb.append("select * from pm_prj where status='ap' ");
        Map<String, Object> queryParams = new HashMap<>();// 创建入参map
        if (Strings.isNotEmpty(projectIds)) {
            sb.append(" and id in (:ids)");
            queryParams.put("ids", Arrays.asList(projectIds.split(",")));
        }
        List<Map<String, Object>> list = myNamedParameterJdbcTemplate.queryForList(sb.toString(), queryParams);

        //节点关联资料清单配置
        List<Map<String, Object>> nodeInventory = myJdbcTemplate.queryForList("select * from PM_PRO_PLAN_NODE_INVENTORY where `STATUS`='ap'");

        if (!CollectionUtils.isEmpty(list)) {
            for (Map<String, Object> objectMap : list) {
                String projectId = JdbcMapUtil.getString(objectMap, "ID");
                List<Map<String, Object>> inventoryList = myJdbcTemplate.queryForList("select pid.*,PRJ_INVENTORY_ID,MATERIAL_INVENTORY_TYPE_ID from prj_inventory_detail pid left join PRJ_INVENTORY pi on pi.id = pid.PRJ_INVENTORY_ID where PM_PRJ_ID =? ", projectId);
                if (!CollectionUtils.isEmpty(inventoryList)) {
                    Map<String, List<Map<String, Object>>> groupData = inventoryList.stream().collect(Collectors.groupingBy(p -> JdbcMapUtil.getString(p, "MATERIAL_INVENTORY_TYPE_ID")));
                    for (String key : groupData.keySet()) {
                        Optional<Map<String, Object>> any = nodeInventory.stream().filter(p -> Objects.equals(p.get("MATERIAL_INVENTORY_TYPE_ID"), key)).findAny();
                        if (any.isPresent()) {
                            Map<String, Object> dataMap = any.get();
                            String nodeName = JdbcMapUtil.getString(dataMap, "NAME_ONE");
                            //查询项目的进度节点
                            List<Map<String, Object>> nodeList = myJdbcTemplate.queryForList("select pn.* from pm_pro_plan_node pn left join pm_pro_plan pl on pn.PM_PRO_PLAN_ID = pl.id  where PM_PRJ_ID = ? and pn.`NAME`=?", projectId, nodeName);
                            if (!CollectionUtils.isEmpty(nodeList)) {
                                String fileIds = groupData.get(key).stream().map(m -> JdbcMapUtil.getString(m, "FL_FILE_ID")).collect(Collectors.joining(","));
                                Map<String, Object> nodeData = nodeList.get(0);
                                if (!COMPLETED.equals(JdbcMapUtil.getString(nodeData, "PROGRESS_STATUS_ID"))) {
                                    myJdbcTemplate.update("update pm_pro_plan_node set PROGRESS_STATUS_ID=?,ATT_FILE_GROUP_ID=?,ACTUAL_COMPL_DATE=?,UPDATE_TYPE='1',IZ_REFRESH='1',IZ_OVERDUE='0' where id=?", COMPLETED, fileIds, nodeData.get("PLAN_COMPL_DATE"), nodeData.get("ID"));
                                } else {
                                    if ("1".equals(JdbcMapUtil.getString(nodeData, "IZ_REFRESH"))) {
                                        myJdbcTemplate.update("update pm_pro_plan_node set ATT_FILE_GROUP_ID=? where id=?", fileIds, nodeData.get("ID"));
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
