package com.cisdi.ext.pm.designManage;

import com.cisdi.ext.pm.processCommon.ProcessCommon;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;


/**
 * 超限审查-流程扩展
 */
public class PmTransfiniteCheckReqExt {

    /**
     * 流程操作-超限审查-确定按钮
     */
    public void transfiniteProcessOK(){
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 流程操作-超限审查-拒绝按钮
     */
    public void transfiniteProcessRefuse(){
        String status = "refuse";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 超限审查-流程完结时操作
     */
    public void transfiniteProcEnd(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID"); //项目id
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code; //流程表名
        String csCommId = entityRecord.csCommId; //流程业务表id
        //审批人员信息写入花名册
        String processId = ExtJarHelper.procId.get();
        ProcessCommon.addPrjPostUser(projectId,entCode,processId,null,csCommId,myJdbcTemplate);
    }

    /**
     * 流程按钮详情处理逻辑
     * @param nodeStatus 节点状态码
     * @param status 流转状态码
     */
    private void processHandle(String nodeStatus, String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        String procInstId = ExtJarHelper.procInstId.get();
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        if ("OK".equals(status)){
            if ("start".equals(nodeStatus)){ //标题生成
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
            if ("1644146747553079296".equals(nodeId)){ //1-发起
                nodeName = "start";
            } else if ("1644146967909228544".equals(nodeId)){ // 2-前期报建评审
                nodeName = "earlyCheckOK";
            } else if ("1644147342888394752".equals(nodeId)){ // 4-前期报建岗超限批复
                nodeName = "earlyReviewCheckOK";
            }
        } else {
            if ("1644146967909228544".equals(nodeId)){ // 2-前期报建评审
                nodeName = "earlyCheckRefuse";
            } else if ("1644147342888394752".equals(nodeId)){ // 4-前期报建岗超限批复
                nodeName = "earlyReviewCheckRefuse";
            }
        }
        return nodeName;
    }
}
