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
 * 人防施工报建。
 */
public class PmDefenseBuildReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmDefenseBuildReq> modelHelper = new ModelHelper<>("PM_DEFENSE_BUILD_REQ", new PmDefenseBuildReq());

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

    public static final String ENT_CODE = "PM_DEFENSE_BUILD_REQ";
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
         * 最后修改日期时间。
         */
        public static final String LAST_MODI_DT = "LAST_MODI_DT";
        /**
         * 最后修改用户。
         */
        public static final String LAST_MODI_USER_ID = "LAST_MODI_USER_ID";
        /**
         * 代码。
         */
        public static final String CODE = "CODE";
        /**
         * 名称。
         */
        public static final String NAME = "NAME";
        /**
         * 锁定流程实例。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 项目编号。
         */
        public static final String PRJ_CODE = "PRJ_CODE";
        /**
         * 业主单位。
         */
        public static final String CUSTOMER_UNIT = "CUSTOMER_UNIT";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 项目管理模式。
         */
        public static final String PRJ_MANAGE_MODE_ID = "PRJ_MANAGE_MODE_ID";
        /**
         * 建设地点。
         */
        public static final String BASE_LOCATION_ID = "BASE_LOCATION_ID";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
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
         * 附件。
         */
        public static final String ATT_FILE_GROUP_ID = "ATT_FILE_GROUP_ID";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 计划完成日期。
         */
        public static final String COMPL_PLAN_DATE = "COMPL_PLAN_DATE";
        /**
         * 实际完成日期。
         */
        public static final String ACTUAL_COMPL_DATE = "ACTUAL_COMPL_DATE";
        /**
         * 对方负责人。
         */
        public static final String OTHER_RESPONSOR = "OTHER_RESPONSOR";
        /**
         * 评审组织单位。
         */
        public static final String REVIEW_ORGANIZATION_UNIT = "REVIEW_ORGANIZATION_UNIT";
        /**
         * 评审单位联系方式。
         */
        public static final String REVIEW_UNIT_PHONE = "REVIEW_UNIT_PHONE";
        /**
         * 审批附件1。
         */
        public static final String APPROVE_FILE_ID_ONE = "APPROVE_FILE_ID_ONE";
        /**
         * 审批附件2。
         */
        public static final String APPROVE_FILE_ID_TWO = "APPROVE_FILE_ID_TWO";
        /**
         * 评审及修编说明。
         */
        public static final String REVIEW_AND_REVISION_REMARK = "REVIEW_AND_REVISION_REMARK";
        /**
         * 日期1。
         */
        public static final String DATE_ONE = "DATE_ONE";
        /**
         * 申请时间。
         */
        public static final String APPLY_TIME = "APPLY_TIME";
        /**
         * 申请人。
         */
        public static final String APPLICANT = "APPLICANT";
        /**
         * 审批附件3。
         */
        public static final String APPROVE_FILE_ID_THREE = "APPROVE_FILE_ID_THREE";
        /**
         * 审批日期1。
         */
        public static final String APPROVAL_DATE_ONE = "APPROVAL_DATE_ONE";
        /**
         * 审批人1。
         */
        public static final String APPROVAL_USER_ONE = "APPROVAL_USER_ONE";
        /**
         * 备案材料。
         */
        public static final String KEEP_RECORD_FILE = "KEEP_RECORD_FILE";
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
    public PmDefenseBuildReq setId(String id) {
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
    public PmDefenseBuildReq setVer(Integer ver) {
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
    public PmDefenseBuildReq setTs(LocalDateTime ts) {
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
    public PmDefenseBuildReq setIsPreset(Boolean isPreset) {
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
    public PmDefenseBuildReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmDefenseBuildReq setLastModiUserId(String lastModiUserId) {
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
    public PmDefenseBuildReq setCode(String code) {
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
    public PmDefenseBuildReq setName(String name) {
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
    public PmDefenseBuildReq setLkWfInstId(String lkWfInstId) {
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
    public PmDefenseBuildReq setPmPrjId(String pmPrjId) {
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
    public PmDefenseBuildReq setStatus(String status) {
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
    public PmDefenseBuildReq setPrjCode(String prjCode) {
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
    public PmDefenseBuildReq setCustomerUnit(String customerUnit) {
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
    public PmDefenseBuildReq setCrtUserId(String crtUserId) {
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
     * 创建部门。
     */
    private String crtDeptId;

    /**
     * 获取：创建部门。
     */
    public String getCrtDeptId() {
        return this.crtDeptId;
    }

    /**
     * 设置：创建部门。
     */
    public PmDefenseBuildReq setCrtDeptId(String crtDeptId) {
        if (this.crtDeptId == null && crtDeptId == null) {
            // 均为null，不做处理。
        } else if (this.crtDeptId != null && crtDeptId != null) {
            // 均非null，判定不等，再做处理：
            if (this.crtDeptId.compareTo(crtDeptId) != 0) {
                this.crtDeptId = crtDeptId;
                if (!this.toUpdateCols.contains("CRT_DEPT_ID")) {
                    this.toUpdateCols.add("CRT_DEPT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.crtDeptId = crtDeptId;
            if (!this.toUpdateCols.contains("CRT_DEPT_ID")) {
                this.toUpdateCols.add("CRT_DEPT_ID");
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
    public PmDefenseBuildReq setPrjManageModeId(String prjManageModeId) {
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
    public PmDefenseBuildReq setBaseLocationId(String baseLocationId) {
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
    public PmDefenseBuildReq setCrtDt(LocalDateTime crtDt) {
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
    public PmDefenseBuildReq setFloorArea(BigDecimal floorArea) {
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
    public PmDefenseBuildReq setProjectTypeId(String projectTypeId) {
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
    public PmDefenseBuildReq setConScaleTypeId(String conScaleTypeId) {
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
    public PmDefenseBuildReq setConScaleQty(BigDecimal conScaleQty) {
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
    public PmDefenseBuildReq setQtyOne(BigDecimal qtyOne) {
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
    public PmDefenseBuildReq setQtyTwo(Integer qtyTwo) {
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
    public PmDefenseBuildReq setQtyThree(Integer qtyThree) {
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
    public PmDefenseBuildReq setConScaleQty2(BigDecimal conScaleQty2) {
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
    public PmDefenseBuildReq setConScaleUomId(String conScaleUomId) {
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
    public PmDefenseBuildReq setBuildYears(BigDecimal buildYears) {
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
    public PmDefenseBuildReq setPrjSituation(String prjSituation) {
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
     * 附件。
     */
    private String attFileGroupId;

    /**
     * 获取：附件。
     */
    public String getAttFileGroupId() {
        return this.attFileGroupId;
    }

    /**
     * 设置：附件。
     */
    public PmDefenseBuildReq setAttFileGroupId(String attFileGroupId) {
        if (this.attFileGroupId == null && attFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.attFileGroupId != null && attFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.attFileGroupId.compareTo(attFileGroupId) != 0) {
                this.attFileGroupId = attFileGroupId;
                if (!this.toUpdateCols.contains("ATT_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("ATT_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.attFileGroupId = attFileGroupId;
            if (!this.toUpdateCols.contains("ATT_FILE_GROUP_ID")) {
                this.toUpdateCols.add("ATT_FILE_GROUP_ID");
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
    public PmDefenseBuildReq setRemark(String remark) {
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
     * 计划完成日期。
     */
    private LocalDate complPlanDate;

    /**
     * 获取：计划完成日期。
     */
    public LocalDate getComplPlanDate() {
        return this.complPlanDate;
    }

    /**
     * 设置：计划完成日期。
     */
    public PmDefenseBuildReq setComplPlanDate(LocalDate complPlanDate) {
        if (this.complPlanDate == null && complPlanDate == null) {
            // 均为null，不做处理。
        } else if (this.complPlanDate != null && complPlanDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.complPlanDate.compareTo(complPlanDate) != 0) {
                this.complPlanDate = complPlanDate;
                if (!this.toUpdateCols.contains("COMPL_PLAN_DATE")) {
                    this.toUpdateCols.add("COMPL_PLAN_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.complPlanDate = complPlanDate;
            if (!this.toUpdateCols.contains("COMPL_PLAN_DATE")) {
                this.toUpdateCols.add("COMPL_PLAN_DATE");
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
    public PmDefenseBuildReq setActualComplDate(LocalDate actualComplDate) {
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
     * 对方负责人。
     */
    private String otherResponsor;

    /**
     * 获取：对方负责人。
     */
    public String getOtherResponsor() {
        return this.otherResponsor;
    }

    /**
     * 设置：对方负责人。
     */
    public PmDefenseBuildReq setOtherResponsor(String otherResponsor) {
        if (this.otherResponsor == null && otherResponsor == null) {
            // 均为null，不做处理。
        } else if (this.otherResponsor != null && otherResponsor != null) {
            // 均非null，判定不等，再做处理：
            if (this.otherResponsor.compareTo(otherResponsor) != 0) {
                this.otherResponsor = otherResponsor;
                if (!this.toUpdateCols.contains("OTHER_RESPONSOR")) {
                    this.toUpdateCols.add("OTHER_RESPONSOR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.otherResponsor = otherResponsor;
            if (!this.toUpdateCols.contains("OTHER_RESPONSOR")) {
                this.toUpdateCols.add("OTHER_RESPONSOR");
            }
        }
        return this;
    }

    /**
     * 评审组织单位。
     */
    private String reviewOrganizationUnit;

    /**
     * 获取：评审组织单位。
     */
    public String getReviewOrganizationUnit() {
        return this.reviewOrganizationUnit;
    }

    /**
     * 设置：评审组织单位。
     */
    public PmDefenseBuildReq setReviewOrganizationUnit(String reviewOrganizationUnit) {
        if (this.reviewOrganizationUnit == null && reviewOrganizationUnit == null) {
            // 均为null，不做处理。
        } else if (this.reviewOrganizationUnit != null && reviewOrganizationUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.reviewOrganizationUnit.compareTo(reviewOrganizationUnit) != 0) {
                this.reviewOrganizationUnit = reviewOrganizationUnit;
                if (!this.toUpdateCols.contains("REVIEW_ORGANIZATION_UNIT")) {
                    this.toUpdateCols.add("REVIEW_ORGANIZATION_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.reviewOrganizationUnit = reviewOrganizationUnit;
            if (!this.toUpdateCols.contains("REVIEW_ORGANIZATION_UNIT")) {
                this.toUpdateCols.add("REVIEW_ORGANIZATION_UNIT");
            }
        }
        return this;
    }

    /**
     * 评审单位联系方式。
     */
    private String reviewUnitPhone;

    /**
     * 获取：评审单位联系方式。
     */
    public String getReviewUnitPhone() {
        return this.reviewUnitPhone;
    }

    /**
     * 设置：评审单位联系方式。
     */
    public PmDefenseBuildReq setReviewUnitPhone(String reviewUnitPhone) {
        if (this.reviewUnitPhone == null && reviewUnitPhone == null) {
            // 均为null，不做处理。
        } else if (this.reviewUnitPhone != null && reviewUnitPhone != null) {
            // 均非null，判定不等，再做处理：
            if (this.reviewUnitPhone.compareTo(reviewUnitPhone) != 0) {
                this.reviewUnitPhone = reviewUnitPhone;
                if (!this.toUpdateCols.contains("REVIEW_UNIT_PHONE")) {
                    this.toUpdateCols.add("REVIEW_UNIT_PHONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.reviewUnitPhone = reviewUnitPhone;
            if (!this.toUpdateCols.contains("REVIEW_UNIT_PHONE")) {
                this.toUpdateCols.add("REVIEW_UNIT_PHONE");
            }
        }
        return this;
    }

    /**
     * 审批附件1。
     */
    private String approveFileIdOne;

    /**
     * 获取：审批附件1。
     */
    public String getApproveFileIdOne() {
        return this.approveFileIdOne;
    }

    /**
     * 设置：审批附件1。
     */
    public PmDefenseBuildReq setApproveFileIdOne(String approveFileIdOne) {
        if (this.approveFileIdOne == null && approveFileIdOne == null) {
            // 均为null，不做处理。
        } else if (this.approveFileIdOne != null && approveFileIdOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.approveFileIdOne.compareTo(approveFileIdOne) != 0) {
                this.approveFileIdOne = approveFileIdOne;
                if (!this.toUpdateCols.contains("APPROVE_FILE_ID_ONE")) {
                    this.toUpdateCols.add("APPROVE_FILE_ID_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approveFileIdOne = approveFileIdOne;
            if (!this.toUpdateCols.contains("APPROVE_FILE_ID_ONE")) {
                this.toUpdateCols.add("APPROVE_FILE_ID_ONE");
            }
        }
        return this;
    }

    /**
     * 审批附件2。
     */
    private String approveFileIdTwo;

    /**
     * 获取：审批附件2。
     */
    public String getApproveFileIdTwo() {
        return this.approveFileIdTwo;
    }

    /**
     * 设置：审批附件2。
     */
    public PmDefenseBuildReq setApproveFileIdTwo(String approveFileIdTwo) {
        if (this.approveFileIdTwo == null && approveFileIdTwo == null) {
            // 均为null，不做处理。
        } else if (this.approveFileIdTwo != null && approveFileIdTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.approveFileIdTwo.compareTo(approveFileIdTwo) != 0) {
                this.approveFileIdTwo = approveFileIdTwo;
                if (!this.toUpdateCols.contains("APPROVE_FILE_ID_TWO")) {
                    this.toUpdateCols.add("APPROVE_FILE_ID_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approveFileIdTwo = approveFileIdTwo;
            if (!this.toUpdateCols.contains("APPROVE_FILE_ID_TWO")) {
                this.toUpdateCols.add("APPROVE_FILE_ID_TWO");
            }
        }
        return this;
    }

    /**
     * 评审及修编说明。
     */
    private String reviewAndRevisionRemark;

    /**
     * 获取：评审及修编说明。
     */
    public String getReviewAndRevisionRemark() {
        return this.reviewAndRevisionRemark;
    }

    /**
     * 设置：评审及修编说明。
     */
    public PmDefenseBuildReq setReviewAndRevisionRemark(String reviewAndRevisionRemark) {
        if (this.reviewAndRevisionRemark == null && reviewAndRevisionRemark == null) {
            // 均为null，不做处理。
        } else if (this.reviewAndRevisionRemark != null && reviewAndRevisionRemark != null) {
            // 均非null，判定不等，再做处理：
            if (this.reviewAndRevisionRemark.compareTo(reviewAndRevisionRemark) != 0) {
                this.reviewAndRevisionRemark = reviewAndRevisionRemark;
                if (!this.toUpdateCols.contains("REVIEW_AND_REVISION_REMARK")) {
                    this.toUpdateCols.add("REVIEW_AND_REVISION_REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.reviewAndRevisionRemark = reviewAndRevisionRemark;
            if (!this.toUpdateCols.contains("REVIEW_AND_REVISION_REMARK")) {
                this.toUpdateCols.add("REVIEW_AND_REVISION_REMARK");
            }
        }
        return this;
    }

    /**
     * 日期1。
     */
    private LocalDate dateOne;

    /**
     * 获取：日期1。
     */
    public LocalDate getDateOne() {
        return this.dateOne;
    }

    /**
     * 设置：日期1。
     */
    public PmDefenseBuildReq setDateOne(LocalDate dateOne) {
        if (this.dateOne == null && dateOne == null) {
            // 均为null，不做处理。
        } else if (this.dateOne != null && dateOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.dateOne.compareTo(dateOne) != 0) {
                this.dateOne = dateOne;
                if (!this.toUpdateCols.contains("DATE_ONE")) {
                    this.toUpdateCols.add("DATE_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dateOne = dateOne;
            if (!this.toUpdateCols.contains("DATE_ONE")) {
                this.toUpdateCols.add("DATE_ONE");
            }
        }
        return this;
    }

    /**
     * 申请时间。
     */
    private LocalDate applyTime;

    /**
     * 获取：申请时间。
     */
    public LocalDate getApplyTime() {
        return this.applyTime;
    }

    /**
     * 设置：申请时间。
     */
    public PmDefenseBuildReq setApplyTime(LocalDate applyTime) {
        if (this.applyTime == null && applyTime == null) {
            // 均为null，不做处理。
        } else if (this.applyTime != null && applyTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.applyTime.compareTo(applyTime) != 0) {
                this.applyTime = applyTime;
                if (!this.toUpdateCols.contains("APPLY_TIME")) {
                    this.toUpdateCols.add("APPLY_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.applyTime = applyTime;
            if (!this.toUpdateCols.contains("APPLY_TIME")) {
                this.toUpdateCols.add("APPLY_TIME");
            }
        }
        return this;
    }

    /**
     * 申请人。
     */
    private String applicant;

    /**
     * 获取：申请人。
     */
    public String getApplicant() {
        return this.applicant;
    }

    /**
     * 设置：申请人。
     */
    public PmDefenseBuildReq setApplicant(String applicant) {
        if (this.applicant == null && applicant == null) {
            // 均为null，不做处理。
        } else if (this.applicant != null && applicant != null) {
            // 均非null，判定不等，再做处理：
            if (this.applicant.compareTo(applicant) != 0) {
                this.applicant = applicant;
                if (!this.toUpdateCols.contains("APPLICANT")) {
                    this.toUpdateCols.add("APPLICANT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.applicant = applicant;
            if (!this.toUpdateCols.contains("APPLICANT")) {
                this.toUpdateCols.add("APPLICANT");
            }
        }
        return this;
    }

    /**
     * 审批附件3。
     */
    private String approveFileIdThree;

    /**
     * 获取：审批附件3。
     */
    public String getApproveFileIdThree() {
        return this.approveFileIdThree;
    }

    /**
     * 设置：审批附件3。
     */
    public PmDefenseBuildReq setApproveFileIdThree(String approveFileIdThree) {
        if (this.approveFileIdThree == null && approveFileIdThree == null) {
            // 均为null，不做处理。
        } else if (this.approveFileIdThree != null && approveFileIdThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.approveFileIdThree.compareTo(approveFileIdThree) != 0) {
                this.approveFileIdThree = approveFileIdThree;
                if (!this.toUpdateCols.contains("APPROVE_FILE_ID_THREE")) {
                    this.toUpdateCols.add("APPROVE_FILE_ID_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approveFileIdThree = approveFileIdThree;
            if (!this.toUpdateCols.contains("APPROVE_FILE_ID_THREE")) {
                this.toUpdateCols.add("APPROVE_FILE_ID_THREE");
            }
        }
        return this;
    }

    /**
     * 审批日期1。
     */
    private LocalDate approvalDateOne;

    /**
     * 获取：审批日期1。
     */
    public LocalDate getApprovalDateOne() {
        return this.approvalDateOne;
    }

    /**
     * 设置：审批日期1。
     */
    public PmDefenseBuildReq setApprovalDateOne(LocalDate approvalDateOne) {
        if (this.approvalDateOne == null && approvalDateOne == null) {
            // 均为null，不做处理。
        } else if (this.approvalDateOne != null && approvalDateOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalDateOne.compareTo(approvalDateOne) != 0) {
                this.approvalDateOne = approvalDateOne;
                if (!this.toUpdateCols.contains("APPROVAL_DATE_ONE")) {
                    this.toUpdateCols.add("APPROVAL_DATE_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalDateOne = approvalDateOne;
            if (!this.toUpdateCols.contains("APPROVAL_DATE_ONE")) {
                this.toUpdateCols.add("APPROVAL_DATE_ONE");
            }
        }
        return this;
    }

    /**
     * 审批人1。
     */
    private String approvalUserOne;

    /**
     * 获取：审批人1。
     */
    public String getApprovalUserOne() {
        return this.approvalUserOne;
    }

    /**
     * 设置：审批人1。
     */
    public PmDefenseBuildReq setApprovalUserOne(String approvalUserOne) {
        if (this.approvalUserOne == null && approvalUserOne == null) {
            // 均为null，不做处理。
        } else if (this.approvalUserOne != null && approvalUserOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalUserOne.compareTo(approvalUserOne) != 0) {
                this.approvalUserOne = approvalUserOne;
                if (!this.toUpdateCols.contains("APPROVAL_USER_ONE")) {
                    this.toUpdateCols.add("APPROVAL_USER_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalUserOne = approvalUserOne;
            if (!this.toUpdateCols.contains("APPROVAL_USER_ONE")) {
                this.toUpdateCols.add("APPROVAL_USER_ONE");
            }
        }
        return this;
    }

    /**
     * 备案材料。
     */
    private String keepRecordFile;

    /**
     * 获取：备案材料。
     */
    public String getKeepRecordFile() {
        return this.keepRecordFile;
    }

    /**
     * 设置：备案材料。
     */
    public PmDefenseBuildReq setKeepRecordFile(String keepRecordFile) {
        if (this.keepRecordFile == null && keepRecordFile == null) {
            // 均为null，不做处理。
        } else if (this.keepRecordFile != null && keepRecordFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.keepRecordFile.compareTo(keepRecordFile) != 0) {
                this.keepRecordFile = keepRecordFile;
                if (!this.toUpdateCols.contains("KEEP_RECORD_FILE")) {
                    this.toUpdateCols.add("KEEP_RECORD_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.keepRecordFile = keepRecordFile;
            if (!this.toUpdateCols.contains("KEEP_RECORD_FILE")) {
                this.toUpdateCols.add("KEEP_RECORD_FILE");
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
    public static PmDefenseBuildReq newData() {
        PmDefenseBuildReq obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmDefenseBuildReq insertData() {
        PmDefenseBuildReq obj = modelHelper.insertData();
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
    public static PmDefenseBuildReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmDefenseBuildReq obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<PmDefenseBuildReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmDefenseBuildReq> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<PmDefenseBuildReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmDefenseBuildReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(PmDefenseBuildReq fromModel, PmDefenseBuildReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}