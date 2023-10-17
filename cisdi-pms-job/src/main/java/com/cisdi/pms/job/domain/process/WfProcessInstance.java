package com.cisdi.pms.job.domain.process;

import com.cisdi.pms.job.domain.base.BaseCommon;
import lombok.Data;

@Data
public class WfProcessInstance extends BaseCommon {

    // id
    private String id;

    // 流程实例id
    private String wfProcessInstanceId;

    // 流程实例名称
    private String wfProcessInstanceName;

    // 发起人
    private String startUserId;
    private String startUserName;

    // 发起时间
    private String startDate;
    private String startDateMin;
    private String startDateMax;

    // 结束时间
    private String endDate;
    private String endDateMin;
    private String endDateMax;

    // 流程id
    private String wfProcessId;

    // 流程名称
    private String processName;

    // 实体id
    private String adEntId;

    // 实体编码
    private String adEntCode;

    // 业务记录id
    private String entityRecordId;

    // 是否紧急 1紧急 0不紧急
    private Integer urgent;

    // 节点实例id
    private String wfNodeInstanceId;

    // 节点实例名称
    private String wfNodeInstanceName;

    // 节点id/当前节点id
    private String wfNodeId;

    // 节点名称/当前节点名称
    private String wfNodeName;

    // 当前代办用户
    private String currentTodoUserIds;
    private String currentToDoUserNames;

    // 当前代办视图
    private String currentViewId;

    // 操作
    private String actId;

    // 流转id
    private String wfFlowId;

    // 序号
    private String seqNo;

    // 是否本轮 1是 0否
    private Integer isCurrentRound;

    // 是否当前 1是 0否
    private Integer isCurrent;

    // 任务id
    private String taskId;

    // 任务用户id
    private String adUserId;

    // 任务用户名称
    private String adUserName;

    // 接收时间
    private String receiveDate;

    // 操作时间
    private String actDate;

    // 查看时间
    private String viewDate;

    // 是否关闭 1已关闭 0未关闭
    private Integer isClosed;

    // 任务类型
    private String wfTaskTypeId;

    // 是否第一个任务 1是 0否
    private Integer isFirstTask;

    // 数量
    private Integer instanceNums;

    // 部门
    private String deptId;
    private String deptName;

    // 审批人
    private String checkUserId;
    private String checkUserName;
}
