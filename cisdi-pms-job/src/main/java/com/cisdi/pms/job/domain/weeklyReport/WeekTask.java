package com.cisdi.pms.job.domain.weeklyReport;

import com.cisdi.pms.job.domain.base.BaseCommon;
import lombok.Data;

@Data
public class WeekTask extends BaseCommon {

    // id
    private String id;

    // 通知用户id
    private String adUserId;

    // 通知内容
    private String content;

    // 通知标题
    private String title;

    // 推送时间
    private String publishStart;

    // 周任务状态
    private String weekTaskStatus;

    // 来源id
    private String relationDataId;

    // 周任务类型id
    private String weekTaskTypeId;

    // 能否转办 1-是 0-否
    private Integer canDispatch;

    // 转办人
    private String transferUserId;

    // 转办时间
    private String transferDate;

    // 项目id
    private String projectId;

    // 不涉及说明
    private String reasonExplain;

    // 计划完成时间
    private String planCompeteDate;

    // 实际完成时间
    private String actualCompeteDate;
}
