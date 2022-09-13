package com.cisdi.ext.pm;

import com.cisdi.ext.util.DateTimeUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.interaction.EntityRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

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
        String comment = "";
        if (!CollectionUtils.isEmpty(list)) {
            comment = list.get(0).get("user_comment") == null ? null : list.get(0).get("user_comment").toString();
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
}
