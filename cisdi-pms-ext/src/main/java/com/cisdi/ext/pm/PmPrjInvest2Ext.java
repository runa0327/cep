package com.cisdi.ext.pm;

import com.cisdi.ext.base.PmPrjExt;
import com.cisdi.ext.invest.InvestAmtExt;
import com.cisdi.ext.pm.processCommon.ProcessCommon;
import com.cisdi.ext.util.WfPmInvestUtil;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 初设概算审批-流程扩展
 */
public class PmPrjInvest2Ext {

    /**
     * 初设概算审批-发起时数据校验
     */
    public void invest2ProcessStartCheck(){
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
     * 初设概算审批-结束时数据校验处理
     */
    public void invest2ProcessEndCheck(){
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
        // 获取建设年限
        String year = JdbcMapUtil.getString(entityRecord.valueMap, "BUILD_YEARS");
        // 修改项目建设年限信息：
        Crud.from("PM_PRJ").where().eq("ID", pmPrjId).update().set("BUILD_YEARS", year).exec();
        //更新项目信息(基础信息、资金信息)
        PmPrjExt.updatePrjBaseData(entityRecord,"PM_PRJ_INVEST2",3,myJdbcTemplate,entCode);
        //创建项目投资测算汇总可研数据
        WfPmInvestUtil.calculateData(csCommId, entCode, pmPrjId);
        //审批人员信息写入花名册
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        String processId = ExtJarHelper.procId.get();
        String companyId = JdbcMapUtil.getString(entityRecord.valueMap,"CUSTOMER_UNIT");
        ProcessCommon.addPrjPostUser(projectId,entCode,processId,companyId,csCommId,myJdbcTemplate);
    }

    /**
     * 初设概算审批-审批按钮-通过
     */
    public void invest2CheckOk() {
        //当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String nodeId = ExtJarHelper.nodeId.get();
        //定义节点状态
        String status = "OK";
        String nodeStatus = getStatus(status,nodeId);
        //详细处理逻辑
        handleCHeckData(nodeStatus,status,nodeId,nodeInstanceId);
    }

    /**
     * 初设概算审批-审批按钮-拒绝
     */
    public void invest2CheckRefuse() {
        //当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String nodeId = ExtJarHelper.nodeId.get();
        //定义节点状态
        String status = "refuse";
        String nodeStatus = getStatus(status,nodeId);
        //详细处理逻辑
        handleCHeckData(nodeStatus,status,nodeId,nodeInstanceId);
    }

    /**
     * 可研报告审批-流程审批意见处理详细逻辑
     * @param nodeStatus 节点状态
     * @param nodeInstanceId 节点实例id
     * @param nodeId 节点id
     * @param status 流程审批操作状态
     */
    private void handleCHeckData(String nodeStatus, String status, String nodeId, String nodeInstanceId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //业务表名
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        // 流程id
        String procInstId = ExtJarHelper.procInstId.get();
        // 当前登录人
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;

        //分支判断
        if ("OK".equals(status)){
            if ("start".equals(nodeStatus)){
                WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
            } else {
                //获取审批意见信息
                Map<String,String> message = ProcessCommon.getCommentNew(nodeInstanceId,userId,myJdbcTemplate,procInstId);

                //审批意见内容
                String file = message.get("file");
                String comment = message.get("comment");

                if ("costCheckOK".equals(nodeStatus)){ //2-成本岗内部审核
                    String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"REMARK_LONG_ONE");
                    //生成最终的意见信息
                    String commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                    ProcessCommon.commentShow("REMARK_LONG_ONE",commentEnd,csCommId,entCode);
                } else if ("costDesignCheckOK".equals(nodeStatus)){ //8-成本岗/设计岗并行意见
                    // 判断当前人岗位信息
                    //项目id
                    String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
                    String companyId = JdbcMapUtil.getString(entityRecord.valueMap,"CUSTOMER_UNIT");
                    userId = ProcessCommon.getOriginalUser(nodeInstanceId,userId,myJdbcTemplate);
                    String dept = PmPrjExt.getUserDeptByRoster(userId,projectId,companyId,csCommId,entCode,myJdbcTemplate);
                    if (!SharedUtil.isEmptyString(dept)){
                        String processComment = "", commentEnd = "";
                        if (dept.contains("AD_USER_EIGHTEEN_ID")){ //成本岗
                            //获取流程中的意见信息
                            processComment = JdbcMapUtil.getString(entityRecord.valueMap,"COST_COMMENT");
                            commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                            ProcessCommon.commentShow("COST_COMMENT",commentEnd,csCommId,entCode);
                        } else if (dept.contains("AD_USER_TWENTY_TWO_ID")){ //设计岗
                            //获取流程中的意见信息
                            processComment = JdbcMapUtil.getString(entityRecord.valueMap,"DESIGN_COMMENT");
                            commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                            ProcessCommon.commentShow("DESIGN_COMMENT",commentEnd,csCommId,entCode);
                        }
                    }
                } else if ("earlyLeaderCheckOK".equals(nodeStatus)){ //9-前期管理部负责人意见
                    String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_TWO");
                    //生成最终的意见信息
                    String commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                    ProcessCommon.commentShow("APPROVAL_COMMENT_TWO",commentEnd,csCommId,entCode);
                }
            }
        } else {
            if ("costCheckRefuse".equals(nodeStatus)){ //2-成本岗内部审核
                ProcessCommon.clearData("REMARK_LONG_ONE",csCommId,entCode,myJdbcTemplate);
            } else if ("costDesignCheckRefuse".equals(nodeStatus)){ //8-成本岗/设计岗并行意见
                ProcessCommon.clearData("COST_COMMENT",csCommId,entCode,myJdbcTemplate);
                ProcessCommon.clearData("DESIGN_COMMENT",csCommId,entCode,myJdbcTemplate);
            } else if ("earlyLeaderCheckRefuse".equals(nodeStatus)){ //9-前期管理部负责人意见
                ProcessCommon.clearData("APPROVAL_COMMENT_TWO",csCommId,entCode,myJdbcTemplate);
            }
        }

    }

