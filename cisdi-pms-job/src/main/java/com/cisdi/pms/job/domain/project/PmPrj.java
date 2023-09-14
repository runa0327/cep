package com.cisdi.pms.job.domain.project;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PmPrj {

    //id
    private String id;
    private String projectId;

    // 项目名称
    private String projectName;

    //是否符合开工条件 1符合0不符合
    private Integer izStart;

    //是否竣工 1已竣工 0未竣工
    private Integer izEnd;

    // 建设单位/业主单位
    private String customerUnitId;

    // 资金来源
    private String investSourceId;

    // 总投资
    private BigDecimal projectTotalInvest;

    // 项目类型
    private String projectTypeId;

    // 启动日期
    private String startDate;

    // 招标模式
    private String tenderModeId;

    // 项目概况/项目简介
    private String projectSituation;

    // 启动说明
    private String startRemark;

    // 启动依据
    private String startFile;
}
