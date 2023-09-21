package com.cisdi.ext.pm.designManage;

import com.cisdi.ext.base.PmPrjExt;
import com.cisdi.ext.model.base.PmPrj;
import com.cisdi.ext.pm.PmInLibraryExt;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 方案设计-扩展
 */
public class PmDesignAssignmentExt {

    /**
     * 流程-方案设计-结束时校验
     */
    public void designAssignmentEndCheck(){
//        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //流程表名
//        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        Map<String,Object> map = entityRecord.valueMap;
        //项目id
        String projectId = JdbcMapUtil.getString(map,"PM_PRJ_ID");
        //更新写入勘察信息
        //获取设计单位名称
//        String designUnitOne = JdbcMapUtil.getString(map,"DESIGN_UNIT_ONE");
        //更新项目设计单位信息 2023-08-14废弃 设计单位从合同签订取数
//        String partyId = PmInLibraryExt.createOrUpdateParty(designUnitOne,"IS_DESIGNER");
//        PmPrj pmPrj = appointValue(projectId,partyId);
//        PmPrjExt.updateData(pmPrj);

        // 效果图
        String rendering = JdbcMapUtil.getString(map,"DESIGN_SKETCH_FILE_ONE");
        // 效果图写入项目库
        renderImgToPrj(rendering,projectId);
    }

    /**
     * 效果图写入项目库
     * @param rendering 效果图
     * @param projectId 项目id
     */
    private void renderImgToPrj(String rendering, String projectId) {
        if (StringUtils.hasText(rendering)){
            PmPrjExt.updatePrjImg(rendering,projectId);
        }
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
     * 流程操作-方案设计-确定按钮
     */
    public void designAssignmentProcessOK(){
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 流程操作-方案设计-拒绝按钮
     */
    public void designAssignmentProcessRefuse(){
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
            if ("0099902212142089953".equals(nodeId)){ //1-发起
                nodeName = "start";
            }
        } else {

        }
        return nodeName;
    }
}
