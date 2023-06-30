package com.cisdi.ext.pm.orderManage;

import com.cisdi.ext.base.PmPrjExt;
import com.cisdi.ext.model.PoOrderSupplementReq;
import com.cisdi.ext.pm.ProcessCommon;
import com.cisdi.ext.pm.ProcessRoleExt;
import com.cisdi.ext.pm.orderManage.detail.PoOrderSupplementPrjDetailExt;
import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
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
 * 合约管理-补充协议-扩展
 */
@Slf4j
public class PoOrderSupplementReqExt {

    /**
     * 采购合同补充协议申请-按照先后顺序审批-第一次审批
     */
    public void checkFirst() {
        String status = "first";
        checkFirst(status);
    }

    /**
     * 采购合同补充协议申请 审批先后顺序 第二次审批
     */
    public void checkSecond() {
        String status = "second";
        checkFirst(status);
    }

    /**
     * 采购合同补充协议申请 审批先后顺序 第三次审批
     */
    public void checkThird() {
        String status = "third";
        checkFirst(status);
    }

    /**
     * 采购合同补充协议申请 审批先后顺序 第四次审批
     */
    public void checkFourth() {
        String status = "fourth";
        checkFirst(status);
    }

    /**
     * 采购合同补充协议申请 审批先后顺序 第五次审批
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
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select u.id u_id,u.code u_code,u.name u_name,tk.user_comment from wf_node_instance ni join wf_task tk on ni.wf_process_instance_id=? and tk.status='ap' and ni.is_current=1 and ni.id=tk.wf_node_instance_id join ad_user u on tk.ad_user_id=u.id", procInstId);
        String comment = "";
        if (!CollectionUtils.isEmpty(list)) {
            comment = list.get(0).get("user_comment") == null ? null : list.get(0).get("user_comment").toString();
        }
        if ("first".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_SUPPLEMENT_REQ").where().eq("ID", csCommId).update()
                    .set("DEPT_APPROVAL_USER", userId).set("DEPT_APPROVAL_DATE", now).set("DEPT_HEAD_APPROVAL_MESSAGE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("second".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_SUPPLEMENT_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_TWO", userId).set("APPROVAL_DATE_TWO", now).set("COST_CONTRACT_APPROVAL_MESSAGE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("third".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_SUPPLEMENT_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_THREE", userId).set("APPROVAL_DATE_THREE", now).set("FINANCE_LEGAL_APPROVAL_MESSAGE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("fourth".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_SUPPLEMENT_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_FOUR", userId).set("APPROVAL_DATE_FOUR", now).set("APPROVAL_COMMENT_FOUR", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("fifth".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_SUPPLEMENT_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_FIVE", userId).set("APPROVAL_DATE_FIVE", now).set("LEADER_APPROVE_COMMENT", comment).exec();
            log.info("已更新：{}", exec);
        }
    }

    //合同补充协议数据写入
    public void createContractExtra() {
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        try {
            Map<String, Object> contractDataMap = myJdbcTemplate.queryForMap("select * from PO_ORDER_SUPPLEMENT_REQ where ID=?", csCommId);
            String id = Crud.from("PO_ORDER").insertData();
            insertContract(id, contractDataMap);
            List<Map<String, Object>> detailList = myJdbcTemplate.queryForList("select * from PM_ORDER__EXTRA_COST_DETAIL where PO_ORDER_SUPPLEMENT_REQ_ID=?", csCommId);
            insertContractDet(id, detailList);
            //查询联系人明细1
            List<Map<String,Object>> list2 = myJdbcTemplate.queryForList("select WIN_BID_UNIT_ONE,OPPO_SITE_LINK_MAN,OPPO_SITE_CONTACT from CONTRACT_SUPPLEMENT_CONTACT where PARENT_ID = ?",csCommId);
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
                    .set("WIN_BID_UNIT_ONE",JdbcMapUtil.getString(item,"WIN_BID_UNIT_ONE"))
                    .exec();
        });
    }

    private void insertContractDet(String id, List<Map<String, Object>> detailList) {
        detailList.forEach(item -> {
            String did = Crud.from("PO_ORDER_DTL").insertData();
            Crud.from("PO_ORDER_DTL").where().eq("ID", did).update()
                    .set("NAME", JdbcMapUtil.getString(item, "NAME"))
                    .set("REMARK", JdbcMapUtil.getString(item, "REMARK"))
                    .set("COST_TYPE_TREE_ID", JdbcMapUtil.getString(item, "COST_TYPE_TREE_ID"))
                    .set("PAY_TYPE", JdbcMapUtil.getString(item, ""))
                    .set("FILE_ATTACHMENT_URL", JdbcMapUtil.getString(item, ""))
                    .set("TOTAL_AMT", JdbcMapUtil.getString(item, "TOTAL_AMT"))
                    .set("AMT", JdbcMapUtil.getString(item, "AMT"))
                    .set("CONTRACT_ID", JdbcMapUtil.getString(item, "CONTRACT_ID"))
                    .set("WORK_CONTENT", JdbcMapUtil.getString(item, ""))
                    .set("FEE_DETAIL", JdbcMapUtil.getString(item, "FEE_DETAIL"))
                    .set("PO_ORDER_ID", id)
                    .exec();
        });
    }

    private void insertContract(String id, Map<String, Object> data) {
        Crud.from("PO_ORDER").where().eq("ID", id).update()
                .set("NAME", JdbcMapUtil.getString(data, "CONTRACT_NAME"))
                .set("REMARK", JdbcMapUtil.getString(data, "REMARK"))
                .set("CONTRACT_AMOUNT", JdbcMapUtil.getString(data, "CONTRACT_AMOUNT"))
                .set("AGENT", JdbcMapUtil.getString(data, ""))
                .set("AGENT_PHONE", JdbcMapUtil.getString(data, ""))
//                .set("OPPO_SITE_CONTACT", JdbcMapUtil.getString(data, "OPPO_SITE_CONTACT"))
                .set("FILE_ATTACHMENT_URL", JdbcMapUtil.getString(data, "ATT_FILE_GROUP_ID"))
//                .set("OPPO_SITE", JdbcMapUtil.getString(data, ""))
//                .set("OPPO_SITE_LINK_MAN", JdbcMapUtil.getString(data, "OPPO_SITE_LINK_MAN"))
                .set("PM_PRJ_ID", JdbcMapUtil.getString(data, "PM_PRJ_ID"))
                .set("CONTRACT_APP_ID",JdbcMapUtil.getString(data,"ID"))
                .set("ORDER_PROCESS_TYPE","合同补充协议")
                .exec();
    }

    /**
     * 流程发起之时，相关数据校验
     */
    public void checkData() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        // 查询明细表合同总金额
        String sql = "select * from PM_ORDER__EXTRA_COST_DETAIL where PO_ORDER_SUPPLEMENT_REQ_ID = ?";
        List<Map<String, Object>> list1 = myJdbcTemplate.queryForList(sql, entityRecord.csCommId);
        if (CollectionUtils.isEmpty(list1)) {
            throw new BaseException("费用明细不能为空！");
        }
        for (Map<String, Object> tmp : list1) {
            String detailId = JdbcMapUtil.getString(tmp,"id");
            //含税金额
            BigDecimal shuiAmt = new BigDecimal(JdbcMapUtil.getString(tmp,"AMT_ONE"));
            //税率
            BigDecimal shuiLv = new BigDecimal(JdbcMapUtil.getString(tmp,"AMT_THREE")).divide(new BigDecimal(100));
            //含税金额
            BigDecimal noShuiAmt = shuiAmt.divide(shuiLv.add(new BigDecimal(1)),2, RoundingMode.HALF_UP);
            Integer integer = myJdbcTemplate.update("update PM_ORDER__EXTRA_COST_DETAIL set AMT_TWO=? where id = ?",noShuiAmt,detailId);
            tmp.put("AMT_TWO",noShuiAmt);
        }

