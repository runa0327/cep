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
 * 节点。
 */
public class WfNode {

    /**
     * 模型助手。
     */
    private static final ModelHelper<WfNode> modelHelper = new ModelHelper<>("WF_NODE", new WfNode());

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

    public static final String ENT_CODE = "WF_NODE";
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
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 阶段。
         */
        public static final String WF_PHASE_ID = "WF_PHASE_ID";
        /**
         * 代码。
         */
        public static final String CODE = "CODE";
        /**
         * 名称。
         */
        public static final String NAME = "NAME";
        /**
         * 流程。
         */
        public static final String WF_PROCESS_ID = "WF_PROCESS_ID";
        /**
         * 节点类型。
         */
        public static final String NODE_TYPE = "NODE_TYPE";
        /**
         * 视图。
         */
        public static final String AD_VIEW_ID = "AD_VIEW_ID";
        /**
         * 图标。
         */
        public static final String ICON_FILE_GROUP_ID = "ICON_FILE_GROUP_ID";
        /**
         * 图标宽度。
         */
        public static final String ICON_WIDTH = "ICON_WIDTH";
        /**
         * 图标高度。
         */
        public static final String ICON_HEIGHT = "ICON_HEIGHT";
        /**
         * 节点X坐标。
         */
        public static final String NODE_X = "NODE_X";
        /**
         * 节点Y坐标。
         */
        public static final String NODE_Y = "NODE_Y";
        /**
         * 节点宽度。
         */
        public static final String NODE_WIDTH = "NODE_WIDTH";
        /**
         * 节点高度。
         */
        public static final String NODE_HEIGHT = "NODE_HEIGHT";
        /**
         * 待办顺序。
         */
        public static final String WF_TODO_SEQ_ID = "WF_TODO_SEQ_ID";
        /**
         * 进入时扩展点。
         */
        public static final String IN_EXT_POINT_ID = "IN_EXT_POINT_ID";
        /**
         * 转办后扩展点。
         */
        public static final String DISPATCHED_EXT_POINT_ID = "DISPATCHED_EXT_POINT_ID";
        /**
         * 能否跳过。
         */
        public static final String CAN_SKIP = "CAN_SKIP";
        /**
         * 序号。
         */
        public static final String SEQ_NO = "SEQ_NO";
        /**
         * 父节点。
         */
        public static final String WF_NODE_PID = "WF_NODE_PID";
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
    public WfNode setId(String id) {
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
    public WfNode setVer(Integer ver) {
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
    public WfNode setTs(LocalDateTime ts) {
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
    public WfNode setIsPreset(Boolean isPreset) {
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
    public WfNode setCrtDt(LocalDateTime crtDt) {
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
    public WfNode setCrtUserId(String crtUserId) {
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
    public WfNode setLastModiDt(LocalDateTime lastModiDt) {
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
    public WfNode setLastModiUserId(String lastModiUserId) {
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
    public WfNode setStatus(String status) {
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
    public WfNode setLkWfInstId(String lkWfInstId) {
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
    public WfNode setRemark(String remark) {
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
     * 阶段。
     */
    private String wfPhaseId;

    /**
     * 获取：阶段。
     */
    public String getWfPhaseId() {
        return this.wfPhaseId;
    }

    /**
     * 设置：阶段。
     */
    public WfNode setWfPhaseId(String wfPhaseId) {
        if (this.wfPhaseId == null && wfPhaseId == null) {
            // 均为null，不做处理。
        } else if (this.wfPhaseId != null && wfPhaseId != null) {
            // 均非null，判定不等，再做处理：
            if (this.wfPhaseId.compareTo(wfPhaseId) != 0) {
                this.wfPhaseId = wfPhaseId;
                if (!this.toUpdateCols.contains("WF_PHASE_ID")) {
                    this.toUpdateCols.add("WF_PHASE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.wfPhaseId = wfPhaseId;
            if (!this.toUpdateCols.contains("WF_PHASE_ID")) {
                this.toUpdateCols.add("WF_PHASE_ID");
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
    public WfNode setCode(String code) {
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
    public WfNode setName(String name) {
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
     * 流程。
     */
    private String wfProcessId;

    /**
     * 获取：流程。
     */
    public String getWfProcessId() {
        return this.wfProcessId;
    }

    /**
     * 设置：流程。
     */
    public WfNode setWfProcessId(String wfProcessId) {
        if (this.wfProcessId == null && wfProcessId == null) {
            // 均为null，不做处理。
        } else if (this.wfProcessId != null && wfProcessId != null) {
            // 均非null，判定不等，再做处理：
            if (this.wfProcessId.compareTo(wfProcessId) != 0) {
                this.wfProcessId = wfProcessId;
                if (!this.toUpdateCols.contains("WF_PROCESS_ID")) {
                    this.toUpdateCols.add("WF_PROCESS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.wfProcessId = wfProcessId;
            if (!this.toUpdateCols.contains("WF_PROCESS_ID")) {
                this.toUpdateCols.add("WF_PROCESS_ID");
            }
        }
        return this;
    }

    /**
     * 节点类型。
     */
    private String nodeType;

    /**
     * 获取：节点类型。
     */
    public String getNodeType() {
        return this.nodeType;
    }

    /**
     * 设置：节点类型。
     */
    public WfNode setNodeType(String nodeType) {
        if (this.nodeType == null && nodeType == null) {
            // 均为null，不做处理。
        } else if (this.nodeType != null && nodeType != null) {
            // 均非null，判定不等，再做处理：
            if (this.nodeType.compareTo(nodeType) != 0) {
                this.nodeType = nodeType;
                if (!this.toUpdateCols.contains("NODE_TYPE")) {
                    this.toUpdateCols.add("NODE_TYPE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.nodeType = nodeType;
            if (!this.toUpdateCols.contains("NODE_TYPE")) {
                this.toUpdateCols.add("NODE_TYPE");
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
    public WfNode setAdViewId(String adViewId) {
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
    public WfNode setIconFileGroupId(String iconFileGroupId) {
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
     * 图标宽度。
     */
    private String iconWidth;

    /**
     * 获取：图标宽度。
     */
    public String getIconWidth() {
        return this.iconWidth;
    }

    /**
     * 设置：图标宽度。
     */
    public WfNode setIconWidth(String iconWidth) {
        if (this.iconWidth == null && iconWidth == null) {
            // 均为null，不做处理。
        } else if (this.iconWidth != null && iconWidth != null) {
            // 均非null，判定不等，再做处理：
            if (this.iconWidth.compareTo(iconWidth) != 0) {
                this.iconWidth = iconWidth;
                if (!this.toUpdateCols.contains("ICON_WIDTH")) {
                    this.toUpdateCols.add("ICON_WIDTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.iconWidth = iconWidth;
            if (!this.toUpdateCols.contains("ICON_WIDTH")) {
                this.toUpdateCols.add("ICON_WIDTH");
            }
        }
        return this;
    }

    /**
     * 图标高度。
     */
    private String iconHeight;

    /**
     * 获取：图标高度。
     */
    public String getIconHeight() {
        return this.iconHeight;
    }

    /**
     * 设置：图标高度。
     */
    public WfNode setIconHeight(String iconHeight) {
        if (this.iconHeight == null && iconHeight == null) {
            // 均为null，不做处理。
        } else if (this.iconHeight != null && iconHeight != null) {
            // 均非null，判定不等，再做处理：
            if (this.iconHeight.compareTo(iconHeight) != 0) {
                this.iconHeight = iconHeight;
                if (!this.toUpdateCols.contains("ICON_HEIGHT")) {
                    this.toUpdateCols.add("ICON_HEIGHT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.iconHeight = iconHeight;
            if (!this.toUpdateCols.contains("ICON_HEIGHT")) {
                this.toUpdateCols.add("ICON_HEIGHT");
            }
        }
        return this;
    }

    /**
     * 节点X坐标。
     */
    private Integer nodeX;

    /**
     * 获取：节点X坐标。
     */
    public Integer getNodeX() {
        return this.nodeX;
    }

    /**
     * 设置：节点X坐标。
     */
    public WfNode setNodeX(Integer nodeX) {
        if (this.nodeX == null && nodeX == null) {
            // 均为null，不做处理。
        } else if (this.nodeX != null && nodeX != null) {
            // 均非null，判定不等，再做处理：
            if (this.nodeX.compareTo(nodeX) != 0) {
                this.nodeX = nodeX;
                if (!this.toUpdateCols.contains("NODE_X")) {
                    this.toUpdateCols.add("NODE_X");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.nodeX = nodeX;
            if (!this.toUpdateCols.contains("NODE_X")) {
                this.toUpdateCols.add("NODE_X");
            }
        }
        return this;
    }

    /**
     * 节点Y坐标。
     */
    private Integer nodeY;

    /**
     * 获取：节点Y坐标。
     */
    public Integer getNodeY() {
        return this.nodeY;
    }

    /**
     * 设置：节点Y坐标。
     */
    public WfNode setNodeY(Integer nodeY) {
        if (this.nodeY == null && nodeY == null) {
            // 均为null，不做处理。
        } else if (this.nodeY != null && nodeY != null) {
            // 均非null，判定不等，再做处理：
            if (this.nodeY.compareTo(nodeY) != 0) {
                this.nodeY = nodeY;
                if (!this.toUpdateCols.contains("NODE_Y")) {
                    this.toUpdateCols.add("NODE_Y");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.nodeY = nodeY;
            if (!this.toUpdateCols.contains("NODE_Y")) {
                this.toUpdateCols.add("NODE_Y");
            }
        }
        return this;
    }

    /**
     * 节点宽度。
     */
    private Integer nodeWidth;

    /**
     * 获取：节点宽度。
     */
    public Integer getNodeWidth() {
        return this.nodeWidth;
    }

    /**
     * 设置：节点宽度。
     */
    public WfNode setNodeWidth(Integer nodeWidth) {
        if (this.nodeWidth == null && nodeWidth == null) {
            // 均为null，不做处理。
        } else if (this.nodeWidth != null && nodeWidth != null) {
            // 均非null，判定不等，再做处理：
            if (this.nodeWidth.compareTo(nodeWidth) != 0) {
                this.nodeWidth = nodeWidth;
                if (!this.toUpdateCols.contains("NODE_WIDTH")) {
                    this.toUpdateCols.add("NODE_WIDTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.nodeWidth = nodeWidth;
            if (!this.toUpdateCols.contains("NODE_WIDTH")) {
                this.toUpdateCols.add("NODE_WIDTH");
            }
        }
        return this;
    }

    /**
     * 节点高度。
     */
    private Integer nodeHeight;

    /**
     * 获取：节点高度。
     */
    public Integer getNodeHeight() {
        return this.nodeHeight;
    }

    /**
     * 设置：节点高度。
     */
    public WfNode setNodeHeight(Integer nodeHeight) {
        if (this.nodeHeight == null && nodeHeight == null) {
            // 均为null，不做处理。
        } else if (this.nodeHeight != null && nodeHeight != null) {
            // 均非null，判定不等，再做处理：
            if (this.nodeHeight.compareTo(nodeHeight) != 0) {
                this.nodeHeight = nodeHeight;
                if (!this.toUpdateCols.contains("NODE_HEIGHT")) {
                    this.toUpdateCols.add("NODE_HEIGHT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.nodeHeight = nodeHeight;
            if (!this.toUpdateCols.contains("NODE_HEIGHT")) {
                this.toUpdateCols.add("NODE_HEIGHT");
            }
        }
        return this;
    }

    /**
     * 待办顺序。
     */
    private String wfTodoSeqId;

    /**
     * 获取：待办顺序。
     */
    public String getWfTodoSeqId() {
        return this.wfTodoSeqId;
    }

    /**
     * 设置：待办顺序。
     */
    public WfNode setWfTodoSeqId(String wfTodoSeqId) {
        if (this.wfTodoSeqId == null && wfTodoSeqId == null) {
            // 均为null，不做处理。
        } else if (this.wfTodoSeqId != null && wfTodoSeqId != null) {
            // 均非null，判定不等，再做处理：
            if (this.wfTodoSeqId.compareTo(wfTodoSeqId) != 0) {
                this.wfTodoSeqId = wfTodoSeqId;
                if (!this.toUpdateCols.contains("WF_TODO_SEQ_ID")) {
                    this.toUpdateCols.add("WF_TODO_SEQ_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.wfTodoSeqId = wfTodoSeqId;
            if (!this.toUpdateCols.contains("WF_TODO_SEQ_ID")) {
                this.toUpdateCols.add("WF_TODO_SEQ_ID");
            }
        }
        return this;
    }

    /**
     * 进入时扩展点。
     */
    private String inExtPointId;

    /**
     * 获取：进入时扩展点。
     */
    public String getInExtPointId() {
        return this.inExtPointId;
    }

    /**
     * 设置：进入时扩展点。
     */
    public WfNode setInExtPointId(String inExtPointId) {
        if (this.inExtPointId == null && inExtPointId == null) {
            // 均为null，不做处理。
        } else if (this.inExtPointId != null && inExtPointId != null) {
            // 均非null，判定不等，再做处理：
            if (this.inExtPointId.compareTo(inExtPointId) != 0) {
                this.inExtPointId = inExtPointId;
                if (!this.toUpdateCols.contains("IN_EXT_POINT_ID")) {
                    this.toUpdateCols.add("IN_EXT_POINT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.inExtPointId = inExtPointId;
            if (!this.toUpdateCols.contains("IN_EXT_POINT_ID")) {
                this.toUpdateCols.add("IN_EXT_POINT_ID");
            }
        }
        return this;
    }

    /**
     * 转办后扩展点。
     */
    private String dispatchedExtPointId;

    /**
     * 获取：转办后扩展点。
     */
    public String getDispatchedExtPointId() {
        return this.dispatchedExtPointId;
    }

    /**
     * 设置：转办后扩展点。
     */
    public WfNode setDispatchedExtPointId(String dispatchedExtPointId) {
        if (this.dispatchedExtPointId == null && dispatchedExtPointId == null) {
            // 均为null，不做处理。
        } else if (this.dispatchedExtPointId != null && dispatchedExtPointId != null) {
            // 均非null，判定不等，再做处理：
            if (this.dispatchedExtPointId.compareTo(dispatchedExtPointId) != 0) {
                this.dispatchedExtPointId = dispatchedExtPointId;
                if (!this.toUpdateCols.contains("DISPATCHED_EXT_POINT_ID")) {
                    this.toUpdateCols.add("DISPATCHED_EXT_POINT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dispatchedExtPointId = dispatchedExtPointId;
            if (!this.toUpdateCols.contains("DISPATCHED_EXT_POINT_ID")) {
                this.toUpdateCols.add("DISPATCHED_EXT_POINT_ID");
            }
        }
        return this;
    }

    /**
     * 能否跳过。
     */
    private Boolean canSkip;

    /**
     * 获取：能否跳过。
     */
    public Boolean getCanSkip() {
        return this.canSkip;
    }

    /**
     * 设置：能否跳过。
     */
    public WfNode setCanSkip(Boolean canSkip) {
        if (this.canSkip == null && canSkip == null) {
            // 均为null，不做处理。
        } else if (this.canSkip != null && canSkip != null) {
            // 均非null，判定不等，再做处理：
            if (this.canSkip.compareTo(canSkip) != 0) {
                this.canSkip = canSkip;
                if (!this.toUpdateCols.contains("CAN_SKIP")) {
                    this.toUpdateCols.add("CAN_SKIP");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.canSkip = canSkip;
            if (!this.toUpdateCols.contains("CAN_SKIP")) {
                this.toUpdateCols.add("CAN_SKIP");
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
    public WfNode setSeqNo(BigDecimal seqNo) {
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
     * 父节点。
     */
    private String wfNodePid;

    /**
     * 获取：父节点。
     */
    public String getWfNodePid() {
        return this.wfNodePid;
    }

    /**
     * 设置：父节点。
     */
    public WfNode setWfNodePid(String wfNodePid) {
        if (this.wfNodePid == null && wfNodePid == null) {
            // 均为null，不做处理。
        } else if (this.wfNodePid != null && wfNodePid != null) {
            // 均非null，判定不等，再做处理：
            if (this.wfNodePid.compareTo(wfNodePid) != 0) {
                this.wfNodePid = wfNodePid;
                if (!this.toUpdateCols.contains("WF_NODE_PID")) {
                    this.toUpdateCols.add("WF_NODE_PID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.wfNodePid = wfNodePid;
            if (!this.toUpdateCols.contains("WF_NODE_PID")) {
                this.toUpdateCols.add("WF_NODE_PID");
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
    public static WfNode newData() {
        WfNode obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static WfNode insertData() {
        WfNode obj = modelHelper.insertData();
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
    public static WfNode selectById(String id, List<String> includeCols, List<String> excludeCols) {
        WfNode obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<WfNode> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<WfNode> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<WfNode> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<WfNode> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(WfNode fromModel, WfNode toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}