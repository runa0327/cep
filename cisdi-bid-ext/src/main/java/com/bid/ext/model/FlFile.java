package com.bid.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文件。
 */
public class FlFile {

    /**
     * 模型助手。
     */
    private static final ModelHelper<FlFile> modelHelper = new ModelHelper<>("FL_FILE", new FlFile());

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

    public static final String ENT_CODE = "FL_FILE";
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
         * 快捷码。
         */
        public static final String FAST_CODE = "FAST_CODE";
        /**
         * 图标。
         */
        public static final String ICON_FILE_GROUP_ID = "ICON_FILE_GROUP_ID";
        /**
         * 扩展名。
         */
        public static final String EXT = "EXT";
        /**
         * 显示名称。
         */
        public static final String DSP_NAME = "DSP_NAME";
        /**
         * 大小KB。
         */
        public static final String SIZE_KB = "SIZE_KB";
        /**
         * 显示大小。
         */
        public static final String DSP_SIZE = "DSP_SIZE";
        /**
         * 文件内联URL。
         */
        public static final String FILE_INLINE_URL = "FILE_INLINE_URL";
        /**
         * 文件附件URL。
         */
        public static final String FILE_ATTACHMENT_URL = "FILE_ATTACHMENT_URL";
        /**
         * 上传时间。
         */
        public static final String UPLOAD_DTTM = "UPLOAD_DTTM";
        /**
         * 物理位置。
         */
        public static final String PHYSICAL_LOCATION = "PHYSICAL_LOCATION";
        /**
         * 原始文件物理位置。
         */
        public static final String ORIGIN_FILE_PHYSICAL_LOCATION = "ORIGIN_FILE_PHYSICAL_LOCATION";
        /**
         * 是否公开读取。
         */
        public static final String IS_PUBLIC_READ = "IS_PUBLIC_READ";
        /**
         * 路径。
         */
        public static final String FL_PATH_ID = "FL_PATH_ID";
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
    public FlFile setId(String id) {
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
    public FlFile setVer(Integer ver) {
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
    public FlFile setTs(LocalDateTime ts) {
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
    public FlFile setIsPreset(Boolean isPreset) {
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
    public FlFile setCrtDt(LocalDateTime crtDt) {
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
    public FlFile setCrtUserId(String crtUserId) {
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
    public FlFile setLastModiDt(LocalDateTime lastModiDt) {
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
    public FlFile setLastModiUserId(String lastModiUserId) {
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
    public FlFile setStatus(String status) {
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
    public FlFile setLkWfInstId(String lkWfInstId) {
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
    public FlFile setCode(String code) {
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
    public FlFile setName(String name) {
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
    public FlFile setRemark(String remark) {
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
    public FlFile setFastCode(String fastCode) {
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
    public FlFile setIconFileGroupId(String iconFileGroupId) {
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
     * 扩展名。
     */
    private String ext;

    /**
     * 获取：扩展名。
     */
    public String getExt() {
        return this.ext;
    }

    /**
     * 设置：扩展名。
     */
    public FlFile setExt(String ext) {
        if (this.ext == null && ext == null) {
            // 均为null，不做处理。
        } else if (this.ext != null && ext != null) {
            // 均非null，判定不等，再做处理：
            if (this.ext.compareTo(ext) != 0) {
                this.ext = ext;
                if (!this.toUpdateCols.contains("EXT")) {
                    this.toUpdateCols.add("EXT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ext = ext;
            if (!this.toUpdateCols.contains("EXT")) {
                this.toUpdateCols.add("EXT");
            }
        }
        return this;
    }

    /**
     * 显示名称。
     */
    private String dspName;

    /**
     * 获取：显示名称。
     */
    public String getDspName() {
        return this.dspName;
    }

    /**
     * 设置：显示名称。
     */
    public FlFile setDspName(String dspName) {
        if (this.dspName == null && dspName == null) {
            // 均为null，不做处理。
        } else if (this.dspName != null && dspName != null) {
            // 均非null，判定不等，再做处理：
            if (this.dspName.compareTo(dspName) != 0) {
                this.dspName = dspName;
                if (!this.toUpdateCols.contains("DSP_NAME")) {
                    this.toUpdateCols.add("DSP_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dspName = dspName;
            if (!this.toUpdateCols.contains("DSP_NAME")) {
                this.toUpdateCols.add("DSP_NAME");
            }
        }
        return this;
    }

    /**
     * 大小KB。
     */
    private BigDecimal sizeKb;

    /**
     * 获取：大小KB。
     */
    public BigDecimal getSizeKb() {
        return this.sizeKb;
    }

    /**
     * 设置：大小KB。
     */
    public FlFile setSizeKb(BigDecimal sizeKb) {
        if (this.sizeKb == null && sizeKb == null) {
            // 均为null，不做处理。
        } else if (this.sizeKb != null && sizeKb != null) {
            // 均非null，判定不等，再做处理：
            if (this.sizeKb.compareTo(sizeKb) != 0) {
                this.sizeKb = sizeKb;
                if (!this.toUpdateCols.contains("SIZE_KB")) {
                    this.toUpdateCols.add("SIZE_KB");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.sizeKb = sizeKb;
            if (!this.toUpdateCols.contains("SIZE_KB")) {
                this.toUpdateCols.add("SIZE_KB");
            }
        }
        return this;
    }

    /**
     * 显示大小。
     */
    private String dspSize;

    /**
     * 获取：显示大小。
     */
    public String getDspSize() {
        return this.dspSize;
    }

    /**
     * 设置：显示大小。
     */
    public FlFile setDspSize(String dspSize) {
        if (this.dspSize == null && dspSize == null) {
            // 均为null，不做处理。
        } else if (this.dspSize != null && dspSize != null) {
            // 均非null，判定不等，再做处理：
            if (this.dspSize.compareTo(dspSize) != 0) {
                this.dspSize = dspSize;
                if (!this.toUpdateCols.contains("DSP_SIZE")) {
                    this.toUpdateCols.add("DSP_SIZE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dspSize = dspSize;
            if (!this.toUpdateCols.contains("DSP_SIZE")) {
                this.toUpdateCols.add("DSP_SIZE");
            }
        }
        return this;
    }

    /**
     * 文件内联URL。
     */
    private String fileInlineUrl;

    /**
     * 获取：文件内联URL。
     */
    public String getFileInlineUrl() {
        return this.fileInlineUrl;
    }

    /**
     * 设置：文件内联URL。
     */
    public FlFile setFileInlineUrl(String fileInlineUrl) {
        if (this.fileInlineUrl == null && fileInlineUrl == null) {
            // 均为null，不做处理。
        } else if (this.fileInlineUrl != null && fileInlineUrl != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileInlineUrl.compareTo(fileInlineUrl) != 0) {
                this.fileInlineUrl = fileInlineUrl;
                if (!this.toUpdateCols.contains("FILE_INLINE_URL")) {
                    this.toUpdateCols.add("FILE_INLINE_URL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileInlineUrl = fileInlineUrl;
            if (!this.toUpdateCols.contains("FILE_INLINE_URL")) {
                this.toUpdateCols.add("FILE_INLINE_URL");
            }
        }
        return this;
    }

    /**
     * 文件附件URL。
     */
    private String fileAttachmentUrl;

    /**
     * 获取：文件附件URL。
     */
    public String getFileAttachmentUrl() {
        return this.fileAttachmentUrl;
    }

    /**
     * 设置：文件附件URL。
     */
    public FlFile setFileAttachmentUrl(String fileAttachmentUrl) {
        if (this.fileAttachmentUrl == null && fileAttachmentUrl == null) {
            // 均为null，不做处理。
        } else if (this.fileAttachmentUrl != null && fileAttachmentUrl != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileAttachmentUrl.compareTo(fileAttachmentUrl) != 0) {
                this.fileAttachmentUrl = fileAttachmentUrl;
                if (!this.toUpdateCols.contains("FILE_ATTACHMENT_URL")) {
                    this.toUpdateCols.add("FILE_ATTACHMENT_URL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileAttachmentUrl = fileAttachmentUrl;
            if (!this.toUpdateCols.contains("FILE_ATTACHMENT_URL")) {
                this.toUpdateCols.add("FILE_ATTACHMENT_URL");
            }
        }
        return this;
    }

    /**
     * 上传时间。
     */
    private LocalDateTime uploadDttm;

    /**
     * 获取：上传时间。
     */
    public LocalDateTime getUploadDttm() {
        return this.uploadDttm;
    }

    /**
     * 设置：上传时间。
     */
    public FlFile setUploadDttm(LocalDateTime uploadDttm) {
        if (this.uploadDttm == null && uploadDttm == null) {
            // 均为null，不做处理。
        } else if (this.uploadDttm != null && uploadDttm != null) {
            // 均非null，判定不等，再做处理：
            if (this.uploadDttm.compareTo(uploadDttm) != 0) {
                this.uploadDttm = uploadDttm;
                if (!this.toUpdateCols.contains("UPLOAD_DTTM")) {
                    this.toUpdateCols.add("UPLOAD_DTTM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.uploadDttm = uploadDttm;
            if (!this.toUpdateCols.contains("UPLOAD_DTTM")) {
                this.toUpdateCols.add("UPLOAD_DTTM");
            }
        }
        return this;
    }

    /**
     * 物理位置。
     */
    private String physicalLocation;

    /**
     * 获取：物理位置。
     */
    public String getPhysicalLocation() {
        return this.physicalLocation;
    }

    /**
     * 设置：物理位置。
     */
    public FlFile setPhysicalLocation(String physicalLocation) {
        if (this.physicalLocation == null && physicalLocation == null) {
            // 均为null，不做处理。
        } else if (this.physicalLocation != null && physicalLocation != null) {
            // 均非null，判定不等，再做处理：
            if (this.physicalLocation.compareTo(physicalLocation) != 0) {
                this.physicalLocation = physicalLocation;
                if (!this.toUpdateCols.contains("PHYSICAL_LOCATION")) {
                    this.toUpdateCols.add("PHYSICAL_LOCATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.physicalLocation = physicalLocation;
            if (!this.toUpdateCols.contains("PHYSICAL_LOCATION")) {
                this.toUpdateCols.add("PHYSICAL_LOCATION");
            }
        }
        return this;
    }

    /**
     * 原始文件物理位置。
     */
    private String originFilePhysicalLocation;

    /**
     * 获取：原始文件物理位置。
     */
    public String getOriginFilePhysicalLocation() {
        return this.originFilePhysicalLocation;
    }

    /**
     * 设置：原始文件物理位置。
     */
    public FlFile setOriginFilePhysicalLocation(String originFilePhysicalLocation) {
        if (this.originFilePhysicalLocation == null && originFilePhysicalLocation == null) {
            // 均为null，不做处理。
        } else if (this.originFilePhysicalLocation != null && originFilePhysicalLocation != null) {
            // 均非null，判定不等，再做处理：
            if (this.originFilePhysicalLocation.compareTo(originFilePhysicalLocation) != 0) {
                this.originFilePhysicalLocation = originFilePhysicalLocation;
                if (!this.toUpdateCols.contains("ORIGIN_FILE_PHYSICAL_LOCATION")) {
                    this.toUpdateCols.add("ORIGIN_FILE_PHYSICAL_LOCATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.originFilePhysicalLocation = originFilePhysicalLocation;
            if (!this.toUpdateCols.contains("ORIGIN_FILE_PHYSICAL_LOCATION")) {
                this.toUpdateCols.add("ORIGIN_FILE_PHYSICAL_LOCATION");
            }
        }
        return this;
    }

    /**
     * 是否公开读取。
     */
    private Boolean isPublicRead;

    /**
     * 获取：是否公开读取。
     */
    public Boolean getIsPublicRead() {
        return this.isPublicRead;
    }

    /**
     * 设置：是否公开读取。
     */
    public FlFile setIsPublicRead(Boolean isPublicRead) {
        if (this.isPublicRead == null && isPublicRead == null) {
            // 均为null，不做处理。
        } else if (this.isPublicRead != null && isPublicRead != null) {
            // 均非null，判定不等，再做处理：
            if (this.isPublicRead.compareTo(isPublicRead) != 0) {
                this.isPublicRead = isPublicRead;
                if (!this.toUpdateCols.contains("IS_PUBLIC_READ")) {
                    this.toUpdateCols.add("IS_PUBLIC_READ");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isPublicRead = isPublicRead;
            if (!this.toUpdateCols.contains("IS_PUBLIC_READ")) {
                this.toUpdateCols.add("IS_PUBLIC_READ");
            }
        }
        return this;
    }

    /**
     * 路径。
     */
    private String flPathId;

    /**
     * 获取：路径。
     */
    public String getFlPathId() {
        return this.flPathId;
    }

    /**
     * 设置：路径。
     */
    public FlFile setFlPathId(String flPathId) {
        if (this.flPathId == null && flPathId == null) {
            // 均为null，不做处理。
        } else if (this.flPathId != null && flPathId != null) {
            // 均非null，判定不等，再做处理：
            if (this.flPathId.compareTo(flPathId) != 0) {
                this.flPathId = flPathId;
                if (!this.toUpdateCols.contains("FL_PATH_ID")) {
                    this.toUpdateCols.add("FL_PATH_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.flPathId = flPathId;
            if (!this.toUpdateCols.contains("FL_PATH_ID")) {
                this.toUpdateCols.add("FL_PATH_ID");
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
    public static FlFile newData() {
        FlFile obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static FlFile insertData() {
        FlFile obj = modelHelper.insertData();
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
    public static FlFile selectById(String id, List<String> includeCols, List<String> excludeCols) {
        FlFile obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static FlFile selectById(String id) {
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
    public static List<FlFile> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<FlFile> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<FlFile> selectByIds(List<String> ids) {
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
    public static List<FlFile> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<FlFile> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<FlFile> selectByWhere(Where where) {
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
    public static FlFile selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<FlFile> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用FlFile.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static FlFile selectOneByWhere(Where where) {
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
    public static void copyCols(FlFile fromModel, FlFile toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(FlFile fromModel, FlFile toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}