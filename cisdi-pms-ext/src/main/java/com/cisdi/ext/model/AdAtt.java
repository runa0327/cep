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
 * 属性。
 */
public class AdAtt {

    /**
     * 模型助手。
     */
    private static final ModelHelper<AdAtt> modelHelper = new ModelHelper<>("AD_ATT", new AdAtt());

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
    public AdAtt setId(String id) {
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
    public AdAtt setVer(Integer ver) {
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
    public AdAtt setTs(LocalDateTime ts) {
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
    public AdAtt setIsPreset(Boolean isPreset) {
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
    public AdAtt setCrtDt(LocalDateTime crtDt) {
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
    public AdAtt setCrtUserId(String crtUserId) {
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
    public AdAtt setLastModiDt(LocalDateTime lastModiDt) {
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
    public AdAtt setLastModiUserId(String lastModiUserId) {
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
    public AdAtt setStatus(String status) {
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
    public AdAtt setLkWfInstId(String lkWfInstId) {
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
    public AdAtt setCode(String code) {
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
    public AdAtt setName(String name) {
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
    public AdAtt setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    /**
     * 属性子类型。
     */
    public String adAttSubTypeId;

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
        this.adAttSubTypeId = adAttSubTypeId;
        return this;
    }

    /**
     * 虚拟表达式。
     */
    public String virtualExpression;

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
        this.virtualExpression = virtualExpression;
        return this;
    }

    /**
     * 快捷操作组。
     */
    public String adFastActGrpId;

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
        this.adFastActGrpId = adFastActGrpId;
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
    public AdAtt setWidth(String width) {
        this.width = width;
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
    public AdAtt setSeqNo(Double seqNo) {
        this.seqNo = seqNo;
        return this;
    }

    /**
     * 是否标识。
     */
    public Boolean isIdentifying;

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
        this.isIdentifying = isIdentifying;
        return this;
    }

    /**
     * 是否默认显示。
     */
    public Boolean isShownByDefault;

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
        this.isShownByDefault = isShownByDefault;
        return this;
    }

    /**
     * 可改逻辑。
     */
    public String editableLogic;

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
        this.editableLogic = editableLogic;
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
    public AdAtt setIgnoreEntRecEditable(Boolean ignoreEntRecEditable) {
        this.ignoreEntRecEditable = ignoreEntRecEditable;
        return this;
    }

    /**
     * 必填逻辑。
     */
    public String mandatoryLogic;

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
        this.mandatoryLogic = mandatoryLogic;
        return this;
    }

    /**
     * 默认值逻辑。
     */
    public String defaultValueLogic;

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
        this.defaultValueLogic = defaultValueLogic;
        return this;
    }

    /**
     * 可见逻辑。
     */
    public String visibleLogic;

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
        this.visibleLogic = visibleLogic;
        return this;
    }

    /**
     * 有效逻辑。
     */
    public String validLogic;

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
        this.validLogic = validLogic;
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
    public AdAtt setTextHoverAsQrCode(Boolean textHoverAsQrCode) {
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
    public AdAtt setOddRowCellStLogic(String oddRowCellStLogic) {
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
    public AdAtt setEvenRowCellStLogic(String evenRowCellStLogic) {
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
    public AdAtt setHeaderSpanTitle(String headerSpanTitle) {
        this.headerSpanTitle = headerSpanTitle;
        return this;
    }

    /**
     * 文件路径。
     */
    public String filePathId;

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
        this.filePathId = filePathId;
        return this;
    }

    /**
     * 文件类型。
     */
    public String fileTypes;

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
        this.fileTypes = fileTypes;
        return this;
    }

    /**
     * 文件最大KB。
     */
    public Integer fileMaxKb;

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
        this.fileMaxKb = fileMaxKb;
        return this;
    }

    /**
     * 文件允许批量上传。
     */
    public Boolean fileIsMulti;

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
        this.fileIsMulti = fileIsMulti;
        return this;
    }

    /**
     * 文件是否作为图片查看。
     */
    public Boolean fileViewAsImg;

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
        this.fileViewAsImg = fileViewAsImg;
        return this;
    }

    /**
     * 文件是否作为图片悬浮。
     */
    public Boolean fileHoverAsImg;

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
        this.fileHoverAsImg = fileHoverAsImg;
        return this;
    }

    /**
     * 文件图片宽度。
     */
    public String fileImgWidth;

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
        this.fileImgWidth = fileImgWidth;
        return this;
    }

    /**
     * 文件图片高度。
     */
    public String fileImgHeight;

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
        this.fileImgHeight = fileImgHeight;
        return this;
    }

    /**
     * 是否分组。
     */
    public Boolean isGrouped;

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
        this.isGrouped = isGrouped;
        return this;
    }

