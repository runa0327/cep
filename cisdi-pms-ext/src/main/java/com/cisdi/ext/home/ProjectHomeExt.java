package com.cisdi.ext.home;

import com.cisdi.ext.util.BigDecimalUtil;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProjectHomeExt
 * @package com.cisdi.ext.home
 * @description 项目首页接口
 * @date 2023/2/28
 */
public class ProjectHomeExt {

    /**
     * 项目总投资
     */
    public void totalInvest() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = String.valueOf(map.get("projectId"));
        List<Map<String, Object>> typeList = myJdbcTemplate.queryForList("select gsv.* from gr_set_value gsv left join gr_set gs on gsv.GR_SET_ID = gs.id where gs.`CODE`='invest_est_type' order by SEQ_NO  ");
        BigDecimal totalAmt = BigDecimal.ZERO;
        BigDecimal planAmt = BigDecimal.ZERO;
        BigDecimal completeAmt = BigDecimal.ZERO;
        for (Map<String, Object> stringObjectMap : typeList) {
            List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ifnull(pied.AMT,0) as amt from pm_invest_est  pie  " +
                    "left join pm_invest_est_dtl pied on pie.id = pied.PM_INVEST_EST_ID  " +
                    "left join pm_exp_type pet on pet.id = pied.PM_EXP_TYPE_ID " +
                    "where  pet.`code`='PRJ_TOTAL_INVEST' and pie.PM_PRJ_ID=? and pie.INVEST_EST_TYPE_ID=?", projectId, stringObjectMap.get("ID"));
            if (!CollectionUtils.isEmpty(list)) {
                BigDecimal amt = new BigDecimal(String.valueOf(list.get(0).get("amt")));
                if (amt.compareTo(BigDecimal.ZERO) > 0) {
                    totalAmt = amt;
                }
            }

        }

        //今年计划投资-取投资计划汇总
        int currentYear = LocalDate.now().getYear();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ifnull(AMT,0) as AMT from PM_INVESTMENT_YEAR_PLAN where PM_PRJ_ID =? and `year`=?", projectId, currentYear);
        if (!CollectionUtils.isEmpty(list)) {
            planAmt = new BigDecimal(String.valueOf(list.get(0).get("AMT")));
        }

        //今年完成投资-取纳统汇总
        String sql = " select id,\n" +
                "        PM_PRJ_ID,\n" +
                "        year,\n" +
                "        month,\n" +
                "        ifnull(architectural_engineering_fee,0) as architectural_engineering_fee,\n" +
                "        ifnull(installation_engineering_fee,0) as installation_engineering_fee,\n" +
                "        ifnull(equipment_purchase_fee,0) as equipment_purchase_fee,\n" +
                "        ifnull(other_fee,0) as other_fee,\n" +
                "        ifnull(this_month_investment,0) as this_month_investment ,\n" +
                "        ifnull(residential,0) as residential,\n" +
                "        ifnull(purchase_old_equipment,0) as purchase_old_equipment,\n" +
                "        ifnull(purchase_old_building,0) as purchase_old_building,\n" +
                "        ifnull(construction_land_charge,0) as construction_land_charge,\n" +
                "        ifnull(this_year_investment,0) as this_year_investment\n" +
                "        from PM_STATISTICS_FEE\n" +
                "        where PM_PRJ_ID=? and year=? " +
                "        order by month";
        List<Map<String, Object>> feeList = myJdbcTemplate.queryForList(sql, projectId, currentYear);
        if (!CollectionUtils.isEmpty(feeList)) {
            completeAmt = BigDecimalUtil.stringToBigDecimal(String.valueOf(feeList.get(0).get("architectural_engineering_fee")))
                    .add(BigDecimalUtil.stringToBigDecimal(String.valueOf(feeList.get(0).get("installation_engineering_fee"))))
                    .add(BigDecimalUtil.stringToBigDecimal(String.valueOf(feeList.get(0).get("equipment_purchase_fee"))))
                    .add(BigDecimalUtil.stringToBigDecimal(String.valueOf(feeList.get(0).get("other_fee"))));
        }

        OutSide outSide = new OutSide();
        outSide.totalAmt = totalAmt;
        outSide.planAmt = planAmt;
        outSide.completeAmt = completeAmt;
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }


    public static class OutSide {
        public BigDecimal totalAmt;

        public BigDecimal planAmt;

        public BigDecimal completeAmt;

    }
}
