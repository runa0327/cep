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

import java.util.Date;
import java.util.List;
import java.util.Map;

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
}
