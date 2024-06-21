package com.pms.bid.job.domain.processInstance;

import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;

@Data
public class WfTask extends BaseDomain {

    /**
     * id
     */
    private String id;

    /**
     * 流程id
     */
    private String wfProcessId;

    /**
     * 流程实例id
     */
    private String wfProcessInstanceId;

    /**
     * 节点id
     */
    private String wfNodeId;

    /**
     * 节点实例id
     */
    private String wfNodeInstanceId;

    /**
     * 用户id
     */
    private String adUserId;

    /**
     * 接收日期时间
     */
    private String receiveDateTime;

    /**
     * 查看日期
     */
    private String viewDateTime;

    /**
     * 操作日期
     */
    private String actDateTime;

    /**
     * 操作
     */
    private String adActId;

    /**
     * 是否关闭
     */
    private Integer isClosed;

    /**
     * 任务类型
     */
    private String wfTaskTypeId;

    /**
     * 处于本轮
     */
    private Integer isCurrentRound;

    /**
     * 序号
     */
    private String seqNo;
}
