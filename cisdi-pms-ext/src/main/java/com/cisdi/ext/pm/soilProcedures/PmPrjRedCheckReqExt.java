package com.cisdi.ext.pm.soilProcedures;

import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.interaction.EntityRecord;

/**
 * 项目红线核查-流程扩展
 */
public class PmPrjRedCheckReqExt {

    /**
     * 流程操作-项目红线核查-确认操作
     */
    public void prjRedCheckOK(){
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        //当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status,nodeId,nodeInstanceId);
    }

    /**
     * 流程操作-项目红线核查-拒绝操作
     */
    public void prjRedCheckRefuse(){
        String status = "false";
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
            if ("1650055345688797184".equals(nodeId)){ // 1-发起
                nodeName = "start";
            } else if ("1650055345730740224".equals(nodeId)){ // 2-土地管理岗核查反馈
                nodeName = "soilManCheckOK";
            }
        } else {
            if ("1650055345730740224".equals(nodeId)){ // 2-土地管理岗核查反馈
                nodeName = "soilManCheckRefuse";
            }
        }
        return nodeName;
    }
}
