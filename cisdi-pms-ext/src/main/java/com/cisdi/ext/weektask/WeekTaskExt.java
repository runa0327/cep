package com.cisdi.ext.weektask;

import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.ProPlanUtils;
import com.cisdi.ext.util.StringUtil;
import com.cisdi.ext.util.WeeklyUtils;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.DateTimeUtil;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title WeekTaskExt
 * @package com.cisdi.ext.weektask
 * @description
 * @date 2023/3/13
 */
public class WeekTaskExt {


    /**
     * 工作台本周工作列表
     */
    public void weekTaskList() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        int pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
        int pageIndex = Integer.parseInt(String.valueOf(map.get("pageIndex")));
        String userId = ExtJarHelper.loginInfo.get().userId;
        Map<String, String> weekDay = WeeklyUtils.weekBeginningAndEnding();
        StringBuilder sb = new StringBuilder();
        sb.append(" select a.* from (");
        sb.append("select wt.*,gsv.`NAME` as task_status,au.name as transferUser,ifnull(CAN_DISPATCH,0) as isTransfer,TRANSFER_USER as transferUserId,gsv.SEQ_NO as SEQ_NO from week_task wt " +
                "left join gr_set_value gsv on wt.WEEK_TASK_STATUS_ID = gsv.id  " +
                "left join ad_user au on au.id = wt.TRANSFER_USER left join pm_prj pj on wt.pm_prj_id = pj.id " +
                "where AD_USER_ID = '").append(userId).append("' and PUBLISH_START between '")
                .append(weekDay.get("begin")).append("' and '").append(weekDay.get("end"))
                .append("' and (pj.PROJECT_STATUS != '1661568714048413696' or pj.PROJECT_STATUS is null )");
        sb.append(" union all ");
        sb.append("select wt.*,gsv.`NAME` as task_status,au.name as transferUser,ifnull(CAN_DISPATCH,0) as isTransfer,TRANSFER_USER as transferUserId,gsv.SEQ_NO as SEQ_NO from week_task wt " +
                "left join gr_set_value gsv on wt.WEEK_TASK_STATUS_ID = gsv.id   " +
                "left join ad_user au on au.id = wt.TRANSFER_USER left join pm_prj pj on wt.pm_prj_id = pj.id  " +
                "where AD_USER_ID = '").append(userId).append("' and PUBLISH_START< '").append(weekDay.get("begin")).append("' and WEEK_TASK_STATUS_ID in ('1634118574056542208','1634118609016066048','1644140821106384896')");
        sb.append(" and (pj.PROJECT_STATUS != '1661568714048413696' or pj.PROJECT_STATUS is null )");
        sb.append(" ) a order by a.SEQ_NO ");

        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<WeekTask> weekTaskList = list.stream().map(p -> {
            WeekTask weekTask = new WeekTask();
            weekTask.id = JdbcMapUtil.getString(p, "ID");
            weekTask.userId = JdbcMapUtil.getString(p, "AD_USER_ID");
            weekTask.title = JdbcMapUtil.getString(p, "TITLE");
            weekTask.content = JdbcMapUtil.getString(p, "CONTENT");
            weekTask.publishStart = StringUtil.withOutT(JdbcMapUtil.getString(p, "PUBLISH_START"));
            weekTask.taskStatus = JdbcMapUtil.getString(p, "task_status");
            weekTask.isTransfer = JdbcMapUtil.getString(p, "isTransfer");
            weekTask.transferUserId = JdbcMapUtil.getString(p, "transferUserId");
            weekTask.transferUser = JdbcMapUtil.getString(p, "transferUser");
            weekTask.transferTime = JdbcMapUtil.getString(p, "TRANSFER_TIME") == null ? null : StringUtil.withOutT(JdbcMapUtil.getString(p, "TRANSFER_TIME"));
            return weekTask;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(weekTaskList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
            OutSide outSide = new OutSide();
            outSide.total = totalList.size();
            outSide.weekTaskList = weekTaskList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }

    }

    /**
     * 本周工作任务详情
     */
    public void weekTaskView() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String id = JdbcMapUtil.getString(map, "id");
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select wt.*,gsv.`NAME` as task_status,CAN_DISPATCH,TRANSFER_USER as transferUserId,au.name as transferUser,TRANSFER_TIME,pm.name as projectName,wt.PLAN_COMPL_DATE as PLAN_COMPL_DATE " +
                " from week_task wt " +
                "left join gr_set_value gsv on wt.WEEK_TASK_STATUS_ID = gsv.id  " +
                "left join ad_user au on au.id = wt.TRANSFER_USER " +
                "left join pm_prj pm on pm.id = wt.pm_prj_id " +
                "left join pm_pro_plan_node pn on pn.id = wt.RELATION_DATA_ID " +
                "where wt.id=?", id);
        if (!CollectionUtils.isEmpty(list)) {
            List<WeekTask> weekTaskList = list.stream().map(p -> {
                WeekTask weekTask = new WeekTask();
                weekTask.id = JdbcMapUtil.getString(p, "ID");
                weekTask.userId = JdbcMapUtil.getString(p, "AD_USER_ID");
                weekTask.title = JdbcMapUtil.getString(p, "TITLE");
                weekTask.content = JdbcMapUtil.getString(p, "CONTENT");
                weekTask.publishStart = StringUtil.withOutT(JdbcMapUtil.getString(p, "PUBLISH_START"));
                weekTask.taskStatus = JdbcMapUtil.getString(p, "task_status");
                weekTask.isTransfer = JdbcMapUtil.getString(p, "CAN_DISPATCH");
                weekTask.transferUserId = JdbcMapUtil.getString(p, "transferUserId");
                weekTask.transferUser = JdbcMapUtil.getString(p, "transferUser");
                weekTask.transferTime = JdbcMapUtil.getString(p, "TRANSFER_TIME");
                weekTask.projectId = JdbcMapUtil.getString(p, "pm_prj_id");
                weekTask.projectName = JdbcMapUtil.getString(p, "projectName");
                weekTask.completeTime = JdbcMapUtil.getString(p, "PLAN_COMPL_DATE");
                return weekTask;
            }).collect(Collectors.toList());
            WeekTask weekTask = weekTaskList.get(0);
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(weekTask), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }
    }

    /**
     * 转办任务列表
     */
    public void transferTaskList() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        int pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
        int pageIndex = Integer.parseInt(String.valueOf(map.get("pageIndex")));
        String userId = ExtJarHelper.loginInfo.get().userId;
        Map<String, String> weekDay = WeeklyUtils.weekBeginningAndEnding();
        StringBuilder sb = new StringBuilder();
        sb.append("select wt.*,gsv.`NAME` as task_status,TRANSFER_USER as transferUserId,au.name as transferUser,CAN_DISPATCH,TRANSFER_TIME,gsv.SEQ_NO as SEQ_NO from week_task wt " +
                "left join gr_set_value gsv on wt.WEEK_TASK_STATUS_ID = gsv.id  " +
                "left join ad_user au on au.id = wt.AD_USER_ID LEFT JOIN PM_PRJ pj on wt.pm_prj_id = pj.id " +
                "where wt.id  in (select id from week_task where TRANSFER_USER='").append(userId).append("')")
                .append(" and PUBLISH_START between '")
                .append(weekDay.get("begin")).append("' and '").append(weekDay.get("end"))
                .append("' and (pj.PROJECT_STATUS != '1661568714048413696' or pj.PROJECT_STATUS is null ) order by SEQ_NO desc");

        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<WeekTask> weekTaskList = list.stream().map(p -> {
            WeekTask weekTask = new WeekTask();
            weekTask.id = JdbcMapUtil.getString(p, "ID");
            weekTask.userId = JdbcMapUtil.getString(p, "AD_USER_ID");
            weekTask.title = JdbcMapUtil.getString(p, "TITLE");
            weekTask.content = JdbcMapUtil.getString(p, "CONTENT");
            weekTask.publishStart = StringUtil.withOutT(JdbcMapUtil.getString(p, "PUBLISH_START"));
            weekTask.taskStatus = JdbcMapUtil.getString(p, "task_status");
            weekTask.isTransfer = JdbcMapUtil.getString(p, "CAN_DISPATCH");
            weekTask.transferUserId = JdbcMapUtil.getString(p, "transferUserId");
            weekTask.transferUser = JdbcMapUtil.getString(p, "transferUser");
            weekTask.transferTime = JdbcMapUtil.getString(p, "TRANSFER_TIME");
            return weekTask;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(weekTaskList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
            OutSide outSide = new OutSide();
            outSide.total = totalList.size();
            outSide.weekTaskList = weekTaskList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 不涉及
     */
    public void noInvolve() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        //把任务状态改为不涉及
        myJdbcTemplate.update("update WEEK_TASK set WEEK_TASK_STATUS_ID='1644140265205915648',REASON_EXPLAIN=? where id=?", map.get("reason"), map.get("id"));
        //把节点状态变成未涉及
        myJdbcTemplate.update("update pm_pro_plan_node set PROGRESS_STATUS_ID ='0099902212142036278' where id =(select RELATION_DATA_ID from week_task where id=?)", map.get("id"));
        //当前节点的子节点都是未涉及，那么当前节点也是未涉及
        nodeNoInvolve(JdbcMapUtil.getString(map, "id"));
        //继续出发后续节点
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select RELATION_DATA_ID from week_task where id=?", map.get("id"));
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> nodeObj = list.get(0);
            String nodeId = JdbcMapUtil.getString(nodeObj, "RELATION_DATA_ID");
            if (!Strings.isNullOrEmpty(nodeId)) {
                sendPreNodeWeekTask(nodeId);
            }
        }

    }

    /**
     * 任务转办
     */
    public void transferTask() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from WEEK_TASK where id=?", map.get("id"));
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> weekData = list.get(0);
            //把当前任务的用户改成指定的人，转办人写入原记录的用户
            Crud.from("WEEK_TASK").where().eq("ID", map.get("id")).update()
                    .set("AD_USER_ID", map.get("userId")).set("CAN_DISPATCH", "1").set("TRANSFER_USER", weekData.get("AD_USER_ID")).set("TRANSFER_TIME", new Date()).exec();
        }
    }

    /**
     * 去处理
     */
    public void dealWith() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(" select pppn.*,wt.pm_prj_id as projectId,pm.`NAME` as projectName from pm_pro_plan_node pppn \n" +
                " left join WEEK_TASK wt on pppn.id = wt.RELATION_DATA_ID \n" +
                " left join pm_prj pm on pm.id = wt.PM_PRJ_ID \n" +
                " where wt.id= ?", map.get("id"));
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> node = list.get(0);
            String processId = JdbcMapUtil.getString(node, "LINKED_WF_PROCESS_ID");
            //查询流程的第一个节点的view
            List<Map<String, Object>> dataList = myJdbcTemplate.queryForList("select wn.ad_view_id as AD_VIEW_ID,wp.EXTRA_INFO  as EXTRA_INFO from wf_node wn left join WF_PROCESS wp on wp.id = wn.WF_PROCESS_ID where wn.NODE_TYPE = 'START_EVENT' AND wn.`STATUS` = 'AP' and wn.WF_PROCESS_ID= ? ", processId);
            if (!CollectionUtils.isEmpty(dataList)) {
                Map<String, Object> dataMap = dataList.get(0);
                String viewId = JdbcMapUtil.getString(dataMap, "AD_VIEW_ID");
                ProcessData resData = new ProcessData();
                resData.processId = processId;
                resData.viewId = viewId;
                resData.icon = JdbcMapUtil.getString(dataMap, "EXTRA_INFO");
                resData.title = JdbcMapUtil.getString(node, "NAME");
                Project project = new Project();
                project.id = JdbcMapUtil.getString(node, "projectId");
                project.name = JdbcMapUtil.getString(node, "projectName");
                resData.project = project;
                List<AttData> attDataList = new ArrayList<>();
                if (JdbcMapUtil.getString(node, "ATT_DATA") != null) {
                    AttData attData = new AttData();
                    attData.AD_ATT_CODE = "BUY_MATTER_TYPE_ID";
                    attData.ATT_VALUE = JdbcMapUtil.getString(node, "ATT_DATA");
                    String txt = "";
                    List<Map<String, Object>> list1 = myJdbcTemplate.queryForList("select * from gr_set_value where id=?", JdbcMapUtil.getString(node, "ATT_DATA"));
                    if (!CollectionUtils.isEmpty(list1)) {
                        Map<String, Object> mapData = list1.get(0);
                        txt = JdbcMapUtil.getString(mapData, "NAME");
                    }
                    attData.ATT_TXT = txt;
                    attData.FOR_PROC = "1";
                    attDataList.add(attData);
                }
                resData.attDataList = attDataList;
                Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
                ExtJarHelper.returnValue.set(outputMap);
            } else {
                ExtJarHelper.returnValue.set(Collections.emptyMap());
            }
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }

    }

    /**
     * 改变任务状态
     */
    public void changeWeekTaskStatus() {
//        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
//        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
//        String opr = JdbcMapUtil.getString(map, "opr");
//        String status = null;
//        if ("去处理".equals(opr)) {
//            status = "1634118609016066048";//进行中
//            String instanceId = JdbcMapUtil.getString(map, "instanceId");
//            if (Strings.isNotEmpty(instanceId)) {
//                myJdbcTemplate.update("update pm_pro_plan_node set LINKED_WF_PROCESS_INSTANCE_ID=? where id =(select RELATION_DATA_ID from week_task where id=? )", instanceId, map.get("id"));
//            }
//        }
//        myJdbcTemplate.update("update WEEK_TASK set WEEK_TASK_STATUS_ID=? where id=?", status, map.get("id"));
    }


    /**
     * 查看进度
     */
    public void checkProcess() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pppn.*,pm_prj_id from pm_pro_plan_node pppn left join WEEK_TASK wt on pppn.id = wt.RELATION_DATA_ID where wt.id= ?", map.get("id"));
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> node = list.get(0);
            String processId = JdbcMapUtil.getString(node, "LINKED_WF_PROCESS_ID");
            //查询流程的第一个节点的view
            List<Map<String, Object>> dataList = myJdbcTemplate.queryForList("select wn.ad_view_id as AD_VIEW_ID,wp.EXTRA_INFO  as EXTRA_INFO,wp.name as wpname from wf_node wn left join WF_PROCESS wp on wp.id = wn.WF_PROCESS_ID where wn.NODE_TYPE = 'START_EVENT' AND wn.`STATUS` = 'AP' and wn.WF_PROCESS_ID= ? ", processId);
            if (!CollectionUtils.isEmpty(dataList)) {
                Map<String, Object> dataMap = dataList.get(0);
                String viewId = JdbcMapUtil.getString(dataMap, "AD_VIEW_ID");
                Map<String, String> res = new HashMap<>();
                res.put("processId", processId);
                res.put("WF_NODE_VIEW_ID", viewId);
                res.put("icon", JdbcMapUtil.getString(dataMap, "EXTRA_INFO"));
                res.put("title", JdbcMapUtil.getString(dataMap, "wpname"));
                res.put("projectId", JdbcMapUtil.getString(node, "pm_prj_id"));
                res.put("WF_PROCESS_INSTANCE_ID", JdbcMapUtil.getString(node, "LINKED_WF_PROCESS_INSTANCE_ID"));
                List<Map<String, Object>> instanceList = myJdbcTemplate.queryForList("select * from wf_process_instance where id=?", JdbcMapUtil.getString(node, "LINKED_WF_PROCESS_INSTANCE_ID"));
                if (!CollectionUtils.isEmpty(instanceList)) {
                    Map<String, Object> objectMap = instanceList.get(0);
                    res.put("ENTITY_RECORD_ID", JdbcMapUtil.getString(objectMap, "ENTITY_RECORD_ID"));
                }

                res.put("nodeId", JdbcMapUtil.getString(node, "ID"));
                ExtJarHelper.returnValue.set(res);
            } else {
                ExtJarHelper.returnValue.set(Collections.emptyMap());
            }
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }
    }

    /**
     * 延期申请
     */
    public void delayApply() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pppn.*,wt.*,pm.`NAME` as projectName,pppn.name nodeName from WEEK_TASK wt \n" +
                " left join pm_prj pm on wt.PM_PRJ_ID = pm.id \n" +
                " left join pm_pro_plan_node pppn on wt.RELATION_DATA_ID = pppn.id \n" +
                " where wt.id=? ", map.get("id"));
        Map<String, Object> processNameMap = myJdbcTemplate.queryForMap("select name from wf_process where id = '1649227469557063680'");
        ProcessData processData = new ProcessData();
        processData.processId = "1649227469557063680";
        processData.viewId = "1649226141279707136";
        List<Map<String, Object>> dataList = myJdbcTemplate.queryForList("select wn.ad_view_id as AD_VIEW_ID,wp.EXTRA_INFO  as EXTRA_INFO from wf_node wn left join WF_PROCESS wp on wp.id = wn.WF_PROCESS_ID where wn.NODE_TYPE = 'START_EVENT' AND wn.`STATUS` = 'AP' and wn.WF_PROCESS_ID= ? ", processData.processId);

        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> pmData = list.get(0);
            Project project = new Project();
            project.id = JdbcMapUtil.getString(pmData, "PM_PRJ_ID");
            project.name = JdbcMapUtil.getString(pmData, "projectName");
            processData.project = project;
            Node node = new Node();
            node.nodeId = JdbcMapUtil.getString(pmData, "RELATION_DATA_ID");
            node.nodeName = JdbcMapUtil.getString(pmData, "nodeName");
            processData.node = node;
            if (!CollectionUtils.isEmpty(dataList)) {
                processData.icon = JdbcMapUtil.getString(dataList.get(0), "EXTRA_INFO");
            }
            processData.title = JdbcMapUtil.getString(processNameMap, "name");
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(processData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }
    }


    public static class WeekTask {
        public String id;
        public String userId;
        //标题
        public String title;
        //内容
        public String content;
        //发布时间
        public String publishStart;
        //状态
        public String taskStatus;
        //是否转办 0-否 1-是
        public String isTransfer;
        //项目ID
        public String projectId;
        //项目名称
        public String projectName;
        //转办人ID
        public String transferUserId;
        //转办人名称
        public String transferUser;
        //转办时间
        public String transferTime;

        public String completeTime;
    }

    public static class OutSide {
        public List<WeekTask> weekTaskList;
        public Integer total;
        public List<DelayApplyHistory> historyList;
    }

    public static class ProcessData {
        public String processId;
        public String viewId;
        public String icon;
        public String title;

        public Project project;

        public Node node;

        public String instanceId;

        public String entId;
        public List<AttData> attDataList;

    }

    public static class AttData {
        public String AD_ATT_ID;

        public String AD_ATT_CODE;
        public String ATT_VALUE;

        public String ATT_TXT;
        public String FOR_NODE;
        public String FOR_PROC;
    }


    public static class Project {
        public String id;
        public String name;
    }

    public static class Node {
        public String nodeId;
        public String nodeName;
    }

    public static class DelayApplyHistory {
        public String id;
        //序号
        public String serNo;
        //延期天数
        public String delayNum;
        //延期说明
        public String description;
        //发起人
        public String applyUser;
        //发起时间
        public String applyTime;
        //延期申请节点
        public String nodeName;
        //计划天数
        public String days;
        //延期天数
        public String delayDays;
        //审批状态
        public String astStatus;
    }

    public static final String NOT_STARTED = "0099799190825106800";
    public static final String IN_PROCESSING = "0099799190825106801";
    public static final String COMPLETED = "0099799190825106802";

    public static final String NOT_INVOLVE = "0099902212142036278";

    /**
     * 给他的后续节点发周任务
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

        String msg = "{0}【{1}】计划将在{2}完成，请及时处理！";
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
                String title = objectMap.get("prjName") + "-" + processName;
                String content = MessageFormat.format(msg, objectMap.get("prjName"), processName, dateOrg);
                myJdbcTemplate.update("update WEEK_TASK set AD_USER_ID=?,TITLE=?,CONTENT=?,PUBLISH_START=?,WEEK_TASK_STATUS_ID=?,WEEK_TASK_TYPE_ID=?,RELATION_DATA_ID=?,CAN_DISPATCH='0',PM_PRJ_ID=?,PLAN_COMPL_DATE=? where id=?",
                        userId, title, content, new Date(), "1634118574056542208", "1635080848313290752", objectMap.get("ID"), objectMap.get("projectId"), objectMap.get("PLAN_COMPL_DATE"), id);
            }
        }
    }

    /**
     * 当前节点的子节点都是未涉及，那么当前节点也是未涉及
     *
     * @param weekTaskId
     */
    private void nodeNoInvolve(String weekTaskId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_pro_plan_node where id=(select RELATION_DATA_ID from week_task where id=?)", weekTaskId);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> dataMap = list.get(0);
            refreshParentStatus(JdbcMapUtil.getString(dataMap, "PM_PRO_PLAN_NODE_PID"));
            List<Map<String, Object>> parentList = ProPlanUtils.selectAllParentNode(JdbcMapUtil.getString(dataMap, "ID"));
            if (!CollectionUtils.isEmpty(parentList)) {
                List<Map<String, Object>> notStart = parentList.stream().filter(p -> "0099799190825106800".equals(JdbcMapUtil.getString(p, "PROGRESS_STATUS_ID"))).collect(Collectors.toList());
                notStart.forEach(item -> {
                    myJdbcTemplate.update("update pm_pro_plan_node set PROGRESS_STATUS_ID='0099799190825106801' where id=?", item.get("ID"));
                });

            }
        }
    }

    /**
     * 刷新父级节点的未涉及
     *
     * @param pid
     */
    private void refreshParentStatus(String pid) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> currentList = myJdbcTemplate.queryForList("select * from pm_pro_plan_node where id=?", pid);
        if (!CollectionUtils.isEmpty(currentList)) {
            Map<String, Object> currentNode = currentList.get(0);
            List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_pro_plan_node where PM_PRO_PLAN_NODE_PID=?", pid);
            if (!CollectionUtils.isEmpty(list)) {
                //统计状态是未涉及的记录条数
                long count = list.stream().filter(p -> "0099902212142036278".equals(JdbcMapUtil.getString(p, "PROGRESS_STATUS_ID"))).count();
                if (list.size() == count) {
                    //未涉及数量等于子节点个数，当前节点也改为未涉及
                    myJdbcTemplate.update("update pm_pro_plan_node set PROGRESS_STATUS_ID='0099902212142036278' where id=?", pid);
                    //查询pid的父级
                    List<Map<String, Object>> pidList = myJdbcTemplate.queryForList("select * from pm_pro_plan_node WHERE id=?", currentNode.get("PM_PRO_PLAN_NODE_PID"));
                    if (!CollectionUtils.isEmpty(pidList)) {
                        Map<String, Object> mapData = pidList.get(0);
                        System.out.println(mapData.get("NAME"));
                        refreshParentStatus(JdbcMapUtil.getString(mapData, "ID"));
                    }
                } else {
                    List<Map<String, Object>> parentList = ProPlanUtils.selectAllParentNode(pid);
                    if (!CollectionUtils.isEmpty(parentList)) {
                        List<Map<String, Object>> notStart = parentList.stream().filter(p -> "0099799190825106800".equals(JdbcMapUtil.getString(p, "PROGRESS_STATUS_ID"))).collect(Collectors.toList());
                        notStart.forEach(item -> {
                            myJdbcTemplate.update("update pm_pro_plan_node set PROGRESS_STATUS_ID='0099799190825106801' where id=?", item.get("ID"));
                        });
                    }

                }
            }
        }

    }


    /**
     * 本周工作任务台账
     */
    public void weekTaskStandingBook() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam requestParam = JsonUtil.fromJson(json, RequestParam.class);
        int pageSize = requestParam.pageSize;
        int pageIndex = requestParam.pageIndex;
        StringBuilder sb = new StringBuilder();
        sb.append("select pm.*,gg.`NAME` as pro_status,gsv.`NAME` as location,gs.`NAME` as type from pm_prj pm  " +
                " left join gr_set_value gg on pm.PROJECT_PHASE_ID = gg.id  " +
                " left join gr_set_value gsv on pm.BASE_LOCATION_ID = gsv.id " +
                " left join gr_set_value gs on pm.PROJECT_TYPE_ID = gs.id  " +
                " where pm.PROJECT_SOURCE_TYPE_ID = '0099952822476441374' and pm.`STATUS`='ap' and pm.IZ_FORMAL_PRJ = 1 and (pm.PROJECT_STATUS != '1661568714048413696' or pm.PROJECT_STATUS is null ) ");
        if (!Strings.isNullOrEmpty(requestParam.name)) {
            sb.append(" and `NAME` like '%").append(requestParam.name).append("%'");
        }
        if (!Strings.isNullOrEmpty(requestParam.ownner)) {
            sb.append(" and CUSTOMER_UNIT = '").append(requestParam.ownner).append("'");
        }
        if (!Strings.isNullOrEmpty(requestParam.location)) {
            sb.append(" and BASE_LOCATION_ID = '").append(requestParam.location).append("'");
        }
        if (!Strings.isNullOrEmpty(requestParam.type)) {
            sb.append(" and PROJECT_TYPE_ID = '").append(requestParam.type).append("'");
        }
        sb.append(" order by pm.PM_CODE desc ");
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<DataInfo> dataInfoList = list.stream().map(this::convertData).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(dataInfoList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
            ResData resData = new ResData();
            resData.total = totalList.size();
            resData.dataInfoList = dataInfoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 本周工作任务台账标题
     */
    public void weekTaskStandingBookTitle() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_prj pm where pm.PROJECT_SOURCE_TYPE_ID = '0099952822476441374' and pm.`STATUS`='ap' and pm.IZ_FORMAL_PRJ = 1 and (pm.PROJECT_STATUS != '1661568714048413696' or pm.PROJECT_STATUS is null ) ");
        TitleCount titleCount = new TitleCount();
        titleCount.prjCount = list.size();
        List<Map<String, Object>> weekTaskList = myJdbcTemplate.queryForList("select * from week_task where status='ap'");
        titleCount.taskCount = weekTaskList.size();
        titleCount.ywcCount = (int) weekTaskList.stream().filter(p -> TASK_COMPLETED.equals(JdbcMapUtil.getString(p, "WEEK_TASK_STATUS_ID"))).count();
        titleCount.wksCount =(int) weekTaskList.stream().filter(p -> TASK_NOT_STARTED.equals(JdbcMapUtil.getString(p, "WEEK_TASK_STATUS_ID"))).count();
        titleCount.jxzCount = (int) weekTaskList.stream().filter(p -> TASK_IN_PROCESSING.equals(JdbcMapUtil.getString(p, "WEEK_TASK_STATUS_ID"))).count();
        titleCount.cqwwcCount = (int) weekTaskList.stream().filter(p -> TASK_OVERDUE.equals(JdbcMapUtil.getString(p, "WEEK_TASK_STATUS_ID"))).count();
        titleCount.cqywcCount = (int) weekTaskList.stream().filter(p -> {
            if (TASK_COMPLETED.equals(JdbcMapUtil.getString(p, "WEEK_TASK_STATUS_ID"))) {
                if (JdbcMapUtil.getString(p, "PLAN_COMPL_DATE") != null && JdbcMapUtil.getString(p, "ACTUAL_COMPL_DATE") != null) {
                    Date plan = DateTimeUtil.stringToDate(JdbcMapUtil.getString(p, "PLAN_COMPL_DATE"));
                    Date actual = DateTimeUtil.stringToDate(JdbcMapUtil.getString(p, "ACTUAL_COMPL_DATE"));
                    if (actual.before(plan)) {
                        return true;
                    }
                }
            }
            return false;
        }).count();

        titleCount.wsjCount =  (int) weekTaskList.stream().filter(p -> TASK_NOT_INVOLVE.equals(JdbcMapUtil.getString(p, "WEEK_TASK_STATUS_ID"))).count();
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(titleCount), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    /**
     * 本周工作任务台账详情
     */
    public void weekTaskStandingBookView() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = JdbcMapUtil.getString(map, "projectId");
        String status = JdbcMapUtil.getString(map, "status");
        String taskName = JdbcMapUtil.getString(map, "taskName");
        String user = JdbcMapUtil.getString(map, "user");
        int pageSize = JdbcMapUtil.getInt(map, "pageSize");
        int pageIndex = JdbcMapUtil.getInt(map, "pageIndex");
        StringBuilder sb = new StringBuilder();
        sb.append(" select wt.ID as id,TITLE,ad.`NAME` as user,gsv.`NAME` as status,if(ifnull(CAN_DISPATCH,'0') = '0','否','是') as iz_tran,TRANSFER_TIME,au.`NAME` as tran_user,REASON_EXPLAIN,PM_PRJ_ID,pm.NAME as projectName from week_task wt \n" +
                " left join pm_prj pm on pm.id = wt.PM_PRJ_ID "+
                " left join ad_user ad on wt.AD_USER_ID = ad.id \n" +
                " left join gr_set_value gsv on wt.WEEK_TASK_STATUS_ID = gsv.id \n" +
                " left join ad_user au on wt.TRANSFER_USER = au.id where wt.status ='ap' ");
        if (!Strings.isNullOrEmpty(projectId)) {
            sb.append(" and wt.PM_PRJ_ID ='").append(projectId).append("'");
        }
        if (!Strings.isNullOrEmpty(status)) {
            if ("全部".equals(status)) {
                sb.append(" and 1=1 ");
            } else if ("超期完成".equals(status)) {
                sb.append(" and wt.WEEK_TASK_STATUS_ID='1634118629769482240' and wt.ACTUAL_COMPL_DATE > wt.PLAN_COMPL_DATE ");
            } else {
                String stausValue = null;
                switch (status) {
                    case "未开始":
                        stausValue = TASK_NOT_STARTED;
                        break;
                    case "进行中":
                        stausValue = TASK_IN_PROCESSING;
                        break;
                    case "已完结":
                        stausValue = TASK_COMPLETED;
                        break;
                    case "不涉及":
                        stausValue = TASK_NOT_INVOLVE;
                        break;
                    case "超期未完成":
                        stausValue = TASK_OVERDUE;
                        break;
                }
                sb.append(" and wt.WEEK_TASK_STATUS_ID ='").append(stausValue).append("'");
            }
        }
        if (!Strings.isNullOrEmpty(taskName)) {
            sb.append(" and wt.TITLE like '%").append(taskName).append("%'");
        }
        if (!Strings.isNullOrEmpty(user)) {
            sb.append(" and ad.`NAME` like '%").append(user).append("%'");
        }
        sb.append(" order by wt.PUBLISH_START desc ");
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<WeekTaskInfo> weekTaskInfoList = list.stream().map(this::convertWeekTaskInfo).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(weekTaskInfoList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
            ResData resData = new ResData();
            resData.total = totalList.size();
            resData.weekTaskInfoList = weekTaskInfoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 延期申请列表
     */
    public void delayApplyList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<DelayApplyHistory> historyList = getDelayApplyList(JdbcMapUtil.getString(map, "id"));
        if (CollectionUtils.isEmpty(historyList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            WeekTaskExt.OutSide outSide = new WeekTaskExt.OutSide();
            outSide.historyList = historyList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 延期申请列表-查看
     */
    public void delayApplyCheck() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pe.*,pm.`NAME` as projectName,pn.`NAME` as nodeName,wpi.id as instanceId from PM_EXTENSION_REQUEST_REQ pe left join pm_prj pm on pm.id = pe.PM_PRJ_ID\n" +
                "left join pm_pro_plan_node pn on pe.PM_PRO_PLAN_NODE_ID = pn.id \n" +
                "left join wf_process_instance wpi on wpi.ENTITY_RECORD_ID = pe.id "+
                "where pe.id=? ", map.get("id"));
        Map<String, Object> processNameMap = myJdbcTemplate.queryForMap("select name from wf_process where id = '1649227469557063680'");
        ProcessData processData = new ProcessData();
        processData.processId = "1649227469557063680";
        processData.viewId = "1649226141279707136";
        List<Map<String, Object>> dataList = myJdbcTemplate.queryForList("select wn.ad_view_id as AD_VIEW_ID,wp.EXTRA_INFO  as EXTRA_INFO from wf_node wn left join WF_PROCESS wp on wp.id = wn.WF_PROCESS_ID where wn.NODE_TYPE = 'START_EVENT' AND wn.`STATUS` = 'AP' and wn.WF_PROCESS_ID= ? ", processData.processId);

        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> pmData = list.get(0);
            Project project = new Project();
            project.id = JdbcMapUtil.getString(pmData, "PM_PRJ_ID");
            project.name = JdbcMapUtil.getString(pmData, "projectName");
            processData.project = project;
            Node node = new Node();
            node.nodeId = JdbcMapUtil.getString(pmData, "PM_PRO_PLAN_NODE_ID");
            node.nodeName = JdbcMapUtil.getString(pmData, "nodeName");
            processData.node = node;
            if (!CollectionUtils.isEmpty(dataList)) {
                processData.icon = JdbcMapUtil.getString(dataList.get(0), "EXTRA_INFO");
            }
            processData.title = JdbcMapUtil.getString(processNameMap, "name");

            processData.instanceId = JdbcMapUtil.getString(pmData, "instanceId");
            processData.entId = JdbcMapUtil.getString(pmData, "ID");
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(processData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }
    }


    public List<DelayApplyHistory> getDelayApplyList(String id) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pe.*,ad.`NAME` as user_name,pnn.name as nodeName,ast.name as astName from PM_EXTENSION_REQUEST_REQ pe \n" +
                " left join pm_pro_plan_node pnn on pe.PM_PRO_PLAN_NODE_ID = pnn.id"+
                " left join ad_user ad on pe.CRT_USER_ID = ad.id \n" +
                " left join week_task wt on wt.RELATION_DATA_ID = pe.PM_PRO_PLAN_NODE_ID\n" +
                " left join ad_status ast on pe.status = ast.id "+
                " where wt.id = ?", id);
        AtomicInteger index = new AtomicInteger(1);
        return list.stream().map(p -> {
            DelayApplyHistory history = new DelayApplyHistory();
            history.id = JdbcMapUtil.getString(p, "ID");
            history.serNo = String.valueOf(index.getAndIncrement());
            history.delayNum = JdbcMapUtil.getString(p, "DAYS_ONE");
            history.description = JdbcMapUtil.getString(p, "TEXT_REMARK_ONE");
            history.applyUser = JdbcMapUtil.getString(p, "user_name");
            history.applyTime = JdbcMapUtil.getString(p, "CRT_DT");
            history.nodeName = JdbcMapUtil.getString(p, "nodeName");
            history.days = JdbcMapUtil.getString(p, "DURATION_ONE");
            history.astStatus = JdbcMapUtil.getString(p, "astName");
            return history;
        }).collect(Collectors.toList());
    }


    public static final String TASK_NOT_STARTED = "1634118574056542208";
    public static final String TASK_IN_PROCESSING = "1634118609016066048";
    public static final String TASK_COMPLETED = "1634118629769482240";
    public static final String TASK_NOT_INVOLVE = "1644140265205915648";
    public static final String TASK_OVERDUE = "1644140821106384896";


    public DataInfo convertData(Map<String, Object> dataMap) {
        DataInfo info = new DataInfo();
        info.id = JdbcMapUtil.getString(dataMap, "ID");
        info.name = JdbcMapUtil.getString(dataMap, "NAME");
        info.status = JdbcMapUtil.getString(dataMap, "pro_status");
        info.location = JdbcMapUtil.getString(dataMap, "location");
        info.type = JdbcMapUtil.getString(dataMap, "type");

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from week_task where status='ap' and PM_PRJ_ID=?", dataMap.get("ID"));
        info.total = list.size();
        info.notStart = (int) list.stream().filter(p -> TASK_NOT_STARTED.equals(JdbcMapUtil.getString(p, "WEEK_TASK_STATUS_ID"))).count();
        info.underway = (int) list.stream().filter(p -> TASK_IN_PROCESSING.equals(JdbcMapUtil.getString(p, "WEEK_TASK_STATUS_ID"))).count();
        info.finished = (int) list.stream().filter(p -> TASK_COMPLETED.equals(JdbcMapUtil.getString(p, "WEEK_TASK_STATUS_ID"))).count();
        info.notInvolve = (int) list.stream().filter(p -> TASK_NOT_INVOLVE.equals(JdbcMapUtil.getString(p, "WEEK_TASK_STATUS_ID"))).count();
        info.cqwwc = (int) list.stream().filter(p -> "1".equals(JdbcMapUtil.getString(p, "IZ_OVERDUE"))).count();
        info.cqwc = (int) list.stream().filter(p -> {
            if (COMPLETED.equals(JdbcMapUtil.getString(p, "WEEK_TASK_STATUS_ID"))) {
                if (JdbcMapUtil.getString(p, "PLAN_COMPL_DATE") != null && JdbcMapUtil.getString(p, "ACTUAL_COMPL_DATE") != null) {
                    Date plan = DateTimeUtil.stringToDate(JdbcMapUtil.getString(p, "PLAN_COMPL_DATE"));
                    Date actual = DateTimeUtil.stringToDate(JdbcMapUtil.getString(p, "ACTUAL_COMPL_DATE"));
                    if (actual.before(plan)) {
                        return true;
                    }
                }
            }
            return false;
        }).count();
        return info;
    }

    public WeekTaskInfo convertWeekTaskInfo(Map<String, Object> dataMap) {
        WeekTaskInfo info = new WeekTaskInfo();
        info.id = JdbcMapUtil.getString(dataMap, "id");
        info.title = JdbcMapUtil.getString(dataMap, "TITLE");
        info.user = JdbcMapUtil.getString(dataMap, "user");
        info.status = JdbcMapUtil.getString(dataMap, "status");
        info.izTurn = JdbcMapUtil.getString(dataMap, "iz_tran");
        info.transferTime = JdbcMapUtil.getString(dataMap, "TRANSFER_TIME");
        info.transferUser = JdbcMapUtil.getString(dataMap, "tran_user");
        info.reasonExplain = JdbcMapUtil.getString(dataMap, "REASON_EXPLAIN");
        info.projectId = JdbcMapUtil.getString(dataMap, "PM_PRJ_ID");
        info.projectName= JdbcMapUtil.getString(dataMap, "projectName");
        int count = 0;
        List<DelayApplyHistory> historyList = getDelayApplyList(info.id);
        if (!CollectionUtils.isEmpty(historyList)) {
            count = historyList.size();
        }
        info.count = count;
        return info;
    }


    public static class RequestParam {
        public String name;
        public String ownner;
        public String location;
        public String type;
        public Integer pageSize;
        public Integer pageIndex;
    }

    public static class DataInfo {
        public String id;
        public String name;
        public String status;
        public String location;
        public String type;
        public Integer total;
        public Integer notStart;
        public Integer underway;
        public Integer finished;
        public Integer notInvolve;
        public Integer cqwwc;
        public Integer cqwc;
    }

    public static class ResData {
        public Integer total;
        public List<DataInfo> dataInfoList;
        public List<WeekTaskInfo> weekTaskInfoList;
    }

    public static class WeekTaskInfo {
        public String id;
        public String title;//任务名称
        public String user;//责任岗位人员
        public String status;//当前状态
        public String izTurn;//是否转办
        public String transferTime;//转办时间
        public String transferUser;//转办人
        public String reasonExplain;//不涉及原因
        public Integer count;//延期申请说明
        public String projectId;
        public String projectName;
    }

    public static class TitleCount{
        //项目数
        public Integer prjCount;
        //任务数
        public Integer taskCount;
        //未开始数
        public Integer wksCount;
        //进行中数
        public Integer jxzCount;
        //已完成数
        public Integer ywcCount;
        //超期未完成数
        public Integer cqwwcCount;
        //超期已完成
        public Integer cqywcCount;
        //未涉及
        public Integer wsjCount;
    }
}
