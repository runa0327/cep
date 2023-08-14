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
}
