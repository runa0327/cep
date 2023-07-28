package com.cisdi.ext.link;

import com.cisdi.ext.link.linkPackage.*;
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
            return ProjectTypeIdLink.linkForPROJECT_TYPE_ID(myJdbcTemplate, attValue);
        } else if ("PM_PRJ_ID".equals(attCode)) {
            return PmPrjIdLink.linkForPM_PRJ_ID(myJdbcTemplate, attValue, entCode, sevId);
        } else if ("PMS_RELEASE_WAY_ID".equals(attCode) || "GUARANTEE_LETTER_TYPE_ID".equals(attCode) || "CONTRACT_CATEGORY_ID".equals(attCode) || "PRJ_MANAGE_MODE_ID".equals(attCode)) {
            return ForManyLink.linkForMany(myJdbcTemplate, attCode, attValue, entCode);
        } else if ("BIDDING_NAME_ID".equals(attCode)) {
            return BiddingNameLink.linkForBIDDING_NAME_ID(myJdbcTemplate, attValue, sevId);
        } else if ("CONTRACT_ID".equals(attCode)) {
            return ContractLink.linkForCONTRACT_ID(myJdbcTemplate, attValue, sevId,entCode);
        } else if (("RELATION_CONTRACT_ID").equals(attCode) || "PO_ORDER_REQ_ID".equals(attCode)) { //关联合同
            return PoOrderReqLink.linkForRELATION_CONTRACT_ID(myJdbcTemplate, attValue,sevId,entCode);
        } else if ("GUARANTEE_ID".equals(attCode)) {
            return linkForGUARANTEE_ID(myJdbcTemplate, attValue);
        } else if ("AMOUT_PM_PRJ_ID".equals(attCode)) {
            return linkForAMOUT_PM_PRJ_ID(myJdbcTemplate, attValue, entCode);
        } else if ("STATUS".equals(attCode)) {
            return StatusLink.linkForSTATUS(attValue, sevId);
        } else if ("RELATION_AMOUT_PLAN_REQ_ID".equals(attCode)){ //资金需求计划联动属性
            return linkForRELATION_AMOUT_PLAN_REQ_ID(myJdbcTemplate, attValue, entCode);
        } else if ("PAY_BASIS_ID".equals(attCode)){ //付款依据属性联动
            return linkPAY_BASIS_ID(myJdbcTemplate, attValue, entCode,sevId);
        } else if ("YES_NO_ONE".equals(attCode)){ // 是否判断
            return YesNoOneLink.linkYES_NO_ONE(myJdbcTemplate, attValue, entCode);
        } else if ("YES_NO_THREE".equals(attCode)){ // 是否判断
            return YesNoThreeLink.linkYES_NO_THREE(myJdbcTemplate, attValue, entCode,sevId);
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
            return PmBidKeepFileReqLink.linkPM_BID_KEEP_FILE_REQ_ID(myJdbcTemplate, attValue, entCode,sevId,param);
        } else if ("FUND_IMPLEMENTATION_V_ID".equals(attCode)){ // 关联资金来源 属性联动
            return FundImplementationVLink.linkFUND_IMPLEMENTATION_V_ID(myJdbcTemplate, attValue, entCode,sevId,param);
        } else if ("FUND_PAY_CODE_V_ID".equals(attCode)){ // 关联支付明细码 属性联动
            return FundPayCodeVLink.linkFUND_PAY_CODE_V_ID(myJdbcTemplate, attValue, entCode,sevId,param);
        } else if ("PM_BID_APPROVAL_REQ_ID".equals(attCode)){ // 招标文件审批 属性联动
            return PmBidApprovalReqLink.linkPM_BID_APPROVAL_REQ_ID(myJdbcTemplate, attValue, entCode,sevId,param);
        } else if ("CUSTOMER_UNIT_ONE".equals(attCode) || "CUSTOMER_UNIT".equals(attCode)){ // 签订公司 属性联动
            return CustomerUnitLink.linkCUSTOMER_UNIT_ONE(myJdbcTemplate, attValue, entCode);
        } else if ("PROJECT_SOURCE_TYPE_ID".equals(attCode)){ // 项目来源 属性联动
            return ProjectSourceTypeLink.linkPROJECT_SOURCE_TYPE_ID(myJdbcTemplate, attValue, entCode,sevId,param);
        } else if ("PROJECT_SOURCE_TYPE_TWO_ID".equals(attCode)){
            return ProjectSourceTypeLink.linkPROJECT_SOURCE_TYPE_TWO_ID(myJdbcTemplate, attValue, entCode,sevId,param);
        } else if ("ORDER_DEMAND_TYPE".equals(attCode)){ //合同需求类型
            return OrderDemandTypeLink.linkORDER_DEMAND_TYPE(myJdbcTemplate,attValue,entCode);
        } else if ("BUILD_PERMIT_TYPE_ID".equals(attCode)){ //施工许可类型
            return BuildPermitTypeLink.linkBUILD_PERMIT_TYPE_ID(myJdbcTemplate,attValue,entCode);
        } else if ("GUARANTEE_COST_TYPE_ID".equals(attCode)){ //保函-费用类型
            return GuaranteeCostTypeLink.linkGUARANTEE_COST_TYPE_ID(myJdbcTemplate,attValue,entCode);
        } else if ("GUARANTEE_DATE_TYPE_ID".equals(attCode)){ //保函-到期类型
            return GuaranteeDateTypeLink.linkGUARANTEE_DATE_TYPE_ID(myJdbcTemplate,attValue,entCode);
        } else if ("PO_ORDER_REQ_IDS".equals(attCode)){ //合同选择，多选
            return PoOrderReqLink.linkPO_ORDER_REQ_IDS(myJdbcTemplate, attValue, sevId,entCode);
        } else if ("PM_PRO_PLAN_NODE_ID".equals(attCode)){
            return PmProPlanNodeLink.linkForPM_PRO_PLAN_NODE_ID(myJdbcTemplate, attValue, entCode);
        } else if ("PM_EXP_TYPE_IDS".equals(attCode)){ // 多选-费用类型
            return PmExpTypeLink.linkForPmExpType(myJdbcTemplate, attValue, entCode);
        }
        else {
            throw new BaseException("属性联动的参数的attCode为" + attCode + "，不支持！");
        }
    }

    //付款类型属性联动
    private AttLinkResult linkPAY_TYPE_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId,AttLinkParam param) {
        AttLinkResult attLinkResult = new AttLinkResult();
        String code = getGrSetCode(myJdbcTemplate,attValue);
        //预付款(advance_charge),进度款(progress_payment)
        if ("PO_ORDER_PAYMENT_REQ".equals(entCode)){
            if ("advance_charge".equals(code)){
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
        String code = getGrSetCode(myJdbcTemplate,attValue);
        if ("PM_USE_CHAPTER_REQ".equals(entCode)){//中选单位及标后用印审批
            // 关联流程(relation_process),  上传依据(upload_basis)
            if ("relation_process".equals(code)){
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
            if ("upload_basis".equals(code)){
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
        String code = getGrSetCode(myJdbcTemplate,attValue);
        if ("PM_BUY_DEMAND_REQ".equals(entCode)){ //采购需求审批
            //会议纪要(meeting_minutes)，其他(other),启动函(start_up_letter)
            boolean changeToEditable = false; //是否可改
            boolean changeToMandatory = false; //是否必填
            String value = "";
            String text = "";
            String projectId = JdbcMapUtil.getString(param.valueMap, "PM_PRJ_IDS");
            if (projectId.contains(",")){
                return attLinkResult;
            }
            if ("meeting_minutes".equals(code) || "other".equals(code) || "start_up_letter".equals(code)){
                changeToEditable = true;
                changeToMandatory = false;
                value = null;
                text = null;
                //采购启动依据文件
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.FILE_GROUP;
                    linkedAtt.value = "";
                    linkedAtt.text = "";
                    linkedAtt.changeToMandatory = false;
                    linkedAtt.changeToEditable = true;
                    linkedAtt.fileInfoList = null;
                    attLinkResult.attMap.put("FILE_ID_THREE", linkedAtt);
                }
            } else {
                // 立项(project_initiation)，可研(feasibility_study)，初概(preliminary_outline),预算财评(budget_financial_evaluation)
                String sql = "";
                String fileValue = "";
                if ("project_initiation".equals(code)){
                    sql = "select PRJ_REPLY_NO as REPLY_NO_WR,REPLY_FILE as file from pm_prj_req WHERE pm_prj_id = ? and `STATUS` = 'ap' order by CRT_DT desc limit 1";
                } else if ("feasibility_study".equals(code)){
                    sql = "select REPLY_NO_WR,REPLY_FILE as file from PM_PRJ_INVEST1 WHERE pm_prj_id = ? and status = 'ap' order by CRT_DT desc limit 1";
                } else if ("preliminary_outline".equals(code)){
                    sql = "select REPLY_NO_WR,REPLY_FILE as file from PM_PRJ_INVEST2 WHERE pm_prj_id = ? and status = 'ap' order by CRT_DT desc limit 1";
                } else if ("budget_financial_evaluation".equals(code)){
                    sql = "select REPLY_NO_WR,FINANCIAL_REVIEW_FILE as file from PM_PRJ_INVEST3 WHERE pm_prj_id = ? and status = 'ap' order by CRT_DT desc limit 1";
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
        String code = getGrSetCode(myJdbcTemplate,attValue);
        // 公开招标(open_bidding),竞争性磋商(competitive_consultations),竞争性谈判(competitive_negotiation)
        if ("PM_BID_APPROVAL_REQ".equals(entCode)){ //招标文件审批
            boolean changeToMandatory = false; //必填
            if ("open_bidding".equals(code) || "competitive_consultations".equals(code) || "competitive_negotiation".equals(code)){
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
        String code = getGrSetCode(myJdbcTemplate,attValue);
        // 到期申请退还，之后续保(expire_return),达到退保条件，申请退保(surrender_return),其他(other)
        boolean changeToMandatory = true;
        boolean changeToEditable = true;
        if ("expire_return".equals(code)){
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
        String code = getGrSetCode(myJdbcTemplate,attValue);
        // 是(yes) ，否(no)
        boolean changeToShown = true;
        boolean changeToMandatory = true;
        boolean changeToEditable = true;

        List<String> baohanList = getBaoHanList();
        if (baohanList.contains(entCode)){
            if ("no".equals(code)){ //隐藏保函类型
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
        String code = getGrSetCode(myJdbcTemplate,attValue);
        if ("PM_FARMING_PROCEDURES".equals(entCode)){ // 农转用手续办理
            // 有(have)， 没有(none)
            if ("none".equals(attValue)){
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

    //付款依据属性联动
    private AttLinkResult linkPAY_BASIS_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode,String sevId) {
        AttLinkResult attLinkResult = new AttLinkResult();
        String code = getGrSetCode(myJdbcTemplate,attValue);
        if ("PO_ORDER_PAYMENT_REQ".equals(entCode)) { //付款申请
            // 其他付款(other_payment) 合同付款(contract_payment)
            boolean fileMustChangeToMandatory = false; // 附件默认非必填
            boolean contractChangeToMandatory = false; //关联合同默认非必填
            boolean fileChangeToShown = true; //附件默认显示
            boolean contractChangeToShown = true; //关联合同默认显示
            boolean contractAmtChangeToShown = true; //合同金额默认显示
            if ("other_payment".equals(code)){ //必填附件
                fileMustChangeToMandatory = true;
                contractChangeToShown = false;
                contractAmtChangeToShown = false;
            } else {
                fileChangeToShown = false;
                contractChangeToMandatory = true;
            }
            //附件
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = "";
                linkedAtt.text = "";
                linkedAtt.changeToMandatory = fileMustChangeToMandatory;
                linkedAtt.changeToShown = fileChangeToShown;
                attLinkResult.attMap.put("ATT_FILE_GROUP_ID", linkedAtt);
            }
            //关联合同
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = "";
                linkedAtt.text = "";
                linkedAtt.changeToMandatory = contractChangeToMandatory;
                linkedAtt.changeToShown = contractChangeToShown;
                attLinkResult.attMap.put("CONTRACT_ID", linkedAtt);
            }
            //合同金额
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = "";
                linkedAtt.text = "";
                linkedAtt.changeToShown = contractAmtChangeToShown;
                attLinkResult.attMap.put("CONTRACT_PRICE", linkedAtt);
            }
            // 明细信息
            if ("0099902212142033284".equals(sevId)) { //采购合同付款申请-发起（实体视图）
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = "";
                    linkedAtt.text = "";
                    attLinkResult.attMap.put("CONTRACT_ID", linkedAtt);
                }
                //清除明细表信息
                attLinkResult.childClear.put("0099902212142514118", true);  //付款申请明细-发起
                attLinkResult.childCreatable.put("0099902212142514118", true); //付款申请明细-发起
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
                Map<String,Object> row2 = list2.get(0);
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

    private AttLinkResult linkForAMOUT_PM_PRJ_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode) {
        AttLinkResult attLinkResult = new AttLinkResult();

        // 资金需求项目名称(AMOUT_PM_PRJ_ID),引用（单值）
        // 项目基础信息
        String sql0 = "select t.PRJ_CODE as prj_code,t.code code,c.id customer_id,c.name customer_name,m.id m_id,m.name m_name," +
                "l.id l_id,l.name l_name,t.FLOOR_AREA,pt.id pt_id,pt.name pt_name,st.id st_id,st.name st_name," +
                "su.id su_id,su.name su_name,t.CON_SCALE_QTY,t.CON_SCALE_QTY2,t.PRJ_SITUATION, t.BUILD_YEARS," +
                "t.PRJ_REPLY_NO, t.PRJ_REPLY_DATE, t.PRJ_REPLY_FILE, t.INVESTMENT_SOURCE_ID,t.BUILDING_AREA, " +
                "(SELECT PRJ_TOTAL_INVEST from PM_PRJ_INVEST1 WHERE PM_PRJ_ID = t.id order by CRT_DT desc limit 1) as 'FS'," +
                "(SELECT PRJ_TOTAL_INVEST from PM_PRJ_INVEST2 WHERE PM_PRJ_ID = t.id order by CRT_DT desc limit 1) as 'PD'," +
                "(SELECT PRJ_TOTAL_INVEST from PM_PRJ_INVEST3 WHERE PM_PRJ_ID = t.id order by CRT_DT desc limit 1) as 'budget'," +
                "t.QTY_ONE,t.QTY_TWO,t.QTY_THREE " +
                "from pm_prj t join PM_PARTY c on t.CUSTOMER_UNIT=c.id " +
                "LEFT JOIN gr_set_value m on t.PRJ_MANAGE_MODE_ID = m.ID " +
                "LEFT JOIN gr_set_value l on t.BASE_LOCATION_ID=l.id " +
                "LEFT JOIN gr_set_value pt on t.PROJECT_TYPE_ID=pt.id " +
                "LEFT JOIN gr_set_value st on t.CON_SCALE_TYPE_ID=st.id " +
                "LEFT JOIN gr_set_value su on t.CON_SCALE_UOM_ID=su.id where t.id=? ";
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql0, attValue);
        if (CollectionUtils.isEmpty(list)) {
//            throw new BaseException("项目的相关属性不完整！");
            return attLinkResult;
        }
        Map<String,Object> row = list.get(0);

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
                Map<String,Object> row2 = map1.get(0);
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
                            if (!CollectionUtils.isEmpty(nameMap)){
                                name = JdbcMapUtil.getString(nameMap.get(0), "name");
                            }
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
                        // 立项完成情况
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("CREATE_PROJECT_COMPLETED", linkedAtt);
                        }
                    } else if (judgeMatch(feasibility, name)) {
                        // 可研完成情况
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("FEASIBILITY_COMPLETED", linkedAtt);
                        }
                    } else if (judgeMatch(landUsePlan, name)) {
                        // 规划选址完成情况
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("SELECT_SITE_COMPLETED", linkedAtt);
                        }
                    } else if (judgeMatch(eia, name)) {
                        // 环评完成情况
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("EIA_COMPLETED", linkedAtt);
                        }
                    } else if (judgeMatch(advanceExam, name)) {
                        // 用地预审
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("USE_LAND_COMPLETED", linkedAtt);
                        }
                    } else if (judgeMatch(save, name)) {
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
            String sql3 = "SELECT GROUP_CONCAT(BID_WIN_NOTICE_FILE_GROUP_ID) AS FILE from PO_PUBLIC_BID_REQ WHERE PM_PRJ_ID = ? AND PMS_RELEASE_WAY_ID in ('0099799190825080728','0099799190825080731') and status = 'AP'";
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
        Map<String,Object> row = list.get(0);

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
            attLinkResult.attMap.put("GUARANTEE_FILE", linkedAtt);
        }


        return attLinkResult;
    }

    // 单表查询用户名称
    private String getUser(String users) {
        String userName = "";
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select group_concat(name) as name from ad_user where id in ('"+users+"') order by id asc";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
        if (!CollectionUtils.isEmpty(list)){
            userName = JdbcMapUtil.getString(list.get(0),"name");
        }
        return userName;
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
                fileInfo.sizeKiloByte = Double.parseDouble(JdbcMapUtil.getString(row, "SIZE_KB"));
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
                linkedAtt.value = "0099902212142036278";
                linkedAtt.text = "未涉及";
//                linkedAtt.text = "0099902212142036278";
                attLinkResult.attMap.put(field, linkedAtt);
            }
        }
    }

    // 获取资金信息
    private AttLinkResult getResult(Map<String, Object> stringObjectMap, AttLinkResult attLinkResult) {
        // 总投资
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "PRJ_TOTAL_INVEST")) ? null:new BigDecimal(JdbcMapUtil.getString(stringObjectMap, "PRJ_TOTAL_INVEST"));
//            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "PRJ_TOTAL_INVEST")) ? null: String.valueOf(new BigDecimal(JdbcMapUtil.getString(stringObjectMap, "PRJ_TOTAL_INVEST")));

            attLinkResult.attMap.put("PRJ_TOTAL_INVEST", linkedAtt);
        }
        // 工程费用
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "PROJECT_AMT")) ? null:new BigDecimal(JdbcMapUtil.getString(stringObjectMap, "PROJECT_AMT"));
//            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "PROJECT_AMT")) ? null:JdbcMapUtil.getString(stringObjectMap, "PROJECT_AMT");

            attLinkResult.attMap.put("PROJECT_AMT", linkedAtt);
        }
        // 工程建设其他费用
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "PROJECT_OTHER_AMT")) ? null:new BigDecimal(JdbcMapUtil.getString(stringObjectMap, "PROJECT_OTHER_AMT"));
//            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "PROJECT_OTHER_AMT")) ? null:JdbcMapUtil.getString(stringObjectMap, "PROJECT_OTHER_AMT");

            attLinkResult.attMap.put("PROJECT_OTHER_AMT", linkedAtt);
        }
        // 预备费
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "PREPARE_AMT")) ? null:new BigDecimal(JdbcMapUtil.getString(stringObjectMap, "PREPARE_AMT"));
//            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "PREPARE_AMT")) ? null:JdbcMapUtil.getString(stringObjectMap, "PREPARE_AMT");

            attLinkResult.attMap.put("PREPARE_AMT", linkedAtt);
        }
        // 利息
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "CONSTRUCT_PERIOD_INTEREST")) ? null:new BigDecimal(JdbcMapUtil.getString(stringObjectMap, "CONSTRUCT_PERIOD_INTEREST"));
//            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "CONSTRUCT_PERIOD_INTEREST")) ? null:JdbcMapUtil.getString(stringObjectMap, "CONSTRUCT_PERIOD_INTEREST");

            attLinkResult.attMap.put("CONSTRUCT_PERIOD_INTEREST", linkedAtt);
        }
        //批复文号
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "REPLY_NO_WR")) ? null:JdbcMapUtil.getString(stringObjectMap, "REPLY_NO_WR");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(stringObjectMap, "REPLY_NO_WR")) ? null:JdbcMapUtil.getString(stringObjectMap, "REPLY_NO_WR");

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

    // 查询可研估算<初设概算<预算财评优先级最高的数据
    private Map getAmtMap(String attValue) {
        Map resultRow = new HashMap();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql1 = "SELECT REPLY_NO_WR,PRJ_TOTAL_INVEST ,PROJECT_AMT, CONSTRUCT_AMT," +
                " PROJECT_OTHER_AMT, PREPARE_AMT, CONSTRUCT_PERIOD_INTEREST FROM PM_PRJ_INVEST3 " +
                "WHERE PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
        List<Map<String, Object>> map = myJdbcTemplate.queryForList(sql1, attValue);
        List<Map<String, Object>> map1 = new ArrayList<>();
        List<Map<String, Object>> map2 = new ArrayList<>();
        if (CollectionUtils.isEmpty(map)) {
            // 初设概算信息
            String sql2 = "SELECT REPLY_NO_WR, PRJ_TOTAL_INVEST, PROJECT_AMT," +
                    " CONSTRUCT_AMT, PROJECT_OTHER_AMT, PREPARE_AMT," +
                    " CONSTRUCT_PERIOD_INTEREST FROM PM_PRJ_INVEST2 WHERE PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
            map1 = myJdbcTemplate.queryForList(sql2, attValue);
            if (CollectionUtils.isEmpty(map1)) {
                String sql3 = "SELECT REPLY_NO_WR, PRJ_TOTAL_INVEST, PROJECT_AMT," +
                        " CONSTRUCT_AMT, PROJECT_OTHER_AMT, PREPARE_AMT," +
                        " CONSTRUCT_PERIOD_INTEREST FROM PM_PRJ_INVEST1 WHERE PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
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

    // 根据属性联动值查询集合值编码
    private String getGrSetCode(MyJdbcTemplate myJdbcTemplate, String attValue) {
        String code = "";
        String sql = "select code from gr_set_value  WHERE id = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,attValue);
        if (!CollectionUtils.isEmpty(list)){
            code = JdbcMapUtil.getString(list.get(0),"code");
        } else {
            throw new BaseException("该选项未匹配到对应编码，请联系管理员处理！");
        }
        return code;
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

}
