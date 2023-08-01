package com.cisdi.ext.pm.designManage;

import com.cisdi.ext.pm.processCommon.ProcessCommon;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.Map;

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
        String csCommId = entityRecord.csCommId;
        String procInstId = ExtJarHelper.procInstId.get();
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        if ("OK".equals(status)){
            if ("start".equals(nodeStatus)){ // 1-发起
                WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
            } else {
                //获取审批意见信息
                Map<String,String> message = ProcessCommon.getCommentNew(nodeInstanceId,userId,myJdbcTemplate,procInstId);
                //审批意见内容
                String comment = message.get("comment");
                String processComment = "", commentEnd = "";
                if ("deptLeaderCheckOK".equals(nodeStatus)){ // 2-部门负责人审批
                    //获取流程中的意见信息
                    processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_ONE");
                    commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                    ProcessCommon.commentShow("APPROVAL_COMMENT_ONE",commentEnd,csCommId,entCode);
                }
            }
        } else {
            if ("deptLeaderCheckRefuse".equals(nodeStatus)){ // 2-部门负责人审批
                ProcessCommon.clearData("APPROVAL_COMMENT_ONE",csCommId,entCode,myJdbcTemplate);
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
            if ("1643134468053262336".equals(nodeId)){ // 1-发起
                nodeName = "start";
            } else if ("1656856307451510784".equals(nodeId)){ // 2-部门负责人审批
                nodeName = "deptLeaderCheckOK";
            }
        } else {
            if ("1656856307451510784".equals(nodeId)){ // 2-部门负责人审批
                nodeName = "deptLeaderCheckRefuse";
            }
        }
        return nodeName;
    }
}
