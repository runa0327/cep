package com.pms.bid.job.domain.process;

import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;

@Data
public class ConstructionPlan extends BaseDomain {

    /**
     * id
     */
    private String id;

    /**
     * 单元工程id
     */
    private String prjStructNodeId;

    /**
     * 单元工程名称
     */
    private String prjStructNodeName;

    /**
     * 方案名称
     */
    private String planName;

    /**
     * 责任单位
     */
    private String responsibleUnit;

    /**
     * 计划完成日期
     */
    private String planCompleteDate;

    /**
     * 提前预警天数
     */
    private String adVanceWarningDays;

    /**
     * 责任人
     */
    private String adUserId;

    /**
     * 是否需要专家审核
     */
    private Integer isCheck;

    /**
     * 备注
     */
    private String planRemark;

    /**
     * 时间差天数
     */
    private Integer diffDay;

    /**
     * 流程实例id
     */
    private String wfProcessInstanceId;
}
