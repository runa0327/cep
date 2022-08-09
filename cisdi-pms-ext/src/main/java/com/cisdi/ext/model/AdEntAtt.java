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
 * 实体属性。
 */
public class AdEntAtt {

    /**
     * 模型助手。
     */
    private static final ModelHelper<AdEntAtt> modelHelper = new ModelHelper<>("AD_ENT_ATT", new AdEntAtt());

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
    public AdEntAtt setId(String id) {
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
    public AdEntAtt setVer(Integer ver) {
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
    public AdEntAtt setTs(LocalDateTime ts) {
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
    public AdEntAtt setIsPreset(Boolean isPreset) {
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
    public AdEntAtt setCrtDt(LocalDateTime crtDt) {
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
    public AdEntAtt setCrtUserId(String crtUserId) {
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
    public AdEntAtt setLastModiDt(LocalDateTime lastModiDt) {
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
    public AdEntAtt setLastModiUserId(String lastModiUserId) {
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
    public AdEntAtt setStatus(String status) {
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
    public AdEntAtt setLkWfInstId(String lkWfInstId) {
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
    public AdEntAtt setCode(String code) {
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
    public AdEntAtt setName(String name) {
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
    public AdEntAtt setRemark(String remark) {
        this.remark = remark;
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
    public AdEntAtt setAdEntId(String adEntId) {
        this.adEntId = adEntId;
        return this;
    }

    /**
     * 属性。
     */
    public String adAttId;

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
        this.adAttId = adAttId;
        return this;
    }

    /**
     * 实体属性关系。
     */
    public String entAttRelation;

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
        this.entAttRelation = entAttRelation;
        return this;
    }

    /**
     * 属性名称。
     */
    public String attName;

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
        this.attName = attName;
        return this;
    }

    /**
     * 属性备注。
     */
    public String attRemark;

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
        this.attRemark = attRemark;
        return this;
    }

    /**
     * 属性宽度。
     */
    public String attWidth;

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
        this.attWidth = attWidth;
        return this;
    }

    /**
     * 属性序号。
     */
    public Double attSeqNo;

    /**
     * 获取：属性序号。
     */
    public Double getAttSeqNo() {
        return this.attSeqNo;
    }

    /**
     * 设置：属性序号。
     */
    public AdEntAtt setAttSeqNo(Double attSeqNo) {
        this.attSeqNo = attSeqNo;
        return this;
    }

    /**
     * 属性是否默认显示。
     */
    public Boolean attIsShownByDefault;

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
        this.attIsShownByDefault = attIsShownByDefault;
        return this;
    }

    /**
     * 属性可改逻辑。
     */
    public String attEditableLogic;

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
        this.attEditableLogic = attEditableLogic;
        return this;
    }

    /**
     * 忽略实体记录可改。
     */
    public Boolean ignoreEntRecEditable;

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
        this.ignoreEntRecEditable = ignoreEntRecEditable;
        return this;
    }

    /**
     * 属性必填逻辑。
     */
    public String attMandatoryLogic;

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
        this.attMandatoryLogic = attMandatoryLogic;
        return this;
    }

    /**
     * 属性默认值逻辑。
     */
    public String attDefaultValueLogic;

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
        this.attDefaultValueLogic = attDefaultValueLogic;
        return this;
    }

    /**
     * 属性可见逻辑。
     */
    public String attVisibleLogic;

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
        this.attVisibleLogic = attVisibleLogic;
        return this;
    }

    /**
     * 属性有效逻辑。
     */
    public String attValidLogic;

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
        this.attValidLogic = attValidLogic;
        return this;
    }

    /**
     * 文本悬浮二维码。
     */
    public Boolean textHoverAsQrCode;

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
        this.textHoverAsQrCode = textHoverAsQrCode;
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
    public AdEntAtt setOddRowCellStLogic(String oddRowCellStLogic) {
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
    public AdEntAtt setEvenRowCellStLogic(String evenRowCellStLogic) {
        this.evenRowCellStLogic = evenRowCellStLogic;
        return this;
    }

    /**
     * 组合标题。
     */
    public String headerSpanTitle;

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
        this.headerSpanTitle = headerSpanTitle;
        return this;
    }

    /**
     * 属性文件路径。
     */
    public String attFilePathId;

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
        this.attFilePathId = attFilePathId;
        return this;
    }

