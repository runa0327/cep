package com.cisdi.pms.job.excel.model.request;

import lombok.Data;

/**
 * @author dlt
 * @date 2022/11/28 周一
 */
@Data
public class PrjRequest {

    //用户id
    private String userId;
    //项目名
    private String name;
    //业主
    private String customerUnitId;
    //项目类型
    private String projectTypeId;
    //项目状态
    private String projectPhaseId;
    //进度阶段
    private String transitionPhaseId;
    //计划开工
    private String startDate;
    private String endDate;
    //计划开工年份
    private String year;

    private String category;
}