        //含税总金额
        BigDecimal amtShui = getSumAmtBy(list1,"AMT_ONE");
        //不含税总金额
        BigDecimal amtNoShui = getSumAmtBy(list1,"AMT_TWO");
        //税率
        BigDecimal shuiLv = getShuiLv(list1,"AMT_THREE");
        //更新合同表合同总金额数
        String sql2 = "update PO_ORDER_SUPPLEMENT_REQ set AMT_TWO = ?,AMT_THREE=?,AMT_FOUR=? where id = ?";
        myJdbcTemplate.update(sql2,amtShui,amtNoShui,shuiLv,entityRecord.csCommId);

        //是否填写联系人
        List<Map<String, Object>> contactList = myJdbcTemplate.queryForList("SELECT * FROM CONTRACT_SUPPLEMENT_CONTACT where PO_ORDER_SUPPLEMENT_REQ_ID = ?", entityRecord.csCommId);
        if (CollectionUtils.isEmpty(contactList)){
            throw new BaseException("联系人不能为空！");
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
            sum = sum.add(new BigDecimal(value));
        }
        return sum;
    }

    // 汇总求和
    private BigDecimal getSumAmt(List<Map<String, Object>> list) {
        BigDecimal sum = new BigDecimal(0);
        for (Map<String, Object> tmp : list) {
            String value = tmp.get("AMT").toString();
            sum = sum.add(new BigDecimal(value));
        }
        return sum;
    }

    /**
     * 补充协议扩展-律师审核
     */
    public void supplementLawyerCheck(){
        String status = "lawyerCheck";
        showComment(status);
    }

    /**
     * 补充协议扩展-法务财务审核
     */
    public void orderLegalFinanceCheck(){
        String status = "orderLegalFinanceCheck";
        showComment(status);
    }

    /**
     * 审批意见、附件回显
     * @param status
     */
    private void showComment(String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Date date = new Date();
        String now = DateTimeUtil.dateToString(date);

        //获取当前节点实例id
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();

        // 当前登录人
        String userName = ExtJarHelper.loginInfo.get().userName;

        // 当前登录人id
        String userId = ExtJarHelper.loginInfo.get().userId;

        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        // 流程id
        String procInstId = ExtJarHelper.procInstId.get();
        // 审批意见
        String sql = "select tk.USER_COMMENT,tk.USER_ATTACHMENT " +
                "from wf_node_instance ni join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id and tk.status = 'ap' " +
                "join ad_user u on tk.ad_user_id=u.id " +
                "where u.id = ?";
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql, procInstId,userId);
        String comment = "";
        String file = "";
        if (!CollectionUtils.isEmpty(list)) {
            comment = SharedUtil.isEmptyString(JdbcMapUtil.getString(list.get(0),"user_comment"))  ? "同意" : JdbcMapUtil.getString(list.get(0),"user_comment");
            file = list.get(0).get("USER_ATTACHMENT") == null ? null : list.get(0).get("USER_ATTACHMENT").toString();
        }
        String sbComment = "";
        String sbFile = "";
        StringBuilder upSql = new StringBuilder();
        if ("lawyerCheck".equals(status)){ //律师审核
            //流程中的审批意见附件
            String processFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_SIX");
            //流程中的审批意见
            String APPROVAL_COMMENT_ONE = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_ONE");
            //判断是否是当轮拒绝回来的、撤销回来的（是否是第一个进入该节点审批的人）
            String sql2 = "select count(*) as num from wf_task where WF_NODE_INSTANCE_ID = ? and IS_CLOSED = 1 and AD_USER_ID != ? and status = 'ap'";
            List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,nodeInstanceId,userId);
            if (!CollectionUtils.isEmpty(list2)){
                String num = JdbcMapUtil.getString(list2.get(0),"num");
                if (SharedUtil.isEmptyString(num) || "0".equals(num)){
                    Integer exec = Crud.from("PO_ORDER_SUPPLEMENT_REQ").where().eq("ID", csCommId).update()
                            .set("FILE_ID_SIX",null).set("APPROVAL_COMMENT_ONE",null).exec();
                    processFile = "";
                    APPROVAL_COMMENT_ONE = "";
                }
            } else {
                Integer exec = Crud.from("PO_ORDER_SUPPLEMENT_REQ").where().eq("ID", csCommId).update()
                        .set("FILE_ID_SIX",null).set("APPROVAL_COMMENT_ONE",null).exec();
                processFile = "";
                APPROVAL_COMMENT_ONE = "";
            }
            sbFile = autoFile(file,processFile);
            sbComment = autoComment(comment,APPROVAL_COMMENT_ONE,userName);
            if (!SharedUtil.isEmptyString(sbFile) || !SharedUtil.isEmptyString(sbComment)){
                upSql.append("update PO_ORDER_SUPPLEMENT_REQ set ");
                if (!SharedUtil.isEmptyString(sbFile)){
                    upSql.append(" FILE_ID_SIX = '"+sbFile+"', ");
                }
                if (!SharedUtil.isEmptyString(sbComment)){
                    upSql.append(" APPROVAL_COMMENT_ONE = '"+sbComment+"', ");
                }
                upSql.append(" LAST_MODI_DT = now() where id = ?");
                Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                log.info("已更新：{}", exec);
            }
        }  else if ("orderLegalFinanceCheck".equals(status)) { //法务财务审批
            String nodeId = ExtJarHelper.nodeId.get();
            String processId = ExtJarHelper.procId.get();
            //流程中的审批意见附件
            String processLegalFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_THREE"); //法务修订稿
            String processFinanceFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_TWO"); //财务修订稿
            //流程中的审批意见
            String processLegalComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_THREE"); //法务部门意见
            String processFinanceComment = JdbcMapUtil.getString(entityRecord.valueMap,"APPROVAL_COMMENT_TWO"); //财务部门意见
            //查询该人员角色信息
            String sql1 = "select b.id,b.name from ad_role_user a left join ad_role b on a.AD_ROLE_ID = b.id where a.AD_USER_ID = ? and b.id in ('0100070673610711083','0099902212142039415')";
            userId = ProcessCommon.getOriginalUser(nodeInstanceId,userId,myJdbcTemplate);
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,userId);
            if (!CollectionUtils.isEmpty(list1)){
                //判断是否是当轮拒绝回来的、撤销回来的
                String sql2 = "select count(*) as num from wf_task where WF_NODE_INSTANCE_ID = ? and IS_CLOSED = 1 and AD_USER_ID != ? and status = 'ap'";
                List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,nodeInstanceId,userId);
                if (!CollectionUtils.isEmpty(list2)){
                    String num = JdbcMapUtil.getString(list2.get(0),"num");
                    if (SharedUtil.isEmptyString(num) || "0".equals(num)){
                        Integer exec = Crud.from("PO_ORDER_SUPPLEMENT_REQ").where().eq("ID", csCommId).update()
                                .set("FILE_ID_THREE",null).set("FILE_ID_TWO",null)
                                .set("APPROVAL_COMMENT_THREE",null).set("APPROVAL_COMMENT_TWO",null).exec();
                        processLegalFile = "";
                        processFinanceFile = "";
                        processLegalComment = "";
                        processFinanceComment = "";
                    }
                } else {
                    Integer exec = Crud.from("PO_ORDER_SUPPLEMENT_REQ").where().eq("ID", csCommId).update()
                            .set("FILE_ID_THREE",null).set("FILE_ID_TWO",null)
                            .set("APPROVAL_COMMENT_THREE",null).set("APPROVAL_COMMENT_TWO",null).exec();
                    processLegalFile = "";
                    processFinanceFile = "";
                    processLegalComment = "";
                    processFinanceComment = "";
                }
                // 0099902212142039415 = 法务部门;0100070673610711083=财务部
                String id = JdbcMapUtil.getString(list1.get(0),"id");

                if ("0099902212142039415".equals(id)){ //法务
                    sbFile = autoFile(file,processLegalFile);
                    sbComment = autoComment(comment,processLegalComment,userName);
                    if (!SharedUtil.isEmptyString(sbComment) || !SharedUtil.isEmptyString(sbFile)){
                        upSql.append("update PO_ORDER_SUPPLEMENT_REQ set ");
                        if (!SharedUtil.isEmptyString(sbFile)){
                            upSql.append(" FILE_ID_THREE = '"+sbFile+"', ");
                        }
                        if (!SharedUtil.isEmptyString(sbComment)){
                            upSql.append(" APPROVAL_COMMENT_THREE = '"+sbComment+"', ");
                        }
                        upSql.append(" LAST_MODI_DT = now() where id = ?");
                        Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                        log.info("已更新：{}", exec);
                    }
                }
                if ("0100070673610711083".equals(id)){
                    sbFile = autoFile(file,processFinanceFile);
                    sbComment = autoComment(comment,processFinanceComment,userName);
                    if (!SharedUtil.isEmptyString(sbComment) || !SharedUtil.isEmptyString(sbFile)){
                        upSql.append("update PO_ORDER_SUPPLEMENT_REQ set ");
                        if (!SharedUtil.isEmptyString(sbFile)){
                            upSql.append(" FILE_ID_TWO = '"+sbFile+"', ");
                        }
                        if (!SharedUtil.isEmptyString(sbComment)){
                            upSql.append(" APPROVAL_COMMENT_TWO = '"+sbComment+"', ");
                        }
                        upSql.append(" LAST_MODI_DT = now() where id = ?");
                        Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                        log.info("已更新：{}", exec);
                    }
                }
                // 判断当前用户是否是财务第一个审批的
                boolean izFirst = ProcessRoleExt.getUserFinanceRole(userId,"0099952822476412306");
                if (izFirst){
                    // 将后续审批人员信息写入任务
                    ProcessCommon.createOrderFinanceCheckUser(nodeInstanceId,"0099952822476412308",processId,procInstId,nodeId);
                }
            }
        }
    }

    // 审核意见附件自动拼接
    private String autoFile(String newFile, String oldFile) {
        StringBuilder sbFile = new StringBuilder();
        if (!SharedUtil.isEmptyString(newFile)){
            if (!SharedUtil.isEmptyString(oldFile)){
                sbFile.append(oldFile).append(",").append(newFile);
            } else {
                sbFile.append(newFile);
            }
        }
        return sbFile.toString();
    }

    // 审核意见自动拼接
    private String autoComment(String newComment, String oldComment, String userName) {
        StringBuilder sbComment = new StringBuilder();
        if (!SharedUtil.isEmptyString(newComment)){
            if (!SharedUtil.isEmptyString(oldComment)){
                sbComment.append(oldComment).append("; \n").append(userName).append(":").append(newComment);
            } else {
                sbComment.append(userName).append(":").append(newComment);
            }
        }
        return sbComment.toString();
    }

    /**
     * 补充协议-流程完结时扩展
     */
    public void OrderProcessEnd(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String id = entityRecord.csCommId;
        //合同工期
        int duration = JdbcMapUtil.getInt(entityRecord.valueMap,"PLAN_TOTAL_DAYS");
        //合同签订日期
        Date signDate = DateTimeUtil.stringToDate(JdbcMapUtil.getString(entityRecord.valueMap,"SIGN_DATE"));
        //计算到期日期
        Date expireDate = DateTimeUtil.addDays(signDate,duration);
        //更新到期日期字段
        Crud.from("PO_ORDER_SUPPLEMENT_REQ").where().eq("id",id).update().set("DATE_FIVE",expireDate).exec();
        //将合同数据写入传输至合同数据表(po_order)
//        PoOrderExtApi.createData(entityRecord,"PO_ORDER_SUPPLEMENT_REQ","0100070673610715221",myJdbcTemplate);
        //项目信息项目明细表
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_IDS");
        PoOrderSupplementPrjDetailExt.insertData(id,projectId);
    }

    /**
     * 流程操作-补充协议-确认操作
     */
    public void orderSupplementProcessOK(){
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 流程操作-补充协议-拒绝操作
     */
    public void orderSupplementProcessRefuse(){
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
        String userId = ExtJarHelper.loginInfo.get().userId;
        String userName = ExtJarHelper.loginInfo.get().userName;
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        String procInstId = ExtJarHelper.procInstId.get();
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        String nodeInstanceId = ExtJarHelper.nodeInstId.get();
        if ("OK".equals(status)){
            if ("start".equals(nodeStatus) || "caiHuaStart".equals(nodeStatus) || "secondStart".equals(nodeStatus)){
                WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
            }
        } else {
            if ("lawyerRefuse".equals(nodeStatus)){ // 法律审批拒绝
                ProcessCommon.clearData("APPROVAL_COMMENT_ONE,FILE_ID_SIX",csCommId,entCode,myJdbcTemplate);
            } else if ("financeLegalRefuse".equals(nodeStatus)){ //财务部法务部审批-通过
                ProcessCommon.clearData("APPROVAL_COMMENT_TWO,FILE_ID_TWO,APPROVAL_COMMENT_THREE,FILE_ID_THREE",csCommId,entCode,myJdbcTemplate);
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
            if ("0099902212142022658".equals(nodeId)){ //1-发起
                nodeName = "start";
            } else if ("0099902212142022659".equals(nodeId)){ //4-才华预审
                nodeName = "caiHuaStart";
            } else if ("0099902212142022662".equals(nodeId)){ //8-发起人重新提交
                nodeName = "secondStart";
            } else if ("0099952822476418938".equals(nodeId)){ //6-法律审核意见-通过
                nodeName = "lawyerOK";
            } else if ("0099902212142022661".equals(nodeId)){ //7-财务部法务部审批-通过
                nodeName = "financeLegalOK";
            }
        } else {
            if ("0099952822476418938".equals(nodeId)){ //6-法律审核意见-拒绝
                nodeName = "lawyerRefuse";
            } else if ("0099902212142022661".equals(nodeId)){ //7-财务部法务部审批-拒绝
                nodeName = "financeLegalRefuse";
            }
        }
        return nodeName;
    }

    /**
     * 历史数据处理
     */
    public void supplementHistoryData(){

        // 非系统项目转系统项目
        List<PoOrderSupplementReq> list1 = PoOrderSupplementReq.selectByWhere(new Where()
                .nin(PoOrderSupplementReq.Cols.STATUS,"VD","VDING")
                .eq(PoOrderSupplementReq.Cols.PROJECT_SOURCE_TYPE_ID,"0099952822476441375"));
        if (!CollectionUtils.isEmpty(list1)){
            for (PoOrderSupplementReq tmp : list1) {
                String projectName = tmp.getProjectNameWr();
                String projectId = PmPrjExt.createPrjByMoreName(projectName);
                String id = tmp.getId();
                Crud.from(PoOrderSupplementReq.ENT_CODE).where().eq(PoOrderSupplementReq.Cols.ID,id).update()
                        .set(PoOrderSupplementReq.Cols.PM_PRJ_IDS,projectId)
                        .exec();
            }
        }

        //系统项目id写入pm_prj_ids
        List<PoOrderSupplementReq> list2 = PoOrderSupplementReq.selectByWhere(new Where()
                .nin(PoOrderSupplementReq.Cols.STATUS,"VD","VDING")
                .neq(PoOrderSupplementReq.Cols.PROJECT_SOURCE_TYPE_ID,"0099952822476441375"));
        if (!CollectionUtils.isEmpty(list2)){
            for (PoOrderSupplementReq tp : list2) {
                String id = tp.getId();
                String projectId = tp.getPmPrjId();
                Crud.from(PoOrderSupplementReq.ENT_CODE).where().eq(PoOrderSupplementReq.Cols.ID,id).update()
                        .set(PoOrderSupplementReq.Cols.PM_PRJ_IDS,projectId)
                        .exec();
            }
        }

        //已批准流程项目id写入明细表
        List<PoOrderSupplementReq> list3 = PoOrderSupplementReq.selectByWhere(new Where().eq(PoOrderSupplementReq.Cols.STATUS,"AP"));
        if (!CollectionUtils.isEmpty(list3)){
            for (PoOrderSupplementReq tmp : list3) {
                String id = tmp.getId();
                String projectId = tmp.getPmPrjIds();
                if (SharedUtil.isEmptyString(projectId)){
                    projectId = tmp.getPmPrjId();
                }
                PoOrderSupplementPrjDetailExt.insertData(id,projectId);
            }
        }
    }
}
