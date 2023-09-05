package com.cisdi.pms.job.domain.weeklyReport;

import com.cisdi.pms.job.domain.base.BaseCommon;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 工程建安费用需求填报模块实体
 */
@Data
public class PmConstruction extends BaseCommon {

    // id
    private String id;

    // 工程建安费用需求父级id
    private String pmConstructionId;

    // 年-id
    private String baseYearId;

    // 年-名称
    private String year;

    // 项目id
    private String projectId;

    // 项目名称
    private String projectName;

    // 月份
    private int month;

    ///是否符合开工条件 1符合0不合格
    private Integer weatherStart;

    //是否竣工 1已竣工0未竣工
    private Integer weatherComplete;

    // 是否填写年度需求资金
    private Integer yearAmtNeed;

    // 初始填报金额
    private BigDecimal firstAmt;

    // 确认金额
    private BigDecimal checkAmt;

    // 月初是否确认 1已确认 0未确认
    private Integer monthCheck;

    // 月初任务是否已推送工作台 1已推送 0未推送
    private Integer pushWeekTask;

    // 年度需求总资金
}