    /**
     * 汇总模式。
     */
    public String sumModeId;

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
        this.sumModeId = sumModeId;
        return this;
    }

    /**
     * 汇总前缀。
     */
    public String sumPrefix;

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
        this.sumPrefix = sumPrefix;
        return this;
    }

    /**
     * 汇总后缀。
     */
    public String sumSuffix;

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
        this.sumSuffix = sumSuffix;
        return this;
    }

    /**
     * 是否隐藏分组汇总。
     */
    public Boolean isGroupSumHidden;

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
        this.isGroupSumHidden = isGroupSumHidden;
        return this;
    }

    /**
     * 是否隐藏总计汇总。
     */
    public Boolean isTtlSumHidden;

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
        this.isTtlSumHidden = isTtlSumHidden;
        return this;
    }

    /**
     * 横向对齐。
     */
    public String hAlign;

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
        this.hAlign = hAlign;
        return this;
    }

    /**
     * 是否固定。
     */
    public Boolean isFixed;

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
        this.isFixed = isFixed;
        return this;
    }

    /**
     * 显示格式。
     */
    public String displayFormat;

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
        this.displayFormat = displayFormat;
        return this;
    }

    /**
     * 变量名称。
     */
    public String varName;

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
        this.varName = varName;
        return this;
    }

    /**
     * 引用的实体视图。
     */
    public String refedSevId;

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
        this.refedSevId = refedSevId;
        return this;
    }

    /**
     * 引用的视图部分列表。
     */
    public String refedVpIds;

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
        this.refedVpIds = refedVpIds;
        return this;
    }

    /**
     * 引用的WHERE语句。
     */
    public String refedWhereClause;

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
        this.refedWhereClause = refedWhereClause;
        return this;
    }

    /**
     * 引用删除模式。
     */
    public String refedOnDelMode;

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
        this.refedOnDelMode = refedOnDelMode;
        return this;
    }

    /**
     * 引用的启用缓存。
     */
    public Boolean refedCacheEnabled;

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
        this.refedCacheEnabled = refedCacheEnabled;
        return this;
    }

    /**
     * 引用的下拉禁用。
     */
    public Boolean refedDropdownDisabled;

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
        this.refedDropdownDisabled = refedDropdownDisabled;
        return this;
    }

    /**
     * 引用的搜索禁用。
     */
    public Boolean refedSearchDisabled;

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
        this.refedSearchDisabled = refedSearchDisabled;
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
    public AdAtt setRefedPopupWidth(String refedPopupWidth) {
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
    public AdAtt setRefedPopupHeight(String refedPopupHeight) {
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
    public AdAtt setRefedSelectableLogic(String refedSelectableLogic) {
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
    public AdAtt setDefSql(String defSql) {
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
    public AdAtt setIsMandatory(Boolean isMandatory) {
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
    public AdAtt setAdIndexTypeId(String adIndexTypeId) {
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
    public AdAtt setCreateFk(Boolean createFk) {
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
    public AdAtt setFormItemTitleHidden(Boolean formItemTitleHidden) {
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
    public AdAtt setFormItemTitleTop(Boolean formItemTitleTop) {
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
    public AdAtt setFormItemTitleWrap(Boolean formItemTitleWrap) {
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
    public AdAtt setFormItemWidth(String formItemWidth) {
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
    public AdAtt setFormItemHeight(String formItemHeight) {
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
    public AdAtt setFormItemRowSpan(Integer formItemRowSpan) {
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
    public AdAtt setFormItemColSpan(Integer formItemColSpan) {
        this.formItemColSpan = formItemColSpan;
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
    public static AdAtt insertData() {
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
    public static AdAtt selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<AdAtt> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<AdAtt> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
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
    public static void copyCols(AdAtt fromModel, AdAtt toModel, List<String> includeCols, List<String> excludeCols) {
        ModelHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}