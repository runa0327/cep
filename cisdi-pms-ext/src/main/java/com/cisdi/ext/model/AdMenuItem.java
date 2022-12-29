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
 * 菜单项。
 */
public class AdMenuItem {

    /**
     * 模型助手。
     */
    private static final ModelHelper<AdMenuItem> modelHelper = new ModelHelper<>("AD_MENU_ITEM", new AdMenuItem());

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

    public static final String ENT_CODE = "AD_MENU_ITEM";
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
         * 父菜单项。
         */
        public static final String PARENT_MI_ID = "PARENT_MI_ID";
        /**
         * 视图。
         */
        public static final String AD_VIEW_ID = "AD_VIEW_ID";
        /**
         * 自动打开逻辑。
         */
        public static final String AUTO_OPEN_LOGIC = "AUTO_OPEN_LOGIC";
        /**
         * 客户端显示。
         */
        public static final String SHOWN_IN_CLIENT = "SHOWN_IN_CLIENT";
        /**
         * 图标。
         */
        public static final String ICON_FILE_GROUP_ID = "ICON_FILE_GROUP_ID";
        /**
         * 移动端显示。
         */
        public static final String SHOWN_IN_MOB = "SHOWN_IN_MOB";
        /**
         * 菜单项类型。
         */
        public static final String AD_MENU_ITEM_TYPE_ID = "AD_MENU_ITEM_TYPE_ID";
        /**
         * 超链接。
         */
        public static final String HREF = "HREF";
        /**
         * 携带身份。
         */
        public static final String CARRY_IDENTITY = "CARRY_IDENTITY";
        /**
         * 额外信息。
         */
        public static final String EXTRA_INFO = "EXTRA_INFO";
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
    public AdMenuItem setId(String id) {
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
    public AdMenuItem setVer(Integer ver) {
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
    public AdMenuItem setTs(LocalDateTime ts) {
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
    public AdMenuItem setIsPreset(Boolean isPreset) {
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
    public AdMenuItem setCrtDt(LocalDateTime crtDt) {
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
    public AdMenuItem setCrtUserId(String crtUserId) {
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
    public AdMenuItem setLastModiDt(LocalDateTime lastModiDt) {
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
    public AdMenuItem setLastModiUserId(String lastModiUserId) {
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
    public AdMenuItem setStatus(String status) {
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
    public AdMenuItem setLkWfInstId(String lkWfInstId) {
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
    public AdMenuItem setCode(String code) {
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
    public AdMenuItem setName(String name) {
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
    public AdMenuItem setRemark(String remark) {
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
    public AdMenuItem setSeqNo(BigDecimal seqNo) {
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
     * 父菜单项。
     */
    private String parentMiId;

    /**
     * 获取：父菜单项。
     */
    public String getParentMiId() {
        return this.parentMiId;
    }

    /**
     * 设置：父菜单项。
     */
    public AdMenuItem setParentMiId(String parentMiId) {
        if (this.parentMiId == null && parentMiId == null) {
            // 均为null，不做处理。
        } else if (this.parentMiId != null && parentMiId != null) {
            // 均非null，判定不等，再做处理：
            if (this.parentMiId.compareTo(parentMiId) != 0) {
                this.parentMiId = parentMiId;
                if (!this.toUpdateCols.contains("PARENT_MI_ID")) {
                    this.toUpdateCols.add("PARENT_MI_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.parentMiId = parentMiId;
            if (!this.toUpdateCols.contains("PARENT_MI_ID")) {
                this.toUpdateCols.add("PARENT_MI_ID");
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
    public AdMenuItem setAdViewId(String adViewId) {
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
     * 自动打开逻辑。
     */
    private String autoOpenLogic;

    /**
     * 获取：自动打开逻辑。
     */
    public String getAutoOpenLogic() {
        return this.autoOpenLogic;
    }

    /**
     * 设置：自动打开逻辑。
     */
    public AdMenuItem setAutoOpenLogic(String autoOpenLogic) {
        if (this.autoOpenLogic == null && autoOpenLogic == null) {
            // 均为null，不做处理。
        } else if (this.autoOpenLogic != null && autoOpenLogic != null) {
            // 均非null，判定不等，再做处理：
            if (this.autoOpenLogic.compareTo(autoOpenLogic) != 0) {
                this.autoOpenLogic = autoOpenLogic;
                if (!this.toUpdateCols.contains("AUTO_OPEN_LOGIC")) {
                    this.toUpdateCols.add("AUTO_OPEN_LOGIC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.autoOpenLogic = autoOpenLogic;
            if (!this.toUpdateCols.contains("AUTO_OPEN_LOGIC")) {
                this.toUpdateCols.add("AUTO_OPEN_LOGIC");
            }
        }
        return this;
    }

    /**
     * 客户端显示。
     */
    private Boolean shownInClient;

    /**
     * 获取：客户端显示。
     */
    public Boolean getShownInClient() {
        return this.shownInClient;
    }

    /**
     * 设置：客户端显示。
     */
    public AdMenuItem setShownInClient(Boolean shownInClient) {
        if (this.shownInClient == null && shownInClient == null) {
            // 均为null，不做处理。
        } else if (this.shownInClient != null && shownInClient != null) {
            // 均非null，判定不等，再做处理：
            if (this.shownInClient.compareTo(shownInClient) != 0) {
                this.shownInClient = shownInClient;
                if (!this.toUpdateCols.contains("SHOWN_IN_CLIENT")) {
                    this.toUpdateCols.add("SHOWN_IN_CLIENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.shownInClient = shownInClient;
            if (!this.toUpdateCols.contains("SHOWN_IN_CLIENT")) {
                this.toUpdateCols.add("SHOWN_IN_CLIENT");
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
    public AdMenuItem setIconFileGroupId(String iconFileGroupId) {
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
     * 移动端显示。
     */
    private Boolean shownInMob;

    /**
     * 获取：移动端显示。
     */
    public Boolean getShownInMob() {
        return this.shownInMob;
    }

    /**
     * 设置：移动端显示。
     */
    public AdMenuItem setShownInMob(Boolean shownInMob) {
        if (this.shownInMob == null && shownInMob == null) {
            // 均为null，不做处理。
        } else if (this.shownInMob != null && shownInMob != null) {
            // 均非null，判定不等，再做处理：
            if (this.shownInMob.compareTo(shownInMob) != 0) {
                this.shownInMob = shownInMob;
                if (!this.toUpdateCols.contains("SHOWN_IN_MOB")) {
                    this.toUpdateCols.add("SHOWN_IN_MOB");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.shownInMob = shownInMob;
            if (!this.toUpdateCols.contains("SHOWN_IN_MOB")) {
                this.toUpdateCols.add("SHOWN_IN_MOB");
            }
        }
        return this;
    }

    /**
     * 菜单项类型。
     */
    private String adMenuItemTypeId;

    /**
     * 获取：菜单项类型。
     */
    public String getAdMenuItemTypeId() {
        return this.adMenuItemTypeId;
    }

    /**
     * 设置：菜单项类型。
     */
    public AdMenuItem setAdMenuItemTypeId(String adMenuItemTypeId) {
        if (this.adMenuItemTypeId == null && adMenuItemTypeId == null) {
            // 均为null，不做处理。
        } else if (this.adMenuItemTypeId != null && adMenuItemTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adMenuItemTypeId.compareTo(adMenuItemTypeId) != 0) {
                this.adMenuItemTypeId = adMenuItemTypeId;
                if (!this.toUpdateCols.contains("AD_MENU_ITEM_TYPE_ID")) {
                    this.toUpdateCols.add("AD_MENU_ITEM_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adMenuItemTypeId = adMenuItemTypeId;
            if (!this.toUpdateCols.contains("AD_MENU_ITEM_TYPE_ID")) {
                this.toUpdateCols.add("AD_MENU_ITEM_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 超链接。
     */
    private String href;

    /**
     * 获取：超链接。
     */
    public String getHref() {
        return this.href;
    }

    /**
     * 设置：超链接。
     */
    public AdMenuItem setHref(String href) {
        if (this.href == null && href == null) {
            // 均为null，不做处理。
        } else if (this.href != null && href != null) {
            // 均非null，判定不等，再做处理：
            if (this.href.compareTo(href) != 0) {
                this.href = href;
                if (!this.toUpdateCols.contains("HREF")) {
                    this.toUpdateCols.add("HREF");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.href = href;
            if (!this.toUpdateCols.contains("HREF")) {
                this.toUpdateCols.add("HREF");
            }
        }
        return this;
    }

    /**
     * 携带身份。
     */
    private Boolean carryIdentity;

    /**
     * 获取：携带身份。
     */
    public Boolean getCarryIdentity() {
        return this.carryIdentity;
    }

    /**
     * 设置：携带身份。
     */
    public AdMenuItem setCarryIdentity(Boolean carryIdentity) {
        if (this.carryIdentity == null && carryIdentity == null) {
            // 均为null，不做处理。
        } else if (this.carryIdentity != null && carryIdentity != null) {
            // 均非null，判定不等，再做处理：
            if (this.carryIdentity.compareTo(carryIdentity) != 0) {
                this.carryIdentity = carryIdentity;
                if (!this.toUpdateCols.contains("CARRY_IDENTITY")) {
                    this.toUpdateCols.add("CARRY_IDENTITY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.carryIdentity = carryIdentity;
            if (!this.toUpdateCols.contains("CARRY_IDENTITY")) {
                this.toUpdateCols.add("CARRY_IDENTITY");
            }
        }
        return this;
    }

    /**
     * 额外信息。
     */
    private String extraInfo;

    /**
     * 获取：额外信息。
     */
    public String getExtraInfo() {
        return this.extraInfo;
    }

    /**
     * 设置：额外信息。
     */
    public AdMenuItem setExtraInfo(String extraInfo) {
        if (this.extraInfo == null && extraInfo == null) {
            // 均为null，不做处理。
        } else if (this.extraInfo != null && extraInfo != null) {
            // 均非null，判定不等，再做处理：
            if (this.extraInfo.compareTo(extraInfo) != 0) {
                this.extraInfo = extraInfo;
                if (!this.toUpdateCols.contains("EXTRA_INFO")) {
                    this.toUpdateCols.add("EXTRA_INFO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.extraInfo = extraInfo;
            if (!this.toUpdateCols.contains("EXTRA_INFO")) {
                this.toUpdateCols.add("EXTRA_INFO");
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
    public static AdMenuItem newData() {
        AdMenuItem obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static AdMenuItem insertData() {
        AdMenuItem obj = modelHelper.insertData();
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
    public static AdMenuItem selectById(String id, List<String> includeCols, List<String> excludeCols) {
        AdMenuItem obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<AdMenuItem> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<AdMenuItem> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<AdMenuItem> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<AdMenuItem> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(AdMenuItem fromModel, AdMenuItem toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}