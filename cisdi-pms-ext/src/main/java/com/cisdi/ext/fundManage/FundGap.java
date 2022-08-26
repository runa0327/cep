package com.cisdi.ext.fundManage;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.model.BasePageEntity;
import com.cisdi.ext.util.JsonUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//资金缺口
@Slf4j
public class FundGap {

    //资金来源支付列表
    public void getFundGapList() {
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        String inputJson = JsonUtil.toJson(inputMap);
        Input input = JsonUtil.fromJson(inputJson, Input.class);

        StringBuffer baseSql = new StringBuffer();
        baseSql.append(
                "SELECT " +
                        "p.id, " +
                        "v3.name managementUnit, " +
                        "p.NAME prjName, " +
                        "p.PRJ_SITUATION prjSituation, " +
                        "r.NAME customerUnit, " +
                        "v1.NAME phaseName, " +
                        "CONCAT( v2.NAME, ROUND( n.ACTUAL_CURRENT_PRO_PERCENT * 100, 0 ), '%' ) processPercent, " +
                        "IFNULL( n.ACTUAL_START_DATE, n.PLAN_START_DATE ) startDay, " +
                        "n.PLAN_COMPL_DATE planComplDate, " +
                        "max(q.PRJ_TOTAL_INVEST) initTotalInvest, " +
                        "max(i2.PRJ_TOTAL_INVEST) estimateTotalInvest, " +
                        "max(i3.PRJ_TOTAL_INVEST) budgetTotalInvest, " +
                        "max(temp1.apportionAmt) apportionAmt, " +
                        "max(temp2.payAmt) payAmt " +
                        "FROM " +
                        "pm_prj p " +
                        "LEFT JOIN pm_party r ON r.id = p.CUSTOMER_UNIT " +
                        "LEFT JOIN gr_set_value v1 ON v1.id = p.PROJECT_PHASE_ID " +
                        "LEFT JOIN gr_set t1 ON t1.id = v1.GR_SET_ID AND t1.CODE = 'project_phase' " +
                        "LEFT JOIN pm_pro_plan n ON n.PM_PRJ_ID = p.id " +
                        "LEFT JOIN gr_set_value v2 ON v2.id = p.TRANSITION_PHASE_ID " +
                        "LEFT JOIN gr_set t2 ON t2.id = v2.GR_SET_ID AND t2.CODE = 'transition_phase' " +
                        "LEFT JOIN gr_set_value v3 ON v3.id = p.PRJ_MANAGE_MODE_ID " +
                        "LEFT JOIN gr_set t3 ON t3.id = v3.GR_SET_ID AND t3.CODE = 'management_unit' " +
                        "LEFT JOIN pm_prj_req q ON q.PM_PRJ_ID = p.id " +
                        "LEFT JOIN PM_PRJ_INVEST2 i2 ON i2.PM_PRJ_ID = p.id and i2.STATUS = 'AP' " +
                        "LEFT JOIN PM_PRJ_INVEST3 i3 ON i3.PM_PRJ_ID = p.id and i3.status = 'AP' " +
                        "left join (select p.id,sum(a.APPORTION_AMT) apportionAmt from pm_prj p left join " +
                        "pm_fund_apportion a on a.PM_PRJ_ID = p.ID group by p.id) temp1 on temp1.id = p.id " +
                        "left join (select p.id,sum(y.PAY_AMT) payAmt from pm_prj p left join pm_fund_pay y on y" +
                        ".PM_PRJ_ID = p.ID group by p.id) temp2 on temp2.id = p.id " +
                        "left join pm_fund_apportion a on a.PM_PRJ_ID = p.id " +
                        "left join pm_fund_source s on s.id = a.PM_FUND_SOURCE_ID where 1=1 "
        );
        if (!Strings.isNullOrEmpty(input.prjId)){
            baseSql.append("and p.id = '" + input.prjId + "' ");
        }
        if (!Strings.isNullOrEmpty(input.manageUnitId)){
            baseSql.append("and p.PRJ_MANAGE_MODE_ID = '" + input.manageUnitId + "' ");
        }
        if (!Strings.isNullOrEmpty(input.prjPhaseId)){
            baseSql.append("and p.PROJECT_PHASE_ID = '" + input.prjPhaseId + "' ");
        }
        if (!Strings.isNullOrEmpty(input.beginStartDay) && !Strings.isNullOrEmpty(input.beginEndDay)) {
            baseSql.append("and IFNULL( n.ACTUAL_START_DATE, n.PLAN_START_DATE ) BETWEEN '" + input.beginStartDay + "' and '" + input.beginEndDay + "' ");
        }
        if (!Strings.isNullOrEmpty(input.finishStartDay) && !Strings.isNullOrEmpty(input.finishEndDay)) {
            baseSql.append("and n.PLAN_COMPL_DATE BETWEEN '" + input.finishStartDay + "' and '" + input.finishEndDay + "' ");
        }
        if (!Strings.isNullOrEmpty(input.fundSourceId)) {
            baseSql.append("and s.id = '" + input.fundSourceId + "' ");
        }

        baseSql.append("group by p.id ");
        //除开翻页查询获取总条数
        String totalSql = baseSql.toString();
        baseSql.append("order by p.CRT_DT desc ");

        Integer start = input.pageSize * (input.pageIndex - 1);
        baseSql.append("limit " + start + "," + input.pageSize);

        log.info(baseSql.toString());
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(baseSql.toString());
        List<Map<String, Object>> totalList = jdbcTemplate.queryForList(totalSql);
        HashMap<String, Object> result = new HashMap<>();
        result.put("resultList", resultList);
        result.put("total", totalList.size());

        Map outputMap = JsonUtil.fromJson(JSONObject.toJSONString(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }
    public static class Input extends BasePageEntity{
        /**
         * 项目id
         */
        public String prjId;

        /**
         * 管理单位id
         */
        public String manageUnitId;

        /**
         * 建设状态id
         */
        public String prjPhaseId;

        /**
         * 开工开始日期
         */
        public String beginStartDay;

        /**
         * 开工结束日期
         */
        public String beginEndDay;

        /**
         * 竣工开始日期
         */
        public String finishStartDay;

        /**
         * 竣工结束日期
         */
        public String finishEndDay;

        /**
         * 资金来源id
         */
        public String fundSourceId;

    }
}
