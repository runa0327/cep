package com.cisdi.ext.history;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
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
     * 0-默认状态
     * 1-资料清单刷新
     * 2-手动在企业云上传历史文件
     * 3-流程刷新
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
                                Map<String, Object> nodeData = nodeList.get(0);
                                String updateType = JdbcMapUtil.getString(nodeData, "UPDATE_TYPE");
                                boolean res = chargeProcess(projectId, JdbcMapUtil.getString(nodeData, "LINKED_WF_PROCESS_ID"));
                                System.out.println("结果为：" + res);
                                if (res) {
                                    if (!"3".equals(updateType)) {
                                        myJdbcTemplate.update("update pm_pro_plan_node set UPDATE_TYPE='3',IZ_OVERDUE='0' where id=?", nodeData.get("ID"));
                                    }
                                } else {
                                    if ("0".equals(updateType) || "1".equals(updateType)) {
                                        myJdbcTemplate.update("update pm_pro_plan_node set PROGRESS_STATUS_ID=?,ACTUAL_COMPL_DATE=?,UPDATE_TYPE='1',IZ_REFRESH='1',IZ_OVERDUE='0' where id=?", COMPLETED, nodeData.get("PLAN_COMPL_DATE"), nodeData.get("ID"));
                                        String fileIds = groupData.get(key).stream().map(m -> JdbcMapUtil.getString(m, "FL_FILE_ID")).collect(Collectors.joining(","));
                                        List<String> ids = Arrays.asList(fileIds.split(","));
                                        if (!CollectionUtils.isEmpty(ids)) {
                                            //处理文件
                                            dealWithFile(ids, JdbcMapUtil.getString(nodeData, "ID"));
                                        }
                                    }
                                    if ("0".equals(updateType) && Strings.isNotEmpty(JdbcMapUtil.getString(nodeData, "ATT_FILE_GROUP_ID"))) {
                                        myJdbcTemplate.update("update pm_pro_plan_node set UPDATE_TYPE='2' where id=?", nodeData.get("ID"));
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }


    private void dealWithFile(List<String> ids, String nodeId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("delete from PM_PRO_PLAN_NODE_FILE where PM_PRO_PLAN_NODE_ID = ?", nodeId);
        for (String fileId : ids) {
            String id = Crud.from("PM_PRO_PLAN_NODE_FILE").insertData();
            Crud.from("PM_PRO_PLAN_NODE_FILE").where().eq("ID", id).update().set("PM_PRO_PLAN_NODE_ID", nodeId).set("FL_FILE_ID", fileId).exec();
        }
    }


    /**
     * 判断当前项目是否完成流程
     *
     * @param projectId
     * @param processId
     * @return
     */
    private boolean chargeProcess(String projectId, String processId) {
        boolean res = false;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ad.`CODE` as tableName,ad.ID as ID from base_process_ent bas left join ad_ent ad on bas.AD_ENT_ONE_ID = ad.ID where WF_PROCESS_ID=?", processId);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> mapData = list.get(0);
            String tableName = JdbcMapUtil.getString(mapData, "tableName");
            List<Map<String, Object>> attList = myJdbcTemplate.queryForList("select * from ad_att adt left join ad_ent_att ant on adt.id = ant.AD_ATT_ID where ant.AD_ENT_ID=?", mapData.get("ID"));
            Optional<Map<String, Object>> optional = attList.stream().filter(p -> "PM_PRJ_IDS".equals(JdbcMapUtil.getString(p, "CODE"))).findAny();
            if (optional.isPresent()) {
                String sql = "select * from  " + tableName + "  where find_in_set('" + projectId + "',PM_PRJ_IDS)";
                List<Map<String, Object>> list1 = myJdbcTemplate.queryForList(sql);
                if (!CollectionUtils.isEmpty(list1)) {
                    res = true;
                }
            } else {
                Optional<Map<String, Object>> op = attList.stream().filter(p -> "PM_PRJ_ID".equals(JdbcMapUtil.getString(p, "CODE"))).findAny();
                if (op.isPresent()) {
                    String sql = "select * from  " + tableName + "  where PM_PRJ_ID= '" + projectId + "'";
                    List<Map<String, Object>> list1 = myJdbcTemplate.queryForList(sql);
                    if (!CollectionUtils.isEmpty(list1)) {
                        res = true;
                    }
                }
            }
        }
        return res;
    }
}
