package com.cisdi.ext.pm;

import com.cisdi.ext.base.PmPrjExt;
import com.cisdi.ext.invest.InvestAmtExt;
import com.cisdi.ext.model.PmPrjSettleAccounts;
import com.cisdi.ext.util.StringUtil;
import com.cisdi.ext.util.WfPmInvestUtil;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 项目结算流程-扩展
 */
public class PmPrjSettleAccountsExt {

    /**
     * 项目结算流程-发起时数据校验
     */
    public void prjSettleStartCheck(){
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //业务id
        String id = entityRecord.csCommId;
        //项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        //一个项目只能发起一次校验
        checkPrjRepeatProcess(projectId,id);
        //结算金额校验
        InvestAmtExt.checkAmt(entityRecord);
    }

    /**
     * 项目结算流程-结束时扩展
     */
    public void prjSettleEndCheck(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //流程id
        String processId = ExtJarHelper.procId.get();
        //流程表名
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        //业主单位
        String companyId = JdbcMapUtil.getString(entityRecord.valueMap,"CUSTOMER_UNIT");
        //流程业务表id
        String csCommId = entityRecord.csCommId;
        //更新资金信息
        PmPrjExt.updatePrjAmt(entityRecord,"PM_PRJ_SETTLE_ACCOUNTS",4,myJdbcTemplate,entCode);
        //创建项目投资测算汇总可研数据
        WfPmInvestUtil.calculateData(csCommId, entCode, projectId);
        //审批人员信息写入花名册(计划运营岗)
        ProcessCommon.addPrjPostUser(projectId,entCode,processId,companyId,csCommId,myJdbcTemplate);
    }


    /**
     * 项目结算流程-一个项目只能发起一次结算流程校验
     * @param projectId 项目id
     * @param id 业务流程表id
     */
    private void checkPrjRepeatProcess(String projectId, String id) {
        List<PmPrjSettleAccounts> list = PmPrjSettleAccounts.selectByWhere(new Where().eq(PmPrjSettleAccounts.Cols.PM_PRJ_ID,projectId)
                .nin(PmPrjSettleAccounts.Cols.STATUS,"VD,VDING")
                .neq(PmPrjSettleAccounts.Cols.ID,id));
        if (!CollectionUtils.isEmpty(list)){
            throw new BaseException("改项目已经发起过此类流程，请勿重复发起");
        }
    }

