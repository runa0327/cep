package com.cisdi.pms.job.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author hgh
 * @date 2022/10/18
 * @apiNote
 */
@Data
@Accessors(chain = true)
public class RemindLog {

    //任务id
    private String taskId;

    //任务名称
    private String taskName;

    //通知类型 NOTI-通知 TODO-代办
    private String taskType;

    //用户id
    private String userId;

    //用户手机号
    private String userPhone;

    //用户名称
    private String userName;

    //最后修改时间
    private String lastUpdateTime;

    //任务数量
    private String count;

    //备注  日志
    private String remark;

    // id
    public String id;

    // 用户编码/电话
    private String userCode;

    // 流程实例名称
    private String wfProcessInstanceName;

    // 流程实例id
    private String wfProcessInstanceId;

    // 流程代办对应业务表记录id
    private String entityRecordId;

    // 流程名称
    private String processName;

    // 流程id
    private String processId;

    // 当前实体视图id
    private String viewId;

}
