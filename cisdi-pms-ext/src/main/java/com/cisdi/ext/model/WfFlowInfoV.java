package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 流转信息。
 */
public class WfFlowInfoV {

    /**
     * 模型助手。
     */
    private static final ModelHelper<WfFlowInfoV> modelHelper = new ModelHelper<>("WF_FLOW_INFO_V", new WfFlowInfoV());

    // 实体常量：
    // <editor-fold>

    public static final String ENT_CODE = "WF_FLOW_INFO_V";
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
         * 流程。
         */
        public static final String WF_PROCESS_ID = "WF_PROCESS_ID";
        /**
         * 流程实例。
         */
        public static final String WF_PROCESS_INSTANCE_ID = "WF_PROCESS_INSTANCE_ID";
        /**
         * 流程实例开始日期时间。
         */
        public static final String PI_START_DT = "PI_START_DT";
        /**
         * 流程实例结束日期时间。
         */
        public static final String PI_END_DT = "PI_END_DT";
        /**
         * 节点实例名称。
         */
        public static final String NI_NAME = "NI_NAME";
        /**
         * 节点实例序号。
         */
        public static final String NI_SEQ_NO = "NI_SEQ_NO";
        /**
         * 任务类型。
         */
        public static final String WF_TASK_TYPE_ID = "WF_TASK_TYPE_ID";
        /**
         * 节点。
         */
        public static final String WF_NODE_ID = "WF_NODE_ID";
        /**
         * 节点视图ID。
         */
        public static final String WF_NODE_VIEW_ID = "WF_NODE_VIEW_ID";
        /**
         * 任务。
         */
        public static final String WF_TASK_ID = "WF_TASK_ID";
        /**
         * 任务是否关闭。
         */
        public static final String TASK_IS_CLOSED = "TASK_IS_CLOSED";
        /**
         * 节点实例生效操作。
         */
        public static final String NI_EFF_ACT_ID = "NI_EFF_ACT_ID";
        /**
         * 任务序号。
         */
        public static final String TASK_SEQ_NO = "TASK_SEQ_NO";
        /**
         * 任务接收日期时间。
         */
        public static final String TASK_REC_DT = "TASK_REC_DT";
        /**
         * 任务查看日期时间。
         */
        public static final String TASK_VIEW_DT = "TASK_VIEW_DT";
        /**
         * 任务操作日期时间。
         */
        public static final String TASK_ACT_DT = "TASK_ACT_DT";
        /**
         * 任务用户。
         */
        public static final String TASK_USER_ID = "TASK_USER_ID";
        /**
         * 任务操作。
         */
        public static final String TASK_ACT_ID = "TASK_ACT_ID";
        /**
         * 任务用户批注。
         */
        public static final String TASK_USER_COMMENT = "TASK_USER_COMMENT";
        /**
         * 实体。
         */
        public static final String AD_ENT_ID = "AD_ENT_ID";
        /**
         * 实体记录ID。
         */
        public static final String ENTITY_RECORD_ID = "ENTITY_RECORD_ID";
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
    public WfFlowInfoV setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 流程。
     */
    public String wfProcessId;

    /**
     * 获取：流程。
     */
    public String getWfProcessId() {
        return this.wfProcessId;
    }

    /**
     * 设置：流程。
     */
    public WfFlowInfoV setWfProcessId(String wfProcessId) {
        this.wfProcessId = wfProcessId;
        return this;
    }

    /**
     * 流程实例。
     */
    public String wfProcessInstanceId;

    /**
     * 获取：流程实例。
     */
    public String getWfProcessInstanceId() {
        return this.wfProcessInstanceId;
    }

    /**
     * 设置：流程实例。
     */
    public WfFlowInfoV setWfProcessInstanceId(String wfProcessInstanceId) {
        this.wfProcessInstanceId = wfProcessInstanceId;
        return this;
    }

    /**
     * 流程实例开始日期时间。
     */
    public LocalDateTime piStartDt;

