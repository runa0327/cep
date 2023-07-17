package com.cisdi.ext.pm.office;

import com.cisdi.ext.pm.processCommon.ProcessCommon;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.Map;

/**
 * 综合办公-项目问题-扩展
 */
public class PmProjectProblemReqExt {

    /**
     * 流程操作-项目问题-确认操作
     */
    public void projectProblemCheckOK() {
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        //当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String nodeStatus = getNodeStatus(status, nodeId);
        processHandle(nodeStatus, status, nodeId, nodeInstanceId);
    }

    /**
     * 流程操作-项目问题-拒绝操作
     */
    public void projectProblemRefuse() {
        String status = "refuse";
        String nodeId = ExtJarHelper.nodeId.get();
        //当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String nodeStatus = getNodeStatus(status, nodeId);
        processHandle(nodeStatus, status, nodeId, nodeInstanceId);
    }

    /**
     * 流程审批操作详细处理逻辑
     * @param nodeStatus     节点状态码名称
     * @param status         流程审批操作状态
     * @param nodeId         节点id
     * @param nodeInstanceId 节点实例id
     */
    private void processHandle(String nodeStatus, String status, String nodeId, String nodeInstanceId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //业务表名
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        String csCommId = entityRecord.csCommId;
        // 流程id
        String procInstId = ExtJarHelper.procInstId.get();
        // 当前登录人
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;

        if ("OK".equals(status)) {
            if ("start".equals(nodeStatus)) { // 1-发起
                WfExt.createProcessTitle(entCode, entityRecord, myJdbcTemplate);
            } else {
                //获取审批意见信息
                Map<String, String> message = ProcessCommon.getCommentNew(nodeInstanceId, userId, myJdbcTemplate, procInstId, userName);
                //审批意见内容
                String comment = message.get("comment");
                String file = message.get("file");

                //获取流程中的意见信息
                String processComment = JdbcMapUtil.getString(entityRecord.valueMap, "TEXT_REMARK_FOUR");
                String processFile = JdbcMapUtil.getString(entityRecord.valueMap, "FILE_ID_TWO");
                //生成最终的意见信息、附件信息
                String commentEnd = ProcessCommon.getNewCommentStr(userName, processComment, comment);
                String fileEnd = ProcessCommon.getEndCommentFile(userId,processFile,file,myJdbcTemplate,"one");
                ProcessCommon.commentShow("TEXT_REMARK_FOUR", commentEnd, csCommId, entCode);
                ProcessCommon.commentShow("FILE_ID_TWO", fileEnd, csCommId, entCode);
            }
        } else {
            ProcessCommon.clearData("TEXT_REMARK_FOUR,FILE_ID_TWO", csCommId, entCode, myJdbcTemplate);
        }
    }

    /**
     * 流程审批节点状态名称赋值
     * @param status 状态码
     * @param nodeId 节点id
     * @return 节点状态码名称
     */
    private String getNodeStatus(String status, String nodeId) {
        String nodeName = "";
        if ("OK".equals(status)) {
            if ("1679779553054162944".equals(nodeId)) { // 1-发起
                nodeName = "start";
            } else if ("1679781184512589824".equals(nodeId)) { // 2-处理人处理
                nodeName = "usersCheckOK";
            } else if ("1679780225422065664".equals(nodeId)) { // 3-部门领导协助
                nodeName = "deptLeaderCheckOK";
            } else if ("1679781304985583616".equals(nodeId)){ // 4-部门指派人处理
                nodeName = "deptLeaderToUserOK";
            }
        } else {
            if ("1679781184512589824".equals(nodeId)) { // 2-处理人处理
                nodeName = "usersRefuse";
            } else if ("1679780225422065664".equals(nodeId)) { // 3-部门领导协助
                nodeName = "deptLeaderRefuse";
            } else if ("1679781304985583616".equals(nodeId)){ // 4-部门指派人处理
                nodeName = "deptLeaderToUserRefuse";
            }
        }
        return nodeName;
    }
}
