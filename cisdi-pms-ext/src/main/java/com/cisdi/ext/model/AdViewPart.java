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
 * 视图部分。
 */
public class AdViewPart {

    /**
     * 模型助手。
     */
    private static final ModelHelper<AdViewPart> modelHelper = new ModelHelper<>("AD_VIEW_PART", new AdViewPart());

    // 实体常量：
    // <editor-fold>

    public static final String ENT_CODE = "AD_VIEW_PART";
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
         * 序号。
         */
        public static final String SEQ_NO = "SEQ_NO";
        /**
         * 容器视图。
         */
        public static final String AD_CONTAINER_ID = "AD_CONTAINER_ID";
        /**
         * 实体视图。
         */
        public static final String AD_SINGLE_ENT_VIEW_ID = "AD_SINGLE_ENT_VIEW_ID";
        /**
         * 定制视图。
         */
        public static final String AD_CUSTOM_VIEW_ID = "AD_CUSTOM_VIEW_ID";
        /**
         * 宽度。
         */
        public static final String WIDTH = "WIDTH";
        /**
         * 高度。
         */
        public static final String HEIGHT = "HEIGHT";
        /**
         * 驱动方的视图部分。
         */
        public static final String DRIVING_VIEW_PART_ID = "DRIVING_VIEW_PART_ID";
        /**
         * 被驱动方的过滤语句。
         */
        public static final String DRIVEN_WHERE_CLAUSE = "DRIVEN_WHERE_CLAUSE";
        /**
         * 最小宽度。
         */
        public static final String MIN_WIDTH = "MIN_WIDTH";
        /**
         * 最大宽度。
         */
        public static final String MAX_WIDTH = "MAX_WIDTH";
        /**
         * 最小高度。
         */
        public static final String MIN_HEIGHT = "MIN_HEIGHT";
        /**
         * 最大高度。
         */
        public static final String MAX_HEIGHT = "MAX_HEIGHT";
        /**
         * PORTAL主列号。
         */
        public static final String PORTAL_MAIN_COL_NUM = "PORTAL_MAIN_COL_NUM";
        /**
         * PORTAL列中行号。
         */
        public static final String PORTAL_ROW_NUM_IN_COL = "PORTAL_ROW_NUM_IN_COL";
        /**
         * PORTAL行中偏移号。
         */
        public static final String PORTAL_ROW_OFFSET_NUM = "PORTAL_ROW_OFFSET_NUM";
        /**
         * 溢出模式。
         */
        public static final String OVERFLOW = "OVERFLOW";
        /**
         * 空隙。
         */
        public static final String MARGIN = "MARGIN";
        /**
         * 边框。
         */
        public static final String BORDER = "BORDER";
        /**
         * 填充。
         */
        public static final String PADDING = "PADDING";
        /**
         * 父视图部分。
         */
        public static final String PARENT_VP_ID = "PARENT_VP_ID";
        /**
         * 视图。
         */
        public static final String AD_VIEW_ID = "AD_VIEW_ID";
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
    public AdViewPart setId(String id) {
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
    public AdViewPart setVer(Integer ver) {
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
    public AdViewPart setTs(LocalDateTime ts) {
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
    public AdViewPart setIsPreset(Boolean isPreset) {
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
    public AdViewPart setCrtDt(LocalDateTime crtDt) {
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
    public AdViewPart setCrtUserId(String crtUserId) {
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
    public AdViewPart setLastModiDt(LocalDateTime lastModiDt) {
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
    public AdViewPart setLastModiUserId(String lastModiUserId) {
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
    public AdViewPart setStatus(String status) {
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
    public AdViewPart setLkWfInstId(String lkWfInstId) {
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
    public AdViewPart setCode(String code) {
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
    public AdViewPart setName(String name) {
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
    public AdViewPart setRemark(String remark) {
        this.remark = remark;
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
    public AdViewPart setSeqNo(Double seqNo) {
        this.seqNo = seqNo;
        return this;
    }

    /**
     * 容器视图。
     */
    public String adContainerId;

    /**
     * 获取：容器视图。
     */
    public String getAdContainerId() {
        return this.adContainerId;
    }

    /**
     * 设置：容器视图。
     */
    public AdViewPart setAdContainerId(String adContainerId) {
        this.adContainerId = adContainerId;
        return this;
    }

    /**
     * 实体视图。
     */
    public String adSingleEntViewId;

    /**
     * 获取：实体视图。
     */
    public String getAdSingleEntViewId() {
        return this.adSingleEntViewId;
    }

    /**
     * 设置：实体视图。
     */
    public AdViewPart setAdSingleEntViewId(String adSingleEntViewId) {
        this.adSingleEntViewId = adSingleEntViewId;
        return this;
    }

    /**
     * 定制视图。
     */
    public String adCustomViewId;

    /**
     * 获取：定制视图。
     */
    public String getAdCustomViewId() {
        return this.adCustomViewId;
    }

    /**
     * 设置：定制视图。
     */
    public AdViewPart setAdCustomViewId(String adCustomViewId) {
        this.adCustomViewId = adCustomViewId;
        return this;
    }

    /**
     * 宽度。
     */
    public String width;

    /**
     * 获取：宽度。
     */
    public String getWidth() {
        return this.width;
    }

    /**
     * 设置：宽度。
     */
    public AdViewPart setWidth(String width) {
        this.width = width;
        return this;
    }

    /**
     * 高度。
     */
    public String height;

    /**
     * 获取：高度。
     */
    public String getHeight() {
        return this.height;
    }

    /**
     * 设置：高度。
     */
    public AdViewPart setHeight(String height) {
        this.height = height;
        return this;
    }

    /**
     * 驱动方的视图部分。
     */
    public String drivingViewPartId;

    /**
     * 获取：驱动方的视图部分。
     */
    public String getDrivingViewPartId() {
        return this.drivingViewPartId;
    }

    /**
     * 设置：驱动方的视图部分。
     */
    public AdViewPart setDrivingViewPartId(String drivingViewPartId) {
        this.drivingViewPartId = drivingViewPartId;
        return this;
    }

    /**
     * 被驱动方的过滤语句。
     */
    public String drivenWhereClause;

    /**
     * 获取：被驱动方的过滤语句。
     */
    public String getDrivenWhereClause() {
        return this.drivenWhereClause;
    }

    /**
     * 设置：被驱动方的过滤语句。
     */
    public AdViewPart setDrivenWhereClause(String drivenWhereClause) {
        this.drivenWhereClause = drivenWhereClause;
        return this;
    }

    /**
     * 最小宽度。
     */
    public Integer minWidth;

    /**
     * 获取：最小宽度。
     */
    public Integer getMinWidth() {
        return this.minWidth;
    }

    /**
     * 设置：最小宽度。
     */
    public AdViewPart setMinWidth(Integer minWidth) {
        this.minWidth = minWidth;
        return this;
    }

    /**
     * 最大宽度。
     */
    public Integer maxWidth;

    /**
     * 获取：最大宽度。
     */
    public Integer getMaxWidth() {
        return this.maxWidth;
    }

    /**
     * 设置：最大宽度。
     */
    public AdViewPart setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    /**
     * 最小高度。
     */
    public Integer minHeight;

    /**
     * 获取：最小高度。
     */
    public Integer getMinHeight() {
        return this.minHeight;
    }

    /**
     * 设置：最小高度。
     */
    public AdViewPart setMinHeight(Integer minHeight) {
        this.minHeight = minHeight;
        return this;
    }

    /**
     * 最大高度。
     */
    public Integer maxHeight;

    /**
     * 获取：最大高度。
     */
    public Integer getMaxHeight() {
        return this.maxHeight;
    }

    /**
     * 设置：最大高度。
     */
    public AdViewPart setMaxHeight(Integer maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    /**
     * PORTAL主列号。
     */
    public Integer portalMainColNum;

    /**
     * 获取：PORTAL主列号。
     */
    public Integer getPortalMainColNum() {
        return this.portalMainColNum;
    }

    /**
     * 设置：PORTAL主列号。
     */
    public AdViewPart setPortalMainColNum(Integer portalMainColNum) {
        this.portalMainColNum = portalMainColNum;
        return this;
    }

    /**
     * PORTAL列中行号。
     */
    public Integer portalRowNumInCol;

    /**
     * 获取：PORTAL列中行号。
     */
    public Integer getPortalRowNumInCol() {
        return this.portalRowNumInCol;
    }

    /**
     * 设置：PORTAL列中行号。
     */
    public AdViewPart setPortalRowNumInCol(Integer portalRowNumInCol) {
        this.portalRowNumInCol = portalRowNumInCol;
        return this;
    }

    /**
     * PORTAL行中偏移号。
     */
    public Integer portalRowOffsetNum;

    /**
     * 获取：PORTAL行中偏移号。
     */
    public Integer getPortalRowOffsetNum() {
        return this.portalRowOffsetNum;
    }

    /**
     * 设置：PORTAL行中偏移号。
     */
    public AdViewPart setPortalRowOffsetNum(Integer portalRowOffsetNum) {
        this.portalRowOffsetNum = portalRowOffsetNum;
        return this;
    }

    /**
     * 溢出模式。
     */
    public String overflow;

    /**
     * 获取：溢出模式。
     */
    public String getOverflow() {
        return this.overflow;
    }

    /**
     * 设置：溢出模式。
     */
    public AdViewPart setOverflow(String overflow) {
        this.overflow = overflow;
        return this;
    }

    /**
     * 空隙。
     */
    public Integer margin;

    /**
     * 获取：空隙。
     */
    public Integer getMargin() {
        return this.margin;
    }

    /**
     * 设置：空隙。
     */
    public AdViewPart setMargin(Integer margin) {
        this.margin = margin;
        return this;
    }

    /**
     * 边框。
     */
    public String border;

    /**
     * 获取：边框。
     */
    public String getBorder() {
        return this.border;
    }

    /**
     * 设置：边框。
     */
    public AdViewPart setBorder(String border) {
        this.border = border;
        return this;
    }

    /**
     * 填充。
     */
    public Integer padding;

    /**
     * 获取：填充。
     */
    public Integer getPadding() {
        return this.padding;
    }

    /**
     * 设置：填充。
     */
    public AdViewPart setPadding(Integer padding) {
        this.padding = padding;
        return this;
    }

    /**
     * 父视图部分。
     */
    public String parentVpId;

    /**
     * 获取：父视图部分。
     */
    public String getParentVpId() {
        return this.parentVpId;
    }

    /**
     * 设置：父视图部分。
     */
    public AdViewPart setParentVpId(String parentVpId) {
        this.parentVpId = parentVpId;
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
    public AdViewPart setAdViewId(String adViewId) {
        this.adViewId = adViewId;
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
    public static AdViewPart insertData() {
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
    public static AdViewPart selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<AdViewPart> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<AdViewPart> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
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
    public static void copyCols(AdViewPart fromModel, AdViewPart toModel, List<String> includeCols, List<String> excludeCols) {
        ModelHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}