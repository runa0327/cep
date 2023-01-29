package com.cisdi.ext.pm;

import com.cisdi.ext.util.DateTimeUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 采购合同变更申请 扩展
 */
@Slf4j
public class PoOrderChangeReqExt {
    /**
     * 采购合同变更申请-按照先后顺序审批-第一次审批
     */
    public void checkFirst() {
        String status = "first";
        checkFirst(status);
    }

    /**
     * 采购合同变更申请 审批先后顺序 第二次审批
     */
    public void checkSecond() {
        String status = "second";
        checkFirst(status);
    }

    /**
     * 采购合同变更申请 审批先后顺序 第三次审批
     */
    public void checkThird() {
        String status = "third";
        checkFirst(status);
    }

    /**
     * 采购合同变更申请 审批先后顺序 第四次审批
     */
    public void checkFourth() {
        String status = "fourth";
        checkFirst(status);
    }

    /**
     * 采购合同变更申请 审批先后顺序 第五次审批
     */
    public void checkFifth() {
        String status = "fifth";
        checkFirst(status);
    }

    private void checkFirst(String status) {
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
            Integer exec = Crud.from("PO_ORDER_CHANGE_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_ONE", userId).set("APPROVAL_DATE_ONE", now).set("DEPT_HEAD_APPROVAL_MESSAGE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("second".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_CHANGE_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_TWO", userId).set("APPROVAL_DATE_TWO", now).set("COST_CONTRACT_APPROVAL_MESSAGE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("third".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_CHANGE_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_THREE", userId).set("APPROVAL_DATE_THREE", now).set("FINANCE_LEGAL_APPROVAL_MESSAGE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("fourth".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_CHANGE_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_FOUR", userId).set("APPROVAL_DATE_FOUR", now).set("APPROVAL_COMMENT_FOUR", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("fifth".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_CHANGE_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_FIVE", userId).set("APPROVAL_DATE_FIVE", now).set("LEADER_APPROVE_COMMENT", comment).exec();
            log.info("已更新：{}", exec);
        }
    }

    //合同变更申请通过同步数据
    public void createContract(){
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        try {
            Map<String, Object> contractDataMap = myJdbcTemplate.queryForMap("select * from PO_ORDER_CHANGE_REQ where ID=?", csCommId);

            String id = Crud.from("PO_ORDER").insertData();
            insertContract(id, contractDataMap,entityRecord);
//            List<Map<String, Object>> detailList = myJdbcTemplate.queryForList("select * from PM_ORDER_COST_DETAIL where CONTRACT_ID=?", csCommId);
//            insertContractDet(id, detailList);
            //查询联系人明细
            List<Map<String,Object>> list2 = myJdbcTemplate.queryForList("select OPPO_SITE_LINK_MAN,OPPO_SITE_CONTACT from CONTRACT_CHANGE_CONTACT where PARENT_ID = ?",csCommId);
            if (!CollectionUtils.isEmpty(list2)){
                insertContacts(id,list2);
            }
        } catch (Exception e) {
            throw new BaseException("查询合同申请数据异常！", e);
        }
    }

    //联系人明细信息
    private void insertContacts(String id, List<Map<String, Object>> list2) {
        String now = DateTimeUtil.dttmToString(new Date());
        list2.forEach(item -> {
            String did = Crud.from("PO_ORDER_CONTACTS").insertData();
            Crud.from("PO_ORDER_CONTACTS").where().eq("ID", did).update()
                    .set("OPPO_SITE_LINK_MAN", JdbcMapUtil.getString(item, "OPPO_SITE_LINK_MAN"))
                    .set("OPPO_SITE_CONTACT", JdbcMapUtil.getString(item, "OPPO_SITE_CONTACT"))
                    .set("PARENT_ID", id)
                    .set("CRT_DT",now)
                    .exec();
        });
    }

    private void insertContract(String id, Map<String, Object> data,EntityRecord entityRecord) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //查询合同名称
        Map<String,Object> map = myJdbcTemplate.queryForMap("select name from PO_ORDER_REQ where id = ?",entityRecord.valueMap.get("CONTRACT_ID").toString());
        String name = map.get("name").toString();
        Crud.from("PO_ORDER").where().eq("ID", id).update()
                .set("NAME", JdbcMapUtil.getString(data, "CONTRACT_NAME"))
                .set("REMARK", JdbcMapUtil.getString(data, "REMARK"))
                .set("CONTRACT_AMOUNT", JdbcMapUtil.getString(data, "CONTRACT_AMOUNT"))
                .set("AGENT", JdbcMapUtil.getString(data, ""))
                .set("AGENT_PHONE", JdbcMapUtil.getString(data, ""))
                .set("FILE_ATTACHMENT_URL", JdbcMapUtil.getString(data, "ATT_FILE_GROUP_ID"))
                .set("PM_PRJ_ID", JdbcMapUtil.getString(data, "PM_PRJ_ID"))
                .set("CONTRACT_APP_ID",JdbcMapUtil.getString(data,"ID"))
                .set("ORDER_PROCESS_TYPE","合同变更")
                .set("name",name)
                .exec();
    }

