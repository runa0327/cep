package com.cisdi.ext.home;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title DynamicCostExt
 * @package com.cisdi.ext.home
 * @description 动态成本
 * @date 2022/11/29
 */
public class DynamicCostExt {

    public void dynamicCostStatistical() {
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = String.valueOf(map.get("projectId"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> feeList = myJdbcTemplate.queryForList("select pied.*,pet.`CODE` as feeCode,pet.`NAME` feeName,pie.INVEST_EST_TYPE_ID,gsv.`CODE` estType from pm_invest_est_dtl pied \n" +
                "left join pm_invest_est pie on pied.PM_INVEST_EST_ID = pie.id \n" +
                "left join pm_exp_type pet on pied.PM_EXP_TYPE_ID = pet.ID \n" +
                "left join gr_set_value gsv on pie.INVEST_EST_TYPE_ID = gsv.id \n" +
                "where PM_PRJ_ID=? ", projectId);
        Map<String, List<Map<String, Object>>> mapData = feeList.stream().collect(Collectors.groupingBy(p -> JdbcMapUtil.getString(p, "estType")));
        List<Map<String, Object>> typeList = myJdbcTemplate.queryForList("select * from pm_exp_type where status='ap'");
        List<ProjectDynamicCostTreeInfo> resList = new ArrayList<>();
        AtomicInteger index = new AtomicInteger(0);
        for (Map<String, Object> stringObjectMap : typeList) {
            ProjectDynamicCostTreeInfo info = new ProjectDynamicCostTreeInfo();
            String code = JdbcMapUtil.getString(stringObjectMap, "CODE");
            info.numericalOrder = String.valueOf(index.getAndIncrement());
            info.indicatorsName = JdbcMapUtil.getString(stringObjectMap, "NAME");
            // 可研批复
            if (mapData.containsKey("invest1")) {
                List<Map<String, Object>> kyList = mapData.get("invest1");
                Optional<Map<String, Object>> optional = kyList.stream().filter(p -> Objects.equals(code, JdbcMapUtil.getString(p, "feeCode"))).findAny();
                optional.ifPresent(objectMap -> info.estimate = new BigDecimal(String.valueOf(objectMap.get("AMT"))));
            }
            //概算批复
            if (mapData.containsKey("invest2")) {
                List<Map<String, Object>> gsList = mapData.get("invest2");
                Optional<Map<String, Object>> optional = gsList.stream().filter(p -> Objects.equals(code, JdbcMapUtil.getString(p, "feeCode"))).findAny();
                optional.ifPresent(objectMap -> info.budgetEstimate = new BigDecimal(String.valueOf(objectMap.get("AMT"))));
            }
            //财评
            if (mapData.containsKey("invest3")) {
                List<Map<String, Object>> gsList = mapData.get("invest3");
                Optional<Map<String, Object>> optional = gsList.stream().filter(p -> Objects.equals(code, JdbcMapUtil.getString(p, "feeCode"))).findAny();
                optional.ifPresent(objectMap -> info.budget = new BigDecimal(String.valueOf(objectMap.get("AMT"))));
            }
            //TODO 计算逻辑待确认
            //预算审核值
            //概算批复-合同价
            //动态造假统计-合同价
            //动态造假统计-合同变更金额
            //动态造假统计-预计结算价
            //概算-预计结算价
            resList.add(info);
        }
        res.put("treeInfos", resList);
        ProjectDynamicCostTotal costTotal = new ProjectDynamicCostTotal();
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
        for (ProjectDynamicCostTreeInfo info : resList) {
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
