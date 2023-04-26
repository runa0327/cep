package com.cisdi.ext.weektask;

import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.cisdi.ext.util.WeeklyUtils;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
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
        sb.append("select wt.*,gsv.`NAME` as task_status,au.name as transferUser,ifnull(CAN_DISPATCH,0) as isTransfer,TRANSFER_USER as transferUserId,TRANSFER_TIME from week_task wt " +
                "left join gr_set_value gsv on wt.WEEK_TASK_STATUS_ID = gsv.id  " +
                "left join ad_user au on au.id = wt.TRANSFER_USER " +
                "where AD_USER_ID = '").append(userId).append("' and PUBLISH_START between '")
                .append(weekDay.get("begin")).append("' and '").append(weekDay.get("end"))
                .append("' order by PUBLISH_START desc");

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
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select wt.*,gsv.`NAME` as task_status,CAN_DISPATCH,TRANSFER_USER as transferUserId,au.name as transferUser,TRANSFER_TIME,pm.name as projectName,PLAN_COMPL_DATE " +
                " from week_task wt " +
                "left join gr_set_value gsv on wt.WEEK_TASK_STATUS_ID = gsv.id  " +
                "left join ad_user au on au.id = wt.TRANSFER_USER " +
                "left join pm_prj pm on pm.id = wt.pm_prj_id " +
                "left join pm_pro_plan_node pn on pn.id = wt.RELATION_DATA_ID "+
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
        sb.append("select wt.*,gsv.`NAME` as task_status,TRANSFER_USER as transferUserId,au.name as transferUser,CAN_DISPATCH,TRANSFER_TIME from week_task wt " +
                "left join gr_set_value gsv on wt.WEEK_TASK_STATUS_ID = gsv.id  " +
                "left join ad_user au on au.id = wt.AD_USER_ID " + "where wt.id  in (select id from week_task where TRANSFER_USER='").append(userId).append("')")
                .append(" and PUBLISH_START between '")
                .append(weekDay.get("begin")).append("' and '").append(weekDay.get("end"))
                .append("' order by PUBLISH_START desc");

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
                //查询标准节点附加信息
                String baseNodeId = JdbcMapUtil.getString(node, "SCHEDULE_NAME");
                List<AttData> attDataList = new ArrayList<>();
                if (!Strings.isNullOrEmpty(baseNodeId)) {
                    List<Map<String, Object>> list1 = myJdbcTemplate.queryForList("select AD_ATT_ID,ATT_VALUE,ifnull(FOR_NODE,0) as FOR_NODE,ifnull(FOR_PROC,0) as FOR_PROC,aa.code as ad_att_code,aa.`NAME` as ad_att_text from STANDARD_NODE_NAME_DEFAULT_ATT t " +
                            " left join AD_ATT aa on t.AD_ATT_ID = aa.id  where STANDARD_NODE_NAME_ID=?", baseNodeId);
                    attDataList = list1.stream().map(m -> {
                        AttData attData = new AttData();
                        attData.AD_ATT_ID = JdbcMapUtil.getString(m, "AD_ATT_ID");
                        attData.AD_ATT_CODE = JdbcMapUtil.getString(m, "ad_att_code");
                        attData.ATT_VALUE = JdbcMapUtil.getString(m, "ATT_VALUE");
                        attData.ATT_TXT = JdbcMapUtil.getString(m, "ad_att_text");
                        attData.FOR_NODE = JdbcMapUtil.getString(m, "FOR_NODE");
                        attData.FOR_PROC = JdbcMapUtil.getString(m, "FOR_PROC");
                        return attData;
                    }).collect(Collectors.toList());
                    resData.attDataList = attDataList;
                }
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
            List<Map<String, Object>> dataList = myJdbcTemplate.queryForList("select wn.ad_view_id as AD_VIEW_ID,wp.EXTRA_INFO  as EXTRA_INFO from wf_node wn left join WF_PROCESS wp on wp.id = wn.WF_PROCESS_ID where wn.NODE_TYPE = 'START_EVENT' AND wn.`STATUS` = 'AP' and wn.WF_PROCESS_ID= ? ", processId);
            if (!CollectionUtils.isEmpty(dataList)) {
                Map<String, Object> dataMap = dataList.get(0);
                String viewId = JdbcMapUtil.getString(dataMap, "AD_VIEW_ID");
                Map<String, String> res = new HashMap<>();
                res.put("processId", processId);
                res.put("WF_NODE_VIEW_ID", viewId);
                res.put("icon", JdbcMapUtil.getString(dataMap, "EXTRA_INFO"));
                res.put("title", JdbcMapUtil.getString(node, "NAME"));
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
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select wt.*,pm.`NAME` as projectName from WEEK_TASK wt \n" +
                " left join pm_prj pm on wt.PM_PRJ_ID = pm.id \n" +
                " where wt.id=? ", map.get("id"));
        ProcessData processData = new ProcessData();
        processData.processId = "1649227469557063680";
        processData.viewId = "1649226141279707136";

        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> pmData = list.get(0);
            Project project = new Project();
            project.id = JdbcMapUtil.getString(pmData, "PM_PRJ_ID");
            project.name = JdbcMapUtil.getString(pmData, "projectName");
            processData.project = project;
            processData.nodeId = JdbcMapUtil.getString(pmData, "RELATION_DATA_ID");
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

        public String nodeId;

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

    public static class DelayApplyHistory {
        //序号
        public String serNo;
        //延期天数
        public String delayNum;
        //延期说明
        public String description;
        //申请人
        public String applyUser;
        //申请时间
        public String applyTime;
    }

    public static final String NOT_STARTED = "0099799190825106800";
    public static final String IN_PROCESSING = "0099799190825106801";
    public static final String COMPLETED = "0099799190825106802";

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
                myJdbcTemplate.update("update WEEK_TASK set AD_USER_ID=?,TITLE=?,CONTENT=?,PUBLISH_START=?,WEEK_TASK_STATUS_ID=?,WEEK_TASK_TYPE_ID=?,RELATION_DATA_ID=?,CAN_DISPATCH='0',PM_PRJ_ID=? where id=?",
                        userId, title, content, new Date(), "1634118574056542208", "1635080848313290752", objectMap.get("ID"), objectMap.get("projectId"), id);
            }
        }
    }
}
