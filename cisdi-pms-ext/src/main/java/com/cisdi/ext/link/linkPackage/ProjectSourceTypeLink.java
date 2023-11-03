package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.base.GrSetValueExt;
import com.cisdi.ext.link.AttLinkParam;
import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkedAtt;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 项目来源-属性联动-系统/非系统
 */
public class ProjectSourceTypeLink {

    /**
     * 数据来源属性联动-入口-新增保函(项目)
     * @param myJdbcTemplate 数据源
     * @param attValue 属性联动值
     * @param entCode 表名
     * @param sevId 视图id
     * @param param 参数
     * @return 属性联动结果
     */
    public static AttLinkResult linkPROJECT_SOURCE_TYPE_TWO_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId, AttLinkParam param) {
        AttLinkResult attLinkResult = new AttLinkResult();
        String code = GrSetValueExt.getGrSetCode(attValue);

        boolean PROJECT_NAME_WRChangeToShown = false; //手填项目名称默认不隐藏
        boolean PM_PRJ_IDSChangeToShown = false; //选择项目名称默认不隐藏

        boolean CPROJECT_NAME_WRChangeToMandatory = false; //手填项目名称默认非必填
        boolean PM_PRJ_IDSChangeToMandatory = false; //选择项目名称默认非必填

        boolean PROJECT_NAME_WRChangeToEditable = false; //手填项目名称默认不可改
        boolean PM_PRJ_IDSChangeToEditable = false; //选择项目名称默认不可改

        if ("PO_GUARANTEE_LETTER_REQUIRE_REQ".equals(entCode)){ // 新增保函
            String contractSourceValue = null;
            String contractSourceText = null;
            boolean contractSourceChangeToEditable = true;

            if ("non_system".equals(code)){ // 非系统
                PROJECT_NAME_WRChangeToShown = true;
                CPROJECT_NAME_WRChangeToMandatory = true;
                PROJECT_NAME_WRChangeToEditable = true;
                attLinkResult = linkPROJECT_SOURCE_TYPE_ID(myJdbcTemplate,attValue,entCode,sevId,param);
                List<Map<String,Object>> list1 = myJdbcTemplate.queryForList("select id,name from gr_set_value where id = ?",attValue);
                if (!CollectionUtils.isEmpty(list1)) {
                    contractSourceValue = JdbcMapUtil.getString(list1.get(0),"id");
                    contractSourceText = JdbcMapUtil.getString(list1.get(0),"name");
                }
                contractSourceChangeToEditable = false;
            } else {
                PM_PRJ_IDSChangeToShown = true;
                PM_PRJ_IDSChangeToMandatory = true;
                PM_PRJ_IDSChangeToEditable = true;
            }
            //项目名称
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToMandatory = CPROJECT_NAME_WRChangeToMandatory;
                linkedAtt.changeToShown = PROJECT_NAME_WRChangeToShown;
                linkedAtt.changeToEditable = PROJECT_NAME_WRChangeToEditable;
                attLinkResult.attMap.put("PROJECT_NAME_WR", linkedAtt);
            }
            //项目名称
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToMandatory = PM_PRJ_IDSChangeToMandatory;
                linkedAtt.changeToShown = PM_PRJ_IDSChangeToShown;
                linkedAtt.changeToEditable = PM_PRJ_IDSChangeToEditable;
                attLinkResult.attMap.put("PM_PRJ_IDS", linkedAtt);
            }
            //合同来源
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = contractSourceValue;
                linkedAtt.text = contractSourceText;
                linkedAtt.changeToEditable = contractSourceChangeToEditable;
                attLinkResult.attMap.put("PROJECT_SOURCE_TYPE_ID", linkedAtt);
            }

            return attLinkResult;
        } else {
            return null;
        }

    }

    /**
     * 数据来源属性联动-入口-新增保函(合同)
     * @param myJdbcTemplate 数据源
     * @param attValue 属性联动值
     * @param entCode 表名
     * @param sevId 视图id
     * @param param 参数
     * @return 属性联动结果
     */
    public static AttLinkResult linkPROJECT_SOURCE_TYPE_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId, AttLinkParam param) {
        AttLinkResult attLinkResult = new AttLinkResult();

        //数据来源属性联动清空
        AttLinkExtDetail.clearDataSourceType(attLinkResult);

        String code = GrSetValueExt.getGrSetCode(attValue);
        List<String> entityCodes = AttLinkDifferentProcess.getWRProject();

        //系统(system)，非系统(non_system)
        if ("PM_BUY_DEMAND_REQ".equals(entCode)) { //采购需求审批
            pmBuyDemandReqProjectSourceType(attLinkResult,code);
            return attLinkResult;
        } else if ("PO_GUARANTEE_LETTER_REQUIRE_REQ".equals(entCode)){ //新增保函申请
            //系统(system)，非系统(non_system)
            boolean CONTRACT_NAMEChangeToShown = false; //手填合同名称默认不隐藏
            boolean CONTRACT_IDChangeToShown = false; //选择合同名称默认不隐藏
            boolean PO_ORDER_REQ_IDSChangeToShown = false; //选择合同名称默认不隐藏

            boolean CONTRACT_NAMEChangeToMandatory = false; //手填合同名称默认非必填
            boolean CONTRACT_IDChangeToMandatory = false; //选择合同名称默认非必填
            boolean PO_ORDER_REQ_IDSChangeToMandatory = false; //选择合同名称默认非必填

            boolean CONTRACT_NAMEChangeToEditable = false; //手填合同名称默认不可改
            boolean CONTRACT_IDChangeToEditable = false; //选择合同名称默认不可改
            boolean PO_ORDER_REQ_IDSChangeToEditable = false; //选择合同名称默认不可改

            boolean CONTRACT_AMOUNTChangeToEditable = false; //合同金额默认不可改
            boolean CONTRACT_AMOUNTChangeToMandatory = false; //合同金额默认非必填

            if ("non_system".equals(code)){
                CONTRACT_NAMEChangeToShown = true;
                CONTRACT_NAMEChangeToMandatory = true;
                CONTRACT_NAMEChangeToEditable = true;
                CONTRACT_AMOUNTChangeToEditable = true;
                CONTRACT_AMOUNTChangeToMandatory = true;
            } else {
                CONTRACT_IDChangeToShown = true;
                PO_ORDER_REQ_IDSChangeToShown = true;
                CONTRACT_IDChangeToMandatory = true;
                PO_ORDER_REQ_IDSChangeToMandatory = true;
                CONTRACT_IDChangeToEditable = true;
                PO_ORDER_REQ_IDSChangeToEditable = true;
            }
            //合同名称
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToMandatory = CONTRACT_NAMEChangeToMandatory;
                linkedAtt.changeToShown = CONTRACT_NAMEChangeToShown;
                linkedAtt.changeToEditable = CONTRACT_NAMEChangeToEditable;
                attLinkResult.attMap.put("CONTRACT_NAME", linkedAtt);
            }
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToMandatory = CONTRACT_IDChangeToMandatory;
                linkedAtt.changeToShown = false;
                linkedAtt.changeToEditable = CONTRACT_IDChangeToEditable;
                attLinkResult.attMap.put("CONTRACT_ID", linkedAtt);
            }
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToMandatory = PO_ORDER_REQ_IDSChangeToMandatory;
                linkedAtt.changeToShown = PO_ORDER_REQ_IDSChangeToShown;
                linkedAtt.changeToEditable = PO_ORDER_REQ_IDSChangeToEditable;
                attLinkResult.attMap.put("PO_ORDER_REQ_IDS", linkedAtt);
            }
            //合同金额
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = null;
                linkedAtt.text = null;
                linkedAtt.changeToMandatory = true;
                linkedAtt.changeToEditable = true;
                attLinkResult.attMap.put("CONTRACT_AMOUNT", linkedAtt);
            }
            return attLinkResult;
        }
        else if ("PO_ORDER_CHANGE_REQ".equals(entCode)){ //合同需求变更
            AttLinkExtDetail.autoLinkProject(attLinkResult,code,entCode);
            return attLinkResult;
        }
        else if ("PO_ORDER_REQ".equals(entCode) || "PO_ORDER_SUPPLEMENT_REQ".equals(entCode) || "PO_ORDER_TERMINATE_REQ".equals(entCode)){ //采购合同签订申请 补充协议 合同终止
            //系统(system)，非系统(non_system)
            attLinkResult = autoLinkProject(attValue,code);
            attLinkResult = autoLinkPrjDetail(attLinkResult,attValue,code);
            return attLinkResult;
        } else if (entityCodes.contains(entCode)){
            attLinkResult = autoLinkProject(attValue,code);
            return attLinkResult;
        } else {
            return attLinkResult;
        }
    }

    /**
     * 项目来源属性联动 控制项目基础信息显示
     * @param attLinkResult
     * @param attValue
     * @param code
     * @return
     */
    private static AttLinkResult autoLinkPrjDetail(AttLinkResult attLinkResult, String attValue, String code) {
        //系统(system)，非系统(non_system)
        boolean REPLY_NOChangeToEditable = false; //项目批复文号，默认不可改
        boolean PROJECT_TYPE_IDChangeToEditable = false; //项目类型，默认不可改
        boolean PRJ_SITUATIONChangeToEditable = false; //项目介绍，默认不可改
        boolean INVESTMENT_SOURCE_IDChangeToEditable = false; //投资来源，默认不可改
        boolean CUSTOMER_UNITChangeToEditable = false; //业主单位，默认不可改
        boolean PRJ_TOTAL_INVESTChangeToEditable = false; //总投资，默认不可改
        boolean PROJECT_AMTChangeToEditable = false; //工程费用，默认不可改
        boolean PROJECT_OTHER_AMTChangeToEditable = false; //工程建设其他费用，默认不可改
        boolean PREPARE_AMTChangeToEditable = false; //预备费，默认不可改
        boolean CONSTRUCT_PERIOD_INTERESTChangeToEditable = false; //建设期利息，默认不可改
        if ("non_system".equals(code)){
            REPLY_NOChangeToEditable = true;
            PROJECT_TYPE_IDChangeToEditable = true;
            PRJ_SITUATIONChangeToEditable = true;
            INVESTMENT_SOURCE_IDChangeToEditable = true;
            CUSTOMER_UNITChangeToEditable = true;
            PRJ_TOTAL_INVESTChangeToEditable = true;
            PROJECT_AMTChangeToEditable = true;
            PROJECT_OTHER_AMTChangeToEditable = true;
            PREPARE_AMTChangeToEditable = true;
            CONSTRUCT_PERIOD_INTERESTChangeToEditable = true;
        }
        //项目批复文号
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            linkedAtt.changeToEditable = REPLY_NOChangeToEditable;
            attLinkResult.attMap.put("REPLY_NO", linkedAtt);
            attLinkResult.attMap.put("PRJ_REPLY_NO", linkedAtt);
        }
        //项目类型
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            linkedAtt.changeToEditable = PROJECT_TYPE_IDChangeToEditable;
            attLinkResult.attMap.put("PROJECT_TYPE_ID", linkedAtt);
        }
        //项目概况
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            linkedAtt.changeToEditable = PRJ_SITUATIONChangeToEditable;
            attLinkResult.attMap.put("PRJ_SITUATION", linkedAtt);
        }
        //投资来源
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            linkedAtt.changeToEditable = INVESTMENT_SOURCE_IDChangeToEditable;
            attLinkResult.attMap.put("INVESTMENT_SOURCE_ID", linkedAtt);
        }
        //业主单位
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            linkedAtt.changeToEditable = CUSTOMER_UNITChangeToEditable;
            attLinkResult.attMap.put("CUSTOMER_UNIT", linkedAtt);
        }
        //总投资
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            linkedAtt.changeToEditable = PRJ_TOTAL_INVESTChangeToEditable;
            attLinkResult.attMap.put("PRJ_TOTAL_INVEST", linkedAtt);
        }
        //工程费用
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            linkedAtt.changeToEditable = PROJECT_AMTChangeToEditable;
            attLinkResult.attMap.put("PROJECT_AMT", linkedAtt);
        }
        //工程建设其他费用
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            linkedAtt.changeToEditable = PROJECT_OTHER_AMTChangeToEditable;
            attLinkResult.attMap.put("PROJECT_OTHER_AMT", linkedAtt);
        }
        //预备费
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            linkedAtt.changeToEditable = PREPARE_AMTChangeToEditable;
            attLinkResult.attMap.put("PREPARE_AMT", linkedAtt);
        }
        //建设期利息
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            linkedAtt.changeToEditable = CONSTRUCT_PERIOD_INTERESTChangeToEditable;
            attLinkResult.attMap.put("CONSTRUCT_PERIOD_INTEREST", linkedAtt);
        }
        return attLinkResult;
    }

    /**
     * 项目来源属性联动选择判断
     * @param attValue
     * @param code
     * @return
     */
    private static AttLinkResult autoLinkProject(String attValue, String code) {
        //系统(system)，非系统(non_system)
        AttLinkResult attLinkResult = new AttLinkResult();
        boolean prjListChangeToShown = false; //下拉项目默认隐藏
        boolean prjNameChangeToShown = false; //手写项目默认隐藏

        boolean prjListChangeToMandatory = false; //下拉项目默认非必填
        boolean prjNameChangeToMandatory = false; //手写项目默认非必填

        boolean prjListChangeToEditable = false; //下拉项目默认不可改
        boolean prjNameChangeToEditable = false; //手写项目默认不可改

        if (!"system".equals(code)) {
            prjNameChangeToShown = true;
            prjNameChangeToMandatory = true;
            prjNameChangeToEditable = true;
        } else {
            prjListChangeToShown = true;
            prjListChangeToMandatory = true;
            prjListChangeToEditable = true;
        }
        //下拉项目列表
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            linkedAtt.changeToMandatory = prjListChangeToMandatory;
            linkedAtt.changeToShown = prjListChangeToShown;
            linkedAtt.changeToEditable = prjListChangeToEditable;
            attLinkResult.attMap.put("PM_PRJ_ID", linkedAtt);
        }
        //项目名称
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            linkedAtt.changeToMandatory = prjNameChangeToMandatory;
            linkedAtt.changeToShown = prjNameChangeToShown;
            linkedAtt.changeToEditable = prjNameChangeToEditable;
            attLinkResult.attMap.put("PROJECT_NAME_WR", linkedAtt);
        }
        return attLinkResult;
    }

    /**
     * 项目来源属性联动-采购需求审批
     * @param attLinkResult
     * @param code
     */
    private static void pmBuyDemandReqProjectSourceType(AttLinkResult attLinkResult, String code) {
        boolean prjListChangeToShown = false; //下拉项目默认隐藏
        boolean prjNameChangeToShown = false; //手写项目默认隐藏

        boolean prjListChangeToMandatory = false; //下拉项目默认非必填
        boolean prjNameChangeToMandatory = false; //手写项目默认非必填
        boolean isZFChangeToMandatory = true; //政府投资默认必填

        boolean prjListChangeToEditable = false; //下拉项目默认不可改
        boolean prjNameChangeToEditable = false; //手写项目默认不可改
        boolean isZFChangeToEditable = true; //是否政府投资默认可改
        boolean amtSourceChangeToEditable = true; //资金来源默认可改

        if (!"system".equals(code)) {
            prjNameChangeToShown = true;
            prjNameChangeToMandatory = true;
            prjNameChangeToEditable = true;
            isZFChangeToEditable = true;
            amtSourceChangeToEditable = true;
        } else {
            prjListChangeToShown = true;
            prjListChangeToMandatory = true;
            prjListChangeToEditable = true;
        }

        //下拉项目列表
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            linkedAtt.changeToMandatory = prjListChangeToMandatory;
            linkedAtt.changeToShown = prjListChangeToShown;
            linkedAtt.changeToEditable = prjListChangeToEditable;
            attLinkResult.attMap.put("PM_PRJ_ID", linkedAtt);
        }
        //项目名称
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            linkedAtt.changeToMandatory = prjNameChangeToMandatory;
            linkedAtt.changeToShown = prjNameChangeToShown;
            linkedAtt.changeToEditable = prjNameChangeToEditable;
            attLinkResult.attMap.put("PROJECT_NAME_WR", linkedAtt);
        }
        //是否政府投资项目
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            linkedAtt.changeToEditable = isZFChangeToEditable;
            linkedAtt.changeToMandatory = isZFChangeToMandatory;
            attLinkResult.attMap.put("YES_NO_ONE", linkedAtt);
        }
        //资金来源
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            linkedAtt.changeToEditable = amtSourceChangeToEditable;
            attLinkResult.attMap.put("INVESTMENT_SOURCE_ID", linkedAtt);
        }
        //清空数据
        //采购启动依据
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            linkedAtt.changeToEditable = true;
            attLinkResult.attMap.put("BUY_START_BASIS_ID", linkedAtt);
            attLinkResult.attMap.put("REPLY_NO_WR", linkedAtt);
        }
        //采购启动依据文件
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.FILE_GROUP;
            linkedAtt.value = null;
            linkedAtt.text = null;
            linkedAtt.changeToEditable = true;
            linkedAtt.fileInfoList = null;
            attLinkResult.attMap.put("FILE_ID_THREE", linkedAtt);
        }
    }
}
