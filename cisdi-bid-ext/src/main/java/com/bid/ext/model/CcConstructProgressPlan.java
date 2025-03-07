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
 * 施工进度计划。
 */
public class CcConstructProgressPlan {

    /**
     * 模型助手。
     */
    private static final ModelHelper<CcConstructProgressPlan> modelHelper = new ModelHelper<>("CC_CONSTRUCT_PROGRESS_PLAN", new CcConstructProgressPlan());

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

    public static final String ENT_CODE = "CC_CONSTRUCT_PROGRESS_PLAN";
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
         * 最后修改日期时间。
         */
        public static final String LAST_MODI_DT = "LAST_MODI_DT";
        /**
         * 最后修改用户。
         */
        public static final String LAST_MODI_USER_ID = "LAST_MODI_USER_ID";
        /**
         * 名称。
         */
        public static final String NAME = "NAME";
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
         * 项目。
         */
        public static final String CC_PRJ_ID = "CC_PRJ_ID";
        /**
         * 是否提交。
         */
        public static final String CC_CONSTRUCT_IS_COMMIT = "CC_CONSTRUCT_IS_COMMIT";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 施工进度计划类型。
         */
        public static final String CC_CONSTRUCT_PROGRESS_PLAN_TYPE_ID = "CC_CONSTRUCT_PROGRESS_PLAN_TYPE_ID";
        /**
         * 施工进度计划责任人。
         */
        public static final String CC_CONSTRUCTION_SCHEDULE_RESPONSIBLE_PERSON = "CC_CONSTRUCTION_SCHEDULE_RESPONSIBLE_PERSON";
        /**
         * 计划从。
         */
        public static final String PLAN_FR = "PLAN_FR";
        /**
         * 计划到。
         */
        public static final String PLAN_TO = "PLAN_TO";
        /**
         * 计划天数。
         */
        public static final String PLAN_DAYS = "PLAN_DAYS";
        /**
         * 实际从。
         */
        public static final String ACT_FR = "ACT_FR";
        /**
         * 实际到。
         */
        public static final String ACT_TO = "ACT_TO";
        /**
         * 实际天数。
         */
        public static final String ACT_DAYS = "ACT_DAYS";
        /**
         * 生效时间。
         */
        public static final String EFFECTIVE_TIME = "EFFECTIVE_TIME";
        /**
         * 附件。
         */
        public static final String CC_ATTACHMENTS = "CC_ATTACHMENTS";
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
    public CcConstructProgressPlan setId(String id) {
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
    public CcConstructProgressPlan setVer(Integer ver) {
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
    public CcConstructProgressPlan setTs(LocalDateTime ts) {
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
    public CcConstructProgressPlan setIsPreset(Boolean isPreset) {
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
    public CcConstructProgressPlan setCrtDt(LocalDateTime crtDt) {
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
    public CcConstructProgressPlan setLastModiDt(LocalDateTime lastModiDt) {
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
    public CcConstructProgressPlan setLastModiUserId(String lastModiUserId) {
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
    public CcConstructProgressPlan setName(String name) {
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
    public CcConstructProgressPlan setStatus(String status) {
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
    public CcConstructProgressPlan setLkWfInstId(String lkWfInstId) {
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
    public CcConstructProgressPlan setCode(String code) {
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
    public CcConstructProgressPlan setRemark(String remark) {
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
    public CcConstructProgressPlan setFastCode(String fastCode) {
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
    public CcConstructProgressPlan setIconFileGroupId(String iconFileGroupId) {
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
    public CcConstructProgressPlan setCcPrjId(String ccPrjId) {
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
     * 是否提交。
     */
    private Boolean ccConstructIsCommit;

    /**
     * 获取：是否提交。
     */
    public Boolean getCcConstructIsCommit() {
        return this.ccConstructIsCommit;
    }

    /**
     * 设置：是否提交。
     */
    public CcConstructProgressPlan setCcConstructIsCommit(Boolean ccConstructIsCommit) {
        if (this.ccConstructIsCommit == null && ccConstructIsCommit == null) {
            // 均为null，不做处理。
        } else if (this.ccConstructIsCommit != null && ccConstructIsCommit != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccConstructIsCommit.compareTo(ccConstructIsCommit) != 0) {
                this.ccConstructIsCommit = ccConstructIsCommit;
                if (!this.toUpdateCols.contains("CC_CONSTRUCT_IS_COMMIT")) {
                    this.toUpdateCols.add("CC_CONSTRUCT_IS_COMMIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccConstructIsCommit = ccConstructIsCommit;
            if (!this.toUpdateCols.contains("CC_CONSTRUCT_IS_COMMIT")) {
                this.toUpdateCols.add("CC_CONSTRUCT_IS_COMMIT");
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
    public CcConstructProgressPlan setCrtUserId(String crtUserId) {
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
     * 施工进度计划类型。
     */
    private String ccConstructProgressPlanTypeId;

    /**
     * 获取：施工进度计划类型。
     */
    public String getCcConstructProgressPlanTypeId() {
        return this.ccConstructProgressPlanTypeId;
    }

    /**
     * 设置：施工进度计划类型。
     */
    public CcConstructProgressPlan setCcConstructProgressPlanTypeId(String ccConstructProgressPlanTypeId) {
        if (this.ccConstructProgressPlanTypeId == null && ccConstructProgressPlanTypeId == null) {
            // 均为null，不做处理。
        } else if (this.ccConstructProgressPlanTypeId != null && ccConstructProgressPlanTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccConstructProgressPlanTypeId.compareTo(ccConstructProgressPlanTypeId) != 0) {
                this.ccConstructProgressPlanTypeId = ccConstructProgressPlanTypeId;
                if (!this.toUpdateCols.contains("CC_CONSTRUCT_PROGRESS_PLAN_TYPE_ID")) {
                    this.toUpdateCols.add("CC_CONSTRUCT_PROGRESS_PLAN_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccConstructProgressPlanTypeId = ccConstructProgressPlanTypeId;
            if (!this.toUpdateCols.contains("CC_CONSTRUCT_PROGRESS_PLAN_TYPE_ID")) {
                this.toUpdateCols.add("CC_CONSTRUCT_PROGRESS_PLAN_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 施工进度计划责任人。
     */
    private String ccConstructionScheduleResponsiblePerson;

    /**
     * 获取：施工进度计划责任人。
     */
    public String getCcConstructionScheduleResponsiblePerson() {
        return this.ccConstructionScheduleResponsiblePerson;
    }

    /**
     * 设置：施工进度计划责任人。
     */
    public CcConstructProgressPlan setCcConstructionScheduleResponsiblePerson(String ccConstructionScheduleResponsiblePerson) {
        if (this.ccConstructionScheduleResponsiblePerson == null && ccConstructionScheduleResponsiblePerson == null) {
            // 均为null，不做处理。
        } else if (this.ccConstructionScheduleResponsiblePerson != null && ccConstructionScheduleResponsiblePerson != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccConstructionScheduleResponsiblePerson.compareTo(ccConstructionScheduleResponsiblePerson) != 0) {
                this.ccConstructionScheduleResponsiblePerson = ccConstructionScheduleResponsiblePerson;
                if (!this.toUpdateCols.contains("CC_CONSTRUCTION_SCHEDULE_RESPONSIBLE_PERSON")) {
                    this.toUpdateCols.add("CC_CONSTRUCTION_SCHEDULE_RESPONSIBLE_PERSON");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccConstructionScheduleResponsiblePerson = ccConstructionScheduleResponsiblePerson;
            if (!this.toUpdateCols.contains("CC_CONSTRUCTION_SCHEDULE_RESPONSIBLE_PERSON")) {
                this.toUpdateCols.add("CC_CONSTRUCTION_SCHEDULE_RESPONSIBLE_PERSON");
            }
        }
        return this;
    }

    /**
     * 计划从。
     */
    private LocalDate planFr;

    /**
     * 获取：计划从。
     */
    public LocalDate getPlanFr() {
        return this.planFr;
    }

    /**
     * 设置：计划从。
     */
    public CcConstructProgressPlan setPlanFr(LocalDate planFr) {
        if (this.planFr == null && planFr == null) {
            // 均为null，不做处理。
        } else if (this.planFr != null && planFr != null) {
            // 均非null，判定不等，再做处理：
            if (this.planFr.compareTo(planFr) != 0) {
                this.planFr = planFr;
                if (!this.toUpdateCols.contains("PLAN_FR")) {
                    this.toUpdateCols.add("PLAN_FR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planFr = planFr;
            if (!this.toUpdateCols.contains("PLAN_FR")) {
                this.toUpdateCols.add("PLAN_FR");
            }
        }
        return this;
    }

    /**
     * 计划到。
     */
    private LocalDate planTo;

    /**
     * 获取：计划到。
     */
    public LocalDate getPlanTo() {
        return this.planTo;
    }

    /**
     * 设置：计划到。
     */
    public CcConstructProgressPlan setPlanTo(LocalDate planTo) {
        if (this.planTo == null && planTo == null) {
            // 均为null，不做处理。
        } else if (this.planTo != null && planTo != null) {
            // 均非null，判定不等，再做处理：
            if (this.planTo.compareTo(planTo) != 0) {
                this.planTo = planTo;
                if (!this.toUpdateCols.contains("PLAN_TO")) {
                    this.toUpdateCols.add("PLAN_TO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planTo = planTo;
            if (!this.toUpdateCols.contains("PLAN_TO")) {
                this.toUpdateCols.add("PLAN_TO");
            }
        }
        return this;
    }

    /**
     * 计划天数。
     */
    private BigDecimal planDays;

    /**
     * 获取：计划天数。
     */
    public BigDecimal getPlanDays() {
        return this.planDays;
    }

    /**
     * 设置：计划天数。
     */
    public CcConstructProgressPlan setPlanDays(BigDecimal planDays) {
        if (this.planDays == null && planDays == null) {
            // 均为null，不做处理。
        } else if (this.planDays != null && planDays != null) {
            // 均非null，判定不等，再做处理：
            if (this.planDays.compareTo(planDays) != 0) {
                this.planDays = planDays;
                if (!this.toUpdateCols.contains("PLAN_DAYS")) {
                    this.toUpdateCols.add("PLAN_DAYS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planDays = planDays;
            if (!this.toUpdateCols.contains("PLAN_DAYS")) {
                this.toUpdateCols.add("PLAN_DAYS");
            }
        }
        return this;
    }

    /**
     * 实际从。
     */
    private LocalDate actFr;

    /**
     * 获取：实际从。
     */
    public LocalDate getActFr() {
        return this.actFr;
    }

    /**
     * 设置：实际从。
     */
    public CcConstructProgressPlan setActFr(LocalDate actFr) {
        if (this.actFr == null && actFr == null) {
            // 均为null，不做处理。
        } else if (this.actFr != null && actFr != null) {
            // 均非null，判定不等，再做处理：
            if (this.actFr.compareTo(actFr) != 0) {
                this.actFr = actFr;
                if (!this.toUpdateCols.contains("ACT_FR")) {
                    this.toUpdateCols.add("ACT_FR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actFr = actFr;
            if (!this.toUpdateCols.contains("ACT_FR")) {
                this.toUpdateCols.add("ACT_FR");
            }
        }
        return this;
    }

    /**
     * 实际到。
     */
    private LocalDate actTo;

    /**
     * 获取：实际到。
     */
    public LocalDate getActTo() {
        return this.actTo;
    }

    /**
     * 设置：实际到。
     */
    public CcConstructProgressPlan setActTo(LocalDate actTo) {
        if (this.actTo == null && actTo == null) {
            // 均为null，不做处理。
        } else if (this.actTo != null && actTo != null) {
            // 均非null，判定不等，再做处理：
            if (this.actTo.compareTo(actTo) != 0) {
                this.actTo = actTo;
                if (!this.toUpdateCols.contains("ACT_TO")) {
                    this.toUpdateCols.add("ACT_TO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actTo = actTo;
            if (!this.toUpdateCols.contains("ACT_TO")) {
                this.toUpdateCols.add("ACT_TO");
            }
        }
        return this;
    }

    /**
     * 实际天数。
     */
    private BigDecimal actDays;

    /**
     * 获取：实际天数。
     */
    public BigDecimal getActDays() {
        return this.actDays;
    }

    /**
     * 设置：实际天数。
     */
    public CcConstructProgressPlan setActDays(BigDecimal actDays) {
        if (this.actDays == null && actDays == null) {
            // 均为null，不做处理。
        } else if (this.actDays != null && actDays != null) {
            // 均非null，判定不等，再做处理：
            if (this.actDays.compareTo(actDays) != 0) {
                this.actDays = actDays;
                if (!this.toUpdateCols.contains("ACT_DAYS")) {
                    this.toUpdateCols.add("ACT_DAYS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actDays = actDays;
            if (!this.toUpdateCols.contains("ACT_DAYS")) {
                this.toUpdateCols.add("ACT_DAYS");
            }
        }
        return this;
    }

    /**
     * 生效时间。
     */
    private LocalDateTime effectiveTime;

    /**
     * 获取：生效时间。
     */
    public LocalDateTime getEffectiveTime() {
        return this.effectiveTime;
    }

    /**
     * 设置：生效时间。
     */
    public CcConstructProgressPlan setEffectiveTime(LocalDateTime effectiveTime) {
        if (this.effectiveTime == null && effectiveTime == null) {
            // 均为null，不做处理。
        } else if (this.effectiveTime != null && effectiveTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.effectiveTime.compareTo(effectiveTime) != 0) {
                this.effectiveTime = effectiveTime;
                if (!this.toUpdateCols.contains("EFFECTIVE_TIME")) {
                    this.toUpdateCols.add("EFFECTIVE_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.effectiveTime = effectiveTime;
            if (!this.toUpdateCols.contains("EFFECTIVE_TIME")) {
                this.toUpdateCols.add("EFFECTIVE_TIME");
            }
        }
        return this;
    }

    /**
     * 附件。
     */
    private String ccAttachments;

    /**
     * 获取：附件。
     */
    public String getCcAttachments() {
        return this.ccAttachments;
    }

    /**
     * 设置：附件。
     */
    public CcConstructProgressPlan setCcAttachments(String ccAttachments) {
        if (this.ccAttachments == null && ccAttachments == null) {
            // 均为null，不做处理。
        } else if (this.ccAttachments != null && ccAttachments != null) {
            // 均非null，判定不等，再做处理：
            if (this.ccAttachments.compareTo(ccAttachments) != 0) {
                this.ccAttachments = ccAttachments;
                if (!this.toUpdateCols.contains("CC_ATTACHMENTS")) {
                    this.toUpdateCols.add("CC_ATTACHMENTS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ccAttachments = ccAttachments;
            if (!this.toUpdateCols.contains("CC_ATTACHMENTS")) {
                this.toUpdateCols.add("CC_ATTACHMENTS");
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
    public static CcConstructProgressPlan newData() {
        CcConstructProgressPlan obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static CcConstructProgressPlan insertData() {
        CcConstructProgressPlan obj = modelHelper.insertData();
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
    public static CcConstructProgressPlan selectById(String id, List<String> includeCols, List<String> excludeCols) {
        CcConstructProgressPlan obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static CcConstructProgressPlan selectById(String id) {
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
    public static List<CcConstructProgressPlan> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<CcConstructProgressPlan> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcConstructProgressPlan> selectByIds(List<String> ids) {
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
    public static List<CcConstructProgressPlan> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcConstructProgressPlan> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmpty(list)方法判断有无。
     */
    public static List<CcConstructProgressPlan> selectByWhere(Where where) {
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
    public static CcConstructProgressPlan selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<CcConstructProgressPlan> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用CcConstructProgressPlan.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmpty(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static CcConstructProgressPlan selectOneByWhere(Where where) {
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
    public static void copyCols(CcConstructProgressPlan fromModel, CcConstructProgressPlan toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(CcConstructProgressPlan fromModel, CcConstructProgressPlan toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}