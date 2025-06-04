package com.bid.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 资料文件。
 */
public class CcDocFile {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcDocFile> modelHelper = new ModelHelper<>("CC_DOC_FILE", new CcDocFile());

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

    public static final String ENT_CODE = "CC_DOC_FILE";
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
         * 快捷码。
         */
        public static final String FAST_CODE = "FAST_CODE";
        /**
         * 图标。
         */
        public static final String ICON_FILE_GROUP_ID = "ICON_FILE_GROUP_ID";
        /**
         * 序号。
         */
        public static final String SEQ_NO = "SEQ_NO";
        /**
         * 收藏用户。
         */
        public static final String CC_FAVORITES_USER_IDS = "CC_FAVORITES_USER_IDS";
        /**
         * 名称。
         */
        public static final String NAME = "NAME";
        /**
         * 项目。
         */
        public static final String CC_PRJ_ID = "CC_PRJ_ID";
        /**
         * 资料文件日期。
         */
        public static final String CC_DOC_DATE = "CC_DOC_DATE";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 最后修改日期时间。
         */
        public static final String LAST_MODI_DT = "LAST_MODI_DT";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 资料文件类型。
         */
        public static final String CC_DOC_FILE_TYPE_ID = "CC_DOC_FILE_TYPE_ID";
        /**
         * 附件。
         */
        public static final String CC_ATTACHMENT = "CC_ATTACHMENT";
        /**
         * 缩略图附件。
         */
        public static final String CC_PREVIEW_ATTACHMENT = "CC_PREVIEW_ATTACHMENT";
        /**
         * 图纸审批状态。
         */
        public static final String CC_DRAWING_APPROVED_STATUS = "CC_DRAWING_APPROVED_STATUS";
        /**
         * 预览文件ID。
         */
        public static final String CC_PREVIEW_FILE_ID = "CC_PREVIEW_FILE_ID";
        /**
         * 预览文件显示大小。
         */
        public static final String CC_PREVIEW_DSP_SIZE = "CC_PREVIEW_DSP_SIZE";
        /**
         * 预览转换状态。
         */
        public static final String CC_PREVIEW_CONVERSION_STATUS_ID = "CC_PREVIEW_CONVERSION_STATUS_ID";
        /**
         * 预览转换开始。
         */
        public static final String CC_PREVIEW_CONVERSION_START = "CC_PREVIEW_CONVERSION_START";
        /**
         * 预览转换结束。
         */
        public static final String CC_PREVIEW_CONVERSION_END = "CC_PREVIEW_CONVERSION_END";
        /**
         * 预览地址。
         */
        public static final String CC_PREVIEW_URL = "CC_PREVIEW_URL";
        /**
         * 默认。
         */
        public static final String IS_DEFAULT = "IS_DEFAULT";
        /**
         * 是否自动转换。
         */
        public static final String IS_AUTO_CONVERT = "IS_AUTO_CONVERT";
        /**
         * 是否收藏。
         */
        public static final String IS_FAVORITES = "IS_FAVORITES";
        /**
         * 资料目录。
         */
        public static final String CC_DOC_DIR_ID = "CC_DOC_DIR_ID";
        /**
         * 竣工验收资料文件类型。
         */
        public static final String CC_COMPLET_FILE_TYPE_ID = "CC_COMPLET_FILE_TYPE_ID";
        /**
         * 竣工验收资料类型。
         */
        public static final String CC_COMPLET_TYPE_ID = "CC_COMPLET_TYPE_ID";
        /**
         * 资料文件来源。
         */
        public static final String CC_DOC_FILE_FROM = "CC_DOC_FILE_FROM";
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
    public CcDocFile setId(String id) {
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
    public CcDocFile setVer(Integer ver) {
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
    public CcDocFile setTs(LocalDateTime ts) {
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
    public CcDocFile setIsPreset(Boolean isPreset) {
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
    public CcDocFile setCrtDt(LocalDateTime crtDt) {
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
    public CcDocFile setLastModiUserId(String lastModiUserId) {
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
    public CcDocFile setStatus(String status) {
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
    public CcDocFile setLkWfInstId(String lkWfInstId) {
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
    public CcDocFile setCode(String code) {
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
     * 快捷码。
     */
    private String fastCode;

    /**
     * 获取：快捷码。
     */
    public String getFastCode() {
        return this.fastCode;
    }

    /**
     * 设置：快捷码。
     */
    public CcDocFile setFastCode(String fastCode) {
        if (this.fastCode == null && fastCode == null) {
            // 均为null，不做处理。
        } else if (this.fastCode != null && fastCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.fastCode.compareTo(fastCode) != 0) {
                this.fastCode = fastCode;
                if (!this.toUpdateCols.contains("FAST_CODE")) {
                    this.toUpdateCols.add("FAST_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fastCode = fastCode;
            if (!this.toUpdateCols.contains("FAST_CODE")) {
                this.toUpdateCols.add("FAST_CODE");
            }
        }
        return this;
    }

    /**
     * 图标。
     */
    private String iconFileGroupId;

    /**
     * 获取：图标。
     */
    public String getIconFileGroupId() {
        return this.iconFileGroupId;
    }

    /**
     * 设置：图标。
     */
    public CcDocFile setIconFileGroupId(String iconFileGroupId) {
        if (this.iconFileGroupId == null && iconFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.iconFileGroupId != null && iconFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.iconFileGroupId.compareTo(iconFileGroupId) != 0) {
                this.iconFileGroupId = iconFileGroupId;
                if (!this.toUpdateCols.contains("ICON_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("ICON_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.iconFileGroupId = iconFileGroupId;
            if (!this.toUpdateCols.contains("ICON_FILE_GROUP_ID")) {
                this.toUpdateCols.add("ICON_FILE_GROUP_ID");
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
    public CcDocFile setSeqNo(BigDecimal seqNo) {
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
     * 收藏用户。
     */
    private String ccFavoritesUserIds;

    /**
     * 获取：收藏用户。
     */
    public String getCcFavoritesUserIds() {
        return this.ccFavoritesUserIds;
    }

    /**
     * 设置：收藏用户。
     */
    public CcDocFile setCcFavoritesUserIds(String ccFavoritesUserIds) {
        if (this.ccFavoritesUserIds == null && ccFavoritesUserIds == null) {
            // 均为null，不做处理。
        } else if (this.ccFavoritesUserIds != null && ccFavoritesUserIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccFavoritesUserIds.compareTo(ccFavoritesUserIds) != 0) {
                this.ccFavoritesUserIds = ccFavoritesUserIds;
                if (!this.toUpdateCols.contains("CC_FAVORITES_USER_IDS")) {
                    this.toUpdateCols.add("CC_FAVORITES_USER_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccFavoritesUserIds = ccFavoritesUserIds;
            if (!this.toUpdateCols.contains("CC_FAVORITES_USER_IDS")) {
                this.toUpdateCols.add("CC_FAVORITES_USER_IDS");
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
    public CcDocFile setName(String name) {
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
     * 项目。
     */
    private String ccPrjId;

    /**
     * 获取：项目。
     */
    public String getCcPrjId() {
        return this.ccPrjId;
    }

    /**
     * 设置：项目。
     */
    public CcDocFile setCcPrjId(String ccPrjId) {
        if (this.ccPrjId == null && ccPrjId == null) {
            // 均为null，不做处理。
        } else if (this.ccPrjId != null && ccPrjId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPrjId.compareTo(ccPrjId) != 0) {
                this.ccPrjId = ccPrjId;
                if (!this.toUpdateCols.contains("CC_PRJ_ID")) {
                    this.toUpdateCols.add("CC_PRJ_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPrjId = ccPrjId;
            if (!this.toUpdateCols.contains("CC_PRJ_ID")) {
                this.toUpdateCols.add("CC_PRJ_ID");
            }
        }
        return this;
    }

    /**
     * 资料文件日期。
     */
    private LocalDate ccDocDate;

    /**
     * 获取：资料文件日期。
     */
    public LocalDate getCcDocDate() {
        return this.ccDocDate;
    }

    /**
     * 设置：资料文件日期。
     */
    public CcDocFile setCcDocDate(LocalDate ccDocDate) {
        if (this.ccDocDate == null && ccDocDate == null) {
            // 均为null，不做处理。
        } else if (this.ccDocDate != null && ccDocDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccDocDate.compareTo(ccDocDate) != 0) {
                this.ccDocDate = ccDocDate;
                if (!this.toUpdateCols.contains("CC_DOC_DATE")) {
                    this.toUpdateCols.add("CC_DOC_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccDocDate = ccDocDate;
            if (!this.toUpdateCols.contains("CC_DOC_DATE")) {
                this.toUpdateCols.add("CC_DOC_DATE");
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
    public CcDocFile setCrtUserId(String crtUserId) {
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
    public CcDocFile setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcDocFile setRemark(String remark) {
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
     * 资料文件类型。
     */
    private String ccDocFileTypeId;

    /**
     * 获取：资料文件类型。
     */
    public String getCcDocFileTypeId() {
        return this.ccDocFileTypeId;
    }

    /**
     * 设置：资料文件类型。
     */
    public CcDocFile setCcDocFileTypeId(String ccDocFileTypeId) {
        if (this.ccDocFileTypeId == null && ccDocFileTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccDocFileTypeId != null && ccDocFileTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccDocFileTypeId.compareTo(ccDocFileTypeId) != 0) {
                this.ccDocFileTypeId = ccDocFileTypeId;
                if (!this.toUpdateCols.contains("CC_DOC_FILE_TYPE_ID")) {
                    this.toUpdateCols.add("CC_DOC_FILE_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccDocFileTypeId = ccDocFileTypeId;
            if (!this.toUpdateCols.contains("CC_DOC_FILE_TYPE_ID")) {
                this.toUpdateCols.add("CC_DOC_FILE_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 附件。
     */
    private String ccAttachment;

    /**
     * 获取：附件。
     */
    public String getCcAttachment() {
        return this.ccAttachment;
    }

    /**
     * 设置：附件。
     */
    public CcDocFile setCcAttachment(String ccAttachment) {
        if (this.ccAttachment == null && ccAttachment == null) {
            // 均为null，不做处理。
        } else if (this.ccAttachment != null && ccAttachment != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccAttachment.compareTo(ccAttachment) != 0) {
                this.ccAttachment = ccAttachment;
                if (!this.toUpdateCols.contains("CC_ATTACHMENT")) {
                    this.toUpdateCols.add("CC_ATTACHMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccAttachment = ccAttachment;
            if (!this.toUpdateCols.contains("CC_ATTACHMENT")) {
                this.toUpdateCols.add("CC_ATTACHMENT");
            }
        }
        return this;
    }

    /**
     * 缩略图附件。
     */
    private String ccPreviewAttachment;

    /**
     * 获取：缩略图附件。
     */
    public String getCcPreviewAttachment() {
        return this.ccPreviewAttachment;
    }

    /**
     * 设置：缩略图附件。
     */
    public CcDocFile setCcPreviewAttachment(String ccPreviewAttachment) {
        if (this.ccPreviewAttachment == null && ccPreviewAttachment == null) {
            // 均为null，不做处理。
        } else if (this.ccPreviewAttachment != null && ccPreviewAttachment != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPreviewAttachment.compareTo(ccPreviewAttachment) != 0) {
                this.ccPreviewAttachment = ccPreviewAttachment;
                if (!this.toUpdateCols.contains("CC_PREVIEW_ATTACHMENT")) {
                    this.toUpdateCols.add("CC_PREVIEW_ATTACHMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPreviewAttachment = ccPreviewAttachment;
            if (!this.toUpdateCols.contains("CC_PREVIEW_ATTACHMENT")) {
                this.toUpdateCols.add("CC_PREVIEW_ATTACHMENT");
            }
        }
        return this;
    }

    /**
     * 图纸审批状态。
     */
    private Integer ccDrawingApprovedStatus;

    /**
     * 获取：图纸审批状态。
     */
    public Integer getCcDrawingApprovedStatus() {
        return this.ccDrawingApprovedStatus;
    }

    /**
     * 设置：图纸审批状态。
     */
    public CcDocFile setCcDrawingApprovedStatus(Integer ccDrawingApprovedStatus) {
        if (this.ccDrawingApprovedStatus == null && ccDrawingApprovedStatus == null) {
            // 均为null，不做处理。
        } else if (this.ccDrawingApprovedStatus != null && ccDrawingApprovedStatus != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccDrawingApprovedStatus.compareTo(ccDrawingApprovedStatus) != 0) {
                this.ccDrawingApprovedStatus = ccDrawingApprovedStatus;
                if (!this.toUpdateCols.contains("CC_DRAWING_APPROVED_STATUS")) {
                    this.toUpdateCols.add("CC_DRAWING_APPROVED_STATUS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccDrawingApprovedStatus = ccDrawingApprovedStatus;
            if (!this.toUpdateCols.contains("CC_DRAWING_APPROVED_STATUS")) {
                this.toUpdateCols.add("CC_DRAWING_APPROVED_STATUS");
            }
        }
        return this;
    }

    /**
     * 预览文件ID。
     */
    private String ccPreviewFileId;

    /**
     * 获取：预览文件ID。
     */
    public String getCcPreviewFileId() {
        return this.ccPreviewFileId;
    }

    /**
     * 设置：预览文件ID。
     */
    public CcDocFile setCcPreviewFileId(String ccPreviewFileId) {
        if (this.ccPreviewFileId == null && ccPreviewFileId == null) {
            // 均为null，不做处理。
        } else if (this.ccPreviewFileId != null && ccPreviewFileId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPreviewFileId.compareTo(ccPreviewFileId) != 0) {
                this.ccPreviewFileId = ccPreviewFileId;
                if (!this.toUpdateCols.contains("CC_PREVIEW_FILE_ID")) {
                    this.toUpdateCols.add("CC_PREVIEW_FILE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPreviewFileId = ccPreviewFileId;
            if (!this.toUpdateCols.contains("CC_PREVIEW_FILE_ID")) {
                this.toUpdateCols.add("CC_PREVIEW_FILE_ID");
            }
        }
        return this;
    }

    /**
     * 预览文件显示大小。
     */
    private String ccPreviewDspSize;

    /**
     * 获取：预览文件显示大小。
     */
    public String getCcPreviewDspSize() {
        return this.ccPreviewDspSize;
    }

    /**
     * 设置：预览文件显示大小。
     */
    public CcDocFile setCcPreviewDspSize(String ccPreviewDspSize) {
        if (this.ccPreviewDspSize == null && ccPreviewDspSize == null) {
            // 均为null，不做处理。
        } else if (this.ccPreviewDspSize != null && ccPreviewDspSize != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPreviewDspSize.compareTo(ccPreviewDspSize) != 0) {
                this.ccPreviewDspSize = ccPreviewDspSize;
                if (!this.toUpdateCols.contains("CC_PREVIEW_DSP_SIZE")) {
                    this.toUpdateCols.add("CC_PREVIEW_DSP_SIZE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPreviewDspSize = ccPreviewDspSize;
            if (!this.toUpdateCols.contains("CC_PREVIEW_DSP_SIZE")) {
                this.toUpdateCols.add("CC_PREVIEW_DSP_SIZE");
            }
        }
        return this;
    }

    /**
     * 预览转换状态。
     */
    private String ccPreviewConversionStatusId;

    /**
     * 获取：预览转换状态。
     */
    public String getCcPreviewConversionStatusId() {
        return this.ccPreviewConversionStatusId;
    }

    /**
     * 设置：预览转换状态。
     */
    public CcDocFile setCcPreviewConversionStatusId(String ccPreviewConversionStatusId) {
        if (this.ccPreviewConversionStatusId == null && ccPreviewConversionStatusId == null) {
            // 均为null，不做处理。
        } else if (this.ccPreviewConversionStatusId != null && ccPreviewConversionStatusId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPreviewConversionStatusId.compareTo(ccPreviewConversionStatusId) != 0) {
                this.ccPreviewConversionStatusId = ccPreviewConversionStatusId;
                if (!this.toUpdateCols.contains("CC_PREVIEW_CONVERSION_STATUS_ID")) {
                    this.toUpdateCols.add("CC_PREVIEW_CONVERSION_STATUS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPreviewConversionStatusId = ccPreviewConversionStatusId;
            if (!this.toUpdateCols.contains("CC_PREVIEW_CONVERSION_STATUS_ID")) {
                this.toUpdateCols.add("CC_PREVIEW_CONVERSION_STATUS_ID");
            }
        }
        return this;
    }

    /**
     * 预览转换开始。
     */
    private LocalDateTime ccPreviewConversionStart;

    /**
     * 获取：预览转换开始。
     */
    public LocalDateTime getCcPreviewConversionStart() {
        return this.ccPreviewConversionStart;
    }

    /**
     * 设置：预览转换开始。
     */
    public CcDocFile setCcPreviewConversionStart(LocalDateTime ccPreviewConversionStart) {
        if (this.ccPreviewConversionStart == null && ccPreviewConversionStart == null) {
            // 均为null，不做处理。
        } else if (this.ccPreviewConversionStart != null && ccPreviewConversionStart != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPreviewConversionStart.compareTo(ccPreviewConversionStart) != 0) {
                this.ccPreviewConversionStart = ccPreviewConversionStart;
                if (!this.toUpdateCols.contains("CC_PREVIEW_CONVERSION_START")) {
                    this.toUpdateCols.add("CC_PREVIEW_CONVERSION_START");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPreviewConversionStart = ccPreviewConversionStart;
            if (!this.toUpdateCols.contains("CC_PREVIEW_CONVERSION_START")) {
                this.toUpdateCols.add("CC_PREVIEW_CONVERSION_START");
            }
        }
        return this;
    }

    /**
     * 预览转换结束。
     */
    private LocalDateTime ccPreviewConversionEnd;

    /**
     * 获取：预览转换结束。
     */
    public LocalDateTime getCcPreviewConversionEnd() {
        return this.ccPreviewConversionEnd;
    }

    /**
     * 设置：预览转换结束。
     */
    public CcDocFile setCcPreviewConversionEnd(LocalDateTime ccPreviewConversionEnd) {
        if (this.ccPreviewConversionEnd == null && ccPreviewConversionEnd == null) {
            // 均为null，不做处理。
        } else if (this.ccPreviewConversionEnd != null && ccPreviewConversionEnd != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPreviewConversionEnd.compareTo(ccPreviewConversionEnd) != 0) {
                this.ccPreviewConversionEnd = ccPreviewConversionEnd;
                if (!this.toUpdateCols.contains("CC_PREVIEW_CONVERSION_END")) {
                    this.toUpdateCols.add("CC_PREVIEW_CONVERSION_END");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPreviewConversionEnd = ccPreviewConversionEnd;
            if (!this.toUpdateCols.contains("CC_PREVIEW_CONVERSION_END")) {
                this.toUpdateCols.add("CC_PREVIEW_CONVERSION_END");
            }
        }
        return this;
    }

    /**
     * 预览地址。
     */
    private String ccPreviewUrl;

    /**
     * 获取：预览地址。
     */
    public String getCcPreviewUrl() {
        return this.ccPreviewUrl;
    }

    /**
     * 设置：预览地址。
     */
    public CcDocFile setCcPreviewUrl(String ccPreviewUrl) {
        if (this.ccPreviewUrl == null && ccPreviewUrl == null) {
            // 均为null，不做处理。
        } else if (this.ccPreviewUrl != null && ccPreviewUrl != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPreviewUrl.compareTo(ccPreviewUrl) != 0) {
                this.ccPreviewUrl = ccPreviewUrl;
                if (!this.toUpdateCols.contains("CC_PREVIEW_URL")) {
                    this.toUpdateCols.add("CC_PREVIEW_URL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPreviewUrl = ccPreviewUrl;
            if (!this.toUpdateCols.contains("CC_PREVIEW_URL")) {
                this.toUpdateCols.add("CC_PREVIEW_URL");
            }
        }
        return this;
    }

    /**
     * 默认。
     */
    private Boolean isDefault;

    /**
     * 获取：默认。
     */
    public Boolean getIsDefault() {
        return this.isDefault;
    }

    /**
     * 设置：默认。
     */
    public CcDocFile setIsDefault(Boolean isDefault) {
        if (this.isDefault == null && isDefault == null) {
            // 均为null，不做处理。
        } else if (this.isDefault != null && isDefault != null) {
            // 均非null，判定不等，再做处理：
            if (this.isDefault.compareTo(isDefault) != 0) {
                this.isDefault = isDefault;
                if (!this.toUpdateCols.contains("IS_DEFAULT")) {
                    this.toUpdateCols.add("IS_DEFAULT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isDefault = isDefault;
            if (!this.toUpdateCols.contains("IS_DEFAULT")) {
                this.toUpdateCols.add("IS_DEFAULT");
            }
        }
        return this;
    }

    /**
     * 是否自动转换。
     */
    private Boolean isAutoConvert;

    /**
     * 获取：是否自动转换。
     */
    public Boolean getIsAutoConvert() {
        return this.isAutoConvert;
    }

    /**
     * 设置：是否自动转换。
     */
    public CcDocFile setIsAutoConvert(Boolean isAutoConvert) {
        if (this.isAutoConvert == null && isAutoConvert == null) {
            // 均为null，不做处理。
        } else if (this.isAutoConvert != null && isAutoConvert != null) {
            // 均非null，判定不等，再做处理：
            if (this.isAutoConvert.compareTo(isAutoConvert) != 0) {
                this.isAutoConvert = isAutoConvert;
                if (!this.toUpdateCols.contains("IS_AUTO_CONVERT")) {
                    this.toUpdateCols.add("IS_AUTO_CONVERT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isAutoConvert = isAutoConvert;
            if (!this.toUpdateCols.contains("IS_AUTO_CONVERT")) {
                this.toUpdateCols.add("IS_AUTO_CONVERT");
            }
        }
        return this;
    }

    /**
     * 是否收藏。
     */
    private Boolean isFavorites;

    /**
     * 获取：是否收藏。
     */
    public Boolean getIsFavorites() {
        return this.isFavorites;
    }

    /**
     * 设置：是否收藏。
     */
    public CcDocFile setIsFavorites(Boolean isFavorites) {
        if (this.isFavorites == null && isFavorites == null) {
            // 均为null，不做处理。
        } else if (this.isFavorites != null && isFavorites != null) {
            // 均非null，判定不等，再做处理：
            if (this.isFavorites.compareTo(isFavorites) != 0) {
                this.isFavorites = isFavorites;
                if (!this.toUpdateCols.contains("IS_FAVORITES")) {
                    this.toUpdateCols.add("IS_FAVORITES");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isFavorites = isFavorites;
            if (!this.toUpdateCols.contains("IS_FAVORITES")) {
                this.toUpdateCols.add("IS_FAVORITES");
            }
        }
        return this;
    }

    /**
     * 资料目录。
     */
    private String ccDocDirId;

    /**
     * 获取：资料目录。
     */
    public String getCcDocDirId() {
        return this.ccDocDirId;
    }

    /**
     * 设置：资料目录。
     */
    public CcDocFile setCcDocDirId(String ccDocDirId) {
        if (this.ccDocDirId == null && ccDocDirId == null) {
            // 均为null，不做处理。
        } else if (this.ccDocDirId != null && ccDocDirId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccDocDirId.compareTo(ccDocDirId) != 0) {
                this.ccDocDirId = ccDocDirId;
                if (!this.toUpdateCols.contains("CC_DOC_DIR_ID")) {
                    this.toUpdateCols.add("CC_DOC_DIR_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccDocDirId = ccDocDirId;
            if (!this.toUpdateCols.contains("CC_DOC_DIR_ID")) {
                this.toUpdateCols.add("CC_DOC_DIR_ID");
            }
        }
        return this;
    }

    /**
     * 竣工验收资料文件类型。
     */
    private String ccCompletFileTypeId;

    /**
     * 获取：竣工验收资料文件类型。
     */
    public String getCcCompletFileTypeId() {
        return this.ccCompletFileTypeId;
    }

    /**
     * 设置：竣工验收资料文件类型。
     */
    public CcDocFile setCcCompletFileTypeId(String ccCompletFileTypeId) {
        if (this.ccCompletFileTypeId == null && ccCompletFileTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccCompletFileTypeId != null && ccCompletFileTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccCompletFileTypeId.compareTo(ccCompletFileTypeId) != 0) {
                this.ccCompletFileTypeId = ccCompletFileTypeId;
                if (!this.toUpdateCols.contains("CC_COMPLET_FILE_TYPE_ID")) {
                    this.toUpdateCols.add("CC_COMPLET_FILE_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccCompletFileTypeId = ccCompletFileTypeId;
            if (!this.toUpdateCols.contains("CC_COMPLET_FILE_TYPE_ID")) {
                this.toUpdateCols.add("CC_COMPLET_FILE_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 竣工验收资料类型。
     */
    private String ccCompletTypeId;

    /**
     * 获取：竣工验收资料类型。
     */
    public String getCcCompletTypeId() {
        return this.ccCompletTypeId;
    }

    /**
     * 设置：竣工验收资料类型。
     */
    public CcDocFile setCcCompletTypeId(String ccCompletTypeId) {
        if (this.ccCompletTypeId == null && ccCompletTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccCompletTypeId != null && ccCompletTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccCompletTypeId.compareTo(ccCompletTypeId) != 0) {
                this.ccCompletTypeId = ccCompletTypeId;
                if (!this.toUpdateCols.contains("CC_COMPLET_TYPE_ID")) {
                    this.toUpdateCols.add("CC_COMPLET_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccCompletTypeId = ccCompletTypeId;
            if (!this.toUpdateCols.contains("CC_COMPLET_TYPE_ID")) {
                this.toUpdateCols.add("CC_COMPLET_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 资料文件来源。
     */
    private Integer ccDocFileFrom;

    /**
     * 获取：资料文件来源。
     */
    public Integer getCcDocFileFrom() {
        return this.ccDocFileFrom;
    }

    /**
     * 设置：资料文件来源。
     */
    public CcDocFile setCcDocFileFrom(Integer ccDocFileFrom) {
        if (this.ccDocFileFrom == null && ccDocFileFrom == null) {
            // 均为null，不做处理。
        } else if (this.ccDocFileFrom != null && ccDocFileFrom != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccDocFileFrom.compareTo(ccDocFileFrom) != 0) {
                this.ccDocFileFrom = ccDocFileFrom;
                if (!this.toUpdateCols.contains("CC_DOC_FILE_FROM")) {
                    this.toUpdateCols.add("CC_DOC_FILE_FROM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccDocFileFrom = ccDocFileFrom;
            if (!this.toUpdateCols.contains("CC_DOC_FILE_FROM")) {
                this.toUpdateCols.add("CC_DOC_FILE_FROM");
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
     * 根据ID插入数据。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param refreshThis 更新后，是否刷新当前对象。刷新时将刷新所有列。
     */
    public void insertById(boolean refreshThis) {
        insertById(null, null, refreshThis);
    }

    /**
     * 根据ID插入数据。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     */
    public void insertById() {
        insertById(null, null, false);
    }

    /**
     * 根据ID更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param includeCols 更新时包含的列，空为包含所有。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @param refreshThis 更新后，是否刷新当前对象。刷新时将刷新所有列。
     */
    public void updateById(List<String> includeCols, List<String> excludeCols, boolean refreshThis) {
        if (SharedUtil.isEmpty(includeCols) && SharedUtil.isEmpty(toUpdateCols)) {
            // 既未指明includeCols，也无toUpdateCols，则不更新。

            if (refreshThis) {
                modelHelper.refreshThis(this.id, this, "无需更新，直接刷新");
            }
        } else {
            // 若已指明includeCols，或有toUpdateCols；则先以includeCols为准，再以toUpdateCols为准：
            modelHelper.updateById(SharedUtil.isEmpty(includeCols) ? toUpdateCols : includeCols, excludeCols, refreshThis, this.id, this);
            this.clearToUpdateCols();
        }
    }

    /**
     * 根据ID更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param refreshThis 更新后，是否刷新当前对象。刷新时将刷新所有列。
     */
    public void updateById(boolean refreshThis) {
        updateById(null, null, refreshThis);
    }

    /**
     * 根据ID更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     */
    public void updateById() {
        updateById(null, null, false);
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
    public static CcDocFile newData() {
        CcDocFile obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcDocFile insertData() {
        CcDocFile obj = modelHelper.insertData();
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
    public static CcDocFile selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcDocFile obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcDocFile selectById(String id) {
        return selectById(id, null, null);
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids         ID列表。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcDocFile> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcDocFile> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcDocFile> selectByIds(List<String> ids) {
        return selectByIds(ids, null, null);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where       Where条件。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcDocFile> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcDocFile> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcDocFile> selectByWhere(Where where) {
        return selectByWhere(where, null, null);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where       Where条件。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象。
     */
    public static CcDocFile selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcDocFile> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcDocFile.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcDocFile selectOneByWhere(Where where) {
        return selectOneByWhere(where, null, null);
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
     * 根据ID更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param id          ID。
     * @param keyValueMap 要更新的列和新值。其中，key为列名、value为新值（可为null）。
     * @return 影响的行数。
     */
    public static int updateById(String id, Map<String, Object> keyValueMap) {
        return updateById(id, keyValueMap, null, null);
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
     * 根据ID列表更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param ids         ID列表。
     * @param keyValueMap 要更新的列和新值。其中，key为列名、value为新值（可为null）。
     * @return 影响的行数。
     */
    public static int updateByIds(List<String> ids, Map<String, Object> keyValueMap) {
        return updateByIds(ids, keyValueMap, null, null);
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
     * 根据Where条件更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param where       Where条件。
     * @param keyValueMap 要更新的列和新值。其中，key为列名、value为新值（可为null）。
     * @return 影响的行数。
     */
    public static int updateByWhere(Where where, Map<String, Object> keyValueMap) {
        return updateByWhere(where, keyValueMap, null, null);
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
    public static void copyCols(CcDocFile fromModel, CcDocFile toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcDocFile fromModel, CcDocFile toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}