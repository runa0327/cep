package com.cisdi.ext.link;

import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

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
            attLinkResult.attMap.put("CUSTOMER_UNIT", linkedAtt); //业主单位
        }
    }

    /**
     * 清空项目配套人员信息
     * @param attLinkResult 返回的数据结果集
     */
    private static void clearProjectUserData(AttLinkResult attLinkResult) {
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
     * 项目成本岗自动带出
     * @param attLinkResult 返回数据结果集
     * @param myJdbcTemplate 数据源
     * @param userId 该岗位人员id
     */
    public static void copyPrjCostUser(AttLinkResult attLinkResult, MyJdbcTemplate myJdbcTemplate, String userId) {
        String name = getName(myJdbcTemplate,userId);
        //成本岗
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = userId;
            linkedAtt.text = name;
            attLinkResult.attMap.put("AD_USER_THREE_ID", linkedAtt);
        }
    }

    /**
     * 查询人员名称
     * @param myJdbcTemplate 数据源
     * @param userId 用户id
     * @return 人员名称
     */
    private static String getName(MyJdbcTemplate myJdbcTemplate, String userId) {
        String name = null;
        String sql = "select name from ad_user where id = ? ";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,userId);
        if (!CollectionUtils.isEmpty(list)){
            name = JdbcMapUtil.getString(list.get(0),"name");
        }
        return name;
    }

    /**
     * 项目数据来源数据清空
     * @param attLinkResult 返回的数据结果集
     */
    public static void clearDataSourceType(AttLinkResult attLinkResult) {
        clearProjectAmtData(attLinkResult); //清空资金信息
        clearBaseProjectData(attLinkResult); //清空项目信息
        clearProjectUserData(attLinkResult); //清空项目配套人员信息
    }

    /**
     * 合同签订、补充协议 是否标准模板属性联动数据清除
     * @param attLinkResult
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
            attLinkResult.attMap.put("AD_USER_ID", linkedAtt); //合同签订联系人
        }
    }

    /**
     * 合同签订、补充协议 是否标准模板属性联动数
     * @param attLinkResult 返回的数据结果集
     * @param code 联动的属性值 是(Y)， 否(N)
     */
    public static void handleOrderIsStandard(AttLinkResult attLinkResult, String code) {
        Boolean faWuChangeToShown =  true; //法务部门修订稿显示
        Boolean caiWuChangeToShown =  true; //财务部门修订稿显示
        Boolean isCaiNaChangeToShown =  true; //审核意见是否完全采纳显示
        Boolean caiNaChangeToShown =  true; //采纳说明显示
        Boolean caiNaFileChangeToShown =  true; //采纳附件显示
        Boolean heTongChangeToShown =  true; //合同送审稿显示
        Boolean falvChangeToShown = true; // 法律审核附件显示
        Boolean phoneChangeToShown = false; // 联系电话 默认不显示
        Boolean userChangeToMandatory = true; // 联系人默认必填
        if ("Y".equals(code)){
            faWuChangeToShown =  false;
            caiWuChangeToShown =  false;
            isCaiNaChangeToShown =  false;
            caiNaChangeToShown =  false;
            caiNaFileChangeToShown =  false;
            heTongChangeToShown =  false;
            falvChangeToShown = false;
            userChangeToMandatory = false;
            phoneChangeToShown = true;
        }
        // 法律审核附件
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.changeToShown = falvChangeToShown;
            attLinkResult.attMap.put("FILE_ID_SIX", linkedAtt);
        }
        // 财务部门修订稿
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.changeToShown = faWuChangeToShown;
            attLinkResult.attMap.put("FILE_ID_TWO", linkedAtt);
        }
        // 法务部门修订稿
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.changeToShown = caiWuChangeToShown;
            attLinkResult.attMap.put("FILE_ID_THREE", linkedAtt);
        }
        // 审核意见是否完全采纳
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.changeToShown = isCaiNaChangeToShown;
            attLinkResult.attMap.put("YES_NO_ONE", linkedAtt);
        }
        // 审核意见采纳说明
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.changeToShown = caiNaChangeToShown;
            attLinkResult.attMap.put("REMARK_ONE", linkedAtt);
        }
        // 采纳意见附件
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.changeToShown = caiNaFileChangeToShown;
            attLinkResult.attMap.put("FILE_ID_FOUR", linkedAtt);
        }
        // 合同送审稿
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.changeToShown = heTongChangeToShown;
            attLinkResult.attMap.put("FILE_ID_ONE", linkedAtt);
        }
        // 联系人
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.changeToMandatory = userChangeToMandatory;
            attLinkResult.attMap.put("AD_USER_ID", linkedAtt);
        }
        // 联系电话
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.changeToShown = phoneChangeToShown;
            attLinkResult.attMap.put("CONTACT_MOBILE_ONE", linkedAtt);
        }
    }
}
