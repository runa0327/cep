package com.cisdi.ext.home;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title YearInvestProgressExt
 * @package com.cisdi.ext.invest
 * @description 年度投资进展
 * @date 2022/11/25
 */
public class YearInvestProgressExt {

    public void investProgress() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String year = String.valueOf(map.get("year"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        AnnualInvestmentProgress res = new AnnualInvestmentProgress();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select IFNULL(pimp.AMT,0)/10000 AMT, MONTH from pm_investment_month_plan pimp \n" +
                "left join PM_INVESTMENT_YEAR_PLAN  piyp on pimp.PM_INVESTMENT_YEAR_PLAN_ID = piyp.id  \n" +
                "where piyp.`YEAR`=?", year);

        List<Map<String, Object>> feeList = myJdbcTemplate.queryForList("select `MONTH`,ifnull(ARCHITECTURAL_ENGINEERING_FEE,0) ARCHITECTURAL_ENGINEERING_FEE," +
                "ifnull(INSTALLATION_ENGINEERING_FEE,0) INSTALLATION_ENGINEERING_FEE, " +
                "ifnull(EQUIPMENT_PURCHASE_FEE,0) EQUIPMENT_PURCHASE_FEE, " +
                "ifnull(OTHER_FEE,0) OTHER_FEE " +
                " from pm_statistics_fee where year=?", year);

        Map<String, List<Map<String, Object>>> monthMapData = list.stream().collect(Collectors.groupingBy(p -> JdbcMapUtil.getString(p, "MONTH")));

        Map<String, List<Map<String, Object>>> monthFeeMapData = feeList.stream().collect(Collectors.groupingBy(p -> JdbcMapUtil.getString(p, "MONTH")));

        List<BigDecimal> planList = new ArrayList<>();
        List<BigDecimal> completeList = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            if (monthMapData.containsKey(String.valueOf(i))) {
                List<Map<String, Object>> currentMonth = monthMapData.get(String.valueOf(i));
                planList.add(currentMonth.stream().map(p -> new BigDecimal(JdbcMapUtil.getString(p, "AMT"))).reduce(BigDecimal.ZERO, BigDecimal::add));
            } else {
                planList.add(BigDecimal.ZERO);
            }
        }
        for (int i = 1; i <= 12; i++) {
            if (monthFeeMapData.containsKey(String.valueOf(i))) {
                List<Map<String, Object>> currentFeeMonth = monthFeeMapData.get(String.valueOf(i));
                BigDecimal jz = currentFeeMonth.stream().map(p -> new BigDecimal(JdbcMapUtil.getString(p, "ARCHITECTURAL_ENGINEERING_FEE"))).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal az = currentFeeMonth.stream().map(p -> new BigDecimal(JdbcMapUtil.getString(p, "INSTALLATION_ENGINEERING_FEE"))).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal cg = currentFeeMonth.stream().map(p -> new BigDecimal(JdbcMapUtil.getString(p, "EQUIPMENT_PURCHASE_FEE"))).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal qt = currentFeeMonth.stream().map(p -> new BigDecimal(JdbcMapUtil.getString(p, "OTHER_FEE"))).reduce(BigDecimal.ZERO, BigDecimal::add);
                completeList.add(jz.add(az).add(cg).add(qt));
            } else {

                completeList.add(BigDecimal.ZERO);
            }
        }

        res.plannedInvestment = planList;
        res.plannedInvestmentSum = list.stream().map(p -> new BigDecimal(JdbcMapUtil.getString(p, "AMT"))).reduce(BigDecimal.ZERO, BigDecimal::add);

        res.completeInvestment = completeList;
        res.completeInvestmentSum = completeList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(res), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }


    public static class AnnualInvestmentProgress {
        /**
         * 计划投资
         */
        public List<BigDecimal> plannedInvestment;

        /**
         * 完成投资
         */
        public List<BigDecimal> completeInvestment;

        public BigDecimal plannedInvestmentSum;

        public BigDecimal completeInvestmentSum;
    }


}
