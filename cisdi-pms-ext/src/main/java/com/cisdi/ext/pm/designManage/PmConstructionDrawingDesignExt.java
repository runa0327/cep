package com.cisdi.ext.pm.designManage;

import com.cisdi.ext.base.PmPrjExt;
import com.cisdi.ext.model.base.PmPrj;
import com.cisdi.ext.pm.PmInLibraryExt;
import com.cisdi.ext.pm.processCommon.ProcessCommon;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.Map;

/**
 * 设计管理-施工图设计管理-扩展
 */
public class PmConstructionDrawingDesignExt {

    /**
     * 流程-施工图设计管理-结束时校验
     */
    public void designAssignmentEndCheck(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        //流程表名
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        //流程业务表id
        String csCommId = entityRecord.csCommId;
        //获取设计单位名称
        String designUnitOne = JdbcMapUtil.getString(entityRecord.valueMap,"DESIGN_UNIT_ONE");
        //更新写入勘察信息
        String partyId = PmInLibraryExt.createOrUpdateParty(designUnitOne,"IS_DESIGNER");
        //更新项目设计单位信息
        PmPrj pmPrj = appointValue(projectId,partyId);
        PmPrjExt.updateData(pmPrj);
        //审批人员信息写入花名册
        String processId = ExtJarHelper.procId.get();
        String companyId = JdbcMapUtil.getString(entityRecord.valueMap,"CUSTOMER_UNIT");
        ProcessCommon.addPrjPostUser(projectId,entCode,processId,companyId,csCommId,myJdbcTemplate);
    }

    /**
     * 项目实体赋值
     * @param projectId 项目id
     * @param partyId 合作方类型id
     * @return 项目实体
     */
    public PmPrj appointValue(String projectId, String partyId) {
        PmPrj pmPrj = new PmPrj();
        pmPrj.setDesignerUnit(partyId);
        pmPrj.setId(projectId);
        return pmPrj;
    }

    /**
     * 流程操作-施工图设计管理-确定按钮
     */
    public void transfiniteProcessOK(){
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 流程操作-施工图设计管理-拒绝按钮
     */
    public void transfiniteProcessRefuse(){
        String status = "refuse";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
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
            } else {

                //获取审批意见信息
                Map<String,String> message = ProcessCommon.getCommentNew(nodeInstanceId,userId,myJdbcTemplate,procInstId);
                //审批意见内容
                String comment = message.get("comment");
                String processComment, commentEnd;

                if ("leaderFirstOK".equals(nodeStatus)){ // 2-部门负责人意见 意见回显
                    //获取流程中的意见信息
                    processComment = JdbcMapUtil.getString(entityRecord.valueMap,"TEXT_REMARK_FOUR");
                    commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                    ProcessCommon.commentShow("TEXT_REMARK_FOUR",commentEnd,csCommId,entCode);
                } else if ("leaderSecondOK".equals(nodeStatus)){ // 4-部门负责人意见
                    processComment = JdbcMapUtil.getString(entityRecord.valueMap,"TEXT_REMARK_FIVE");
                    commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                    ProcessCommon.commentShow("TEXT_REMARK_FIVE",commentEnd,csCommId,entCode);
                }
            }
        } else {
            if ("leaderFirstRefuse".equals(nodeStatus)){
                ProcessCommon.clearData("TEXT_REMARK_FOUR",csCommId,entCode,myJdbcTemplate);
            } else if ("leaderSecondRefuse".equals(nodeStatus)){
                ProcessCommon.clearData("TEXT_REMARK_FIVE",csCommId,entCode,myJdbcTemplate);
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
            if ("0099902212142090122".equals(nodeId)){ // 1-设计部发起
                nodeName = "start";
            } else if ("0099902212142090123".equals(nodeId)){ // 3-经办人上传外审意见
                nodeName = "externalCheckOK";
            } else if ("0099902212142090124".equals(nodeId)){ // 5-经办人上传批复
                nodeName = "replyOK";
            } else if ("1676047023150948352".equals(nodeId)){ // 2-部门负责人意见
                nodeName = "leaderFirstOK";
            } else if ("1676047214541234176".equals(nodeId)){ // 4-部门负责人意见
                nodeName = "leaderSecondOK";
            }
        } else {
            if ("0099902212142090123".equals(nodeId)){ // 3-经办人上传外审意见
                nodeName = "externalCheckRefuse";
            } else if ("0099902212142090124".equals(nodeId)){ // 5-经办人上传批复
                nodeName = "replyRefuse";
            } else if ("1676047023150948352".equals(nodeId)){ // 2-部门负责人意见
                nodeName = "leaderFirstRefuse";
            } else if ("1676047214541234176".equals(nodeId)){ // 4-部门负责人意见
                nodeName = "leaderSecondRefuse";
            }
        }
        return nodeName;
    }
}
