package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.link.AttLinkParam;
import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkedAtt;
import com.cisdi.ext.link.LinkedRecord;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 关联支付明细码属性联动
 */
public class FundPayCodeVLink {

    /**
     * 关联支付明细码-属性联动-入口
     * @param myJdbcTemplate 数据源
     * @param attValue 属性联动值
     * @param entCode 业务表名
     * @param sevId 视图id
     * @param param 参数
     * @return 数量联动结果
     */
    public static AttLinkResult linkFUND_PAY_CODE_V_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId, AttLinkParam param) {
        AttLinkResult attLinkResult = new AttLinkResult();
        //资金支付明细表选择关联支付明细码后联动
        if ("FUND_NEWLY_INCREASED_DETAIL".equals(entCode)){
            String echoSql = "select pr.name prjName,fs.PM_PRJ_ID prjId,fi.FUND_SOURCE_TEXT sourceName,fid.id sourceId,fi.FUND_CATEGORY_FIRST categoryFirstId,ft1" +
                    ".name categoryFirstName,fi.FUND_CATEGORY_SECOND categorySecondId,ft2.name categorySecondName,fs.CUSTOMER_UNIT customerUnitId,pa.name " +
                    "customerUnitName,pr.PRJ_MANAGE_MODE_ID prjModeId,sv3.name prjModeName,fs.FUND_REACH_CATEGORY fundCategoryId,sv1.name fundCategoryName,sv2.name costCategoryName,op.COST_CATEGORY_ID " +
                    "costCategoryId,fs.nper,fs.PAID_AMT paidAmt,fs.PAY_UNIT payUnitId,rb1.name payUnitName,fs.RECEIPT_BANK receiptBankId,rb2.name " +
                    "receiptBankName,fs.RECEIPT_ACCOUNT receiptAccountId,rb3.name receiptAccountName\n" +
                    "from fund_special fs \n" +
                    "left join (select fid.ID,fi.FUND_SOURCE_TEXT name,fid.PM_PRJ_ID from fund_implementation fi left join " +
                    "fund_implementation_detail fid on fid.FUND_IMPLEMENTATION_ID = fi.id) sourceTemp on sourceTemp.id = fs" +
                    ".FUND_IMPLEMENTATION_V_ID\n" +
                    "left join fund_implementation fi on fi.FUND_SOURCE_TEXT = sourceTemp.name\n" +
                    "left join fund_implementation_detail fid on fid.FUND_IMPLEMENTATION_ID = fi.id\n" +
                    "left join pm_prj pr on pr.id = fs.PM_PRJ_ID\n" +
                    "left join fund_type ft1 on ft1.id = fi.FUND_CATEGORY_FIRST\n" +
                    "left join fund_type ft2 on ft2.id = fi.FUND_CATEGORY_SECOND\n" +
                    "left join PM_PARTY pa on pa.id = fs.CUSTOMER_UNIT\n" +
                    "left join gr_set_value sv1 on sv1.id = fs.FUND_REACH_CATEGORY\n" +
                    "left join gr_set se1 on se1.id = sv1.GR_SET_ID and sv1.code = 'fund_reach_category'\n" +
                    "left join PO_ORDER_PAYMENT_REQ op on op.AMOUT_PM_PRJ_ID = fs.PM_PRJ_ID\n" +
                    "left join gr_set_value sv2 on sv2.id = op.COST_CATEGORY_ID\n" +
                    "left join gr_set se2 on se2.id = sv2.GR_SET_ID and se2.code = 'cost_category'\n" +
                    "left join receiving_bank rb1 on rb1.id = fs.PAY_UNIT\n" +
                    "left join receiving_bank rb2 on rb2.id = fs.RECEIPT_BANK\n" +
                    "left join gr_set_value sv3 on sv3.id = pr.PRJ_MANAGE_MODE_ID\n" +
                    "left join gr_set se3 on se3.id = sv3.GR_SET_ID and se3.code = 'management_unit'\n" +
                    "left join receiving_bank rb3 on rb3.id = fs.RECEIPT_ACCOUNT\n" +
                    "where fs.FUND_PAY_CODE = ?";
            List<Map<String, Object>> echoList = myJdbcTemplate.queryForList(echoSql, attValue);
            if (!CollectionUtils.isEmpty(echoList)){
                Map<String, Object> echoMap = echoList.get(0);
                //项目
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(echoMap,"prjId");
                    linkedAtt.text = JdbcMapUtil.getString(echoMap,"prjName");
                    linkedAtt.changeToEditable = false;
                    attLinkResult.attMap.put("PM_PRJ_ID",linkedAtt);
                }

                //资金来源
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(echoMap,"sourceId");
                    linkedAtt.text = JdbcMapUtil.getString(echoMap,"sourceName");
                    linkedAtt.changeToEditable = false;
                    attLinkResult.attMap.put("FUND_IMPLEMENTATION_V_ID",linkedAtt);
                }

                //资金类别（一级）
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(echoMap,"categoryFirstId");
                    linkedAtt.text = JdbcMapUtil.getString(echoMap,"categoryFirstName");
                    linkedAtt.changeToEditable = false;
                    attLinkResult.attMap.put("FUND_CATEGORY_FIRST",linkedAtt);
                }

                //资金类别（二级）
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(echoMap,"categorySecondId");
                    linkedAtt.text = JdbcMapUtil.getString(echoMap,"categorySecondName");
                    linkedAtt.changeToEditable = false;
                    attLinkResult.attMap.put("FUND_CATEGORY_SECOND",linkedAtt);
                }

                //业主单位
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(echoMap,"customerUnitId");
                    linkedAtt.text = JdbcMapUtil.getString(echoMap,"customerUnitName");
                    linkedAtt.changeToEditable = false;
                    attLinkResult.attMap.put("CUSTOMER_UNIT",linkedAtt);
                }

                //代管单位
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(echoMap,"prjModeId");
                    linkedAtt.text = JdbcMapUtil.getString(echoMap,"prjModeName");
                    linkedAtt.changeToEditable = false;
                    attLinkResult.attMap.put("PRJ_MANAGE_MODE_ID",linkedAtt);
                }

                //期数
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(echoMap,"nper");
                    linkedAtt.text = JdbcMapUtil.getString(echoMap,"nper");
                    linkedAtt.changeToEditable = false;
                    attLinkResult.attMap.put("NPER",linkedAtt);
                }


                //支付信息表(子表)
                String viewId = "0099952822476415265";
                ArrayList<LinkedRecord> linkedRecordList = new ArrayList<>();
                LinkedRecord linkedRecord = new LinkedRecord();
                //资金类别
                linkedRecord.valueMap.put("FUND_REACH_CATEGORY",JdbcMapUtil.getString(echoMap,"fundCategoryId"));
                linkedRecord.textMap.put("FUND_REACH_CATEGORY",JdbcMapUtil.getString(echoMap,"fundCategoryName"));
                //费用大类
                linkedRecord.valueMap.put("COST_CATEGORY_ID",JdbcMapUtil.getString(echoMap,"costCategoryId"));
                linkedRecord.textMap.put("COST_CATEGORY_ID",JdbcMapUtil.getString(echoMap,"costCategoryName"));
                //期数
