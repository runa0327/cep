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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 采购合同补充协议申请-扩展
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
        String nodeId = ExtJarHelper.nodeInstId.get();

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
                "from wf_node_instance ni join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id join ad_user u on tk.ad_user_id=u.id" +
                " where u.id = ?";
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
            //判断是否是当轮拒绝回来的、撤销回来的（是否是第一个进入该节点审批的人）
            String sql2 = "select count(*) as num from wf_task where WF_NODE_INSTANCE_ID = ? and IS_CLOSED = 1 and AD_USER_ID != ?";
            List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,nodeId,userId);
            if (!CollectionUtils.isEmpty(list2)){
                String num = JdbcMapUtil.getString(list2.get(0),"num");
                if (SharedUtil.isEmptyString(num) || "0".equals(num)){
                    Integer exec = Crud.from("PO_ORDER_SUPPLEMENT_REQ").where().eq("ID", csCommId).update()
                            .set("FILE_ID_SIX",null).exec();
                    processFile = "";
                }
            } else {
                Integer exec = Crud.from("PO_ORDER_SUPPLEMENT_REQ").where().eq("ID", csCommId).update()
                        .set("FILE_ID_SIX",null).exec();
                processFile = "";
            }
            sbFile = autoFile(file,processFile);
            if (!SharedUtil.isEmptyString(sbFile)){
                upSql.append("update PO_ORDER_SUPPLEMENT_REQ set ");
                if (!SharedUtil.isEmptyString(sbFile)){
                    upSql.append(" FILE_ID_SIX = '"+sbFile+"', ");
                }
                upSql.append(" LAST_MODI_DT = now() where id = ?");
                Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                log.info("已更新：{}", exec);
            }
        }  else if ("orderLegalFinanceCheck".equals(status)) { //法务财务审批
            //流程中的审批意见
            String processLegalFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_THREE"); //法务修订稿
            String processFinanceFile = JdbcMapUtil.getString(entityRecord.valueMap,"FILE_ID_TWO"); //财务修订稿
            //查询该人员角色信息
            String sql1 = "select b.id,b.name from ad_role_user a left join ad_role b on a.AD_ROLE_ID = b.id where a.AD_USER_ID = ? and b.id in ('0100070673610711083','0099902212142039415')";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,userId);
            if (!CollectionUtils.isEmpty(list1)){
                //判断是否是当轮拒绝回来的、撤销回来的
                String sql2 = "select count(*) as num from wf_task where WF_NODE_INSTANCE_ID = ? and IS_CLOSED = 1 and AD_USER_ID != ?";
                List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,nodeId,userId);
                if (!CollectionUtils.isEmpty(list2)){
                    String num = JdbcMapUtil.getString(list2.get(0),"num");
                    if (SharedUtil.isEmptyString(num) || "0".equals(num)){
                        Integer exec = Crud.from("PO_ORDER_SUPPLEMENT_REQ").where().eq("ID", csCommId).update()
                                .set("FILE_ID_THREE",null).set("FILE_ID_TWO",null).exec();
                        processLegalFile = "";
                        processFinanceFile = "";
                    }
                } else {
                    Integer exec = Crud.from("PO_ORDER_SUPPLEMENT_REQ").where().eq("ID", csCommId).update()
                            .set("FILE_ID_THREE",null).set("FILE_ID_TWO",null).exec();
                    processLegalFile = "";
                    processFinanceFile = "";
                }
                // 0099902212142039415 = 法务部门;0100070673610711083=财务部
                String id = JdbcMapUtil.getString(list1.get(0),"id");

                if ("0099902212142039415".equals(id)){ //法务
                    sbFile = autoFile(file,processLegalFile);
                    if (!SharedUtil.isEmptyString(sbComment) || !SharedUtil.isEmptyString(sbFile)){
                        upSql.append("update PO_ORDER_SUPPLEMENT_REQ set ");
                        if (!SharedUtil.isEmptyString(sbFile)){
                            upSql.append(" FILE_ID_THREE = '"+sbFile+"', ");
                        }
                        upSql.append(" LAST_MODI_DT = now() where id = ?");
                        Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                        log.info("已更新：{}", exec);
                    }
                }
                if ("0100070673610711083".equals(id)){
                    sbFile = autoFile(file,processFinanceFile);
                    if (!SharedUtil.isEmptyString(sbComment) || !SharedUtil.isEmptyString(sbFile)){
                        upSql.append("update PO_ORDER_SUPPLEMENT_REQ set ");
                        if (!SharedUtil.isEmptyString(sbFile)){
                            upSql.append(" FILE_ID_TWO = '"+sbFile+"', ");
                        }
                        upSql.append(" LAST_MODI_DT = now() where id = ?");
                        Integer exec = myJdbcTemplate.update(upSql.toString(),csCommId);
                        log.info("已更新：{}", exec);
                    }
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
}
