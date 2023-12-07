package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkedAtt;
import com.qygly.shared.ad.att.AttDataTypeE;

/**
 * 项目属性联动清除数据
 */
public class AttLinkClear {

    /**
     * 项目结算审批-项目属性联动清除
     * @param attLinkResult 属性联动返回值
     */
    public static void clearSettleData(AttLinkResult attLinkResult) {
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            attLinkResult.attMap.put("PRJ_TOTAL_INVEST2", linkedAtt); //概算信息-总投资
            attLinkResult.attMap.put("PROJECT_AMT_INVEST2", linkedAtt); //概算信息-工程费用
            attLinkResult.attMap.put("CONSTRUCT_PRJ_AMT_INVEST2", linkedAtt); //概算信息-建安工程费
            attLinkResult.attMap.put("EQUIP_BUY_AMT_INVEST2", linkedAtt); //概算信息-设备采购费
            attLinkResult.attMap.put("EQUIPMENT_COST_INVEST2", linkedAtt); //概算信息-科研设备费
            attLinkResult.attMap.put("PROJECT_OTHER_AMT_INVEST2", linkedAtt); //概算信息-工程其他费用
            attLinkResult.attMap.put("LAND_AMT_INVEST2", linkedAtt); //概算信息-土地征拆费用
            attLinkResult.attMap.put("PREPARE_AMT_INVEST2", linkedAtt); //概算信息-预备费
            attLinkResult.attMap.put("CONSTRUCT_PERIOD_INVEST2", linkedAtt); //概算信息-建设期利息
            attLinkResult.attMap.put("REPLY_DATE_INVEST2", linkedAtt); //概算信息-批复日期
            attLinkResult.attMap.put("REPLY_NO_INVEST2", linkedAtt); //概算信息-批复文号
            attLinkResult.attMap.put("REPLY_FILE_INVEST2", linkedAtt); //概算信息-批复文件
            attLinkResult.attMap.put("PRJ_TOTAL_INVEST3", linkedAtt); //财评信息-总投资
            attLinkResult.attMap.put("PROJECT_AMT_INVEST3", linkedAtt); //财评信息-工程费用
            attLinkResult.attMap.put("CONSTRUCT_PRJ_AMT_INVEST3", linkedAtt); //财评信息-建安工程费
            attLinkResult.attMap.put("EQUIP_BUY_AMT_INVEST3", linkedAtt); //财评信息-设备采购费
            attLinkResult.attMap.put("EQUIPMENT_COST_INVEST3", linkedAtt); //财评信息-科研设备费
            attLinkResult.attMap.put("PROJECT_OTHER_AMT_INVEST3", linkedAtt); //财评信息-工程其他费用
            attLinkResult.attMap.put("LAND_AMT_INVEST3", linkedAtt); //财评信息-土地征拆费用
            attLinkResult.attMap.put("PREPARE_AMT_INVEST3", linkedAtt); //财评信息-预备费
            attLinkResult.attMap.put("CONSTRUCT_PERIOD_INVEST3", linkedAtt); //财评信息-建设期利息
            attLinkResult.attMap.put("REPLY_DATE_INVEST3", linkedAtt); //财评信息-批复日期
            attLinkResult.attMap.put("REPLY_NO_INVEST3", linkedAtt); //财评信息-批复文号
            attLinkResult.attMap.put("REPLY_FILE_INVEST3", linkedAtt); //财评信息-批复文件
            attLinkResult.attMap.put("PRJ_TOTAL_HISTORY", linkedAtt); //历史结算信息汇总-总投资
            attLinkResult.attMap.put("CONSTRUCT_PRJ_AMT_HISTORY", linkedAtt); //历史结算信息汇总-建安工程费
            attLinkResult.attMap.put("PROJECT_OTHER_AMT_HISTORY", linkedAtt); //历史结算信息汇总-工程其他费用
            attLinkResult.attMap.put("EQUIP_BUY_AMT_HISTORY", linkedAtt); //历史结算信息汇总-设备采购费
            attLinkResult.attMap.put("EQUIPMENT_COST_HISTORY", linkedAtt); //历史结算信息汇总-科研设备费
            attLinkResult.attMap.put("LAND_AMT_HISTORY", linkedAtt); //历史结算信息汇总-土地征拆费用
            attLinkResult.attMap.put("PREPARE_AMT_HISTORY", linkedAtt); //历史结算信息汇总-预备费
            attLinkResult.attMap.put("CONSTRUCT_PERIOD_HISTORY", linkedAtt); //历史结算信息汇总-建设期利息
            attLinkResult.attMap.put("CUM_PAY_AMT_TWO", linkedAtt); //历史结算信息汇总-累计已支付金额
            attLinkResult.attMap.put("OVER_PAYED_PERCENT", linkedAtt); //历史结算信息汇总-已支付比例(%)
            attLinkResult.attMap.put("TEXT_REMARK_TWO", linkedAtt); //历史结算信息汇总-项目结算内容摘要
            attLinkResult.attMap.put("DATE_TWO", linkedAtt); //历史结算信息汇总-上次结算日期
            attLinkResult.attMap.put("FILE_ID_FOUR", linkedAtt); //历史结算信息汇总-结算盖章文件
            attLinkResult.attMap.put("FILE_ID_FIVE", linkedAtt); //历史结算信息汇总-项目结算电子资料
            attLinkResult.attMap.put("FILE_ID_SIX", linkedAtt); //历史结算信息汇总-其他附件
        }
    }

    /**
     * 清空项目基础信息
     * @param attLinkResult 返回的数据结果集
     */
    public static void clearBaseProjectData(AttLinkResult attLinkResult) {
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            attLinkResult.attMap.put("PM_PRJ_IDS", linkedAtt); //项目名称
            attLinkResult.attMap.put("PM_PRJ_ID", linkedAtt); //项目名称
            attLinkResult.attMap.put("PROJECT_NAME_WR", linkedAtt); //项目名称
            attLinkResult.attMap.put("PRJ_REPLY_NO", linkedAtt); //项目批复文号
            attLinkResult.attMap.put("REPLY_NO", linkedAtt); // 批复文号
            attLinkResult.attMap.put("INVESTMENT_SOURCE_ID", linkedAtt); //资金来源
            attLinkResult.attMap.put("CUSTOMER_UNIT", linkedAtt); //业主单位
            attLinkResult.attMap.put("PRJ_MANAGE_MODE_ID", linkedAtt); //项目管理模式
            attLinkResult.attMap.put("BASE_LOCATION_ID", linkedAtt); //建设地点
            attLinkResult.attMap.put("FLOOR_AREA", linkedAtt); //占地面积
            attLinkResult.attMap.put("PROJECT_TYPE_ID", linkedAtt); //项目类型
            attLinkResult.attMap.put("CON_SCALE_TYPE_ID", linkedAtt); //建设规模类型
            attLinkResult.attMap.put("CON_SCALE_QTY", linkedAtt); //长度
            attLinkResult.attMap.put("CON_SCALE_QTY2", linkedAtt); //宽度
            attLinkResult.attMap.put("QTY_ONE", linkedAtt); //建筑面积
            attLinkResult.attMap.put("QTY_THREE", linkedAtt); //海域面积
            attLinkResult.attMap.put("OTHER", linkedAtt); //其他
            attLinkResult.attMap.put("QTY_TWO", linkedAtt); //其他
            attLinkResult.attMap.put("CON_SCALE_UOM_ID", linkedAtt); //建设规模单位
            attLinkResult.attMap.put("BUILD_YEARS", linkedAtt); // 建设年限
            attLinkResult.attMap.put("PRJ_SITUATION", linkedAtt); //项目规模及内容
        }
    }

    /**
     * 清空项目资金信息
     * @param attLinkResult 返回的数据结果集
     */
    public static void clearProjectAmtData(AttLinkResult attLinkResult) {
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            attLinkResult.attMap.put("PRJ_TOTAL_INVEST", linkedAtt); //总投资
            attLinkResult.attMap.put("PROJECT_AMT", linkedAtt); //其中工程费用
            attLinkResult.attMap.put("PROJECT_OTHER_AMT", linkedAtt); //其中工程建设其他费
            attLinkResult.attMap.put("PREPARE_AMT", linkedAtt); //其中预备费
            attLinkResult.attMap.put("CONSTRUCT_PERIOD_INTEREST", linkedAtt); //其中建设期利息
            attLinkResult.attMap.put("CONSTRUCT_AMT", linkedAtt); //建安费
            attLinkResult.attMap.put("EQUIP_AMT", linkedAtt); //设备费
            attLinkResult.attMap.put("EQUIPMENT_COST", linkedAtt); //科研设备费
            attLinkResult.attMap.put("LAND_AMT", linkedAtt); //土地征迁费
            attLinkResult.attMap.put("FEASIBILITY_APPROVE_FUND", linkedAtt); // 可研批复资金
            attLinkResult.attMap.put("ESTIMATE_APPROVE_FUND", linkedAtt); // 初概批复资金
            attLinkResult.attMap.put("EVALUATION_APPROVE_FUND", linkedAtt); // 财评批复资金
        }
    }

    /**
     * 清除项目基础信息-不包含项目id
     * @param attLinkResult 返回的集合信息
     */
    public static void clearBaseProjectDataNOPrj(AttLinkResult attLinkResult) {
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            attLinkResult.attMap.put("PROJECT_NAME_WR", linkedAtt); //项目名称
            attLinkResult.attMap.put("PRJ_REPLY_NO", linkedAtt); //项目批复文号
            attLinkResult.attMap.put("REPLY_NO", linkedAtt); // 批复文号
            attLinkResult.attMap.put("INVESTMENT_SOURCE_ID", linkedAtt); //资金来源
            attLinkResult.attMap.put("CUSTOMER_UNIT", linkedAtt); //业主单位
            attLinkResult.attMap.put("PRJ_MANAGE_MODE_ID", linkedAtt); //项目管理模式
            attLinkResult.attMap.put("BASE_LOCATION_ID", linkedAtt); //建设地点
            attLinkResult.attMap.put("FLOOR_AREA", linkedAtt); //占地面积
            attLinkResult.attMap.put("PROJECT_TYPE_ID", linkedAtt); //项目类型
            attLinkResult.attMap.put("CON_SCALE_TYPE_ID", linkedAtt); //建设规模类型
            attLinkResult.attMap.put("CON_SCALE_QTY", linkedAtt); //长度
            attLinkResult.attMap.put("CON_SCALE_QTY2", linkedAtt); //宽度
            attLinkResult.attMap.put("QTY_ONE", linkedAtt); //建筑面积
            attLinkResult.attMap.put("QTY_THREE", linkedAtt); //海域面积
            attLinkResult.attMap.put("OTHER", linkedAtt); //其他
            attLinkResult.attMap.put("QTY_TWO", linkedAtt); //其他
            attLinkResult.attMap.put("CON_SCALE_UOM_ID", linkedAtt); //建设规模单位
            attLinkResult.attMap.put("BUILD_YEARS", linkedAtt); // 建设年限
            attLinkResult.attMap.put("PRJ_SITUATION", linkedAtt); //项目规模及内容
            attLinkResult.attMap.put("PRJ_DESIGN_USER_ID", linkedAtt); // 设计岗
            attLinkResult.attMap.put("PRJ_COST_USER_ID", linkedAtt); // 成本岗
            attLinkResult.attMap.put("PRJ_REPLY_DATE", linkedAtt); // 批复日期
            attLinkResult.attMap.put("PRJ_REPLY_FILE", linkedAtt); // 批复材料
        }
    }

    /**
     * 清空项目配套人员信息
     * @param attLinkResult 返回的数据结果集
     */
    public static void clearProjectUserData(AttLinkResult attLinkResult) {
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            attLinkResult.attMap.put("AD_USER_THREE_ID", linkedAtt); //成本岗
            attLinkResult.attMap.put("PRJ_COST_USER_ID", linkedAtt); //成本岗
            attLinkResult.attMap.put("PRJ_DESIGN_USER_ID", linkedAtt); //设计岗
            attLinkResult.attMap.put("PRJ_EARLY_USER_ID", linkedAtt); //前期岗
        }
    }

    /**
     * 清除项目流程审批岗位信息
     */
    public static void clearProcessPostUser(AttLinkResult attLinkResult) {
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            attLinkResult.attMap.put("AD_USER_TWENTY_FIVE_ID", linkedAtt); // 财务管理岗
            attLinkResult.attMap.put("AD_USER_TWENTY_FOUR_ID", linkedAtt); // 征拆对接岗
            attLinkResult.attMap.put("AD_USER_TWENTY_THREE_ID", linkedAtt); // 工程管理岗
            attLinkResult.attMap.put("AD_USER_TWENTY_TWO_ID", linkedAtt); // 设计管理岗
            attLinkResult.attMap.put("AD_USER_TWENTY_ONE_ID", linkedAtt); // 采购管理岗
            attLinkResult.attMap.put("AD_USER_TWENTY_ID", linkedAtt); // 设备成本岗
            attLinkResult.attMap.put("AD_USER_NINETEEN_ID", linkedAtt); // 合约管理岗
            attLinkResult.attMap.put("AD_USER_EIGHTEEN_ID", linkedAtt); // 成本管理岗
            attLinkResult.attMap.put("AD_USER_SIXTEEN_ID", linkedAtt); // 前期设备岗
            attLinkResult.attMap.put("AD_USER_FIFTEEN_ID", linkedAtt); // 计划运营岗
            attLinkResult.attMap.put("AD_USER_FOURTEEN_ID", linkedAtt); // 管线迁改岗
            attLinkResult.attMap.put("AD_USER_THIRTEEN_ID", linkedAtt); // 土地管理岗
            attLinkResult.attMap.put("AD_USER_TWELVE_ID", linkedAtt); // 前期报建岗
        }
    }

    /**
     * 合同签订、补充协议 是否标准模板属性联动数据清除
     * @param attLinkResult 返回的集合信息
     */
    public static void clearOrderIsStandard(AttLinkResult attLinkResult) {
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            attLinkResult.attMap.put("FILE_ID_SIX", linkedAtt); //法律审核附件
            attLinkResult.attMap.put("FILE_ID_TWO", linkedAtt); //财务部门修订稿
            attLinkResult.attMap.put("FILE_ID_THREE", linkedAtt); //法务部门修订稿
            attLinkResult.attMap.put("YES_NO_ONE", linkedAtt); //审核意见是否完全采纳
            attLinkResult.attMap.put("REMARK_ONE", linkedAtt); //审核意见采纳说明
            attLinkResult.attMap.put("FILE_ID_FOUR", linkedAtt); //采纳意见附件
            attLinkResult.attMap.put("FILE_ID_ONE", linkedAtt); //合同送审稿
        }
    }

    /**
     * 清除项目类型属性联动值
     * @param attLinkResult 返回map值
     */
    public static void clearProjectTypeData(AttLinkResult attLinkResult) {
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            attLinkResult.attMap.put("CON_SCALE_QTY", linkedAtt); //道路长度(m)
            attLinkResult.attMap.put("CON_SCALE_QTY2", linkedAtt); //道路宽度(m)
            attLinkResult.attMap.put("QTY_ONE", linkedAtt); // 建筑面积(㎡)
            attLinkResult.attMap.put("QTY_THREE", linkedAtt); //海域面积(㎡)
            attLinkResult.attMap.put("OTHER", linkedAtt); //其他
        }
    }

    /**
     * 业主单位切换清空涉及数据
     */
    public static void clearUser(AttLinkResult attLinkResult) {
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            attLinkResult.attMap.put("OPERATOR_ONE_ID",linkedAtt); // 经办人
            attLinkResult.attMap.put("AD_USER_THREE_ID",linkedAtt); // 成本岗
            attLinkResult.attMap.put("AD_USER_FOUR_ID",linkedAtt); // 合同岗
            attLinkResult.attMap.put("AD_USER_EIGHTH_ID",linkedAtt); // 法务岗用户
            attLinkResult.attMap.put("AD_USER_NINTH_ID",linkedAtt); // 财务岗用户
            attLinkResult.attMap.put("CRT_DEPT_ID",linkedAtt); // 所属部门
        }
    }
}
