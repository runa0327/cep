package com.cisdi.pms.job.excel.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.cisdi.pms.job.excel.model.InvestStatisticModel;
import com.cisdi.pms.job.excel.model.request.InvestRequest;
import com.cisdi.pms.job.excel.strategy.InvestMergeStrategy;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author dlt
 * @date 2022/11/30 周三
 */
@RestController
@RequestMapping("invest")
public class InvestStatisticController extends BaseController {

    @Autowired
    private JdbcTemplate myJdbcTemplate;

    @SneakyThrows
    @GetMapping("/export")
    public void investmentStatistics(InvestRequest request, HttpServletResponse response) {

        String projectName = request.getProjectName();
        String projectType = request.getProjectType();
        String managementUnit = request.getManagementUnit();

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
            return;
        } else {
            AtomicInteger index = new AtomicInteger(1);
            infoList.get(0).serialNumber = index.get();
            for (int i = 1; i < infoList.size(); i++) {
                if (infoList.get(i).projectId.equals(infoList.get(i - 1).projectId)){
                    infoList.get(i).serialNumber = index.get();
                }else {
                    infoList.get(i).serialNumber = index.incrementAndGet();
                }
            }
        }

        List<List<Object>> modelLists = new ArrayList<>();
        for (CostStatisticsInfo info : infoList) {
            modelLists.add(this.getList(info));
        }
        super.setExcelRespProp(response,"投资统计");
        EasyExcel.write(response.getOutputStream())
                .head(InvestStatisticModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("投资统计")
                .registerWriteHandler(new InvestMergeStrategy(this.getSerial(infoList),0))
                .registerWriteHandler(new InvestMergeStrategy(this.getMergePrj(infoList),1))
                .registerWriteHandler(new SimpleColumnWidthStyleStrategy(30))
                .doWrite(modelLists);
    }

    //得到需要合并的项目列
    private List<String> getMergePrj(List<CostStatisticsInfo> infoList){
        return infoList.stream().map(info -> info.projectName).collect(Collectors.toList());
    }

    //得到需要合并的序号列
    private List<String> getSerial(List<CostStatisticsInfo> infoList){
        return infoList.stream().map(info -> String.valueOf(info.serialNumber)).collect(Collectors.toList());
    }

    //实体转导出模型list
    private List<Object> getList(CostStatisticsInfo info){
        List<Object> modelList = new ArrayList<>();
        modelList.add(String.valueOf(info.serialNumber));
        modelList.add(info.projectName);
        modelList.add(info.pointerTypeName == null ? "合计" : info.pointerTypeName);
        modelList.add(info.feasibleSum.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP));
        modelList.add(info.budgetEstimateSum.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP));
        modelList.add(info.financialAppraisalSum.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP));
        modelList.add(info.specialFunds.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP));
        modelList.add(info.contractAmount.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP));
        modelList.add(info.havePaidSum.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP));
        modelList.add(info.appliedAmount.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP));
        modelList.add(info.settlementAmount.divide(new BigDecimal(10000),2,BigDecimal.ROUND_HALF_UP));

        return modelList;
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
