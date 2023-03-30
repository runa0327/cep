package com.cisdi.ext.InvestPlan;

import com.cisdi.ext.fund.FundReachApi;
import com.cisdi.ext.model.ResponseModel;
import com.cisdi.ext.util.BigDecimalUtil;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title InvestPlanExt
 * @package com.cisdi.ext.InvestPlan
 * @description 项目投资计划
 * @date 2022/10/26
 */
public class InvestPlanExt {


    /**
     * 投资计划列表查询
     */
    public void investPlanList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam param = JsonUtil.fromJson(json, RequestParam.class);
        String projectId = param.projectId;
        int pageSize = param.pageSize;
        int pageIndex = param.pageIndex;
        StringBuilder sb = new StringBuilder();
        sb.append("select ID,PM_PRJ_ID,YEAR,round(ifnull(AMT,0),2) as AMT from PM_INVESTMENT_YEAR_PLAN where PM_PRJ_ID = '").append(projectId).append("'");
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        String sql = sb.toString();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql);
        List<InvestYearPlan> planList = list.stream().map(this::convertData).collect(Collectors.toList());
        for (InvestYearPlan plan : planList) {
            String oldSql = "select PM_PRJ_ID,YEAR,round(ifnull(AMT,0),2) as AMT from PM_INVESTMENT_YEAR_PLAN where PM_PRJ_ID= " + plan.projectId + " and year < " + plan.year;
            List<Map<String, Object>> oldList = myJdbcTemplate.queryForList(oldSql);
            BigDecimal beforeYearTotal = BigDecimal.ZERO;
            if (!CollectionUtils.isEmpty(oldList)) {
                List<InvestYearPlan> oldPlan = oldList.stream().map(this::convertData).collect(Collectors.toList());
                beforeYearTotal = oldPlan.stream().map(p -> p.totalPlanOutputValue).reduce(BigDecimal.ZERO, BigDecimal::add);
            }
            plan.beforeYearTotal = beforeYearTotal;
            //本年完成产值--查询纳统数据
            String ntSql = "  select PM_PRJ_ID, `year`, `month`,round(ifnull((architectural_engineering_fee + installation_engineering_fee + equipment_purchase_fee + other_fee),0),2) as fee " +
                    "        from PM_STATISTICS_FEE " +
                    "        where PM_PRJ_ID = ? and year = ? ";
            List<Map<String, Object>> ntList = myJdbcTemplate.queryForList(ntSql, plan.projectId, plan.year);
            BigDecimal totalActualOutputValue = BigDecimal.ZERO;
            if (!CollectionUtils.isEmpty(ntList)) {
                totalActualOutputValue = ntList.stream().map(m -> new BigDecimal(JdbcMapUtil.getString(m, "fee"))).reduce(BigDecimal.ZERO, BigDecimal::add);
            }
            totalActualOutputValue = totalActualOutputValue.multiply(new BigDecimal(10000));
            plan.totalActualOutputValue = totalActualOutputValue;

            BigDecimal percentageComplete = BigDecimal.ZERO;
            if (plan.totalPlanOutputValue.compareTo(BigDecimal.ZERO) != 0) {
                percentageComplete = totalActualOutputValue.divide(plan.totalPlanOutputValue, RoundingMode.HALF_UP);
            }
            plan.percentageComplete = percentageComplete;
        }

        if (CollectionUtils.isEmpty(planList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
            OutSide model = new OutSide();
            model.total = totalList.size();
            model.planList = planList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(model), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 新增投资计划
     */
    public void investPlanModify() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        InvestYearPlanInput investYearPlanInput = JsonUtil.fromJson(json, InvestYearPlanInput.class);
        String id = investYearPlanInput.id;
        if (!Strings.isNotEmpty(id)) {
            id = Crud.from("PM_INVESTMENT_YEAR_PLAN").insertData();
        }
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //删除原有的月度投资计划
        myJdbcTemplate.update("delete from PM_INVESTMENT_MONTH_PLAN where PM_INVESTMENT_YEAR_PLAN_ID = ?", id);
        List<InvestMonthPlanInput> monthPlanInputs = investYearPlanInput.investMonthPlanList;

        BigDecimal totalAmt = monthPlanInputs.stream().map(m -> m.amt).reduce(BigDecimal.ZERO, BigDecimal::add);
        //新增现有的月度投资计划
        for (InvestMonthPlanInput monthPlanInput : monthPlanInputs) {
            String monthId = Crud.from("PM_INVESTMENT_MONTH_PLAN").insertData();
            Crud.from("PM_INVESTMENT_MONTH_PLAN").where().eq("ID", monthId).update()
                    .set("PM_INVESTMENT_YEAR_PLAN_ID", id).set("MONTH", monthPlanInput.month).set("AMT", monthPlanInput.amt).exec();
        }
        //处理年度投资计划
        Crud.from("PM_INVESTMENT_YEAR_PLAN").where().eq("ID", id).update()
                .set("PM_PRJ_ID", investYearPlanInput.projectId).set("YEAR", investYearPlanInput.year).set("AMT", totalAmt).exec();

    }


    /**
     * 投资计划详情(修改用)
     */
    public void investPlanView() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam param = JsonUtil.fromJson(json, RequestParam.class);
        String id = param.id;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        try {
            Map<String, Object> stringObjectMap = myJdbcTemplate.queryForMap("select ID,PM_PRJ_ID,YEAR,round(ifnull(AMT,0),2) as AMT from PM_INVESTMENT_YEAR_PLAN where id=?", id);
            InvestYearPlanResponse planResponse = new InvestYearPlanResponse();
            planResponse.id = JdbcMapUtil.getString(stringObjectMap, "ID");
            planResponse.projectId = JdbcMapUtil.getString(stringObjectMap, "PM_PRJ_ID");
            planResponse.year = JdbcMapUtil.getString(stringObjectMap, "YEAR");
            planResponse.amt = new BigDecimal(JdbcMapUtil.getString(stringObjectMap, "AMT"));
            List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ID,MONTH,round(ifnull(AMT,0),2) as AMT from PM_INVESTMENT_MONTH_PLAN where PM_INVESTMENT_YEAR_PLAN_ID=?", id);
            planResponse.monthPlanResponseList = list.stream().map(p -> {
                InvestMonthPlanResponse monthPlanResponse = new InvestMonthPlanResponse();
                monthPlanResponse.id = JdbcMapUtil.getString(p, "ID");
                monthPlanResponse.month = JdbcMapUtil.getString(p, "MONTH");
                monthPlanResponse.amt = new BigDecimal(JdbcMapUtil.getString(p, "AMT"));
                return monthPlanResponse;
            }).collect(Collectors.toList());
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(planResponse), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        }

    }

    /**
     * 投资计划删除
     */
    public void investPlanDel() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam param = JsonUtil.fromJson(json, RequestParam.class);
        String id = param.id;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("delete from PM_INVESTMENT_MONTH_PLAN where PM_INVESTMENT_YEAR_PLAN_ID = ?", id);
        myJdbcTemplate.update("delete from PM_INVESTMENT_YEAR_PLAN where ID = ?", id);
    }


    /**
     * 详情接口
     */
    public void investPlanPreView() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam param = JsonUtil.fromJson(json, RequestParam.class);
        String id = param.id;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        try {
            Map<String, Object> stringObjectMap = myJdbcTemplate.queryForMap("select ID,PM_PRJ_ID,YEAR,round(ifnull(AMT,0),2) as AMT from PM_INVESTMENT_YEAR_PLAN where id=?", id);
            InvestYearPlan plan = this.convertData(stringObjectMap);
            String oldSql = "select PM_PRJ_ID,YEAR,round(ifnull(AMT,0),2) as AMT from PM_INVESTMENT_YEAR_PLAN where PM_PRJ_ID= " + plan.projectId + " and year < " + plan.year;
            List<Map<String, Object>> oldList = myJdbcTemplate.queryForList(oldSql);
            BigDecimal beforeYearTotal = BigDecimal.ZERO;
            if (!CollectionUtils.isEmpty(oldList)) {
                List<InvestYearPlan> oldPlan = oldList.stream().map(this::convertData).collect(Collectors.toList());
                beforeYearTotal = oldPlan.stream().map(p -> p.totalPlanOutputValue).reduce(BigDecimal.ZERO, BigDecimal::add);
            }
            plan.beforeYearTotal = beforeYearTotal;
            //本年完成产值--查询纳统数据
            String ntSql = "  select PM_PRJ_ID, `year`, `month`,round(ifnull((architectural_engineering_fee + installation_engineering_fee + equipment_purchase_fee + other_fee),0),2) as fee " +
                    "        from PM_STATISTICS_FEE " +
                    "        where PM_PRJ_ID = ? and year = ? ";
            List<Map<String, Object>> ntList = myJdbcTemplate.queryForList(ntSql, plan.projectId, plan.year);
            BigDecimal totalActualOutputValue = BigDecimal.ZERO;
            if (!CollectionUtils.isEmpty(ntList)) {
                totalActualOutputValue = ntList.stream().map(m -> new BigDecimal(JdbcMapUtil.getString(m, "fee"))).reduce(BigDecimal.ZERO, BigDecimal::add);
            }
            totalActualOutputValue = totalActualOutputValue.multiply(new BigDecimal(10000));
            plan.totalActualOutputValue = totalActualOutputValue;

            BigDecimal percentageComplete = BigDecimal.ZERO;
            if (plan.totalPlanOutputValue.compareTo(BigDecimal.ZERO) != 0) {
                percentageComplete = BigDecimalUtil.divide(totalActualOutputValue, 2, plan.totalPlanOutputValue);
            }
            plan.percentageComplete = percentageComplete;


            List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ID,MONTH,round(ifnull(AMT,0),2) as AMT from PM_INVESTMENT_MONTH_PLAN where PM_INVESTMENT_YEAR_PLAN_ID=?", id);
            List<InvestMonthPlan> monthPlanList = list.stream().map(p -> {
                InvestMonthPlan monthPlan = new InvestMonthPlan();
                monthPlan.id = JdbcMapUtil.getString(p, "ID");
                monthPlan.month = JdbcMapUtil.getString(p, "MONTH");
                monthPlan.amt = new BigDecimal(JdbcMapUtil.getString(p, "AMT"));

                String nSql = "  select PM_PRJ_ID, `year`, `month`,round(ifnull((architectural_engineering_fee + installation_engineering_fee + equipment_purchase_fee + other_fee),0),2) as fee " +
                        "        from PM_STATISTICS_FEE " +
                        "        where PM_PRJ_ID = ? and year = ? and month = ? ";
                List<Map<String, Object>> nList = myJdbcTemplate.queryForList(nSql, plan.projectId, plan.year, monthPlan.month);
                BigDecimal sj = BigDecimal.ZERO;
                if (!CollectionUtils.isEmpty(nList)) {
                    sj = new BigDecimal(String.valueOf(nList.get(0).get("fee")));
                }
                monthPlan.sjAmt = sj;
                BigDecimal wcR = BigDecimal.ZERO;
                if (monthPlan.amt.compareTo(BigDecimal.ZERO) != 0) {
                    wcR = sj.divide(monthPlan.amt, RoundingMode.HALF_UP);
                }
                monthPlan.wcRate = wcR;
                return monthPlan;
            }).collect(Collectors.toList());

            plan.monthPlanList = monthPlanList;

            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(plan), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        }
    }


    private InvestYearPlan convertData(Map<String, Object> data) {
        InvestYearPlan plan = new InvestYearPlan();
        plan.id = JdbcMapUtil.getString(data, "ID");
        plan.projectId = JdbcMapUtil.getString(data, "PM_PRJ_ID");
        plan.year = JdbcMapUtil.getString(data, "YEAR");
        plan.totalPlanOutputValue = new BigDecimal(JdbcMapUtil.getString(data, "AMT"));
        return plan;
    }

    public static class InvestYearPlanInput {
        public String id;
        public String projectId;
        public String year;
        public BigDecimal amt;
        public List<InvestMonthPlanInput> investMonthPlanList;

    }

    public static class InvestMonthPlanInput {
        public String id;
        public String month;
        public BigDecimal amt;
    }

    public static class InvestYearPlan {
        public String id;
        public String projectId;
        public String year;
        /**
         * 之前年度合计
         */
        public BigDecimal beforeYearTotal;
        /**
         * 本年度计划
         */
        public BigDecimal totalPlanOutputValue;

        /**
         * 实际产值总值
         */
        public BigDecimal totalActualOutputValue;

        /**
         * 完成率
         */
        public BigDecimal percentageComplete;

        public List<InvestMonthPlan> monthPlanList;
    }

    public static class InvestMonthPlan {
        public String id;
        public String month;
        //计划产值
        public BigDecimal amt;
        //实际产值
        public BigDecimal sjAmt;
        //完成率
        public BigDecimal wcRate;
    }

    private static class RequestParam {
        public String projectId;
        public Integer pageSize;
        public Integer pageIndex;
        public String id;
    }

    public static class InvestYearPlanResponse {
        public String id;
        public String projectId;
        public String year;
        public BigDecimal amt;
        public List<InvestMonthPlanResponse> monthPlanResponseList;
    }

    public static class InvestMonthPlanResponse {
        public String id;
        public String month;
        public BigDecimal amt;
    }

    public static class OutSide {
        public Integer total;
        public List<InvestYearPlan> planList;
    }
}
