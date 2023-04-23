package com.cisdi.ext.pm;

import com.cisdi.ext.base.PmPrjExt;
import com.cisdi.ext.model.PmPrj;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.Map;

/**
 * 施工图设计管理-扩展
 */
public class PmConstructionDrawingDesignExt {

    /**
     * 流程-施工图设计管理-结束时校验
     */
    public void designAssignmentEndCheck(){
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        //获取设计单位名称
        String designUnitOne = JdbcMapUtil.getString(entityRecord.valueMap,"DESIGN_UNIT_ONE");
        //更新写入勘察信息
        String partyId = PmInLibraryExt.createOrUpdateParty(designUnitOne,"IS_DESIGNER");
        //更新项目设计单位信息
        PmPrj pmPrj = appointValue(projectId,partyId);
        PmPrjExt.updateData(pmPrj);
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
            if ("0099902212142090122".equals(nodeId)){ //1-发起
                nodeName = "start";
            } else if ("0099902212142090123".equals(nodeId)){ // 2-外审
                nodeName = "externalCheckOK";
            } else if ("0099902212142090124".equals(nodeId)){ // 3-批复
                nodeName = "replyOK";
            }
        } else {
            if ("0099902212142090123".equals(nodeId)){ // 2-外审
                nodeName = "externalCheckRefuse";
            } else if ("0099902212142090124".equals(nodeId)){ // 3-批复
                nodeName = "replyRefuse";
            }
        }
        return nodeName;
    }
}
