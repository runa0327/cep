package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkedAtt;
import com.cisdi.ext.link.LinkedRecord;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContractLink {
    public static AttLinkResult linkForCONTRACT_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String sevId, String entCode) {
        AttLinkResult attLinkResult = new AttLinkResult();

        // 根据id查询招投标信息
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select CUSTOMER_UNIT_ONE,CONTRACT_CODE, CONTRACT_CATEGORY_ONE_ID, CONTRACT_NAME, CONTRACT_PRICE, " +
                "PM_BID_KEEP_FILE_REQ_ID,BID_CTL_PRICE_LAUNCH,WINNING_BIDS_AMOUNT,PLAN_TOTAL_DAYS," +
                "IS_REFER_GUARANTEE_ID,GUARANTEE_LETTER_TYPE_IDS,YES_NO_THREE,BUY_TYPE_ID,WIN_BID_UNIT_ONE " +
                "from po_order_req where id = ?", attValue);

        if (CollectionUtils.isEmpty(list)) {
            throw new BaseException("合同签订相关属性不完善！");
        }
        Map row = list.get(0);
        // 合同编号
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "CONTRACT_CODE");
            linkedAtt.text = JdbcMapUtil.getString(row, "CONTRACT_CODE");
            attLinkResult.attMap.put("CONTRACT_CODE", linkedAtt);
        }
        // 合同类型
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            String id = JdbcMapUtil.getString(row, "CONTRACT_CATEGORY_ONE_ID");
            String name = "";
            String sql2 = "select name from gr_set_value where id = ?";
            List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,id);
            if (!CollectionUtils.isEmpty(list2)){
                name = JdbcMapUtil.getString(list2.get(0),"name");
            }
            linkedAtt.value = id;
            linkedAtt.text = name;
            attLinkResult.attMap.put("CONTRACT_CATEGORY_ONE_ID", linkedAtt);
        }
        //合同签订公司
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            String id = JdbcMapUtil.getString(row, "CUSTOMER_UNIT_ONE");
            String name = "";
            String sql2 = "select name from gr_set_value where id = ?";
            List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,id);
            if (!CollectionUtils.isEmpty(list2)){
                name = JdbcMapUtil.getString(list2.get(0),"name");
            }
            linkedAtt.value = id;
            linkedAtt.text = name;
            attLinkResult.attMap.put("CUSTOMER_UNIT_ONE", linkedAtt);
        }
        // 关联招采
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            String id = JdbcMapUtil.getString(row, "PM_BID_KEEP_FILE_REQ_ID");
            String name = "";
            String sql1 = "select name from PM_BID_KEEP_FILE_REQ where id = ?";
            List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql1,id);
            if (!CollectionUtils.isEmpty(list2)){
                name = JdbcMapUtil.getString(list2.get(0),"name");
            }
            linkedAtt.value = id;
            linkedAtt.text = name;
            attLinkResult.attMap.put("PM_BID_KEEP_FILE_REQ_ID", linkedAtt);
        }
        // 招标类别
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "BUY_TYPE_ID");
            linkedAtt.text = JdbcMapUtil.getString(row, "BUY_TYPE_ID");
            attLinkResult.attMap.put("BUY_TYPE_ID", linkedAtt);
        }
        // 招标控制价
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "BID_CTL_PRICE_LAUNCH");
            linkedAtt.text = JdbcMapUtil.getString(row, "BID_CTL_PRICE_LAUNCH");
            attLinkResult.attMap.put("BID_CTL_PRICE_LAUNCH", linkedAtt);
        }
        // 中标单位
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "WIN_BID_UNIT_ONE");
            linkedAtt.text = JdbcMapUtil.getString(row, "WIN_BID_UNIT_ONE");
            attLinkResult.attMap.put("WIN_BID_UNIT_ONE", linkedAtt);
        }
        // 中标价
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "WINNING_BIDS_AMOUNT");
            linkedAtt.text = JdbcMapUtil.getString(row, "WINNING_BIDS_AMOUNT");
            attLinkResult.attMap.put("WINNING_BIDS_AMOUNT", linkedAtt);
        }
        // 合同工期
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.INTEGER;
            linkedAtt.value = JdbcMapUtil.getString(row, "PLAN_TOTAL_DAYS");
            linkedAtt.text = JdbcMapUtil.getString(row, "PLAN_TOTAL_DAYS");
            attLinkResult.attMap.put("PLAN_TOTAL_DAYS", linkedAtt);
        }

        //保函逻辑可改
        List<String> editGuarantee = AttLinkDifferentProcess.getEditGuarantee();
        Boolean editIsBaoHan = true;  //是否涉及保函可改
        Boolean editHaoHanType = true; //保函类型可改
        Boolean editIsBaoHanMust = true; //是否涉及保函必填
        Boolean editHaoHanTypeMust = true; //保函类型必填
        if (editGuarantee.contains(entCode)){
            editIsBaoHan = false;
            editHaoHanType = false;
            editIsBaoHanMust = false;
            editHaoHanTypeMust = false;
        }

        // 是否涉及保函
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            String code = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "IS_REFER_GUARANTEE_ID")) ? "0":JdbcMapUtil.getString(row, "IS_REFER_GUARANTEE_ID");
            List<Map<String, Object>> idMap = myJdbcTemplate.queryForList("SELECT v.id id FROM gr_set_value v left join gr_set k on v.GR_SET_ID = k.id where k.`CODE` = 'is_refer_guarantee' and v.`id` = ?", code);
            if (!CollectionUtils.isEmpty(idMap)){
                linkedAtt.value = JdbcMapUtil.getString(idMap.get(0),"id");
                linkedAtt.text = JdbcMapUtil.getString(idMap.get(0),"id");
                linkedAtt.changeToEditable = editIsBaoHan;
                linkedAtt.changeToMandatory = editIsBaoHanMust;
            }
            attLinkResult.attMap.put("IS_REFER_GUARANTEE_ID", linkedAtt);
        }
        //关联合同不需要自动带出保函类型的流程
        List<String> notAutoBaoHan = AttLinkDifferentProcess.getNotAutoBaoHan();
        if (!notAutoBaoHan.contains(entCode)){
            // 保函类型
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = JdbcMapUtil.getString(row, "GUARANTEE_LETTER_TYPE_IDS");
                linkedAtt.text = JdbcMapUtil.getString(row, "GUARANTEE_LETTER_TYPE_IDS");
                linkedAtt.changeToEditable = editHaoHanType;
                linkedAtt.changeToMandatory = editHaoHanTypeMust;
                attLinkResult.attMap.put("GUARANTEE_LETTER_TYPE_ID", linkedAtt);
                attLinkResult.attMap.put("GUARANTEE_LETTER_TYPE_IDS", linkedAtt);
            }
        }

        // 是否标准模板
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            String id = JdbcMapUtil.getString(row, "YES_NO_THREE");
            String name = "";
            List<Map<String, Object>> idMap = myJdbcTemplate.queryForList("SELECT name from gr_set_value where id = ?",id);
            if (!CollectionUtils.isEmpty(idMap)){
                name = JdbcMapUtil.getString(idMap.get(0),"name");
            }
            linkedAtt.value = id;
            linkedAtt.text = name;
            attLinkResult.attMap.put("YES_NO_THREE", linkedAtt);
        }

        // 合同名称带序号
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "CONTRACT_NAME");
            linkedAtt.text = JdbcMapUtil.getString(row, "CONTRACT_NAME");
            attLinkResult.attMap.put("CONTRACT_NAME", linkedAtt);
        }

        // 合同总金额
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "CONTRACT_PRICE");
            linkedAtt.text = JdbcMapUtil.getString(row, "CONTRACT_PRICE");

            attLinkResult.attMap.put("CONTRACT_PRICE", linkedAtt);
            attLinkResult.attMap.put("CONTRACT_AMOUNT", linkedAtt);
        }

        // 明细信息
        if ("0099902212142033284".equals(sevId)) {
            List<LinkedRecord> linkedRecordList = new ArrayList<>();
            // 查询明细信息
            String sql = "select COST_TYPE_TREE_ID,FEE_DETAIL,TOTAL_AMT,AMT from pm_order_cost_detail where CONTRACT_ID = ? order by id asc";
            List<Map<String, Object>> list1 = myJdbcTemplate.queryForList(sql, attValue);
            if (!CollectionUtils.isEmpty(list1)) {
                for (Map<String, Object> tmp : list1) {
                    LinkedRecord linkedRecord = new LinkedRecord();

                    // 费用类型
                    String payType = tmp.get("COST_TYPE_TREE_ID").toString();
                    linkedRecord.valueMap.put("COST_TYPE_TREE_ID", payType);
                    linkedRecord.textMap.put("COST_TYPE_TREE_ID", payType);
                    // 费用明细
                    linkedRecord.valueMap.put("FEE_DETAIL", tmp.get("FEE_DETAIL").toString());
                    linkedRecord.textMap.put("FEE_DETAIL", tmp.get("FEE_DETAIL").toString());
                    // 费用金额
                    BigDecimal TOTAL_AMT = new BigDecimal(tmp.get("TOTAL_AMT").toString());
                    linkedRecord.valueMap.put("TOTAL_AMT", String.valueOf(TOTAL_AMT));
                    linkedRecord.textMap.put("TOTAL_AMT", String.valueOf(TOTAL_AMT));
                    // 合同金额
                    linkedRecord.valueMap.put("AMT", tmp.get("AMT").toString());
                    linkedRecord.textMap.put("AMT", tmp.get("AMT").toString());

                    // 查询明细付款信息
                    String sql2 = "SELECT a.PAY_AMT_TWO,a.PAY_AMT_ONE FROM PM_PAY_COST_DETAIL a left join PO_ORDER_PAYMENT_REQ b on a.PARENT_ID = b.id WHERE a.CONTRACT_ID = ? AND a.COST_TYPE_TREE_ID = ? AND b.STATUS = 'AP' order by a.CRT_DT asc";
                    List<Map<String, Object>> list2 = myJdbcTemplate.queryForList(sql2, attValue, payType);
                    if (CollectionUtils.isEmpty(list2)) {
                        // 已付金额
                        linkedRecord.valueMap.put("PAY_AMT_TWO", "0");
                        linkedRecord.textMap.put("PAY_AMT_TWO", "0");
                        // 可付金额
                        linkedRecord.valueMap.put("REQ_AMT", tmp.get("AMT").toString());
                        linkedRecord.textMap.put("REQ_AMT", tmp.get("AMT").toString());
                    } else {
                        // 已付金额
                        BigDecimal priceDetail = bigDecimalSum(list2);
                        linkedRecord.valueMap.put("PAY_AMT_TWO", String.valueOf(priceDetail));
                        linkedRecord.textMap.put("PAY_AMT_TWO", String.valueOf(priceDetail));
                        // 可付金额
                        BigDecimal REQ_AMT = new BigDecimal(tmp.get("AMT").toString());
                        BigDecimal cha = REQ_AMT.subtract(priceDetail);
                        linkedRecord.valueMap.put("REQ_AMT", String.valueOf(cha));
                        linkedRecord.textMap.put("REQ_AMT", String.valueOf(cha));
                    }
                    // 本期支付
//                    linkedRecord.valueMap.put("PAY_AMT_ONE", "0");
//                    linkedRecord.textMap.put("PAY_AMT_ONE", "0");

                    linkedRecordList.add(linkedRecord);
                }
                attLinkResult.childData.put("0099902212142514118", linkedRecordList);
            }
            attLinkResult.childCreatable.put("0099902212142514118", false);
            attLinkResult.childClear.put("0099902212142514118", true);
        }

        //需要带出相对方联系人明细的实体试图
        List<String> contactDetailList = AttLinkDifferentProcess.getContactDetailList();
        //需要带出联系人明细的实体试图(只读)
        List<String> contactListRead = AttLinkDifferentProcess.getContactListRead();
        //联系人明细信息
        if (contactDetailList.contains(sevId)) {
            String viewId = "";
            if ("0099902212142025475".equals(sevId)){
                viewId = "0099952822476384659"; //合同终止联系人明细试图部门id
            }
            //查询明细信息
            String sql2 = "select * from CONTRACT_SIGNING_CONTACT where PARENT_ID = ?";
            List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,attValue);
            List<LinkedRecord> linkedRecordList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(list2)){
                for (Map<String, Object> tmp : list2) {
                    LinkedRecord linkedRecord = new LinkedRecord();
                    //相对方联系人
                    String CONTRACT_SIGNING_CONTACT = tmp.get("OPPO_SITE_LINK_MAN").toString();
                    linkedRecord.valueMap.put("OPPO_SITE_LINK_MAN", CONTRACT_SIGNING_CONTACT);
                    linkedRecord.textMap.put("OPPO_SITE_LINK_MAN", CONTRACT_SIGNING_CONTACT);
                    //相对方联系方式
                    String OPPO_SITE_CONTACT = tmp.get("OPPO_SITE_CONTACT").toString();
                    linkedRecord.valueMap.put("OPPO_SITE_CONTACT", OPPO_SITE_CONTACT);
                    linkedRecord.textMap.put("OPPO_SITE_CONTACT", OPPO_SITE_CONTACT);
                    //相对方公司
                    String WIN_BID_UNIT_ONE = tmp.get("WIN_BID_UNIT_ONE").toString();
                    linkedRecord.valueMap.put("WIN_BID_UNIT_ONE", WIN_BID_UNIT_ONE);
                    linkedRecord.textMap.put("WIN_BID_UNIT_ONE", WIN_BID_UNIT_ONE);
                    linkedRecordList.add(linkedRecord);
                }
                attLinkResult.childData.put(viewId, linkedRecordList);
            }
            attLinkResult.childCreatable.put(viewId, false);
            attLinkResult.childClear.put(viewId, true);
        }
        return attLinkResult;
    }

    /**
     * list内求和
     * @param list list
     * @return 累计已付金额
     */
    private static BigDecimal bigDecimalSum(List<Map<String, Object>> list) {
        BigDecimal sum = new BigDecimal(0);
        for (int i = 0; i < list.size(); i++) {
            String date;
            if (i == 0) {
                date = list.get(0).get("PAY_AMT_ONE").toString();
            } else {
                date = list.get(i).get("PAY_AMT_TWO").toString();
            }
            sum = sum.add(new BigDecimal(date));
        }
        return sum;
    }
}
