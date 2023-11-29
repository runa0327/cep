package com.cisdi.ext.pm.bidPurchase;

import com.cisdi.ext.model.PmPurchaseProcessEnrollDetail;
import com.cisdi.ext.pm.bidPurchase.detail.PmPurchaseProcessPrjDetailExt;
import com.cisdi.ext.pm.processCommon.ProcessCommon;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.Map;

/**
 * 采购过程管理-扩展
 */
public class PmPurchaseProcessReqExt {

    /**
     * 发起时数据校验
     */
    public void purchaseStart(){
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        Map<String,Object> map = entityRecord.valueMap;
        String id = entityRecord.csCommId; // 主表单id
        // 获取采购方式
        String purchaseTypeId = JdbcMapUtil.getString(map,"BUY_TYPE_ID");
        dealPurchase(id,purchaseTypeId);
    }

    /**
     * 根据采购方式不同清空不同的明细信息 公开类清空报名，非公开清空投标
     * @param id 主表单id
     * @param purchaseTypeId 采购方式id 0099952822476385221 = 公开招标
     */
    private void dealPurchase(String id, String purchaseTypeId) {
        if ("0099952822476385221".contains(purchaseTypeId)){
            Crud.from(PmPurchaseProcessEnrollDetail.ENT_CODE).where().eq(PmPurchaseProcessEnrollDetail.Cols.PM_PURCHASE_PROCESS_REQ_ID,id).delete().exec();
        }
    }

    /**
     * 结束时校验扩展
     */
    public void purchaseEnd(){
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String id = entityRecord.csCommId;
        // 项目信息项目明细表
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_IDS");
        PmPurchaseProcessPrjDetailExt.insertData(id,projectId);
    }

    /**
     * 备案回执信息自动填补字段
     */
    public void showRecord(){
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        Map<String,Object> map = entityRecord.valueMap;
        String id = entityRecord.csCommId;
        Crud.from("PM_PURCHASE_PROCESS_REQ").where().eq("ID",id).update()
                .set("PM_PRJ_ID_ONE",JdbcMapUtil.getString(map,"PM_PRJ_ID")) // 项目
                .set("BUY_TYPE_ONE_ID",JdbcMapUtil.getString(map,"BUY_TYPE_ID")) // 采购方式
                .set("AMT_EIGHTH",JdbcMapUtil.getString(map,"AMT_FIVE")) // 预算金额
                .set("AMT_NINTH",JdbcMapUtil.getString(map,"AMT_SEVEN")) // 中标金额
                .exec();
    }

    /**
     * 流程操作-采购过程管理-确认操作
     */
    public void purchaseOK(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String status = "OK";
        String nodeStatus = processCheck(status,nodeInstanceId,myJdbcTemplate);
        handleCheckData(status,nodeStatus,nodeInstanceId,myJdbcTemplate);
    }

    /**
     * 流程操作-采购过程管理-拒绝操作
     */
    public void purchaseRefuse(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String status = "false";
        String nodeStatus = processCheck(status,nodeInstanceId,myJdbcTemplate);
        handleCheckData(status,nodeStatus,nodeInstanceId,myJdbcTemplate);
    }

    /**
     * 采购需求审批-流程审批处理逻辑
     * @param status 状态码
     * @param nodeStatus 节点状态名称
     * @param nodeInstanceId 节点实例id
     * @param myJdbcTemplate 数据源
     */
    private void handleCheckData(String status, String nodeStatus, String nodeInstanceId, MyJdbcTemplate myJdbcTemplate) {
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        Map<String,Object> map = entityRecord.valueMap;
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;
        //表名
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        //流程实例id
        String procInstId = ExtJarHelper.procInstId.get();
        String csCommId = entityRecord.csCommId; // 主表单id

        //分支判断，逻辑处理
        if ("OK".equals(status)){
            if ("startOk".equals(nodeStatus)){
                WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
            } else {

                //获取审批意见
                Map<String,String> message = ProcessCommon.getCommentNew(nodeInstanceId,userId,myJdbcTemplate,procInstId);
                //审批意见、内容
                String comment = message.get("comment");

                if ("deptLeaderOk".equals(nodeStatus)){ // 3-部门负责人审批
                    ProcessCommon.updateComment("TEXT_REMARK_FOUR",map,comment,entCode,csCommId,userName);
                } else if ("costOk".equals(nodeStatus)){ // 4-成本部负责人审批
                    ProcessCommon.updateComment("TEXT_REMARK_FIVE",map,comment,entCode,csCommId,userName);
                } else if ("buyOk".equals(nodeStatus)){ // 5-采购部负责人审批
                    ProcessCommon.updateComment("TEXT_REMARK_SIX",map,comment,entCode,csCommId,userName);
                }
            }
        } else {
            if ("deptLeaderRefuse".equals(nodeStatus)){ // 3-部门负责人审批
                ProcessCommon.updateProcColsValue("TEXT_REMARK_FOUR",entCode,csCommId,null);
            } else if ("costRefuse".equals(nodeStatus)){ // 4-成本部负责人审批
                ProcessCommon.updateProcColsValue("TEXT_REMARK_FIVE",entCode,csCommId,null);
            } else if ("buyRefuse".equals(nodeStatus)){ // 5-采购部负责人审批
                ProcessCommon.updateProcColsValue("TEXT_REMARK_SIX",entCode,csCommId,null);
            }
        }
    }

    /**
     * 节点赋值
     * @param status 状态码
     * @param nodeInstanceId 节点实例id
     * @param myJdbcTemplate 数据源
     */
    private String processCheck(String status, String nodeInstanceId, MyJdbcTemplate myJdbcTemplate) {
        String nodeId = ProcessCommon.getNodeIdByNodeInstanceId(nodeInstanceId,myJdbcTemplate);
        String name = "";
        if ("OK".equals(status)){
            if ("1666731896467521536".equals(nodeId)){ //1-发起
                name = "startOk";
            } else if ("1686636956383117312".equals(nodeId)){ // 3-部门负责人审批
                name = "deptLeaderOk";
            } else if ("1686637554746720256".equals(nodeId)){ // 4-成本部负责人审批
                name = "costOk";
            } else if ("1686638906130497536".equals(nodeId)){ // 5-采购部负责人审批
                name = "buyOk";
            }
        } else {
            if ("1686636956383117312".equals(nodeId)){ // 3-部门负责人审批
                name = "deptLeaderRefuse";
            } else if ("1686637554746720256".equals(nodeId)){ // 4-成本部负责人审批
                name = "costRefuse";
            } else if ("1686638906130497536".equals(nodeId)){ // 5-采购部负责人审批
                name = "buyRefuse";
            }
        }
        return name;
    }
}
