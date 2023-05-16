package com.cisdi.ext.pm.bidPurchase;

import com.cisdi.ext.base.PmPrjExt;
import com.cisdi.ext.model.PmBidApprovalReq;
import com.cisdi.ext.pm.ProcessCommon;
import com.cisdi.ext.pm.bidPurchase.detail.BidApprovalPrjDetailExt;
import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 *  招标采购-招标文件审批-扩展
 */
@Slf4j
public class PmBidApprovalReqExt {

    /**
     * 招标文件审批扩展-按照先后顺序审批-第一次审批
     */
    public void checkFirst() {
        String status = "first";
        check(status);
    }

    /**
     * 招标文件审批扩展 审批先后顺序 第二次审批
     */
    public void checkSecond() {
        String status = "second";
        check(status);
    }

    private void check(String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Date date = new Date();
        String now = DateTimeUtil.dateToString(date);
        // 当前登录人
        String userId = ExtJarHelper.loginInfo.get().userName;

        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        // 流程id
        String procInstId = ExtJarHelper.procInstId.get();
        // 审批意见
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select u.id u_id,u.code u_code,u.name u_name,tk.user_comment from wf_node_instance ni join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id join ad_user u on tk.ad_user_id=u.id", procInstId);
        StringBuffer comment = new StringBuffer();
        if (!CollectionUtils.isEmpty(list)) {
            for (Map<String, Object> tmp : list) {
                String txt = JdbcMapUtil.getString(tmp,"user_comment");
                if (!SharedUtil.isEmptyString(txt)){
                    comment = comment.append(JdbcMapUtil.getString(tmp,"u_name")).append("： ").append(txt).append("; ");
                }

            }
        }
        if ("first".equals(status)) {
            Integer exec = Crud.from("PM_BID_APPROVAL_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_COMMENT_ONE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("second".equals(status)) {
            Integer exec = Crud.from("PM_BID_APPROVAL_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_COMMENT_TWO", comment).exec();
            log.info("已更新：{}", exec);
        }
    }

    /** 获取成本岗角色 **/
    public void getCostUser() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;
            MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
            String user_id = myJdbcTemplate.queryForMap("select AD_USER_THREE_ID from PM_BID_APPROVAL_REQ where id=?", csCommId).get("AD_USER_THREE_ID").toString();
            ArrayList<Object> userIdList = new ArrayList<>(1);
            userIdList.add(user_id);
            ExtJarHelper.returnValue.set(userIdList);
        }
    }

    /** 获取合约部角色 **/
    public void getContractUser() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;
            MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
            String user_id = myJdbcTemplate.queryForMap("select AD_USER_FOUR_ID from PM_BID_APPROVAL_REQ where id=?", csCommId).get("AD_USER_FOUR_ID").toString();
            ArrayList<Object> userIdList = new ArrayList<>(1);
            userIdList.add(user_id);
            ExtJarHelper.returnValue.set(userIdList);
        }
    }

    /** 获取发起选择的经办人**/
    public void getOperatorOneIdUser(){
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            getCreateDeptUser(entityRecord,"OPERATOR_ONE_ID","经办人");
        }
    }

    /** 获取发起选择的采购经办人**/
    public void getAdUserIdUser(){
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            getCreateDeptUser(entityRecord,"AD_USER_ID","采购经办人");
        }
    }

    /** 获取发起选择的合同岗**/
    public void getAdUserFourIdUser(){
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            getCreateDeptUser(entityRecord,"AD_USER_FOUR_ID","合同岗");
        }
    }

    /** 获取发起选择的法务岗**/
    public void getAdUserEighthIdUser(){
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            getCreateDeptUser(entityRecord,"AD_USER_EIGHTH_ID","法务岗");
        }
    }

    /** 获取发起选择的合同岗**/
    public void getAdUserNinthIdUser(){
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            getCreateDeptUser(entityRecord,"AD_USER_NINTH_ID","财务岗");
        }
    }

    /** 办结通知-发起人+需求经办人**/
    public void getNoticeUser(){
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            getCreateDeptUser(entityRecord,"notice","办结通知");
        }
    }


    /** 获取流程发起时选择的各个岗位信息 **/
    private void getCreateDeptUser(EntityRecord entityRecord, String code, String name) {
        String csCommId = entityRecord.csCommId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        if ("notice".equals(code)){
            String sql = "select OPERATOR_ONE_ID,CRT_USER_ID from PM_BID_APPROVAL_REQ where id=?";
            List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,csCommId);
            if (!CollectionUtils.isEmpty(list)){
                String value1 = JdbcMapUtil.getString(list.get(0),"OPERATOR_ONE_ID");
                String value2 = JdbcMapUtil.getString(list.get(0),"CRT_USER_ID");
                ArrayList<Object> userIdList = new ArrayList<>(1);
                userIdList.add(value1);
                userIdList.add(value2);
                ExtJarHelper.returnValue.set(userIdList);
            }
        } else {
            String sql = "select "+code+" from PM_BID_APPROVAL_REQ where id=?";
            List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,csCommId);
            if (!CollectionUtils.isEmpty(list)){
                String value = JdbcMapUtil.getString(list.get(0),code);
                if (!SharedUtil.isEmptyString(value)){
                    ArrayList<Object> userIdList = new ArrayList<>(1);
                    userIdList.add(value);
                    ExtJarHelper.returnValue.set(userIdList);
                }
            }
        }
    }

    /** 成本/合约/需求经办人审批-意见回显 **/
    public void costContractOperatorCheck(){
        String status = "costContractOperatorCheck";
        newCheck(status);
    }

    /** 成本/合约/需求经办人审批-拒绝 **/
    public void costContractOperatorCheckRefuse(){
        String status = "costContractOperatorCheckRefuse";
        newCheck(status);
    }

    /** 成本/合约/需求/财务/法务部审批-意见回显 **/
    public void costContractOperatorLegalFinanceCheck(){
        String status = "costContractOperatorLegalFinanceCheck";
        newCheck(status);
    }

    /** 成本/合约/需求/财务/法务部审批-拒绝 **/
    public void costContractOperatorLegalFinanceCheckRefuse(){
        String status = "costContractOperatorLegalFinanceCheckRefuse";
        newCheck(status);
    }

    /** 法律审批-意见回显 **/
    public void lawyerCheck(){
        String status = "lawyerCheck";
        newCheck(status);
    }

    /** 法律审批-拒绝 **/
    public void lawyerCheckRefuse(){
        String status = "lawyerCheckRefuse";
        newCheck(status);
    }

    /** 采购部负责人-意见回显 **/
    public void buyLeaderCheck(){
        String status = "buyLeaderCheck";
        newCheck(status);
    }

    /** 采购部负责人-拒绝 **/
    public void buyLeaderCheckRefuse(){
        String status = "buyLeaderCheckRefuse";
        newCheck(status);
    }

    private void newCheck(String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        // 流程id
        String procInstId = ExtJarHelper.procInstId.get();
        //获取当前节点实例id
        String nodeId = ExtJarHelper.nodeInstId.get();
        // 当前登录人id
        String userId = ExtJarHelper.loginInfo.get().userId;

        String comment = getCommentValue("user_comment",procInstId,userId,myJdbcTemplate);
        String file = getCommentValue("USER_ATTACHMENT",procInstId,userId,myJdbcTemplate);
        //判断当前是否为第一个审批人
        Boolean firstCheck = getFirstCheck(nodeId,userId,myJdbcTemplate);

        if ("costContractOperatorCheck".equals(status)){
            if (firstCheck == true){
                //成本/合约/需求审批-清除数据
                clearPM_BID_APPROVAL_REQData("OPERATOR_ONE_ID,AD_USER_THREE_ID,AD_USER_FOUR_ID",csCommId,myJdbcTemplate);
            }
            //判断当前登录人岗位
            String deptName = getUserDept(myJdbcTemplate,userId,csCommId,nodeId);
            //根据岗位判断修改数据
            updatePM_BID_APPROVAL_REQDate(deptName,file,comment,csCommId,myJdbcTemplate);
        } else if ("costContractOperatorCheckRefuse".equals(status)){
            //成本/合约/需求审批-清除数据
            clearPM_BID_APPROVAL_REQData("OPERATOR_ONE_ID,AD_USER_THREE_ID,AD_USER_FOUR_ID",csCommId,myJdbcTemplate);
        } else if ("costContractOperatorLegalFinanceCheck".equals(status)){
            if (firstCheck == true){
                //成本/合约/需求/财务/法务部审批-清除数据
                clearPM_BID_APPROVAL_REQData("OPERATOR_ONE_ID,AD_USER_THREE_ID,AD_USER_FOUR_ID,AD_USER_EIGHTH_ID,AD_USER_NINTH_ID",csCommId,myJdbcTemplate);
            }
            //判断当前登录人岗位
            String deptName = getUserDept(myJdbcTemplate,userId,csCommId,nodeId);
            updatePM_BID_APPROVAL_REQDate(deptName,file,comment,csCommId,myJdbcTemplate);
        } else if ("costContractOperatorLegalFinanceCheckRefuse".equals(status)){
            clearPM_BID_APPROVAL_REQData("OPERATOR_ONE_ID,AD_USER_THREE_ID,AD_USER_FOUR_ID,AD_USER_EIGHTH_ID,AD_USER_NINTH_ID",csCommId,myJdbcTemplate);
        } else if ("lawyerCheckRefuse".equals(status)){
            clearPM_BID_APPROVAL_REQData("lawyer",csCommId,myJdbcTemplate);
        } else if ("lawyerCheck".equals(status)){
            if (firstCheck == true){
                //采购部负责人-清除数据
                clearPM_BID_APPROVAL_REQData("lawyer",csCommId,myJdbcTemplate);
            }
            updatePM_BID_APPROVAL_REQDate("lawyer",file,comment,csCommId,myJdbcTemplate);
        } else if ("buyLeaderCheck".equals(status)){
            if (firstCheck == true){
                //采购部负责人-清除数据
                clearPM_BID_APPROVAL_REQData("buyLeader",csCommId,myJdbcTemplate);
            }
            updatePM_BID_APPROVAL_REQDate("buyLeader",file,comment,csCommId,myJdbcTemplate);
        } else if ("buyLeaderCheckRefuse".equals(status)){
            clearPM_BID_APPROVAL_REQData("buyLeader",csCommId,myJdbcTemplate);
        }
    }

    /**
     * 获取审批意见
     * @param type 意见类型(user_comment文本/USER_ATTACHMENT文件)
     * @param procInstId 流程id
     * @param userId 当前登录人id
     * @param myJdbcTemplate 数据源
     * @return
     */
    private String getCommentValue(String type, String procInstId, String userId,MyJdbcTemplate myJdbcTemplate) {
        String value = null;
        //获取审批意见
        String sql = "select tk.USER_COMMENT,tk.USER_ATTACHMENT from wf_node_instance ni " +
                "join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id and tk.status = 'ap' " +
                "join ad_user u on tk.ad_user_id=u.id " +
                "where u.id = ?";
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql, procInstId,userId);
        if (!CollectionUtils.isEmpty(list)) {
            if ("user_comment".equals(type)){
                value = SharedUtil.isEmptyString(JdbcMapUtil.getString(list.get(0),"user_comment"))  ? "同意" : JdbcMapUtil.getString(list.get(0),"user_comment");
            } else if ("USER_ATTACHMENT".equals(type)){
                value = SharedUtil.isEmptyString(JdbcMapUtil.getString(list.get(0),"USER_ATTACHMENT"))  ? "" : JdbcMapUtil.getString(list.get(0),"USER_ATTACHMENT");
            }
        }
        return value;
    }

    /**
     * 判断当前是否为第一个审批人
     * @param nodeId 节点id
     * @param userId 当前操作人id
     * @param myJdbcTemplate 数据源
     * @return
     */
    private Boolean getFirstCheck(String nodeId, String userId, MyJdbcTemplate myJdbcTemplate) {
        //判断是否是当轮拒绝回来的、撤销回来的（是否是第一个进入该节点审批的人）
        String sql2 = "select count(*) as num from wf_task where WF_NODE_INSTANCE_ID = ? and IS_CLOSED = 1 and AD_USER_ID != ? and status = 'ap'";
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,nodeId,userId);
        Boolean firstCheck = true; //是第一次审批
        if (!CollectionUtils.isEmpty(list2)){
            String num = JdbcMapUtil.getString(list2.get(0),"num");
            if (!SharedUtil.isEmptyString(num) && !"0".equals(num)){
                firstCheck = false;
            }
        }
        return firstCheck;
    }

    /**
     * 意见回显
     * @param deptName 岗位字段
     * @param file 文件值
     * @param comment 意见值
     * @param csCommId 业务数据表id
     * @param myJdbcTemplate 数据库连接
     */
    private void updatePM_BID_APPROVAL_REQDate(String deptName, String file, String comment, String csCommId, MyJdbcTemplate myJdbcTemplate) {
        StringBuilder sb = new StringBuilder("update PM_BID_APPROVAL_REQ set ");
        if ("OPERATOR_ONE_ID".equals(deptName)){
            sb.append("FILE_ID_FOUR = ?,APPROVAL_COMMENT_THREE = ?");
        } else if ("AD_USER_THREE_ID".equals(deptName)){
            sb.append("FILE_ID_FIVE = ?,APPROVAL_COMMENT_FOUR = ?");
        } else if ("AD_USER_FOUR_ID".equals(deptName)){
            sb.append("FILE_ID_SIX = ?,APPROVAL_COMMENT_SIX = ?");
        } else if ("AD_USER_EIGHTH_ID".equals(deptName)){
            sb.append("FILE_ID_EIGHTH = ?,APPROVAL_COMMENT_EIGHTH = ?");
        } else if ("AD_USER_NINTH_ID".equals(deptName)){
            sb.append("FILE_ID_NINTH = ?,APPROVAL_COMMENT_NINTH = ?");
        } else if ("lawyer".equals(deptName)){
            sb.append("FILE_ID_SEVEN = ?,APPROVAL_COMMENT_SEVEN = ?");
        } else if ("buyLeader".equals(deptName)){
            sb.append("FILE_ID_TENTH = ?,APPROVAL_COMMENT_TENTH = ?");
        }
        sb.append(" where id = ?");
        Integer exec = myJdbcTemplate.update(sb.toString(),file,comment,csCommId);
        log.info("已更新：{}",exec);
    }

    // 招标文件流程-数据清除
    private void clearPM_BID_APPROVAL_REQData(String str, String csCommId,MyJdbcTemplate myJdbcTemplate) {
        String[] arr = str.split(",");
        StringBuilder sb = new StringBuilder("update PM_BID_APPROVAL_REQ set ");
        for (String tmp : arr) {
            if ("OPERATOR_ONE_ID".equals(tmp)){
                sb.append("FILE_ID_FOUR = null,APPROVAL_COMMENT_THREE = null,");
            } else if ("AD_USER_THREE_ID".equals(tmp)){
                sb.append("FILE_ID_FIVE = null,APPROVAL_COMMENT_FOUR = null,");
            } else if ("AD_USER_FOUR_ID".equals(tmp)){
                sb.append("FILE_ID_SIX = null,APPROVAL_COMMENT_SIX = null,");
            } else if ("AD_USER_EIGHTH_ID".equals(tmp)){
                sb.append("FILE_ID_EIGHTH = null,APPROVAL_COMMENT_EIGHTH = null,");
            } else if ("AD_USER_NINTH_ID".equals(tmp)){
                sb.append("FILE_ID_NINTH = null,APPROVAL_COMMENT_NINTH = null,");
            } else if ("lawyer".equals(tmp)){
                sb.append("FILE_ID_SEVEN = null,APPROVAL_COMMENT_SEVEN = null,");
            } else if ("buyLeader".equals(tmp)){
                sb.append("FILE_ID_TENTH = null,APPROVAL_COMMENT_TENTH = null,");
            }
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(" where id = ?");
        Integer exec = myJdbcTemplate.update(sb.toString(),csCommId);
        log.info("已更新：{}",exec);
    }

    private String getUserDept(MyJdbcTemplate myJdbcTemplate,String userId, String csCommId,String nodeInstanceId) {
        //判断是否是转办来的
        userId = ProcessCommon.getOriginalUser(nodeInstanceId,userId,myJdbcTemplate);
        String sql3 = "select OPERATOR_ONE_ID,AD_USER_THREE_ID,AD_USER_FOUR_ID,AD_USER_EIGHTH_ID,AD_USER_NINTH_ID from PM_BID_APPROVAL_REQ where id = ?";
        Map<String,Object> map = myJdbcTemplate.queryForMap(sql3,csCommId);
        return getDeptName(map,userId);
    }

    //判断当前登录人在流程中的岗位
    private String getDeptName(Map<String, Object> map,String userId) {
        String name = "";
        Set<String> set = map.keySet();
        for (String tmp : set) {
            String value = map.get(tmp).toString();
            if (value.contains(userId)){
                return tmp;
            }
        }
        return name;
    }

    /**
     * 招标文件审批-结束时数据校验处理
     */
    public void bidApprovalEndCheck(){
        //插入日志结束
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //流程业务表id
        String csCommId = entityRecord.csCommId;
        //计算公示日期
        Date bidDate = DateTimeUtil.stringToDate(JdbcMapUtil.getString(entityRecord.valueMap,"DATE_TWO")); //开标日期
        Date publicDate = DateTimeUtil.addDays(bidDate,3);
        Crud.from("PM_BID_APPROVAL_REQ").where().eq("id",csCommId).update().set("DATE_THREE",publicDate).exec();
        //项目信息写入明细表
        String projectIds = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_IDS");
        BidApprovalPrjDetailExt.createData(csCommId,projectIds);
    }

    /**
     * 招标文件审批-历史数据处理
     */
    public void bidApprovalHistory(){

        //处理非系统项目，存储进系统项目库
        List<PmBidApprovalReq> noPrjList = PmBidApprovalReq.selectByWhere(new Where().nin(PmBidApprovalReq.Cols.STATUS,"VD","VDING")
                .eq(PmBidApprovalReq.Cols.PROJECT_SOURCE_TYPE_ID,"0099952822476441375"));
        if (!CollectionUtils.isEmpty(noPrjList)){
            for (PmBidApprovalReq tmp : noPrjList) {
                String id = tmp.getId();
                String projectName = tmp.getProjectNameWr();
                String projectId = PmPrjExt.createPrjByMoreName(projectName);
                Crud.from(PmBidApprovalReq.ENT_CODE).where().eq(PmBidApprovalReq.Cols.ID,id).update()
                        .set(PmBidApprovalReq.Cols.PM_PRJ_IDS,projectId)
                        .exec();
            }
        }

        //多选项目id赋值
        List<PmBidApprovalReq> list = PmBidApprovalReq.selectByWhere(new Where().nin(PmBidApprovalReq.Cols.STATUS,"VD","VDING"));
        if (!CollectionUtils.isEmpty(list)){
            for (PmBidApprovalReq tmp : list) {
                String type = tmp.getProjectSourceTypeId();
                String id = tmp.getId();
                String projectId = "";
                if ("0099952822476441375".equals(type)){ //非系统
                    projectId = tmp.getPmPrjIds();
                } else {
                    projectId = tmp.getPmPrjId();
                    if (SharedUtil.isEmptyString(projectId)){
                        projectId = tmp.getPmPrjIds();
                    }
                }
                Crud.from(PmBidApprovalReq.ENT_CODE).where().eq(PmBidApprovalReq.Cols.ID,id).update()
                        .set(PmBidApprovalReq.Cols.PM_PRJ_IDS,projectId).exec();
            }
        }

        //审批通过的数据写入明细表
        List<PmBidApprovalReq> list2 = PmBidApprovalReq.selectByWhere(new Where().eq(PmBidApprovalReq.Cols.STATUS,"AP"));
        if (!CollectionUtils.isEmpty(list2)){
            for (PmBidApprovalReq tmp : list2) {
                String projectId = tmp.getPmPrjIds();
                if (!SharedUtil.isEmptyString(projectId)){
                    BidApprovalPrjDetailExt.createData(tmp.getId(),projectId);
                }
            }
        }

    }

    /**
     * 流程操作-招标文件审批-确定按钮
     */
    public void approvalProcessOK(){
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 流程操作-招标文件审批-拒绝按钮
     */
    public void approvalProcessRefuse(){
        String status = "refuse";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 流程流转详细处理逻辑
     * @param nodeStatus 节点状态码
     * @param status 操作状态码
     */
    public void processHandle(String nodeStatus, String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        String procInstId = ExtJarHelper.procInstId.get();
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        String nodeId = ExtJarHelper.nodeId.get();
        if ("OK".equals(status)){
            if ("start".equals(nodeStatus)){ // 1-发起
                WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
            } else {
                //获取审批意见信息
                Map<String,String> message = ProcessCommon.getCommentNew(nodeInstanceId,userId,myJdbcTemplate,procInstId,userName);
                //审批意见内容
                String comment = message.get("comment");
                String file = message.get("file");
                String processComment, processFile, commentEnd, fileEnd;
                if ("buyLeader".equals(nodeStatus)){ // 2-采购部负责人审批
                    //获取流程中的意见信息
                    processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_TENTH");
                    commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                    ProcessCommon.commentShow("APPROVAL_COMMENT_TENTH",commentEnd,csCommId,entCode);
                    ProcessCommon.commentShow("FILE_ID_TENTH",file,csCommId,entCode);
                } else if ("costOrderOperator".equals(nodeStatus)){ // 3-成本/合约/需求经办人审批
                    String deptId = getUserDept(myJdbcTemplate,userId,csCommId,nodeId);
                    if ("AD_USER_THREE_ID".equals(deptId)){ //成本岗
                        processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_FOUR");
                        commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                        ProcessCommon.commentShow("APPROVAL_COMMENT_FOUR",commentEnd,csCommId,entCode);
                        ProcessCommon.commentShow("FILE_ID_FIVE",file,csCommId,entCode);
                    } else if ("AD_USER_FOUR_ID".equals(deptId)){ // 合同岗
                        processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_SIX");
                        commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                        ProcessCommon.commentShow("APPROVAL_COMMENT_SIX",commentEnd,csCommId,entCode);
                        ProcessCommon.commentShow("FILE_ID_SIX",file,csCommId,entCode);
                    } else if ("OPERATOR_ONE_ID".equals(deptId)){ //经办人
                        processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_THREE");
                        commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                        ProcessCommon.commentShow("APPROVAL_COMMENT_THREE",commentEnd,csCommId,entCode);
                        ProcessCommon.commentShow("FILE_ID_FOUR",file,csCommId,entCode);
                    }
                } else if ("lawyerOK".equals(nodeStatus)){ // 4-法律审批
                    WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
                    processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_SEVEN");
                    commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                    ProcessCommon.commentShow("APPROVAL_COMMENT_SEVEN",commentEnd,csCommId,entCode);
                    ProcessCommon.commentShow("FILE_ID_SEVEN",file,csCommId,entCode);
                } else if ("costOrderFinance".equals(nodeStatus)){ // 5-成本/合约/需求/财务/法务部审批
                    String deptId = getUserDept(myJdbcTemplate,userId,csCommId,nodeId);
                    if ("AD_USER_THREE_ID".equals(deptId)){ //成本岗
                        processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_FOUR");
                        commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                        ProcessCommon.commentShow("APPROVAL_COMMENT_FOUR",commentEnd,csCommId,entCode);
                        ProcessCommon.commentShow("FILE_ID_FIVE",file,csCommId,entCode);
                    } else if ("AD_USER_FOUR_ID".equals(deptId)){ // 合同岗
                        processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_SIX");
                        commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                        ProcessCommon.commentShow("APPROVAL_COMMENT_SIX",commentEnd,csCommId,entCode);
                        ProcessCommon.commentShow("FILE_ID_SIX",file,csCommId,entCode);
                    } else if ("OPERATOR_ONE_ID".equals(deptId)){ //经办人
                        processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_THREE");
                        commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                        ProcessCommon.commentShow("APPROVAL_COMMENT_THREE",commentEnd,csCommId,entCode);
                        ProcessCommon.commentShow("FILE_ID_FOUR",file,csCommId,entCode);
                    } else if ("AD_USER_EIGHTH_ID".equals(deptId)){ //法务岗
                        processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_EIGHTH");
                        commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                        ProcessCommon.commentShow("APPROVAL_COMMENT_EIGHTH",commentEnd,csCommId,entCode);
                        ProcessCommon.commentShow("FILE_ID_EIGHTH",file,csCommId,entCode);
                    } else if ("AD_USER_NINTH_ID".equals(deptId)){ //财务岗
                        processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_NINTH");
                        commentEnd = ProcessCommon.getNewCommentStr(userName,processComment,comment);
                        ProcessCommon.commentShow("APPROVAL_COMMENT_NINTH",commentEnd,csCommId,entCode);
                        ProcessCommon.commentShow("FILE_ID_NINTH",file,csCommId,entCode);
                    }
                }
            }
        } else {
            if ("buyLeaderRefuse".equals(nodeStatus)){ // 2-采购部负责人审批
                ProcessCommon.clearData("APPROVAL_COMMENT_TENTH,FILE_ID_TENTH",csCommId,entCode,myJdbcTemplate);
            } else if ("costOrderOperatorRefuse".equals(nodeStatus)){ // 3-成本/合约/需求经办人审批
                ProcessCommon.clearData("FILE_ID_FIVE,APPROVAL_COMMENT_FOUR,FILE_ID_SIX,APPROVAL_COMMENT_SIX,FILE_ID_FOUR,APPROVAL_COMMENT_THREE",csCommId,entCode,myJdbcTemplate);
            } else if ("lawyerOKRefuse".equals(nodeStatus)){ // 4-法律审批
                ProcessCommon.clearData("APPROVAL_COMMENT_SEVEN,FILE_ID_SEVEN",csCommId,entCode,myJdbcTemplate);
            } else if ("costOrderFinanceRefuse".equals(nodeStatus)){ // 5-成本/合约/需求/财务/法务部审批
                ProcessCommon.clearData("FILE_ID_FIVE,APPROVAL_COMMENT_FOUR,FILE_ID_SIX,APPROVAL_COMMENT_SIX,FILE_ID_FOUR,APPROVAL_COMMENT_THREE,FILE_ID_NINTH,APPROVAL_COMMENT_NINTH,FILE_ID_EIGHTH,APPROVAL_COMMENT_EIGHTH",csCommId,entCode,myJdbcTemplate);
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
            if ("1612712172176171008".equals(nodeId)){ // 1-发起
                nodeName = "start";
            } else if ("1612712172583018496".equals(nodeId)){ // 2-采购部负责人审批
                nodeName = "buyLeader";
            } else if ("1612712172352331776".equals(nodeId)){ // 3-成本/合约/需求经办人审批
                nodeName = "costOrderOperator";
            } else if ("1612712172373303296".equals(nodeId)){ // 4-法律审批
                nodeName = "lawyerOK";
            } else if ("1612713839856607232".equals(nodeId)){ // 5-成本/合约/需求/财务/法务部审批
                nodeName = "costOrderFinance";
            }
        } else {
            if ("1612712172583018496".equals(nodeId)){ // 2-采购部负责人审批
                nodeName = "buyLeaderRefuse";
            } else if ("1612712172352331776".equals(nodeId)){ // 3-成本/合约/需求经办人审批
                nodeName = "costOrderOperatorRefuse";
            } else if ("1612712172373303296".equals(nodeId)){ // 4-法律审批
                nodeName = "lawyerOKRefuse";
            } else if ("1612713839856607232".equals(nodeId)){ // 5-成本/合约/需求/财务/法务部审批
                nodeName = "costOrderFinanceRefuse";
            }
        }
        return nodeName;
    }
}
