package com.cisdi.ext.pm;

import com.cisdi.ext.util.AmtUtil;
import com.cisdi.ext.util.ConvertUtils;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 保函相关扩展
 */

@Slf4j
public class GuaranteeExt {

    /**
     * 保函预付款金额生成
     */
    public void getAdvanceCharge() {

        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {

            // 获取id
            String id = entityRecord.csCommId;
            // 合同金额
            BigDecimal contractAmount = new BigDecimal(entityRecord.valueMap.get("CONTRACT_AMOUNT").toString());
            // 预付款比例
            BigDecimal percent = new BigDecimal(entityRecord.valueMap.get("ADVANCE_CHARGE_PERCENT").toString());
            // 预付款金额
            BigDecimal amount = contractAmount.multiply(percent).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);

            // 修改预付款金额信息：
            Integer exec = Crud.from("PO_GUARANTEE_LETTER_REQUIRE_REQ").where().eq("ID", id).update().set("ADVANCE_CHARGE_AMT", amount).exec();
            log.info("已更新：{}", exec);
        }
    }

    /**
     * 最后第二次财务会审
     */
    public void changeStatusTwo() {
        String newStatus = "Two";
        getAgency(newStatus);
    }

    /**
     * 最后一次财务会审
     */
    public void changeStatusOne() {
        String newStatus = "One";
        getAgency(newStatus);
    }

    public void getAgency(String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Date date = new Date();
        String now = DateTimeUtil.dateToString(date);
        // 当前登录人
        String userId = ExtJarHelper.loginInfo.get().userName;
        // 审批意见
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        // 流程id
        String procInstId = ExtJarHelper.procInstId.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select u.id u_id,u.code u_code,u.name u_name,tk.user_comment from wf_node_instance ni join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id join ad_user u on tk.ad_user_id=u.id where tk.status = 'ap' and ni.status = 'ap' ", procInstId);
        StringBuffer comment = new StringBuffer();
        if (!CollectionUtils.isEmpty(list)) {
            for (Map<String, Object> tmp : list) {
                String txt = SharedUtil.isEmptyString(JdbcMapUtil.getString(tmp,"user_comment")) ? "同意" : JdbcMapUtil.getString(tmp,"user_comment");
                if (!SharedUtil.isEmptyString(txt)){
                    comment = comment.append(JdbcMapUtil.getString(tmp,"u_name")).append("： ").append(txt).append(";").append("\n");
                }
            }
        }
        comment.deleteCharAt(comment.length()-2);
        if ("One".equals(status)) {
            Integer exec = Crud.from("PO_GUARANTEE_LETTER_REQUIRE_REQ").where().eq("ID", csCommId).update()
                    .set("FINANCE_PUBLISH_USER", userId).set("FINANCE_PUBLISH_DATE", now).set("FINANCE_MESSAGE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("Two".equals(status)) {
            Integer exec = Crud.from("PO_GUARANTEE_LETTER_REQUIRE_REQ").where().eq("ID", csCommId).update()
                    .set("COMMENT_PUBLISH_USER", userId).set("COMMENT_PUBLISH_DATE", now).set("COMMENT_PUBLISH_CONTENT", comment).exec();
            log.info("已更新：{}", exec);
        }
    }

    /**
     * 保函退还申请审批 审批先后顺序 第一次审批
     */
    public void checkFirst() {
        String newStatus = "first";
        checkFirst(newStatus);
    }

    public void checkFirst(String status) {
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
        String comment = "";
        if (!CollectionUtils.isEmpty(list)) {
            comment = list.get(0).get("user_comment") == null ? null : list.get(0).get("user_comment").toString();
        }
        if ("first".equals(status)) {
            Integer exec = Crud.from("PO_GUARANTEE_LETTER_RETURN_REQ").where().eq("ID", csCommId).update()
                    .set("DEPT_APPROVAL_USER", userId).set("DEPT_APPROVAL_DATE", now).set("DEPT_APPROVAL_COMMENT", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("second".equals(status)) {
            Integer exec = Crud.from("PO_GUARANTEE_LETTER_RETURN_REQ").where().eq("ID", csCommId).update()
                    .set("FINANCE_APPROVAL_USER_ONE", userId).set("FINANCE_APPROVAL_DATE_ONE", now).set("FINANCE_APPROVAL_COMMENT_ONE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("third".equals(status)) {
            Integer exec = Crud.from("PO_GUARANTEE_LETTER_RETURN_REQ").where().eq("ID", csCommId).update()
                    .set("FINANCE_APPROVAL_USER_TWO", userId).set("FINANCE_APPROVAL_DATE_TWO", now).set("FINANCE_APPROVAL_COMMENT_TWO", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("fourth".equals(status)) {
            Integer exec = Crud.from("PO_GUARANTEE_LETTER_RETURN_REQ").where().eq("ID", csCommId).update()
                    .set("FINANCE_APPROVAL_USER_THREE", userId).set("FINANCE_APPROVAL_DATE_THREE", now).set("FINANCE_APPROVAL_COMMENT_THREE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("fifth".equals(status)) {
            Integer exec = Crud.from("PO_GUARANTEE_LETTER_RETURN_REQ").where().eq("ID", csCommId).update()
                    .set("FINANCE_LEADER_USER", userId).set("FINANCE_APPROVAL_LEADER_DATE", now).set("FINANCE_APPROVAL_LEADER_COMMENT", comment).exec();
            log.info("已更新：{}", exec);
        }
    }

    /**
     * 保函退还申请审批 审批先后顺序 第二次审批
     */
    public void checkSecond() {
        String newStatus = "second";
        checkFirst(newStatus);
    }

    /**
     * 保函退还申请审批 审批先后顺序 第三次审批
     */
    public void checkThird() {
        String newStatus = "third";
        checkFirst(newStatus);
    }

    /**
     * 保函退还申请审批 审批先后顺序 第四次审批
     */
    public void checkFourth() {
        String newStatus = "fourth";
        checkFirst(newStatus);
    }

    /**
     * 保函退还申请审批 审批先后顺序 第五次审批
     */
    public void checkFifth() {
        String newStatus = "fifth";
        checkFirst(newStatus);
    }

    /**
     * 保函退还申请 发起时数据校验
     */
    public void checkData(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //流程id
        String id = entityRecord.csCommId;
        //获取金额
        String amt = JdbcMapUtil.getString(entityRecord.valueMap,"AMT_WR_ONE").trim();
        String amtChina = AmtUtil.number2CNMontrayUnit(new BigDecimal(amt));
        String sql = "update PO_GUARANTEE_LETTER_RETURN_OA_REQ set REMARK_TWO = ? where id = ?";
        int num = myJdbcTemplate.update(sql,amtChina,id);
    }
}
