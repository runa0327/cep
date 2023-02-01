package com.cisdi.ext.demostration;

import com.cisdi.ext.home.DynamicCostExt;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title DynamicCostTempExt
 * @package com.cisdi.ext.demostration
 * @description 动态成本
 * @date 2023/2/1
 */
public class DynamicCostTempExt {


    /**
     * 动态成本
     */
    public void dynamicCostTemp() {

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select COST_TYPE_WR,\n" +
                "ifnull(FEASIBILITY_APPROVE_FUND,0) as FEASIBILITY_APPROVE_FUND,\n" +
                "ifnull(ESTIMATE_AMT,0) as ESTIMATE_AMT,\n" +
                "ifnull(EVALUATION_APPROVE_FUND,0) as EVALUATION_APPROVE_FUND,\n" +
                "ifnull(AMT,0) as AMT,\n" +
                "ifnull(CONTRACT_PRICE,0) as CONTRACT_PRICE,\n" +
                "ifnull(CONTRACT_AMOUNT,0) as CONTRACT_AMOUNT,\n" +
                "ifnull(CHANGE_AMOUNT,0) as CHANGE_AMOUNT,\n" +
                "ifnull(SETTLEMENT_AMT,0) as SETTLEMENT_AMT,\n" +
                "ifnull(AMT_ONE,0) as AMT_ONE,\n" +
                "ifnull(AMT_TWO,0) as AMT_TWO,\n" +
                "ifnull(AMT_THREE,0) as AMT_THREE,\n" +
                "ifnull(AMT_FOUR,0) as AMT_FOUR,\n" +
                "ifnull(AMT_FIVE,0) as AMT_FIVE,\n" +
                "ifnull(TOTAL_AMT_6,0) as TOTAL_AMT_6,\n" +
                "REMARK_SHORT \n" +
                "from DYNAMIC_COST");
        AtomicInteger index = new AtomicInteger(0);
        List<ProjectDynamicCostTreeInfo> infoList = list.stream().map(p -> {
            ProjectDynamicCostTreeInfo info = new ProjectDynamicCostTreeInfo();
            info.numericalOrder = JdbcMapUtil.getString(p, String.valueOf(index.getAndIncrement()));
            info.indicatorsName = JdbcMapUtil.getString(p, "COST_TYPE_WR");
            info.estimate = JdbcMapUtil.getBigDecimal(p, "FEASIBILITY_APPROVE_FUND");
            info.budgetEstimate = JdbcMapUtil.getBigDecimal(p, "ESTIMATE_AMT");
            info.budget = JdbcMapUtil.getBigDecimal(p, "EVALUATION_APPROVE_FUND");
            info.prices1 = JdbcMapUtil.getBigDecimal(p, "AMT");
            info.prices2 = JdbcMapUtil.getBigDecimal(p, "CONTRACT_PRICE");
            info.contractPrice = JdbcMapUtil.getBigDecimal(p, "CONTRACT_AMOUNT");
            info.contractChangeAmount = JdbcMapUtil.getBigDecimal(p, "CHANGE_AMOUNT");
            info.estimatedSettlementPrice = JdbcMapUtil.getBigDecimal(p, "SETTLEMENT_AMT");
            info.prices3 = JdbcMapUtil.getBigDecimal(p, "AMT_ONE");
            info.proportion = JdbcMapUtil.getBigDecimal(p, "AMT_TWO");
            info.ownFunds = JdbcMapUtil.getBigDecimal(p, "AMT_THREE");
            info.specialFund = JdbcMapUtil.getBigDecimal(p, "AMT_FOUR");
            info.paidProRata = JdbcMapUtil.getBigDecimal(p, "AMT_FIVE");
            info.settlementPrice = JdbcMapUtil.getBigDecimal(p, "TOTAL_AMT_6");
            info.remakes = JdbcMapUtil.getBigDecimal(p, "REMARK_SHORT");
            return info;
        }).collect(Collectors.toList());

        Map<String, Object> res = new HashMap<>();
        res.put("treeInfos", infoList);

        DynamicCostExt.ProjectDynamicCostTotal costTotal = new DynamicCostExt.ProjectDynamicCostTotal();
        String[] str = new String[]{"工程费用", "工程其他费用", "预备费"};

