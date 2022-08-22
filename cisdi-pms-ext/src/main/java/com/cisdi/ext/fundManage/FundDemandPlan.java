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
import java.util.Objects;

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

        HashMap<String, Object> result = new HashMap<>();
        result.put("resultList", resultList);
        result.put("total", resultList.size());

        Map outputMap = JsonUtil.fromJson(JSONObject.toJSONString(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }


    //获取项目名称下拉框
    public void getPrjDrop() {
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        String prjName = Objects.isNull(inputMap.get("prjName")) ? null : inputMap.get("prjName").toString();

        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        StringBuffer baseSql = new StringBuffer();
        baseSql.append("select name,id from pm_prj where STATUS = 'AP' ");
        if (!Strings.isNullOrEmpty(prjName)) {
            baseSql.append("and name like '%" + prjName + "%' ");
        }
        baseSql.append("order by CRT_DT desc ");

        List<Map<String, Object>> projects = jdbcTemplate.queryForList(baseSql.toString());

        HashMap<String, Object> result = new HashMap<>();
        result.put("projects", projects);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);

    }

    //详情
    public void getFundDemandDetail() {
        Map<String, Object> idMap = ExtJarHelper.extApiParamMap.get();
        String id = idMap.get("id").toString();
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();

        Map<String, Object> listInfo = jdbcTemplate.queryForMap("select r.id, r.NAME planName, p.NAME prjName," +
                "d.NAME deptName,r.CRT_DT createTime,r" +
                ".REMARK remark from pm_fund_req_plan r left join pm_prj p on r.PM_PRJ_ID = p.ID " +
                "left join hr_dept d on r.HR_DEPT_ID = d.ID where r.id=? ", id);
        List<Map<String, Object>> detailList = jdbcTemplate.queryForList("select d.REQ_TIME reqTime,d.REQ_AMT reqAmt,v.name" +
                " from pm_fund_req_plan_detail d " +
                "left join gr_set_value v on v.id = d.FUND_REQ_TYPE_ID left join gr_set s on s.id = v.GR_SET_ID and s" +
                ".CODE = 'psm_money_type' where d.PM_FUND_REQ_PLAN_ID = ?", id);
        HashMap<Object, Object> result = new HashMap<>();
        result.put("listInfo",listInfo);
        result.put("detailList",detailList);

        Map outputMap = JsonUtil.fromJson(JSONObject.toJSONString(result), Map.class);
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
