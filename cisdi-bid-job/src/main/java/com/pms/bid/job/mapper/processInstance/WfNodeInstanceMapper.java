package com.pms.bid.job.mapper.processInstance;

import com.pms.bid.job.domain.processInstance.WfNodeInstance;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 根据流程实例ia获取流程节点实例id
     * @param processInstanceId
     */
    String  queryNodeInstanceId(@Param("processInstanceId") String processInstanceId);
}
