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
        String userId = ExtJarHelper.loginInfo.get().userId;
        List<Map<String, Object>> projectPhaseList = myJdbcTemplate.queryForList("select count(p.id) num,IFNULL(v.name," +
                "'其他') projectPhase\n" +
                "from pm_prj p left join gr_set_value v on v.id = p.PROJECT_PHASE_ID left join gr_set s on s.CODE = " +
                "'project_phase'\n" +
                " where p.status = 'AP' and p.PROJECT_SOURCE_TYPE_ID='0099952822476441374' \n" +
//                " and IF(? in (select ad_user_id from ad_role_user where ad_role_id = '0099250247095870406') ,1=1," +
//                " p.id in (select DISTINCT pm_prj_id from pm_dept WHERE STATUS = 'ap' and FIND_IN_SET(?, USER_IDS ))) \n" +
//                "group by v.id",userId,userId);
                "group by v.id");
        int total = projectPhaseList.stream().mapToInt(item -> Integer.parseInt(item.get("num").toString())).sum();
//        HashMap<String, Object> totals = new HashMap<>();
//        totals.put("项目总数", total);
//        projectPhaseList.add(totals);
        HashMap<String, Object> result = new HashMap<>();
        result.put("statistics", projectPhaseList);
        result.put("total", total);
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
        String userId = ExtJarHelper.loginInfo.get().userId;
        List<Map<String, Object>> projectPhaseList = myJdbcTemplate.queryForList("select sum(a.num) as num ,a.transitionPhase as transitionPhase from \n" +
                "( \n" +
                " select count(p.id) num,IFNULL(v.name, '其他') transitionPhase \n" +
                " from pm_prj p \n" +
                " left join gr_set_value v on v.id = p.PROJECT_TYPE_ID left join gr_set s on s.CODE = 'project_type' \n" +
                " where p.status = 'AP' and p.PROJECT_SOURCE_TYPE_ID='0099952822476441374' " +
//                " and IF(? in (select ad_user_id from ad_role_user where ad_role_id = '0099250247095870406') ,1=1," +
//                " p.id in (select DISTINCT pm_prj_id from pm_dept WHERE STATUS = 'ap' and FIND_IN_SET(?, USER_IDS ))) \n" +
                " group by v.id order by v.SEQ_NO \n" +
//                ") a group by a.transitionPhase", userId,userId);
                ") a group by a.transitionPhase");
        int total = projectPhaseList.stream().mapToInt(item -> Integer.parseInt(item.get("num").toString())).sum();
        HashMap<String, Object> totals = new HashMap<>();
//        totals.put("项目总数", total);
//        projectPhaseList.add(totals);
        HashMap<String, Object> result = new HashMap<>();
        result.put("statistics", projectPhaseList);
        result.put("total", total);

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
