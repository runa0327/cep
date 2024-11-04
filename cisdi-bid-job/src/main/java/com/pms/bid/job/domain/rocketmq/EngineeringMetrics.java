package com.pms.bid.job.domain.rocketmq;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 工程量值实体
 */
@Data
public class EngineeringMetrics {

    /**
     * 合同编号
     */
    private String contractNum;

    /**
     * 施工图名称
     */
    private String drawingName;

    /**
     * 施工图号
     */
    private String drawingNum;

    /**
     * 施工单位
     */
    private String organization;

    /**
     * 上部结构砼（m³）
     */
    private BigDecimal superstructureConcrete;

    /**
     * 基础砼（m³）
     */
    private BigDecimal infrastructure;

    /**
     * 桩（m³）
     */
    private BigDecimal pile;

    /**
     * 管道（t）
     */
    private BigDecimal pipeline;

    /**
     * 桥架（t）
     */
    private BigDecimal qj;

    /**
     * 钢结构（t）
     */
    private BigDecimal steelwork;

    /**
     * 电缆（km）
     */
    private BigDecimal cable;

    /**
     * 设备（t）
     */
    private  BigDecimal device;

    /**
     * 单元工程code
     */
    private String unitProjectCode;

    /**
     * 单元工程名称
     */
    private String unitProjectName;
}
