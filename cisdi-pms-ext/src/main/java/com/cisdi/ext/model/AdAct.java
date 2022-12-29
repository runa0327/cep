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
 * 操作。
 */
public class AdAct {

    /**
     * 模型助手。
     */
    private static final ModelHelper<AdAct> modelHelper = new ModelHelper<>("AD_ACT", new AdAct());

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

    public static final String ENT_CODE = "AD_ACT";
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
         * 序号。
         */
        public static final String SEQ_NO = "SEQ_NO";
        /**
         * 组名。
         */
        public static final String GROUP_NAME = "GROUP_NAME";
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
         * 图标。
         */
        public static final String ICON_FILE_GROUP_ID = "ICON_FILE_GROUP_ID";
        /**
         * 热键。
         */
        public static final String HOTKEY = "HOTKEY";
        /**
         * 操作类型。
         */
        public static final String AD_ACT_TYPE_ID = "AD_ACT_TYPE_ID";
        /**
         * 锁定记录。
         */
        public static final String LOCK_REC = "LOCK_REC";
        /**
         * 预检测扩展点。
         */
        public static final String PRE_CHK_EXT_POINT_ID = "PRE_CHK_EXT_POINT_ID";
        /**
         * 操作时扩展点。
         */
        public static final String ON_ACT_EXT_POINT_ID = "ON_ACT_EXT_POINT_ID";
        /**
         * 与所选相关。
         */
        public static final String IS_SELECT_AWARE = "IS_SELECT_AWARE";
        /**
         * 可执行逻辑。
         */
        public static final String EXECUTABLE_LOGIC = "EXECUTABLE_LOGIC";
        /**
         * 操作选择模式。
         */
        public static final String AD_ACT_SELECT_MODE_ID = "AD_ACT_SELECT_MODE_ID";
        /**
         * 客户端显示。
         */
        public static final String SHOWN_IN_CLIENT = "SHOWN_IN_CLIENT";
        /**
         * 移动端显示。
         */
        public static final String SHOWN_IN_MOB = "SHOWN_IN_MOB";
        /**
         * 操作确认模式。
         */
        public static final String AD_ACT_CONFIRM_MODE_ID = "AD_ACT_CONFIRM_MODE_ID";
        /**
         * 操作确认消息。
         */
        public static final String AD_ACT_CONFIRM_MSG = "AD_ACT_CONFIRM_MSG";
        /**
         * 明细显示。
         */
        public static final String SHOWN_IN_DTL_WINDOW = "SHOWN_IN_DTL_WINDOW";
        /**
         * 父操作。
         */
        public static final String PARENT_ACT_ID = "PARENT_ACT_ID";
        /**
         * 弹出的过滤器实体视图。
         */
        public static final String POPUP_FILTER_SEV_ID = "POPUP_FILTER_SEV_ID";
        /**
         * 弹出宽度。
         */
        public static final String POPUP_WIDTH = "POPUP_WIDTH";
        /**
         * 弹出高度。
         */
        public static final String POPUP_HEIGHT = "POPUP_HEIGHT";
        /**
         * 弹出显示直到成功。
         */
        public static final String POPUP_SHOW_TILL_SUCC = "POPUP_SHOW_TILL_SUCC";
        /**
         * 权限级别。
         */
        public static final String AUTH_LVL = "AUTH_LVL";
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
    public AdAct setId(String id) {
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
    public AdAct setVer(Integer ver) {
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
    public AdAct setTs(LocalDateTime ts) {
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
    public AdAct setIsPreset(Boolean isPreset) {
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
    public AdAct setCrtDt(LocalDateTime crtDt) {
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
    public AdAct setCrtUserId(String crtUserId) {
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
    public AdAct setLastModiDt(LocalDateTime lastModiDt) {
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
    public AdAct setLastModiUserId(String lastModiUserId) {
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
    public AdAct setStatus(String status) {
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
    public AdAct setLkWfInstId(String lkWfInstId) {
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
    public AdAct setSeqNo(BigDecimal seqNo) {
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
     * 组名。
     */
    private String groupName;

    /**
     * 获取：组名。
     */
    public String getGroupName() {
        return this.groupName;
    }

    /**
     * 设置：组名。
     */
    public AdAct setGroupName(String groupName) {
        if (this.groupName == null && groupName == null) {
            // 均为null，不做处理。
        } else if (this.groupName != null && groupName != null) {
            // 均非null，判定不等，再做处理：
            if (this.groupName.compareTo(groupName) != 0) {
                this.groupName = groupName;
                if (!this.toUpdateCols.contains("GROUP_NAME")) {
                    this.toUpdateCols.add("GROUP_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.groupName = groupName;
            if (!this.toUpdateCols.contains("GROUP_NAME")) {
                this.toUpdateCols.add("GROUP_NAME");
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
    public AdAct setCode(String code) {
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
    public AdAct setName(String name) {
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
    public AdAct setRemark(String remark) {
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
    public AdAct setIconFileGroupId(String iconFileGroupId) {
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
     * 热键。
     */
    private String hotkey;

    /**
     * 获取：热键。
     */
    public String getHotkey() {
        return this.hotkey;
    }

    /**
     * 设置：热键。
     */
    public AdAct setHotkey(String hotkey) {
        if (this.hotkey == null && hotkey == null) {
            // 均为null，不做处理。
        } else if (this.hotkey != null && hotkey != null) {
            // 均非null，判定不等，再做处理：
            if (this.hotkey.compareTo(hotkey) != 0) {
                this.hotkey = hotkey;
                if (!this.toUpdateCols.contains("HOTKEY")) {
                    this.toUpdateCols.add("HOTKEY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hotkey = hotkey;
            if (!this.toUpdateCols.contains("HOTKEY")) {
                this.toUpdateCols.add("HOTKEY");
            }
        }
        return this;
    }

    /**
     * 操作类型。
     */
    private String adActTypeId;

    /**
     * 获取：操作类型。
     */
    public String getAdActTypeId() {
        return this.adActTypeId;
    }

    /**
     * 设置：操作类型。
     */
    public AdAct setAdActTypeId(String adActTypeId) {
        if (this.adActTypeId == null && adActTypeId == null) {
            // 均为null，不做处理。
        } else if (this.adActTypeId != null && adActTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adActTypeId.compareTo(adActTypeId) != 0) {
                this.adActTypeId = adActTypeId;
                if (!this.toUpdateCols.contains("AD_ACT_TYPE_ID")) {
                    this.toUpdateCols.add("AD_ACT_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adActTypeId = adActTypeId;
            if (!this.toUpdateCols.contains("AD_ACT_TYPE_ID")) {
                this.toUpdateCols.add("AD_ACT_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 锁定记录。
     */
    private Boolean lockRec;

    /**
     * 获取：锁定记录。
     */
    public Boolean getLockRec() {
        return this.lockRec;
    }

    /**
     * 设置：锁定记录。
     */
    public AdAct setLockRec(Boolean lockRec) {
        if (this.lockRec == null && lockRec == null) {
            // 均为null，不做处理。
        } else if (this.lockRec != null && lockRec != null) {
            // 均非null，判定不等，再做处理：
            if (this.lockRec.compareTo(lockRec) != 0) {
                this.lockRec = lockRec;
                if (!this.toUpdateCols.contains("LOCK_REC")) {
                    this.toUpdateCols.add("LOCK_REC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.lockRec = lockRec;
            if (!this.toUpdateCols.contains("LOCK_REC")) {
                this.toUpdateCols.add("LOCK_REC");
            }
        }
        return this;
    }

    /**
     * 预检测扩展点。
     */
    private String preChkExtPointId;

    /**
     * 获取：预检测扩展点。
     */
    public String getPreChkExtPointId() {
        return this.preChkExtPointId;
    }

    /**
     * 设置：预检测扩展点。
     */
    public AdAct setPreChkExtPointId(String preChkExtPointId) {
        if (this.preChkExtPointId == null && preChkExtPointId == null) {
            // 均为null，不做处理。
        } else if (this.preChkExtPointId != null && preChkExtPointId != null) {
            // 均非null，判定不等，再做处理：
            if (this.preChkExtPointId.compareTo(preChkExtPointId) != 0) {
                this.preChkExtPointId = preChkExtPointId;
                if (!this.toUpdateCols.contains("PRE_CHK_EXT_POINT_ID")) {
                    this.toUpdateCols.add("PRE_CHK_EXT_POINT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.preChkExtPointId = preChkExtPointId;
            if (!this.toUpdateCols.contains("PRE_CHK_EXT_POINT_ID")) {
                this.toUpdateCols.add("PRE_CHK_EXT_POINT_ID");
            }
        }
        return this;
    }

    /**
     * 操作时扩展点。
     */
    private String onActExtPointId;

    /**
     * 获取：操作时扩展点。
     */
    public String getOnActExtPointId() {
        return this.onActExtPointId;
    }

    /**
     * 设置：操作时扩展点。
     */
    public AdAct setOnActExtPointId(String onActExtPointId) {
        if (this.onActExtPointId == null && onActExtPointId == null) {
            // 均为null，不做处理。
        } else if (this.onActExtPointId != null && onActExtPointId != null) {
            // 均非null，判定不等，再做处理：
            if (this.onActExtPointId.compareTo(onActExtPointId) != 0) {
                this.onActExtPointId = onActExtPointId;
                if (!this.toUpdateCols.contains("ON_ACT_EXT_POINT_ID")) {
                    this.toUpdateCols.add("ON_ACT_EXT_POINT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.onActExtPointId = onActExtPointId;
            if (!this.toUpdateCols.contains("ON_ACT_EXT_POINT_ID")) {
                this.toUpdateCols.add("ON_ACT_EXT_POINT_ID");
            }
        }
        return this;
    }

    /**
     * 与所选相关。
     */
    private Boolean isSelectAware;

    /**
     * 获取：与所选相关。
     */
    public Boolean getIsSelectAware() {
        return this.isSelectAware;
    }

    /**
     * 设置：与所选相关。
     */
    public AdAct setIsSelectAware(Boolean isSelectAware) {
        if (this.isSelectAware == null && isSelectAware == null) {
            // 均为null，不做处理。
        } else if (this.isSelectAware != null && isSelectAware != null) {
            // 均非null，判定不等，再做处理：
            if (this.isSelectAware.compareTo(isSelectAware) != 0) {
                this.isSelectAware = isSelectAware;
                if (!this.toUpdateCols.contains("IS_SELECT_AWARE")) {
                    this.toUpdateCols.add("IS_SELECT_AWARE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isSelectAware = isSelectAware;
            if (!this.toUpdateCols.contains("IS_SELECT_AWARE")) {
                this.toUpdateCols.add("IS_SELECT_AWARE");
            }
        }
        return this;
    }

    /**
     * 可执行逻辑。
     */
    private String executableLogic;

    /**
     * 获取：可执行逻辑。
     */
    public String getExecutableLogic() {
        return this.executableLogic;
    }

    /**
     * 设置：可执行逻辑。
     */
    public AdAct setExecutableLogic(String executableLogic) {
        if (this.executableLogic == null && executableLogic == null) {
            // 均为null，不做处理。
        } else if (this.executableLogic != null && executableLogic != null) {
            // 均非null，判定不等，再做处理：
            if (this.executableLogic.compareTo(executableLogic) != 0) {
                this.executableLogic = executableLogic;
                if (!this.toUpdateCols.contains("EXECUTABLE_LOGIC")) {
                    this.toUpdateCols.add("EXECUTABLE_LOGIC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.executableLogic = executableLogic;
            if (!this.toUpdateCols.contains("EXECUTABLE_LOGIC")) {
                this.toUpdateCols.add("EXECUTABLE_LOGIC");
            }
        }
        return this;
    }

    /**
     * 操作选择模式。
     */
    private String adActSelectModeId;

    /**
     * 获取：操作选择模式。
     */
    public String getAdActSelectModeId() {
        return this.adActSelectModeId;
    }

    /**
     * 设置：操作选择模式。
     */
    public AdAct setAdActSelectModeId(String adActSelectModeId) {
        if (this.adActSelectModeId == null && adActSelectModeId == null) {
            // 均为null，不做处理。
        } else if (this.adActSelectModeId != null && adActSelectModeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adActSelectModeId.compareTo(adActSelectModeId) != 0) {
                this.adActSelectModeId = adActSelectModeId;
                if (!this.toUpdateCols.contains("AD_ACT_SELECT_MODE_ID")) {
                    this.toUpdateCols.add("AD_ACT_SELECT_MODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adActSelectModeId = adActSelectModeId;
            if (!this.toUpdateCols.contains("AD_ACT_SELECT_MODE_ID")) {
                this.toUpdateCols.add("AD_ACT_SELECT_MODE_ID");
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
    public AdAct setShownInClient(Boolean shownInClient) {
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
    public AdAct setShownInMob(Boolean shownInMob) {
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
     * 操作确认模式。
     */
    private String adActConfirmModeId;

    /**
     * 获取：操作确认模式。
     */
    public String getAdActConfirmModeId() {
        return this.adActConfirmModeId;
    }

    /**
     * 设置：操作确认模式。
     */
    public AdAct setAdActConfirmModeId(String adActConfirmModeId) {
        if (this.adActConfirmModeId == null && adActConfirmModeId == null) {
            // 均为null，不做处理。
        } else if (this.adActConfirmModeId != null && adActConfirmModeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adActConfirmModeId.compareTo(adActConfirmModeId) != 0) {
                this.adActConfirmModeId = adActConfirmModeId;
                if (!this.toUpdateCols.contains("AD_ACT_CONFIRM_MODE_ID")) {
                    this.toUpdateCols.add("AD_ACT_CONFIRM_MODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adActConfirmModeId = adActConfirmModeId;
            if (!this.toUpdateCols.contains("AD_ACT_CONFIRM_MODE_ID")) {
                this.toUpdateCols.add("AD_ACT_CONFIRM_MODE_ID");
            }
        }
        return this;
    }

    /**
     * 操作确认消息。
     */
    private String adActConfirmMsg;

    /**
     * 获取：操作确认消息。
     */
    public String getAdActConfirmMsg() {
        return this.adActConfirmMsg;
    }

    /**
     * 设置：操作确认消息。
     */
    public AdAct setAdActConfirmMsg(String adActConfirmMsg) {
        if (this.adActConfirmMsg == null && adActConfirmMsg == null) {
            // 均为null，不做处理。
        } else if (this.adActConfirmMsg != null && adActConfirmMsg != null) {
            // 均非null，判定不等，再做处理：
            if (this.adActConfirmMsg.compareTo(adActConfirmMsg) != 0) {
                this.adActConfirmMsg = adActConfirmMsg;
                if (!this.toUpdateCols.contains("AD_ACT_CONFIRM_MSG")) {
                    this.toUpdateCols.add("AD_ACT_CONFIRM_MSG");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adActConfirmMsg = adActConfirmMsg;
            if (!this.toUpdateCols.contains("AD_ACT_CONFIRM_MSG")) {
                this.toUpdateCols.add("AD_ACT_CONFIRM_MSG");
            }
        }
        return this;
    }

    /**
     * 明细显示。
     */
    private Boolean shownInDtlWindow;

    /**
     * 获取：明细显示。
     */
    public Boolean getShownInDtlWindow() {
        return this.shownInDtlWindow;
    }

    /**
     * 设置：明细显示。
     */
    public AdAct setShownInDtlWindow(Boolean shownInDtlWindow) {
        if (this.shownInDtlWindow == null && shownInDtlWindow == null) {
            // 均为null，不做处理。
        } else if (this.shownInDtlWindow != null && shownInDtlWindow != null) {
            // 均非null，判定不等，再做处理：
            if (this.shownInDtlWindow.compareTo(shownInDtlWindow) != 0) {
                this.shownInDtlWindow = shownInDtlWindow;
                if (!this.toUpdateCols.contains("SHOWN_IN_DTL_WINDOW")) {
                    this.toUpdateCols.add("SHOWN_IN_DTL_WINDOW");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.shownInDtlWindow = shownInDtlWindow;
            if (!this.toUpdateCols.contains("SHOWN_IN_DTL_WINDOW")) {
                this.toUpdateCols.add("SHOWN_IN_DTL_WINDOW");
            }
        }
        return this;
    }

    /**
     * 父操作。
     */
    private String parentActId;

    /**
     * 获取：父操作。
     */
    public String getParentActId() {
        return this.parentActId;
    }

    /**
     * 设置：父操作。
     */
    public AdAct setParentActId(String parentActId) {
        if (this.parentActId == null && parentActId == null) {
            // 均为null，不做处理。
        } else if (this.parentActId != null && parentActId != null) {
            // 均非null，判定不等，再做处理：
            if (this.parentActId.compareTo(parentActId) != 0) {
                this.parentActId = parentActId;
                if (!this.toUpdateCols.contains("PARENT_ACT_ID")) {
                    this.toUpdateCols.add("PARENT_ACT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.parentActId = parentActId;
            if (!this.toUpdateCols.contains("PARENT_ACT_ID")) {
                this.toUpdateCols.add("PARENT_ACT_ID");
            }
        }
        return this;
    }

    /**
     * 弹出的过滤器实体视图。
     */
    private String popupFilterSevId;

    /**
     * 获取：弹出的过滤器实体视图。
     */
    public String getPopupFilterSevId() {
        return this.popupFilterSevId;
    }

    /**
     * 设置：弹出的过滤器实体视图。
     */
    public AdAct setPopupFilterSevId(String popupFilterSevId) {
        if (this.popupFilterSevId == null && popupFilterSevId == null) {
            // 均为null，不做处理。
        } else if (this.popupFilterSevId != null && popupFilterSevId != null) {
            // 均非null，判定不等，再做处理：
            if (this.popupFilterSevId.compareTo(popupFilterSevId) != 0) {
                this.popupFilterSevId = popupFilterSevId;
                if (!this.toUpdateCols.contains("POPUP_FILTER_SEV_ID")) {
                    this.toUpdateCols.add("POPUP_FILTER_SEV_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.popupFilterSevId = popupFilterSevId;
            if (!this.toUpdateCols.contains("POPUP_FILTER_SEV_ID")) {
                this.toUpdateCols.add("POPUP_FILTER_SEV_ID");
            }
        }
        return this;
    }

    /**
     * 弹出宽度。
     */
    private String popupWidth;

    /**
     * 获取：弹出宽度。
     */
    public String getPopupWidth() {
        return this.popupWidth;
    }

    /**
     * 设置：弹出宽度。
     */
    public AdAct setPopupWidth(String popupWidth) {
        if (this.popupWidth == null && popupWidth == null) {
            // 均为null，不做处理。
        } else if (this.popupWidth != null && popupWidth != null) {
            // 均非null，判定不等，再做处理：
            if (this.popupWidth.compareTo(popupWidth) != 0) {
                this.popupWidth = popupWidth;
                if (!this.toUpdateCols.contains("POPUP_WIDTH")) {
                    this.toUpdateCols.add("POPUP_WIDTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.popupWidth = popupWidth;
            if (!this.toUpdateCols.contains("POPUP_WIDTH")) {
                this.toUpdateCols.add("POPUP_WIDTH");
            }
        }
        return this;
    }

    /**
     * 弹出高度。
     */
    private String popupHeight;

    /**
     * 获取：弹出高度。
     */
    public String getPopupHeight() {
        return this.popupHeight;
    }

    /**
     * 设置：弹出高度。
     */
    public AdAct setPopupHeight(String popupHeight) {
        if (this.popupHeight == null && popupHeight == null) {
            // 均为null，不做处理。
        } else if (this.popupHeight != null && popupHeight != null) {
            // 均非null，判定不等，再做处理：
            if (this.popupHeight.compareTo(popupHeight) != 0) {
                this.popupHeight = popupHeight;
                if (!this.toUpdateCols.contains("POPUP_HEIGHT")) {
                    this.toUpdateCols.add("POPUP_HEIGHT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.popupHeight = popupHeight;
            if (!this.toUpdateCols.contains("POPUP_HEIGHT")) {
                this.toUpdateCols.add("POPUP_HEIGHT");
            }
        }
        return this;
    }

    /**
     * 弹出显示直到成功。
     */
    private Boolean popupShowTillSucc;

    /**
     * 获取：弹出显示直到成功。
     */
    public Boolean getPopupShowTillSucc() {
        return this.popupShowTillSucc;
    }

    /**
     * 设置：弹出显示直到成功。
     */
    public AdAct setPopupShowTillSucc(Boolean popupShowTillSucc) {
        if (this.popupShowTillSucc == null && popupShowTillSucc == null) {
            // 均为null，不做处理。
        } else if (this.popupShowTillSucc != null && popupShowTillSucc != null) {
            // 均非null，判定不等，再做处理：
            if (this.popupShowTillSucc.compareTo(popupShowTillSucc) != 0) {
                this.popupShowTillSucc = popupShowTillSucc;
                if (!this.toUpdateCols.contains("POPUP_SHOW_TILL_SUCC")) {
                    this.toUpdateCols.add("POPUP_SHOW_TILL_SUCC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.popupShowTillSucc = popupShowTillSucc;
            if (!this.toUpdateCols.contains("POPUP_SHOW_TILL_SUCC")) {
                this.toUpdateCols.add("POPUP_SHOW_TILL_SUCC");
            }
        }
        return this;
    }

    /**
     * 权限级别。
     */
    private Integer authLvl;

    /**
     * 获取：权限级别。
     */
    public Integer getAuthLvl() {
        return this.authLvl;
    }

    /**
     * 设置：权限级别。
     */
    public AdAct setAuthLvl(Integer authLvl) {
        if (this.authLvl == null && authLvl == null) {
            // 均为null，不做处理。
        } else if (this.authLvl != null && authLvl != null) {
            // 均非null，判定不等，再做处理：
            if (this.authLvl.compareTo(authLvl) != 0) {
                this.authLvl = authLvl;
                if (!this.toUpdateCols.contains("AUTH_LVL")) {
                    this.toUpdateCols.add("AUTH_LVL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.authLvl = authLvl;
            if (!this.toUpdateCols.contains("AUTH_LVL")) {
                this.toUpdateCols.add("AUTH_LVL");
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
    public static AdAct newData() {
        AdAct obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static AdAct insertData() {
        AdAct obj = modelHelper.insertData();
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
    public static AdAct selectById(String id, List<String> includeCols, List<String> excludeCols) {
        AdAct obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<AdAct> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<AdAct> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<AdAct> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<AdAct> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(AdAct fromModel, AdAct toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}