package com.cisdi.ext.wf;

import com.cisdi.ext.link.linkPackage.AttLinkDifferentProcess;
import com.cisdi.ext.model.PmProPlanNode;
import com.cisdi.ext.model.WfProcessInstance;
import com.cisdi.ext.model.base.BaseMatterTypeCon;
import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.WeekTaskUtils;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 进入流程节点时的扩展。
 */
@Slf4j
public class WfInNodeExt {

    public static final String NOT_STARTED = "0099799190825106800";
    public static final String IN_PROCESSING = "0099799190825106801";
    public static final String COMPLETED = "0099799190825106802";

    /**
     * 根据所有的流程实例，更新所有的项目的进度计划。
     */
    public void updateAllPrjProPlanByAllProcInst() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 获取所有的节点实例：
        List<Map<String, Object>> nodeInstList = myJdbcTemplate.queryForList("select ni.* from wf_node_instance ni where ni.`STATUS`='AP'/* and ni.wf_process_instance_id='1634108398195544064'*/ ORDER BY ni.wf_process_instance_id, ni.id");
        update(myJdbcTemplate, nodeInstList);
    }

    /**
     * 根据选择的流程实例，更新相应的项目的进度计划。
     */
    public void updatePrjProPlanByProcInst() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        List<String> procInstIdList = entityRecordList.stream().map(item -> item.csCommId).collect(Collectors.toList());

        updatePrjProPlanByProcInst(procInstIdList);
    }

    public void updatePrjProPlanByProcInst(List<String> procInstIdList) {
        // 获取指定流程实例的节点实例：
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> nodeInstList = myJdbcTemplate.queryForList("select ni.* from wf_node_instance ni where ni.`STATUS`='AP' and ni.wf_process_instance_id in (" + procInstIdList.stream().map(item -> "?").collect(Collectors.joining(",")) + ") ORDER BY ni.wf_process_instance_id, ni.id", procInstIdList.toArray());
        update(myJdbcTemplate, nodeInstList);
    }

    private void update(MyJdbcTemplate myJdbcTemplate, List<Map<String, Object>> nodeInstList) {
        if (SharedUtil.isEmptyList(nodeInstList)) {
            return;
        }

        int i = 0;

        for (Map<String, Object> nodeInst : nodeInstList) {
            i++;
            // 获取对应的流程实例：
            Map<String, Object> procInst = myJdbcTemplate.queryForMap("select * from WF_PROCESS_INSTANCE t where t.id=?", nodeInst.get("WF_PROCESS_INSTANCE_ID"));

            updatePrjProPlanNode(procInst, nodeInst, false);

            // 每10个节点实例处理后，提交一次：
            if (i % 10 == 0) {
                myJdbcTemplate.execute("COMMIT");
            }
        }

        // 剩下的也提交了：
        myJdbcTemplate.execute("COMMIT");
    }

    /**
     * 更新项目进度计划节点。
     * 用于流程的节点进入时扩展。
     */
    public void updatePrjProPlanNode() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String procInstId = ExtJarHelper.procInstId.get();
        String nodeInstId = ExtJarHelper.nodeInstId.get();

        Map<String, Object> procInst = myJdbcTemplate.queryForMap("select * from WF_PROCESS_INSTANCE t where t.id=?", procInstId);
        Map<String, Object> nodeInst = myJdbcTemplate.queryForMap("select * from wf_node_instance t where t.id=?", nodeInstId);
        updatePrjProPlanNode(procInst, nodeInst, true);
    }

    private static Date getDateOnly(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 时
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        // 分
        calendar.set(Calendar.MINUTE, 0);
        // 秒
        calendar.set(Calendar.SECOND, 0);
        // 毫秒
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 更新项目进度计划节点。
     */
    private void updatePrjProPlanNode(Map<String, Object> procInst, Map<String, Object> nodeInst, boolean processWeekTask) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        String nodeInstId = JdbcMapUtil.getString(nodeInst, "ID");
        String nodeId = JdbcMapUtil.getString(nodeInst, "WF_NODE_ID");
        Date now = getDateOnly(JdbcMapUtil.getDate(nodeInst, "START_DATETIME"));

        String procInstId = JdbcMapUtil.getString(procInst, "ID");

        List<String> prjIdList = getPrjIdList(procInst);

        if (!SharedUtil.isEmptyList(prjIdList)) {
            prjIdList.forEach(pmPrjId -> {
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
                                    updateStartInfoForPlanNode(procInstId, nodeInstId, now, leafNode, processWeekTask);
                                }

                                // 若为结束节点：
                                String endWfNodeId = JdbcMapUtil.getString(leafNode, "LINKED_END_WF_NODE_ID");
                                if (nodeId.equals(endWfNodeId)) {

                                    // 若无开始信息，则先更新开始信息：
                                    if (SharedUtil.isEmptyObject(leafNode.get("ACTUAL_START_DATE"))) {
                                        updateStartInfoForPlanNode(procInstId, nodeInstId, now, leafNode, processWeekTask);
                                    }

                                    // 更新结束信息：
                                    String entCode = procInst.get("ENT_CODE").toString();
                                    updateEndInfoForPlanNode(entCode, procInstId, nodeInstId, now, leafNode, processWeekTask);
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
            });
        }
    }

    public static List<String> getPrjIdList(Map<String, Object> procInst) {
        String entCode = JdbcMapUtil.getString(procInst, "ENT_CODE");
        String entityRecordId = JdbcMapUtil.getString(procInst, "ENTITY_RECORD_ID");
        List<Map<String, Object>> entityRecordlist = ExtJarHelper.myJdbcTemplate.get().queryForList("select * from " + entCode + " t where t.id=?", entityRecordId);
        if (entityRecordlist.size() != 1) {
            // throw new BaseException("没有对应的表单的实体记录！（实体代码：" + entCode + "实体记录ID：" + entityRecordId + "）");
            return null;
        }

        Map<String, Object> entityRecord = entityRecordlist.get(0);
        if (!SharedUtil.isEmptyObject(entityRecord.get("pm_prj_id"))) {
            return Arrays.asList(entityRecord.get("pm_prj_id").toString());
        } else if (!SharedUtil.isEmptyObject(entityRecord.get("PM_PRJ_IDS"))) {
            return SharedUtil.strArrToStrList(entityRecord.get("PM_PRJ_IDS").toString().split(","));
        } else {
            return null;
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

    private void updateStartInfoForPlanNode(String procInstId, String nodeInstId, Date now, Map<String, Object> leafNode, boolean processWeekTask) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        Date startDate = getStandingBookDate(JdbcMapUtil.getString(leafNode, "ID"), procInstId, "1");
        if (startDate == null) {
            startDate = new Date();
        }

        Crud.from("pm_pro_plan_node").where().eq("ID", leafNode.get("ID")).update()
                // 设置进度信息：
                .set("PROGRESS_STATUS_ID", IN_PROCESSING).set("ACTUAL_START_DATE", startDate).set("ACTUAL_CARRY_DAYS", 1).set("ACTUAL_CURRENT_PRO_PERCENT", 10).set("ACTUAL_COMPL_DATE", null).set("ACTUAL_TOTAL_DAYS", null)
                // 设置关联信息：
                .set("LINKED_WF_PROCESS_INSTANCE_ID", procInstId).set("LINKED_START_WF_NODE_INSTANCE_ID", nodeInstId).set("LINKED_END_WF_NODE_INSTANCE_ID", null).exec();

        if (processWeekTask) {
            // 当前节点有关的工作台任务状态变为进行中
            myJdbcTemplate.update("update week_task set WEEK_TASK_STATUS_ID='1634118609016066048' where RELATION_DATA_ID=?", JdbcMapUtil.getString(leafNode, "ID"));
        }
    }

    private void updateEndInfoForPlanNode(String entCode, String procInstId, String nodeInstId, Date now, Map<String, Object> leafNode, boolean processWeekTask) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 更新节点状态判断
        Boolean izTrue = checkIzChange(entCode, procInstId, leafNode, myJdbcTemplate);
        if (izTrue) {
            // 计算时间工期
            int actualDays = 0;
            List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_pro_plan_node where id=?", leafNode.get("ID"));
            if (!CollectionUtils.isEmpty(list)) {
                Map<String, Object> currentNode = list.get(0);
                Date startDate = JdbcMapUtil.getDate(currentNode, "ACTUAL_START_DATE");
                try {
                    actualDays = DateTimeUtil.daysBetween(now, startDate);
                } catch (Exception e) {
                    log.error("计算实际完成工期失败！");
                }
            }
            Date endDate = getStandingBookDate(JdbcMapUtil.getString(leafNode, "ID"), procInstId, "2");
            if (endDate == null) {
                endDate = new Date();
            }
            Crud.from("pm_pro_plan_node").where().eq("ID", leafNode.get("ID")).update()
                    // 设置进度信息：
                    .set("PROGRESS_STATUS_ID", COMPLETED).set("ACTUAL_CURRENT_PRO_PERCENT", 100).set("ACTUAL_COMPL_DATE", endDate).set("ACTUAL_TOTAL_DAYS", actualDays)
                    // 设置关联信息：
                    .set("LINKED_WF_PROCESS_INSTANCE_ID", procInstId).set("LINKED_END_WF_NODE_INSTANCE_ID", nodeInstId).set("IZ_OVERDUE", "0").exec();


            myJdbcTemplate.update("update pm_pro_plan_node t set t.ACTUAL_CARRY_DAYS=t.ACTUAL_COMPL_DATE-t.ACTUAL_START_DATE+1,t.ACTUAL_TOTAL_DAYS=t.ACTUAL_COMPL_DATE-t.ACTUAL_START_DATE+1 WHERE t.id=?", leafNode.get("ID"));

            if (processWeekTask) {
                // 给后续节点发周任务
                WeekTaskUtils.sendPreNodeWeekTask(JdbcMapUtil.getString(leafNode, "ID"));
                // 当前节点有关的工作台任务状态变为已完成
                myJdbcTemplate.update("update week_task set WEEK_TASK_STATUS_ID='1634118629769482240' where RELATION_DATA_ID=?", JdbcMapUtil.getString(leafNode, "ID"));
            }
        }

    }

    /**
     * 更新全景计划节点状态-判断是否需要更改
     *
     * @param procInstId     流程实例id
     * @param leafNode       项目节点信息
     * @param myJdbcTemplate 数据源
     * @return 判断结果
     */
    private Boolean checkIzChange(String entCode, String procInstId, Map<String, Object> leafNode, MyJdbcTemplate myJdbcTemplate) {
        Boolean izChange = true;
        List<String> purchaseList = AttLinkDifferentProcess.getPurchaseList();
        if (purchaseList.contains(entCode)) {
            // 获取流程表信息等
//            List<Map<String, Object>> list1 = myJdbcTemplate.queryForList("select * from wf_process_instance where id = ?", procInstId);
            List<WfProcessInstance> list1 = WfProcessInstance.selectByWhere(new Where().eq(WfProcessInstance.Cols.ID,procInstId));
            if (!CollectionUtils.isEmpty(list1)) {
                String entityRecordId = list1.get(0).getEntityRecordId();
                String attData = JdbcMapUtil.getString(leafNode, "ATT_DATA"); // 节点中设置的采购事项类型
                List<Map<String, Object>> list2 = myJdbcTemplate.queryForList("select * from " + entCode + " where id = ?", entityRecordId);
                if (!CollectionUtils.isEmpty(list2)) {
                    String buyMatterId = JdbcMapUtil.getString(list2.get(0), "BUY_MATTER_ID"); // 采购事项
                    List<BaseMatterTypeCon> list3 = BaseMatterTypeCon.selectByWhere(new Where().eq(BaseMatterTypeCon.Cols.GR_SET_VALUE_ID, buyMatterId));
//                    List<Map<String, Object>> list3 = myJdbcTemplate.queryForList("select * from BASE_MATTER_TYPE_CON where GR_SET_VALUE_ID = ?", buyMatterId);
                    if (!CollectionUtils.isEmpty(list3)) {
                        String buyMatterTypeId = list3.get(0).getGrSetValueOneId(); // 采购事项类别
                        if (SharedUtil.isEmptyString(attData)) {
                            izChange = false;
                        } else if (SharedUtil.isEmptyString(buyMatterTypeId) || !buyMatterTypeId.equals(attData)) {
                            izChange = false;
                        }
                    }
                }
            }
        }
        return izChange;
    }


    /**
     * 获取台账中的对应日期
     *
     * @param nodeId     节点ID
     * @param procInstId 流程实例ID
     * @param mark       1-开始节点  2-结束节点
     * @return
     */
    private Date getStandingBookDate(String nodeId, String procInstId, String mark) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_pro_plan_node where id=?", nodeId);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> nodeMap = list.get(0);
            String processId = JdbcMapUtil.getString(nodeMap, "LINKED_WF_PROCESS_ID");
            if (!Strings.isNullOrEmpty(processId)) {
                String filed;
                if ("1".equals(mark)) {
                    filed = JdbcMapUtil.getString(nodeMap, "START_DATE_FIELD");
                } else {
                    filed = JdbcMapUtil.getString(nodeMap, "END_DATE_FIELD");
                }
                if (Strings.isNullOrEmpty(filed)) {
                    return null;
                }
                //判断台账有没有这个字段
                List<Map<String, Object>> attInfoList = myJdbcTemplate.queryForList("SELECT m.*,att.id as att_id,att.`CODE` as att_code,ifnull(aet.ATT_NAME,att.`NAME`) as att_name FROM \n" +
                        "( \n" +
                        "\tselect c.id,c.code,c.name from wf_process a \n" +
                        "\tLEFT JOIN AD_SINGLE_ENT_VIEW b ON a.STARTABLE_SEV_IDS = b.id \n" +
                        "\tLEFT JOIN AD_ENT c ON b.AD_ENT_ID = c.id where a.id =? \n" +
                        ") m \n" +
                        "left join AD_ENT_ATT aet on m.id = aet.AD_ENT_ID \n" +
                        "left join ad_att att on att.id = aet.AD_ATT_ID where att.id=?", processId, filed);
                if (!CollectionUtils.isEmpty(attInfoList)) {
                    Map<String, Object> attInfo = attInfoList.get(0);
                    String table = JdbcMapUtil.getString(attInfo, "code");
                    String fields =  JdbcMapUtil.getString(attInfo, "att_code");
                    List<Map<String, Object>> list1 = myJdbcTemplate.queryForList("select * from wf_process_instance where id=?", procInstId);
                    if (!CollectionUtils.isEmpty(list1)) {
                        Map<String, Object> insInfo = list1.get(0);
                        String dataId = JdbcMapUtil.getString(insInfo, "ENTITY_RECORD_ID");
                        String sql = "select * from " + table + " where id='" + dataId + "'";
                        List<Map<String, Object>> list2 = myJdbcTemplate.queryForList(sql);
                        if (!CollectionUtils.isEmpty(list2)) {
                            Map<String, Object> dataMap = list2.get(0);
                            String dataOrg = JdbcMapUtil.getString(dataMap, fields);
                            if (!Strings.isNullOrEmpty(dataOrg)) {
                                return getDateOnly(DateTimeUtil.stringToDate(dataOrg));
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
