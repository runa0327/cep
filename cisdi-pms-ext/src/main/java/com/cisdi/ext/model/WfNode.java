package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 节点。
 */
public class WfNode {

    /**
     * 模型助手。
     */
    private static final ModelHelper<WfNode> modelHelper = new ModelHelper<>("WF_NODE", new WfNode());

    // 实体常量：
    // <editor-fold>

    public static final String ENT_CODE = "WF_NODE";
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
         * 节点类型。
         */
        public static final String NODE_TYPE = "NODE_TYPE";
        /**
         * 视图。
         */
        public static final String AD_VIEW_ID = "AD_VIEW_ID";
        /**
         * 图标文件组。
         */
        public static final String ICON_FILE_GROUP_ID = "ICON_FILE_GROUP_ID";
        /**
         * 图标宽度。
         */
        public static final String ICON_WIDTH = "ICON_WIDTH";
        /**
         * 图标高度。
         */
        public static final String ICON_HEIGHT = "ICON_HEIGHT";
        /**
         * 节点X坐标。
         */
        public static final String NODE_X = "NODE_X";
        /**
         * 节点Y坐标。
         */
        public static final String NODE_Y = "NODE_Y";
        /**
         * 节点宽度。
         */
        public static final String NODE_WIDTH = "NODE_WIDTH";
        /**
         * 节点高度。
         */
        public static final String NODE_HEIGHT = "NODE_HEIGHT";
        /**
         * 待办顺序。
         */
        public static final String WF_TODO_SEQ_ID = "WF_TODO_SEQ_ID";
        /**
         * 序号。
         */
        public static final String SEQ_NO = "SEQ_NO";
        /**
         * 父节点。
         */
        public static final String WF_NODE_PID = "WF_NODE_PID";
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
    public WfNode setId(String id) {
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
    public WfNode setVer(Integer ver) {
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
    public WfNode setTs(LocalDateTime ts) {
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
    public WfNode setIsPreset(Boolean isPreset) {
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
    public WfNode setCrtDt(LocalDateTime crtDt) {
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
    public WfNode setCrtUserId(String crtUserId) {
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
    public WfNode setLastModiDt(LocalDateTime lastModiDt) {
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
    public WfNode setLastModiUserId(String lastModiUserId) {
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
    public WfNode setStatus(String status) {
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
    public WfNode setLkWfInstId(String lkWfInstId) {
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
    public WfNode setCode(String code) {
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
    public WfNode setName(String name) {
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
    public WfNode setRemark(String remark) {
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
    public WfNode setWfProcessId(String wfProcessId) {
        this.wfProcessId = wfProcessId;
        return this;
    }

    /**
     * 节点类型。
     */
    public String nodeType;

    /**
     * 获取：节点类型。
     */
    public String getNodeType() {
        return this.nodeType;
    }

    /**
     * 设置：节点类型。
     */
    public WfNode setNodeType(String nodeType) {
        this.nodeType = nodeType;
        return this;
    }

    /**
     * 视图。
     */
    public String adViewId;

    /**
     * 获取：视图。
     */
    public String getAdViewId() {
        return this.adViewId;
    }

    /**
     * 设置：视图。
     */
    public WfNode setAdViewId(String adViewId) {
        this.adViewId = adViewId;
        return this;
    }

    /**
     * 图标文件组。
     */
    public String iconFileGroupId;

    /**
     * 获取：图标文件组。
     */
    public String getIconFileGroupId() {
        return this.iconFileGroupId;
    }

    /**
     * 设置：图标文件组。
     */
    public WfNode setIconFileGroupId(String iconFileGroupId) {
        this.iconFileGroupId = iconFileGroupId;
        return this;
    }

    /**
     * 图标宽度。
     */
    public String iconWidth;

    /**
     * 获取：图标宽度。
     */
    public String getIconWidth() {
        return this.iconWidth;
    }

    /**
     * 设置：图标宽度。
     */
    public WfNode setIconWidth(String iconWidth) {
        this.iconWidth = iconWidth;
        return this;
    }

    /**
     * 图标高度。
     */
    public String iconHeight;

    /**
     * 获取：图标高度。
     */
    public String getIconHeight() {
        return this.iconHeight;
    }

    /**
     * 设置：图标高度。
     */
    public WfNode setIconHeight(String iconHeight) {
        this.iconHeight = iconHeight;
        return this;
    }

    /**
     * 节点X坐标。
     */
    public Integer nodeX;

    /**
     * 获取：节点X坐标。
     */
    public Integer getNodeX() {
        return this.nodeX;
    }

    /**
     * 设置：节点X坐标。
     */
    public WfNode setNodeX(Integer nodeX) {
        this.nodeX = nodeX;
        return this;
    }

    /**
     * 节点Y坐标。
     */
    public Integer nodeY;

    /**
     * 获取：节点Y坐标。
     */
    public Integer getNodeY() {
        return this.nodeY;
    }

    /**
     * 设置：节点Y坐标。
     */
    public WfNode setNodeY(Integer nodeY) {
        this.nodeY = nodeY;
        return this;
    }

    /**
     * 节点宽度。
     */
    public Integer nodeWidth;

    /**
     * 获取：节点宽度。
     */
    public Integer getNodeWidth() {
        return this.nodeWidth;
    }

    /**
     * 设置：节点宽度。
     */
    public WfNode setNodeWidth(Integer nodeWidth) {
        this.nodeWidth = nodeWidth;
        return this;
    }

    /**
     * 节点高度。
     */
    public Integer nodeHeight;

    /**
     * 获取：节点高度。
     */
    public Integer getNodeHeight() {
        return this.nodeHeight;
    }

    /**
     * 设置：节点高度。
     */
    public WfNode setNodeHeight(Integer nodeHeight) {
        this.nodeHeight = nodeHeight;
        return this;
    }

    /**
     * 待办顺序。
     */
    public String wfTodoSeqId;

    /**
     * 获取：待办顺序。
     */
    public String getWfTodoSeqId() {
        return this.wfTodoSeqId;
    }

    /**
     * 设置：待办顺序。
     */
    public WfNode setWfTodoSeqId(String wfTodoSeqId) {
        this.wfTodoSeqId = wfTodoSeqId;
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
    public WfNode setSeqNo(Double seqNo) {
        this.seqNo = seqNo;
        return this;
    }

    /**
     * 父节点。
     */
    public String wfNodePid;

    /**
     * 获取：父节点。
     */
    public String getWfNodePid() {
        return this.wfNodePid;
    }

    /**
     * 设置：父节点。
     */
    public WfNode setWfNodePid(String wfNodePid) {
        this.wfNodePid = wfNodePid;
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
    public static WfNode newData() {
        return modelHelper.newData();
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static WfNode insertData() {
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
    public static WfNode selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<WfNode> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<WfNode> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
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
    public static void copyCols(WfNode fromModel, WfNode toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}