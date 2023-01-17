package com.cisdi.ext.link;

import com.qygly.shared.ad.att.AttDataTypeE;

/**
 * 属性联动详情
 */
public class AttLinkExtDetail {

    /**
     * 合同需求变更
     * @param attLinkResult 返回的数据结果集
     * @param code 系统(system)，非系统(non_system)
     */
    public static void getPoOrderChange(AttLinkResult attLinkResult, String code) {
        //每次切换，清空原有数据
        // a.清空项目基础信息
        clearBaseProjectData(attLinkResult);
        // b.清空项目资金信息
        clearProjectAmtData(attLinkResult);
        // 项目来源属性联动(多选项目+手填)
        projectLink(attLinkResult,code);
    }

    /**
     * 项目来源属性联动(多选项目+手填)
     * @param attLinkResult 返回的数据结果集
     * @param code 系统(system)，非系统(non_system)
     */
    private static void projectLink(AttLinkResult attLinkResult, String code) {
        //系统(system)，非系统(non_system)
        Boolean prjListChangeToShown = false; //下拉项目默认隐藏
        Boolean prjNameChangeToShown = false; //手写项目默认隐藏

        Boolean prjListChangeToMandatory = false; //下拉项目默认非必填
        Boolean prjNameChangeToMandatory = false; //手写项目默认非必填

        Boolean prjListChangeToEditable = false; //下拉项目默认不可改
        Boolean prjNameChangeToEditable = false; //手写项目默认不可改

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
            attLinkResult.attMap.put("PM_PRJ_IDS", linkedAtt);
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
    }

    /**
     * 清空项目资金信息
     * @param attLinkResult 返回的数据结果集
     */
    private static void clearProjectAmtData(AttLinkResult attLinkResult) {
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
        }
    }

    /**
     * 清空项目基础信息
     * @param attLinkResult 返回的数据结果集
     */
    private static void clearBaseProjectData(AttLinkResult attLinkResult) {
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = null;
            linkedAtt.text = null;
            attLinkResult.attMap.put("PM_PRJ_IDS", linkedAtt); //项目名称
            attLinkResult.attMap.put("PM_PRJ_ID", linkedAtt); //项目名称
            attLinkResult.attMap.put("PROJECT_NAME_WR", linkedAtt); //项目名称
            attLinkResult.attMap.put("PRJ_REPLY_NO", linkedAtt); //项目批复文号
            attLinkResult.attMap.put("PROJECT_TYPE_ID", linkedAtt); //项目类型
            attLinkResult.attMap.put("PRJ_SITUATION", linkedAtt); //项目介绍
            attLinkResult.attMap.put("INVESTMENT_SOURCE_ID", linkedAtt); //资金来源
        }
    }
}
