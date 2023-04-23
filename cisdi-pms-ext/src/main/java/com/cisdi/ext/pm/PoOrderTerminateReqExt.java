package com.cisdi.ext.pm;

import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 采购合同终止申请 扩展
 */

@Slf4j
public class PoOrderTerminateReqExt {

    /**
     * 采购合同终止申请-按照先后顺序审批-第一次审批
     */
    public void checkFirst() {
        String status = "first";
        checkFirst(status);
    }

    /**
     * 采购合同终止申请 审批先后顺序 第二次审批
     */
    public void checkSecond() {
        String status = "second";
        checkFirst(status);
    }

    /**
     * 采购合同终止申请 审批先后顺序 第三次审批
     */
    public void checkThird() {
        String status = "third";
        checkFirst(status);
    }

    /**
     * 采购合同终止申请 审批先后顺序 第四次审批
     */
    public void checkFourth() {
        String status = "fourth";
        checkFirst(status);
    }

    /**
     * 采购合同终止申请 审批先后顺序 第五次审批
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
            Integer exec = Crud.from("PO_ORDER_TERMINATE_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_ONE", userId).set("APPROVAL_DATE_ONE", now).set("DEPT_HEAD_APPROVAL_MESSAGE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("second".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_TERMINATE_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_TWO", userId).set("APPROVAL_DATE_TWO", now).set("COST_CONTRACT_APPROVAL_MESSAGE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("third".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_TERMINATE_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_THREE", userId).set("APPROVAL_DATE_THREE", now).set("FINANCE_LEGAL_APPROVAL_MESSAGE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("fourth".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_TERMINATE_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_FOUR", userId).set("APPROVAL_DATE_FOUR", now).set("APPROVAL_COMMENT_ONE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("fifth".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_TERMINATE_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_FIVE", userId).set("APPROVAL_DATE_FIVE", now).set("LEADER_APPROVE_COMMENT", comment).exec();
            log.info("已更新：{}", exec);
        }
    }

    //合同终止数据写入
    public void createContractExtra() {
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        try {
            Map<String, Object> contractDataMap = myJdbcTemplate.queryForMap("select * from PO_ORDER_TERMINATE_REQ where ID=?", csCommId);
            String id = Crud.from("PO_ORDER").insertData();
            insertContract(id, contractDataMap,entityRecord);
            //查询联系人明细
            List<Map<String,Object>> list2 = myJdbcTemplate.queryForList("select OPPO_SITE_LINK_MAN,OPPO_SITE_CONTACT from CONTRACT_END_CONTACT where PARENT_ID = ?",csCommId);
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

    private void insertContractDet(String id, List<Map<String, Object>> detailList) {
    }

    private void insertContract(String id, Map<String, Object> data,EntityRecord entityRecord) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //查询合同名称
        Map<String,Object> map = myJdbcTemplate.queryForMap("select name from PO_ORDER_REQ where id = ?",entityRecord.valueMap.get("CONTRACT_ID").toString());
        String name = map.get("name").toString();
        Crud.from("PO_ORDER").where().eq("ID", id).update()
                .set("NAME", name)
                .set("REMARK", JdbcMapUtil.getString(data, "REMARK"))
                .set("CONTRACT_AMOUNT", JdbcMapUtil.getString(data, "CONTRACT_AMOUNT"))
                .set("AGENT", JdbcMapUtil.getString(data, ""))
                .set("AGENT_PHONE", JdbcMapUtil.getString(data, ""))
                .set("FILE_ATTACHMENT_URL", JdbcMapUtil.getString(data, "ATT_FILE_GROUP_ID"))
                .set("PM_PRJ_ID", JdbcMapUtil.getString(data, "PM_PRJ_ID"))
                .set("CONTRACT_APP_ID",JdbcMapUtil.getString(data,"ID"))
                .set("ORDER_PROCESS_TYPE","合同终止")
                .exec();
    }

    /**
     * 合同终止-审批流扩展-审批通过
     */
    public void OrderEndFlowExtOK(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        //节点id
        String nodeId = ExtJarHelper.nodeId.get();
        //定义节点状态
        String nodeStatus = getStatus("true",nodeId);
        //详细处理逻辑
        handleCHeckData(nodeStatus,nodeInstanceId,myJdbcTemplate);
    }

    /**
     * 合同终止-审批流扩展-审批拒绝
     */
    public void OrderEndFlowExtRefuse(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        //节点id
        String nodeId = ExtJarHelper.nodeId.get();
        //定义节点状态
        String nodeStatus = getStatus("false",nodeId);
        //详细处理逻辑
        handleCHeckData(nodeStatus,nodeInstanceId,myJdbcTemplate);
    }

