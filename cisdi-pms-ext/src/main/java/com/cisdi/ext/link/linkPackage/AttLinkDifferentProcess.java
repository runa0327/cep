package com.cisdi.ext.link.linkPackage;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 属性联动分支判断各类流程
 */
public class AttLinkDifferentProcess {

    // 默认自动带出成本岗的流程
    public static List<String> getAutoCostUser() {
        List<String> list = new ArrayList<>();
//        list.add("PO_ORDER_CHANGE_REQ"); //合同需求审批,不默认带出
        return list;
    }

    /**
     * 合同相关流程有同一相同标题生码规则
     * @return 涉及流程
     */
    public static List<String> getOrderProcessName() {
        List<String> list = new ArrayList<>();
        list.add("PO_ORDER_TERMINATE_REQ"); //合同终止
        list.add("PO_ORDER_SUPPLEMENT_REQ"); //补充协议
        list.add("PO_ORDER_CHANGE_REQ"); //合同需求变更
        return list;
    }

    // 特殊流程，名称在流程发起时即生成，此处不生成
    public static List<String> getSpecialList() {
        List<String> list = new ArrayList<>();
        list.add("PM_BUY_DEMAND_REQ"); //采购需求审批
        list.add("PM_BID_APPROVAL_REQ"); //招标文件审批
        list.add("PM_USE_CHAPTER_REQ"); //中选单位及标后用印申请
        list.add("PO_ORDER_REQ"); //合同签订
        list.add("PM_SUPERVISE_PLAN_REQ"); // 监理规划及细则申请
        list.add("QUALITY_RECORD"); // 质量交底记录
        list.add("PM_SUPERVISE_NOTICE_REQ"); // 监理通知单
        return list;
    }