    /**
     * 节点赋值
     * @param status 状态码
     * @param nodeId 节点id
     * @return
     */
    private String getStatus(String status, String nodeId) {
        String name = "";
        if ("OK".equals(status)){
            if ("0100031468512030982".equals(nodeId)){ //1-设计岗初概申报
                name = "start";
            } else if ("0100031468512030985".equals(nodeId)){ //2-成本岗内部审核
                name = "costCheckOK";
            } else if ("1605404508899385344".equals(nodeId)){ //8-成本岗/设计岗并行意见
                name = "costDesignCheckOK";
            } else if ("1605404580898807808".equals(nodeId)){ //9-前期管理部负责人意见
                name = "earlyLeaderCheckOK";
            }
        } else {
            if ("0100031468512030985".equals(nodeId)){ //2-成本岗内部审核
                name = "costCheckRefuse";
            } else if ("1605404508899385344".equals(nodeId)){ //8-成本岗/设计岗并行意见
                name = "costDesignCheckRefuse";
            } else if ("1605404580898807808".equals(nodeId)){ //9-前期管理部负责人意见
                name = "earlyLeaderCheckRefuse";
            }
        }
        return name;
    }

    /**====================================历史数据处理开始===================================================**/

    /**
     * 根据选择的记录刷新项目预算及项目资金信息
     */
    public void updatePrjAmtByInvest2(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<EntityRecord> entityRecord = ExtJarHelper.entityRecordList.get();
        if (!CollectionUtils.isEmpty(entityRecord)){
            for (EntityRecord record : entityRecord) {
                String csCommId = record.csCommId;
                //项目id
                String pmPrjId = String.valueOf(record.valueMap.get("PM_PRJ_ID"));
                //更新项目信息(基础信息、资金信息)
                PmPrjExt.updatePrjBaseData(record,"PM_PRJ_INVEST2",3,myJdbcTemplate,"PM_PRJ_INVEST2");
                //创建项目投资测算汇总可研数据
                WfPmInvestUtil.calculateData(csCommId, "PM_PRJ_INVEST2", pmPrjId);
                //审批人员信息写入花名册
                String projectId = JdbcMapUtil.getString(record.valueMap,"PM_PRJ_ID");
                String processId = ExtJarHelper.procId.get();
                String companyId = JdbcMapUtil.getString(record.valueMap,"CUSTOMER_UNIT");
                ProcessCommon.addPrjPostUser(projectId,"PM_PRJ_INVEST2",processId,companyId,csCommId,myJdbcTemplate);
            }
        }
    }

    /**====================================历史数据处理结束===================================================**/
}
