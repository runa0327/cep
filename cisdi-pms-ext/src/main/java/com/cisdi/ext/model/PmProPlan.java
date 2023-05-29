package com.cisdi.ext.model;

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
 * 项目进度计划。
 */
public class PmProPlan {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmProPlan> modelHelper = new ModelHelper<>("PM_PRO_PLAN", new PmProPlan());

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

    public static final String ENT_CODE = "PM_PRO_PLAN";
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
         * 是否模板。
         */
        public static final String IS_TEMPLATE = "IS_TEMPLATE";
        /**
         * 模板适用项目类型。
         */
        public static final String TEMPLATE_FOR_PROJECT_TYPE_ID = "TEMPLATE_FOR_PROJECT_TYPE_ID";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 第几天开始。
         */
        public static final String START_DAY = "START_DAY";
        /**
         * 预计开始日期。
         */
        public static final String PLAN_START_DATE = "PLAN_START_DATE";
        /**
         * 预计完成日期。
         */
        public static final String PLAN_COMPL_DATE = "PLAN_COMPL_DATE";
        /**
         * 预计总天数。
         */
        public static final String PLAN_TOTAL_DAYS = "PLAN_TOTAL_DAYS";
        /**
         * 预计已开展工期。
         */
        public static final String PLAN_CARRY_DAYS = "PLAN_CARRY_DAYS";
        /**
         * 预计当前进度百分比。
         */
        public static final String PLAN_CURRENT_PRO_PERCENT = "PLAN_CURRENT_PRO_PERCENT";
        /**
         * 实际开始日期。
         */
        public static final String ACTUAL_START_DATE = "ACTUAL_START_DATE";
        /**
         * 实际完成日期。
         */
        public static final String ACTUAL_COMPL_DATE = "ACTUAL_COMPL_DATE";
        /**
         * 实际总天数。
         */
        public static final String ACTUAL_TOTAL_DAYS = "ACTUAL_TOTAL_DAYS";
        /**
         * 实际已开展工期。
         */
        public static final String ACTUAL_CARRY_DAYS = "ACTUAL_CARRY_DAYS";
        /**
         * 实际当前进度百分比。
         */
        public static final String ACTUAL_CURRENT_PRO_PERCENT = "ACTUAL_CURRENT_PRO_PERCENT";
        /**
         * 进度状态。
         */
        public static final String PROGRESS_STATUS_ID = "PROGRESS_STATUS_ID";
        /**
         * 进度风险类型。
         */
        public static final String PROGRESS_RISK_TYPE_ID = "PROGRESS_RISK_TYPE_ID";
        /**
         * 进度风险说明。
         */
        public static final String PROGRESS_RISK_REMARK = "PROGRESS_RISK_REMARK";
        /**
         * CPMS的UUID。
         */
        public static final String CPMS_UUID = "CPMS_UUID";
        /**
         * CPMS的ID。
         */
        public static final String CPMS_ID = "CPMS_ID";
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
    public PmProPlan setId(String id) {
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
    public PmProPlan setVer(Integer ver) {
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
    public PmProPlan setTs(LocalDateTime ts) {
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
    public PmProPlan setIsPreset(Boolean isPreset) {
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
    public PmProPlan setCrtDt(LocalDateTime crtDt) {
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
    public PmProPlan setCrtUserId(String crtUserId) {
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
    public PmProPlan setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmProPlan setLastModiUserId(String lastModiUserId) {
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
    public PmProPlan setStatus(String status) {
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
    public PmProPlan setLkWfInstId(String lkWfInstId) {
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
    public PmProPlan setCode(String code) {
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
    public PmProPlan setName(String name) {
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
    public PmProPlan setRemark(String remark) {
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
     * 是否模板。
     */
    private Boolean isTemplate;

    /**
     * 获取：是否模板。
     */
    public Boolean getIsTemplate() {
        return this.isTemplate;
    }

    /**
     * 设置：是否模板。
     */
    public PmProPlan setIsTemplate(Boolean isTemplate) {
        if (this.isTemplate == null && isTemplate == null) {
            // 均为null，不做处理。
        } else if (this.isTemplate != null && isTemplate != null) {
            // 均非null，判定不等，再做处理：
            if (this.isTemplate.compareTo(isTemplate) != 0) {
                this.isTemplate = isTemplate;
                if (!this.toUpdateCols.contains("IS_TEMPLATE")) {
                    this.toUpdateCols.add("IS_TEMPLATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isTemplate = isTemplate;
            if (!this.toUpdateCols.contains("IS_TEMPLATE")) {
                this.toUpdateCols.add("IS_TEMPLATE");
            }
        }
        return this;
    }

    /**
     * 模板适用项目类型。
     */
    private String templateForProjectTypeId;

    /**
     * 获取：模板适用项目类型。
     */
    public String getTemplateForProjectTypeId() {
        return this.templateForProjectTypeId;
    }

    /**
     * 设置：模板适用项目类型。
     */
    public PmProPlan setTemplateForProjectTypeId(String templateForProjectTypeId) {
        if (this.templateForProjectTypeId == null && templateForProjectTypeId == null) {
            // 均为null，不做处理。
        } else if (this.templateForProjectTypeId != null && templateForProjectTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.templateForProjectTypeId.compareTo(templateForProjectTypeId) != 0) {
                this.templateForProjectTypeId = templateForProjectTypeId;
                if (!this.toUpdateCols.contains("TEMPLATE_FOR_PROJECT_TYPE_ID")) {
                    this.toUpdateCols.add("TEMPLATE_FOR_PROJECT_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.templateForProjectTypeId = templateForProjectTypeId;
            if (!this.toUpdateCols.contains("TEMPLATE_FOR_PROJECT_TYPE_ID")) {
                this.toUpdateCols.add("TEMPLATE_FOR_PROJECT_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 项目。
     */
    private String pmPrjId;

    /**
     * 获取：项目。
     */
    public String getPmPrjId() {
        return this.pmPrjId;
    }

    /**
     * 设置：项目。
     */
    public PmProPlan setPmPrjId(String pmPrjId) {
        if (this.pmPrjId == null && pmPrjId == null) {
            // 均为null，不做处理。
        } else if (this.pmPrjId != null && pmPrjId != null) {
            // 均非null，判定不等，再做处理：
            if (this.pmPrjId.compareTo(pmPrjId) != 0) {
                this.pmPrjId = pmPrjId;
                if (!this.toUpdateCols.contains("PM_PRJ_ID")) {
                    this.toUpdateCols.add("PM_PRJ_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pmPrjId = pmPrjId;
            if (!this.toUpdateCols.contains("PM_PRJ_ID")) {
                this.toUpdateCols.add("PM_PRJ_ID");
            }
        }
        return this;
    }

    /**
     * 第几天开始。
     */
    private Integer startDay;

    /**
     * 获取：第几天开始。
     */
    public Integer getStartDay() {
        return this.startDay;
    }

    /**
     * 设置：第几天开始。
     */
    public PmProPlan setStartDay(Integer startDay) {
        if (this.startDay == null && startDay == null) {
            // 均为null，不做处理。
        } else if (this.startDay != null && startDay != null) {
            // 均非null，判定不等，再做处理：
            if (this.startDay.compareTo(startDay) != 0) {
                this.startDay = startDay;
                if (!this.toUpdateCols.contains("START_DAY")) {
                    this.toUpdateCols.add("START_DAY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.startDay = startDay;
            if (!this.toUpdateCols.contains("START_DAY")) {
                this.toUpdateCols.add("START_DAY");
            }
        }
        return this;
    }

    /**
     * 预计开始日期。
     */
    private LocalDate planStartDate;

    /**
     * 获取：预计开始日期。
     */
    public LocalDate getPlanStartDate() {
        return this.planStartDate;
    }

    /**
     * 设置：预计开始日期。
     */
    public PmProPlan setPlanStartDate(LocalDate planStartDate) {
        if (this.planStartDate == null && planStartDate == null) {
            // 均为null，不做处理。
        } else if (this.planStartDate != null && planStartDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.planStartDate.compareTo(planStartDate) != 0) {
                this.planStartDate = planStartDate;
                if (!this.toUpdateCols.contains("PLAN_START_DATE")) {
                    this.toUpdateCols.add("PLAN_START_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planStartDate = planStartDate;
            if (!this.toUpdateCols.contains("PLAN_START_DATE")) {
                this.toUpdateCols.add("PLAN_START_DATE");
            }
        }
        return this;
    }

    /**
     * 预计完成日期。
     */
    private LocalDate planComplDate;

    /**
     * 获取：预计完成日期。
     */
    public LocalDate getPlanComplDate() {
        return this.planComplDate;
    }

    /**
     * 设置：预计完成日期。
     */
    public PmProPlan setPlanComplDate(LocalDate planComplDate) {
        if (this.planComplDate == null && planComplDate == null) {
            // 均为null，不做处理。
        } else if (this.planComplDate != null && planComplDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.planComplDate.compareTo(planComplDate) != 0) {
                this.planComplDate = planComplDate;
                if (!this.toUpdateCols.contains("PLAN_COMPL_DATE")) {
                    this.toUpdateCols.add("PLAN_COMPL_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planComplDate = planComplDate;
            if (!this.toUpdateCols.contains("PLAN_COMPL_DATE")) {
                this.toUpdateCols.add("PLAN_COMPL_DATE");
            }
        }
        return this;
    }

    /**
     * 预计总天数。
     */
    private Integer planTotalDays;

    /**
     * 获取：预计总天数。
     */
    public Integer getPlanTotalDays() {
        return this.planTotalDays;
    }

    /**
     * 设置：预计总天数。
     */
    public PmProPlan setPlanTotalDays(Integer planTotalDays) {
        if (this.planTotalDays == null && planTotalDays == null) {
            // 均为null，不做处理。
        } else if (this.planTotalDays != null && planTotalDays != null) {
            // 均非null，判定不等，再做处理：
            if (this.planTotalDays.compareTo(planTotalDays) != 0) {
                this.planTotalDays = planTotalDays;
                if (!this.toUpdateCols.contains("PLAN_TOTAL_DAYS")) {
                    this.toUpdateCols.add("PLAN_TOTAL_DAYS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planTotalDays = planTotalDays;
            if (!this.toUpdateCols.contains("PLAN_TOTAL_DAYS")) {
                this.toUpdateCols.add("PLAN_TOTAL_DAYS");
            }
        }
        return this;
    }

    /**
     * 预计已开展工期。
     */
    private Integer planCarryDays;

    /**
     * 获取：预计已开展工期。
     */
    public Integer getPlanCarryDays() {
        return this.planCarryDays;
    }

    /**
     * 设置：预计已开展工期。
     */
    public PmProPlan setPlanCarryDays(Integer planCarryDays) {
        if (this.planCarryDays == null && planCarryDays == null) {
            // 均为null，不做处理。
        } else if (this.planCarryDays != null && planCarryDays != null) {
            // 均非null，判定不等，再做处理：
            if (this.planCarryDays.compareTo(planCarryDays) != 0) {
                this.planCarryDays = planCarryDays;
                if (!this.toUpdateCols.contains("PLAN_CARRY_DAYS")) {
                    this.toUpdateCols.add("PLAN_CARRY_DAYS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planCarryDays = planCarryDays;
            if (!this.toUpdateCols.contains("PLAN_CARRY_DAYS")) {
                this.toUpdateCols.add("PLAN_CARRY_DAYS");
            }
        }
        return this;
    }

    /**
     * 预计当前进度百分比。
     */
    private BigDecimal planCurrentProPercent;

    /**
     * 获取：预计当前进度百分比。
     */
    public BigDecimal getPlanCurrentProPercent() {
        return this.planCurrentProPercent;
    }

    /**
     * 设置：预计当前进度百分比。
     */
    public PmProPlan setPlanCurrentProPercent(BigDecimal planCurrentProPercent) {
        if (this.planCurrentProPercent == null && planCurrentProPercent == null) {
            // 均为null，不做处理。
        } else if (this.planCurrentProPercent != null && planCurrentProPercent != null) {
            // 均非null，判定不等，再做处理：
            if (this.planCurrentProPercent.compareTo(planCurrentProPercent) != 0) {
                this.planCurrentProPercent = planCurrentProPercent;
                if (!this.toUpdateCols.contains("PLAN_CURRENT_PRO_PERCENT")) {
                    this.toUpdateCols.add("PLAN_CURRENT_PRO_PERCENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planCurrentProPercent = planCurrentProPercent;
            if (!this.toUpdateCols.contains("PLAN_CURRENT_PRO_PERCENT")) {
                this.toUpdateCols.add("PLAN_CURRENT_PRO_PERCENT");
            }
        }
        return this;
    }

    /**
     * 实际开始日期。
     */
    private LocalDate actualStartDate;

    /**
     * 获取：实际开始日期。
     */
    public LocalDate getActualStartDate() {
        return this.actualStartDate;
    }

    /**
     * 设置：实际开始日期。
     */
    public PmProPlan setActualStartDate(LocalDate actualStartDate) {
        if (this.actualStartDate == null && actualStartDate == null) {
            // 均为null，不做处理。
        } else if (this.actualStartDate != null && actualStartDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.actualStartDate.compareTo(actualStartDate) != 0) {
                this.actualStartDate = actualStartDate;
                if (!this.toUpdateCols.contains("ACTUAL_START_DATE")) {
                    this.toUpdateCols.add("ACTUAL_START_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actualStartDate = actualStartDate;
            if (!this.toUpdateCols.contains("ACTUAL_START_DATE")) {
                this.toUpdateCols.add("ACTUAL_START_DATE");
            }
        }
        return this;
    }

    /**
     * 实际完成日期。
     */
    private LocalDate actualComplDate;

    /**
     * 获取：实际完成日期。
     */
    public LocalDate getActualComplDate() {
        return this.actualComplDate;
    }

    /**
     * 设置：实际完成日期。
     */
    public PmProPlan setActualComplDate(LocalDate actualComplDate) {
        if (this.actualComplDate == null && actualComplDate == null) {
            // 均为null，不做处理。
        } else if (this.actualComplDate != null && actualComplDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.actualComplDate.compareTo(actualComplDate) != 0) {
                this.actualComplDate = actualComplDate;
                if (!this.toUpdateCols.contains("ACTUAL_COMPL_DATE")) {
                    this.toUpdateCols.add("ACTUAL_COMPL_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actualComplDate = actualComplDate;
            if (!this.toUpdateCols.contains("ACTUAL_COMPL_DATE")) {
                this.toUpdateCols.add("ACTUAL_COMPL_DATE");
            }
        }
        return this;
    }

    /**
     * 实际总天数。
     */
    private Integer actualTotalDays;

    /**
     * 获取：实际总天数。
     */
    public Integer getActualTotalDays() {
        return this.actualTotalDays;
    }

    /**
     * 设置：实际总天数。
     */
    public PmProPlan setActualTotalDays(Integer actualTotalDays) {
        if (this.actualTotalDays == null && actualTotalDays == null) {
            // 均为null，不做处理。
        } else if (this.actualTotalDays != null && actualTotalDays != null) {
            // 均非null，判定不等，再做处理：
            if (this.actualTotalDays.compareTo(actualTotalDays) != 0) {
                this.actualTotalDays = actualTotalDays;
                if (!this.toUpdateCols.contains("ACTUAL_TOTAL_DAYS")) {
                    this.toUpdateCols.add("ACTUAL_TOTAL_DAYS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actualTotalDays = actualTotalDays;
            if (!this.toUpdateCols.contains("ACTUAL_TOTAL_DAYS")) {
                this.toUpdateCols.add("ACTUAL_TOTAL_DAYS");
            }
        }
        return this;
    }

    /**
     * 实际已开展工期。
     */
    private Integer actualCarryDays;

    /**
     * 获取：实际已开展工期。
     */
    public Integer getActualCarryDays() {
        return this.actualCarryDays;
    }

    /**
     * 设置：实际已开展工期。
     */
    public PmProPlan setActualCarryDays(Integer actualCarryDays) {
        if (this.actualCarryDays == null && actualCarryDays == null) {
            // 均为null，不做处理。
        } else if (this.actualCarryDays != null && actualCarryDays != null) {
            // 均非null，判定不等，再做处理：
            if (this.actualCarryDays.compareTo(actualCarryDays) != 0) {
                this.actualCarryDays = actualCarryDays;
                if (!this.toUpdateCols.contains("ACTUAL_CARRY_DAYS")) {
                    this.toUpdateCols.add("ACTUAL_CARRY_DAYS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actualCarryDays = actualCarryDays;
            if (!this.toUpdateCols.contains("ACTUAL_CARRY_DAYS")) {
                this.toUpdateCols.add("ACTUAL_CARRY_DAYS");
            }
        }
        return this;
    }

    /**
     * 实际当前进度百分比。
     */
    private BigDecimal actualCurrentProPercent;

    /**
     * 获取：实际当前进度百分比。
     */
    public BigDecimal getActualCurrentProPercent() {
        return this.actualCurrentProPercent;
    }

    /**
     * 设置：实际当前进度百分比。
     */
    public PmProPlan setActualCurrentProPercent(BigDecimal actualCurrentProPercent) {
        if (this.actualCurrentProPercent == null && actualCurrentProPercent == null) {
            // 均为null，不做处理。
        } else if (this.actualCurrentProPercent != null && actualCurrentProPercent != null) {
            // 均非null，判定不等，再做处理：
            if (this.actualCurrentProPercent.compareTo(actualCurrentProPercent) != 0) {
                this.actualCurrentProPercent = actualCurrentProPercent;
                if (!this.toUpdateCols.contains("ACTUAL_CURRENT_PRO_PERCENT")) {
                    this.toUpdateCols.add("ACTUAL_CURRENT_PRO_PERCENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actualCurrentProPercent = actualCurrentProPercent;
            if (!this.toUpdateCols.contains("ACTUAL_CURRENT_PRO_PERCENT")) {
                this.toUpdateCols.add("ACTUAL_CURRENT_PRO_PERCENT");
            }
        }
        return this;
    }

    /**
     * 进度状态。
     */
    private String progressStatusId;

    /**
     * 获取：进度状态。
     */
    public String getProgressStatusId() {
        return this.progressStatusId;
    }

    /**
     * 设置：进度状态。
     */
    public PmProPlan setProgressStatusId(String progressStatusId) {
        if (this.progressStatusId == null && progressStatusId == null) {
            // 均为null，不做处理。
        } else if (this.progressStatusId != null && progressStatusId != null) {
            // 均非null，判定不等，再做处理：
            if (this.progressStatusId.compareTo(progressStatusId) != 0) {
                this.progressStatusId = progressStatusId;
                if (!this.toUpdateCols.contains("PROGRESS_STATUS_ID")) {
                    this.toUpdateCols.add("PROGRESS_STATUS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.progressStatusId = progressStatusId;
            if (!this.toUpdateCols.contains("PROGRESS_STATUS_ID")) {
                this.toUpdateCols.add("PROGRESS_STATUS_ID");
            }
        }
        return this;
    }

    /**
     * 进度风险类型。
     */
    private String progressRiskTypeId;

    /**
     * 获取：进度风险类型。
     */
    public String getProgressRiskTypeId() {
        return this.progressRiskTypeId;
    }

    /**
     * 设置：进度风险类型。
     */
    public PmProPlan setProgressRiskTypeId(String progressRiskTypeId) {
        if (this.progressRiskTypeId == null && progressRiskTypeId == null) {
            // 均为null，不做处理。
        } else if (this.progressRiskTypeId != null && progressRiskTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.progressRiskTypeId.compareTo(progressRiskTypeId) != 0) {
                this.progressRiskTypeId = progressRiskTypeId;
                if (!this.toUpdateCols.contains("PROGRESS_RISK_TYPE_ID")) {
                    this.toUpdateCols.add("PROGRESS_RISK_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.progressRiskTypeId = progressRiskTypeId;
            if (!this.toUpdateCols.contains("PROGRESS_RISK_TYPE_ID")) {
                this.toUpdateCols.add("PROGRESS_RISK_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 进度风险说明。
     */
    private String progressRiskRemark;

    /**
     * 获取：进度风险说明。
     */
    public String getProgressRiskRemark() {
        return this.progressRiskRemark;
    }

    /**
     * 设置：进度风险说明。
     */
    public PmProPlan setProgressRiskRemark(String progressRiskRemark) {
        if (this.progressRiskRemark == null && progressRiskRemark == null) {
            // 均为null，不做处理。
        } else if (this.progressRiskRemark != null && progressRiskRemark != null) {
            // 均非null，判定不等，再做处理：
            if (this.progressRiskRemark.compareTo(progressRiskRemark) != 0) {
                this.progressRiskRemark = progressRiskRemark;
                if (!this.toUpdateCols.contains("PROGRESS_RISK_REMARK")) {
                    this.toUpdateCols.add("PROGRESS_RISK_REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.progressRiskRemark = progressRiskRemark;
            if (!this.toUpdateCols.contains("PROGRESS_RISK_REMARK")) {
                this.toUpdateCols.add("PROGRESS_RISK_REMARK");
            }
        }
        return this;
    }

    /**
     * CPMS的UUID。
     */
    private String cpmsUuid;

    /**
     * 获取：CPMS的UUID。
     */
    public String getCpmsUuid() {
        return this.cpmsUuid;
    }

    /**
     * 设置：CPMS的UUID。
     */
    public PmProPlan setCpmsUuid(String cpmsUuid) {
        if (this.cpmsUuid == null && cpmsUuid == null) {
            // 均为null，不做处理。
        } else if (this.cpmsUuid != null && cpmsUuid != null) {
            // 均非null，判定不等，再做处理：
            if (this.cpmsUuid.compareTo(cpmsUuid) != 0) {
                this.cpmsUuid = cpmsUuid;
                if (!this.toUpdateCols.contains("CPMS_UUID")) {
                    this.toUpdateCols.add("CPMS_UUID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cpmsUuid = cpmsUuid;
            if (!this.toUpdateCols.contains("CPMS_UUID")) {
                this.toUpdateCols.add("CPMS_UUID");
            }
        }
        return this;
    }

    /**
     * CPMS的ID。
     */
    private String cpmsId;

    /**
     * 获取：CPMS的ID。
     */
    public String getCpmsId() {
        return this.cpmsId;
    }

    /**
     * 设置：CPMS的ID。
     */
    public PmProPlan setCpmsId(String cpmsId) {
        if (this.cpmsId == null && cpmsId == null) {
            // 均为null，不做处理。
        } else if (this.cpmsId != null && cpmsId != null) {
            // 均非null，判定不等，再做处理：
            if (this.cpmsId.compareTo(cpmsId) != 0) {
                this.cpmsId = cpmsId;
                if (!this.toUpdateCols.contains("CPMS_ID")) {
                    this.toUpdateCols.add("CPMS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cpmsId = cpmsId;
            if (!this.toUpdateCols.contains("CPMS_ID")) {
                this.toUpdateCols.add("CPMS_ID");
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
    public static PmProPlan newData() {
        PmProPlan obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmProPlan insertData() {
        PmProPlan obj = modelHelper.insertData();
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
    public static PmProPlan selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmProPlan obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static PmProPlan selectById(String id) {
        return selectById(id, null, null);
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids         ID列表。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmProPlan> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmProPlan> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmProPlan> selectByIds(List<String> ids) {
        return selectByIds(ids, null, null);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where       Where条件。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmProPlan> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmProPlan> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmProPlan> selectByWhere(Where where) {
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
    public static PmProPlan selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmProPlan> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用PmProPlan.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmptyList(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static PmProPlan selectOneByWhere(Where where) {
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
    public static void copyCols(PmProPlan fromModel, PmProPlan toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(PmProPlan fromModel, PmProPlan toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}
