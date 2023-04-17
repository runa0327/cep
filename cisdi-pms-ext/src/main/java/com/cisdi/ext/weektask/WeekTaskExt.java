package com.cisdi.ext.weektask;

import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.cisdi.ext.util.WeeklyUtils;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

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
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select wt.*,gsv.`NAME` as task_status,CAN_DISPATCH,TRANSFER_USER as transferUserId,au.name as transferUser,TRANSFER_TIME,pm.name as projectName from week_task wt " +
                "left join gr_set_value gsv on wt.WEEK_TASK_STATUS_ID = gsv.id  " +
                "left join ad_user au on au.id = wt.TRANSFER_USER " +
                "left join pm_prj pm on pm.id = wt.pm_prj_id " +
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
                "left join ad_user au on au.id = wt.AD_USER_ID " + "where wt.id  in (select id from week_task where TRANSFER_USER='").append(userId)
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
                res.put("viewId", viewId);
                res.put("icon", JdbcMapUtil.getString(dataMap, "EXTRA_INFO"));
                res.put("title", JdbcMapUtil.getString(node, "NAME"));
                res.put("projectId", JdbcMapUtil.getString(node, "pm_prj_id"));
                ExtJarHelper.returnValue.set(res);
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
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String opr = JdbcMapUtil.getString(map, "opr");
        String status = null;
        if ("去处理".equals(opr)) {
            status = "1634118609016066048";//进行中
            String instanceId = JdbcMapUtil.getString(map, "instanceId");
            if (Strings.isNotEmpty(instanceId)) {
                myJdbcTemplate.update("update pm_pro_plan_node set LINKED_WF_PROCESS_INSTANCE_ID=? where id =(select RELATION_DATA_ID from week_task where id=? )", instanceId, map.get("id"));
            }
        }
        myJdbcTemplate.update("update WEEK_TASK set WEEK_TASK_STATUS_ID=? where id=?", status, map.get("id"));
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
    }

    public static class OutSide {
        public List<WeekTask> weekTaskList;
        public Integer total;
    }
}
