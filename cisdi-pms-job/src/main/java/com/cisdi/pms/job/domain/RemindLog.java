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

    //通知类型
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

}
