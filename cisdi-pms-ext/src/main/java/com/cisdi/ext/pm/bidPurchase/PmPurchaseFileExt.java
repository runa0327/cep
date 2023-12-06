package com.cisdi.ext.pm.bidPurchase;

import com.cisdi.ext.api.HrDeptExt;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.Map;

/**
 * 采购管理归档-扩展
 */
public class PmPurchaseFileExt {

    /**
     * 自动发起流程时创建单条数据
     * @param purchaseId 父级流程实例业务记录id
     * @param entityRecordId 单条记录业务表id
     * @param valueMap 父级流程表单详情
     * @param wfProcessInstanceId 流程实例id
     * @param createBy 创建人
     * @param now 当前时间
     * @param fatherProcessInstanceId 自动发起流程，父级流程实例id
     * @param userId 自动发起流程，数据创建人。对于自动发起流程，创建人不一定是发起人
     * @param myJdbcTemplate 数据源
     */
    public static void createOneData(String purchaseId, String entityRecordId, Map<String, Object> valueMap, String wfProcessInstanceId, String createBy, String now, String fatherProcessInstanceId, String userId, MyJdbcTemplate myJdbcTemplate) {
        String deptId = HrDeptExt.getDeptIdByUser(createBy,myJdbcTemplate);
        Crud.from("PM_PURCHASE_FILE").where().eq("ID",entityRecordId).update()
                .set("VER","1")
                .set("TS",now)
                .set("CRT_DT",now)
                .set("LAST_MODI_DT",now)
                .set("LAST_MODI_USER_ID",userId)
                .set("LK_WF_INST_ID",wfProcessInstanceId)
                .set("STATUS","DR")
                .set("CRT_USER_ID", JdbcMapUtil.getString(valueMap,"CRT_USER_ID"))
                .set("CRT_DEPT_ID",deptId)
                .set("CRT_DT",now)
                .set("PM_PURCHASE_PROCESS_REQ_ID",purchaseId)
                .set("PM_PRJ_IDS",JdbcMapUtil.getString(valueMap,"PM_PRJ_IDS"))
                .set("CUSTOMER_UNIT",JdbcMapUtil.getString(valueMap,"CUSTOMER_UNIT"))
                .set("COMPANY_ID",JdbcMapUtil.getString(valueMap,"COMPANY_ID"))
                .set("BUY_MATTER_ID",JdbcMapUtil.getString(valueMap,"BUY_MATTER_ID"))
                .set("BID_PLATFORM",JdbcMapUtil.getString(valueMap,"BID_PLATFORM"))
                .set("WF_PROCESS_INSTANCE_ID",fatherProcessInstanceId)
                .exec();
    }
}