    /**
     * 合同需求审批-法务财务拒绝
     */
    public void legalFinanceRefuse(){
        String status = "legalFinanceRefuse";
        flowAct(status);
    }
    /**
     * 合同需求审批-法务拒绝
     */
    public void legalRefuse(){
        String status = "legalRefuse";
        flowAct(status);
    }
    /**
     * 合同需求审批-财务拒绝
     */
    public void financeRefuse(){
        String status = "financeRefuse";
        flowAct(status);
    }
    /**
     * 合同需求审批-律师拒绝
     */
    public void lawyerRefuse(){
        String status = "lawyerRefuse";
        flowAct(status);
    }


    private void flowAct(String status) {
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        if ("legalFinanceRefuse".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_CHANGE_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_COMMENT_TWO", null).set("FILE_ID_THREE", null)
                    .set("APPROVAL_COMMENT_THREE", null).set("FILE_ID_FOUR",null).exec();
            log.info("已更新：{}", exec);
        } else if ("legalRefuse".equals(status)){
            Integer exec = Crud.from("PO_ORDER_CHANGE_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_COMMENT_TWO", null).set("FILE_ID_THREE", null).exec();
            log.info("已更新：{}", exec);
        } else if ("financeRefuse".equals(status)){
            Integer exec = Crud.from("PO_ORDER_CHANGE_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_COMMENT_THREE", null).set("FILE_ID_FOUR",null).exec();
            log.info("已更新：{}", exec);
        } else if ("lawyerRefuse".equals(status)){
            Integer exec = Crud.from("PO_ORDER_CHANGE_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_COMMENT_ONE", null).set("FILE_ID_TWO",null).exec();
            log.info("已更新：{}", exec);
        }
    }

    /**
     * 合同变更审批-第二版审批流扩展-审批通过
     */
    public void flowExtTrueSecond(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //当前节点id
        String nodeId = ExtJarHelper.nodeInstId.get();
        //定义节点状态
        String nodeStatus = getStatus("true",nodeId,myJdbcTemplate);
        //详细处理逻辑
        handleCHeckData(nodeStatus,nodeId,myJdbcTemplate);
    }

    /**
     * 合同变更审批-第二版审批流扩展-审批驳回
     */
    public void flowExtFalseSecond(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //当前节点id
        String nodeId = ExtJarHelper.nodeInstId.get();
        //定义节点状态
        String nodeStatus = getStatus("false",nodeId,myJdbcTemplate);
        //详细处理逻辑
        handleCHeckData(nodeStatus,nodeId,myJdbcTemplate);
    }

