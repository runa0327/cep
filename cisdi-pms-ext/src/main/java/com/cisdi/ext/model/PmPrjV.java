package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 项目（视图）。
 */
public class PmPrjV {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmPrjV> modelHelper = new ModelHelper<>("PM_PRJ_V", new PmPrjV());

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

    public static final String ENT_CODE = "PM_PRJ_V";
    public static final EntityTypeE ENTITY_TYPE = EntityTypeE.SUB_QUERY;

    // </editor-fold>

    // 属性常量相关：
    // <editor-fold>

    public static class Cols {
        /**
         * ID。
         */
        public static final String ID = "ID";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 代码。
         */
        public static final String CODE = "CODE";
        /**
         * 名称。
         */
        public static final String NAME = "NAME";
        /**
         * 业主单位。
         */
        public static final String CUSTOMER_UNIT = "CUSTOMER_UNIT";
        /**
         * 项目类型。
         */
        public static final String PROJECT_TYPE_ID = "PROJECT_TYPE_ID";
        /**
         * 建设地点。
         */
        public static final String BASE_LOCATION_ID = "BASE_LOCATION_ID";
        /**
         * 项目管理模式。
         */
        public static final String PRJ_MANAGE_MODE_ID = "PRJ_MANAGE_MODE_ID";
        /**
         * 项目状态。
         */
        public static final String PROJECT_PHASE_ID = "PROJECT_PHASE_ID";
        /**
         * 进度阶段。
         */
        public static final String TRANSITION_PHASE_ID = "TRANSITION_PHASE_ID";
        /**
         * 总投资（万）。
         */
        public static final String PRJ_TOTAL_INVEST = "PRJ_TOTAL_INVEST";
        /**
         * 预计开始日期。
         */
        public static final String PLAN_START_DATE = "PLAN_START_DATE";
        /**
         * 实际开始日期。
         */
        public static final String ACTUAL_START_DATE = "ACTUAL_START_DATE";
        /**
         * 预计完成日期。
         */
        public static final String PLAN_COMPL_DATE = "PLAN_COMPL_DATE";
        /**
         * 实际完成日期。
         */
        public static final String ACTUAL_COMPL_DATE = "ACTUAL_COMPL_DATE";
        /**
         * 预计当前进度百分比。
         */
        public static final String PLAN_CURRENT_PRO_PERCENT = "PLAN_CURRENT_PRO_PERCENT";
        /**
         * 实际当前进度百分比。
         */
        public static final String ACTUAL_CURRENT_PRO_PERCENT = "ACTUAL_CURRENT_PRO_PERCENT";
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
    public PmPrjV setId(String id) {
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
    public PmPrjV setCrtDt(LocalDateTime crtDt) {
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
    public PmPrjV setCode(String code) {
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
    public PmPrjV setName(String name) {
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
     * 业主单位。
     */
    private String customerUnit;

    /**
     * 获取：业主单位。
     */
    public String getCustomerUnit() {
        return this.customerUnit;
    }

    /**
     * 设置：业主单位。
     */
    public PmPrjV setCustomerUnit(String customerUnit) {
        if (this.customerUnit == null && customerUnit == null) {
            // 均为null，不做处理。
        } else if (this.customerUnit != null && customerUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.customerUnit.compareTo(customerUnit) != 0) {
                this.customerUnit = customerUnit;
                if (!this.toUpdateCols.contains("CUSTOMER_UNIT")) {
                    this.toUpdateCols.add("CUSTOMER_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.customerUnit = customerUnit;
            if (!this.toUpdateCols.contains("CUSTOMER_UNIT")) {
                this.toUpdateCols.add("CUSTOMER_UNIT");
            }
        }
        return this;
    }

    /**
     * 项目类型。
     */
    private String projectTypeId;

    /**
     * 获取：项目类型。
     */
    public String getProjectTypeId() {
        return this.projectTypeId;
    }

    /**
     * 设置：项目类型。
     */
    public PmPrjV setProjectTypeId(String projectTypeId) {
        if (this.projectTypeId == null && projectTypeId == null) {
            // 均为null，不做处理。
        } else if (this.projectTypeId != null && projectTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectTypeId.compareTo(projectTypeId) != 0) {
                this.projectTypeId = projectTypeId;
                if (!this.toUpdateCols.contains("PROJECT_TYPE_ID")) {
                    this.toUpdateCols.add("PROJECT_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectTypeId = projectTypeId;
            if (!this.toUpdateCols.contains("PROJECT_TYPE_ID")) {
                this.toUpdateCols.add("PROJECT_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 建设地点。
     */
    private String baseLocationId;

    /**
     * 获取：建设地点。
     */
    public String getBaseLocationId() {
        return this.baseLocationId;
    }

    /**
     * 设置：建设地点。
     */
    public PmPrjV setBaseLocationId(String baseLocationId) {
        if (this.baseLocationId == null && baseLocationId == null) {
            // 均为null，不做处理。
        } else if (this.baseLocationId != null && baseLocationId != null) {
            // 均非null，判定不等，再做处理：
            if (this.baseLocationId.compareTo(baseLocationId) != 0) {
                this.baseLocationId = baseLocationId;
                if (!this.toUpdateCols.contains("BASE_LOCATION_ID")) {
                    this.toUpdateCols.add("BASE_LOCATION_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.baseLocationId = baseLocationId;
            if (!this.toUpdateCols.contains("BASE_LOCATION_ID")) {
                this.toUpdateCols.add("BASE_LOCATION_ID");
            }
        }
        return this;
    }

    /**
     * 项目管理模式。
     */
    private String prjManageModeId;

    /**
     * 获取：项目管理模式。
     */
    public String getPrjManageModeId() {
        return this.prjManageModeId;
    }

    /**
     * 设置：项目管理模式。
     */
    public PmPrjV setPrjManageModeId(String prjManageModeId) {
        if (this.prjManageModeId == null && prjManageModeId == null) {
            // 均为null，不做处理。
        } else if (this.prjManageModeId != null && prjManageModeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjManageModeId.compareTo(prjManageModeId) != 0) {
                this.prjManageModeId = prjManageModeId;
                if (!this.toUpdateCols.contains("PRJ_MANAGE_MODE_ID")) {
                    this.toUpdateCols.add("PRJ_MANAGE_MODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjManageModeId = prjManageModeId;
            if (!this.toUpdateCols.contains("PRJ_MANAGE_MODE_ID")) {
                this.toUpdateCols.add("PRJ_MANAGE_MODE_ID");
            }
        }
        return this;
    }

    /**
     * 项目状态。
     */
    private String projectPhaseId;

    /**
     * 获取：项目状态。
     */
    public String getProjectPhaseId() {
        return this.projectPhaseId;
    }

    /**
     * 设置：项目状态。
     */
    public PmPrjV setProjectPhaseId(String projectPhaseId) {
        if (this.projectPhaseId == null && projectPhaseId == null) {
            // 均为null，不做处理。
        } else if (this.projectPhaseId != null && projectPhaseId != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectPhaseId.compareTo(projectPhaseId) != 0) {
                this.projectPhaseId = projectPhaseId;
                if (!this.toUpdateCols.contains("PROJECT_PHASE_ID")) {
                    this.toUpdateCols.add("PROJECT_PHASE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectPhaseId = projectPhaseId;
            if (!this.toUpdateCols.contains("PROJECT_PHASE_ID")) {
                this.toUpdateCols.add("PROJECT_PHASE_ID");
            }
        }
        return this;
    }

    /**
     * 进度阶段。
     */
    private String transitionPhaseId;

    /**
     * 获取：进度阶段。
     */
    public String getTransitionPhaseId() {
        return this.transitionPhaseId;
    }

    /**
     * 设置：进度阶段。
     */
    public PmPrjV setTransitionPhaseId(String transitionPhaseId) {
        if (this.transitionPhaseId == null && transitionPhaseId == null) {
            // 均为null，不做处理。
        } else if (this.transitionPhaseId != null && transitionPhaseId != null) {
            // 均非null，判定不等，再做处理：
            if (this.transitionPhaseId.compareTo(transitionPhaseId) != 0) {
                this.transitionPhaseId = transitionPhaseId;
                if (!this.toUpdateCols.contains("TRANSITION_PHASE_ID")) {
                    this.toUpdateCols.add("TRANSITION_PHASE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.transitionPhaseId = transitionPhaseId;
            if (!this.toUpdateCols.contains("TRANSITION_PHASE_ID")) {
                this.toUpdateCols.add("TRANSITION_PHASE_ID");
            }
        }
        return this;
    }

    /**
     * 总投资（万）。
     */
    private BigDecimal prjTotalInvest;

    /**
     * 获取：总投资（万）。
     */
    public BigDecimal getPrjTotalInvest() {
        return this.prjTotalInvest;
    }

    /**
     * 设置：总投资（万）。
     */
    public PmPrjV setPrjTotalInvest(BigDecimal prjTotalInvest) {
        if (this.prjTotalInvest == null && prjTotalInvest == null) {
            // 均为null，不做处理。
        } else if (this.prjTotalInvest != null && prjTotalInvest != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjTotalInvest.compareTo(prjTotalInvest) != 0) {
                this.prjTotalInvest = prjTotalInvest;
                if (!this.toUpdateCols.contains("PRJ_TOTAL_INVEST")) {
                    this.toUpdateCols.add("PRJ_TOTAL_INVEST");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjTotalInvest = prjTotalInvest;
            if (!this.toUpdateCols.contains("PRJ_TOTAL_INVEST")) {
                this.toUpdateCols.add("PRJ_TOTAL_INVEST");
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
    public PmPrjV setPlanStartDate(LocalDate planStartDate) {
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
    public PmPrjV setActualStartDate(LocalDate actualStartDate) {
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
    public PmPrjV setPlanComplDate(LocalDate planComplDate) {
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
    public PmPrjV setActualComplDate(LocalDate actualComplDate) {
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
    public PmPrjV setPlanCurrentProPercent(BigDecimal planCurrentProPercent) {
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
    public PmPrjV setActualCurrentProPercent(BigDecimal actualCurrentProPercent) {
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
    public PmPrjV setCpmsUuid(String cpmsUuid) {
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
    public PmPrjV setCpmsId(String cpmsId) {
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
    public static PmPrjV newData() {
        PmPrjV obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmPrjV insertData() {
        PmPrjV obj = modelHelper.insertData();
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
    public static PmPrjV selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmPrjV obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<PmPrjV> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmPrjV> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<PmPrjV> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmPrjV> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(PmPrjV fromModel, PmPrjV toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}