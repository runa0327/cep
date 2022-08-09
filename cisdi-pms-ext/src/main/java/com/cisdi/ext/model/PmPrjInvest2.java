package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * 初设概算。
 */
public class PmPrjInvest2 {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmPrjInvest2> modelHelper = new ModelHelper<>("PM_PRJ_INVEST2", new PmPrjInvest2());

    // 实体常量：
    // <editor-fold>

    public static final String ENT_CODE = "PM_PRJ_INVEST2";
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
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
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
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 业主单位。
         */
        public static final String CUSTOMER_UNIT = "CUSTOMER_UNIT";
        /**
         * 项目管理模式。
         */
        public static final String PRJ_MANAGE_MODE_ID = "PRJ_MANAGE_MODE_ID";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 建设地点。
         */
        public static final String BASE_LOCATION_ID = "BASE_LOCATION_ID";
        /**
         * 占地面积（平方）。
         */
        public static final String FLOOR_AREA = "FLOOR_AREA";
        /**
         * 项目类型。
         */
        public static final String PROJECT_TYPE_ID = "PROJECT_TYPE_ID";
        /**
         * 建设规模类型。
         */
        public static final String CON_SCALE_TYPE_ID = "CON_SCALE_TYPE_ID";
        /**
         * 建设规模数量。
         */
        public static final String CON_SCALE_QTY = "CON_SCALE_QTY";
        /**
         * 建设规模数量2。
         */
        public static final String CON_SCALE_QTY2 = "CON_SCALE_QTY2";
        /**
         * 建设规模单位。
         */
        public static final String CON_SCALE_UOM_ID = "CON_SCALE_UOM_ID";
        /**
         * 建设年限。
         */
        public static final String BUILD_YEARS = "BUILD_YEARS";
        /**
         * 项目概况。
         */
        public static final String PRJ_SITUATION = "PRJ_SITUATION";
        /**
         * 招采标流程标题。
         */
        public static final String BIDDING_NAME_ID = "BIDDING_NAME_ID";
        /**
         * 审核状态。
         */
        public static final String APPROVAL_STATUS = "APPROVAL_STATUS";
        /**
         * 委托单位(文本)。
         */
        public static final String ENTRUSTING_UNIT_TEXT = "ENTRUSTING_UNIT_TEXT";
        /**
         * 采购经办人。
         */
        public static final String PROCURE_USER_ID = "PROCURE_USER_ID";
        /**
         * 编制单位(文本)。
         */
        public static final String AUTHOR_UNIT_TEXT = "AUTHOR_UNIT_TEXT";
        /**
         * 对方负责人。
         */
        public static final String OTHER_RESPONSOR = "OTHER_RESPONSOR";
        /**
         * 进场日期。
         */
        public static final String IN_DATE = "IN_DATE";
        /**
         * 服务周期。
         */
        public static final String SERVICE_DAYS = "SERVICE_DAYS";
        /**
         * 对方联系方式。
         */
        public static final String OTHER_CONTACT_PHONE = "OTHER_CONTACT_PHONE";
        /**
         * 概算申报材料。
         */
        public static final String BUDGETESTIMATEDECLARATION_FILE = "BUDGETESTIMATEDECLARATION_FILE";
        /**
         * 概算申报备注。
         */
        public static final String BUDGETESTIMATEDECLARATION_REMARK = "BUDGETESTIMATEDECLARATION_REMARK";
        /**
         * 专家评审计划完成日期。
         */
        public static final String EXPERT_COMPL_PLAN_DATE = "EXPERT_COMPL_PLAN_DATE";
        /**
         * 专家评审实际完成日期。
         */
        public static final String EXPERT_COMPL_ACTUAL_DATE = "EXPERT_COMPL_ACTUAL_DATE";
        /**
         * 评审单位负责人。
         */
        public static final String REVIEW_UNIT_CHIEF = "REVIEW_UNIT_CHIEF";
        /**
         * 评审单位(文本)。
         */
        public static final String REVIEW_UNIT_TEXT = "REVIEW_UNIT_TEXT";
        /**
         * 评审单位联系方式。
         */
        public static final String REVIEW_UNIT_PHONE = "REVIEW_UNIT_PHONE";
        /**
         * 评审稿文件。
         */
        public static final String REVIEW_REPORT_FILE = "REVIEW_REPORT_FILE";
        /**
         * 专家意见文件。
         */
        public static final String EXPERT_FILE = "EXPERT_FILE";
        /**
         * 修编稿文件。
         */
        public static final String REVISION_FILE = "REVISION_FILE";
        /**
         * 评审及修编说明。
         */
        public static final String REVIEW_AND_REVISION_REMARK = "REVIEW_AND_REVISION_REMARK";
        /**
         * 成本部意见。
         */
        public static final String COST_COMMENT = "COST_COMMENT";
        /**
         * 设计部意见。
         */
        public static final String DESIGN_COMMENT = "DESIGN_COMMENT";
        /**
         * 前期部意见。
         */
        public static final String EARLY_COMMENT = "EARLY_COMMENT";
        /**
         * 计划批复日期。
         */
        public static final String REPLY_PLAN_DATE = "REPLY_PLAN_DATE";
        /**
         * 实际批复日期。
         */
        public static final String REPLY_ACTUAL_DATE = "REPLY_ACTUAL_DATE";
        /**
         * 批复文号。
         */
        public static final String REPLY_NO = "REPLY_NO";
        /**
         * 批复文件。
         */
        public static final String REPLY_FILE = "REPLY_FILE";
        /**
         * 总投资（万）。
         */
        public static final String PRJ_TOTAL_INVEST = "PRJ_TOTAL_INVEST";
        /**
         * 工程费用（万）。
         */
        public static final String PROJECT_AMT = "PROJECT_AMT";
        /**
         * 建安费（万）。
         */
        public static final String CONSTRUCT_AMT = "CONSTRUCT_AMT";
        /**
         * 设备费（万）。
         */
        public static final String EQUIP_AMT = "EQUIP_AMT";
        /**
         * 工程其他费用（万）。
         */
        public static final String PROJECT_OTHER_AMT = "PROJECT_OTHER_AMT";
        /**
         * 土地拆迁费（万）。
         */
        public static final String LAND_AMT = "LAND_AMT";
        /**
         * 预备费（万）。
         */
        public static final String PREPARE_AMT = "PREPARE_AMT";
        /**
         * 建设期利息。
         */
        public static final String CONSTRUCT_PERIOD_INTEREST = "CONSTRUCT_PERIOD_INTEREST";
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
    public PmPrjInvest2 setId(String id) {
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
    public PmPrjInvest2 setVer(Integer ver) {
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
    public PmPrjInvest2 setTs(LocalDateTime ts) {
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
    public PmPrjInvest2 setIsPreset(Boolean isPreset) {
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
    public PmPrjInvest2 setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmPrjInvest2 setLastModiUserId(String lastModiUserId) {
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
    public PmPrjInvest2 setCode(String code) {
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
    public PmPrjInvest2 setName(String name) {
        this.name = name;
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
    public PmPrjInvest2 setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    /**
     * 项目。
     */
    public String pmPrjId;

    /**
     * 获取：项目。
     */
    public String getPmPrjId() {
        return this.pmPrjId;
    }

    /**
     * 设置：项目。
     */
    public PmPrjInvest2 setPmPrjId(String pmPrjId) {
        this.pmPrjId = pmPrjId;
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
    public PmPrjInvest2 setLkWfInstId(String lkWfInstId) {
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
    public PmPrjInvest2 setPrjCode(String prjCode) {
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
    public PmPrjInvest2 setStatus(String status) {
        this.status = status;
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
    public PmPrjInvest2 setCrtUserId(String crtUserId) {
        this.crtUserId = crtUserId;
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
    public PmPrjInvest2 setCustomerUnit(String customerUnit) {
        this.customerUnit = customerUnit;
        return this;
    }

    /**
     * 项目管理模式。
     */
    public String prjManageModeId;

    /**
     * 获取：项目管理模式。
     */
    public String getPrjManageModeId() {
        return this.prjManageModeId;
    }

    /**
     * 设置：项目管理模式。
     */
    public PmPrjInvest2 setPrjManageModeId(String prjManageModeId) {
        this.prjManageModeId = prjManageModeId;
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
    public PmPrjInvest2 setCrtDeptId(String crtDeptId) {
        this.crtDeptId = crtDeptId;
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
    public PmPrjInvest2 setCrtDt(LocalDateTime crtDt) {
        this.crtDt = crtDt;
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
    public PmPrjInvest2 setBaseLocationId(String baseLocationId) {
        this.baseLocationId = baseLocationId;
        return this;
    }

    /**
     * 占地面积（平方）。
     */
    public Double floorArea;

    /**
     * 获取：占地面积（平方）。
     */
    public Double getFloorArea() {
        return this.floorArea;
    }

    /**
     * 设置：占地面积（平方）。
     */
    public PmPrjInvest2 setFloorArea(Double floorArea) {
        this.floorArea = floorArea;
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
    public PmPrjInvest2 setProjectTypeId(String projectTypeId) {
        this.projectTypeId = projectTypeId;
        return this;
    }

    /**
     * 建设规模类型。
     */
    public String conScaleTypeId;

    /**
     * 获取：建设规模类型。
     */
    public String getConScaleTypeId() {
        return this.conScaleTypeId;
    }

    /**
     * 设置：建设规模类型。
     */
    public PmPrjInvest2 setConScaleTypeId(String conScaleTypeId) {
        this.conScaleTypeId = conScaleTypeId;
        return this;
    }

    /**
     * 建设规模数量。
     */
    public Double conScaleQty;

    /**
     * 获取：建设规模数量。
     */
    public Double getConScaleQty() {
        return this.conScaleQty;
    }

    /**
     * 设置：建设规模数量。
     */
    public PmPrjInvest2 setConScaleQty(Double conScaleQty) {
        this.conScaleQty = conScaleQty;
        return this;
    }

    /**
     * 建设规模数量2。
     */
    public Double conScaleQty2;

    /**
     * 获取：建设规模数量2。
     */
    public Double getConScaleQty2() {
        return this.conScaleQty2;
    }

    /**
     * 设置：建设规模数量2。
     */
    public PmPrjInvest2 setConScaleQty2(Double conScaleQty2) {
        this.conScaleQty2 = conScaleQty2;
        return this;
    }

    /**
     * 建设规模单位。
     */
    public String conScaleUomId;

    /**
     * 获取：建设规模单位。
     */
    public String getConScaleUomId() {
        return this.conScaleUomId;
    }

    /**
     * 设置：建设规模单位。
     */
    public PmPrjInvest2 setConScaleUomId(String conScaleUomId) {
        this.conScaleUomId = conScaleUomId;
        return this;
    }

    /**
     * 建设年限。
     */
    public Double buildYears;

    /**
     * 获取：建设年限。
     */
    public Double getBuildYears() {
        return this.buildYears;
    }

    /**
     * 设置：建设年限。
     */
    public PmPrjInvest2 setBuildYears(Double buildYears) {
        this.buildYears = buildYears;
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
    public PmPrjInvest2 setPrjSituation(String prjSituation) {
        this.prjSituation = prjSituation;
        return this;
    }

    /**
     * 招采标流程标题。
     */
    public String biddingNameId;

    /**
     * 获取：招采标流程标题。
     */
    public String getBiddingNameId() {
        return this.biddingNameId;
    }

    /**
     * 设置：招采标流程标题。
     */
    public PmPrjInvest2 setBiddingNameId(String biddingNameId) {
        this.biddingNameId = biddingNameId;
        return this;
    }

    /**
     * 审核状态。
     */
    public String approvalStatus;

    /**
     * 获取：审核状态。
     */
    public String getApprovalStatus() {
        return this.approvalStatus;
    }

    /**
     * 设置：审核状态。
     */
    public PmPrjInvest2 setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
        return this;
    }

    /**
     * 委托单位(文本)。
     */
    public String entrustingUnitText;

    /**
     * 获取：委托单位(文本)。
     */
    public String getEntrustingUnitText() {
        return this.entrustingUnitText;
    }

    /**
     * 设置：委托单位(文本)。
     */
    public PmPrjInvest2 setEntrustingUnitText(String entrustingUnitText) {
        this.entrustingUnitText = entrustingUnitText;
        return this;
    }

    /**
     * 采购经办人。
     */
    public String procureUserId;

    /**
     * 获取：采购经办人。
     */
    public String getProcureUserId() {
        return this.procureUserId;
    }

    /**
     * 设置：采购经办人。
     */
    public PmPrjInvest2 setProcureUserId(String procureUserId) {
        this.procureUserId = procureUserId;
        return this;
    }

    /**
     * 编制单位(文本)。
     */
    public String authorUnitText;

    /**
     * 获取：编制单位(文本)。
     */
    public String getAuthorUnitText() {
        return this.authorUnitText;
    }

    /**
     * 设置：编制单位(文本)。
     */
    public PmPrjInvest2 setAuthorUnitText(String authorUnitText) {
        this.authorUnitText = authorUnitText;
        return this;
    }

    /**
     * 对方负责人。
     */
    public String otherResponsor;

    /**
     * 获取：对方负责人。
     */
    public String getOtherResponsor() {
        return this.otherResponsor;
    }

    /**
     * 设置：对方负责人。
     */
    public PmPrjInvest2 setOtherResponsor(String otherResponsor) {
        this.otherResponsor = otherResponsor;
        return this;
    }

    /**
     * 进场日期。
     */
    public LocalDate inDate;

    /**
     * 获取：进场日期。
     */
    public LocalDate getInDate() {
        return this.inDate;
    }

    /**
     * 设置：进场日期。
     */
    public PmPrjInvest2 setInDate(LocalDate inDate) {
        this.inDate = inDate;
        return this;
    }

    /**
     * 服务周期。
     */
    public Integer serviceDays;

    /**
     * 获取：服务周期。
     */
    public Integer getServiceDays() {
        return this.serviceDays;
    }

    /**
     * 设置：服务周期。
     */
    public PmPrjInvest2 setServiceDays(Integer serviceDays) {
        this.serviceDays = serviceDays;
        return this;
    }

    /**
     * 对方联系方式。
     */
    public String otherContactPhone;

    /**
     * 获取：对方联系方式。
     */
    public String getOtherContactPhone() {
        return this.otherContactPhone;
    }

    /**
     * 设置：对方联系方式。
     */
    public PmPrjInvest2 setOtherContactPhone(String otherContactPhone) {
        this.otherContactPhone = otherContactPhone;
        return this;
    }

    /**
     * 概算申报材料。
     */
    public String budgetestimatedeclarationFile;

    /**
     * 获取：概算申报材料。
     */
    public String getBudgetestimatedeclarationFile() {
        return this.budgetestimatedeclarationFile;
    }

    /**
     * 设置：概算申报材料。
     */
    public PmPrjInvest2 setBudgetestimatedeclarationFile(String budgetestimatedeclarationFile) {
        this.budgetestimatedeclarationFile = budgetestimatedeclarationFile;
        return this;
    }

    /**
     * 概算申报备注。
     */
    public String budgetestimatedeclarationRemark;

    /**
     * 获取：概算申报备注。
     */
    public String getBudgetestimatedeclarationRemark() {
        return this.budgetestimatedeclarationRemark;
    }

    /**
     * 设置：概算申报备注。
     */
    public PmPrjInvest2 setBudgetestimatedeclarationRemark(String budgetestimatedeclarationRemark) {
        this.budgetestimatedeclarationRemark = budgetestimatedeclarationRemark;
        return this;
    }

    /**
     * 专家评审计划完成日期。
     */
    public LocalDate expertComplPlanDate;

    /**
     * 获取：专家评审计划完成日期。
     */
    public LocalDate getExpertComplPlanDate() {
        return this.expertComplPlanDate;
    }

    /**
     * 设置：专家评审计划完成日期。
     */
    public PmPrjInvest2 setExpertComplPlanDate(LocalDate expertComplPlanDate) {
        this.expertComplPlanDate = expertComplPlanDate;
        return this;
    }

    /**
     * 专家评审实际完成日期。
     */
    public LocalDate expertComplActualDate;

    /**
     * 获取：专家评审实际完成日期。
     */
    public LocalDate getExpertComplActualDate() {
        return this.expertComplActualDate;
    }

    /**
     * 设置：专家评审实际完成日期。
     */
    public PmPrjInvest2 setExpertComplActualDate(LocalDate expertComplActualDate) {
        this.expertComplActualDate = expertComplActualDate;
        return this;
    }

    /**
     * 评审单位负责人。
     */
    public String reviewUnitChief;

    /**
     * 获取：评审单位负责人。
     */
    public String getReviewUnitChief() {
        return this.reviewUnitChief;
    }

    /**
     * 设置：评审单位负责人。
     */
    public PmPrjInvest2 setReviewUnitChief(String reviewUnitChief) {
        this.reviewUnitChief = reviewUnitChief;
        return this;
    }

    /**
     * 评审单位(文本)。
     */
    public String reviewUnitText;

    /**
     * 获取：评审单位(文本)。
     */
    public String getReviewUnitText() {
        return this.reviewUnitText;
    }

    /**
     * 设置：评审单位(文本)。
     */
    public PmPrjInvest2 setReviewUnitText(String reviewUnitText) {
        this.reviewUnitText = reviewUnitText;
        return this;
    }

    /**
     * 评审单位联系方式。
     */
    public String reviewUnitPhone;

    /**
     * 获取：评审单位联系方式。
     */
    public String getReviewUnitPhone() {
        return this.reviewUnitPhone;
    }

    /**
     * 设置：评审单位联系方式。
     */
    public PmPrjInvest2 setReviewUnitPhone(String reviewUnitPhone) {
        this.reviewUnitPhone = reviewUnitPhone;
        return this;
    }

    /**
     * 评审稿文件。
     */
    public String reviewReportFile;

    /**
     * 获取：评审稿文件。
     */
    public String getReviewReportFile() {
        return this.reviewReportFile;
    }

    /**
     * 设置：评审稿文件。
     */
    public PmPrjInvest2 setReviewReportFile(String reviewReportFile) {
        this.reviewReportFile = reviewReportFile;
        return this;
    }

    /**
     * 专家意见文件。
     */
    public String expertFile;

    /**
     * 获取：专家意见文件。
     */
    public String getExpertFile() {
        return this.expertFile;
    }

    /**
     * 设置：专家意见文件。
     */
    public PmPrjInvest2 setExpertFile(String expertFile) {
        this.expertFile = expertFile;
        return this;
    }

    /**
     * 修编稿文件。
     */
    public String revisionFile;

    /**
     * 获取：修编稿文件。
     */
    public String getRevisionFile() {
        return this.revisionFile;
    }

    /**
     * 设置：修编稿文件。
     */
    public PmPrjInvest2 setRevisionFile(String revisionFile) {
        this.revisionFile = revisionFile;
        return this;
    }

    /**
     * 评审及修编说明。
     */
    public String reviewAndRevisionRemark;

    /**
     * 获取：评审及修编说明。
     */
    public String getReviewAndRevisionRemark() {
        return this.reviewAndRevisionRemark;
    }

    /**
     * 设置：评审及修编说明。
     */
    public PmPrjInvest2 setReviewAndRevisionRemark(String reviewAndRevisionRemark) {
        this.reviewAndRevisionRemark = reviewAndRevisionRemark;
        return this;
    }

    /**
     * 成本部意见。
     */
    public String costComment;

    /**
     * 获取：成本部意见。
     */
    public String getCostComment() {
        return this.costComment;
    }

    /**
     * 设置：成本部意见。
     */
    public PmPrjInvest2 setCostComment(String costComment) {
        this.costComment = costComment;
        return this;
    }

    /**
     * 设计部意见。
     */
    public String designComment;

    /**
     * 获取：设计部意见。
     */
    public String getDesignComment() {
        return this.designComment;
    }

    /**
     * 设置：设计部意见。
     */
    public PmPrjInvest2 setDesignComment(String designComment) {
        this.designComment = designComment;
        return this;
    }

    /**
     * 前期部意见。
     */
    public String earlyComment;

    /**
     * 获取：前期部意见。
     */
    public String getEarlyComment() {
        return this.earlyComment;
    }

    /**
     * 设置：前期部意见。
     */
    public PmPrjInvest2 setEarlyComment(String earlyComment) {
        this.earlyComment = earlyComment;
        return this;
    }

    /**
     * 计划批复日期。
     */
    public LocalDate replyPlanDate;

    /**
     * 获取：计划批复日期。
     */
    public LocalDate getReplyPlanDate() {
        return this.replyPlanDate;
    }

    /**
     * 设置：计划批复日期。
     */
    public PmPrjInvest2 setReplyPlanDate(LocalDate replyPlanDate) {
        this.replyPlanDate = replyPlanDate;
        return this;
    }

    /**
     * 实际批复日期。
     */
    public LocalDate replyActualDate;

    /**
     * 获取：实际批复日期。
     */
    public LocalDate getReplyActualDate() {
        return this.replyActualDate;
    }

    /**
     * 设置：实际批复日期。
     */
    public PmPrjInvest2 setReplyActualDate(LocalDate replyActualDate) {
        this.replyActualDate = replyActualDate;
        return this;
    }

    /**
     * 批复文号。
     */
    public String replyNo;

    /**
     * 获取：批复文号。
     */
    public String getReplyNo() {
        return this.replyNo;
    }

    /**
     * 设置：批复文号。
     */
    public PmPrjInvest2 setReplyNo(String replyNo) {
        this.replyNo = replyNo;
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
    public PmPrjInvest2 setReplyFile(String replyFile) {
        this.replyFile = replyFile;
        return this;
    }

    /**
     * 总投资（万）。
     */
    public Double prjTotalInvest;

    /**
     * 获取：总投资（万）。
     */
    public Double getPrjTotalInvest() {
        return this.prjTotalInvest;
    }

    /**
     * 设置：总投资（万）。
     */
    public PmPrjInvest2 setPrjTotalInvest(Double prjTotalInvest) {
        this.prjTotalInvest = prjTotalInvest;
        return this;
    }

    /**
     * 工程费用（万）。
     */
    public Double projectAmt;

    /**
     * 获取：工程费用（万）。
     */
    public Double getProjectAmt() {
        return this.projectAmt;
    }

    /**
     * 设置：工程费用（万）。
     */
    public PmPrjInvest2 setProjectAmt(Double projectAmt) {
        this.projectAmt = projectAmt;
        return this;
    }

    /**
     * 建安费（万）。
     */
    public Double constructAmt;

    /**
     * 获取：建安费（万）。
     */
    public Double getConstructAmt() {
        return this.constructAmt;
    }

    /**
     * 设置：建安费（万）。
     */
    public PmPrjInvest2 setConstructAmt(Double constructAmt) {
        this.constructAmt = constructAmt;
        return this;
    }

    /**
     * 设备费（万）。
     */
    public Double equipAmt;

    /**
     * 获取：设备费（万）。
     */
    public Double getEquipAmt() {
        return this.equipAmt;
    }

    /**
     * 设置：设备费（万）。
     */
    public PmPrjInvest2 setEquipAmt(Double equipAmt) {
        this.equipAmt = equipAmt;
        return this;
    }

    /**
     * 工程其他费用（万）。
     */
    public Double projectOtherAmt;

    /**
     * 获取：工程其他费用（万）。
     */
    public Double getProjectOtherAmt() {
        return this.projectOtherAmt;
    }

    /**
     * 设置：工程其他费用（万）。
     */
    public PmPrjInvest2 setProjectOtherAmt(Double projectOtherAmt) {
        this.projectOtherAmt = projectOtherAmt;
        return this;
    }

    /**
     * 土地拆迁费（万）。
     */
    public Double landAmt;

    /**
     * 获取：土地拆迁费（万）。
     */
    public Double getLandAmt() {
        return this.landAmt;
    }

    /**
     * 设置：土地拆迁费（万）。
     */
    public PmPrjInvest2 setLandAmt(Double landAmt) {
        this.landAmt = landAmt;
        return this;
    }

    /**
     * 预备费（万）。
     */
    public Double prepareAmt;

    /**
     * 获取：预备费（万）。
     */
    public Double getPrepareAmt() {
        return this.prepareAmt;
    }

    /**
     * 设置：预备费（万）。
     */
    public PmPrjInvest2 setPrepareAmt(Double prepareAmt) {
        this.prepareAmt = prepareAmt;
        return this;
    }

    /**
     * 建设期利息。
     */
    public Double constructPeriodInterest;

    /**
     * 获取：建设期利息。
     */
    public Double getConstructPeriodInterest() {
        return this.constructPeriodInterest;
    }

    /**
     * 设置：建设期利息。
     */
    public PmPrjInvest2 setConstructPeriodInterest(Double constructPeriodInterest) {
        this.constructPeriodInterest = constructPeriodInterest;
        return this;
    }

    // </editor-fold>

    // 实例方法：
    // <editor-fold>

    /**
     * 根据ID更新数据。ID自身不会更新。
     *
     * @param includeCols 更新时包含的列，空为包含所有。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @param refreshThis 更新后，是否刷新当前对象。刷新时将刷新所有列。
     */
    public void updateById(List<String> includeCols, List<String> excludeCols, boolean refreshThis) {
        modelHelper.updateById(includeCols, excludeCols, refreshThis, this.id);
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
     * 插入数据。
     *
     * @return
     */
    public static PmPrjInvest2 insertData() {
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
    public static PmPrjInvest2 selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PmPrjInvest2> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PmPrjInvest2> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        return modelHelper.selectByWhere(where, includeCols, excludeCols);
    }

    /**
     * 根据ID更新数据。若要更新ID自身，可在keyValueMap里为ID设置新值。
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
     * 根据ID列表更新数据。若要更新ID自身，可在keyValueMap里为ID设置新值。
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
     * 根据Where条件更新数据。若要更新ID自身，可在keyValueMap里为ID设置新值。
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
    public static void copyCols(PmPrjInvest2 fromModel, PmPrjInvest2 toModel, List<String> includeCols, List<String> excludeCols) {
        ModelHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}