    /**
     * 合同终止-流程扩展处理详情逻辑
     * @param nodeStatus 节点信息
     * @param nodeInstanceId 节点实例id
     * @param myJdbcTemplate 数据源
     */
    private void handleCHeckData(String nodeStatus, String nodeInstanceId, MyJdbcTemplate myJdbcTemplate) {
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        // 流程实例id
        String procInstId = ExtJarHelper.procInstId.get();
        // 当前登录人
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;

        //获取审批意见信息
        Map<String,String> message = ProcessCommon.getComment(procInstId,userId,myJdbcTemplate);

        //审批意见内容
        String file = message.get("file");
        String comment = message.get("comment");

        //分支判断
        if ("lawyerTrue".equals(nodeStatus)){ // 6-法律审核-通过
            //获取流程中的附件和意见信息
            String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_ONE");
            String processFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_SIX");
            //判断生成最终的意见和附件信息
            Map<String,String> map2 = ProcessCommon.getEndComment(userId,userName,processComment,processFile,comment,file,myJdbcTemplate);
            String newCommentStr = map2.get("comment");
            String newCommentFile = SharedUtil.isEmptyString(map2.get("file")) ? null:map2.get("file");

            Crud.from("PO_ORDER_TERMINATE_REQ").where().eq("id",csCommId).update()
                    .set("APPROVAL_COMMENT_ONE",newCommentStr).set("FILE_ID_SIX",newCommentFile).exec();
        } else if ("legalFinanceTrue".equals(nodeStatus)){ // 7-财务法务审核-通过
            //判断当前登录人是法务还是财务角色 0100070673610702919-财务；0100070673610702924-法务
            userId = ProcessCommon.getOriginalUser(nodeInstanceId,userId,myJdbcTemplate);
            String roleId = ProcessRoleExt.getUserRole(myJdbcTemplate,userId);
            if ("0100070673610702919".equals(roleId)){ //财务
                //获取流程中的附件和意见信息
                String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_TWO");
                String processFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_TWO");
                //判断生成最终的意见和附件信息
                Map<String,String> map2 = ProcessCommon.getEndComment(userId,userName,processComment,processFile,comment,file,myJdbcTemplate);
                String newCommentStr = map2.get("comment");
                String newCommentFile = SharedUtil.isEmptyString(map2.get("file")) ? null:map2.get("file");

                Crud.from("PO_ORDER_TERMINATE_REQ").where().eq("id",csCommId).update()
                        .set("APPROVAL_COMMENT_TWO",newCommentStr).set("FILE_ID_TWO",newCommentFile).exec();
            } else { //法务
                //获取流程中的附件和意见信息
                String processComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_THREE");
                String processFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_THREE");
                //判断生成最终的意见和附件信息
                Map<String,String> map2 = ProcessCommon.getEndComment(userId,userName,processComment,processFile,comment,file,myJdbcTemplate);
                String newCommentStr = map2.get("comment");
                String newCommentFile = SharedUtil.isEmptyString(map2.get("file")) ? null:map2.get("file");

                Crud.from("PO_ORDER_TERMINATE_REQ").where().eq("id",csCommId).update()
                        .set("APPROVAL_COMMENT_THREE",newCommentStr).set("FILE_ID_THREE",newCommentFile).exec();
            }
        } else if ("lawyerFalse".equals(nodeStatus)){ //法律拒绝
            ProcessCommon.clearData("APPROVAL_COMMENT_ONE,FILE_ID_SIX",csCommId,entCode,myJdbcTemplate);
        } else if ("legalFinanceFalse".equals(nodeStatus)){ //财务法务拒绝
            ProcessCommon.clearData("APPROVAL_COMMENT_TWO,FILE_ID_TWO,APPROVAL_COMMENT_THREE,FILE_ID_THREE",csCommId,entCode,myJdbcTemplate);
        } else if ("start".equals(nodeStatus) || "caiHuaStart".equals(nodeStatus) || "secondStart".equals(nodeStatus)){
            WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
        }
    }

