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
 * 施工许可证申请。
 */
public class PmConstructPermitReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmConstructPermitReq> modelHelper = new ModelHelper<>("PM_CONSTRUCT_PERMIT_REQ", new PmConstructPermitReq());

    // 实体常量：
    // <editor-fold>

    public static final String ENT_CODE = "PM_CONSTRUCT_PERMIT_REQ";
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
         * 建设年限。
         */
        public static final String BUILD_YEARS = "BUILD_YEARS";
        /**
         * 项目概况。
         */
        public static final String PRJ_SITUATION = "PRJ_SITUATION";
        /**
         * 土石方申请日期。
         */
        public static final String EARTHWORK_APPLY_DATE = "EARTHWORK_APPLY_DATE";
        /**
         * 计划完成日期申请。
         */
        public static final String COMPL_PLAN_DATE_APPLY = "COMPL_PLAN_DATE_APPLY";
        /**
         * 土石方申请人。
         */
        public static final String EARTHWORK_APPLY_USER = "EARTHWORK_APPLY_USER";
        /**
         * 立项申请材料。
         */
        public static final String PRJ_REQ_FILE = "PRJ_REQ_FILE";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 主体申请日期。
         */
        public static final String SUBJECT_APPLY_DATE = "SUBJECT_APPLY_DATE";
        /**
         * 主体许可计划完成日期。
         */
        public static final String SUBJECT_PLAN_COMPL_DATE = "SUBJECT_PLAN_COMPL_DATE";
        /**
         * 主体申请人。
         */
        public static final String SUBJECT_APPLY_USER = "SUBJECT_APPLY_USER";
        /**
         * 备案材料。
         */
        public static final String KEEP_RECORD_FILE = "KEEP_RECORD_FILE";
        /**
         * 操作备注。
         */
        public static final String ACT_REMARK = "ACT_REMARK";
        /**
         * 土石方核实日期。
         */
        public static final String EARTHWORK_VERIFY_DATE = "EARTHWORK_VERIFY_DATE";
        /**
         * 有效期。
         */
        public static final String VALID_TERM = "VALID_TERM";
        /**
         * 土石方材料。
         */
        public static final String EARTHWORK_FILE = "EARTHWORK_FILE";
        /**
         * 主体核实日期。
         */
        public static final String SUBJECT_VERIFY_DATE = "SUBJECT_VERIFY_DATE";
        /**
         * 主体有效期。
         */
        public static final String SUBJECT_TERM = "SUBJECT_TERM";
        /**
         * 主体材料。
         */
        public static final String SUBJECT_FILE = "SUBJECT_FILE";
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
    public PmConstructPermitReq setId(String id) {
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
    public PmConstructPermitReq setVer(Integer ver) {
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
    public PmConstructPermitReq setTs(LocalDateTime ts) {
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
    public PmConstructPermitReq setIsPreset(Boolean isPreset) {
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
    public PmConstructPermitReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmConstructPermitReq setLastModiUserId(String lastModiUserId) {
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
    public PmConstructPermitReq setCode(String code) {
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
    public PmConstructPermitReq setName(String name) {
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
    public PmConstructPermitReq setPmPrjId(String pmPrjId) {
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
    public PmConstructPermitReq setLkWfInstId(String lkWfInstId) {
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
    public PmConstructPermitReq setPrjCode(String prjCode) {
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
    public PmConstructPermitReq setStatus(String status) {
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
    public PmConstructPermitReq setCrtUserId(String crtUserId) {
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
    public PmConstructPermitReq setCustomerUnit(String customerUnit) {
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
    public PmConstructPermitReq setPrjManageModeId(String prjManageModeId) {
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
    public PmConstructPermitReq setCrtDeptId(String crtDeptId) {
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
    public PmConstructPermitReq setBaseLocationId(String baseLocationId) {
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
    public PmConstructPermitReq setCrtDt(LocalDateTime crtDt) {
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
    public PmConstructPermitReq setFloorArea(Double floorArea) {
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
    public PmConstructPermitReq setProjectTypeId(String projectTypeId) {
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
    public PmConstructPermitReq setConScaleTypeId(String conScaleTypeId) {
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
    public PmConstructPermitReq setConScaleQty(Double conScaleQty) {
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
    public PmConstructPermitReq setConScaleQty2(Double conScaleQty2) {
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
    public PmConstructPermitReq setConScaleUomId(String conScaleUomId) {
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
    public PmConstructPermitReq setBuildYears(Double buildYears) {
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
    public PmConstructPermitReq setPrjSituation(String prjSituation) {
        this.prjSituation = prjSituation;
        return this;
    }

    /**
     * 土石方申请日期。
     */
    public LocalDate earthworkApplyDate;

    /**
     * 获取：土石方申请日期。
     */
    public LocalDate getEarthworkApplyDate() {
        return this.earthworkApplyDate;
    }

    /**
     * 设置：土石方申请日期。
     */
    public PmConstructPermitReq setEarthworkApplyDate(LocalDate earthworkApplyDate) {
        this.earthworkApplyDate = earthworkApplyDate;
        return this;
    }

    /**
     * 计划完成日期申请。
     */
    public LocalDate complPlanDateApply;

    /**
     * 获取：计划完成日期申请。
     */
    public LocalDate getComplPlanDateApply() {
        return this.complPlanDateApply;
    }

    /**
     * 设置：计划完成日期申请。
     */
    public PmConstructPermitReq setComplPlanDateApply(LocalDate complPlanDateApply) {
        this.complPlanDateApply = complPlanDateApply;
        return this;
    }

    /**
     * 土石方申请人。
     */
    public String earthworkApplyUser;

    /**
     * 获取：土石方申请人。
     */
    public String getEarthworkApplyUser() {
        return this.earthworkApplyUser;
    }

    /**
     * 设置：土石方申请人。
     */
    public PmConstructPermitReq setEarthworkApplyUser(String earthworkApplyUser) {
        this.earthworkApplyUser = earthworkApplyUser;
        return this;
    }

    /**
     * 立项申请材料。
     */
    public String prjReqFile;

    /**
     * 获取：立项申请材料。
     */
    public String getPrjReqFile() {
        return this.prjReqFile;
    }

    /**
     * 设置：立项申请材料。
     */
    public PmConstructPermitReq setPrjReqFile(String prjReqFile) {
        this.prjReqFile = prjReqFile;
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
    public PmConstructPermitReq setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    /**
     * 主体申请日期。
     */
    public LocalDate subjectApplyDate;

    /**
     * 获取：主体申请日期。
     */
    public LocalDate getSubjectApplyDate() {
        return this.subjectApplyDate;
    }

    /**
     * 设置：主体申请日期。
     */
    public PmConstructPermitReq setSubjectApplyDate(LocalDate subjectApplyDate) {
        this.subjectApplyDate = subjectApplyDate;
        return this;
    }

    /**
     * 主体许可计划完成日期。
     */
    public LocalDate subjectPlanComplDate;

    /**
     * 获取：主体许可计划完成日期。
     */
    public LocalDate getSubjectPlanComplDate() {
        return this.subjectPlanComplDate;
    }

    /**
     * 设置：主体许可计划完成日期。
     */
    public PmConstructPermitReq setSubjectPlanComplDate(LocalDate subjectPlanComplDate) {
        this.subjectPlanComplDate = subjectPlanComplDate;
        return this;
    }

    /**
     * 主体申请人。
     */
    public String subjectApplyUser;

    /**
     * 获取：主体申请人。
     */
    public String getSubjectApplyUser() {
        return this.subjectApplyUser;
    }

    /**
     * 设置：主体申请人。
     */
    public PmConstructPermitReq setSubjectApplyUser(String subjectApplyUser) {
        this.subjectApplyUser = subjectApplyUser;
        return this;
    }

    /**
     * 备案材料。
     */
    public String keepRecordFile;

    /**
     * 获取：备案材料。
     */
    public String getKeepRecordFile() {
        return this.keepRecordFile;
    }

    /**
     * 设置：备案材料。
     */
    public PmConstructPermitReq setKeepRecordFile(String keepRecordFile) {
        this.keepRecordFile = keepRecordFile;
        return this;
    }

    /**
     * 操作备注。
     */
    public String actRemark;

    /**
     * 获取：操作备注。
     */
    public String getActRemark() {
        return this.actRemark;
    }

    /**
     * 设置：操作备注。
     */
    public PmConstructPermitReq setActRemark(String actRemark) {
        this.actRemark = actRemark;
        return this;
    }

    /**
     * 土石方核实日期。
     */
    public LocalDate earthworkVerifyDate;

    /**
     * 获取：土石方核实日期。
     */
    public LocalDate getEarthworkVerifyDate() {
        return this.earthworkVerifyDate;
    }

    /**
     * 设置：土石方核实日期。
     */
    public PmConstructPermitReq setEarthworkVerifyDate(LocalDate earthworkVerifyDate) {
        this.earthworkVerifyDate = earthworkVerifyDate;
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
    public PmConstructPermitReq setValidTerm(String validTerm) {
        this.validTerm = validTerm;
        return this;
    }

    /**
     * 土石方材料。
     */
    public String earthworkFile;

    /**
     * 获取：土石方材料。
     */
    public String getEarthworkFile() {
        return this.earthworkFile;
    }

    /**
     * 设置：土石方材料。
     */
    public PmConstructPermitReq setEarthworkFile(String earthworkFile) {
        this.earthworkFile = earthworkFile;
        return this;
    }

    /**
     * 主体核实日期。
     */
    public LocalDate subjectVerifyDate;

    /**
     * 获取：主体核实日期。
     */
    public LocalDate getSubjectVerifyDate() {
        return this.subjectVerifyDate;
    }

    /**
     * 设置：主体核实日期。
     */
    public PmConstructPermitReq setSubjectVerifyDate(LocalDate subjectVerifyDate) {
        this.subjectVerifyDate = subjectVerifyDate;
        return this;
    }

    /**
     * 主体有效期。
     */
    public String subjectTerm;

    /**
     * 获取：主体有效期。
     */
    public String getSubjectTerm() {
        return this.subjectTerm;
    }

    /**
     * 设置：主体有效期。
     */
    public PmConstructPermitReq setSubjectTerm(String subjectTerm) {
        this.subjectTerm = subjectTerm;
        return this;
    }

    /**
     * 主体材料。
     */
    public String subjectFile;

    /**
     * 获取：主体材料。
     */
    public String getSubjectFile() {
        return this.subjectFile;
    }

    /**
     * 设置：主体材料。
     */
    public PmConstructPermitReq setSubjectFile(String subjectFile) {
        this.subjectFile = subjectFile;
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
    public static PmConstructPermitReq insertData() {
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
    public static PmConstructPermitReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PmConstructPermitReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PmConstructPermitReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
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
    public static void copyCols(PmConstructPermitReq fromModel, PmConstructPermitReq toModel, List<String> includeCols, List<String> excludeCols) {
        ModelHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}