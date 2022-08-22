package com.cisdi.ext.procedure;

import com.cisdi.ext.model.BasePageEntity;
import com.cisdi.ext.util.JsonUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

@Slf4j
public class ProcedureStatistics {

    public void getProcedureStatisticsList() {
        //获取参数
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        String inputJson = JsonUtil.toJson(inputMap);
        Input input = JsonUtil.fromJson(inputJson, Input.class);
        String projectName = input.getProjectName();
        Integer pageIndex = input.getPageIndex();
        Integer pageSize = input.getPageSize();

        //获取数据
        //表头
        List<Map<String, Object>> headList = getFixedNodeName();
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();

        StringBuffer baseSql = new StringBuffer("select pj.id projectId,pj.NAME projectName,");
        for (Map<String, Object> head : headList) {
            String nodeNameId = head.get("nodeNameId").toString();
            String nodeName = head.get("nodeName").toString();
            baseSql.append("MAX(IF(pn.SCHEDULE_NAME = '").append(nodeNameId)
                    .append("',IF(ISNULL(pn.ACTUAL_COMPL_DATE),concat( '计划完成时间：', date_format(pn.PLAN_COMPL_DATE," +
                            "'%Y-%m-%d')),concat('完成时间：',pn.ACTUAL_COMPL_DATE)),'')) '")
                    .append(nodeName).append("',");
        }
        //删除最后一个逗号
        baseSql.deleteCharAt(baseSql.length() - 1);
        baseSql.append(" from pm_pro_plan pp \n" +
                "left join pm_prj pj on pj.id = pp.PM_PRJ_ID \n" +
                "left join pm_pro_plan_node pn on pn.PM_PRO_PLAN_ID = pp.id \n" +
                "where pn.LEVEL = 3 and pj.id is not null ");
        if (!Strings.isNullOrEmpty(projectName)) {
            baseSql.append("and pj.NAME like '%" + projectName + "%' ");
        }
        baseSql.append("group by pj.id ");

        //分页
        Integer start = pageSize * (pageIndex - 1);
        baseSql.append("limit " + start + "," + pageSize);

        log.info(baseSql.toString());
        //查询
        List<Map<String, Object>> contentList = jdbcTemplate.queryForList(baseSql.toString());

        //响应组装
        HashMap<String, Object> row0Name = new HashMap<>();
        row0Name.put("name", "项目/任务节点");
        headList.add(0, row0Name);

        HashMap<String, Object> result = new HashMap<>();
        result.put("contentList", contentList);
        result.put("total", contentList.size());
        result.put("headList", headList);

        //响应
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);

    }

    public List<Map<String, Object>> getFixedNodeName() {
        //查询表头并排序
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        //所有表头
        List<Map<String, Object>> headers = jdbcTemplate.queryForList("select distinct pn.NAME nodeName, pn" +
                ".SCHEDULE_NAME nodeNameId from " +
                "pm_pro_plan_node pn where pn.SHOW_IN_EARLY_PROC = 1 and pn.SCHEDULE_NAME is not null  and pn.level =" +
                " '3'");
        //所有可显示在前期手续的节点
//        List<Map<String, Object>> allVisualNode = jdbcTemplate.queryForList("select distinct PM_PRO_PLAN_NODE_PID,
//        ID,NAME," +
//                "SCHEDULE_NAME,LEVEL,SEQ_NO from pm_pro_plan_node where SHOW_IN_EARLY_PROC = 1 and SCHEDULE_NAME is
//                " +
//                "not null");
//        allVisualNode.stream().sorted(Comparator.comparing(item -> item.get("LEVEL").toString()))
        return headers;
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
}