        //估算总额
        BigDecimal estimateTotal = BigDecimal.ZERO;
        //概算总额
        BigDecimal budgetEstimateTotal = BigDecimal.ZERO;
        //预算总额
        BigDecimal budgetTotal = BigDecimal.ZERO;
        //合同总额
        BigDecimal contractTotal = BigDecimal.ZERO;
        //专项资金总额
        BigDecimal specialFundTotal = BigDecimal.ZERO;
        //自有资金总额
        BigDecimal ownFundsTotal = BigDecimal.ZERO;
        for (ProjectDynamicCostTreeInfo info : infoList) {
            if (Arrays.asList(str).contains(info.indicatorsName)) {
                estimateTotal = estimateTotal.add(info.estimate);
                budgetEstimateTotal = budgetEstimateTotal.add(info.budgetEstimate);
                budgetTotal = budgetTotal.add(info.budget);
                contractTotal = contractTotal.add(info.contractPrice);
                specialFundTotal = specialFundTotal.add(info.specialFund);
                ownFundsTotal = ownFundsTotal.add(info.ownFunds);
            }
        }
        costTotal.budgetEstimateTotal = budgetEstimateTotal;
        costTotal.budgetTotal = budgetTotal;
        costTotal.contractTotal = contractTotal;
        costTotal.specialFundTotal = specialFundTotal;
        costTotal.estimateTotal = estimateTotal;
        costTotal.ownFundsTotal = ownFundsTotal;
        res.put("costTotal", costTotal);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(res), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }


    public static class ProjectDynamicCostTreeInfo {

        /**
         * 序号
         */
        public String numericalOrder;
        /**
         * 费用类型
         */
        public String indicatorsName;

        /**
         * 可研批复金额（万元）
         */
        public BigDecimal estimate = BigDecimal.ZERO;


        /**
         * 概算批复金额（万元）
         */
        public BigDecimal budgetEstimate = BigDecimal.ZERO;

        /**
         * 财评批复金额（万元）
         */
        public BigDecimal budget = BigDecimal.ZERO;

        /**
         * 概算批复-预算审核值（万元）
         */
        public BigDecimal prices1 = BigDecimal.ZERO;

        /**
         * 概算批复-合同价（万元）
         */
        public BigDecimal prices2 = BigDecimal.ZERO;

        /**
         * 合同价(1)（万元）
         */
        public BigDecimal contractPrice = BigDecimal.ZERO;

        /**
         * 合同变更金额(2)（万元）
         */
        public BigDecimal contractChangeAmount = BigDecimal.ZERO;

        /**
         * 预计结算价(3)=(1)+(2)（万元）
         */
        public BigDecimal estimatedSettlementPrice = BigDecimal.ZERO;
        /**
         * 概算-预计结算价（万元）
         */
        public BigDecimal prices3 = BigDecimal.ZERO;
        /**
         * 估结算价超合同价比列（%）
         */
        public BigDecimal proportion = BigDecimal.ZERO;
        /**
         * 自有资金（万元）
         */
        public BigDecimal ownFunds = BigDecimal.ZERO;
        /**
         * 专项资金（万元）
         */
        public BigDecimal specialFund = BigDecimal.ZERO;
        /**
         * 支付比例
         */
        public BigDecimal paidProRata = BigDecimal.ZERO;
        /**
         * 结算价（万元）
         */
        public BigDecimal settlementPrice = BigDecimal.ZERO;
        /**
         * 备注
         */
        public BigDecimal remakes = BigDecimal.ZERO;
    }

    public static class ProjectDynamicCostTotal {
        /**
         * 估算总额  (可研批复)
         */
        public BigDecimal estimateTotal = BigDecimal.ZERO;
        /**
         * 概算总额 (概算批复)
         */

        public BigDecimal budgetEstimateTotal = BigDecimal.ZERO;

        /**
         * 预算总额 (财评批复)
         */
        public BigDecimal budgetTotal = BigDecimal.ZERO;

        /**
         * 合同总额
         */
        public BigDecimal contractTotal = BigDecimal.ZERO;

        /**
         * 专项资金总额
         */
        public BigDecimal specialFundTotal = BigDecimal.ZERO;
        /**
         * 自有资金总额
         */
        public BigDecimal ownFundsTotal = BigDecimal.ZERO;
    }
}
