package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.base.GrSetValueExt;
import com.cisdi.ext.enums.EntCodeEnum;
import com.cisdi.ext.link.*;
import com.cisdi.ext.link.AttLinkProcessDetail;
import com.cisdi.ext.model.HrDept;
import com.cisdi.ext.model.LinkedAttModel;
import com.cisdi.ext.model.PostInfo;
import com.cisdi.ext.model.base.PmPrj;
import com.cisdi.ext.pm.PmDeptExt;
import com.cisdi.ext.pm.PmRosterExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.cisdi.ext.util.ProPlanUtils;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 项目id属性联动
 */
public class PmPrjIdLink {

    /**
     * 概算回显对应map
     */
    public static final Map<String, String> INVEST2_MAP = new HashMap<>();

    static {
        INVEST2_MAP.put("PRJ_TOTAL_INVEST2", "PRJ_TOTAL_INVEST"); //总投资
        INVEST2_MAP.put("PROJECT_AMT_INVEST2", "PROJECT_AMT"); //工程费用
        INVEST2_MAP.put("CONSTRUCT_PRJ_AMT_INVEST2", "CONSTRUCT_AMT"); //建安工程费
        INVEST2_MAP.put("EQUIP_BUY_AMT_INVEST2", "EQUIP_AMT"); //设备采购费
        INVEST2_MAP.put("EQUIPMENT_COST_INVEST2", "EQUIPMENT_COST"); //科研设备费
        INVEST2_MAP.put("PROJECT_OTHER_AMT_INVEST2", "PROJECT_OTHER_AMT"); //工程其他费用
        INVEST2_MAP.put("LAND_AMT_INVEST2", "LAND_AMT"); //土地征拆费用
        INVEST2_MAP.put("PREPARE_AMT_INVEST2", "PREPARE_AMT"); //预备费
        INVEST2_MAP.put("CONSTRUCT_PERIOD_INVEST2", "CONSTRUCT_PERIOD_INTEREST"); //建设期利息
        INVEST2_MAP.put("REPLY_DATE_INVEST2", "REPLY_ACTUAL_DATE"); //批复日期
        INVEST2_MAP.put("REPLY_NO_INVEST2", "REPLY_NO_WR"); //批复文号
        INVEST2_MAP.put("REPLY_FILE_INVEST2", "REPLY_FILE"); //批复文件
    }

    /**
     * 财评回显对应map
     */
    public static final Map<String, String> INVEST3_MAP = new HashMap<>();

    static {
        INVEST3_MAP.put("PRJ_TOTAL_INVEST3", "PRJ_TOTAL_INVEST"); //总投资
        INVEST3_MAP.put("PROJECT_AMT_INVEST3", "PROJECT_AMT"); //工程费用
        INVEST3_MAP.put("CONSTRUCT_PRJ_AMT_INVEST3", "CONSTRUCT_AMT"); //建安工程费
        INVEST3_MAP.put("EQUIP_BUY_AMT_INVEST3", "EQUIP_AMT"); //设备采购费
        INVEST3_MAP.put("EQUIPMENT_COST_INVEST3", "EQUIPMENT_COST"); //科研设备费
        INVEST3_MAP.put("PROJECT_OTHER_AMT_INVEST3", "PROJECT_OTHER_AMT"); //工程其他费用
        INVEST3_MAP.put("LAND_AMT_INVEST3", "LAND_AMT"); //土地征拆费用
        INVEST3_MAP.put("PREPARE_AMT_INVEST3", "PREPARE_AMT"); //预备费
        INVEST3_MAP.put("CONSTRUCT_PERIOD_INVEST3", "CONSTRUCT_PERIOD_INTEREST"); //建设期利息
        INVEST3_MAP.put("REPLY_DATE_INVEST3", "FINANCIAL_ACTUAL_DATE"); //批复日期
        INVEST3_MAP.put("REPLY_NO_INVEST3", "REPLY_NO_WR"); //批复文号
        INVEST3_MAP.put("REPLY_FILE_INVEST3", "FINANCIAL_REVIEW_FILE"); //批复文件
    }

