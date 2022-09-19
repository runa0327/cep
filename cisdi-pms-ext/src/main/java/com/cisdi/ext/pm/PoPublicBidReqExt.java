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
 * 采购公开招标申请 扩展
 */
@Slf4j
public class PoPublicBidReqExt {

    /**
     * 流程发起人部门领导审批数据回显
     */
    public void checkFirst() {
        String status = "first";
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
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select u.id u_id,u.code u_code,u.name u_name,tk.user_comment,tk.USER_ATTACHMENT from wf_node_instance ni join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id join ad_user u on tk.ad_user_id=u.id", procInstId);
        String comment = "";
        String file = "";
        if (!CollectionUtils.isEmpty(list)) {
            comment = list.get(0).get("user_comment") == null ? null : list.get(0).get("user_comment").toString();
            file = list.get(0).get("USER_ATTACHMENT") == null ? null : list.get(0).get("USER_ATTACHMENT").toString();
        }

        // 采购方式
        String buyType = entityRecord.valueMap.get("APPROVE_PURCHASE_TYPE").toString();
        // 招标控制价
        String price = entityRecord.valueMap.get("APPROVE_BID_CTL_PRICE").toString();
//        myJdbcTemplate.update("update PO_PUBLIC_BID_REQ t set t.APPROVE_PURCHASE_TYPE_ECHO = ?,t" +
//                ".BID_CTL_PRICE_LAUNCH_ECHO=? where t.id=?", buyType, price, csCommId);

        if ("first".equals(status)) {
            Integer exec = Crud.from("PO_PUBLIC_BID_REQ").where().eq("ID", csCommId).update()
                    .set("LEADER_APPROVE_COMMENT", comment).set("LEADER_APPROVE_FILE_GROUP_ID", file)
                    .set("APPROVE_PURCHASE_TYPE_ECHO",buyType).set("BID_CTL_PRICE_LAUNCH_ECHO",price)
                    .exec();
            log.info("已更新：{}", exec);
        }
    }
}
