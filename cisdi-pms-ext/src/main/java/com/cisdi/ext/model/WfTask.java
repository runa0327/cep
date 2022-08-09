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
 * 任务。
 */
public class WfTask {

    /**
     * 模型助手。
     */
    private static final ModelHelper<WfTask> modelHelper = new ModelHelper<>("WF_TASK", new WfTask());

    // 实体常量：
    // <editor-fold>

    public static final String ENT_CODE = "WF_TASK";
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
         * 流程。
         */
        public static final String WF_PROCESS_ID = "WF_PROCESS_ID";
        /**
         * 流程实例。
         */
        public static final String WF_PROCESS_INSTANCE_ID = "WF_PROCESS_INSTANCE_ID";
        /**
         * 节点。
         */
        public static final String WF_NODE_ID = "WF_NODE_ID";
        /**
         * 节点实例。
         */
        public static final String WF_NODE_INSTANCE_ID = "WF_NODE_INSTANCE_ID";
        /**
         * 用户。
         */
        public static final String AD_USER_ID = "AD_USER_ID";
        /**
         * 接收日期时间。
         */
        public static final String RECEIVE_DATETIME = "RECEIVE_DATETIME";
        /**
         * 查看日期时间。
         */
        public static final String VIEW_DATETIME = "VIEW_DATETIME";
        /**
         * 操作日期时间。
         */
        public static final String ACT_DATETIME = "ACT_DATETIME";
        /**
         * 用户意见。
         */
        public static final String USER_COMMENT = "USER_COMMENT";
        /**
         * 操作。
         */
        public static final String AD_ACT_ID = "AD_ACT_ID";
        /**
         * 是否关闭。
         */
        public static final String IS_CLOSED = "IS_CLOSED";
        /**
         * 任务类型。
         */
        public static final String WF_TASK_TYPE_ID = "WF_TASK_TYPE_ID";
        /**
         * 处于本轮。
         */
        public static final String IN_CURRENT_ROUND = "IN_CURRENT_ROUND";
        /**
         * 序号。
         */
        public static final String SEQ_NO = "SEQ_NO";
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
    public WfTask setId(String id) {
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
    public WfTask setVer(Integer ver) {
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
    public WfTask setTs(LocalDateTime ts) {
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
    public WfTask setIsPreset(Boolean isPreset) {
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
    public WfTask setCrtDt(LocalDateTime crtDt) {
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
    public WfTask setCrtUserId(String crtUserId) {
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
    public WfTask setLastModiDt(LocalDateTime lastModiDt) {
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
    public WfTask setLastModiUserId(String lastModiUserId) {
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
    public WfTask setStatus(String status) {
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
    public WfTask setLkWfInstId(String lkWfInstId) {
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
    public WfTask setCode(String code) {
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
    public WfTask setName(String name) {
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
    public WfTask setRemark(String remark) {
        this.remark = remark;
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
    public WfTask setWfProcessId(String wfProcessId) {
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
    public WfTask setWfProcessInstanceId(String wfProcessInstanceId) {
        this.wfProcessInstanceId = wfProcessInstanceId;
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
    public WfTask setWfNodeId(String wfNodeId) {
        this.wfNodeId = wfNodeId;
        return this;
    }

    /**
     * 节点实例。
     */
    public String wfNodeInstanceId;

    /**
     * 获取：节点实例。
     */
    public String getWfNodeInstanceId() {
        return this.wfNodeInstanceId;
    }

    /**
     * 设置：节点实例。
     */
    public WfTask setWfNodeInstanceId(String wfNodeInstanceId) {
        this.wfNodeInstanceId = wfNodeInstanceId;
        return this;
    }

    /**
     * 用户。
     */
    public String adUserId;

    /**
     * 获取：用户。
     */
    public String getAdUserId() {
        return this.adUserId;
    }

    /**
     * 设置：用户。
     */
    public WfTask setAdUserId(String adUserId) {
        this.adUserId = adUserId;
        return this;
    }

    /**
     * 接收日期时间。
     */
    public LocalDateTime receiveDatetime;

    /**
     * 获取：接收日期时间。
     */
    public LocalDateTime getReceiveDatetime() {
        return this.receiveDatetime;
    }

    /**
     * 设置：接收日期时间。
     */
    public WfTask setReceiveDatetime(LocalDateTime receiveDatetime) {
        this.receiveDatetime = receiveDatetime;
        return this;
    }

    /**
     * 查看日期时间。
     */
    public LocalDateTime viewDatetime;

    /**
     * 获取：查看日期时间。
     */
    public LocalDateTime getViewDatetime() {
        return this.viewDatetime;
    }

    /**
     * 设置：查看日期时间。
     */
    public WfTask setViewDatetime(LocalDateTime viewDatetime) {
        this.viewDatetime = viewDatetime;
        return this;
    }

    /**
     * 操作日期时间。
     */
    public LocalDateTime actDatetime;

    /**
     * 获取：操作日期时间。
     */
    public LocalDateTime getActDatetime() {
        return this.actDatetime;
    }

    /**
     * 设置：操作日期时间。
     */
    public WfTask setActDatetime(LocalDateTime actDatetime) {
        this.actDatetime = actDatetime;
        return this;
    }

    /**
     * 用户意见。
     */
    public String userComment;

    /**
     * 获取：用户意见。
     */
    public String getUserComment() {
        return this.userComment;
    }

    /**
     * 设置：用户意见。
     */
    public WfTask setUserComment(String userComment) {
        this.userComment = userComment;
        return this;
    }

    /**
     * 操作。
     */
    public String adActId;

    /**
     * 获取：操作。
     */
    public String getAdActId() {
        return this.adActId;
    }

    /**
     * 设置：操作。
     */
    public WfTask setAdActId(String adActId) {
        this.adActId = adActId;
        return this;
    }

    /**
     * 是否关闭。
     */
    public Boolean isClosed;

    /**
     * 获取：是否关闭。
     */
    public Boolean getIsClosed() {
        return this.isClosed;
    }

    /**
     * 设置：是否关闭。
     */
    public WfTask setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
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
    public WfTask setWfTaskTypeId(String wfTaskTypeId) {
        this.wfTaskTypeId = wfTaskTypeId;
        return this;
    }

    /**
     * 处于本轮。
     */
    public Boolean inCurrentRound;

    /**
     * 获取：处于本轮。
     */
    public Boolean getInCurrentRound() {
        return this.inCurrentRound;
    }

    /**
     * 设置：处于本轮。
     */
    public WfTask setInCurrentRound(Boolean inCurrentRound) {
        this.inCurrentRound = inCurrentRound;
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
    public WfTask setSeqNo(Double seqNo) {
        this.seqNo = seqNo;
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
     * 插入数据。
     *
     * @return
     */
    public static WfTask insertData() {
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
    public static WfTask selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<WfTask> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<WfTask> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
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
    public static void copyCols(WfTask fromModel, WfTask toModel, List<String> includeCols, List<String> excludeCols) {
        ModelHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}