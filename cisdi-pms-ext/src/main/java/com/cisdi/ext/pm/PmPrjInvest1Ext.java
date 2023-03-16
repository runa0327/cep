package com.cisdi.ext.pm;

import com.cisdi.ext.base.PmPrjExt;
import com.cisdi.ext.invest.InvestAmtExt;
import com.cisdi.ext.util.WfPmInvestUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.util.Map;

/**
 * 可研报告审批-流程扩展
 */
public class PmPrjInvest1Ext {

    /**
     * 可研报告审批-发起时数据校验
     */
    public void invest1ProcessStartCheck(){
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
     * 可研报告审批-结束时数据校验处理
     */
    public void invest1ProcessEndCheck(){
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
        PmPrjExt.updatePrjBaseData(entityRecord,"PM_PRJ_INVEST1",2,myJdbcTemplate,entCode);
        //创建项目投资测算汇总可研数据
        WfPmInvestUtil.calculateData(csCommId, entCode, pmPrjId);
        //评审组织单位入库
        String reviewOrganizationName = JdbcMapUtil.getString(entityRecord.valueMap,"REVIEW_ORGANIZATION_UNIT"); //评审组织单位
        PmInLibraryExt.updateOrCreateParty(reviewOrganizationName,"IS_REVIEW",myJdbcTemplate);
    }

    /**
     * 可研报告审批-审批按钮-通过
     */
    public void invest1CheckOk() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        //定义节点状态
        String nodeStatus = getStatus("true",nodeInstanceId,myJdbcTemplate);
        //详细处理逻辑
        handleCHeckData(nodeStatus,nodeInstanceId,myJdbcTemplate);
    }

    /**
     * 可研报告审批-审批按钮-拒绝
     */
    public void invest1CheckRefuse() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        //定义节点状态
        String nodeStatus = getStatus("false",nodeInstanceId,myJdbcTemplate);
        //详细处理逻辑
        handleCHeckData(nodeStatus,nodeInstanceId,myJdbcTemplate);
    }

    /**
     * 可研报告审批-流程审批意见处理详细逻辑
     * @param nodeStatus 节点状态
     * @param nodeInstanceId 节点实例id
     * @param myJdbcTemplate 数据源
     */
    private void handleCHeckData(String nodeStatus, String nodeInstanceId, MyJdbcTemplate myJdbcTemplate) {
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        // 流程id
        String procInstId = ExtJarHelper.procInstId.get();
        // 当前登录人
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;
        //获取审批意见信息
        Map<String,String> message = ProcessCommon.getCommentNew(nodeInstanceId,userId,myJdbcTemplate,procInstId,userName);

        //审批意见内容
        String file = message.get("file");
        String comment = message.get("comment");

        //分支判断
        if ("createByReport".equals(nodeStatus)){ // 4-创建人提交修编及评审报告
            //获取流程中的意见信息
            String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"REVIEW_AND_REVISION_REMARK");
            //生成最终的意见信息
            String commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
            Crud.from("PM_PRJ_INVEST1").where().eq("id",csCommId).update()
                    .set("REVIEW_AND_REVISION_REMARK",commentEnd).exec();
        } else if ("costDesignCheck".equals(nodeStatus)){ //5-成本设计岗审批
            // 判断当前人岗位信息
            //项目id
            String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
            Map<String,String> map = PmPrjExt.getProjectDeptUser(projectId,myJdbcTemplate);
            String dept = PmPrjExt.getUserDept(userId,map);
            String processComment = "", commentEnd = "";
            if ("PRJ_COST_USER_ID".equals(dept)){ //成本岗
                //获取流程中的意见信息
                processComment = JdbcMapUtil.getString(entityRecord.valueMap,"COST_COMMENT");
                commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                Crud.from("PM_PRJ_INVEST1").where().eq("id",csCommId).update().set("COST_COMMENT",commentEnd).exec();
            } else if ("PRJ_DESIGN_USER_ID".equals(dept)){ //设计岗
                //获取流程中的意见信息
                processComment = JdbcMapUtil.getString(entityRecord.valueMap,"DESIGN_COMMENT");
                commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                Crud.from("PM_PRJ_INVEST1").where().eq("id",csCommId).update().set("DESIGN_COMMENT",commentEnd).exec();
            }
        } else if ("earlyLeaderCheckOK".equals(nodeStatus)){ //6-前期部领导审批-OK
            //获取流程中的意见信息
            String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"EARLY_COMMENT");
            //生成最终的意见信息
            String commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
            Crud.from("PM_PRJ_INVEST1").where().eq("id",csCommId).update()
                    .set("EARLY_COMMENT",commentEnd).exec();
        } else if ("earlyLeaderCheckRefuse".equals(nodeStatus)){ //6-前期部领导审批-拒绝
            Crud.from("PM_PRJ_INVEST1").where().eq("id",csCommId).update()
                    .set("EARLY_COMMENT",null).exec();
        }

    }

    /**
     * 节点赋值
     * @param status 状态码
     * @param nodeInstanceId 节点实例id
     * @param myJdbcTemplate 数据源
     * @return
     */
    private String getStatus(String status, String nodeInstanceId, MyJdbcTemplate myJdbcTemplate) {
        String nodeId = ProcessCommon.getNodeIdByNodeInstanceId(nodeInstanceId,myJdbcTemplate);
        String name = "";
        if ("true".equals(status)){
            if ("0100031468512029152".equals(nodeId)){ //4-创建人提交修编及评审报告
                name = "createByReport";
            } else if ("0100031468512029154".equals(nodeId)){ //5-成本设计岗审批
                name = "costDesignCheck";
            } else if ("0100031468512029161".equals(nodeId)){ //6-前期部领导审批
                name = "earlyLeaderCheckOK";
            }
        } else {
            if ("0100031468512029161".equals(nodeId)){ //6-前期部领导审批
                name = "earlyLeaderCheckRefuse";
            }
        }
        return name;
    }
}