    /**
     * 流程中需要自定义标题的流程
     * @return 涉及流程
     */
    public static List<String> getTableList() {
        List<String> list = new ArrayList<>();
        list.add("PM_PRJ_REQ"); // 立项申请
        list.add("PM_PRJ_INVEST1"); // 可研估算
        list.add("PM_PRJ_INVEST2"); // 初设概算
//        list.add("PM_PRJ_INVEST3"); // 预算财评
        list.add("PM_STABLE_EVAL"); // 社会稳定性评价
        list.add("PM_ENERGY_EVAL"); // 固定资产投资节能评价
        list.add("PM_WATER_PLAN"); // 水保方案
        list.add("PM_ENVIRONMENT_EVAL"); // 环评
//        list.add("PO_ORDER_REQ"); // 采购合同签订申请
        list.add("PO_PUBLIC_BID_REQ"); // 采购公开招标申请
        list.add("PM_CONSTRUCT_PERMIT_REQ"); // 施工许可
        list.add("PM_PRJ_PLANNING_PERMIT_REQ"); // 工程规划许可
        list.add("PO_GUARANTEE_LETTER_REQUIRE_REQ"); // 新增保函申请
        list.add("PO_GUARANTEE_LETTER_RETURN_OA_REQ"); // 保函退还申请
//        list.add("PO_ORDER_SUPPLEMENT_REQ"); // 采购合同补充协议申请
//        list.add("PO_ORDER_TERMINATE_REQ"); // 采购合同终止申请
        list.add("PO_ORDER_CHANGE_REQ"); // 采购合同变更申请
        list.add("PM_PRJ_PARTY_REQ"); // 五方责任主体维护申请
        list.add("PM_SUPERVISE_PLAN_REQ"); // 监理规划及细则申请
        list.add("PM_PRJ_KICK_OFF_REQ"); // 工程开工申请
        list.add("PM_FUND_REQUIRE_PLAN_REQ"); // 资金需求计划申请
        list.add("PO_ORDER_PAYMENT_REQ"); // 采购合同付款申请
        list.add("SKILL_DISCLOSURE_PAPER_RECHECK_RECORD"); // 技术交底与图纸会审记录
        list.add("PM_CONCEPTUAL_SCHEME_DESIGN"); // 概念方案设计管理
//        list.add("PM_CONSTRUCTION_DRAWING_DESIGN"); // 施工图设计管理
//        list.add("PM_DESIGN_ASSIGNMENT"); // 方案设计管理
        list.add("PM_DESIGN_ASSIGNMENT_BOOK"); // 设计任务书
        list.add("PM_FARMING_PROCEDURES"); // 农转用手续办理
        list.add("PM_WOODLAND_PROCEDURES"); // 林地调整办理手续
        list.add("PM_LAND_USE_REQ"); // 用地规划许可
        list.add("PM_LAND_EXAMINE_REQ"); // 征地调查
        list.add("PM_LAND_CERTIFICATE"); // 土地证办理
        list.add("PM_TOPSOIL_STRIPPING_REQ"); // 耕作层剥离
        list.add("PM_LAND_ALLOCATION_REQ"); // 土地划拨
        list.add("PM_TENDER_VERIFICATION"); // 招核标准
        list.add("PM_DEFENSE_PLAN_REQ"); // 人防规划报建
        list.add("PM_DEFENSE_BUILD_REQ"); // 人防施工报建
        list.add("PM_TERMITE_CONTROL_REQ"); // 白蚁防治
        list.add("PM_NATIONAL_BUILD_REQ"); // 国安报建
        list.add("PM_MATERIAL_EXIT"); // 材料退场
        list.add("MATERIAL_EQUIPMENT_ENTER_CHECK"); // 工程材料设备进场验收
        list.add("MATERIAL_EQUIPMENT_BRAND_APPROVAL"); // 工程材料设备及品牌报审
        list.add("PM_BUILD_ORGAN_PLAN_REQ"); // 施工组织设计及施工方案
        list.add("PM_BUILD_PROGRESS_REQ"); // 施工进度计划
        list.add("PM_WORK_LIST_REQ"); // 工作联系单
        list.add("CONTRACT_BRAND_CHANGE_APPLICATION"); // 合同品牌变更申请
        list.add("SCIENTIFIC_MATERIAL_CHECK"); // 科研材料设备进场验收
        list.add("PROJECT_QUALITY_INSPECTION"); // 工程质量检查
        list.add("QUALITY_RECORD"); // 质量交底记录
        list.add("COMPLETION_PRE_ACCEPTANCE"); // 竣工预验收
        list.add("EXPENSE_CLAIM_APPROVAL"); // 费用索赔报审表
        list.add("PROJECT_CLAIM_NOTICE"); // 工程索赔通知书
        list.add("APPROVAL_INSPECTION"); // 报审、报验
        list.add("PM_CONTROL_FLOOD_REQ"); // 防洪评价
        list.add("PM_TRAFFIC_SAFETY_REQ"); // 交通安全评价
        list.add("APPROVAL_WITH_SEAL"); // 用章审批
        list.add("PM_DESIGN_CHANGE_REQ"); // 设计变更
        list.add("PM_SEND_APPROVAL_REQ"); // 发文呈批表
        list.add("PM_SUPERVISE_NOTICE_REQ"); // 监理通知单
        list.add("PM_SUPERVISE_NOTICE_REPLY_REQ"); // 监理通知回复单
        list.add("PM_START_ORDER_REQ"); // 开工令
//        list.add("PM_BUY_DEMAND_REQ"); // 采购需求审批
        list.add("PM_BID_APPROVAL_REQ"); // 招标文件审批
        list.add("PM_FILE_CHAPTER_REQ"); // 标前资料用印审批
        list.add("PM_USE_CHAPTER_REQ"); // 中选单位及标后用印审批
        list.add("PM_BID_KEEP_FILE_REQ"); // 招采项目备案及归档
        list.add("PM_PRJ_STOP_ORDER_REQ"); // 工程暂停令
        list.add("COMPLETION_ACCEPTANCE_COMMENTS"); // 竣工联合验收意见
        list.add("SUBCONTRACTOR_QUALIFICATION_REPORT"); // 分包单位资质报审
        list.add("PM_PRJ_RESTART_ORDER_REQ"); // 工程复工令
        list.add("PM_PRJ_RESTART_TRIAL_REQ"); // 工程复工报审表
        list.add("BID_PROCESS_MANAGE"); // 招标过程管理
        list.add("PIPELINE_RELOCATION_REQ"); // 管线迁改
        list.add("PM_POST_APPOINT"); // 岗位指派
        list.add("PRJ_LAND_CHECK"); // 项目红线核查
//        list.add("PM_PRJ_SETTLE_ACCOUNTS"); // 项目结算审批
        return list;
    }

    /**
     * 获取所有流程的表名
     * @return
     */
    public static List<String> getAllProcessList(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> codeMaps = myJdbcTemplate.queryForList("select code from ad_ent where PARENT_ENT_ID = '0099799190825077752' and status = 'AP'");
        List<String> codes = codeMaps.stream().map(m -> JdbcMapUtil.getString(m, "code")).collect(Collectors.toList());
        return codes;
    }

