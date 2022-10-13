package com.cisdi.ext.nt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProjectStatisticsManualFeeResp
 * @package com.cisdi.ext.nt.model
 * @description
 * @date 2022/10/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProjectStatisticsManualFeeResp {
    /** 唯一业务标识 */
    private String id;

    /** 项目ID */
    private String projectId;

    /** 年份 */
    private Integer year;

    /** 月份 */
    private Integer month;

    /** 建筑工程（万元） */
    private BigDecimal architecturalEngineeringFee;

    /** 安装工程（万元） */
    private BigDecimal installationEngineeringFee;

    /** 设备工器具购置（万元） */
    private BigDecimal equipmentPurchaseFee;

    /** 其他费用（万元） */
    private BigDecimal otherFee;

    /** 本年完成投资（万元） */
    private BigDecimal yearTotalFee;

    /**
     * 其中：本月完成投资
     */
    private BigDecimal thisMonthInvestment;

    /**
     * 其中：住宅
     */
    private BigDecimal residential;

    /**
     * 其中：购置旧设备
     */
    private BigDecimal purchaseOldEquipment;

    /**
     * 其中：旧建筑购置费
     */
    private BigDecimal purchaseOldBuilding;

    /**
     * 建设用地费
     */
    private BigDecimal constructionLandCharge;
}
