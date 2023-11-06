package com.cisdi.ext.util;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProPlanUtils
 * @package com.cisdi.ext.util
 * @description
 * @date 2023/5/22
 */
public class ProPlanUtils {

    /**
     * 多标段产生逻辑
     *
     * @param projectId
     * @param count
     */
    public static void moreSection(String projectId, int count) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pn.* from pm_pro_plan_node pn left join pm_pro_plan pl on pn.PM_PRO_PLAN_ID = pl.id  where IZ_MORE='1' and  pl.PM_PRJ_ID==?", projectId);
        for (Map<String, Object> nodeMap : list) {
            for (int i = 0; i < count; i++) {
                String newId = insertData(nodeMap, nodeMap.get("pm_pro_plan_node_pid"));
                if ("1".equals(JdbcMapUtil.getString(nodeMap, "level"))) {
                    //查询当前节点下的所有节点，进行复制操作
                    List<Map<String, Object>> secondList = selectSonNode(JdbcMapUtil.getString(nodeMap, "ID"));
                    if (!CollectionUtils.isEmpty(secondList)) {
                        for (Map<String, Object> stringObjectMap : secondList) {
                            String newSecondId = insertData(stringObjectMap, newId);
                            List<Map<String, Object>> thirdList = selectSonNode(JdbcMapUtil.getString(stringObjectMap, "ID"));
                            if (!CollectionUtils.isEmpty(thirdList)) {
                                //三级节点
                                for (Map<String, Object> map : thirdList) {
                                    insertData(map, newSecondId);
                                }
                            }
                        }
                    }
                } else if ("2".equals(JdbcMapUtil.getString(nodeMap, "level"))) {
                    //查询当前节点下的所有节点，进行复制操作
                    List<Map<String, Object>> thirdList = selectSonNode(JdbcMapUtil.getString(nodeMap, "ID"));
                    if (!CollectionUtils.isEmpty(thirdList)) {
                        //三级节点
                        for (Map<String, Object> stringObjectMap : thirdList) {
                            insertData(stringObjectMap, newId);
                        }
                    }
                }
            }
        }


    }

    public static List<Map<String, Object>> selectSonNode(String nodeId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        return myJdbcTemplate.queryForList("select * from PM_PRO_PLAN_NODE where PM_PRO_PLAN_NODE_PID=?", nodeId);
    }

    private static String insertData(Map<String, Object> objectMap, Object pId) {
        String id = Crud.from("PM_PRO_PLAN_NODE").insertData();
        Crud.from("PM_PRO_PLAN_NODE").where().eq("ID", id).update().set("NAME", objectMap.get("NAME")).set("PM_PRO_PLAN_ID", objectMap.get("PM_PRO_PLAN_ID")).set("PM_PRO_PLAN_NODE_PID", pId)
                .set("PLAN_TOTAL_DAYS", objectMap.get("PLAN_TOTAL_DAYS")).set("PROGRESS_STATUS_ID", objectMap.get("PROGRESS_STATUS_ID")).set("PROGRESS_RISK_TYPE_ID", objectMap.get("PROGRESS_RISK_TYPE_ID"))
                .set("CHIEF_DEPT_ID", objectMap.get("CHIEF_DEPT_ID")).set("CHIEF_USER_ID", objectMap.get("CHIEF_USER_ID")).set("START_DAY", objectMap.get("START_DAY")).set("SEQ_NO", objectMap.get("SEQ_NO")).set("LEVEL", objectMap.get("LEVEL"))
                .set("LINKED_WF_PROCESS_ID", objectMap.get("LINKED_WF_PROCESS_ID")).set("LINKED_START_WF_NODE_ID", objectMap.get("LINKED_START_WF_NODE_ID")).set("LINKED_END_WF_NODE_ID", objectMap.get("LINKED_END_WF_NODE_ID")).set("SHOW_IN_EARLY_PROC", objectMap.get("SHOW_IN_EARLY_PROC"))
                .set("SHOW_IN_PRJ_OVERVIEW", objectMap.get("SHOW_IN_PRJ_OVERVIEW")).set("POST_INFO_ID", objectMap.get("POST_INFO_ID")).set("CHIEF_USER_ID", objectMap.get("AD_USER_ID")).set("CAN_START", objectMap.get("CAN_START")).set("IZ_MORE", objectMap.get("IZ_MORE"))
                .set("PRE_NODE_ID", objectMap.get("PRE_NODE_ID")).set("AD_ENT_ID_IMP", objectMap.get("AD_ENT_ID_IMP")).set("AD_ATT_ID_IMP", objectMap.get("AD_ATT_ID_IMP")).set("IZ_MILESTONE", objectMap.get("IZ_MILESTONE")).set("SCHEDULE_NAME", objectMap.get("SCHEDULE_NAME")).exec();
        return id;
    }


    public static List<Map<String, Object>> sortLevel3Bt(String proPlanId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pn.id as id,pn.`NAME` as nodeName,ifnull(PM_PRO_PLAN_NODE_PID,0) pid," +
                "pn.SEQ_NO as seq,pn.level as level,'0' as seq_bak,pn.PLAN_COMPL_DATE as PLAN_COMPL_DATE,OPREATION_TYPE,SCHEDULE_NAME,ifnull(pn.PROGRESS_STATUS_ID,0) as status  " +
                " from pm_pro_plan_node pn  where PM_PRO_PLAN_ID=?", proPlanId);

        list.stream().filter(p -> "0".equals(JdbcMapUtil.getString(p, "pid")))
                .sorted(Comparator.comparing(o -> BigDecimalUtil.stringToBigDecimal(JdbcMapUtil.getString(o, "seq")))).peek(m -> {
            BigDecimal parentSeq = BigDecimalUtil.stringToBigDecimal(JdbcMapUtil.getString(m, "seq")).multiply(new BigDecimal(1000));
            m.put("seq_bak", parentSeq);
            getChildren(m, list, parentSeq);
        }).collect(Collectors.toList());
        return list.stream().filter(p -> "3".equals(JdbcMapUtil.getString(p, "level"))).sorted(Comparator.comparing(o -> JdbcMapUtil.getString(o, "seq_bak"))).collect(Collectors.toList());
    }

    /**
     * 排序3级节点
     *
     * @param projectId
     * @return
     */
    public static List<Map<String, Object>> sortLevel3(String projectId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pn.id as id,pn.`NAME` as nodeName,ifnull(PM_PRO_PLAN_NODE_PID,0) pid," +
                "pn.SEQ_NO as seq,pn.level as level,'0' as seq_bak,pn.PLAN_COMPL_DATE as PLAN_COMPL_DATE,OPREATION_TYPE,SCHEDULE_NAME,ifnull(pn.PROGRESS_STATUS_ID,0) as status  " +
                " from pm_pro_plan_node pn left join pm_pro_plan pl on pn.PM_PRO_PLAN_ID = pl.id where PM_PRJ_ID=?", projectId);
        list.stream().filter(p -> "0".equals(JdbcMapUtil.getString(p, "pid")))
                .sorted(Comparator.comparing(o -> BigDecimalUtil.stringToBigDecimal(JdbcMapUtil.getString(o, "seq")))).peek(m -> {
            BigDecimal parentSeq = BigDecimalUtil.stringToBigDecimal(JdbcMapUtil.getString(m, "seq")).multiply(new BigDecimal(1000));
            m.put("seq_bak", parentSeq);
            getChildren(m, list, parentSeq);
        }).collect(Collectors.toList());
        return list.stream().filter(p -> "3".equals(JdbcMapUtil.getString(p, "level"))).sorted(Comparator.comparing(o -> JdbcMapUtil.getString(o, "seq_bak"))).collect(Collectors.toList());
    }

    /**
     * 根据项目id查询该项目3及节点，并带出节点状态信息
     * @param projectId 项目id
     * @param myJdbcTemplate 数据源
     * @return 节点信息
     */
    public static List<Map<String, Object>> queryLevel3(String projectId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "SELECT pn.id AS id,pn.`NAME` AS nodeName,pn.PLAN_COMPL_DATE AS PLAN_COMPL_DATE,OPREATION_TYPE,SCHEDULE_NAME,d.name as typeName,pn.SEQ_NO as seq,ifnull(PM_PRO_PLAN_NODE_PID,0) pid,'0' as seq_bak,pn.level as level FROM pm_pro_plan_node pn LEFT JOIN pm_pro_plan pl ON pn.PM_PRO_PLAN_ID = pl.id left join STANDARD_NODE_NAME c on pn.SCHEDULE_NAME = c.id left join gr_set_value d on c.PROJECT_NODE_TYPE_ID = d.id and d.GR_SET_ID = '1717352141542887424' WHERE pl.PM_PRJ_ID =? and pn.status = 'ap'  ORDER BY pn.SEQ_NO asc";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,projectId);
        sortList(list);
        return list.stream().filter(p -> "3".equals(JdbcMapUtil.getString(p, "level"))).sorted(Comparator.comparing(o -> JdbcMapUtil.getString(o, "seq_bak"))).collect(Collectors.toList());
    }

    /**
     * 根据项目id查询该项目3及节点，此类节点信息包含项目节点类型
     * @param projectId 项目id
     * @param myJdbcTemplate 数据源
     * @return 节点信息
     */
    public static List<Map<String, Object>> queryLevel3HaveNodeType(String projectId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "SELECT pn.id AS id,pn.`NAME` AS nodeName,pn.PLAN_COMPL_DATE AS PLAN_COMPL_DATE,OPREATION_TYPE,SCHEDULE_NAME,d.name as typeName,pn.SEQ_NO as seq,ifnull(PM_PRO_PLAN_NODE_PID,0) pid,'0' as seq_bak,pn.level as level FROM pm_pro_plan_node pn LEFT JOIN pm_pro_plan pl ON pn.PM_PRO_PLAN_ID = pl.id left join STANDARD_NODE_NAME c on pn.SCHEDULE_NAME = c.id left join gr_set_value d on c.PROJECT_NODE_TYPE_ID = d.id and d.GR_SET_ID = '1717352141542887424' WHERE pl.PM_PRJ_ID =? and pn.status = 'ap' and pn.level = 3  and d.name is not null  ORDER BY typeName desc,pn.SEQ_NO asc";
        return myJdbcTemplate.queryForList(sql,projectId);
    }

    /**
     * 节点信息排序
     * @param list 节点信息
     */
    private static void sortList(List<Map<String, Object>> list) {
        list.stream().filter(p -> "0".equals(JdbcMapUtil.getString(p, "pid")))
                .sorted(Comparator.comparing(o -> BigDecimalUtil.stringToBigDecimal(JdbcMapUtil.getString(o, "seq")))).peek(m -> {
                    BigDecimal parentSeq = BigDecimalUtil.stringToBigDecimal(JdbcMapUtil.getString(m, "seq")).multiply(new BigDecimal(1000));
                    m.put("seq_bak", parentSeq);
                    getChildren(m, list, parentSeq);
                }).collect(Collectors.toList());
    }

    private static List<Map<String, Object>> getChildren(Map<String, Object> parent, List<Map<String, Object>> allData, BigDecimal parentSeq) {
        return allData.stream().filter(p -> Objects.equals(parent.get("id"), p.get("pid")))
                .sorted(Comparator.comparing(o -> BigDecimalUtil.stringToBigDecimal(JdbcMapUtil.getString(o, "seq")))).peek(m -> {
                    BigDecimal currentSeq = BigDecimalUtil.stringToBigDecimal(JdbcMapUtil.getString(m, "seq"));
                    if ("1".equals(JdbcMapUtil.getString(m, "level"))) {
                        currentSeq = BigDecimalUtil.multiply(currentSeq, new BigDecimal(1000));
                    } else if ("2".equals(JdbcMapUtil.getString(m, "level"))) {
                        currentSeq = BigDecimalUtil.multiply(currentSeq, new BigDecimal(100));
                    } else if ("3".equals(JdbcMapUtil.getString(m, "level"))) {
                        currentSeq = BigDecimalUtil.multiply(currentSeq, new BigDecimal(10));
                    }
                    BigDecimal obj = parentSeq.add(currentSeq);
                    m.put("seq_bak", obj);
                    getChildren(m, allData, obj);
                }).collect(Collectors.toList());
    }

    /**
     * 查询当前节点的前置节点，顺延往上推导
     *
     * @param nodeId
     * @return
     */
    public static List<Map<String, Object>> selectAllPreNode(String nodeId) {
        List<Map<String, Object>> result = new ArrayList<>();
        getPreNode(nodeId, result);
        return result;
    }

    private static void getPreNode(String nodeId, List<Map<String, Object>> result) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_pro_plan_node where id=?", nodeId);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> dataMap = list.get(0);
            result.add(dataMap);
            if (JdbcMapUtil.getString(dataMap, "PRE_NODE_ID") != null) {
                getPreNode(JdbcMapUtil.getString(dataMap, "PRE_NODE_ID"), result);
            }
        }
    }


    /**
     * 查询当前节点的父级节点，顺延往上推导
     *
     * @param nodeId
     * @return
     */
    public static List<Map<String, Object>> selectAllParentNode(String nodeId) {
        List<Map<String, Object>> result = new ArrayList<>();
        getParentNode(nodeId, result);
        return result;
    }

    private static void getParentNode(String nodeId, List<Map<String, Object>> result) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select a.*,ifnull(PM_PRO_PLAN_NODE_PID,0) as pid from pm_pro_plan_node a where a.id=?", nodeId);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> dataMap = list.get(0);
            result.add(dataMap);
            getParentNode(JdbcMapUtil.getString(dataMap, "PM_PRO_PLAN_NODE_PID"), result);
        }
    }
}
