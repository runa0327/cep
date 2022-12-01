package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author dlt
 * @date 2022/11/30 周三
 */
@Data
public class InvestStatisticModel {

    /**
     * 序号
     */
    @ExcelProperty("序号")
    public String serialNumber;

    /**
     * 项目名称
     */
    @ExcelProperty("项目名称")
    public String projectName;

    /**
     * 指标类型名称
     */
    @ExcelProperty("指标类型")
    public String pointerTypeName;

    /**
     * 可研批复金额（万元）
     */
    @ExcelProperty("可研批复金额（万元）")
    public String feasibleSum;

    /**
     * 概算批复金额（万元）
     */
    @ExcelProperty("概算批复金额（万元）")
    public String budgetEstimateSum;

    /**
     * 财评批复金额（万元）
     */
    @ExcelProperty("财评批复金额（万元）")
    public String financialAppraisalSum;

    /**
     * 政府专项资金（万元）
     */
    @ExcelProperty("政府专项资金（万元）")
    public BigDecimal specialFunds = BigDecimal.ZERO;

    /**
     * 已签合同金额（万元） ：指标关联所有合同变更后金额之和
     */
    @ExcelProperty("已签合同金额（万元）")
    public BigDecimal contractAmount = BigDecimal.ZERO;

    /**
     * 累计已支付（万元）：截至本期已支付总额
     */
    @ExcelProperty("累计已支付（万元）")
    public BigDecimal havePaidSum = BigDecimal.ZERO;

    /**
     * 专项资金已申请金额（万元） ：本期计量金额之和
     */
    @ExcelProperty("专项资金已申请金额（万元）")
    public BigDecimal appliedAmount = BigDecimal.ZERO;

    /**
     * 结算金额（万元）
     */
    @ExcelProperty("结算金额（万元）")
    public BigDecimal settlementAmount = BigDecimal.ZERO;
}
