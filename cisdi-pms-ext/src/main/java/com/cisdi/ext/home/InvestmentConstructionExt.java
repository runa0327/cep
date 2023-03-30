package com.cisdi.ext.home;

import com.cisdi.ext.util.BigDecimalUtil;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title InvestmentConstructionExt
 * @package com.cisdi.ext.home
 * @description 项目建设投资
 * @date 2022/11/24
 */
public class InvestmentConstructionExt {

    /**
     * 项目建设投资统计
     */
    public void statisticalData() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String userId = ExtJarHelper.loginInfo.get().userId;
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_PRJ where status='ap' and PROJECT_SOURCE_TYPE_ID='0099952822476441374' and " +
                " IF(? in (select ad_user_id from ad_role_user where ad_role_id = '0099250247095870406') ,1=1," +
                " id in (select DISTINCT pm_prj_id from pm_dept WHERE STATUS = 'ap' and FIND_IN_SET(?, USER_IDS ))) ", userId,userId);
        Map<String, List<Map<String, Object>>> mapData = list.stream().collect(Collectors.groupingBy(p -> Optional.ofNullable(JdbcMapUtil.getString(p, "PROJECT_PHASE_ID")).orElse("0")));
        List<StatisticalData> resData = new ArrayList<>();

        List<Map<String, Object>> phaseList = myJdbcTemplate.queryForList("select gsv.* from gr_set_value gsv left join gr_set gs on gs.id = gsv.GR_SET_ID\n" +
                " where gs.`code` ='project_phase'");

        for (String key : mapData.keySet()) {
            StatisticalData data = new StatisticalData();
            String projectPhase = "";
            if (Objects.equals("0", key)) {
                projectPhase = "其他";
            } else {
                Optional<Map<String, Object>> optional = phaseList.stream().filter(p -> Objects.equals(key, JdbcMapUtil.getString(p, "ID"))).findAny();
                if (optional.isPresent()) {
                    projectPhase = String.valueOf(optional.get().get("NAME"));
                }
            }
            data.projectPhase = projectPhase;
            List<Map<String, Object>> typeData = mapData.get(key);
            data.totalProject = typeData.size();

            List<String> ids = typeData.stream().map(m -> JdbcMapUtil.getString(m, "ID")).collect(Collectors.toList());
            //总投资金额
            MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
            Map<String, Object> dataParam = new HashMap<>();
            dataParam.put("ids", ids);
            List<Map<String, Object>> tzList = myNamedParameterJdbcTemplate.queryForList(" select sum(ifnull(PRJ_TOTAL_INVEST,0)) PRJ_TOTAL_INVEST from PM_INVEST_EST pie \n" +
                    " left join gr_set_value gsv on pie.INVEST_EST_TYPE_ID = gsv.id \n" +
                    " where  pie.PM_PRJ_ID in (:ids) order by gsv.code desc limit 0,1 ", dataParam);
            data.totalInvestment = tzList.stream().map(p1 -> new BigDecimal(JdbcMapUtil.getString(p1, "PRJ_TOTAL_INVEST"))).reduce(BigDecimal.ZERO, BigDecimal::add);
            //完成投资
            List<Map<String, Object>> wcList = myNamedParameterJdbcTemplate.queryForList("select * from pm_statistics_fee where PM_PRJ_ID in (:ids)", dataParam);
            Map<String, List<Map<String, Object>>> feeMapData = wcList.stream().collect(Collectors.groupingBy(p -> JdbcMapUtil.getString(p, "PM_PRJ_ID")));
            BigDecimal completeInvestment = BigDecimal.ZERO;
            for (String feeKey : feeMapData.keySet()) {
                List<Map<String, Object>> feeData = feeMapData.get(feeKey);
                Map<String, List<Map<String, Object>>> yearMapData = feeData.stream().collect(Collectors.groupingBy(p -> JdbcMapUtil.getString(p, "YEAR")));
                for (String yearKey : yearMapData.keySet()) {
                    List<Map<String, Object>> yearData = yearMapData.get(yearKey);
                    List<Map<String, Object>> maxYear = yearData.stream().sorted(Comparator.comparing(p -> JdbcMapUtil.getString(p, "MONTH"))).collect(Collectors.toList());
                    Map<String, Object> maxMonthData = maxYear.get(0);
                    BigDecimal thisYearFee = new BigDecimal(JdbcMapUtil.getString(maxMonthData, "ARCHITECTURAL_ENGINEERING_FEE"))
                            .add(new BigDecimal(JdbcMapUtil.getString(maxMonthData, "INSTALLATION_ENGINEERING_FEE")))
                            .add(new BigDecimal(JdbcMapUtil.getString(maxMonthData, "EQUIPMENT_PURCHASE_FEE")))
                            .add(new BigDecimal(JdbcMapUtil.getString(maxMonthData, "OTHER_FEE")));
                    completeInvestment = completeInvestment.add(thisYearFee);

                }
            }
            data.completeInvestment = completeInvestment.multiply(new BigDecimal(10000));
            //完成支付
            List<Map<String, Object>> zfList = myNamedParameterJdbcTemplate.queryForList("select pod.*,sum(ifnull(pop.PAY_AMT,0)) as PAY_AMT from PO_ORDER_DTL pod \n" +
                    "left join po_order po on po.id = pod.PO_ORDER_ID \n" +
                    "left join PO_ORDER_PAYMENT pop on  pop.PO_ORDER_DTL_ID = pod.id \n" +
                    "where po.PM_PRJ_ID in (:ids) group by pod.id", dataParam);
            BigDecimal totalMeasurePrice = zfList.stream().map(p -> new BigDecimal(JdbcMapUtil.getString(p, "PAY_AMT"))).reduce(BigDecimal.ZERO, BigDecimal::add);
            data.totalMeasurePrice = totalMeasurePrice.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP);
            data.totalPayPrice = totalMeasurePrice.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP);
            resData.add(data);
        }

        if (CollectionUtils.isEmpty(resData)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide res = new OutSide();
            res.statisticalDataList = resData;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(res), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    public static class StatisticalData {
        /**
         * 项目阶段
         */
        public String projectPhase;

        /**
         * 项目总数量
         */
        public Integer totalProject;

        /**
         * 总投资金额
         */
        public BigDecimal totalInvestment;

        /**
         * 完成投资金额
         */
        public BigDecimal completeInvestment;

        /**
         * 总计量金额
         */
        public BigDecimal totalMeasurePrice;

        /**
         * 总支付金额
         */
        public BigDecimal totalPayPrice;
    }

    public static class OutSide {
        public List<StatisticalData> statisticalDataList;
    }
}
