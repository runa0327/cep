package com.pms.bid.job.mapper.processInstance;

import com.pms.bid.job.domain.processInstance.WfNodeInstance;

public interface WfNodeInstanceMapper {

    /**
     * 节点实例创建
     *
     * @param wfNodeInstance 节点实例实体
     */
    void create(WfNodeInstance wfNodeInstance);

    /**
     * 根据id修改数据
     * @param wfNodeInstance 节点实例实体
     */
    void updateById(WfNodeInstance wfNodeInstance);
}
