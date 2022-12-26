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
 * 视图部分。
 */
public class AdViewPart {

    /**
     * 模型助手。
     */
    private static final ModelHelper<AdViewPart> modelHelper = new ModelHelper<>("AD_VIEW_PART", new AdViewPart());

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

    public static final String ENT_CODE = "AD_VIEW_PART";
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
         * 序号。
         */
        public static final String SEQ_NO = "SEQ_NO";
        /**
         * 容器视图。
         */
        public static final String AD_CONTAINER_ID = "AD_CONTAINER_ID";
        /**
         * 实体视图。
         */
        public static final String AD_SINGLE_ENT_VIEW_ID = "AD_SINGLE_ENT_VIEW_ID";
        /**
         * 定制视图。
         */
        public static final String AD_CUSTOM_VIEW_ID = "AD_CUSTOM_VIEW_ID";
        /**
         * 宽度。
         */
        public static final String WIDTH = "WIDTH";
        /**
         * 高度。
         */
        public static final String HEIGHT = "HEIGHT";
        /**
         * 驱动方的视图部分。
         */
        public static final String DRIVING_VIEW_PART_ID = "DRIVING_VIEW_PART_ID";
        /**
         * 被驱动方的过滤语句。
         */
        public static final String DRIVEN_WHERE_CLAUSE = "DRIVEN_WHERE_CLAUSE";
        /**
         * 最小宽度。
         */
        public static final String MIN_WIDTH = "MIN_WIDTH";
        /**
         * 最大宽度。
         */
        public static final String MAX_WIDTH = "MAX_WIDTH";
        /**
         * 最小高度。
         */
        public static final String MIN_HEIGHT = "MIN_HEIGHT";
        /**
         * 最大高度。
         */
        public static final String MAX_HEIGHT = "MAX_HEIGHT";
        /**
         * PORTAL主列号。
         */
        public static final String PORTAL_MAIN_COL_NUM = "PORTAL_MAIN_COL_NUM";
        /**
         * PORTAL列中行号。
         */
        public static final String PORTAL_ROW_NUM_IN_COL = "PORTAL_ROW_NUM_IN_COL";
        /**
         * PORTAL行中偏移号。
         */
        public static final String PORTAL_ROW_OFFSET_NUM = "PORTAL_ROW_OFFSET_NUM";
        /**
         * 溢出模式。
         */
        public static final String OVERFLOW = "OVERFLOW";
        /**
         * 空隙。
         */
        public static final String MARGIN = "MARGIN";
        /**
         * 边框。
         */
        public static final String BORDER = "BORDER";
        /**
         * 填充。
         */
        public static final String PADDING = "PADDING";
        /**
         * 父视图部分。
         */
        public static final String PARENT_VP_ID = "PARENT_VP_ID";
        /**
         * 视图。
         */
        public static final String AD_VIEW_ID = "AD_VIEW_ID";
        /**
         * 作为父属性时的序号。
         */
        public static final String SEQ_NO_AS_PARENT_ATT = "SEQ_NO_AS_PARENT_ATT";
        /**
         * 隐藏分组标题。
         */
        public static final String HIDE_GRP_TITLE = "HIDE_GRP_TITLE";
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
    public AdViewPart setId(String id) {
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
    public AdViewPart setVer(Integer ver) {
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
    public AdViewPart setTs(LocalDateTime ts) {
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
    public AdViewPart setIsPreset(Boolean isPreset) {
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
    public AdViewPart setCrtDt(LocalDateTime crtDt) {
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
    public AdViewPart setCrtUserId(String crtUserId) {
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
    public AdViewPart setLastModiDt(LocalDateTime lastModiDt) {
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
    public AdViewPart setLastModiUserId(String lastModiUserId) {
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
    public AdViewPart setStatus(String status) {
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
    public AdViewPart setLkWfInstId(String lkWfInstId) {
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
    public AdViewPart setCode(String code) {
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
    public AdViewPart setName(String name) {
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
    public AdViewPart setRemark(String remark) {
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
    public AdViewPart setSeqNo(BigDecimal seqNo) {
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
     * 容器视图。
     */
    private String adContainerId;

    /**
     * 获取：容器视图。
     */
    public String getAdContainerId() {
        return this.adContainerId;
    }

    /**
     * 设置：容器视图。
     */
    public AdViewPart setAdContainerId(String adContainerId) {
        if (this.adContainerId == null && adContainerId == null) {
            // 均为null，不做处理。
        } else if (this.adContainerId != null && adContainerId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adContainerId.compareTo(adContainerId) != 0) {
                this.adContainerId = adContainerId;
                if (!this.toUpdateCols.contains("AD_CONTAINER_ID")) {
                    this.toUpdateCols.add("AD_CONTAINER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adContainerId = adContainerId;
            if (!this.toUpdateCols.contains("AD_CONTAINER_ID")) {
                this.toUpdateCols.add("AD_CONTAINER_ID");
            }
        }
        return this;
    }

    /**
     * 实体视图。
     */
    private String adSingleEntViewId;

    /**
     * 获取：实体视图。
     */
    public String getAdSingleEntViewId() {
        return this.adSingleEntViewId;
    }

    /**
     * 设置：实体视图。
     */
    public AdViewPart setAdSingleEntViewId(String adSingleEntViewId) {
        if (this.adSingleEntViewId == null && adSingleEntViewId == null) {
            // 均为null，不做处理。
        } else if (this.adSingleEntViewId != null && adSingleEntViewId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adSingleEntViewId.compareTo(adSingleEntViewId) != 0) {
                this.adSingleEntViewId = adSingleEntViewId;
                if (!this.toUpdateCols.contains("AD_SINGLE_ENT_VIEW_ID")) {
                    this.toUpdateCols.add("AD_SINGLE_ENT_VIEW_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adSingleEntViewId = adSingleEntViewId;
            if (!this.toUpdateCols.contains("AD_SINGLE_ENT_VIEW_ID")) {
                this.toUpdateCols.add("AD_SINGLE_ENT_VIEW_ID");
            }
        }
        return this;
    }

    /**
     * 定制视图。
     */
    private String adCustomViewId;

    /**
     * 获取：定制视图。
     */
    public String getAdCustomViewId() {
        return this.adCustomViewId;
    }

    /**
     * 设置：定制视图。
     */
    public AdViewPart setAdCustomViewId(String adCustomViewId) {
        if (this.adCustomViewId == null && adCustomViewId == null) {
            // 均为null，不做处理。
        } else if (this.adCustomViewId != null && adCustomViewId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adCustomViewId.compareTo(adCustomViewId) != 0) {
                this.adCustomViewId = adCustomViewId;
                if (!this.toUpdateCols.contains("AD_CUSTOM_VIEW_ID")) {
                    this.toUpdateCols.add("AD_CUSTOM_VIEW_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adCustomViewId = adCustomViewId;
            if (!this.toUpdateCols.contains("AD_CUSTOM_VIEW_ID")) {
                this.toUpdateCols.add("AD_CUSTOM_VIEW_ID");
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
    public AdViewPart setWidth(String width) {
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
     * 高度。
     */
    private String height;

    /**
     * 获取：高度。
     */
    public String getHeight() {
        return this.height;
    }

    /**
     * 设置：高度。
     */
    public AdViewPart setHeight(String height) {
        if (this.height == null && height == null) {
            // 均为null，不做处理。
        } else if (this.height != null && height != null) {
            // 均非null，判定不等，再做处理：
            if (this.height.compareTo(height) != 0) {
                this.height = height;
                if (!this.toUpdateCols.contains("HEIGHT")) {
                    this.toUpdateCols.add("HEIGHT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.height = height;
            if (!this.toUpdateCols.contains("HEIGHT")) {
                this.toUpdateCols.add("HEIGHT");
            }
        }
        return this;
    }

    /**
     * 驱动方的视图部分。
     */
    private String drivingViewPartId;

    /**
     * 获取：驱动方的视图部分。
     */
    public String getDrivingViewPartId() {
        return this.drivingViewPartId;
    }

    /**
     * 设置：驱动方的视图部分。
     */
    public AdViewPart setDrivingViewPartId(String drivingViewPartId) {
        if (this.drivingViewPartId == null && drivingViewPartId == null) {
            // 均为null，不做处理。
        } else if (this.drivingViewPartId != null && drivingViewPartId != null) {
            // 均非null，判定不等，再做处理：
            if (this.drivingViewPartId.compareTo(drivingViewPartId) != 0) {
                this.drivingViewPartId = drivingViewPartId;
                if (!this.toUpdateCols.contains("DRIVING_VIEW_PART_ID")) {
                    this.toUpdateCols.add("DRIVING_VIEW_PART_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.drivingViewPartId = drivingViewPartId;
            if (!this.toUpdateCols.contains("DRIVING_VIEW_PART_ID")) {
                this.toUpdateCols.add("DRIVING_VIEW_PART_ID");
            }
        }
        return this;
    }

    /**
     * 被驱动方的过滤语句。
     */
    private String drivenWhereClause;

    /**
     * 获取：被驱动方的过滤语句。
     */
    public String getDrivenWhereClause() {
        return this.drivenWhereClause;
    }

    /**
     * 设置：被驱动方的过滤语句。
     */
    public AdViewPart setDrivenWhereClause(String drivenWhereClause) {
        if (this.drivenWhereClause == null && drivenWhereClause == null) {
            // 均为null，不做处理。
        } else if (this.drivenWhereClause != null && drivenWhereClause != null) {
            // 均非null，判定不等，再做处理：
            if (this.drivenWhereClause.compareTo(drivenWhereClause) != 0) {
                this.drivenWhereClause = drivenWhereClause;
                if (!this.toUpdateCols.contains("DRIVEN_WHERE_CLAUSE")) {
                    this.toUpdateCols.add("DRIVEN_WHERE_CLAUSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.drivenWhereClause = drivenWhereClause;
            if (!this.toUpdateCols.contains("DRIVEN_WHERE_CLAUSE")) {
                this.toUpdateCols.add("DRIVEN_WHERE_CLAUSE");
            }
        }
        return this;
    }

    /**
     * 最小宽度。
     */
    private Integer minWidth;

    /**
     * 获取：最小宽度。
     */
    public Integer getMinWidth() {
        return this.minWidth;
    }

    /**
     * 设置：最小宽度。
     */
    public AdViewPart setMinWidth(Integer minWidth) {
        if (this.minWidth == null && minWidth == null) {
            // 均为null，不做处理。
        } else if (this.minWidth != null && minWidth != null) {
            // 均非null，判定不等，再做处理：
            if (this.minWidth.compareTo(minWidth) != 0) {
                this.minWidth = minWidth;
                if (!this.toUpdateCols.contains("MIN_WIDTH")) {
                    this.toUpdateCols.add("MIN_WIDTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.minWidth = minWidth;
            if (!this.toUpdateCols.contains("MIN_WIDTH")) {
                this.toUpdateCols.add("MIN_WIDTH");
            }
        }
        return this;
    }

    /**
     * 最大宽度。
     */
    private Integer maxWidth;

    /**
     * 获取：最大宽度。
     */
    public Integer getMaxWidth() {
        return this.maxWidth;
    }

    /**
     * 设置：最大宽度。
     */
    public AdViewPart setMaxWidth(Integer maxWidth) {
        if (this.maxWidth == null && maxWidth == null) {
            // 均为null，不做处理。
        } else if (this.maxWidth != null && maxWidth != null) {
            // 均非null，判定不等，再做处理：
            if (this.maxWidth.compareTo(maxWidth) != 0) {
                this.maxWidth = maxWidth;
                if (!this.toUpdateCols.contains("MAX_WIDTH")) {
                    this.toUpdateCols.add("MAX_WIDTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.maxWidth = maxWidth;
            if (!this.toUpdateCols.contains("MAX_WIDTH")) {
                this.toUpdateCols.add("MAX_WIDTH");
            }
        }
        return this;
    }

    /**
     * 最小高度。
     */
    private Integer minHeight;

    /**
     * 获取：最小高度。
     */
    public Integer getMinHeight() {
        return this.minHeight;
    }

    /**
     * 设置：最小高度。
     */
    public AdViewPart setMinHeight(Integer minHeight) {
        if (this.minHeight == null && minHeight == null) {
            // 均为null，不做处理。
        } else if (this.minHeight != null && minHeight != null) {
            // 均非null，判定不等，再做处理：
            if (this.minHeight.compareTo(minHeight) != 0) {
                this.minHeight = minHeight;
                if (!this.toUpdateCols.contains("MIN_HEIGHT")) {
                    this.toUpdateCols.add("MIN_HEIGHT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.minHeight = minHeight;
            if (!this.toUpdateCols.contains("MIN_HEIGHT")) {
                this.toUpdateCols.add("MIN_HEIGHT");
            }
        }
        return this;
    }

    /**
     * 最大高度。
     */
    private Integer maxHeight;

    /**
     * 获取：最大高度。
     */
    public Integer getMaxHeight() {
        return this.maxHeight;
    }

    /**
     * 设置：最大高度。
     */
    public AdViewPart setMaxHeight(Integer maxHeight) {
        if (this.maxHeight == null && maxHeight == null) {
            // 均为null，不做处理。
        } else if (this.maxHeight != null && maxHeight != null) {
            // 均非null，判定不等，再做处理：
            if (this.maxHeight.compareTo(maxHeight) != 0) {
                this.maxHeight = maxHeight;
                if (!this.toUpdateCols.contains("MAX_HEIGHT")) {
                    this.toUpdateCols.add("MAX_HEIGHT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.maxHeight = maxHeight;
            if (!this.toUpdateCols.contains("MAX_HEIGHT")) {
                this.toUpdateCols.add("MAX_HEIGHT");
            }
        }
        return this;
    }

    /**
     * PORTAL主列号。
     */
    private Integer portalMainColNum;

    /**
     * 获取：PORTAL主列号。
     */
    public Integer getPortalMainColNum() {
        return this.portalMainColNum;
    }

    /**
     * 设置：PORTAL主列号。
     */
    public AdViewPart setPortalMainColNum(Integer portalMainColNum) {
        if (this.portalMainColNum == null && portalMainColNum == null) {
            // 均为null，不做处理。
        } else if (this.portalMainColNum != null && portalMainColNum != null) {
            // 均非null，判定不等，再做处理：
            if (this.portalMainColNum.compareTo(portalMainColNum) != 0) {
                this.portalMainColNum = portalMainColNum;
                if (!this.toUpdateCols.contains("PORTAL_MAIN_COL_NUM")) {
                    this.toUpdateCols.add("PORTAL_MAIN_COL_NUM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.portalMainColNum = portalMainColNum;
            if (!this.toUpdateCols.contains("PORTAL_MAIN_COL_NUM")) {
                this.toUpdateCols.add("PORTAL_MAIN_COL_NUM");
            }
        }
        return this;
    }

    /**
     * PORTAL列中行号。
     */
    private Integer portalRowNumInCol;

    /**
     * 获取：PORTAL列中行号。
     */
    public Integer getPortalRowNumInCol() {
        return this.portalRowNumInCol;
    }

    /**
     * 设置：PORTAL列中行号。
     */
    public AdViewPart setPortalRowNumInCol(Integer portalRowNumInCol) {
        if (this.portalRowNumInCol == null && portalRowNumInCol == null) {
            // 均为null，不做处理。
        } else if (this.portalRowNumInCol != null && portalRowNumInCol != null) {
            // 均非null，判定不等，再做处理：
            if (this.portalRowNumInCol.compareTo(portalRowNumInCol) != 0) {
                this.portalRowNumInCol = portalRowNumInCol;
                if (!this.toUpdateCols.contains("PORTAL_ROW_NUM_IN_COL")) {
                    this.toUpdateCols.add("PORTAL_ROW_NUM_IN_COL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.portalRowNumInCol = portalRowNumInCol;
            if (!this.toUpdateCols.contains("PORTAL_ROW_NUM_IN_COL")) {
                this.toUpdateCols.add("PORTAL_ROW_NUM_IN_COL");
            }
        }
        return this;
    }

    /**
     * PORTAL行中偏移号。
     */
    private Integer portalRowOffsetNum;

    /**
     * 获取：PORTAL行中偏移号。
     */
    public Integer getPortalRowOffsetNum() {
        return this.portalRowOffsetNum;
    }

    /**
     * 设置：PORTAL行中偏移号。
     */
    public AdViewPart setPortalRowOffsetNum(Integer portalRowOffsetNum) {
        if (this.portalRowOffsetNum == null && portalRowOffsetNum == null) {
            // 均为null，不做处理。
        } else if (this.portalRowOffsetNum != null && portalRowOffsetNum != null) {
            // 均非null，判定不等，再做处理：
            if (this.portalRowOffsetNum.compareTo(portalRowOffsetNum) != 0) {
                this.portalRowOffsetNum = portalRowOffsetNum;
                if (!this.toUpdateCols.contains("PORTAL_ROW_OFFSET_NUM")) {
                    this.toUpdateCols.add("PORTAL_ROW_OFFSET_NUM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.portalRowOffsetNum = portalRowOffsetNum;
            if (!this.toUpdateCols.contains("PORTAL_ROW_OFFSET_NUM")) {
                this.toUpdateCols.add("PORTAL_ROW_OFFSET_NUM");
            }
        }
        return this;
    }

    /**
     * 溢出模式。
     */
    private String overflow;

    /**
     * 获取：溢出模式。
     */
    public String getOverflow() {
        return this.overflow;
    }

    /**
     * 设置：溢出模式。
     */
    public AdViewPart setOverflow(String overflow) {
        if (this.overflow == null && overflow == null) {
            // 均为null，不做处理。
        } else if (this.overflow != null && overflow != null) {
            // 均非null，判定不等，再做处理：
            if (this.overflow.compareTo(overflow) != 0) {
                this.overflow = overflow;
                if (!this.toUpdateCols.contains("OVERFLOW")) {
                    this.toUpdateCols.add("OVERFLOW");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.overflow = overflow;
            if (!this.toUpdateCols.contains("OVERFLOW")) {
                this.toUpdateCols.add("OVERFLOW");
            }
        }
        return this;
    }

    /**
     * 空隙。
     */
    private Integer margin;

    /**
     * 获取：空隙。
     */
    public Integer getMargin() {
        return this.margin;
    }

    /**
     * 设置：空隙。
     */
    public AdViewPart setMargin(Integer margin) {
        if (this.margin == null && margin == null) {
            // 均为null，不做处理。
        } else if (this.margin != null && margin != null) {
            // 均非null，判定不等，再做处理：
            if (this.margin.compareTo(margin) != 0) {
                this.margin = margin;
                if (!this.toUpdateCols.contains("MARGIN")) {
                    this.toUpdateCols.add("MARGIN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.margin = margin;
            if (!this.toUpdateCols.contains("MARGIN")) {
                this.toUpdateCols.add("MARGIN");
            }
        }
        return this;
    }

    /**
     * 边框。
     */
    private String border;

    /**
     * 获取：边框。
     */
    public String getBorder() {
        return this.border;
    }

    /**
     * 设置：边框。
     */
    public AdViewPart setBorder(String border) {
        if (this.border == null && border == null) {
            // 均为null，不做处理。
        } else if (this.border != null && border != null) {
            // 均非null，判定不等，再做处理：
            if (this.border.compareTo(border) != 0) {
                this.border = border;
                if (!this.toUpdateCols.contains("BORDER")) {
                    this.toUpdateCols.add("BORDER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.border = border;
            if (!this.toUpdateCols.contains("BORDER")) {
                this.toUpdateCols.add("BORDER");
            }
        }
        return this;
    }

    /**
     * 填充。
     */
    private Integer padding;

    /**
     * 获取：填充。
     */
    public Integer getPadding() {
        return this.padding;
    }

    /**
     * 设置：填充。
     */
    public AdViewPart setPadding(Integer padding) {
        if (this.padding == null && padding == null) {
            // 均为null，不做处理。
        } else if (this.padding != null && padding != null) {
            // 均非null，判定不等，再做处理：
            if (this.padding.compareTo(padding) != 0) {
                this.padding = padding;
                if (!this.toUpdateCols.contains("PADDING")) {
                    this.toUpdateCols.add("PADDING");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.padding = padding;
            if (!this.toUpdateCols.contains("PADDING")) {
                this.toUpdateCols.add("PADDING");
            }
        }
        return this;
    }

    /**
     * 父视图部分。
     */
    private String parentVpId;

    /**
     * 获取：父视图部分。
     */
    public String getParentVpId() {
        return this.parentVpId;
    }

    /**
     * 设置：父视图部分。
     */
    public AdViewPart setParentVpId(String parentVpId) {
        if (this.parentVpId == null && parentVpId == null) {
            // 均为null，不做处理。
        } else if (this.parentVpId != null && parentVpId != null) {
            // 均非null，判定不等，再做处理：
            if (this.parentVpId.compareTo(parentVpId) != 0) {
                this.parentVpId = parentVpId;
                if (!this.toUpdateCols.contains("PARENT_VP_ID")) {
                    this.toUpdateCols.add("PARENT_VP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.parentVpId = parentVpId;
            if (!this.toUpdateCols.contains("PARENT_VP_ID")) {
                this.toUpdateCols.add("PARENT_VP_ID");
            }
        }
        return this;
    }

    /**
     * 视图。
     */
    private String adViewId;

    /**
     * 获取：视图。
     */
    public String getAdViewId() {
        return this.adViewId;
    }

    /**
     * 设置：视图。
     */
    public AdViewPart setAdViewId(String adViewId) {
        if (this.adViewId == null && adViewId == null) {
            // 均为null，不做处理。
        } else if (this.adViewId != null && adViewId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adViewId.compareTo(adViewId) != 0) {
                this.adViewId = adViewId;
                if (!this.toUpdateCols.contains("AD_VIEW_ID")) {
                    this.toUpdateCols.add("AD_VIEW_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adViewId = adViewId;
            if (!this.toUpdateCols.contains("AD_VIEW_ID")) {
                this.toUpdateCols.add("AD_VIEW_ID");
            }
        }
        return this;
    }

    /**
     * 作为父属性时的序号。
     */
    private BigDecimal seqNoAsParentAtt;

    /**
     * 获取：作为父属性时的序号。
     */
    public BigDecimal getSeqNoAsParentAtt() {
        return this.seqNoAsParentAtt;
    }

    /**
     * 设置：作为父属性时的序号。
     */
    public AdViewPart setSeqNoAsParentAtt(BigDecimal seqNoAsParentAtt) {
        if (this.seqNoAsParentAtt == null && seqNoAsParentAtt == null) {
            // 均为null，不做处理。
        } else if (this.seqNoAsParentAtt != null && seqNoAsParentAtt != null) {
            // 均非null，判定不等，再做处理：
            if (this.seqNoAsParentAtt.compareTo(seqNoAsParentAtt) != 0) {
                this.seqNoAsParentAtt = seqNoAsParentAtt;
                if (!this.toUpdateCols.contains("SEQ_NO_AS_PARENT_ATT")) {
                    this.toUpdateCols.add("SEQ_NO_AS_PARENT_ATT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.seqNoAsParentAtt = seqNoAsParentAtt;
            if (!this.toUpdateCols.contains("SEQ_NO_AS_PARENT_ATT")) {
                this.toUpdateCols.add("SEQ_NO_AS_PARENT_ATT");
            }
        }
        return this;
    }

    /**
     * 隐藏分组标题。
     */
    private Boolean hideGrpTitle;

    /**
     * 获取：隐藏分组标题。
     */
    public Boolean getHideGrpTitle() {
        return this.hideGrpTitle;
    }

    /**
     * 设置：隐藏分组标题。
     */
    public AdViewPart setHideGrpTitle(Boolean hideGrpTitle) {
        if (this.hideGrpTitle == null && hideGrpTitle == null) {
            // 均为null，不做处理。
        } else if (this.hideGrpTitle != null && hideGrpTitle != null) {
            // 均非null，判定不等，再做处理：
            if (this.hideGrpTitle.compareTo(hideGrpTitle) != 0) {
                this.hideGrpTitle = hideGrpTitle;
                if (!this.toUpdateCols.contains("HIDE_GRP_TITLE")) {
                    this.toUpdateCols.add("HIDE_GRP_TITLE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hideGrpTitle = hideGrpTitle;
            if (!this.toUpdateCols.contains("HIDE_GRP_TITLE")) {
                this.toUpdateCols.add("HIDE_GRP_TITLE");
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
    public static AdViewPart newData() {
        AdViewPart obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static AdViewPart insertData() {
        AdViewPart obj = modelHelper.insertData();
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
    public static AdViewPart selectById(String id, List<String> includeCols, List<String> excludeCols) {
        AdViewPart obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<AdViewPart> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<AdViewPart> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<AdViewPart> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<AdViewPart> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(AdViewPart fromModel, AdViewPart toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}