package com.cisdi.pms.job.excel.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 初设概算 实体类
 */
@Data
public class PmPrjInvest2Model {
    private String id;
    private String VER;
    private String PM_PRJ_ID;
    private String REPLY_NO_WR;
    private String status;
    //总投资
    private BigDecimal PRJ_TOTAL_INVEST;
    //工程费用
    private BigDecimal PROJECT_AMT;
    //建安费
    private BigDecimal CONSTRUCT_AMT;
    //设备费
    private BigDecimal EQUIP_AMT;
    //工程其他费用
    private BigDecimal PROJECT_OTHER_AMT;
    //土地拆迁费
    private BigDecimal LAND_AMT;
    //预备费
    private BigDecimal PREPARE_AMT;
    //建设期利息
    private BigDecimal CONSTRUCT_PERIOD_INTEREST;
}
