package com.cisdi.ext.model.view.process;

import com.cisdi.ext.model.BasePageEntity;

public class PmProjectProblemReqView extends BasePageEntity {

    // id
    private String id;

    // 问题描述
    private String problemDescribe;

    // 解决方案
    private String solvePlan;

    // 项目名称
    private String projectName;

    // 项目id
    private String projectId;

    // 项目推进问题类型id
    private String projectPushProblemTypeId;

    // 项目推进问题类型名称
    private String projectPushProblemTypeName;

    // 问题状态id
    private String statusId;

    // 问题状态名称
    private String statusName;

    // 发起人id
    private String userId;

    // 发起人名称
    private String userName;

    // 发起时间
    private String startTime;
    private String startTimeMin;
    private String startTimeMax;

    // 流程实例id
    private String wfProcessInstanceId;

    // 视图id
    private String viewId;
}
