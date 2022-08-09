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
 * 项目进度计划节点。
 */
public class PmProPlanNode {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmProPlanNode> modelHelper = new ModelHelper<>("PM_PRO_PLAN_NODE", new PmProPlanNode());

    // 实体常量：
    // <editor-fold>

    public static final String ENT_CODE = "PM_PRO_PLAN_NODE";
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
         * 责任部门。
         */
        public static final String CHIEF_DEPT_ID = "CHIEF_DEPT_ID";
        /**
         * 责任用户。
         */
        public static final String CHIEF_USER_ID = "CHIEF_USER_ID";
        /**
         * 第几天开始。
         */
        public static final String START_DAY = "START_DAY";
        /**
         * 进度状态。
         */
        public static final String PROGRESS_STATUS_ID = "PROGRESS_STATUS_ID";
        /**
         * 预计开始日期。
         */
        public static final String PLAN_START_DATE = "PLAN_START_DATE";
        /**
         * 预计完成日期。
         */
        public static final String PLAN_COMPL_DATE = "PLAN_COMPL_DATE";
        /**
         * 预计总天数。
         */
        public static final String PLAN_TOTAL_DAYS = "PLAN_TOTAL_DAYS";
        /**
         * 预计已开展工期。
         */
        public static final String PLAN_CARRY_DAYS = "PLAN_CARRY_DAYS";
        /**
         * 预计当前进度百分比。
         */
        public static final String PLAN_CURRENT_PRO_PERCENT = "PLAN_CURRENT_PRO_PERCENT";
        /**
         * 实际开始日期。
         */
        public static final String ACTUAL_START_DATE = "ACTUAL_START_DATE";
        /**
         * 实际完成日期。
         */
        public static final String ACTUAL_COMPL_DATE = "ACTUAL_COMPL_DATE";
        /**
         * 实际总天数。
         */
        public static final String ACTUAL_TOTAL_DAYS = "ACTUAL_TOTAL_DAYS";
        /**
         * 实际已开展工期。
         */
        public static final String ACTUAL_CARRY_DAYS = "ACTUAL_CARRY_DAYS";
        /**
         * 实际当前进度百分比。
         */
        public static final String ACTUAL_CURRENT_PRO_PERCENT = "ACTUAL_CURRENT_PRO_PERCENT";
        /**
         * 进度风险类型。
         */
        public static final String PROGRESS_RISK_TYPE_ID = "PROGRESS_RISK_TYPE_ID";
        /**
         * 进度风险说明。
         */
        public static final String PROGRESS_RISK_REMARK = "PROGRESS_RISK_REMARK";
        /**
         * 显示在前期手续。
         */
        public static final String SHOW_IN_EARLY_PROC = "SHOW_IN_EARLY_PROC";
        /**
         * 显示在项目概览。
         */
        public static final String SHOW_IN_PRJ_OVERVIEW = "SHOW_IN_PRJ_OVERVIEW";
        /**
         * 项目进度计划父节点。
         */
        public static final String PM_PRO_PLAN_NODE_PID = "PM_PRO_PLAN_NODE_PID";
        /**
         * 序号。
         */
        public static final String SEQ_NO = "SEQ_NO";
        /**
         * 项目进度计划。
         */
        public static final String PM_PRO_PLAN_ID = "PM_PRO_PLAN_ID";
        /**
         * CPMS的UUID。
         */
        public static final String CPMS_UUID = "CPMS_UUID";
        /**
         * CPMS的ID。
         */
        public static final String CPMS_ID = "CPMS_ID";
        /**
         * 层级。
         */
        public static final String LEVEL = "LEVEL";
        /**
         * 关联的流程。
         */
        public static final String LINKED_WF_PROCESS_ID = "LINKED_WF_PROCESS_ID";
        /**
         * 关联的流程节点。
         */
        public static final String LINKED_WF_NODE_ID = "LINKED_WF_NODE_ID";
        /**
         * 关联的流程实例。
         */
        public static final String LINKED_WF_PROCESS_INSTANCE_ID = "LINKED_WF_PROCESS_INSTANCE_ID";
        /**
         * 关联的流程节点实例。
         */
        public static final String LINKED_WF_NODE_INSTANCE_ID = "LINKED_WF_NODE_INSTANCE_ID";
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
    public PmProPlanNode setId(String id) {
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
    public PmProPlanNode setVer(Integer ver) {
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
    public PmProPlanNode setTs(LocalDateTime ts) {
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
    public PmProPlanNode setIsPreset(Boolean isPreset) {
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
    public PmProPlanNode setCrtDt(LocalDateTime crtDt) {
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
    public PmProPlanNode setCrtUserId(String crtUserId) {
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
    public PmProPlanNode setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmProPlanNode setLastModiUserId(String lastModiUserId) {
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
    public PmProPlanNode setStatus(String status) {
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
    public PmProPlanNode setLkWfInstId(String lkWfInstId) {
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
    public PmProPlanNode setCode(String code) {
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
    public PmProPlanNode setName(String name) {
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
    public PmProPlanNode setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    /**
     * 责任部门。
     */
    public String chiefDeptId;

    /**
     * 获取：责任部门。
     */
    public String getChiefDeptId() {
        return this.chiefDeptId;
    }

    /**
     * 设置：责任部门。
     */
    public PmProPlanNode setChiefDeptId(String chiefDeptId) {
        this.chiefDeptId = chiefDeptId;
        return this;
    }

    /**
     * 责任用户。
     */
    public String chiefUserId;

    /**
     * 获取：责任用户。
     */
    public String getChiefUserId() {
        return this.chiefUserId;
    }

    /**
     * 设置：责任用户。
     */
    public PmProPlanNode setChiefUserId(String chiefUserId) {
        this.chiefUserId = chiefUserId;
        return this;
    }

    /**
     * 第几天开始。
     */
    public Integer startDay;

    /**
     * 获取：第几天开始。
     */
    public Integer getStartDay() {
        return this.startDay;
    }

    /**
     * 设置：第几天开始。
     */
    public PmProPlanNode setStartDay(Integer startDay) {
        this.startDay = startDay;
        return this;
    }

    /**
     * 进度状态。
     */
    public String progressStatusId;

    /**
     * 获取：进度状态。
     */
    public String getProgressStatusId() {
        return this.progressStatusId;
    }

    /**
     * 设置：进度状态。
     */
    public PmProPlanNode setProgressStatusId(String progressStatusId) {
        this.progressStatusId = progressStatusId;
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
    public PmProPlanNode setPlanStartDate(LocalDate planStartDate) {
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
    public PmProPlanNode setPlanComplDate(LocalDate planComplDate) {
        this.planComplDate = planComplDate;
        return this;
    }

    /**
     * 预计总天数。
     */
    public Integer planTotalDays;

    /**
     * 获取：预计总天数。
     */
    public Integer getPlanTotalDays() {
        return this.planTotalDays;
    }

    /**
     * 设置：预计总天数。
     */
    public PmProPlanNode setPlanTotalDays(Integer planTotalDays) {
        this.planTotalDays = planTotalDays;
        return this;
    }

    /**
     * 预计已开展工期。
     */
    public Integer planCarryDays;

    /**
     * 获取：预计已开展工期。
     */
    public Integer getPlanCarryDays() {
        return this.planCarryDays;
    }

    /**
     * 设置：预计已开展工期。
     */
    public PmProPlanNode setPlanCarryDays(Integer planCarryDays) {
        this.planCarryDays = planCarryDays;
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
    public PmProPlanNode setPlanCurrentProPercent(Double planCurrentProPercent) {
        this.planCurrentProPercent = planCurrentProPercent;
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
    public PmProPlanNode setActualStartDate(LocalDate actualStartDate) {
        this.actualStartDate = actualStartDate;
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
    public PmProPlanNode setActualComplDate(LocalDate actualComplDate) {
        this.actualComplDate = actualComplDate;
        return this;
    }

    /**
     * 实际总天数。
     */
    public Integer actualTotalDays;

    /**
     * 获取：实际总天数。
     */
    public Integer getActualTotalDays() {
        return this.actualTotalDays;
    }

    /**
     * 设置：实际总天数。
     */
    public PmProPlanNode setActualTotalDays(Integer actualTotalDays) {
        this.actualTotalDays = actualTotalDays;
        return this;
    }

    /**
     * 实际已开展工期。
     */
    public Integer actualCarryDays;

    /**
     * 获取：实际已开展工期。
     */
    public Integer getActualCarryDays() {
        return this.actualCarryDays;
    }

    /**
     * 设置：实际已开展工期。
     */
    public PmProPlanNode setActualCarryDays(Integer actualCarryDays) {
        this.actualCarryDays = actualCarryDays;
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
    public PmProPlanNode setActualCurrentProPercent(Double actualCurrentProPercent) {
        this.actualCurrentProPercent = actualCurrentProPercent;
        return this;
    }

    /**
     * 进度风险类型。
     */
    public String progressRiskTypeId;

    /**
     * 获取：进度风险类型。
     */
    public String getProgressRiskTypeId() {
        return this.progressRiskTypeId;
    }

    /**
     * 设置：进度风险类型。
     */
    public PmProPlanNode setProgressRiskTypeId(String progressRiskTypeId) {
        this.progressRiskTypeId = progressRiskTypeId;
        return this;
    }

    /**
     * 进度风险说明。
     */
    public String progressRiskRemark;

    /**
     * 获取：进度风险说明。
     */
    public String getProgressRiskRemark() {
        return this.progressRiskRemark;
    }

    /**
     * 设置：进度风险说明。
     */
    public PmProPlanNode setProgressRiskRemark(String progressRiskRemark) {
        this.progressRiskRemark = progressRiskRemark;
        return this;
    }

    /**
     * 显示在前期手续。
     */
    public Boolean showInEarlyProc;

    /**
     * 获取：显示在前期手续。
     */
    public Boolean getShowInEarlyProc() {
        return this.showInEarlyProc;
    }

    /**
     * 设置：显示在前期手续。
     */
    public PmProPlanNode setShowInEarlyProc(Boolean showInEarlyProc) {
        this.showInEarlyProc = showInEarlyProc;
        return this;
    }

    /**
     * 显示在项目概览。
     */
    public Boolean showInPrjOverview;

    /**
     * 获取：显示在项目概览。
     */
    public Boolean getShowInPrjOverview() {
        return this.showInPrjOverview;
    }

    /**
     * 设置：显示在项目概览。
     */
    public PmProPlanNode setShowInPrjOverview(Boolean showInPrjOverview) {
        this.showInPrjOverview = showInPrjOverview;
        return this;
    }

    /**
     * 项目进度计划父节点。
     */
    public String pmProPlanNodePid;

    /**
     * 获取：项目进度计划父节点。
     */
    public String getPmProPlanNodePid() {
        return this.pmProPlanNodePid;
    }

    /**
     * 设置：项目进度计划父节点。
     */
    public PmProPlanNode setPmProPlanNodePid(String pmProPlanNodePid) {
        this.pmProPlanNodePid = pmProPlanNodePid;
        return this;
    }

    /**
     * 序号。
     */
    public Double seqNo;

    /**
     * 获取：序号。
     */
    public Double getSeqNo() {
        return this.seqNo;
    }

    /**
     * 设置：序号。
     */
    public PmProPlanNode setSeqNo(Double seqNo) {
        this.seqNo = seqNo;
        return this;
    }

    /**
     * 项目进度计划。
     */
    public String pmProPlanId;

    /**
     * 获取：项目进度计划。
     */
    public String getPmProPlanId() {
        return this.pmProPlanId;
    }

    /**
     * 设置：项目进度计划。
     */
    public PmProPlanNode setPmProPlanId(String pmProPlanId) {
        this.pmProPlanId = pmProPlanId;
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
    public PmProPlanNode setCpmsUuid(String cpmsUuid) {
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
    public PmProPlanNode setCpmsId(String cpmsId) {
        this.cpmsId = cpmsId;
        return this;
    }

    /**
     * 层级。
     */
    public Integer level;

    /**
     * 获取：层级。
     */
    public Integer getLevel() {
        return this.level;
    }

    /**
     * 设置：层级。
     */
    public PmProPlanNode setLevel(Integer level) {
        this.level = level;
        return this;
    }

    /**
     * 关联的流程。
     */
    public String linkedWfProcessId;

    /**
     * 获取：关联的流程。
     */
    public String getLinkedWfProcessId() {
        return this.linkedWfProcessId;
    }

    /**
     * 设置：关联的流程。
     */
    public PmProPlanNode setLinkedWfProcessId(String linkedWfProcessId) {
        this.linkedWfProcessId = linkedWfProcessId;
        return this;
    }

    /**
     * 关联的流程节点。
     */
    public String linkedWfNodeId;

    /**
     * 获取：关联的流程节点。
     */
    public String getLinkedWfNodeId() {
        return this.linkedWfNodeId;
    }

    /**
     * 设置：关联的流程节点。
     */
    public PmProPlanNode setLinkedWfNodeId(String linkedWfNodeId) {
        this.linkedWfNodeId = linkedWfNodeId;
        return this;
    }

    /**
     * 关联的流程实例。
     */
    public String linkedWfProcessInstanceId;

    /**
     * 获取：关联的流程实例。
     */
    public String getLinkedWfProcessInstanceId() {
        return this.linkedWfProcessInstanceId;
    }

    /**
     * 设置：关联的流程实例。
     */
    public PmProPlanNode setLinkedWfProcessInstanceId(String linkedWfProcessInstanceId) {
        this.linkedWfProcessInstanceId = linkedWfProcessInstanceId;
        return this;
    }

    /**
     * 关联的流程节点实例。
     */
    public String linkedWfNodeInstanceId;

    /**
     * 获取：关联的流程节点实例。
     */
    public String getLinkedWfNodeInstanceId() {
        return this.linkedWfNodeInstanceId;
    }

    /**
     * 设置：关联的流程节点实例。
     */
    public PmProPlanNode setLinkedWfNodeInstanceId(String linkedWfNodeInstanceId) {
        this.linkedWfNodeInstanceId = linkedWfNodeInstanceId;
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
    public static PmProPlanNode insertData() {
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
    public static PmProPlanNode selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PmProPlanNode> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PmProPlanNode> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
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
    public static void copyCols(PmProPlanNode fromModel, PmProPlanNode toModel, List<String> includeCols, List<String> excludeCols) {
        ModelHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}