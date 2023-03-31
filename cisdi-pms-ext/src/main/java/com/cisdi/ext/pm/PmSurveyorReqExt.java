package com.cisdi.ext.pm;

import com.cisdi.ext.base.PmPrjExt;
import com.cisdi.ext.model.PmPrj;
import com.cisdi.ext.util.WfPmInvestUtil;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;

/**
 * 勘察阶段-扩展
 */
public class PmSurveyorReqExt {

    /**
     * 流程操作-勘察阶段-确定按钮
     */
    public void surveyorProcessOK(){
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 流程操作-勘察阶段-拒绝按钮
     */
    public void surveyorProcessRefuse(){
        String status = "refuse";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    private void processHandle(String nodeStatus, String status) {
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
            if ("1641697251351023616".equals(nodeId)){ //1-发起
                nodeName = "start";
            }
        } else {

        }
        return nodeName;
    }

    /**
     * 流程-勘察阶段-结束时扩展
     */
    public void surveyorEndCheck(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //流程表名
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        //获取勘察单位名称
        String surveyorName = JdbcMapUtil.getString(entityRecord.valueMap,"SURVEY_UNIT_NAME");
        //更新写入勘察信息
        String partyId = PmInLibraryExt.createOrUpdateParty(surveyorName,"IS_SURVEYOR");
        //更新项目勘察信息
        PmPrj pmPrj = appointValue(projectId,partyId);
        PmPrjExt.updateData(pmPrj);
    }

    /**
     * 项目实体赋值
     * @param projectId 项目id
     * @param partyId 合作方id
     * @return 项目实体
     */
    private PmPrj appointValue(String projectId, String partyId) {
        PmPrj pmPrj = new PmPrj();
        pmPrj.setSurveyorUnit(partyId);
        pmPrj.setId(projectId);
        return pmPrj;
    }
}
