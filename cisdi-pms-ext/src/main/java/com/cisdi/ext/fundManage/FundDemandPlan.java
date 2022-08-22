package com.cisdi.ext.fundManage;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.model.BasePageEntity;
import com.cisdi.ext.util.JsonUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class FundDemandPlan {

    //资金需求计划列表
    public void getFundDemandPlan() {
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        String inputJson = JsonUtil.toJson(inputMap);
        Input input = JsonUtil.fromJson(inputJson, Input.class);

        StringBuffer baseSql = new StringBuffer();
        baseSql.append("select r.id, r.NAME planName, p.NAME prjName,d.NAME deptName,r.CRT_DT createTime,r" +
                ".REMARK remark from pm_fund_req_plan r left join pm_prj p on r.PM_PRJ_ID = p.ID left join hr_dept d " +
                "on r.HR_DEPT_ID = d.ID where 1=1 ");
        if (!Strings.isNullOrEmpty(input.planName)) {
            baseSql.append("and r.NAME like '%" + input.planName + "%' ");
        }
        if (!Strings.isNullOrEmpty(input.prjId)) {
            baseSql.append("and r.PM_PRJ_ID = '" + input.prjId + "' ");
        }
        if (!Strings.isNullOrEmpty(input.startDate) && !Strings.isNullOrEmpty(input.endDate)) {
            baseSql.append("and r.CRT_DT BETWEEN '" + input.startDate + "' and '" + input.endDate + "' ");
        }
        baseSql.append("order by r.CRT_DT desc ");

        Integer start = input.pageSize * (input.pageIndex - 1);
        baseSql.append("limit " + start + "," + input.pageSize);


        log.info(baseSql.toString());
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(baseSql.toString());

        HashMap<String, Object> total = new HashMap<>();
        total.put("total", resultList.size());
        resultList.add(total);
        HashMap<String, Object> result = new HashMap<>();
        result.put("resultList", resultList);

        Map outputMap = JsonUtil.fromJson(JSONObject.toJSONString(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }


    //获取项目名称下拉框
    public void getPrjDrop(){
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        String prjName = inputMap.get("prjName").toString();

        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        StringBuffer baseSql = new StringBuffer();
        baseSql.append("select name,id from pm_prj where STATUS = 'AP' ");
        if (!Strings.isNullOrEmpty(prjName)){
            baseSql.append("and name like '%"+prjName+"%' ");
        }
        baseSql.append("order by CRT_DT desc ");

        List<Map<String, Object>> projects = jdbcTemplate.queryForList(baseSql.toString());

        HashMap<String, Object> result = new HashMap<>();
        result.put("projects",projects);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);

    }

    public static class Input extends BasePageEntity {
        /**
         * 计划名称
         */
        public String planName;

        /**
         * 项目id
         */
        public String prjId;

        /**
         * 开始日期
         */
        public String startDate;

        /**
         * 结束日期
         */
        public String endDate;


    }
}
