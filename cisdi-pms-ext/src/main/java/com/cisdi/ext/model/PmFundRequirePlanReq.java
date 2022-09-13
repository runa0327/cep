package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 资金需求计划申请。
 */
public class PmFundRequirePlanReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmFundRequirePlanReq> modelHelper = new ModelHelper<>("PM_FUND_REQUIRE_PLAN_REQ", new PmFundRequirePlanReq());

    // 实体常量：
    // <editor-fold>

    public static final String ENT_CODE = "PM_FUND_REQUIRE_PLAN_REQ";
    public static final EntityTypeE ENTITY_TYPE = EntityTypeE.TABLE;

    // </editor-fold>

    // 属性常量相关：
    // <editor-fold>

    public static class Cols {
        /**
         * ID。
         */
        public static final String ID = "ID";
        /**
         * 版本。
         */
        public static final String VER = "VER";
        /**
         * 时间戳。
         */
        public static final String TS = "TS";
        /**
         * 是否预设。
         */
        public static final String IS_PRESET = "IS_PRESET";
        /**
         * 最后修改日期时间。
         */
        public static final String LAST_MODI_DT = "LAST_MODI_DT";
        /**
         * 最后修改用户。
         */
        public static final String LAST_MODI_USER_ID = "LAST_MODI_USER_ID";
        /**
         * 代码。
         */
        public static final String CODE = "CODE";
        /**
         * 名称。
         */
        public static final String NAME = "NAME";
        /**
         * 资金需求项目名称。
         */
        public static final String AMOUT_PM_PRJ_ID = "AMOUT_PM_PRJ_ID";
        /**
         * 锁定流程实例。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
        /**
         * 项目编号。
         */
        public static final String PRJ_CODE = "PRJ_CODE";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 立项批复文号。
         */
        public static final String PRJ_REPLY_NO = "PRJ_REPLY_NO";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 项目概况。
         */
        public static final String PRJ_SITUATION = "PRJ_SITUATION";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 资金来源。
         */
        public static final String PM_FUND_SOURCE_ID = "PM_FUND_SOURCE_ID";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 业主单位。
         */
        public static final String CUSTOMER_UNIT = "CUSTOMER_UNIT";
        /**
         * 项目类型。
         */
        public static final String PROJECT_TYPE_ID = "PROJECT_TYPE_ID";
        /**
         * 所属年份。
         */
        public static final String YEAR = "YEAR";
        /**
         * 是否市财政预算政府投资计划项目。
         */
        public static final String IS_BUDGET = "IS_BUDGET";
        /**
         * 项目性质。
         */
        public static final String PROJECT_NATURE_ID = "PROJECT_NATURE_ID";
        /**
         * 建设地点。
         */
        public static final String BASE_LOCATION_ID = "BASE_LOCATION_ID";
        /**
         * 代建单位项目负责人。
         */
        public static final String AGENT_BUILD_UNIT_RESPONSE = "AGENT_BUILD_UNIT_RESPONSE";
        /**
         * 代建单位项目负责人电话。
         */
        public static final String AGENT_BUILD_UNIT_RESPONSE_PHONE = "AGENT_BUILD_UNIT_RESPONSE_PHONE";
        /**
         * 征地拆迁工作完成情况。
         */
        public static final String DEMOLITION_COMPLETED = "DEMOLITION_COMPLETED";
        /**
         * 预计开始日期。
         */
        public static final String PLAN_START_DATE = "PLAN_START_DATE";
        /**
         * 预计完成日期。
         */
        public static final String PLAN_COMPL_DATE = "PLAN_COMPL_DATE";
        /**
         * 实际开始日期。
         */
        public static final String ACTUAL_START_DATE = "ACTUAL_START_DATE";
        /**
         * 立项完成情况。
         */
        public static final String CREATE_PROJECT_COMPLETED = "CREATE_PROJECT_COMPLETED";
        /**
         * 可研完成情况。
         */
        public static final String FEASIBILITY_COMPLETED = "FEASIBILITY_COMPLETED";
        /**
         * 规划选址完成情况。
         */
        public static final String SELECT_SITE_COMPLETED = "SELECT_SITE_COMPLETED";
        /**
         * 用地预审完成情况。
         */
        public static final String USE_LAND_COMPLETED = "USE_LAND_COMPLETED";
        /**
         * 环评审批完成情况。
         */
        public static final String EIA_COMPLETED = "EIA_COMPLETED";
        /**
         * 概算完成情况。
         */
        public static final String ESTIMATE_COMPLETED = "ESTIMATE_COMPLETED";
        /**
         * 批复文件。
         */
        public static final String REPLY_FILE = "REPLY_FILE";
        /**
         * 预算评审完成情况。
         */
        public static final String BUDGET_REVIEW_COMPLETED = "BUDGET_REVIEW_COMPLETED";
        /**
         * 环评批复文件。
         */
        public static final String CONSERVATION_REPLY_FILE = "CONSERVATION_REPLY_FILE";
        /**
         * 关联合同。
         */
        public static final String RELATION_CONTRACT_ID = "RELATION_CONTRACT_ID";
        /**
         * 委托单位。
         */
        public static final String ENTRUSTING_UNIT = "ENTRUSTING_UNIT";
        /**
         * 合同金额。
         */
        public static final String CONTRACT_PRICE = "CONTRACT_PRICE";
        /**
         * 支付金额。
         */
        public static final String PAY_AMT = "PAY_AMT";
        /**
         * 预付款比例。
         */
        public static final String ADVANCE_CHARGE_PERCENT = "ADVANCE_CHARGE_PERCENT";
        /**
         * 概评金额。
         */
        public static final String ESTIMATED_AMOUNT = "ESTIMATED_AMOUNT";
        /**
         * 财评金额。
         */
        public static final String FINANCIAL_AMOUNT = "FINANCIAL_AMOUNT";
        /**
         * 指标金额。
         */
        public static final String TGT_AMT = "TGT_AMT";
        /**
         * 合同类型。
         */
        public static final String CONTRACT_CATEGORY_ID = "CONTRACT_CATEGORY_ID";
        /**
         * 标后附件。
         */
        public static final String BID_AFTER_FILE_GROUP_ID = "BID_AFTER_FILE_GROUP_ID";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 财务意见发表日期。
         */
        public static final String FINANCE_PUBLISH_DATE = "FINANCE_PUBLISH_DATE";
        /**
         * 财务意见发表人。
         */
        public static final String FINANCE_PUBLISH_USER = "FINANCE_PUBLISH_USER";
        /**
         * 财务意见。
         */
        public static final String FINANCE_MESSAGE = "FINANCE_MESSAGE";
        /**
         * 意见发表日期。
         */
        public static final String COMMENT_PUBLISH_DATE = "COMMENT_PUBLISH_DATE";
        /**
         * 意见发表人。
         */
        public static final String COMMENT_PUBLISH_USER = "COMMENT_PUBLISH_USER";
        /**
         * 意见内容。
         */
        public static final String COMMENT_PUBLISH_CONTENT = "COMMENT_PUBLISH_CONTENT";
    }

    // </editor-fold>

    // 各个属性及setter、getter：
    // <editor-fold>

    /**
     * ID。
     */
    public String id;

    /**
     * 获取：ID。
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置：ID。
     */
    public PmFundRequirePlanReq setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 版本。
     */
    public Integer ver;

    /**
     * 获取：版本。
     */
    public Integer getVer() {
        return this.ver;
    }

    /**
     * 设置：版本。
     */
    public PmFundRequirePlanReq setVer(Integer ver) {
        this.ver = ver;
        return this;
    }

    /**
     * 时间戳。
     */
    public LocalDateTime ts;

    /**
     * 获取：时间戳。
     */
    public LocalDateTime getTs() {
        return this.ts;
    }

    /**
     * 设置：时间戳。
     */
    public PmFundRequirePlanReq setTs(LocalDateTime ts) {
        this.ts = ts;
        return this;
    }

    /**
     * 是否预设。
     */
    public Boolean isPreset;

    /**
     * 获取：是否预设。
     */
    public Boolean getIsPreset() {
        return this.isPreset;
    }

    /**
     * 设置：是否预设。
     */
    public PmFundRequirePlanReq setIsPreset(Boolean isPreset) {
        this.isPreset = isPreset;
        return this;
    }

    /**
     * 最后修改日期时间。
     */
    public LocalDateTime lastModiDt;

    /**
     * 获取：最后修改日期时间。
     */
    public LocalDateTime getLastModiDt() {
        return this.lastModiDt;
    }

    /**
     * 设置：最后修改日期时间。
     */
    public PmFundRequirePlanReq setLastModiDt(LocalDateTime lastModiDt) {
        this.lastModiDt = lastModiDt;
        return this;
    }

    /**
     * 最后修改用户。
     */
    public String lastModiUserId;

    /**
     * 获取：最后修改用户。
     */
    public String getLastModiUserId() {
        return this.lastModiUserId;
    }

    /**
     * 设置：最后修改用户。
     */
    public PmFundRequirePlanReq setLastModiUserId(String lastModiUserId) {
        this.lastModiUserId = lastModiUserId;
        return this;
    }

    /**
     * 代码。
     */
    public String code;

    /**
     * 获取：代码。
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 设置：代码。
     */
    public PmFundRequirePlanReq setCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * 名称。
     */
    public String name;

    /**
     * 获取：名称。
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置：名称。
     */
    public PmFundRequirePlanReq setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 资金需求项目名称。
     */
    public String amoutPmPrjId;

    /**
     * 获取：资金需求项目名称。
     */
    public String getAmoutPmPrjId() {
        return this.amoutPmPrjId;
    }

    /**
     * 设置：资金需求项目名称。
     */
    public PmFundRequirePlanReq setAmoutPmPrjId(String amoutPmPrjId) {
        this.amoutPmPrjId = amoutPmPrjId;
        return this;
    }

    /**
     * 锁定流程实例。
     */
    public String lkWfInstId;

    /**
     * 获取：锁定流程实例。
     */
    public String getLkWfInstId() {
        return this.lkWfInstId;
    }

    /**
     * 设置：锁定流程实例。
     */
    public PmFundRequirePlanReq setLkWfInstId(String lkWfInstId) {
        this.lkWfInstId = lkWfInstId;
        return this;
    }

    /**
     * 项目编号。
     */
    public String prjCode;

    /**
     * 获取：项目编号。
     */
    public String getPrjCode() {
        return this.prjCode;
    }

    /**
     * 设置：项目编号。
     */
    public PmFundRequirePlanReq setPrjCode(String prjCode) {
        this.prjCode = prjCode;
        return this;
    }

    /**
     * 记录状态。
     */
    public String status;

    /**
     * 获取：记录状态。
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * 设置：记录状态。
     */
    public PmFundRequirePlanReq setStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * 立项批复文号。
     */
    public String prjReplyNo;

    /**
     * 获取：立项批复文号。
     */
    public String getPrjReplyNo() {
        return this.prjReplyNo;
    }

    /**
     * 设置：立项批复文号。
     */
    public PmFundRequirePlanReq setPrjReplyNo(String prjReplyNo) {
        this.prjReplyNo = prjReplyNo;
        return this;
    }

    /**
     * 创建用户。
     */
    public String crtUserId;

    /**
     * 获取：创建用户。
     */
    public String getCrtUserId() {
        return this.crtUserId;
    }

    /**
     * 设置：创建用户。
     */
    public PmFundRequirePlanReq setCrtUserId(String crtUserId) {
        this.crtUserId = crtUserId;
        return this;
    }

    /**
     * 项目概况。
     */
    public String prjSituation;

    /**
     * 获取：项目概况。
     */
    public String getPrjSituation() {
        return this.prjSituation;
    }

    /**
     * 设置：项目概况。
     */
    public PmFundRequirePlanReq setPrjSituation(String prjSituation) {
        this.prjSituation = prjSituation;
        return this;
    }

    /**
     * 创建部门。
     */
    public String crtDeptId;

    /**
     * 获取：创建部门。
     */
    public String getCrtDeptId() {
        return this.crtDeptId;
    }

    /**
     * 设置：创建部门。
     */
    public PmFundRequirePlanReq setCrtDeptId(String crtDeptId) {
        this.crtDeptId = crtDeptId;
        return this;
    }

    /**
     * 资金来源。
     */
    public String pmFundSourceId;

    /**
     * 获取：资金来源。
     */
    public String getPmFundSourceId() {
        return this.pmFundSourceId;
    }

    /**
     * 设置：资金来源。
     */
    public PmFundRequirePlanReq setPmFundSourceId(String pmFundSourceId) {
        this.pmFundSourceId = pmFundSourceId;
        return this;
    }

    /**
     * 创建日期时间。
     */
    public LocalDateTime crtDt;

    /**
     * 获取：创建日期时间。
     */
    public LocalDateTime getCrtDt() {
        return this.crtDt;
    }

    /**
     * 设置：创建日期时间。
     */
    public PmFundRequirePlanReq setCrtDt(LocalDateTime crtDt) {
        this.crtDt = crtDt;
        return this;
    }

    /**
     * 业主单位。
     */
    public String customerUnit;

    /**
     * 获取：业主单位。
     */
    public String getCustomerUnit() {
        return this.customerUnit;
    }

    /**
     * 设置：业主单位。
     */
    public PmFundRequirePlanReq setCustomerUnit(String customerUnit) {
        this.customerUnit = customerUnit;
        return this;
    }

    /**
     * 项目类型。
     */
    public String projectTypeId;

    /**
     * 获取：项目类型。
     */
    public String getProjectTypeId() {
        return this.projectTypeId;
    }

    /**
     * 设置：项目类型。
     */
    public PmFundRequirePlanReq setProjectTypeId(String projectTypeId) {
        this.projectTypeId = projectTypeId;
        return this;
    }

    /**
     * 所属年份。
     */
    public String year;

    /**
     * 获取：所属年份。
     */
    public String getYear() {
        return this.year;
    }

    /**
     * 设置：所属年份。
     */
    public PmFundRequirePlanReq setYear(String year) {
        this.year = year;
        return this;
    }

    /**
     * 是否市财政预算政府投资计划项目。
     */
    public Boolean isBudget;

    /**
     * 获取：是否市财政预算政府投资计划项目。
     */
    public Boolean getIsBudget() {
        return this.isBudget;
    }

    /**
     * 设置：是否市财政预算政府投资计划项目。
     */
    public PmFundRequirePlanReq setIsBudget(Boolean isBudget) {
        this.isBudget = isBudget;
        return this;
    }

    /**
     * 项目性质。
     */
    public String projectNatureId;

    /**
     * 获取：项目性质。
     */
    public String getProjectNatureId() {
        return this.projectNatureId;
    }

    /**
     * 设置：项目性质。
     */
    public PmFundRequirePlanReq setProjectNatureId(String projectNatureId) {
        this.projectNatureId = projectNatureId;
        return this;
    }

    /**
     * 建设地点。
     */
    public String baseLocationId;

    /**
     * 获取：建设地点。
     */
    public String getBaseLocationId() {
        return this.baseLocationId;
    }

    /**
     * 设置：建设地点。
     */
    public PmFundRequirePlanReq setBaseLocationId(String baseLocationId) {
        this.baseLocationId = baseLocationId;
        return this;
    }

    /**
     * 代建单位项目负责人。
     */
    public String agentBuildUnitResponse;

    /**
     * 获取：代建单位项目负责人。
     */
    public String getAgentBuildUnitResponse() {
        return this.agentBuildUnitResponse;
    }

    /**
     * 设置：代建单位项目负责人。
     */
    public PmFundRequirePlanReq setAgentBuildUnitResponse(String agentBuildUnitResponse) {
        this.agentBuildUnitResponse = agentBuildUnitResponse;
        return this;
    }

    /**
     * 代建单位项目负责人电话。
     */
    public String agentBuildUnitResponsePhone;

    /**
     * 获取：代建单位项目负责人电话。
     */
    public String getAgentBuildUnitResponsePhone() {
        return this.agentBuildUnitResponsePhone;
    }

    /**
     * 设置：代建单位项目负责人电话。
     */
    public PmFundRequirePlanReq setAgentBuildUnitResponsePhone(String agentBuildUnitResponsePhone) {
        this.agentBuildUnitResponsePhone = agentBuildUnitResponsePhone;
        return this;
    }

    /**
     * 征地拆迁工作完成情况。
     */
    public String demolitionCompleted;

    /**
     * 获取：征地拆迁工作完成情况。
     */
    public String getDemolitionCompleted() {
        return this.demolitionCompleted;
    }

    /**
     * 设置：征地拆迁工作完成情况。
     */
    public PmFundRequirePlanReq setDemolitionCompleted(String demolitionCompleted) {
        this.demolitionCompleted = demolitionCompleted;
        return this;
    }

    /**
     * 预计开始日期。
     */
    public LocalDate planStartDate;

    /**
     * 获取：预计开始日期。
     */
    public LocalDate getPlanStartDate() {
        return this.planStartDate;
    }

    /**
     * 设置：预计开始日期。
     */
    public PmFundRequirePlanReq setPlanStartDate(LocalDate planStartDate) {
        this.planStartDate = planStartDate;
        return this;
    }

    /**
     * 预计完成日期。
     */
    public LocalDate planComplDate;

    /**
     * 获取：预计完成日期。
     */
    public LocalDate getPlanComplDate() {
        return this.planComplDate;
    }

    /**
     * 设置：预计完成日期。
     */
    public PmFundRequirePlanReq setPlanComplDate(LocalDate planComplDate) {
        this.planComplDate = planComplDate;
        return this;
    }

    /**
     * 实际开始日期。
     */
    public LocalDate actualStartDate;

    /**
     * 获取：实际开始日期。
     */
    public LocalDate getActualStartDate() {
        return this.actualStartDate;
    }

    /**
     * 设置：实际开始日期。
     */
    public PmFundRequirePlanReq setActualStartDate(LocalDate actualStartDate) {
        this.actualStartDate = actualStartDate;
        return this;
    }

    /**
     * 立项完成情况。
     */
    public String createProjectCompleted;

    /**
     * 获取：立项完成情况。
     */
    public String getCreateProjectCompleted() {
        return this.createProjectCompleted;
    }

    /**
     * 设置：立项完成情况。
     */
    public PmFundRequirePlanReq setCreateProjectCompleted(String createProjectCompleted) {
        this.createProjectCompleted = createProjectCompleted;
        return this;
    }

    /**
     * 可研完成情况。
     */
    public String feasibilityCompleted;

    /**
     * 获取：可研完成情况。
     */
    public String getFeasibilityCompleted() {
        return this.feasibilityCompleted;
    }

    /**
     * 设置：可研完成情况。
     */
    public PmFundRequirePlanReq setFeasibilityCompleted(String feasibilityCompleted) {
        this.feasibilityCompleted = feasibilityCompleted;
        return this;
    }

    /**
     * 规划选址完成情况。
     */
    public String selectSiteCompleted;

    /**
     * 获取：规划选址完成情况。
     */
    public String getSelectSiteCompleted() {
        return this.selectSiteCompleted;
    }

    /**
     * 设置：规划选址完成情况。
     */
    public PmFundRequirePlanReq setSelectSiteCompleted(String selectSiteCompleted) {
        this.selectSiteCompleted = selectSiteCompleted;
        return this;
    }

    /**
     * 用地预审完成情况。
     */
    public String useLandCompleted;

    /**
     * 获取：用地预审完成情况。
     */
    public String getUseLandCompleted() {
        return this.useLandCompleted;
    }

    /**
     * 设置：用地预审完成情况。
     */
    public PmFundRequirePlanReq setUseLandCompleted(String useLandCompleted) {
        this.useLandCompleted = useLandCompleted;
        return this;
    }

    /**
     * 环评审批完成情况。
     */
    public String eiaCompleted;

    /**
     * 获取：环评审批完成情况。
     */
    public String getEiaCompleted() {
        return this.eiaCompleted;
    }

    /**
     * 设置：环评审批完成情况。
     */
    public PmFundRequirePlanReq setEiaCompleted(String eiaCompleted) {
        this.eiaCompleted = eiaCompleted;
        return this;
    }

    /**
     * 概算完成情况。
     */
    public String estimateCompleted;

    /**
     * 获取：概算完成情况。
     */
    public String getEstimateCompleted() {
        return this.estimateCompleted;
    }

    /**
     * 设置：概算完成情况。
     */
    public PmFundRequirePlanReq setEstimateCompleted(String estimateCompleted) {
        this.estimateCompleted = estimateCompleted;
        return this;
    }

    /**
     * 批复文件。
     */
    public String replyFile;

    /**
     * 获取：批复文件。
     */
    public String getReplyFile() {
        return this.replyFile;
    }

    /**
     * 设置：批复文件。
     */
    public PmFundRequirePlanReq setReplyFile(String replyFile) {
        this.replyFile = replyFile;
        return this;
    }

    /**
     * 预算评审完成情况。
     */
    public String budgetReviewCompleted;

    /**
     * 获取：预算评审完成情况。
     */
    public String getBudgetReviewCompleted() {
        return this.budgetReviewCompleted;
    }

    /**
     * 设置：预算评审完成情况。
     */
    public PmFundRequirePlanReq setBudgetReviewCompleted(String budgetReviewCompleted) {
        this.budgetReviewCompleted = budgetReviewCompleted;
        return this;
    }

    /**
     * 环评批复文件。
     */
    public String conservationReplyFile;

    /**
     * 获取：环评批复文件。
     */
    public String getConservationReplyFile() {
        return this.conservationReplyFile;
    }

    /**
     * 设置：环评批复文件。
     */
    public PmFundRequirePlanReq setConservationReplyFile(String conservationReplyFile) {
        this.conservationReplyFile = conservationReplyFile;
        return this;
    }

    /**
     * 关联合同。
     */
    public String relationContractId;

    /**
     * 获取：关联合同。
     */
    public String getRelationContractId() {
        return this.relationContractId;
    }

    /**
     * 设置：关联合同。
     */
    public PmFundRequirePlanReq setRelationContractId(String relationContractId) {
        this.relationContractId = relationContractId;
        return this;
    }

    /**
     * 委托单位。
     */
    public String entrustingUnit;

    /**
     * 获取：委托单位。
     */
    public String getEntrustingUnit() {
        return this.entrustingUnit;
    }

    /**
     * 设置：委托单位。
     */
    public PmFundRequirePlanReq setEntrustingUnit(String entrustingUnit) {
        this.entrustingUnit = entrustingUnit;
        return this;
    }

    /**
     * 合同金额。
     */
    public String contractPrice;

    /**
     * 获取：合同金额。
     */
    public String getContractPrice() {
        return this.contractPrice;
    }

    /**
     * 设置：合同金额。
     */
    public PmFundRequirePlanReq setContractPrice(String contractPrice) {
        this.contractPrice = contractPrice;
        return this;
    }

    /**
     * 支付金额。
     */
    public Double payAmt;

    /**
     * 获取：支付金额。
     */
    public Double getPayAmt() {
        return this.payAmt;
    }

    /**
     * 设置：支付金额。
     */
    public PmFundRequirePlanReq setPayAmt(Double payAmt) {
        this.payAmt = payAmt;
        return this;
    }

    /**
     * 预付款比例。
     */
    public String advanceChargePercent;

    /**
     * 获取：预付款比例。
     */
    public String getAdvanceChargePercent() {
        return this.advanceChargePercent;
    }

    /**
     * 设置：预付款比例。
     */
    public PmFundRequirePlanReq setAdvanceChargePercent(String advanceChargePercent) {
        this.advanceChargePercent = advanceChargePercent;
        return this;
    }

    /**
     * 概评金额。
     */
    public Double estimatedAmount;

    /**
     * 获取：概评金额。
     */
    public Double getEstimatedAmount() {
        return this.estimatedAmount;
    }

    /**
     * 设置：概评金额。
     */
    public PmFundRequirePlanReq setEstimatedAmount(Double estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
        return this;
    }

    /**
     * 财评金额。
     */
    public Double financialAmount;

    /**
     * 获取：财评金额。
     */
    public Double getFinancialAmount() {
        return this.financialAmount;
    }

    /**
     * 设置：财评金额。
     */
    public PmFundRequirePlanReq setFinancialAmount(Double financialAmount) {
        this.financialAmount = financialAmount;
        return this;
    }

    /**
     * 指标金额。
     */
    public Double tgtAmt;

    /**
     * 获取：指标金额。
     */
    public Double getTgtAmt() {
        return this.tgtAmt;
    }

    /**
     * 设置：指标金额。
     */
    public PmFundRequirePlanReq setTgtAmt(Double tgtAmt) {
        this.tgtAmt = tgtAmt;
        return this;
    }

    /**
     * 合同类型。
     */
    public String contractCategoryId;

    /**
     * 获取：合同类型。
     */
    public String getContractCategoryId() {
        return this.contractCategoryId;
    }

    /**
     * 设置：合同类型。
     */
    public PmFundRequirePlanReq setContractCategoryId(String contractCategoryId) {
        this.contractCategoryId = contractCategoryId;
        return this;
    }

    /**
     * 标后附件。
     */
    public String bidAfterFileGroupId;

    /**
     * 获取：标后附件。
     */
    public String getBidAfterFileGroupId() {
        return this.bidAfterFileGroupId;
    }

    /**
     * 设置：标后附件。
     */
    public PmFundRequirePlanReq setBidAfterFileGroupId(String bidAfterFileGroupId) {
        this.bidAfterFileGroupId = bidAfterFileGroupId;
        return this;
    }

    /**
     * 备注。
     */
    public String remark;

    /**
     * 获取：备注。
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置：备注。
     */
    public PmFundRequirePlanReq setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    /**
     * 财务意见发表日期。
     */
    public LocalDate financePublishDate;

    /**
     * 获取：财务意见发表日期。
     */
    public LocalDate getFinancePublishDate() {
        return this.financePublishDate;
    }

    /**
     * 设置：财务意见发表日期。
     */
    public PmFundRequirePlanReq setFinancePublishDate(LocalDate financePublishDate) {
        this.financePublishDate = financePublishDate;
        return this;
    }

    /**
     * 财务意见发表人。
     */
    public String financePublishUser;

    /**
     * 获取：财务意见发表人。
     */
    public String getFinancePublishUser() {
        return this.financePublishUser;
    }

    /**
     * 设置：财务意见发表人。
     */
    public PmFundRequirePlanReq setFinancePublishUser(String financePublishUser) {
        this.financePublishUser = financePublishUser;
        return this;
    }

    /**
     * 财务意见。
     */
    public String financeMessage;

    /**
     * 获取：财务意见。
     */
    public String getFinanceMessage() {
        return this.financeMessage;
    }

    /**
     * 设置：财务意见。
     */
    public PmFundRequirePlanReq setFinanceMessage(String financeMessage) {
        this.financeMessage = financeMessage;
        return this;
    }

    /**
     * 意见发表日期。
     */
    public LocalDate commentPublishDate;

    /**
     * 获取：意见发表日期。
     */
    public LocalDate getCommentPublishDate() {
        return this.commentPublishDate;
    }

    /**
     * 设置：意见发表日期。
     */
    public PmFundRequirePlanReq setCommentPublishDate(LocalDate commentPublishDate) {
        this.commentPublishDate = commentPublishDate;
        return this;
    }

    /**
     * 意见发表人。
     */
    public String commentPublishUser;

    /**
     * 获取：意见发表人。
     */
    public String getCommentPublishUser() {
        return this.commentPublishUser;
    }

    /**
     * 设置：意见发表人。
     */
    public PmFundRequirePlanReq setCommentPublishUser(String commentPublishUser) {
        this.commentPublishUser = commentPublishUser;
        return this;
    }

    /**
     * 意见内容。
     */
    public String commentPublishContent;

    /**
     * 获取：意见内容。
     */
    public String getCommentPublishContent() {
        return this.commentPublishContent;
    }

    /**
     * 设置：意见内容。
     */
    public PmFundRequirePlanReq setCommentPublishContent(String commentPublishContent) {
        this.commentPublishContent = commentPublishContent;
        return this;
    }

    // </editor-fold>

    // 实例方法：
    // <editor-fold>

    /**
     * 根据ID插入数据。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param includeCols 更新时包含的列，空为包含所有。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @param refreshThis 更新后，是否刷新当前对象。刷新时将刷新所有列。
     */
    public void insertById(List<String> includeCols, List<String> excludeCols, boolean refreshThis) {
        modelHelper.insertById(includeCols, excludeCols, refreshThis, this.id, this);
    }

    /**
     * 根据ID更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param includeCols 更新时包含的列，空为包含所有。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @param refreshThis 更新后，是否刷新当前对象。刷新时将刷新所有列。
     */
    public void updateById(List<String> includeCols, List<String> excludeCols, boolean refreshThis) {
        modelHelper.updateById(includeCols, excludeCols, refreshThis, this.id, this);
    }

    /**
     * 根据ID删除数据。
     */
    public void deleteById() {
        modelHelper.deleteById(this.id);
    }

    // </editor-fold>

    // 静态方法：
    // <editor-fold>

    /**
     * 获取新的数据（未插入）。
     *
     * @return
     */
    public static PmFundRequirePlanReq newData() {
        return modelHelper.newData();
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmFundRequirePlanReq insertData() {
        return modelHelper.insertData();
    }

    /**
     * 根据ID获取数据。
     *
     * @param id          ID。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象，若无则为null。
     */
    public static PmFundRequirePlanReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
        return modelHelper.selectById(id, includeCols, excludeCols);
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids         ID列表。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmFundRequirePlanReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        return modelHelper.selectByIds(ids, includeCols, excludeCols);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where       Where条件。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmFundRequirePlanReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        return modelHelper.selectByWhere(where, includeCols, excludeCols);
    }

    /**
     * 根据ID更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param id          ID。
     * @param keyValueMap 要更新的列和新值。其中，key为列名、value为新值（可为null）。
     * @param includeCols 更新时包含的列，空为包含所有keyValueMap里的列。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @return 影响的行数。
     */
    public static int updateById(String id, Map<String, Object> keyValueMap, List<String> includeCols, List<String> excludeCols) {
        return modelHelper.updateById(id, keyValueMap, includeCols, excludeCols);
    }

    /**
     * 根据ID列表更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param ids         ID列表。
     * @param keyValueMap 要更新的列和新值。其中，key为列名、value为新值（可为null）。
     * @param includeCols 更新时包含的列，空为包含所有keyValueMap里的列。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @return 影响的行数。
     */
    public static int updateByIds(List<String> ids, Map<String, Object> keyValueMap, List<String> includeCols, List<String> excludeCols) {
        return modelHelper.updateByIds(ids, keyValueMap, includeCols, excludeCols);
    }

    /**
     * 根据Where条件更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param where       Where条件。
     * @param keyValueMap 要更新的列和新值。其中，key为列名、value为新值（可为null）。
     * @param includeCols 更新时包含的列，空为包含所有keyValueMap里的列。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @return 影响的行数。
     */
    public static int updateByWhere(Where where, Map<String, Object> keyValueMap, List<String> includeCols, List<String> excludeCols) {
        return modelHelper.updateByWhere(where, keyValueMap, includeCols, excludeCols);
    }

    /**
     * 根据ID删除数据。
     *
     * @param id ID。
     * @return 影响的行数。
     */
    public static int deleteById(String id) {
        return modelHelper.deleteById(id);
    }

    /**
     * 根据ID列表删除数据。
     *
     * @param ids ID列表。
     * @return 影响的行数。
     */
    public static int deleteByIds(List<String> ids) {
        return modelHelper.deleteByIds(ids);
    }

    /**
     * 根据Where条件删除数据。
     *
     * @param where Where条件。
     * @return 影响的行数。
     */
    public static int deleteByWhere(Where where) {
        return modelHelper.deleteByWhere(where);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel   从模型。
     * @param toModel     到模型。
     * @param includeCols 拷贝时包含的列，空为包含所有。
     * @param excludeCols 拷贝时排除的列，空为不排除。
     */
    public static void copyCols(PmFundRequirePlanReq fromModel, PmFundRequirePlanReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}