    /**
     * 合同需求审批-流程扩展处理详情逻辑
     * @param nodeStatus 节点信息
     * @param nodeId 节点id信息
     * @param myJdbcTemplate 数据源
     */
    private void handleCHeckData(String nodeStatus,String nodeId,MyJdbcTemplate myJdbcTemplate) {
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        // 流程id
        String procInstId = ExtJarHelper.procInstId.get();
        // 当前登录人
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;
        //获取审批意见信息
        Map<String,String> message = getMessage(nodeId,userId,myJdbcTemplate,procInstId,userName);

        //审批意见内容
        String file = message.get("file");
        String comment = message.get("comment");

        //判断当前是否为第一个审批人
        Boolean firstCheck = ProcessCommon.getFirstCheck(nodeId,userId,myJdbcTemplate);

        //分支判断
        if ("caiHuaTrue".equals(nodeStatus)){ //才华审批通过
            if (firstCheck){
                //才华审批意见清除
                clearPO_ORDER_CHANGE_REQ("caiHua",csCommId,myJdbcTemplate);
            }
            //审批意见数据回显
            updatePO_ORDER_CHANGE_REQ("caiHua",file,comment,csCommId,myJdbcTemplate);
        } else if ("caiHuaFalse".equals(nodeStatus)){ //才华审批拒绝
            //才华审批意见清除
            clearPO_ORDER_CHANGE_REQ("caiHua",csCommId,myJdbcTemplate);
        } else if ("costTrue".equals(nodeStatus)){ //成本岗审批
            if (firstCheck){
                //成本岗审批意见清除
                clearPO_ORDER_CHANGE_REQ("AD_USER_THREE_ID",csCommId,myJdbcTemplate);
            }
            //审批意见数据回显
            updatePO_ORDER_CHANGE_REQ("AD_USER_THREE_ID",file,comment,csCommId,myJdbcTemplate);
        } else if ("costFalse".equals(nodeStatus)){
            //成本岗审批意见清除
            clearPO_ORDER_CHANGE_REQ("AD_USER_THREE_ID",csCommId,myJdbcTemplate);
        } else if ("financeTrue".equals(nodeStatus)){ //财务岗审批
            if (firstCheck){
                //财务岗审批意见清除
                clearPO_ORDER_CHANGE_REQ("AD_USER_NINTH_ID",csCommId,myJdbcTemplate);
            }
            //审批意见数据回显
            updatePO_ORDER_CHANGE_REQ("AD_USER_NINTH_ID",file,comment,csCommId,myJdbcTemplate);
        } else if ("financeFalse".equals(nodeStatus)){ // 财务岗拒绝
            //财务岗审批意见清除
            clearPO_ORDER_CHANGE_REQ("AD_USER_NINTH_ID",csCommId,myJdbcTemplate);
        } else if ("costFinanceTrue".equals(nodeStatus)){ //成本/财务审批
            if (firstCheck){
                //成本/财务审批意见清除
                clearPO_ORDER_CHANGE_REQ("AD_USER_THREE_ID,AD_USER_NINTH_ID",csCommId,myJdbcTemplate);
            }
            //判断当前登录人岗位信息
            String deptName = getUserDept(myJdbcTemplate,userId,csCommId);
            //审批意见数据回显
            updatePO_ORDER_CHANGE_REQ(deptName,file,comment,csCommId,myJdbcTemplate);
        } else if ("costFinanceFalse".equals(nodeStatus)){ // 成本/财务审批拒绝
            //成本/财务审批意见清除
            clearPO_ORDER_CHANGE_REQ("AD_USER_THREE_ID,AD_USER_NINTH_ID",csCommId,myJdbcTemplate);
        } else if ("lawyerTrue".equals(nodeStatus)){ //法律顾问审批通过
            if (firstCheck){
                //法律顾问审批意见清除
                clearPO_ORDER_CHANGE_REQ("lawyer",csCommId,myJdbcTemplate);
            }
            //审批意见数据回显
            updatePO_ORDER_CHANGE_REQ("lawyer",file,comment,csCommId,myJdbcTemplate);
        } else if ("lawyerFalse".equals(nodeStatus)){ // 法律顾问审批拒绝
            //法律顾问审批意见清除
            clearPO_ORDER_CHANGE_REQ("lawyer",csCommId,myJdbcTemplate);
        } else if ("legalTrue".equals(nodeStatus)){ //法务审批通过
            if (firstCheck){
                //法务审批意见清除
                clearPO_ORDER_CHANGE_REQ("AD_USER_EIGHTH_ID",csCommId,myJdbcTemplate);
            }
            //审批意见数据回显
            updatePO_ORDER_CHANGE_REQ("AD_USER_EIGHTH_ID",file,comment,csCommId,myJdbcTemplate);
        } else if ("legalFalse".equals(nodeStatus)){ // 法务审批拒绝
            //法务审批意见清除
            clearPO_ORDER_CHANGE_REQ("AD_USER_EIGHTH_ID",csCommId,myJdbcTemplate);
        } else if ("legalFinanceTrue".equals(nodeStatus)){ //法务/财务审批审批
            if (firstCheck){
                //法务/财务审批审批意见清除
                clearPO_ORDER_CHANGE_REQ("AD_USER_EIGHTH_ID,AD_USER_NINTH_ID",csCommId,myJdbcTemplate);
            }
            //判断当前登录人岗位信息
            String deptName = getUserDept(myJdbcTemplate,userId,csCommId);
            //审批意见数据回显
            updatePO_ORDER_CHANGE_REQ(deptName,file,comment,csCommId,myJdbcTemplate);
        } else if ("legalFinanceFalse".equals(nodeStatus)){ // 法务/财务审批拒绝
            //法务/财务审批审批意见清除
            clearPO_ORDER_CHANGE_REQ("AD_USER_EIGHTH_ID,AD_USER_NINTH_ID",csCommId,myJdbcTemplate);
        } else if ("costLegalFinanceTrue".equals(nodeStatus)){ //法务/财务/成本审批
            if (firstCheck){
                //法务/财务/成本审批意见清除
                clearPO_ORDER_CHANGE_REQ("AD_USER_THREE_ID,AD_USER_EIGHTH_ID,AD_USER_NINTH_ID",csCommId,myJdbcTemplate);
            }
            //判断当前登录人岗位信息
            String deptName = getUserDept(myJdbcTemplate,userId,csCommId);
            //审批意见数据回显
            updatePO_ORDER_CHANGE_REQ(deptName,file,comment,csCommId,myJdbcTemplate);
        } else if ("costLegalFinanceFalse".equals(nodeStatus)){ // 法务/财务/成本审批拒绝
            //法务/财务/成本审批意见清除
            clearPO_ORDER_CHANGE_REQ("AD_USER_THREE_ID,AD_USER_EIGHTH_ID,AD_USER_NINTH_ID",csCommId,myJdbcTemplate);
        } else if ("deptLeaderTrue".equals(nodeStatus)){ //部门领导审批通过
            if (firstCheck){
                //部门领导审批意见清除
                clearPO_ORDER_CHANGE_REQ("deptLeader",csCommId,myJdbcTemplate);
            }
            //审批意见数据回显
            updatePO_ORDER_CHANGE_REQ("deptLeader",file,comment,csCommId,myJdbcTemplate);
        } else if ("deptLeaderFalse".equals(nodeStatus)){ // 部门领导审批拒绝
            //部门领导审批意见清除
            clearPO_ORDER_CHANGE_REQ("deptLeader",csCommId,myJdbcTemplate);
        } else if ("chargeLeaderTrue".equals(nodeStatus)){ //分管领导审批通过
            if (firstCheck){
                //分管领导审批意见清除
                clearPO_ORDER_CHANGE_REQ("chargeLeader",csCommId,myJdbcTemplate);
            }
            //审批意见数据回显
            updatePO_ORDER_CHANGE_REQ("chargeLeader",file,comment,csCommId,myJdbcTemplate);
        } else if ("chargeLeaderFalse".equals(nodeStatus)){ // 分管领导审批拒绝
            //分管领导审批意见清除
            clearPO_ORDER_CHANGE_REQ("chargeLeader",csCommId,myJdbcTemplate);
        }
    }

