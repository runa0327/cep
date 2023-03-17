package com.cisdi.ext.weektask;

import com.cisdi.ext.fund.FundReachApi;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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
        userId = "0099799190825078670";
        StringBuilder sb = new StringBuilder();
        sb.append("select wt.*,gsv.`NAME` as task_status from week_task wt left join gr_set_value gsv on wt.WEEK_TASK_STATUS_ID = gsv.id  where AD_USER_ID = '").append(userId).append("' order by PUBLISH_START desc");

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
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select wt.*,gsv.`NAME` as task_status from week_task wt left join gr_set_value gsv on wt.WEEK_TASK_STATUS_ID = gsv.id  where wt.id=?", id);
        if (!CollectionUtils.isEmpty(list)) {
            List<WeekTask> weekTaskList = list.stream().map(p -> {
                WeekTask weekTask = new WeekTask();
                weekTask.id = JdbcMapUtil.getString(p, "ID");
                weekTask.userId = JdbcMapUtil.getString(p, "AD_USER_ID");
                weekTask.title = JdbcMapUtil.getString(p, "TITLE");
                weekTask.content = JdbcMapUtil.getString(p, "CONTENT");
                weekTask.publishStart = StringUtil.withOutT(JdbcMapUtil.getString(p, "PUBLISH_START"));
                weekTask.taskStatus = JdbcMapUtil.getString(p, "task_status");
                return weekTask;
            }).collect(Collectors.toList());
            WeekTask weekTask = weekTaskList.get(0);
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(weekTask), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }
    }


    public static class WeekTask {
        public String id;
        public String userId;
        public String title;
        public String content;
        public String publishStart;
        public String taskStatus;
    }

    public static class OutSide {
        public List<WeekTask> weekTaskList;
        public Integer total;
    }
}