    /**
     * 获取：流程实例开始日期时间。
     */
    public LocalDateTime getPiStartDt() {
        return this.piStartDt;
    }

    /**
     * 设置：流程实例开始日期时间。
     */
    public WfFlowInfoV setPiStartDt(LocalDateTime piStartDt) {
        this.piStartDt = piStartDt;
        return this;
    }

    /**
     * 流程实例结束日期时间。
     */
    public LocalDateTime piEndDt;

    /**
     * 获取：流程实例结束日期时间。
     */
    public LocalDateTime getPiEndDt() {
        return this.piEndDt;
    }

    /**
     * 设置：流程实例结束日期时间。
     */
    public WfFlowInfoV setPiEndDt(LocalDateTime piEndDt) {
        this.piEndDt = piEndDt;
        return this;
    }

    /**
     * 节点实例名称。
     */
    public String niName;

    /**
     * 获取：节点实例名称。
     */
    public String getNiName() {
        return this.niName;
    }

    /**
     * 设置：节点实例名称。
     */
    public WfFlowInfoV setNiName(String niName) {
        this.niName = niName;
        return this;
    }

    /**
     * 节点实例序号。
     */
    public Double niSeqNo;

    /**
     * 获取：节点实例序号。
     */
    public Double getNiSeqNo() {
        return this.niSeqNo;
    }

    /**
     * 设置：节点实例序号。
     */
    public WfFlowInfoV setNiSeqNo(Double niSeqNo) {
        this.niSeqNo = niSeqNo;
        return this;
    }

    /**
     * 任务类型。
     */
    public String wfTaskTypeId;

    /**
     * 获取：任务类型。
     */
    public String getWfTaskTypeId() {
        return this.wfTaskTypeId;
    }

    /**
     * 设置：任务类型。
     */
    public WfFlowInfoV setWfTaskTypeId(String wfTaskTypeId) {
        this.wfTaskTypeId = wfTaskTypeId;
        return this;
    }

    /**
     * 节点。
     */
    public String wfNodeId;

    /**
     * 获取：节点。
     */
    public String getWfNodeId() {
        return this.wfNodeId;
    }

    /**
     * 设置：节点。
     */
    public WfFlowInfoV setWfNodeId(String wfNodeId) {
        this.wfNodeId = wfNodeId;
        return this;
    }

    /**
     * 节点视图ID。
     */
    public String wfNodeViewId;

    /**
     * 获取：节点视图ID。
     */
    public String getWfNodeViewId() {
        return this.wfNodeViewId;
    }

    /**
     * 设置：节点视图ID。
     */
    public WfFlowInfoV setWfNodeViewId(String wfNodeViewId) {
        this.wfNodeViewId = wfNodeViewId;
        return this;
    }

    /**
     * 任务。
     */
    public String wfTaskId;

    /**
     * 获取：任务。
     */
    public String getWfTaskId() {
        return this.wfTaskId;
    }

    /**
     * 设置：任务。
     */
    public WfFlowInfoV setWfTaskId(String wfTaskId) {
        this.wfTaskId = wfTaskId;
        return this;
    }

    /**
     * 任务是否关闭。
     */
    public Boolean taskIsClosed;

    /**
     * 获取：任务是否关闭。
     */
    public Boolean getTaskIsClosed() {
        return this.taskIsClosed;
    }

    /**
     * 设置：任务是否关闭。
     */
    public WfFlowInfoV setTaskIsClosed(Boolean taskIsClosed) {
        this.taskIsClosed = taskIsClosed;
        return this;
    }

    /**
     * 节点实例生效操作。
     */
    public String niEffActId;

    /**
     * 获取：节点实例生效操作。
     */
    public String getNiEffActId() {
        return this.niEffActId;
    }

    /**
     * 设置：节点实例生效操作。
     */
    public WfFlowInfoV setNiEffActId(String niEffActId) {
        this.niEffActId = niEffActId;
        return this;
    }

