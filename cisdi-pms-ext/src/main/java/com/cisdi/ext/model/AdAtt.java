package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 属性。
 */
public class AdAtt {

    /**
     * 模型助手。
     */
    private static final ModelHelper<AdAtt> modelHelper = new ModelHelper<>("AD_ATT", new AdAtt());

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

    public static final String ENT_CODE = "AD_ATT";
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
         * 属性子类型。
         */
        public static final String AD_ATT_SUB_TYPE_ID = "AD_ATT_SUB_TYPE_ID";
        /**
         * 虚拟表达式。
         */
        public static final String VIRTUAL_EXPRESSION = "VIRTUAL_EXPRESSION";
        /**
         * 快捷操作组。
         */
        public static final String AD_FAST_ACT_GRP_ID = "AD_FAST_ACT_GRP_ID";
        /**
         * 宽度。
         */
        public static final String WIDTH = "WIDTH";
        /**
         * 序号。
         */
        public static final String SEQ_NO = "SEQ_NO";
        /**
         * 是否标识。
         */
        public static final String IS_IDENTIFYING = "IS_IDENTIFYING";
        /**
         * 是否默认显示。
         */
        public static final String IS_SHOWN_BY_DEFAULT = "IS_SHOWN_BY_DEFAULT";
        /**
         * 可改逻辑。
         */
        public static final String EDITABLE_LOGIC = "EDITABLE_LOGIC";
        /**
         * 忽略实体记录可改。
         */
        public static final String IGNORE_ENT_REC_EDITABLE = "IGNORE_ENT_REC_EDITABLE";
        /**
         * 必填逻辑。
         */
        public static final String MANDATORY_LOGIC = "MANDATORY_LOGIC";
        /**
         * 默认值逻辑。
         */
        public static final String DEFAULT_VALUE_LOGIC = "DEFAULT_VALUE_LOGIC";
        /**
         * 可见逻辑。
         */
        public static final String VISIBLE_LOGIC = "VISIBLE_LOGIC";
        /**
         * 有效逻辑。
         */
        public static final String VALID_LOGIC = "VALID_LOGIC";
        /**
         * 文本悬浮二维码。
         */
        public static final String TEXT_HOVER_AS_QR_CODE = "TEXT_HOVER_AS_QR_CODE";
        /**
         * 奇行单元格样式文本逻辑。
         */
        public static final String ODD_ROW_CELL_ST_LOGIC = "ODD_ROW_CELL_ST_LOGIC";
        /**
         * 偶行单元格样式文本逻辑。
         */
        public static final String EVEN_ROW_CELL_ST_LOGIC = "EVEN_ROW_CELL_ST_LOGIC";
        /**
         * 组合标题。
         */
        public static final String HEADER_SPAN_TITLE = "HEADER_SPAN_TITLE";
        /**
         * 文件路径。
         */
        public static final String FILE_PATH_ID = "FILE_PATH_ID";
        /**
         * 文件类型。
         */
        public static final String FILE_TYPES = "FILE_TYPES";
        /**
         * 文件最大KB。
         */
        public static final String FILE_MAX_KB = "FILE_MAX_KB";
        /**
         * 文件允许批量上传。
         */
        public static final String FILE_IS_MULTI = "FILE_IS_MULTI";
        /**
         * 文件是否作为图片查看。
         */
        public static final String FILE_VIEW_AS_IMG = "FILE_VIEW_AS_IMG";
        /**
         * 文件是否作为图片悬浮。
         */
        public static final String FILE_HOVER_AS_IMG = "FILE_HOVER_AS_IMG";
        /**
         * 文件图片宽度。
         */
        public static final String FILE_IMG_WIDTH = "FILE_IMG_WIDTH";
        /**
         * 文件图片高度。
         */
        public static final String FILE_IMG_HEIGHT = "FILE_IMG_HEIGHT";
        /**
         * 是否分组。
         */
        public static final String IS_GROUPED = "IS_GROUPED";
        /**
         * 汇总模式。
         */
        public static final String SUM_MODE_ID = "SUM_MODE_ID";
        /**
         * 汇总前缀。
         */
        public static final String SUM_PREFIX = "SUM_PREFIX";
        /**
         * 汇总后缀。
         */
        public static final String SUM_SUFFIX = "SUM_SUFFIX";
        /**
         * 是否隐藏分组汇总。
         */
        public static final String IS_GROUP_SUM_HIDDEN = "IS_GROUP_SUM_HIDDEN";
        /**
         * 是否隐藏总计汇总。
         */
        public static final String IS_TTL_SUM_HIDDEN = "IS_TTL_SUM_HIDDEN";
        /**
         * 横向对齐。
         */
        public static final String H_ALIGN = "H_ALIGN";
        /**
         * 是否固定。
         */
        public static final String IS_FIXED = "IS_FIXED";
        /**
         * 显示格式。
         */
        public static final String DISPLAY_FORMAT = "DISPLAY_FORMAT";
        /**
         * 变量名称。
         */
        public static final String VAR_NAME = "VAR_NAME";
        /**
         * 引用的实体视图。
         */
        public static final String REFED_SEV_ID = "REFED_SEV_ID";
        /**
         * 引用的视图部分列表。
         */
        public static final String REFED_VP_IDS = "REFED_VP_IDS";
        /**
         * 引用的WHERE语句。
         */
        public static final String REFED_WHERE_CLAUSE = "REFED_WHERE_CLAUSE";
        /**
         * 引用删除模式。
         */
        public static final String REFED_ON_DEL_MODE = "REFED_ON_DEL_MODE";
        /**
         * 引用的启用缓存。
         */
        public static final String REFED_CACHE_ENABLED = "REFED_CACHE_ENABLED";
        /**
         * 引用的下拉禁用。
         */
        public static final String REFED_DROPDOWN_DISABLED = "REFED_DROPDOWN_DISABLED";
        /**
         * 引用的搜索禁用。
         */
        public static final String REFED_SEARCH_DISABLED = "REFED_SEARCH_DISABLED";
        /**
         * 引用的弹出宽度。
         */
        public static final String REFED_POPUP_WIDTH = "REFED_POPUP_WIDTH";
        /**
         * 引用的弹出高度。
         */
        public static final String REFED_POPUP_HEIGHT = "REFED_POPUP_HEIGHT";
        /**
         * 引用的可选择逻辑。
         */
        public static final String REFED_SELECTABLE_LOGIC = "REFED_SELECTABLE_LOGIC";
        /**
         * 定义SQL。
         */
        public static final String DEF_SQL = "DEF_SQL";
        /**
         * 是否必填。
         */
        public static final String IS_MANDATORY = "IS_MANDATORY";
        /**
         * 索引类型。
         */
        public static final String AD_INDEX_TYPE_ID = "AD_INDEX_TYPE_ID";
        /**
         * 创建外键。
         */
        public static final String CREATE_FK = "CREATE_FK";
        /**
         * 表单项标题隐藏。
         */
        public static final String FORM_ITEM_TITLE_HIDDEN = "FORM_ITEM_TITLE_HIDDEN";
        /**
         * 表单项标题在上。
         */
        public static final String FORM_ITEM_TITLE_TOP = "FORM_ITEM_TITLE_TOP";
        /**
         * 表单项标题换行。
         */
        public static final String FORM_ITEM_TITLE_WRAP = "FORM_ITEM_TITLE_WRAP";
        /**
         * 表单项宽度。
         */
        public static final String FORM_ITEM_WIDTH = "FORM_ITEM_WIDTH";
        /**
         * 表单项高度。
         */
        public static final String FORM_ITEM_HEIGHT = "FORM_ITEM_HEIGHT";
        /**
         * 表单项行跨度。
         */
        public static final String FORM_ITEM_ROW_SPAN = "FORM_ITEM_ROW_SPAN";
        /**
         * 表单项列跨度。
         */
        public static final String FORM_ITEM_COL_SPAN = "FORM_ITEM_COL_SPAN";
        /**
         * 在列表页隐藏。
         */
        public static final String HIDE_IN_LIST = "HIDE_IN_LIST";
        /**
         * 在详情页隐藏。
         */
        public static final String HIDE_IN_DTL = "HIDE_IN_DTL";
        /**
         * 在打印时隐藏。
         */
        public static final String HIDE_IN_PRINT = "HIDE_IN_PRINT";
        /**
         * 在简单过滤里隐藏。
         */
        public static final String HIDE_IN_SIMPLE_FILTER = "HIDE_IN_SIMPLE_FILTER";
        /**
         * 在复杂过滤里隐藏。
         */
        public static final String HIDE_IN_COMPLEX_FILTER = "HIDE_IN_COMPLEX_FILTER";
        /**
         * 存储计量单位。
         */
        public static final String STORE_UOM_ID = "STORE_UOM_ID";
        /**
         * 显示计量单位。
         */
        public static final String DISPLAY_UOM_ID = "DISPLAY_UOM_ID";
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
    public AdAtt setId(String id) {
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
    public AdAtt setVer(Integer ver) {
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
    public AdAtt setTs(LocalDateTime ts) {
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
    public AdAtt setIsPreset(Boolean isPreset) {
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
    public AdAtt setCrtDt(LocalDateTime crtDt) {
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
    public AdAtt setCrtUserId(String crtUserId) {
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
    public AdAtt setLastModiDt(LocalDateTime lastModiDt) {
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
    public AdAtt setLastModiUserId(String lastModiUserId) {
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
    public AdAtt setStatus(String status) {
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
    public AdAtt setLkWfInstId(String lkWfInstId) {
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
    public AdAtt setCode(String code) {
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
    public AdAtt setName(String name) {
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
    public AdAtt setRemark(String remark) {
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
     * 属性子类型。
     */
    private String adAttSubTypeId;

    /**
     * 获取：属性子类型。
     */
    public String getAdAttSubTypeId() {
        return this.adAttSubTypeId;
    }

    /**
     * 设置：属性子类型。
     */
    public AdAtt setAdAttSubTypeId(String adAttSubTypeId) {
        if (this.adAttSubTypeId == null && adAttSubTypeId == null) {
            // 均为null，不做处理。
        } else if (this.adAttSubTypeId != null && adAttSubTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adAttSubTypeId.compareTo(adAttSubTypeId) != 0) {
                this.adAttSubTypeId = adAttSubTypeId;
                if (!this.toUpdateCols.contains("AD_ATT_SUB_TYPE_ID")) {
                    this.toUpdateCols.add("AD_ATT_SUB_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adAttSubTypeId = adAttSubTypeId;
            if (!this.toUpdateCols.contains("AD_ATT_SUB_TYPE_ID")) {
                this.toUpdateCols.add("AD_ATT_SUB_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 虚拟表达式。
     */
    private String virtualExpression;

    /**
     * 获取：虚拟表达式。
     */
    public String getVirtualExpression() {
        return this.virtualExpression;
    }

    /**
     * 设置：虚拟表达式。
     */
    public AdAtt setVirtualExpression(String virtualExpression) {
        if (this.virtualExpression == null && virtualExpression == null) {
            // 均为null，不做处理。
        } else if (this.virtualExpression != null && virtualExpression != null) {
            // 均非null，判定不等，再做处理：
            if (this.virtualExpression.compareTo(virtualExpression) != 0) {
                this.virtualExpression = virtualExpression;
                if (!this.toUpdateCols.contains("VIRTUAL_EXPRESSION")) {
                    this.toUpdateCols.add("VIRTUAL_EXPRESSION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.virtualExpression = virtualExpression;
            if (!this.toUpdateCols.contains("VIRTUAL_EXPRESSION")) {
                this.toUpdateCols.add("VIRTUAL_EXPRESSION");
            }
        }
        return this;
    }

    /**
     * 快捷操作组。
     */
    private String adFastActGrpId;

    /**
     * 获取：快捷操作组。
     */
    public String getAdFastActGrpId() {
        return this.adFastActGrpId;
    }

    /**
     * 设置：快捷操作组。
     */
    public AdAtt setAdFastActGrpId(String adFastActGrpId) {
        if (this.adFastActGrpId == null && adFastActGrpId == null) {
            // 均为null，不做处理。
        } else if (this.adFastActGrpId != null && adFastActGrpId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adFastActGrpId.compareTo(adFastActGrpId) != 0) {
                this.adFastActGrpId = adFastActGrpId;
                if (!this.toUpdateCols.contains("AD_FAST_ACT_GRP_ID")) {
                    this.toUpdateCols.add("AD_FAST_ACT_GRP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adFastActGrpId = adFastActGrpId;
            if (!this.toUpdateCols.contains("AD_FAST_ACT_GRP_ID")) {
                this.toUpdateCols.add("AD_FAST_ACT_GRP_ID");
            }
        }
        return this;
    }

    /**
     * 宽度。
     */
    private String width;

    /**
     * 获取：宽度。
     */
    public String getWidth() {
        return this.width;
    }

    /**
     * 设置：宽度。
     */
    public AdAtt setWidth(String width) {
        if (this.width == null && width == null) {
            // 均为null，不做处理。
        } else if (this.width != null && width != null) {
            // 均非null，判定不等，再做处理：
            if (this.width.compareTo(width) != 0) {
                this.width = width;
                if (!this.toUpdateCols.contains("WIDTH")) {
                    this.toUpdateCols.add("WIDTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.width = width;
            if (!this.toUpdateCols.contains("WIDTH")) {
                this.toUpdateCols.add("WIDTH");
            }
        }
        return this;
    }

    /**
     * 序号。
     */
    private BigDecimal seqNo;

    /**
     * 获取：序号。
     */
    public BigDecimal getSeqNo() {
        return this.seqNo;
    }

    /**
     * 设置：序号。
     */
    public AdAtt setSeqNo(BigDecimal seqNo) {
        if (this.seqNo == null && seqNo == null) {
            // 均为null，不做处理。
        } else if (this.seqNo != null && seqNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.seqNo.compareTo(seqNo) != 0) {
                this.seqNo = seqNo;
                if (!this.toUpdateCols.contains("SEQ_NO")) {
                    this.toUpdateCols.add("SEQ_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.seqNo = seqNo;
            if (!this.toUpdateCols.contains("SEQ_NO")) {
                this.toUpdateCols.add("SEQ_NO");
            }
        }
        return this;
    }

    /**
     * 是否标识。
     */
    private Boolean isIdentifying;

    /**
     * 获取：是否标识。
     */
    public Boolean getIsIdentifying() {
        return this.isIdentifying;
    }

    /**
     * 设置：是否标识。
     */
    public AdAtt setIsIdentifying(Boolean isIdentifying) {
        if (this.isIdentifying == null && isIdentifying == null) {
            // 均为null，不做处理。
        } else if (this.isIdentifying != null && isIdentifying != null) {
            // 均非null，判定不等，再做处理：
            if (this.isIdentifying.compareTo(isIdentifying) != 0) {
                this.isIdentifying = isIdentifying;
                if (!this.toUpdateCols.contains("IS_IDENTIFYING")) {
                    this.toUpdateCols.add("IS_IDENTIFYING");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isIdentifying = isIdentifying;
            if (!this.toUpdateCols.contains("IS_IDENTIFYING")) {
                this.toUpdateCols.add("IS_IDENTIFYING");
            }
        }
        return this;
    }

    /**
     * 是否默认显示。
     */
    private Boolean isShownByDefault;

    /**
     * 获取：是否默认显示。
     */
    public Boolean getIsShownByDefault() {
        return this.isShownByDefault;
    }

    /**
     * 设置：是否默认显示。
     */
    public AdAtt setIsShownByDefault(Boolean isShownByDefault) {
        if (this.isShownByDefault == null && isShownByDefault == null) {
            // 均为null，不做处理。
        } else if (this.isShownByDefault != null && isShownByDefault != null) {
            // 均非null，判定不等，再做处理：
            if (this.isShownByDefault.compareTo(isShownByDefault) != 0) {
                this.isShownByDefault = isShownByDefault;
                if (!this.toUpdateCols.contains("IS_SHOWN_BY_DEFAULT")) {
                    this.toUpdateCols.add("IS_SHOWN_BY_DEFAULT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isShownByDefault = isShownByDefault;
            if (!this.toUpdateCols.contains("IS_SHOWN_BY_DEFAULT")) {
                this.toUpdateCols.add("IS_SHOWN_BY_DEFAULT");
            }
        }
        return this;
    }

    /**
     * 可改逻辑。
     */
    private String editableLogic;

    /**
     * 获取：可改逻辑。
     */
    public String getEditableLogic() {
        return this.editableLogic;
    }

    /**
     * 设置：可改逻辑。
     */
    public AdAtt setEditableLogic(String editableLogic) {
        if (this.editableLogic == null && editableLogic == null) {
            // 均为null，不做处理。
        } else if (this.editableLogic != null && editableLogic != null) {
            // 均非null，判定不等，再做处理：
            if (this.editableLogic.compareTo(editableLogic) != 0) {
                this.editableLogic = editableLogic;
                if (!this.toUpdateCols.contains("EDITABLE_LOGIC")) {
                    this.toUpdateCols.add("EDITABLE_LOGIC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.editableLogic = editableLogic;
            if (!this.toUpdateCols.contains("EDITABLE_LOGIC")) {
                this.toUpdateCols.add("EDITABLE_LOGIC");
            }
        }
        return this;
    }

    /**
     * 忽略实体记录可改。
     */
    private Boolean ignoreEntRecEditable;

    /**
     * 获取：忽略实体记录可改。
     */
    public Boolean getIgnoreEntRecEditable() {
        return this.ignoreEntRecEditable;
    }

    /**
     * 设置：忽略实体记录可改。
     */
    public AdAtt setIgnoreEntRecEditable(Boolean ignoreEntRecEditable) {
        if (this.ignoreEntRecEditable == null && ignoreEntRecEditable == null) {
            // 均为null，不做处理。
        } else if (this.ignoreEntRecEditable != null && ignoreEntRecEditable != null) {
            // 均非null，判定不等，再做处理：
            if (this.ignoreEntRecEditable.compareTo(ignoreEntRecEditable) != 0) {
                this.ignoreEntRecEditable = ignoreEntRecEditable;
                if (!this.toUpdateCols.contains("IGNORE_ENT_REC_EDITABLE")) {
                    this.toUpdateCols.add("IGNORE_ENT_REC_EDITABLE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ignoreEntRecEditable = ignoreEntRecEditable;
            if (!this.toUpdateCols.contains("IGNORE_ENT_REC_EDITABLE")) {
                this.toUpdateCols.add("IGNORE_ENT_REC_EDITABLE");
            }
        }
        return this;
    }

    /**
     * 必填逻辑。
     */
    private String mandatoryLogic;

    /**
     * 获取：必填逻辑。
     */
    public String getMandatoryLogic() {
        return this.mandatoryLogic;
    }

    /**
     * 设置：必填逻辑。
     */
    public AdAtt setMandatoryLogic(String mandatoryLogic) {
        if (this.mandatoryLogic == null && mandatoryLogic == null) {
            // 均为null，不做处理。
        } else if (this.mandatoryLogic != null && mandatoryLogic != null) {
            // 均非null，判定不等，再做处理：
            if (this.mandatoryLogic.compareTo(mandatoryLogic) != 0) {
                this.mandatoryLogic = mandatoryLogic;
                if (!this.toUpdateCols.contains("MANDATORY_LOGIC")) {
                    this.toUpdateCols.add("MANDATORY_LOGIC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.mandatoryLogic = mandatoryLogic;
            if (!this.toUpdateCols.contains("MANDATORY_LOGIC")) {
                this.toUpdateCols.add("MANDATORY_LOGIC");
            }
        }
        return this;
    }

    /**
     * 默认值逻辑。
     */
    private String defaultValueLogic;

    /**
     * 获取：默认值逻辑。
     */
    public String getDefaultValueLogic() {
        return this.defaultValueLogic;
    }

    /**
     * 设置：默认值逻辑。
     */
    public AdAtt setDefaultValueLogic(String defaultValueLogic) {
        if (this.defaultValueLogic == null && defaultValueLogic == null) {
            // 均为null，不做处理。
        } else if (this.defaultValueLogic != null && defaultValueLogic != null) {
            // 均非null，判定不等，再做处理：
            if (this.defaultValueLogic.compareTo(defaultValueLogic) != 0) {
                this.defaultValueLogic = defaultValueLogic;
                if (!this.toUpdateCols.contains("DEFAULT_VALUE_LOGIC")) {
                    this.toUpdateCols.add("DEFAULT_VALUE_LOGIC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.defaultValueLogic = defaultValueLogic;
            if (!this.toUpdateCols.contains("DEFAULT_VALUE_LOGIC")) {
                this.toUpdateCols.add("DEFAULT_VALUE_LOGIC");
            }
        }
        return this;
    }

    /**
     * 可见逻辑。
     */
    private String visibleLogic;

    /**
     * 获取：可见逻辑。
     */
    public String getVisibleLogic() {
        return this.visibleLogic;
    }

    /**
     * 设置：可见逻辑。
     */
    public AdAtt setVisibleLogic(String visibleLogic) {
        if (this.visibleLogic == null && visibleLogic == null) {
            // 均为null，不做处理。
        } else if (this.visibleLogic != null && visibleLogic != null) {
            // 均非null，判定不等，再做处理：
            if (this.visibleLogic.compareTo(visibleLogic) != 0) {
                this.visibleLogic = visibleLogic;
                if (!this.toUpdateCols.contains("VISIBLE_LOGIC")) {
                    this.toUpdateCols.add("VISIBLE_LOGIC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.visibleLogic = visibleLogic;
            if (!this.toUpdateCols.contains("VISIBLE_LOGIC")) {
                this.toUpdateCols.add("VISIBLE_LOGIC");
            }
        }
        return this;
    }

    /**
     * 有效逻辑。
     */
    private String validLogic;

    /**
     * 获取：有效逻辑。
     */
    public String getValidLogic() {
        return this.validLogic;
    }

    /**
     * 设置：有效逻辑。
     */
    public AdAtt setValidLogic(String validLogic) {
        if (this.validLogic == null && validLogic == null) {
            // 均为null，不做处理。
        } else if (this.validLogic != null && validLogic != null) {
            // 均非null，判定不等，再做处理：
            if (this.validLogic.compareTo(validLogic) != 0) {
                this.validLogic = validLogic;
                if (!this.toUpdateCols.contains("VALID_LOGIC")) {
                    this.toUpdateCols.add("VALID_LOGIC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.validLogic = validLogic;
            if (!this.toUpdateCols.contains("VALID_LOGIC")) {
                this.toUpdateCols.add("VALID_LOGIC");
            }
        }
        return this;
    }

    /**
     * 文本悬浮二维码。
     */
    private Boolean textHoverAsQrCode;

    /**
     * 获取：文本悬浮二维码。
     */
    public Boolean getTextHoverAsQrCode() {
        return this.textHoverAsQrCode;
    }

    /**
     * 设置：文本悬浮二维码。
     */
    public AdAtt setTextHoverAsQrCode(Boolean textHoverAsQrCode) {
        if (this.textHoverAsQrCode == null && textHoverAsQrCode == null) {
            // 均为null，不做处理。
        } else if (this.textHoverAsQrCode != null && textHoverAsQrCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.textHoverAsQrCode.compareTo(textHoverAsQrCode) != 0) {
                this.textHoverAsQrCode = textHoverAsQrCode;
                if (!this.toUpdateCols.contains("TEXT_HOVER_AS_QR_CODE")) {
                    this.toUpdateCols.add("TEXT_HOVER_AS_QR_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.textHoverAsQrCode = textHoverAsQrCode;
            if (!this.toUpdateCols.contains("TEXT_HOVER_AS_QR_CODE")) {
                this.toUpdateCols.add("TEXT_HOVER_AS_QR_CODE");
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
    public AdAtt setOddRowCellStLogic(String oddRowCellStLogic) {
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
    public AdAtt setEvenRowCellStLogic(String evenRowCellStLogic) {
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
     * 组合标题。
     */
    private String headerSpanTitle;

    /**
     * 获取：组合标题。
     */
    public String getHeaderSpanTitle() {
        return this.headerSpanTitle;
    }

    /**
     * 设置：组合标题。
     */
    public AdAtt setHeaderSpanTitle(String headerSpanTitle) {
        if (this.headerSpanTitle == null && headerSpanTitle == null) {
            // 均为null，不做处理。
        } else if (this.headerSpanTitle != null && headerSpanTitle != null) {
            // 均非null，判定不等，再做处理：
            if (this.headerSpanTitle.compareTo(headerSpanTitle) != 0) {
                this.headerSpanTitle = headerSpanTitle;
                if (!this.toUpdateCols.contains("HEADER_SPAN_TITLE")) {
                    this.toUpdateCols.add("HEADER_SPAN_TITLE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.headerSpanTitle = headerSpanTitle;
            if (!this.toUpdateCols.contains("HEADER_SPAN_TITLE")) {
                this.toUpdateCols.add("HEADER_SPAN_TITLE");
            }
        }
        return this;
    }

    /**
     * 文件路径。
     */
    private String filePathId;

    /**
     * 获取：文件路径。
     */
    public String getFilePathId() {
        return this.filePathId;
    }

    /**
     * 设置：文件路径。
     */
    public AdAtt setFilePathId(String filePathId) {
        if (this.filePathId == null && filePathId == null) {
            // 均为null，不做处理。
        } else if (this.filePathId != null && filePathId != null) {
            // 均非null，判定不等，再做处理：
            if (this.filePathId.compareTo(filePathId) != 0) {
                this.filePathId = filePathId;
                if (!this.toUpdateCols.contains("FILE_PATH_ID")) {
                    this.toUpdateCols.add("FILE_PATH_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.filePathId = filePathId;
            if (!this.toUpdateCols.contains("FILE_PATH_ID")) {
                this.toUpdateCols.add("FILE_PATH_ID");
            }
        }
        return this;
    }

    /**
     * 文件类型。
     */
    private String fileTypes;

    /**
     * 获取：文件类型。
     */
    public String getFileTypes() {
        return this.fileTypes;
    }

    /**
     * 设置：文件类型。
     */
    public AdAtt setFileTypes(String fileTypes) {
        if (this.fileTypes == null && fileTypes == null) {
            // 均为null，不做处理。
        } else if (this.fileTypes != null && fileTypes != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileTypes.compareTo(fileTypes) != 0) {
                this.fileTypes = fileTypes;
                if (!this.toUpdateCols.contains("FILE_TYPES")) {
                    this.toUpdateCols.add("FILE_TYPES");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileTypes = fileTypes;
            if (!this.toUpdateCols.contains("FILE_TYPES")) {
                this.toUpdateCols.add("FILE_TYPES");
            }
        }
        return this;
    }

    /**
     * 文件最大KB。
     */
    private Integer fileMaxKb;

    /**
     * 获取：文件最大KB。
     */
    public Integer getFileMaxKb() {
        return this.fileMaxKb;
    }

    /**
     * 设置：文件最大KB。
     */
    public AdAtt setFileMaxKb(Integer fileMaxKb) {
        if (this.fileMaxKb == null && fileMaxKb == null) {
            // 均为null，不做处理。
        } else if (this.fileMaxKb != null && fileMaxKb != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileMaxKb.compareTo(fileMaxKb) != 0) {
                this.fileMaxKb = fileMaxKb;
                if (!this.toUpdateCols.contains("FILE_MAX_KB")) {
                    this.toUpdateCols.add("FILE_MAX_KB");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileMaxKb = fileMaxKb;
            if (!this.toUpdateCols.contains("FILE_MAX_KB")) {
                this.toUpdateCols.add("FILE_MAX_KB");
            }
        }
        return this;
    }

    /**
     * 文件允许批量上传。
     */
    private Boolean fileIsMulti;

    /**
     * 获取：文件允许批量上传。
     */
    public Boolean getFileIsMulti() {
        return this.fileIsMulti;
    }

    /**
     * 设置：文件允许批量上传。
     */
    public AdAtt setFileIsMulti(Boolean fileIsMulti) {
        if (this.fileIsMulti == null && fileIsMulti == null) {
            // 均为null，不做处理。
        } else if (this.fileIsMulti != null && fileIsMulti != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIsMulti.compareTo(fileIsMulti) != 0) {
                this.fileIsMulti = fileIsMulti;
                if (!this.toUpdateCols.contains("FILE_IS_MULTI")) {
                    this.toUpdateCols.add("FILE_IS_MULTI");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIsMulti = fileIsMulti;
            if (!this.toUpdateCols.contains("FILE_IS_MULTI")) {
                this.toUpdateCols.add("FILE_IS_MULTI");
            }
        }
        return this;
    }

    /**
     * 文件是否作为图片查看。
     */
    private Boolean fileViewAsImg;

    /**
     * 获取：文件是否作为图片查看。
     */
    public Boolean getFileViewAsImg() {
        return this.fileViewAsImg;
    }

    /**
     * 设置：文件是否作为图片查看。
     */
    public AdAtt setFileViewAsImg(Boolean fileViewAsImg) {
        if (this.fileViewAsImg == null && fileViewAsImg == null) {
            // 均为null，不做处理。
        } else if (this.fileViewAsImg != null && fileViewAsImg != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileViewAsImg.compareTo(fileViewAsImg) != 0) {
                this.fileViewAsImg = fileViewAsImg;
                if (!this.toUpdateCols.contains("FILE_VIEW_AS_IMG")) {
                    this.toUpdateCols.add("FILE_VIEW_AS_IMG");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileViewAsImg = fileViewAsImg;
            if (!this.toUpdateCols.contains("FILE_VIEW_AS_IMG")) {
                this.toUpdateCols.add("FILE_VIEW_AS_IMG");
            }
        }
        return this;
    }

    /**
     * 文件是否作为图片悬浮。
     */
    private Boolean fileHoverAsImg;

    /**
     * 获取：文件是否作为图片悬浮。
     */
    public Boolean getFileHoverAsImg() {
        return this.fileHoverAsImg;
    }

    /**
     * 设置：文件是否作为图片悬浮。
     */
    public AdAtt setFileHoverAsImg(Boolean fileHoverAsImg) {
        if (this.fileHoverAsImg == null && fileHoverAsImg == null) {
            // 均为null，不做处理。
        } else if (this.fileHoverAsImg != null && fileHoverAsImg != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileHoverAsImg.compareTo(fileHoverAsImg) != 0) {
                this.fileHoverAsImg = fileHoverAsImg;
                if (!this.toUpdateCols.contains("FILE_HOVER_AS_IMG")) {
                    this.toUpdateCols.add("FILE_HOVER_AS_IMG");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileHoverAsImg = fileHoverAsImg;
            if (!this.toUpdateCols.contains("FILE_HOVER_AS_IMG")) {
                this.toUpdateCols.add("FILE_HOVER_AS_IMG");
            }
        }
        return this;
    }

    /**
     * 文件图片宽度。
     */
    private String fileImgWidth;

    /**
     * 获取：文件图片宽度。
     */
    public String getFileImgWidth() {
        return this.fileImgWidth;
    }

    /**
     * 设置：文件图片宽度。
     */
    public AdAtt setFileImgWidth(String fileImgWidth) {
        if (this.fileImgWidth == null && fileImgWidth == null) {
            // 均为null，不做处理。
        } else if (this.fileImgWidth != null && fileImgWidth != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileImgWidth.compareTo(fileImgWidth) != 0) {
                this.fileImgWidth = fileImgWidth;
                if (!this.toUpdateCols.contains("FILE_IMG_WIDTH")) {
                    this.toUpdateCols.add("FILE_IMG_WIDTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileImgWidth = fileImgWidth;
            if (!this.toUpdateCols.contains("FILE_IMG_WIDTH")) {
                this.toUpdateCols.add("FILE_IMG_WIDTH");
            }
        }
        return this;
    }

    /**
     * 文件图片高度。
     */
    private String fileImgHeight;

    /**
     * 获取：文件图片高度。
     */
    public String getFileImgHeight() {
        return this.fileImgHeight;
    }

    /**
     * 设置：文件图片高度。
     */
    public AdAtt setFileImgHeight(String fileImgHeight) {
        if (this.fileImgHeight == null && fileImgHeight == null) {
            // 均为null，不做处理。
        } else if (this.fileImgHeight != null && fileImgHeight != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileImgHeight.compareTo(fileImgHeight) != 0) {
                this.fileImgHeight = fileImgHeight;
                if (!this.toUpdateCols.contains("FILE_IMG_HEIGHT")) {
                    this.toUpdateCols.add("FILE_IMG_HEIGHT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileImgHeight = fileImgHeight;
            if (!this.toUpdateCols.contains("FILE_IMG_HEIGHT")) {
                this.toUpdateCols.add("FILE_IMG_HEIGHT");
            }
        }
        return this;
    }

    /**
     * 是否分组。
     */
    private Boolean isGrouped;

    /**
     * 获取：是否分组。
     */
    public Boolean getIsGrouped() {
        return this.isGrouped;
    }

    /**
     * 设置：是否分组。
     */
    public AdAtt setIsGrouped(Boolean isGrouped) {
        if (this.isGrouped == null && isGrouped == null) {
            // 均为null，不做处理。
        } else if (this.isGrouped != null && isGrouped != null) {
            // 均非null，判定不等，再做处理：
            if (this.isGrouped.compareTo(isGrouped) != 0) {
                this.isGrouped = isGrouped;
                if (!this.toUpdateCols.contains("IS_GROUPED")) {
                    this.toUpdateCols.add("IS_GROUPED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isGrouped = isGrouped;
            if (!this.toUpdateCols.contains("IS_GROUPED")) {
                this.toUpdateCols.add("IS_GROUPED");
            }
        }
        return this;
    }

    /**
     * 汇总模式。
     */
    private String sumModeId;

    /**
     * 获取：汇总模式。
     */
    public String getSumModeId() {
        return this.sumModeId;
    }

    /**
     * 设置：汇总模式。
     */
    public AdAtt setSumModeId(String sumModeId) {
        if (this.sumModeId == null && sumModeId == null) {
            // 均为null，不做处理。
        } else if (this.sumModeId != null && sumModeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.sumModeId.compareTo(sumModeId) != 0) {
                this.sumModeId = sumModeId;
                if (!this.toUpdateCols.contains("SUM_MODE_ID")) {
                    this.toUpdateCols.add("SUM_MODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.sumModeId = sumModeId;
            if (!this.toUpdateCols.contains("SUM_MODE_ID")) {
                this.toUpdateCols.add("SUM_MODE_ID");
            }
        }
        return this;
    }

    /**
     * 汇总前缀。
     */
    private String sumPrefix;

    /**
     * 获取：汇总前缀。
     */
    public String getSumPrefix() {
        return this.sumPrefix;
    }

    /**
     * 设置：汇总前缀。
     */
    public AdAtt setSumPrefix(String sumPrefix) {
        if (this.sumPrefix == null && sumPrefix == null) {
            // 均为null，不做处理。
        } else if (this.sumPrefix != null && sumPrefix != null) {
            // 均非null，判定不等，再做处理：
            if (this.sumPrefix.compareTo(sumPrefix) != 0) {
                this.sumPrefix = sumPrefix;
                if (!this.toUpdateCols.contains("SUM_PREFIX")) {
                    this.toUpdateCols.add("SUM_PREFIX");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.sumPrefix = sumPrefix;
            if (!this.toUpdateCols.contains("SUM_PREFIX")) {
                this.toUpdateCols.add("SUM_PREFIX");
            }
        }
        return this;
    }

    /**
     * 汇总后缀。
     */
    private String sumSuffix;

    /**
     * 获取：汇总后缀。
     */
    public String getSumSuffix() {
        return this.sumSuffix;
    }

    /**
     * 设置：汇总后缀。
     */
    public AdAtt setSumSuffix(String sumSuffix) {
        if (this.sumSuffix == null && sumSuffix == null) {
            // 均为null，不做处理。
        } else if (this.sumSuffix != null && sumSuffix != null) {
            // 均非null，判定不等，再做处理：
            if (this.sumSuffix.compareTo(sumSuffix) != 0) {
                this.sumSuffix = sumSuffix;
                if (!this.toUpdateCols.contains("SUM_SUFFIX")) {
                    this.toUpdateCols.add("SUM_SUFFIX");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.sumSuffix = sumSuffix;
            if (!this.toUpdateCols.contains("SUM_SUFFIX")) {
                this.toUpdateCols.add("SUM_SUFFIX");
            }
        }
        return this;
    }

    /**
     * 是否隐藏分组汇总。
     */
    private Boolean isGroupSumHidden;

    /**
     * 获取：是否隐藏分组汇总。
     */
    public Boolean getIsGroupSumHidden() {
        return this.isGroupSumHidden;
    }

    /**
     * 设置：是否隐藏分组汇总。
     */
    public AdAtt setIsGroupSumHidden(Boolean isGroupSumHidden) {
        if (this.isGroupSumHidden == null && isGroupSumHidden == null) {
            // 均为null，不做处理。
        } else if (this.isGroupSumHidden != null && isGroupSumHidden != null) {
            // 均非null，判定不等，再做处理：
            if (this.isGroupSumHidden.compareTo(isGroupSumHidden) != 0) {
                this.isGroupSumHidden = isGroupSumHidden;
                if (!this.toUpdateCols.contains("IS_GROUP_SUM_HIDDEN")) {
                    this.toUpdateCols.add("IS_GROUP_SUM_HIDDEN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isGroupSumHidden = isGroupSumHidden;
            if (!this.toUpdateCols.contains("IS_GROUP_SUM_HIDDEN")) {
                this.toUpdateCols.add("IS_GROUP_SUM_HIDDEN");
            }
        }
        return this;
    }

    /**
     * 是否隐藏总计汇总。
     */
    private Boolean isTtlSumHidden;

    /**
     * 获取：是否隐藏总计汇总。
     */
    public Boolean getIsTtlSumHidden() {
        return this.isTtlSumHidden;
    }

    /**
     * 设置：是否隐藏总计汇总。
     */
    public AdAtt setIsTtlSumHidden(Boolean isTtlSumHidden) {
        if (this.isTtlSumHidden == null && isTtlSumHidden == null) {
            // 均为null，不做处理。
        } else if (this.isTtlSumHidden != null && isTtlSumHidden != null) {
            // 均非null，判定不等，再做处理：
            if (this.isTtlSumHidden.compareTo(isTtlSumHidden) != 0) {
                this.isTtlSumHidden = isTtlSumHidden;
                if (!this.toUpdateCols.contains("IS_TTL_SUM_HIDDEN")) {
                    this.toUpdateCols.add("IS_TTL_SUM_HIDDEN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isTtlSumHidden = isTtlSumHidden;
            if (!this.toUpdateCols.contains("IS_TTL_SUM_HIDDEN")) {
                this.toUpdateCols.add("IS_TTL_SUM_HIDDEN");
            }
        }
        return this;
    }

    /**
     * 横向对齐。
     */
    private String hAlign;

    /**
     * 获取：横向对齐。
     */
    public String getHAlign() {
        return this.hAlign;
    }

    /**
     * 设置：横向对齐。
     */
    public AdAtt setHAlign(String hAlign) {
        if (this.hAlign == null && hAlign == null) {
            // 均为null，不做处理。
        } else if (this.hAlign != null && hAlign != null) {
            // 均非null，判定不等，再做处理：
            if (this.hAlign.compareTo(hAlign) != 0) {
                this.hAlign = hAlign;
                if (!this.toUpdateCols.contains("H_ALIGN")) {
                    this.toUpdateCols.add("H_ALIGN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hAlign = hAlign;
            if (!this.toUpdateCols.contains("H_ALIGN")) {
                this.toUpdateCols.add("H_ALIGN");
            }
        }
        return this;
    }

    /**
     * 是否固定。
     */
    private Boolean isFixed;

    /**
     * 获取：是否固定。
     */
    public Boolean getIsFixed() {
        return this.isFixed;
    }

    /**
     * 设置：是否固定。
     */
    public AdAtt setIsFixed(Boolean isFixed) {
        if (this.isFixed == null && isFixed == null) {
            // 均为null，不做处理。
        } else if (this.isFixed != null && isFixed != null) {
            // 均非null，判定不等，再做处理：
            if (this.isFixed.compareTo(isFixed) != 0) {
                this.isFixed = isFixed;
                if (!this.toUpdateCols.contains("IS_FIXED")) {
                    this.toUpdateCols.add("IS_FIXED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isFixed = isFixed;
            if (!this.toUpdateCols.contains("IS_FIXED")) {
                this.toUpdateCols.add("IS_FIXED");
            }
        }
        return this;
    }

    /**
     * 显示格式。
     */
    private String displayFormat;

    /**
     * 获取：显示格式。
     */
    public String getDisplayFormat() {
        return this.displayFormat;
    }

    /**
     * 设置：显示格式。
     */
    public AdAtt setDisplayFormat(String displayFormat) {
        if (this.displayFormat == null && displayFormat == null) {
            // 均为null，不做处理。
        } else if (this.displayFormat != null && displayFormat != null) {
            // 均非null，判定不等，再做处理：
            if (this.displayFormat.compareTo(displayFormat) != 0) {
                this.displayFormat = displayFormat;
                if (!this.toUpdateCols.contains("DISPLAY_FORMAT")) {
                    this.toUpdateCols.add("DISPLAY_FORMAT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.displayFormat = displayFormat;
            if (!this.toUpdateCols.contains("DISPLAY_FORMAT")) {
                this.toUpdateCols.add("DISPLAY_FORMAT");
            }
        }
        return this;
    }

    /**
     * 变量名称。
     */
    private String varName;

    /**
     * 获取：变量名称。
     */
    public String getVarName() {
        return this.varName;
    }

    /**
     * 设置：变量名称。
     */
    public AdAtt setVarName(String varName) {
        if (this.varName == null && varName == null) {
            // 均为null，不做处理。
        } else if (this.varName != null && varName != null) {
            // 均非null，判定不等，再做处理：
            if (this.varName.compareTo(varName) != 0) {
                this.varName = varName;
                if (!this.toUpdateCols.contains("VAR_NAME")) {
                    this.toUpdateCols.add("VAR_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.varName = varName;
            if (!this.toUpdateCols.contains("VAR_NAME")) {
                this.toUpdateCols.add("VAR_NAME");
            }
        }
        return this;
    }

    /**
     * 引用的实体视图。
     */
    private String refedSevId;

    /**
     * 获取：引用的实体视图。
     */
    public String getRefedSevId() {
        return this.refedSevId;
    }

    /**
     * 设置：引用的实体视图。
     */
    public AdAtt setRefedSevId(String refedSevId) {
        if (this.refedSevId == null && refedSevId == null) {
            // 均为null，不做处理。
        } else if (this.refedSevId != null && refedSevId != null) {
            // 均非null，判定不等，再做处理：
            if (this.refedSevId.compareTo(refedSevId) != 0) {
                this.refedSevId = refedSevId;
                if (!this.toUpdateCols.contains("REFED_SEV_ID")) {
                    this.toUpdateCols.add("REFED_SEV_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.refedSevId = refedSevId;
            if (!this.toUpdateCols.contains("REFED_SEV_ID")) {
                this.toUpdateCols.add("REFED_SEV_ID");
            }
        }
        return this;
    }

    /**
     * 引用的视图部分列表。
     */
    private String refedVpIds;

    /**
     * 获取：引用的视图部分列表。
     */
    public String getRefedVpIds() {
        return this.refedVpIds;
    }

    /**
     * 设置：引用的视图部分列表。
     */
    public AdAtt setRefedVpIds(String refedVpIds) {
        if (this.refedVpIds == null && refedVpIds == null) {
            // 均为null，不做处理。
        } else if (this.refedVpIds != null && refedVpIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.refedVpIds.compareTo(refedVpIds) != 0) {
                this.refedVpIds = refedVpIds;
                if (!this.toUpdateCols.contains("REFED_VP_IDS")) {
                    this.toUpdateCols.add("REFED_VP_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.refedVpIds = refedVpIds;
            if (!this.toUpdateCols.contains("REFED_VP_IDS")) {
                this.toUpdateCols.add("REFED_VP_IDS");
            }
        }
        return this;
    }

    /**
     * 引用的WHERE语句。
     */
    private String refedWhereClause;

    /**
     * 获取：引用的WHERE语句。
     */
    public String getRefedWhereClause() {
        return this.refedWhereClause;
    }

    /**
     * 设置：引用的WHERE语句。
     */
    public AdAtt setRefedWhereClause(String refedWhereClause) {
        if (this.refedWhereClause == null && refedWhereClause == null) {
            // 均为null，不做处理。
        } else if (this.refedWhereClause != null && refedWhereClause != null) {
            // 均非null，判定不等，再做处理：
            if (this.refedWhereClause.compareTo(refedWhereClause) != 0) {
                this.refedWhereClause = refedWhereClause;
                if (!this.toUpdateCols.contains("REFED_WHERE_CLAUSE")) {
                    this.toUpdateCols.add("REFED_WHERE_CLAUSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.refedWhereClause = refedWhereClause;
            if (!this.toUpdateCols.contains("REFED_WHERE_CLAUSE")) {
                this.toUpdateCols.add("REFED_WHERE_CLAUSE");
            }
        }
        return this;
    }

    /**
     * 引用删除模式。
     */
    private String refedOnDelMode;

    /**
     * 获取：引用删除模式。
     */
    public String getRefedOnDelMode() {
        return this.refedOnDelMode;
    }

    /**
     * 设置：引用删除模式。
     */
    public AdAtt setRefedOnDelMode(String refedOnDelMode) {
        if (this.refedOnDelMode == null && refedOnDelMode == null) {
            // 均为null，不做处理。
        } else if (this.refedOnDelMode != null && refedOnDelMode != null) {
            // 均非null，判定不等，再做处理：
            if (this.refedOnDelMode.compareTo(refedOnDelMode) != 0) {
                this.refedOnDelMode = refedOnDelMode;
                if (!this.toUpdateCols.contains("REFED_ON_DEL_MODE")) {
                    this.toUpdateCols.add("REFED_ON_DEL_MODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.refedOnDelMode = refedOnDelMode;
            if (!this.toUpdateCols.contains("REFED_ON_DEL_MODE")) {
                this.toUpdateCols.add("REFED_ON_DEL_MODE");
            }
        }
        return this;
    }

    /**
     * 引用的启用缓存。
     */
    private Boolean refedCacheEnabled;

    /**
     * 获取：引用的启用缓存。
     */
    public Boolean getRefedCacheEnabled() {
        return this.refedCacheEnabled;
    }

    /**
     * 设置：引用的启用缓存。
     */
    public AdAtt setRefedCacheEnabled(Boolean refedCacheEnabled) {
        if (this.refedCacheEnabled == null && refedCacheEnabled == null) {
            // 均为null，不做处理。
        } else if (this.refedCacheEnabled != null && refedCacheEnabled != null) {
            // 均非null，判定不等，再做处理：
            if (this.refedCacheEnabled.compareTo(refedCacheEnabled) != 0) {
                this.refedCacheEnabled = refedCacheEnabled;
                if (!this.toUpdateCols.contains("REFED_CACHE_ENABLED")) {
                    this.toUpdateCols.add("REFED_CACHE_ENABLED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.refedCacheEnabled = refedCacheEnabled;
            if (!this.toUpdateCols.contains("REFED_CACHE_ENABLED")) {
                this.toUpdateCols.add("REFED_CACHE_ENABLED");
            }
        }
        return this;
    }

    /**
     * 引用的下拉禁用。
     */
    private Boolean refedDropdownDisabled;

    /**
     * 获取：引用的下拉禁用。
     */
    public Boolean getRefedDropdownDisabled() {
        return this.refedDropdownDisabled;
    }

    /**
     * 设置：引用的下拉禁用。
     */
    public AdAtt setRefedDropdownDisabled(Boolean refedDropdownDisabled) {
        if (this.refedDropdownDisabled == null && refedDropdownDisabled == null) {
            // 均为null，不做处理。
        } else if (this.refedDropdownDisabled != null && refedDropdownDisabled != null) {
            // 均非null，判定不等，再做处理：
            if (this.refedDropdownDisabled.compareTo(refedDropdownDisabled) != 0) {
                this.refedDropdownDisabled = refedDropdownDisabled;
                if (!this.toUpdateCols.contains("REFED_DROPDOWN_DISABLED")) {
                    this.toUpdateCols.add("REFED_DROPDOWN_DISABLED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.refedDropdownDisabled = refedDropdownDisabled;
            if (!this.toUpdateCols.contains("REFED_DROPDOWN_DISABLED")) {
                this.toUpdateCols.add("REFED_DROPDOWN_DISABLED");
            }
        }
        return this;
    }

    /**
     * 引用的搜索禁用。
     */
    private Boolean refedSearchDisabled;

    /**
     * 获取：引用的搜索禁用。
     */
    public Boolean getRefedSearchDisabled() {
        return this.refedSearchDisabled;
    }

    /**
     * 设置：引用的搜索禁用。
     */
    public AdAtt setRefedSearchDisabled(Boolean refedSearchDisabled) {
        if (this.refedSearchDisabled == null && refedSearchDisabled == null) {
            // 均为null，不做处理。
        } else if (this.refedSearchDisabled != null && refedSearchDisabled != null) {
            // 均非null，判定不等，再做处理：
            if (this.refedSearchDisabled.compareTo(refedSearchDisabled) != 0) {
                this.refedSearchDisabled = refedSearchDisabled;
                if (!this.toUpdateCols.contains("REFED_SEARCH_DISABLED")) {
                    this.toUpdateCols.add("REFED_SEARCH_DISABLED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.refedSearchDisabled = refedSearchDisabled;
            if (!this.toUpdateCols.contains("REFED_SEARCH_DISABLED")) {
                this.toUpdateCols.add("REFED_SEARCH_DISABLED");
            }
        }
        return this;
    }

    /**
     * 引用的弹出宽度。
     */
    private String refedPopupWidth;

    /**
     * 获取：引用的弹出宽度。
     */
    public String getRefedPopupWidth() {
        return this.refedPopupWidth;
    }

    /**
     * 设置：引用的弹出宽度。
     */
    public AdAtt setRefedPopupWidth(String refedPopupWidth) {
        if (this.refedPopupWidth == null && refedPopupWidth == null) {
            // 均为null，不做处理。
        } else if (this.refedPopupWidth != null && refedPopupWidth != null) {
            // 均非null，判定不等，再做处理：
            if (this.refedPopupWidth.compareTo(refedPopupWidth) != 0) {
                this.refedPopupWidth = refedPopupWidth;
                if (!this.toUpdateCols.contains("REFED_POPUP_WIDTH")) {
                    this.toUpdateCols.add("REFED_POPUP_WIDTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.refedPopupWidth = refedPopupWidth;
            if (!this.toUpdateCols.contains("REFED_POPUP_WIDTH")) {
                this.toUpdateCols.add("REFED_POPUP_WIDTH");
            }
        }
        return this;
    }

    /**
     * 引用的弹出高度。
     */
    private String refedPopupHeight;

    /**
     * 获取：引用的弹出高度。
     */
    public String getRefedPopupHeight() {
        return this.refedPopupHeight;
    }

    /**
     * 设置：引用的弹出高度。
     */
    public AdAtt setRefedPopupHeight(String refedPopupHeight) {
        if (this.refedPopupHeight == null && refedPopupHeight == null) {
            // 均为null，不做处理。
        } else if (this.refedPopupHeight != null && refedPopupHeight != null) {
            // 均非null，判定不等，再做处理：
            if (this.refedPopupHeight.compareTo(refedPopupHeight) != 0) {
                this.refedPopupHeight = refedPopupHeight;
                if (!this.toUpdateCols.contains("REFED_POPUP_HEIGHT")) {
                    this.toUpdateCols.add("REFED_POPUP_HEIGHT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.refedPopupHeight = refedPopupHeight;
            if (!this.toUpdateCols.contains("REFED_POPUP_HEIGHT")) {
                this.toUpdateCols.add("REFED_POPUP_HEIGHT");
            }
        }
        return this;
    }

    /**
     * 引用的可选择逻辑。
     */
    private String refedSelectableLogic;

    /**
     * 获取：引用的可选择逻辑。
     */
    public String getRefedSelectableLogic() {
        return this.refedSelectableLogic;
    }

    /**
     * 设置：引用的可选择逻辑。
     */
    public AdAtt setRefedSelectableLogic(String refedSelectableLogic) {
        if (this.refedSelectableLogic == null && refedSelectableLogic == null) {
            // 均为null，不做处理。
        } else if (this.refedSelectableLogic != null && refedSelectableLogic != null) {
            // 均非null，判定不等，再做处理：
            if (this.refedSelectableLogic.compareTo(refedSelectableLogic) != 0) {
                this.refedSelectableLogic = refedSelectableLogic;
                if (!this.toUpdateCols.contains("REFED_SELECTABLE_LOGIC")) {
                    this.toUpdateCols.add("REFED_SELECTABLE_LOGIC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.refedSelectableLogic = refedSelectableLogic;
            if (!this.toUpdateCols.contains("REFED_SELECTABLE_LOGIC")) {
                this.toUpdateCols.add("REFED_SELECTABLE_LOGIC");
            }
        }
        return this;
    }

    /**
     * 定义SQL。
     */
    private String defSql;

    /**
     * 获取：定义SQL。
     */
    public String getDefSql() {
        return this.defSql;
    }

    /**
     * 设置：定义SQL。
     */
    public AdAtt setDefSql(String defSql) {
        if (this.defSql == null && defSql == null) {
            // 均为null，不做处理。
        } else if (this.defSql != null && defSql != null) {
            // 均非null，判定不等，再做处理：
            if (this.defSql.compareTo(defSql) != 0) {
                this.defSql = defSql;
                if (!this.toUpdateCols.contains("DEF_SQL")) {
                    this.toUpdateCols.add("DEF_SQL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.defSql = defSql;
            if (!this.toUpdateCols.contains("DEF_SQL")) {
                this.toUpdateCols.add("DEF_SQL");
            }
        }
        return this;
    }

    /**
     * 是否必填。
     */
    private Boolean isMandatory;

    /**
     * 获取：是否必填。
     */
    public Boolean getIsMandatory() {
        return this.isMandatory;
    }

    /**
     * 设置：是否必填。
     */
    public AdAtt setIsMandatory(Boolean isMandatory) {
        if (this.isMandatory == null && isMandatory == null) {
            // 均为null，不做处理。
        } else if (this.isMandatory != null && isMandatory != null) {
            // 均非null，判定不等，再做处理：
            if (this.isMandatory.compareTo(isMandatory) != 0) {
                this.isMandatory = isMandatory;
                if (!this.toUpdateCols.contains("IS_MANDATORY")) {
                    this.toUpdateCols.add("IS_MANDATORY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isMandatory = isMandatory;
            if (!this.toUpdateCols.contains("IS_MANDATORY")) {
                this.toUpdateCols.add("IS_MANDATORY");
            }
        }
        return this;
    }

    /**
     * 索引类型。
     */
    private String adIndexTypeId;

    /**
     * 获取：索引类型。
     */
    public String getAdIndexTypeId() {
        return this.adIndexTypeId;
    }

    /**
     * 设置：索引类型。
     */
    public AdAtt setAdIndexTypeId(String adIndexTypeId) {
        if (this.adIndexTypeId == null && adIndexTypeId == null) {
            // 均为null，不做处理。
        } else if (this.adIndexTypeId != null && adIndexTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adIndexTypeId.compareTo(adIndexTypeId) != 0) {
                this.adIndexTypeId = adIndexTypeId;
                if (!this.toUpdateCols.contains("AD_INDEX_TYPE_ID")) {
                    this.toUpdateCols.add("AD_INDEX_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adIndexTypeId = adIndexTypeId;
            if (!this.toUpdateCols.contains("AD_INDEX_TYPE_ID")) {
                this.toUpdateCols.add("AD_INDEX_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 创建外键。
     */
    private Boolean createFk;

    /**
     * 获取：创建外键。
     */
    public Boolean getCreateFk() {
        return this.createFk;
    }

    /**
     * 设置：创建外键。
     */
    public AdAtt setCreateFk(Boolean createFk) {
        if (this.createFk == null && createFk == null) {
            // 均为null，不做处理。
        } else if (this.createFk != null && createFk != null) {
            // 均非null，判定不等，再做处理：
            if (this.createFk.compareTo(createFk) != 0) {
                this.createFk = createFk;
                if (!this.toUpdateCols.contains("CREATE_FK")) {
                    this.toUpdateCols.add("CREATE_FK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.createFk = createFk;
            if (!this.toUpdateCols.contains("CREATE_FK")) {
                this.toUpdateCols.add("CREATE_FK");
            }
        }
        return this;
    }

    /**
     * 表单项标题隐藏。
     */
    private Boolean formItemTitleHidden;

    /**
     * 获取：表单项标题隐藏。
     */
    public Boolean getFormItemTitleHidden() {
        return this.formItemTitleHidden;
    }

    /**
     * 设置：表单项标题隐藏。
     */
    public AdAtt setFormItemTitleHidden(Boolean formItemTitleHidden) {
        if (this.formItemTitleHidden == null && formItemTitleHidden == null) {
            // 均为null，不做处理。
        } else if (this.formItemTitleHidden != null && formItemTitleHidden != null) {
            // 均非null，判定不等，再做处理：
            if (this.formItemTitleHidden.compareTo(formItemTitleHidden) != 0) {
                this.formItemTitleHidden = formItemTitleHidden;
                if (!this.toUpdateCols.contains("FORM_ITEM_TITLE_HIDDEN")) {
                    this.toUpdateCols.add("FORM_ITEM_TITLE_HIDDEN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.formItemTitleHidden = formItemTitleHidden;
            if (!this.toUpdateCols.contains("FORM_ITEM_TITLE_HIDDEN")) {
                this.toUpdateCols.add("FORM_ITEM_TITLE_HIDDEN");
            }
        }
        return this;
    }

    /**
     * 表单项标题在上。
     */
    private Boolean formItemTitleTop;

    /**
     * 获取：表单项标题在上。
     */
    public Boolean getFormItemTitleTop() {
        return this.formItemTitleTop;
    }

    /**
     * 设置：表单项标题在上。
     */
    public AdAtt setFormItemTitleTop(Boolean formItemTitleTop) {
        if (this.formItemTitleTop == null && formItemTitleTop == null) {
            // 均为null，不做处理。
        } else if (this.formItemTitleTop != null && formItemTitleTop != null) {
            // 均非null，判定不等，再做处理：
            if (this.formItemTitleTop.compareTo(formItemTitleTop) != 0) {
                this.formItemTitleTop = formItemTitleTop;
                if (!this.toUpdateCols.contains("FORM_ITEM_TITLE_TOP")) {
                    this.toUpdateCols.add("FORM_ITEM_TITLE_TOP");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.formItemTitleTop = formItemTitleTop;
            if (!this.toUpdateCols.contains("FORM_ITEM_TITLE_TOP")) {
                this.toUpdateCols.add("FORM_ITEM_TITLE_TOP");
            }
        }
        return this;
    }

    /**
     * 表单项标题换行。
     */
    private Boolean formItemTitleWrap;

    /**
     * 获取：表单项标题换行。
     */
    public Boolean getFormItemTitleWrap() {
        return this.formItemTitleWrap;
    }

    /**
     * 设置：表单项标题换行。
     */
    public AdAtt setFormItemTitleWrap(Boolean formItemTitleWrap) {
        if (this.formItemTitleWrap == null && formItemTitleWrap == null) {
            // 均为null，不做处理。
        } else if (this.formItemTitleWrap != null && formItemTitleWrap != null) {
            // 均非null，判定不等，再做处理：
            if (this.formItemTitleWrap.compareTo(formItemTitleWrap) != 0) {
                this.formItemTitleWrap = formItemTitleWrap;
                if (!this.toUpdateCols.contains("FORM_ITEM_TITLE_WRAP")) {
                    this.toUpdateCols.add("FORM_ITEM_TITLE_WRAP");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.formItemTitleWrap = formItemTitleWrap;
            if (!this.toUpdateCols.contains("FORM_ITEM_TITLE_WRAP")) {
                this.toUpdateCols.add("FORM_ITEM_TITLE_WRAP");
            }
        }
        return this;
    }

    /**
     * 表单项宽度。
     */
    private String formItemWidth;

    /**
     * 获取：表单项宽度。
     */
    public String getFormItemWidth() {
        return this.formItemWidth;
    }

    /**
     * 设置：表单项宽度。
     */
    public AdAtt setFormItemWidth(String formItemWidth) {
        if (this.formItemWidth == null && formItemWidth == null) {
            // 均为null，不做处理。
        } else if (this.formItemWidth != null && formItemWidth != null) {
            // 均非null，判定不等，再做处理：
            if (this.formItemWidth.compareTo(formItemWidth) != 0) {
                this.formItemWidth = formItemWidth;
                if (!this.toUpdateCols.contains("FORM_ITEM_WIDTH")) {
                    this.toUpdateCols.add("FORM_ITEM_WIDTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.formItemWidth = formItemWidth;
            if (!this.toUpdateCols.contains("FORM_ITEM_WIDTH")) {
                this.toUpdateCols.add("FORM_ITEM_WIDTH");
            }
        }
        return this;
    }

    /**
     * 表单项高度。
     */
    private String formItemHeight;

    /**
     * 获取：表单项高度。
     */
    public String getFormItemHeight() {
        return this.formItemHeight;
    }

    /**
     * 设置：表单项高度。
     */
    public AdAtt setFormItemHeight(String formItemHeight) {
        if (this.formItemHeight == null && formItemHeight == null) {
            // 均为null，不做处理。
        } else if (this.formItemHeight != null && formItemHeight != null) {
            // 均非null，判定不等，再做处理：
            if (this.formItemHeight.compareTo(formItemHeight) != 0) {
                this.formItemHeight = formItemHeight;
                if (!this.toUpdateCols.contains("FORM_ITEM_HEIGHT")) {
                    this.toUpdateCols.add("FORM_ITEM_HEIGHT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.formItemHeight = formItemHeight;
            if (!this.toUpdateCols.contains("FORM_ITEM_HEIGHT")) {
                this.toUpdateCols.add("FORM_ITEM_HEIGHT");
            }
        }
        return this;
    }

    /**
     * 表单项行跨度。
     */
    private Integer formItemRowSpan;

    /**
     * 获取：表单项行跨度。
     */
    public Integer getFormItemRowSpan() {
        return this.formItemRowSpan;
    }

    /**
     * 设置：表单项行跨度。
     */
    public AdAtt setFormItemRowSpan(Integer formItemRowSpan) {
        if (this.formItemRowSpan == null && formItemRowSpan == null) {
            // 均为null，不做处理。
        } else if (this.formItemRowSpan != null && formItemRowSpan != null) {
            // 均非null，判定不等，再做处理：
            if (this.formItemRowSpan.compareTo(formItemRowSpan) != 0) {
                this.formItemRowSpan = formItemRowSpan;
                if (!this.toUpdateCols.contains("FORM_ITEM_ROW_SPAN")) {
                    this.toUpdateCols.add("FORM_ITEM_ROW_SPAN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.formItemRowSpan = formItemRowSpan;
            if (!this.toUpdateCols.contains("FORM_ITEM_ROW_SPAN")) {
                this.toUpdateCols.add("FORM_ITEM_ROW_SPAN");
            }
        }
        return this;
    }

    /**
     * 表单项列跨度。
     */
    private Integer formItemColSpan;

    /**
     * 获取：表单项列跨度。
     */
    public Integer getFormItemColSpan() {
        return this.formItemColSpan;
    }

    /**
     * 设置：表单项列跨度。
     */
    public AdAtt setFormItemColSpan(Integer formItemColSpan) {
        if (this.formItemColSpan == null && formItemColSpan == null) {
            // 均为null，不做处理。
        } else if (this.formItemColSpan != null && formItemColSpan != null) {
            // 均非null，判定不等，再做处理：
            if (this.formItemColSpan.compareTo(formItemColSpan) != 0) {
                this.formItemColSpan = formItemColSpan;
                if (!this.toUpdateCols.contains("FORM_ITEM_COL_SPAN")) {
                    this.toUpdateCols.add("FORM_ITEM_COL_SPAN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.formItemColSpan = formItemColSpan;
            if (!this.toUpdateCols.contains("FORM_ITEM_COL_SPAN")) {
                this.toUpdateCols.add("FORM_ITEM_COL_SPAN");
            }
        }
        return this;
    }

    /**
     * 在列表页隐藏。
     */
    private Boolean hideInList;

    /**
     * 获取：在列表页隐藏。
     */
    public Boolean getHideInList() {
        return this.hideInList;
    }

    /**
     * 设置：在列表页隐藏。
     */
    public AdAtt setHideInList(Boolean hideInList) {
        if (this.hideInList == null && hideInList == null) {
            // 均为null，不做处理。
        } else if (this.hideInList != null && hideInList != null) {
            // 均非null，判定不等，再做处理：
            if (this.hideInList.compareTo(hideInList) != 0) {
                this.hideInList = hideInList;
                if (!this.toUpdateCols.contains("HIDE_IN_LIST")) {
                    this.toUpdateCols.add("HIDE_IN_LIST");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hideInList = hideInList;
            if (!this.toUpdateCols.contains("HIDE_IN_LIST")) {
                this.toUpdateCols.add("HIDE_IN_LIST");
            }
        }
        return this;
    }

    /**
     * 在详情页隐藏。
     */
    private Boolean hideInDtl;

    /**
     * 获取：在详情页隐藏。
     */
    public Boolean getHideInDtl() {
        return this.hideInDtl;
    }

    /**
     * 设置：在详情页隐藏。
     */
    public AdAtt setHideInDtl(Boolean hideInDtl) {
        if (this.hideInDtl == null && hideInDtl == null) {
            // 均为null，不做处理。
        } else if (this.hideInDtl != null && hideInDtl != null) {
            // 均非null，判定不等，再做处理：
            if (this.hideInDtl.compareTo(hideInDtl) != 0) {
                this.hideInDtl = hideInDtl;
                if (!this.toUpdateCols.contains("HIDE_IN_DTL")) {
                    this.toUpdateCols.add("HIDE_IN_DTL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hideInDtl = hideInDtl;
            if (!this.toUpdateCols.contains("HIDE_IN_DTL")) {
                this.toUpdateCols.add("HIDE_IN_DTL");
            }
        }
        return this;
    }

    /**
     * 在打印时隐藏。
     */
    private Boolean hideInPrint;

    /**
     * 获取：在打印时隐藏。
     */
    public Boolean getHideInPrint() {
        return this.hideInPrint;
    }

    /**
     * 设置：在打印时隐藏。
     */
    public AdAtt setHideInPrint(Boolean hideInPrint) {
        if (this.hideInPrint == null && hideInPrint == null) {
            // 均为null，不做处理。
        } else if (this.hideInPrint != null && hideInPrint != null) {
            // 均非null，判定不等，再做处理：
            if (this.hideInPrint.compareTo(hideInPrint) != 0) {
                this.hideInPrint = hideInPrint;
                if (!this.toUpdateCols.contains("HIDE_IN_PRINT")) {
                    this.toUpdateCols.add("HIDE_IN_PRINT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hideInPrint = hideInPrint;
            if (!this.toUpdateCols.contains("HIDE_IN_PRINT")) {
                this.toUpdateCols.add("HIDE_IN_PRINT");
            }
        }
        return this;
    }

    /**
     * 在简单过滤里隐藏。
     */
    private Boolean hideInSimpleFilter;

    /**
     * 获取：在简单过滤里隐藏。
     */
    public Boolean getHideInSimpleFilter() {
        return this.hideInSimpleFilter;
    }

    /**
     * 设置：在简单过滤里隐藏。
     */
    public AdAtt setHideInSimpleFilter(Boolean hideInSimpleFilter) {
        if (this.hideInSimpleFilter == null && hideInSimpleFilter == null) {
            // 均为null，不做处理。
        } else if (this.hideInSimpleFilter != null && hideInSimpleFilter != null) {
            // 均非null，判定不等，再做处理：
            if (this.hideInSimpleFilter.compareTo(hideInSimpleFilter) != 0) {
                this.hideInSimpleFilter = hideInSimpleFilter;
                if (!this.toUpdateCols.contains("HIDE_IN_SIMPLE_FILTER")) {
                    this.toUpdateCols.add("HIDE_IN_SIMPLE_FILTER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hideInSimpleFilter = hideInSimpleFilter;
            if (!this.toUpdateCols.contains("HIDE_IN_SIMPLE_FILTER")) {
                this.toUpdateCols.add("HIDE_IN_SIMPLE_FILTER");
            }
        }
        return this;
    }

    /**
     * 在复杂过滤里隐藏。
     */
    private Boolean hideInComplexFilter;

    /**
     * 获取：在复杂过滤里隐藏。
     */
    public Boolean getHideInComplexFilter() {
        return this.hideInComplexFilter;
    }

    /**
     * 设置：在复杂过滤里隐藏。
     */
    public AdAtt setHideInComplexFilter(Boolean hideInComplexFilter) {
        if (this.hideInComplexFilter == null && hideInComplexFilter == null) {
            // 均为null，不做处理。
        } else if (this.hideInComplexFilter != null && hideInComplexFilter != null) {
            // 均非null，判定不等，再做处理：
            if (this.hideInComplexFilter.compareTo(hideInComplexFilter) != 0) {
                this.hideInComplexFilter = hideInComplexFilter;
                if (!this.toUpdateCols.contains("HIDE_IN_COMPLEX_FILTER")) {
                    this.toUpdateCols.add("HIDE_IN_COMPLEX_FILTER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hideInComplexFilter = hideInComplexFilter;
            if (!this.toUpdateCols.contains("HIDE_IN_COMPLEX_FILTER")) {
                this.toUpdateCols.add("HIDE_IN_COMPLEX_FILTER");
            }
        }
        return this;
    }

    /**
     * 存储计量单位。
     */
    private String storeUomId;

    /**
     * 获取：存储计量单位。
     */
    public String getStoreUomId() {
        return this.storeUomId;
    }

    /**
     * 设置：存储计量单位。
     */
    public AdAtt setStoreUomId(String storeUomId) {
        if (this.storeUomId == null && storeUomId == null) {
            // 均为null，不做处理。
        } else if (this.storeUomId != null && storeUomId != null) {
            // 均非null，判定不等，再做处理：
            if (this.storeUomId.compareTo(storeUomId) != 0) {
                this.storeUomId = storeUomId;
                if (!this.toUpdateCols.contains("STORE_UOM_ID")) {
                    this.toUpdateCols.add("STORE_UOM_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.storeUomId = storeUomId;
            if (!this.toUpdateCols.contains("STORE_UOM_ID")) {
                this.toUpdateCols.add("STORE_UOM_ID");
            }
        }
        return this;
    }

    /**
     * 显示计量单位。
     */
    private String displayUomId;

    /**
     * 获取：显示计量单位。
     */
    public String getDisplayUomId() {
        return this.displayUomId;
    }

    /**
     * 设置：显示计量单位。
     */
    public AdAtt setDisplayUomId(String displayUomId) {
        if (this.displayUomId == null && displayUomId == null) {
            // 均为null，不做处理。
        } else if (this.displayUomId != null && displayUomId != null) {
            // 均非null，判定不等，再做处理：
            if (this.displayUomId.compareTo(displayUomId) != 0) {
                this.displayUomId = displayUomId;
                if (!this.toUpdateCols.contains("DISPLAY_UOM_ID")) {
                    this.toUpdateCols.add("DISPLAY_UOM_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.displayUomId = displayUomId;
            if (!this.toUpdateCols.contains("DISPLAY_UOM_ID")) {
                this.toUpdateCols.add("DISPLAY_UOM_ID");
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
    public static AdAtt newData() {
        AdAtt obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static AdAtt insertData() {
        AdAtt obj = modelHelper.insertData();
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
    public static AdAtt selectById(String id, List<String> includeCols, List<String> excludeCols) {
        AdAtt obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<AdAtt> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<AdAtt> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<AdAtt> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<AdAtt> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(AdAtt fromModel, AdAtt toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}