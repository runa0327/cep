package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    /**
     * 待更新的列。
     */
    private List<String> toUpdateCols = new ArrayList<>();

    /**
     * 清除待更新的列。
     */
    public void clearToUpdateCols() {
        this.toUpdateCols.clear();
    }

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
         * 权限控制。
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
    private String id;

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
        if (this.id == null && id == null) {
            // 均为null，不做处理。
        } else if (this.id != null && id != null) {
            // 均非null，判定不等，再做处理：
            if (this.id.compareTo(id) != 0) {
                this.id = id;
                if (!this.toUpdateCols.contains("ID")) {
                    this.toUpdateCols.add("ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.id = id;
            if (!this.toUpdateCols.contains("ID")) {
                this.toUpdateCols.add("ID");
            }
        }
        return this;
    }

    /**
     * 版本。
     */
    private Integer ver;

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
        if (this.ver == null && ver == null) {
            // 均为null，不做处理。
        } else if (this.ver != null && ver != null) {
            // 均非null，判定不等，再做处理：
            if (this.ver.compareTo(ver) != 0) {
                this.ver = ver;
                if (!this.toUpdateCols.contains("VER")) {
                    this.toUpdateCols.add("VER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ver = ver;
            if (!this.toUpdateCols.contains("VER")) {
                this.toUpdateCols.add("VER");
            }
        }
        return this;
    }

    /**
     * 时间戳。
     */
    private LocalDateTime ts;

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
        if (this.ts == null && ts == null) {
            // 均为null，不做处理。
        } else if (this.ts != null && ts != null) {
            // 均非null，判定不等，再做处理：
            if (this.ts.compareTo(ts) != 0) {
                this.ts = ts;
                if (!this.toUpdateCols.contains("TS")) {
                    this.toUpdateCols.add("TS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ts = ts;
            if (!this.toUpdateCols.contains("TS")) {
                this.toUpdateCols.add("TS");
            }
        }
        return this;
    }

    /**
     * 是否预设。
     */
    private Boolean isPreset;

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
        if (this.isPreset == null && isPreset == null) {
            // 均为null，不做处理。
        } else if (this.isPreset != null && isPreset != null) {
            // 均非null，判定不等，再做处理：
            if (this.isPreset.compareTo(isPreset) != 0) {
                this.isPreset = isPreset;
                if (!this.toUpdateCols.contains("IS_PRESET")) {
                    this.toUpdateCols.add("IS_PRESET");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isPreset = isPreset;
            if (!this.toUpdateCols.contains("IS_PRESET")) {
                this.toUpdateCols.add("IS_PRESET");
            }
        }
        return this;
    }

    /**
     * 创建日期时间。
     */
    private LocalDateTime crtDt;

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
        if (this.crtDt == null && crtDt == null) {
            // 均为null，不做处理。
        } else if (this.crtDt != null && crtDt != null) {
            // 均非null，判定不等，再做处理：
            if (this.crtDt.compareTo(crtDt) != 0) {
                this.crtDt = crtDt;
                if (!this.toUpdateCols.contains("CRT_DT")) {
                    this.toUpdateCols.add("CRT_DT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.crtDt = crtDt;
            if (!this.toUpdateCols.contains("CRT_DT")) {
                this.toUpdateCols.add("CRT_DT");
            }
        }
        return this;
    }

    /**
     * 创建用户。
     */
    private String crtUserId;

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
        if (this.crtUserId == null && crtUserId == null) {
            // 均为null，不做处理。
        } else if (this.crtUserId != null && crtUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.crtUserId.compareTo(crtUserId) != 0) {
                this.crtUserId = crtUserId;
                if (!this.toUpdateCols.contains("CRT_USER_ID")) {
                    this.toUpdateCols.add("CRT_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.crtUserId = crtUserId;
            if (!this.toUpdateCols.contains("CRT_USER_ID")) {
                this.toUpdateCols.add("CRT_USER_ID");
            }
        }
        return this;
    }

    /**
     * 最后修改日期时间。
     */
    private LocalDateTime lastModiDt;

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
        if (this.lastModiDt == null && lastModiDt == null) {
            // 均为null，不做处理。
        } else if (this.lastModiDt != null && lastModiDt != null) {
            // 均非null，判定不等，再做处理：
            if (this.lastModiDt.compareTo(lastModiDt) != 0) {
                this.lastModiDt = lastModiDt;
                if (!this.toUpdateCols.contains("LAST_MODI_DT")) {
                    this.toUpdateCols.add("LAST_MODI_DT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.lastModiDt = lastModiDt;
            if (!this.toUpdateCols.contains("LAST_MODI_DT")) {
                this.toUpdateCols.add("LAST_MODI_DT");
            }
        }
        return this;
    }

    /**
     * 最后修改用户。
     */
    private String lastModiUserId;

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
        if (this.lastModiUserId == null && lastModiUserId == null) {
            // 均为null，不做处理。
        } else if (this.lastModiUserId != null && lastModiUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.lastModiUserId.compareTo(lastModiUserId) != 0) {
                this.lastModiUserId = lastModiUserId;
                if (!this.toUpdateCols.contains("LAST_MODI_USER_ID")) {
                    this.toUpdateCols.add("LAST_MODI_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.lastModiUserId = lastModiUserId;
            if (!this.toUpdateCols.contains("LAST_MODI_USER_ID")) {
                this.toUpdateCols.add("LAST_MODI_USER_ID");
            }
        }
        return this;
    }

    /**
     * 记录状态。
     */
    private String status;

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
        if (this.status == null && status == null) {
            // 均为null，不做处理。
        } else if (this.status != null && status != null) {
            // 均非null，判定不等，再做处理：
            if (this.status.compareTo(status) != 0) {
                this.status = status;
                if (!this.toUpdateCols.contains("STATUS")) {
                    this.toUpdateCols.add("STATUS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.status = status;
            if (!this.toUpdateCols.contains("STATUS")) {
                this.toUpdateCols.add("STATUS");
            }
        }
        return this;
    }

    /**
     * 锁定流程实例。
     */
    private String lkWfInstId;

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
        if (this.lkWfInstId == null && lkWfInstId == null) {
            // 均为null，不做处理。
        } else if (this.lkWfInstId != null && lkWfInstId != null) {
            // 均非null，判定不等，再做处理：
            if (this.lkWfInstId.compareTo(lkWfInstId) != 0) {
                this.lkWfInstId = lkWfInstId;
                if (!this.toUpdateCols.contains("LK_WF_INST_ID")) {
                    this.toUpdateCols.add("LK_WF_INST_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.lkWfInstId = lkWfInstId;
            if (!this.toUpdateCols.contains("LK_WF_INST_ID")) {
                this.toUpdateCols.add("LK_WF_INST_ID");
            }
        }
        return this;
    }

    /**
     * 代码。
     */
    private String code;

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
        if (this.code == null && code == null) {
            // 均为null，不做处理。
        } else if (this.code != null && code != null) {
            // 均非null，判定不等，再做处理：
            if (this.code.compareTo(code) != 0) {
                this.code = code;
                if (!this.toUpdateCols.contains("CODE")) {
                    this.toUpdateCols.add("CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.code = code;
            if (!this.toUpdateCols.contains("CODE")) {
                this.toUpdateCols.add("CODE");
            }
        }
        return this;
    }

    /**
     * 名称。
     */
    private String name;

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
        if (this.name == null && name == null) {
            // 均为null，不做处理。
        } else if (this.name != null && name != null) {
            // 均非null，判定不等，再做处理：
            if (this.name.compareTo(name) != 0) {
                this.name = name;
                if (!this.toUpdateCols.contains("NAME")) {
                    this.toUpdateCols.add("NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.name = name;
            if (!this.toUpdateCols.contains("NAME")) {
                this.toUpdateCols.add("NAME");
            }
        }
        return this;
    }

    /**
     * 备注。
     */
    private String remark;

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
        if (this.remark == null && remark == null) {
            // 均为null，不做处理。
        } else if (this.remark != null && remark != null) {
            // 均非null，判定不等，再做处理：
            if (this.remark.compareTo(remark) != 0) {
                this.remark = remark;
                if (!this.toUpdateCols.contains("REMARK")) {
                    this.toUpdateCols.add("REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remark = remark;
            if (!this.toUpdateCols.contains("REMARK")) {
                this.toUpdateCols.add("REMARK");
            }
        }
        return this;
    }

    /**
     * 实体视图显示类型。
     */
    private String sevDisplayType;

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
        if (this.sevDisplayType == null && sevDisplayType == null) {
            // 均为null，不做处理。
        } else if (this.sevDisplayType != null && sevDisplayType != null) {
            // 均非null，判定不等，再做处理：
            if (this.sevDisplayType.compareTo(sevDisplayType) != 0) {
                this.sevDisplayType = sevDisplayType;
                if (!this.toUpdateCols.contains("SEV_DISPLAY_TYPE")) {
                    this.toUpdateCols.add("SEV_DISPLAY_TYPE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.sevDisplayType = sevDisplayType;
            if (!this.toUpdateCols.contains("SEV_DISPLAY_TYPE")) {
                this.toUpdateCols.add("SEV_DISPLAY_TYPE");
            }
        }
        return this;
    }

    /**
     * 实体。
     */
    private String adEntId;

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
        if (this.adEntId == null && adEntId == null) {
            // 均为null，不做处理。
        } else if (this.adEntId != null && adEntId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adEntId.compareTo(adEntId) != 0) {
                this.adEntId = adEntId;
                if (!this.toUpdateCols.contains("AD_ENT_ID")) {
                    this.toUpdateCols.add("AD_ENT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adEntId = adEntId;
            if (!this.toUpdateCols.contains("AD_ENT_ID")) {
                this.toUpdateCols.add("AD_ENT_ID");
            }
        }
        return this;
    }

    /**
     * 表连接语句。
     */
    private String joinOnClause;

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
        if (this.joinOnClause == null && joinOnClause == null) {
            // 均为null，不做处理。
        } else if (this.joinOnClause != null && joinOnClause != null) {
            // 均非null，判定不等，再做处理：
            if (this.joinOnClause.compareTo(joinOnClause) != 0) {
                this.joinOnClause = joinOnClause;
                if (!this.toUpdateCols.contains("JOIN_ON_CLAUSE")) {
                    this.toUpdateCols.add("JOIN_ON_CLAUSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.joinOnClause = joinOnClause;
            if (!this.toUpdateCols.contains("JOIN_ON_CLAUSE")) {
                this.toUpdateCols.add("JOIN_ON_CLAUSE");
            }
        }
        return this;
    }

    /**
     * 排序语句。
     */
    private String orderByClause;

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
        if (this.orderByClause == null && orderByClause == null) {
            // 均为null，不做处理。
        } else if (this.orderByClause != null && orderByClause != null) {
            // 均非null，判定不等，再做处理：
            if (this.orderByClause.compareTo(orderByClause) != 0) {
                this.orderByClause = orderByClause;
                if (!this.toUpdateCols.contains("ORDER_BY_CLAUSE")) {
                    this.toUpdateCols.add("ORDER_BY_CLAUSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.orderByClause = orderByClause;
            if (!this.toUpdateCols.contains("ORDER_BY_CLAUSE")) {
                this.toUpdateCols.add("ORDER_BY_CLAUSE");
            }
        }
        return this;
    }

    /**
     * 过滤语句。
     */
    private String whereClause;

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
        if (this.whereClause == null && whereClause == null) {
            // 均为null，不做处理。
        } else if (this.whereClause != null && whereClause != null) {
            // 均非null，判定不等，再做处理：
            if (this.whereClause.compareTo(whereClause) != 0) {
                this.whereClause = whereClause;
                if (!this.toUpdateCols.contains("WHERE_CLAUSE")) {
                    this.toUpdateCols.add("WHERE_CLAUSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.whereClause = whereClause;
            if (!this.toUpdateCols.contains("WHERE_CLAUSE")) {
                this.toUpdateCols.add("WHERE_CLAUSE");
            }
        }
        return this;
    }

    /**
     * 分组语句。
     */
    private String groupByClause;

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
        if (this.groupByClause == null && groupByClause == null) {
            // 均为null，不做处理。
        } else if (this.groupByClause != null && groupByClause != null) {
            // 均非null，判定不等，再做处理：
            if (this.groupByClause.compareTo(groupByClause) != 0) {
                this.groupByClause = groupByClause;
                if (!this.toUpdateCols.contains("GROUP_BY_CLAUSE")) {
                    this.toUpdateCols.add("GROUP_BY_CLAUSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.groupByClause = groupByClause;
            if (!this.toUpdateCols.contains("GROUP_BY_CLAUSE")) {
                this.toUpdateCols.add("GROUP_BY_CLAUSE");
            }
        }
        return this;
    }

    /**
     * HAVING语句。
     */
    private String havingClause;

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
        if (this.havingClause == null && havingClause == null) {
            // 均为null，不做处理。
        } else if (this.havingClause != null && havingClause != null) {
            // 均非null，判定不等，再做处理：
            if (this.havingClause.compareTo(havingClause) != 0) {
                this.havingClause = havingClause;
                if (!this.toUpdateCols.contains("HAVING_CLAUSE")) {
                    this.toUpdateCols.add("HAVING_CLAUSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.havingClause = havingClause;
            if (!this.toUpdateCols.contains("HAVING_CLAUSE")) {
                this.toUpdateCols.add("HAVING_CLAUSE");
            }
        }
        return this;
    }

    /**
     * 页面大小。
     */
    private Integer pageSize;

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
        if (this.pageSize == null && pageSize == null) {
            // 均为null，不做处理。
        } else if (this.pageSize != null && pageSize != null) {
            // 均非null，判定不等，再做处理：
            if (this.pageSize.compareTo(pageSize) != 0) {
                this.pageSize = pageSize;
                if (!this.toUpdateCols.contains("PAGE_SIZE")) {
                    this.toUpdateCols.add("PAGE_SIZE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pageSize = pageSize;
            if (!this.toUpdateCols.contains("PAGE_SIZE")) {
                this.toUpdateCols.add("PAGE_SIZE");
            }
        }
        return this;
    }

    /**
     * 移动端页面大小。
     */
    private Integer mobPageSize;

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
        if (this.mobPageSize == null && mobPageSize == null) {
            // 均为null，不做处理。
        } else if (this.mobPageSize != null && mobPageSize != null) {
            // 均非null，判定不等，再做处理：
            if (this.mobPageSize.compareTo(mobPageSize) != 0) {
                this.mobPageSize = mobPageSize;
                if (!this.toUpdateCols.contains("MOB_PAGE_SIZE")) {
                    this.toUpdateCols.add("MOB_PAGE_SIZE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.mobPageSize = mobPageSize;
            if (!this.toUpdateCols.contains("MOB_PAGE_SIZE")) {
                this.toUpdateCols.add("MOB_PAGE_SIZE");
            }
        }
        return this;
    }

    /**
     * 树属性。
     */
    private String treeAttId;

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
        if (this.treeAttId == null && treeAttId == null) {
            // 均为null，不做处理。
        } else if (this.treeAttId != null && treeAttId != null) {
            // 均非null，判定不等，再做处理：
            if (this.treeAttId.compareTo(treeAttId) != 0) {
                this.treeAttId = treeAttId;
                if (!this.toUpdateCols.contains("TREE_ATT_ID")) {
                    this.toUpdateCols.add("TREE_ATT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.treeAttId = treeAttId;
            if (!this.toUpdateCols.contains("TREE_ATT_ID")) {
                this.toUpdateCols.add("TREE_ATT_ID");
            }
        }
        return this;
    }

    /**
     * 自动获取数据类型。
     */
    private String adAuoFetchDataTypeId;

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
        if (this.adAuoFetchDataTypeId == null && adAuoFetchDataTypeId == null) {
            // 均为null，不做处理。
        } else if (this.adAuoFetchDataTypeId != null && adAuoFetchDataTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adAuoFetchDataTypeId.compareTo(adAuoFetchDataTypeId) != 0) {
                this.adAuoFetchDataTypeId = adAuoFetchDataTypeId;
                if (!this.toUpdateCols.contains("AD_AUO_FETCH_DATA_TYPE_ID")) {
                    this.toUpdateCols.add("AD_AUO_FETCH_DATA_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adAuoFetchDataTypeId = adAuoFetchDataTypeId;
            if (!this.toUpdateCols.contains("AD_AUO_FETCH_DATA_TYPE_ID")) {
                this.toUpdateCols.add("AD_AUO_FETCH_DATA_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 刷新后选择1行。
     */
    private Boolean select1AftFth;

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
        if (this.select1AftFth == null && select1AftFth == null) {
            // 均为null，不做处理。
        } else if (this.select1AftFth != null && select1AftFth != null) {
            // 均非null，判定不等，再做处理：
            if (this.select1AftFth.compareTo(select1AftFth) != 0) {
                this.select1AftFth = select1AftFth;
                if (!this.toUpdateCols.contains("SELECT_1_AFT_FTH")) {
                    this.toUpdateCols.add("SELECT_1_AFT_FTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.select1AftFth = select1AftFth;
            if (!this.toUpdateCols.contains("SELECT_1_AFT_FTH")) {
                this.toUpdateCols.add("SELECT_1_AFT_FTH");
            }
        }
        return this;
    }

    /**
     * 多选类型。
     */
    private String adMultiSelectTypeId;

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
        if (this.adMultiSelectTypeId == null && adMultiSelectTypeId == null) {
            // 均为null，不做处理。
        } else if (this.adMultiSelectTypeId != null && adMultiSelectTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adMultiSelectTypeId.compareTo(adMultiSelectTypeId) != 0) {
                this.adMultiSelectTypeId = adMultiSelectTypeId;
                if (!this.toUpdateCols.contains("AD_MULTI_SELECT_TYPE_ID")) {
                    this.toUpdateCols.add("AD_MULTI_SELECT_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adMultiSelectTypeId = adMultiSelectTypeId;
            if (!this.toUpdateCols.contains("AD_MULTI_SELECT_TYPE_ID")) {
                this.toUpdateCols.add("AD_MULTI_SELECT_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 状态列显示类型。
     */
    private String signalColsDspType;

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
        if (this.signalColsDspType == null && signalColsDspType == null) {
            // 均为null，不做处理。
        } else if (this.signalColsDspType != null && signalColsDspType != null) {
            // 均非null，判定不等，再做处理：
            if (this.signalColsDspType.compareTo(signalColsDspType) != 0) {
                this.signalColsDspType = signalColsDspType;
                if (!this.toUpdateCols.contains("SIGNAL_COLS_DSP_TYPE")) {
                    this.toUpdateCols.add("SIGNAL_COLS_DSP_TYPE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.signalColsDspType = signalColsDspType;
            if (!this.toUpdateCols.contains("SIGNAL_COLS_DSP_TYPE")) {
                this.toUpdateCols.add("SIGNAL_COLS_DSP_TYPE");
            }
        }
        return this;
    }

    /**
     * 折行类型。
     */
    private String adWrapTypeId;

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
        if (this.adWrapTypeId == null && adWrapTypeId == null) {
            // 均为null，不做处理。
        } else if (this.adWrapTypeId != null && adWrapTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adWrapTypeId.compareTo(adWrapTypeId) != 0) {
                this.adWrapTypeId = adWrapTypeId;
                if (!this.toUpdateCols.contains("AD_WRAP_TYPE_ID")) {
                    this.toUpdateCols.add("AD_WRAP_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adWrapTypeId = adWrapTypeId;
            if (!this.toUpdateCols.contains("AD_WRAP_TYPE_ID")) {
                this.toUpdateCols.add("AD_WRAP_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 显示过滤行。
     */
    private Boolean showFilterRow;

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
        if (this.showFilterRow == null && showFilterRow == null) {
            // 均为null，不做处理。
        } else if (this.showFilterRow != null && showFilterRow != null) {
            // 均非null，判定不等，再做处理：
            if (this.showFilterRow.compareTo(showFilterRow) != 0) {
                this.showFilterRow = showFilterRow;
                if (!this.toUpdateCols.contains("SHOW_FILTER_ROW")) {
                    this.toUpdateCols.add("SHOW_FILTER_ROW");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.showFilterRow = showFilterRow;
            if (!this.toUpdateCols.contains("SHOW_FILTER_ROW")) {
                this.toUpdateCols.add("SHOW_FILTER_ROW");
            }
        }
        return this;
    }

    /**
     * 隐藏标题。
     */
    private Boolean hideTitle;

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
        if (this.hideTitle == null && hideTitle == null) {
            // 均为null，不做处理。
        } else if (this.hideTitle != null && hideTitle != null) {
            // 均非null，判定不等，再做处理：
            if (this.hideTitle.compareTo(hideTitle) != 0) {
                this.hideTitle = hideTitle;
                if (!this.toUpdateCols.contains("HIDE_TITLE")) {
                    this.toUpdateCols.add("HIDE_TITLE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hideTitle = hideTitle;
            if (!this.toUpdateCols.contains("HIDE_TITLE")) {
                this.toUpdateCols.add("HIDE_TITLE");
            }
        }
        return this;
    }

    /**
     * 隐藏操作条。
     */
    private Boolean hideActStrp;

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
        if (this.hideActStrp == null && hideActStrp == null) {
            // 均为null，不做处理。
        } else if (this.hideActStrp != null && hideActStrp != null) {
            // 均非null，判定不等，再做处理：
            if (this.hideActStrp.compareTo(hideActStrp) != 0) {
                this.hideActStrp = hideActStrp;
                if (!this.toUpdateCols.contains("HIDE_ACT_STRP")) {
                    this.toUpdateCols.add("HIDE_ACT_STRP");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hideActStrp = hideActStrp;
            if (!this.toUpdateCols.contains("HIDE_ACT_STRP")) {
                this.toUpdateCols.add("HIDE_ACT_STRP");
            }
        }
        return this;
    }

    /**
     * 分页条显示类型。
     */
    private String adPageStripDspTypeId;

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
        if (this.adPageStripDspTypeId == null && adPageStripDspTypeId == null) {
            // 均为null，不做处理。
        } else if (this.adPageStripDspTypeId != null && adPageStripDspTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adPageStripDspTypeId.compareTo(adPageStripDspTypeId) != 0) {
                this.adPageStripDspTypeId = adPageStripDspTypeId;
                if (!this.toUpdateCols.contains("AD_PAGE_STRIP_DSP_TYPE_ID")) {
                    this.toUpdateCols.add("AD_PAGE_STRIP_DSP_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adPageStripDspTypeId = adPageStripDspTypeId;
            if (!this.toUpdateCols.contains("AD_PAGE_STRIP_DSP_TYPE_ID")) {
                this.toUpdateCols.add("AD_PAGE_STRIP_DSP_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 隐藏列头。
     */
    private Boolean hideHeader;

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
        if (this.hideHeader == null && hideHeader == null) {
            // 均为null，不做处理。
        } else if (this.hideHeader != null && hideHeader != null) {
            // 均非null，判定不等，再做处理：
            if (this.hideHeader.compareTo(hideHeader) != 0) {
                this.hideHeader = hideHeader;
                if (!this.toUpdateCols.contains("HIDE_HEADER")) {
                    this.toUpdateCols.add("HIDE_HEADER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hideHeader = hideHeader;
            if (!this.toUpdateCols.contains("HIDE_HEADER")) {
                this.toUpdateCols.add("HIDE_HEADER");
            }
        }
        return this;
    }

    /**
     * 忽略标准新建。
     */
    private Boolean noStdInsert;

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
        if (this.noStdInsert == null && noStdInsert == null) {
            // 均为null，不做处理。
        } else if (this.noStdInsert != null && noStdInsert != null) {
            // 均非null，判定不等，再做处理：
            if (this.noStdInsert.compareTo(noStdInsert) != 0) {
                this.noStdInsert = noStdInsert;
                if (!this.toUpdateCols.contains("NO_STD_INSERT")) {
                    this.toUpdateCols.add("NO_STD_INSERT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.noStdInsert = noStdInsert;
            if (!this.toUpdateCols.contains("NO_STD_INSERT")) {
                this.toUpdateCols.add("NO_STD_INSERT");
            }
        }
        return this;
    }

    /**
     * 忽略标准更新。
     */
    private Boolean noStdUpdate;

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
        if (this.noStdUpdate == null && noStdUpdate == null) {
            // 均为null，不做处理。
        } else if (this.noStdUpdate != null && noStdUpdate != null) {
            // 均非null，判定不等，再做处理：
            if (this.noStdUpdate.compareTo(noStdUpdate) != 0) {
                this.noStdUpdate = noStdUpdate;
                if (!this.toUpdateCols.contains("NO_STD_UPDATE")) {
                    this.toUpdateCols.add("NO_STD_UPDATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.noStdUpdate = noStdUpdate;
            if (!this.toUpdateCols.contains("NO_STD_UPDATE")) {
                this.toUpdateCols.add("NO_STD_UPDATE");
            }
        }
        return this;
    }

    /**
     * 忽略标准删除。
     */
    private Boolean noStdDelete;

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
        if (this.noStdDelete == null && noStdDelete == null) {
            // 均为null，不做处理。
        } else if (this.noStdDelete != null && noStdDelete != null) {
            // 均非null，判定不等，再做处理：
            if (this.noStdDelete.compareTo(noStdDelete) != 0) {
                this.noStdDelete = noStdDelete;
                if (!this.toUpdateCols.contains("NO_STD_DELETE")) {
                    this.toUpdateCols.add("NO_STD_DELETE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.noStdDelete = noStdDelete;
            if (!this.toUpdateCols.contains("NO_STD_DELETE")) {
                this.toUpdateCols.add("NO_STD_DELETE");
            }
        }
        return this;
    }

    /**
     * 标准版本检查。
     */
    private Boolean stdVerChk;

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
        if (this.stdVerChk == null && stdVerChk == null) {
            // 均为null，不做处理。
        } else if (this.stdVerChk != null && stdVerChk != null) {
            // 均非null，判定不等，再做处理：
            if (this.stdVerChk.compareTo(stdVerChk) != 0) {
                this.stdVerChk = stdVerChk;
                if (!this.toUpdateCols.contains("STD_VER_CHK")) {
                    this.toUpdateCols.add("STD_VER_CHK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.stdVerChk = stdVerChk;
            if (!this.toUpdateCols.contains("STD_VER_CHK")) {
                this.toUpdateCols.add("STD_VER_CHK");
            }
        }
        return this;
    }

    /**
     * 忽略自动保存。
     */
    private Boolean ignoreAutoSave;

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
        if (this.ignoreAutoSave == null && ignoreAutoSave == null) {
            // 均为null，不做处理。
        } else if (this.ignoreAutoSave != null && ignoreAutoSave != null) {
            // 均非null，判定不等，再做处理：
            if (this.ignoreAutoSave.compareTo(ignoreAutoSave) != 0) {
                this.ignoreAutoSave = ignoreAutoSave;
                if (!this.toUpdateCols.contains("IGNORE_AUTO_SAVE")) {
                    this.toUpdateCols.add("IGNORE_AUTO_SAVE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ignoreAutoSave = ignoreAutoSave;
            if (!this.toUpdateCols.contains("IGNORE_AUTO_SAVE")) {
                this.toUpdateCols.add("IGNORE_AUTO_SAVE");
            }
        }
        return this;
    }

    /**
     * 根据数据导航。
     */
    private Boolean navByData;

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
        if (this.navByData == null && navByData == null) {
            // 均为null，不做处理。
        } else if (this.navByData != null && navByData != null) {
            // 均非null，判定不等，再做处理：
            if (this.navByData.compareTo(navByData) != 0) {
                this.navByData = navByData;
                if (!this.toUpdateCols.contains("NAV_BY_DATA")) {
                    this.toUpdateCols.add("NAV_BY_DATA");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.navByData = navByData;
            if (!this.toUpdateCols.contains("NAV_BY_DATA")) {
                this.toUpdateCols.add("NAV_BY_DATA");
            }
        }
        return this;
    }

    /**
     * 实体记录新建位置。
     */
    private String adErCrtPosId;

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
        if (this.adErCrtPosId == null && adErCrtPosId == null) {
            // 均为null，不做处理。
        } else if (this.adErCrtPosId != null && adErCrtPosId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adErCrtPosId.compareTo(adErCrtPosId) != 0) {
                this.adErCrtPosId = adErCrtPosId;
                if (!this.toUpdateCols.contains("AD_ER_CRT_POS_ID")) {
                    this.toUpdateCols.add("AD_ER_CRT_POS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adErCrtPosId = adErCrtPosId;
            if (!this.toUpdateCols.contains("AD_ER_CRT_POS_ID")) {
                this.toUpdateCols.add("AD_ER_CRT_POS_ID");
            }
        }
        return this;
    }

    /**
     * 无法新建时消息逻辑。
     */
    private String notCreatableMsgLogic;

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
        if (this.notCreatableMsgLogic == null && notCreatableMsgLogic == null) {
            // 均为null，不做处理。
        } else if (this.notCreatableMsgLogic != null && notCreatableMsgLogic != null) {
            // 均非null，判定不等，再做处理：
            if (this.notCreatableMsgLogic.compareTo(notCreatableMsgLogic) != 0) {
                this.notCreatableMsgLogic = notCreatableMsgLogic;
                if (!this.toUpdateCols.contains("NOT_CREATABLE_MSG_LOGIC")) {
                    this.toUpdateCols.add("NOT_CREATABLE_MSG_LOGIC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.notCreatableMsgLogic = notCreatableMsgLogic;
            if (!this.toUpdateCols.contains("NOT_CREATABLE_MSG_LOGIC")) {
                this.toUpdateCols.add("NOT_CREATABLE_MSG_LOGIC");
            }
        }
        return this;
    }

    /**
     * 改变时过滤。
     */
    private Boolean filterOnChange;

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
        if (this.filterOnChange == null && filterOnChange == null) {
            // 均为null，不做处理。
        } else if (this.filterOnChange != null && filterOnChange != null) {
            // 均非null，判定不等，再做处理：
            if (this.filterOnChange.compareTo(filterOnChange) != 0) {
                this.filterOnChange = filterOnChange;
                if (!this.toUpdateCols.contains("FILTER_ON_CHANGE")) {
                    this.toUpdateCols.add("FILTER_ON_CHANGE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.filterOnChange = filterOnChange;
            if (!this.toUpdateCols.contains("FILTER_ON_CHANGE")) {
                this.toUpdateCols.add("FILTER_ON_CHANGE");
            }
        }
        return this;
    }

    /**
     * 显示过滤按钮。
     */
    private Boolean showFilterBtn;

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
        if (this.showFilterBtn == null && showFilterBtn == null) {
            // 均为null，不做处理。
        } else if (this.showFilterBtn != null && showFilterBtn != null) {
            // 均非null，判定不等，再做处理：
            if (this.showFilterBtn.compareTo(showFilterBtn) != 0) {
                this.showFilterBtn = showFilterBtn;
                if (!this.toUpdateCols.contains("SHOW_FILTER_BTN")) {
                    this.toUpdateCols.add("SHOW_FILTER_BTN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.showFilterBtn = showFilterBtn;
            if (!this.toUpdateCols.contains("SHOW_FILTER_BTN")) {
                this.toUpdateCols.add("SHOW_FILTER_BTN");
            }
        }
        return this;
    }

    /**
     * 显示过滤重置按钮。
     */
    private Boolean showFilterResetBtn;

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
        if (this.showFilterResetBtn == null && showFilterResetBtn == null) {
            // 均为null，不做处理。
        } else if (this.showFilterResetBtn != null && showFilterResetBtn != null) {
            // 均非null，判定不等，再做处理：
            if (this.showFilterResetBtn.compareTo(showFilterResetBtn) != 0) {
                this.showFilterResetBtn = showFilterResetBtn;
                if (!this.toUpdateCols.contains("SHOW_FILTER_RESET_BTN")) {
                    this.toUpdateCols.add("SHOW_FILTER_RESET_BTN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.showFilterResetBtn = showFilterResetBtn;
            if (!this.toUpdateCols.contains("SHOW_FILTER_RESET_BTN")) {
                this.toUpdateCols.add("SHOW_FILTER_RESET_BTN");
            }
        }
        return this;
    }

    /**
     * 启用样式文本。
     */
    private Boolean enableStyleText;

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
        if (this.enableStyleText == null && enableStyleText == null) {
            // 均为null，不做处理。
        } else if (this.enableStyleText != null && enableStyleText != null) {
            // 均非null，判定不等，再做处理：
            if (this.enableStyleText.compareTo(enableStyleText) != 0) {
                this.enableStyleText = enableStyleText;
                if (!this.toUpdateCols.contains("ENABLE_STYLE_TEXT")) {
                    this.toUpdateCols.add("ENABLE_STYLE_TEXT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.enableStyleText = enableStyleText;
            if (!this.toUpdateCols.contains("ENABLE_STYLE_TEXT")) {
                this.toUpdateCols.add("ENABLE_STYLE_TEXT");
            }
        }
        return this;
    }

    /**
     * 奇行单元格样式文本逻辑。
     */
    private String oddRowCellStLogic;

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
        if (this.oddRowCellStLogic == null && oddRowCellStLogic == null) {
            // 均为null，不做处理。
        } else if (this.oddRowCellStLogic != null && oddRowCellStLogic != null) {
            // 均非null，判定不等，再做处理：
            if (this.oddRowCellStLogic.compareTo(oddRowCellStLogic) != 0) {
                this.oddRowCellStLogic = oddRowCellStLogic;
                if (!this.toUpdateCols.contains("ODD_ROW_CELL_ST_LOGIC")) {
                    this.toUpdateCols.add("ODD_ROW_CELL_ST_LOGIC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.oddRowCellStLogic = oddRowCellStLogic;
            if (!this.toUpdateCols.contains("ODD_ROW_CELL_ST_LOGIC")) {
                this.toUpdateCols.add("ODD_ROW_CELL_ST_LOGIC");
            }
        }
        return this;
    }

    /**
     * 偶行单元格样式文本逻辑。
     */
    private String evenRowCellStLogic;

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
        if (this.evenRowCellStLogic == null && evenRowCellStLogic == null) {
            // 均为null，不做处理。
        } else if (this.evenRowCellStLogic != null && evenRowCellStLogic != null) {
            // 均非null，判定不等，再做处理：
            if (this.evenRowCellStLogic.compareTo(evenRowCellStLogic) != 0) {
                this.evenRowCellStLogic = evenRowCellStLogic;
                if (!this.toUpdateCols.contains("EVEN_ROW_CELL_ST_LOGIC")) {
                    this.toUpdateCols.add("EVEN_ROW_CELL_ST_LOGIC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.evenRowCellStLogic = evenRowCellStLogic;
            if (!this.toUpdateCols.contains("EVEN_ROW_CELL_ST_LOGIC")) {
                this.toUpdateCols.add("EVEN_ROW_CELL_ST_LOGIC");
            }
        }
        return this;
    }

    /**
     * 每次查询最大行数。
     */
    private Integer maxRowCountPerQuery;

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
        if (this.maxRowCountPerQuery == null && maxRowCountPerQuery == null) {
            // 均为null，不做处理。
        } else if (this.maxRowCountPerQuery != null && maxRowCountPerQuery != null) {
            // 均非null，判定不等，再做处理：
            if (this.maxRowCountPerQuery.compareTo(maxRowCountPerQuery) != 0) {
                this.maxRowCountPerQuery = maxRowCountPerQuery;
                if (!this.toUpdateCols.contains("MAX_ROW_COUNT_PER_QUERY")) {
                    this.toUpdateCols.add("MAX_ROW_COUNT_PER_QUERY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.maxRowCountPerQuery = maxRowCountPerQuery;
            if (!this.toUpdateCols.contains("MAX_ROW_COUNT_PER_QUERY")) {
                this.toUpdateCols.add("MAX_ROW_COUNT_PER_QUERY");
            }
        }
        return this;
    }

    /**
     * 移动端单行操作数量。
     */
    private Integer mobActCtInLine;

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
        if (this.mobActCtInLine == null && mobActCtInLine == null) {
            // 均为null，不做处理。
        } else if (this.mobActCtInLine != null && mobActCtInLine != null) {
            // 均非null，判定不等，再做处理：
            if (this.mobActCtInLine.compareTo(mobActCtInLine) != 0) {
                this.mobActCtInLine = mobActCtInLine;
                if (!this.toUpdateCols.contains("MOB_ACT_CT_IN_LINE")) {
                    this.toUpdateCols.add("MOB_ACT_CT_IN_LINE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.mobActCtInLine = mobActCtInLine;
            if (!this.toUpdateCols.contains("MOB_ACT_CT_IN_LINE")) {
                this.toUpdateCols.add("MOB_ACT_CT_IN_LINE");
            }
        }
        return this;
    }

    /**
     * 展开的树层级。
     */
    private Integer expandedTreeLevel;

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
        if (this.expandedTreeLevel == null && expandedTreeLevel == null) {
            // 均为null，不做处理。
        } else if (this.expandedTreeLevel != null && expandedTreeLevel != null) {
            // 均非null，判定不等，再做处理：
            if (this.expandedTreeLevel.compareTo(expandedTreeLevel) != 0) {
                this.expandedTreeLevel = expandedTreeLevel;
                if (!this.toUpdateCols.contains("EXPANDED_TREE_LEVEL")) {
                    this.toUpdateCols.add("EXPANDED_TREE_LEVEL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.expandedTreeLevel = expandedTreeLevel;
            if (!this.toUpdateCols.contains("EXPANDED_TREE_LEVEL")) {
                this.toUpdateCols.add("EXPANDED_TREE_LEVEL");
            }
        }
        return this;
    }

    /**
     * 树图标属性。
     */
    private String treeIconAttId;

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
        if (this.treeIconAttId == null && treeIconAttId == null) {
            // 均为null，不做处理。
        } else if (this.treeIconAttId != null && treeIconAttId != null) {
            // 均非null，判定不等，再做处理：
            if (this.treeIconAttId.compareTo(treeIconAttId) != 0) {
                this.treeIconAttId = treeIconAttId;
                if (!this.toUpdateCols.contains("TREE_ICON_ATT_ID")) {
                    this.toUpdateCols.add("TREE_ICON_ATT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.treeIconAttId = treeIconAttId;
            if (!this.toUpdateCols.contains("TREE_ICON_ATT_ID")) {
                this.toUpdateCols.add("TREE_ICON_ATT_ID");
            }
        }
        return this;
    }

    /**
     * 树图标字段宽度。
     */
    private Integer treeIconFieldWidth;

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
        if (this.treeIconFieldWidth == null && treeIconFieldWidth == null) {
            // 均为null，不做处理。
        } else if (this.treeIconFieldWidth != null && treeIconFieldWidth != null) {
            // 均非null，判定不等，再做处理：
            if (this.treeIconFieldWidth.compareTo(treeIconFieldWidth) != 0) {
                this.treeIconFieldWidth = treeIconFieldWidth;
                if (!this.toUpdateCols.contains("TREE_ICON_FIELD_WIDTH")) {
                    this.toUpdateCols.add("TREE_ICON_FIELD_WIDTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.treeIconFieldWidth = treeIconFieldWidth;
            if (!this.toUpdateCols.contains("TREE_ICON_FIELD_WIDTH")) {
                this.toUpdateCols.add("TREE_ICON_FIELD_WIDTH");
            }
        }
        return this;
    }

    /**
     * 客户端操作收缩。
     */
    private Boolean clientActCollapsed;

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
        if (this.clientActCollapsed == null && clientActCollapsed == null) {
            // 均为null，不做处理。
        } else if (this.clientActCollapsed != null && clientActCollapsed != null) {
            // 均非null，判定不等，再做处理：
            if (this.clientActCollapsed.compareTo(clientActCollapsed) != 0) {
                this.clientActCollapsed = clientActCollapsed;
                if (!this.toUpdateCols.contains("CLIENT_ACT_COLLAPSED")) {
                    this.toUpdateCols.add("CLIENT_ACT_COLLAPSED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.clientActCollapsed = clientActCollapsed;
            if (!this.toUpdateCols.contains("CLIENT_ACT_COLLAPSED")) {
                this.toUpdateCols.add("CLIENT_ACT_COLLAPSED");
            }
        }
        return this;
    }

    /**
     * 权限控制。
     */
    private Boolean aclChk;

    /**
     * 获取：权限控制。
     */
    public Boolean getAclChk() {
        return this.aclChk;
    }

    /**
     * 设置：权限控制。
     */
    public AdSingleEntView setAclChk(Boolean aclChk) {
        if (this.aclChk == null && aclChk == null) {
            // 均为null，不做处理。
        } else if (this.aclChk != null && aclChk != null) {
            // 均非null，判定不等，再做处理：
            if (this.aclChk.compareTo(aclChk) != 0) {
                this.aclChk = aclChk;
                if (!this.toUpdateCols.contains("ACL_CHK")) {
                    this.toUpdateCols.add("ACL_CHK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.aclChk = aclChk;
            if (!this.toUpdateCols.contains("ACL_CHK")) {
                this.toUpdateCols.add("ACL_CHK");
            }
        }
        return this;
    }

    /**
     * 显示行号。
     */
    private Boolean showRownum;

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
        if (this.showRownum == null && showRownum == null) {
            // 均为null，不做处理。
        } else if (this.showRownum != null && showRownum != null) {
            // 均非null，判定不等，再做处理：
            if (this.showRownum.compareTo(showRownum) != 0) {
                this.showRownum = showRownum;
                if (!this.toUpdateCols.contains("SHOW_ROWNUM")) {
                    this.toUpdateCols.add("SHOW_ROWNUM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.showRownum = showRownum;
            if (!this.toUpdateCols.contains("SHOW_ROWNUM")) {
                this.toUpdateCols.add("SHOW_ROWNUM");
            }
        }
        return this;
    }

    /**
     * 操作最大权限级别。
     */
    private Integer actMaxAuthLvl;

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
        if (this.actMaxAuthLvl == null && actMaxAuthLvl == null) {
            // 均为null，不做处理。
        } else if (this.actMaxAuthLvl != null && actMaxAuthLvl != null) {
            // 均非null，判定不等，再做处理：
            if (this.actMaxAuthLvl.compareTo(actMaxAuthLvl) != 0) {
                this.actMaxAuthLvl = actMaxAuthLvl;
                if (!this.toUpdateCols.contains("ACT_MAX_AUTH_LVL")) {
                    this.toUpdateCols.add("ACT_MAX_AUTH_LVL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actMaxAuthLvl = actMaxAuthLvl;
            if (!this.toUpdateCols.contains("ACT_MAX_AUTH_LVL")) {
                this.toUpdateCols.add("ACT_MAX_AUTH_LVL");
            }
        }
        return this;
    }

    /**
     * 快捷搜索启用。
     */
    private Boolean fastSearchEnabled;

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
        if (this.fastSearchEnabled == null && fastSearchEnabled == null) {
            // 均为null，不做处理。
        } else if (this.fastSearchEnabled != null && fastSearchEnabled != null) {
            // 均非null，判定不等，再做处理：
            if (this.fastSearchEnabled.compareTo(fastSearchEnabled) != 0) {
                this.fastSearchEnabled = fastSearchEnabled;
                if (!this.toUpdateCols.contains("FAST_SEARCH_ENABLED")) {
                    this.toUpdateCols.add("FAST_SEARCH_ENABLED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fastSearchEnabled = fastSearchEnabled;
            if (!this.toUpdateCols.contains("FAST_SEARCH_ENABLED")) {
                this.toUpdateCols.add("FAST_SEARCH_ENABLED");
            }
        }
        return this;
    }

    /**
     * 快捷搜索提示。
     */
    private String fastSearchHint;

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
        if (this.fastSearchHint == null && fastSearchHint == null) {
            // 均为null，不做处理。
        } else if (this.fastSearchHint != null && fastSearchHint != null) {
            // 均非null，判定不等，再做处理：
            if (this.fastSearchHint.compareTo(fastSearchHint) != 0) {
                this.fastSearchHint = fastSearchHint;
                if (!this.toUpdateCols.contains("FAST_SEARCH_HINT")) {
                    this.toUpdateCols.add("FAST_SEARCH_HINT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fastSearchHint = fastSearchHint;
            if (!this.toUpdateCols.contains("FAST_SEARCH_HINT")) {
                this.toUpdateCols.add("FAST_SEARCH_HINT");
            }
        }
        return this;
    }

    /**
     * 快捷搜索过滤语句。
     */
    private String fastSearchWhereClause;

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
        if (this.fastSearchWhereClause == null && fastSearchWhereClause == null) {
            // 均为null，不做处理。
        } else if (this.fastSearchWhereClause != null && fastSearchWhereClause != null) {
            // 均非null，判定不等，再做处理：
            if (this.fastSearchWhereClause.compareTo(fastSearchWhereClause) != 0) {
                this.fastSearchWhereClause = fastSearchWhereClause;
                if (!this.toUpdateCols.contains("FAST_SEARCH_WHERE_CLAUSE")) {
                    this.toUpdateCols.add("FAST_SEARCH_WHERE_CLAUSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fastSearchWhereClause = fastSearchWhereClause;
            if (!this.toUpdateCols.contains("FAST_SEARCH_WHERE_CLAUSE")) {
                this.toUpdateCols.add("FAST_SEARCH_WHERE_CLAUSE");
            }
        }
        return this;
    }

    /**
     * 实体视图编辑模式。
     */
    private String adSevEditModeId;

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
        if (this.adSevEditModeId == null && adSevEditModeId == null) {
            // 均为null，不做处理。
        } else if (this.adSevEditModeId != null && adSevEditModeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adSevEditModeId.compareTo(adSevEditModeId) != 0) {
                this.adSevEditModeId = adSevEditModeId;
                if (!this.toUpdateCols.contains("AD_SEV_EDIT_MODE_ID")) {
                    this.toUpdateCols.add("AD_SEV_EDIT_MODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adSevEditModeId = adSevEditModeId;
            if (!this.toUpdateCols.contains("AD_SEV_EDIT_MODE_ID")) {
                this.toUpdateCols.add("AD_SEV_EDIT_MODE_ID");
            }
        }
        return this;
    }

    /**
     * 明细窗口宽度。
     */
    private String dtlWindowWidth;

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
        if (this.dtlWindowWidth == null && dtlWindowWidth == null) {
            // 均为null，不做处理。
        } else if (this.dtlWindowWidth != null && dtlWindowWidth != null) {
            // 均非null，判定不等，再做处理：
            if (this.dtlWindowWidth.compareTo(dtlWindowWidth) != 0) {
                this.dtlWindowWidth = dtlWindowWidth;
                if (!this.toUpdateCols.contains("DTL_WINDOW_WIDTH")) {
                    this.toUpdateCols.add("DTL_WINDOW_WIDTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dtlWindowWidth = dtlWindowWidth;
            if (!this.toUpdateCols.contains("DTL_WINDOW_WIDTH")) {
                this.toUpdateCols.add("DTL_WINDOW_WIDTH");
            }
        }
        return this;
    }

    /**
     * 明细窗口高度。
     */
    private String dtlWindowHeight;

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
        if (this.dtlWindowHeight == null && dtlWindowHeight == null) {
            // 均为null，不做处理。
        } else if (this.dtlWindowHeight != null && dtlWindowHeight != null) {
            // 均非null，判定不等，再做处理：
            if (this.dtlWindowHeight.compareTo(dtlWindowHeight) != 0) {
                this.dtlWindowHeight = dtlWindowHeight;
                if (!this.toUpdateCols.contains("DTL_WINDOW_HEIGHT")) {
                    this.toUpdateCols.add("DTL_WINDOW_HEIGHT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dtlWindowHeight = dtlWindowHeight;
            if (!this.toUpdateCols.contains("DTL_WINDOW_HEIGHT")) {
                this.toUpdateCols.add("DTL_WINDOW_HEIGHT");
            }
        }
        return this;
    }

    /**
     * 明细窗口视图部分。
     */
    private String dtlWindowVpId;

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
        if (this.dtlWindowVpId == null && dtlWindowVpId == null) {
            // 均为null，不做处理。
        } else if (this.dtlWindowVpId != null && dtlWindowVpId != null) {
            // 均非null，判定不等，再做处理：
            if (this.dtlWindowVpId.compareTo(dtlWindowVpId) != 0) {
                this.dtlWindowVpId = dtlWindowVpId;
                if (!this.toUpdateCols.contains("DTL_WINDOW_VP_ID")) {
                    this.toUpdateCols.add("DTL_WINDOW_VP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dtlWindowVpId = dtlWindowVpId;
            if (!this.toUpdateCols.contains("DTL_WINDOW_VP_ID")) {
                this.toUpdateCols.add("DTL_WINDOW_VP_ID");
            }
        }
        return this;
    }

    /**
     * 明细窗口启用流程。
     */
    private Boolean dtlWindowWfEnabled;

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
        if (this.dtlWindowWfEnabled == null && dtlWindowWfEnabled == null) {
            // 均为null，不做处理。
        } else if (this.dtlWindowWfEnabled != null && dtlWindowWfEnabled != null) {
            // 均非null，判定不等，再做处理：
            if (this.dtlWindowWfEnabled.compareTo(dtlWindowWfEnabled) != 0) {
                this.dtlWindowWfEnabled = dtlWindowWfEnabled;
                if (!this.toUpdateCols.contains("DTL_WINDOW_WF_ENABLED")) {
                    this.toUpdateCols.add("DTL_WINDOW_WF_ENABLED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dtlWindowWfEnabled = dtlWindowWfEnabled;
            if (!this.toUpdateCols.contains("DTL_WINDOW_WF_ENABLED")) {
                this.toUpdateCols.add("DTL_WINDOW_WF_ENABLED");
            }
        }
        return this;
    }

    /**
     * 表单宽度。
     */
    private String formWidth;

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
        if (this.formWidth == null && formWidth == null) {
            // 均为null，不做处理。
        } else if (this.formWidth != null && formWidth != null) {
            // 均非null，判定不等，再做处理：
            if (this.formWidth.compareTo(formWidth) != 0) {
                this.formWidth = formWidth;
                if (!this.toUpdateCols.contains("FORM_WIDTH")) {
                    this.toUpdateCols.add("FORM_WIDTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.formWidth = formWidth;
            if (!this.toUpdateCols.contains("FORM_WIDTH")) {
                this.toUpdateCols.add("FORM_WIDTH");
            }
        }
        return this;
    }

    /**
     * 表单列数。
     */
    private Integer formCols;

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
        if (this.formCols == null && formCols == null) {
            // 均为null，不做处理。
        } else if (this.formCols != null && formCols != null) {
            // 均非null，判定不等，再做处理：
            if (this.formCols.compareTo(formCols) != 0) {
                this.formCols = formCols;
                if (!this.toUpdateCols.contains("FORM_COLS")) {
                    this.toUpdateCols.add("FORM_COLS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.formCols = formCols;
            if (!this.toUpdateCols.contains("FORM_COLS")) {
                this.toUpdateCols.add("FORM_COLS");
            }
        }
        return this;
    }

    /**
     * 表单各列宽度。
     */
    private String formColWidths;

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
        if (this.formColWidths == null && formColWidths == null) {
            // 均为null，不做处理。
        } else if (this.formColWidths != null && formColWidths != null) {
            // 均非null，判定不等，再做处理：
            if (this.formColWidths.compareTo(formColWidths) != 0) {
                this.formColWidths = formColWidths;
                if (!this.toUpdateCols.contains("FORM_COL_WIDTHS")) {
                    this.toUpdateCols.add("FORM_COL_WIDTHS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.formColWidths = formColWidths;
            if (!this.toUpdateCols.contains("FORM_COL_WIDTHS")) {
                this.toUpdateCols.add("FORM_COL_WIDTHS");
            }
        }
        return this;
    }

    /**
     * 表单单元格边框。
     */
    private Integer formCellBorder;

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
        if (this.formCellBorder == null && formCellBorder == null) {
            // 均为null，不做处理。
        } else if (this.formCellBorder != null && formCellBorder != null) {
            // 均非null，判定不等，再做处理：
            if (this.formCellBorder.compareTo(formCellBorder) != 0) {
                this.formCellBorder = formCellBorder;
                if (!this.toUpdateCols.contains("FORM_CELL_BORDER")) {
                    this.toUpdateCols.add("FORM_CELL_BORDER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.formCellBorder = formCellBorder;
            if (!this.toUpdateCols.contains("FORM_CELL_BORDER")) {
                this.toUpdateCols.add("FORM_CELL_BORDER");
            }
        }
        return this;
    }

    /**
     * 表单单元格填充。
     */
    private Integer formCellPadding;

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
        if (this.formCellPadding == null && formCellPadding == null) {
            // 均为null，不做处理。
        } else if (this.formCellPadding != null && formCellPadding != null) {
            // 均非null，判定不等，再做处理：
            if (this.formCellPadding.compareTo(formCellPadding) != 0) {
                this.formCellPadding = formCellPadding;
                if (!this.toUpdateCols.contains("FORM_CELL_PADDING")) {
                    this.toUpdateCols.add("FORM_CELL_PADDING");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.formCellPadding = formCellPadding;
            if (!this.toUpdateCols.contains("FORM_CELL_PADDING")) {
                this.toUpdateCols.add("FORM_CELL_PADDING");
            }
        }
        return this;
    }

    /**
     * 批量修改窗口宽度。
     */
    private String batchModiWindowWidth;

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
        if (this.batchModiWindowWidth == null && batchModiWindowWidth == null) {
            // 均为null，不做处理。
        } else if (this.batchModiWindowWidth != null && batchModiWindowWidth != null) {
            // 均非null，判定不等，再做处理：
            if (this.batchModiWindowWidth.compareTo(batchModiWindowWidth) != 0) {
                this.batchModiWindowWidth = batchModiWindowWidth;
                if (!this.toUpdateCols.contains("BATCH_MODI_WINDOW_WIDTH")) {
                    this.toUpdateCols.add("BATCH_MODI_WINDOW_WIDTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.batchModiWindowWidth = batchModiWindowWidth;
            if (!this.toUpdateCols.contains("BATCH_MODI_WINDOW_WIDTH")) {
                this.toUpdateCols.add("BATCH_MODI_WINDOW_WIDTH");
            }
        }
        return this;
    }

    /**
     * 批量修改窗口高度。
     */
    private String batchModiWindowHeight;

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
        if (this.batchModiWindowHeight == null && batchModiWindowHeight == null) {
            // 均为null，不做处理。
        } else if (this.batchModiWindowHeight != null && batchModiWindowHeight != null) {
            // 均非null，判定不等，再做处理：
            if (this.batchModiWindowHeight.compareTo(batchModiWindowHeight) != 0) {
                this.batchModiWindowHeight = batchModiWindowHeight;
                if (!this.toUpdateCols.contains("BATCH_MODI_WINDOW_HEIGHT")) {
                    this.toUpdateCols.add("BATCH_MODI_WINDOW_HEIGHT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.batchModiWindowHeight = batchModiWindowHeight;
            if (!this.toUpdateCols.contains("BATCH_MODI_WINDOW_HEIGHT")) {
                this.toUpdateCols.add("BATCH_MODI_WINDOW_HEIGHT");
            }
        }
        return this;
    }

    /**
     * 启用选择相关操作。
     */
    private Boolean enableSelectAwareAct;

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
        if (this.enableSelectAwareAct == null && enableSelectAwareAct == null) {
            // 均为null，不做处理。
        } else if (this.enableSelectAwareAct != null && enableSelectAwareAct != null) {
            // 均非null，判定不等，再做处理：
            if (this.enableSelectAwareAct.compareTo(enableSelectAwareAct) != 0) {
                this.enableSelectAwareAct = enableSelectAwareAct;
                if (!this.toUpdateCols.contains("ENABLE_SELECT_AWARE_ACT")) {
                    this.toUpdateCols.add("ENABLE_SELECT_AWARE_ACT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.enableSelectAwareAct = enableSelectAwareAct;
            if (!this.toUpdateCols.contains("ENABLE_SELECT_AWARE_ACT")) {
                this.toUpdateCols.add("ENABLE_SELECT_AWARE_ACT");
            }
        }
        return this;
    }

    /**
     * 启用显示文本。
     */
    private Boolean enableDspText;

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
        if (this.enableDspText == null && enableDspText == null) {
            // 均为null，不做处理。
        } else if (this.enableDspText != null && enableDspText != null) {
            // 均非null，判定不等，再做处理：
            if (this.enableDspText.compareTo(enableDspText) != 0) {
                this.enableDspText = enableDspText;
                if (!this.toUpdateCols.contains("ENABLE_DSP_TEXT")) {
                    this.toUpdateCols.add("ENABLE_DSP_TEXT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.enableDspText = enableDspText;
            if (!this.toUpdateCols.contains("ENABLE_DSP_TEXT")) {
                this.toUpdateCols.add("ENABLE_DSP_TEXT");
            }
        }
        return this;
    }

    /**
     * FLEX开始样式。
     */
    private String flexBeginStyle;

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
        if (this.flexBeginStyle == null && flexBeginStyle == null) {
            // 均为null，不做处理。
        } else if (this.flexBeginStyle != null && flexBeginStyle != null) {
            // 均非null，判定不等，再做处理：
            if (this.flexBeginStyle.compareTo(flexBeginStyle) != 0) {
                this.flexBeginStyle = flexBeginStyle;
                if (!this.toUpdateCols.contains("FLEX_BEGIN_STYLE")) {
                    this.toUpdateCols.add("FLEX_BEGIN_STYLE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.flexBeginStyle = flexBeginStyle;
            if (!this.toUpdateCols.contains("FLEX_BEGIN_STYLE")) {
                this.toUpdateCols.add("FLEX_BEGIN_STYLE");
            }
        }
        return this;
    }

    /**
     * FLEX记录样式。
     */
    private String flexRecStyle;

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
        if (this.flexRecStyle == null && flexRecStyle == null) {
            // 均为null，不做处理。
        } else if (this.flexRecStyle != null && flexRecStyle != null) {
            // 均非null，判定不等，再做处理：
            if (this.flexRecStyle.compareTo(flexRecStyle) != 0) {
                this.flexRecStyle = flexRecStyle;
                if (!this.toUpdateCols.contains("FLEX_REC_STYLE")) {
                    this.toUpdateCols.add("FLEX_REC_STYLE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.flexRecStyle = flexRecStyle;
            if (!this.toUpdateCols.contains("FLEX_REC_STYLE")) {
                this.toUpdateCols.add("FLEX_REC_STYLE");
            }
        }
        return this;
    }

    /**
     * FLEX分隔样式。
     */
    private String flexSplitStyle;

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
        if (this.flexSplitStyle == null && flexSplitStyle == null) {
            // 均为null，不做处理。
        } else if (this.flexSplitStyle != null && flexSplitStyle != null) {
            // 均非null，判定不等，再做处理：
            if (this.flexSplitStyle.compareTo(flexSplitStyle) != 0) {
                this.flexSplitStyle = flexSplitStyle;
                if (!this.toUpdateCols.contains("FLEX_SPLIT_STYLE")) {
                    this.toUpdateCols.add("FLEX_SPLIT_STYLE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.flexSplitStyle = flexSplitStyle;
            if (!this.toUpdateCols.contains("FLEX_SPLIT_STYLE")) {
                this.toUpdateCols.add("FLEX_SPLIT_STYLE");
            }
        }
        return this;
    }

    /**
     * FLEX结束样式。
     */
    private String flexEndStyle;

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
        if (this.flexEndStyle == null && flexEndStyle == null) {
            // 均为null，不做处理。
        } else if (this.flexEndStyle != null && flexEndStyle != null) {
            // 均非null，判定不等，再做处理：
            if (this.flexEndStyle.compareTo(flexEndStyle) != 0) {
                this.flexEndStyle = flexEndStyle;
                if (!this.toUpdateCols.contains("FLEX_END_STYLE")) {
                    this.toUpdateCols.add("FLEX_END_STYLE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.flexEndStyle = flexEndStyle;
            if (!this.toUpdateCols.contains("FLEX_END_STYLE")) {
                this.toUpdateCols.add("FLEX_END_STYLE");
            }
        }
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
        this.clearToUpdateCols();
    }

    /**
     * 根据ID更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param includeCols 更新时包含的列，空为包含所有。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @param refreshThis 更新后，是否刷新当前对象。刷新时将刷新所有列。
     */
    public void updateById(List<String> includeCols, List<String> excludeCols, boolean refreshThis) {
        if (SharedUtil.isEmptyList(includeCols) && SharedUtil.isEmptyList(toUpdateCols)) {
            // 既未指明includeCols，也无toUpdateCols，则不更新。

            if (refreshThis) {
                modelHelper.refreshThis(this.id, this, "无需更新，直接刷新");
            }
        } else {
            // 若已指明includeCols，或有toUpdateCols；则先以includeCols为准，再以toUpdateCols为准：
            modelHelper.updateById(SharedUtil.isEmptyList(includeCols) ? toUpdateCols : includeCols, excludeCols, refreshThis, this.id, this);
            this.clearToUpdateCols();
        }
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
    public static AdSingleEntView newData() {
        AdSingleEntView obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static AdSingleEntView insertData() {
        AdSingleEntView obj = modelHelper.insertData();
        return obj;
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
        AdSingleEntView obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
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
        List<AdSingleEntView> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
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
        List<AdSingleEntView> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
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