    /**
     * 合同需求审批-数据回显
     * @param deptName 岗位信息
     * @param file 审批附件
     * @param comment 审批意见
     * @param csCommId 流程业务数据表id
     * @param myJdbcTemplate 数据库连接
     */
    private void updatePO_ORDER_CHANGE_REQ(String deptName, String file, String comment, String csCommId, MyJdbcTemplate myJdbcTemplate) {
        StringBuilder sb = new StringBuilder("update PO_ORDER_CHANGE_REQ set ");
        if ("caiHua".equals(deptName)){ //才华回显
            sb.append("FILE_ID_SEVEN = ?, APPROVAL_COMMENT_SEVEN = ?");
        } else if ("AD_USER_THREE_ID".equals(deptName)){ //成本岗回显
            sb.append("FILE_ID_EIGHTH = ?, APPROVAL_COMMENT_EIGHTH = ?");
        } else if ("AD_USER_NINTH_ID".equals(deptName)){ //财务岗回显
            sb.append("FILE_ID_FOUR = ?, APPROVAL_COMMENT_THREE = ?");
        } else if ("lawyer".equals(deptName)){ //法律意见回显
            sb.append("FILE_ID_TWO = ?, APPROVAL_COMMENT_ONE = ?");
        } else if ("AD_USER_EIGHTH_ID".equals(deptName)){ //法务意见回显
            sb.append("FILE_ID_THREE = ?, APPROVAL_COMMENT_TWO = ?");
        } else if ("deptLeader".equals(deptName)){ //部门领导意见回显
            sb.append("FILE_ID_FIVE = ?, APPROVAL_COMMENT_FOUR = ?");
        } else if ("chargeLeader".equals(deptName)){ //分管领导意见回显
            sb.append("FILE_ID_SIX = ?, APPROVAL_COMMENT_FIVE = ?");
        }
        sb.append(" where id = ?");
        Integer exec = myJdbcTemplate.update(sb.toString(),file,comment,csCommId);
        log.info("已更新：{}",exec);
    }

