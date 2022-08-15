package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * 实体视图操作。
 */
public class AdSingleEntViewAct {

    /**
     * 模型助手。
     */
    private static final ModelHelper<AdSingleEntViewAct> modelHelper = new ModelHelper<>("AD_SINGLE_ENT_VIEW_ACT", new AdSingleEntViewAct());

    // 实体常量：
    // <editor-fold>

    public static final String ENT_CODE = "AD_SINGLE_ENT_VIEW_ACT";
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
         * 实体视图。
         */
        public static final String AD_SINGLE_ENT_VIEW_ID = "AD_SINGLE_ENT_VIEW_ID";
        /**
         * 操作。
         */
        public static final String AD_ACT_ID = "AD_ACT_ID";
        /**
         * 实体操作关系。
         */
        public static final String ENT_ACT_RELATION = "ENT_ACT_RELATION";
        /**
         * 操作序号。
         */
        public static final String ACT_SEQ_NO = "ACT_SEQ_NO";
        /**
         * 操作名称。
         */
        public static final String ACT_NAME = "ACT_NAME";
        /**
         * 操作备注。
         */
        public static final String ACT_REMARK = "ACT_REMARK";
        /**
         * 操作热键。
         */
        public static final String ACT_HOTKEY = "ACT_HOTKEY";
        /**
         * 锁定记录。
         */
        public static final String LOCK_REC = "LOCK_REC";
        /**
         * 预检测扩展点。
         */
        public static final String PRE_CHK_EXT_POINT_ID = "PRE_CHK_EXT_POINT_ID";
        /**
         * 操作时扩展点。
         */
        public static final String ON_ACT_EXT_POINT_ID = "ON_ACT_EXT_POINT_ID";
        /**
         * 操作与所选相关。
         */
        public static final String ACT_IS_SELECT_AWARE = "ACT_IS_SELECT_AWARE";
        /**
         * 操作可执行逻辑。
         */
        public static final String ACT_EXECUTABLE_LOGIC = "ACT_EXECUTABLE_LOGIC";
        /**
         * 操作选择模式。
         */
        public static final String AD_ACT_SELECT_MODE_ID = "AD_ACT_SELECT_MODE_ID";
        /**
         * 操作确认模式。
         */
        public static final String AD_ACT_CONFIRM_MODE_ID = "AD_ACT_CONFIRM_MODE_ID";
        /**
         * 操作确认消息。
         */
        public static final String AD_ACT_CONFIRM_MSG = "AD_ACT_CONFIRM_MSG";
        /**
         * 明细显示。
         */
        public static final String SHOWN_IN_DTL_WINDOW = "SHOWN_IN_DTL_WINDOW";
        /**
         * 弹出的过滤器实体视图。
         */
        public static final String POPUP_FILTER_SEV_ID = "POPUP_FILTER_SEV_ID";
        /**
         * 弹出宽度。
         */
        public static final String POPUP_WIDTH = "POPUP_WIDTH";
        /**
         * 弹出高度。
         */
        public static final String POPUP_HEIGHT = "POPUP_HEIGHT";
        /**
         * 弹出显示直到成功。
         */
        public static final String POPUP_SHOW_TILL_SUCC = "POPUP_SHOW_TILL_SUCC";
        /**
         * 权限级别。
         */
        public static final String AUTH_LVL = "AUTH_LVL";
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
    public AdSingleEntViewAct setId(String id) {
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
    public AdSingleEntViewAct setVer(Integer ver) {
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
    public AdSingleEntViewAct setTs(LocalDateTime ts) {
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
    public AdSingleEntViewAct setIsPreset(Boolean isPreset) {
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
    public AdSingleEntViewAct setCrtDt(LocalDateTime crtDt) {
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
    public AdSingleEntViewAct setCrtUserId(String crtUserId) {
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
    public AdSingleEntViewAct setLastModiDt(LocalDateTime lastModiDt) {
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
    public AdSingleEntViewAct setLastModiUserId(String lastModiUserId) {
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
    public AdSingleEntViewAct setStatus(String status) {
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
    public AdSingleEntViewAct setLkWfInstId(String lkWfInstId) {
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
    public AdSingleEntViewAct setCode(String code) {
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
    public AdSingleEntViewAct setName(String name) {
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
    public AdSingleEntViewAct setRemark(String remark) {
        this.remark = remark;
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
    public AdSingleEntViewAct setAdSingleEntViewId(String adSingleEntViewId) {
        this.adSingleEntViewId = adSingleEntViewId;
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
    public AdSingleEntViewAct setAdActId(String adActId) {
        this.adActId = adActId;
        return this;
    }

    /**
     * 实体操作关系。
     */
    public String entActRelation;

    /**
     * 获取：实体操作关系。
     */
    public String getEntActRelation() {
        return this.entActRelation;
    }

    /**
     * 设置：实体操作关系。
     */
    public AdSingleEntViewAct setEntActRelation(String entActRelation) {
        this.entActRelation = entActRelation;
        return this;
    }

    /**
     * 操作序号。
     */
    public Double actSeqNo;

    /**
     * 获取：操作序号。
     */
    public Double getActSeqNo() {
        return this.actSeqNo;
    }

    /**
     * 设置：操作序号。
     */
    public AdSingleEntViewAct setActSeqNo(Double actSeqNo) {
        this.actSeqNo = actSeqNo;
        return this;
    }

    /**
     * 操作名称。
     */
    public String actName;

    /**
     * 获取：操作名称。
     */
    public String getActName() {
        return this.actName;
    }

    /**
     * 设置：操作名称。
     */
    public AdSingleEntViewAct setActName(String actName) {
        this.actName = actName;
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
    public AdSingleEntViewAct setActRemark(String actRemark) {
        this.actRemark = actRemark;
        return this;
    }

    /**
     * 操作热键。
     */
    public String actHotkey;

    /**
     * 获取：操作热键。
     */
    public String getActHotkey() {
        return this.actHotkey;
    }

    /**
     * 设置：操作热键。
     */
    public AdSingleEntViewAct setActHotkey(String actHotkey) {
        this.actHotkey = actHotkey;
        return this;
    }

    /**
     * 锁定记录。
     */
    public Boolean lockRec;

    /**
     * 获取：锁定记录。
     */
    public Boolean getLockRec() {
        return this.lockRec;
    }

    /**
     * 设置：锁定记录。
     */
    public AdSingleEntViewAct setLockRec(Boolean lockRec) {
        this.lockRec = lockRec;
        return this;
    }

    /**
     * 预检测扩展点。
     */
    public String preChkExtPointId;

    /**
     * 获取：预检测扩展点。
     */
    public String getPreChkExtPointId() {
        return this.preChkExtPointId;
    }

    /**
     * 设置：预检测扩展点。
     */
    public AdSingleEntViewAct setPreChkExtPointId(String preChkExtPointId) {
        this.preChkExtPointId = preChkExtPointId;
        return this;
    }

    /**
     * 操作时扩展点。
     */
    public String onActExtPointId;

    /**
     * 获取：操作时扩展点。
     */
    public String getOnActExtPointId() {
        return this.onActExtPointId;
    }

    /**
     * 设置：操作时扩展点。
     */
    public AdSingleEntViewAct setOnActExtPointId(String onActExtPointId) {
        this.onActExtPointId = onActExtPointId;
        return this;
    }

    /**
     * 操作与所选相关。
     */
    public Boolean actIsSelectAware;

    /**
     * 获取：操作与所选相关。
     */
    public Boolean getActIsSelectAware() {
        return this.actIsSelectAware;
    }

    /**
     * 设置：操作与所选相关。
     */
    public AdSingleEntViewAct setActIsSelectAware(Boolean actIsSelectAware) {
        this.actIsSelectAware = actIsSelectAware;
        return this;
    }

    /**
     * 操作可执行逻辑。
     */
    public String actExecutableLogic;

    /**
     * 获取：操作可执行逻辑。
     */
    public String getActExecutableLogic() {
        return this.actExecutableLogic;
    }

    /**
     * 设置：操作可执行逻辑。
     */
    public AdSingleEntViewAct setActExecutableLogic(String actExecutableLogic) {
        this.actExecutableLogic = actExecutableLogic;
        return this;
    }

    /**
     * 操作选择模式。
     */
    public String adActSelectModeId;

    /**
     * 获取：操作选择模式。
     */
    public String getAdActSelectModeId() {
        return this.adActSelectModeId;
    }

    /**
     * 设置：操作选择模式。
     */
    public AdSingleEntViewAct setAdActSelectModeId(String adActSelectModeId) {
        this.adActSelectModeId = adActSelectModeId;
        return this;
    }

    /**
     * 操作确认模式。
     */
    public String adActConfirmModeId;

    /**
     * 获取：操作确认模式。
     */
    public String getAdActConfirmModeId() {
        return this.adActConfirmModeId;
    }

    /**
     * 设置：操作确认模式。
     */
    public AdSingleEntViewAct setAdActConfirmModeId(String adActConfirmModeId) {
        this.adActConfirmModeId = adActConfirmModeId;
        return this;
    }

    /**
     * 操作确认消息。
     */
    public String adActConfirmMsg;

    /**
     * 获取：操作确认消息。
     */
    public String getAdActConfirmMsg() {
        return this.adActConfirmMsg;
    }

    /**
     * 设置：操作确认消息。
     */
    public AdSingleEntViewAct setAdActConfirmMsg(String adActConfirmMsg) {
        this.adActConfirmMsg = adActConfirmMsg;
        return this;
    }

    /**
     * 明细显示。
     */
    public Boolean shownInDtlWindow;

    /**
     * 获取：明细显示。
     */
    public Boolean getShownInDtlWindow() {
        return this.shownInDtlWindow;
    }

    /**
     * 设置：明细显示。
     */
    public AdSingleEntViewAct setShownInDtlWindow(Boolean shownInDtlWindow) {
        this.shownInDtlWindow = shownInDtlWindow;
        return this;
    }

    /**
     * 弹出的过滤器实体视图。
     */
    public String popupFilterSevId;

    /**
     * 获取：弹出的过滤器实体视图。
     */
    public String getPopupFilterSevId() {
        return this.popupFilterSevId;
    }

    /**
     * 设置：弹出的过滤器实体视图。
     */
    public AdSingleEntViewAct setPopupFilterSevId(String popupFilterSevId) {
        this.popupFilterSevId = popupFilterSevId;
        return this;
    }

    /**
     * 弹出宽度。
     */
    public String popupWidth;

    /**
     * 获取：弹出宽度。
     */
    public String getPopupWidth() {
        return this.popupWidth;
    }

    /**
     * 设置：弹出宽度。
     */
    public AdSingleEntViewAct setPopupWidth(String popupWidth) {
        this.popupWidth = popupWidth;
        return this;
    }

    /**
     * 弹出高度。
     */
    public String popupHeight;

    /**
     * 获取：弹出高度。
     */
    public String getPopupHeight() {
        return this.popupHeight;
    }

    /**
     * 设置：弹出高度。
     */
    public AdSingleEntViewAct setPopupHeight(String popupHeight) {
        this.popupHeight = popupHeight;
        return this;
    }

    /**
     * 弹出显示直到成功。
     */
    public Boolean popupShowTillSucc;

    /**
     * 获取：弹出显示直到成功。
     */
    public Boolean getPopupShowTillSucc() {
        return this.popupShowTillSucc;
    }

    /**
     * 设置：弹出显示直到成功。
     */
    public AdSingleEntViewAct setPopupShowTillSucc(Boolean popupShowTillSucc) {
        this.popupShowTillSucc = popupShowTillSucc;
        return this;
    }

    /**
     * 权限级别。
     */
    public Integer authLvl;

    /**
     * 获取：权限级别。
     */
    public Integer getAuthLvl() {
        return this.authLvl;
    }

    /**
     * 设置：权限级别。
     */
    public AdSingleEntViewAct setAuthLvl(Integer authLvl) {
        this.authLvl = authLvl;
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
    public static AdSingleEntViewAct newData() {
        return modelHelper.newData();
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static AdSingleEntViewAct insertData() {
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
    public static AdSingleEntViewAct selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<AdSingleEntViewAct> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<AdSingleEntViewAct> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
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
    public static void copyCols(AdSingleEntViewAct fromModel, AdSingleEntViewAct toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}