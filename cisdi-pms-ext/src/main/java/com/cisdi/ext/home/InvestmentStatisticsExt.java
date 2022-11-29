package com.cisdi.ext.home;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title InvestmentStatisticsExt
 * @package com.cisdi.ext.home
 * @description 投资统计
 * @date 2022/11/28
 */
public class InvestmentStatisticsExt {

    public void investmentStatistics() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectName = String.valueOf(map.get("projectName"));
        String projectType = String.valueOf(map.get("projectType"));
        String managementUnit = String.valueOf(map.get("managementUnit"));
        int pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
        int pageIndex = Integer.parseInt(String.valueOf(map.get("pageIndex")));

        StringBuffer sb = new StringBuffer();
        sb.append("select * from pm_prj where `STATUS`='ap' ");
        if (Strings.isNotEmpty(projectName)) {
            sb.append(" and `NAME` like ").append("'%").append(projectName).append("%'");
        }
        if (Strings.isNotEmpty(projectType)) {
            sb.append(" and PROJECT_TYPE_ID = '").append(projectType).append("'");
        }
        if (Strings.isNotEmpty(managementUnit)) {
            sb.append(" and PRJ_MANAGE_MODE_ID = '").append(managementUnit).append("'");
        }
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        Map<String, String> feeType = this.feeType();
        List<CostStatisticsInfo> infoList = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : list) {
            String projectId = JdbcMapUtil.getString(stringObjectMap, "ID");
            List<Map<String, Object>> feeList = myJdbcTemplate.queryForList("select pied.*,pet.`CODE` as feeCode,pie.INVEST_EST_TYPE_ID,gsv.`CODE` estType from pm_invest_est_dtl pied \n" +
                    "left join pm_invest_est pie on pied.PM_INVEST_EST_ID = pie.id \n" +
                    "left join pm_exp_type pet on pied.PM_EXP_TYPE_ID = pet.ID \n" +
                    "left join gr_set_value gsv on pie.INVEST_EST_TYPE_ID = gsv.id \n" +
                    "where PM_PRJ_ID=? ", projectId);
            Map<String, List<Map<String, Object>>> mapData = feeList.stream().collect(Collectors.groupingBy(p -> JdbcMapUtil.getString(p, "estType")));
            BigDecimal feasibleSumTotal = BigDecimal.ZERO;
            BigDecimal budgetEstimateSumTotal = BigDecimal.ZERO;
            BigDecimal financialAppraisalSumTotal = BigDecimal.ZERO;
            for (String key : feeType.keySet()) {
                CostStatisticsInfo info = new CostStatisticsInfo();
                info.projectId = projectId;
                info.projectName = JdbcMapUtil.getString(stringObjectMap, "NAME");
                info.pointerTypeName = feeType.get(key);

                List<Map<String, Object>> dataList1 = mapData.get("invest1");
                List<Map<String, Object>> dataList2 = mapData.get("invest2");
                List<Map<String, Object>> dataList3 = mapData.get("invest3");

                if (!CollectionUtils.isEmpty(dataList1)) {
                    Optional<Map<String, Object>> optional = dataList1.stream().filter(p -> Objects.equals(key, JdbcMapUtil.getString(p, "feeCode"))).findAny();
                    optional.ifPresent(objectMap -> info.feasibleSum = new BigDecimal(String.valueOf(objectMap.get("AMT"))));

                }
                if (!CollectionUtils.isEmpty(dataList2)) {
                    Optional<Map<String, Object>> optional = dataList2.stream().filter(p -> Objects.equals(key, JdbcMapUtil.getString(p, "feeCode"))).findAny();
                    optional.ifPresent(objectMap -> info.budgetEstimateSum = new BigDecimal(String.valueOf(objectMap.get("AMT"))));

                }
                if (!CollectionUtils.isEmpty(dataList3)) {
                    Optional<Map<String, Object>> optional = dataList3.stream().filter(p -> Objects.equals(key, JdbcMapUtil.getString(p, "feeCode"))).findAny();
                    optional.ifPresent(objectMap -> info.financialAppraisalSum = new BigDecimal(String.valueOf(objectMap.get("AMT"))));

                }
                infoList.add(info);
                feasibleSumTotal = feasibleSumTotal.add(info.feasibleSum);
                budgetEstimateSumTotal = budgetEstimateSumTotal.add(info.budgetEstimateSum);
                financialAppraisalSumTotal = financialAppraisalSumTotal.add(info.financialAppraisalSum);
            }
            CostStatisticsInfo info = new CostStatisticsInfo();
            info.projectId = projectId;
            info.projectName = JdbcMapUtil.getString(stringObjectMap, "NAME");
            info.pointerTypeName = feeType.get("合计");
            info.feasibleSum = feasibleSumTotal;
            info.budgetEstimateSum = budgetEstimateSumTotal;
            info.financialAppraisalSum = financialAppraisalSumTotal;
            infoList.add(info);
        }
        if (CollectionUtils.isEmpty(infoList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            AtomicInteger index = new AtomicInteger(0);
            infoList.forEach(item -> {
                item.serialNumber = index.getAndIncrement();
            });
            OutSide res = new OutSide();
            List<Map<String, Object>> list1 = myJdbcTemplate.queryForList(totalSql);
            res.list = infoList;
            res.total = list1.size();
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(res), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    private Map<String, String> feeType() {
        Map<String, String> data = new HashMap<>();
        data.put("PROJECT_AMT", "工程费用");
        data.put("PROJECT_OTHER_AMT", "工程其他费用");
        data.put("PREPARE_AMT", "预备费");
        return data;
    }

    public static class CostStatisticsInfo {
        /**
         * 序号
         */
        public int serialNumber;

        /**
         * 项目id
         */
        public String projectId;

        /**
         * 项目名称
         */
        public String projectName;

        /**
         * 指标类型
         */
        public String pointerType;
        /**
         * 指标类型名称
         */
        public String pointerTypeName;

        /**
         * 可研批复金额（万元）
         */
        public BigDecimal feasibleSum = BigDecimal.ZERO;

        /**
         * 概算批复金额（万元）
         */
        public BigDecimal budgetEstimateSum = BigDecimal.ZERO;

        /**
         * 财评批复金额（万元）
         */

        public BigDecimal financialAppraisalSum = BigDecimal.ZERO;

        /**
         * 政府专项资金（万元）
         */

        public BigDecimal specialFunds = BigDecimal.ZERO;

        /**
         * 已签合同金额（万元） ：指标关联所有合同变更后金额之和
         */

        public BigDecimal contractAmount = BigDecimal.ZERO;

        /**
         * 累计已支付（万元）：截至本期已支付总额
         */

        public BigDecimal havePaidSum = BigDecimal.ZERO;

        /**
         * 专项资金已申请金额（万元） ：本期计量金额之和
         */
        public BigDecimal appliedAmount = BigDecimal.ZERO;

        /**
         * 结算金额（万元）
         */
        public BigDecimal settlementAmount = BigDecimal.ZERO;
    }

    public static class OutSide {
        public Integer total;
        public List<CostStatisticsInfo> list;
    }
}
