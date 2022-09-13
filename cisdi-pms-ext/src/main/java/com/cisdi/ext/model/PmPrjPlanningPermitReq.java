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
 * 工程规划许可证申请。
 */
public class PmPrjPlanningPermitReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmPrjPlanningPermitReq> modelHelper = new ModelHelper<>("PM_PRJ_PLANNING_PERMIT_REQ", new PmPrjPlanningPermitReq());

    // 实体常量：
    // <editor-fold>

    public static final String ENT_CODE = "PM_PRJ_PLANNING_PERMIT_REQ";
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
         * 计划完成日期。
         */
        public static final String COMPL_PLAN_DATE = "COMPL_PLAN_DATE";
        /**
         * 申请时间。
         */
        public static final String APPLY_TIME = "APPLY_TIME";
        /**
         * 申请人。
         */
        public static final String APPLICANT = "APPLICANT";
        /**
         * 环评申请材料。
         */
        public static final String EIA_REQ_FILE = "EIA_REQ_FILE";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
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
         * 评审单位。
         */
        public static final String REVIEW_UNIT = "REVIEW_UNIT";
        /**
         * 评审单位联系方式。
         */
        public static final String REVIEW_UNIT_PHONE = "REVIEW_UNIT_PHONE";
        /**
         * 评审稿文件。
         */
        public static final String REVIEW_REPORT_FILE = "REVIEW_REPORT_FILE";
        /**
         * 评审报告文件。
         */
        public static final String REVIEW_DRAFT_FILE = "REVIEW_DRAFT_FILE";
        /**
         * 修编稿文件。
         */
        public static final String REVISION_FILE = "REVISION_FILE";
        /**
         * 评审及修编说明。
         */
        public static final String REVIEW_AND_REVISION_REMARK = "REVIEW_AND_REVISION_REMARK";
        /**
         * 计划完成日期检查。
         */
        public static final String COMPL_PLAN_DATE_CHECK = "COMPL_PLAN_DATE_CHECK";
        /**
         * 完成日期。
         */
        public static final String COMPL_DATE = "COMPL_DATE";
        /**
         * 编制负责人。
         */
        public static final String AUTHOR_RESPONSE = "AUTHOR_RESPONSE";
        /**
         * 方案检查ppt文件。
         */
        public static final String PLAN_CHECK_PPT_FILE_GROUP_ID = "PLAN_CHECK_PPT_FILE_GROUP_ID";
        /**
         * 管理局局务预审会会议时间。
         */
        public static final String ADMIN_MEETING_DATE = "ADMIN_MEETING_DATE";
        /**
         * 管理局预审会是否通过。
         */
        public static final String ADMIN_IS_PASS = "ADMIN_IS_PASS";
        /**
         * 管理局预审会市委会意见。
         */
        public static final String ADMIN_COMMITTEE_OPINON_FILE_GROUP_ID = "ADMIN_COMMITTEE_OPINON_FILE_GROUP_ID";
        /**
         * 规委会预审会会议时间。
         */
        public static final String PLAN_MEETING_DATE = "PLAN_MEETING_DATE";
        /**
         * 规委会预审会是否通过。
         */
        public static final String PLAN_IS_PASS = "PLAN_IS_PASS";
        /**
         * 规委会预审会市委会意见。
         */
        public static final String PLAN_COMMITTEE_OPINON_FILE_GROUP_ID = "PLAN_COMMITTEE_OPINON_FILE_GROUP_ID";
        /**
         * 分管副市长预审会会议时间。
         */
        public static final String DEPUTY_MAYOR_MEETING_DATE = "DEPUTY_MAYOR_MEETING_DATE";
        /**
         * 分管副市长预审会是否通过。
         */
        public static final String DEPUTY_MAYOR_IS_PASS = "DEPUTY_MAYOR_IS_PASS";
        /**
         * 分管副市长预审会市委会意见。
         */
        public static final String DEPUTY_MAYOR_COMMITTEE_OPINON_FILE_GROUP_ID = "DEPUTY_MAYOR_COMMITTEE_OPINON_FILE_GROUP_ID";
        /**
         * 分管副市长和市长预审会会议时间。
         */
        public static final String MAYOR_MEETING_DATE = "MAYOR_MEETING_DATE";
        /**
         * 分管副市长和市长预审会是否通过。
         */
        public static final String MAYOR_IS_PASS = "MAYOR_IS_PASS";
        /**
         * 分管副市长和市长预审会市委会意见。
         */
        public static final String MAYOR_COMMITTEE_OPINON_FILE_GROUP_ID = "MAYOR_COMMITTEE_OPINON_FILE_GROUP_ID";
        /**
         * 市规委会会议时间。
         */
        public static final String COMMITTEE_MEETING_DATE = "COMMITTEE_MEETING_DATE";
        /**
         * 市规委会是否通过。
         */
        public static final String COMMITTEE_IS_PASS = "COMMITTEE_IS_PASS";
        /**
         * 市委会市规委会意见。
         */
        public static final String COMMITTEE_OPINON_FILE_GROUP_ID = "COMMITTEE_OPINON_FILE_GROUP_ID";
        /**
         * 工程规划许可证申请时间。
         */
        public static final String PRJ_PLAN_APPLY_TIME = "PRJ_PLAN_APPLY_TIME";
        /**
         * 预计完成日期。
         */
        public static final String PLAN_COMPL_DATE = "PLAN_COMPL_DATE";
        /**
         * 工程规划许可证申请人。
         */
        public static final String PRJ_PLAN_APPLICANT = "PRJ_PLAN_APPLICANT";
        /**
         * 工程规划许可证申请材料。
         */
        public static final String PRJ_PLAN_REQ_FILE = "PRJ_PLAN_REQ_FILE";
        /**
         * 工程规划许可证申请备注。
         */
        public static final String PRJ_PLAN_REQ_REMARK = "PRJ_PLAN_REQ_REMARK";
        /**
         * 下发时间。
         */
        public static final String ISSUE_DATE = "ISSUE_DATE";
        /**
         * 工程规划许可证编号。
         */
        public static final String PRJ_PLAN_REQ_CODE = "PRJ_PLAN_REQ_CODE";
        /**
         * 有效期。
         */
        public static final String VALID_TERM = "VALID_TERM";
        /**
         * 工程规划许可证。
         */
        public static final String PRJ_PLAN_REQ_PERMIT_FILE_GROUP_ID = "PRJ_PLAN_REQ_PERMIT_FILE_GROUP_ID";
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
    public PmPrjPlanningPermitReq setId(String id) {
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
    public PmPrjPlanningPermitReq setVer(Integer ver) {
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
    public PmPrjPlanningPermitReq setTs(LocalDateTime ts) {
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
    public PmPrjPlanningPermitReq setIsPreset(Boolean isPreset) {
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
    public PmPrjPlanningPermitReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmPrjPlanningPermitReq setLastModiUserId(String lastModiUserId) {
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
    public PmPrjPlanningPermitReq setCode(String code) {
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
    public PmPrjPlanningPermitReq setName(String name) {
        this.name = name;
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
    public PmPrjPlanningPermitReq setPmPrjId(String pmPrjId) {
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
    public PmPrjPlanningPermitReq setLkWfInstId(String lkWfInstId) {
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
    public PmPrjPlanningPermitReq setPrjCode(String prjCode) {
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
    public PmPrjPlanningPermitReq setStatus(String status) {
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
    public PmPrjPlanningPermitReq setCrtUserId(String crtUserId) {
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
    public PmPrjPlanningPermitReq setCustomerUnit(String customerUnit) {
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
    public PmPrjPlanningPermitReq setPrjManageModeId(String prjManageModeId) {
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
    public PmPrjPlanningPermitReq setCrtDeptId(String crtDeptId) {
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
    public PmPrjPlanningPermitReq setCrtDt(LocalDateTime crtDt) {
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
    public PmPrjPlanningPermitReq setBaseLocationId(String baseLocationId) {
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
    public PmPrjPlanningPermitReq setFloorArea(Double floorArea) {
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
    public PmPrjPlanningPermitReq setProjectTypeId(String projectTypeId) {
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
    public PmPrjPlanningPermitReq setConScaleTypeId(String conScaleTypeId) {
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
    public PmPrjPlanningPermitReq setConScaleQty(Double conScaleQty) {
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
    public PmPrjPlanningPermitReq setConScaleQty2(Double conScaleQty2) {
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
    public PmPrjPlanningPermitReq setConScaleUomId(String conScaleUomId) {
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
    public PmPrjPlanningPermitReq setBuildYears(Double buildYears) {
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
    public PmPrjPlanningPermitReq setPrjSituation(String prjSituation) {
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
    public PmPrjPlanningPermitReq setBiddingNameId(String biddingNameId) {
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
    public PmPrjPlanningPermitReq setApprovalStatus(String approvalStatus) {
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
    public PmPrjPlanningPermitReq setEntrustingUnitText(String entrustingUnitText) {
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
    public PmPrjPlanningPermitReq setProcureUserId(String procureUserId) {
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
    public PmPrjPlanningPermitReq setAuthorUnitText(String authorUnitText) {
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
    public PmPrjPlanningPermitReq setOtherResponsor(String otherResponsor) {
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
    public PmPrjPlanningPermitReq setInDate(LocalDate inDate) {
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
    public PmPrjPlanningPermitReq setServiceDays(Integer serviceDays) {
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
    public PmPrjPlanningPermitReq setOtherContactPhone(String otherContactPhone) {
        this.otherContactPhone = otherContactPhone;
        return this;
    }

    /**
     * 计划完成日期。
     */
    public LocalDate complPlanDate;

    /**
     * 获取：计划完成日期。
     */
    public LocalDate getComplPlanDate() {
        return this.complPlanDate;
    }

    /**
     * 设置：计划完成日期。
     */
    public PmPrjPlanningPermitReq setComplPlanDate(LocalDate complPlanDate) {
        this.complPlanDate = complPlanDate;
        return this;
    }

    /**
     * 申请时间。
     */
    public LocalDate applyTime;

    /**
     * 获取：申请时间。
     */
    public LocalDate getApplyTime() {
        return this.applyTime;
    }

    /**
     * 设置：申请时间。
     */
    public PmPrjPlanningPermitReq setApplyTime(LocalDate applyTime) {
        this.applyTime = applyTime;
        return this;
    }

    /**
     * 申请人。
     */
    public String applicant;

    /**
     * 获取：申请人。
     */
    public String getApplicant() {
        return this.applicant;
    }

    /**
     * 设置：申请人。
     */
    public PmPrjPlanningPermitReq setApplicant(String applicant) {
        this.applicant = applicant;
        return this;
    }

    /**
     * 环评申请材料。
     */
    public String eiaReqFile;

    /**
     * 获取：环评申请材料。
     */
    public String getEiaReqFile() {
        return this.eiaReqFile;
    }

    /**
     * 设置：环评申请材料。
     */
    public PmPrjPlanningPermitReq setEiaReqFile(String eiaReqFile) {
        this.eiaReqFile = eiaReqFile;
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
    public PmPrjPlanningPermitReq setRemark(String remark) {
        this.remark = remark;
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
    public PmPrjPlanningPermitReq setExpertComplPlanDate(LocalDate expertComplPlanDate) {
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
    public PmPrjPlanningPermitReq setExpertComplActualDate(LocalDate expertComplActualDate) {
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
    public PmPrjPlanningPermitReq setReviewUnitChief(String reviewUnitChief) {
        this.reviewUnitChief = reviewUnitChief;
        return this;
    }

    /**
     * 评审单位。
     */
    public String reviewUnit;

    /**
     * 获取：评审单位。
     */
    public String getReviewUnit() {
        return this.reviewUnit;
    }

    /**
     * 设置：评审单位。
     */
    public PmPrjPlanningPermitReq setReviewUnit(String reviewUnit) {
        this.reviewUnit = reviewUnit;
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
    public PmPrjPlanningPermitReq setReviewUnitPhone(String reviewUnitPhone) {
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
    public PmPrjPlanningPermitReq setReviewReportFile(String reviewReportFile) {
        this.reviewReportFile = reviewReportFile;
        return this;
    }

    /**
     * 评审报告文件。
     */
    public String reviewDraftFile;

    /**
     * 获取：评审报告文件。
     */
    public String getReviewDraftFile() {
        return this.reviewDraftFile;
    }

    /**
     * 设置：评审报告文件。
     */
    public PmPrjPlanningPermitReq setReviewDraftFile(String reviewDraftFile) {
        this.reviewDraftFile = reviewDraftFile;
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
    public PmPrjPlanningPermitReq setRevisionFile(String revisionFile) {
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
    public PmPrjPlanningPermitReq setReviewAndRevisionRemark(String reviewAndRevisionRemark) {
        this.reviewAndRevisionRemark = reviewAndRevisionRemark;
        return this;
    }

    /**
     * 计划完成日期检查。
     */
    public LocalDate complPlanDateCheck;

    /**
     * 获取：计划完成日期检查。
     */
    public LocalDate getComplPlanDateCheck() {
        return this.complPlanDateCheck;
    }

    /**
     * 设置：计划完成日期检查。
     */
    public PmPrjPlanningPermitReq setComplPlanDateCheck(LocalDate complPlanDateCheck) {
        this.complPlanDateCheck = complPlanDateCheck;
        return this;
    }

    /**
     * 完成日期。
     */
    public LocalDate complDate;

    /**
     * 获取：完成日期。
     */
    public LocalDate getComplDate() {
        return this.complDate;
    }

    /**
     * 设置：完成日期。
     */
    public PmPrjPlanningPermitReq setComplDate(LocalDate complDate) {
        this.complDate = complDate;
        return this;
    }

    /**
     * 编制负责人。
     */
    public String authorResponse;

    /**
     * 获取：编制负责人。
     */
    public String getAuthorResponse() {
        return this.authorResponse;
    }

    /**
     * 设置：编制负责人。
     */
    public PmPrjPlanningPermitReq setAuthorResponse(String authorResponse) {
        this.authorResponse = authorResponse;
        return this;
    }

    /**
     * 方案检查ppt文件。
     */
    public String planCheckPptFileGroupId;

    /**
     * 获取：方案检查ppt文件。
     */
    public String getPlanCheckPptFileGroupId() {
        return this.planCheckPptFileGroupId;
    }

    /**
     * 设置：方案检查ppt文件。
     */
    public PmPrjPlanningPermitReq setPlanCheckPptFileGroupId(String planCheckPptFileGroupId) {
        this.planCheckPptFileGroupId = planCheckPptFileGroupId;
        return this;
    }

    /**
     * 管理局局务预审会会议时间。
     */
    public LocalDate adminMeetingDate;

    /**
     * 获取：管理局局务预审会会议时间。
     */
    public LocalDate getAdminMeetingDate() {
        return this.adminMeetingDate;
    }

    /**
     * 设置：管理局局务预审会会议时间。
     */
    public PmPrjPlanningPermitReq setAdminMeetingDate(LocalDate adminMeetingDate) {
        this.adminMeetingDate = adminMeetingDate;
        return this;
    }

    /**
     * 管理局预审会是否通过。
     */
    public Boolean adminIsPass;

    /**
     * 获取：管理局预审会是否通过。
     */
    public Boolean getAdminIsPass() {
        return this.adminIsPass;
    }

    /**
     * 设置：管理局预审会是否通过。
     */
    public PmPrjPlanningPermitReq setAdminIsPass(Boolean adminIsPass) {
        this.adminIsPass = adminIsPass;
        return this;
    }

    /**
     * 管理局预审会市委会意见。
     */
    public String adminCommitteeOpinonFileGroupId;

    /**
     * 获取：管理局预审会市委会意见。
     */
    public String getAdminCommitteeOpinonFileGroupId() {
        return this.adminCommitteeOpinonFileGroupId;
    }

    /**
     * 设置：管理局预审会市委会意见。
     */
    public PmPrjPlanningPermitReq setAdminCommitteeOpinonFileGroupId(String adminCommitteeOpinonFileGroupId) {
        this.adminCommitteeOpinonFileGroupId = adminCommitteeOpinonFileGroupId;
        return this;
    }

    /**
     * 规委会预审会会议时间。
     */
    public LocalDate planMeetingDate;

    /**
     * 获取：规委会预审会会议时间。
     */
    public LocalDate getPlanMeetingDate() {
        return this.planMeetingDate;
    }

    /**
     * 设置：规委会预审会会议时间。
     */
    public PmPrjPlanningPermitReq setPlanMeetingDate(LocalDate planMeetingDate) {
        this.planMeetingDate = planMeetingDate;
        return this;
    }

    /**
     * 规委会预审会是否通过。
     */
    public Boolean planIsPass;

    /**
     * 获取：规委会预审会是否通过。
     */
    public Boolean getPlanIsPass() {
        return this.planIsPass;
    }

    /**
     * 设置：规委会预审会是否通过。
     */
    public PmPrjPlanningPermitReq setPlanIsPass(Boolean planIsPass) {
        this.planIsPass = planIsPass;
        return this;
    }

    /**
     * 规委会预审会市委会意见。
     */
    public String planCommitteeOpinonFileGroupId;

    /**
     * 获取：规委会预审会市委会意见。
     */
    public String getPlanCommitteeOpinonFileGroupId() {
        return this.planCommitteeOpinonFileGroupId;
    }

    /**
     * 设置：规委会预审会市委会意见。
     */
    public PmPrjPlanningPermitReq setPlanCommitteeOpinonFileGroupId(String planCommitteeOpinonFileGroupId) {
        this.planCommitteeOpinonFileGroupId = planCommitteeOpinonFileGroupId;
        return this;
    }

    /**
     * 分管副市长预审会会议时间。
     */
    public LocalDate deputyMayorMeetingDate;

    /**
     * 获取：分管副市长预审会会议时间。
     */
    public LocalDate getDeputyMayorMeetingDate() {
        return this.deputyMayorMeetingDate;
    }

    /**
     * 设置：分管副市长预审会会议时间。
     */
    public PmPrjPlanningPermitReq setDeputyMayorMeetingDate(LocalDate deputyMayorMeetingDate) {
        this.deputyMayorMeetingDate = deputyMayorMeetingDate;
        return this;
    }

    /**
     * 分管副市长预审会是否通过。
     */
    public Boolean deputyMayorIsPass;

    /**
     * 获取：分管副市长预审会是否通过。
     */
    public Boolean getDeputyMayorIsPass() {
        return this.deputyMayorIsPass;
    }

    /**
     * 设置：分管副市长预审会是否通过。
     */
    public PmPrjPlanningPermitReq setDeputyMayorIsPass(Boolean deputyMayorIsPass) {
        this.deputyMayorIsPass = deputyMayorIsPass;
        return this;
    }

    /**
     * 分管副市长预审会市委会意见。
     */
    public String deputyMayorCommitteeOpinonFileGroupId;

    /**
     * 获取：分管副市长预审会市委会意见。
     */
    public String getDeputyMayorCommitteeOpinonFileGroupId() {
        return this.deputyMayorCommitteeOpinonFileGroupId;
    }

    /**
     * 设置：分管副市长预审会市委会意见。
     */
    public PmPrjPlanningPermitReq setDeputyMayorCommitteeOpinonFileGroupId(String deputyMayorCommitteeOpinonFileGroupId) {
        this.deputyMayorCommitteeOpinonFileGroupId = deputyMayorCommitteeOpinonFileGroupId;
        return this;
    }

    /**
     * 分管副市长和市长预审会会议时间。
     */
    public LocalDate mayorMeetingDate;

    /**
     * 获取：分管副市长和市长预审会会议时间。
     */
    public LocalDate getMayorMeetingDate() {
        return this.mayorMeetingDate;
    }

    /**
     * 设置：分管副市长和市长预审会会议时间。
     */
    public PmPrjPlanningPermitReq setMayorMeetingDate(LocalDate mayorMeetingDate) {
        this.mayorMeetingDate = mayorMeetingDate;
        return this;
    }

    /**
     * 分管副市长和市长预审会是否通过。
     */
    public Boolean mayorIsPass;

    /**
     * 获取：分管副市长和市长预审会是否通过。
     */
    public Boolean getMayorIsPass() {
        return this.mayorIsPass;
    }

    /**
     * 设置：分管副市长和市长预审会是否通过。
     */
    public PmPrjPlanningPermitReq setMayorIsPass(Boolean mayorIsPass) {
        this.mayorIsPass = mayorIsPass;
        return this;
    }

    /**
     * 分管副市长和市长预审会市委会意见。
     */
    public String mayorCommitteeOpinonFileGroupId;

    /**
     * 获取：分管副市长和市长预审会市委会意见。
     */
    public String getMayorCommitteeOpinonFileGroupId() {
        return this.mayorCommitteeOpinonFileGroupId;
    }

    /**
     * 设置：分管副市长和市长预审会市委会意见。
     */
    public PmPrjPlanningPermitReq setMayorCommitteeOpinonFileGroupId(String mayorCommitteeOpinonFileGroupId) {
        this.mayorCommitteeOpinonFileGroupId = mayorCommitteeOpinonFileGroupId;
        return this;
    }

    /**
     * 市规委会会议时间。
     */
    public LocalDate committeeMeetingDate;

    /**
     * 获取：市规委会会议时间。
     */
    public LocalDate getCommitteeMeetingDate() {
        return this.committeeMeetingDate;
    }

    /**
     * 设置：市规委会会议时间。
     */
    public PmPrjPlanningPermitReq setCommitteeMeetingDate(LocalDate committeeMeetingDate) {
        this.committeeMeetingDate = committeeMeetingDate;
        return this;
    }

    /**
     * 市规委会是否通过。
     */
    public Boolean committeeIsPass;

    /**
     * 获取：市规委会是否通过。
     */
    public Boolean getCommitteeIsPass() {
        return this.committeeIsPass;
    }

    /**
     * 设置：市规委会是否通过。
     */
    public PmPrjPlanningPermitReq setCommitteeIsPass(Boolean committeeIsPass) {
        this.committeeIsPass = committeeIsPass;
        return this;
    }

    /**
     * 市委会市规委会意见。
     */
    public String committeeOpinonFileGroupId;

    /**
     * 获取：市委会市规委会意见。
     */
    public String getCommitteeOpinonFileGroupId() {
        return this.committeeOpinonFileGroupId;
    }

    /**
     * 设置：市委会市规委会意见。
     */
    public PmPrjPlanningPermitReq setCommitteeOpinonFileGroupId(String committeeOpinonFileGroupId) {
        this.committeeOpinonFileGroupId = committeeOpinonFileGroupId;
        return this;
    }

    /**
     * 工程规划许可证申请时间。
     */
    public LocalDate prjPlanApplyTime;

    /**
     * 获取：工程规划许可证申请时间。
     */
    public LocalDate getPrjPlanApplyTime() {
        return this.prjPlanApplyTime;
    }

    /**
     * 设置：工程规划许可证申请时间。
     */
    public PmPrjPlanningPermitReq setPrjPlanApplyTime(LocalDate prjPlanApplyTime) {
        this.prjPlanApplyTime = prjPlanApplyTime;
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
    public PmPrjPlanningPermitReq setPlanComplDate(LocalDate planComplDate) {
        this.planComplDate = planComplDate;
        return this;
    }

    /**
     * 工程规划许可证申请人。
     */
    public String prjPlanApplicant;

    /**
     * 获取：工程规划许可证申请人。
     */
    public String getPrjPlanApplicant() {
        return this.prjPlanApplicant;
    }

    /**
     * 设置：工程规划许可证申请人。
     */
    public PmPrjPlanningPermitReq setPrjPlanApplicant(String prjPlanApplicant) {
        this.prjPlanApplicant = prjPlanApplicant;
        return this;
    }

    /**
     * 工程规划许可证申请材料。
     */
    public String prjPlanReqFile;

    /**
     * 获取：工程规划许可证申请材料。
     */
    public String getPrjPlanReqFile() {
        return this.prjPlanReqFile;
    }

    /**
     * 设置：工程规划许可证申请材料。
     */
    public PmPrjPlanningPermitReq setPrjPlanReqFile(String prjPlanReqFile) {
        this.prjPlanReqFile = prjPlanReqFile;
        return this;
    }

    /**
     * 工程规划许可证申请备注。
     */
    public String prjPlanReqRemark;

    /**
     * 获取：工程规划许可证申请备注。
     */
    public String getPrjPlanReqRemark() {
        return this.prjPlanReqRemark;
    }

    /**
     * 设置：工程规划许可证申请备注。
     */
    public PmPrjPlanningPermitReq setPrjPlanReqRemark(String prjPlanReqRemark) {
        this.prjPlanReqRemark = prjPlanReqRemark;
        return this;
    }

    /**
     * 下发时间。
     */
    public LocalDate issueDate;

    /**
     * 获取：下发时间。
     */
    public LocalDate getIssueDate() {
        return this.issueDate;
    }

    /**
     * 设置：下发时间。
     */
    public PmPrjPlanningPermitReq setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    /**
     * 工程规划许可证编号。
     */
    public String prjPlanReqCode;

    /**
     * 获取：工程规划许可证编号。
     */
    public String getPrjPlanReqCode() {
        return this.prjPlanReqCode;
    }

    /**
     * 设置：工程规划许可证编号。
     */
    public PmPrjPlanningPermitReq setPrjPlanReqCode(String prjPlanReqCode) {
        this.prjPlanReqCode = prjPlanReqCode;
        return this;
    }

    /**
     * 有效期。
     */
    public String validTerm;

    /**
     * 获取：有效期。
     */
    public String getValidTerm() {
        return this.validTerm;
    }

    /**
     * 设置：有效期。
     */
    public PmPrjPlanningPermitReq setValidTerm(String validTerm) {
        this.validTerm = validTerm;
        return this;
    }

    /**
     * 工程规划许可证。
     */
    public String prjPlanReqPermitFileGroupId;

    /**
     * 获取：工程规划许可证。
     */
    public String getPrjPlanReqPermitFileGroupId() {
        return this.prjPlanReqPermitFileGroupId;
    }

    /**
     * 设置：工程规划许可证。
     */
    public PmPrjPlanningPermitReq setPrjPlanReqPermitFileGroupId(String prjPlanReqPermitFileGroupId) {
        this.prjPlanReqPermitFileGroupId = prjPlanReqPermitFileGroupId;
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
    public static PmPrjPlanningPermitReq newData() {
        return modelHelper.newData();
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmPrjPlanningPermitReq insertData() {
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
    public static PmPrjPlanningPermitReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PmPrjPlanningPermitReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PmPrjPlanningPermitReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
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
    public static void copyCols(PmPrjPlanningPermitReq fromModel, PmPrjPlanningPermitReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}