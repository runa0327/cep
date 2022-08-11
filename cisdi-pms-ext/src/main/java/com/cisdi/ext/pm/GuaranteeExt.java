package com.cisdi.ext.pm;

import com.cisdi.ext.util.DateTimeUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.interaction.EntityRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 保函相关扩展
 */

@Slf4j
public class GuaranteeExt {

    /**
     * 保函预付款金额生成
     */
    public void getAdvanceCharge(){

        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {

            //获取id
            String id = entityRecord.csCommId;
            //合同金额
            BigDecimal contractAmount = new BigDecimal(entityRecord.valueMap.get("CONTRACT_AMOUNT").toString());
            //预付款比例
            BigDecimal percent = new BigDecimal(entityRecord.valueMap.get("ADVANCE_CHARGE_PERCENT").toString());
            //预付款金额
            BigDecimal amount = contractAmount.multiply(percent);

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

    public void getAgency(String status){
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        Date date = new Date();
        String now = DateTimeUtil.dateToString(date);
        //当前登录人
        String userId = ExtJarHelper.loginInfo.get().userName;
        //审批意见
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        //流程id
        String procInstId = ExtJarHelper.procInstId.get();
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select u.id u_id,u.code u_code,u.name u_name,tk.user_comment from wf_node_instance ni join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id join ad_user u on tk.ad_user_id=u.id", procInstId);
        String comment = "";
        if (!CollectionUtils.isEmpty(list)){
            comment = list.get(0).get("user_comment") == null ? null : list.get(0).get("user_comment").toString();
        }
        if ("One".equals(status)){
            Integer exec = Crud.from("PO_GUARANTEE_LETTER_REQUIRE_REQ").where().eq("ID", csCommId).update()
                    .set("FINANCE_PUBLISH_USER", userId).set("FINANCE_PUBLISH_DATE",now).set("FINANCE_MESSAGE",comment).exec();
            log.info("已更新：{}", exec);
        } else if ("Two".equals(status)){
            Integer exec = Crud.from("PO_GUARANTEE_LETTER_REQUIRE_REQ").where().eq("ID", csCommId).update()
                    .set("COMMENT_PUBLISH_USER", userId).set("COMMENT_PUBLISH_DATE",now).set("COMMENT_PUBLISH_CONTENT",comment).exec();
            log.info("已更新：{}", exec);
        }
    }

}
