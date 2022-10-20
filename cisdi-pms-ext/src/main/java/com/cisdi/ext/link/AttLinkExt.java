package com.cisdi.ext.link;

import com.cisdi.ext.util.ConvertUtils;
import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class AttLinkExt {

    public void attLink() {
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        AttLinkParam input = JsonUtil.fromJson(json, AttLinkParam.class);

        // 逻辑处理：
        AttLinkResult output = attLink(input);


        // 返回输出：
        // 转换为Map再设置到返回值；若直接将对象设置到返回值，调试时（通过MQ返回给平台）可能无法解析出相应的类：
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(output), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    private AttLinkResult attLink(AttLinkParam param) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        String attCode = param.attCode;
        String attValue = param.attValue;
        String sevId = param.sevId;

        // 获取实体代码（表名）：
        String entCode = myJdbcTemplate.queryForMap("select e.code ent_code from ad_single_ent_view sev join ad_ent e on sev.AD_ENT_ID = e.ID and sev.id=?", sevId).get("ent_code").toString();

        if ("PROJECT_TYPE_ID".equals(attCode)) {
            return linkForPROJECT_TYPE_ID(myJdbcTemplate, attValue);
        } else if ("PM_PRJ_ID".equals(attCode)) {
            return linkForPM_PRJ_ID(myJdbcTemplate, attValue, entCode);
        } else if ("PMS_RELEASE_WAY_ID".equals(attCode) || "GUARANTEE_LETTER_TYPE_ID".equals(attCode) || "CONTRACT_CATEGORY_ID".equals(attCode) || "PRJ_MANAGE_MODE_ID".equals(attCode)) {
            return linkForMany(myJdbcTemplate, attCode, attValue);
        } else if ("BIDDING_NAME_ID".equals(attCode)) {
            return linkForBIDDING_NAME_ID(myJdbcTemplate, attValue, sevId);
        } else if ("CONTRACT_ID".equals(attCode)) {
            return linkForCONTRACT_ID(myJdbcTemplate, attValue, sevId,entCode);
        } else if (("RELATION_CONTRACT_ID").equals(attCode)) {
            return linkForRELATION_CONTRACT_ID(myJdbcTemplate, attValue,sevId,entCode);
        } else if ("GUARANTEE_ID".equals(attCode)) {
            return linkForGUARANTEE_ID(myJdbcTemplate, attValue);
        } else if ("AMOUT_PM_PRJ_ID".equals(attCode)) {
            return linkForAMOUT_PM_PRJ_ID(myJdbcTemplate, attValue, entCode);
        } else if ("STATUS".equals(attCode)) {
            return linkForSTATUS(attValue, sevId);
        } else if ("RELATION_AMOUT_PLAN_REQ_ID".equals(attCode)){ //资金需求计划联动属性
            return linkForRELATION_AMOUT_PLAN_REQ_ID(myJdbcTemplate, attValue, entCode);
        } else if ("PAY_BASIS_ID".equals(attCode)){ //付款依据属性联动
            return linkPAY_BASIS_ID(myJdbcTemplate, attValue, entCode,sevId);
        } else if ("YES_NO_ONE".equals(attCode)){ // 是否判断
            return linkYES_NO_ONE(myJdbcTemplate, attValue, entCode,sevId);
        } else if ("YES_NO_THREE".equals(attCode)){ // 是否判断
            return linkYES_NO_THREE(myJdbcTemplate, attValue, entCode,sevId);
        } else if ("YES_NO_TWO".equals(attCode)){ // 有无判断
            return linkYES_NO_TWO(myJdbcTemplate, attValue, entCode,sevId);
        } else if ("IS_REFER_GUARANTEE_ID".equals(attCode)){ // 是否涉及保函
            return linkIS_REFER_GUARANTEE_ID(myJdbcTemplate, attValue, entCode,sevId);
        } else if ("REASON".equals(attCode)){ // 退还原因
            return linkREASON(myJdbcTemplate, attValue, entCode,sevId);
        } else if ("BUY_TYPE_ID".equals(attCode)){ // 招采方式
            return linkBUY_TYPE_ID(myJdbcTemplate, attValue, entCode,sevId);
        } else if ("BUY_START_BASIS_ID".equals(attCode)){ // 采购启动依据属性联动
            return linkBUY_START_BASIS_ID(myJdbcTemplate, attValue, entCode,sevId,param);
        } else if ("PM_USE_CHAPTER_REQ_ID".equals(attCode)){ // 中标单位及标后用章属性联动
            return linkPM_USE_CHAPTER_REQ_ID(myJdbcTemplate, attValue, entCode,sevId,param);
        } else if ("TYPE_ONE_ID".equals(attCode)){ // 关联流程或上传依据 属性联动
            return linkTYPE_ONE_ID(myJdbcTemplate, attValue, entCode,sevId);
        } else if ("PM_BUY_DEMAND_REQ_ID".equals(attCode)){ // 关联采购需求审批表 属性联动
            return linkPM_BUY_DEMAND_REQ_ID(myJdbcTemplate, attValue, entCode,sevId);
        } else if ("PAY_TYPE_ID".equals(attCode)){ // 付款类型 属性联动
            return linkPAY_TYPE_ID(myJdbcTemplate, attValue, entCode,sevId,param);
        } else if ("PM_BID_KEEP_FILE_REQ_ID".equals(attCode)){ // 招采项目备案及归档 属性联动
            return linkPM_BID_KEEP_FILE_REQ_ID(myJdbcTemplate, attValue, entCode,sevId,param);
        } else if ("FUND_IMPLEMENTATION_V_ID".equals(attCode)){ // 关联资金来源 属性联动
            return linkFUND_IMPLEMENTATION_V_ID(myJdbcTemplate, attValue, entCode,sevId,param);
        } else if ("FUND_PAY_CODE_V_ID".equals(attCode)){ // 关联支付明细码 属性联动
            return linkFUND_PAY_CODE_V_ID(myJdbcTemplate, attValue, entCode,sevId,param);
        } else if ("PM_BID_APPROVAL_REQ_ID".equals(attCode)){ // 招标文件审批 属性联动
            return linkPM_BID_APPROVAL_REQ_ID(myJdbcTemplate, attValue, entCode,sevId,param);
        } else {
            throw new BaseException("属性联动的参数的attCode为" + attCode + "，不支持！");
        }

    }

    private AttLinkResult linkFUND_PAY_CODE_V_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId, AttLinkParam param) {
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
                String viewId = "99952822476415265";
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
                linkedRecord.valueMap.put("PAY_AMT",JdbcMapUtil.getDouble(echoMap,"paidAmt"));
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

    //招标文件审批 属性联动
    private AttLinkResult linkPM_BID_APPROVAL_REQ_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId, AttLinkParam param) {
        AttLinkResult attLinkResult = new AttLinkResult();
        if ("PM_USE_CHAPTER_REQ".equals(entCode)){ //中选单位及标后用印审批
            String sql1 = "select a.id,a.name,a.NAME_ONE,a.status,(select name from ad_status where id=a.status) as statusName from PM_BID_APPROVAL_REQ a where id = ?";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,attValue);
            if (CollectionUtils.isEmpty(list1)){ //全为空
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = "";
                    linkedAtt.text = "";
                    attLinkResult.attMap.put("APPROVED_AMOUNT",linkedAtt);
                    attLinkResult.attMap.put("STATUS_ONE",linkedAtt);
                    attLinkResult.attMap.put("NAME_ONE",linkedAtt);
                }
            } else {
                // 关联招标文件审批
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"id");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"name");
                    attLinkResult.attMap.put("APPROVED_AMOUNT",linkedAtt);
                }
                // 审批状态
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"status");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"statusName");
                    attLinkResult.attMap.put("STATUS_ONE",linkedAtt);
                }
                // 投标名称
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"NAME_ONE");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"NAME_ONE");
                    attLinkResult.attMap.put("NAME_ONE",linkedAtt);
                }
            }
        }
        return attLinkResult;
    }


    private AttLinkResult linkFUND_IMPLEMENTATION_V_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId, AttLinkParam param) {
        AttLinkResult attLinkResult = new AttLinkResult();
        //专项资金支付选择资金来源后联动
        if ("FUND_SPECIAL".equals(entCode) || "FUND_NEWLY_INCREASED_DETAIL".equals(entCode)){
            String fundSource = attValue;//资金来源
            Map<String, Object> fundSourceMap = myJdbcTemplate.queryForMap("select fi.FUND_SOURCE_TEXT name from fund_implementation fi left join" +
                    " fund_implementation_detail fid on fid.FUND_IMPLEMENTATION_ID = fi.id where fid.id = ?", fundSource);
            String fundSourceName = JdbcMapUtil.getString(fundSourceMap, "name");
            String prjId = JdbcMapUtil.getString(param.valueMap, "PM_PRJ_ID");//项目id
            if (Strings.isNullOrEmpty(prjId)){
                throw new BaseException("请先选择项目");
            }
            //数据联动主页面sql
            String baseSql = "select fi.id fundImpId,fi.FUND_SOURCE_TEXT,fid.PM_PRJ_ID,IFNULL(temp1.appAmt,0) appAmt,IFNULL(temp2.cumReachAmt,0) " +
                    "cumReachAmt,IFNULL(temp3.cumPayAmt,0) cumPayAmt,IFNULL(temp1.appAmt,0)-IFNULL(temp2.cumReachAmt,0) notReachAmt,pa.name " +
              "customerUnitName,pa.id customerUnitId,sv.name manageModeName,sv.id manageModeId,IFNULL(temp4.cumBuildReachAmt,0) cumBuildReachAmt,IFNULL(temp5.cumAcqReachAmt,0) cumAcqReachAmt\n" +
                    "from fund_implementation fi left join fund_implementation_detail fid on fid.FUND_IMPLEMENTATION_ID = fi.id\n" +
                    "left join (select sum(IFNULL(fid.APPROVED_AMOUNT,0)) appAmt,fid.PM_PRJ_ID,fi.FUND_SOURCE_TEXT from fund_implementation_detail" +
                     " fid left join fund_implementation fi on fi.id = fid.FUND_IMPLEMENTATION_ID group by fid.PM_PRJ_ID,fi.FUND_SOURCE_TEXT) temp1" +
                      " on temp1.PM_PRJ_ID = fid.PM_PRJ_ID and temp1.FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT\n" +
                    "left join (select sum(IFNULL(fr.REACH_AMOUNT,0)) cumReachAmt,fr.FUND_SOURCE_TEXT from fund_reach fr group by fr" +
                    ".FUND_SOURCE_TEXT,fr.pm_prj_id\n" +
                    ") temp2 on temp2.FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT\n" +
                    "left join (select sum(IFNULL(fs.PAID_AMT,0)) cumPayAmt,fs.FUND_IMPLEMENTATION_V_ID,fs.PM_PRJ_ID from fund_special fs group by " +
                    "fs.FUND_IMPLEMENTATION_V_ID,fs.PM_PRJ_ID) temp3 on temp3.FUND_IMPLEMENTATION_V_ID = fid.id and temp3.PM_PRJ_ID =" +
                    " fid.PM_PRJ_ID\n" +
                    "left join pm_prj pr on pr.id = fid.PM_PRJ_ID\n" +
                    "left join PM_PARTY pa on pa.id = pr.CUSTOMER_UNIT\n" +
                    "left join gr_set_value sv on sv.id = pr.PRJ_MANAGE_MODE_ID\n" +
                    "left join gr_set se on se.id = sv.GR_SET_ID and se.code = 'management_unit'\n" +
                    "left join (select sum(IFNULL(fr.REACH_AMOUNT,0)) cumBuildReachAmt,fr.FUND_SOURCE_TEXT from fund_reach fr where fr" +
                    ".FUND_REACH_CATEGORY = '99952822476371282' group by fr.FUND_SOURCE_TEXT,fr.pm_prj_id\n" +
                    ") temp4 on temp4.FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT\n" +
                    "left join (select sum(IFNULL(fr.REACH_AMOUNT,0)) cumAcqReachAmt,fr.FUND_SOURCE_TEXT from fund_reach fr where fr" +
                    ".FUND_REACH_CATEGORY = '99952822476371281' group by fr.FUND_SOURCE_TEXT,fr.pm_prj_id\n" +
                    ") temp5 on temp5.FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT where fi.FUND_SOURCE_TEXT = ? and fid.PM_PRJ_ID = ? ";
            //基础统计回显
            List<Map<String, Object>> priStatistic = myJdbcTemplate.queryForList(baseSql, fundSourceName, prjId);

            //查询银行到位余额
//            String bankReachSql = "select reachTemp.bankName,reachTemp.FUND_SOURCE_TEXT soureName,appTemp.appAmt - reachTemp.reachAmt reachBalance " +
//                    "from (select rb.name bankName,re.FUND_SOURCE_TEXT,sum(re.REACH_AMOUNT) reachAmt,re.PM_PRJ_ID\n" +
//                    "from fund_reach re\n" +
//                    "left join fund_implementation fi on re.FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT\n" +
//                    "left join fund_implementation_detail fid on fid.FUND_IMPLEMENTATION_ID = fi.id and fid.PM_PRJ_ID = re.PM_PRJ_ID\n" +
//                    "left join receiving_bank rb on rb.id = re.RECEIVING_BANK_ID\n" +
//                    "group by re.RECEIVING_BANK_ID,re.FUND_SOURCE_TEXT,re.PM_PRJ_ID) reachTemp\n" +
//                    "left join (select fi.FUND_SOURCE_TEXT,fid.PM_PRJ_ID,sum(fid.APPROVED_AMOUNT) appAmt\n" +
//                    "\tfrom fund_implementation fi\n" +
//                    "\tleft join fund_implementation_detail fid on fid.FUND_IMPLEMENTATION_ID = fi.id\n" +
//                    "\tgroup by fi.FUND_SOURCE_TEXT,fid.PM_PRJ_ID\n" +
//                    ") appTemp on appTemp.FUND_SOURCE_TEXT = reachTemp.FUND_SOURCE_TEXT and appTemp.PM_PRJ_ID = reachTemp.PM_PRJ_ID\n" +
//                    "where reachTemp.FUND_SOURCE_TEXT = ? and reachTemp.PM_PRJ_ID = ?";
//            myJdbcTemplate.queryForList(bankReachSql,fundSourceName,prjId);

            if (!CollectionUtils.isEmpty(priStatistic)){
                Map<String, Object> priStatisticMap = priStatistic.get(0);
                //批复金额(APPROVED_AMOUNT),双精度浮点
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = JdbcMapUtil.getString(priStatisticMap,"appAmt");
                    linkedAtt.text = JdbcMapUtil.getString(priStatisticMap,"appAmt");
                    attLinkResult.attMap.put("APPROVED_AMOUNT",linkedAtt);
                }

                //累计到位金额(CUM_REACH_AMT),双精度浮点
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = JdbcMapUtil.getString(priStatisticMap,"cumReachAmt");
                    linkedAtt.text = JdbcMapUtil.getString(priStatisticMap,"cumReachAmt");
                    attLinkResult.attMap.put("CUM_REACH_AMT",linkedAtt);
                }

                //累计支付金额(CUM_PAY_AMT),双精度浮点
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = JdbcMapUtil.getString(priStatisticMap,"cumPayAmt");
                    linkedAtt.text = JdbcMapUtil.getString(priStatisticMap,"cumPayAmt");
                    attLinkResult.attMap.put("CUM_PAY_AMT",linkedAtt);
                }

                //未到位资金(NOT_REACH_AMT),双精度浮点
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = JdbcMapUtil.getString(priStatisticMap,"notReachAmt");
                    linkedAtt.text = JdbcMapUtil.getString(priStatisticMap,"notReachAmt");
                    attLinkResult.attMap.put("NOT_REACH_AMT",linkedAtt);
                }

                //业主单位(CUSTOMER_UNIT),引用（单值）
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(priStatisticMap,"customerUnitId");
                    linkedAtt.text = JdbcMapUtil.getString(priStatisticMap,"customerUnitName");
                    attLinkResult.attMap.put("CUSTOMER_UNIT",linkedAtt);
                }

                //项目管理模式(PRJ_MANAGE_MODE_ID),引用（单值）(代管单位)
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(priStatisticMap,"manageModeId");
                    linkedAtt.text = JdbcMapUtil.getString(priStatisticMap,"manageModeName");
                    attLinkResult.attMap.put("PRJ_MANAGE_MODE_ID",linkedAtt);
                }

                //累计到位建设资金(CUM_BUILD_REACH_AMT),双精度浮点
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = JdbcMapUtil.getString(priStatisticMap,"cumBuildReachAmt");
                    linkedAtt.text = JdbcMapUtil.getString(priStatisticMap,"cumBuildReachAmt");
                    attLinkResult.attMap.put("CUM_BUILD_REACH_AMT",linkedAtt);
                }

                //累计到位征拆资金(CUM_ACQ_REACH_AMT),双精度浮点
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = JdbcMapUtil.getString(priStatisticMap,"cumAcqReachAmt");
                    linkedAtt.text = JdbcMapUtil.getString(priStatisticMap,"cumAcqReachAmt");
                    attLinkResult.attMap.put("CUM_ACQ_REACH_AMT",linkedAtt);
                }

                //剩余到位资金(NOT_REACH_AMT),双精度浮点
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = JdbcMapUtil.getString(priStatisticMap,"notReachAmt");
                    linkedAtt.text = JdbcMapUtil.getString(priStatisticMap,"notReachAmt");
                    attLinkResult.attMap.put("SUR_REACH_AMT",linkedAtt);
                }

                //支付明细码(FUND_PAY_CODE),文本（短）
                //查询相同日期专项资金支付条数
                Map<String, Object> countToday = myJdbcTemplate.queryForMap("select count(*) countToday from fund_special where CRT_DT > DATE(NOW())");
                DecimalFormat df = new DecimalFormat("0000");
                String suffixNum = df.format(JdbcMapUtil.getInt(countToday, "countToday") + 1);
                SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
                String fundPayCode = "zf" + sdf.format(new Date()) + suffixNum;
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = fundPayCode;
                    linkedAtt.text = fundPayCode;
                    attLinkResult.attMap.put("FUND_PAY_CODE",linkedAtt);
                }

                //期数
                //查询相同项目、资金来源专项资金支付条数
                int count = 0;
                Map<String, Object> specialCountMap = myJdbcTemplate.queryForMap("select ifnull(max(NPER),0) count from fund_special where PM_PRJ_ID = ? and FUND_IMPLEMENTATION_V_ID = ?", prjId, fundSource);
                Map<String, Object> payCountMap = myJdbcTemplate.queryForMap("select ifnull(max(NPER),0) count from fund_newly_increased_detail where PM_PRJ_ID = ? and FUND_IMPLEMENTATION_V_ID = ?",prjId,fundSource);
                count = JdbcMapUtil.getInt(specialCountMap,"count") > JdbcMapUtil.getInt(payCountMap,"count") ? JdbcMapUtil.getInt(specialCountMap,"count") : JdbcMapUtil.getInt(payCountMap,"count");

                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.INTEGER;
                    linkedAtt.value = count + 1;
                    linkedAtt.text = String.valueOf(count + 1);
                    attLinkResult.attMap.put("NPER",linkedAtt);
                }

                if ("FUND_NEWLY_INCREASED_DETAIL".equals(entCode)){//支付资金信息表
                    String fundCategorySql = "select fi.FUND_CATEGORY_FIRST categoryFirstId,ft1.name categoryFirstName,fi.FUND_CATEGORY_SECOND " +
                            "categorySecondId,ft2.name categorySecondName \n" +
                            "from fund_implementation fi \n" +
                            "left join fund_implementation_detail fid on fid.FUND_IMPLEMENTATION_ID = fi.id\n" +
                            "left join fund_type ft1 on ft1.id = fi.FUND_CATEGORY_FIRST\n" +
                            "left join fund_type ft2 on ft2.id = fi.FUND_CATEGORY_SECOND\n" +
                            "where fi.FUND_SOURCE_TEXT = ? and fid.PM_PRJ_ID = ?";
                    List<Map<String, Object>> categoryList = myJdbcTemplate.queryForList(fundCategorySql, fundSourceName, prjId);
                    if (!CollectionUtils.isEmpty(categoryList)){
                        //资金大类一级
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = JdbcMapUtil.getString(categoryList.get(0),"categoryFirstId");
                            linkedAtt.text = JdbcMapUtil.getString(categoryList.get(0),"categoryFirstName");
                            attLinkResult.attMap.put("FUND_CATEGORY_FIRST",linkedAtt);
                        }

                        //资金大类二级
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = JdbcMapUtil.getString(categoryList.get(0),"categorySecondId");
                            linkedAtt.text = JdbcMapUtil.getString(categoryList.get(0),"categorySecondName");
                            attLinkResult.attMap.put("FUND_CATEGORY_SECOND",linkedAtt);
                        }

                    }
                    //支付信息（子表）