//                linkedRecord.valueMap.put("NPER",JdbcMapUtil.getInt(echoMap,"nper"));
//                linkedRecord.textMap.put("NPER",JdbcMapUtil.getString(echoMap,"nper"));
                //支付金额
                linkedRecord.valueMap.put("PAY_AMT",JdbcMapUtil.getString(echoMap,"paidAmt"));
                linkedRecord.textMap.put("PAY_AMT",JdbcMapUtil.getString(echoMap,"paidAmt"));
                //付款单位
                linkedRecord.valueMap.put("PAY_UNIT",JdbcMapUtil.getString(echoMap,"payUnitId"));
                linkedRecord.textMap.put("PAY_UNIT",JdbcMapUtil.getString(echoMap,"payUnitName"));
                //支付银行
                linkedRecord.valueMap.put("RECEIPT_BANK",JdbcMapUtil.getString(echoMap,"receiptBankId"));
                linkedRecord.textMap.put("RECEIPT_BANK",JdbcMapUtil.getString(echoMap,"receiptBankName"));
                //支付账户
                linkedRecord.valueMap.put("RECEIPT_ACCOUNT",JdbcMapUtil.getString(echoMap,"receiptAccountId"));
                linkedRecord.textMap.put("RECEIPT_ACCOUNT",JdbcMapUtil.getString(echoMap,"receiptAccountName"));
                linkedRecordList.add(linkedRecord);
                attLinkResult.childData.put(viewId,linkedRecordList);
                attLinkResult.childCreatable.put(viewId, true);
                attLinkResult.childClear.put(viewId, true);
            }


        }

        return attLinkResult;
    }
}
