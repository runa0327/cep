package com.cisdi.ext.link;

import com.cisdi.ext.link.linkPackage.*;
import com.cisdi.ext.util.ConvertUtils;
import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
            return ProjectTypeIdLink.linkForPROJECT_TYPE_ID(myJdbcTemplate, attValue,entCode);
        } else if ("PM_PRJ_ID".equals(attCode)) { // 项目单选
            return PmPrjIdLink.linkForPM_PRJ_ID(myJdbcTemplate, attValue, entCode, sevId);
        } else if ("PM_PRJ_IDS".equals(attCode)){ // 项目多选
            return PmPrjIdLink.linkForPM_PRJ_IDS(myJdbcTemplate, attValue, entCode, sevId);
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
            return PmPrjIdLink.linkForAMOUT_PM_PRJ_ID(myJdbcTemplate, attValue, entCode);
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
            return BuyTypeLink.linkBUY_TYPE_ID(myJdbcTemplate, attValue, entCode,sevId,param);
        } else if ("BUY_START_BASIS_ID".equals(attCode)){ // 采购启动依据属性联动
            return linkBUY_START_BASIS_ID(myJdbcTemplate, attValue, entCode,sevId,param);
        } else if ("PM_USE_CHAPTER_REQ_ID".equals(attCode)){ // 中标单位及标后用章属性联动
            return linkPM_USE_CHAPTER_REQ_ID(myJdbcTemplate, attValue, entCode,sevId,param);
        } else if ("TYPE_ONE_ID".equals(attCode)){ // 关联流程或上传依据 属性联动
            return linkTYPE_ONE_ID(myJdbcTemplate, attValue, entCode,sevId);
        } else if ("PM_BUY_DEMAND_REQ_ID".equals(attCode)){ // 关联采购需求审批表 属性联动
            return PmBuyDemandReqLink.linkForPM_BUY_DEMAND_REQ_ID(myJdbcTemplate, attValue, entCode);
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
        } else if ("BUILD_PERMIT_TYPE_ID".equals(attCode)){ //施工许可类型（单选）
            return BuildPermitTypeLink.linkBUILD_PERMIT_TYPE_ID(myJdbcTemplate,attValue,entCode);
        } else if ("BUILD_PERMIT_TYPE_IDS".equals(attCode)){ //施工许可类型（多选）
            return BuildPermitTypeLink.linkBUILD_PERMIT_TYPE_IDS(attValue);
        } else if ("GUARANTEE_COST_TYPE_ID".equals(attCode)){ //保函-费用类型
            return GuaranteeCostTypeLink.linkGUARANTEE_COST_TYPE_ID(myJdbcTemplate,attValue,entCode);
        } else if ("GUARANTEE_DATE_TYPE_ID".equals(attCode)){ //保函-到期类型
            return GuaranteeDateTypeLink.linkGUARANTEE_DATE_TYPE_ID(myJdbcTemplate,attValue,entCode);
        } else if ("PO_ORDER_REQ_IDS".equals(attCode)){ //合同选择，多选
            return PoOrderReqLink.linkPO_ORDER_REQ_IDS(myJdbcTemplate, attValue, sevId,entCode);
        } else if ("PM_PRO_PLAN_NODE_ID".equals(attCode)){
            return PmProPlanNodeLink.linkForPM_PRO_PLAN_NODE_ID(myJdbcTemplate, attValue, entCode);
        } else if ("PM_EXP_TYPE_IDS".equals(attCode)){ // 多选-费用类型
            return PmExpTypeLink.linkForPmExpType(myJdbcTemplate, attValue, entCode,sevId,param);
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
            if (!StringUtils.hasText(projectId)){
                throw new BaseException("项目信息不能为空，请先选择项目名称！");
            }
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
            LinkUtils.mapAddAllValue("REPLY_NO_WR",AttDataTypeE.TEXT_LONG,value,text,true,changeToMandatory,changeToEditable,attLinkResult);
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
