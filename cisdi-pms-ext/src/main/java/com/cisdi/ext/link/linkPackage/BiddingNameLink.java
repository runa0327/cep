package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkedAtt;
import com.cisdi.ext.link.LinkedRecord;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BiddingNameLink {
    public static AttLinkResult linkForBIDDING_NAME_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String sevId) {
        AttLinkResult attLinkResult = new AttLinkResult();
        // 合同签订的费用明细实体视图
        if ("0099799190825103187".equals(sevId)) {
            List<LinkedRecord> linkedRecordList = new ArrayList<>();
            // 查询明细信息
            String sql = "select COST_TYPE_TREE_ID,FEE_DETAIL,TOTAL_AMT from pm_cost_detail where BIDDING_NAME_ID = ? order by id asc";
            List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql, attValue);
            if (!CollectionUtils.isEmpty(list)) {
                for (Map<String, Object> tmp : list) {
                    LinkedRecord linkedRecord = new LinkedRecord();

                    // 费用类型
                    linkedRecord.valueMap.put("COST_TYPE_TREE_ID", JdbcMapUtil.getString(tmp,"COST_TYPE_TREE_ID"));
                    linkedRecord.textMap.put("COST_TYPE_TREE_ID", JdbcMapUtil.getString(tmp,"COST_TYPE_TREE_ID"));
                    // 费用明细
                    linkedRecord.valueMap.put("FEE_DETAIL", JdbcMapUtil.getString(tmp,"FEE_DETAIL"));
                    linkedRecord.textMap.put("FEE_DETAIL", JdbcMapUtil.getString(tmp,"FEE_DETAIL"));
                    // 费用金额
                    linkedRecord.valueMap.put("TOTAL_AMT", JdbcMapUtil.getString(tmp,"TOTAL_AMT"));
                    linkedRecord.textMap.put("TOTAL_AMT", JdbcMapUtil.getString(tmp,"TOTAL_AMT"));

                    linkedRecordList.add(linkedRecord);
                }
                attLinkResult.childData.put("0099902212142513357", linkedRecordList);
            }
            attLinkResult.childCreatable.put("0099902212142513357", false);
            attLinkResult.childClear.put("0099902212142513357", true);

        }

        // 根据id查询招投标信息
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("SELECT a.PMS_RELEASE_WAY_ID,a.BID_CTL_PRICE_LAUNCH,a.APPROVE_PURCHASE_TYPE,a.WIN_BID_UNIT_TXT,a.TENDER_OFFER,a.CONTACT_MOBILE_WIN,a.CONTACT_NAME_RECORD,a.BID_USER_ID,a.STATUS,a.BID_UNIT, ifnull((SELECT END_DATETIME FROM wf_process_instance WHERE id = a.LK_WF_INST_ID ),0) as END_DATETIME, a.SERVICE_DAYS FROM po_public_bid_req a WHERE id = ?", attValue);
        if (CollectionUtils.isEmpty(list)) {
            throw new BaseException("采购流程相关属性不完善！");
        }
        Map row = list.get(0);
        // 招采类型
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "PMS_RELEASE_WAY_ID");
            linkedAtt.text = JdbcMapUtil.getString(row, "PMS_RELEASE_WAY_ID");

            attLinkResult.attMap.put("PMS_RELEASE_WAY_ID", linkedAtt);
        }
        // 招标控制价
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "BID_CTL_PRICE_LAUNCH");
            linkedAtt.text = JdbcMapUtil.getString(row, "BID_CTL_PRICE_LAUNCH");

            attLinkResult.attMap.put("BID_CTL_PRICE_LAUNCH", linkedAtt);
        }
        // 采购方式
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "APPROVE_PURCHASE_TYPE");
            linkedAtt.text = JdbcMapUtil.getString(row, "APPROVE_PURCHASE_TYPE");

            attLinkResult.attMap.put("PURCHASE_TYPE", linkedAtt);
        }
        // 中标单位
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "WIN_BID_UNIT_TXT");
            linkedAtt.text = JdbcMapUtil.getString(row, "WIN_BID_UNIT_TXT");

            attLinkResult.attMap.put("AUTHOR_UNIT", linkedAtt);
            attLinkResult.attMap.put("AUTHOR_UNIT_TEXT", linkedAtt);
            attLinkResult.attMap.put("WIN_BID_UNIT_TXT", linkedAtt);
        }
        // 中标单位报价
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "TENDER_OFFER");
            linkedAtt.text = JdbcMapUtil.getString(row, "TENDER_OFFER");

            attLinkResult.attMap.put("ENTRUSTING_UNIT", linkedAtt);
            attLinkResult.attMap.put("WINNING_BIDS_AMOUNT", linkedAtt);
        }
        // 进场时间
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DATE;
            linkedAtt.value = JdbcMapUtil.getString(row, "END_DATETIME");
            linkedAtt.text = JdbcMapUtil.getString(row, "END_DATETIME");

            attLinkResult.attMap.put("IN_DATE", linkedAtt);
        }
        // 招标单位
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "BID_UNIT");
            linkedAtt.text = JdbcMapUtil.getString(row, "BID_UNIT");

            attLinkResult.attMap.put("ENTRUSTING_UNIT", linkedAtt);
            attLinkResult.attMap.put("ENTRUSTING_UNIT_TEXT", linkedAtt);
        }
        // 审批状态
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "STATUS");
            linkedAtt.text = JdbcMapUtil.getString(row, "STATUS");

            attLinkResult.attMap.put("APPROVAL_STATUS", linkedAtt);
        }
        // 经办人
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            String id = JdbcMapUtil.getString(row, "BID_USER_ID");
            // 根据id查询人员名称
            String name = "";
            List<Map<String,Object>> map = myJdbcTemplate.queryForList("select name from ad_user where id = ?", id);
            if (!CollectionUtils.isEmpty(map)){
                name = JdbcMapUtil.getString(map.get(0),"name");
            }
            linkedAtt.text = name;
            linkedAtt.value = id;

            attLinkResult.attMap.put("PROCURE_USER_ID", linkedAtt);
        }
        // 对方联系人
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "CONTACT_NAME_RECORD");
            linkedAtt.text = JdbcMapUtil.getString(row, "CONTACT_NAME_RECORD");

            attLinkResult.attMap.put("OTHER_RESPONSOR", linkedAtt);
        }
        // 对方联系方式
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "CONTACT_MOBILE_WIN");
            linkedAtt.text = JdbcMapUtil.getString(row, "CONTACT_MOBILE_WIN");

            attLinkResult.attMap.put("OTHER_CONTACT_PHONE", linkedAtt);
        }
        // 服务周期
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "SERVICE_DAYS");
            linkedAtt.text = JdbcMapUtil.getString(row, "SERVICE_DAYS");

            attLinkResult.attMap.put("SERVICE_DAYS", linkedAtt);
        }
        return attLinkResult;
    }
}
