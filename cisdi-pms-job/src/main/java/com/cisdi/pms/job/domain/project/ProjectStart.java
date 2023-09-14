package com.cisdi.pms.job.domain.project;

import com.cisdi.pms.job.domain.base.BaseCommon;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 项目启动
 */
@Data
public class ProjectStart extends BaseCommon {

    // id
    private String id;

    // 项目id
    private String projectId;

    // 名称
    private String name;
    private String projectName;

    // 项目概况/项目简介
    private String projectSituation;

    // 启动依据
    private String startFile;

    // 投资总额/总投资
    private BigDecimal projectTotalInvest;

    // 项目类型
    private String projectTypeId;

    // 建设单位/业主单位
    private String customerUnitId;

    // 启动时间/启动日期
    private String startDate;

    // 经办人
    private String agent;

    // 项目状态 (已暂停、进行中、已完成等)
    private String projectStatus;

    // 项目编号
    private String projectCode;

    // 投资来源/资金来源
    private String investSourceId;

    // 启动说明
    private String startRemark;

    // 招标模式
    private String tenderModeId;

    // 前期报建岗人员
    private String earlyConstructionPostUserId;

    // 计划开工时间
    private String planStartDate;

    // 建设地点
    private String baseLocationId;

    // 计划完工时间
    private String planEndDate;
}
