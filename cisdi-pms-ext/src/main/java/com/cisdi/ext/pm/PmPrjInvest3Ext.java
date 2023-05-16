package com.cisdi.ext.pm;

import com.cisdi.ext.base.PmPrjExt;
import com.cisdi.ext.invest.InvestAmtExt;
import com.cisdi.ext.util.WfPmInvestUtil;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.util.Map;

/**
 * 预算财评-流程扩展
 */
public class PmPrjInvest3Ext {

    /**
     * 预算财评-发起时数据校验
     */
    public void invest3ProcessStartCheck(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //流程表名
        String entityCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        //流程业务表id
        String id = entityRecord.csCommId;
        //校验该项目是否已经发起过
        String errorMsg = "";
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            String projectName = JdbcMapUtil.getString(entityRecord.valueMap,"PROJECT_NAME_WR");
            if (projectName.contains("、")){
                throw new BaseException("该流程不允许多个项目同时发起，请重新填写单个项目信息！");
            }
            errorMsg = ProcessCommon.prjRepeatStartByName(entityCode,projectName,id,myJdbcTemplate);
        } else {
            errorMsg = ProcessCommon.prjRepeatStartById(entityCode,projectId,id,myJdbcTemplate);
        }
        if (!SharedUtil.isEmptyString(errorMsg)){
            throw new BaseException(errorMsg);
        }
    }

    /**
     * 预算财评-结束时数据校验处理
     */
    public void invest3ProcessEndCheck(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //校验资金金额
        InvestAmtExt.checkAmt(entityRecord);
        //流程表名
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        //流程业务表id
        String csCommId = entityRecord.csCommId;
        //项目id
        String pmPrjId = String.valueOf(entityRecord.valueMap.get("PM_PRJ_ID"));
        //更新项目信息(基础信息、资金信息)
        PmPrjExt.updatePrjBaseData(entityRecord,"PM_PRJ_INVEST3",4,myJdbcTemplate,entCode);
        //创建项目投资测算汇总财评数据
        WfPmInvestUtil.calculateData(csCommId, entCode, pmPrjId);
    }

    /**
     * 流程操作-预算财评-确认操作
     */
    public void invest3CheckOk() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        //定义节点状态
        String status = "OK";
        String nodeStatus = getStatus(status,nodeInstanceId,myJdbcTemplate);
        //详细处理逻辑
        handleCHeckData(status,nodeStatus,nodeInstanceId,myJdbcTemplate);
    }

    /**
     * 流程操作-预算财评-拒绝操作
     */
    public void invest3CheckRefuse() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        //定义节点状态
        String status = "false";
        String nodeStatus = getStatus(status,nodeInstanceId,myJdbcTemplate);
        //详细处理逻辑
        handleCHeckData(status,nodeStatus,nodeInstanceId,myJdbcTemplate);
    }

    /**
     * 流程操作详细处理逻辑
     * @param status 状态码
     * @param nodeStatus 节点状态码
     * @param nodeInstanceId 节点实例id
     * @param myJdbcTemplate 数据源
     */
    public void handleCHeckData(String status, String nodeStatus, String nodeInstanceId, MyJdbcTemplate myJdbcTemplate) {
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        String procInstId = ExtJarHelper.procInstId.get();
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        if ("OK".equals(status)){
            if ("start".equals(nodeStatus)){
                WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
            } else {
                //获取审批意见信息
                Map<String,String> message = ProcessCommon.getCommentNew(nodeInstanceId,userId,myJdbcTemplate,procInstId,userName);
                //审批意见内容
                String file = message.get("file");
                String comment = message.get("comment");

                if ("costCheck".equals(nodeStatus)){ //成本部审核意见回显
                    //获取流程中的意见信息
                    String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"COST_SECOND_REVIEW_COMMENT");
                    //生成最终的意见信息
                    String commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                    ProcessCommon.commentShow("COST_SECOND_REVIEW_COMMENT",commentEnd,csCommId,entCode);
                }
            }
        } else {
            if ("costCheckFalse".equals(nodeStatus)){ //成本部审核意见回显清除
                ProcessCommon.clearData("COST_SECOND_REVIEW_COMMENT",csCommId,entCode,myJdbcTemplate);
            }
        }
    }

    /**
     * 节点赋值
     * @param status 状态码
     * @param nodeInstanceId 节点实例id
     * @param myJdbcTemplate 数据源
     * @return 节点状态名
     */
    private String getStatus(String status, String nodeInstanceId, MyJdbcTemplate myJdbcTemplate) {
        String nodeId = ProcessCommon.getNodeIdByNodeInstanceId(nodeInstanceId,myJdbcTemplate);
        String name = "";
        if ("OK".equals(status)){
            if ("0099799190825084088".equals(nodeId)){ //1-经办人提交预算初稿
                name = "start";
            } else if ("0099799190825094000".equals(nodeId)){ //2-成本部负责人审核
                name = "costCheck";
            }
        } else {
            if ("0099799190825094000".equals(nodeId)){ //2-成本部负责人审核
                name = "costCheckFalse";
            }
        }
        return name;
    }
}
