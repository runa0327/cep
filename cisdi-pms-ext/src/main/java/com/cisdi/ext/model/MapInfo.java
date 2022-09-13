package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 地图信息。
 */
@ToString
public class MapInfo {

    /**
     * 模型助手。
     */
    private static final ModelHelper<MapInfo> modelHelper = new ModelHelper<>("MAP_INFO", new MapInfo());

    // 实体常量：
    // <editor-fold>

    public static final String ENT_CODE = "MAP_INFO";
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
         * 填充不透明度。
         */
        public static final String FILL_OPACITY = "FILL_OPACITY";
        /**
         * 地图id。
         */
        public static final String MAP_ID = "MAP_ID";
        /**
         * 类型(中间)。
         */
        public static final String MID_TYPE = "MID_TYPE";
        /**
         * 类型(里面)。
         */
        public static final String INNER_TYPE = "INNER_TYPE";
        /**
         * 笔画不透明度。
         */
        public static final String STROKE_OPACITY = "STROKE_OPACITY";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 项目名称。
         */
        public static final String PRJ_NAME = "PRJ_NAME";
        /**
         * 笔画宽度。
         */
        public static final String STROKE_WIDTH = "STROKE_WIDTH";
        /**
         * 填充。
         */
        public static final String FILL = "FILL";
        /**
         * 笔画。
         */
        public static final String STROKE = "STROKE";
        /**
         * 面积。
         */
        public static final String AREA = "AREA";
        /**
         * 容积率。
         */
        public static final String PLOT_RATIO = "PLOT_RATIO";
        /**
         * 土地信息备注。
         */
        public static final String LAND_NOTE = "LAND_NOTE";
        /**
         * 字典键值。
         */
        public static final String DICT_VALUE = "DICT_VALUE";
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
    public MapInfo setId(String id) {
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
    public MapInfo setVer(Integer ver) {
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
    public MapInfo setTs(LocalDateTime ts) {
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
    public MapInfo setIsPreset(Boolean isPreset) {
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
    public MapInfo setCrtDt(LocalDateTime crtDt) {
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
    public MapInfo setCrtUserId(String crtUserId) {
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
    public MapInfo setLastModiDt(LocalDateTime lastModiDt) {
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
    public MapInfo setLastModiUserId(String lastModiUserId) {
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
    public MapInfo setStatus(String status) {
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
    public MapInfo setLkWfInstId(String lkWfInstId) {
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
    public MapInfo setCode(String code) {
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
    public MapInfo setName(String name) {
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
    public MapInfo setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    /**
     * 填充不透明度。
     */
    public String fillOpacity;

    /**
     * 获取：填充不透明度。
     */
    public String getFillOpacity() {
        return this.fillOpacity;
    }

    /**
     * 设置：填充不透明度。
     */
    public MapInfo setFillOpacity(String fillOpacity) {
        this.fillOpacity = fillOpacity;
        return this;
    }

    /**
     * 地图id。
     */
    public String mapId;

    /**
     * 获取：地图id。
     */
    public String getMapId() {
        return this.mapId;
    }

    /**
     * 设置：地图id。
     */
    public MapInfo setMapId(String mapId) {
        this.mapId = mapId;
        return this;
    }

    /**
     * 类型(中间)。
     */
    public String midType;

    /**
     * 获取：类型(中间)。
     */
    public String getMidType() {
        return this.midType;
    }

    /**
     * 设置：类型(中间)。
     */
    public MapInfo setMidType(String midType) {
        this.midType = midType;
        return this;
    }

    /**
     * 类型(里面)。
     */
    public String innerType;

    /**
     * 获取：类型(里面)。
     */
    public String getInnerType() {
        return this.innerType;
    }

    /**
     * 设置：类型(里面)。
     */
    public MapInfo setInnerType(String innerType) {
        this.innerType = innerType;
        return this;
    }

    /**
     * 笔画不透明度。
     */
    public Double strokeOpacity;

    /**
     * 获取：笔画不透明度。
     */
    public Double getStrokeOpacity() {
        return this.strokeOpacity;
    }

    /**
     * 设置：笔画不透明度。
     */
    public MapInfo setStrokeOpacity(Double strokeOpacity) {
        this.strokeOpacity = strokeOpacity;
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
    public MapInfo setPmPrjId(String pmPrjId) {
        this.pmPrjId = pmPrjId;
        return this;
    }

    /**
     * 项目名称。
     */
    public String prjName;

    /**
     * 获取：项目名称。
     */
    public String getPrjName() {
        return this.prjName;
    }

    /**
     * 设置：项目名称。
     */
    public MapInfo setPrjName(String prjName) {
        this.prjName = prjName;
        return this;
    }

    /**
     * 笔画宽度。
     */
    public Double strokeWidth;

    /**
     * 获取：笔画宽度。
     */
    public Double getStrokeWidth() {
        return this.strokeWidth;
    }

    /**
     * 设置：笔画宽度。
     */
    public MapInfo setStrokeWidth(Double strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }

    /**
     * 填充。
     */
    public String fill;

    /**
     * 获取：填充。
     */
    public String getFill() {
        return this.fill;
    }

    /**
     * 设置：填充。
     */
    public MapInfo setFill(String fill) {
        this.fill = fill;
        return this;
    }

    /**
     * 笔画。
     */
    public String stroke;

    /**
     * 获取：笔画。
     */
    public String getStroke() {
        return this.stroke;
    }

    /**
     * 设置：笔画。
     */
    public MapInfo setStroke(String stroke) {
        this.stroke = stroke;
        return this;
    }

    /**
     * 面积。
     */
    public Double area;

    /**
     * 获取：面积。
     */
    public Double getArea() {
        return this.area;
    }

    /**
     * 设置：面积。
     */
    public MapInfo setArea(Double area) {
        this.area = area;
        return this;
    }

    /**
     * 容积率。
     */
    public Double plotRatio;

    /**
     * 获取：容积率。
     */
    public Double getPlotRatio() {
        return this.plotRatio;
    }

    /**
     * 设置：容积率。
     */
    public MapInfo setPlotRatio(Double plotRatio) {
        this.plotRatio = plotRatio;
        return this;
    }

    /**
     * 土地信息备注。
     */
    public String landNote;

    /**
     * 获取：土地信息备注。
     */
    public String getLandNote() {
        return this.landNote;
    }

    /**
     * 设置：土地信息备注。
     */
    public MapInfo setLandNote(String landNote) {
        this.landNote = landNote;
        return this;
    }

    /**
     * 字典键值。
     */
    public String dictValue;

    /**
     * 获取：字典键值。
     */
    public String getDictValue() {
        return this.dictValue;
    }

    /**
     * 设置：字典键值。
     */
    public MapInfo setDictValue(String dictValue) {
        this.dictValue = dictValue;
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
    public static MapInfo newData() {
        return modelHelper.newData();
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static MapInfo insertData() {
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
    public static MapInfo selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<MapInfo> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<MapInfo> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
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
    public static void copyCols(MapInfo fromModel, MapInfo toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}