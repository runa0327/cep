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
 * 实体。
 */
public class AdEnt {

    /**
     * 模型助手。
     */
    private static final ModelHelper<AdEnt> modelHelper = new ModelHelper<>("AD_ENT", new AdEnt());

    // 实体常量：
    // <editor-fold>

    public static final String ENT_CODE = "AD_ENT";
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
         * 父实体。
         */
        public static final String PARENT_ENT_ID = "PARENT_ENT_ID";
        /**
         * 实体类型。
         */
        public static final String ENT_TYPE = "ENT_TYPE";
        /**
         * 实体被引用时显示的属性。
         */
        public static final String DSP_ATT_ID_WHEN_REFED = "DSP_ATT_ID_WHEN_REFED";
        /**
         * 表连接语句。
         */
        public static final String JOIN_ON_CLAUSE = "JOIN_ON_CLAUSE";
        /**
         * 排序语句。
         */
        public static final String ORDER_BY_CLAUSE = "ORDER_BY_CLAUSE";
        /**
         * 过滤语句。
         */
        public static final String WHERE_CLAUSE = "WHERE_CLAUSE";
        /**
         * 分组语句。
         */
        public static final String GROUP_BY_CLAUSE = "GROUP_BY_CLAUSE";
        /**
         * HAVING语句。
         */
        public static final String HAVING_CLAUSE = "HAVING_CLAUSE";
        /**
         * 页面大小。
         */
        public static final String PAGE_SIZE = "PAGE_SIZE";
        /**
         * 移动端页面大小。
         */
        public static final String MOB_PAGE_SIZE = "MOB_PAGE_SIZE";
        /**
         * 忽略标准新建。
         */
        public static final String NO_STD_INSERT = "NO_STD_INSERT";
        /**
         * 忽略标准更新。
         */
        public static final String NO_STD_UPDATE = "NO_STD_UPDATE";
        /**
         * 忽略标准删除。
         */
        public static final String NO_STD_DELETE = "NO_STD_DELETE";
        /**
         * 标准版本检查。
         */
        public static final String STD_VER_CHK = "STD_VER_CHK";
        /**
         * 忽略自动保存。
         */
        public static final String IGNORE_AUTO_SAVE = "IGNORE_AUTO_SAVE";
        /**
         * 缓存所有记录的ID和文本。
         */
        public static final String CACHE_ALL_REC_ID_TEXT = "CACHE_ALL_REC_ID_TEXT";
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
    public AdEnt setId(String id) {
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
    public AdEnt setVer(Integer ver) {
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
    public AdEnt setTs(LocalDateTime ts) {
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
    public AdEnt setIsPreset(Boolean isPreset) {
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
    public AdEnt setCrtDt(LocalDateTime crtDt) {
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
    public AdEnt setCrtUserId(String crtUserId) {
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
    public AdEnt setLastModiDt(LocalDateTime lastModiDt) {
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
    public AdEnt setLastModiUserId(String lastModiUserId) {
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
    public AdEnt setStatus(String status) {
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
    public AdEnt setLkWfInstId(String lkWfInstId) {
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
    public AdEnt setCode(String code) {
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
    public AdEnt setName(String name) {
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
    public AdEnt setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    /**
     * 父实体。
     */
    public String parentEntId;

    /**
     * 获取：父实体。
     */
    public String getParentEntId() {
        return this.parentEntId;
    }

    /**
     * 设置：父实体。
     */
    public AdEnt setParentEntId(String parentEntId) {
        this.parentEntId = parentEntId;
        return this;
    }

    /**
     * 实体类型。
     */
    public String entType;

    /**
     * 获取：实体类型。
     */
    public String getEntType() {
        return this.entType;
    }

    /**
     * 设置：实体类型。
     */
    public AdEnt setEntType(String entType) {
        this.entType = entType;
        return this;
    }

    /**
     * 实体被引用时显示的属性。
     */
    public String dspAttIdWhenRefed;

    /**
     * 获取：实体被引用时显示的属性。
     */
    public String getDspAttIdWhenRefed() {
        return this.dspAttIdWhenRefed;
    }

    /**
     * 设置：实体被引用时显示的属性。
     */
    public AdEnt setDspAttIdWhenRefed(String dspAttIdWhenRefed) {
        this.dspAttIdWhenRefed = dspAttIdWhenRefed;
        return this;
    }

    /**
     * 表连接语句。
     */
    public String joinOnClause;

    /**
     * 获取：表连接语句。
     */
    public String getJoinOnClause() {
        return this.joinOnClause;
    }

    /**
     * 设置：表连接语句。
     */
    public AdEnt setJoinOnClause(String joinOnClause) {
        this.joinOnClause = joinOnClause;
        return this;
    }

    /**
     * 排序语句。
     */
    public String orderByClause;

    /**
     * 获取：排序语句。
     */
    public String getOrderByClause() {
        return this.orderByClause;
    }

    /**
     * 设置：排序语句。
     */
    public AdEnt setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
        return this;
    }

    /**
     * 过滤语句。
     */
    public String whereClause;

    /**
     * 获取：过滤语句。
     */
    public String getWhereClause() {
        return this.whereClause;
    }

    /**
     * 设置：过滤语句。
     */
    public AdEnt setWhereClause(String whereClause) {
        this.whereClause = whereClause;
        return this;
    }

    /**
     * 分组语句。
     */
    public String groupByClause;

    /**
     * 获取：分组语句。
     */
    public String getGroupByClause() {
        return this.groupByClause;
    }

    /**
     * 设置：分组语句。
     */
    public AdEnt setGroupByClause(String groupByClause) {
        this.groupByClause = groupByClause;
        return this;
    }

    /**
     * HAVING语句。
     */
    public String havingClause;

    /**
     * 获取：HAVING语句。
     */
    public String getHavingClause() {
        return this.havingClause;
    }

    /**
     * 设置：HAVING语句。
     */
    public AdEnt setHavingClause(String havingClause) {
        this.havingClause = havingClause;
        return this;
    }

    /**
     * 页面大小。
     */
    public Integer pageSize;

    /**
     * 获取：页面大小。
     */
    public Integer getPageSize() {
        return this.pageSize;
    }

    /**
     * 设置：页面大小。
     */
    public AdEnt setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    /**
     * 移动端页面大小。
     */
    public Integer mobPageSize;

    /**
     * 获取：移动端页面大小。
     */
    public Integer getMobPageSize() {
        return this.mobPageSize;
    }

    /**
     * 设置：移动端页面大小。
     */
    public AdEnt setMobPageSize(Integer mobPageSize) {
        this.mobPageSize = mobPageSize;
        return this;
    }

    /**
     * 忽略标准新建。
     */
    public Boolean noStdInsert;

    /**
     * 获取：忽略标准新建。
     */
    public Boolean getNoStdInsert() {
        return this.noStdInsert;
    }

    /**
     * 设置：忽略标准新建。
     */
    public AdEnt setNoStdInsert(Boolean noStdInsert) {
        this.noStdInsert = noStdInsert;
        return this;
    }

    /**
     * 忽略标准更新。
     */
    public Boolean noStdUpdate;

    /**
     * 获取：忽略标准更新。
     */
    public Boolean getNoStdUpdate() {
        return this.noStdUpdate;
    }

    /**
     * 设置：忽略标准更新。
     */
    public AdEnt setNoStdUpdate(Boolean noStdUpdate) {
        this.noStdUpdate = noStdUpdate;
        return this;
    }

    /**
     * 忽略标准删除。
     */
    public Boolean noStdDelete;

    /**
     * 获取：忽略标准删除。
     */
    public Boolean getNoStdDelete() {
        return this.noStdDelete;
    }

    /**
     * 设置：忽略标准删除。
     */
    public AdEnt setNoStdDelete(Boolean noStdDelete) {
        this.noStdDelete = noStdDelete;
        return this;
    }

    /**
     * 标准版本检查。
     */
    public Boolean stdVerChk;

    /**
     * 获取：标准版本检查。
     */
    public Boolean getStdVerChk() {
        return this.stdVerChk;
    }

    /**
     * 设置：标准版本检查。
     */
    public AdEnt setStdVerChk(Boolean stdVerChk) {
        this.stdVerChk = stdVerChk;
        return this;
    }

    /**
     * 忽略自动保存。
     */
    public Boolean ignoreAutoSave;

    /**
     * 获取：忽略自动保存。
     */
    public Boolean getIgnoreAutoSave() {
        return this.ignoreAutoSave;
    }

    /**
     * 设置：忽略自动保存。
     */
    public AdEnt setIgnoreAutoSave(Boolean ignoreAutoSave) {
        this.ignoreAutoSave = ignoreAutoSave;
        return this;
    }

    /**
     * 缓存所有记录的ID和文本。
     */
    public Boolean cacheAllRecIdText;

    /**
     * 获取：缓存所有记录的ID和文本。
     */
    public Boolean getCacheAllRecIdText() {
        return this.cacheAllRecIdText;
    }

    /**
     * 设置：缓存所有记录的ID和文本。
     */
    public AdEnt setCacheAllRecIdText(Boolean cacheAllRecIdText) {
        this.cacheAllRecIdText = cacheAllRecIdText;
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
    public static AdEnt insertData() {
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
    public static AdEnt selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<AdEnt> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<AdEnt> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
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
    public static void copyCols(AdEnt fromModel, AdEnt toModel, List<String> includeCols, List<String> excludeCols) {
        ModelHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}