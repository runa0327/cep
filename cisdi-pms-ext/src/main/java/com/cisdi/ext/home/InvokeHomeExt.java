package com.cisdi.ext.home;


import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;

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
                " and PROJECT_PHASE_ID <> '0099902212142009989' " +
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
                " and PROJECT_PHASE_ID <> '0099902212142009989' " +
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


    /**
     * app项目投资统计
     */
    public void appInvestSatistics() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = JdbcMapUtil.getString(map, "projectId");
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pie.id,round(ifnull(pie.PRJ_TOTAL_INVEST,0)/10000,2) as amt ,gsv.code from pm_invest_est pie  " +
                "left join  gr_set_value gsv on gsv.id = pie.INVEST_EST_TYPE_ID " +
                "where PM_PRJ_ID=? and PRJ_TOTAL_INVEST<>0 order by gsv.`CODE` desc limit 0,1  ", projectId);
        List<DataInfo> dataInfoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> mapDate = list.get(0);
            List<Map<String, Object>> dtlList = myJdbcTemplate.queryForList("select * from pm_invest_est_dtl where PM_INVEST_EST_ID=?", mapDate.get("id"));

            DataInfo info = new DataInfo();
            info.name = "建安费";
            Optional<Map<String, Object>> optional = dtlList.stream().filter(p -> "0099799190825099548".equals(JdbcMapUtil.getString(p, "PM_EXP_TYPE_ID"))).findAny();
            optional.ifPresent(stringObjectMap -> info.value = JdbcMapUtil.getString(stringObjectMap, "AMT"));
            dataInfoList.add(info);

            DataInfo info1 = new DataInfo();
            info1.name = "工程其他费";
            Optional<Map<String, Object>> optional1 = dtlList.stream().filter(p -> "0099799190825099550".equals(JdbcMapUtil.getString(p, "PM_EXP_TYPE_ID"))).findAny();
            optional1.ifPresent(stringObjectMap -> info1.value = JdbcMapUtil.getString(stringObjectMap, "AMT"));
            dataInfoList.add(info1);

            DataInfo info2 = new DataInfo();
            info2.name = "预备费";
            Optional<Map<String, Object>> optional2 = dtlList.stream().filter(p -> "0099799190825099552".equals(JdbcMapUtil.getString(p, "PM_EXP_TYPE_ID"))).findAny();
            optional2.ifPresent(stringObjectMap -> info2.value = JdbcMapUtil.getString(stringObjectMap, "AMT"));
            dataInfoList.add(info2);

            DataInfo info3 = new DataInfo();
            info3.name = "总投资";
            Optional<Map<String, Object>> optional3 = dtlList.stream().filter(p -> "0099799190825099546".equals(JdbcMapUtil.getString(p, "PM_EXP_TYPE_ID"))).findAny();
            optional3.ifPresent(stringObjectMap -> info3.value = JdbcMapUtil.getString(stringObjectMap, "AMT"));
            dataInfoList.add(info3);

        }
        if (CollectionUtils.isEmpty(dataInfoList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.dataInfoList = dataInfoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    public static class DataInfo {
        public String name;
        public String value;
    }

    public static class OutSide {
        public List<DataInfo> dataInfoList;
    }
}
