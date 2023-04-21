package com.cisdi.ext.pm;

import com.cisdi.ext.proPlan.PmProPlanExt;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.Map;

/**
 * 节点延期申请-扩展
 */
public class PmExtensionRequestReqExt {

    /**
     * 流程-节点延期申请-结束时校验
     */
    public void extensionProEndCheck(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //流程id
        String processId = ExtJarHelper.procId.get();
        //业务表名
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        //项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        //业务记录id
        String csCommId = entityRecord.csCommId;
        //业主单位
        String companyId = JdbcMapUtil.getString(entityRecord.valueMap,"CUSTOMER_UNIT");
        //进度计划节点id
        String proNodeId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRO_PLAN_NODE_ID");
        //延期天数
        Integer day = JdbcMapUtil.getInt(entityRecord.valueMap,"DAYS_ONE");
        //更新进度计划完成时间
        PmProPlanExt.updatePlanEndDate(projectId,proNodeId,day,myJdbcTemplate);
        //刷新项目进度节点
//        PrjPlanUtil.updatePreNodeTime(proNodeId,projectId);
        //审批人员信息写入花名册(计划运营岗)
        ProcessCommon.addPrjPostUser(projectId,entCode,processId,companyId,csCommId,myJdbcTemplate);
    }

    /**
     * 流程操作-节点延期申请-确认操作
     */
    public void extensionCheckOK(){
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        //当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status,nodeId,nodeInstanceId);
    }

    /**
     * 流程操作-节点延期申请-拒绝操作
     */
    public void extensionCheckRefuse(){
        String status = "refuse";
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

        if ("OK".equals(status)){
            if ("start".equals(nodeStatus)){ // 1-发起
                WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
            } else {
                //获取审批意见信息
                Map<String,String> message = ProcessCommon.getCommentNew(nodeInstanceId,userId,myJdbcTemplate,procInstId,userName);
                //审批意见内容
                String file = message.get("file");
                String comment = message.get("comment");

                if ("leaderCheckOk".equals(nodeStatus)){ // 2-部门领导审批
                    //获取流程中的意见信息
                    String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"TEXT_REMARK_TWO");
                    //生成最终的意见信息
                    String commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                    ProcessCommon.commentShow("TEXT_REMARK_TWO",commentEnd,csCommId,entCode);
                } else if ("planRunCheckOK".equals(nodeStatus)){ // 3-计划运营审批
                    //获取流程中的意见信息
                    String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"TEXT_REMARK_THREE");
                    //生成最终的意见信息
                    String commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                    ProcessCommon.commentShow("TEXT_REMARK_THREE",commentEnd,csCommId,entCode);
                }
            }
        } else {
            if ("leaderCheckRefuse".equals(nodeStatus)){ // 2-部门领导审批
                ProcessCommon.clearData("TEXT_REMARK_TWO",csCommId,entCode,myJdbcTemplate);
            } else if ("planRunCheckRefuse".equals(nodeStatus)){ // 3-计划运营审批
                ProcessCommon.clearData("TEXT_REMARK_THREE",csCommId,entCode,myJdbcTemplate);
            }
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
        if ("OK".equals(status)){
            if ("1649227801083240448".equals(nodeId)){ // 1-发起
                nodeName = "start";
            } else if ("1649228232681316352".equals(nodeId)){ // 2-部门领导审批
                nodeName = "leaderCheckOk";
            } else if ("1649229030735732736".equals(nodeId)){ // 3-计划运营审批
                nodeName = "planRunCheckOK";
            }
        } else {
            if ("1649228232681316352".equals(nodeId)){ // 2-部门领导审批
                nodeName = "leaderCheckRefuse";
            } else if ("1649229030735732736".equals(nodeId)){ // 3-计划运营审批
                nodeName = "planRunCheckRefuse";
            }
        }
        return nodeName;
    }
}