    /**
     * 节点审批通过状态
     * @param status 状态码
     * @param nodeId 节点id
     * @return 节点审批状态码
     */
    private String getStatus(String status, String nodeId) {
        String name = "";
        if ("true".equals(status)){
            if ("1628588141483012096".equals(nodeId)){ // 6-法律审核
                name = "lawyerTrue";
            } else if ("1628588141323628544".equals(nodeId)){ // 7-财务法务审核
                name = "legalFinanceTrue";
            } else if ("1628588141222965248".equals(nodeId)){ //1-发起
                name = "start";
            } else if ("1628588141256519680".equals(nodeId)){ //4-才华预审
                name = "caiHuaStart";
            } else if ("1628588141361377280".equals(nodeId)){ //8-发起人重新提交
                name = "secondStart";
            }
        } else if ("false".equals(status)){
            if ("1628588141483012096".equals(nodeId)){ // 6-法律审核
                name = "lawyerFalse";
            } else if ("1628588141323628544".equals(nodeId)){ // 7-财务法务审核
                name = "legalFinanceFalse";
            }
        }
        return name;
    }

    /**
     * 合同终止-发起时数据校验
     */
    public void orderTerminateStartCheck(){
        String status = "start";
        processCheck(status);
    }

    /**
     * 流程处理过程中数据校验及处理
     * @param status 状态码
     */
    private void processCheck(String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        // 流程实例id
        String procInstId = ExtJarHelper.procInstId.get();
        if ("start".equals(status)){ //流程发起时数据校验
            checkPayDetail(csCommId,myJdbcTemplate);
        }
    }

    /**
     * 流程发起时校验费用明细信息
     * @param csCommId 流程业务表id
     * @param myJdbcTemplate 数据源
     */
    private void checkPayDetail(String csCommId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select * from CONTRACT_END_PAY where PO_ORDER_TERMINATE_REQ_ID = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,csCommId);
        if (CollectionUtils.isEmpty(list)){
            throw new BaseException("费用明细不能为空！");
        } else {
            for (Map<String, Object> tmp : list) {
                String detailId = JdbcMapUtil.getString(tmp,"id");
                //含税金额
                BigDecimal shuiAmt = new BigDecimal(JdbcMapUtil.getString(tmp,"AMT_FIVE"));
                //税率
                BigDecimal shuiLv = new BigDecimal(JdbcMapUtil.getString(tmp,"AMT_THREE")).divide(new BigDecimal(100));
                //不含税金额
                BigDecimal noShuiAmt = shuiAmt.divide(shuiLv.add(new BigDecimal(1)),2, RoundingMode.HALF_UP);
                myJdbcTemplate.update("update CONTRACT_END_PAY set AMT_SIX=? where id = ?",noShuiAmt,detailId);
                tmp.put("AMT_SIX",noShuiAmt);
            }
            //含税总金额
            BigDecimal amtShui = getSumAmtBy(list,"AMT_FIVE");
            //不含税总金额
            BigDecimal amtNoShui = getSumAmtBy(list,"AMT_SIX");
            //税率
            BigDecimal shuiLv = getShuiLv(list,"AMT_THREE");
            //更新合同表合同总金额数
            String sql2 = "update PO_ORDER_TERMINATE_REQ set AMT_TWO = ?,AMT_THREE=?,AMT_FOUR=? where id = ?";
            myJdbcTemplate.update(sql2,amtShui,amtNoShui,shuiLv,csCommId);
        }
    }

    //获取税率
    private BigDecimal getShuiLv(List<Map<String, Object>> list, String str) {
        BigDecimal sum = new BigDecimal(0);
        tp: for (Map<String, Object> tmp : list) {
            String value =JdbcMapUtil.getString(tmp,str);
            if (!SharedUtil.isEmptyString(value)){
                sum = new BigDecimal(value);
                break tp;
            }
        }
        return sum;
    }

    // 根据字段求和
    private BigDecimal getSumAmtBy(List<Map<String, Object>> list, String str) {
        BigDecimal sum = new BigDecimal(0);
        for (Map<String, Object> tmp : list) {
            String value =JdbcMapUtil.getString(tmp,str);
            if (SharedUtil.isEmptyString(value)){
                value = "0";
            }
            sum = sum.add(new BigDecimal(value));
        }
        return sum;
    }

    /**
     * 合同终止-流程完结时扩展
     */
    public void OrderProcessEnd(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //合同工期
        int duration = JdbcMapUtil.getInt(entityRecord.valueMap,"PLAN_TOTAL_DAYS");
        //合同签订日期
        Date signDate = DateTimeUtil.stringToDate(JdbcMapUtil.getString(entityRecord.valueMap,"SIGN_DATE"));
        //计算到期日期
        Date expireDate = DateTimeUtil.addDays(signDate,duration);
        //更新到期日期字段
        Crud.from("PO_ORDER_TERMINATE_REQ").where().eq("id",entityRecord.csCommId).update().set("DATE_FIVE",expireDate).exec();
        //将合同数据写入传输至合同数据表(po_order)
    }
}
