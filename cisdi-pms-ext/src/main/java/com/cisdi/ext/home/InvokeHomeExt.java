package com.cisdi.ext.home;


import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvokeHomeExt {

    // 项目状态统计
    public void proStatusStatistics() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> projectPhaseList = myJdbcTemplate.queryForList("select count(p.id) num,IFNULL(v.name," +
                "'其他') projectPhase\n" +
                "from pm_prj p left join gr_set_value v on v.id = p.PROJECT_PHASE_ID left join gr_set s on s.CODE = " +
                "'project_phase'\n" +
                "where p.status = 'AP'\n" +
                "group by v.id");
        int total = projectPhaseList.stream().mapToInt(item -> Integer.parseInt(item.get("num").toString())).sum();
//        HashMap<String, Object> totals = new HashMap<>();
//        totals.put("项目总数", total);
//        projectPhaseList.add(totals);
        HashMap<String, Object> result = new HashMap<>();
        result.put("statistics", projectPhaseList);
        result.put("total",total);
        // 返回输出：
        // 转换为Map再设置到返回值；若直接将对象设置到返回值，调试时（通过MQ返回给平台）可能无法解析出相应的类：
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    /**
     * 项目阶段统计
     */
    public void proStageStatistics() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> projectPhaseList = myJdbcTemplate.queryForList("select count(p.id) num,IFNULL(v.name, \n" +
                "'其他') transitionPhase \n" +
                "from pm_prj p left join gr_set_value v on v.id = p.PROJECT_TYPE_ID left join gr_set s on s.CODE \n" +
                "= 'project_type' \n" +
                "where p.status = 'AP' \n" +
                "group by v.id order by v.SEQ_NO;");
        int total = projectPhaseList.stream().mapToInt(item -> Integer.parseInt(item.get("num").toString())).sum();
        HashMap<String, Object> totals = new HashMap<>();
//        totals.put("项目总数", total);
//        projectPhaseList.add(totals);
        HashMap<String, Object> result = new HashMap<>();
        result.put("statistics", projectPhaseList);
        result.put("total",total);

        // 返回输出：
        // 转换为Map再设置到返回值；若直接将对象设置到返回值，调试时（通过MQ返回给平台）可能无法解析出相应的类：
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    public static class ProjectStatus {
        /**
         * 项目个数
         */
        public Integer total;

        /**
         * 项目推进情况
         */
        public List<Map<String, Integer>> promotionStatus;

        /**
         * 项目推进情况
         */
        public List<Map<String, Integer>> stageStatus;


    }
}
