package com.pms.bid.job.domain.processInstance;

import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;

@Data
public class WfNodeInstance extends BaseDomain {

    /**
     * id
     */
    private String id;

    /**
     * 节点实例id
     */
    private String wfNodeInstanceId;

    /**
     * 节点实例名称
     */
    private String wfNodeInstanceName;

    /**
     * 节点id/当前节点id
     */
    private String wfNodeId;

    /**
     * 节点名称/当前节点名称
     */
    private String wfNodeName;

    /**
     * 流程id
     */
    private String wfProcessId;

    /**
     * 流程实例id
     */
    private String wfProcessInstanceId;

    /**
     * 开始日期
     */
    private String startDateTime;

    /**
     * 结束日期
     */
    private String endDateTime;

    /**
     * 生效操作
     */
    private String adActId;

    /**
     * 处于本轮
     */
    private Integer isCurrentRound;

    /**
     * 是否当前
     */
    private Integer isCurrent;

    /**
     * 序号
     */
    private String seqNo;
}
