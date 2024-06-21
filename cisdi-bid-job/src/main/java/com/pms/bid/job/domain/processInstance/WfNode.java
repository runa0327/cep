package com.pms.bid.job.domain.processInstance;

import lombok.Data;

@Data
public class WfNode {

    /**
     * id
     */
    private String id;

    /**
     * 节点名称
     */
    private String wfNodeName;

    /**
     * 视图
     */
    private String viewId;

    /**
     * 操作
     */
    private String adActId;

    /**
     * 到节点
     */
    private String toNodeId;

    /**
     * 流程id
     */
    private String wfProcessId;

    /**
     * 序号
     */
    private Integer seqNo;
}
