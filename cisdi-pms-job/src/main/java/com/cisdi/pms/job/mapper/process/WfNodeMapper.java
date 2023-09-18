package com.cisdi.pms.job.mapper.process;

import com.cisdi.pms.job.domain.process.WfNode;

import java.util.List;

public interface WfNodeMapper {

    /**
     * 查询流程发起节点信息
     * @param wfProcessId 流程id
     * @return 发起节点信息
     */
    WfNode queryStartNodeByProcess(String wfProcessId);

    /**
     * 根据节点id查询节点信息
     * @param nodeId 节点id
     * @return 节点信息
     */
    WfNode queryNodeMsgByNodeId(String nodeId);
}
