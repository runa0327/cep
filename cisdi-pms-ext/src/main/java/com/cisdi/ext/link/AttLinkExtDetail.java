package com.cisdi.ext.link;

import com.cisdi.ext.base.AdUserExt;
import com.cisdi.ext.base.GrSetValue;
import com.cisdi.ext.model.PmRoster;
import com.cisdi.ext.pm.PmBuyDemandReqExt;
import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 属性联动详情
 */
public class AttLinkExtDetail {

    public static final Map<String,String> postCodeMap = new HashMap<>();
    static {
        postCodeMap.put("FINANCE_POST","AD_USER_TWENTY_FIVE_ID"); //财务管理岗
        postCodeMap.put("BUY_POST","AD_USER_TWENTY_ONE_ID"); //采购管理岗
        postCodeMap.put("TREATY_POST","AD_USER_NINETEEN_ID"); //合约管理岗
        postCodeMap.put("EARLY_EQUIP_POST","AD_USER_SIXTEEN_ID"); //前期设备岗
        postCodeMap.put("EQUIP_COST_POST","AD_USER_EIGHTEEN_ID"); //设备成本岗
        postCodeMap.put("LAND_POST","AD_USER_THIRTEEN_ID"); //土地管理岗
    }

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
        clearBaseProjectDataNOPrj(attLinkResult); //清空项目信息
        clearProjectUserData(attLinkResult); //清空项目配套人员信息
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
        // 联系电话
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.changeToShown = phoneChangeToShown;
            attLinkResult.attMap.put("CONTACT_MOBILE_ONE", linkedAtt);
        }
    }

    /**
     * 流程明细表获取合同费用明细信息
     * @param attLinkResult 返回的集合
     * @param sevId 实体视图id
     * @param myJdbcTemplate 数据源
     * @param attValue 属性联动值
     * @return 值
     */
    public static void getOrderPayDetail(AttLinkResult attLinkResult, String sevId, MyJdbcTemplate myJdbcTemplate, String attValue) {
        Map<String,Object> map = getViewPayMap(sevId);
        String viewId = (String) map.get("viewId");
        Boolean createTable = (Boolean) map.get("createTable");

        List<LinkedRecord> linkedRecordList = new ArrayList<>();
        String sql = "select AMT_ONE,AMT_THREE,AMT_TWO,COST_TYPE_TREE_ID,FEE_DETAIL from PM_ORDER_COST_DETAIL where CONTRACT_ID = ? order by id asc";
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
                // 含税金额(元)
                linkedRecord.valueMap.put("AMT_ONE", StringUtil.getBigDecimal(JdbcMapUtil.getString(tmp,"AMT_ONE")));
                linkedRecord.textMap.put("AMT_ONE", JdbcMapUtil.getString(tmp,"AMT_ONE"));
                linkedRecord.valueMap.put("AMT_FIVE", StringUtil.getBigDecimal(JdbcMapUtil.getString(tmp,"AMT_ONE")));
                linkedRecord.textMap.put("AMT_FIVE", JdbcMapUtil.getString(tmp,"AMT_ONE"));
                // 不含税金额(元)
                linkedRecord.valueMap.put("AMT_TWO", StringUtil.getBigDecimal(JdbcMapUtil.getString(tmp,"AMT_TWO")));
                linkedRecord.textMap.put("AMT_TWO", JdbcMapUtil.getString(tmp,"AMT_TWO"));
                linkedRecord.valueMap.put("AMT_SIX", StringUtil.getBigDecimal(JdbcMapUtil.getString(tmp,"AMT_TWO")));
                linkedRecord.textMap.put("AMT_SIX", JdbcMapUtil.getString(tmp,"AMT_TWO"));
                // 税率(%)
                linkedRecord.valueMap.put("AMT_THREE", StringUtil.getBigDecimal(JdbcMapUtil.getString(tmp,"AMT_THREE")));
                linkedRecord.textMap.put("AMT_THREE", JdbcMapUtil.getString(tmp,"AMT_THREE"));
                linkedRecordList.add(linkedRecord);
            }
            attLinkResult.childData.put(viewId, linkedRecordList);
        }
        attLinkResult.childCreatable.put(viewId, createTable);
        attLinkResult.childClear.put(viewId, true);
    }

    /**
     * @param sevId 实体视图id
     * @return 费用明细实体视图对应的视图id及是否允许创建明细表
     */
    private static Map<String, Object> getViewPayMap(String sevId) {
        Map<String,Object> map = new HashMap<>();
        String viewId = "";
        Boolean createTable = false;
        if ("0099902212142028526".equals(sevId)){ // 资金需求计划
            viewId = "0099952822476362402";
        } else if ("0099902212142022303".equals(sevId)){ //补充协议
            viewId = "0099952822476410750";
            createTable = true;
        } else if ("0099902212142025475".equals(sevId)){ //合同终止
            viewId = "1628613927485190144";
            createTable = true;
        }
        map.put("viewId",viewId);
        map.put("createTable",createTable);
        return map;
    }

    /**
     * @param sevId 实体视图id
     * @return 联系人明细实体视图对应的视图id
     */
    private static String getViewContacts(String sevId) {
        String viewId = "";
        if ("0099902212142022303".equals(sevId)){ //补充协议
            viewId = "0099952822476378742";
        } else if ("0099902212142025475".equals(sevId)){ //合同终止
            viewId = "1628614121601773568";
        }
        return viewId;
    }

    /**
     * 流程明细表获取合同联系人明细信息
     * @param attLinkResult 返回的数据集
     * @param sevId 实体视图id
     * @param myJdbcTemplate 数据源
     * @param attValue 属性联动值
     */
    public static void getOrderContactsDetail(AttLinkResult attLinkResult, String sevId, MyJdbcTemplate myJdbcTemplate, String attValue) {
            String viewId = getViewContacts(sevId);
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

    /**
     * 项目属性联动-赋值
     * @param attLinkResult 返回的集合值
     * @param row 单条项目属性详情信息
     * @param entCode 实体表名
     * @param myJdbcTemplate 数据源
     */
    public static void assignmentAttLinkResult(AttLinkResult attLinkResult, Map row, String entCode, MyJdbcTemplate myJdbcTemplate) {
        //项目类型属性联动处理
        List<String> noPrjTypeList = AttLinkDifferentProcess.noPrjTypeLink();
        if (!noPrjTypeList.contains(entCode)){
            prjTypeLinkNew(row,attLinkResult);
        }
        // 建筑面积
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "BUILDING_AREA")) ? null:new BigDecimal(JdbcMapUtil.getString(row, "BUILDING_AREA"));
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "BUILDING_AREA")) ? null:JdbcMapUtil.getString(row, "BUILDING_AREA");
            attLinkResult.attMap.put("BUILDING_AREA", linkedAtt);
        }
        // 业主单位
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "customer_id")) ? null:JdbcMapUtil.getString(row, "customer_id");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "customer_name")) ? null:JdbcMapUtil.getString(row, "customer_name");
            attLinkResult.attMap.put("CUSTOMER_UNIT", linkedAtt);
        }
        // 项目管理模式
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "m_id")) ? null:JdbcMapUtil.getString(row, "m_id");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "m_name")) ? null:JdbcMapUtil.getString(row, "m_name");
            attLinkResult.attMap.put("PRJ_MANAGE_MODE_ID", linkedAtt);
        }
        // 建设地点
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "l_id")) ? null:JdbcMapUtil.getString(row, "l_id");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "l_name")) ? null:JdbcMapUtil.getString(row, "l_name");
            attLinkResult.attMap.put("BASE_LOCATION_ID", linkedAtt);
        }
        // 项目类型
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "pt_id")) ? null:JdbcMapUtil.getString(row, "pt_id");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "pt_name")) ? null:JdbcMapUtil.getString(row, "pt_name");
            attLinkResult.attMap.put("PROJECT_TYPE_ID", linkedAtt);
        }
        // 建设规模类型
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "st_id")) ? null:JdbcMapUtil.getString(row, "st_id");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "st_name")) ? null:JdbcMapUtil.getString(row, "st_name");
            attLinkResult.attMap.put("CON_SCALE_TYPE_ID", linkedAtt);
        }
        // 建设规模单位
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "su_id")) ? null:JdbcMapUtil.getString(row, "su_id");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "su_name")) ? null:JdbcMapUtil.getString(row, "su_name");
            attLinkResult.attMap.put("CON_SCALE_UOM_ID", linkedAtt);
        }
        // 建设年限
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "BUILD_YEARS")) ? null:JdbcMapUtil.getString(row, "BUILD_YEARS");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "BUILD_YEARS")) ? null:JdbcMapUtil.getString(row, "BUILD_YEARS");
            attLinkResult.attMap.put("BUILD_YEARS", linkedAtt);
        }
        // 项目概况
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "PRJ_SITUATION")) ? null:JdbcMapUtil.getString(row, "PRJ_SITUATION");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "PRJ_SITUATION")) ? null:JdbcMapUtil.getString(row, "PRJ_SITUATION");

            attLinkResult.attMap.put("PRJ_SITUATION", linkedAtt);
            attLinkResult.attMap.put("PRJ_INTRODUCE", linkedAtt);
        }
        if (!"PM_PRJ_REQ".equals(entCode)){
            // 批复日期
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.DATE;
                linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "PRJ_REPLY_DATE")) ? null:JdbcMapUtil.getString(row, "PRJ_REPLY_DATE");
                linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "PRJ_REPLY_DATE")) ? null:JdbcMapUtil.getString(row, "PRJ_REPLY_DATE");
                attLinkResult.attMap.put("PRJ_REPLY_DATE", linkedAtt);
            }
        }
        // 批复材料
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.FILE_GROUP;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "PRJ_REPLY_FILE")) ? null:JdbcMapUtil.getString(row, "PRJ_REPLY_FILE");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "PRJ_REPLY_FILE")) ? null:JdbcMapUtil.getString(row, "PRJ_REPLY_FILE");

            attLinkResult.attMap.put("PRJ_REPLY_FILE", linkedAtt);
        }
        // 可研批复资金
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "FS")) ? null:JdbcMapUtil.getString(row, "FS");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "FS")) ? null:JdbcMapUtil.getString(row, "FS");

            attLinkResult.attMap.put("FEASIBILITY_APPROVE_FUND", linkedAtt);
        }
        // 初概批复资金
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "PD")) ? null:JdbcMapUtil.getString(row, "PD");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "PD")) ? null:JdbcMapUtil.getString(row, "PD");
            attLinkResult.attMap.put("ESTIMATE_APPROVE_FUND", linkedAtt);
        }
        // 财评批复资金
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "budget")) ? null:JdbcMapUtil.getString(row, "budget");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "budget")) ? null:JdbcMapUtil.getString(row, "budget");
            attLinkResult.attMap.put("EVALUATION_APPROVE_FUND", linkedAtt);
        }
        // 总投资
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "ESTIMATED_TOTAL_INVEST")) ? null:JdbcMapUtil.getString(row, "ESTIMATED_TOTAL_INVEST");
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "ESTIMATED_TOTAL_INVEST")) ? null:JdbcMapUtil.getString(row, "ESTIMATED_TOTAL_INVEST");
            attLinkResult.attMap.put("PRJ_TOTAL_INVEST", linkedAtt);
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
        //海域面积
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "QTY_THREE")) ? null:JdbcMapUtil.getString(row, "QTY_THREE");
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "QTY_THREE")) ? null:new BigDecimal(JdbcMapUtil.getString(row, "QTY_THREE"));
            attLinkResult.attMap.put("QTY_THREE", linkedAtt);
        }
        //长
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "CON_SCALE_QTY")) ? null:JdbcMapUtil.getString(row, "CON_SCALE_QTY");
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "CON_SCALE_QTY")) ? null:new BigDecimal(JdbcMapUtil.getString(row, "CON_SCALE_QTY"));
            attLinkResult.attMap.put("CON_SCALE_QTY", linkedAtt);
        }
        //宽
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "CON_SCALE_QTY2")) ? null:JdbcMapUtil.getString(row, "CON_SCALE_QTY2");
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "CON_SCALE_QTY2")) ? null:new BigDecimal(JdbcMapUtil.getString(row, "CON_SCALE_QTY2"));
            attLinkResult.attMap.put("CON_SCALE_QTY2", linkedAtt);
        }
        //其他
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "QTY_TWO")) ? null:JdbcMapUtil.getString(row, "QTY_TWO");
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "QTY_TWO")) ? null:JdbcMapUtil.getString(row, "QTY_TWO");
            attLinkResult.attMap.put("OTHER", linkedAtt);
        }
    }

    /**
     * 项目类型属性联动，根据项目类型联动，控制如：面积、长、宽等
     * @param row 单条项目记录
     * @param attLinkResult 返回的集合map
     */
    public static void prjTypeLinkNew(Map row, AttLinkResult attLinkResult) {
        Boolean roadLengthMustEdit = false; //道路长度默认非必填
        Boolean roadWidthMustEdit = false; //道路宽度默认非必填
        Boolean buildAreaMustEdit = false; //建筑面积默认非必填
        Boolean seaAreaMustEdit = false; //海域面积默认非必填
        Boolean otherMustEdit = false; //其他默认非必填

        Boolean roadLengthShow = false; //道路长度默认不显示
        Boolean roadWidthShow = false; //道路宽度默认不显示
        Boolean buildAreaShow = false; //建筑面积默认不显示
        Boolean seaAreaShow = false; //海域面积默认不显示
        Boolean otherShow = false; //其他默认不显示

        Boolean roadLengthChangeToEditable = false; //道路长度默认不可改
        Boolean roadWidthChangeToEditable = false; //道路宽度默认不可改
        Boolean buildAreaChangeToEditable = false; //建筑面积默认不可改
        Boolean seaAreaChangeToEditable = false; //海域面积默认不可改
        Boolean otherChangeToEditable = false; //其他默认不可改

        //项目类型名称
//        0099799190825080689(民用建筑) 0099799190825080690(市政道路)	 0099799190825080691(港口航道)  0099799190825080692(园林景观)
//        0099799190825080739(轨道交通) 0099799190825080740(工业建筑)   0099799190825080993(市政管道)   0099799190825080994(设备采购)	 0099799190825080750(其他)
//        String projectTypeName = JdbcMapUtil.getString(row,"pt_name");
        String projectTypeId = JdbcMapUtil.getString(row,"PROJECT_TYPE_ID");
//        String projectTypeId = GrSetValue.getValueIdByName(projectTypeName,"PROJECT_TYPE");
        if ("0099799190825080690".equals(projectTypeId) || "0099799190825080739".equals(projectTypeId)){//市政道路、轨道交通，显示长宽
            roadLengthMustEdit = true;
            roadWidthMustEdit = true;
            roadLengthShow = true;
            roadWidthShow = true;
            roadLengthChangeToEditable = true;
            roadWidthChangeToEditable = true;
        } else if ("0099799190825080691".equals(projectTypeId)){ //港口航道，显示海域面积
            seaAreaMustEdit = true;
            seaAreaShow = true;
            seaAreaChangeToEditable = true;
        } else if ("0099799190825080689".equals(projectTypeId) || "0099799190825080692".equals(projectTypeId) || "0099799190825080740".equals(projectTypeId)){
            // 民用建筑、园林景观、工业建筑，显示建筑面积
            buildAreaMustEdit = true;
            buildAreaShow = true;
            buildAreaChangeToEditable = true;
        } else if ("0099799190825080994".equals(projectTypeId) || "0099799190825080750".equals(projectTypeId) || "0099799190825080993".equals(projectTypeId)){
            //其他、设备采购、市政管道，显示其他
            otherMustEdit = true;
            otherShow = true;
            otherChangeToEditable = true;
        }
        //建筑面积
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.changeToShown = buildAreaShow;
            linkedAtt.changeToMandatory = buildAreaMustEdit;
            linkedAtt.changeToEditable = buildAreaChangeToEditable;
            attLinkResult.attMap.put("QTY_ONE", linkedAtt);
        }
        //海域面积
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.changeToShown = seaAreaShow;
            linkedAtt.changeToMandatory = seaAreaMustEdit;
            linkedAtt.changeToEditable = seaAreaChangeToEditable;
            attLinkResult.attMap.put("QTY_THREE", linkedAtt);
        }
        //长
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.changeToShown = roadLengthShow;
            linkedAtt.changeToMandatory = roadLengthMustEdit;
            linkedAtt.changeToEditable = roadLengthChangeToEditable;
            attLinkResult.attMap.put("CON_SCALE_QTY", linkedAtt);
        }
        //宽
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.changeToShown = roadWidthShow;
            linkedAtt.changeToMandatory = roadWidthMustEdit;
            linkedAtt.changeToEditable = roadWidthChangeToEditable;
            attLinkResult.attMap.put("CON_SCALE_QTY2", linkedAtt);
        }
        //其他
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.changeToShown = otherShow;
            linkedAtt.changeToMandatory = otherMustEdit;
            linkedAtt.changeToEditable = otherChangeToEditable;
        }
    }

    /**
     * 项目类型属性联动，控制如：面积、长、宽等
     * @param row 单条项目记录
     * @param attLinkResult 返回的集合map
     */
    public static void prjTypeLink(Map row, AttLinkResult attLinkResult) {
        Boolean areashow = true; //面积显示
        Boolean lengthShow = true; //长显示
        Boolean widthShow = true; //宽显示
        Boolean otherShow = true; //其他显示
        Boolean seaShow = true; //海域面积显示
        Boolean buildAreaShow = false; //海域面积显示

        Boolean areaMustEdit = false; //面积必填
        Boolean lengthMustEdit = false; //长必填
        Boolean widthMustEdit = false; //宽必填
        Boolean otherMustEdit = false; //其他必填
        Boolean seaMustEdit = false; //海域面积必填
        Boolean floorAreaMustEdit = false; //占地面积必填

        String name1 = JdbcMapUtil.getString(row, "st_name");
        if (!SharedUtil.isEmptyString(name1)){
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
                    buildAreaShow = true;
                    seaMustEdit = true;
                } else if (name1.contains("建筑面积")){
                    lengthShow = false;
                    widthShow = false;
                    otherShow = false;
                    lengthMustEdit = false;
                    widthMustEdit = false;
                    otherMustEdit = false;
                    seaShow = false;
                    floorAreaMustEdit = true;
                }

            } else if (name1.contains("长宽")){
                areashow = false;
                otherShow = false;
                areaMustEdit = false;
                otherMustEdit = false;
                seaShow = false;
            } else {
                if (name1.contains("市政管线")){
                    floorAreaMustEdit = false;
                }
                areashow = false;
                lengthShow = false;
                widthShow = false;
                areaMustEdit = false;
                lengthMustEdit = false;
                widthMustEdit = false;
                seaShow = false;
            }
        }
        // 占地面积
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "FLOOR_AREA")) ? null:new BigDecimal(JdbcMapUtil.getString(row, "FLOOR_AREA"));
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "FLOOR_AREA")) ? null:JdbcMapUtil.getString(row, "FLOOR_AREA");
            linkedAtt.changeToMandatory = floorAreaMustEdit;
            attLinkResult.attMap.put("FLOOR_AREA", linkedAtt);
        }
        //建筑面积
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.changeToShown = areashow;
            linkedAtt.changeToMandatory = areaMustEdit;
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "QTY_ONE")) ? null:JdbcMapUtil.getString(row, "QTY_ONE");
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "QTY_ONE")) ? null:new BigDecimal(JdbcMapUtil.getString(row, "QTY_ONE"));
            attLinkResult.attMap.put("QTY_ONE", linkedAtt);
        }
        //海域面积
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.changeToShown = seaShow;
            linkedAtt.changeToMandatory = seaMustEdit;
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "QTY_THREE")) ? null:JdbcMapUtil.getString(row, "QTY_THREE");
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "QTY_THREE")) ? null:new BigDecimal(JdbcMapUtil.getString(row, "QTY_THREE"));
            attLinkResult.attMap.put("QTY_THREE", linkedAtt);
        }
        //长
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.changeToShown = lengthShow;
            linkedAtt.changeToMandatory = lengthMustEdit;
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "CON_SCALE_QTY")) ? null:JdbcMapUtil.getString(row, "CON_SCALE_QTY");
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "CON_SCALE_QTY")) ? null:new BigDecimal(JdbcMapUtil.getString(row, "CON_SCALE_QTY"));
            attLinkResult.attMap.put("CON_SCALE_QTY", linkedAtt);
        }
        //宽
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.changeToShown = widthShow;
            linkedAtt.changeToMandatory = widthMustEdit;
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "CON_SCALE_QTY2")) ? null:JdbcMapUtil.getString(row, "CON_SCALE_QTY2");
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "CON_SCALE_QTY2")) ? null:new BigDecimal(JdbcMapUtil.getString(row, "CON_SCALE_QTY2"));
            attLinkResult.attMap.put("CON_SCALE_QTY2", linkedAtt);
        }
        //其他
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.changeToShown = otherShow;
            linkedAtt.changeToMandatory = otherMustEdit;
            linkedAtt.text = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "QTY_TWO")) ? null:JdbcMapUtil.getString(row, "QTY_TWO");
            linkedAtt.value = SharedUtil.isEmptyString(JdbcMapUtil.getString(row, "QTY_TWO")) ? null:JdbcMapUtil.getString(row, "QTY_TWO");
            attLinkResult.attMap.put("OTHER", linkedAtt);