    /**
     * 项目id属性联动
     *
     * @param myJdbcTemplate 数据源
     * @param attValue       属性联动值
     * @param entCode        业务表名
     * @param sevId          实体视图id
     * @return 回显值
     */
    public static AttLinkResult linkForPM_PRJ_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId) {
        AttLinkResult attLinkResult = new AttLinkResult();
        if ("PM_POST_APPOINT".equals(entCode)) { //岗位指派单独查询项目信息逻辑
            AttLinkExtDetail.selectPostAppointLink(attLinkResult, attValue, myJdbcTemplate);
        } else {
            //查询项目相关信息
            List<Map<String, Object>> list = LinkSql.PmPrjIdLink(attValue, myJdbcTemplate);

            //清空项目基础信息
            AttLinkClear.clearBaseProjectDataNOPrj(attLinkResult);
            AttLinkClear.clearProjectAmtData(attLinkResult);

            if (!CollectionUtils.isEmpty(list)) {

                //赋值
                Map<String, Object> row = list.get(0);
                String companyId = JdbcMapUtil.getString(row,"customer_id");

                AttLinkExtDetail.assignmentAttLinkResult(attLinkResult,row,entCode,myJdbcTemplate);
                if ("PM_PRJ_REQ".equals(entCode)){ //立项申请
                    //回显项目启动的总投资
                    AttLinkExtDetail.linkPrjTotalInvest(attLinkResult, attValue);
                } else if ("PM_PRJ_KICK_OFF_REQ".equals(entCode)) { // 工程开工报审
                    AttLinkProcessDetail.pmPrjKickOffReqPrjLink(attLinkResult, attValue, myJdbcTemplate);
                } else if ("PM_BUY_DEMAND_REQ".equals(entCode) || "PIPELINE_RELOCATION_REQ".equals(entCode)) { // 采购需求审批 管线迁改
                    // 0099799190825080705 = 企业自筹
                    String id = JdbcMapUtil.getString(row, "INVESTMENT_SOURCE_ID");
                    AttLinkExtDetail.assignmentPrjYesNoOne(id, attLinkResult);
                } else if ("PM_PRJ_SETTLE_ACCOUNTS".equals(entCode)) { // 项目结算审批
                    AttLinkClear.clearSettleData(attLinkResult);
                    settlePrjLink(attLinkResult, attValue);
                } else if ("PM_EXTENSION_REQUEST_REQ".equals(entCode)) { // 节点延期申请
                    handlePrjNode(attValue, myJdbcTemplate);
                } else if ("PM_NODE_ADJUST_REQ".equals(entCode)) { // 全景计划展示表
                    nodeDetail(attValue, sevId, attLinkResult, myJdbcTemplate);
                }

                //需要自动岗位人员的流程
                List<String> processUserList = AttLinkDifferentProcess.getLinkUserProcess();
                if (processUserList.contains(entCode)) { //岗位自动人员
                    //清除人员岗位信息
                    AttLinkClear.clearProcessPostUser(attLinkResult);
                    AttLinkExtDetail.autoPostUser(entCode,attValue,companyId,attLinkResult,myJdbcTemplate);
                }

                // 资金信息回显。优先级 可研估算<初设概算<预算财<项目结算
                List<String> amtList = AttLinkDifferentProcess.getAmtList();
                if (amtList.contains(entCode)) {
                    Map<String,Object> resultRow1 = AttLinkExtDetail.getAmtMap(attValue, myJdbcTemplate);
                    AttLinkExtDetail.getResult(resultRow1, attLinkResult);
                }
            }
        }
        return attLinkResult;
    }

    /**
     * 项目id属性联动-多选项目属性联动
     *
     * @param myJdbcTemplate 数据源
     * @param attValue       属性联动值
     * @param entCode        业务表名
     * @param sevId          实体视图id
     * @return 回显值
     */
    public static AttLinkResult linkForPM_PRJ_IDS(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId) {
        AttLinkResult attLinkResult = new AttLinkResult();
        String[] prjIdArr = attValue.split(",");
        String projectId = prjIdArr[0];
        attLinkResult = linkForPM_PRJ_ID(myJdbcTemplate,projectId,entCode,sevId);
        return attLinkResult;
    }

    /**
     * 判断处理人员岗位信息
     * @param projectId 项目id
     * @param companyId 业主单位
     * @param attLinkResult 返回结果集
     * @param myJdbcTemplate 数据源
     */
    private static void checkUserPost(String projectId, String companyId, AttLinkResult attLinkResult, MyJdbcTemplate myJdbcTemplate) {
        String userId = ExtJarHelper.loginInfo.get().userId;
        //花名册取数岗位信息
        String postId = PmRosterExt.getUserPostIdByPrj(userId,projectId,companyId);
        if (SharedUtil.isEmptyString(postId)){
            //根据部门信息获取岗位信息
            String deptCode = PmDeptExt.getUserPostIdByDeptId(userId,companyId,myJdbcTemplate);
            if (!SharedUtil.isEmptyString(deptCode)){
                postId = PmDeptExt.getPostIdByDept(deptCode);
            }
        }
        //赋值回显
        if (!SharedUtil.isEmptyString(postId)){
            String postName = PostInfo.selectOneByWhere(new Where().eq(PostInfo.Cols.ID,postId)).getName();
            LinkUtils.mapAddAllValue("BUY_MATTER_TYPE_ID",AttDataTypeE.TEXT_LONG,postId,postName,false,false,false,attLinkResult);
        }
    }

    /**
     * 属性联动-项目计划进度节点-3级节点
     *
     * @param attValue       属性联动值(项目id)
     * @param myJdbcTemplate 数据源
     */
    public static void handlePrjNode(String attValue, MyJdbcTemplate myJdbcTemplate) {
        List<Map<String, Object>> list = LinkSql.selectPrjNode(attValue, "3", myJdbcTemplate);
        if (CollectionUtils.isEmpty(list)) {
            throw new BaseException("对不起，该项目不存在项目进度计划节点信息，请先维护进度计划或联系管理员处理！");
        }
    }

    /**
     * 项目结算审批-关联带出概算、财评资金信息
     *
     * @param attLinkResult 返回集合值
     * @param attValue      项目id
     */
    public static void settlePrjLink(AttLinkResult attLinkResult, String attValue) {
        List<LinkedAttModel> list = new ArrayList<>();
        //查询概算信息
        List<Map<String, Object>> list2 = Crud.from("PM_PRJ_INVEST2").where().eq("PM_PRJ_ID", attValue)
                .eq("STATUS", "AP").select().execForMapList();
        List<Map<String, Object>> list3 = Crud.from("PM_PRJ_INVEST3").where().eq("PM_PRJ_ID", attValue)
                .eq("STATUS", "AP").select().execForMapList();
        getList(list, list2, INVEST2_MAP);
        getList(list, list3, INVEST3_MAP);
        BigDecimal bigValue = new BigDecimal(0);
        for (LinkedAttModel tmp : list) {
            if (AttDataTypeE.FILE_GROUP == tmp.getType()) {
                AttLinkExtDetail.attLinkResultAddValue(tmp.getValue(), tmp.getKey(), tmp.getType(), attLinkResult);
            } else {
                String value = tmp.getValue();
                if (StringUtils.hasText(value)){
                    if (value.matches("[0-9]*\\.?[0-9]+")){
                        bigValue = StringUtils.hasText(value) ? new BigDecimal(value) : bigValue;
                        LinkUtils.mapAddAllValue(tmp.getKey(),AttDataTypeE.TEXT_LONG,bigValue,value,true,false,false,attLinkResult);
                    }
                } else {
                    AttLinkExtDetail.attLinkResultAddValue(value, value, tmp.getKey(), tmp.getType(), attLinkResult);
                }
//                if (value.matches("[0-9]*\\.?[0-9]+")){
//                    bigValue = StringUtils.hasText(value) ? new BigDecimal(value) : bigValue;
//                    LinkUtils.mapAddAllValue(tmp.getKey(),AttDataTypeE.TEXT_LONG,bigValue,value,true,false,false,attLinkResult);
//                } else {
//                    AttLinkExtDetail.attLinkResultAddValue(value, value, tmp.getKey(), tmp.getType(), attLinkResult);
//                }
            }
        }
        //历史结算信息汇总模块 处理显示
        LinkUtils.mapAddValueNull("PRJ_TOTAL_INVEST", AttDataTypeE.DOUBLE, attLinkResult); //总投资
        AttLinkExtDetail.settleAmtHistory(attValue, attLinkResult);
    }

    /**
     * @param list       最终处理list
     * @param list2      流程业务list
     * @param invest2Map 对应关系map
     */
    private static void getList(List<LinkedAttModel> list, List<Map<String, Object>> list2, Map<String, String> invest2Map) {
        if (!CollectionUtils.isEmpty(list2)) {
            for (String key : invest2Map.keySet()) {
                LinkedAttModel linkedAttModel = new LinkedAttModel();
                String code = invest2Map.get(key);
                String value = JdbcMapUtil.getString(list2.get(0), code);
                AttDataTypeE dataTypeE = getCodeType(key);
                linkedAttModel.setType(dataTypeE);
                linkedAttModel.setKey(key);
                if (!SharedUtil.isEmptyString(value)) {
                    linkedAttModel.setValue(value);
                }
                list.add(linkedAttModel);
            }
        }
    }

    /**
     * 获取回显字段数据
     *
     * @param value 回显字段key
     * @return 类型
     */
    private static AttDataTypeE getCodeType(String value) {
        AttDataTypeE type = null;
        for (String key : EntCodeEnum.SETTLE_MAP.keySet()) {
            if (value.equals(key)) {
                type = (AttDataTypeE) EntCodeEnum.SETTLE_MAP.get(key);
            }
        }
        return type;
    }

    /**
     * 全景计划展示表-节点明细
     *
     * @param projectId      项目id
     * @param sevId          实体视图id
     * @param attLinkResult  返回结果集
     * @param myJdbcTemplate 数据源
     */
    public static void nodeDetail(String projectId, String sevId, AttLinkResult attLinkResult, MyJdbcTemplate myJdbcTemplate) {
//        List<Map<String,Object>> list = ProPlanUtils.queryLevel3HaveNodeType(projectId,myJdbcTemplate); // 里程碑、一级节点等重要节点
        List<Map<String, Object>> list1 = ProPlanUtils.queryLevel3(projectId,myJdbcTemplate); // 所有节点
        if (CollectionUtils.isEmpty(list1)) {
            throw new BaseException("该项目不存在项目进度节点信息，请先处理！");
        }
        if ("1658641185226608640".equals(sevId)) {
            PmPrj pmPrj = PmPrj.selectById(projectId);
            // 核心节点处理
//            mainNode(list,pmPrj,"1720257325147308032",attLinkResult);
            mainNode(list1,pmPrj,attLinkResult);

        }

    }

    /**
     * 核心节点明细赋值
     * @param list 数据详情
     * @param pmPrj 项目项目
     * @param attLinkResult 返回属性联动值
     */
    private static void mainNode(List<Map<String, Object>> list, PmPrj pmPrj, AttLinkResult attLinkResult) {
        String tenderId = pmPrj.getTenderModeId(); // 招标类型 1640259853484171264=EPC招标 1640259929484959744=清单招标
        List<LinkedRecord> linkedRecordList = new ArrayList<>();
        List<LinkedRecord> linkedRecordList2 = new ArrayList<>(); // 核心节点
        for (Map<String, Object> tmp : list) {
            String typeName = JdbcMapUtil.getString(tmp,"typeName");
            LinkedRecord linkedRecord = new LinkedRecord();
            linkedRecordValue(linkedRecord, tmp, typeName,tenderId);
            linkedRecordList.add(linkedRecord);
            if (StringUtils.hasText(typeName)){
                linkedRecordList2.add(linkedRecord);
            }
        }
        childData(linkedRecordList2,"1720257325147308032",attLinkResult);
        childData(linkedRecordList,"1658642775492775936",attLinkResult);
    }

    /**
     * 明细节点赋值
     * @param list 数据值
     * @param viewId 视图下挂明细视图模块
     * @param attLinkResult 属性联动返回值
     */
    private static void childData(List<LinkedRecord> list, String viewId, AttLinkResult attLinkResult) {
        attLinkResult.childData.put(viewId, list);
        attLinkResult.childCreatable.put(viewId, true);
        attLinkResult.childClear.put(viewId, true);
    }

    /**
     * 单条数据判断处理
     * @param linkedRecord 返回结果
     * @param tmp 原始数据
     * @param typeName 节点状态类型名称
     * @param tenderId 招标类型 1640259853484171264=EPC招标 1640259929484959744=清单招标
     */
    private static void linkedRecordValue(LinkedRecord linkedRecord, Map<String, Object> tmp, String typeName,String tenderId) {
        String planName = JdbcMapUtil.getString(tmp, "nodeName");
        planName = checkNodeName(planName,tenderId,typeName);

        //计划完成日期
        linkedRecord.valueMap.put("PLAN_COMPL_DATE", JdbcMapUtil.getString(tmp, "PLAN_COMPL_DATE"));
        linkedRecord.textMap.put("PLAN_COMPL_DATE", JdbcMapUtil.getString(tmp, "PLAN_COMPL_DATE"));
        //操作类型
        String OPREATION_TYPE = JdbcMapUtil.getString(tmp, "OPREATION_TYPE");
        if (!SharedUtil.isEmptyString(OPREATION_TYPE) && ("del".equals(OPREATION_TYPE) || "DEL".equals(OPREATION_TYPE))){
            linkedRecord.editableAttCodeList = new ArrayList<>();
            linkedRecord.editableAttCodeList.add("OPREATION_TYPE");
            planName = "待删除："+planName;
        } else {
            if ("add".equals(OPREATION_TYPE) || "ADD".equals(OPREATION_TYPE)){
                planName = "待新增："+planName;
            }
            linkedRecord.mandatoryAttCodeList = new ArrayList<>();
            linkedRecord.mandatoryAttCodeList.add("PLAN_COMPL_DATE");
        }

        //项目进度计划节点
        linkedRecord.valueMap.put("PM_PRO_PLAN_NODE_ID", JdbcMapUtil.getString(tmp, "id"));
        linkedRecord.textMap.put("PM_PRO_PLAN_NODE_ID", planName);

        linkedRecord.textMap.put("NAME_ONE", planName);
        linkedRecord.valueMap.put("NAME_ONE", planName);
        if (planName.contains("里程碑") || planName.contains("一级节点")){
            linkedRecord.valueMap.put("IS_DISCOLORED", "1");
            linkedRecord.textMap.put("IS_DISCOLORED", "1");
        }
    }

    /**
     * 节点名称根据不同逻辑单独处理
     * @param planName 节点名称
     * @param tenderId 招标类型 1640259853484171264=EPC招标 1640259929484959744=清单招标
     * @param typeName 节点状态类型名称
     * @return 节点名称
     */
    private static String checkNodeName(String planName, String tenderId, String typeName) {
        if ("初设概算批复".equals(planName)){
            if ("1640259853484171264".equals(tenderId)){ // EPC招标
                planName = planName + "(里程碑)";
            } else {
                planName = planName + "(一级节点)";
            }
        } else if ("预算编制".equals(planName)){
            if ("1640259853484171264".equals(tenderId)){ // EPC招标
                planName = planName + "(一级节点)";
            } else {
                planName = planName + "(里程碑)";
            }
        } else if ("预算批复".equals(planName)){
            if ("1640259853484171264".equals(tenderId)){ // EPC招标
                planName = planName + "(一级节点)";
            } else {
                planName = planName + "(里程碑)";
            }
        } else if ("初概批复".equals(planName)){
            if ("1640259853484171264".equals(tenderId)){ // EPC招标
                planName = planName + "(里程碑)";
            } else {
                planName = planName + "(一级节点)";
            }
        } else {
            if (StringUtils.hasText(typeName)){
                planName = planName + "(" + typeName + ")";
            }
        }
        return planName;
    }

    /**
     * 项目名称及内部管理单位赋值
     * @param prjIds 项目ids(需提前判断是否为空)
     * @param projectId 项目id(查询内部管理单位)
     * @param attLinkResult 联动返回值
     * @param myJdbcTemplate 数据源
     */
    public static void prjNameAndCompanyValue(String prjIds,String projectId, AttLinkResult attLinkResult, MyJdbcTemplate myJdbcTemplate) {
        String projectIds = prjIds.replace(",","','");
        String projectName = JdbcMapUtil.getString(myJdbcTemplate.queryForList("select group_concat(name) as name from pm_prj where id in ('"+projectIds+"')").get(0),"name");
        PmPrj pmPrj = PmPrj.selectById(projectId);
        if (pmPrj != null){
            String companyId = pmPrj.getCompanyId();
            if (StringUtils.hasText(companyId)){
                String companyName = HrDept.selectById(companyId).getName();
                LinkUtils.mapAddAllValue("COMPANY_ID", AttDataTypeE.TEXT_LONG,companyId,companyName,true,true,false,attLinkResult);
            }
        }
        LinkUtils.mapAddAllValue("PM_PRJ_IDS",AttDataTypeE.TEXT_LONG,prjIds,projectName,true,true,false,attLinkResult);
    }

    /**
     * 项目id属性联动
     *
     * @param myJdbcTemplate 数据源
     * @param attValue       属性联动值
     * @param entCode        业务表名
     * @return 回显值
     */
    public static AttLinkResult linkForAMOUT_PM_PRJ_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode) {
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
            return attLinkResult;
        }
        Map<String,Object> row = list.get(0);

        // 回显项目信息
        String prjCode = JdbcMapUtil.getString(row, "prj_code"); // 项目编号
        LinkUtils.mapAddAllValue("PRJ_CODE",AttDataTypeE.TEXT_LONG,prjCode,prjCode,true,true,false,attLinkResult);

        // 项目批复文号
        Map<String, Object> resultRow = getReplyNo(attValue);
        String replyNo = JdbcMapUtil.getString(resultRow, "REPLY_NO_WR");
        LinkUtils.mapAddAllValue("PRJ_REPLY_NO",AttDataTypeE.TEXT_LONG,replyNo,replyNo,true,true,false,attLinkResult);
        LinkUtils.mapAddAllValue("REPLY_NO",AttDataTypeE.TEXT_LONG,replyNo,replyNo,true,true,false,attLinkResult);

        String prjSituation = JdbcMapUtil.getString(row, "PRJ_SITUATION"); // 项目介绍
        LinkUtils.mapAddAllValue("PRJ_SITUATION",AttDataTypeE.TEXT_LONG,prjSituation,prjSituation,true,true,false,attLinkResult);

        String investSourceId = JdbcMapUtil.getString(row, "INVESTMENT_SOURCE_ID"); // 资金来源
        LinkUtils.mapAddAllValue("PM_FUND_SOURCE_ID",AttDataTypeE.TEXT_LONG,investSourceId,investSourceId,true,true,false,attLinkResult);
        LinkUtils.mapAddAllValue("INVESTMENT_SOURCE_ID",AttDataTypeE.TEXT_LONG,investSourceId,investSourceId,true,true,false,attLinkResult);

        String customerId = JdbcMapUtil.getString(row, "customer_id"); // 业主单位
        String customerName = JdbcMapUtil.getString(row, "customer_name");
        LinkUtils.mapAddAllValue("CUSTOMER_UNIT",AttDataTypeE.TEXT_LONG,customerId,customerName,true,true,false,attLinkResult);

        String projectTypeId = JdbcMapUtil.getString(row, "pt_id"); // 项目类型
        String projectTypeName = JdbcMapUtil.getString(row, "pt_name");
        LinkUtils.mapAddAllValue("PROJECT_TYPE_ID",AttDataTypeE.TEXT_LONG,projectTypeId,projectTypeName,true,true,false,attLinkResult);

        if ("PO_ORDER_PAYMENT_REQ".equals(entCode)) { // 采购合同付款申请
            prjLinkPaymentReq(attValue,attLinkResult,myJdbcTemplate);
        } else if ("PM_FUND_REQUIRE_PLAN_REQ".equals(entCode)) { // 资金需求计划申请
            prjLinkFundPlanReq(attValue,row,attLinkResult,myJdbcTemplate);
        } else if ("SKILL_DISCLOSURE_PAPER_RECHECK_RECORD".equals(entCode) || "PM_DESIGN_ASSIGNMENT_BOOK".equals(entCode)) {
            // SKILL_DISCLOSURE_PAPER_RECHECK_RECORD 技术交底与图纸会审记录 PM_DESIGN_ASSIGNMENT_BOOK 设计任务书
            Map<String, Object> resultRow1 = getAmtMap(attValue);
            getResult(resultRow1, attLinkResult);
        }
        return attLinkResult;
    }

    /**
     * 项目属性联动-资金需求计划申请
     * @param attValue 属性联动值
     * @param row 项目基础信息
     * @param attLinkResult 属性联动结果集
     * @param myJdbcTemplate 数据源
     */
    private static void prjLinkFundPlanReq(String attValue, Map<String,Object> row, AttLinkResult attLinkResult, MyJdbcTemplate myJdbcTemplate) {
        // 查询关联合同信息
        List<Map<String, Object>> contractMaps = myJdbcTemplate.queryForList("select o.CONTRACT_PRICE,a.PRJ_TOTAL_INVEST estimate,b.PRJ_TOTAL_INVEST budget from PO_ORDER_REQ " +
                "o left join PM_PRJ_INVEST2 a on a.PM_PRJ_ID =o.PM_PRJ_ID left join PM_PRJ_INVEST3 b on b" +
                ".PM_PRJ_ID = o.PM_PRJ_ID where o.PM_PRJ_ID = ? and o.`STATUS` = 'AP' order by o.CRT_DT " +
                "limit 1", attValue);
        if (!CollectionUtils.isEmpty(contractMaps)) {
            Map<String, Object> contractRow = contractMaps.get(0);

            String estimateAmt = JdbcMapUtil.getString(contractRow, "estimate"); // 概算金额
            LinkUtils.mapAddAllValue("ESTIMATED_AMOUNT",AttDataTypeE.TEXT_LONG,estimateAmt,estimateAmt,true,true,false,attLinkResult);

            String budgetAmt = JdbcMapUtil.getString(contractRow, "budget"); // 预算金额
            LinkUtils.mapAddAllValue("FINANCIAL_AMOUNT",AttDataTypeE.TEXT_LONG,budgetAmt,budgetAmt,true,true,false,attLinkResult);
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
                List<String> keyList = AttLinkDifferentProcess.getKeyList();
                Set<String> keys = reqRow.keySet();
                for (String key : keys) {
                    String id = JdbcMapUtil.getString(reqRow, key);
                    String name = id;
                    if (keyList.contains(key)) {// 是否财政预算
                        name = GrSetValueExt.getValueNameById(id);
                    }
                    LinkUtils.mapAddAllValue(key,AttDataTypeE.TEXT_LONG,id,name,true,true,true,attLinkResult);
                }
            }

            // 可研完成情况
        } else {// 未填写 根据项目回显

            String year = JdbcMapUtil.getString(row, "PRJ_REPLY_DATE"); // 立项年度
            LinkUtils.mapAddAllValue("YEAR",AttDataTypeE.TEXT_LONG,year,year,true,true,false,attLinkResult);

            String locationId = JdbcMapUtil.getString(row, "l_id"); // 建设地点
            String locationName = JdbcMapUtil.getString(row, "l_name");
            LinkUtils.mapAddAllValue("BASE_LOCATION_ID", AttDataTypeE.TEXT_LONG,locationId,locationName,true,true,false,attLinkResult);

            // 查询五方
            List<Map<String, Object>> partyMaps = myJdbcTemplate.queryForList("SELECT r.BUILD_UNIT_RESPONSE,r.AGENT_PHONE FROM PM_PRJ_PARTY_REQ r left join pm_prj p on p.id = r.PM_PRJ_ID where p.id = ? and r.`STATUS` = 'AP' ORDER BY r.CRT_DT desc LIMIT 1", attValue);
            if (!CollectionUtils.isEmpty(partyMaps)) {
                Map<String, Object> partyRow = partyMaps.get(0);

                String unitPerson = JdbcMapUtil.getString(partyRow, "BUILD_UNIT_RESPONSE"); // 项目负责人
                LinkUtils.mapAddAllValue("AGENT_BUILD_UNIT_RESPONSE",AttDataTypeE.TEXT_LONG,unitPerson,unitPerson,true,true,false,attLinkResult);

                String userPhone = JdbcMapUtil.getString(partyRow, "AGENT_PHONE"); // 负责人联系电话
                LinkUtils.mapAddAllValue("AGENT_BUILD_UNIT_RESPONSE_PHONE",AttDataTypeE.TEXT_LONG,userPhone,userPhone,true,true,false,attLinkResult);
            }
            LinkUtils.mapAddAllValue("DEMOLITION_COMPLETED",AttDataTypeE.TEXT_LONG,(String) null,null,true,true,false,attLinkResult); // 征地拆迁完成情况
            LinkUtils.mapAddAllValue("PLAN_START_DATE",AttDataTypeE.TEXT_LONG,(String) null,null,true,true,false,attLinkResult); // 预计开工时间
            LinkUtils.mapAddAllValue("PLAN_COMPL_DATE",AttDataTypeE.TEXT_LONG,(String) null,null,true,true,false,attLinkResult); // 预计完工时间
            LinkUtils.mapAddAllValue("ACTUAL_START_DATE",AttDataTypeE.TEXT_LONG,(String) null,null,true,true,false,attLinkResult); // 实际开工时间

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
                    LinkUtils.mapAddAllValue("CREATE_PROJECT_COMPLETED",AttDataTypeE.TEXT_LONG,id,valueName,true,true,false,attLinkResult); // 立项完成情况
                } else if (judgeMatch(feasibility, name)) {
                    LinkUtils.mapAddAllValue("FEASIBILITY_COMPLETED",AttDataTypeE.TEXT_LONG,id,valueName,true,true,false,attLinkResult); // 可研完成情况
                } else if (judgeMatch(landUsePlan, name)) {
                    LinkUtils.mapAddAllValue("SELECT_SITE_COMPLETED",AttDataTypeE.TEXT_LONG,id,valueName,true,true,false,attLinkResult); // 规划选址完成情况
                } else if (judgeMatch(eia, name)) {
                    LinkUtils.mapAddAllValue("EIA_COMPLETED",AttDataTypeE.TEXT_LONG,id,valueName,true,true,false,attLinkResult); // 环评完成情况
                } else if (judgeMatch(advanceExam, name)) {
                    LinkUtils.mapAddAllValue("USE_LAND_COMPLETED",AttDataTypeE.TEXT_LONG,id,valueName,true,true,false,attLinkResult); // 用地预审
                } else if (judgeMatch(save, name)) {
                    LinkUtils.mapAddAllValue("WOODLAND_WATER_SOIL_COMPLETED",AttDataTypeE.TEXT_LONG,id,valueName,true,true,false,attLinkResult); // 用地预审
                } else if ("初步设计概算批复".equals(name)) {
                    LinkUtils.mapAddAllValue("ESTIMATE_COMPLETED",AttDataTypeE.TEXT_LONG,id,valueName,true,true,false,attLinkResult); // 概算完成情况
                } else if ("预算财政评审".equals(name)) {
                    LinkUtils.mapAddAllValue("BUDGET_REVIEW_COMPLETED",AttDataTypeE.TEXT_LONG,id,valueName,true,true,false,attLinkResult); // 预算评审完成情况
                } else if (judgeMatch(prj, name)) {
                    LinkUtils.mapAddAllValue("CONSTRUCT_BID_COMPLETED",AttDataTypeE.TEXT_LONG,id,valueName,true,true,false,attLinkResult); // 预算评审完成情况
                }
            }
        }

        //概算批复文件信息回显
        String sql1 = "select REPLY_FILE from PM_PRJ_INVEST2 where PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,attValue);
        if (!CollectionUtils.isEmpty(list1)){
            String fileId = JdbcMapUtil.getString(list1.get(0),"REPLY_FILE");
            LinkUtils.mapAddValueByValueFile("REPLY_FILE",fileId,fileId,true,true,true,AttDataTypeE.FILE_GROUP,attLinkResult);
        }
        //预算批复文件信息回显
        String sql2 = "select FINANCIAL_REVIEW_FILE from PM_PRJ_INVEST3 where PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,attValue);
        if (!CollectionUtils.isEmpty(list2)){
            String fileId = JdbcMapUtil.getString(list2.get(0),"FINANCIAL_REVIEW_FILE");
            LinkUtils.mapAddValueByValueFile("CONSERVATION_REPLY_FILE",fileId,fileId,true,true,true,AttDataTypeE.FILE_GROUP,attLinkResult);
        }
        //施工中标通知书（招采里的中标通知书）
        String sql3 = "SELECT GROUP_CONCAT(BID_WIN_NOTICE_FILE_GROUP_ID) AS FILE from PO_PUBLIC_BID_REQ WHERE PM_PRJ_ID = ? AND PMS_RELEASE_WAY_ID in ('0099799190825080728','0099799190825080731') and status = 'AP'";
        List<Map<String,Object>> list3 = myJdbcTemplate.queryForList(sql3,attValue);
        if (!CollectionUtils.isEmpty(list3)){
            String fileId = JdbcMapUtil.getString(list3.get(0),"FILE");
            LinkUtils.mapAddValueByValueFile("BID_WIN_NOTICE_FILE_GROUP_ID",fileId,fileId,true,true,true,AttDataTypeE.FILE_GROUP,attLinkResult);
        }
    }

    /**
     * 项目属性联动-采购合同付款申请
     * @param attValue 属性联动值
     * @param attLinkResult 属性联动结果集
     * @param myJdbcTemplate 数据源
     */
    private static void prjLinkPaymentReq(String attValue, AttLinkResult attLinkResult, MyJdbcTemplate myJdbcTemplate) {
        // 查询付款申请历史信息
        String sql = "SELECT COLLECTION_DEPT_TWO,BANK_OF_DEPOSIT,ACCOUNT_NO,RECEIPT,SPECIAL_BANK_OF_DEPOSIT,SPECIAL_ACCOUNT_NO FROM PO_ORDER_PAYMENT_REQ WHERE AMOUT_PM_PRJ_ID = ? AND STATUS = 'AP' ORDER BY CRT_DT DESC limit 1";
        List<Map<String, Object>> map1 = myJdbcTemplate.queryForList(sql, attValue);
        if (!CollectionUtils.isEmpty(map1)) {
            Map<String,Object> row2 = map1.get(0);

            String collectionDept = JdbcMapUtil.getString(row2, "COLLECTION_DEPT_TWO"); // 收款单位
            LinkUtils.mapAddAllValue("COLLECTION_DEPT_TWO",AttDataTypeE.TEXT_LONG,collectionDept,collectionDept,true,true,false,attLinkResult);

            String bank = JdbcMapUtil.getString(row2, "BANK_OF_DEPOSIT"); // 开户行
            LinkUtils.mapAddAllValue("BANK_OF_DEPOSIT",AttDataTypeE.TEXT_LONG,bank,bank,true,true,false,attLinkResult);

            String accountNo = JdbcMapUtil.getString(row2, "ACCOUNT_NO"); // 账号
            LinkUtils.mapAddAllValue("ACCOUNT_NO",AttDataTypeE.TEXT_LONG,accountNo,accountNo,true,true,false,attLinkResult);

            String receipt = JdbcMapUtil.getString(row2, "RECEIPT"); // 农民工工资专用账号收款单位
            LinkUtils.mapAddAllValue("RECEIPT",AttDataTypeE.TEXT_LONG,receipt,receipt,true,true,false,attLinkResult);

            String specialBank = JdbcMapUtil.getString(row2, "SPECIAL_BANK_OF_DEPOSIT"); // 专户开户行
            LinkUtils.mapAddAllValue("SPECIAL_BANK_OF_DEPOSIT",AttDataTypeE.TEXT_LONG,specialBank,specialBank,true,true,false,attLinkResult);

            String specialAccountNo = JdbcMapUtil.getString(row2, "SPECIAL_ACCOUNT_NO"); // 专户账号
            LinkUtils.mapAddAllValue("SPECIAL_ACCOUNT_NO",AttDataTypeE.TEXT_LONG,specialAccountNo,specialAccountNo,true,true,false,attLinkResult);
        }
    }

    /**
     * 属性赋值
     * @param stringObjectMap 查询结果键值对
     * @param attLinkResult 属性联动返回值
     */
    private static void getResult(Map<String, Object> stringObjectMap, AttLinkResult attLinkResult) {

        String prjTotalAmt = JdbcMapUtil.getString(stringObjectMap, "PRJ_TOTAL_INVEST"); // 总投资
        if (StringUtils.hasText(prjTotalAmt)){
            LinkUtils.mapAddAllValue("PRJ_TOTAL_INVEST",AttDataTypeE.TEXT_LONG,new BigDecimal(prjTotalAmt),prjTotalAmt,true,true,false,attLinkResult);
        } else {
            LinkUtils.mapAddAllValue("PRJ_TOTAL_INVEST",AttDataTypeE.TEXT_LONG,(String) null,prjTotalAmt,true,true,false,attLinkResult);
        }

        String projectAmt = JdbcMapUtil.getString(stringObjectMap, "PROJECT_AMT"); // 工程费用
        if (StringUtils.hasText(projectAmt)){
            LinkUtils.mapAddAllValue("PROJECT_AMT",AttDataTypeE.TEXT_LONG,new BigDecimal(projectAmt),projectAmt,true,true,false,attLinkResult);
        } else {
            LinkUtils.mapAddAllValue("PROJECT_AMT",AttDataTypeE.TEXT_LONG,(String) null,projectAmt,true,true,false,attLinkResult);
        }

        String projectOtherAmt = JdbcMapUtil.getString(stringObjectMap, "PROJECT_OTHER_AMT"); // 工程建设其他费用
        if (StringUtils.hasText(projectOtherAmt)){
            LinkUtils.mapAddAllValue("PROJECT_OTHER_AMT",AttDataTypeE.TEXT_LONG,new BigDecimal(projectOtherAmt),projectOtherAmt,true,true,false,attLinkResult);
        } else {
            LinkUtils.mapAddAllValue("PROJECT_OTHER_AMT",AttDataTypeE.TEXT_LONG,(String) null,projectOtherAmt,true,true,false,attLinkResult);
        }

        String prepareAmt = JdbcMapUtil.getString(stringObjectMap, "PREPARE_AMT"); // 预备费
        if (StringUtils.hasText(prepareAmt)){
            LinkUtils.mapAddAllValue("PREPARE_AMT",AttDataTypeE.TEXT_LONG,new BigDecimal(prepareAmt),prepareAmt,true,true,false,attLinkResult);
        } else {
            LinkUtils.mapAddAllValue("PREPARE_AMT",AttDataTypeE.TEXT_LONG,(String) null,prepareAmt,true,true,false,attLinkResult);
        }

        String periodAmt = JdbcMapUtil.getString(stringObjectMap, "CONSTRUCT_PERIOD_INTEREST"); // 利息
        if (StringUtils.hasText(periodAmt)){
            LinkUtils.mapAddAllValue("CONSTRUCT_PERIOD_INTEREST",AttDataTypeE.TEXT_LONG,new BigDecimal(periodAmt),periodAmt,true,true,false,attLinkResult);
        } else {
            LinkUtils.mapAddAllValue("CONSTRUCT_PERIOD_INTEREST",AttDataTypeE.TEXT_LONG,(String) null,periodAmt,true,true,false,attLinkResult);
        }

        String replyNo = JdbcMapUtil.getString(stringObjectMap, "REPLY_NO_WR"); //批复文号
        LinkUtils.mapAddAllValue("REPLY_NO",AttDataTypeE.TEXT_LONG,replyNo,replyNo,true,true,false,attLinkResult);
        LinkUtils.mapAddAllValue("PRJ_REPLY_NO",AttDataTypeE.TEXT_LONG,replyNo,replyNo,true,true,false,attLinkResult);

    }

    /**
     * 查询可研估算<初设概算<预算财评优先级最高的数据
     * @param attValue 属性联动值
     * @return 查询结果
     */
    private static Map<String, Object> getAmtMap(String attValue) {
        Map<String, Object> resultRow = new HashMap<>();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql1 = "SELECT REPLY_NO_WR,PRJ_TOTAL_INVEST ,PROJECT_AMT, CONSTRUCT_AMT," +
                " PROJECT_OTHER_AMT, PREPARE_AMT, CONSTRUCT_PERIOD_INTEREST FROM PM_PRJ_INVEST3 " +
                "WHERE PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
        List<Map<String, Object>> map = myJdbcTemplate.queryForList(sql1, attValue);
        List<Map<String, Object>> map1;
        List<Map<String, Object>> map2;
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

    /**
     * 名称匹配
     * @param words 总名称
     * @param name 节点名称
     * @return 匹配结果
     */
    private static boolean judgeMatch(List<String> words, String name) {
        for (String word : words) {
            if (name.contains(word)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 默认不涉及
     * @param attLinkResult 属性联动返回结果集
     */
    private static void initNUll(AttLinkResult attLinkResult) {
        List<String> fields = Arrays.asList("CREATE_PROJECT_COMPLETED", "FEASIBILITY_COMPLETED",
                "SELECT_SITE_COMPLETED", "EIA_COMPLETED", "USE_LAND_COMPLETED", "WOODLAND_WATER_SOIL_COMPLETED",
                "ESTIMATE_COMPLETED", "BUDGET_REVIEW_COMPLETED", "CONSTRUCT_BID_COMPLETED");
        for (String field : fields) {
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = "0099902212142036278";
                linkedAtt.text = "未涉及";
                attLinkResult.attMap.put(field, linkedAtt);
            }
        }
    }

    /**
     * 查询可研估算<初设概算<预算财评优先级最高的数据  查询文号
     * @param attValue 属性值
     * @return 各个级别流程信息
     */
    private static Map<String, Object> getReplyNo(String attValue) {
        Map<String, Object> resultRow = new HashMap<>();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql1 = "SELECT REPLY_NO_WR FROM PM_PRJ_INVEST3 WHERE PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
        List<Map<String, Object>> map = myJdbcTemplate.queryForList(sql1, attValue);
        List<Map<String, Object>> map1;
        List<Map<String, Object>> map2;
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
}
