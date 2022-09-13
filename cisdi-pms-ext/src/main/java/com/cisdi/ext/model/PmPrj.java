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
 * 项目。
 */
public class PmPrj {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmPrj> modelHelper = new ModelHelper<>("PM_PRJ", new PmPrj());

    // 实体常量：
    // <editor-fold>

    public static final String ENT_CODE = "PM_PRJ";
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
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 最后修改日期时间。
         */
        public static final String LAST_MODI_DT = "LAST_MODI_DT";
        /**
         * 最后修改用户。
         */
        public static final String LAST_MODI_USER_ID = "LAST_MODI_USER_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 锁定流程实例。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
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
         * 业主单位。
         */
        public static final String CUSTOMER_UNIT = "CUSTOMER_UNIT";
        /**
         * 项目管理模式。
         */
        public static final String PRJ_MANAGE_MODE_ID = "PRJ_MANAGE_MODE_ID";
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
         * 投资来源。
         */
        public static final String INVESTMENT_SOURCE_ID = "INVESTMENT_SOURCE_ID";
        /**
         * EPC类型。
         */
        public static final String BASE_EPC_ID = "BASE_EPC_ID";
        /**
         * 项目状态。
         */
        public static final String PROJECT_PHASE_ID = "PROJECT_PHASE_ID";
        /**
         * 进度阶段。
         */
        public static final String TRANSITION_PHASE_ID = "TRANSITION_PHASE_ID";
        /**
         * 立项申请。
         */
        public static final String PM_PRJ_REQ_ID = "PM_PRJ_REQ_ID";
        /**
         * 立项批复文号。
         */
        public static final String PRJ_REPLY_NO = "PRJ_REPLY_NO";
        /**
         * 立项批复日期。
         */
        public static final String PRJ_REPLY_DATE = "PRJ_REPLY_DATE";
        /**
         * 立项批复材料。
         */
        public static final String PRJ_REPLY_FILE = "PRJ_REPLY_FILE";
        /**
         * 项目前期岗。
         */
        public static final String PRJ_EARLY_USER_ID = "PRJ_EARLY_USER_ID";
        /**
         * 项目设计岗。
         */
        public static final String PRJ_DESIGN_USER_ID = "PRJ_DESIGN_USER_ID";
        /**
         * 项目成本岗。
         */
        public static final String PRJ_COST_USER_ID = "PRJ_COST_USER_ID";
        /**
         * 进入省库。
         */
        public static final String IN_PROVINCE_REP = "IN_PROVINCE_REP";
        /**
         * 进入国库。
         */
        public static final String IN_COUNTRY_REP = "IN_COUNTRY_REP";
        /**
         * 建设单位。
         */
        public static final String BUILDER_UNIT = "BUILDER_UNIT";
        /**
         * 勘察单位。
         */
        public static final String SURVEYOR_UNIT = "SURVEYOR_UNIT";
        /**
         * 设计单位。
         */
        public static final String DESIGNER_UNIT = "DESIGNER_UNIT";
        /**
         * 施工单位。
         */
        public static final String CONSTRUCTOR_UNIT = "CONSTRUCTOR_UNIT";
        /**
         * 监理单位。
         */
        public static final String SUPERVISOR_UNIT = "SUPERVISOR_UNIT";
        /**
         * 检测单位。
         */
        public static final String CHECKER_UNIT = "CHECKER_UNIT";
        /**
         * 全过程造价单位。
         */
        public static final String CONSULTER_UNIT = "CONSULTER_UNIT";
        /**
         * 项目图片。
         */
        public static final String PRJ_IMG = "PRJ_IMG";
        /**
         * CPMS的UUID。
         */
        public static final String CPMS_UUID = "CPMS_UUID";
        /**
         * CPMS的ID。
         */
        public static final String CPMS_ID = "CPMS_ID";
        /**
         * CPMS建设地点。
         */
        public static final String CPMS_CONSTRUCTION_SITE = "CPMS_CONSTRUCTION_SITE";
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
    public PmPrj setId(String id) {
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
    public PmPrj setVer(Integer ver) {
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
    public PmPrj setTs(LocalDateTime ts) {
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
    public PmPrj setIsPreset(Boolean isPreset) {
        this.isPreset = isPreset;
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
    public PmPrj setCrtDt(LocalDateTime crtDt) {
        this.crtDt = crtDt;
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
    public PmPrj setCrtUserId(String crtUserId) {
        this.crtUserId = crtUserId;
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
    public PmPrj setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmPrj setLastModiUserId(String lastModiUserId) {
        this.lastModiUserId = lastModiUserId;
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
    public PmPrj setStatus(String status) {
        this.status = status;
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
    public PmPrj setLkWfInstId(String lkWfInstId) {
        this.lkWfInstId = lkWfInstId;
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
    public PmPrj setCode(String code) {
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
    public PmPrj setName(String name) {
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
    public PmPrj setRemark(String remark) {
        this.remark = remark;
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
    public PmPrj setCustomerUnit(String customerUnit) {
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
    public PmPrj setPrjManageModeId(String prjManageModeId) {
        this.prjManageModeId = prjManageModeId;
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
    public PmPrj setBaseLocationId(String baseLocationId) {
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
    public PmPrj setFloorArea(Double floorArea) {
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
    public PmPrj setProjectTypeId(String projectTypeId) {
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
    public PmPrj setConScaleTypeId(String conScaleTypeId) {
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
    public PmPrj setConScaleQty(Double conScaleQty) {
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
    public PmPrj setConScaleQty2(Double conScaleQty2) {
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
    public PmPrj setConScaleUomId(String conScaleUomId) {
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
    public PmPrj setBuildYears(Double buildYears) {
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
    public PmPrj setPrjSituation(String prjSituation) {
        this.prjSituation = prjSituation;
        return this;
    }

    /**
     * 投资来源。
     */
    public String investmentSourceId;

    /**
     * 获取：投资来源。
     */
    public String getInvestmentSourceId() {
        return this.investmentSourceId;
    }

    /**
     * 设置：投资来源。
     */
    public PmPrj setInvestmentSourceId(String investmentSourceId) {
        this.investmentSourceId = investmentSourceId;
        return this;
    }

    /**
     * EPC类型。
     */
    public String baseEpcId;

    /**
     * 获取：EPC类型。
     */
    public String getBaseEpcId() {
        return this.baseEpcId;
    }

    /**
     * 设置：EPC类型。
     */
    public PmPrj setBaseEpcId(String baseEpcId) {
        this.baseEpcId = baseEpcId;
        return this;
    }

    /**
     * 项目状态。
     */
    public String projectPhaseId;

    /**
     * 获取：项目状态。
     */
    public String getProjectPhaseId() {
        return this.projectPhaseId;
    }

    /**
     * 设置：项目状态。
     */
    public PmPrj setProjectPhaseId(String projectPhaseId) {
        this.projectPhaseId = projectPhaseId;
        return this;
    }

    /**
     * 进度阶段。
     */
    public String transitionPhaseId;

    /**
     * 获取：进度阶段。
     */
    public String getTransitionPhaseId() {
        return this.transitionPhaseId;
    }

    /**
     * 设置：进度阶段。
     */
    public PmPrj setTransitionPhaseId(String transitionPhaseId) {
        this.transitionPhaseId = transitionPhaseId;
        return this;
    }

    /**
     * 立项申请。
     */
    public String pmPrjReqId;

    /**
     * 获取：立项申请。
     */
    public String getPmPrjReqId() {
        return this.pmPrjReqId;
    }

    /**
     * 设置：立项申请。
     */
    public PmPrj setPmPrjReqId(String pmPrjReqId) {
        this.pmPrjReqId = pmPrjReqId;
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
    public PmPrj setPrjReplyNo(String prjReplyNo) {
        this.prjReplyNo = prjReplyNo;
        return this;
    }

    /**
     * 立项批复日期。
     */
    public LocalDate prjReplyDate;

    /**
     * 获取：立项批复日期。
     */
    public LocalDate getPrjReplyDate() {
        return this.prjReplyDate;
    }

    /**
     * 设置：立项批复日期。
     */
    public PmPrj setPrjReplyDate(LocalDate prjReplyDate) {
        this.prjReplyDate = prjReplyDate;
        return this;
    }

    /**
     * 立项批复材料。
     */
    public String prjReplyFile;

    /**
     * 获取：立项批复材料。
     */
    public String getPrjReplyFile() {
        return this.prjReplyFile;
    }

    /**
     * 设置：立项批复材料。
     */
    public PmPrj setPrjReplyFile(String prjReplyFile) {
        this.prjReplyFile = prjReplyFile;
        return this;
    }

    /**
     * 项目前期岗。
     */
    public String prjEarlyUserId;

    /**
     * 获取：项目前期岗。
     */
    public String getPrjEarlyUserId() {
        return this.prjEarlyUserId;
    }

    /**
     * 设置：项目前期岗。
     */
    public PmPrj setPrjEarlyUserId(String prjEarlyUserId) {
        this.prjEarlyUserId = prjEarlyUserId;
        return this;
    }

    /**
     * 项目设计岗。
     */
    public String prjDesignUserId;

    /**
     * 获取：项目设计岗。
     */
    public String getPrjDesignUserId() {
        return this.prjDesignUserId;
    }

    /**
     * 设置：项目设计岗。
     */
    public PmPrj setPrjDesignUserId(String prjDesignUserId) {
        this.prjDesignUserId = prjDesignUserId;
        return this;
    }

    /**
     * 项目成本岗。
     */
    public String prjCostUserId;

    /**
     * 获取：项目成本岗。
     */
    public String getPrjCostUserId() {
        return this.prjCostUserId;
    }

    /**
     * 设置：项目成本岗。
     */
    public PmPrj setPrjCostUserId(String prjCostUserId) {
        this.prjCostUserId = prjCostUserId;
        return this;
    }

    /**
     * 进入省库。
     */
    public Boolean inProvinceRep;

    /**
     * 获取：进入省库。
     */
    public Boolean getInProvinceRep() {
        return this.inProvinceRep;
    }

    /**
     * 设置：进入省库。
     */
    public PmPrj setInProvinceRep(Boolean inProvinceRep) {
        this.inProvinceRep = inProvinceRep;
        return this;
    }

    /**
     * 进入国库。
     */
    public Boolean inCountryRep;

    /**
     * 获取：进入国库。
     */
    public Boolean getInCountryRep() {
        return this.inCountryRep;
    }

    /**
     * 设置：进入国库。
     */
    public PmPrj setInCountryRep(Boolean inCountryRep) {
        this.inCountryRep = inCountryRep;
        return this;
    }

    /**
     * 建设单位。
     */
    public String builderUnit;

    /**
     * 获取：建设单位。
     */
    public String getBuilderUnit() {
        return this.builderUnit;
    }

    /**
     * 设置：建设单位。
     */
    public PmPrj setBuilderUnit(String builderUnit) {
        this.builderUnit = builderUnit;
        return this;
    }

    /**
     * 勘察单位。
     */
    public String surveyorUnit;

    /**
     * 获取：勘察单位。
     */
    public String getSurveyorUnit() {
        return this.surveyorUnit;
    }

    /**
     * 设置：勘察单位。
     */
    public PmPrj setSurveyorUnit(String surveyorUnit) {
        this.surveyorUnit = surveyorUnit;
        return this;
    }

    /**
     * 设计单位。
     */
    public String designerUnit;

    /**
     * 获取：设计单位。
     */
    public String getDesignerUnit() {
        return this.designerUnit;
    }

    /**
     * 设置：设计单位。
     */
    public PmPrj setDesignerUnit(String designerUnit) {
        this.designerUnit = designerUnit;
        return this;
    }

    /**
     * 施工单位。
     */
    public String constructorUnit;

    /**
     * 获取：施工单位。
     */
    public String getConstructorUnit() {
        return this.constructorUnit;
    }

    /**
     * 设置：施工单位。
     */
    public PmPrj setConstructorUnit(String constructorUnit) {
        this.constructorUnit = constructorUnit;
        return this;
    }

    /**
     * 监理单位。
     */
    public String supervisorUnit;

    /**
     * 获取：监理单位。
     */
    public String getSupervisorUnit() {
        return this.supervisorUnit;
    }

    /**
     * 设置：监理单位。
     */
    public PmPrj setSupervisorUnit(String supervisorUnit) {
        this.supervisorUnit = supervisorUnit;
        return this;
    }

    /**
     * 检测单位。
     */
    public String checkerUnit;

    /**
     * 获取：检测单位。
     */
    public String getCheckerUnit() {
        return this.checkerUnit;
    }

    /**
     * 设置：检测单位。
     */
    public PmPrj setCheckerUnit(String checkerUnit) {
        this.checkerUnit = checkerUnit;
        return this;
    }

    /**
     * 全过程造价单位。
     */
    public String consulterUnit;

    /**
     * 获取：全过程造价单位。
     */
    public String getConsulterUnit() {
        return this.consulterUnit;
    }

    /**
     * 设置：全过程造价单位。
     */
    public PmPrj setConsulterUnit(String consulterUnit) {
        this.consulterUnit = consulterUnit;
        return this;
    }

    /**
     * 项目图片。
     */
    public String prjImg;

    /**
     * 获取：项目图片。
     */
    public String getPrjImg() {
        return this.prjImg;
    }

    /**
     * 设置：项目图片。
     */
    public PmPrj setPrjImg(String prjImg) {
        this.prjImg = prjImg;
        return this;
    }

    /**
     * CPMS的UUID。
     */
    public String cpmsUuid;

    /**
     * 获取：CPMS的UUID。
     */
    public String getCpmsUuid() {
        return this.cpmsUuid;
    }

    /**
     * 设置：CPMS的UUID。
     */
    public PmPrj setCpmsUuid(String cpmsUuid) {
        this.cpmsUuid = cpmsUuid;
        return this;
    }

    /**
     * CPMS的ID。
     */
    public String cpmsId;

    /**
     * 获取：CPMS的ID。
     */
    public String getCpmsId() {
        return this.cpmsId;
    }

    /**
     * 设置：CPMS的ID。
     */
    public PmPrj setCpmsId(String cpmsId) {
        this.cpmsId = cpmsId;
        return this;
    }

    /**
     * CPMS建设地点。
     */
    public String cpmsConstructionSite;

    /**
     * 获取：CPMS建设地点。
     */
    public String getCpmsConstructionSite() {
        return this.cpmsConstructionSite;
    }

    /**
     * 设置：CPMS建设地点。
     */
    public PmPrj setCpmsConstructionSite(String cpmsConstructionSite) {
        this.cpmsConstructionSite = cpmsConstructionSite;
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
    public static PmPrj newData() {
        return modelHelper.newData();
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmPrj insertData() {
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
    public static PmPrj selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PmPrj> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PmPrj> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
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
    public static void copyCols(PmPrj fromModel, PmPrj toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}