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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 采购合同签订扩展
 */
@Slf4j
public class PoOrderReqExt {

    /**
     * 采购合同签订扩展-按照先后顺序审批-第一次审批
     */
    public void checkFirst() {
        String status = "first";
        checkFirst(status);
    }

    /**
     * 采购合同签订扩展 审批先后顺序 第二次审批
     */
    public void checkSecond() {
        String status = "second";
        checkFirst(status);
    }

    /**
     * 采购合同签订扩展 审批先后顺序 第三次审批
     */
    public void checkThird() {
        String status = "third";
        checkFirst(status);
    }

    /**
     * 采购合同签订扩展 审批先后顺序 第四次审批
     */
    public void checkFourth() {
        String status = "fourth";
        checkFirst(status);
    }

    /**
     * 采购合同签订扩展 审批先后顺序 第五次审批
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
        String comment = "";
        if (!CollectionUtils.isEmpty(list)) {
            comment = list.get(0).get("user_comment") == null ? null : list.get(0).get("user_comment").toString();
        }
        if ("first".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_ONE", userId).set("APPROVAL_DATE_ONE", now).set("APPROVAL_MESSAGE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("second".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_TWO", userId).set("APPROVAL_DATE_TWO", now).set("EARLY_COMMENT", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("third".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_THREE", userId).set("APPROVAL_DATE_THREE", now).set("DESIGN_COMMENT", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("fourth".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_FOUR", userId).set("APPROVAL_DATE_FOUR", now).set("APPROVAL_COMMENT_ONE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("fifth".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_REQ").where().eq("ID", csCommId).update()
                    .set("APPROVAL_USER_FIVE", userId).set("APPROVAL_DATE_FIVE", now).set("USER_COMMENT", comment).exec();
            log.info("已更新：{}", exec);
        }
    }

    /**
     * 流程发起之时，相关数据校验
     */
    public void checkData() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        // 查询明细表合同总金额
        String sql = "select AMT from PM_ORDER_COST_DETAIL where CONTRACT_ID = ?";
        List<Map<String, Object>> list1 = myJdbcTemplate.queryForList(sql, entityRecord.csCommId);
        if (CollectionUtils.isEmpty(list1)) {
            throw new BaseException("费用明细不能为空！");
        }
        BigDecimal account = getSumAmt(list1);
        //更新合同表合同总金额数
        String sql2 = "update PO_ORDER_REQ set CONTRACT_PRICE = ? where id = ?";
        myJdbcTemplate.update(sql2,account,entityRecord.csCommId);

        //是否涉及保函
        String baoHan = JdbcMapUtil.getString(entityRecord.valueMap,"IS_REFER_GUARANTEE");
        //保函类型
        String baoHanType = JdbcMapUtil.getString(entityRecord.valueMap,"GUARANTEE_LETTER_TYPE_ID");
        if ("true".equals(baoHan)){
            if (SharedUtil.isEmptyString(baoHanType)){
                throw new BaseException("保函类型不能为空！");
            }
        }
        if ("false".equals(baoHan)){
            if (!SharedUtil.isEmptyString(baoHanType)){
                throw new BaseException("未涉及保函，请勿勾选保函类型！");
            }
        }

        //是否填写联系人
        List<Map<String, Object>> contactList = myJdbcTemplate.queryForList("SELECT OPPO_SITE_LINK_MAN, OPPO_SITE_CONTACT " +
                "FROM CONTRACT_SIGNING_CONTACT where CONTRACT_ID = ?", entityRecord.csCommId);
        if (CollectionUtils.isEmpty(contactList)){
            throw new BaseException("联系人不能为空！");
        }
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
}
