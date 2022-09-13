package com.cisdi.ext.procedure;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.model.BasePageEntity;
import com.cisdi.ext.util.JsonUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
/**
 * 前期手续
 */
public class ProcedureStatistics {

    public void getProcedureStatisticsList() {
        // 获取参数
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        String inputJson = JsonUtil.toJson(inputMap);
        Input input = JsonUtil.fromJson(inputJson, Input.class);
        String projectName = input.getProjectName();
        Integer pageIndex = input.getPageIndex();
        Integer pageSize = input.getPageSize();

        // 获取数据
        // 表头
        List<Map<String, Object>> headList = getFixedNodeName();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        StringBuffer baseSql = new StringBuffer("select pj.id projectId,pj.NAME projectName,");
        for (Map<String, Object> head : headList) {
            String nodeNameId = head.get("nodeNameId").toString();
            String nodeName = head.get("nodeName").toString();
            baseSql.append("MAX(IF(pn.SCHEDULE_NAME = '").append(nodeNameId)
                    .append("',IF(ISNULL(pn.ACTUAL_COMPL_DATE),concat( '计划完成时间：', date_format(pn.PLAN_COMPL_DATE," +
                            "'%Y-%m-%d')),concat('完成时间：',pn.ACTUAL_COMPL_DATE)),'')) '")
                    .append(nodeName).append("',");
        }
        // 删除最后一个逗号
        baseSql.deleteCharAt(baseSql.length() - 1);
        baseSql.append(" from pm_pro_plan pp \n" +
                "left join pm_prj pj on pj.id = pp.PM_PRJ_ID \n" +
                "left join pm_pro_plan_node pn on pn.PM_PRO_PLAN_ID = pp.id \n" +
                "where pn.LEVEL = 3 and pj.id is not null ");
        if (!Strings.isNullOrEmpty(projectName)) {
            baseSql.append("and pj.NAME like '%" + projectName + "%' ");
        }
        baseSql.append("group by pj.id ");

        // 分页
        Integer start = pageSize * (pageIndex - 1);
        baseSql.append("limit " + start + "," + pageSize);

        log.info(baseSql.toString());
        // 查询
        List<Map<String, Object>> contentList = myJdbcTemplate.queryForList(baseSql.toString());

        // 响应组装
        HashMap<String, Object> row0Name = new HashMap<>();
        row0Name.put("name", "项目/任务节点");
        headList.add(0, row0Name);

        HashMap<String, Object> result = new HashMap<>();
        result.put("contentList", contentList);
        result.put("total", contentList.size());
        result.put("headList", headList);

        // 响应
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);

    }

    public List<Map<String, Object>> getFixedNodeName() {
        // 查询表头并排序
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 所有表头
        List<Map<String, Object>> headers = myJdbcTemplate.queryForList("select distinct pn.NAME nodeName, pn" +
                ".SCHEDULE_NAME nodeNameId from " +
                "pm_pro_plan_node pn where pn.SHOW_IN_EARLY_PROC = 1 and pn.SCHEDULE_NAME is not null  and pn.level =" +
                " '3'");
        // 所有可显示在前期手续的节点
//        List<Map<String, Object>> allVisualNode = myJdbcTemplate.queryForList("select distinct PM_PRO_PLAN_NODE_PID,
//        ID,NAME," +
//                "SCHEDULE_NAME,LEVEL,SEQ_NO from pm_pro_plan_node where SHOW_IN_EARLY_PROC = 1 and SCHEDULE_NAME is
//                " +
//                "not null");
//        allVisualNode.stream().sorted(Comparator.comparing(item -> item.get("LEVEL").toString()))
        return headers;
    }

    // app前期手续列表
    public void getProjectStatistic() {
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        String inputJson = JsonUtil.toJson(inputMap);
        Input input = JsonUtil.fromJson(inputJson, Input.class);
        String baseSql = "select pj.id projectId,pj.NAME projectName," +
                "COUNT(case v.name when '未启动' then 1 end) notStarted," +
                "COUNT(case v.name when '进行中' then 1 end) inProgress," +
                "COUNT(case v.name when '已完成' then 1 end) finished," +
                "COUNT(case v.name when '暂停中' then 1 end) onPause," +
                "COUNT(case v.name when '未涉及' then 1 end) notInvolved " +
                "from pm_pro_plan pp left join pm_prj pj on pj.id = pp.PM_PRJ_ID " +
                "left join pm_pro_plan_node pn on pn.PM_PRO_PLAN_ID = pp.ID " +
                "left join gr_set_value v on v.id = pn.PROGRESS_STATUS_ID " +
                "left join gr_set s on s.id = v.GR_SET_ID and s.code = 'PROGRESS_STATUS' " +
                "where pn.LEVEL = 3 and pj.id is not null " +
                "group by pj.id " +
                "order by pj.CRT_DT desc ";
        // 查询总数sql
        String totalSql = baseSql;
        // 分页
        Integer start = input.pageSize * (input.pageIndex - 1);
        baseSql += ("limit " + start + "," + input.pageSize);

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> statisticList = myJdbcTemplate.queryForList(baseSql);
        List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);

        // 封装实体
        ArrayList<ProjectStatistic> projectStatistics = new ArrayList<>();
        for (Map<String, Object> statisticMap : statisticList) {
            ProjectStatistic projectStatistic = new ProjectStatistic();
            projectStatistic.projectId = JdbcMapUtil.getString(statisticMap, "projectId");
            projectStatistic.projectName = JdbcMapUtil.getString(statisticMap, "projectName");
            projectStatistic.notStarted = JdbcMapUtil.getInt(statisticMap, "notStarted");
            projectStatistic.inProgress = JdbcMapUtil.getInt(statisticMap, "inProgress");
            projectStatistic.finished = JdbcMapUtil.getInt(statisticMap, "finished");
            projectStatistic.onPause = JdbcMapUtil.getInt(statisticMap, "onPause");
            projectStatistic.notInvolved = JdbcMapUtil.getInt(statisticMap, "notInvolved");
            projectStatistics.add(projectStatistic);
        }
        StatisticList statistics = new StatisticList();
        statistics.projectStatistics = projectStatistics;
        statistics.total = totalList.size();
        Map outputMap = JsonUtil.fromJson(JSONObject.toJSONString(statistics), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    // app前期手续详情
    public void getStatisticDetail() {
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        String projectId = JdbcMapUtil.getString(inputMap, "projectId");
        String baseSql = "select pj.id projectId,pj.NAME projectName,pn.name nodeName,v.name processStatus " +
                "from pm_pro_plan pp left join pm_prj pj on pj.id = pp.PM_PRJ_ID " +
                "left join pm_pro_plan_node pn on pn.PM_PRO_PLAN_ID = pp.ID " +
                "left join gr_set_value v on v.id = pn.PROGRESS_STATUS_ID " +
                "left join gr_set s on s.id = v.GR_SET_ID and s.code = 'PROGRESS_STATUS' " +
                "where pn.LEVEL = 3 and pj.id is not null and pj.id = ?";
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> nodeStatus = myJdbcTemplate.queryForList(baseSql, projectId);
        // 构造详情返回对象
        NodeStatusDetailResp detailResp = new NodeStatusDetailResp();
        // 项目id
        detailResp.projectId = projectId;
        // 项目名
        if (!CollectionUtils.isEmpty(nodeStatus)) {
            detailResp.projectName = JdbcMapUtil.getString(nodeStatus.get(0), "projectName");
        }
        // 节点任务状态列表
        ArrayList<PrjNodeStatus> prjNodeStatusList = new ArrayList<>();
        for (Map<String, Object> status : nodeStatus) {
            PrjNodeStatus prjNodeStatus = new PrjNodeStatus();
            prjNodeStatus.nodeName = JdbcMapUtil.getString(status, "nodeName");
            prjNodeStatus.processStatus = JdbcMapUtil.getString(status, "processStatus");
            prjNodeStatusList.add(prjNodeStatus);
        }
        detailResp.prjNodeStatuses = prjNodeStatusList;

        Map outPutMap = JsonUtil.fromJson(JSONObject.toJSONString(detailResp), Map.class);
        ExtJarHelper.returnValue.set(outPutMap);
    }

    public static class Input extends BasePageEntity {
        public String projectName;

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }
    }

    public static class ProjectStatistic {
        // 项目id
        public String projectId;
        // 项目名
        public String projectName;
        // 未开始
        public Integer notStarted;
        // 进行中
        public Integer inProgress;
        // 已完成
        public Integer finished;
        // 暂停中
        public Integer onPause;
        // 未涉及
        public Integer notInvolved;

    }

    public static class PrjNodeStatus {
        // 节点任务名称
        public String nodeName;
        // 完成状态
        public String processStatus;
    }

    public static class NodeStatusDetailResp {
        // 项目id
        public String projectId;
        // 项目名
        public String projectName;
        // 节点状态列表
        public List<PrjNodeStatus> prjNodeStatuses;
    }

    public static class StatisticList {
        public List<ProjectStatistic> projectStatistics;
        public Integer total;
    }
}
