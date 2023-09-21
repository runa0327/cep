package com.cisdi.ext.model.base;

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
 * 项目。
 */
public class PmPrj {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmPrj> modelHelper = new ModelHelper<>("PM_PRJ", new PmPrj());

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

    public static final String ENT_CODE = "PM_PRJ";
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
         * 实际开工时间。
         */
        public static final String ACTUAL_START_TIME = "ACTUAL_START_TIME";
        /**
         * 计划开工时间。
         */
        public static final String PLAN_START_TIME = "PLAN_START_TIME";
        /**
         * 是否导入的记录。
         */
        public static final String IS_IMPORT = "IS_IMPORT";
        /**
         * 招标模式。
         */
        public static final String TENDER_MODE_ID = "TENDER_MODE_ID";
        /**
         * 实际竣工时间。
         */
        public static final String ACTUAL_END_TIME = "ACTUAL_END_TIME";
        /**
         * 计划竣工时间。
         */
        public static final String PLAN_END_TIME = "PLAN_END_TIME";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 名称。
         */
        public static final String NAME = "NAME";
        /**
         * 项目编码。
         */
        public static final String PM_CODE = "PM_CODE";
        /**
         * 项目序号。
         */
        public static final String PM_SEQ = "PM_SEQ";
        /**
         * 业主单位。
         */
        public static final String CUSTOMER_UNIT = "CUSTOMER_UNIT";
        /**
         * 项目管理模式。
         */
        public static final String PRJ_MANAGE_MODE_ID = "PRJ_MANAGE_MODE_ID";
        /**
         * 建设地点。
         */
        public static final String BASE_LOCATION_ID = "BASE_LOCATION_ID";
        /**
         * 占地面积(平方米)。
         */
        public static final String FLOOR_AREA = "FLOOR_AREA";
        /**
         * 项目类型。
         */
        public static final String PROJECT_TYPE_ID = "PROJECT_TYPE_ID";
        /**
         * 建设规模类型。
         */
        public static final String CON_SCALE_TYPE_ID = "CON_SCALE_TYPE_ID";
        /**
         * 建设规模数量。
         */
        public static final String CON_SCALE_QTY = "CON_SCALE_QTY";
        /**
         * 数量1。
         */
        public static final String QTY_ONE = "QTY_ONE";
        /**
         * 其他。
         */
        public static final String OTHER = "OTHER";
        /**
         * 数量2。
         */
        public static final String QTY_TWO = "QTY_TWO";
        /**
         * 数量3。
         */
        public static final String QTY_THREE = "QTY_THREE";
        /**
         * 建设规模数量2。
         */
        public static final String CON_SCALE_QTY2 = "CON_SCALE_QTY2";
        /**
         * 建设规模单位。
         */
        public static final String CON_SCALE_UOM_ID = "CON_SCALE_UOM_ID";
        /**
         * 建设年限。
         */
        public static final String BUILD_YEARS = "BUILD_YEARS";
        /**
         * 项目概况。
         */
        public static final String PRJ_SITUATION = "PRJ_SITUATION";
        /**
         * 投资来源。
         */
        public static final String INVESTMENT_SOURCE_ID = "INVESTMENT_SOURCE_ID";
        /**
         * EPC类型。
         */
        public static final String BASE_EPC_ID = "BASE_EPC_ID";
        /**
         * 项目状态。
         */
        public static final String PROJECT_PHASE_ID = "PROJECT_PHASE_ID";
        /**
         * 项目状态。
         */
        public static final String PROJECT_STATUS = "PROJECT_STATUS";
        /**
         * 进度阶段。
         */
        public static final String TRANSITION_PHASE_ID = "TRANSITION_PHASE_ID";
        /**
         * 是否正式项目。
         */
        public static final String IZ_FORMAL_PRJ = "IZ_FORMAL_PRJ";
        /**
         * 是否符合开工条件。
         */
        public static final String IZ_START_REQUIRE = "IZ_START_REQUIRE";
        /**
         * 是否结束。
         */
        public static final String IZ_END = "IZ_END";
        /**
         * 立项申请。
         */
        public static final String PM_PRJ_REQ_ID = "PM_PRJ_REQ_ID";
        /**
         * 立项批复文号。
         */
        public static final String PRJ_REPLY_NO = "PRJ_REPLY_NO";
        /**
         * 立项批复日期。
         */
        public static final String PRJ_REPLY_DATE = "PRJ_REPLY_DATE";
        /**
         * 立项批复材料。
         */
        public static final String PRJ_REPLY_FILE = "PRJ_REPLY_FILE";
        /**
         * 项目前期岗。
         */
        public static final String PRJ_EARLY_USER_ID = "PRJ_EARLY_USER_ID";
        /**
         * 项目设计岗。
         */
        public static final String PRJ_DESIGN_USER_ID = "PRJ_DESIGN_USER_ID";
        /**
         * 项目成本岗。
         */
        public static final String PRJ_COST_USER_ID = "PRJ_COST_USER_ID";
        /**
         * 进入省库。
         */
        public static final String IN_PROVINCE_REP = "IN_PROVINCE_REP";
        /**
         * 进入国库。
         */
        public static final String IN_COUNTRY_REP = "IN_COUNTRY_REP";
        /**
         * 建设单位。
         */
        public static final String BUILDER_UNIT = "BUILDER_UNIT";
        /**
         * 勘察单位。
         */
        public static final String SURVEYOR_UNIT = "SURVEYOR_UNIT";
        /**
         * 项目编号。
         */
        public static final String PRJ_CODE = "PRJ_CODE";
        /**
         * 设计单位。
         */
        public static final String DESIGNER_UNIT = "DESIGNER_UNIT";
        /**
         * 施工单位。
         */
        public static final String CONSTRUCTOR_UNIT = "CONSTRUCTOR_UNIT";
        /**
         * 监理单位。
         */
        public static final String SUPERVISOR_UNIT = "SUPERVISOR_UNIT";
        /**
         * 检测单位。
         */
        public static final String CHECKER_UNIT = "CHECKER_UNIT";
        /**
         * 全过程造价单位。
         */
        public static final String CONSULTER_UNIT = "CONSULTER_UNIT";
        /**
         * 项目图片。
         */
        public static final String PRJ_IMG = "PRJ_IMG";
        /**
         * 建筑面积（平方）。
         */
        public static final String BUILDING_AREA = "BUILDING_AREA";
        /**
         * 责任人。
         */
        public static final String RESPONSIBLE_ID = "RESPONSIBLE_ID";
        /**
         * 批复日期。
         */
        public static final String REPLY_DATE = "REPLY_DATE";
        /**
         * 批复文号。
         */
        public static final String REPLY_NO = "REPLY_NO";
        /**
         * 项目来源类型。
         */
        public static final String PROJECT_SOURCE_TYPE_ID = "PROJECT_SOURCE_TYPE_ID";
        /**
         * 概算金额（万）。
         */
        public static final String ESTIMATE_AMT = "ESTIMATE_AMT";
        /**
         * 预算金额。
         */
        public static final String BUDGET_AMT = "BUDGET_AMT";
        /**
         * 结算金额（万）。
         */
        public static final String SETTLEMENT_AMT = "SETTLEMENT_AMT";
        /**
         * 项目建议书实际完成日期。
         */
        public static final String PROJECT_PROPOSAL_ACTUAL_DATE = "PROJECT_PROPOSAL_ACTUAL_DATE";
        /**
         * 编制人。
         */
        public static final String AUTHOR = "AUTHOR";
        /**
         * 盖章的立项申请书。
         */
        public static final String STAMPED_PRJ_REQ_FILE = "STAMPED_PRJ_REQ_FILE";
        /**
         * CPMS的UUID。
         */
        public static final String CPMS_UUID = "CPMS_UUID";
        /**
         * CPMS的ID。
         */
        public static final String CPMS_ID = "CPMS_ID";
        /**
         * CPMS建设地点。
         */
        public static final String CPMS_CONSTRUCTION_SITE = "CPMS_CONSTRUCTION_SITE";
        /**
         * 匡算总投资(万)。
         */
        public static final String ESTIMATED_TOTAL_INVEST = "ESTIMATED_TOTAL_INVEST";
        /**
         * 工程费用（万）。
         */
        public static final String PROJECT_AMT = "PROJECT_AMT";
        /**
         * 建安工程费(万)。
         */
        public static final String CONSTRUCT_PRJ_AMT = "CONSTRUCT_PRJ_AMT";
        /**
         * 设备采购费(万)。
         */
        public static final String EQUIP_BUY_AMT = "EQUIP_BUY_AMT";
        /**
         * 科研设备费(万)。
         */
        public static final String EQUIPMENT_COST = "EQUIPMENT_COST";
        /**
         * 工程其他费用(万)。
         */
        public static final String PROJECT_OTHER_AMT = "PROJECT_OTHER_AMT";
        /**
         * 土地征拆费用(万)。
         */
        public static final String LAND_BUY_AMT = "LAND_BUY_AMT";
        /**
         * 预备费(万)。
         */
        public static final String PREPARE_AMT = "PREPARE_AMT";
        /**
         * 建设期利息（万）。
         */
        public static final String CONSTRUCT_PERIOD_INTEREST = "CONSTRUCT_PERIOD_INTEREST";
        /**
         * 投资测算优先级。
         */
        public static final String INVEST_PRIORITY = "INVEST_PRIORITY";
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
    public PmPrj setId(String id) {
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
    public PmPrj setVer(Integer ver) {
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
    public PmPrj setTs(LocalDateTime ts) {
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
    public PmPrj setIsPreset(Boolean isPreset) {
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
    public PmPrj setCrtDt(LocalDateTime crtDt) {
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
    public PmPrj setCrtUserId(String crtUserId) {
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
    public PmPrj setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmPrj setLastModiUserId(String lastModiUserId) {
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
    public PmPrj setLkWfInstId(String lkWfInstId) {
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
    public PmPrj setCode(String code) {
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
    public PmPrj setRemark(String remark) {
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
     * 实际开工时间。
     */
    private LocalDateTime actualStartTime;

    /**
     * 获取：实际开工时间。
     */
    public LocalDateTime getActualStartTime() {
        return this.actualStartTime;
    }

    /**
     * 设置：实际开工时间。
     */
    public PmPrj setActualStartTime(LocalDateTime actualStartTime) {
        if (this.actualStartTime == null && actualStartTime == null) {
            // 均为null，不做处理。
        } else if (this.actualStartTime != null && actualStartTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.actualStartTime.compareTo(actualStartTime) != 0) {
                this.actualStartTime = actualStartTime;
                if (!this.toUpdateCols.contains("ACTUAL_START_TIME")) {
                    this.toUpdateCols.add("ACTUAL_START_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actualStartTime = actualStartTime;
            if (!this.toUpdateCols.contains("ACTUAL_START_TIME")) {
                this.toUpdateCols.add("ACTUAL_START_TIME");
            }
        }
        return this;
    }

    /**
     * 计划开工时间。
     */
    private LocalDateTime planStartTime;

    /**
     * 获取：计划开工时间。
     */
    public LocalDateTime getPlanStartTime() {
        return this.planStartTime;
    }

    /**
     * 设置：计划开工时间。
     */
    public PmPrj setPlanStartTime(LocalDateTime planStartTime) {
        if (this.planStartTime == null && planStartTime == null) {
            // 均为null，不做处理。
        } else if (this.planStartTime != null && planStartTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.planStartTime.compareTo(planStartTime) != 0) {
                this.planStartTime = planStartTime;
                if (!this.toUpdateCols.contains("PLAN_START_TIME")) {
                    this.toUpdateCols.add("PLAN_START_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planStartTime = planStartTime;
            if (!this.toUpdateCols.contains("PLAN_START_TIME")) {
                this.toUpdateCols.add("PLAN_START_TIME");
            }
        }
        return this;
    }

    /**
     * 是否导入的记录。
     */
    private Boolean isImport;

    /**
     * 获取：是否导入的记录。
     */
    public Boolean getIsImport() {
        return this.isImport;
    }

    /**
     * 设置：是否导入的记录。
     */
    public PmPrj setIsImport(Boolean isImport) {
        if (this.isImport == null && isImport == null) {
            // 均为null，不做处理。
        } else if (this.isImport != null && isImport != null) {
            // 均非null，判定不等，再做处理：
            if (this.isImport.compareTo(isImport) != 0) {
                this.isImport = isImport;
                if (!this.toUpdateCols.contains("IS_IMPORT")) {
                    this.toUpdateCols.add("IS_IMPORT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isImport = isImport;
            if (!this.toUpdateCols.contains("IS_IMPORT")) {
                this.toUpdateCols.add("IS_IMPORT");
            }
        }
        return this;
    }

    /**
     * 招标模式。
     */
    private String tenderModeId;

    /**
     * 获取：招标模式。
     */
    public String getTenderModeId() {
        return this.tenderModeId;
    }

    /**
     * 设置：招标模式。
     */
    public PmPrj setTenderModeId(String tenderModeId) {
        if (this.tenderModeId == null && tenderModeId == null) {
            // 均为null，不做处理。
        } else if (this.tenderModeId != null && tenderModeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.tenderModeId.compareTo(tenderModeId) != 0) {
                this.tenderModeId = tenderModeId;
                if (!this.toUpdateCols.contains("TENDER_MODE_ID")) {
                    this.toUpdateCols.add("TENDER_MODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.tenderModeId = tenderModeId;
            if (!this.toUpdateCols.contains("TENDER_MODE_ID")) {
                this.toUpdateCols.add("TENDER_MODE_ID");
            }
        }
        return this;
    }

    /**
     * 实际竣工时间。
     */
    private LocalDateTime actualEndTime;

    /**
     * 获取：实际竣工时间。
     */
    public LocalDateTime getActualEndTime() {
        return this.actualEndTime;
    }

    /**
     * 设置：实际竣工时间。
     */
    public PmPrj setActualEndTime(LocalDateTime actualEndTime) {
        if (this.actualEndTime == null && actualEndTime == null) {
            // 均为null，不做处理。
        } else if (this.actualEndTime != null && actualEndTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.actualEndTime.compareTo(actualEndTime) != 0) {
                this.actualEndTime = actualEndTime;
                if (!this.toUpdateCols.contains("ACTUAL_END_TIME")) {
                    this.toUpdateCols.add("ACTUAL_END_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actualEndTime = actualEndTime;
            if (!this.toUpdateCols.contains("ACTUAL_END_TIME")) {
                this.toUpdateCols.add("ACTUAL_END_TIME");
            }
        }
        return this;
    }

    /**
     * 计划竣工时间。
     */
    private LocalDateTime planEndTime;

    /**
     * 获取：计划竣工时间。
     */
    public LocalDateTime getPlanEndTime() {
        return this.planEndTime;
    }

    /**
     * 设置：计划竣工时间。
     */
    public PmPrj setPlanEndTime(LocalDateTime planEndTime) {
        if (this.planEndTime == null && planEndTime == null) {
            // 均为null，不做处理。
        } else if (this.planEndTime != null && planEndTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.planEndTime.compareTo(planEndTime) != 0) {
                this.planEndTime = planEndTime;
                if (!this.toUpdateCols.contains("PLAN_END_TIME")) {
                    this.toUpdateCols.add("PLAN_END_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planEndTime = planEndTime;
            if (!this.toUpdateCols.contains("PLAN_END_TIME")) {
                this.toUpdateCols.add("PLAN_END_TIME");
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
    public PmPrj setStatus(String status) {
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
    public PmPrj setName(String name) {
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
     * 项目编码。
     */
    private String pmCode;

    /**
     * 获取：项目编码。
     */
    public String getPmCode() {
        return this.pmCode;
    }

    /**
     * 设置：项目编码。
     */
    public PmPrj setPmCode(String pmCode) {
        if (this.pmCode == null && pmCode == null) {
            // 均为null，不做处理。
        } else if (this.pmCode != null && pmCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.pmCode.compareTo(pmCode) != 0) {
                this.pmCode = pmCode;
                if (!this.toUpdateCols.contains("PM_CODE")) {
                    this.toUpdateCols.add("PM_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pmCode = pmCode;
            if (!this.toUpdateCols.contains("PM_CODE")) {
                this.toUpdateCols.add("PM_CODE");
            }
        }
        return this;
    }

    /**
     * 项目序号。
     */
    private Integer pmSeq;

    /**
     * 获取：项目序号。
     */
    public Integer getPmSeq() {
        return this.pmSeq;
    }

    /**
     * 设置：项目序号。
     */
    public PmPrj setPmSeq(Integer pmSeq) {
        if (this.pmSeq == null && pmSeq == null) {
            // 均为null，不做处理。
        } else if (this.pmSeq != null && pmSeq != null) {
            // 均非null，判定不等，再做处理：
            if (this.pmSeq.compareTo(pmSeq) != 0) {
                this.pmSeq = pmSeq;
                if (!this.toUpdateCols.contains("PM_SEQ")) {
                    this.toUpdateCols.add("PM_SEQ");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pmSeq = pmSeq;
            if (!this.toUpdateCols.contains("PM_SEQ")) {
                this.toUpdateCols.add("PM_SEQ");
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
    public PmPrj setCustomerUnit(String customerUnit) {
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
    public PmPrj setPrjManageModeId(String prjManageModeId) {
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
    public PmPrj setBaseLocationId(String baseLocationId) {
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
     * 占地面积(平方米)。
     */
    private BigDecimal floorArea;

    /**
     * 获取：占地面积(平方米)。
     */
    public BigDecimal getFloorArea() {
        return this.floorArea;
    }

    /**
     * 设置：占地面积(平方米)。
     */
    public PmPrj setFloorArea(BigDecimal floorArea) {
        if (this.floorArea == null && floorArea == null) {
            // 均为null，不做处理。
        } else if (this.floorArea != null && floorArea != null) {
            // 均非null，判定不等，再做处理：
            if (this.floorArea.compareTo(floorArea) != 0) {
                this.floorArea = floorArea;
                if (!this.toUpdateCols.contains("FLOOR_AREA")) {
                    this.toUpdateCols.add("FLOOR_AREA");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.floorArea = floorArea;
            if (!this.toUpdateCols.contains("FLOOR_AREA")) {
                this.toUpdateCols.add("FLOOR_AREA");
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
    public PmPrj setProjectTypeId(String projectTypeId) {
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
     * 建设规模类型。
     */
    private String conScaleTypeId;

    /**
     * 获取：建设规模类型。
     */
    public String getConScaleTypeId() {
        return this.conScaleTypeId;
    }

    /**
     * 设置：建设规模类型。
     */
    public PmPrj setConScaleTypeId(String conScaleTypeId) {
        if (this.conScaleTypeId == null && conScaleTypeId == null) {
            // 均为null，不做处理。
        } else if (this.conScaleTypeId != null && conScaleTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.conScaleTypeId.compareTo(conScaleTypeId) != 0) {
                this.conScaleTypeId = conScaleTypeId;
                if (!this.toUpdateCols.contains("CON_SCALE_TYPE_ID")) {
                    this.toUpdateCols.add("CON_SCALE_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.conScaleTypeId = conScaleTypeId;
            if (!this.toUpdateCols.contains("CON_SCALE_TYPE_ID")) {
                this.toUpdateCols.add("CON_SCALE_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 建设规模数量。
     */
    private BigDecimal conScaleQty;

    /**
     * 获取：建设规模数量。
     */
    public BigDecimal getConScaleQty() {
        return this.conScaleQty;
    }

    /**
     * 设置：建设规模数量。
     */
    public PmPrj setConScaleQty(BigDecimal conScaleQty) {
        if (this.conScaleQty == null && conScaleQty == null) {
            // 均为null，不做处理。
        } else if (this.conScaleQty != null && conScaleQty != null) {
            // 均非null，判定不等，再做处理：
            if (this.conScaleQty.compareTo(conScaleQty) != 0) {
                this.conScaleQty = conScaleQty;
                if (!this.toUpdateCols.contains("CON_SCALE_QTY")) {
                    this.toUpdateCols.add("CON_SCALE_QTY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.conScaleQty = conScaleQty;
            if (!this.toUpdateCols.contains("CON_SCALE_QTY")) {
                this.toUpdateCols.add("CON_SCALE_QTY");
            }
        }
        return this;
    }

    /**
     * 数量1。
     */
    private BigDecimal qtyOne;

    /**
     * 获取：数量1。
     */
    public BigDecimal getQtyOne() {
        return this.qtyOne;
    }

    /**
     * 设置：数量1。
     */
    public PmPrj setQtyOne(BigDecimal qtyOne) {
        if (this.qtyOne == null && qtyOne == null) {
            // 均为null，不做处理。
        } else if (this.qtyOne != null && qtyOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.qtyOne.compareTo(qtyOne) != 0) {
                this.qtyOne = qtyOne;
                if (!this.toUpdateCols.contains("QTY_ONE")) {
                    this.toUpdateCols.add("QTY_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.qtyOne = qtyOne;
            if (!this.toUpdateCols.contains("QTY_ONE")) {
                this.toUpdateCols.add("QTY_ONE");
            }
        }
        return this;
    }

    /**
     * 其他。
     */
    private String other;

    /**
     * 获取：其他。
     */
    public String getOther() {
        return this.other;
    }

    /**
     * 设置：其他。
     */
    public PmPrj setOther(String other) {
        if (this.other == null && other == null) {
            // 均为null，不做处理。
        } else if (this.other != null && other != null) {
            // 均非null，判定不等，再做处理：
            if (this.other.compareTo(other) != 0) {
                this.other = other;
                if (!this.toUpdateCols.contains("OTHER")) {
                    this.toUpdateCols.add("OTHER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.other = other;
            if (!this.toUpdateCols.contains("OTHER")) {
                this.toUpdateCols.add("OTHER");
            }
        }
        return this;
    }

    /**
     * 数量2。
     */
    private Integer qtyTwo;

    /**
     * 获取：数量2。
     */
    public Integer getQtyTwo() {
        return this.qtyTwo;
    }

    /**
     * 设置：数量2。
     */
    public PmPrj setQtyTwo(Integer qtyTwo) {
        if (this.qtyTwo == null && qtyTwo == null) {
            // 均为null，不做处理。
        } else if (this.qtyTwo != null && qtyTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.qtyTwo.compareTo(qtyTwo) != 0) {
                this.qtyTwo = qtyTwo;
                if (!this.toUpdateCols.contains("QTY_TWO")) {
                    this.toUpdateCols.add("QTY_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.qtyTwo = qtyTwo;
            if (!this.toUpdateCols.contains("QTY_TWO")) {
                this.toUpdateCols.add("QTY_TWO");
            }
        }
        return this;
    }

    /**
     * 数量3。
     */
    private Integer qtyThree;

    /**
     * 获取：数量3。
     */
    public Integer getQtyThree() {
        return this.qtyThree;
    }

    /**
     * 设置：数量3。
     */
    public PmPrj setQtyThree(Integer qtyThree) {
        if (this.qtyThree == null && qtyThree == null) {
            // 均为null，不做处理。
        } else if (this.qtyThree != null && qtyThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.qtyThree.compareTo(qtyThree) != 0) {
                this.qtyThree = qtyThree;
                if (!this.toUpdateCols.contains("QTY_THREE")) {
                    this.toUpdateCols.add("QTY_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.qtyThree = qtyThree;
            if (!this.toUpdateCols.contains("QTY_THREE")) {
                this.toUpdateCols.add("QTY_THREE");
            }
        }
        return this;
    }

    /**
     * 建设规模数量2。
     */
    private BigDecimal conScaleQty2;

    /**
     * 获取：建设规模数量2。
     */
    public BigDecimal getConScaleQty2() {
        return this.conScaleQty2;
    }

    /**
     * 设置：建设规模数量2。
     */
    public PmPrj setConScaleQty2(BigDecimal conScaleQty2) {
        if (this.conScaleQty2 == null && conScaleQty2 == null) {
            // 均为null，不做处理。
        } else if (this.conScaleQty2 != null && conScaleQty2 != null) {
            // 均非null，判定不等，再做处理：
            if (this.conScaleQty2.compareTo(conScaleQty2) != 0) {
                this.conScaleQty2 = conScaleQty2;
                if (!this.toUpdateCols.contains("CON_SCALE_QTY2")) {
                    this.toUpdateCols.add("CON_SCALE_QTY2");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.conScaleQty2 = conScaleQty2;
            if (!this.toUpdateCols.contains("CON_SCALE_QTY2")) {
                this.toUpdateCols.add("CON_SCALE_QTY2");
            }
        }
        return this;
    }

    /**
     * 建设规模单位。
     */
    private String conScaleUomId;

    /**
     * 获取：建设规模单位。
     */
    public String getConScaleUomId() {
        return this.conScaleUomId;
    }

    /**
     * 设置：建设规模单位。
     */
    public PmPrj setConScaleUomId(String conScaleUomId) {
        if (this.conScaleUomId == null && conScaleUomId == null) {
            // 均为null，不做处理。
        } else if (this.conScaleUomId != null && conScaleUomId != null) {
            // 均非null，判定不等，再做处理：
            if (this.conScaleUomId.compareTo(conScaleUomId) != 0) {
                this.conScaleUomId = conScaleUomId;
                if (!this.toUpdateCols.contains("CON_SCALE_UOM_ID")) {
                    this.toUpdateCols.add("CON_SCALE_UOM_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.conScaleUomId = conScaleUomId;
            if (!this.toUpdateCols.contains("CON_SCALE_UOM_ID")) {
                this.toUpdateCols.add("CON_SCALE_UOM_ID");
            }
        }
        return this;
    }

    /**
     * 建设年限。
     */
    private BigDecimal buildYears;

    /**
     * 获取：建设年限。
     */
    public BigDecimal getBuildYears() {
        return this.buildYears;
    }

    /**
     * 设置：建设年限。
     */
    public PmPrj setBuildYears(BigDecimal buildYears) {
        if (this.buildYears == null && buildYears == null) {
            // 均为null，不做处理。
        } else if (this.buildYears != null && buildYears != null) {
            // 均非null，判定不等，再做处理：
            if (this.buildYears.compareTo(buildYears) != 0) {
                this.buildYears = buildYears;
                if (!this.toUpdateCols.contains("BUILD_YEARS")) {
                    this.toUpdateCols.add("BUILD_YEARS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.buildYears = buildYears;
            if (!this.toUpdateCols.contains("BUILD_YEARS")) {
                this.toUpdateCols.add("BUILD_YEARS");
            }
        }
        return this;
    }

    /**
     * 项目概况。
     */
    private String prjSituation;

    /**
     * 获取：项目概况。
     */
    public String getPrjSituation() {
        return this.prjSituation;
    }

    /**
     * 设置：项目概况。
     */
    public PmPrj setPrjSituation(String prjSituation) {
        if (this.prjSituation == null && prjSituation == null) {
            // 均为null，不做处理。
        } else if (this.prjSituation != null && prjSituation != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjSituation.compareTo(prjSituation) != 0) {
                this.prjSituation = prjSituation;
                if (!this.toUpdateCols.contains("PRJ_SITUATION")) {
                    this.toUpdateCols.add("PRJ_SITUATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjSituation = prjSituation;
            if (!this.toUpdateCols.contains("PRJ_SITUATION")) {
                this.toUpdateCols.add("PRJ_SITUATION");
            }
        }
        return this;
    }

    /**
     * 投资来源。
     */
    private String investmentSourceId;

    /**
     * 获取：投资来源。
     */
    public String getInvestmentSourceId() {
        return this.investmentSourceId;
    }

    /**
     * 设置：投资来源。
     */
    public PmPrj setInvestmentSourceId(String investmentSourceId) {
        if (this.investmentSourceId == null && investmentSourceId == null) {
            // 均为null，不做处理。
        } else if (this.investmentSourceId != null && investmentSourceId != null) {
            // 均非null，判定不等，再做处理：
            if (this.investmentSourceId.compareTo(investmentSourceId) != 0) {
                this.investmentSourceId = investmentSourceId;
                if (!this.toUpdateCols.contains("INVESTMENT_SOURCE_ID")) {
                    this.toUpdateCols.add("INVESTMENT_SOURCE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.investmentSourceId = investmentSourceId;
            if (!this.toUpdateCols.contains("INVESTMENT_SOURCE_ID")) {
                this.toUpdateCols.add("INVESTMENT_SOURCE_ID");
            }
        }
        return this;
    }

    /**
     * EPC类型。
     */
    private String baseEpcId;

    /**
     * 获取：EPC类型。
     */
    public String getBaseEpcId() {
        return this.baseEpcId;
    }

    /**
     * 设置：EPC类型。
     */
    public PmPrj setBaseEpcId(String baseEpcId) {
        if (this.baseEpcId == null && baseEpcId == null) {
            // 均为null，不做处理。
        } else if (this.baseEpcId != null && baseEpcId != null) {
            // 均非null，判定不等，再做处理：
            if (this.baseEpcId.compareTo(baseEpcId) != 0) {
                this.baseEpcId = baseEpcId;
                if (!this.toUpdateCols.contains("BASE_EPC_ID")) {
                    this.toUpdateCols.add("BASE_EPC_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.baseEpcId = baseEpcId;
            if (!this.toUpdateCols.contains("BASE_EPC_ID")) {
                this.toUpdateCols.add("BASE_EPC_ID");
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
    public PmPrj setProjectPhaseId(String projectPhaseId) {
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
     * 项目状态。
     */
    private String projectStatus;

    /**
     * 获取：项目状态。
     */
    public String getProjectStatus() {
        return this.projectStatus;
    }

    /**
     * 设置：项目状态。
     */
    public PmPrj setProjectStatus(String projectStatus) {
        if (this.projectStatus == null && projectStatus == null) {
            // 均为null，不做处理。
        } else if (this.projectStatus != null && projectStatus != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectStatus.compareTo(projectStatus) != 0) {
                this.projectStatus = projectStatus;
                if (!this.toUpdateCols.contains("PROJECT_STATUS")) {
                    this.toUpdateCols.add("PROJECT_STATUS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectStatus = projectStatus;
            if (!this.toUpdateCols.contains("PROJECT_STATUS")) {
                this.toUpdateCols.add("PROJECT_STATUS");
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
    public PmPrj setTransitionPhaseId(String transitionPhaseId) {
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
     * 是否正式项目。
     */
    private Boolean izFormalPrj;

    /**
     * 获取：是否正式项目。
     */
    public Boolean getIzFormalPrj() {
        return this.izFormalPrj;
    }

    /**
     * 设置：是否正式项目。
     */
    public PmPrj setIzFormalPrj(Boolean izFormalPrj) {
        if (this.izFormalPrj == null && izFormalPrj == null) {
            // 均为null，不做处理。
        } else if (this.izFormalPrj != null && izFormalPrj != null) {
            // 均非null，判定不等，再做处理：
            if (this.izFormalPrj.compareTo(izFormalPrj) != 0) {
                this.izFormalPrj = izFormalPrj;
                if (!this.toUpdateCols.contains("IZ_FORMAL_PRJ")) {
                    this.toUpdateCols.add("IZ_FORMAL_PRJ");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.izFormalPrj = izFormalPrj;
            if (!this.toUpdateCols.contains("IZ_FORMAL_PRJ")) {
                this.toUpdateCols.add("IZ_FORMAL_PRJ");
            }
        }
        return this;
    }

    /**
     * 是否符合开工条件。
     */
    private Boolean izStartRequire;

    /**
     * 获取：是否符合开工条件。
     */
    public Boolean getIzStartRequire() {
        return this.izStartRequire;
    }

    /**
     * 设置：是否符合开工条件。
     */
    public PmPrj setIzStartRequire(Boolean izStartRequire) {
        if (this.izStartRequire == null && izStartRequire == null) {
            // 均为null，不做处理。
        } else if (this.izStartRequire != null && izStartRequire != null) {
            // 均非null，判定不等，再做处理：
            if (this.izStartRequire.compareTo(izStartRequire) != 0) {
                this.izStartRequire = izStartRequire;
                if (!this.toUpdateCols.contains("IZ_START_REQUIRE")) {
                    this.toUpdateCols.add("IZ_START_REQUIRE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.izStartRequire = izStartRequire;
            if (!this.toUpdateCols.contains("IZ_START_REQUIRE")) {
                this.toUpdateCols.add("IZ_START_REQUIRE");
            }
        }
        return this;
    }

    /**
     * 是否结束。
     */
    private Boolean izEnd;

    /**
     * 获取：是否结束。
     */
    public Boolean getIzEnd() {
        return this.izEnd;
    }

    /**
     * 设置：是否结束。
     */
    public PmPrj setIzEnd(Boolean izEnd) {
        if (this.izEnd == null && izEnd == null) {
            // 均为null，不做处理。
        } else if (this.izEnd != null && izEnd != null) {
            // 均非null，判定不等，再做处理：
            if (this.izEnd.compareTo(izEnd) != 0) {
                this.izEnd = izEnd;
                if (!this.toUpdateCols.contains("IZ_END")) {
                    this.toUpdateCols.add("IZ_END");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.izEnd = izEnd;
            if (!this.toUpdateCols.contains("IZ_END")) {
                this.toUpdateCols.add("IZ_END");
            }
        }
        return this;
    }

    /**
     * 立项申请。
     */
    private String pmPrjReqId;

    /**
     * 获取：立项申请。
     */
    public String getPmPrjReqId() {
        return this.pmPrjReqId;
    }

    /**
     * 设置：立项申请。
     */
    public PmPrj setPmPrjReqId(String pmPrjReqId) {
        if (this.pmPrjReqId == null && pmPrjReqId == null) {
            // 均为null，不做处理。
        } else if (this.pmPrjReqId != null && pmPrjReqId != null) {
            // 均非null，判定不等，再做处理：
            if (this.pmPrjReqId.compareTo(pmPrjReqId) != 0) {
                this.pmPrjReqId = pmPrjReqId;
                if (!this.toUpdateCols.contains("PM_PRJ_REQ_ID")) {
                    this.toUpdateCols.add("PM_PRJ_REQ_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pmPrjReqId = pmPrjReqId;
            if (!this.toUpdateCols.contains("PM_PRJ_REQ_ID")) {
                this.toUpdateCols.add("PM_PRJ_REQ_ID");
            }
        }
        return this;
    }

    /**
     * 立项批复文号。
     */
    private String prjReplyNo;

    /**
     * 获取：立项批复文号。
     */
    public String getPrjReplyNo() {
        return this.prjReplyNo;
    }

    /**
     * 设置：立项批复文号。
     */
    public PmPrj setPrjReplyNo(String prjReplyNo) {
        if (this.prjReplyNo == null && prjReplyNo == null) {
            // 均为null，不做处理。
        } else if (this.prjReplyNo != null && prjReplyNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjReplyNo.compareTo(prjReplyNo) != 0) {
                this.prjReplyNo = prjReplyNo;
                if (!this.toUpdateCols.contains("PRJ_REPLY_NO")) {
                    this.toUpdateCols.add("PRJ_REPLY_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjReplyNo = prjReplyNo;
            if (!this.toUpdateCols.contains("PRJ_REPLY_NO")) {
                this.toUpdateCols.add("PRJ_REPLY_NO");
            }
        }
        return this;
    }

    /**
     * 立项批复日期。
     */
    private LocalDate prjReplyDate;

    /**
     * 获取：立项批复日期。
     */
    public LocalDate getPrjReplyDate() {
        return this.prjReplyDate;
    }

    /**
     * 设置：立项批复日期。
     */
    public PmPrj setPrjReplyDate(LocalDate prjReplyDate) {
        if (this.prjReplyDate == null && prjReplyDate == null) {
            // 均为null，不做处理。
        } else if (this.prjReplyDate != null && prjReplyDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjReplyDate.compareTo(prjReplyDate) != 0) {
                this.prjReplyDate = prjReplyDate;
                if (!this.toUpdateCols.contains("PRJ_REPLY_DATE")) {
                    this.toUpdateCols.add("PRJ_REPLY_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjReplyDate = prjReplyDate;
            if (!this.toUpdateCols.contains("PRJ_REPLY_DATE")) {
                this.toUpdateCols.add("PRJ_REPLY_DATE");
            }
        }
        return this;
    }

    /**
     * 立项批复材料。
     */
    private String prjReplyFile;

    /**
     * 获取：立项批复材料。
     */
    public String getPrjReplyFile() {
        return this.prjReplyFile;
    }

    /**
     * 设置：立项批复材料。
     */
    public PmPrj setPrjReplyFile(String prjReplyFile) {
        if (this.prjReplyFile == null && prjReplyFile == null) {
            // 均为null，不做处理。
        } else if (this.prjReplyFile != null && prjReplyFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjReplyFile.compareTo(prjReplyFile) != 0) {
                this.prjReplyFile = prjReplyFile;
                if (!this.toUpdateCols.contains("PRJ_REPLY_FILE")) {
                    this.toUpdateCols.add("PRJ_REPLY_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjReplyFile = prjReplyFile;
            if (!this.toUpdateCols.contains("PRJ_REPLY_FILE")) {
                this.toUpdateCols.add("PRJ_REPLY_FILE");
            }
        }
        return this;
    }

    /**
     * 项目前期岗。
     */
    private String prjEarlyUserId;

    /**
     * 获取：项目前期岗。
     */
    public String getPrjEarlyUserId() {
        return this.prjEarlyUserId;
    }

    /**
     * 设置：项目前期岗。
     */
    public PmPrj setPrjEarlyUserId(String prjEarlyUserId) {
        if (this.prjEarlyUserId == null && prjEarlyUserId == null) {
            // 均为null，不做处理。
        } else if (this.prjEarlyUserId != null && prjEarlyUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjEarlyUserId.compareTo(prjEarlyUserId) != 0) {
                this.prjEarlyUserId = prjEarlyUserId;
                if (!this.toUpdateCols.contains("PRJ_EARLY_USER_ID")) {
                    this.toUpdateCols.add("PRJ_EARLY_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjEarlyUserId = prjEarlyUserId;
            if (!this.toUpdateCols.contains("PRJ_EARLY_USER_ID")) {
                this.toUpdateCols.add("PRJ_EARLY_USER_ID");
            }
        }
        return this;
    }

    /**
     * 项目设计岗。
     */
    private String prjDesignUserId;

    /**
     * 获取：项目设计岗。
     */
    public String getPrjDesignUserId() {
        return this.prjDesignUserId;
    }

    /**
     * 设置：项目设计岗。
     */
    public PmPrj setPrjDesignUserId(String prjDesignUserId) {
        if (this.prjDesignUserId == null && prjDesignUserId == null) {
            // 均为null，不做处理。
        } else if (this.prjDesignUserId != null && prjDesignUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjDesignUserId.compareTo(prjDesignUserId) != 0) {
                this.prjDesignUserId = prjDesignUserId;
                if (!this.toUpdateCols.contains("PRJ_DESIGN_USER_ID")) {
                    this.toUpdateCols.add("PRJ_DESIGN_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjDesignUserId = prjDesignUserId;
            if (!this.toUpdateCols.contains("PRJ_DESIGN_USER_ID")) {
                this.toUpdateCols.add("PRJ_DESIGN_USER_ID");
            }
        }
        return this;
    }

    /**
     * 项目成本岗。
     */
    private String prjCostUserId;

    /**
     * 获取：项目成本岗。
     */
    public String getPrjCostUserId() {
        return this.prjCostUserId;
    }

    /**
     * 设置：项目成本岗。
     */
    public PmPrj setPrjCostUserId(String prjCostUserId) {
        if (this.prjCostUserId == null && prjCostUserId == null) {
            // 均为null，不做处理。
        } else if (this.prjCostUserId != null && prjCostUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjCostUserId.compareTo(prjCostUserId) != 0) {
                this.prjCostUserId = prjCostUserId;
                if (!this.toUpdateCols.contains("PRJ_COST_USER_ID")) {
                    this.toUpdateCols.add("PRJ_COST_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjCostUserId = prjCostUserId;
            if (!this.toUpdateCols.contains("PRJ_COST_USER_ID")) {
                this.toUpdateCols.add("PRJ_COST_USER_ID");
            }
        }
        return this;
    }

    /**
     * 进入省库。
     */
    private Boolean inProvinceRep;

    /**
     * 获取：进入省库。
     */
    public Boolean getInProvinceRep() {
        return this.inProvinceRep;
    }

    /**
     * 设置：进入省库。
     */
    public PmPrj setInProvinceRep(Boolean inProvinceRep) {
        if (this.inProvinceRep == null && inProvinceRep == null) {
            // 均为null，不做处理。
        } else if (this.inProvinceRep != null && inProvinceRep != null) {
            // 均非null，判定不等，再做处理：
            if (this.inProvinceRep.compareTo(inProvinceRep) != 0) {
                this.inProvinceRep = inProvinceRep;
                if (!this.toUpdateCols.contains("IN_PROVINCE_REP")) {
                    this.toUpdateCols.add("IN_PROVINCE_REP");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.inProvinceRep = inProvinceRep;
            if (!this.toUpdateCols.contains("IN_PROVINCE_REP")) {
                this.toUpdateCols.add("IN_PROVINCE_REP");
            }
        }
        return this;
    }

    /**
     * 进入国库。
     */
    private Boolean inCountryRep;

    /**
     * 获取：进入国库。
     */
    public Boolean getInCountryRep() {
        return this.inCountryRep;
    }

    /**
     * 设置：进入国库。
     */
    public PmPrj setInCountryRep(Boolean inCountryRep) {
        if (this.inCountryRep == null && inCountryRep == null) {
            // 均为null，不做处理。
        } else if (this.inCountryRep != null && inCountryRep != null) {
            // 均非null，判定不等，再做处理：
            if (this.inCountryRep.compareTo(inCountryRep) != 0) {
                this.inCountryRep = inCountryRep;
                if (!this.toUpdateCols.contains("IN_COUNTRY_REP")) {
                    this.toUpdateCols.add("IN_COUNTRY_REP");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.inCountryRep = inCountryRep;
            if (!this.toUpdateCols.contains("IN_COUNTRY_REP")) {
                this.toUpdateCols.add("IN_COUNTRY_REP");
            }
        }
        return this;
    }

    /**
     * 建设单位。
     */
    private String builderUnit;

    /**
     * 获取：建设单位。
     */
    public String getBuilderUnit() {
        return this.builderUnit;
    }

    /**
     * 设置：建设单位。
     */
    public PmPrj setBuilderUnit(String builderUnit) {
        if (this.builderUnit == null && builderUnit == null) {
            // 均为null，不做处理。
        } else if (this.builderUnit != null && builderUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.builderUnit.compareTo(builderUnit) != 0) {
                this.builderUnit = builderUnit;
                if (!this.toUpdateCols.contains("BUILDER_UNIT")) {
                    this.toUpdateCols.add("BUILDER_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.builderUnit = builderUnit;
            if (!this.toUpdateCols.contains("BUILDER_UNIT")) {
                this.toUpdateCols.add("BUILDER_UNIT");
            }
        }
        return this;
    }

    /**
     * 勘察单位。
     */
    private String surveyorUnit;

    /**
     * 获取：勘察单位。
     */
    public String getSurveyorUnit() {
        return this.surveyorUnit;
    }

    /**
     * 设置：勘察单位。
     */
    public PmPrj setSurveyorUnit(String surveyorUnit) {
        if (this.surveyorUnit == null && surveyorUnit == null) {
            // 均为null，不做处理。
        } else if (this.surveyorUnit != null && surveyorUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.surveyorUnit.compareTo(surveyorUnit) != 0) {
                this.surveyorUnit = surveyorUnit;
                if (!this.toUpdateCols.contains("SURVEYOR_UNIT")) {
                    this.toUpdateCols.add("SURVEYOR_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.surveyorUnit = surveyorUnit;
            if (!this.toUpdateCols.contains("SURVEYOR_UNIT")) {
                this.toUpdateCols.add("SURVEYOR_UNIT");
            }
        }
        return this;
    }

    /**
     * 项目编号。
     */
    private String prjCode;

    /**
     * 获取：项目编号。
     */
    public String getPrjCode() {
        return this.prjCode;
    }

    /**
     * 设置：项目编号。
     */
    public PmPrj setPrjCode(String prjCode) {
        if (this.prjCode == null && prjCode == null) {
            // 均为null，不做处理。
        } else if (this.prjCode != null && prjCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjCode.compareTo(prjCode) != 0) {
                this.prjCode = prjCode;
                if (!this.toUpdateCols.contains("PRJ_CODE")) {
                    this.toUpdateCols.add("PRJ_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjCode = prjCode;
            if (!this.toUpdateCols.contains("PRJ_CODE")) {
                this.toUpdateCols.add("PRJ_CODE");
            }
        }
        return this;
    }

    /**
     * 设计单位。
     */
    private String designerUnit;

    /**
     * 获取：设计单位。
     */
    public String getDesignerUnit() {
        return this.designerUnit;
    }

    /**
     * 设置：设计单位。
     */
    public PmPrj setDesignerUnit(String designerUnit) {
        if (this.designerUnit == null && designerUnit == null) {
            // 均为null，不做处理。
        } else if (this.designerUnit != null && designerUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.designerUnit.compareTo(designerUnit) != 0) {
                this.designerUnit = designerUnit;
                if (!this.toUpdateCols.contains("DESIGNER_UNIT")) {
                    this.toUpdateCols.add("DESIGNER_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designerUnit = designerUnit;
            if (!this.toUpdateCols.contains("DESIGNER_UNIT")) {
                this.toUpdateCols.add("DESIGNER_UNIT");
            }
        }
        return this;
    }

    /**
     * 施工单位。
     */
    private String constructorUnit;

    /**
     * 获取：施工单位。
     */
    public String getConstructorUnit() {
        return this.constructorUnit;
    }

    /**
     * 设置：施工单位。
     */
    public PmPrj setConstructorUnit(String constructorUnit) {
        if (this.constructorUnit == null && constructorUnit == null) {
            // 均为null，不做处理。
        } else if (this.constructorUnit != null && constructorUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.constructorUnit.compareTo(constructorUnit) != 0) {
                this.constructorUnit = constructorUnit;
                if (!this.toUpdateCols.contains("CONSTRUCTOR_UNIT")) {
                    this.toUpdateCols.add("CONSTRUCTOR_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.constructorUnit = constructorUnit;
            if (!this.toUpdateCols.contains("CONSTRUCTOR_UNIT")) {
                this.toUpdateCols.add("CONSTRUCTOR_UNIT");
            }
        }
        return this;
    }

    /**
     * 监理单位。
     */
    private String supervisorUnit;

    /**
     * 获取：监理单位。
     */
    public String getSupervisorUnit() {
        return this.supervisorUnit;
    }

    /**
     * 设置：监理单位。
     */
    public PmPrj setSupervisorUnit(String supervisorUnit) {
        if (this.supervisorUnit == null && supervisorUnit == null) {
            // 均为null，不做处理。
        } else if (this.supervisorUnit != null && supervisorUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.supervisorUnit.compareTo(supervisorUnit) != 0) {
                this.supervisorUnit = supervisorUnit;
                if (!this.toUpdateCols.contains("SUPERVISOR_UNIT")) {
                    this.toUpdateCols.add("SUPERVISOR_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.supervisorUnit = supervisorUnit;
            if (!this.toUpdateCols.contains("SUPERVISOR_UNIT")) {
                this.toUpdateCols.add("SUPERVISOR_UNIT");
            }
        }
        return this;
    }

    /**
     * 检测单位。
     */
    private String checkerUnit;

    /**
     * 获取：检测单位。
     */
    public String getCheckerUnit() {
        return this.checkerUnit;
    }

    /**
     * 设置：检测单位。
     */
    public PmPrj setCheckerUnit(String checkerUnit) {
        if (this.checkerUnit == null && checkerUnit == null) {
            // 均为null，不做处理。
        } else if (this.checkerUnit != null && checkerUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.checkerUnit.compareTo(checkerUnit) != 0) {
                this.checkerUnit = checkerUnit;
                if (!this.toUpdateCols.contains("CHECKER_UNIT")) {
                    this.toUpdateCols.add("CHECKER_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.checkerUnit = checkerUnit;
            if (!this.toUpdateCols.contains("CHECKER_UNIT")) {
                this.toUpdateCols.add("CHECKER_UNIT");
            }
        }
        return this;
    }

    /**
     * 全过程造价单位。
     */
    private String consulterUnit;

    /**
     * 获取：全过程造价单位。
     */
    public String getConsulterUnit() {
        return this.consulterUnit;
    }

    /**
     * 设置：全过程造价单位。
     */
    public PmPrj setConsulterUnit(String consulterUnit) {
        if (this.consulterUnit == null && consulterUnit == null) {
            // 均为null，不做处理。
        } else if (this.consulterUnit != null && consulterUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.consulterUnit.compareTo(consulterUnit) != 0) {
                this.consulterUnit = consulterUnit;
                if (!this.toUpdateCols.contains("CONSULTER_UNIT")) {
                    this.toUpdateCols.add("CONSULTER_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.consulterUnit = consulterUnit;
            if (!this.toUpdateCols.contains("CONSULTER_UNIT")) {
                this.toUpdateCols.add("CONSULTER_UNIT");
            }
        }
        return this;
    }

    /**
     * 项目图片。
     */
    private String prjImg;

    /**
     * 获取：项目图片。
     */
    public String getPrjImg() {
        return this.prjImg;
    }

    /**
     * 设置：项目图片。
     */
    public PmPrj setPrjImg(String prjImg) {
        if (this.prjImg == null && prjImg == null) {
            // 均为null，不做处理。
        } else if (this.prjImg != null && prjImg != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjImg.compareTo(prjImg) != 0) {
                this.prjImg = prjImg;
                if (!this.toUpdateCols.contains("PRJ_IMG")) {
                    this.toUpdateCols.add("PRJ_IMG");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjImg = prjImg;
            if (!this.toUpdateCols.contains("PRJ_IMG")) {
                this.toUpdateCols.add("PRJ_IMG");
            }
        }
        return this;
    }

    /**
     * 建筑面积（平方）。
     */
    private BigDecimal buildingArea;

    /**
     * 获取：建筑面积（平方）。
     */
    public BigDecimal getBuildingArea() {
        return this.buildingArea;
    }

    /**
     * 设置：建筑面积（平方）。
     */
    public PmPrj setBuildingArea(BigDecimal buildingArea) {
        if (this.buildingArea == null && buildingArea == null) {
            // 均为null，不做处理。
        } else if (this.buildingArea != null && buildingArea != null) {
            // 均非null，判定不等，再做处理：
            if (this.buildingArea.compareTo(buildingArea) != 0) {
                this.buildingArea = buildingArea;
                if (!this.toUpdateCols.contains("BUILDING_AREA")) {
                    this.toUpdateCols.add("BUILDING_AREA");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.buildingArea = buildingArea;
            if (!this.toUpdateCols.contains("BUILDING_AREA")) {
                this.toUpdateCols.add("BUILDING_AREA");
            }
        }
        return this;
    }

    /**
     * 责任人。
     */
    private String responsibleId;

    /**
     * 获取：责任人。
     */
    public String getResponsibleId() {
        return this.responsibleId;
    }

    /**
     * 设置：责任人。
     */
    public PmPrj setResponsibleId(String responsibleId) {
        if (this.responsibleId == null && responsibleId == null) {
            // 均为null，不做处理。
        } else if (this.responsibleId != null && responsibleId != null) {
            // 均非null，判定不等，再做处理：
            if (this.responsibleId.compareTo(responsibleId) != 0) {
                this.responsibleId = responsibleId;
                if (!this.toUpdateCols.contains("RESPONSIBLE_ID")) {
                    this.toUpdateCols.add("RESPONSIBLE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.responsibleId = responsibleId;
            if (!this.toUpdateCols.contains("RESPONSIBLE_ID")) {
                this.toUpdateCols.add("RESPONSIBLE_ID");
            }
        }
        return this;
    }

    /**
     * 批复日期。
     */
    private LocalDate replyDate;

    /**
     * 获取：批复日期。
     */
    public LocalDate getReplyDate() {
        return this.replyDate;
    }

    /**
     * 设置：批复日期。
     */
    public PmPrj setReplyDate(LocalDate replyDate) {
        if (this.replyDate == null && replyDate == null) {
            // 均为null，不做处理。
        } else if (this.replyDate != null && replyDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.replyDate.compareTo(replyDate) != 0) {
                this.replyDate = replyDate;
                if (!this.toUpdateCols.contains("REPLY_DATE")) {
                    this.toUpdateCols.add("REPLY_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.replyDate = replyDate;
            if (!this.toUpdateCols.contains("REPLY_DATE")) {
                this.toUpdateCols.add("REPLY_DATE");
            }
        }
        return this;
    }

    /**
     * 批复文号。
     */
    private String replyNo;

    /**
     * 获取：批复文号。
     */
    public String getReplyNo() {
        return this.replyNo;
    }

    /**
     * 设置：批复文号。
     */
    public PmPrj setReplyNo(String replyNo) {
        if (this.replyNo == null && replyNo == null) {
            // 均为null，不做处理。
        } else if (this.replyNo != null && replyNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.replyNo.compareTo(replyNo) != 0) {
                this.replyNo = replyNo;
                if (!this.toUpdateCols.contains("REPLY_NO")) {
                    this.toUpdateCols.add("REPLY_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.replyNo = replyNo;
            if (!this.toUpdateCols.contains("REPLY_NO")) {
                this.toUpdateCols.add("REPLY_NO");
            }
        }
        return this;
    }

    /**
     * 项目来源类型。
     */
    private String projectSourceTypeId;

    /**
     * 获取：项目来源类型。
     */
    public String getProjectSourceTypeId() {
        return this.projectSourceTypeId;
    }

    /**
     * 设置：项目来源类型。
     */
    public PmPrj setProjectSourceTypeId(String projectSourceTypeId) {
        if (this.projectSourceTypeId == null && projectSourceTypeId == null) {
            // 均为null，不做处理。
        } else if (this.projectSourceTypeId != null && projectSourceTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectSourceTypeId.compareTo(projectSourceTypeId) != 0) {
                this.projectSourceTypeId = projectSourceTypeId;
                if (!this.toUpdateCols.contains("PROJECT_SOURCE_TYPE_ID")) {
                    this.toUpdateCols.add("PROJECT_SOURCE_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectSourceTypeId = projectSourceTypeId;
            if (!this.toUpdateCols.contains("PROJECT_SOURCE_TYPE_ID")) {
                this.toUpdateCols.add("PROJECT_SOURCE_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 概算金额（万）。
     */
    private BigDecimal estimateAmt;

    /**
     * 获取：概算金额（万）。
     */
    public BigDecimal getEstimateAmt() {
        return this.estimateAmt;
    }

    /**
     * 设置：概算金额（万）。
     */
    public PmPrj setEstimateAmt(BigDecimal estimateAmt) {
        if (this.estimateAmt == null && estimateAmt == null) {
            // 均为null，不做处理。
        } else if (this.estimateAmt != null && estimateAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.estimateAmt.compareTo(estimateAmt) != 0) {
                this.estimateAmt = estimateAmt;
                if (!this.toUpdateCols.contains("ESTIMATE_AMT")) {
                    this.toUpdateCols.add("ESTIMATE_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.estimateAmt = estimateAmt;
            if (!this.toUpdateCols.contains("ESTIMATE_AMT")) {
                this.toUpdateCols.add("ESTIMATE_AMT");
            }
        }
        return this;
    }

    /**
     * 预算金额。
     */
    private BigDecimal budgetAmt;

    /**
     * 获取：预算金额。
     */
    public BigDecimal getBudgetAmt() {
        return this.budgetAmt;
    }

    /**
     * 设置：预算金额。
     */
    public PmPrj setBudgetAmt(BigDecimal budgetAmt) {
        if (this.budgetAmt == null && budgetAmt == null) {
            // 均为null，不做处理。
        } else if (this.budgetAmt != null && budgetAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.budgetAmt.compareTo(budgetAmt) != 0) {
                this.budgetAmt = budgetAmt;
                if (!this.toUpdateCols.contains("BUDGET_AMT")) {
                    this.toUpdateCols.add("BUDGET_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.budgetAmt = budgetAmt;
            if (!this.toUpdateCols.contains("BUDGET_AMT")) {
                this.toUpdateCols.add("BUDGET_AMT");
            }
        }
        return this;
    }

    /**
     * 结算金额（万）。
     */
    private BigDecimal settlementAmt;

    /**
     * 获取：结算金额（万）。
     */
    public BigDecimal getSettlementAmt() {
        return this.settlementAmt;
    }

    /**
     * 设置：结算金额（万）。
     */
    public PmPrj setSettlementAmt(BigDecimal settlementAmt) {
        if (this.settlementAmt == null && settlementAmt == null) {
            // 均为null，不做处理。
        } else if (this.settlementAmt != null && settlementAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.settlementAmt.compareTo(settlementAmt) != 0) {
                this.settlementAmt = settlementAmt;
                if (!this.toUpdateCols.contains("SETTLEMENT_AMT")) {
                    this.toUpdateCols.add("SETTLEMENT_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.settlementAmt = settlementAmt;
            if (!this.toUpdateCols.contains("SETTLEMENT_AMT")) {
                this.toUpdateCols.add("SETTLEMENT_AMT");
            }
        }
        return this;
    }

    /**
     * 项目建议书实际完成日期。
     */
    private LocalDate projectProposalActualDate;

    /**
     * 获取：项目建议书实际完成日期。
     */
    public LocalDate getProjectProposalActualDate() {
        return this.projectProposalActualDate;
    }

    /**
     * 设置：项目建议书实际完成日期。
     */
    public PmPrj setProjectProposalActualDate(LocalDate projectProposalActualDate) {
        if (this.projectProposalActualDate == null && projectProposalActualDate == null) {
            // 均为null，不做处理。
        } else if (this.projectProposalActualDate != null && projectProposalActualDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectProposalActualDate.compareTo(projectProposalActualDate) != 0) {
                this.projectProposalActualDate = projectProposalActualDate;
                if (!this.toUpdateCols.contains("PROJECT_PROPOSAL_ACTUAL_DATE")) {
                    this.toUpdateCols.add("PROJECT_PROPOSAL_ACTUAL_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectProposalActualDate = projectProposalActualDate;
            if (!this.toUpdateCols.contains("PROJECT_PROPOSAL_ACTUAL_DATE")) {
                this.toUpdateCols.add("PROJECT_PROPOSAL_ACTUAL_DATE");
            }
        }
        return this;
    }

    /**
     * 编制人。
     */
    private String author;

    /**
     * 获取：编制人。
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * 设置：编制人。
     */
    public PmPrj setAuthor(String author) {
        if (this.author == null && author == null) {
            // 均为null，不做处理。
        } else if (this.author != null && author != null) {
            // 均非null，判定不等，再做处理：
            if (this.author.compareTo(author) != 0) {
                this.author = author;
                if (!this.toUpdateCols.contains("AUTHOR")) {
                    this.toUpdateCols.add("AUTHOR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.author = author;
            if (!this.toUpdateCols.contains("AUTHOR")) {
                this.toUpdateCols.add("AUTHOR");
            }
        }
        return this;
    }

    /**
     * 盖章的立项申请书。
     */
    private String stampedPrjReqFile;

    /**
     * 获取：盖章的立项申请书。
     */
    public String getStampedPrjReqFile() {
        return this.stampedPrjReqFile;
    }

    /**
     * 设置：盖章的立项申请书。
     */
    public PmPrj setStampedPrjReqFile(String stampedPrjReqFile) {
        if (this.stampedPrjReqFile == null && stampedPrjReqFile == null) {
            // 均为null，不做处理。
        } else if (this.stampedPrjReqFile != null && stampedPrjReqFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.stampedPrjReqFile.compareTo(stampedPrjReqFile) != 0) {
                this.stampedPrjReqFile = stampedPrjReqFile;
                if (!this.toUpdateCols.contains("STAMPED_PRJ_REQ_FILE")) {
                    this.toUpdateCols.add("STAMPED_PRJ_REQ_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.stampedPrjReqFile = stampedPrjReqFile;
            if (!this.toUpdateCols.contains("STAMPED_PRJ_REQ_FILE")) {
                this.toUpdateCols.add("STAMPED_PRJ_REQ_FILE");
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
    public PmPrj setCpmsUuid(String cpmsUuid) {
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
    public PmPrj setCpmsId(String cpmsId) {
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

    /**
     * CPMS建设地点。
     */
    private String cpmsConstructionSite;

    /**
     * 获取：CPMS建设地点。
     */
    public String getCpmsConstructionSite() {
        return this.cpmsConstructionSite;
    }

    /**
     * 设置：CPMS建设地点。
     */
    public PmPrj setCpmsConstructionSite(String cpmsConstructionSite) {
        if (this.cpmsConstructionSite == null && cpmsConstructionSite == null) {
            // 均为null，不做处理。
        } else if (this.cpmsConstructionSite != null && cpmsConstructionSite != null) {
            // 均非null，判定不等，再做处理：
            if (this.cpmsConstructionSite.compareTo(cpmsConstructionSite) != 0) {
                this.cpmsConstructionSite = cpmsConstructionSite;
                if (!this.toUpdateCols.contains("CPMS_CONSTRUCTION_SITE")) {
                    this.toUpdateCols.add("CPMS_CONSTRUCTION_SITE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cpmsConstructionSite = cpmsConstructionSite;
            if (!this.toUpdateCols.contains("CPMS_CONSTRUCTION_SITE")) {
                this.toUpdateCols.add("CPMS_CONSTRUCTION_SITE");
            }
        }
        return this;
    }

    /**
     * 匡算总投资(万)。
     */
    private BigDecimal estimatedTotalInvest;

    /**
     * 获取：匡算总投资(万)。
     */
    public BigDecimal getEstimatedTotalInvest() {
        return this.estimatedTotalInvest;
    }

    /**
     * 设置：匡算总投资(万)。
     */
    public PmPrj setEstimatedTotalInvest(BigDecimal estimatedTotalInvest) {
        if (this.estimatedTotalInvest == null && estimatedTotalInvest == null) {
            // 均为null，不做处理。
        } else if (this.estimatedTotalInvest != null && estimatedTotalInvest != null) {
            // 均非null，判定不等，再做处理：
            if (this.estimatedTotalInvest.compareTo(estimatedTotalInvest) != 0) {
                this.estimatedTotalInvest = estimatedTotalInvest;
                if (!this.toUpdateCols.contains("ESTIMATED_TOTAL_INVEST")) {
                    this.toUpdateCols.add("ESTIMATED_TOTAL_INVEST");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.estimatedTotalInvest = estimatedTotalInvest;
            if (!this.toUpdateCols.contains("ESTIMATED_TOTAL_INVEST")) {
                this.toUpdateCols.add("ESTIMATED_TOTAL_INVEST");
            }
        }
        return this;
    }

    /**
     * 工程费用（万）。
     */
    private BigDecimal projectAmt;

    /**
     * 获取：工程费用（万）。
     */
    public BigDecimal getProjectAmt() {
        return this.projectAmt;
    }

    /**
     * 设置：工程费用（万）。
     */
    public PmPrj setProjectAmt(BigDecimal projectAmt) {
        if (this.projectAmt == null && projectAmt == null) {
            // 均为null，不做处理。
        } else if (this.projectAmt != null && projectAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectAmt.compareTo(projectAmt) != 0) {
                this.projectAmt = projectAmt;
                if (!this.toUpdateCols.contains("PROJECT_AMT")) {
                    this.toUpdateCols.add("PROJECT_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectAmt = projectAmt;
            if (!this.toUpdateCols.contains("PROJECT_AMT")) {
                this.toUpdateCols.add("PROJECT_AMT");
            }
        }
        return this;
    }

    /**
     * 建安工程费(万)。
     */
    private BigDecimal constructPrjAmt;

    /**
     * 获取：建安工程费(万)。
     */
    public BigDecimal getConstructPrjAmt() {
        return this.constructPrjAmt;
    }

    /**
     * 设置：建安工程费(万)。
     */
    public PmPrj setConstructPrjAmt(BigDecimal constructPrjAmt) {
        if (this.constructPrjAmt == null && constructPrjAmt == null) {
            // 均为null，不做处理。
        } else if (this.constructPrjAmt != null && constructPrjAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.constructPrjAmt.compareTo(constructPrjAmt) != 0) {
                this.constructPrjAmt = constructPrjAmt;
                if (!this.toUpdateCols.contains("CONSTRUCT_PRJ_AMT")) {
                    this.toUpdateCols.add("CONSTRUCT_PRJ_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.constructPrjAmt = constructPrjAmt;
            if (!this.toUpdateCols.contains("CONSTRUCT_PRJ_AMT")) {
                this.toUpdateCols.add("CONSTRUCT_PRJ_AMT");
            }
        }
        return this;
    }

    /**
     * 设备采购费(万)。
     */
    private BigDecimal equipBuyAmt;

    /**
     * 获取：设备采购费(万)。
     */
    public BigDecimal getEquipBuyAmt() {
        return this.equipBuyAmt;
    }

    /**
     * 设置：设备采购费(万)。
     */
    public PmPrj setEquipBuyAmt(BigDecimal equipBuyAmt) {
        if (this.equipBuyAmt == null && equipBuyAmt == null) {
            // 均为null，不做处理。
        } else if (this.equipBuyAmt != null && equipBuyAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.equipBuyAmt.compareTo(equipBuyAmt) != 0) {
                this.equipBuyAmt = equipBuyAmt;
                if (!this.toUpdateCols.contains("EQUIP_BUY_AMT")) {
                    this.toUpdateCols.add("EQUIP_BUY_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.equipBuyAmt = equipBuyAmt;
            if (!this.toUpdateCols.contains("EQUIP_BUY_AMT")) {
                this.toUpdateCols.add("EQUIP_BUY_AMT");
            }
        }
        return this;
    }

    /**
     * 科研设备费(万)。
     */
    private BigDecimal equipmentCost;

    /**
     * 获取：科研设备费(万)。
     */
    public BigDecimal getEquipmentCost() {
        return this.equipmentCost;
    }

    /**
     * 设置：科研设备费(万)。
     */
    public PmPrj setEquipmentCost(BigDecimal equipmentCost) {
        if (this.equipmentCost == null && equipmentCost == null) {
            // 均为null，不做处理。
        } else if (this.equipmentCost != null && equipmentCost != null) {
            // 均非null，判定不等，再做处理：
            if (this.equipmentCost.compareTo(equipmentCost) != 0) {
                this.equipmentCost = equipmentCost;
                if (!this.toUpdateCols.contains("EQUIPMENT_COST")) {
                    this.toUpdateCols.add("EQUIPMENT_COST");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.equipmentCost = equipmentCost;
            if (!this.toUpdateCols.contains("EQUIPMENT_COST")) {
                this.toUpdateCols.add("EQUIPMENT_COST");
            }
        }
        return this;
    }

    /**
     * 工程其他费用(万)。
     */
    private BigDecimal projectOtherAmt;

    /**
     * 获取：工程其他费用(万)。
     */
    public BigDecimal getProjectOtherAmt() {
        return this.projectOtherAmt;
    }

    /**
     * 设置：工程其他费用(万)。
     */
    public PmPrj setProjectOtherAmt(BigDecimal projectOtherAmt) {
        if (this.projectOtherAmt == null && projectOtherAmt == null) {
            // 均为null，不做处理。
        } else if (this.projectOtherAmt != null && projectOtherAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectOtherAmt.compareTo(projectOtherAmt) != 0) {
                this.projectOtherAmt = projectOtherAmt;
                if (!this.toUpdateCols.contains("PROJECT_OTHER_AMT")) {
                    this.toUpdateCols.add("PROJECT_OTHER_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectOtherAmt = projectOtherAmt;
            if (!this.toUpdateCols.contains("PROJECT_OTHER_AMT")) {
                this.toUpdateCols.add("PROJECT_OTHER_AMT");
            }
        }
        return this;
    }

    /**
     * 土地征拆费用(万)。
     */
    private BigDecimal landBuyAmt;

    /**
     * 获取：土地征拆费用(万)。
     */
    public BigDecimal getLandBuyAmt() {
        return this.landBuyAmt;
    }

    /**
     * 设置：土地征拆费用(万)。
     */
    public PmPrj setLandBuyAmt(BigDecimal landBuyAmt) {
        if (this.landBuyAmt == null && landBuyAmt == null) {
            // 均为null，不做处理。
        } else if (this.landBuyAmt != null && landBuyAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.landBuyAmt.compareTo(landBuyAmt) != 0) {
                this.landBuyAmt = landBuyAmt;
                if (!this.toUpdateCols.contains("LAND_BUY_AMT")) {
                    this.toUpdateCols.add("LAND_BUY_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.landBuyAmt = landBuyAmt;
            if (!this.toUpdateCols.contains("LAND_BUY_AMT")) {
                this.toUpdateCols.add("LAND_BUY_AMT");
            }
        }
        return this;
    }

    /**
     * 预备费(万)。
     */
    private BigDecimal prepareAmt;

    /**
     * 获取：预备费(万)。
     */
    public BigDecimal getPrepareAmt() {
        return this.prepareAmt;
    }

    /**
     * 设置：预备费(万)。
     */
    public PmPrj setPrepareAmt(BigDecimal prepareAmt) {
        if (this.prepareAmt == null && prepareAmt == null) {
            // 均为null，不做处理。
        } else if (this.prepareAmt != null && prepareAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.prepareAmt.compareTo(prepareAmt) != 0) {
                this.prepareAmt = prepareAmt;
                if (!this.toUpdateCols.contains("PREPARE_AMT")) {
                    this.toUpdateCols.add("PREPARE_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prepareAmt = prepareAmt;
            if (!this.toUpdateCols.contains("PREPARE_AMT")) {
                this.toUpdateCols.add("PREPARE_AMT");
            }
        }
        return this;
    }

    /**
     * 建设期利息（万）。
     */
    private BigDecimal constructPeriodInterest;

    /**
     * 获取：建设期利息（万）。
     */
    public BigDecimal getConstructPeriodInterest() {
        return this.constructPeriodInterest;
    }

    /**
     * 设置：建设期利息（万）。
     */
    public PmPrj setConstructPeriodInterest(BigDecimal constructPeriodInterest) {
        if (this.constructPeriodInterest == null && constructPeriodInterest == null) {
            // 均为null，不做处理。
        } else if (this.constructPeriodInterest != null && constructPeriodInterest != null) {
            // 均非null，判定不等，再做处理：
            if (this.constructPeriodInterest.compareTo(constructPeriodInterest) != 0) {
                this.constructPeriodInterest = constructPeriodInterest;
                if (!this.toUpdateCols.contains("CONSTRUCT_PERIOD_INTEREST")) {
                    this.toUpdateCols.add("CONSTRUCT_PERIOD_INTEREST");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.constructPeriodInterest = constructPeriodInterest;
            if (!this.toUpdateCols.contains("CONSTRUCT_PERIOD_INTEREST")) {
                this.toUpdateCols.add("CONSTRUCT_PERIOD_INTEREST");
            }
        }
        return this;
    }

    /**
     * 投资测算优先级。
     */
    private String investPriority;

    /**
     * 获取：投资测算优先级。
     */
    public String getInvestPriority() {
        return this.investPriority;
    }

    /**
     * 设置：投资测算优先级。
     */
    public PmPrj setInvestPriority(String investPriority) {
        if (this.investPriority == null && investPriority == null) {
            // 均为null，不做处理。
        } else if (this.investPriority != null && investPriority != null) {
            // 均非null，判定不等，再做处理：
            if (this.investPriority.compareTo(investPriority) != 0) {
                this.investPriority = investPriority;
                if (!this.toUpdateCols.contains("INVEST_PRIORITY")) {
                    this.toUpdateCols.add("INVEST_PRIORITY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.investPriority = investPriority;
            if (!this.toUpdateCols.contains("INVEST_PRIORITY")) {
                this.toUpdateCols.add("INVEST_PRIORITY");
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
    public static PmPrj newData() {
        PmPrj obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmPrj insertData() {
        PmPrj obj = modelHelper.insertData();
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
    public static PmPrj selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmPrj obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static PmPrj selectById(String id) {
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
    public static List<PmPrj> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmPrj> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmPrj> selectByIds(List<String> ids) {
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
    public static List<PmPrj> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmPrj> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmPrj> selectByWhere(Where where) {
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
    public static PmPrj selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmPrj> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用PmPrj.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmptyList(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static PmPrj selectOneByWhere(Where where) {
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
    public static void copyCols(PmPrj fromModel, PmPrj toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(PmPrj fromModel, PmPrj toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}
