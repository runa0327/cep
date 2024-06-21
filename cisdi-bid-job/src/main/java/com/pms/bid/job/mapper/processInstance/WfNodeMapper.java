package com.pms.bid.job.mapper.processInstance;

import com.pms.bid.job.domain.processInstance.WfNode;

public interface WfNodeMapper {

    /**
     * 获取流程发起节点信息
     * @param wfProcessId 流程id
     * @return 节点信息
     */
    WfNode queryFirstNode(String wfProcessId);

    /**
     * 根据节点id查询节点信息
     * @param nodeId 节点id
     * @return 节点信息
     */
    WfNode queryNodeById(String nodeId);
}