    /**
     * 数据清除
     * @param str 需要情况的字段
     * @param csCommId 流程业务表id
     * @param myJdbcTemplate 数据源
     */
    private void clearPO_ORDER_CHANGE_REQ(String str, String csCommId, MyJdbcTemplate myJdbcTemplate) {
        String[] arr = str.split(",");
        StringBuilder sb = new StringBuilder("update PO_ORDER_CHANGE_REQ set ");
        for (String tmp : arr) {
            if ("caiHua".equals(tmp)){ //才华清除
                sb.append("APPROVAL_COMMENT_SEVEN = null,FILE_ID_SEVEN = null,");
            } else if ("AD_USER_THREE_ID".equals(tmp)){ //成本岗清除
                sb.append("APPROVAL_COMMENT_EIGHTH = null,FILE_ID_EIGHTH = null,");
            } else if ("AD_USER_NINTH_ID".equals(tmp)){ //财务岗清除
                sb.append("FILE_ID_FOUR = null, APPROVAL_COMMENT_THREE = null,");
            }  else if ("lawyer".equals(tmp)){ //法律意见清除
                sb.append("FILE_ID_TWO = null, APPROVAL_COMMENT_ONE = null,");
            } else if ("AD_USER_EIGHTH_ID".equals(tmp)){ //法务意见清除
                sb.append("FILE_ID_THREE = null, APPROVAL_COMMENT_TWO = null,");
            } else if ("deptLeader".equals(tmp)){ //部门领导意见清除
                sb.append("FILE_ID_FIVE = null, APPROVAL_COMMENT_FOUR = null,");
            } else if ("chargeLeader".equals(tmp)){ //分管领导意见清除
                sb.append("FILE_ID_SIX = null, APPROVAL_COMMENT_FIVE = null,");
            }
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(" where id = ?");
        Integer exec = myJdbcTemplate.update(sb.toString(),csCommId);
        log.info("已更新：{}",exec);
    }

    /**
     * 获取流程当前审批人审批操作时评价信息
     * @param nodeId 节点id
     * @param userId 当前操作人id
     * @param myJdbcTemplate 数据库源
     * @param procInstId 流程id
     * @param userName 审批人名称
     * @return 审批人审批信息
     */
    private Map<String, String> getMessage(String nodeId, String userId, MyJdbcTemplate myJdbcTemplate, String procInstId, String userName) {
        Map<String,String> map = new HashMap<>();
        String sql = "select a.USER_COMMENT,a.USER_ATTACHMENT from wf_task a " +
                "left join wf_node_instance b on a.WF_NODE_INSTANCE_ID = b.id and b.status = 'ap' " +
                "where b.id = ? and a.AD_USER_ID = ? and a.status = 'ap' and a.IS_CLOSED = 1 and a.WF_PROCESS_INSTANCE_ID = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,nodeId,userId,procInstId);
        if (!CollectionUtils.isEmpty(list)){
            String value = SharedUtil.isEmptyString(JdbcMapUtil.getString(list.get(0),"USER_COMMENT")) ? "同意" : JdbcMapUtil.getString(list.get(0),"USER_COMMENT");
            map.put("comment",userName + "：" + value);
            map.put("file",JdbcMapUtil.getString(list.get(0),"USER_ATTACHMENT"));
        }
        return map;
    }

    // 节点状态赋值
    private String getStatus(String status, String wfNodeId, MyJdbcTemplate myJdbcTemplate) {
        //根据节点实例id查询流程节点id
        String sql = "select WF_NODE_ID from wf_node_instance where id = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,wfNodeId);
        String nodeId = JdbcMapUtil.getString(list.get(0),"WF_NODE_ID");
        String name = "";
        if ("true".equals(status)){
            if ("1619159246757302272".equals(nodeId)){ //2-才华预审
                name = "caiHuaTrue";
            } else if ("1619158462116270080".equals(nodeId)){ //3-成本岗审批
                name = "costTrue";
            } else if ("1619158462284042240".equals(nodeId)){ //4-财务岗审批
                name = "financeTrue";
            } else if ("1619158462313402368".equals(nodeId)){ //5-成本/财务审批
                name = "costFinanceTrue";
            } else if ("1619158462460203008".equals(nodeId)){ //6-法律顾问审批
                name = "lawyerTrue";
            } else if ("1619179426375929856".equals(nodeId)){ //7-法务审批
                name = "legalTrue";
            } else if ("1619186764008787968".equals(nodeId)){ //8-法务/财务审批
                name = "legalFinanceTrue";
            } else if ("1619186850457587712".equals(nodeId)){ //9-法务/财务/成本审批
                name = "costLegalFinanceTrue";
            } else if ("1619158462435037184".equals(nodeId)){ //10-部门领导审批
                name = "deptLeaderTrue";
            } else if ("1619158462342762496".equals(nodeId)){ //11-分管领导审批
                name = "chargeLeaderTrue";
            }
        } else if ("false".equals(status)){
            if ("1619159246757302272".equals(nodeId)){ //2-才华预审
                name = "caiHuaFalse";
            } else if ("1619158462116270080".equals(nodeId)){ //3-成本岗审批
                name = "costFalse";
            } else if ("1619158462284042240".equals(nodeId)){ //4-财务岗审批
                name = "financeFalse";
            } else if ("1619158462313402368".equals(nodeId)){ //5-成本/财务审批
                name = "costFinanceFalse";
            } else if ("1619158462460203008".equals(nodeId)){ //6-法律顾问审批
                name = "lawyerFalse";
            } else if ("1619179426375929856".equals(nodeId)){ //7-法务审批
                name = "legalFalse";
            } else if ("1619186764008787968".equals(nodeId)){ //8-法务/财务审批
                name = "legalFinanceFalse";
            } else if ("1619186850457587712".equals(nodeId)){ //9-法务/财务/成本审批
                name = "costLegalFinanceFalse";
            } else if ("1619158462435037184".equals(nodeId)){ //10-部门领导审批
                name = "deptLeaderFalse";
            } else if ("1619158462342762496".equals(nodeId)){ //11-分管领导审批
                name = "chargeLeaderFalse";
            }
        }
        return name;
    }



    /**
     * 查询人员在该流程中的岗位
     * @param myJdbcTemplate 数据源
     * @param userId 当前登录人id
     * @param csCommId 流程实例id
     * @return
     */
    private String getUserDept(MyJdbcTemplate myJdbcTemplate, String userId, String csCommId) {
        String sql3 = "select AD_USER_THREE_ID,AD_USER_EIGHTH_ID,AD_USER_NINTH_ID from PO_ORDER_CHANGE_REQ where id = ?";
        Map<String,Object> map = myJdbcTemplate.queryForMap(sql3,csCommId);
        return getDeptName(map,userId);
    }

    //判断当前登录人在流程中的岗位
    private String getDeptName(Map<String, Object> map,String userId) {
        String name = "";
        Set<String> set = map.keySet();
        for (String tmp : set) {
            Object object = map.get(tmp);
            if (object != null){
                String value = object.toString();
                if (value.contains(userId)){
                    return tmp;
                }
            }

        }
        return name;
    }

}