    /**
     * 任务序号。
     */
    public Double taskSeqNo;

    /**
     * 获取：任务序号。
     */
    public Double getTaskSeqNo() {
        return this.taskSeqNo;
    }

    /**
     * 设置：任务序号。
     */
    public WfFlowInfoV setTaskSeqNo(Double taskSeqNo) {
        this.taskSeqNo = taskSeqNo;
        return this;
    }

    /**
     * 任务接收日期时间。
     */
    public LocalDateTime taskRecDt;

    /**
     * 获取：任务接收日期时间。
     */
    public LocalDateTime getTaskRecDt() {
        return this.taskRecDt;
    }

    /**
     * 设置：任务接收日期时间。
     */
    public WfFlowInfoV setTaskRecDt(LocalDateTime taskRecDt) {
        this.taskRecDt = taskRecDt;
        return this;
    }

    /**
     * 任务查看日期时间。
     */
    public LocalDateTime taskViewDt;

    /**
     * 获取：任务查看日期时间。
     */
    public LocalDateTime getTaskViewDt() {
        return this.taskViewDt;
    }

    /**
     * 设置：任务查看日期时间。
     */
    public WfFlowInfoV setTaskViewDt(LocalDateTime taskViewDt) {
        this.taskViewDt = taskViewDt;
        return this;
    }

    /**
     * 任务操作日期时间。
     */
    public LocalDateTime taskActDt;

    /**
     * 获取：任务操作日期时间。
     */
    public LocalDateTime getTaskActDt() {
        return this.taskActDt;
    }

    /**
     * 设置：任务操作日期时间。
     */
    public WfFlowInfoV setTaskActDt(LocalDateTime taskActDt) {
        this.taskActDt = taskActDt;
        return this;
    }

    /**
     * 任务用户。
     */
    public String taskUserId;

    /**
     * 获取：任务用户。
     */
    public String getTaskUserId() {
        return this.taskUserId;
    }

    /**
     * 设置：任务用户。
     */
    public WfFlowInfoV setTaskUserId(String taskUserId) {
        this.taskUserId = taskUserId;
        return this;
    }

    /**
     * 任务操作。
     */
    public String taskActId;

    /**
     * 获取：任务操作。
     */
    public String getTaskActId() {
        return this.taskActId;
    }

    /**
     * 设置：任务操作。
     */
    public WfFlowInfoV setTaskActId(String taskActId) {
        this.taskActId = taskActId;
        return this;
    }

    /**
     * 任务用户批注。
     */
    public String taskUserComment;

    /**
     * 获取：任务用户批注。
     */
    public String getTaskUserComment() {
        return this.taskUserComment;
    }

    /**
     * 设置：任务用户批注。
     */
    public WfFlowInfoV setTaskUserComment(String taskUserComment) {
        this.taskUserComment = taskUserComment;
        return this;
    }

    /**
     * 实体。
     */
    public String adEntId;

    /**
     * 获取：实体。
     */
    public String getAdEntId() {
        return this.adEntId;
    }

    /**
     * 设置：实体。
     */
    public WfFlowInfoV setAdEntId(String adEntId) {
        this.adEntId = adEntId;
        return this;
    }

    /**
     * 实体记录ID。
     */
    public String entityRecordId;

    /**
     * 获取：实体记录ID。
     */
    public String getEntityRecordId() {
        return this.entityRecordId;
    }

    /**
     * 设置：实体记录ID。
     */
    public WfFlowInfoV setEntityRecordId(String entityRecordId) {
        this.entityRecordId = entityRecordId;
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
    public static WfFlowInfoV newData() {
        return modelHelper.newData();
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static WfFlowInfoV insertData() {
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
    public static WfFlowInfoV selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<WfFlowInfoV> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<WfFlowInfoV> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
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
    public static void copyCols(WfFlowInfoV fromModel, WfFlowInfoV toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}