//                    String viewId = "99952822476415265";
//                    ArrayList<LinkedRecord> linkedRecordList = new ArrayList<>();
//                    LinkedRecord linkedRecord = new LinkedRecord();
//                    //资金类别
//                    linkedRecord.valueMap.put("NPER",count + 1);
//                    linkedRecord.textMap.put("NPER",String.valueOf(count + 1));
//                    linkedRecordList.add(linkedRecord);
//                    attLinkResult.childData.put(viewId,linkedRecordList);
//                    attLinkResult.childCreatable.put(viewId, true);
//                    attLinkResult.childClear.put(viewId, true);
                }

            }
        }
        return attLinkResult;
    }

    //招采项目备案及归档 属性联动
    private AttLinkResult linkPM_BID_KEEP_FILE_REQ_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId, AttLinkParam param) {
        AttLinkResult attLinkResult = new AttLinkResult();
        if ("PO_ORDER_REQ".equals(entCode)){ //合同签订
            //查询招采项目备案及归档信息
            String sql1 = "select PM_PRJ_ID,PAY_AMT_TWO,BUY_TYPE_ID,WIN_BID_UNIT_ONE,AMT_ONE,BUY_MATTER_ID from PM_BID_KEEP_FILE_REQ where id = ?";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,attValue);
            if (!CollectionUtils.isEmpty(list1)){
                //招标类别
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    String id = JdbcMapUtil.getString(list1.get(0),"BUY_TYPE_ID");
                    String name = "";
                    String sql2 = "select name from gr_set_value where id = ?";
                    List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,id);
                    if (!CollectionUtils.isEmpty(list2)){
                        name = JdbcMapUtil.getString(list2.get(0),"name");
                    }
                    linkedAtt.value = id;
                    linkedAtt.text = name;
                    attLinkResult.attMap.put("BUY_TYPE_ID",linkedAtt);
                }
                //招标控制价
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"PAY_AMT_TWO");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"PAY_AMT_TWO");
                    attLinkResult.attMap.put("BID_CTL_PRICE_LAUNCH",linkedAtt);
                }
                //采购方式
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    String id = JdbcMapUtil.getString(list1.get(0),"BUY_MATTER_ID");
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
                //中标单位
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"WIN_BID_UNIT_ONE");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"WIN_BID_UNIT_ONE");
                    attLinkResult.attMap.put("WIN_BID_UNIT_ONE",linkedAtt);
                }
                //中标价
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"AMT_ONE");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"AMT_ONE");
                    attLinkResult.attMap.put("WINNING_BIDS_AMOUNT",linkedAtt);
                }
            }
        }
        return attLinkResult;
    }

    //付款类型属性联动
    private AttLinkResult linkPAY_TYPE_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId,AttLinkParam param) {
        AttLinkResult attLinkResult = new AttLinkResult();
        //99952822476390858=预付款,99952822476390859=进度款
        if ("PO_ORDER_PAYMENT_REQ".equals(entCode)){
            if ("99952822476390858".equals(attValue)){
                //进度款隐藏
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.changeToShown = false;
                    attLinkResult.attMap.put("REMARK_ONE",linkedAtt);
                }
                //预付款
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.changeToMandatory = true;
                    linkedAtt.changeToShown = true;
                    attLinkResult.attMap.put("BUDGET_AMT",linkedAtt);
                }
            } else {
                //预付款隐藏
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.changeToShown = false;
                    attLinkResult.attMap.put("BUDGET_AMT",linkedAtt);
                }
                //查询当前应该是第几期预付款
                String projectId = JdbcMapUtil.getString(param.valueMap,"AMOUT_PM_PRJ_ID");
                String contractId = JdbcMapUtil.getString(param.valueMap,"CONTRACT_ID");
                String sql1 = "select count(*) as num from PO_ORDER_PAYMENT_REQ where AMOUT_PM_PRJ_ID=? and CONTRACT_ID=? and id !=?";
                List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,projectId,contractId,sevId);
                String count = "";
                if (CollectionUtils.isEmpty(list1)){
                    count = "一";
                } else {
                    count = ConvertUtils.convert(Integer.parseInt(JdbcMapUtil.getString(list1.get(0),"num")+1));
                }
                count = "第"+count+"笔款";
                //进度款
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.changeToShown = true;
                    linkedAtt.changeToEditable = false;
                    linkedAtt.value = count;
                    linkedAtt.text = count;
                    attLinkResult.attMap.put("REMARK_ONE",linkedAtt);
                }
            }
        }
        return attLinkResult;
    }

    private AttLinkResult linkPM_BUY_DEMAND_REQ_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId) {
        AttLinkResult attLinkResult = new AttLinkResult();
        if ("PM_USE_CHAPTER_REQ".equals(entCode)){ //中选单位及标后用印审批
            List<Map<String, Object>> preBidCheck = myJdbcTemplate.queryForList("select a.name,a.id,a.status,s.name statusName " +
                    "from pm_file_chapter_req a " +
                    "left join ad_status s on s.id = a.status " +
                    "where a.PM_BUY_DEMAND_REQ_ID = ? " +
                    "order by a.CRT_DT desc " +
                    "limit 1", attValue);
            List<Map<String, Object>> prjList = myJdbcTemplate.queryForList("select d.status,(select name from ad_status where id = d.status) as statusName," +
                    "p.name,d.PM_PRJ_ID,d.BUY_TYPE_ID,d.PAY_AMT_TWO,d.BUY_MATTER_ID " +
                    "from PM_BUY_DEMAND_REQ d left join pm_prj p on p.id = d.PM_PRJ_ID " +
                    "where d.id = ?", attValue);
            if (!CollectionUtils.isEmpty(prjList)){
                //项目
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(prjList.get(0),"PM_PRJ_ID");
                    linkedAtt.text = JdbcMapUtil.getString(prjList.get(0),"name");
                    attLinkResult.attMap.put("PM_PRJ_ID",linkedAtt);
                }
                //采购方式
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(prjList.get(0),"BUY_TYPE_ID");
                    linkedAtt.text = JdbcMapUtil.getString(prjList.get(0),"BUY_TYPE_ID");
                    attLinkResult.attMap.put("BUY_TYPE_ID",linkedAtt);
                }
                //付款金额
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = JdbcMapUtil.getString(prjList.get(0),"PAY_AMT_TWO");
                    linkedAtt.text = JdbcMapUtil.getString(prjList.get(0),"PAY_AMT_TWO");
                    attLinkResult.attMap.put("PAY_AMT_TWO",linkedAtt);
                }
                //采购事项
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(prjList.get(0),"BUY_MATTER_ID");
                    linkedAtt.text = JdbcMapUtil.getString(prjList.get(0),"BUY_MATTER_ID");
                    attLinkResult.attMap.put("BUY_MATTER_ID",linkedAtt);
                }
                //审批状态
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(prjList.get(0),"status");
                    linkedAtt.text = JdbcMapUtil.getString(prjList.get(0),"statusName");
                    attLinkResult.attMap.put("STATUS_THREE",linkedAtt);
                }
            }
            if (!CollectionUtils.isEmpty(preBidCheck)){
                Map<String, Object> preBidCheckMap = preBidCheck.get(0);
                //关联标前资料用印审批 PM_FILE_CHAPTER_REQ_ID
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(preBidCheckMap,"id");
                    linkedAtt.text = JdbcMapUtil.getString(preBidCheckMap, "name");
                    attLinkResult.attMap.put("PM_FILE_CHAPTER_REQ_ID",linkedAtt);
                }

                //关联标前资料用印审批状态 STATUS_TWO
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(preBidCheckMap,"status");
                    linkedAtt.text = JdbcMapUtil.getString(preBidCheckMap, "statusName");
                    attLinkResult.attMap.put("STATUS_TWO",linkedAtt);
                }
            }
        } else if ("PM_BID_APPROVAL_REQ".equals(entCode) || "PM_FILE_CHAPTER_REQ".equals(entCode)){ //招标文件审批 标前资料用印审批
            String sql1 = "select BUY_TYPE_ID from PM_BUY_DEMAND_REQ where id = ?";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,attValue);
            String id = "";
            String name = "";
            if (!CollectionUtils.isEmpty(list1)){
                id = JdbcMapUtil.getString(list1.get(0),"BUY_TYPE_ID");
                String sql2 = "select name from gr_set_value where id = ?";
                List<Map<String, Object>> nameMap = myJdbcTemplate.queryForList(sql2, id);
                if (!CollectionUtils.isEmpty(nameMap)){
                    name = JdbcMapUtil.getString(nameMap.get(0), "name");
                }
            }
            // 招采方式
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.DOUBLE;
                linkedAtt.value = id;
                linkedAtt.text = name;
                attLinkResult.attMap.put("BUY_TYPE_ID", linkedAtt);
            }
            return attLinkResult;
        }


        return attLinkResult;
    }

    private AttLinkResult linkTYPE_ONE_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId) {
        AttLinkResult attLinkResult = new AttLinkResult();
        if ("PM_USE_CHAPTER_REQ".equals(entCode)){//中选单位及标后用印审批
            //99952822476386421 关联流程, 99952822476386422 上传依据
            if ("99952822476386421".equals(attValue)){
                //PM_BUY_DEMAND_REQ_ID 关联采购需求审批表
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = null;
                    linkedAtt.text = null;
                    linkedAtt.changeToMandatory = true;
                    linkedAtt.changeToEditable = true;
                    attLinkResult.attMap.put("PM_BUY_DEMAND_REQ_ID", linkedAtt);
                }

                //FILE_ID_ONE 其他依据
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = null;
                    linkedAtt.changeToMandatory = false;
                    linkedAtt.changeToEditable = false;
                    attLinkResult.attMap.put("FILE_ID_ONE", linkedAtt);
                }
            }
            if ("99952822476386422".equals(attValue)){
                //PM_BUY_DEMAND_REQ_ID 关联采购需求审批表
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = null;
                    linkedAtt.text = null;
                    linkedAtt.changeToMandatory = false;
                    linkedAtt.changeToEditable = false;
                    attLinkResult.attMap.put("PM_BUY_DEMAND_REQ_ID", linkedAtt);
                }

                //FILE_ID_ONE 其他依据
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = null;
                    linkedAtt.changeToMandatory = true;
                    linkedAtt.changeToEditable = true;
                    attLinkResult.attMap.put("FILE_ID_ONE", linkedAtt);
                }
            }
        }
        return attLinkResult;
    }

    //中标单位及标后用章属性联动
    private AttLinkResult linkPM_USE_CHAPTER_REQ_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId, AttLinkParam param) {
        AttLinkResult attLinkResult = new AttLinkResult();
        if ("PM_BID_KEEP_FILE_REQ".equals(entCode)){ //招采项目备案及归档
            String sql = "select CUSTOMER_UNIT_ONE,BUY_MATTER_ID,PM_PRJ_ID,WIN_BID_UNIT_ONE,AMT_ONE,CONTACTS_ONE,CONTACT_MOBILE_ONE,PAY_AMT_TWO,BUY_TYPE_ID from PM_USE_CHAPTER_REQ where id = ?";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql,attValue);
            if (!CollectionUtils.isEmpty(list1)){
                //业主单位
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"CUSTOMER_UNIT_ONE");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"CUSTOMER_UNIT_ONE");
                    attLinkResult.attMap.put("CUSTOMER_UNIT_ONE", linkedAtt);
                }
                //中标单位名称
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"WIN_BID_UNIT_ONE");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"WIN_BID_UNIT_ONE");
                    attLinkResult.attMap.put("WIN_BID_UNIT_ONE", linkedAtt);
                }
                //投标报价(元)
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"AMT_ONE");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"AMT_ONE");
                    attLinkResult.attMap.put("AMT_ONE", linkedAtt);
                }
                //联系人姓名
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"CONTACTS_ONE");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"CONTACTS_ONE");
                    attLinkResult.attMap.put("CONTACTS_ONE", linkedAtt);
                }
                //联系人电话
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"CONTACT_MOBILE_ONE");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"CONTACT_MOBILE_ONE");
                    attLinkResult.attMap.put("CONTACT_MOBILE_ONE", linkedAtt);
                }
                //招标控制价
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"PAY_AMT_TWO");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"PAY_AMT_TWO");
                    attLinkResult.attMap.put("PAY_AMT_TWO", linkedAtt);
                }
                //采购方式
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"BUY_TYPE_ID");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"BUY_TYPE_ID");
                    attLinkResult.attMap.put("BUY_TYPE_ID", linkedAtt);
                }
                //采购事项名称
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    String id = JdbcMapUtil.getString(list1.get(0),"BUY_MATTER_ID");;
                    String name = "";
                    String sql2 = "select name from gr_set_value where id = ?";
                    List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,id);
                    if (!CollectionUtils.isEmpty(list2)){
                        name = JdbcMapUtil.getString(list2.get(0),"name");
                    }
                    linkedAtt.value = id;
                    linkedAtt.text = name;
                    attLinkResult.attMap.put("BUY_MATTER_ID", linkedAtt);
                }
            } else {
                //中标单位名称
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = "";
                    linkedAtt.text = "";
                    attLinkResult.attMap.put("WIN_BID_UNIT_ONE", linkedAtt);
                }
                //投标报价(元)
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = "";
                    linkedAtt.text = "";
                    attLinkResult.attMap.put("AMT_ONE", linkedAtt);
                }
                //联系人姓名
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = "";
                    linkedAtt.text = "";
                    attLinkResult.attMap.put("CONTACTS_ONE", linkedAtt);
                }
                //联系人电话
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = "";
                    linkedAtt.text = "";
                    attLinkResult.attMap.put("CONTACT_MOBILE_ONE", linkedAtt);
                }
                //招标控制价
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = "";
                    linkedAtt.text = "";
                    attLinkResult.attMap.put("PAY_AMT_TWO", linkedAtt);
                }
                //采购方式
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = "";
                    linkedAtt.text = "";
                    attLinkResult.attMap.put("BUY_TYPE_ID", linkedAtt);
                }
                //采购事项名称
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = "";
                    linkedAtt.text = "";
                    attLinkResult.attMap.put("BUY_MATTER_ID", linkedAtt);
                }
            }
        }
        return attLinkResult;
    }

    //采购启动依据属性联动
    private AttLinkResult linkBUY_START_BASIS_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId,AttLinkParam param) {
        AttLinkResult attLinkResult = new AttLinkResult();
        if ("PM_BUY_DEMAND_REQ".equals(entCode)){ //采购需求审批
            //99952822476385260=会议纪要，99952822476385261=其他
            Boolean changeToEditable = false; //是否可改
            Boolean changeToMandatory = false; //是否必填
            String value = "";
            String text = "";
            String projectId = JdbcMapUtil.getString(param.valueMap, "PM_PRJ_ID");
            if (Strings.isNullOrEmpty(projectId)){
                throw new BaseException("请先选择项目！");
            }
            if ("99952822476385260".equals(attValue) || "99952822476385261".equals(attValue)){
                changeToEditable = true;
                changeToMandatory = true;
                value = null;
                text = null;
                //采购启动依据文件
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = null;
                    linkedAtt.text = null;
                    linkedAtt.changeToMandatory = true;
                    linkedAtt.changeToEditable = true;
                    linkedAtt.fileInfoList = null;
                    attLinkResult.attMap.put("FILE_ID_THREE", linkedAtt);
                }
            } else {
                // 99952822476385257 = 立项，99952822476385258=可研，99952822476385259=初概
                String sql = "";
                String fileValue = "";
                if ("99952822476385257".equals(attValue)){
                    sql = "select CONSTRUCTION_PROJECT_CODE as REPLY_NO_WR,REPLY_FILE as file from pm_prj_req WHERE name = (select name from pm_prj WHERE id = ?) and `STATUS` = 'ap' order by CRT_DT desc limit 1";
                } else if ("99952822476385258".equals(attValue)){
                    sql = "select REPLY_NO_WR,REPLY_FILE as file from PM_PRJ_INVEST1 WHERE pm_prj_id = ? and status = 'ap' order by CRT_DT desc limit 1";
                } else if ("99952822476385259".equals(attValue)){
                    sql = "select REPLY_NO_WR,REPLY_FILE as file from PM_PRJ_INVEST2 WHERE pm_prj_id = ? and status = 'ap' order by CRT_DT desc limit 1";
                }
                List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql,projectId);
                if (!CollectionUtils.isEmpty(list2)){
                    value = JdbcMapUtil.getString(list2.get(0),"REPLY_NO_WR");
                    text = value;
                    fileValue = JdbcMapUtil.getString(list2.get(0),"file");
                } else {
                    fileValue = null;
                }
                //采购启动依据文件
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.FILE_GROUP;
                    linkedAtt.value = fileValue;
                    getFileInfoList(linkedAtt);
                    linkedAtt.changeToMandatory = false;
                    linkedAtt.changeToEditable = false;
                    attLinkResult.attMap.put("FILE_ID_THREE", linkedAtt);
                }
            }
            //启动依据文号
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = value;
                linkedAtt.text = text;
                linkedAtt.changeToMandatory = changeToMandatory;
                linkedAtt.changeToEditable = changeToEditable;
                attLinkResult.attMap.put("REPLY_NO_WR", linkedAtt);
            }
        }
        return attLinkResult;
    }

    //招采方式 属性联动
    private AttLinkResult linkBUY_TYPE_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId) {
        AttLinkResult attLinkResult = new AttLinkResult();
        // 99952822476385221=公开招标,99952822476385222=竞争性磋商,99952822476385223=竞争性谈判
        if ("PM_BID_APPROVAL_REQ".equals(entCode)){ //招标文件审批
            Boolean changeToMandatory = false; //必填
            if ("99952822476385221".equals(attValue) || "99952822476385222".equals(attValue) || "99952822476385223".equals(attValue)){
                changeToMandatory = true;
            }
            //BID_AGENCY
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToMandatory = changeToMandatory;
                attLinkResult.attMap.put("BID_AGENCY", linkedAtt);
            }
        }
        return attLinkResult;
    }

    //退还原因属性联动
    private AttLinkResult linkREASON(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId) {
        AttLinkResult attLinkResult = new AttLinkResult();
        // 99902212142025881 = 到期申请退还，之后续保,99902212142025882=达到退保条件，申请退保,99902212142025883=其他
        Boolean changeToMandatory = true;
        Boolean changeToEditable = true;
        if ("99902212142025881".equals(attValue)){
            changeToMandatory = false;
            changeToEditable = false;
        }
        //原因说明
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            linkedAtt.changeToMandatory = changeToMandatory;
            linkedAtt.changeToEditable = changeToEditable;
            attLinkResult.attMap.put("REASON_EXPLAIN", linkedAtt);
        }
        return attLinkResult;
    }

    //是否涉及保函 属性联动
    private AttLinkResult linkIS_REFER_GUARANTEE_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId) {
        AttLinkResult attLinkResult = new AttLinkResult();
        // 99902212142031851 = 是 ，99902212142031855 = 否
        Boolean changeToShown = true;
        Boolean changeToMandatory = true;
        Boolean changeToEditable = true;

        List<String> baohanList = getBaoHanList();
        if (baohanList.contains(entCode)){
            if ("99902212142031855".equals(attValue)){ //隐藏保函类型
                changeToShown = false;
                changeToMandatory = false;
                changeToEditable = false;
            } else { //显示保函类型
                changeToShown = true;
                changeToMandatory = true;
                changeToEditable = true;
            }
            //保函类型
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToShown = changeToShown;
                linkedAtt.changeToMandatory = changeToMandatory;
                linkedAtt.changeToEditable = changeToEditable;
                attLinkResult.attMap.put("GUARANTEE_LETTER_TYPE_IDS", linkedAtt);
            }
        }
        return attLinkResult;
    }

    // 有 无  属性联动
    private AttLinkResult linkYES_NO_TWO(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId) {
        AttLinkResult attLinkResult = new AttLinkResult();
        if ("PM_FARMING_PROCEDURES".equals(entCode)){ // 农转用手续办理
            // 99902212142514832 = 有， 99902212142514833 = 没有
            if ("99902212142514833".equals(attValue)){
                // 预估有指标时间
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
                    linkedAtt.value = null;
                    linkedAtt.text = null;
                    linkedAtt.changeToShown = true;
                    linkedAtt.changeToMandatory = true;
                    linkedAtt.changeToEditable = true;
                    attLinkResult.attMap.put("DATE_ONE", linkedAtt);
                }
            } else {
                // 预估有指标时间
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
                    linkedAtt.value = null;
                    linkedAtt.text = null;
                    linkedAtt.changeToShown = true;
                    linkedAtt.changeToMandatory = false;
                    linkedAtt.changeToEditable = true;
                    attLinkResult.attMap.put("DATE_ONE", linkedAtt);
                }
            }
        }
        return attLinkResult;
    }

    // 是否属性联动
    private AttLinkResult linkYES_NO_ONE(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId) {
        AttLinkResult attLinkResult = new AttLinkResult();
        Boolean changeToShown = true;
        Boolean changeToMandatory = true;
        Boolean changeToEditable = true;
        if ("PM_FARMING_PROCEDURES".equals(entCode)){ //农转用手续办理
            // 99799190825080669 = 是， 99799190825080670 = 否
            if (!"99799190825080670".equals(attValue)) {  // 是否需要办理选是，文件不做判断
                changeToShown = true;
                changeToMandatory = false;
                changeToEditable = false;
            }
            // 附件
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = "";
                linkedAtt.text = "";
                linkedAtt.changeToShown = changeToShown;
                linkedAtt.changeToMandatory = changeToMandatory;
                linkedAtt.changeToEditable = changeToEditable;
                attLinkResult.attMap.put("ATT_FILE_GROUP_ID", linkedAtt);
            }
        } else if ("PO_ORDER_REQ".equals(entCode) || "PO_ORDER_SUPPLEMENT_REQ".equals(entCode)){ //采购合同签订申请 采购合同补充协议申请
            // 99799190825080669 = 是，99799190825080670=否
            Boolean replyChangeToMandatory = true; //审核意见采纳说明必填
            Boolean fileChangeToMandatory = true; //审核意见附件必填
            Boolean heTongFileChangeToMandatory = true; //合同送审稿必填
            if ("99799190825080669".equals(attValue)){
                replyChangeToMandatory = false;
                fileChangeToMandatory = false;
                heTongFileChangeToMandatory = false;
            }
            // 审核意见采纳说明
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = "";
                linkedAtt.text = "";
                linkedAtt.changeToMandatory = replyChangeToMandatory;
                attLinkResult.attMap.put("REMARK_ONE", linkedAtt);
            }
            // 审核意见采纳说明
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = "";
                linkedAtt.text = "";
                linkedAtt.changeToMandatory = fileChangeToMandatory;
                attLinkResult.attMap.put("FILE_ID_FOUR", linkedAtt);
            }
            // 审核意见采纳说明
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = "";
                linkedAtt.text = "";
                linkedAtt.changeToMandatory = heTongFileChangeToMandatory;
                attLinkResult.attMap.put("FILE_ID_ONE", linkedAtt);
            }

        }
        return attLinkResult;
    }

    // 是 否  属性联动
    private AttLinkResult linkYES_NO_THREE(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId) {
        AttLinkResult attLinkResult = new AttLinkResult();
        if ("PM_SEND_APPROVAL_REQ".equals(entCode)){ //发文呈批
            // 99799190825080669 = 是， 99799190825080670 = 否
            if ("99799190825080669".equals(attValue)){  //隐藏 是否呈董事长审批
                // 是否呈董事长审批
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = "";
                    linkedAtt.text = "";
                    linkedAtt.changeToShown = false;
                    linkedAtt.changeToMandatory = false;
                    linkedAtt.changeToEditable = false;
                    attLinkResult.attMap.put("YES_NO_FOUR", linkedAtt);
                }
            } else {
                // 是否呈董事长审批
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = "";
                    linkedAtt.text = "";
                    linkedAtt.changeToShown = true;
                    linkedAtt.changeToMandatory = true;
                    linkedAtt.changeToEditable = true;
                    attLinkResult.attMap.put("YES_NO_FOUR", linkedAtt);
                }
            }
        } else if ("PO_ORDER_REQ".equals(entCode) || "PO_ORDER_SUPPLEMENT_REQ".equals(entCode)){ //采购合同签订申请 采购合同补充协议申请
            // 99799190825080669 = 是， 99799190825080670 = 否
            Boolean faWuChangeToShown =  true; //法务部门修订稿显示
            Boolean caiWuChangeToShown =  true; //财务部门修订稿显示
            Boolean isCaiNaChangeToShown =  true; //审核意见是否完全采纳显示
            Boolean caiNaChangeToShown =  true; //采纳说明显示
            Boolean caiNaFileChangeToShown =  true; //采纳附件显示
            Boolean heTongChangeToShown =  true; //合同送审稿显示
            Boolean falvChangeToShown = true; // 法律审核附件显示
            if ("99799190825080669".equals(attValue)){
                faWuChangeToShown =  false;
                caiWuChangeToShown =  false;
                isCaiNaChangeToShown =  false;
                caiNaChangeToShown =  false;
                caiNaFileChangeToShown =  false;
                heTongChangeToShown =  false;
                falvChangeToShown = false;
            }
            // 财务部门修订稿
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = "";
                linkedAtt.text = "";
                linkedAtt.changeToShown = falvChangeToShown;
                attLinkResult.attMap.put("FILE_ID_SIX", linkedAtt);
            }
            // 财务部门修订稿
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = "";
                linkedAtt.text = "";
                linkedAtt.changeToShown = faWuChangeToShown;
                attLinkResult.attMap.put("FILE_ID_TWO", linkedAtt);
            }
            // 法务部门修订稿
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = "";
                linkedAtt.text = "";
                linkedAtt.changeToShown = caiWuChangeToShown;
                attLinkResult.attMap.put("FILE_ID_THREE", linkedAtt);
            }
            // 审核意见是否完全采纳
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = "";
                linkedAtt.text = "";
                linkedAtt.changeToShown = isCaiNaChangeToShown;
                attLinkResult.attMap.put("YES_NO_ONE", linkedAtt);
            }
            // 审核意见采纳说明
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = "";
                linkedAtt.text = "";
                linkedAtt.changeToShown = caiNaChangeToShown;
                attLinkResult.attMap.put("REMARK_ONE", linkedAtt);
            }
            // 采纳意见附件
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = "";
                linkedAtt.text = "";
                linkedAtt.changeToShown = caiNaFileChangeToShown;
                attLinkResult.attMap.put("FILE_ID_FOUR", linkedAtt);
            }
            // 合同送审稿
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = "";
                linkedAtt.text = "";
                linkedAtt.changeToShown = heTongChangeToShown;
                attLinkResult.attMap.put("FILE_ID_ONE", linkedAtt);
            }
        }
        return attLinkResult;
    }

    //付款依据属性联动
    private AttLinkResult linkPAY_BASIS_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode,String sevId) {
        AttLinkResult attLinkResult = new AttLinkResult();
        if ("PO_ORDER_PAYMENT_REQ".equals(entCode)) { //付款申请
            // 明细信息
            if ("99902212142033284".equals(sevId)) {
                // 付款依据 99902212142031993 = 其他付款 99902212142031992=合同付款
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = "";
                    linkedAtt.text = "";
                    attLinkResult.attMap.put("CONTRACT_ID", linkedAtt);
                }
                //清除明细表信息
                attLinkResult.childClear.put("99902212142514118", true);
                attLinkResult.childCreatable.put("99902212142514118", true);
            }
        }
        return attLinkResult;
    }

    //资金需求计划联动属性
    private AttLinkResult linkForRELATION_AMOUT_PLAN_REQ_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode) {
        AttLinkResult attLinkResult = new AttLinkResult();
        if ("PO_ORDER_PAYMENT_REQ".equals(entCode)){ //付款申请
            //查询项目信息
            String sql1 = "SELECT a.AMOUT_PM_PRJ_ID,b.name FROM PM_FUND_REQUIRE_PLAN_REQ a LEFT JOIN pm_prj b on a.AMOUT_PM_PRJ_ID = b.id WHERE a.id = ?";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,attValue);
            if (!CollectionUtils.isEmpty(list1)){
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"AMOUT_PM_PRJ_ID");
                    linkedAtt.text = JdbcMapUtil.getString(list1.get(0),"name");
                    attLinkResult.attMap.put("AMOUT_PM_PRJ_ID", linkedAtt);
                }
            }
            //查询付款信息开户行等信息
            String sql2 = "SELECT COLLECTION_DEPT_TWO,BANK_OF_DEPOSIT,ACCOUNT_NO,RECEIPT,SPECIAL_BANK_OF_DEPOSIT,SPECIAL_ACCOUNT_NO FROM PO_ORDER_PAYMENT_REQ WHERE RELATION_AMOUT_PLAN_REQ_ID = ? AND STATUS = 'AP' ORDER BY CRT_DT DESC limit 1";
            List<Map<String, Object>> list2 = myJdbcTemplate.queryForList(sql2, attValue);
            if (!CollectionUtils.isEmpty(list2)) {
                Map row2 = list2.get(0);
                // 收款单位
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
                    linkedAtt.value = JdbcMapUtil.getString(row2, "COLLECTION_DEPT_TWO");
                    linkedAtt.text = JdbcMapUtil.getString(row2, "COLLECTION_DEPT_TWO");
                    attLinkResult.attMap.put("COLLECTION_DEPT_TWO", linkedAtt);
                }
                // 开户行
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
                    linkedAtt.value = JdbcMapUtil.getString(row2, "BANK_OF_DEPOSIT");
                    linkedAtt.text = JdbcMapUtil.getString(row2, "BANK_OF_DEPOSIT");
                    attLinkResult.attMap.put("BANK_OF_DEPOSIT", linkedAtt);
                }
                // 账号
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
                    linkedAtt.value = JdbcMapUtil.getString(row2, "ACCOUNT_NO");
                    linkedAtt.text = JdbcMapUtil.getString(row2, "ACCOUNT_NO");
                    attLinkResult.attMap.put("ACCOUNT_NO", linkedAtt);
                }
                // 农民工工资专用账号收款单位
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
                    linkedAtt.value = JdbcMapUtil.getString(row2, "RECEIPT");
                    linkedAtt.text = JdbcMapUtil.getString(row2, "RECEIPT");
                    attLinkResult.attMap.put("RECEIPT", linkedAtt);
                }
                // 专户开户行
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
                    linkedAtt.value = JdbcMapUtil.getString(row2, "SPECIAL_BANK_OF_DEPOSIT");
                    linkedAtt.text = JdbcMapUtil.getString(row2, "SPECIAL_BANK_OF_DEPOSIT");
                    attLinkResult.attMap.put("SPECIAL_BANK_OF_DEPOSIT", linkedAtt);
                }
                // 专户账号
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
                    linkedAtt.value = JdbcMapUtil.getString(row2, "SPECIAL_ACCOUNT_NO");
                    linkedAtt.text = JdbcMapUtil.getString(row2, "SPECIAL_ACCOUNT_NO");
                    attLinkResult.attMap.put("SPECIAL_ACCOUNT_NO", linkedAtt);
                }
            }

        }
        return attLinkResult;
    }

    private AttLinkResult linkForMany(MyJdbcTemplate myJdbcTemplate, String attCode, String attValue) {
        AttLinkResult attLinkResult = new AttLinkResult();

        // 1.PMS_RELEASE_WAY_ID 招标类别下拉框
        // 2.GUARANTEE_LETTER_TYPE_ID 保函类别下拉框
        // 3.PMS_RELEASE_WAY_ID 项目类别下拉框

        List<Map<String, Object>> list = myJdbcTemplate.queryForList("SELECT t.id, t.name FROM gr_set_value t WHERE t.id = ?", attValue);

        if (SharedUtil.isEmptyList(list)) {
            throw new BaseException("未设置相应项目类型的联动！");
        } else if (list.size() > 1) {
            throw new BaseException("重复设置相应项目类型的联动！");
        }

        Map row = list.get(0);

        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "id");
            linkedAtt.text = JdbcMapUtil.getString(row, "name");

            attLinkResult.attMap.put(attCode, linkedAtt);
        }

        return attLinkResult;
    }

    private AttLinkResult linkForAMOUT_PM_PRJ_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode) {
        AttLinkResult attLinkResult = new AttLinkResult();

        // 资金需求项目名称(AMOUT_PM_PRJ_ID),引用（单值）
        // 项目基础信息
        List<Map<String, Object>> list = myJdbcTemplate
                .queryForList("select t.PRJ_CODE as prj_code,t.code,c.id customer_id,c.name customer_name,m.id m_id,m.name m_name," +
                        "l.id l_id,l.name l_name,t.FLOOR_AREA,pt.id pt_id,pt.name pt_name,st.id st_id,st.name st_name," +
                        "su.id su_id,su.name su_name,t.CON_SCALE_QTY,t.CON_SCALE_QTY2,t.PRJ_SITUATION, t.BUILD_YEARS," +
                        "t.PRJ_REPLY_NO, t.PRJ_REPLY_DATE, t.PRJ_REPLY_FILE, t.INVESTMENT_SOURCE_ID,t.PRJ_CODE, " +
                        "(SELECT PRJ_TOTAL_INVEST from PM_PRJ_INVEST1 WHERE PM_PRJ_ID = t.id order by CRT_DT desc limit 1) as 'FS', " +
                        "(SELECT PRJ_TOTAL_INVEST from PM_PRJ_INVEST2 WHERE PM_PRJ_ID = t.id order by CRT_DT desc limit 1) as 'PD', " +
                        "(SELECT PRJ_TOTAL_INVEST from PM_PRJ_INVEST3 WHERE PM_PRJ_ID = t.id order by CRT_DT desc limit 1) as 'budget', " +
                        "t.QTY_ONE,t.QTY_TWO,t.QTY_THREE " +
                        "from pm_prj t join PM_PARTY c on t.id=? and t.CUSTOMER_UNIT=c.id " +
                        "join gr_set_value m on t.PRJ_MANAGE_MODE_ID = m.ID " +
                        "join gr_set_value l on t.BASE_LOCATION_ID=l.id " +
                        "join gr_set_value pt on t.PROJECT_TYPE_ID=pt.id " +
                        "join gr_set_value st on t.CON_SCALE_TYPE_ID=st.id " +
                        "join gr_set_value su on t.CON_SCALE_UOM_ID=su.id", attValue);
        if (CollectionUtils.isEmpty(list)) {
//            throw new BaseException("项目的相关属性不完整！");
            return attLinkResult;
        }
        Map row = list.get(0);

        // 回显项目信息
        // 项目编号
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "prj_code");
            linkedAtt.text = JdbcMapUtil.getString(row, "prj_code");
            attLinkResult.attMap.put("PRJ_CODE", linkedAtt);
        }

        // 项目批复文号
        Map resultRow = getReplyNo(attValue);
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(resultRow, "REPLY_NO_WR");
            linkedAtt.text = JdbcMapUtil.getString(resultRow, "REPLY_NO_WR");
            attLinkResult.attMap.put("PRJ_REPLY_NO", linkedAtt);
            attLinkResult.attMap.put("REPLY_NO", linkedAtt);
        }
        // 项目介绍
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "PRJ_SITUATION");
            linkedAtt.text = JdbcMapUtil.getString(row, "PRJ_SITUATION");
            attLinkResult.attMap.put("PRJ_SITUATION", linkedAtt);
        }
        // 资金来源
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "INVESTMENT_SOURCE_ID");
            linkedAtt.text = JdbcMapUtil.getString(row, "INVESTMENT_SOURCE_ID");
            attLinkResult.attMap.put("PM_FUND_SOURCE_ID", linkedAtt);
            attLinkResult.attMap.put("INVESTMENT_SOURCE_ID", linkedAtt);
        }
        // 业主单位
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "customer_id");
            linkedAtt.text = JdbcMapUtil.getString(row, "customer_name");
            attLinkResult.attMap.put("CUSTOMER_UNIT", linkedAtt);
        }
        // 项目类型
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "pt_id");
            linkedAtt.text = JdbcMapUtil.getString(row, "pt_name");
            attLinkResult.attMap.put("PROJECT_TYPE_ID", linkedAtt);
        }
        if ("PO_ORDER_PAYMENT_REQ".equals(entCode)) { // 采购合同付款申请
            // 查询付款申请历史信息
            String sql = "SELECT COLLECTION_DEPT_TWO,BANK_OF_DEPOSIT,ACCOUNT_NO,RECEIPT,SPECIAL_BANK_OF_DEPOSIT,SPECIAL_ACCOUNT_NO FROM PO_ORDER_PAYMENT_REQ WHERE AMOUT_PM_PRJ_ID = ? AND STATUS = 'AP' ORDER BY CRT_DT DESC limit 1";
            List<Map<String, Object>> map1 = myJdbcTemplate.queryForList(sql, attValue);
            if (!CollectionUtils.isEmpty(map1)) {
                Map row2 = map1.get(0);
                // 收款单位
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
                    linkedAtt.value = JdbcMapUtil.getString(row2, "COLLECTION_DEPT_TWO");
                    linkedAtt.text = JdbcMapUtil.getString(row2, "COLLECTION_DEPT_TWO");
                    attLinkResult.attMap.put("COLLECTION_DEPT_TWO", linkedAtt);
                }
                // 开户行
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
                    linkedAtt.value = JdbcMapUtil.getString(row2, "BANK_OF_DEPOSIT");
                    linkedAtt.text = JdbcMapUtil.getString(row2, "BANK_OF_DEPOSIT");
                    attLinkResult.attMap.put("BANK_OF_DEPOSIT", linkedAtt);
                }
                // 账号
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
                    linkedAtt.value = JdbcMapUtil.getString(row2, "ACCOUNT_NO");
                    linkedAtt.text = JdbcMapUtil.getString(row2, "ACCOUNT_NO");
                    attLinkResult.attMap.put("ACCOUNT_NO", linkedAtt);
                }
                // 农民工工资专用账号收款单位
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
                    linkedAtt.value = JdbcMapUtil.getString(row2, "RECEIPT");
                    linkedAtt.text = JdbcMapUtil.getString(row2, "RECEIPT");
                    attLinkResult.attMap.put("RECEIPT", linkedAtt);
                }
                // 专户开户行
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
                    linkedAtt.value = JdbcMapUtil.getString(row2, "SPECIAL_BANK_OF_DEPOSIT");
                    linkedAtt.text = JdbcMapUtil.getString(row2, "SPECIAL_BANK_OF_DEPOSIT");
                    attLinkResult.attMap.put("SPECIAL_BANK_OF_DEPOSIT", linkedAtt);
                }
                // 专户账号
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
                    linkedAtt.value = JdbcMapUtil.getString(row2, "SPECIAL_ACCOUNT_NO");
                    linkedAtt.text = JdbcMapUtil.getString(row2, "SPECIAL_ACCOUNT_NO");
                    attLinkResult.attMap.put("SPECIAL_ACCOUNT_NO", linkedAtt);
                }
            }
            return attLinkResult;
        } else if ("PM_FUND_REQUIRE_PLAN_REQ".equals(entCode)) { // 资金需求计划申请
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");

            // 查询关联合同信息
            List<Map<String, Object>> contractMaps = myJdbcTemplate.queryForList("select o.CONTRACT_PRICE,a.PRJ_TOTAL_INVEST estimate,b.PRJ_TOTAL_INVEST budget from PO_ORDER_REQ " +
                    "o left join PM_PRJ_INVEST2 a on a.PM_PRJ_ID =o.PM_PRJ_ID left join PM_PRJ_INVEST3 b on b" +
                    ".PM_PRJ_ID = o.PM_PRJ_ID where o.PM_PRJ_ID = ? and o.`STATUS` = 'AP' order by o.CRT_DT " +
                    "limit 1", attValue);
            if (!CollectionUtils.isEmpty(contractMaps)) {
                Map<String, Object> contractRow = contractMaps.get(0);
                // 合同已支付金额

                // 支付比例

                // 概算金额
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(contractRow, "estimate");
                    linkedAtt.text = JdbcMapUtil.getString(contractRow, "estimate");
                    attLinkResult.attMap.put("ESTIMATED_AMOUNT", linkedAtt);
                }
                // 预算金额
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(contractRow, "budget");
                    linkedAtt.text = JdbcMapUtil.getString(contractRow, "budget");
                    attLinkResult.attMap.put("FINANCIAL_AMOUNT", linkedAtt);
                }
                // 结算金额
            }

            // 该项目之前有无资金需求计划
            List<Map<String, Object>> reqMaps = myJdbcTemplate.queryForList("select r.YEAR,r.IS_BUDGET_ID,r.PROJECT_NATURE_ID," +
                    "r.BASE_LOCATION_ID,r.AGENT_BUILD_UNIT_RESPONSE,r.AGENT_BUILD_UNIT_RESPONSE_PHONE,r.DEMOLITION_COMPLETED," +
                    "r.PLAN_START_DATE,r.PLAN_COMPL_DATE,r.ACTUAL_START_DATE,r.CREATE_PROJECT_COMPLETED,r.FEASIBILITY_COMPLETED," +
                    "r.SELECT_SITE_COMPLETED,r.USE_LAND_COMPLETED,r.EIA_COMPLETED,r.WOODLAND_WATER_SOIL_COMPLETED,r.ESTIMATE_COMPLETED," +
                    "r.REPLY_FILE,r.BUDGET_REVIEW_COMPLETED,r.CONSERVATION_REPLY_FILE,r.CONSTRUCT_BID_COMPLETED,r.BID_WIN_NOTICE_FILE_GROUP_ID " +
                    " from PM_FUND_REQUIRE_PLAN_REQ r " +
                    "left join pm_prj p on r.AMOUT_PM_PRJ_ID = p.id " +
                    "where p.id = ? and r.`STATUS` = 'AP'", attValue);
            if (!CollectionUtils.isEmpty(reqMaps)) {
                Map<String, Object> reqRow = reqMaps.get(0);
                if (!CollectionUtils.isEmpty(reqMaps)) {// 该项目有过资金需求计划，回显表单数据
                    List<String> keyList = getKeyList();
                    Set<String> keys = reqRow.keySet();
                    for (String key : keys) {
                        String id = JdbcMapUtil.getString(reqRow, key);
                        String name = id;
                        if (keyList.contains(key)) {// 是否财政预算
                            String sqlName = "select name from gr_set_value where id = ?";
                            List<Map<String, Object>> nameMap = myJdbcTemplate.queryForList(sqlName, id);
                            name = JdbcMapUtil.getString(nameMap.get(0), "name");
                        }
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = name;
                            attLinkResult.attMap.put(key, linkedAtt);
                        }
                    }
                }

                // 可研完成情况
            } else {// 未填写 根据项目回显

                // 立项年度
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
//                    String year = simpleDateFormat.format(JdbcMapUtil.getDate(row, "PRJ_REPLY_DATE"));
                    String year = JdbcMapUtil.getString(row, "PRJ_REPLY_DATE");
                    linkedAtt.value = year;
                    linkedAtt.text = year;
                    attLinkResult.attMap.put("YEAR", linkedAtt);
                }
                // 建设地点
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.REF_SINGLE;
                    linkedAtt.value = JdbcMapUtil.getString(row, "l_id");
                    linkedAtt.text = JdbcMapUtil.getString(row, "l_name");
                    attLinkResult.attMap.put("BASE_LOCATION_ID", linkedAtt);
                }
                // 查询五方
                List<Map<String, Object>> partyMaps = myJdbcTemplate.queryForList("SELECT r.BUILD_UNIT_RESPONSE,r" +
                        ".AGENT_PHONE FROM PM_PRJ_PARTY_REQ r left join pm_prj p on p.id = r.PM_PRJ_ID where p.id = ?" +
                        " and r.`STATUS` = 'AP' ORDER BY r.CRT_DT desc LIMIT 1", attValue);
                if (!CollectionUtils.isEmpty(partyMaps)) {
                    Map<String, Object> partyRow = partyMaps.get(0);
                    // 项目负责人
                    {
                        LinkedAtt linkedAtt = new LinkedAtt();
                        linkedAtt.type = AttDataTypeE.TEXT_SHORT;
                        linkedAtt.value = JdbcMapUtil.getString(partyRow, "BUILD_UNIT_RESPONSE");
                        linkedAtt.text = JdbcMapUtil.getString(partyRow, "BUILD_UNIT_RESPONSE");
                        attLinkResult.attMap.put("AGENT_BUILD_UNIT_RESPONSE", linkedAtt);
                    }
                    // 负责人联系电话
                    {
                        LinkedAtt linkedAtt = new LinkedAtt();
                        linkedAtt.type = AttDataTypeE.TEXT_SHORT;
                        linkedAtt.value = JdbcMapUtil.getString(partyRow, "AGENT_PHONE");
                        linkedAtt.text = JdbcMapUtil.getString(partyRow, "AGENT_PHONE");
                        attLinkResult.attMap.put("AGENT_BUILD_UNIT_RESPONSE_PHONE", linkedAtt);
                    }
                }
                // 征地拆迁完成情况
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_SHORT;
                    linkedAtt.value = null;
                    linkedAtt.text = null;
                    attLinkResult.attMap.put("DEMOLITION_COMPLETED", linkedAtt);
                }
                // 预计开工时间
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_SHORT;
                    linkedAtt.value = null;
                    linkedAtt.text = null;
                    attLinkResult.attMap.put("PLAN_START_DATE", linkedAtt);
                }
                // 预计完工时间
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_SHORT;
                    linkedAtt.value = null;
                    linkedAtt.text = null;
                    attLinkResult.attMap.put("PLAN_COMPL_DATE", linkedAtt);
                }
                // 实际开工时间
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_SHORT;
                    linkedAtt.value = null;
                    linkedAtt.text = null;
                    attLinkResult.attMap.put("ACTUAL_START_DATE", linkedAtt);
                }
                // 查询节点名称
                List<Map<String, Object>> nodeMaps = myJdbcTemplate.queryForList("select n.name,n.PROGRESS_STATUS_ID from PM_PRO_PLAN_NODE n" +
                        " left join PM_PRO_PLAN p on n.PM_PRO_PLAN_ID = p.id where p.PM_PRJ_ID = ? and n.level = '3'", attValue);
                if (CollectionUtils.isEmpty(nodeMaps)) {
                    throw new BaseException("项目没有对应的三级节点！");
                }

                // 默认未涉及
                initNUll(attLinkResult);
                // 匹配字段
                List<String> createMatch = Arrays.asList("立项", "立项批复");
                List<String> feasibility = Arrays.asList("可研", "可研批复");
                List<String> landUsePlan = Arrays.asList("用地规划", "用地规划许可证");
                List<String> eia = Arrays.asList("环评审批完成情况", "环评");
                List<String> advanceExam = Arrays.asList("用地预审");
                List<String> save = Arrays.asList("节能", "水保", "林地使用调整");
                List<String> prj = Arrays.asList("(施工)工程量清单", "工程量清单", "EPC", "施工", "工程量清单");
                for (Map<String, Object> nodeMap : nodeMaps) {
                    String name = nodeMap.get("name").toString();
                    String id = JdbcMapUtil.getString(nodeMap, "PROGRESS_STATUS_ID");
                    String sqlName = "select name from gr_set_value where id = ?";
                    List<Map<String, Object>> nameMap = myJdbcTemplate.queryForList(sqlName, id);
                    String valueName = JdbcMapUtil.getString(nameMap.get(0), "name");
                    if (judgeMatch(createMatch, name)) {
//                        if ("立项批复".equals(name)){
                        // 立项完成情况
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("CREATE_PROJECT_COMPLETED", linkedAtt);
                        }
                    } else if (judgeMatch(feasibility, name)) {
//                            if ("可研批复".equals(name)){
                        // 可研完成情况
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("FEASIBILITY_COMPLETED", linkedAtt);
                        }
                    } else if (judgeMatch(landUsePlan, name)) {
//                            if ("用地规划许可证".equals(name)){
                        // 规划选址完成情况
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("SELECT_SITE_COMPLETED", linkedAtt);
                        }
                    } else if (judgeMatch(eia, name)) {
//                        if ("环评".equals(name)){
                        // 环评完成情况
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("EIA_COMPLETED", linkedAtt);
                        }
                    } else if (judgeMatch(advanceExam, name)) {
//                        if ("用地预审".equals(name)){
                        // 用地预审
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("USE_LAND_COMPLETED", linkedAtt);
                        }
                    } else if (judgeMatch(save, name)) {
//                        if ("节能+水保+林地使用调整".equals(name)){
                        // 用地预审
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("WOODLAND_WATER_SOIL_COMPLETED", linkedAtt);
                        }
                    } else if ("初步设计概算批复".equals(name)) {
                        // 概算完成情况
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("ESTIMATE_COMPLETED", linkedAtt);
                        }
                    } else if ("预算财政评审".equals(name)) {
                        // 预算评审完成情况
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("BUDGET_REVIEW_COMPLETED", linkedAtt);
                        }
                    } else if (judgeMatch(prj, name)) {
//                        if ("工程量清单、EPC、施工".equals(name)){
                        // 预算评审完成情况
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("CONSTRUCT_BID_COMPLETED", linkedAtt);
                        }
                    }

                }
            }

            //概算批复文件信息回显
            String sql1 = "select REPLY_FILE from PM_PRJ_INVEST2 where PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,attValue);
            if (!CollectionUtils.isEmpty(list1)){
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.FILE_GROUP;
                    linkedAtt.value = JdbcMapUtil.getString(list1.get(0),"REPLY_FILE");
                    getFileInfoList(linkedAtt);
                    attLinkResult.attMap.put("REPLY_FILE", linkedAtt);
                }
            }
            //预算批复文件信息回显
            String sql2 = "select FINANCIAL_REVIEW_FILE from PM_PRJ_INVEST3 where PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
            List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,attValue);
            if (!CollectionUtils.isEmpty(list2)){
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.FILE_GROUP;
                    linkedAtt.value = JdbcMapUtil.getString(list2.get(0),"FINANCIAL_REVIEW_FILE");
                    getFileInfoList(linkedAtt);
                    attLinkResult.attMap.put("CONSERVATION_REPLY_FILE", linkedAtt);
                }
            }
            //施工中标通知书（招采里的中标通知书）
            String sql3 = "SELECT GROUP_CONCAT(BID_WIN_NOTICE_FILE_GROUP_ID) AS FILE from PO_PUBLIC_BID_REQ WHERE PM_PRJ_ID = ? AND PMS_RELEASE_WAY_ID in ('99799190825080728','99799190825080731') and status = 'AP'";
            List<Map<String,Object>> list3 = myJdbcTemplate.queryForList(sql3,attValue);
            if (!CollectionUtils.isEmpty(list3)){
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.FILE_GROUP;
                    linkedAtt.value = JdbcMapUtil.getString(list3.get(0),"FILE");
                    getFileInfoList(linkedAtt);
                    attLinkResult.attMap.put("BID_WIN_NOTICE_FILE_GROUP_ID", linkedAtt);
                }
            }
            return attLinkResult;

        } else if ("SKILL_DISCLOSURE_PAPER_RECHECK_RECORD".equals(entCode) || "PM_DESIGN_ASSIGNMENT_BOOK".equals(entCode)) {
            // SKILL_DISCLOSURE_PAPER_RECHECK_RECORD 技术交底与图纸会审记录 PM_DESIGN_ASSIGNMENT_BOOK 设计任务书
            Map resultRow1 = getAmtMap(attValue);
            attLinkResult = getResult(resultRow1, attLinkResult);
            return attLinkResult;
        } else {
            return attLinkResult;
        }
    }

    private AttLinkResult linkForGUARANTEE_ID(MyJdbcTemplate myJdbcTemplate, String attValue) {
        AttLinkResult attLinkResult = new AttLinkResult();

        // 获取保函名称

        // 查询保函相关信息
        String sql = "select NAME,SUPPLIER,BENEFICIARY,GUARANTEE_LETTER_TYPE_ID,GUARANTEE_MECHANISM,GUARANTEE_CODE,GUARANTEE_AMT,GUARANTEE_START_DATE,GUARANTEE_END_DATE,GUARANTEE_FILE,GUARANTEE_FILE from PO_GUARANTEE_LETTER_REQUIRE_REQ where id = ?";
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql, attValue);
        if (CollectionUtils.isEmpty(list)) {
            throw new BaseException("保函没有相关信息，请先完善");
        }
        Map row = list.get(0);

        // 保函名称
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "NAME");
            linkedAtt.text = JdbcMapUtil.getString(row, "NAME");
            attLinkResult.attMap.put("GUARANTEE_NAME", linkedAtt);
        }
        // 供应商
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "SUPPLIER");
            linkedAtt.text = JdbcMapUtil.getString(row, "SUPPLIER");

            attLinkResult.attMap.put("SUPPLIER", linkedAtt);
        }
        // 受益人
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "BENEFICIARY");
            linkedAtt.text = JdbcMapUtil.getString(row, "BENEFICIARY");

            attLinkResult.attMap.put("BENEFICIARY", linkedAtt);
        }
        // 保函类型
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "GUARANTEE_LETTER_TYPE_ID");
            linkedAtt.text = JdbcMapUtil.getString(row, "GUARANTEE_LETTER_TYPE_ID");

            attLinkResult.attMap.put("GUARANTEE_LETTER_TYPE_ID", linkedAtt);
        }
        // 保函开立机构
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "GUARANTEE_MECHANISM");
            linkedAtt.text = JdbcMapUtil.getString(row, "GUARANTEE_MECHANISM");

            attLinkResult.attMap.put("GUARANTEE_MECHANISM", linkedAtt);
        }
        // 保函编号
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "GUARANTEE_CODE");
            linkedAtt.text = JdbcMapUtil.getString(row, "GUARANTEE_CODE");

            attLinkResult.attMap.put("GUARANTEE_CODE", linkedAtt);
        }
        // 担保金额
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "GUARANTEE_AMT");
            linkedAtt.text = JdbcMapUtil.getString(row, "GUARANTEE_AMT");

            attLinkResult.attMap.put("GUARANTEE_AMT", linkedAtt);
        }
        // 保函开立日期
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DATE;
            linkedAtt.value = JdbcMapUtil.getString(row, "GUARANTEE_START_DATE");
            linkedAtt.text = JdbcMapUtil.getString(row, "GUARANTEE_START_DATE");

            attLinkResult.attMap.put("GUARANTEE_START_DATE", linkedAtt);
        }
        // 保函到期日期
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DATE;
            linkedAtt.value = JdbcMapUtil.getString(row, "GUARANTEE_END_DATE");
            linkedAtt.text = JdbcMapUtil.getString(row, "GUARANTEE_END_DATE");

            attLinkResult.attMap.put("GUARANTEE_END_DATE", linkedAtt);
        }
        //保函附件
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.FILE_GROUP;
            linkedAtt.value = JdbcMapUtil.getString(row,"GUARANTEE_FILE");
            getFileInfoList(linkedAtt);
            attLinkResult.attMap.put("GUARANTEE_RESULT_FILE", linkedAtt);
        }


        return attLinkResult;
    }

    private AttLinkResult linkForRELATION_CONTRACT_ID(MyJdbcTemplate myJdbcTemplate, String attValue,String sevId, String entCode) {
        AttLinkResult attLinkResult = new AttLinkResult();

        //查询明细表信息
        //99902212142022303采购合同补充协议申请-填写项目信息及关联合同信息;99902212142028526资金需求计划申请-发起 实体视图id
        if ("99902212142028526".equals(sevId) || "99902212142022303".equals(sevId)){
            String viewId = "";
            Boolean createTable = false;
            if ("99902212142028526".equals(sevId)){
                viewId = "99952822476362402";
            } else if ("99902212142022303".equals(sevId)){
                viewId = "99952822476410750";
                createTable = true;
            }
            List<LinkedRecord> linkedRecordList = new ArrayList<>();
            // 查询明细信息
            String sql = "select TOTAL_AMT,COST_TYPE_TREE_ID,FEE_DETAIL,AMT from PM_ORDER_COST_DETAIL where CONTRACT_ID = ? order by id asc";
            List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql, attValue);
            if (!CollectionUtils.isEmpty(list)) {
                for (Map<String, Object> tmp : list) {
                    LinkedRecord linkedRecord = new LinkedRecord();

                    // 费用类型
                    linkedRecord.valueMap.put("COST_TYPE_TREE_ID", tmp.get("COST_TYPE_TREE_ID").toString());
                    linkedRecord.textMap.put("COST_TYPE_TREE_ID", tmp.get("COST_TYPE_TREE_ID").toString());
                    // 费用明细
                    linkedRecord.valueMap.put("FEE_DETAIL", tmp.get("FEE_DETAIL"));
                    linkedRecord.textMap.put("FEE_DETAIL", tmp.get("FEE_DETAIL").toString());
                    // 合同金额
                    linkedRecord.valueMap.put("AMT", tmp.get("AMT"));
                    linkedRecord.textMap.put("AMT", tmp.get("AMT").toString());
                    // 金额
                    linkedRecord.valueMap.put("TOTAL_AMT", tmp.get("TOTAL_AMT"));
                    linkedRecord.textMap.put("TOTAL_AMT", tmp.get("TOTAL_AMT").toString());

                    linkedRecordList.add(linkedRecord);
                }
                attLinkResult.childData.put(viewId, linkedRecordList);
            }
            attLinkResult.childCreatable.put(viewId, createTable);
            attLinkResult.childClear.put(viewId, true);
        }

        // 根据id查询招投标信息
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select CONTACT_MOBILE_ONE,CONTACTS_ONE,YES_NO_THREE,GUARANTEE_LETTER_TYPE_IDS,IS_REFER_GUARANTEE_ID,PLAN_TOTAL_DAYS," +
                "CONTRACT_CATEGORY_ONE_ID,FILE_ID_FIVE,WIN_BID_UNIT_ONE,AMT_ONE,WINNING_BIDS_AMOUNT,BUY_TYPE_ID,BID_CTL_PRICE_LAUNCH,BUY_MATTER_ID," +
                "PM_BID_KEEP_FILE_REQ_ID,CONTRACT_NAME,PM_BID_KEEP_FILE_REQ_ID,CONTRACT_CODE,NAME,WIN_BID_UNIT_ONE,CUSTOMER_UNIT_ONE," +
                "CONTRACT_PRICE,ATT_FILE_GROUP_ID from po_order_req where id = ?", attValue);

        if (CollectionUtils.isEmpty(list)) {
            throw new BaseException("合同相关属性不完善！");
        }
        Map row = list.get(0);

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
        // 首付款比列
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "AMT_ONE");
            linkedAtt.text = JdbcMapUtil.getString(row, "AMT_ONE");
            attLinkResult.attMap.put("AMT_ONE", linkedAtt);
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
        //是否涉及保函 99902212142031851=是；99902212142031855=否
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
        if ("99902212142031851".equals(baoHanId)){
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
            linkedAtt.value = JdbcMapUtil.getString(row, "CONTACTS_ONE");
            linkedAtt.text = JdbcMapUtil.getString(row, "CONTACTS_ONE");
            attLinkResult.attMap.put("CONTACTS_ONE", linkedAtt);
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
            getFileInfoList(linkedAtt);
            attLinkResult.attMap.put("FILE_ID_SEVEN",linkedAtt);
        }
        //资金需求申请合同附件回显
        if ("PM_FUND_REQUIRE_PLAN_REQ".equals(entCode) || "PO_ORDER_SUPPLEMENT_REQ".equals(entCode)){
            //合同附件
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.FILE_GROUP;
                linkedAtt.value = JdbcMapUtil.getString(row,"ATT_FILE_GROUP_ID");
                getFileInfoList(linkedAtt);
                attLinkResult.attMap.put("CONTRACT_FILE_GROUP_ID", linkedAtt);
            }
        }
        //查询联系人明细表信息
        //99902212142022303采购合同补充协议申请-填写项目信息及关联合同信息
        if ("99902212142022303".equals(sevId)){
            String viewId = "";
            if ("99902212142022303".equals(sevId)){
                viewId = "99952822476378742";
            }
            List<LinkedRecord> linkedRecordList = new ArrayList<>();
            // 查询明细信息
            String sql1 = "select WIN_BID_UNIT_ONE,OPPO_SITE_LINK_MAN,OPPO_SITE_CONTACT from CONTRACT_SIGNING_CONTACT where PARENT_ID = ?";
            List<Map<String, Object>> list1 = myJdbcTemplate.queryForList(sql1, attValue);
            if (!CollectionUtils.isEmpty(list1)) {
                for (Map<String, Object> tmp : list1) {
                    LinkedRecord linkedRecord = new LinkedRecord();

                    // 相对方公司
                    linkedRecord.valueMap.put("WIN_BID_UNIT_ONE", JdbcMapUtil.getString(tmp,"WIN_BID_UNIT_ONE"));
                    linkedRecord.textMap.put("WIN_BID_UNIT_ONE", JdbcMapUtil.getString(tmp,"WIN_BID_UNIT_ONE"));
                    // 相对方联系人
                    linkedRecord.valueMap.put("OPPO_SITE_LINK_MAN", JdbcMapUtil.getString(tmp,"OPPO_SITE_LINK_MAN"));
                    linkedRecord.textMap.put("OPPO_SITE_LINK_MAN", JdbcMapUtil.getString(tmp,"OPPO_SITE_LINK_MAN"));
                    // 相对方联系方式
                    linkedRecord.valueMap.put("OPPO_SITE_CONTACT", JdbcMapUtil.getString(tmp,"OPPO_SITE_CONTACT"));
                    linkedRecord.textMap.put("OPPO_SITE_CONTACT", JdbcMapUtil.getString(tmp,"OPPO_SITE_CONTACT"));

                    linkedRecordList.add(linkedRecord);
                }
                attLinkResult.childData.put(viewId, linkedRecordList);
            }
            attLinkResult.childCreatable.put(viewId, true);
            attLinkResult.childClear.put(viewId, true);
        }

        return attLinkResult;
    }

    private AttLinkResult linkForCONTRACT_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String sevId,String entCode) {
        AttLinkResult attLinkResult = new AttLinkResult();

        // 根据id查询招投标信息
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select CONTRACT_CODE, CONTRACT_CATEGORY_ONE_ID, CONTRACT_NAME, CONTRACT_PRICE, " +
                "BIDDING_NAME_ID,PMS_RELEASE_WAY_ID,BID_CTL_PRICE_LAUNCH,PURCHASE_TYPE,WIN_BID_UNIT_TXT,WINNING_BIDS_AMOUNT,PLAN_TOTAL_DAYS," +
                "IS_REFER_GUARANTEE_ID,GUARANTEE_LETTER_TYPE_IDS,YES_NO_THREE " +
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
            linkedAtt.value = JdbcMapUtil.getString(row, "CONTRACT_CATEGORY_ONE_ID");
            linkedAtt.text = JdbcMapUtil.getString(row, "CONTRACT_CATEGORY_ONE_ID");
            attLinkResult.attMap.put("CONTRACT_CATEGORY_ID", linkedAtt);
        }
        // 关联招采
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "BIDDING_NAME_ID");
            linkedAtt.text = JdbcMapUtil.getString(row, "BIDDING_NAME_ID");
            attLinkResult.attMap.put("BIDDING_NAME_ID", linkedAtt);
        }
        // 招标类别
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
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "BID_CTL_PRICE_LAUNCH");
            linkedAtt.text = JdbcMapUtil.getString(row, "BID_CTL_PRICE_LAUNCH");
            attLinkResult.attMap.put("BID_CTL_PRICE_LAUNCH", linkedAtt);
        }
        // 采购方式
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "PURCHASE_TYPE");
            linkedAtt.text = JdbcMapUtil.getString(row, "PURCHASE_TYPE");
            attLinkResult.attMap.put("PURCHASE_TYPE", linkedAtt);
        }
        // 中标单位
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "WIN_BID_UNIT_TXT");
            linkedAtt.text = JdbcMapUtil.getString(row, "WIN_BID_UNIT_TXT");
            attLinkResult.attMap.put("WIN_BID_UNIT_TXT", linkedAtt);
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
            attLinkResult.attMap.put("CONTRACT_DAYS", linkedAtt);
        }

        //保函逻辑可改
        List<String> editGuarantee = getEditGuarantee();
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
        List<String> notAutoBaoHan = getNotAutoBaoHan();
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
        if ("99902212142033284".equals(sevId)) {
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
                    linkedRecord.valueMap.put("PAY_AMT_ONE", "0");
                    linkedRecord.textMap.put("PAY_AMT_ONE", "0");

                    linkedRecordList.add(linkedRecord);
                }
                attLinkResult.childData.put("99902212142514118", linkedRecordList);
            }
            attLinkResult.childCreatable.put("99902212142514118", false);
            attLinkResult.childClear.put("99902212142514118", true);
        }

        //需要带出相对方联系人明细的实体试图
        List<String> contactDetailList = getContactDetailList();
        //需要带出联系人明细的实体试图(只读)
        List<String> contactListRead = getContactListRead();
        //联系人明细信息
        if (contactDetailList.contains(sevId)) {
            String viewId = "";
            if ("99902212142025475".equals(sevId)){
                viewId = "99952822476384659"; //合同终止联系人明细试图部门id
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
                    linkedRecordList.add(linkedRecord);
                }
                attLinkResult.childData.put(viewId, linkedRecordList);
            }
            attLinkResult.childCreatable.put(viewId, false);
            attLinkResult.childClear.put(viewId, true);
        }
        return attLinkResult;
    }

    // list内求和
    private BigDecimal bigDecimalSum(List<Map<String, Object>> list) {
        BigDecimal sum = new BigDecimal(0);
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                String date = list.get(0).get("PAY_AMT_ONE").toString();
                sum = sum.add(new BigDecimal(date));
            } else {
                String date = list.get(i).get("PAY_AMT_TWO").toString();
                sum = sum.add(new BigDecimal(date));
            }
        }
        return sum;
    }

    private AttLinkResult linkForBIDDING_NAME_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String sevId) {
        AttLinkResult attLinkResult = new AttLinkResult();
        // 合同签订的费用明细实体视图
        if ("99799190825103187".equals(sevId)) {
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
                attLinkResult.childData.put("99902212142513357", linkedRecordList);
            }
            attLinkResult.childCreatable.put("99902212142513357", false);
            attLinkResult.childClear.put("99902212142513357", true);

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

    private AttLinkResult linkForPROJECT_TYPE_ID(MyJdbcTemplate myJdbcTemplate, String attValue) {
        AttLinkResult attLinkResult = new AttLinkResult();

        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select v0.id project_type_id,v0.name project_type_name,v1.id CON_SCALE_TYPE_ID,v1.name CON_SCALE_TYPE_NAME,v2.id CON_SCALE_UOM_ID,v2.name CON_SCALE_UOM_NAME from pm_prj_type_link t join gr_set_value v0 on t.PROJECT_TYPE_ID=v0.id join gr_set_value v1 on t.CON_SCALE_TYPE_ID=v1.id join gr_set_value v2 on t.CON_SCALE_UOM_ID=v2.id where t.PROJECT_TYPE_ID=?", attValue);

        if (SharedUtil.isEmptyList(list)) {
            throw new BaseException("未设置相应项目类型的联动！");
        } else if (list.size() > 1) {
            throw new BaseException("重复设置相应项目类型的联动！");
        }

        Map row = list.get(0);

        //建设规模类型
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "CON_SCALE_TYPE_ID");
            linkedAtt.text = JdbcMapUtil.getString(row, "CON_SCALE_TYPE_NAME");

            attLinkResult.attMap.put("CON_SCALE_TYPE_ID", linkedAtt);
        }
        //建设规模单位
        String id = JdbcMapUtil.getString(row, "CON_SCALE_UOM_ID");
        String name = JdbcMapUtil.getString(row, "CON_SCALE_UOM_NAME");
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = id;
            linkedAtt.text = name;
            attLinkResult.attMap.put("CON_SCALE_UOM_ID", linkedAtt);
        }

        Boolean areashow = true; //面积显示
        Boolean lengthShow = true; //长显示
        Boolean widthShow = true; //宽显示
        Boolean otherShow = true; //其他显示
        Boolean seaShow = true; //海域面积显示

        Boolean areaEdit = true; //面积可改
        Boolean lengthEdit = true; //长可改
        Boolean widthEdit = true; //宽可改
        Boolean otherEdit = true; //其他可改
        Boolean seaEdit = true; //海域面积可改

        Boolean areaMustEdit = true; //面积必填
        Boolean lengthMustEdit = true; //长必填
        Boolean widthMustEdit = true; //宽必填
        Boolean otherMustEdit = true; //其他必填
        Boolean seaMustEdit = true; //海域面积必填

        String name1 = JdbcMapUtil.getString(row, "CON_SCALE_TYPE_NAME");
        if (name1.contains("面积")){
            if (name1.contains("海域")){
                lengthShow = false;
                widthShow = false;
                otherShow = false;
                areashow = false;
                lengthMustEdit = false;
                widthMustEdit = false;
                otherMustEdit = false;
                areaMustEdit = false;
            } else if (name1.contains("建筑")){
                lengthShow = false;
                widthShow = false;
                otherShow = false;
                seaShow = false;
                lengthMustEdit = false;
                widthMustEdit = false;
                otherMustEdit = false;
                seaMustEdit = false;
            }
        } else if (name1.contains("长宽")){
            areashow = false;
            otherShow = false;
            areaMustEdit = false;
            otherMustEdit = false;
            seaShow = false;
            seaMustEdit = false;
        } else {
            areashow = false;
            lengthShow = false;
            widthShow = false;
            areaMustEdit = false;
            lengthMustEdit = false;
            widthMustEdit = false;
            seaShow = false;
            seaMustEdit = false;
        }
        //面积
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.changeToShown = areashow;
            linkedAtt.changeToMandatory = areaMustEdit;
            linkedAtt.text = null;
            linkedAtt.value = null;
            attLinkResult.attMap.put("QTY_ONE", linkedAtt);
        }
        //长
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.changeToShown = lengthShow;
            linkedAtt.changeToMandatory = lengthMustEdit;
            linkedAtt.text = null;
            linkedAtt.value = null;
            attLinkResult.attMap.put("CON_SCALE_QTY", linkedAtt);
        }
        //宽
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.changeToShown = widthShow;
            linkedAtt.changeToMandatory = widthMustEdit;
            linkedAtt.text = null;
            linkedAtt.value = null;
            attLinkResult.attMap.put("CON_SCALE_QTY2", linkedAtt);
        }
        //其他
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.changeToShown = otherShow;
            linkedAtt.changeToMandatory = otherMustEdit;
            linkedAtt.text = null;
            linkedAtt.value = null;
            attLinkResult.attMap.put("QTY_TWO", linkedAtt);
        }
        //海域面积
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.changeToShown = seaShow;
            linkedAtt.changeToMandatory = seaMustEdit;
            linkedAtt.text = null;
            linkedAtt.value = null;
            attLinkResult.attMap.put("QTY_THREE", linkedAtt);
        }

        return attLinkResult;
    }

    private AttLinkResult linkForPM_PRJ_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode) {
        AttLinkResult attLinkResult = new AttLinkResult();

        // 项目基础信息
        List<Map<String, Object>> list = myJdbcTemplate
                .queryForList("select t.PRJ_CODE as prj_code,t.code code,c.id customer_id,c.name customer_name,m.id m_id,m.name m_name," +
                        "l.id l_id,l.name l_name,t.FLOOR_AREA,pt.id pt_id,pt.name pt_name,st.id st_id,st.name st_name," +
                        "su.id su_id,su.name su_name,t.CON_SCALE_QTY,t.CON_SCALE_QTY2,t.PRJ_SITUATION, t.BUILD_YEARS," +
                        "t.PRJ_REPLY_NO, t.PRJ_REPLY_DATE, t.PRJ_REPLY_FILE, t.INVESTMENT_SOURCE_ID,t.BUILDING_AREA, " +
                        "(SELECT PRJ_TOTAL_INVEST from PM_PRJ_INVEST1 WHERE PM_PRJ_ID = t.id order by CRT_DT desc limit 1) as 'FS', " +
                        "(SELECT PRJ_TOTAL_INVEST from PM_PRJ_INVEST2 WHERE PM_PRJ_ID = t.id order by CRT_DT desc limit 1) as 'PD', " +
                        "(SELECT PRJ_TOTAL_INVEST from PM_PRJ_INVEST3 WHERE PM_PRJ_ID = t.id order by CRT_DT desc limit 1) as 'budget', " +
                        "t.QTY_ONE,t.QTY_TWO,t.QTY_THREE " +
                        "from pm_prj t join PM_PARTY c on t.id=? and t.CUSTOMER_UNIT=c.id " +
                        "join gr_set_value m on t.PRJ_MANAGE_MODE_ID = m.ID " +
                        "join gr_set_value l on t.BASE_LOCATION_ID=l.id " +
                        "join gr_set_value pt on t.PROJECT_TYPE_ID=pt.id " +
                        "join gr_set_value st on t.CON_SCALE_TYPE_ID=st.id " +
                        "join gr_set_value su on t.CON_SCALE_UOM_ID=su.id", attValue);


//        if (CollectionUtils.isEmpty(list)) {
//            throw new BaseException("项目的相关属性不完整！");
//        }
        if (CollectionUtils.isEmpty(list)){

            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_SHORT;
                linkedAtt.value = "";
                linkedAtt.text = "";

                attLinkResult.attMap.put("PRJ_CODE", linkedAtt); // 项目编号
                attLinkResult.attMap.put("BUILDING_AREA", linkedAtt); // 建筑面积
                attLinkResult.attMap.put("CUSTOMER_UNIT", linkedAtt); // 业主单位
                attLinkResult.attMap.put("PRJ_MANAGE_MODE_ID", linkedAtt); // 项目管理模式
                attLinkResult.attMap.put("BASE_LOCATION_ID", linkedAtt); // 建设地点
                attLinkResult.attMap.put("FLOOR_AREA", linkedAtt); // 占地面积
                attLinkResult.attMap.put("PROJECT_TYPE_ID", linkedAtt); // 项目类型
                attLinkResult.attMap.put("CON_SCALE_TYPE_ID", linkedAtt); // 建设规模类型
                attLinkResult.attMap.put("CON_SCALE_UOM_ID", linkedAtt); // 建设规模单位
                attLinkResult.attMap.put("QTY_ONE", linkedAtt); // 建筑面积
                attLinkResult.attMap.put("QTY_THREE", linkedAtt); // 海域面积
                attLinkResult.attMap.put("CON_SCALE_QTY", linkedAtt); // 长
                attLinkResult.attMap.put("CON_SCALE_QTY2", linkedAtt); // 宽
                attLinkResult.attMap.put("QTY_TWO", linkedAtt); // 其他
                attLinkResult.attMap.put("BUILD_YEARS", linkedAtt); // 建设年限
                attLinkResult.attMap.put("PRJ_SITUATION", linkedAtt); // 项目概况
                attLinkResult.attMap.put("PRJ_INTRODUCE", linkedAtt); // 项目介绍
                attLinkResult.attMap.put("PRJ_REPLY_NO", linkedAtt); // 批复文号
                attLinkResult.attMap.put("REPLY_NO", linkedAtt); // 批复文号
                attLinkResult.attMap.put("PRJ_REPLY_DATE", linkedAtt); // 批复日期
                attLinkResult.attMap.put("PRJ_REPLY_FILE", linkedAtt); // 批复材料
                attLinkResult.attMap.put("INVESTMENT_SOURCE_ID", linkedAtt); // 资金来源
                attLinkResult.attMap.put("PM_FUND_SOURCE_ID", linkedAtt); // 资金来源
                attLinkResult.attMap.put("FEASIBILITY_APPROVE_FUND", linkedAtt); // 可研批复资金
                attLinkResult.attMap.put("ESTIMATE_APPROVE_FUND", linkedAtt); // 初概批复资金
                attLinkResult.attMap.put("EVALUATION_APPROVE_FUND", linkedAtt); // 财评批复资金
                attLinkResult.attMap.put("PRJ_TOTAL_INVEST", linkedAtt); // 总投资
                attLinkResult.attMap.put("PROJECT_AMT", linkedAtt); // 工程费用
                attLinkResult.attMap.put("PROJECT_OTHER_AMT", linkedAtt); // 工程建设其他费用
                attLinkResult.attMap.put("PREPARE_AMT", linkedAtt); // 预备费
                attLinkResult.attMap.put("CONSTRUCT_PERIOD_INTEREST", linkedAtt); // 利息
                attLinkResult.attMap.put("REPLY_NO", linkedAtt); // 批复文号
                attLinkResult.attMap.put("PRJ_REPLY_NO", linkedAtt); // 批复文号
            }
            return attLinkResult;
        }

        Map row = list.get(0);

        if ("PM_PRJ_KICK_OFF_REQ".equals(entCode)) { // 工程开工报审
            String sql = "select PRJ_TOTAL_INVEST,PROJECT_AMT from PM_PRJ_INVEST2 where PM_PRJ_ID = ? and STATUS = 'AP' order by CRT_DT desc limit 1";
            List<Map<String, Object>> map = myJdbcTemplate.queryForList(sql, attValue);
            if (CollectionUtils.isEmpty(map)) {
                throw new BaseException("该项目的总投资、工程费用信息不完善！");
            }
            Map row2 = map.get(0);
            // 总投资
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.DOUBLE;
                linkedAtt.value = JdbcMapUtil.getString(row2, "PRJ_TOTAL_INVEST");
                linkedAtt.text = JdbcMapUtil.getString(row2, "PRJ_TOTAL_INVEST");

                attLinkResult.attMap.put("PRJ_TOTAL_INVEST", linkedAtt);
            }
            // 工程费用
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.DOUBLE;
                linkedAtt.value = JdbcMapUtil.getString(row2, "PROJECT_AMT");
                linkedAtt.text = JdbcMapUtil.getString(row2, "PROJECT_AMT");

                attLinkResult.attMap.put("PROJECT_AMT", linkedAtt);
            }
        }

        // 项目编号
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_SHORT;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "prj_code")) ? "":JdbcMapUtil.getString(row, "prj_code");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "prj_code")) ? "":JdbcMapUtil.getString(row, "prj_code");

            attLinkResult.attMap.put("PRJ_CODE", linkedAtt);
        }
        // 建筑面积
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "BUILDING_AREA")) ? "":JdbcMapUtil.getString(row, "BUILDING_AREA");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "BUILDING_AREA")) ? "":JdbcMapUtil.getString(row, "BUILDING_AREA");
            attLinkResult.attMap.put("BUILDING_AREA", linkedAtt);
        }
        // 业主单位
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "customer_id")) ? "":JdbcMapUtil.getString(row, "customer_id");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "customer_name")) ? "":JdbcMapUtil.getString(row, "customer_name");

            attLinkResult.attMap.put("CUSTOMER_UNIT", linkedAtt);
        }
        // 项目管理模式
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "m_id")) ? "":JdbcMapUtil.getString(row, "m_id");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "m_name")) ? "":JdbcMapUtil.getString(row, "m_name");

            attLinkResult.attMap.put("PRJ_MANAGE_MODE_ID", linkedAtt);
        }
        // 建设地点
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "l_id")) ? "":JdbcMapUtil.getString(row, "l_id");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "l_name")) ? "":JdbcMapUtil.getString(row, "l_name");

            attLinkResult.attMap.put("BASE_LOCATION_ID", linkedAtt);
        }
        // 占地面积
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "FLOOR_AREA")) ? "":JdbcMapUtil.getString(row, "FLOOR_AREA");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "FLOOR_AREA")) ? "":JdbcMapUtil.getString(row, "FLOOR_AREA");

            attLinkResult.attMap.put("FLOOR_AREA", linkedAtt);
        }
        // 项目类型
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "pt_id")) ? "":JdbcMapUtil.getString(row, "pt_id");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "pt_name")) ? "":JdbcMapUtil.getString(row, "pt_name");

            attLinkResult.attMap.put("PROJECT_TYPE_ID", linkedAtt);
        }
        // 建设规模类型
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "st_id")) ? "":JdbcMapUtil.getString(row, "st_id");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "st_name")) ? "":JdbcMapUtil.getString(row, "st_name");

            attLinkResult.attMap.put("CON_SCALE_TYPE_ID", linkedAtt);
        }
        // 建设规模单位
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "su_id")) ? "":JdbcMapUtil.getString(row, "su_id");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "su_name")) ? "":JdbcMapUtil.getString(row, "su_name");

            attLinkResult.attMap.put("CON_SCALE_UOM_ID", linkedAtt);
        }

        Boolean areashow = true; //面积显示
        Boolean lengthShow = true; //长显示
        Boolean widthShow = true; //宽显示
        Boolean otherShow = true; //其他显示
        Boolean seaShow = true; //海域面积显示

        Boolean areaEdit = true; //面积可改
        Boolean lengthEdit = true; //长可改
        Boolean widthEdit = true; //宽可改
        Boolean otherEdit = true; //其他可改
        Boolean seaEdit = true; //海域面积可改

        Boolean areaMustEdit = true; //面积必填
        Boolean lengthMustEdit = true; //长必填
        Boolean widthMustEdit = true; //宽必填
        Boolean otherMustEdit = true; //其他必填
        Boolean seaMustEdit = true; //海域面积必填

        String name1 = JdbcMapUtil.getString(row, "st_name");
        if (name1.contains("面积")){
            if (name1.contains("海域")){
                lengthShow = false;
                widthShow = false;
                otherShow = false;
                lengthMustEdit = false;
                widthMustEdit = false;
                otherMustEdit = false;
                areashow = false;
                areaMustEdit = false;
            } else if (name1.contains("建筑面积")){
                lengthShow = false;
                widthShow = false;
                otherShow = false;
                lengthMustEdit = false;
                widthMustEdit = false;
                otherMustEdit = false;
                seaShow = false;
                seaMustEdit = false;
            }

        } else if (name1.contains("长宽")){
            areashow = false;
            otherShow = false;
            areaMustEdit = false;
            otherMustEdit = false;
            seaShow = false;
            seaMustEdit = false;
        } else {
            areashow = false;
            lengthShow = false;
            widthShow = false;
            areaMustEdit = false;
            lengthMustEdit = false;
            widthMustEdit = false;
            seaShow = false;
            seaMustEdit = false;
        }
        //面积
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.changeToShown = areashow;
            linkedAtt.changeToMandatory = areaMustEdit;
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "QTY_ONE")) ? "":JdbcMapUtil.getString(row, "QTY_ONE");
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "QTY_ONE")) ? "":JdbcMapUtil.getString(row, "QTY_ONE");
            attLinkResult.attMap.put("QTY_ONE", linkedAtt);
        }
        //海域面积
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.changeToShown = seaShow;
            linkedAtt.changeToMandatory = seaMustEdit;
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "QTY_THREE")) ? "":JdbcMapUtil.getString(row, "QTY_THREE");
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "QTY_THREE")) ? "":JdbcMapUtil.getString(row, "QTY_THREE");
            attLinkResult.attMap.put("QTY_THREE", linkedAtt);
        }
        //长
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.changeToShown = lengthShow;
            linkedAtt.changeToMandatory = lengthMustEdit;
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "CON_SCALE_QTY")) ? "":JdbcMapUtil.getString(row, "CON_SCALE_QTY");
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "CON_SCALE_QTY")) ? "":JdbcMapUtil.getString(row, "CON_SCALE_QTY");
            attLinkResult.attMap.put("CON_SCALE_QTY", linkedAtt);
        }
        //宽
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.changeToShown = widthShow;
            linkedAtt.changeToMandatory = widthMustEdit;
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "CON_SCALE_QTY2")) ? "":JdbcMapUtil.getString(row, "CON_SCALE_QTY2");
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "CON_SCALE_QTY2")) ? "":JdbcMapUtil.getString(row, "CON_SCALE_QTY2");
            attLinkResult.attMap.put("CON_SCALE_QTY2", linkedAtt);
        }
        //其他
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.changeToShown = otherShow;
            linkedAtt.changeToMandatory = otherMustEdit;
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "QTY_TWO")) ? "":JdbcMapUtil.getString(row, "QTY_TWO");
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "QTY_TWO")) ? "":JdbcMapUtil.getString(row, "QTY_TWO");
            attLinkResult.attMap.put("QTY_TWO", linkedAtt);
        }


        // 建设年限
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "BUILD_YEARS")) ? "":JdbcMapUtil.getString(row, "BUILD_YEARS");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "BUILD_YEARS")) ? "":JdbcMapUtil.getString(row, "BUILD_YEARS");
            attLinkResult.attMap.put("BUILD_YEARS", linkedAtt);
        }
        // 项目概况
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "PRJ_SITUATION")) ? "":JdbcMapUtil.getString(row, "PRJ_SITUATION");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "PRJ_SITUATION")) ? "":JdbcMapUtil.getString(row, "PRJ_SITUATION");

            attLinkResult.attMap.put("PRJ_SITUATION", linkedAtt);
            attLinkResult.attMap.put("PRJ_INTRODUCE", linkedAtt);
        }
        // 批复文号
        Map resultRow = getReplyNo(attValue);
        String value = JdbcMapUtil.getString(resultRow, "REPLY_NO_WR");;
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(resultRow, "REPLY_NO_WR")) ? "":JdbcMapUtil.getString(resultRow, "REPLY_NO_WR");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(resultRow, "REPLY_NO_WR")) ? "":JdbcMapUtil.getString(resultRow, "REPLY_NO_WR");

            attLinkResult.attMap.put("PRJ_REPLY_NO", linkedAtt);
            attLinkResult.attMap.put("REPLY_NO", linkedAtt);
        }
        // 批复日期
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DATE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "PRJ_REPLY_DATE")) ? "":JdbcMapUtil.getString(row, "PRJ_REPLY_DATE");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "PRJ_REPLY_DATE")) ? "":JdbcMapUtil.getString(row, "PRJ_REPLY_DATE");

            attLinkResult.attMap.put("PRJ_REPLY_DATE", linkedAtt);
        }
        // 批复材料
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.FILE_GROUP;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "PRJ_REPLY_FILE")) ? "":JdbcMapUtil.getString(row, "PRJ_REPLY_FILE");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "PRJ_REPLY_FILE")) ? "":JdbcMapUtil.getString(row, "PRJ_REPLY_FILE");

            attLinkResult.attMap.put("PRJ_REPLY_FILE", linkedAtt);
        }
        // 资金来源
        {
            String id = JdbcMapUtil.getString(row, "INVESTMENT_SOURCE_ID");
            String sqlName = "select name from gr_set_value where id = ?";
            List<Map<String, Object>> nameMap = myJdbcTemplate.queryForList(sqlName, id);
            String name = "";
            if (!CollectionUtils.isEmpty(nameMap)){
                name = JdbcMapUtil.getString(nameMap.get(0), "name");
            }
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = id;
            linkedAtt.text = name;

            attLinkResult.attMap.put("INVESTMENT_SOURCE_ID", linkedAtt);
            attLinkResult.attMap.put("PM_FUND_SOURCE_ID", linkedAtt);
        }
        if("PM_BUY_DEMAND_REQ".equals(entCode)){ // 采购需求审批
            // 99799190825080705 = 企业自筹
            String id = JdbcMapUtil.getString(row, "INVESTMENT_SOURCE_ID");
            String val = "";
            String txt = "";
            if ("99799190825080705".equals(id)){
                val = "99799190825080670";
                txt = "否";
            } else {
                val = "99799190825080669";
                txt = "是";
            }
            //是否政府投资项目
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = val;
                linkedAtt.text = txt;
                attLinkResult.attMap.put("YES_NO_ONE", linkedAtt);
            }
        }
        // 可研批复资金
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "FS")) ? "":JdbcMapUtil.getString(row, "FS");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "FS")) ? "":JdbcMapUtil.getString(row, "FS");

            attLinkResult.attMap.put("FEASIBILITY_APPROVE_FUND", linkedAtt);
        }
        // 初概批复资金
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "PD")) ? "":JdbcMapUtil.getString(row, "PD");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "PD")) ? "":JdbcMapUtil.getString(row, "PD");

            attLinkResult.attMap.put("ESTIMATE_APPROVE_FUND", linkedAtt);
        }
        // 财评批复资金
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "budget")) ? "":JdbcMapUtil.getString(row, "budget");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "budget")) ? "":JdbcMapUtil.getString(row, "budget");

            attLinkResult.attMap.put("EVALUATION_APPROVE_FUND", linkedAtt);
        }

        // 资金信息回显。优先级 可研估算<初设概算<预算财评
        List<String> amtList = getAmtList();
        if (amtList.contains(entCode)) {
            // 查询预算财评信息
            Map resultRow1 = getAmtMap(attValue);
            attLinkResult = getResult(resultRow1, attLinkResult);
        }

        return attLinkResult;
    }

    private AttLinkResult linkForSTATUS(String attValue, String sevId) {
        AttLinkResult attLinkResult = null;
        if ("99626673179203336".equals(sevId)) {
            // 实体视图ID=99626673179203336,当前触发联动的实体视图是”测试学生“：

            attLinkResult = linkForSTATUSofTestStu(attValue);
        } else if ("99626673179203381".equals(sevId)) {
            // 实体视图ID=99626673179203381,当前触发联动的实体视图是”测试老师“：

            attLinkResult = linkForSTATUSofTestClass(attValue);
        }
        return attLinkResult;
    }

    private AttLinkResult linkForSTATUSofTestStu(String attValue) {
        AttLinkResult attLinkResult = new AttLinkResult();

        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            if ("AP".equals(attValue)) {
                linkedAtt.value = "已批准";
                linkedAtt.text = "已批准";
            } else if ("APING".equals(attValue)) {
                linkedAtt.value = "审批中";
                linkedAtt.text = "审批中";
            } else if ("DN".equals(attValue)) {
                linkedAtt.value = "已拒绝";
                linkedAtt.text = "已拒绝";
            } else if ("DR".equals(attValue)) {
                linkedAtt.value = "草稿";
                linkedAtt.text = "草稿";
            } else if ("VD".equals(attValue)) {
                linkedAtt.value = "已作废";
                linkedAtt.text = "已作废";
            } else if ("VDING".equals(attValue)) {
                linkedAtt.value = "作废中";
                linkedAtt.text = "作废中";
            }
            attLinkResult.attMap.put("NAME", linkedAtt);
        }
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            if ("AP".equals(attValue)) {
                linkedAtt.value = "已批准备注";
                linkedAtt.text = "已批准备注";
            } else if ("APING".equals(attValue)) {
                linkedAtt.value = "审批中备注";
                linkedAtt.text = "审批中备注";
            } else if ("DN".equals(attValue)) {
                linkedAtt.value = "已拒绝备注";
                linkedAtt.text = "已拒绝备注";
            } else if ("DR".equals(attValue)) {
                linkedAtt.value = "草稿备注";
                linkedAtt.text = "草稿备注";
            } else if ("VD".equals(attValue)) {
                linkedAtt.value = "已作废备注";
                linkedAtt.text = "已作废备注";
            } else if ("VDING".equals(attValue)) {
                linkedAtt.value = "作废中备注";
                linkedAtt.text = "作废中备注";
            }
            attLinkResult.attMap.put("REMARK", linkedAtt);
        }
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.INTEGER;
            if ("AP".equals(attValue)) {
                linkedAtt.value = "1";
                linkedAtt.text = "1";
            } else if ("APING".equals(attValue)) {
                linkedAtt.value = "2";
                linkedAtt.text = "2";
            } else if ("DN".equals(attValue)) {
                linkedAtt.value = "3";
                linkedAtt.text = "3";
            } else if ("DR".equals(attValue)) {
                linkedAtt.value = "4";
                linkedAtt.text = "4";
            } else if ("VD".equals(attValue)) {
                linkedAtt.value = "5";
                linkedAtt.text = "5";
            } else if ("VDING".equals(attValue)) {
                linkedAtt.value = "6";
                linkedAtt.text = "6";
            }
            attLinkResult.attMap.put("CALC_TIMES", linkedAtt);
        }
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            if ("AP".equals(attValue)) {
                linkedAtt.value = "1.10";
                linkedAtt.text = "1.10";
            } else if ("APING".equals(attValue)) {
                linkedAtt.value = "2.10";
                linkedAtt.text = "2.10";
            } else if ("DN".equals(attValue)) {
                linkedAtt.value = "3.10";
                linkedAtt.text = "3.10";
            } else if ("DR".equals(attValue)) {
                linkedAtt.value = "4.10";
                linkedAtt.text = "4.10";
            } else if ("VD".equals(attValue)) {
                linkedAtt.value = "5.10";
                linkedAtt.text = "5.10";
            } else if ("VDING".equals(attValue)) {
                linkedAtt.value = "6.10";
                linkedAtt.text = "6.10";
            }
            attLinkResult.attMap.put("SIGN_AMT", linkedAtt);
        }
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DATE;
            if ("AP".equals(attValue)) {
                linkedAtt.value = "2022-01-01";
                linkedAtt.text = "2022-01-01";
            } else if ("APING".equals(attValue)) {
                linkedAtt.value = "2022-01-02";
                linkedAtt.text = "2022-01-02";
            } else if ("DN".equals(attValue)) {
                linkedAtt.value = "2022-01-03";
                linkedAtt.text = "2022-01-03";
            } else if ("DR".equals(attValue)) {
                linkedAtt.value = "2022-01-04";
                linkedAtt.text = "2022-01-04";
            } else if ("VD".equals(attValue)) {
                linkedAtt.value = "2022-01-05";
                linkedAtt.text = "2022-01-05";
            } else if ("VDING".equals(attValue)) {
                linkedAtt.value = "2022-01-06";
                linkedAtt.text = "2022-01-06";
            }
            attLinkResult.attMap.put("SIGN_DATE", linkedAtt);
        }
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TIME;
            if ("AP".equals(attValue)) {
                linkedAtt.value = "10:10:10";
                linkedAtt.text = "10:10:10";
            } else if ("APING".equals(attValue)) {
                linkedAtt.value = "11:11:11";
                linkedAtt.text = "11:11:11";
            } else if ("DN".equals(attValue)) {
                linkedAtt.value = "12:12:12";
                linkedAtt.text = "12:12:12";
            } else if ("DR".equals(attValue)) {
                linkedAtt.value = "13:13:13";
                linkedAtt.text = "13:13:13";
            } else if ("VD".equals(attValue)) {
                linkedAtt.value = "14:14:14";
                linkedAtt.text = "14:14:14";
            } else if ("VDING".equals(attValue)) {
                linkedAtt.value = "15:15:15";
                linkedAtt.text = "15:15:15";
            }
            attLinkResult.attMap.put("TEST_TIME", linkedAtt);
        }
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DATETIME;
            if ("AP".equals(attValue)) {
                linkedAtt.value = "2022-01-01 10:10:10";
                linkedAtt.text = "2022-01-0110:10:10";
            } else if ("APING".equals(attValue)) {
                linkedAtt.value = "2022-01-02 11:11:11";
                linkedAtt.text = "2022-01-02 11:11:11";
            } else if ("DN".equals(attValue)) {
                linkedAtt.value = "2022-01-03 12:12:12";
                linkedAtt.text = "2022-01-03 12:12:12";
            } else if ("DR".equals(attValue)) {
                linkedAtt.value = "2022-01-04 13:13:13";
                linkedAtt.text = "2022-01-04 13:13:13";
            } else if ("VD".equals(attValue)) {
                linkedAtt.value = "2022-01-05 14:14:14";
                linkedAtt.text = "2022-01-05 14:14:14";
            } else if ("VDING".equals(attValue)) {
                linkedAtt.value = "2022-01-06 15:15:15";
                linkedAtt.text = "2022-01-06 15:15:15";
            }
            attLinkResult.attMap.put("CALC_DTTM", linkedAtt);
        }
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.BOOLEAN;
            if ("AP".equals(attValue)) {
                linkedAtt.value = "true";
                linkedAtt.text = "true";
            } else if ("APING".equals(attValue)) {
                linkedAtt.value = "false";
                linkedAtt.text = "false";
            } else if ("DN".equals(attValue)) {
                linkedAtt.value = "true";
                linkedAtt.text = "true";
            } else if ("DR".equals(attValue)) {
                linkedAtt.value = "false";
                linkedAtt.text = "false";
            } else if ("VD".equals(attValue)) {
                linkedAtt.value = "true";
                linkedAtt.text = "true";
            } else if ("VDING".equals(attValue)) {
                linkedAtt.value = "false";
                linkedAtt.text = "false";
            }
            attLinkResult.attMap.put("CALC_SUCC", linkedAtt);
        }
        return attLinkResult;
    }

    private AttLinkResult linkForSTATUSofTestClass(String attValue) {
        AttLinkResult attLinkResult = new AttLinkResult();

        boolean ap = "AP".equals(attValue);

        // 附件：
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.FILE_GROUP;
            linkedAtt.value = "99952822476358787,99952822476358788";
//            linkedAtt.text = "/qygly-gateway/qygly-file/viewImage?fileId=99952822476358787,/qygly-gateway/qygly-file/viewImage?fileId=99952822476358788";

            getFileInfoList(linkedAtt);

            attLinkResult.attMap.put("ATT_FILE_GROUP_ID", linkedAtt);
        }
        // 附件：
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.FILE_GROUP;
            linkedAtt.value = "99952822476358787,99952822476358788";
