package com.cisdi.ext.fundManage;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.model.BasePageEntity;
import com.cisdi.ext.model.view.CommonDrop;
import com.cisdi.ext.model.view.File;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class FundDemandPlan {

    // 资金需求计划列表
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
        // 总条数sql
        String totalSql = baseSql.toString();
        baseSql.append("order by r.CRT_DT desc ");

        Integer start = input.pageSize * (input.pageIndex - 1);
        baseSql.append("limit " + start + "," + input.pageSize);


        log.info(baseSql.toString());
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> resultList = myJdbcTemplate.queryForList(baseSql.toString());
        ArrayList<Demand> demandList = new ArrayList<>();
        resultList.forEach(result -> {
            Demand demand = new Demand();
            demand.id = JdbcMapUtil.getString(result, "id");
            demand.planName = JdbcMapUtil.getString(result, "planName");
            demand.prjName = JdbcMapUtil.getString(result, "prjName");
            demand.deptName = JdbcMapUtil.getString(result, "deptName");
            demand.createTime = StringUtil.withOutT(JdbcMapUtil.getString(result, "createTime"));
            demand.remark = JdbcMapUtil.getString(result, "remark");
            demandList.add(demand);
        });

        List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
        HashMap<String, Object> result = new HashMap<>();
        result.put("resultList", demandList);
        result.put("total", totalList.size());

        Map outputMap = JsonUtil.fromJson(JSONObject.toJSONString(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }


    // 获取项目名称下拉框
    public void getPrjDrop() {
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        String prjName = Objects.isNull(inputMap.get("prjName")) ? null : inputMap.get("prjName").toString();

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        StringBuffer baseSql = new StringBuffer();
        baseSql.append("select name,id from pm_prj where STATUS = 'AP' ");
        if (!Strings.isNullOrEmpty(prjName)) {
            baseSql.append("and name like '%" + prjName + "%' ");
        }
        baseSql.append("order by CRT_DT desc ");

        List<Map<String, Object>> projects = myJdbcTemplate.queryForList(baseSql.toString());
        List<CommonDrop> commonDrops = new ArrayList<>();
        for (Map<String, Object> project : projects) {
            CommonDrop projectDrop = new CommonDrop();
            projectDrop.id = JdbcMapUtil.getString(project, "id");
            projectDrop.name = JdbcMapUtil.getString(project, "name");
            commonDrops.add(projectDrop);
        }
        HashMap<String, Object> result = new HashMap<>();
        result.put("projects", commonDrops);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);

    }

    // 详情
    public void getFundDemandDetail() {
        Map<String, Object> idMap = ExtJarHelper.extApiParamMap.get();
        String id = idMap.get("id").toString();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        Map<String, Object> listInfo = myJdbcTemplate.queryForMap("select r.id, r.NAME planName, p.NAME prjName, TOTAL_AMT totalAmt," +
                "d.NAME deptName,r.CRT_DT createTime,r" +
                ".REMARK remark,r.ATT_FILE_GROUP_ID fileIds from pm_fund_req_plan r left join pm_prj p on r.PM_PRJ_ID = p.ID " +
                "left join hr_dept d on r.HR_DEPT_ID = d.ID where r.id=? ", id);
        List<Map<String, Object>> detailList = myJdbcTemplate.queryForList("select d.REQ_TIME reqTime,d.REQ_AMT reqAmt,v.name" +
                " from pm_fund_req_plan_detail d " +
                "left join gr_set_value v on v.id = d.FUND_REQ_TYPE_ID left join gr_set s on s.id = v.GR_SET_ID and s" +
                ".CODE = 'psm_money_type' where d.PM_FUND_REQ_PLAN_ID = ?", id);
        List<File> fileList = FileCommon.getFileResp(JdbcMapUtil.getString(listInfo, "fileIds"), myJdbcTemplate);
        listInfo.put("fileList", fileList);
        HashMap<Object, Object> result = new HashMap<>();
        // 处理时间
        String createTime = JdbcMapUtil.getString(listInfo, "createTime");
        listInfo.put("createTime", StringUtil.withOutT(createTime));

        result.put("listInfo", listInfo);
        result.put("detailList", detailList);

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

    public static class Demand {
        public String id;
        public String planName;
        public String prjName;
        public String deptName;
        public String createTime;
        public String remark;
    }
}
