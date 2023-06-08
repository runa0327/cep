package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.enums.EntCodeEnum;
import com.cisdi.ext.link.AttLinkProcessDetail;
import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkSql;
import com.cisdi.ext.link.LinkedRecord;
import com.cisdi.ext.model.LinkedAttModel;
import com.cisdi.ext.model.PostInfo;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            AttLinkExtDetail.clearBaseProjectDataNOPrj(attLinkResult);
            AttLinkExtDetail.clearProjectAmtData(attLinkResult);

            if (!CollectionUtils.isEmpty(list)) {

                //赋值
                Map row = list.get(0);
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
                    AttLinkExtDetail.clearProcessPostUser(attLinkResult);
                    AttLinkExtDetail.autoPostUser(entCode,attValue,companyId,attLinkResult,myJdbcTemplate);
                }

                // 资金信息回显。优先级 可研估算<初设概算<预算财<项目结算
                List<String> amtList = AttLinkDifferentProcess.getAmtList();
                if (amtList.contains(entCode)) {
                    // 查询预算财评信息
                    Map resultRow1 = AttLinkExtDetail.getAmtMap(attValue, myJdbcTemplate);
                    AttLinkExtDetail.getResult(resultRow1, attLinkResult);
                }
            }
        }
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
            LinkUtils.mapAddValueByValue("BUY_MATTER_TYPE_ID",postName,postId,AttDataTypeE.TEXT_LONG,attLinkResult);
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
        for (LinkedAttModel tmp : list) {
            if (AttDataTypeE.FILE_GROUP == tmp.getType()) {
                AttLinkExtDetail.attLinkResultAddValue(tmp.getValue(), tmp.getKey(), tmp.getType(), attLinkResult);
            } else {
                AttLinkExtDetail.attLinkResultAddValue(tmp.getValue(), tmp.getValue(), tmp.getKey(), tmp.getType(), attLinkResult);
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
//        List<Map<String, Object>> list = LinkSql.getPrjNodeLevel3(projectId,myJdbcTemplate);
        List<Map<String, Object>> list = ProPlanUtils.sortLevel3(projectId);
        if (CollectionUtils.isEmpty(list)) {
            throw new BaseException("该项目不存在项目进度节点信息，请先处理！");
        } else {
            if ("1658641185226608640".equals(sevId)) {
                List<LinkedRecord> linkedRecordList = new ArrayList<>();
                for (Map<String, Object> tmp : list) {
                    LinkedRecord linkedRecord = new LinkedRecord();
                    //项目进度计划节点
                    linkedRecord.valueMap.put("PM_PRO_PLAN_NODE_ID", JdbcMapUtil.getString(tmp, "id"));
                    linkedRecord.textMap.put("PM_PRO_PLAN_NODE_ID", JdbcMapUtil.getString(tmp, "nodeName"));
                    //计划完成日期
                    linkedRecord.valueMap.put("PLAN_COMPL_DATE", JdbcMapUtil.getString(tmp, "PLAN_COMPL_DATE"));
                    linkedRecord.textMap.put("PLAN_COMPL_DATE", JdbcMapUtil.getString(tmp, "PLAN_COMPL_DATE"));
                    //操作类型
                    String OPREATION_TYPE = JdbcMapUtil.getString(tmp, "OPREATION_TYPE");
                    if (!SharedUtil.isEmptyString(OPREATION_TYPE) && ("del".equals(OPREATION_TYPE) || "DEL".equals(OPREATION_TYPE))){
                        linkedRecord.editableAttCodeList = new ArrayList<>();
                        linkedRecord.editableAttCodeList.add("OPREATION_TYPE");
                    } else {
                        linkedRecord.mandatoryAttCodeList = new ArrayList<>();
                        linkedRecord.mandatoryAttCodeList.add("PLAN_COMPL_DATE");
                    }

                    //TODO 操作类型字段返回  JdbcMapUtil.getString(tmp, "OPREATION_TYPE")

                    linkedRecordList.add(linkedRecord);
                }
                attLinkResult.childData.put("1658642775492775936", linkedRecordList);
                attLinkResult.childCreatable.put("1658642775492775936", true);
                attLinkResult.childClear.put("1658642775492775936", true);
            }
        }
    }
}
