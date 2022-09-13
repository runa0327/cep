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
 * 预算财评。
 */
public class PmPrjInvest3 {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmPrjInvest3> modelHelper = new ModelHelper<>("PM_PRJ_INVEST3", new PmPrjInvest3());

    // 实体常量：
    // <editor-fold>

    public static final String ENT_CODE = "PM_PRJ_INVEST3";
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
         * 建设地点。
         */
        public static final String BASE_LOCATION_ID = "BASE_LOCATION_ID";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
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
         * 项目概况。
         */
        public static final String PRJ_SITUATION = "PRJ_SITUATION";
        /**
         * 概算申报材料。
         */
        public static final String BUDGETESTIMATEDECLARATION_FILE = "BUDGETESTIMATEDECLARATION_FILE";
        /**
         * 施工图预算材料备注。
         */
        public static final String WORK_DRAWING_BUDGET_MATERIALS_REMARK = "WORK_DRAWING_BUDGET_MATERIALS_REMARK";
        /**
         * 成本部初审意见。
         */
        public static final String COST_FIRST_REVIEW_COMMENT = "COST_FIRST_REVIEW_COMMENT";
        /**
         * 施工图预算送审稿。
         */
        public static final String WORK_DRAWING_TO_REVIEW_FILE = "WORK_DRAWING_TO_REVIEW_FILE";
        /**
         * 预算审核结论材料。
         */
        public static final String BUDGET_REVIEW_CONCLUSION_FILE = "BUDGET_REVIEW_CONCLUSION_FILE";
        /**
         * 施工图预算送审备注。
         */
        public static final String WORK_DRAWING_BUDGET_TO_REVIEW_REMARK = "WORK_DRAWING_BUDGET_TO_REVIEW_REMARK";
        /**
         * 成本部复审意见。
         */
        public static final String COST_SECOND_REVIEW_COMMENT = "COST_SECOND_REVIEW_COMMENT";
        /**
         * 财评批复计划时间。
         */
        public static final String FINANCIAL_PLAN_DATE = "FINANCIAL_PLAN_DATE";
        /**
         * 财评批复实际时间。
         */
        public static final String FINANCIAL_ACTUAL_DATE = "FINANCIAL_ACTUAL_DATE";
        /**
         * 批复文号。
         */
        public static final String REPLY_NO = "REPLY_NO";
        /**
         * 财评批复材料。
         */
        public static final String FINANCIAL_REVIEW_FILE = "FINANCIAL_REVIEW_FILE";
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
    public PmPrjInvest3 setId(String id) {
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
    public PmPrjInvest3 setVer(Integer ver) {
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
    public PmPrjInvest3 setTs(LocalDateTime ts) {
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
    public PmPrjInvest3 setIsPreset(Boolean isPreset) {
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
    public PmPrjInvest3 setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmPrjInvest3 setLastModiUserId(String lastModiUserId) {
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
    public PmPrjInvest3 setCode(String code) {
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
    public PmPrjInvest3 setName(String name) {
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
    public PmPrjInvest3 setRemark(String remark) {
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
    public PmPrjInvest3 setPmPrjId(String pmPrjId) {
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
    public PmPrjInvest3 setLkWfInstId(String lkWfInstId) {
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
    public PmPrjInvest3 setPrjCode(String prjCode) {
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
    public PmPrjInvest3 setStatus(String status) {
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
    public PmPrjInvest3 setCrtUserId(String crtUserId) {
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
    public PmPrjInvest3 setCustomerUnit(String customerUnit) {
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
    public PmPrjInvest3 setPrjManageModeId(String prjManageModeId) {
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
    public PmPrjInvest3 setCrtDeptId(String crtDeptId) {
        this.crtDeptId = crtDeptId;
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
    public PmPrjInvest3 setBaseLocationId(String baseLocationId) {
        this.baseLocationId = baseLocationId;
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
    public PmPrjInvest3 setCrtDt(LocalDateTime crtDt) {
        this.crtDt = crtDt;
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
    public PmPrjInvest3 setFloorArea(Double floorArea) {
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
    public PmPrjInvest3 setProjectTypeId(String projectTypeId) {
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
    public PmPrjInvest3 setConScaleTypeId(String conScaleTypeId) {
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
    public PmPrjInvest3 setConScaleQty(Double conScaleQty) {
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
    public PmPrjInvest3 setConScaleQty2(Double conScaleQty2) {
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
    public PmPrjInvest3 setConScaleUomId(String conScaleUomId) {
        this.conScaleUomId = conScaleUomId;
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
    public PmPrjInvest3 setPrjSituation(String prjSituation) {
        this.prjSituation = prjSituation;
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
    public PmPrjInvest3 setBudgetestimatedeclarationFile(String budgetestimatedeclarationFile) {
        this.budgetestimatedeclarationFile = budgetestimatedeclarationFile;
        return this;
    }

    /**
     * 施工图预算材料备注。
     */
    public String workDrawingBudgetMaterialsRemark;

    /**
     * 获取：施工图预算材料备注。
     */
    public String getWorkDrawingBudgetMaterialsRemark() {
        return this.workDrawingBudgetMaterialsRemark;
    }

    /**
     * 设置：施工图预算材料备注。
     */
    public PmPrjInvest3 setWorkDrawingBudgetMaterialsRemark(String workDrawingBudgetMaterialsRemark) {
        this.workDrawingBudgetMaterialsRemark = workDrawingBudgetMaterialsRemark;
        return this;
    }

    /**
     * 成本部初审意见。
     */
    public String costFirstReviewComment;

    /**
     * 获取：成本部初审意见。
     */
    public String getCostFirstReviewComment() {
        return this.costFirstReviewComment;
    }

    /**
     * 设置：成本部初审意见。
     */
    public PmPrjInvest3 setCostFirstReviewComment(String costFirstReviewComment) {
        this.costFirstReviewComment = costFirstReviewComment;
        return this;
    }

    /**
     * 施工图预算送审稿。
     */
    public String workDrawingToReviewFile;

    /**
     * 获取：施工图预算送审稿。
     */
    public String getWorkDrawingToReviewFile() {
        return this.workDrawingToReviewFile;
    }

    /**
     * 设置：施工图预算送审稿。
     */
    public PmPrjInvest3 setWorkDrawingToReviewFile(String workDrawingToReviewFile) {
        this.workDrawingToReviewFile = workDrawingToReviewFile;
        return this;
    }

    /**
     * 预算审核结论材料。
     */
    public String budgetReviewConclusionFile;

    /**
     * 获取：预算审核结论材料。
     */
    public String getBudgetReviewConclusionFile() {
        return this.budgetReviewConclusionFile;
    }

    /**
     * 设置：预算审核结论材料。
     */
    public PmPrjInvest3 setBudgetReviewConclusionFile(String budgetReviewConclusionFile) {
        this.budgetReviewConclusionFile = budgetReviewConclusionFile;
        return this;
    }

    /**
     * 施工图预算送审备注。
     */
    public String workDrawingBudgetToReviewRemark;

    /**
     * 获取：施工图预算送审备注。
     */
    public String getWorkDrawingBudgetToReviewRemark() {
        return this.workDrawingBudgetToReviewRemark;
    }

    /**
     * 设置：施工图预算送审备注。
     */
    public PmPrjInvest3 setWorkDrawingBudgetToReviewRemark(String workDrawingBudgetToReviewRemark) {
        this.workDrawingBudgetToReviewRemark = workDrawingBudgetToReviewRemark;
        return this;
    }

    /**
     * 成本部复审意见。
     */
    public String costSecondReviewComment;

    /**
     * 获取：成本部复审意见。
     */
    public String getCostSecondReviewComment() {
        return this.costSecondReviewComment;
    }

    /**
     * 设置：成本部复审意见。
     */
    public PmPrjInvest3 setCostSecondReviewComment(String costSecondReviewComment) {
        this.costSecondReviewComment = costSecondReviewComment;
        return this;
    }

    /**
     * 财评批复计划时间。
     */
    public LocalDate financialPlanDate;

    /**
     * 获取：财评批复计划时间。
     */
    public LocalDate getFinancialPlanDate() {
        return this.financialPlanDate;
    }

    /**
     * 设置：财评批复计划时间。
     */
    public PmPrjInvest3 setFinancialPlanDate(LocalDate financialPlanDate) {
        this.financialPlanDate = financialPlanDate;
        return this;
    }

    /**
     * 财评批复实际时间。
     */
    public LocalDate financialActualDate;

    /**
     * 获取：财评批复实际时间。
     */
    public LocalDate getFinancialActualDate() {
        return this.financialActualDate;
    }

    /**
     * 设置：财评批复实际时间。
     */
    public PmPrjInvest3 setFinancialActualDate(LocalDate financialActualDate) {
        this.financialActualDate = financialActualDate;
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
    public PmPrjInvest3 setReplyNo(String replyNo) {
        this.replyNo = replyNo;
        return this;
    }

    /**
     * 财评批复材料。
     */
    public String financialReviewFile;

    /**
     * 获取：财评批复材料。
     */
    public String getFinancialReviewFile() {
        return this.financialReviewFile;
    }

    /**
     * 设置：财评批复材料。
     */
    public PmPrjInvest3 setFinancialReviewFile(String financialReviewFile) {
        this.financialReviewFile = financialReviewFile;
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
    public PmPrjInvest3 setPrjTotalInvest(Double prjTotalInvest) {
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
    public PmPrjInvest3 setProjectAmt(Double projectAmt) {
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
    public PmPrjInvest3 setConstructAmt(Double constructAmt) {
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
    public PmPrjInvest3 setEquipAmt(Double equipAmt) {
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
    public PmPrjInvest3 setProjectOtherAmt(Double projectOtherAmt) {
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
    public PmPrjInvest3 setLandAmt(Double landAmt) {
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
    public PmPrjInvest3 setPrepareAmt(Double prepareAmt) {
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
    public PmPrjInvest3 setConstructPeriodInterest(Double constructPeriodInterest) {
        this.constructPeriodInterest = constructPeriodInterest;
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
    public static PmPrjInvest3 newData() {
        return modelHelper.newData();
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmPrjInvest3 insertData() {
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
    public static PmPrjInvest3 selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PmPrjInvest3> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PmPrjInvest3> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
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
    public static void copyCols(PmPrjInvest3 fromModel, PmPrjInvest3 toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}