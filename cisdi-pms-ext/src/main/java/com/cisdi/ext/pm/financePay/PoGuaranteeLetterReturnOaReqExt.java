package com.cisdi.ext.pm.financePay;

import com.cisdi.ext.pm.processCommon.ProcessCommon;
import com.cisdi.ext.util.AmtUtil;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 保函退还申请-扩展
 */
public class PoGuaranteeLetterReturnOaReqExt {

    /**
     * 保函退还申请-发起时数据校验
     */
    public void guaranteeOAStartCheck(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //流程id
        String id = entityRecord.csCommId;
        //获取金额
        String amt = JdbcMapUtil.getString(entityRecord.valueMap,"AMT_FIVE").trim();
        String amtChina = AmtUtil.number2CNMontrayUnit(new BigDecimal(amt));
        String sql = "update PO_GUARANTEE_LETTER_RETURN_OA_REQ set REMARK_TWO = ? where id = ?";
        myJdbcTemplate.update(sql,amtChina,id);
    }

    /**
     * 流程操作-新增保函-确认操作
     */
    public void guaranteeOACheckOK(){
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 流程操作-新增保函-拒绝操作
     */
    public void guaranteeOACheckRefuse(){
        String status = "refuse";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 流程流程操作详细处理逻辑
     * @param nodeStatus 节点状态码
     * @param status 流程操作状态码
     */
    private void processHandle(String nodeStatus, String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;
        String procInstId = ExtJarHelper.procInstId.get();
        if ("OK".equals(status)){
            if ("start".equals(nodeStatus)){
                WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
            } else {
                //获取审批意见信息
                Map<String,String> message = ProcessCommon.getCommentNew(nodeInstanceId,userId,myJdbcTemplate,procInstId);
                String comment = message.get("comment");

                if ("caiLeaderOK".equals(nodeStatus)){ // 财务分管领导审批
                    ProcessCommon.updateComment("APPROVAL_COMMENT_FIVE",entityRecord.valueMap,comment,entCode,csCommId,userName);
                }
            }
        } else {
            if ("caiLeaderRefuse".equals(nodeStatus)){
                ProcessCommon.updateProcColsValue("APPROVAL_COMMENT_FIVE",entCode,csCommId,null);
            }
        }
    }

    /**
     * 节点状态赋值
     * @param status 状态码
     * @param nodeId 节点id
     * @return
     */
    private String getNodeStatus(String status, String nodeId) {
        String nodeName = "";
        if ("OK".equals(status)){
            if ("0100031468512035336".equals(nodeId)){ //1-发起
                nodeName = "start";
            } else if ("1688729823704477696".equals(nodeId) || "0100031468512035341".equals(nodeId)){ // 财务分管领导审批
                nodeName = "caiLeaderOK";
            }
        } else {
            if ("1688729823704477696".equals(nodeId) || "0100031468512035341".equals(nodeId) ){ // 财务分管领导审批
                nodeName = "caiLeaderRefuse";
            }
        }
        return nodeName;
    }
}