    /**
     * 项目属性联动-不进行项目类型联动
     */
    public static List<String> noPrjTypeLink(){
        List<String> list = new ArrayList<>();
        list.add("PM_PRJ_INVEST1"); //可研报告
        list.add("PM_PRJ_INVEST2"); //初设概算
        list.add("PM_PRJ_INVEST3"); //预算财评
        return list;
    }

    /**
     * 系统非系统属性联动-联动项目来源-多选项目
     * @return 涉及流程
     */
    public static List<String> sourceTypeLinkProjects() {
        List<String> list = new ArrayList<>();
        list.add("PO_ORDER_CHANGE_REQ");
        return list;
    }

    /**
     * 需要自动岗位人员的流程
     * @return 流程集合
     */
    public static List<String> getLinkUserProcess() {
        List<String> list = new ArrayList<>();
        list.add("PM_PRJ_SETTLE_ACCOUNTS"); //项目结算审批
        return list;
    }

    /**
     * 业主单位变化有对应值需要变化的流程
     * @return 涉及流程
     */
    public static List<String> getAutoGetDept() {
        List<String> list = new ArrayList<>();
        list.add("PM_BID_APPROVAL_REQ");  //招标文件审批
        list.add("PM_PRJ_SETTLE_ACCOUNTS");  //项目结算审批
        return list;
    }

    /**
     * 资金信息回显，按照优先级 可研估算<初设概算<预算财评 的流程
     * @return 涉及流程
     */
    public static List<String> getAmtList() {
        List<String> list = new ArrayList<>();
        list.add("PM_SUPERVISE_PLAN_REQ"); // 监理规划及细则申请
        list.add("PM_PRJ_PARTY_REQ"); // 五方责任主体维护申请
        list.add("PO_ORDER_TERMINATE_REQ"); // 采购合同终止申请
        list.add("PO_ORDER_CHANGE_REQ"); // 采购合同变更申请
        list.add("PO_ORDER_SUPPLEMENT_REQ"); // 采购合同补充协议申请
        list.add("PO_ORDER_REQ"); // 采购合同签订申请
        list.add("PO_PUBLIC_BID_REQ"); // 采购公开招标申请
        list.add("PM_BUILD_ORGAN_PLAN_REQ"); // 施工组织设计及施工方案
        list.add("PM_WORK_LIST_REQ"); // 工作联系单
        list.add("COMPLETION_PRE_ACCEPTANCE"); // 竣工预验收
        list.add("EXPENSE_CLAIM_APPROVAL"); // 费用索赔报审表
        list.add("PROJECT_CLAIM_NOTICE"); // 工程索赔通知书
        list.add("APPROVAL_INSPECTION"); // 报审、报验
        list.add("PM_BUILD_PROGRESS_REQ"); // 施工进度计划
        return list;
    }

    /**
     * 资金详细信息需要回显的 如工程费、建安费、总投资此类
     * @return list集合
     */
    public static List<String> getInvestProcess() {
        List<String> list = new ArrayList<>();
        list.add("PM_PRJ_INVEST1"); //可研报告审批
        list.add("PM_PRJ_INVEST2"); //初设概算审批
        list.add("PM_PRJ_INVEST3"); //预算财评
        return list;
    }

    /**
     * 需要带出相对方联系人明细的实体试图
     * @return list集合
     */
    public static List<String> getContactDetailList() {
        List<String> list = new ArrayList<>();
        list.add("0099902212142025475"); // 采购合同终止申请--填写项目信息及合同变更信息
        return list;
    }

    /**
     * 相对方联系人 只读
     * @return list集合
     */
    public static List<String> getContactListRead() {
        List<String> list = new ArrayList<>();
        list.add("0099902212142025475"); // 采购合同终止申请--填写项目信息及合同变更信息
        return list;
    }

    /**
     * 关联合同不需要自动带出保函类型的流程
     * @return list集合
     */
    public static List<String> getNotAutoBaoHan() {
        List<String> list = new ArrayList<>();
        list.add("PO_GUARANTEE_LETTER_REQUIRE_REQ"); //新增保函
        return list;
    }

    /**
     * 保函逻辑可改
     * @return list集合
     */
    public static List<String> getEditGuarantee() {
        List<String> editGuarantee = new ArrayList<>();
        editGuarantee.add("PO_ORDER_TERMINATE_REQ"); //采购合同终止申请
        return editGuarantee;
    }
}
