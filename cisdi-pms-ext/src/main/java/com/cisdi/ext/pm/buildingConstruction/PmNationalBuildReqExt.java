package com.cisdi.ext.pm.buildingConstruction;

import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.interaction.EntityRecord;

/**
 * 施工报建手续-国安报建-流程扩展
 */
public class PmNationalBuildReqExt {

    /**
     * 流程操作-国安报建-确认操作
     */
    public void nationalCheckOK(){
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        //当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status,nodeId,nodeInstanceId);
    }

    /**
     * 流程审批操作详细处理逻辑
     * @param nodeStatus 节点状态码名称
     * @param status 流程审批操作状态
     * @param nodeId 节点id
     * @param nodeInstanceId 节点实例id
     */
    public void processHandle(String nodeStatus, String status, String nodeId, String nodeInstanceId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //业务表名
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        if ("OK".equals(status)){
            if ("start".equals(nodeStatus)){ // 1-发起
                WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
            }
        }
    }

    /**
     * 流程审批节点状态名称赋值
     * @param status 状态码
     * @param nodeId 节点id
     * @return 节点状态码名称
     */
    public String getNodeStatus(String status, String nodeId) {
        String nodeName = "";
        if ("OK".equals(status)){
            if ("0099952822476346505".equals(nodeId)){ // 1-发起
                nodeName = "start";
            }
        }
        return nodeName;
    }
}