    /**
     * 属性文件最大KB。
     */
    public Integer attFileMaxKb;

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
        this.attFileMaxKb = attFileMaxKb;
        return this;
    }

    /**
     * 属性文件类型。
     */
    public String attFileTypes;

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
        this.attFileTypes = attFileTypes;
        return this;
    }

    /**
     * 属性文件允许批量上传。
     */
    public Boolean attFileIsMulti;

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
        this.attFileIsMulti = attFileIsMulti;
        return this;
    }

    /**
     * 属性文件是否作为图片查看。
     */
    public Boolean attFileViewAsImg;

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
        this.attFileViewAsImg = attFileViewAsImg;
        return this;
    }

    /**
     * 属性文件图片高度。
     */
    public String attFileImgHeight;

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
        this.attFileImgHeight = attFileImgHeight;
        return this;
    }

    /**
     * 属性文件图片宽度。
     */
    public String attFileImgWidth;

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
        this.attFileImgWidth = attFileImgWidth;
        return this;
    }

    /**
     * 属性文件是否作为图片悬浮。
     */
    public Boolean attFileHoverAsImg;

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
        this.attFileHoverAsImg = attFileHoverAsImg;
        return this;
    }

    /**
     * 属性是否分组。
     */
    public Boolean attIsGrouped;

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
        this.attIsGrouped = attIsGrouped;
        return this;
    }

    /**
     * 属性汇总模式。
     */
    public String attSumModeId;

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
        this.attSumModeId = attSumModeId;
        return this;
    }

    /**
     * 属性汇总前缀。
     */
    public String attSumPrefix;

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
        this.attSumPrefix = attSumPrefix;
        return this;
    }

    /**
     * 属性汇总后缀。
     */
    public String attSumSuffix;

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
        this.attSumSuffix = attSumSuffix;
        return this;
    }

    /**
     * 属性是否隐藏分组汇总。
     */
    public Boolean attIsGroupSumHidden;

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
        this.attIsGroupSumHidden = attIsGroupSumHidden;
        return this;
    }

    /**
     * 属性是否隐藏总计汇总。
     */
    public Boolean attIsTtlSumHidden;

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
        this.attIsTtlSumHidden = attIsTtlSumHidden;
        return this;
    }

    /**
     * 属性横向对齐。
     */
    public String attHAlign;

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
        this.attHAlign = attHAlign;
        return this;
    }

    /**
     * 属性是否固定。
     */
    public Boolean attIsFixed;

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
        this.attIsFixed = attIsFixed;
        return this;
    }

    /**
     * 属性显示格式。
     */
    public String attDisplayFormat;

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
        this.attDisplayFormat = attDisplayFormat;
        return this;
    }

    /**
     * 属性变量名称。
     */
    public String attVarName;

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
        this.attVarName = attVarName;
        return this;
    }

    /**
     * 属性引用的实体视图。
     */
    public String attRefedSevId;

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
        this.attRefedSevId = attRefedSevId;
        return this;
    }

    /**
     * 属性引用的视图部分列表。
     */
    public String attRefedVpIds;

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
        this.attRefedVpIds = attRefedVpIds;
        return this;
    }

    /**
     * 属性引用的WHERE语句。
     */
    public String attRefedWhereClause;

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
        this.attRefedWhereClause = attRefedWhereClause;
        return this;
    }

    /**
     * 属性引用删除模式。
     */
    public String attRefedOnDelMode;

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
        this.attRefedOnDelMode = attRefedOnDelMode;
        return this;
    }

    /**
     * 属性引用的启用缓存。
     */
    public Boolean attRefedCacheEnabled;

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
        this.attRefedCacheEnabled = attRefedCacheEnabled;
        return this;
    }

    /**
     * 属性引用的下拉禁用。
     */
    public Boolean attRefedDropdownDisabled;

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
        this.attRefedDropdownDisabled = attRefedDropdownDisabled;
        return this;
    }

    /**
     * 属性引用的搜索禁用。
     */
    public Boolean attRefedSearchDisabled;

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
        this.attRefedSearchDisabled = attRefedSearchDisabled;
        return this;
    }

    /**
     * 引用的弹出宽度。
     */
    public String refedPopupWidth;

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
        this.refedPopupWidth = refedPopupWidth;
        return this;
    }

    /**
     * 引用的弹出高度。
     */
    public String refedPopupHeight;

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
        this.refedPopupHeight = refedPopupHeight;
        return this;
    }

    /**
     * 引用的可选择逻辑。
     */
    public String refedSelectableLogic;

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
        this.refedSelectableLogic = refedSelectableLogic;
        return this;
    }

    /**
     * 定义SQL。
     */
    public String defSql;

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
        this.defSql = defSql;
        return this;
    }

    /**
     * 是否必填。
     */
    public Boolean isMandatory;

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
        this.isMandatory = isMandatory;
        return this;
    }

    /**
     * 索引类型。
     */
    public String adIndexTypeId;

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
        this.adIndexTypeId = adIndexTypeId;
        return this;
    }

    /**
     * 创建外键。
     */
    public Boolean createFk;

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
        this.createFk = createFk;
        return this;
    }

    /**
     * 表单项标题隐藏。
     */
    public Boolean formItemTitleHidden;

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
        this.formItemTitleHidden = formItemTitleHidden;
        return this;
    }

    /**
     * 表单项标题在上。
     */
    public Boolean formItemTitleTop;

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
        this.formItemTitleTop = formItemTitleTop;
        return this;
    }

    /**
     * 表单项标题换行。
     */
    public Boolean formItemTitleWrap;

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
        this.formItemTitleWrap = formItemTitleWrap;
        return this;
    }

    /**
     * 表单项宽度。
     */
    public String formItemWidth;

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
        this.formItemWidth = formItemWidth;
        return this;
    }

    /**
     * 表单项高度。
     */
    public String formItemHeight;

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
        this.formItemHeight = formItemHeight;
        return this;
    }

    /**
     * 表单项行跨度。
     */
    public Integer formItemRowSpan;

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
        this.formItemRowSpan = formItemRowSpan;
        return this;
    }

    /**
     * 表单项列跨度。
     */
    public Integer formItemColSpan;

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
        this.formItemColSpan = formItemColSpan;
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
    public static AdEntAtt insertData() {
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
    public static AdEntAtt selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<AdEntAtt> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<AdEntAtt> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
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
    public static void copyCols(AdEntAtt fromModel, AdEntAtt toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}