package com.cisdi.ext.base;

import com.cisdi.ext.model.WfFlow;
import com.qygly.ext.jar.helper.sql.Where;

/**
 * 流转-扩展
 */
public class WfFlowExt {

    /**
     * 通过流转id查询下一节点id
     * @param flowId 流转id
     * @return 下一节点id
     */
    public static String getNodeIdByFlowId(String flowId) {
        String nextNodeId = WfFlow.selectByWhere(new Where().eq("ID",flowId)).get(0).getToNodeId();
        return nextNodeId;
    }
}
