package com.cisdi.ext.contract;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmTenderExt
 * @package com.cisdi.ext.contract
 * @description 招标扩展
 * @date 2022/9/15
 */
public class PmTenderExt {

    public void createTender() {
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        try {
            Map<String, Object> dataMap = myJdbcTemplate.queryForMap("select * from PO_PUBLIC_BID_REQ where ID=?", csCommId);
            String id = Crud.from("PO_PUBLIC_BID").insertData();
            Crud.from("PO_PUBLIC_BID").where().eq("ID", id).update()
                    .set("NAME", JdbcMapUtil.getString(dataMap, "NAME"))
                    .set("PM_PRJ_ID", JdbcMapUtil.getString(dataMap, "PM_PRJ_ID"))
//                    .set("PRJ_CODE", JdbcMapUtil.getString(dataMap, ""))
//                    .set("PRJ_REPLY_NO", JdbcMapUtil.getString(dataMap, ""))
//                    .set("PRJ_SITUATION", JdbcMapUtil.getString(dataMap, ""))
//                    .set("INVESTMENT_SOURCE_ID", JdbcMapUtil.getString(dataMap, ""))
//                    .set("CUSTOMER_UNIT", JdbcMapUtil.getString(dataMap, ""))
                    .set("PMS_RELEASE_WAY_ID", JdbcMapUtil.getString(dataMap, "PMS_RELEASE_WAY_ID"))
//                    .set("FEASIBILITY_APPROVE_FUND", JdbcMapUtil.getString(dataMap, ""))
//                    .set("ESTIMATE_APPROVE_FUND", JdbcMapUtil.getString(dataMap, ""))
//                    .set("EVALUATION_APPROVE_FUND", JdbcMapUtil.getString(dataMap, ""))
                    .set("BID_UNIT", JdbcMapUtil.getString(dataMap, "BID_UNIT"))
                    .set("BID_BASIS", JdbcMapUtil.getString(dataMap, "BID_BASIS"))
                    .set("BID_CTL_PRICE_LAUNCH", JdbcMapUtil.getString(dataMap, "BID_CTL_PRICE_LAUNCH"))
                    .set("SERVICE_DAYS", JdbcMapUtil.getString(dataMap, "SERVICE_DAYS"))
                    .set("BID_DEMAND_FILE_GROUP_ID", JdbcMapUtil.getString(dataMap, "BID_DEMAND_FILE_GROUP_ID"))
                    .set("REMARK", JdbcMapUtil.getString(dataMap, "REMARK"))
                    .set("APPROVE_PMS_RELEASE_WAY_ID", JdbcMapUtil.getString(dataMap, "APPROVE_PMS_RELEASE_WAY_ID"))
                    .set("APPROVE_PURCHASE_TYPE", JdbcMapUtil.getString(dataMap, "APPROVE_PURCHASE_TYPE"))
                    .set("APPROVE_BID_CTL_PRICE", JdbcMapUtil.getString(dataMap, "APPROVE_BID_CTL_PRICE"))
                    .set("LEADER_APPROVE_COMMENT", JdbcMapUtil.getString(dataMap, "LEADER_APPROVE_COMMENT"))
                    .set("LEADER_APPROVE_FILE_GROUP_ID", JdbcMapUtil.getString(dataMap, "LEADER_APPROVE_FILE_GROUP_ID"))
                    .set("APPROVE_PURCHASE_TYPE_ECHO", JdbcMapUtil.getString(dataMap, "APPROVE_PURCHASE_TYPE_ECHO"))
                    .set("BID_CTL_PRICE_LAUNCH_ECHO", JdbcMapUtil.getString(dataMap, "BID_CTL_PRICE_LAUNCH_ECHO"))
                    .set("BID_USER_ID", JdbcMapUtil.getString(dataMap, "BID_USER_ID"))
                    .set("BID_AGENCY", JdbcMapUtil.getString(dataMap, "BID_AGENCY"))
                    .set("DEMAND_PROMOTER", JdbcMapUtil.getString(dataMap, "DEMAND_PROMOTER"))
                    .set("BID_FILE_GROUP_ID", JdbcMapUtil.getString(dataMap, "BID_FILE_GROUP_ID"))
                    .set("REGISTRATION_DATE", JdbcMapUtil.getString(dataMap, "REGISTRATION_DATE"))
                    .set("BID_OPEN_DATE", JdbcMapUtil.getString(dataMap, "BID_OPEN_DATE"))
                    .set("BID_PLATFORM", JdbcMapUtil.getString(dataMap, "BID_PLATFORM"))
                    .set("BID_ISSUE_INFO", JdbcMapUtil.getString(dataMap, "BID_ISSUE_INFO"))
                    .set("SEAL_AGENT_APPROVE_COMMENT", JdbcMapUtil.getString(dataMap, "SEAL_AGENT_APPROVE_COMMENT"))
                    .set("BID_ISSUE_FILE_GROUP_ID", JdbcMapUtil.getString(dataMap, "BID_ISSUE_FILE_GROUP_ID"))
                    .set("SEAL_APPROVE_COMMENT", JdbcMapUtil.getString(dataMap, "SEAL_APPROVE_COMMENT"))
                    .set("WIN_BID_UNIT_TXT", JdbcMapUtil.getString(dataMap, "WIN_BID_UNIT_TXT"))
                    .set("TENDER_OFFER", JdbcMapUtil.getString(dataMap, "TENDER_OFFER"))
                    .set("CONTACT_NAME", JdbcMapUtil.getString(dataMap, "CONTACT_NAME"))
                    .set("CONTACT_MOBILE_WIN", JdbcMapUtil.getString(dataMap, "CONTACT_MOBILE_WIN"))
                    .set("CONTACT_IDCARD_WIN", JdbcMapUtil.getString(dataMap, "CONTACT_IDCARD_WIN"))
                    .set("BID_AFTER_FILE_GROUP_ID", JdbcMapUtil.getString(dataMap, "BID_AFTER_FILE_GROUP_ID"))
                    .set("BID_AFTER_APPROVE_COMMENT", JdbcMapUtil.getString(dataMap, "BID_AFTER_APPROVE_COMMENT"))
                    .set("WIN_BID_UNIT_RECORD", JdbcMapUtil.getString(dataMap, "WIN_BID_UNIT_RECORD"))
                    .set("TENDER_OFFER_RECORD", JdbcMapUtil.getString(dataMap, "TENDER_OFFER_RECORD"))
                    .set("IS_RECORD", JdbcMapUtil.getString(dataMap, "IS_RECORD"))
                    .set("CONTACT_NAME_RECORD", JdbcMapUtil.getString(dataMap, "CONTACT_NAME_RECORD"))
                    .set("CONTACT_MOBILE_RECORD", JdbcMapUtil.getString(dataMap, "CONTACT_MOBILE_RECORD"))
                    .set("CONTACT_IDCARD_RECORD", JdbcMapUtil.getString(dataMap, "CONTACT_IDCARD_RECORD"))
                    .set("BID_WIN_NOTICE_FILE_GROUP_ID ", JdbcMapUtil.getString(dataMap, "BID_WIN_NOTICE_FILE_GROUP_ID"))
                    .exec();
        } catch (Exception e) {
            throw new BaseException("查询招标申请数据异常！", e);
        }
    }
}
