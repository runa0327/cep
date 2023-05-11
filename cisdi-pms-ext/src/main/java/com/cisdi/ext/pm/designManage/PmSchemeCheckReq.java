package com.cisdi.ext.pm.designManage;

import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.interaction.EntityRecord;

/**
 * 设计管理-方案审批管理-扩展
 */
public class PmSchemeCheckReq {

    /**
     * 流程操作-方案审批管理-确定按钮
     */
    public void schemeCheckProcessOK(){
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 流程操作-方案审批管理-拒绝按钮
     */
    public void schemeCheckProcessRefuse(){
        String status = "refuse";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 流程流转详细处理逻辑
     * @param nodeStatus 节点状态码
     * @param status 操作状态码
     */
    public void processHandle(String nodeStatus, String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String id = entityRecord.csCommId;
        String procInstId = ExtJarHelper.procInstId.get();
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        if ("OK".equals(status)){
            if ("start".equals(nodeStatus)){
                WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
            }
        }
    }

    /**
     * 各审批节点赋值
     * @param status 状态码
     * @param nodeId 节点id
     * @return 节点状态名称
     */
    private String getNodeStatus(String status, String nodeId) {
        String nodeName = "";
        if ("OK".equals(status)){
            if ("1643134468053262336".equals(nodeId)){ //1-发起
                nodeName = "start";
            }
        } else {

        }
        return nodeName;
    }
}
