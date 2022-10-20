package com.cisdi.data.domain;

import lombok.Data;

/**
 * @author hgh
 * @date 2022/10/18
 * @apiNote
 */
@Data
public class RemindLog {

    //任务id
    private String taskId;

    //通知类型
    private String taskType;

    //用户手机号
    private String userPhone;

    //用户名称
    private String userName;

}
