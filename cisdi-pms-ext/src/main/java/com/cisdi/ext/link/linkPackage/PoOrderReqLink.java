package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkedAtt;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public class PoOrderReqLink {
    public static AttLinkResult linkForRELATION_CONTRACT_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String sevId, String entCode) {
        AttLinkResult attLinkResult = new AttLinkResult();

        // 根据id查询招投标信息
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select CONTACT_MOBILE_ONE,AD_USER_ID,YES_NO_THREE,GUARANTEE_LETTER_TYPE_IDS,IS_REFER_GUARANTEE_ID,PLAN_TOTAL_DAYS," +
                "CONTRACT_CATEGORY_ONE_ID,FILE_ID_FIVE,WIN_BID_UNIT_ONE,AMT_ONE,WINNING_BIDS_AMOUNT,BUY_TYPE_ID,BID_CTL_PRICE_LAUNCH,BUY_MATTER_ID," +
                "PM_BID_KEEP_FILE_REQ_ID,CONTRACT_NAME,PM_BID_KEEP_FILE_REQ_ID,CONTRACT_CODE,NAME,WIN_BID_UNIT_ONE,CUSTOMER_UNIT_ONE," +
                "CONTRACT_PRICE,ATT_FILE_GROUP_ID,AMT_ONE,AMT_TWO,AMT_THREE,AMT_FOUR from po_order_req where id = ?", attValue);

        if (CollectionUtils.isEmpty(list)) {
            throw new BaseException("合同相关属性不完善！");
        }
        Map row = list.get(0);

        //查询明细表信息
        if ("0099902212142025475".equals(sevId) || "0099902212142022303".equals(sevId)){ //0099902212142025475(合同终止) 0099902212142022303(补充协议)
            //流程明细表获取合同费用明细信息
            AttLinkExtDetail.getOrderPayDetail(attLinkResult,sevId,myJdbcTemplate,attValue);
            AttLinkExtDetail.getOrderContactsDetail(attLinkResult,sevId,myJdbcTemplate,attValue);
        } else if ("0099902212142028526".equals(sevId)){ //00099902212142028526资金需求计划申请(资金需求计划申请)
            //流程明细表获取合同费用明细信息
            AttLinkExtDetail.getOrderPayDetail(attLinkResult,sevId,myJdbcTemplate,attValue);
        }

        // 签订公司
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            String id = JdbcMapUtil.getString(row, "CUSTOMER_UNIT_ONE");
            String name = "";
            String sql1 = "select name from PM_PARTY where id = ?";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,id);
            if (!CollectionUtils.isEmpty(list1)){
                name = JdbcMapUtil.getString(list1.get(0),"name");
            }
            linkedAtt.value = id;
            linkedAtt.text = name;
            attLinkResult.attMap.put("CUSTOMER_UNIT_ONE", linkedAtt);
        }
        //首付款比列(%)
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = StringUtil.getBigDecimal(JdbcMapUtil.getString(row, "AMT_ONE"));
            linkedAtt.text = String.valueOf(StringUtil.getBigDecimal(JdbcMapUtil.getString(row, "AMT_ONE")));
            attLinkResult.attMap.put("AMT_ONE", linkedAtt);
        }
        //含税总金额(元)
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = StringUtil.getBigDecimal(JdbcMapUtil.getString(row, "AMT_TWO"));
            linkedAtt.text = JdbcMapUtil.getString(row, "AMT_TWO");
            attLinkResult.attMap.put("AMT_TWO", linkedAtt);
        }
        //不含税总金额(元)
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = StringUtil.getBigDecimal(JdbcMapUtil.getString(row, "AMT_THREE"));
            linkedAtt.text = JdbcMapUtil.getString(row, "AMT_THREE");
            attLinkResult.attMap.put("AMT_THREE", linkedAtt);
        }
        //税金(%)
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = StringUtil.getBigDecimal(JdbcMapUtil.getString(row, "AMT_FOUR"));
            linkedAtt.text = JdbcMapUtil.getString(row, "AMT_FOUR");
            attLinkResult.attMap.put("AMT_FOUR", linkedAtt);
        }
        // 合同编号
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "CONTRACT_CODE");
            linkedAtt.text = JdbcMapUtil.getString(row, "CONTRACT_CODE");
            attLinkResult.attMap.put("CONTRACT_CODE", linkedAtt);
        }
        // 合同名称
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "CONTRACT_NAME");
            linkedAtt.text = JdbcMapUtil.getString(row, "CONTRACT_NAME");
            attLinkResult.attMap.put("CONTRACT_NAME", linkedAtt);
        }
        // 中标单位
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "WIN_BID_UNIT_ONE");
            linkedAtt.text = JdbcMapUtil.getString(row, "WIN_BID_UNIT_ONE");
            attLinkResult.attMap.put("WIN_BID_UNIT_ONE", linkedAtt);
        }
        // 合同总金额
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "CONTRACT_PRICE");
            linkedAtt.text = JdbcMapUtil.getString(row, "CONTRACT_PRICE");
            attLinkResult.attMap.put("CONTRACT_PRICE", linkedAtt);
        }
        //关联招采流程
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
        // 招标类别
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            String id = JdbcMapUtil.getString(row, "BUY_TYPE_ID");
            String name = "";
            String sql2 = "select name from gr_set_value where id = ?";
            List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,id);
            if (!CollectionUtils.isEmpty(list2)){
                name = JdbcMapUtil.getString(list2.get(0),"name");
            }
            linkedAtt.value = id;
            linkedAtt.text = name;
            attLinkResult.attMap.put("BUY_TYPE_ID", linkedAtt);
        }
        //招标控制价
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = JdbcMapUtil.getString(row,"BID_CTL_PRICE_LAUNCH");
            linkedAtt.text = JdbcMapUtil.getString(row,"BID_CTL_PRICE_LAUNCH");
            attLinkResult.attMap.put("BID_CTL_PRICE_LAUNCH",linkedAtt);
        }
        //采购方式
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            String id = JdbcMapUtil.getString(row,"BUY_MATTER_ID");
            String name = "";
            String sql2 = "select name from gr_set_value where id = ?";
            List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,id);
            if (!CollectionUtils.isEmpty(list2)){
                name = JdbcMapUtil.getString(list2.get(0),"name");
            }
            linkedAtt.value = id;
            linkedAtt.text = name;
            attLinkResult.attMap.put("BUY_MATTER_ID",linkedAtt);
        }
        //中标价
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = JdbcMapUtil.getString(row,"WINNING_BIDS_AMOUNT");
            linkedAtt.text = JdbcMapUtil.getString(row,"WINNING_BIDS_AMOUNT");
            attLinkResult.attMap.put("WINNING_BIDS_AMOUNT",linkedAtt);
        }
        //合同工期
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row,"PLAN_TOTAL_DAYS");
            linkedAtt.text = JdbcMapUtil.getString(row,"PLAN_TOTAL_DAYS");
            attLinkResult.attMap.put("PLAN_TOTAL_DAYS",linkedAtt);
        }
        //是否涉及保函 0099902212142031851=是；0099902212142031855=否
        String baoHanId = JdbcMapUtil.getString(row,"IS_REFER_GUARANTEE_ID");
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            String name = "";
            String sql2 = "select name from gr_set_value where id = ?";
            List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,baoHanId);
            if (!CollectionUtils.isEmpty(list2)){
                name = JdbcMapUtil.getString(list2.get(0),"name");
            }
            linkedAtt.value = baoHanId;
            linkedAtt.text = name;
            attLinkResult.attMap.put("IS_REFER_GUARANTEE_ID",linkedAtt);
        }
        // 保函类型
        Boolean isShow = false;
        if ("0099902212142031851".equals(baoHanId)){
            isShow = true;
        }
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            String ids = JdbcMapUtil.getString(row,"GUARANTEE_LETTER_TYPE_IDS");
            String id = StringUtil.codeToSplit(ids);
            String name = "";
            String sql2 = "select group_concat(name) as name from gr_set_value where id in ('"+id+"')";
            List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2);
            if (!CollectionUtils.isEmpty(list2)){
                name = JdbcMapUtil.getString(list2.get(0),"name");
            }
            linkedAtt.value = ids;
            linkedAtt.text = name;
            attLinkResult.attMap.put("GUARANTEE_LETTER_TYPE_IDS",linkedAtt);
        }
        //是否标准模板

        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            String id = JdbcMapUtil.getString(row,"YES_NO_THREE");
            String name = "";
            String sql2 = "select name from gr_set_value where id = ?";
            List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,id);
            if (!CollectionUtils.isEmpty(list2)){
                name = JdbcMapUtil.getString(list2.get(0),"name");
            }
            linkedAtt.value = id;
            linkedAtt.text = name;
            attLinkResult.attMap.put("YES_NO_THREE",linkedAtt);
        }
        // 合同签订联系人
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            String userId = JdbcMapUtil.getString(row, "AD_USER_ID");
            List<Map<String, Object>> userNameList = myJdbcTemplate.queryForList("select name from ad_user where id = ?", userId);
            if (!CollectionUtils.isEmpty(userNameList)){
                linkedAtt.value = userId;
                linkedAtt.text = JdbcMapUtil.getString(userNameList.get(0),"name");
            }
            attLinkResult.attMap.put("AD_USER_ID", linkedAtt);
        }
        // 联系电话
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "CONTACT_MOBILE_ONE");
            linkedAtt.text = JdbcMapUtil.getString(row, "CONTACT_MOBILE_ONE");
            attLinkResult.attMap.put("CONTACT_MOBILE_ONE", linkedAtt);
        }
        //合同原稿（正式合同附件）
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.FILE_GROUP;
            linkedAtt.value = JdbcMapUtil.getString(row,"FILE_ID_FIVE");
            AttLinkExtDetail.getFileInfoList(linkedAtt);
            attLinkResult.attMap.put("FILE_ID_SEVEN",linkedAtt);
        }
        //资金需求申请合同附件回显
        if ("PM_FUND_REQUIRE_PLAN_REQ".equals(entCode) || "PO_ORDER_SUPPLEMENT_REQ".equals(entCode)){
            //合同附件
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.FILE_GROUP;
                linkedAtt.value = JdbcMapUtil.getString(row,"ATT_FILE_GROUP_ID");
                AttLinkExtDetail.getFileInfoList(linkedAtt);
                attLinkResult.attMap.put("CONTRACT_FILE_GROUP_ID", linkedAtt);
            }
        }

        return attLinkResult;
    }
}
