package com.cisdi.pms.job.domain.process;

import lombok.Data;

/**
 * 节点信息
 */
@Data
public class WfNode {

    // id
    private String id;
    private String WfNodeId;

    // 名称
    private String name;
    private String wfNodeName;

    // 节点类型
    private String nodeType;

    // 视图id
    private String viewId;

    // 流转id
    private String wfFlowId;

    // 从节点
    private String fromNodeId;

    // 至节点
    private String toNodeId;

    // 操作
    private String actId;

    // 有效逻辑
    private String validLogic;

    // 角色
    private String adRoleId;
}
