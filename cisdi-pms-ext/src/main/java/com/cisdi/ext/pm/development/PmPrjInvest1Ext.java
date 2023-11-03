package com.cisdi.ext.pm.development;

import com.cisdi.ext.base.PmPrjExt;
import com.cisdi.ext.invest.InvestAmtExt;
import com.cisdi.ext.pm.PmInLibraryExt;
import com.cisdi.ext.pm.processCommon.ProcessCommon;
import com.cisdi.ext.util.WfPmInvestUtil;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.StringUtils;

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
        String errorMsg;
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
        //审批人员信息写入花名册
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        String processId = ExtJarHelper.procId.get();
        String companyId = JdbcMapUtil.getString(entityRecord.valueMap,"CUSTOMER_UNIT");
        ProcessCommon.addPrjPostUser(projectId,entCode,processId,companyId,csCommId,myJdbcTemplate);
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
        //业务表名
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        // 流程id
        String procInstId = ExtJarHelper.procInstId.get();
        // 当前登录人
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;

        if ("start".equals(nodeStatus)){
            WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
        } else {
            //获取审批意见信息
            Map<String,String> message = ProcessCommon.getCommentNew(nodeInstanceId,userId,myJdbcTemplate,procInstId);
            String comment = message.get("comment");

            if ("createByReport".equals(nodeStatus)){ // 4-创建人提交修编及评审报告
                ProcessCommon.updateComment("REVIEW_AND_REVISION_REMARK",entityRecord.valueMap,comment,entCode,csCommId,userName);
            } else if ("costDesignCheck".equals(nodeStatus)){ //5-成本设计岗审批

                String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID"); //项目id
                String companyId = JdbcMapUtil.getString(entityRecord.valueMap,"CUSTOMER_UNIT"); // 业主单位id
                userId = ProcessCommon.getOriginalUser(nodeInstanceId,userId,myJdbcTemplate); // 原始表单人员信息

                String dept = PmPrjExt.getUserDeptByRoster(userId,projectId,companyId,csCommId,entCode,myJdbcTemplate); // 判断当前人岗位信息
                if (StringUtils.hasText(dept)){
                    if (dept.contains("AD_USER_EIGHTEEN_ID")){ //成本岗
                        ProcessCommon.updateComment("COST_COMMENT",entityRecord.valueMap,comment,entCode,csCommId,userName);
                    } else if (dept.contains("AD_USER_TWENTY_TWO_ID")){ //设计岗
                        ProcessCommon.updateComment("DESIGN_COMMENT",entityRecord.valueMap,comment,entCode,csCommId,userName);
                    }
                }
            } else if ("earlyLeaderCheckOK".equals(nodeStatus)){ //6-前期部领导审批-OK
                ProcessCommon.updateComment("EARLY_COMMENT",entityRecord.valueMap,comment,entCode,csCommId,userName);
            } else if ("earlyLeaderCheckRefuse".equals(nodeStatus)){ //6-前期部领导审批-拒绝
                ProcessCommon.updateProcColsValue("EARLY_COMMENT",entCode,csCommId,null);
            } else if ("costDesignCheckRefuse".equals(nodeStatus)){ // 5-成本设计岗审批-拒绝
                ProcessCommon.updateProcColsValue("COST_COMMENT",entCode,csCommId,null);
                ProcessCommon.updateProcColsValue("DESIGN_COMMENT",entCode,csCommId,null);
            }
        }

    }

    /**
     * 节点赋值
     * @param status 状态码
     * @param nodeInstanceId 节点实例id
     * @param myJdbcTemplate 数据源
     * @return 节点状态
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
            } else if ("0100031468512029142".equals(nodeId)){
                name = "start";
            }
        } else {
            if ("0100031468512029161".equals(nodeId)){ //6-前期部领导审批
                name = "earlyLeaderCheckRefuse";
            } else if ("0100031468512029154".equals(nodeId)){ //5-成本设计岗审批
                name = "costDesignCheckRefuse";
            }
        }
        return name;
    }
}
