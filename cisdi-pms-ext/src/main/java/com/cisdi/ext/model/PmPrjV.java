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
 * 项目（视图）。
 */
public class PmPrjV {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmPrjV> modelHelper = new ModelHelper<>("PM_PRJ_V", new PmPrjV());

    // 实体常量：
    // <editor-fold>

    public static final String ENT_CODE = "PM_PRJ_V";
    public static final EntityTypeE ENTITY_TYPE = EntityTypeE.VIEW;

    // </editor-fold>

    // 属性常量相关：
    // <editor-fold>

    public static class Cols {
        /**
         * ID。
         */
        public static final String ID = "ID";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 代码。
         */
        public static final String CODE = "CODE";
        /**
         * 名称。
         */
        public static final String NAME = "NAME";
        /**
         * 业主单位。
         */
        public static final String CUSTOMER_UNIT = "CUSTOMER_UNIT";
        /**
         * 项目类型。
         */
        public static final String PROJECT_TYPE_ID = "PROJECT_TYPE_ID";
        /**
         * 建设地点。
         */
        public static final String BASE_LOCATION_ID = "BASE_LOCATION_ID";
        /**
         * 项目管理模式。
         */
        public static final String PRJ_MANAGE_MODE_ID = "PRJ_MANAGE_MODE_ID";
        /**
         * 项目状态。
         */
        public static final String PROJECT_PHASE_ID = "PROJECT_PHASE_ID";
        /**
         * 进度阶段。
         */
        public static final String TRANSITION_PHASE_ID = "TRANSITION_PHASE_ID";
        /**
         * 总投资（万）。
         */
        public static final String PRJ_TOTAL_INVEST = "PRJ_TOTAL_INVEST";
        /**
         * 预计开始日期。
         */
        public static final String PLAN_START_DATE = "PLAN_START_DATE";
        /**
         * 实际开始日期。
         */
        public static final String ACTUAL_START_DATE = "ACTUAL_START_DATE";
        /**
         * 预计完成日期。
         */
        public static final String PLAN_COMPL_DATE = "PLAN_COMPL_DATE";
        /**
         * 实际完成日期。
         */
        public static final String ACTUAL_COMPL_DATE = "ACTUAL_COMPL_DATE";
        /**
         * 预计当前进度百分比。
         */
        public static final String PLAN_CURRENT_PRO_PERCENT = "PLAN_CURRENT_PRO_PERCENT";
        /**
         * 实际当前进度百分比。
         */
        public static final String ACTUAL_CURRENT_PRO_PERCENT = "ACTUAL_CURRENT_PRO_PERCENT";
        /**
         * CPMS的UUID。
         */
        public static final String CPMS_UUID = "CPMS_UUID";
        /**
         * CPMS的ID。
         */
        public static final String CPMS_ID = "CPMS_ID";
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
    public PmPrjV setId(String id) {
        this.id = id;
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
    public PmPrjV setCrtDt(LocalDateTime crtDt) {
        this.crtDt = crtDt;
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
    public PmPrjV setCode(String code) {
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
    public PmPrjV setName(String name) {
        this.name = name;
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
    public PmPrjV setCustomerUnit(String customerUnit) {
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
    public PmPrjV setProjectTypeId(String projectTypeId) {
        this.projectTypeId = projectTypeId;
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
    public PmPrjV setBaseLocationId(String baseLocationId) {
        this.baseLocationId = baseLocationId;
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
    public PmPrjV setPrjManageModeId(String prjManageModeId) {
        this.prjManageModeId = prjManageModeId;
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
    public PmPrjV setProjectPhaseId(String projectPhaseId) {
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
    public PmPrjV setTransitionPhaseId(String transitionPhaseId) {
        this.transitionPhaseId = transitionPhaseId;
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
    public PmPrjV setPrjTotalInvest(Double prjTotalInvest) {
        this.prjTotalInvest = prjTotalInvest;
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
    public PmPrjV setPlanStartDate(LocalDate planStartDate) {
        this.planStartDate = planStartDate;
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
    public PmPrjV setActualStartDate(LocalDate actualStartDate) {
        this.actualStartDate = actualStartDate;
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
    public PmPrjV setPlanComplDate(LocalDate planComplDate) {
        this.planComplDate = planComplDate;
        return this;
    }

    /**
     * 实际完成日期。
     */
    public LocalDate actualComplDate;

    /**
     * 获取：实际完成日期。
     */
    public LocalDate getActualComplDate() {
        return this.actualComplDate;
    }

    /**
     * 设置：实际完成日期。
     */
    public PmPrjV setActualComplDate(LocalDate actualComplDate) {
        this.actualComplDate = actualComplDate;
        return this;
    }

    /**
     * 预计当前进度百分比。
     */
    public Double planCurrentProPercent;

    /**
     * 获取：预计当前进度百分比。
     */
    public Double getPlanCurrentProPercent() {
        return this.planCurrentProPercent;
    }

    /**
     * 设置：预计当前进度百分比。
     */
    public PmPrjV setPlanCurrentProPercent(Double planCurrentProPercent) {
        this.planCurrentProPercent = planCurrentProPercent;
        return this;
    }

    /**
     * 实际当前进度百分比。
     */
    public Double actualCurrentProPercent;

    /**
     * 获取：实际当前进度百分比。
     */
    public Double getActualCurrentProPercent() {
        return this.actualCurrentProPercent;
    }

    /**
     * 设置：实际当前进度百分比。
     */
    public PmPrjV setActualCurrentProPercent(Double actualCurrentProPercent) {
        this.actualCurrentProPercent = actualCurrentProPercent;
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
    public PmPrjV setCpmsUuid(String cpmsUuid) {
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
    public PmPrjV setCpmsId(String cpmsId) {
        this.cpmsId = cpmsId;
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
    public static PmPrjV newData() {
        return modelHelper.newData();
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmPrjV insertData() {
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
    public static PmPrjV selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PmPrjV> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PmPrjV> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
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
    public static void copyCols(PmPrjV fromModel, PmPrjV toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}