//            linkedAtt.text = "/qygly-gateway/qygly-file/viewImage?fileId=99952822476358787,/qygly-gateway/qygly-file/viewImage?fileId=99952822476358788";

            getFileInfoList(linkedAtt);

            attLinkResult.attMap.put("APPROVE_FILE_ID_TWO", linkedAtt);
        }

        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = "状态" + (ap ? "“已批准”" : "”非已批准“") + "联动后的备注";
            linkedAtt.text = "状态" + (ap ? "“已批准”" : "”非已批准“") + "联动后的备注";

            linkedAtt.changeToName = "状态" + (ap ? "“已批准”" : "”非已批准“") + "联动后的备注";
            linkedAtt.changeToShown = ap;
            linkedAtt.changeToEditable = ap;
            linkedAtt.changeToMandatory = ap;

            attLinkResult.attMap.put("REMARK", linkedAtt);
        }
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_SHORT;
            long l = System.currentTimeMillis();
            linkedAtt.value = "名称" + l;
            linkedAtt.text = "名称" + l;

            attLinkResult.attMap.put("NAME", linkedAtt);
        }
        attLinkResult.childClear.put("99626673179203460", ap);// 测试学生的视图部分ID，不是实体视图ID
        attLinkResult.childClear.put("99902212142028120", !ap);// 测试老师的视图部分ID，不是实体视图ID

        attLinkResult.childCreatable.put("99626673179203460", ap);// 测试学生的视图部分ID，不是实体视图ID
        attLinkResult.childCreatable.put("99902212142028120", !ap);// 测试老师的视图部分ID，不是实体视图ID

        attLinkResult.childData.put("99626673179203460", null);// 测试学生的视图部分ID，不是实体视图ID

        List<LinkedRecord> linkedRecordList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            LinkedRecord linkedRecord = new LinkedRecord();
            linkedRecord.valueMap.put("NAME", "老师" + (i + 1));
            linkedRecord.textMap.put("NAME", "老师" + (i + 1));

            linkedRecordList.add(linkedRecord);
        }
        attLinkResult.childData.put("99902212142028120", linkedRecordList);// 测试老师的视图部分ID，不是实体视图ID

        return attLinkResult;
    }

    /**
     * 为联动的属性获取文件信息列表。
     *
     * @param linkedAtt
     */
    private void getFileInfoList(LinkedAtt linkedAtt) {
        if (SharedUtil.isEmptyObject(linkedAtt.value)) {
            return;
        }

        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        String[] idArr = linkedAtt.value.toString().split(",");
        List<String> idList = Arrays.asList(idArr);
        Map<String, Object> map = new HashMap<>();
        map.put("ids", idList);
        List<Map<String, Object>> list = myNamedParameterJdbcTemplate.queryForList("select t.*, crt_user.code crt_user_code, crt_user.name crt_user_name from fl_file t left join ad_user crt_user on t.crt_user_id = crt_user.id where t.id in (:ids)", map);
        if (!CollectionUtils.isEmpty(list)) {
            linkedAtt.fileInfoList = new ArrayList<>(list.size());
            StringBuilder sb = new StringBuilder();
            for (Map<String, Object> row : list) {
                LinkedAttFileInfo fileInfo = new LinkedAttFileInfo();
                linkedAtt.fileInfoList.add(fileInfo);
                fileInfo.attachmentUrl = JdbcMapUtil.getString(row, "FILE_ATTACHMENT_URL");
                fileInfo.code = JdbcMapUtil.getString(row, "CODE");
                fileInfo.crtUserCode = JdbcMapUtil.getString(row, "CRT_USER_CODE");
                fileInfo.crtUserName = JdbcMapUtil.getString(row, "CRT_USER_NAME");
                fileInfo.dspName = JdbcMapUtil.getString(row, "DSP_NAME");
                fileInfo.dspSize = JdbcMapUtil.getString(row, "DSP_SIZE");
                fileInfo.ext = JdbcMapUtil.getString(row, "EXT");
                fileInfo.id = JdbcMapUtil.getString(row, "ID");
                String url = JdbcMapUtil.getString(row, "FILE_INLINE_URL");
                fileInfo.inlineUrl = url;
                fileInfo.name = JdbcMapUtil.getString(row, "NAME");
                fileInfo.sizeKiloByte = JdbcMapUtil.getDouble(row, "SIZE_KB");
                fileInfo.uploadDttm = DateTimeUtil.dttmToString(JdbcMapUtil.getObject(row, "UPLOAD_DTTM"));
                sb.append(url).append(",");
            }
            linkedAtt.text = sb.substring(0,sb.length()-1);
        }
    }

    // 默认未涉及
    private void initNUll(AttLinkResult attLinkResult) {
        List<String> fields = Arrays.asList("CREATE_PROJECT_COMPLETED", "FEASIBILITY_COMPLETED",
                "SELECT_SITE_COMPLETED", "EIA_COMPLETED", "USE_LAND_COMPLETED", "WOODLAND_WATER_SOIL_COMPLETED",
                "ESTIMATE_COMPLETED", "BUDGET_REVIEW_COMPLETED", "CONSTRUCT_BID_COMPLETED");
        for (String field : fields) {
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = "99902212142036278";
                linkedAtt.text = "未涉及";
//                linkedAtt.text = "99902212142036278";
                attLinkResult.attMap.put(field, linkedAtt);
            }
        }
    }

    // 获取资金信息
    private AttLinkResult getResult(Map<String, Object> stringObjectMap, AttLinkResult attLinkResult) {
//        AttLinkResult attLinkResult = new AttLinkResult();
//        attLinkResult.attMap = new HashMap<>();
        // 总投资
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "PRJ_TOTAL_INVEST")) ? "":JdbcMapUtil.getString(stringObjectMap, "PRJ_TOTAL_INVEST");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "PRJ_TOTAL_INVEST")) ? "":JdbcMapUtil.getString(stringObjectMap, "PRJ_TOTAL_INVEST");

            attLinkResult.attMap.put("PRJ_TOTAL_INVEST", linkedAtt);
        }
        // 工程费用
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "PROJECT_AMT")) ? "":JdbcMapUtil.getString(stringObjectMap, "PROJECT_AMT");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "PROJECT_AMT")) ? "":JdbcMapUtil.getString(stringObjectMap, "PROJECT_AMT");

            attLinkResult.attMap.put("PROJECT_AMT", linkedAtt);
        }
        // 工程建设其他费用
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "PROJECT_OTHER_AMT")) ? "":JdbcMapUtil.getString(stringObjectMap, "PROJECT_OTHER_AMT");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "PROJECT_OTHER_AMT")) ? "":JdbcMapUtil.getString(stringObjectMap, "PROJECT_OTHER_AMT");

            attLinkResult.attMap.put("PROJECT_OTHER_AMT", linkedAtt);
        }
        // 预备费
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "PREPARE_AMT")) ? "":JdbcMapUtil.getString(stringObjectMap, "PREPARE_AMT");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "PREPARE_AMT")) ? "":JdbcMapUtil.getString(stringObjectMap, "PREPARE_AMT");

            attLinkResult.attMap.put("PREPARE_AMT", linkedAtt);
        }
        // 利息
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "CONSTRUCT_PERIOD_INTEREST")) ? "":JdbcMapUtil.getString(stringObjectMap, "CONSTRUCT_PERIOD_INTEREST");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "CONSTRUCT_PERIOD_INTEREST")) ? "":JdbcMapUtil.getString(stringObjectMap, "CONSTRUCT_PERIOD_INTEREST");

            attLinkResult.attMap.put("CONSTRUCT_PERIOD_INTEREST", linkedAtt);
        }
        //批复文号
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "REPLY_NO_WR")) ? "":JdbcMapUtil.getString(stringObjectMap, "REPLY_NO_WR");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "REPLY_NO_WR")) ? "":JdbcMapUtil.getString(stringObjectMap, "REPLY_NO_WR");

            attLinkResult.attMap.put("REPLY_NO", linkedAtt);
            attLinkResult.attMap.put("PRJ_REPLY_NO", linkedAtt);
        }

        return attLinkResult;
    }

    public boolean judgeMatch(List<String> words, String name) {
        for (String word : words) {
            if (name.contains(word)) {
                return true;
            }
        }
        return false;
    }

    // 资金信息回显，按照优先级 可研估算<初设概算<预算财评 的流程
    public List<String> getAmtList() {
        List<String> list = new ArrayList<>();
        list.add("PM_SUPERVISE_PLAN_REQ"); // 监理规划及细则申请
        list.add("PM_PRJ_PARTY_REQ"); // 五方责任主体维护申请
        list.add("PO_ORDER_TERMINATE_REQ"); // 采购合同终止申请
        list.add("PO_ORDER_CHANGE_REQ"); // 采购合同变更申请
        list.add("PO_ORDER_SUPPLEMENT_REQ"); // 采购合同补充协议申请
        list.add("PO_ORDER_REQ"); // 采购合同签订申请
        list.add("PO_PUBLIC_BID_REQ"); // 采购公开招标申请
        list.add("PM_BUILD_ORGAN_PLAN_REQ"); // 施工组织设计及施工方案
        list.add("PM_WORK_LIST_REQ"); // 工作联系单
        list.add("COMPLETION_PRE_ACCEPTANCE"); // 竣工预验收
        list.add("EXPENSE_CLAIM_APPROVAL"); // 费用索赔报审表
        list.add("PROJECT_CLAIM_NOTICE"); // 工程索赔通知书
        list.add("APPROVAL_INSPECTION"); // 报审、报验
        return list;
    }

    // 查询可研估算<初设概算<预算财评优先级最高的数据
    private Map getAmtMap(String attValue) {
        Map resultRow = new HashMap();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql1 = "SELECT REPLY_NO_WR,PRJ_TOTAL_INVEST,PROJECT_AMT,CONSTRUCT_AMT,PROJECT_OTHER_AMT,PREPARE_AMT,CONSTRUCT_PERIOD_INTEREST FROM PM_PRJ_INVEST3 WHERE PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
        List<Map<String, Object>> map = myJdbcTemplate.queryForList(sql1, attValue);
        List<Map<String, Object>> map1 = new ArrayList<>();
        List<Map<String, Object>> map2 = new ArrayList<>();
        if (CollectionUtils.isEmpty(map)) {
            // 初设概算信息
            String sql2 = "SELECT REPLY_NO_WR,PRJ_TOTAL_INVEST,PROJECT_AMT,CONSTRUCT_AMT,PROJECT_OTHER_AMT,PREPARE_AMT,CONSTRUCT_PERIOD_INTEREST FROM PM_PRJ_INVEST2 WHERE PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
            map1 = myJdbcTemplate.queryForList(sql2, attValue);
            if (CollectionUtils.isEmpty(map1)) {
                String sql3 = "SELECT REPLY_NO_WR,PRJ_TOTAL_INVEST,PROJECT_AMT,CONSTRUCT_AMT,PROJECT_OTHER_AMT,PREPARE_AMT,CONSTRUCT_PERIOD_INTEREST FROM PM_PRJ_INVEST1 WHERE PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
                map2 = myJdbcTemplate.queryForList(sql3, attValue);
                if (!CollectionUtils.isEmpty(map2)) {
                    resultRow = map2.get(0);
                }
            } else {
                resultRow = map1.get(0);
            }
        } else {
            resultRow = map.get(0);
        }
        return resultRow;
    }

    // 查询可研估算<初设概算<预算财评优先级最高的数据  查询文号
    private Map getReplyNo(String attValue) {
        Map resultRow = new HashMap();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql1 = "SELECT REPLY_NO_WR FROM PM_PRJ_INVEST3 WHERE PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
        List<Map<String, Object>> map = myJdbcTemplate.queryForList(sql1, attValue);
        List<Map<String, Object>> map1 = new ArrayList<>();
        List<Map<String, Object>> map2 = new ArrayList<>();
        if (CollectionUtils.isEmpty(map)) {
            // 初设概算信息
            String sql2 = "SELECT REPLY_NO_WR FROM PM_PRJ_INVEST2 WHERE PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
            map1 = myJdbcTemplate.queryForList(sql2, attValue);
            if (CollectionUtils.isEmpty(map1)) {
                String sql3 = "SELECT REPLY_NO_WR FROM PM_PRJ_INVEST1 WHERE PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
                map2 = myJdbcTemplate.queryForList(sql3, attValue);
                if (!CollectionUtils.isEmpty(map2)) {
                    resultRow = map2.get(0);
                }
            } else {
                resultRow = map1.get(0);
            }
        } else {
            resultRow = map.get(0);
        }
        return resultRow;
    }


    // 属性联动中需要单独查询额外字典名称的key
    public List<String> getKeyList() {
        List<String> keyList = new ArrayList<>();
        keyList.add("IS_BUDGET_ID");  // 是否市财政预算政府投资计划项目
        keyList.add("FEASIBILITY_COMPLETED");  // 可研完成情况
        keyList.add("CREATE_PROJECT_COMPLETED"); // 立项完成情况
        keyList.add("SELECT_SITE_COMPLETED"); // 规划选址完成情况
        keyList.add("USE_LAND_COMPLETED"); // 用地预审完成情况
        keyList.add("EIA_COMPLETED"); // 环评审批完成情况
        keyList.add("WOODLAND_WATER_SOIL_COMPLETED"); // 林地、水土保持、节能完成情况
        keyList.add("ESTIMATE_COMPLETED"); // 概算完成情况
        keyList.add("BUDGET_REVIEW_COMPLETED"); // 预算评审完成情况
        keyList.add("CONSTRUCT_BID_COMPLETED"); // 施工招标备案完成情况
        return keyList;
    }

    //属性联动中包含类型联动隐藏的list
    public List<String> getBaoHanList() {
        List<String> baoHanList = new ArrayList<>();
        baoHanList.add("PO_ORDER_REQ"); //合同签订
        baoHanList.add("PO_ORDER_TERMINATE_REQ"); //采购合同终止申请
        baoHanList.add("PO_ORDER_CHANGE_REQ"); //采购合同变更申请
        baoHanList.add("PO_ORDER_SUPPLEMENT_REQ"); //采购合同补充协议申请
        return baoHanList;
    }

    //合同关联信息里，保函可改的流程
    public List<String> getEditGuarantee() {
        List<String> editGuarantee = new ArrayList<>();
        editGuarantee.add("PO_ORDER_TERMINATE_REQ"); //采购合同终止申请
        return editGuarantee;
    }

    // 相对方联系人 只读
    public List<String> getContactListRead() {
        List<String> list = new ArrayList<>();
        list.add("99902212142025475"); // 采购合同终止申请--填写项目信息及合同变更信息
        return list;
    }

    // 需要带出相对方联系人明细的实体试图
    public List<String> getContactDetailList() {
        List<String> list = new ArrayList<>();
        list.add("99902212142025475"); // 采购合同终止申请--填写项目信息及合同变更信息
        return list;
    }

    //关联合同，不需要自动带出保函类型的流程
    public List<String> getNotAutoBaoHan() {
        List<String> list = new ArrayList<>();
        list.add("PO_GUARANTEE_LETTER_REQUIRE_REQ"); //新增保函
        return list;
    }
}