//            attLinkResult.attMap.put("QTY_TWO", linkedAtt);
        }
    }

    /**
     * 项目属性联动-是否正否投资
     * @param id 属性联动id值
     * @param attLinkResult 返回值
     */
    public static void assignmentPrjYesNoOne(String id, AttLinkResult attLinkResult) {
        String val = null;
        String txt = null;
        if ("0099799190825080705".equals(id)){
            val = "0099799190825080670";
            txt = "否";
        } else if ("0099799190825080704".equals(id) || "0099952822476392682".equals(id)){
            val = "0099799190825080669";
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
     * 项目属性联动-岗位指派流程-项目启动基础信息
     * @param attLinkResult 返回的集合信息
     * @param attValue 属性联动值
     * @param myJdbcTemplate 数据源
     */
    public static void selectPostAppointLink(AttLinkResult attLinkResult, String attValue, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select a.*,b.id as projectId from PRJ_START a left join pm_prj b on a.PM_CODE = b.PM_CODE where b.id = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,attValue);
        if (!CollectionUtils.isEmpty(list)){
            //业主单位
            String companyId = JdbcMapUtil.getString(list.get(0), "BUILDER_UNIT");
            //岗位人员赋值
            assignmentPostLink(attLinkResult,companyId,attValue,myJdbcTemplate);
            // 资金来源
            {
                String id = JdbcMapUtil.getString(list.get(0), "INVESTMENT_SOURCE_ID");
                String name = GrSetValue.getValueNameById(id);
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = id;
                linkedAtt.text = name;
                attLinkResult.attMap.put("INVESTMENT_SOURCE_ID", linkedAtt);
            }
            //资金总额 PRJ_TOTAL_INVEST
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.DOUBLE;
                linkedAtt.value = new BigDecimal(JdbcMapUtil.getString(list.get(0),"PRJ_TOTAL_INVEST"));
                linkedAtt.text = JdbcMapUtil.getString(list.get(0),"PRJ_TOTAL_INVEST");
                attLinkResult.attMap.put("PRJ_TOTAL_INVEST", linkedAtt);
            }
            // 项目类型
            {
                String id = JdbcMapUtil.getString(list.get(0), "PROJECT_TYPE_ID");
                String name = GrSetValue.getValueNameById(id);
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = id;
                linkedAtt.text = name;
                attLinkResult.attMap.put("PROJECT_TYPE_ID", linkedAtt);
            }
            // 启动时间
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.DATE;
                linkedAtt.value = JdbcMapUtil.getString(list.get(0), "START_TIME");
                linkedAtt.text = JdbcMapUtil.getString(list.get(0), "START_TIME");
                attLinkResult.attMap.put("START_TIME", linkedAtt);
            }
            // 建设单位
            {
                String name = GrSetValue.getValueNameById(companyId);
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = companyId;
                linkedAtt.text = name;
                attLinkResult.attMap.put("CUSTOMER_UNIT", linkedAtt);
            }
            // 招标方式
            {
                String id = JdbcMapUtil.getString(list.get(0), "TENDER_WAY_ID");
                String name = GrSetValue.getValueNameById(id);
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = id;
                linkedAtt.text = name;
                attLinkResult.attMap.put("TENDER_WAY_ID", linkedAtt);
            }
            // 项目简介
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = JdbcMapUtil.getString(list.get(0), "PRJ_SITUATION");
                linkedAtt.text = JdbcMapUtil.getString(list.get(0), "PRJ_SITUATION");
                attLinkResult.attMap.put("PRJ_SITUATION", linkedAtt);
            }
            // 启动说明
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = JdbcMapUtil.getString(list.get(0), "START_REMARK");
                linkedAtt.text = JdbcMapUtil.getString(list.get(0), "START_REMARK");
                attLinkResult.attMap.put("START_REMARK", linkedAtt);
            }
            // 启动依据
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.FILE_GROUP;
                linkedAtt.value = JdbcMapUtil.getString(list.get(0), "ATT_FILE_GROUP_ID");
                getFileInfoList(linkedAtt);
                attLinkResult.attMap.put("FILE_ID_ONE", linkedAtt);
            }
        }
    }

    /**
     * 项目默认岗位值赋值
     * @param attLinkResult 返回集合值
     * @param companyId 业主单位id
     * @param projectId 项目id
     * @param myJdbcTemplate 数据源
     */
    private static void assignmentPostLink(AttLinkResult attLinkResult, String companyId, String projectId, MyJdbcTemplate myJdbcTemplate) {
        //获取项目岗位默认配置
        String sql = "select * from BASE_POST_USER where CUSTOMER_UNIT = ? and status = 'AP'";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,companyId);
        if (!CollectionUtils.isEmpty(list)){
            //查询出的数据处理成Map集合
            Map<String,String> postMap = getPostMap(list);
            for (String key : postMap.keySet()){
                String userId = postMap.get(key);
                String userName = AdUserExt.getUserName(userId,myJdbcTemplate);
                String code = getMapToCode(key,postCodeMap);
                attLinkResultAddValue(userId,userName,code,attLinkResult);
            }
        }
    }

    /**
     * 默认岗位信息默认赋值
     * @param userId 用户id
     * @param userName 用户姓名
     * @param code 返回显示的页面表单字段
     * @param attLinkResult 返回的集合
     */
    private static void attLinkResultAddValue(String userId, String userName, String code, AttLinkResult attLinkResult) {
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = userId;
            linkedAtt.text = userName;
            attLinkResult.attMap.put(code, linkedAtt);
        }
    }
    /**
     * 默认岗位信息默认赋值 含不可改属性
     * @param userId 用户id
     * @param userName 用户姓名
     * @param code 返回显示的页面表单字段
     * @param changeToEditable 返回显示的页面表单字段
     * @param attLinkResult 返回的集合
     */
    private static void attLinkResultAddValue(String userId, String userName, String code, Boolean changeToEditable, AttLinkResult attLinkResult) {
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = userId;
            linkedAtt.text = userName;
            linkedAtt.changeToEditable = changeToEditable;
            attLinkResult.attMap.put(code, linkedAtt);
        }
    }

    /**
     * 默认项目code和流程中的字段匹配
     * @param key 默认项目岗位的code
     * @param postCodeMap 匹配关系map
     * @return 流程岗位的code
     */
    private static String getMapToCode(String key, Map<String, String> postCodeMap) {
        String value = "";
        for (String tmp : postCodeMap.keySet()){
            if (key.equals(tmp)){
                value = postCodeMap.get(tmp);
                break;
            }
        }
        return value;
    }

    /**
     * 默认岗位信息转MAP
     * @param list 源数据
     * @return map集合
     */
    private static Map<String, String> getPostMap(List<Map<String, Object>> list) {
        Map<String,String> map = new HashMap<>();
        for (Map<String, Object> tmp : list) {
            String code = JdbcMapUtil.getString(tmp,"code");
            String value = JdbcMapUtil.getString(tmp,"ad_user_id");
            map.put(code,value);
        }
        return map;
    }

    /**
     * 获取处理文件信息列表
     * @param linkedAtt 属性值
     */
    private static void getFileInfoList(LinkedAtt linkedAtt) {
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

    /**
     * 系统非系统属性联动-项目手填/多选项目/单选项目
     * @param attLinkResult 返回数据集
     * @param code 状态码 系统(system)，非系统(non_system)
     * @param entCode 业务表名
     */
    public static void autoLinkProject(AttLinkResult attLinkResult, String code, String entCode) {

        List<String> list = AttLinkDifferentProcess.sourceTypeLinkProjects();
        String project = "PM_PRJ_ID";
        if (list.contains(entCode)){
            project = "PM_PRJ_IDS";
        }

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
            attLinkResult.attMap.put(project, linkedAtt);
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
     * 流程项目带出人员信息
     * @param projectId 项目id
     * @param entCode 流程业务表名
     * @param companyId 业主单位id
     * @param attLinkResult 回显数据集
     * @param myJdbcTemplate 数据源
     */
    public static void processLinkUser(String projectId, String entCode, String companyId, AttLinkResult attLinkResult,MyJdbcTemplate myJdbcTemplate) {
        //查询该项目花名册所有人
        List<Map<String,Object>> list = LinkSql.prjRoster(projectId,companyId,myJdbcTemplate);
        if (!CollectionUtils.isEmpty(list)){
            if ("PM_BUY_DEMAND_REQ".equals(entCode)){ //采购需求回显逻辑
                Map<String,String> map = PmBuyDemandReqExt.POSTCODEMAP;
                //人员信息回显
                userForeach(list,map,attLinkResult,myJdbcTemplate);
            }
        }
    }

    /**
     * 项目花名册岗位人员信息在流程中回显
     * @param list 项目花名册信息
     * @param map 流程中岗位map信息
     * @param attLinkResult 返回回显的集合
     * @param myJdbcTemplate 数据源
     */
    private static void userForeach(List<Map<String, Object>> list, Map<String, String> map, AttLinkResult attLinkResult, MyJdbcTemplate myJdbcTemplate) {
        for (String key : map.keySet()){
            for (Map<String, Object> tmp : list) {
                String userId = JdbcMapUtil.getString(tmp,"ad_user_id");
                String postCode = JdbcMapUtil.getString(tmp,"postInfoCode");
                if (key.equals(postCode)){
                    //回显赋值
                    String userName = AdUserExt.getUserName(userId,myJdbcTemplate);
                    attLinkResultAddValue(userId,userName,key,false,attLinkResult);
                }
            }
        }

    }
}