    /**
     * 项目结算流程-操作时扩展-审批通过
     */
    public void settleCheckOk(){
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 项目结算流程-操作时扩展-审批驳回
     */
    public void settleCheckRefuse(){
        String status = "false";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 项目结算流程-操作时扩展-流程操作时详细处理逻辑
     * @param nodeStatus 节点状态名称
     * @param status 状态码
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
        //业主单位
        String companyId = JdbcMapUtil.getString(entityRecord.valueMap,"CUSTOMER_UNIT");
        if ("false".equals(status)){ //拒绝
            if ("chargeCheckRefuse".equals(nodeStatus)){ //2-部门主管意见-拒绝
                clearComment("REMARK_LONG_ONE",id,myJdbcTemplate);
            } else if ("earlyManageDesignFinanceCheckRefuse".equals(nodeStatus)){ //3-前期/工程/设计/财务审批-拒绝
                clearComment("REMARK_LONG_TWO,REMARK_LONG_THREE,REMARK_LONG_FOUR,REMARK_LONG_FIVE",id,myJdbcTemplate);
            } else if ("leaderCheckRefuse".equals(nodeStatus)){ //4-分管领导审批-拒绝
                clearComment("REMARK_LONG_SIX",id,myJdbcTemplate);
            } else if ("presidentCheckRefuse".equals(nodeStatus)){ //5-总经理审批-拒绝
                clearComment("REMARK_LONG_SEVEN",id,myJdbcTemplate);
            }
        } else { //确定，审批通过
            if ("start".equals(nodeStatus)){ //流程标题生成
                //重写通用单据部门信息
                ProcessCommon.updateProcessDept(id,userId,companyId,entCode);
                WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
            } else {
                //项目id
                String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
                //获取审批意见信息
                Map<String,String> message = ProcessCommon.getCommentNew(nodeInstanceId,userId,myJdbcTemplate,procInstId,userName);
                //审批意见内容
                String comment = message.get("comment");
                if ("chargeCheckOk".equals(nodeStatus)){ //2-部门主管意见-通过
                    updateComment("REMARK_LONG_ONE",userName,comment,entityRecord.valueMap,id);
                } else if ("earlyManageDesignFinanceCheckOK".equals(nodeStatus)){ //3-前期/工程/设计/财务审批-通过
                    userId = ProcessCommon.getOriginalUser(nodeInstanceId,userId,myJdbcTemplate);
                    //根据花名册信息推出该人员
                    String processPostId = ProcessCommon.getUserProcessPost(userId,projectId,companyId,entCode,id,myJdbcTemplate);
                    if ("1636192694655168512".equals(processPostId)){ //前期岗
                        updateComment("REMARK_LONG_TWO",userName,comment,entityRecord.valueMap,id);
                    } else if ("1637988041987633152".equals(processPostId)){ //工程岗
                        updateComment("REMARK_LONG_THREE",userName,comment,entityRecord.valueMap,id);
                    } else if ("1637987984030740480".equals(processPostId)){ //设计岗
                        updateComment("REMARK_LONG_FOUR",userName,comment,entityRecord.valueMap,id);
                    } else if ("1637988004163399680".equals(processPostId)){ //财务岗
                        updateComment("REMARK_LONG_FIVE",userName,comment,entityRecord.valueMap,id);
                    }
                } else if ("leaderCheckOk".equals(nodeStatus)){ //4-分管领导审批-通过
                    updateComment("REMARK_LONG_SIX",userName,comment,entityRecord.valueMap,id);
                } else if ("presidentCheckOK".equals(nodeStatus)){ //5-总经理审批-通过
                    updateComment("REMARK_LONG_SEVEN",userName,comment,entityRecord.valueMap,id);
                }
            }
        }
    }

    /**
     * 审批意见修改
     * @param cols 被修改字段
     * @param userName 操作人名称
     * @param comment 意见
     * @param valueMap 表单数据
     * @param id id
     */
    private void updateComment(String cols, String userName, String comment, Map<String, Object> valueMap, String id) {
        //获取流程中的意见信息
        String processComment = JdbcMapUtil.getString(valueMap,cols);
        //生成最终的意见信息
        String commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
        Crud.from("PM_PRJ_SETTLE_ACCOUNTS").where().eq("ID",id).update()
                .set(cols,commentEnd).exec();
    }

    /**
     * 清除数据
     * @param entCode 被清除字段
     * @param id id
     * @param myJdbcTemplate 数据源
     */
    private void clearComment(String entCode, String id, MyJdbcTemplate myJdbcTemplate) {
        List<String> list = StringUtil.getStrToList(entCode,",");
        StringBuilder sb = new StringBuilder("update PM_PRJ_SETTLE_ACCOUNTS set ");
        for (String tmp : list) {
            sb.append(tmp).append(" = null,");
        }
        sb.append("LAST_MODI_DT = now() where id = '").append(id).append("'");
        myJdbcTemplate.update(sb.toString());
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
            if ("1640179993889873920".equals(nodeId)){ //1-发起
                nodeName = "start";
            } else if ("1640179994070228992".equals(nodeId)){ //2-部门主管意见-通过
                nodeName = "chargeCheckOk";
            } else if ("1640179994145726464".equals(nodeId)){ //3-前期/工程/设计/财务审批-通过
                nodeName = "earlyManageDesignFinanceCheckOK";
            } else if ("1640179994179280896".equals(nodeId)){ //4-分管领导审批-通过
                nodeName = "leaderCheckOk";
            } else if ("1640179994221223936".equals(nodeId)){ //5-总经理审批-通过
                nodeName = "presidentCheckOK";
            }
        } else {
            if ("1640179994070228992".equals(nodeId)){ //2-部门主管意见-拒绝
                nodeName = "chargeCheckRefuse";
            } else if ("1640179994145726464".equals(nodeId)){ //3-前期/工程/设计/财务审批-拒绝
                nodeName = "earlyManageDesignFinanceCheckRefuse";
            } else if ("1640179994179280896".equals(nodeId)){ //4-分管领导审批-拒绝
                nodeName = "leaderCheckRefuse";
            } else if ("1640179994221223936".equals(nodeId)){ //5-总经理审批-拒绝
                nodeName = "presidentCheckRefuse";
            }
        }
        return nodeName;
    }
}
