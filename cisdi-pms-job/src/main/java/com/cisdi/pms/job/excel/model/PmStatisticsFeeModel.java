package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmStatisticsFeeModel
 * @package com.cisdi.pms.job.excel.model
 * @description 纳通填报
 * @date 2023/2/27
 */
@Data
public class PmStatisticsFeeModel {

    @ExcelProperty("项目")
    private String projectName;

    @ExcelProperty("年份")
    private Integer year;

    @ExcelProperty("月份")
    private Integer month;

    @ExcelProperty("本年完成投资(万元)")
    private BigDecimal thisYearInvestment;

    @ExcelProperty("本月完成投资(万元)")
    private BigDecimal thisMonthInvestment;

    @ExcelProperty("其中：住宅(万元)")
    private BigDecimal residential;

    @ExcelProperty("建设工程费(万元)")
    private BigDecimal architecturalEngineeringFee;

    @ExcelProperty("安装工程费(万元)")
    private BigDecimal installationEngineeringFee;

    @ExcelProperty("设备工器具购置费(万元)")
    private BigDecimal equipmentPurchaseFee;

    @ExcelProperty("其中：购置旧设备费(万元)")
    private BigDecimal purchaseOldEquipment;

    @ExcelProperty("其他费用(万元)")
    private BigDecimal otherFee;

    @ExcelProperty("其中：旧建筑物购置费(万元)")
    private BigDecimal purchaseOldBuilding;

    @ExcelProperty("其中：建设用地费(万元)")
    private BigDecimal constructionLandCharge;
}
