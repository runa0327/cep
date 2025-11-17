package com.bid.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 质安巡检。
 */
public class CcQsInspection {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcQsInspection> modelHelper = new ModelHelper<>("CC_QS_INSPECTION", new CcQsInspection());

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

    public static final String ENT_CODE = "CC_QS_INSPECTION";
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
         * 项目。
         */
        public static final String CC_PRJ_ID = "CC_PRJ_ID";
        /**
         * 快捷码。
         */
        public static final String FAST_CODE = "FAST_CODE";
        /**
         * 图标。
         */
        public static final String ICON_FILE_GROUP_ID = "ICON_FILE_GROUP_ID";
        /**
         * 项目结构节点。
         */
        public static final String CC_PRJ_STRUCT_NODE_ID = "CC_PRJ_STRUCT_NODE_ID";
        /**
         * 质安发起方。
         */
        public static final String CC_QS_INITIATING_POST_ID = "CC_QS_INITIATING_POST_ID";
        /**
         * 质安发布时间。
         */
        public static final String CC_QS_INSPECTION_TIME = "CC_QS_INSPECTION_TIME";
        /**
         * 咨询问题。
         */
        public static final String CC_QUESTION = "CC_QUESTION";
        /**
         * 咨询名称。
         */
        public static final String CC_NAME = "CC_NAME";
        /**
         * 质安问题要点分类(直接子节点)。
         */
        public static final String CC_QS_ISSUE_POINT_TYPE_CHILD_ID = "CC_QS_ISSUE_POINT_TYPE_CHILD_ID";
        /**
         * BIM模型构件。
         */
        public static final String CC_BIM_MODEL_COMPONENTS_ID = "CC_BIM_MODEL_COMPONENTS_ID";
        /**
         * 质安巡检用户。
         */
        public static final String CC_QS_INSPECTION_USER = "CC_QS_INSPECTION_USER";
        /**
         * 质安巡检类型。
         */
        public static final String CC_QS_INSPECTION_TYPE_ID = "CC_QS_INSPECTION_TYPE_ID";
        /**
         * 巡检部位。
         */
        public static final String CC_QS_INSPECTION_POSITION = "CC_QS_INSPECTION_POSITION";
        /**
         * 质安发起类型。
         */
        public static final String CC_QS_INITIATION_TYPE_ID = "CC_QS_INITIATION_TYPE_ID";
        /**
         * 质安问题要点分类。
         */
        public static final String CC_QS_ISSUE_POINT_TYPE_ID = "CC_QS_ISSUE_POINT_TYPE_ID";
        /**
         * 质安问题等级。
         */
        public static final String CC_QS_ISSUE_LEVEL_ID = "CC_QS_ISSUE_LEVEL_ID";
        /**
         * 质量考核条款(多值)。
         */
        public static final String CC_QS_ASSESSMENT_CRITERIA_IDS = "CC_QS_ASSESSMENT_CRITERIA_IDS";
        /**
         * 关联的分部分项节点。
         */
        public static final String CC_PRJ_PBS_NODE_ID = "CC_PRJ_PBS_NODE_ID";
        /**
         * 质安问题要点（多个）。
         */
        public static final String CC_QS_ISSUE_POINT_IDS = "CC_QS_ISSUE_POINT_IDS";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 整改期限。
         */
        public static final String RECTIFICATION_PERIOD = "RECTIFICATION_PERIOD";
        /**
         * 质安责任单位。
         */
        public static final String CC_QS_DUTY_COMPANY = "CC_QS_DUTY_COMPANY";
        /**
         * 质安责任用户。
         */
        public static final String CC_QS_DUTY_USER = "CC_QS_DUTY_USER";
        /**
         * 安全责任人（多）。
         */
        public static final String CC_SAFE_DUTY_USER_IDS = "CC_SAFE_DUTY_USER_IDS";
        /**
         * 质安责任方。
         */
        public static final String CC_QS_DUTY_POST_ID = "CC_QS_DUTY_POST_ID";
        /**
         * 质安分包单位。
         */
        public static final String CC_QS_SUBCONTRACTOR_ID = "CC_QS_SUBCONTRACTOR_ID";
        /**
         * 质安复核用户。
         */
        public static final String CC_QS_CHECK_USER = "CC_QS_CHECK_USER";
        /**
         * 质安抄送用户。
         */
        public static final String CC_QS_COPY_USER = "CC_QS_COPY_USER";
        /**
         * 抄报单位。
         */
        public static final String HC_WCF_CC_UNIT = "HC_WCF_CC_UNIT";
        /**
         * 通知单编号。
         */
        public static final String HCY_TABLE_NUMBER = "HCY_TABLE_NUMBER";
        /**
         * 质安问题图片。
         */
        public static final String CC_QS_ISSUES_IMG = "CC_QS_ISSUES_IMG";
        /**
         * 附件。
         */
        public static final String ATTACHMENT = "ATTACHMENT";
        /**
         * 质安当前状态。
         */
        public static final String CC_QS_CURRENT_STATE_ID = "CC_QS_CURRENT_STATE_ID";
        /**
         * 质安整改时间。
         */
        public static final String CC_QS_RECTIFY_TIME = "CC_QS_RECTIFY_TIME";
        /**
         * 质安整改图片。
         */
        public static final String CC_QS_RECTIFY_IMG = "CC_QS_RECTIFY_IMG";
        /**
         * 质安整改详述。
         */
        public static final String CC_QS_RECTIFY_DESC = "CC_QS_RECTIFY_DESC";
        /**
         * 质安复核时间。
         */
        public static final String CC_QS_CHECK_TIME = "CC_QS_CHECK_TIME";
        /**
         * 质安通知单。
         */
        public static final String CC_QS_NOTICE_ID = "CC_QS_NOTICE_ID";
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
    public CcQsInspection setId(String id) {
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
    public CcQsInspection setVer(Integer ver) {
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
    public CcQsInspection setTs(LocalDateTime ts) {
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
    public CcQsInspection setIsPreset(Boolean isPreset) {
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
    public CcQsInspection setCrtDt(LocalDateTime crtDt) {
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
    public CcQsInspection setCrtUserId(String crtUserId) {
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
    public CcQsInspection setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcQsInspection setLastModiUserId(String lastModiUserId) {
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
    public CcQsInspection setStatus(String status) {
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
    public CcQsInspection setLkWfInstId(String lkWfInstId) {
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
    public CcQsInspection setCode(String code) {
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
    public CcQsInspection setName(String name) {
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
    public CcQsInspection setCcPrjId(String ccPrjId) {
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
    public CcQsInspection setFastCode(String fastCode) {
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
    public CcQsInspection setIconFileGroupId(String iconFileGroupId) {
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
     * 项目结构节点。
     */
    private String ccPrjStructNodeId;

    /**
     * 获取：项目结构节点。
     */
    public String getCcPrjStructNodeId() {
        return this.ccPrjStructNodeId;
    }

    /**
     * 设置：项目结构节点。
     */
    public CcQsInspection setCcPrjStructNodeId(String ccPrjStructNodeId) {
        if (this.ccPrjStructNodeId == null && ccPrjStructNodeId == null) {
            // 均为null，不做处理。
        } else if (this.ccPrjStructNodeId != null && ccPrjStructNodeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPrjStructNodeId.compareTo(ccPrjStructNodeId) != 0) {
                this.ccPrjStructNodeId = ccPrjStructNodeId;
                if (!this.toUpdateCols.contains("CC_PRJ_STRUCT_NODE_ID")) {
                    this.toUpdateCols.add("CC_PRJ_STRUCT_NODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPrjStructNodeId = ccPrjStructNodeId;
            if (!this.toUpdateCols.contains("CC_PRJ_STRUCT_NODE_ID")) {
                this.toUpdateCols.add("CC_PRJ_STRUCT_NODE_ID");
            }
        }
        return this;
    }

    /**
     * 质安发起方。
     */
    private String ccQsInitiatingPostId;

    /**
     * 获取：质安发起方。
     */
    public String getCcQsInitiatingPostId() {
        return this.ccQsInitiatingPostId;
    }

    /**
     * 设置：质安发起方。
     */
    public CcQsInspection setCcQsInitiatingPostId(String ccQsInitiatingPostId) {
        if (this.ccQsInitiatingPostId == null && ccQsInitiatingPostId == null) {
            // 均为null，不做处理。
        } else if (this.ccQsInitiatingPostId != null && ccQsInitiatingPostId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsInitiatingPostId.compareTo(ccQsInitiatingPostId) != 0) {
                this.ccQsInitiatingPostId = ccQsInitiatingPostId;
                if (!this.toUpdateCols.contains("CC_QS_INITIATING_POST_ID")) {
                    this.toUpdateCols.add("CC_QS_INITIATING_POST_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsInitiatingPostId = ccQsInitiatingPostId;
            if (!this.toUpdateCols.contains("CC_QS_INITIATING_POST_ID")) {
                this.toUpdateCols.add("CC_QS_INITIATING_POST_ID");
            }
        }
        return this;
    }

    /**
     * 质安发布时间。
     */
    private LocalDate ccQsInspectionTime;

    /**
     * 获取：质安发布时间。
     */
    public LocalDate getCcQsInspectionTime() {
        return this.ccQsInspectionTime;
    }

    /**
     * 设置：质安发布时间。
     */
    public CcQsInspection setCcQsInspectionTime(LocalDate ccQsInspectionTime) {
        if (this.ccQsInspectionTime == null && ccQsInspectionTime == null) {
            // 均为null，不做处理。
        } else if (this.ccQsInspectionTime != null && ccQsInspectionTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsInspectionTime.compareTo(ccQsInspectionTime) != 0) {
                this.ccQsInspectionTime = ccQsInspectionTime;
                if (!this.toUpdateCols.contains("CC_QS_INSPECTION_TIME")) {
                    this.toUpdateCols.add("CC_QS_INSPECTION_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsInspectionTime = ccQsInspectionTime;
            if (!this.toUpdateCols.contains("CC_QS_INSPECTION_TIME")) {
                this.toUpdateCols.add("CC_QS_INSPECTION_TIME");
            }
        }
        return this;
    }

    /**
     * 咨询问题。
     */
    private String ccQuestion;

    /**
     * 获取：咨询问题。
     */
    public String getCcQuestion() {
        return this.ccQuestion;
    }

    /**
     * 设置：咨询问题。
     */
    public CcQsInspection setCcQuestion(String ccQuestion) {
        if (this.ccQuestion == null && ccQuestion == null) {
            // 均为null，不做处理。
        } else if (this.ccQuestion != null && ccQuestion != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQuestion.compareTo(ccQuestion) != 0) {
                this.ccQuestion = ccQuestion;
                if (!this.toUpdateCols.contains("CC_QUESTION")) {
                    this.toUpdateCols.add("CC_QUESTION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQuestion = ccQuestion;
            if (!this.toUpdateCols.contains("CC_QUESTION")) {
                this.toUpdateCols.add("CC_QUESTION");
            }
        }
        return this;
    }

    /**
     * 咨询名称。
     */
    private String ccName;

    /**
     * 获取：咨询名称。
     */
    public String getCcName() {
        return this.ccName;
    }

    /**
     * 设置：咨询名称。
     */
    public CcQsInspection setCcName(String ccName) {
        if (this.ccName == null && ccName == null) {
            // 均为null，不做处理。
        } else if (this.ccName != null && ccName != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccName.compareTo(ccName) != 0) {
                this.ccName = ccName;
                if (!this.toUpdateCols.contains("CC_NAME")) {
                    this.toUpdateCols.add("CC_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccName = ccName;
            if (!this.toUpdateCols.contains("CC_NAME")) {
                this.toUpdateCols.add("CC_NAME");
            }
        }
        return this;
    }

    /**
     * 质安问题要点分类(直接子节点)。
     */
    private String ccQsIssuePointTypeChildId;

    /**
     * 获取：质安问题要点分类(直接子节点)。
     */
    public String getCcQsIssuePointTypeChildId() {
        return this.ccQsIssuePointTypeChildId;
    }

    /**
     * 设置：质安问题要点分类(直接子节点)。
     */
    public CcQsInspection setCcQsIssuePointTypeChildId(String ccQsIssuePointTypeChildId) {
        if (this.ccQsIssuePointTypeChildId == null && ccQsIssuePointTypeChildId == null) {
            // 均为null，不做处理。
        } else if (this.ccQsIssuePointTypeChildId != null && ccQsIssuePointTypeChildId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsIssuePointTypeChildId.compareTo(ccQsIssuePointTypeChildId) != 0) {
                this.ccQsIssuePointTypeChildId = ccQsIssuePointTypeChildId;
                if (!this.toUpdateCols.contains("CC_QS_ISSUE_POINT_TYPE_CHILD_ID")) {
                    this.toUpdateCols.add("CC_QS_ISSUE_POINT_TYPE_CHILD_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsIssuePointTypeChildId = ccQsIssuePointTypeChildId;
            if (!this.toUpdateCols.contains("CC_QS_ISSUE_POINT_TYPE_CHILD_ID")) {
                this.toUpdateCols.add("CC_QS_ISSUE_POINT_TYPE_CHILD_ID");
            }
        }
        return this;
    }

    /**
     * BIM模型构件。
     */
    private String ccBimModelComponentsId;

    /**
     * 获取：BIM模型构件。
     */
    public String getCcBimModelComponentsId() {
        return this.ccBimModelComponentsId;
    }

    /**
     * 设置：BIM模型构件。
     */
    public CcQsInspection setCcBimModelComponentsId(String ccBimModelComponentsId) {
        if (this.ccBimModelComponentsId == null && ccBimModelComponentsId == null) {
            // 均为null，不做处理。
        } else if (this.ccBimModelComponentsId != null && ccBimModelComponentsId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccBimModelComponentsId.compareTo(ccBimModelComponentsId) != 0) {
                this.ccBimModelComponentsId = ccBimModelComponentsId;
                if (!this.toUpdateCols.contains("CC_BIM_MODEL_COMPONENTS_ID")) {
                    this.toUpdateCols.add("CC_BIM_MODEL_COMPONENTS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccBimModelComponentsId = ccBimModelComponentsId;
            if (!this.toUpdateCols.contains("CC_BIM_MODEL_COMPONENTS_ID")) {
                this.toUpdateCols.add("CC_BIM_MODEL_COMPONENTS_ID");
            }
        }
        return this;
    }

    /**
     * 质安巡检用户。
     */
    private String ccQsInspectionUser;

    /**
     * 获取：质安巡检用户。
     */
    public String getCcQsInspectionUser() {
        return this.ccQsInspectionUser;
    }

    /**
     * 设置：质安巡检用户。
     */
    public CcQsInspection setCcQsInspectionUser(String ccQsInspectionUser) {
        if (this.ccQsInspectionUser == null && ccQsInspectionUser == null) {
            // 均为null，不做处理。
        } else if (this.ccQsInspectionUser != null && ccQsInspectionUser != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsInspectionUser.compareTo(ccQsInspectionUser) != 0) {
                this.ccQsInspectionUser = ccQsInspectionUser;
                if (!this.toUpdateCols.contains("CC_QS_INSPECTION_USER")) {
                    this.toUpdateCols.add("CC_QS_INSPECTION_USER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsInspectionUser = ccQsInspectionUser;
            if (!this.toUpdateCols.contains("CC_QS_INSPECTION_USER")) {
                this.toUpdateCols.add("CC_QS_INSPECTION_USER");
            }
        }
        return this;
    }

    /**
     * 质安巡检类型。
     */
    private String ccQsInspectionTypeId;

    /**
     * 获取：质安巡检类型。
     */
    public String getCcQsInspectionTypeId() {
        return this.ccQsInspectionTypeId;
    }

    /**
     * 设置：质安巡检类型。
     */
    public CcQsInspection setCcQsInspectionTypeId(String ccQsInspectionTypeId) {
        if (this.ccQsInspectionTypeId == null && ccQsInspectionTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccQsInspectionTypeId != null && ccQsInspectionTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsInspectionTypeId.compareTo(ccQsInspectionTypeId) != 0) {
                this.ccQsInspectionTypeId = ccQsInspectionTypeId;
                if (!this.toUpdateCols.contains("CC_QS_INSPECTION_TYPE_ID")) {
                    this.toUpdateCols.add("CC_QS_INSPECTION_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsInspectionTypeId = ccQsInspectionTypeId;
            if (!this.toUpdateCols.contains("CC_QS_INSPECTION_TYPE_ID")) {
                this.toUpdateCols.add("CC_QS_INSPECTION_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 巡检部位。
     */
    private String ccQsInspectionPosition;

    /**
     * 获取：巡检部位。
     */
    public String getCcQsInspectionPosition() {
        return this.ccQsInspectionPosition;
    }

    /**
     * 设置：巡检部位。
     */
    public CcQsInspection setCcQsInspectionPosition(String ccQsInspectionPosition) {
        if (this.ccQsInspectionPosition == null && ccQsInspectionPosition == null) {
            // 均为null，不做处理。
        } else if (this.ccQsInspectionPosition != null && ccQsInspectionPosition != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsInspectionPosition.compareTo(ccQsInspectionPosition) != 0) {
                this.ccQsInspectionPosition = ccQsInspectionPosition;
                if (!this.toUpdateCols.contains("CC_QS_INSPECTION_POSITION")) {
                    this.toUpdateCols.add("CC_QS_INSPECTION_POSITION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsInspectionPosition = ccQsInspectionPosition;
            if (!this.toUpdateCols.contains("CC_QS_INSPECTION_POSITION")) {
                this.toUpdateCols.add("CC_QS_INSPECTION_POSITION");
            }
        }
        return this;
    }

    /**
     * 质安发起类型。
     */
    private String ccQsInitiationTypeId;

    /**
     * 获取：质安发起类型。
     */
    public String getCcQsInitiationTypeId() {
        return this.ccQsInitiationTypeId;
    }

    /**
     * 设置：质安发起类型。
     */
    public CcQsInspection setCcQsInitiationTypeId(String ccQsInitiationTypeId) {
        if (this.ccQsInitiationTypeId == null && ccQsInitiationTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccQsInitiationTypeId != null && ccQsInitiationTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsInitiationTypeId.compareTo(ccQsInitiationTypeId) != 0) {
                this.ccQsInitiationTypeId = ccQsInitiationTypeId;
                if (!this.toUpdateCols.contains("CC_QS_INITIATION_TYPE_ID")) {
                    this.toUpdateCols.add("CC_QS_INITIATION_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsInitiationTypeId = ccQsInitiationTypeId;
            if (!this.toUpdateCols.contains("CC_QS_INITIATION_TYPE_ID")) {
                this.toUpdateCols.add("CC_QS_INITIATION_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 质安问题要点分类。
     */
    private String ccQsIssuePointTypeId;

    /**
     * 获取：质安问题要点分类。
     */
    public String getCcQsIssuePointTypeId() {
        return this.ccQsIssuePointTypeId;
    }

    /**
     * 设置：质安问题要点分类。
     */
    public CcQsInspection setCcQsIssuePointTypeId(String ccQsIssuePointTypeId) {
        if (this.ccQsIssuePointTypeId == null && ccQsIssuePointTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccQsIssuePointTypeId != null && ccQsIssuePointTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsIssuePointTypeId.compareTo(ccQsIssuePointTypeId) != 0) {
                this.ccQsIssuePointTypeId = ccQsIssuePointTypeId;
                if (!this.toUpdateCols.contains("CC_QS_ISSUE_POINT_TYPE_ID")) {
                    this.toUpdateCols.add("CC_QS_ISSUE_POINT_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsIssuePointTypeId = ccQsIssuePointTypeId;
            if (!this.toUpdateCols.contains("CC_QS_ISSUE_POINT_TYPE_ID")) {
                this.toUpdateCols.add("CC_QS_ISSUE_POINT_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 质安问题等级。
     */
    private String ccQsIssueLevelId;

    /**
     * 获取：质安问题等级。
     */
    public String getCcQsIssueLevelId() {
        return this.ccQsIssueLevelId;
    }

    /**
     * 设置：质安问题等级。
     */
    public CcQsInspection setCcQsIssueLevelId(String ccQsIssueLevelId) {
        if (this.ccQsIssueLevelId == null && ccQsIssueLevelId == null) {
            // 均为null，不做处理。
        } else if (this.ccQsIssueLevelId != null && ccQsIssueLevelId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsIssueLevelId.compareTo(ccQsIssueLevelId) != 0) {
                this.ccQsIssueLevelId = ccQsIssueLevelId;
                if (!this.toUpdateCols.contains("CC_QS_ISSUE_LEVEL_ID")) {
                    this.toUpdateCols.add("CC_QS_ISSUE_LEVEL_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsIssueLevelId = ccQsIssueLevelId;
            if (!this.toUpdateCols.contains("CC_QS_ISSUE_LEVEL_ID")) {
                this.toUpdateCols.add("CC_QS_ISSUE_LEVEL_ID");
            }
        }
        return this;
    }

    /**
     * 质量考核条款(多值)。
     */
    private String ccQsAssessmentCriteriaIds;

    /**
     * 获取：质量考核条款(多值)。
     */
    public String getCcQsAssessmentCriteriaIds() {
        return this.ccQsAssessmentCriteriaIds;
    }

    /**
     * 设置：质量考核条款(多值)。
     */
    public CcQsInspection setCcQsAssessmentCriteriaIds(String ccQsAssessmentCriteriaIds) {
        if (this.ccQsAssessmentCriteriaIds == null && ccQsAssessmentCriteriaIds == null) {
            // 均为null，不做处理。
        } else if (this.ccQsAssessmentCriteriaIds != null && ccQsAssessmentCriteriaIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsAssessmentCriteriaIds.compareTo(ccQsAssessmentCriteriaIds) != 0) {
                this.ccQsAssessmentCriteriaIds = ccQsAssessmentCriteriaIds;
                if (!this.toUpdateCols.contains("CC_QS_ASSESSMENT_CRITERIA_IDS")) {
                    this.toUpdateCols.add("CC_QS_ASSESSMENT_CRITERIA_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsAssessmentCriteriaIds = ccQsAssessmentCriteriaIds;
            if (!this.toUpdateCols.contains("CC_QS_ASSESSMENT_CRITERIA_IDS")) {
                this.toUpdateCols.add("CC_QS_ASSESSMENT_CRITERIA_IDS");
            }
        }
        return this;
    }

    /**
     * 关联的分部分项节点。
     */
    private String ccPrjPbsNodeId;

    /**
     * 获取：关联的分部分项节点。
     */
    public String getCcPrjPbsNodeId() {
        return this.ccPrjPbsNodeId;
    }

    /**
     * 设置：关联的分部分项节点。
     */
    public CcQsInspection setCcPrjPbsNodeId(String ccPrjPbsNodeId) {
        if (this.ccPrjPbsNodeId == null && ccPrjPbsNodeId == null) {
            // 均为null，不做处理。
        } else if (this.ccPrjPbsNodeId != null && ccPrjPbsNodeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccPrjPbsNodeId.compareTo(ccPrjPbsNodeId) != 0) {
                this.ccPrjPbsNodeId = ccPrjPbsNodeId;
                if (!this.toUpdateCols.contains("CC_PRJ_PBS_NODE_ID")) {
                    this.toUpdateCols.add("CC_PRJ_PBS_NODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccPrjPbsNodeId = ccPrjPbsNodeId;
            if (!this.toUpdateCols.contains("CC_PRJ_PBS_NODE_ID")) {
                this.toUpdateCols.add("CC_PRJ_PBS_NODE_ID");
            }
        }
        return this;
    }

    /**
     * 质安问题要点（多个）。
     */
    private String ccQsIssuePointIds;

    /**
     * 获取：质安问题要点（多个）。
     */
    public String getCcQsIssuePointIds() {
        return this.ccQsIssuePointIds;
    }

    /**
     * 设置：质安问题要点（多个）。
     */
    public CcQsInspection setCcQsIssuePointIds(String ccQsIssuePointIds) {
        if (this.ccQsIssuePointIds == null && ccQsIssuePointIds == null) {
            // 均为null，不做处理。
        } else if (this.ccQsIssuePointIds != null && ccQsIssuePointIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsIssuePointIds.compareTo(ccQsIssuePointIds) != 0) {
                this.ccQsIssuePointIds = ccQsIssuePointIds;
                if (!this.toUpdateCols.contains("CC_QS_ISSUE_POINT_IDS")) {
                    this.toUpdateCols.add("CC_QS_ISSUE_POINT_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsIssuePointIds = ccQsIssuePointIds;
            if (!this.toUpdateCols.contains("CC_QS_ISSUE_POINT_IDS")) {
                this.toUpdateCols.add("CC_QS_ISSUE_POINT_IDS");
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
    public CcQsInspection setRemark(String remark) {
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
     * 整改期限。
     */
    private LocalDate rectificationPeriod;

    /**
     * 获取：整改期限。
     */
    public LocalDate getRectificationPeriod() {
        return this.rectificationPeriod;
    }

    /**
     * 设置：整改期限。
     */
    public CcQsInspection setRectificationPeriod(LocalDate rectificationPeriod) {
        if (this.rectificationPeriod == null && rectificationPeriod == null) {
            // 均为null，不做处理。
        } else if (this.rectificationPeriod != null && rectificationPeriod != null) {
            // 均非null，判定不等，再做处理：
            if (this.rectificationPeriod.compareTo(rectificationPeriod) != 0) {
                this.rectificationPeriod = rectificationPeriod;
                if (!this.toUpdateCols.contains("RECTIFICATION_PERIOD")) {
                    this.toUpdateCols.add("RECTIFICATION_PERIOD");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.rectificationPeriod = rectificationPeriod;
            if (!this.toUpdateCols.contains("RECTIFICATION_PERIOD")) {
                this.toUpdateCols.add("RECTIFICATION_PERIOD");
            }
        }
        return this;
    }

    /**
     * 质安责任单位。
     */
    private String ccQsDutyCompany;

    /**
     * 获取：质安责任单位。
     */
    public String getCcQsDutyCompany() {
        return this.ccQsDutyCompany;
    }

    /**
     * 设置：质安责任单位。
     */
    public CcQsInspection setCcQsDutyCompany(String ccQsDutyCompany) {
        if (this.ccQsDutyCompany == null && ccQsDutyCompany == null) {
            // 均为null，不做处理。
        } else if (this.ccQsDutyCompany != null && ccQsDutyCompany != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsDutyCompany.compareTo(ccQsDutyCompany) != 0) {
                this.ccQsDutyCompany = ccQsDutyCompany;
                if (!this.toUpdateCols.contains("CC_QS_DUTY_COMPANY")) {
                    this.toUpdateCols.add("CC_QS_DUTY_COMPANY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsDutyCompany = ccQsDutyCompany;
            if (!this.toUpdateCols.contains("CC_QS_DUTY_COMPANY")) {
                this.toUpdateCols.add("CC_QS_DUTY_COMPANY");
            }
        }
        return this;
    }

    /**
     * 质安责任用户。
     */
    private String ccQsDutyUser;

    /**
     * 获取：质安责任用户。
     */
    public String getCcQsDutyUser() {
        return this.ccQsDutyUser;
    }

    /**
     * 设置：质安责任用户。
     */
    public CcQsInspection setCcQsDutyUser(String ccQsDutyUser) {
        if (this.ccQsDutyUser == null && ccQsDutyUser == null) {
            // 均为null，不做处理。
        } else if (this.ccQsDutyUser != null && ccQsDutyUser != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsDutyUser.compareTo(ccQsDutyUser) != 0) {
                this.ccQsDutyUser = ccQsDutyUser;
                if (!this.toUpdateCols.contains("CC_QS_DUTY_USER")) {
                    this.toUpdateCols.add("CC_QS_DUTY_USER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsDutyUser = ccQsDutyUser;
            if (!this.toUpdateCols.contains("CC_QS_DUTY_USER")) {
                this.toUpdateCols.add("CC_QS_DUTY_USER");
            }
        }
        return this;
    }

    /**
     * 安全责任人（多）。
     */
    private String ccSafeDutyUserIds;

    /**
     * 获取：安全责任人（多）。
     */
    public String getCcSafeDutyUserIds() {
        return this.ccSafeDutyUserIds;
    }

    /**
     * 设置：安全责任人（多）。
     */
    public CcQsInspection setCcSafeDutyUserIds(String ccSafeDutyUserIds) {
        if (this.ccSafeDutyUserIds == null && ccSafeDutyUserIds == null) {
            // 均为null，不做处理。
        } else if (this.ccSafeDutyUserIds != null && ccSafeDutyUserIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccSafeDutyUserIds.compareTo(ccSafeDutyUserIds) != 0) {
                this.ccSafeDutyUserIds = ccSafeDutyUserIds;
                if (!this.toUpdateCols.contains("CC_SAFE_DUTY_USER_IDS")) {
                    this.toUpdateCols.add("CC_SAFE_DUTY_USER_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccSafeDutyUserIds = ccSafeDutyUserIds;
            if (!this.toUpdateCols.contains("CC_SAFE_DUTY_USER_IDS")) {
                this.toUpdateCols.add("CC_SAFE_DUTY_USER_IDS");
            }
        }
        return this;
    }

    /**
     * 质安责任方。
     */
    private String ccQsDutyPostId;

    /**
     * 获取：质安责任方。
     */
    public String getCcQsDutyPostId() {
        return this.ccQsDutyPostId;
    }

    /**
     * 设置：质安责任方。
     */
    public CcQsInspection setCcQsDutyPostId(String ccQsDutyPostId) {
        if (this.ccQsDutyPostId == null && ccQsDutyPostId == null) {
            // 均为null，不做处理。
        } else if (this.ccQsDutyPostId != null && ccQsDutyPostId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsDutyPostId.compareTo(ccQsDutyPostId) != 0) {
                this.ccQsDutyPostId = ccQsDutyPostId;
                if (!this.toUpdateCols.contains("CC_QS_DUTY_POST_ID")) {
                    this.toUpdateCols.add("CC_QS_DUTY_POST_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsDutyPostId = ccQsDutyPostId;
            if (!this.toUpdateCols.contains("CC_QS_DUTY_POST_ID")) {
                this.toUpdateCols.add("CC_QS_DUTY_POST_ID");
            }
        }
        return this;
    }

    /**
     * 质安分包单位。
     */
    private String ccQsSubcontractorId;

    /**
     * 获取：质安分包单位。
     */
    public String getCcQsSubcontractorId() {
        return this.ccQsSubcontractorId;
    }

    /**
     * 设置：质安分包单位。
     */
    public CcQsInspection setCcQsSubcontractorId(String ccQsSubcontractorId) {
        if (this.ccQsSubcontractorId == null && ccQsSubcontractorId == null) {
            // 均为null，不做处理。
        } else if (this.ccQsSubcontractorId != null && ccQsSubcontractorId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsSubcontractorId.compareTo(ccQsSubcontractorId) != 0) {
                this.ccQsSubcontractorId = ccQsSubcontractorId;
                if (!this.toUpdateCols.contains("CC_QS_SUBCONTRACTOR_ID")) {
                    this.toUpdateCols.add("CC_QS_SUBCONTRACTOR_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsSubcontractorId = ccQsSubcontractorId;
            if (!this.toUpdateCols.contains("CC_QS_SUBCONTRACTOR_ID")) {
                this.toUpdateCols.add("CC_QS_SUBCONTRACTOR_ID");
            }
        }
        return this;
    }

    /**
     * 质安复核用户。
     */
    private String ccQsCheckUser;

    /**
     * 获取：质安复核用户。
     */
    public String getCcQsCheckUser() {
        return this.ccQsCheckUser;
    }

    /**
     * 设置：质安复核用户。
     */
    public CcQsInspection setCcQsCheckUser(String ccQsCheckUser) {
        if (this.ccQsCheckUser == null && ccQsCheckUser == null) {
            // 均为null，不做处理。
        } else if (this.ccQsCheckUser != null && ccQsCheckUser != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsCheckUser.compareTo(ccQsCheckUser) != 0) {
                this.ccQsCheckUser = ccQsCheckUser;
                if (!this.toUpdateCols.contains("CC_QS_CHECK_USER")) {
                    this.toUpdateCols.add("CC_QS_CHECK_USER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsCheckUser = ccQsCheckUser;
            if (!this.toUpdateCols.contains("CC_QS_CHECK_USER")) {
                this.toUpdateCols.add("CC_QS_CHECK_USER");
            }
        }
        return this;
    }

    /**
     * 质安抄送用户。
     */
    private String ccQsCopyUser;

    /**
     * 获取：质安抄送用户。
     */
    public String getCcQsCopyUser() {
        return this.ccQsCopyUser;
    }

    /**
     * 设置：质安抄送用户。
     */
    public CcQsInspection setCcQsCopyUser(String ccQsCopyUser) {
        if (this.ccQsCopyUser == null && ccQsCopyUser == null) {
            // 均为null，不做处理。
        } else if (this.ccQsCopyUser != null && ccQsCopyUser != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsCopyUser.compareTo(ccQsCopyUser) != 0) {
                this.ccQsCopyUser = ccQsCopyUser;
                if (!this.toUpdateCols.contains("CC_QS_COPY_USER")) {
                    this.toUpdateCols.add("CC_QS_COPY_USER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsCopyUser = ccQsCopyUser;
            if (!this.toUpdateCols.contains("CC_QS_COPY_USER")) {
                this.toUpdateCols.add("CC_QS_COPY_USER");
            }
        }
        return this;
    }

    /**
     * 抄报单位。
     */
    private String hcWcfCcUnit;

    /**
     * 获取：抄报单位。
     */
    public String getHcWcfCcUnit() {
        return this.hcWcfCcUnit;
    }

    /**
     * 设置：抄报单位。
     */
    public CcQsInspection setHcWcfCcUnit(String hcWcfCcUnit) {
        if (this.hcWcfCcUnit == null && hcWcfCcUnit == null) {
            // 均为null，不做处理。
        } else if (this.hcWcfCcUnit != null && hcWcfCcUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.hcWcfCcUnit.compareTo(hcWcfCcUnit) != 0) {
                this.hcWcfCcUnit = hcWcfCcUnit;
                if (!this.toUpdateCols.contains("HC_WCF_CC_UNIT")) {
                    this.toUpdateCols.add("HC_WCF_CC_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hcWcfCcUnit = hcWcfCcUnit;
            if (!this.toUpdateCols.contains("HC_WCF_CC_UNIT")) {
                this.toUpdateCols.add("HC_WCF_CC_UNIT");
            }
        }
        return this;
    }

    /**
     * 通知单编号。
     */
    private String hcyTableNumber;

    /**
     * 获取：通知单编号。
     */
    public String getHcyTableNumber() {
        return this.hcyTableNumber;
    }

    /**
     * 设置：通知单编号。
     */
    public CcQsInspection setHcyTableNumber(String hcyTableNumber) {
        if (this.hcyTableNumber == null && hcyTableNumber == null) {
            // 均为null，不做处理。
        } else if (this.hcyTableNumber != null && hcyTableNumber != null) {
            // 均非null，判定不等，再做处理：
            if (this.hcyTableNumber.compareTo(hcyTableNumber) != 0) {
                this.hcyTableNumber = hcyTableNumber;
                if (!this.toUpdateCols.contains("HCY_TABLE_NUMBER")) {
                    this.toUpdateCols.add("HCY_TABLE_NUMBER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hcyTableNumber = hcyTableNumber;
            if (!this.toUpdateCols.contains("HCY_TABLE_NUMBER")) {
                this.toUpdateCols.add("HCY_TABLE_NUMBER");
            }
        }
        return this;
    }

    /**
     * 质安问题图片。
     */
    private String ccQsIssuesImg;

    /**
     * 获取：质安问题图片。
     */
    public String getCcQsIssuesImg() {
        return this.ccQsIssuesImg;
    }

    /**
     * 设置：质安问题图片。
     */
    public CcQsInspection setCcQsIssuesImg(String ccQsIssuesImg) {
        if (this.ccQsIssuesImg == null && ccQsIssuesImg == null) {
            // 均为null，不做处理。
        } else if (this.ccQsIssuesImg != null && ccQsIssuesImg != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsIssuesImg.compareTo(ccQsIssuesImg) != 0) {
                this.ccQsIssuesImg = ccQsIssuesImg;
                if (!this.toUpdateCols.contains("CC_QS_ISSUES_IMG")) {
                    this.toUpdateCols.add("CC_QS_ISSUES_IMG");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsIssuesImg = ccQsIssuesImg;
            if (!this.toUpdateCols.contains("CC_QS_ISSUES_IMG")) {
                this.toUpdateCols.add("CC_QS_ISSUES_IMG");
            }
        }
        return this;
    }

    /**
     * 附件。
     */
    private String attachment;

    /**
     * 获取：附件。
     */
    public String getAttachment() {
        return this.attachment;
    }

    /**
     * 设置：附件。
     */
    public CcQsInspection setAttachment(String attachment) {
        if (this.attachment == null && attachment == null) {
            // 均为null，不做处理。
        } else if (this.attachment != null && attachment != null) {
            // 均非null，判定不等，再做处理：
            if (this.attachment.compareTo(attachment) != 0) {
                this.attachment = attachment;
                if (!this.toUpdateCols.contains("ATTACHMENT")) {
                    this.toUpdateCols.add("ATTACHMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attachment = attachment;
            if (!this.toUpdateCols.contains("ATTACHMENT")) {
                this.toUpdateCols.add("ATTACHMENT");
            }
        }
        return this;
    }

    /**
     * 质安当前状态。
     */
    private String ccQsCurrentStateId;

    /**
     * 获取：质安当前状态。
     */
    public String getCcQsCurrentStateId() {
        return this.ccQsCurrentStateId;
    }

    /**
     * 设置：质安当前状态。
     */
    public CcQsInspection setCcQsCurrentStateId(String ccQsCurrentStateId) {
        if (this.ccQsCurrentStateId == null && ccQsCurrentStateId == null) {
            // 均为null，不做处理。
        } else if (this.ccQsCurrentStateId != null && ccQsCurrentStateId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsCurrentStateId.compareTo(ccQsCurrentStateId) != 0) {
                this.ccQsCurrentStateId = ccQsCurrentStateId;
                if (!this.toUpdateCols.contains("CC_QS_CURRENT_STATE_ID")) {
                    this.toUpdateCols.add("CC_QS_CURRENT_STATE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsCurrentStateId = ccQsCurrentStateId;
            if (!this.toUpdateCols.contains("CC_QS_CURRENT_STATE_ID")) {
                this.toUpdateCols.add("CC_QS_CURRENT_STATE_ID");
            }
        }
        return this;
    }

    /**
     * 质安整改时间。
     */
    private LocalDate ccQsRectifyTime;

    /**
     * 获取：质安整改时间。
     */
    public LocalDate getCcQsRectifyTime() {
        return this.ccQsRectifyTime;
    }

    /**
     * 设置：质安整改时间。
     */
    public CcQsInspection setCcQsRectifyTime(LocalDate ccQsRectifyTime) {
        if (this.ccQsRectifyTime == null && ccQsRectifyTime == null) {
            // 均为null，不做处理。
        } else if (this.ccQsRectifyTime != null && ccQsRectifyTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsRectifyTime.compareTo(ccQsRectifyTime) != 0) {
                this.ccQsRectifyTime = ccQsRectifyTime;
                if (!this.toUpdateCols.contains("CC_QS_RECTIFY_TIME")) {
                    this.toUpdateCols.add("CC_QS_RECTIFY_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsRectifyTime = ccQsRectifyTime;
            if (!this.toUpdateCols.contains("CC_QS_RECTIFY_TIME")) {
                this.toUpdateCols.add("CC_QS_RECTIFY_TIME");
            }
        }
        return this;
    }

    /**
     * 质安整改图片。
     */
    private String ccQsRectifyImg;

    /**
     * 获取：质安整改图片。
     */
    public String getCcQsRectifyImg() {
        return this.ccQsRectifyImg;
    }

    /**
     * 设置：质安整改图片。
     */
    public CcQsInspection setCcQsRectifyImg(String ccQsRectifyImg) {
        if (this.ccQsRectifyImg == null && ccQsRectifyImg == null) {
            // 均为null，不做处理。
        } else if (this.ccQsRectifyImg != null && ccQsRectifyImg != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsRectifyImg.compareTo(ccQsRectifyImg) != 0) {
                this.ccQsRectifyImg = ccQsRectifyImg;
                if (!this.toUpdateCols.contains("CC_QS_RECTIFY_IMG")) {
                    this.toUpdateCols.add("CC_QS_RECTIFY_IMG");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsRectifyImg = ccQsRectifyImg;
            if (!this.toUpdateCols.contains("CC_QS_RECTIFY_IMG")) {
                this.toUpdateCols.add("CC_QS_RECTIFY_IMG");
            }
        }
        return this;
    }

    /**
     * 质安整改详述。
     */
    private String ccQsRectifyDesc;

    /**
     * 获取：质安整改详述。
     */
    public String getCcQsRectifyDesc() {
        return this.ccQsRectifyDesc;
    }

    /**
     * 设置：质安整改详述。
     */
    public CcQsInspection setCcQsRectifyDesc(String ccQsRectifyDesc) {
        if (this.ccQsRectifyDesc == null && ccQsRectifyDesc == null) {
            // 均为null，不做处理。
        } else if (this.ccQsRectifyDesc != null && ccQsRectifyDesc != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsRectifyDesc.compareTo(ccQsRectifyDesc) != 0) {
                this.ccQsRectifyDesc = ccQsRectifyDesc;
                if (!this.toUpdateCols.contains("CC_QS_RECTIFY_DESC")) {
                    this.toUpdateCols.add("CC_QS_RECTIFY_DESC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsRectifyDesc = ccQsRectifyDesc;
            if (!this.toUpdateCols.contains("CC_QS_RECTIFY_DESC")) {
                this.toUpdateCols.add("CC_QS_RECTIFY_DESC");
            }
        }
        return this;
    }

    /**
     * 质安复核时间。
     */
    private LocalDate ccQsCheckTime;

    /**
     * 获取：质安复核时间。
     */
    public LocalDate getCcQsCheckTime() {
        return this.ccQsCheckTime;
    }

    /**
     * 设置：质安复核时间。
     */
    public CcQsInspection setCcQsCheckTime(LocalDate ccQsCheckTime) {
        if (this.ccQsCheckTime == null && ccQsCheckTime == null) {
            // 均为null，不做处理。
        } else if (this.ccQsCheckTime != null && ccQsCheckTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsCheckTime.compareTo(ccQsCheckTime) != 0) {
                this.ccQsCheckTime = ccQsCheckTime;
                if (!this.toUpdateCols.contains("CC_QS_CHECK_TIME")) {
                    this.toUpdateCols.add("CC_QS_CHECK_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsCheckTime = ccQsCheckTime;
            if (!this.toUpdateCols.contains("CC_QS_CHECK_TIME")) {
                this.toUpdateCols.add("CC_QS_CHECK_TIME");
            }
        }
        return this;
    }

    /**
     * 质安通知单。
     */
    private String ccQsNoticeId;

    /**
     * 获取：质安通知单。
     */
    public String getCcQsNoticeId() {
        return this.ccQsNoticeId;
    }

    /**
     * 设置：质安通知单。
     */
    public CcQsInspection setCcQsNoticeId(String ccQsNoticeId) {
        if (this.ccQsNoticeId == null && ccQsNoticeId == null) {
            // 均为null，不做处理。
        } else if (this.ccQsNoticeId != null && ccQsNoticeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccQsNoticeId.compareTo(ccQsNoticeId) != 0) {
                this.ccQsNoticeId = ccQsNoticeId;
                if (!this.toUpdateCols.contains("CC_QS_NOTICE_ID")) {
                    this.toUpdateCols.add("CC_QS_NOTICE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccQsNoticeId = ccQsNoticeId;
            if (!this.toUpdateCols.contains("CC_QS_NOTICE_ID")) {
                this.toUpdateCols.add("CC_QS_NOTICE_ID");
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
    public static CcQsInspection newData() {
        CcQsInspection obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcQsInspection insertData() {
        CcQsInspection obj = modelHelper.insertData();
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
    public static CcQsInspection selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcQsInspection obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcQsInspection selectById(String id) {
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
    public static List<CcQsInspection> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcQsInspection> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcQsInspection> selectByIds(List<String> ids) {
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
    public static List<CcQsInspection> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcQsInspection> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcQsInspection> selectByWhere(Where where) {
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
    public static CcQsInspection selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcQsInspection> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcQsInspection.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcQsInspection selectOneByWhere(Where where) {
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
    public static void copyCols(CcQsInspection fromModel, CcQsInspection toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcQsInspection fromModel, CcQsInspection toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}