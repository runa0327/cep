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
 * 实体视图。
 */
public class AdSingleEntView {

    /**
     * 模型助手。
     */
    private static final ModelHelper<AdSingleEntView> modelHelper = new ModelHelper<>("AD_SINGLE_ENT_VIEW", new AdSingleEntView());

    // 实体常量：
    // <editor-fold>

    public static final String ENT_CODE = "AD_SINGLE_ENT_VIEW";
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
         * 实体视图显示类型。
         */
        public static final String SEV_DISPLAY_TYPE = "SEV_DISPLAY_TYPE";
        /**
         * 实体。
         */
        public static final String AD_ENT_ID = "AD_ENT_ID";
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
         * 树属性。
         */
        public static final String TREE_ATT_ID = "TREE_ATT_ID";
        /**
         * 自动获取数据类型。
         */
        public static final String AD_AUO_FETCH_DATA_TYPE_ID = "AD_AUO_FETCH_DATA_TYPE_ID";
        /**
         * 刷新后选择1行。
         */
        public static final String SELECT_1_AFT_FTH = "SELECT_1_AFT_FTH";
        /**
         * 多选类型。
         */
        public static final String AD_MULTI_SELECT_TYPE_ID = "AD_MULTI_SELECT_TYPE_ID";
        /**
         * 状态列显示类型。
         */
        public static final String SIGNAL_COLS_DSP_TYPE = "SIGNAL_COLS_DSP_TYPE";
        /**
         * 折行类型。
         */
        public static final String AD_WRAP_TYPE_ID = "AD_WRAP_TYPE_ID";
        /**
         * 显示过滤行。
         */
        public static final String SHOW_FILTER_ROW = "SHOW_FILTER_ROW";
        /**
         * 隐藏标题。
         */
        public static final String HIDE_TITLE = "HIDE_TITLE";
        /**
         * 隐藏操作条。
         */
        public static final String HIDE_ACT_STRP = "HIDE_ACT_STRP";
        /**
         * 分页条显示类型。
         */
        public static final String AD_PAGE_STRIP_DSP_TYPE_ID = "AD_PAGE_STRIP_DSP_TYPE_ID";
        /**
         * 隐藏列头。
         */
        public static final String HIDE_HEADER = "HIDE_HEADER";
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
         * 根据数据导航。
         */
        public static final String NAV_BY_DATA = "NAV_BY_DATA";
        /**
         * 实体记录新建位置。
         */
        public static final String AD_ER_CRT_POS_ID = "AD_ER_CRT_POS_ID";
        /**
         * 无法新建时消息逻辑。
         */
        public static final String NOT_CREATABLE_MSG_LOGIC = "NOT_CREATABLE_MSG_LOGIC";
        /**
         * 改变时过滤。
         */
        public static final String FILTER_ON_CHANGE = "FILTER_ON_CHANGE";
        /**
         * 显示过滤按钮。
         */
        public static final String SHOW_FILTER_BTN = "SHOW_FILTER_BTN";
        /**
         * 显示过滤重置按钮。
         */
        public static final String SHOW_FILTER_RESET_BTN = "SHOW_FILTER_RESET_BTN";
        /**
         * 启用样式文本。
         */
        public static final String ENABLE_STYLE_TEXT = "ENABLE_STYLE_TEXT";
        /**
         * 奇行单元格样式文本逻辑。
         */
        public static final String ODD_ROW_CELL_ST_LOGIC = "ODD_ROW_CELL_ST_LOGIC";
        /**
         * 偶行单元格样式文本逻辑。
         */
        public static final String EVEN_ROW_CELL_ST_LOGIC = "EVEN_ROW_CELL_ST_LOGIC";
        /**
         * 每次查询最大行数。
         */
        public static final String MAX_ROW_COUNT_PER_QUERY = "MAX_ROW_COUNT_PER_QUERY";
        /**
         * 移动端单行操作数量。
         */
        public static final String MOB_ACT_CT_IN_LINE = "MOB_ACT_CT_IN_LINE";
        /**
         * 展开的树层级。
         */
        public static final String EXPANDED_TREE_LEVEL = "EXPANDED_TREE_LEVEL";
        /**
         * 树图标属性。
         */
        public static final String TREE_ICON_ATT_ID = "TREE_ICON_ATT_ID";
        /**
         * 树图标字段宽度。
         */
        public static final String TREE_ICON_FIELD_WIDTH = "TREE_ICON_FIELD_WIDTH";
        /**
         * 客户端操作收缩。
         */
        public static final String CLIENT_ACT_COLLAPSED = "CLIENT_ACT_COLLAPSED";
        /**
         * 权限检查。
         */
        public static final String ACL_CHK = "ACL_CHK";
        /**
         * 显示行号。
         */
        public static final String SHOW_ROWNUM = "SHOW_ROWNUM";
        /**
         * 操作最大权限级别。
         */
        public static final String ACT_MAX_AUTH_LVL = "ACT_MAX_AUTH_LVL";
        /**
         * 快捷搜索启用。
         */
        public static final String FAST_SEARCH_ENABLED = "FAST_SEARCH_ENABLED";
        /**
         * 快捷搜索提示。
         */
        public static final String FAST_SEARCH_HINT = "FAST_SEARCH_HINT";
        /**
         * 快捷搜索过滤语句。
         */
        public static final String FAST_SEARCH_WHERE_CLAUSE = "FAST_SEARCH_WHERE_CLAUSE";
        /**
         * 实体视图编辑模式。
         */
        public static final String AD_SEV_EDIT_MODE_ID = "AD_SEV_EDIT_MODE_ID";
        /**
         * 明细窗口宽度。
         */
        public static final String DTL_WINDOW_WIDTH = "DTL_WINDOW_WIDTH";
        /**
         * 明细窗口高度。
         */
        public static final String DTL_WINDOW_HEIGHT = "DTL_WINDOW_HEIGHT";
        /**
         * 明细窗口视图部分。
         */
        public static final String DTL_WINDOW_VP_ID = "DTL_WINDOW_VP_ID";
        /**
         * 明细窗口启用流程。
         */
        public static final String DTL_WINDOW_WF_ENABLED = "DTL_WINDOW_WF_ENABLED";
        /**
         * 表单宽度。
         */
        public static final String FORM_WIDTH = "FORM_WIDTH";
        /**
         * 表单列数。
         */
        public static final String FORM_COLS = "FORM_COLS";
        /**
         * 表单各列宽度。
         */
        public static final String FORM_COL_WIDTHS = "FORM_COL_WIDTHS";
        /**
         * 表单单元格边框。
         */
        public static final String FORM_CELL_BORDER = "FORM_CELL_BORDER";
        /**
         * 表单单元格填充。
         */
        public static final String FORM_CELL_PADDING = "FORM_CELL_PADDING";
        /**
         * 批量修改窗口宽度。
         */
        public static final String BATCH_MODI_WINDOW_WIDTH = "BATCH_MODI_WINDOW_WIDTH";
        /**
         * 批量修改窗口高度。
         */
        public static final String BATCH_MODI_WINDOW_HEIGHT = "BATCH_MODI_WINDOW_HEIGHT";
        /**
         * 启用选择相关操作。
         */
        public static final String ENABLE_SELECT_AWARE_ACT = "ENABLE_SELECT_AWARE_ACT";
        /**
         * 启用显示文本。
         */
        public static final String ENABLE_DSP_TEXT = "ENABLE_DSP_TEXT";
        /**
         * FLEX开始样式。
         */
        public static final String FLEX_BEGIN_STYLE = "FLEX_BEGIN_STYLE";
        /**
         * FLEX记录样式。
         */
        public static final String FLEX_REC_STYLE = "FLEX_REC_STYLE";
        /**
         * FLEX分隔样式。
         */
        public static final String FLEX_SPLIT_STYLE = "FLEX_SPLIT_STYLE";
        /**
         * FLEX结束样式。
         */
        public static final String FLEX_END_STYLE = "FLEX_END_STYLE";
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
    public AdSingleEntView setId(String id) {
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
    public AdSingleEntView setVer(Integer ver) {
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
    public AdSingleEntView setTs(LocalDateTime ts) {
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
    public AdSingleEntView setIsPreset(Boolean isPreset) {
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
    public AdSingleEntView setCrtDt(LocalDateTime crtDt) {
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
    public AdSingleEntView setCrtUserId(String crtUserId) {
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
    public AdSingleEntView setLastModiDt(LocalDateTime lastModiDt) {
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
    public AdSingleEntView setLastModiUserId(String lastModiUserId) {
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
    public AdSingleEntView setStatus(String status) {
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
    public AdSingleEntView setLkWfInstId(String lkWfInstId) {
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
    public AdSingleEntView setCode(String code) {
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
    public AdSingleEntView setName(String name) {
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
    public AdSingleEntView setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    /**
     * 实体视图显示类型。
     */
    public String sevDisplayType;

    /**
     * 获取：实体视图显示类型。
     */
    public String getSevDisplayType() {
        return this.sevDisplayType;
    }

    /**
     * 设置：实体视图显示类型。
     */
    public AdSingleEntView setSevDisplayType(String sevDisplayType) {
        this.sevDisplayType = sevDisplayType;
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
    public AdSingleEntView setAdEntId(String adEntId) {
        this.adEntId = adEntId;
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
    public AdSingleEntView setJoinOnClause(String joinOnClause) {
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
    public AdSingleEntView setOrderByClause(String orderByClause) {
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
    public AdSingleEntView setWhereClause(String whereClause) {
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
    public AdSingleEntView setGroupByClause(String groupByClause) {
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
    public AdSingleEntView setHavingClause(String havingClause) {
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
    public AdSingleEntView setPageSize(Integer pageSize) {
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
    public AdSingleEntView setMobPageSize(Integer mobPageSize) {
        this.mobPageSize = mobPageSize;
        return this;
    }

    /**
     * 树属性。
     */
    public String treeAttId;

    /**
     * 获取：树属性。
     */
    public String getTreeAttId() {
        return this.treeAttId;
    }

    /**
     * 设置：树属性。
     */
    public AdSingleEntView setTreeAttId(String treeAttId) {
        this.treeAttId = treeAttId;
        return this;
    }

    /**
     * 自动获取数据类型。
     */
    public String adAuoFetchDataTypeId;

    /**
     * 获取：自动获取数据类型。
     */
    public String getAdAuoFetchDataTypeId() {
        return this.adAuoFetchDataTypeId;
    }

    /**
     * 设置：自动获取数据类型。
     */
    public AdSingleEntView setAdAuoFetchDataTypeId(String adAuoFetchDataTypeId) {
        this.adAuoFetchDataTypeId = adAuoFetchDataTypeId;
        return this;
    }

    /**
     * 刷新后选择1行。
     */
    public Boolean select1AftFth;

    /**
     * 获取：刷新后选择1行。
     */
    public Boolean getSelect1AftFth() {
        return this.select1AftFth;
    }

    /**
     * 设置：刷新后选择1行。
     */
    public AdSingleEntView setSelect1AftFth(Boolean select1AftFth) {
        this.select1AftFth = select1AftFth;
        return this;
    }

    /**
     * 多选类型。
     */
    public String adMultiSelectTypeId;

    /**
     * 获取：多选类型。
     */
    public String getAdMultiSelectTypeId() {
        return this.adMultiSelectTypeId;
    }

    /**
     * 设置：多选类型。
     */
    public AdSingleEntView setAdMultiSelectTypeId(String adMultiSelectTypeId) {
        this.adMultiSelectTypeId = adMultiSelectTypeId;
        return this;
    }

    /**
     * 状态列显示类型。
     */
    public String signalColsDspType;

    /**
     * 获取：状态列显示类型。
     */
    public String getSignalColsDspType() {
        return this.signalColsDspType;
    }

    /**
     * 设置：状态列显示类型。
     */
    public AdSingleEntView setSignalColsDspType(String signalColsDspType) {
        this.signalColsDspType = signalColsDspType;
        return this;
    }

    /**
     * 折行类型。
     */
    public String adWrapTypeId;

    /**
     * 获取：折行类型。
     */
    public String getAdWrapTypeId() {
        return this.adWrapTypeId;
    }

    /**
     * 设置：折行类型。
     */
    public AdSingleEntView setAdWrapTypeId(String adWrapTypeId) {
        this.adWrapTypeId = adWrapTypeId;
        return this;
    }

    /**
     * 显示过滤行。
     */
    public Boolean showFilterRow;

    /**
     * 获取：显示过滤行。
     */
    public Boolean getShowFilterRow() {
        return this.showFilterRow;
    }

    /**
     * 设置：显示过滤行。
     */
    public AdSingleEntView setShowFilterRow(Boolean showFilterRow) {
        this.showFilterRow = showFilterRow;
        return this;
    }

    /**
     * 隐藏标题。
     */
    public Boolean hideTitle;

    /**
     * 获取：隐藏标题。
     */
    public Boolean getHideTitle() {
        return this.hideTitle;
    }

    /**
     * 设置：隐藏标题。
     */
    public AdSingleEntView setHideTitle(Boolean hideTitle) {
        this.hideTitle = hideTitle;
        return this;
    }

    /**
     * 隐藏操作条。
     */
    public Boolean hideActStrp;

    /**
     * 获取：隐藏操作条。
     */
    public Boolean getHideActStrp() {
        return this.hideActStrp;
    }

    /**
     * 设置：隐藏操作条。
     */
    public AdSingleEntView setHideActStrp(Boolean hideActStrp) {
        this.hideActStrp = hideActStrp;
        return this;
    }

    /**
     * 分页条显示类型。
     */
    public String adPageStripDspTypeId;

    /**
     * 获取：分页条显示类型。
     */
    public String getAdPageStripDspTypeId() {
        return this.adPageStripDspTypeId;
    }

    /**
     * 设置：分页条显示类型。
     */
    public AdSingleEntView setAdPageStripDspTypeId(String adPageStripDspTypeId) {
        this.adPageStripDspTypeId = adPageStripDspTypeId;
        return this;
    }

    /**
     * 隐藏列头。
     */
    public Boolean hideHeader;

    /**
     * 获取：隐藏列头。
     */
    public Boolean getHideHeader() {
        return this.hideHeader;
    }

    /**
     * 设置：隐藏列头。
     */
    public AdSingleEntView setHideHeader(Boolean hideHeader) {
        this.hideHeader = hideHeader;
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
    public AdSingleEntView setNoStdInsert(Boolean noStdInsert) {
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
    public AdSingleEntView setNoStdUpdate(Boolean noStdUpdate) {
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
    public AdSingleEntView setNoStdDelete(Boolean noStdDelete) {
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
    public AdSingleEntView setStdVerChk(Boolean stdVerChk) {
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
    public AdSingleEntView setIgnoreAutoSave(Boolean ignoreAutoSave) {
        this.ignoreAutoSave = ignoreAutoSave;
        return this;
    }

    /**
     * 根据数据导航。
     */
    public Boolean navByData;

    /**
     * 获取：根据数据导航。
     */
    public Boolean getNavByData() {
        return this.navByData;
    }

    /**
     * 设置：根据数据导航。
     */
    public AdSingleEntView setNavByData(Boolean navByData) {
        this.navByData = navByData;
        return this;
    }

    /**
     * 实体记录新建位置。
     */
    public String adErCrtPosId;

    /**
     * 获取：实体记录新建位置。
     */
    public String getAdErCrtPosId() {
        return this.adErCrtPosId;
    }

    /**
     * 设置：实体记录新建位置。
     */
    public AdSingleEntView setAdErCrtPosId(String adErCrtPosId) {
        this.adErCrtPosId = adErCrtPosId;
        return this;
    }

    /**
     * 无法新建时消息逻辑。
     */
    public String notCreatableMsgLogic;

    /**
     * 获取：无法新建时消息逻辑。
     */
    public String getNotCreatableMsgLogic() {
        return this.notCreatableMsgLogic;
    }

    /**
     * 设置：无法新建时消息逻辑。
     */
    public AdSingleEntView setNotCreatableMsgLogic(String notCreatableMsgLogic) {
        this.notCreatableMsgLogic = notCreatableMsgLogic;
        return this;
    }

    /**
     * 改变时过滤。
     */
    public Boolean filterOnChange;

    /**
     * 获取：改变时过滤。
     */
    public Boolean getFilterOnChange() {
        return this.filterOnChange;
    }

    /**
     * 设置：改变时过滤。
     */
    public AdSingleEntView setFilterOnChange(Boolean filterOnChange) {
        this.filterOnChange = filterOnChange;
        return this;
    }

    /**
     * 显示过滤按钮。
     */
    public Boolean showFilterBtn;

    /**
     * 获取：显示过滤按钮。
     */
    public Boolean getShowFilterBtn() {
        return this.showFilterBtn;
    }

    /**
     * 设置：显示过滤按钮。
     */
    public AdSingleEntView setShowFilterBtn(Boolean showFilterBtn) {
        this.showFilterBtn = showFilterBtn;
        return this;
    }

    /**
     * 显示过滤重置按钮。
     */
    public Boolean showFilterResetBtn;

    /**
     * 获取：显示过滤重置按钮。
     */
    public Boolean getShowFilterResetBtn() {
        return this.showFilterResetBtn;
    }

    /**
     * 设置：显示过滤重置按钮。
     */
    public AdSingleEntView setShowFilterResetBtn(Boolean showFilterResetBtn) {
        this.showFilterResetBtn = showFilterResetBtn;
        return this;
    }

    /**
     * 启用样式文本。
     */
    public Boolean enableStyleText;

    /**
     * 获取：启用样式文本。
     */
    public Boolean getEnableStyleText() {
        return this.enableStyleText;
    }

    /**
     * 设置：启用样式文本。
     */
    public AdSingleEntView setEnableStyleText(Boolean enableStyleText) {
        this.enableStyleText = enableStyleText;
        return this;
    }

    /**
     * 奇行单元格样式文本逻辑。
     */
    public String oddRowCellStLogic;

    /**
     * 获取：奇行单元格样式文本逻辑。
     */
    public String getOddRowCellStLogic() {
        return this.oddRowCellStLogic;
    }

    /**
     * 设置：奇行单元格样式文本逻辑。
     */
    public AdSingleEntView setOddRowCellStLogic(String oddRowCellStLogic) {
        this.oddRowCellStLogic = oddRowCellStLogic;
        return this;
    }

    /**
     * 偶行单元格样式文本逻辑。
     */
    public String evenRowCellStLogic;

    /**
     * 获取：偶行单元格样式文本逻辑。
     */
    public String getEvenRowCellStLogic() {
        return this.evenRowCellStLogic;
    }

    /**
     * 设置：偶行单元格样式文本逻辑。
     */
    public AdSingleEntView setEvenRowCellStLogic(String evenRowCellStLogic) {
        this.evenRowCellStLogic = evenRowCellStLogic;
        return this;
    }

    /**
     * 每次查询最大行数。
     */
    public Integer maxRowCountPerQuery;

    /**
     * 获取：每次查询最大行数。
     */
    public Integer getMaxRowCountPerQuery() {
        return this.maxRowCountPerQuery;
    }

    /**
     * 设置：每次查询最大行数。
     */
    public AdSingleEntView setMaxRowCountPerQuery(Integer maxRowCountPerQuery) {
        this.maxRowCountPerQuery = maxRowCountPerQuery;
        return this;
    }

    /**
     * 移动端单行操作数量。
     */
    public Integer mobActCtInLine;

    /**
     * 获取：移动端单行操作数量。
     */
    public Integer getMobActCtInLine() {
        return this.mobActCtInLine;
    }

    /**
     * 设置：移动端单行操作数量。
     */
    public AdSingleEntView setMobActCtInLine(Integer mobActCtInLine) {
        this.mobActCtInLine = mobActCtInLine;
        return this;
    }

    /**
     * 展开的树层级。
     */
    public Integer expandedTreeLevel;

    /**
     * 获取：展开的树层级。
     */
    public Integer getExpandedTreeLevel() {
        return this.expandedTreeLevel;
    }

    /**
     * 设置：展开的树层级。
     */
    public AdSingleEntView setExpandedTreeLevel(Integer expandedTreeLevel) {
        this.expandedTreeLevel = expandedTreeLevel;
        return this;
    }

    /**
     * 树图标属性。
     */
    public String treeIconAttId;

    /**
     * 获取：树图标属性。
     */
    public String getTreeIconAttId() {
        return this.treeIconAttId;
    }

    /**
     * 设置：树图标属性。
     */
    public AdSingleEntView setTreeIconAttId(String treeIconAttId) {
        this.treeIconAttId = treeIconAttId;
        return this;
    }

    /**
     * 树图标字段宽度。
     */
    public Integer treeIconFieldWidth;

    /**
     * 获取：树图标字段宽度。
     */
    public Integer getTreeIconFieldWidth() {
        return this.treeIconFieldWidth;
    }

    /**
     * 设置：树图标字段宽度。
     */
    public AdSingleEntView setTreeIconFieldWidth(Integer treeIconFieldWidth) {
        this.treeIconFieldWidth = treeIconFieldWidth;
        return this;
    }

    /**
     * 客户端操作收缩。
     */
    public Boolean clientActCollapsed;

    /**
     * 获取：客户端操作收缩。
     */
    public Boolean getClientActCollapsed() {
        return this.clientActCollapsed;
    }

    /**
     * 设置：客户端操作收缩。
     */
    public AdSingleEntView setClientActCollapsed(Boolean clientActCollapsed) {
        this.clientActCollapsed = clientActCollapsed;
        return this;
    }

    /**
     * 权限检查。
     */
    public Boolean aclChk;

    /**
     * 获取：权限检查。
     */
    public Boolean getAclChk() {
        return this.aclChk;
    }

    /**
     * 设置：权限检查。
     */
    public AdSingleEntView setAclChk(Boolean aclChk) {
        this.aclChk = aclChk;
        return this;
    }

    /**
     * 显示行号。
     */
    public Boolean showRownum;

    /**
     * 获取：显示行号。
     */
    public Boolean getShowRownum() {
        return this.showRownum;
    }

    /**
     * 设置：显示行号。
     */
    public AdSingleEntView setShowRownum(Boolean showRownum) {
        this.showRownum = showRownum;
        return this;
    }

    /**
     * 操作最大权限级别。
     */
    public Integer actMaxAuthLvl;

    /**
     * 获取：操作最大权限级别。
     */
    public Integer getActMaxAuthLvl() {
        return this.actMaxAuthLvl;
    }

    /**
     * 设置：操作最大权限级别。
     */
    public AdSingleEntView setActMaxAuthLvl(Integer actMaxAuthLvl) {
        this.actMaxAuthLvl = actMaxAuthLvl;
        return this;
    }

    /**
     * 快捷搜索启用。
     */
    public Boolean fastSearchEnabled;

    /**
     * 获取：快捷搜索启用。
     */
    public Boolean getFastSearchEnabled() {
        return this.fastSearchEnabled;
    }

    /**
     * 设置：快捷搜索启用。
     */
    public AdSingleEntView setFastSearchEnabled(Boolean fastSearchEnabled) {
        this.fastSearchEnabled = fastSearchEnabled;
        return this;
    }

    /**
     * 快捷搜索提示。
     */
    public String fastSearchHint;

    /**
     * 获取：快捷搜索提示。
     */
    public String getFastSearchHint() {
        return this.fastSearchHint;
    }

    /**
     * 设置：快捷搜索提示。
     */
    public AdSingleEntView setFastSearchHint(String fastSearchHint) {
        this.fastSearchHint = fastSearchHint;
        return this;
    }

    /**
     * 快捷搜索过滤语句。
     */
    public String fastSearchWhereClause;

    /**
     * 获取：快捷搜索过滤语句。
     */
    public String getFastSearchWhereClause() {
        return this.fastSearchWhereClause;
    }

    /**
     * 设置：快捷搜索过滤语句。
     */
    public AdSingleEntView setFastSearchWhereClause(String fastSearchWhereClause) {
        this.fastSearchWhereClause = fastSearchWhereClause;
        return this;
    }

    /**
     * 实体视图编辑模式。
     */
    public String adSevEditModeId;

    /**
     * 获取：实体视图编辑模式。
     */
    public String getAdSevEditModeId() {
        return this.adSevEditModeId;
    }

    /**
     * 设置：实体视图编辑模式。
     */
    public AdSingleEntView setAdSevEditModeId(String adSevEditModeId) {
        this.adSevEditModeId = adSevEditModeId;
        return this;
    }

    /**
     * 明细窗口宽度。
     */
    public String dtlWindowWidth;

    /**
     * 获取：明细窗口宽度。
     */
    public String getDtlWindowWidth() {
        return this.dtlWindowWidth;
    }

    /**
     * 设置：明细窗口宽度。
     */
    public AdSingleEntView setDtlWindowWidth(String dtlWindowWidth) {
        this.dtlWindowWidth = dtlWindowWidth;
        return this;
    }

    /**
     * 明细窗口高度。
     */
    public String dtlWindowHeight;

    /**
     * 获取：明细窗口高度。
     */
    public String getDtlWindowHeight() {
        return this.dtlWindowHeight;
    }

    /**
     * 设置：明细窗口高度。
     */
    public AdSingleEntView setDtlWindowHeight(String dtlWindowHeight) {
        this.dtlWindowHeight = dtlWindowHeight;
        return this;
    }

    /**
     * 明细窗口视图部分。
     */
    public String dtlWindowVpId;

    /**
     * 获取：明细窗口视图部分。
     */
    public String getDtlWindowVpId() {
        return this.dtlWindowVpId;
    }

    /**
     * 设置：明细窗口视图部分。
     */
    public AdSingleEntView setDtlWindowVpId(String dtlWindowVpId) {
        this.dtlWindowVpId = dtlWindowVpId;
        return this;
    }

    /**
     * 明细窗口启用流程。
     */
    public Boolean dtlWindowWfEnabled;

    /**
     * 获取：明细窗口启用流程。
     */
    public Boolean getDtlWindowWfEnabled() {
        return this.dtlWindowWfEnabled;
    }

    /**
     * 设置：明细窗口启用流程。
     */
    public AdSingleEntView setDtlWindowWfEnabled(Boolean dtlWindowWfEnabled) {
        this.dtlWindowWfEnabled = dtlWindowWfEnabled;
        return this;
    }

    /**
     * 表单宽度。
     */
    public String formWidth;

    /**
     * 获取：表单宽度。
     */
    public String getFormWidth() {
        return this.formWidth;
    }

    /**
     * 设置：表单宽度。
     */
    public AdSingleEntView setFormWidth(String formWidth) {
        this.formWidth = formWidth;
        return this;
    }

    /**
     * 表单列数。
     */
    public Integer formCols;

    /**
     * 获取：表单列数。
     */
    public Integer getFormCols() {
        return this.formCols;
    }

    /**
     * 设置：表单列数。
     */
    public AdSingleEntView setFormCols(Integer formCols) {
        this.formCols = formCols;
        return this;
    }

    /**
     * 表单各列宽度。
     */
    public String formColWidths;

    /**
     * 获取：表单各列宽度。
     */
    public String getFormColWidths() {
        return this.formColWidths;
    }

    /**
     * 设置：表单各列宽度。
     */
    public AdSingleEntView setFormColWidths(String formColWidths) {
        this.formColWidths = formColWidths;
        return this;
    }

    /**
     * 表单单元格边框。
     */
    public Integer formCellBorder;

    /**
     * 获取：表单单元格边框。
     */
    public Integer getFormCellBorder() {
        return this.formCellBorder;
    }

    /**
     * 设置：表单单元格边框。
     */
    public AdSingleEntView setFormCellBorder(Integer formCellBorder) {
        this.formCellBorder = formCellBorder;
        return this;
    }

    /**
     * 表单单元格填充。
     */
    public Integer formCellPadding;

    /**
     * 获取：表单单元格填充。
     */
    public Integer getFormCellPadding() {
        return this.formCellPadding;
    }

    /**
     * 设置：表单单元格填充。
     */
    public AdSingleEntView setFormCellPadding(Integer formCellPadding) {
        this.formCellPadding = formCellPadding;
        return this;
    }

    /**
     * 批量修改窗口宽度。
     */
    public String batchModiWindowWidth;

    /**
     * 获取：批量修改窗口宽度。
     */
    public String getBatchModiWindowWidth() {
        return this.batchModiWindowWidth;
    }

    /**
     * 设置：批量修改窗口宽度。
     */
    public AdSingleEntView setBatchModiWindowWidth(String batchModiWindowWidth) {
        this.batchModiWindowWidth = batchModiWindowWidth;
        return this;
    }

    /**
     * 批量修改窗口高度。
     */
    public String batchModiWindowHeight;

    /**
     * 获取：批量修改窗口高度。
     */
    public String getBatchModiWindowHeight() {
        return this.batchModiWindowHeight;
    }

    /**
     * 设置：批量修改窗口高度。
     */
    public AdSingleEntView setBatchModiWindowHeight(String batchModiWindowHeight) {
        this.batchModiWindowHeight = batchModiWindowHeight;
        return this;
    }

    /**
     * 启用选择相关操作。
     */
    public Boolean enableSelectAwareAct;

    /**
     * 获取：启用选择相关操作。
     */
    public Boolean getEnableSelectAwareAct() {
        return this.enableSelectAwareAct;
    }

    /**
     * 设置：启用选择相关操作。
     */
    public AdSingleEntView setEnableSelectAwareAct(Boolean enableSelectAwareAct) {
        this.enableSelectAwareAct = enableSelectAwareAct;
        return this;
    }

    /**
     * 启用显示文本。
     */
    public Boolean enableDspText;

    /**
     * 获取：启用显示文本。
     */
    public Boolean getEnableDspText() {
        return this.enableDspText;
    }

    /**
     * 设置：启用显示文本。
     */
    public AdSingleEntView setEnableDspText(Boolean enableDspText) {
        this.enableDspText = enableDspText;
        return this;
    }

    /**
     * FLEX开始样式。
     */
    public String flexBeginStyle;

    /**
     * 获取：FLEX开始样式。
     */
    public String getFlexBeginStyle() {
        return this.flexBeginStyle;
    }

    /**
     * 设置：FLEX开始样式。
     */
    public AdSingleEntView setFlexBeginStyle(String flexBeginStyle) {
        this.flexBeginStyle = flexBeginStyle;
        return this;
    }

    /**
     * FLEX记录样式。
     */
    public String flexRecStyle;

    /**
     * 获取：FLEX记录样式。
     */
    public String getFlexRecStyle() {
        return this.flexRecStyle;
    }

    /**
     * 设置：FLEX记录样式。
     */
    public AdSingleEntView setFlexRecStyle(String flexRecStyle) {
        this.flexRecStyle = flexRecStyle;
        return this;
    }

    /**
     * FLEX分隔样式。
     */
    public String flexSplitStyle;

    /**
     * 获取：FLEX分隔样式。
     */
    public String getFlexSplitStyle() {
        return this.flexSplitStyle;
    }

    /**
     * 设置：FLEX分隔样式。
     */
    public AdSingleEntView setFlexSplitStyle(String flexSplitStyle) {
        this.flexSplitStyle = flexSplitStyle;
        return this;
    }

    /**
     * FLEX结束样式。
     */
    public String flexEndStyle;

    /**
     * 获取：FLEX结束样式。
     */
    public String getFlexEndStyle() {
        return this.flexEndStyle;
    }

    /**
     * 设置：FLEX结束样式。
     */
    public AdSingleEntView setFlexEndStyle(String flexEndStyle) {
        this.flexEndStyle = flexEndStyle;
        return this;
    }

    // </editor-fold>

    // 实例方法：
    // <editor-fold>

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
     * 插入数据。
     *
     * @return
     */
    public static AdSingleEntView insertData() {
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
    public static AdSingleEntView selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<AdSingleEntView> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<AdSingleEntView> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
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
    public static void copyCols(AdSingleEntView fromModel, AdSingleEntView toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}