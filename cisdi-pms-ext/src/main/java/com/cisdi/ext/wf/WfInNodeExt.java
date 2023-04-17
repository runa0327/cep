package com.cisdi.ext.wf;

import com.cisdi.ext.model.PmProPlanNode;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

/**
 * 进入流程节点时的扩展。
 */
public class WfInNodeExt {

    public static final String NOT_STARTED = "0099799190825106800";
    public static final String IN_PROCESSING = "0099799190825106801";
    public static final String COMPLETED = "0099799190825106802";


    /**
     * 更新项目进度计划节点。
     */
    public void updatePrjProPlanNode() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        if (!SharedUtil.isEmptyList(entityRecordList)) {
            for (EntityRecord entityRecord : entityRecordList) {
                updatePrjProPlanNode(entityRecord);
            }
        }
    }

    /**
     * 更新项目进度计划节点。
     */
    private void updatePrjProPlanNode(EntityRecord entityRecord) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String procInstId = ExtJarHelper.procInstId.get();
        String nodeInstId = ExtJarHelper.nodeInstId.get();
        String nodeId = myJdbcTemplate.queryForMap("select t.WF_NODE_ID from wf_node_instance t where t.id=?", nodeInstId).get("WF_NODE_ID").toString();
        Date now = new Date();

        Map<String, Object> valueMap = entityRecord.valueMap;
        Object pmPrjId = valueMap.get("PM_PRJ_ID");
        if (!SharedUtil.isEmptyObject(pmPrjId)) {
            // 获取项目的进度计划列表：
            List<Map<String, Object>> planList = Crud.from("PM_PRO_PLAN").where().eq("PM_PRJ_ID", pmPrjId).select().execForMapList();
            if (!SharedUtil.isEmptyList(planList)) {
                // 遍历项目的进度计划：
                for (Map<String, Object> plan : planList) {
                    // 获取关联了当前流程节点的叶子计划节点列表：
                    List<Map<String, Object>> leafNodeList = myJdbcTemplate.queryForList("select * from pm_pro_plan_node n where n.PM_PRO_PLAN_ID=?/*指定计划*/ and not exists(select 1 from pm_pro_plan_node n2 where n2.PM_PRO_PLAN_NODE_PID=n.id)/*为叶子节点*/ and (n.LINKED_START_WF_NODE_ID=? or n.LINKED_END_WF_NODE_ID=?)/*关联的起始节点或结束节点为当前节点*/", plan.get("ID"), nodeId, nodeId);
                    if (!SharedUtil.isEmptyList(leafNodeList)) {
                        // 遍历叶子计划节点：
                        for (Map<String, Object> leafNode : leafNodeList) {

                            // 若为开始节点：
                            String startWfNodeId = JdbcMapUtil.getString(leafNode, "LINKED_START_WF_NODE_ID");
                            if (nodeId.equals(startWfNodeId)) {
                                updateStartInfoForPlanNode(procInstId, nodeInstId, now, leafNode);
                            }

                            // 若为结束节点：
                            String endWfNodeId = JdbcMapUtil.getString(leafNode, "LINKED_END_WF_NODE_ID");
                            if (nodeId.equals(endWfNodeId)) {

                                // 若无开始信息，则先更新开始信息：
                                if (SharedUtil.isEmptyObject(leafNode.get("ACTUAL_START_DATE"))) {
                                    updateStartInfoForPlanNode(procInstId, nodeInstId, now, leafNode);
                                }

                                // 更新结束信息：
                                updateEndInfoForPlanNode(procInstId, nodeInstId, now, leafNode);
                            }

                            // 针对父节点，进行递归：
                            Object pid = leafNode.get("PM_PRO_PLAN_NODE_PID");
                            if (!SharedUtil.isEmptyObject(pid)) {
                                updateStartEndInfoForNodeRecursively(new ArrayList<>(), pid.toString());
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 针对非叶子节点，向上递归，更新开始和结束信息。
     *
     * @param id
     */
    private void updateStartEndInfoForNodeRecursively(List<String> processedIdList, String id) {
        if (processedIdList.contains(id)) {
            throw new BaseException("更新项目进度计划节点的进度时，出现死循环！路径上某个节点的ID：" + id);
        }

        processedIdList.add(id);

        Map<String, Object> planNode = Crud.from("pm_pro_plan_node").where().eq("ID", id).select().execForMap();
        if (!SharedUtil.isEmptyMap(planNode)) {

            Where where = new Where();
            where.eq(PmProPlanNode.Cols.PM_PRO_PLAN_NODE_PID, id);
            List<PmProPlanNode> childNodeList = PmProPlanNode.selectByWhere(where);

            boolean allChildEnd = true;
            LocalDate minStartDate = LocalDate.MAX;
            LocalDate maxEndDate = LocalDate.MIN;
            for (PmProPlanNode childNode : childNodeList) {
                if (childNode.getActualStartDate() != null && childNode.getActualStartDate().compareTo(minStartDate) < 0) {
                    minStartDate = childNode.getActualStartDate();
                }
                if (childNode.getActualComplDate() != null && childNode.getActualComplDate().compareTo(maxEndDate) > 0) {
                    maxEndDate = childNode.getActualComplDate();
                }

                if (childNode.getActualComplDate() == null) {
                    allChildEnd = false;
                }
            }

            if (minStartDate.compareTo(LocalDate.MAX) == 0) {
                minStartDate = null;
            }
            if (maxEndDate.compareTo(LocalDate.MIN) == 0) {
                maxEndDate = null;
            }

            Integer actualCarryDays = null;
            if (minStartDate != null) {
                actualCarryDays = Period.between(minStartDate, LocalDate.now()).getDays() + 1;
            }

            Integer actualTotalDays = null;
            if (minStartDate != null && maxEndDate != null) {
                actualTotalDays = Period.between(minStartDate, maxEndDate).getDays() + 1;
            }

            Crud.from("pm_pro_plan_node").where().eq("ID", id).update()
                    // 设置进度信息：
                    .set("PROGRESS_STATUS_ID", allChildEnd ? COMPLETED : (minStartDate == null ? NOT_STARTED : IN_PROCESSING)).set("ACTUAL_START_DATE", minStartDate).set("ACTUAL_CARRY_DAYS", actualCarryDays).set("ACTUAL_CURRENT_PRO_PERCENT", allChildEnd ? 100 : 10).set("ACTUAL_COMPL_DATE", allChildEnd ? maxEndDate : null).set("ACTUAL_TOTAL_DAYS", allChildEnd ? actualTotalDays : null)
                    // 设置关联信息：
                    .set("LINKED_WF_PROCESS_INSTANCE_ID", null).set("LINKED_START_WF_NODE_INSTANCE_ID", null).set("LINKED_END_WF_NODE_INSTANCE_ID", null).exec();

            Object pid = planNode.get("PM_PRO_PLAN_NODE_PID");
            if (!SharedUtil.isEmptyObject(pid)) {
                updateStartEndInfoForNodeRecursively(processedIdList, pid.toString());
            }
        }
    }

    private void updateStartInfoForPlanNode(String procInstId, String nodeInstId, Date now, Map<String, Object> leafNode) {
        Crud.from("pm_pro_plan_node").where().eq("ID", leafNode.get("ID")).update()
                // 设置进度信息：
                .set("PROGRESS_STATUS_ID", IN_PROCESSING).set("ACTUAL_START_DATE", now).set("ACTUAL_CARRY_DAYS", 1).set("ACTUAL_CURRENT_PRO_PERCENT", 10).set("ACTUAL_COMPL_DATE", null).set("ACTUAL_TOTAL_DAYS", null)
                // 设置关联信息：
                .set("LINKED_WF_PROCESS_INSTANCE_ID", procInstId).set("LINKED_START_WF_NODE_INSTANCE_ID", nodeInstId).set("LINKED_END_WF_NODE_INSTANCE_ID", null).exec();
    }

    private void updateEndInfoForPlanNode(String procInstId, String nodeInstId, Date now, Map<String, Object> leafNode) {
        Crud.from("pm_pro_plan_node").where().eq("ID", leafNode.get("ID")).update()
                // 设置进度信息：
                .set("PROGRESS_STATUS_ID", COMPLETED).set("ACTUAL_CURRENT_PRO_PERCENT", 100).set("ACTUAL_COMPL_DATE", now)
                // 设置关联信息：
                .set("LINKED_WF_PROCESS_INSTANCE_ID", procInstId).set("LINKED_END_WF_NODE_INSTANCE_ID", nodeInstId).exec();

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("update pm_pro_plan_node t set t.ACTUAL_CARRY_DAYS=t.ACTUAL_COMPL_DATE-t.ACTUAL_START_DATE+1,t.ACTUAL_TOTAL_DAYS=t.ACTUAL_COMPL_DATE-t.ACTUAL_START_DATE+1 WHERE t.id=?", leafNode.get("ID"));

        //给后续节点发周任务
        sendPreNodeWeekTask(JdbcMapUtil.getString(leafNode, "ID"));
        //当前节点有关的工作台任务状态变为已完成
        myJdbcTemplate.update("update week_task set WEEK_TASK_STATUS_ID='1634118629769482240' where RELATION_DATA_ID=?", JdbcMapUtil.getString(leafNode, "ID"));
    }


    /**
     * 当前节点完成时，给他的后续节点发周任务
     *
     * @param nodeId
     */
    private void sendPreNodeWeekTask(String nodeId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pm.`NAME` as prjName,pppn.*,pi.AD_USER_ID as default_user,pm.id as projectId from pm_pro_plan_node pppn \n" +
                "left join pm_pro_plan ppp on ppp.id = pppn.PM_PRO_PLAN_ID \n" +
                "left join pm_prj pm on pm.id = ppp.PM_PRJ_ID  \n" +
                "left join POST_INFO pi on pi.id = pppn.POST_INFO_ID  \n" +
                "where PRE_NODE_ID =?", nodeId);

        String msg = "{0}【{1}】计划将在{2}开始，请及时处理！";
        for (Map<String, Object> objectMap : list) {
            //当节点状态是未启动的时候才发周任务
            String status = JdbcMapUtil.getString(objectMap, "PROGRESS_STATUS_ID");
            if (NOT_STARTED.equals(status)) {
                String id = Crud.from("WEEK_TASK").insertData();
                String userId = JdbcMapUtil.getString(objectMap, "CHIEF_USER_ID");
                if (Objects.isNull(objectMap.get("CHIEF_USER_ID"))) {
                    userId = JdbcMapUtil.getString(objectMap, "default_user");
                }
                String processName = JdbcMapUtil.getString(objectMap, "NAME");
                if (Objects.nonNull(objectMap.get("LINKED_WF_PROCESS_ID"))) {
                    //取流程名称
                    List<Map<String, Object>> processlist = myJdbcTemplate.queryForList("select * from WF_PROCESS where id=?", objectMap.get("LINKED_WF_PROCESS_ID"));
                    if (!CollectionUtils.isEmpty(processlist)) {
                        Map<String, Object> dataMap = processlist.get(0);
                        processName = JdbcMapUtil.getString(dataMap, "NAME");
                    }
                }
                String dateOrg = "";
                if (Objects.nonNull(objectMap.get("PLAN_COMPL_DATE"))) {
                    Date compDate = JdbcMapUtil.getDate(objectMap, "PLAN_COMPL_DATE");
                    SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
                    dateOrg = sp.format(compDate);
                }
                String title = MessageFormat.format(msg, objectMap.get("prjName"), processName, dateOrg);
                myJdbcTemplate.update("update WEEK_TASK set AD_USER_ID=?,TITLE=?,CONTENT=?,PUBLISH_START=?,WEEK_TASK_STATUS_ID=?,WEEK_TASK_TYPE_ID=?,RELATION_DATA_ID=?,CAN_DISPATCH='0',PM_PRJ_ID=? where id=?",
                        userId, title, title, new Date(), "1634118574056542208", "1635080848313290752", objectMap.get("ID"), objectMap.get("projectId"), id);
            }
        }
    }
}
