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
 * 实体属性。
 */
public class AdEntAtt {

    /**
     * 模型助手。
     */
    private static final ModelHelper<AdEntAtt> modelHelper = new ModelHelper<>("AD_ENT_ATT", new AdEntAtt());

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

    public static final String ENT_CODE = "AD_ENT_ATT";
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
         * 实体。
         */
        public static final String AD_ENT_ID = "AD_ENT_ID";
        /**
         * 属性。
         */
        public static final String AD_ATT_ID = "AD_ATT_ID";
        /**
         * 实体属性关系。
         */
        public static final String ENT_ATT_RELATION = "ENT_ATT_RELATION";
        /**
         * 属性名称。
         */
        public static final String ATT_NAME = "ATT_NAME";
        /**
         * 属性备注。
         */
        public static final String ATT_REMARK = "ATT_REMARK";
        /**
         * 属性宽度。
         */
        public static final String ATT_WIDTH = "ATT_WIDTH";
        /**
         * 属性序号。
         */
        public static final String ATT_SEQ_NO = "ATT_SEQ_NO";
        /**
         * 属性是否默认显示。
         */
        public static final String ATT_IS_SHOWN_BY_DEFAULT = "ATT_IS_SHOWN_BY_DEFAULT";
        /**
         * 属性可改逻辑。
         */
        public static final String ATT_EDITABLE_LOGIC = "ATT_EDITABLE_LOGIC";
        /**
         * 忽略实体记录可改。
         */
        public static final String IGNORE_ENT_REC_EDITABLE = "IGNORE_ENT_REC_EDITABLE";
        /**
         * 属性必填逻辑。
         */
        public static final String ATT_MANDATORY_LOGIC = "ATT_MANDATORY_LOGIC";
        /**
         * 属性默认值逻辑。
         */
        public static final String ATT_DEFAULT_VALUE_LOGIC = "ATT_DEFAULT_VALUE_LOGIC";
        /**
         * 属性可见逻辑。
         */
        public static final String ATT_VISIBLE_LOGIC = "ATT_VISIBLE_LOGIC";
        /**
         * 属性有效逻辑。
         */
        public static final String ATT_VALID_LOGIC = "ATT_VALID_LOGIC";
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
         * 属性文件路径。
         */
        public static final String ATT_FILE_PATH_ID = "ATT_FILE_PATH_ID";
        /**
         * 属性文件最大KB。
         */
        public static final String ATT_FILE_MAX_KB = "ATT_FILE_MAX_KB";
        /**
         * 属性文件类型。
         */
        public static final String ATT_FILE_TYPES = "ATT_FILE_TYPES";
        /**
         * 属性文件允许批量上传。
         */
        public static final String ATT_FILE_IS_MULTI = "ATT_FILE_IS_MULTI";
        /**
         * 属性文件是否作为图片查看。
         */
        public static final String ATT_FILE_VIEW_AS_IMG = "ATT_FILE_VIEW_AS_IMG";
        /**
         * 属性文件图片高度。
         */
        public static final String ATT_FILE_IMG_HEIGHT = "ATT_FILE_IMG_HEIGHT";
        /**
         * 属性文件图片宽度。
         */
        public static final String ATT_FILE_IMG_WIDTH = "ATT_FILE_IMG_WIDTH";
        /**
         * 属性文件是否作为图片悬浮。
         */
        public static final String ATT_FILE_HOVER_AS_IMG = "ATT_FILE_HOVER_AS_IMG";
        /**
         * 属性是否分组。
         */
        public static final String ATT_IS_GROUPED = "ATT_IS_GROUPED";
        /**
         * 属性汇总模式。
         */
        public static final String ATT_SUM_MODE_ID = "ATT_SUM_MODE_ID";
        /**
         * 属性汇总前缀。
         */
        public static final String ATT_SUM_PREFIX = "ATT_SUM_PREFIX";
        /**
         * 属性汇总后缀。
         */
        public static final String ATT_SUM_SUFFIX = "ATT_SUM_SUFFIX";
        /**
         * 属性是否隐藏分组汇总。
         */
        public static final String ATT_IS_GROUP_SUM_HIDDEN = "ATT_IS_GROUP_SUM_HIDDEN";
        /**
         * 属性是否隐藏总计汇总。
         */
        public static final String ATT_IS_TTL_SUM_HIDDEN = "ATT_IS_TTL_SUM_HIDDEN";
        /**
         * 属性横向对齐。
         */
        public static final String ATT_H_ALIGN = "ATT_H_ALIGN";
        /**
         * 属性是否固定。
         */
        public static final String ATT_IS_FIXED = "ATT_IS_FIXED";
        /**
         * 属性显示格式。
         */
        public static final String ATT_DISPLAY_FORMAT = "ATT_DISPLAY_FORMAT";
        /**
         * 属性变量名称。
         */
        public static final String ATT_VAR_NAME = "ATT_VAR_NAME";
        /**
         * 属性引用的实体视图。
         */
        public static final String ATT_REFED_SEV_ID = "ATT_REFED_SEV_ID";
        /**
         * 属性引用的视图部分列表。
         */
        public static final String ATT_REFED_VP_IDS = "ATT_REFED_VP_IDS";
        /**
         * 属性引用的WHERE语句。
         */
        public static final String ATT_REFED_WHERE_CLAUSE = "ATT_REFED_WHERE_CLAUSE";
        /**
         * 属性引用删除模式。
         */
        public static final String ATT_REFED_ON_DEL_MODE = "ATT_REFED_ON_DEL_MODE";
        /**
         * 属性引用的启用缓存。
         */
        public static final String ATT_REFED_CACHE_ENABLED = "ATT_REFED_CACHE_ENABLED";
        /**
         * 属性引用的下拉禁用。
         */
        public static final String ATT_REFED_DROPDOWN_DISABLED = "ATT_REFED_DROPDOWN_DISABLED";
        /**
         * 属性引用的搜索禁用。
         */
        public static final String ATT_REFED_SEARCH_DISABLED = "ATT_REFED_SEARCH_DISABLED";
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
    public AdEntAtt setId(String id) {
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
    public AdEntAtt setVer(Integer ver) {
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
    public AdEntAtt setTs(LocalDateTime ts) {
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
    public AdEntAtt setIsPreset(Boolean isPreset) {
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
    public AdEntAtt setCrtDt(LocalDateTime crtDt) {
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
    public AdEntAtt setCrtUserId(String crtUserId) {
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
    public AdEntAtt setLastModiDt(LocalDateTime lastModiDt) {
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
    public AdEntAtt setLastModiUserId(String lastModiUserId) {
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
    public AdEntAtt setStatus(String status) {
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
    public AdEntAtt setLkWfInstId(String lkWfInstId) {
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
    public AdEntAtt setCode(String code) {
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
    public AdEntAtt setName(String name) {
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
    public AdEntAtt setRemark(String remark) {
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
    public AdEntAtt setAdEntId(String adEntId) {
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
     * 属性。
     */
    private String adAttId;

    /**
     * 获取：属性。
     */
    public String getAdAttId() {
        return this.adAttId;
    }

    /**
     * 设置：属性。
     */
    public AdEntAtt setAdAttId(String adAttId) {
        if (this.adAttId == null && adAttId == null) {
            // 均为null，不做处理。
        } else if (this.adAttId != null && adAttId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adAttId.compareTo(adAttId) != 0) {
                this.adAttId = adAttId;
                if (!this.toUpdateCols.contains("AD_ATT_ID")) {
                    this.toUpdateCols.add("AD_ATT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adAttId = adAttId;
            if (!this.toUpdateCols.contains("AD_ATT_ID")) {
                this.toUpdateCols.add("AD_ATT_ID");
            }
        }
        return this;
    }

    /**
     * 实体属性关系。
     */
    private String entAttRelation;

    /**
     * 获取：实体属性关系。
     */
    public String getEntAttRelation() {
        return this.entAttRelation;
    }

    /**
     * 设置：实体属性关系。
     */
    public AdEntAtt setEntAttRelation(String entAttRelation) {
        if (this.entAttRelation == null && entAttRelation == null) {
            // 均为null，不做处理。
        } else if (this.entAttRelation != null && entAttRelation != null) {
            // 均非null，判定不等，再做处理：
            if (this.entAttRelation.compareTo(entAttRelation) != 0) {
                this.entAttRelation = entAttRelation;
                if (!this.toUpdateCols.contains("ENT_ATT_RELATION")) {
                    this.toUpdateCols.add("ENT_ATT_RELATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.entAttRelation = entAttRelation;
            if (!this.toUpdateCols.contains("ENT_ATT_RELATION")) {
                this.toUpdateCols.add("ENT_ATT_RELATION");
            }
        }
        return this;
    }

    /**
     * 属性名称。
     */
    private String attName;

    /**
     * 获取：属性名称。
     */
    public String getAttName() {
        return this.attName;
    }

    /**
     * 设置：属性名称。
     */
    public AdEntAtt setAttName(String attName) {
        if (this.attName == null && attName == null) {
            // 均为null，不做处理。
        } else if (this.attName != null && attName != null) {
            // 均非null，判定不等，再做处理：
            if (this.attName.compareTo(attName) != 0) {
                this.attName = attName;
                if (!this.toUpdateCols.contains("ATT_NAME")) {
                    this.toUpdateCols.add("ATT_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attName = attName;
            if (!this.toUpdateCols.contains("ATT_NAME")) {
                this.toUpdateCols.add("ATT_NAME");
            }
        }
        return this;
    }

    /**
     * 属性备注。
     */
    private String attRemark;

    /**
     * 获取：属性备注。
     */
    public String getAttRemark() {
        return this.attRemark;
    }

    /**
     * 设置：属性备注。
     */
    public AdEntAtt setAttRemark(String attRemark) {
        if (this.attRemark == null && attRemark == null) {
            // 均为null，不做处理。
        } else if (this.attRemark != null && attRemark != null) {
            // 均非null，判定不等，再做处理：
            if (this.attRemark.compareTo(attRemark) != 0) {
                this.attRemark = attRemark;
                if (!this.toUpdateCols.contains("ATT_REMARK")) {
                    this.toUpdateCols.add("ATT_REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attRemark = attRemark;
            if (!this.toUpdateCols.contains("ATT_REMARK")) {
                this.toUpdateCols.add("ATT_REMARK");
            }
        }
        return this;
    }

    /**
     * 属性宽度。
     */
    private String attWidth;

    /**
     * 获取：属性宽度。
     */
    public String getAttWidth() {
        return this.attWidth;
    }

    /**
     * 设置：属性宽度。
     */
    public AdEntAtt setAttWidth(String attWidth) {
        if (this.attWidth == null && attWidth == null) {
            // 均为null，不做处理。
        } else if (this.attWidth != null && attWidth != null) {
            // 均非null，判定不等，再做处理：
            if (this.attWidth.compareTo(attWidth) != 0) {
                this.attWidth = attWidth;
                if (!this.toUpdateCols.contains("ATT_WIDTH")) {
                    this.toUpdateCols.add("ATT_WIDTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attWidth = attWidth;
            if (!this.toUpdateCols.contains("ATT_WIDTH")) {
                this.toUpdateCols.add("ATT_WIDTH");
            }
        }
        return this;
    }

    /**
     * 属性序号。
     */
    private BigDecimal attSeqNo;

    /**
     * 获取：属性序号。
     */
    public BigDecimal getAttSeqNo() {
        return this.attSeqNo;
    }

    /**
     * 设置：属性序号。
     */
    public AdEntAtt setAttSeqNo(BigDecimal attSeqNo) {
        if (this.attSeqNo == null && attSeqNo == null) {
            // 均为null，不做处理。
        } else if (this.attSeqNo != null && attSeqNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.attSeqNo.compareTo(attSeqNo) != 0) {
                this.attSeqNo = attSeqNo;
                if (!this.toUpdateCols.contains("ATT_SEQ_NO")) {
                    this.toUpdateCols.add("ATT_SEQ_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attSeqNo = attSeqNo;
            if (!this.toUpdateCols.contains("ATT_SEQ_NO")) {
                this.toUpdateCols.add("ATT_SEQ_NO");
            }
        }
        return this;
    }

    /**
     * 属性是否默认显示。
     */
    private Boolean attIsShownByDefault;

    /**
     * 获取：属性是否默认显示。
     */
    public Boolean getAttIsShownByDefault() {
        return this.attIsShownByDefault;
    }

    /**
     * 设置：属性是否默认显示。
     */
    public AdEntAtt setAttIsShownByDefault(Boolean attIsShownByDefault) {
        if (this.attIsShownByDefault == null && attIsShownByDefault == null) {
            // 均为null，不做处理。
        } else if (this.attIsShownByDefault != null && attIsShownByDefault != null) {
            // 均非null，判定不等，再做处理：
            if (this.attIsShownByDefault.compareTo(attIsShownByDefault) != 0) {
                this.attIsShownByDefault = attIsShownByDefault;
                if (!this.toUpdateCols.contains("ATT_IS_SHOWN_BY_DEFAULT")) {
                    this.toUpdateCols.add("ATT_IS_SHOWN_BY_DEFAULT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attIsShownByDefault = attIsShownByDefault;
            if (!this.toUpdateCols.contains("ATT_IS_SHOWN_BY_DEFAULT")) {
                this.toUpdateCols.add("ATT_IS_SHOWN_BY_DEFAULT");
            }
        }
        return this;
    }

    /**
     * 属性可改逻辑。
     */
    private String attEditableLogic;

    /**
     * 获取：属性可改逻辑。
     */
    public String getAttEditableLogic() {
        return this.attEditableLogic;
    }

    /**
     * 设置：属性可改逻辑。
     */
    public AdEntAtt setAttEditableLogic(String attEditableLogic) {
        if (this.attEditableLogic == null && attEditableLogic == null) {
            // 均为null，不做处理。
        } else if (this.attEditableLogic != null && attEditableLogic != null) {
            // 均非null，判定不等，再做处理：
            if (this.attEditableLogic.compareTo(attEditableLogic) != 0) {
                this.attEditableLogic = attEditableLogic;
                if (!this.toUpdateCols.contains("ATT_EDITABLE_LOGIC")) {
                    this.toUpdateCols.add("ATT_EDITABLE_LOGIC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attEditableLogic = attEditableLogic;
            if (!this.toUpdateCols.contains("ATT_EDITABLE_LOGIC")) {
                this.toUpdateCols.add("ATT_EDITABLE_LOGIC");
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
    public AdEntAtt setIgnoreEntRecEditable(Boolean ignoreEntRecEditable) {
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
     * 属性必填逻辑。
     */
    private String attMandatoryLogic;

    /**
     * 获取：属性必填逻辑。
     */
    public String getAttMandatoryLogic() {
        return this.attMandatoryLogic;
    }

    /**
     * 设置：属性必填逻辑。
     */
    public AdEntAtt setAttMandatoryLogic(String attMandatoryLogic) {
        if (this.attMandatoryLogic == null && attMandatoryLogic == null) {
            // 均为null，不做处理。
        } else if (this.attMandatoryLogic != null && attMandatoryLogic != null) {
            // 均非null，判定不等，再做处理：
            if (this.attMandatoryLogic.compareTo(attMandatoryLogic) != 0) {
                this.attMandatoryLogic = attMandatoryLogic;
                if (!this.toUpdateCols.contains("ATT_MANDATORY_LOGIC")) {
                    this.toUpdateCols.add("ATT_MANDATORY_LOGIC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attMandatoryLogic = attMandatoryLogic;
            if (!this.toUpdateCols.contains("ATT_MANDATORY_LOGIC")) {
                this.toUpdateCols.add("ATT_MANDATORY_LOGIC");
            }
        }
        return this;
    }

    /**
     * 属性默认值逻辑。
     */
    private String attDefaultValueLogic;

    /**
     * 获取：属性默认值逻辑。
     */
    public String getAttDefaultValueLogic() {
        return this.attDefaultValueLogic;
    }

    /**
     * 设置：属性默认值逻辑。
     */
    public AdEntAtt setAttDefaultValueLogic(String attDefaultValueLogic) {
        if (this.attDefaultValueLogic == null && attDefaultValueLogic == null) {
            // 均为null，不做处理。
        } else if (this.attDefaultValueLogic != null && attDefaultValueLogic != null) {
            // 均非null，判定不等，再做处理：
            if (this.attDefaultValueLogic.compareTo(attDefaultValueLogic) != 0) {
                this.attDefaultValueLogic = attDefaultValueLogic;
                if (!this.toUpdateCols.contains("ATT_DEFAULT_VALUE_LOGIC")) {
                    this.toUpdateCols.add("ATT_DEFAULT_VALUE_LOGIC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attDefaultValueLogic = attDefaultValueLogic;
            if (!this.toUpdateCols.contains("ATT_DEFAULT_VALUE_LOGIC")) {
                this.toUpdateCols.add("ATT_DEFAULT_VALUE_LOGIC");
            }
        }
        return this;
    }

    /**
     * 属性可见逻辑。
     */
    private String attVisibleLogic;

    /**
     * 获取：属性可见逻辑。
     */
    public String getAttVisibleLogic() {
        return this.attVisibleLogic;
    }

    /**
     * 设置：属性可见逻辑。
     */
    public AdEntAtt setAttVisibleLogic(String attVisibleLogic) {
        if (this.attVisibleLogic == null && attVisibleLogic == null) {
            // 均为null，不做处理。
        } else if (this.attVisibleLogic != null && attVisibleLogic != null) {
            // 均非null，判定不等，再做处理：
            if (this.attVisibleLogic.compareTo(attVisibleLogic) != 0) {
                this.attVisibleLogic = attVisibleLogic;
                if (!this.toUpdateCols.contains("ATT_VISIBLE_LOGIC")) {
                    this.toUpdateCols.add("ATT_VISIBLE_LOGIC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attVisibleLogic = attVisibleLogic;
            if (!this.toUpdateCols.contains("ATT_VISIBLE_LOGIC")) {
                this.toUpdateCols.add("ATT_VISIBLE_LOGIC");
            }
        }
        return this;
    }

    /**
     * 属性有效逻辑。
     */
    private String attValidLogic;

    /**
     * 获取：属性有效逻辑。
     */
    public String getAttValidLogic() {
        return this.attValidLogic;
    }

    /**
     * 设置：属性有效逻辑。
     */
    public AdEntAtt setAttValidLogic(String attValidLogic) {
        if (this.attValidLogic == null && attValidLogic == null) {
            // 均为null，不做处理。
        } else if (this.attValidLogic != null && attValidLogic != null) {
            // 均非null，判定不等，再做处理：
            if (this.attValidLogic.compareTo(attValidLogic) != 0) {
                this.attValidLogic = attValidLogic;
                if (!this.toUpdateCols.contains("ATT_VALID_LOGIC")) {
                    this.toUpdateCols.add("ATT_VALID_LOGIC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attValidLogic = attValidLogic;
            if (!this.toUpdateCols.contains("ATT_VALID_LOGIC")) {
                this.toUpdateCols.add("ATT_VALID_LOGIC");
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
    public AdEntAtt setTextHoverAsQrCode(Boolean textHoverAsQrCode) {
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
    public AdEntAtt setOddRowCellStLogic(String oddRowCellStLogic) {
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
    public AdEntAtt setEvenRowCellStLogic(String evenRowCellStLogic) {
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
    public AdEntAtt setHeaderSpanTitle(String headerSpanTitle) {
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
     * 属性文件路径。
     */
    private String attFilePathId;

    /**
     * 获取：属性文件路径。
     */
    public String getAttFilePathId() {
        return this.attFilePathId;
    }

    /**
     * 设置：属性文件路径。
     */
    public AdEntAtt setAttFilePathId(String attFilePathId) {
        if (this.attFilePathId == null && attFilePathId == null) {
            // 均为null，不做处理。
        } else if (this.attFilePathId != null && attFilePathId != null) {
            // 均非null，判定不等，再做处理：
            if (this.attFilePathId.compareTo(attFilePathId) != 0) {
                this.attFilePathId = attFilePathId;
                if (!this.toUpdateCols.contains("ATT_FILE_PATH_ID")) {
                    this.toUpdateCols.add("ATT_FILE_PATH_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attFilePathId = attFilePathId;
            if (!this.toUpdateCols.contains("ATT_FILE_PATH_ID")) {
                this.toUpdateCols.add("ATT_FILE_PATH_ID");
            }
        }
        return this;
    }

    /**
     * 属性文件最大KB。
     */
    private Integer attFileMaxKb;

    /**
     * 获取：属性文件最大KB。
     */
    public Integer getAttFileMaxKb() {
        return this.attFileMaxKb;
    }

    /**
     * 设置：属性文件最大KB。
     */
    public AdEntAtt setAttFileMaxKb(Integer attFileMaxKb) {
        if (this.attFileMaxKb == null && attFileMaxKb == null) {
            // 均为null，不做处理。
        } else if (this.attFileMaxKb != null && attFileMaxKb != null) {
            // 均非null，判定不等，再做处理：
            if (this.attFileMaxKb.compareTo(attFileMaxKb) != 0) {
                this.attFileMaxKb = attFileMaxKb;
                if (!this.toUpdateCols.contains("ATT_FILE_MAX_KB")) {
                    this.toUpdateCols.add("ATT_FILE_MAX_KB");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attFileMaxKb = attFileMaxKb;
            if (!this.toUpdateCols.contains("ATT_FILE_MAX_KB")) {
                this.toUpdateCols.add("ATT_FILE_MAX_KB");
            }
        }
        return this;
    }

    /**
     * 属性文件类型。
     */
    private String attFileTypes;

    /**
     * 获取：属性文件类型。
     */
    public String getAttFileTypes() {
        return this.attFileTypes;
    }

    /**
     * 设置：属性文件类型。
     */
    public AdEntAtt setAttFileTypes(String attFileTypes) {
        if (this.attFileTypes == null && attFileTypes == null) {
            // 均为null，不做处理。
        } else if (this.attFileTypes != null && attFileTypes != null) {
            // 均非null，判定不等，再做处理：
            if (this.attFileTypes.compareTo(attFileTypes) != 0) {
                this.attFileTypes = attFileTypes;
                if (!this.toUpdateCols.contains("ATT_FILE_TYPES")) {
                    this.toUpdateCols.add("ATT_FILE_TYPES");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attFileTypes = attFileTypes;
            if (!this.toUpdateCols.contains("ATT_FILE_TYPES")) {
                this.toUpdateCols.add("ATT_FILE_TYPES");
            }
        }
        return this;
    }

    /**
     * 属性文件允许批量上传。
     */
    private Boolean attFileIsMulti;

    /**
     * 获取：属性文件允许批量上传。
     */
    public Boolean getAttFileIsMulti() {
        return this.attFileIsMulti;
    }

    /**
     * 设置：属性文件允许批量上传。
     */
    public AdEntAtt setAttFileIsMulti(Boolean attFileIsMulti) {
        if (this.attFileIsMulti == null && attFileIsMulti == null) {
            // 均为null，不做处理。
        } else if (this.attFileIsMulti != null && attFileIsMulti != null) {
            // 均非null，判定不等，再做处理：
            if (this.attFileIsMulti.compareTo(attFileIsMulti) != 0) {
                this.attFileIsMulti = attFileIsMulti;
                if (!this.toUpdateCols.contains("ATT_FILE_IS_MULTI")) {
                    this.toUpdateCols.add("ATT_FILE_IS_MULTI");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attFileIsMulti = attFileIsMulti;
            if (!this.toUpdateCols.contains("ATT_FILE_IS_MULTI")) {
                this.toUpdateCols.add("ATT_FILE_IS_MULTI");
            }
        }
        return this;
    }

    /**
     * 属性文件是否作为图片查看。
     */
    private Boolean attFileViewAsImg;

    /**
     * 获取：属性文件是否作为图片查看。
     */
    public Boolean getAttFileViewAsImg() {
        return this.attFileViewAsImg;
    }

    /**
     * 设置：属性文件是否作为图片查看。
     */
    public AdEntAtt setAttFileViewAsImg(Boolean attFileViewAsImg) {
        if (this.attFileViewAsImg == null && attFileViewAsImg == null) {
            // 均为null，不做处理。
        } else if (this.attFileViewAsImg != null && attFileViewAsImg != null) {
            // 均非null，判定不等，再做处理：
            if (this.attFileViewAsImg.compareTo(attFileViewAsImg) != 0) {
                this.attFileViewAsImg = attFileViewAsImg;
                if (!this.toUpdateCols.contains("ATT_FILE_VIEW_AS_IMG")) {
                    this.toUpdateCols.add("ATT_FILE_VIEW_AS_IMG");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attFileViewAsImg = attFileViewAsImg;
            if (!this.toUpdateCols.contains("ATT_FILE_VIEW_AS_IMG")) {
                this.toUpdateCols.add("ATT_FILE_VIEW_AS_IMG");
            }
        }
        return this;
    }

    /**
     * 属性文件图片高度。
     */
    private String attFileImgHeight;

    /**
     * 获取：属性文件图片高度。
     */
    public String getAttFileImgHeight() {
        return this.attFileImgHeight;
    }

    /**
     * 设置：属性文件图片高度。
     */
    public AdEntAtt setAttFileImgHeight(String attFileImgHeight) {
        if (this.attFileImgHeight == null && attFileImgHeight == null) {
            // 均为null，不做处理。
        } else if (this.attFileImgHeight != null && attFileImgHeight != null) {
            // 均非null，判定不等，再做处理：
            if (this.attFileImgHeight.compareTo(attFileImgHeight) != 0) {
                this.attFileImgHeight = attFileImgHeight;
                if (!this.toUpdateCols.contains("ATT_FILE_IMG_HEIGHT")) {
                    this.toUpdateCols.add("ATT_FILE_IMG_HEIGHT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attFileImgHeight = attFileImgHeight;
            if (!this.toUpdateCols.contains("ATT_FILE_IMG_HEIGHT")) {
                this.toUpdateCols.add("ATT_FILE_IMG_HEIGHT");
            }
        }
        return this;
    }

    /**
     * 属性文件图片宽度。
     */
    private String attFileImgWidth;

    /**
     * 获取：属性文件图片宽度。
     */
    public String getAttFileImgWidth() {
        return this.attFileImgWidth;
    }

    /**
     * 设置：属性文件图片宽度。
     */
    public AdEntAtt setAttFileImgWidth(String attFileImgWidth) {
        if (this.attFileImgWidth == null && attFileImgWidth == null) {
            // 均为null，不做处理。
        } else if (this.attFileImgWidth != null && attFileImgWidth != null) {
            // 均非null，判定不等，再做处理：
            if (this.attFileImgWidth.compareTo(attFileImgWidth) != 0) {
                this.attFileImgWidth = attFileImgWidth;
                if (!this.toUpdateCols.contains("ATT_FILE_IMG_WIDTH")) {
                    this.toUpdateCols.add("ATT_FILE_IMG_WIDTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attFileImgWidth = attFileImgWidth;
            if (!this.toUpdateCols.contains("ATT_FILE_IMG_WIDTH")) {
                this.toUpdateCols.add("ATT_FILE_IMG_WIDTH");
            }
        }
        return this;
    }

    /**
     * 属性文件是否作为图片悬浮。
     */
    private Boolean attFileHoverAsImg;

    /**
     * 获取：属性文件是否作为图片悬浮。
     */
    public Boolean getAttFileHoverAsImg() {
        return this.attFileHoverAsImg;
    }

    /**
     * 设置：属性文件是否作为图片悬浮。
     */
    public AdEntAtt setAttFileHoverAsImg(Boolean attFileHoverAsImg) {
        if (this.attFileHoverAsImg == null && attFileHoverAsImg == null) {
            // 均为null，不做处理。
        } else if (this.attFileHoverAsImg != null && attFileHoverAsImg != null) {
            // 均非null，判定不等，再做处理：
            if (this.attFileHoverAsImg.compareTo(attFileHoverAsImg) != 0) {
                this.attFileHoverAsImg = attFileHoverAsImg;
                if (!this.toUpdateCols.contains("ATT_FILE_HOVER_AS_IMG")) {
                    this.toUpdateCols.add("ATT_FILE_HOVER_AS_IMG");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attFileHoverAsImg = attFileHoverAsImg;
            if (!this.toUpdateCols.contains("ATT_FILE_HOVER_AS_IMG")) {
                this.toUpdateCols.add("ATT_FILE_HOVER_AS_IMG");
            }
        }
        return this;
    }

    /**
     * 属性是否分组。
     */
    private Boolean attIsGrouped;

    /**
     * 获取：属性是否分组。
     */
    public Boolean getAttIsGrouped() {
        return this.attIsGrouped;
    }

    /**
     * 设置：属性是否分组。
     */
    public AdEntAtt setAttIsGrouped(Boolean attIsGrouped) {
        if (this.attIsGrouped == null && attIsGrouped == null) {
            // 均为null，不做处理。
        } else if (this.attIsGrouped != null && attIsGrouped != null) {
            // 均非null，判定不等，再做处理：
            if (this.attIsGrouped.compareTo(attIsGrouped) != 0) {
                this.attIsGrouped = attIsGrouped;
                if (!this.toUpdateCols.contains("ATT_IS_GROUPED")) {
                    this.toUpdateCols.add("ATT_IS_GROUPED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attIsGrouped = attIsGrouped;
            if (!this.toUpdateCols.contains("ATT_IS_GROUPED")) {
                this.toUpdateCols.add("ATT_IS_GROUPED");
            }
        }
        return this;
    }

    /**
     * 属性汇总模式。
     */
    private String attSumModeId;

    /**
     * 获取：属性汇总模式。
     */
    public String getAttSumModeId() {
        return this.attSumModeId;
    }

    /**
     * 设置：属性汇总模式。
     */
    public AdEntAtt setAttSumModeId(String attSumModeId) {
        if (this.attSumModeId == null && attSumModeId == null) {
            // 均为null，不做处理。
        } else if (this.attSumModeId != null && attSumModeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.attSumModeId.compareTo(attSumModeId) != 0) {
                this.attSumModeId = attSumModeId;
                if (!this.toUpdateCols.contains("ATT_SUM_MODE_ID")) {
                    this.toUpdateCols.add("ATT_SUM_MODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attSumModeId = attSumModeId;
            if (!this.toUpdateCols.contains("ATT_SUM_MODE_ID")) {
                this.toUpdateCols.add("ATT_SUM_MODE_ID");
            }
        }
        return this;
    }

    /**
     * 属性汇总前缀。
     */
    private String attSumPrefix;

    /**
     * 获取：属性汇总前缀。
     */
    public String getAttSumPrefix() {
        return this.attSumPrefix;
    }

    /**
     * 设置：属性汇总前缀。
     */
    public AdEntAtt setAttSumPrefix(String attSumPrefix) {
        if (this.attSumPrefix == null && attSumPrefix == null) {
            // 均为null，不做处理。
        } else if (this.attSumPrefix != null && attSumPrefix != null) {
            // 均非null，判定不等，再做处理：
            if (this.attSumPrefix.compareTo(attSumPrefix) != 0) {
                this.attSumPrefix = attSumPrefix;
                if (!this.toUpdateCols.contains("ATT_SUM_PREFIX")) {
                    this.toUpdateCols.add("ATT_SUM_PREFIX");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attSumPrefix = attSumPrefix;
            if (!this.toUpdateCols.contains("ATT_SUM_PREFIX")) {
                this.toUpdateCols.add("ATT_SUM_PREFIX");
            }
        }
        return this;
    }

    /**
     * 属性汇总后缀。
     */
    private String attSumSuffix;

    /**
     * 获取：属性汇总后缀。
     */
    public String getAttSumSuffix() {
        return this.attSumSuffix;
    }

    /**
     * 设置：属性汇总后缀。
     */
    public AdEntAtt setAttSumSuffix(String attSumSuffix) {
        if (this.attSumSuffix == null && attSumSuffix == null) {
            // 均为null，不做处理。
        } else if (this.attSumSuffix != null && attSumSuffix != null) {
            // 均非null，判定不等，再做处理：
            if (this.attSumSuffix.compareTo(attSumSuffix) != 0) {
                this.attSumSuffix = attSumSuffix;
                if (!this.toUpdateCols.contains("ATT_SUM_SUFFIX")) {
                    this.toUpdateCols.add("ATT_SUM_SUFFIX");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attSumSuffix = attSumSuffix;
            if (!this.toUpdateCols.contains("ATT_SUM_SUFFIX")) {
                this.toUpdateCols.add("ATT_SUM_SUFFIX");
            }
        }
        return this;
    }

    /**
     * 属性是否隐藏分组汇总。
     */
    private Boolean attIsGroupSumHidden;

    /**
     * 获取：属性是否隐藏分组汇总。
     */
    public Boolean getAttIsGroupSumHidden() {
        return this.attIsGroupSumHidden;
    }

    /**
     * 设置：属性是否隐藏分组汇总。
     */
    public AdEntAtt setAttIsGroupSumHidden(Boolean attIsGroupSumHidden) {
        if (this.attIsGroupSumHidden == null && attIsGroupSumHidden == null) {
            // 均为null，不做处理。
        } else if (this.attIsGroupSumHidden != null && attIsGroupSumHidden != null) {
            // 均非null，判定不等，再做处理：
            if (this.attIsGroupSumHidden.compareTo(attIsGroupSumHidden) != 0) {
                this.attIsGroupSumHidden = attIsGroupSumHidden;
                if (!this.toUpdateCols.contains("ATT_IS_GROUP_SUM_HIDDEN")) {
                    this.toUpdateCols.add("ATT_IS_GROUP_SUM_HIDDEN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attIsGroupSumHidden = attIsGroupSumHidden;
            if (!this.toUpdateCols.contains("ATT_IS_GROUP_SUM_HIDDEN")) {
                this.toUpdateCols.add("ATT_IS_GROUP_SUM_HIDDEN");
            }
        }
        return this;
    }

    /**
     * 属性是否隐藏总计汇总。
     */
    private Boolean attIsTtlSumHidden;

    /**
     * 获取：属性是否隐藏总计汇总。
     */
    public Boolean getAttIsTtlSumHidden() {
        return this.attIsTtlSumHidden;
    }

    /**
     * 设置：属性是否隐藏总计汇总。
     */
    public AdEntAtt setAttIsTtlSumHidden(Boolean attIsTtlSumHidden) {
        if (this.attIsTtlSumHidden == null && attIsTtlSumHidden == null) {
            // 均为null，不做处理。
        } else if (this.attIsTtlSumHidden != null && attIsTtlSumHidden != null) {
            // 均非null，判定不等，再做处理：
            if (this.attIsTtlSumHidden.compareTo(attIsTtlSumHidden) != 0) {
                this.attIsTtlSumHidden = attIsTtlSumHidden;
                if (!this.toUpdateCols.contains("ATT_IS_TTL_SUM_HIDDEN")) {
                    this.toUpdateCols.add("ATT_IS_TTL_SUM_HIDDEN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attIsTtlSumHidden = attIsTtlSumHidden;
            if (!this.toUpdateCols.contains("ATT_IS_TTL_SUM_HIDDEN")) {
                this.toUpdateCols.add("ATT_IS_TTL_SUM_HIDDEN");
            }
        }
        return this;
    }

    /**
     * 属性横向对齐。
     */
    private String attHAlign;

    /**
     * 获取：属性横向对齐。
     */
    public String getAttHAlign() {
        return this.attHAlign;
    }

    /**
     * 设置：属性横向对齐。
     */
    public AdEntAtt setAttHAlign(String attHAlign) {
        if (this.attHAlign == null && attHAlign == null) {
            // 均为null，不做处理。
        } else if (this.attHAlign != null && attHAlign != null) {
            // 均非null，判定不等，再做处理：
            if (this.attHAlign.compareTo(attHAlign) != 0) {
                this.attHAlign = attHAlign;
                if (!this.toUpdateCols.contains("ATT_H_ALIGN")) {
                    this.toUpdateCols.add("ATT_H_ALIGN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attHAlign = attHAlign;
            if (!this.toUpdateCols.contains("ATT_H_ALIGN")) {
                this.toUpdateCols.add("ATT_H_ALIGN");
            }
        }
        return this;
    }

    /**
     * 属性是否固定。
     */
    private Boolean attIsFixed;

    /**
     * 获取：属性是否固定。
     */
    public Boolean getAttIsFixed() {
        return this.attIsFixed;
    }

    /**
     * 设置：属性是否固定。
     */
    public AdEntAtt setAttIsFixed(Boolean attIsFixed) {
        if (this.attIsFixed == null && attIsFixed == null) {
            // 均为null，不做处理。
        } else if (this.attIsFixed != null && attIsFixed != null) {
            // 均非null，判定不等，再做处理：
            if (this.attIsFixed.compareTo(attIsFixed) != 0) {
                this.attIsFixed = attIsFixed;
                if (!this.toUpdateCols.contains("ATT_IS_FIXED")) {
                    this.toUpdateCols.add("ATT_IS_FIXED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attIsFixed = attIsFixed;
            if (!this.toUpdateCols.contains("ATT_IS_FIXED")) {
                this.toUpdateCols.add("ATT_IS_FIXED");
            }
        }
        return this;
    }

    /**
     * 属性显示格式。
     */
    private String attDisplayFormat;

    /**
     * 获取：属性显示格式。
     */
    public String getAttDisplayFormat() {
        return this.attDisplayFormat;
    }

    /**
     * 设置：属性显示格式。
     */
    public AdEntAtt setAttDisplayFormat(String attDisplayFormat) {
        if (this.attDisplayFormat == null && attDisplayFormat == null) {
            // 均为null，不做处理。
        } else if (this.attDisplayFormat != null && attDisplayFormat != null) {
            // 均非null，判定不等，再做处理：
            if (this.attDisplayFormat.compareTo(attDisplayFormat) != 0) {
                this.attDisplayFormat = attDisplayFormat;
                if (!this.toUpdateCols.contains("ATT_DISPLAY_FORMAT")) {
                    this.toUpdateCols.add("ATT_DISPLAY_FORMAT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attDisplayFormat = attDisplayFormat;
            if (!this.toUpdateCols.contains("ATT_DISPLAY_FORMAT")) {
                this.toUpdateCols.add("ATT_DISPLAY_FORMAT");
            }
        }
        return this;
    }

    /**
     * 属性变量名称。
     */
    private String attVarName;

    /**
     * 获取：属性变量名称。
     */
    public String getAttVarName() {
        return this.attVarName;
    }

    /**
     * 设置：属性变量名称。
     */
    public AdEntAtt setAttVarName(String attVarName) {
        if (this.attVarName == null && attVarName == null) {
            // 均为null，不做处理。
        } else if (this.attVarName != null && attVarName != null) {
            // 均非null，判定不等，再做处理：
            if (this.attVarName.compareTo(attVarName) != 0) {
                this.attVarName = attVarName;
                if (!this.toUpdateCols.contains("ATT_VAR_NAME")) {
                    this.toUpdateCols.add("ATT_VAR_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attVarName = attVarName;
            if (!this.toUpdateCols.contains("ATT_VAR_NAME")) {
                this.toUpdateCols.add("ATT_VAR_NAME");
            }
        }
        return this;
    }

    /**
     * 属性引用的实体视图。
     */
    private String attRefedSevId;

    /**
     * 获取：属性引用的实体视图。
     */
    public String getAttRefedSevId() {
        return this.attRefedSevId;
    }

    /**
     * 设置：属性引用的实体视图。
     */
    public AdEntAtt setAttRefedSevId(String attRefedSevId) {
        if (this.attRefedSevId == null && attRefedSevId == null) {
            // 均为null，不做处理。
        } else if (this.attRefedSevId != null && attRefedSevId != null) {
            // 均非null，判定不等，再做处理：
            if (this.attRefedSevId.compareTo(attRefedSevId) != 0) {
                this.attRefedSevId = attRefedSevId;
                if (!this.toUpdateCols.contains("ATT_REFED_SEV_ID")) {
                    this.toUpdateCols.add("ATT_REFED_SEV_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attRefedSevId = attRefedSevId;
            if (!this.toUpdateCols.contains("ATT_REFED_SEV_ID")) {
                this.toUpdateCols.add("ATT_REFED_SEV_ID");
            }
        }
        return this;
    }

    /**
     * 属性引用的视图部分列表。
     */
    private String attRefedVpIds;

    /**
     * 获取：属性引用的视图部分列表。
     */
    public String getAttRefedVpIds() {
        return this.attRefedVpIds;
    }

    /**
     * 设置：属性引用的视图部分列表。
     */
    public AdEntAtt setAttRefedVpIds(String attRefedVpIds) {
        if (this.attRefedVpIds == null && attRefedVpIds == null) {
            // 均为null，不做处理。
        } else if (this.attRefedVpIds != null && attRefedVpIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.attRefedVpIds.compareTo(attRefedVpIds) != 0) {
                this.attRefedVpIds = attRefedVpIds;
                if (!this.toUpdateCols.contains("ATT_REFED_VP_IDS")) {
                    this.toUpdateCols.add("ATT_REFED_VP_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attRefedVpIds = attRefedVpIds;
            if (!this.toUpdateCols.contains("ATT_REFED_VP_IDS")) {
                this.toUpdateCols.add("ATT_REFED_VP_IDS");
            }
        }
        return this;
    }

    /**
     * 属性引用的WHERE语句。
     */
    private String attRefedWhereClause;

    /**
     * 获取：属性引用的WHERE语句。
     */
    public String getAttRefedWhereClause() {
        return this.attRefedWhereClause;
    }

    /**
     * 设置：属性引用的WHERE语句。
     */
    public AdEntAtt setAttRefedWhereClause(String attRefedWhereClause) {
        if (this.attRefedWhereClause == null && attRefedWhereClause == null) {
            // 均为null，不做处理。
        } else if (this.attRefedWhereClause != null && attRefedWhereClause != null) {
            // 均非null，判定不等，再做处理：
            if (this.attRefedWhereClause.compareTo(attRefedWhereClause) != 0) {
                this.attRefedWhereClause = attRefedWhereClause;
                if (!this.toUpdateCols.contains("ATT_REFED_WHERE_CLAUSE")) {
                    this.toUpdateCols.add("ATT_REFED_WHERE_CLAUSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attRefedWhereClause = attRefedWhereClause;
            if (!this.toUpdateCols.contains("ATT_REFED_WHERE_CLAUSE")) {
                this.toUpdateCols.add("ATT_REFED_WHERE_CLAUSE");
            }
        }
        return this;
    }

    /**
     * 属性引用删除模式。
     */
    private String attRefedOnDelMode;

    /**
     * 获取：属性引用删除模式。
     */
    public String getAttRefedOnDelMode() {
        return this.attRefedOnDelMode;
    }

    /**
     * 设置：属性引用删除模式。
     */
    public AdEntAtt setAttRefedOnDelMode(String attRefedOnDelMode) {
        if (this.attRefedOnDelMode == null && attRefedOnDelMode == null) {
            // 均为null，不做处理。
        } else if (this.attRefedOnDelMode != null && attRefedOnDelMode != null) {
            // 均非null，判定不等，再做处理：
            if (this.attRefedOnDelMode.compareTo(attRefedOnDelMode) != 0) {
                this.attRefedOnDelMode = attRefedOnDelMode;
                if (!this.toUpdateCols.contains("ATT_REFED_ON_DEL_MODE")) {
                    this.toUpdateCols.add("ATT_REFED_ON_DEL_MODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attRefedOnDelMode = attRefedOnDelMode;
            if (!this.toUpdateCols.contains("ATT_REFED_ON_DEL_MODE")) {
                this.toUpdateCols.add("ATT_REFED_ON_DEL_MODE");
            }
        }
        return this;
    }

    /**
     * 属性引用的启用缓存。
     */
    private Boolean attRefedCacheEnabled;

    /**
     * 获取：属性引用的启用缓存。
     */
    public Boolean getAttRefedCacheEnabled() {
        return this.attRefedCacheEnabled;
    }

    /**
     * 设置：属性引用的启用缓存。
     */
    public AdEntAtt setAttRefedCacheEnabled(Boolean attRefedCacheEnabled) {
        if (this.attRefedCacheEnabled == null && attRefedCacheEnabled == null) {
            // 均为null，不做处理。
        } else if (this.attRefedCacheEnabled != null && attRefedCacheEnabled != null) {
            // 均非null，判定不等，再做处理：
            if (this.attRefedCacheEnabled.compareTo(attRefedCacheEnabled) != 0) {
                this.attRefedCacheEnabled = attRefedCacheEnabled;
                if (!this.toUpdateCols.contains("ATT_REFED_CACHE_ENABLED")) {
                    this.toUpdateCols.add("ATT_REFED_CACHE_ENABLED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attRefedCacheEnabled = attRefedCacheEnabled;
            if (!this.toUpdateCols.contains("ATT_REFED_CACHE_ENABLED")) {
                this.toUpdateCols.add("ATT_REFED_CACHE_ENABLED");
            }
        }
        return this;
    }

    /**
     * 属性引用的下拉禁用。
     */
    private Boolean attRefedDropdownDisabled;

    /**
     * 获取：属性引用的下拉禁用。
     */
    public Boolean getAttRefedDropdownDisabled() {
        return this.attRefedDropdownDisabled;
    }

    /**
     * 设置：属性引用的下拉禁用。
     */
    public AdEntAtt setAttRefedDropdownDisabled(Boolean attRefedDropdownDisabled) {
        if (this.attRefedDropdownDisabled == null && attRefedDropdownDisabled == null) {
            // 均为null，不做处理。
        } else if (this.attRefedDropdownDisabled != null && attRefedDropdownDisabled != null) {
            // 均非null，判定不等，再做处理：
            if (this.attRefedDropdownDisabled.compareTo(attRefedDropdownDisabled) != 0) {
                this.attRefedDropdownDisabled = attRefedDropdownDisabled;
                if (!this.toUpdateCols.contains("ATT_REFED_DROPDOWN_DISABLED")) {
                    this.toUpdateCols.add("ATT_REFED_DROPDOWN_DISABLED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attRefedDropdownDisabled = attRefedDropdownDisabled;
            if (!this.toUpdateCols.contains("ATT_REFED_DROPDOWN_DISABLED")) {
                this.toUpdateCols.add("ATT_REFED_DROPDOWN_DISABLED");
            }
        }
        return this;
    }

    /**
     * 属性引用的搜索禁用。
     */
    private Boolean attRefedSearchDisabled;

    /**
     * 获取：属性引用的搜索禁用。
     */
    public Boolean getAttRefedSearchDisabled() {
        return this.attRefedSearchDisabled;
    }

    /**
     * 设置：属性引用的搜索禁用。
     */
    public AdEntAtt setAttRefedSearchDisabled(Boolean attRefedSearchDisabled) {
        if (this.attRefedSearchDisabled == null && attRefedSearchDisabled == null) {
            // 均为null，不做处理。
        } else if (this.attRefedSearchDisabled != null && attRefedSearchDisabled != null) {
            // 均非null，判定不等，再做处理：
            if (this.attRefedSearchDisabled.compareTo(attRefedSearchDisabled) != 0) {
                this.attRefedSearchDisabled = attRefedSearchDisabled;
                if (!this.toUpdateCols.contains("ATT_REFED_SEARCH_DISABLED")) {
                    this.toUpdateCols.add("ATT_REFED_SEARCH_DISABLED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attRefedSearchDisabled = attRefedSearchDisabled;
            if (!this.toUpdateCols.contains("ATT_REFED_SEARCH_DISABLED")) {
                this.toUpdateCols.add("ATT_REFED_SEARCH_DISABLED");
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
    public AdEntAtt setRefedPopupWidth(String refedPopupWidth) {
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
    public AdEntAtt setRefedPopupHeight(String refedPopupHeight) {
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
    public AdEntAtt setRefedSelectableLogic(String refedSelectableLogic) {
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
    public AdEntAtt setDefSql(String defSql) {
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
    public AdEntAtt setIsMandatory(Boolean isMandatory) {
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
    public AdEntAtt setAdIndexTypeId(String adIndexTypeId) {
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
    public AdEntAtt setCreateFk(Boolean createFk) {
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
    public AdEntAtt setFormItemTitleHidden(Boolean formItemTitleHidden) {
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
    public AdEntAtt setFormItemTitleTop(Boolean formItemTitleTop) {
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
    public AdEntAtt setFormItemTitleWrap(Boolean formItemTitleWrap) {
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
    public AdEntAtt setFormItemWidth(String formItemWidth) {
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
    public AdEntAtt setFormItemHeight(String formItemHeight) {
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
    public AdEntAtt setFormItemRowSpan(Integer formItemRowSpan) {
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
    public AdEntAtt setFormItemColSpan(Integer formItemColSpan) {
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
    public AdEntAtt setHideInList(Boolean hideInList) {
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
    public AdEntAtt setHideInDtl(Boolean hideInDtl) {
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
    public AdEntAtt setHideInPrint(Boolean hideInPrint) {
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
    public AdEntAtt setHideInSimpleFilter(Boolean hideInSimpleFilter) {
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
    public AdEntAtt setHideInComplexFilter(Boolean hideInComplexFilter) {
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
    public AdEntAtt setDisplayUomId(String displayUomId) {
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
    public static AdEntAtt newData() {
        AdEntAtt obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static AdEntAtt insertData() {
        AdEntAtt obj = modelHelper.insertData();
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
    public static AdEntAtt selectById(String id, List<String> includeCols, List<String> excludeCols) {
        AdEntAtt obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<AdEntAtt> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<AdEntAtt> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<AdEntAtt> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<AdEntAtt> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(AdEntAtt fromModel, AdEntAtt toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}