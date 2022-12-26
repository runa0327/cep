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
 * 施工许可。
 */
public class PmConstructPermitReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmConstructPermitReq> modelHelper = new ModelHelper<>("PM_CONSTRUCT_PERMIT_REQ", new PmConstructPermitReq());

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

    public static final String ENT_CODE = "PM_CONSTRUCT_PERMIT_REQ";
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
         * 施工许可类型。
         */
        public static final String BUILD_PERMIT_TYPE_ID = "BUILD_PERMIT_TYPE_ID";
        /**
         * 项目概况。
         */
        public static final String PRJ_SITUATION = "PRJ_SITUATION";
        /**
         * 土石方申请日期。
         */
        public static final String EARTHWORK_APPLY_DATE = "EARTHWORK_APPLY_DATE";
        /**
         * 计划完成日期申请。
         */
        public static final String COMPL_PLAN_DATE_APPLY = "COMPL_PLAN_DATE_APPLY";
        /**
         * 土石方申请人。
         */
        public static final String EARTHWORK_APPLY_USER = "EARTHWORK_APPLY_USER";
        /**
         * 立项申请材料。
         */
        public static final String PRJ_REQ_FILE = "PRJ_REQ_FILE";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 主体申请日期。
         */
        public static final String SUBJECT_APPLY_DATE = "SUBJECT_APPLY_DATE";
        /**
         * 主体许可计划完成日期。
         */
        public static final String SUBJECT_PLAN_COMPL_DATE = "SUBJECT_PLAN_COMPL_DATE";
        /**
         * 主体申请人。
         */
        public static final String SUBJECT_APPLY_USER = "SUBJECT_APPLY_USER";
        /**
         * 备案材料。
         */
        public static final String KEEP_RECORD_FILE = "KEEP_RECORD_FILE";
        /**
         * 操作备注。
         */
        public static final String ACT_REMARK = "ACT_REMARK";
        /**
         * 土石方核实日期。
         */
        public static final String EARTHWORK_VERIFY_DATE = "EARTHWORK_VERIFY_DATE";
        /**
         * 有效期。
         */
        public static final String VALID_TERM = "VALID_TERM";
        /**
         * 土石方材料。
         */
        public static final String EARTHWORK_FILE = "EARTHWORK_FILE";
        /**
         * 主体核实日期。
         */
        public static final String SUBJECT_VERIFY_DATE = "SUBJECT_VERIFY_DATE";
        /**
         * 主体有效期。
         */
        public static final String SUBJECT_TERM = "SUBJECT_TERM";
        /**
         * 主体材料。
         */
        public static final String SUBJECT_FILE = "SUBJECT_FILE";
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
    public PmConstructPermitReq setId(String id) {
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
    public PmConstructPermitReq setVer(Integer ver) {
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
    public PmConstructPermitReq setTs(LocalDateTime ts) {
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
    public PmConstructPermitReq setIsPreset(Boolean isPreset) {
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
    public PmConstructPermitReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmConstructPermitReq setLastModiUserId(String lastModiUserId) {
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
    public PmConstructPermitReq setCode(String code) {
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
    public PmConstructPermitReq setName(String name) {
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
    public PmConstructPermitReq setLkWfInstId(String lkWfInstId) {
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
    public PmConstructPermitReq setPmPrjId(String pmPrjId) {
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
    public PmConstructPermitReq setStatus(String status) {
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
    public PmConstructPermitReq setPrjCode(String prjCode) {
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
    public PmConstructPermitReq setCustomerUnit(String customerUnit) {
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
    public PmConstructPermitReq setCrtUserId(String crtUserId) {
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
    public PmConstructPermitReq setCrtDeptId(String crtDeptId) {
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
    public PmConstructPermitReq setPrjManageModeId(String prjManageModeId) {
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
    public PmConstructPermitReq setBaseLocationId(String baseLocationId) {
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
    public PmConstructPermitReq setCrtDt(LocalDateTime crtDt) {
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
    public PmConstructPermitReq setFloorArea(BigDecimal floorArea) {
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
    public PmConstructPermitReq setProjectTypeId(String projectTypeId) {
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
    public PmConstructPermitReq setConScaleTypeId(String conScaleTypeId) {
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
    public PmConstructPermitReq setConScaleQty(BigDecimal conScaleQty) {
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
    public PmConstructPermitReq setQtyOne(BigDecimal qtyOne) {
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
    public PmConstructPermitReq setQtyTwo(Integer qtyTwo) {
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
    public PmConstructPermitReq setQtyThree(Integer qtyThree) {
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
    public PmConstructPermitReq setConScaleQty2(BigDecimal conScaleQty2) {
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
    public PmConstructPermitReq setConScaleUomId(String conScaleUomId) {
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
    public PmConstructPermitReq setBuildYears(BigDecimal buildYears) {
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
     * 施工许可类型。
     */
    private String buildPermitTypeId;

    /**
     * 获取：施工许可类型。
     */
    public String getBuildPermitTypeId() {
        return this.buildPermitTypeId;
    }

    /**
     * 设置：施工许可类型。
     */
    public PmConstructPermitReq setBuildPermitTypeId(String buildPermitTypeId) {
        if (this.buildPermitTypeId == null && buildPermitTypeId == null) {
            // 均为null，不做处理。
        } else if (this.buildPermitTypeId != null && buildPermitTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.buildPermitTypeId.compareTo(buildPermitTypeId) != 0) {
                this.buildPermitTypeId = buildPermitTypeId;
                if (!this.toUpdateCols.contains("BUILD_PERMIT_TYPE_ID")) {
                    this.toUpdateCols.add("BUILD_PERMIT_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.buildPermitTypeId = buildPermitTypeId;
            if (!this.toUpdateCols.contains("BUILD_PERMIT_TYPE_ID")) {
                this.toUpdateCols.add("BUILD_PERMIT_TYPE_ID");
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
    public PmConstructPermitReq setPrjSituation(String prjSituation) {
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
     * 土石方申请日期。
     */
    private LocalDate earthworkApplyDate;

    /**
     * 获取：土石方申请日期。
     */
    public LocalDate getEarthworkApplyDate() {
        return this.earthworkApplyDate;
    }

    /**
     * 设置：土石方申请日期。
     */
    public PmConstructPermitReq setEarthworkApplyDate(LocalDate earthworkApplyDate) {
        if (this.earthworkApplyDate == null && earthworkApplyDate == null) {
            // 均为null，不做处理。
        } else if (this.earthworkApplyDate != null && earthworkApplyDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.earthworkApplyDate.compareTo(earthworkApplyDate) != 0) {
                this.earthworkApplyDate = earthworkApplyDate;
                if (!this.toUpdateCols.contains("EARTHWORK_APPLY_DATE")) {
                    this.toUpdateCols.add("EARTHWORK_APPLY_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.earthworkApplyDate = earthworkApplyDate;
            if (!this.toUpdateCols.contains("EARTHWORK_APPLY_DATE")) {
                this.toUpdateCols.add("EARTHWORK_APPLY_DATE");
            }
        }
        return this;
    }

    /**
     * 计划完成日期申请。
     */
    private LocalDate complPlanDateApply;

    /**
     * 获取：计划完成日期申请。
     */
    public LocalDate getComplPlanDateApply() {
        return this.complPlanDateApply;
    }

    /**
     * 设置：计划完成日期申请。
     */
    public PmConstructPermitReq setComplPlanDateApply(LocalDate complPlanDateApply) {
        if (this.complPlanDateApply == null && complPlanDateApply == null) {
            // 均为null，不做处理。
        } else if (this.complPlanDateApply != null && complPlanDateApply != null) {
            // 均非null，判定不等，再做处理：
            if (this.complPlanDateApply.compareTo(complPlanDateApply) != 0) {
                this.complPlanDateApply = complPlanDateApply;
                if (!this.toUpdateCols.contains("COMPL_PLAN_DATE_APPLY")) {
                    this.toUpdateCols.add("COMPL_PLAN_DATE_APPLY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.complPlanDateApply = complPlanDateApply;
            if (!this.toUpdateCols.contains("COMPL_PLAN_DATE_APPLY")) {
                this.toUpdateCols.add("COMPL_PLAN_DATE_APPLY");
            }
        }
        return this;
    }

    /**
     * 土石方申请人。
     */
    private String earthworkApplyUser;

    /**
     * 获取：土石方申请人。
     */
    public String getEarthworkApplyUser() {
        return this.earthworkApplyUser;
    }

    /**
     * 设置：土石方申请人。
     */
    public PmConstructPermitReq setEarthworkApplyUser(String earthworkApplyUser) {
        if (this.earthworkApplyUser == null && earthworkApplyUser == null) {
            // 均为null，不做处理。
        } else if (this.earthworkApplyUser != null && earthworkApplyUser != null) {
            // 均非null，判定不等，再做处理：
            if (this.earthworkApplyUser.compareTo(earthworkApplyUser) != 0) {
                this.earthworkApplyUser = earthworkApplyUser;
                if (!this.toUpdateCols.contains("EARTHWORK_APPLY_USER")) {
                    this.toUpdateCols.add("EARTHWORK_APPLY_USER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.earthworkApplyUser = earthworkApplyUser;
            if (!this.toUpdateCols.contains("EARTHWORK_APPLY_USER")) {
                this.toUpdateCols.add("EARTHWORK_APPLY_USER");
            }
        }
        return this;
    }

    /**
     * 立项申请材料。
     */
    private String prjReqFile;

    /**
     * 获取：立项申请材料。
     */
    public String getPrjReqFile() {
        return this.prjReqFile;
    }

    /**
     * 设置：立项申请材料。
     */
    public PmConstructPermitReq setPrjReqFile(String prjReqFile) {
        if (this.prjReqFile == null && prjReqFile == null) {
            // 均为null，不做处理。
        } else if (this.prjReqFile != null && prjReqFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjReqFile.compareTo(prjReqFile) != 0) {
                this.prjReqFile = prjReqFile;
                if (!this.toUpdateCols.contains("PRJ_REQ_FILE")) {
                    this.toUpdateCols.add("PRJ_REQ_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjReqFile = prjReqFile;
            if (!this.toUpdateCols.contains("PRJ_REQ_FILE")) {
                this.toUpdateCols.add("PRJ_REQ_FILE");
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
    public PmConstructPermitReq setRemark(String remark) {
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
     * 主体申请日期。
     */
    private LocalDate subjectApplyDate;

    /**
     * 获取：主体申请日期。
     */
    public LocalDate getSubjectApplyDate() {
        return this.subjectApplyDate;
    }

    /**
     * 设置：主体申请日期。
     */
    public PmConstructPermitReq setSubjectApplyDate(LocalDate subjectApplyDate) {
        if (this.subjectApplyDate == null && subjectApplyDate == null) {
            // 均为null，不做处理。
        } else if (this.subjectApplyDate != null && subjectApplyDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.subjectApplyDate.compareTo(subjectApplyDate) != 0) {
                this.subjectApplyDate = subjectApplyDate;
                if (!this.toUpdateCols.contains("SUBJECT_APPLY_DATE")) {
                    this.toUpdateCols.add("SUBJECT_APPLY_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.subjectApplyDate = subjectApplyDate;
            if (!this.toUpdateCols.contains("SUBJECT_APPLY_DATE")) {
                this.toUpdateCols.add("SUBJECT_APPLY_DATE");
            }
        }
        return this;
    }

    /**
     * 主体许可计划完成日期。
     */
    private LocalDate subjectPlanComplDate;

    /**
     * 获取：主体许可计划完成日期。
     */
    public LocalDate getSubjectPlanComplDate() {
        return this.subjectPlanComplDate;
    }

    /**
     * 设置：主体许可计划完成日期。
     */
    public PmConstructPermitReq setSubjectPlanComplDate(LocalDate subjectPlanComplDate) {
        if (this.subjectPlanComplDate == null && subjectPlanComplDate == null) {
            // 均为null，不做处理。
        } else if (this.subjectPlanComplDate != null && subjectPlanComplDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.subjectPlanComplDate.compareTo(subjectPlanComplDate) != 0) {
                this.subjectPlanComplDate = subjectPlanComplDate;
                if (!this.toUpdateCols.contains("SUBJECT_PLAN_COMPL_DATE")) {
                    this.toUpdateCols.add("SUBJECT_PLAN_COMPL_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.subjectPlanComplDate = subjectPlanComplDate;
            if (!this.toUpdateCols.contains("SUBJECT_PLAN_COMPL_DATE")) {
                this.toUpdateCols.add("SUBJECT_PLAN_COMPL_DATE");
            }
        }
        return this;
    }

    /**
     * 主体申请人。
     */
    private String subjectApplyUser;

    /**
     * 获取：主体申请人。
     */
    public String getSubjectApplyUser() {
        return this.subjectApplyUser;
    }

    /**
     * 设置：主体申请人。
     */
    public PmConstructPermitReq setSubjectApplyUser(String subjectApplyUser) {
        if (this.subjectApplyUser == null && subjectApplyUser == null) {
            // 均为null，不做处理。
        } else if (this.subjectApplyUser != null && subjectApplyUser != null) {
            // 均非null，判定不等，再做处理：
            if (this.subjectApplyUser.compareTo(subjectApplyUser) != 0) {
                this.subjectApplyUser = subjectApplyUser;
                if (!this.toUpdateCols.contains("SUBJECT_APPLY_USER")) {
                    this.toUpdateCols.add("SUBJECT_APPLY_USER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.subjectApplyUser = subjectApplyUser;
            if (!this.toUpdateCols.contains("SUBJECT_APPLY_USER")) {
                this.toUpdateCols.add("SUBJECT_APPLY_USER");
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
    public PmConstructPermitReq setKeepRecordFile(String keepRecordFile) {
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

    /**
     * 操作备注。
     */
    private String actRemark;

    /**
     * 获取：操作备注。
     */
    public String getActRemark() {
        return this.actRemark;
    }

    /**
     * 设置：操作备注。
     */
    public PmConstructPermitReq setActRemark(String actRemark) {
        if (this.actRemark == null && actRemark == null) {
            // 均为null，不做处理。
        } else if (this.actRemark != null && actRemark != null) {
            // 均非null，判定不等，再做处理：
            if (this.actRemark.compareTo(actRemark) != 0) {
                this.actRemark = actRemark;
                if (!this.toUpdateCols.contains("ACT_REMARK")) {
                    this.toUpdateCols.add("ACT_REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.actRemark = actRemark;
            if (!this.toUpdateCols.contains("ACT_REMARK")) {
                this.toUpdateCols.add("ACT_REMARK");
            }
        }
        return this;
    }

    /**
     * 土石方核实日期。
     */
    private LocalDate earthworkVerifyDate;

    /**
     * 获取：土石方核实日期。
     */
    public LocalDate getEarthworkVerifyDate() {
        return this.earthworkVerifyDate;
    }

    /**
     * 设置：土石方核实日期。
     */
    public PmConstructPermitReq setEarthworkVerifyDate(LocalDate earthworkVerifyDate) {
        if (this.earthworkVerifyDate == null && earthworkVerifyDate == null) {
            // 均为null，不做处理。
        } else if (this.earthworkVerifyDate != null && earthworkVerifyDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.earthworkVerifyDate.compareTo(earthworkVerifyDate) != 0) {
                this.earthworkVerifyDate = earthworkVerifyDate;
                if (!this.toUpdateCols.contains("EARTHWORK_VERIFY_DATE")) {
                    this.toUpdateCols.add("EARTHWORK_VERIFY_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.earthworkVerifyDate = earthworkVerifyDate;
            if (!this.toUpdateCols.contains("EARTHWORK_VERIFY_DATE")) {
                this.toUpdateCols.add("EARTHWORK_VERIFY_DATE");
            }
        }
        return this;
    }

    /**
     * 有效期。
     */
    private String validTerm;

    /**
     * 获取：有效期。
     */
    public String getValidTerm() {
        return this.validTerm;
    }

    /**
     * 设置：有效期。
     */
    public PmConstructPermitReq setValidTerm(String validTerm) {
        if (this.validTerm == null && validTerm == null) {
            // 均为null，不做处理。
        } else if (this.validTerm != null && validTerm != null) {
            // 均非null，判定不等，再做处理：
            if (this.validTerm.compareTo(validTerm) != 0) {
                this.validTerm = validTerm;
                if (!this.toUpdateCols.contains("VALID_TERM")) {
                    this.toUpdateCols.add("VALID_TERM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.validTerm = validTerm;
            if (!this.toUpdateCols.contains("VALID_TERM")) {
                this.toUpdateCols.add("VALID_TERM");
            }
        }
        return this;
    }

    /**
     * 土石方材料。
     */
    private String earthworkFile;

    /**
     * 获取：土石方材料。
     */
    public String getEarthworkFile() {
        return this.earthworkFile;
    }

    /**
     * 设置：土石方材料。
     */
    public PmConstructPermitReq setEarthworkFile(String earthworkFile) {
        if (this.earthworkFile == null && earthworkFile == null) {
            // 均为null，不做处理。
        } else if (this.earthworkFile != null && earthworkFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.earthworkFile.compareTo(earthworkFile) != 0) {
                this.earthworkFile = earthworkFile;
                if (!this.toUpdateCols.contains("EARTHWORK_FILE")) {
                    this.toUpdateCols.add("EARTHWORK_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.earthworkFile = earthworkFile;
            if (!this.toUpdateCols.contains("EARTHWORK_FILE")) {
                this.toUpdateCols.add("EARTHWORK_FILE");
            }
        }
        return this;
    }

    /**
     * 主体核实日期。
     */
    private LocalDate subjectVerifyDate;

    /**
     * 获取：主体核实日期。
     */
    public LocalDate getSubjectVerifyDate() {
        return this.subjectVerifyDate;
    }

    /**
     * 设置：主体核实日期。
     */
    public PmConstructPermitReq setSubjectVerifyDate(LocalDate subjectVerifyDate) {
        if (this.subjectVerifyDate == null && subjectVerifyDate == null) {
            // 均为null，不做处理。
        } else if (this.subjectVerifyDate != null && subjectVerifyDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.subjectVerifyDate.compareTo(subjectVerifyDate) != 0) {
                this.subjectVerifyDate = subjectVerifyDate;
                if (!this.toUpdateCols.contains("SUBJECT_VERIFY_DATE")) {
                    this.toUpdateCols.add("SUBJECT_VERIFY_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.subjectVerifyDate = subjectVerifyDate;
            if (!this.toUpdateCols.contains("SUBJECT_VERIFY_DATE")) {
                this.toUpdateCols.add("SUBJECT_VERIFY_DATE");
            }
        }
        return this;
    }

    /**
     * 主体有效期。
     */
    private String subjectTerm;

    /**
     * 获取：主体有效期。
     */
    public String getSubjectTerm() {
        return this.subjectTerm;
    }

    /**
     * 设置：主体有效期。
     */
    public PmConstructPermitReq setSubjectTerm(String subjectTerm) {
        if (this.subjectTerm == null && subjectTerm == null) {
            // 均为null，不做处理。
        } else if (this.subjectTerm != null && subjectTerm != null) {
            // 均非null，判定不等，再做处理：
            if (this.subjectTerm.compareTo(subjectTerm) != 0) {
                this.subjectTerm = subjectTerm;
                if (!this.toUpdateCols.contains("SUBJECT_TERM")) {
                    this.toUpdateCols.add("SUBJECT_TERM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.subjectTerm = subjectTerm;
            if (!this.toUpdateCols.contains("SUBJECT_TERM")) {
                this.toUpdateCols.add("SUBJECT_TERM");
            }
        }
        return this;
    }

    /**
     * 主体材料。
     */
    private String subjectFile;

    /**
     * 获取：主体材料。
     */
    public String getSubjectFile() {
        return this.subjectFile;
    }

    /**
     * 设置：主体材料。
     */
    public PmConstructPermitReq setSubjectFile(String subjectFile) {
        if (this.subjectFile == null && subjectFile == null) {
            // 均为null，不做处理。
        } else if (this.subjectFile != null && subjectFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.subjectFile.compareTo(subjectFile) != 0) {
                this.subjectFile = subjectFile;
                if (!this.toUpdateCols.contains("SUBJECT_FILE")) {
                    this.toUpdateCols.add("SUBJECT_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.subjectFile = subjectFile;
            if (!this.toUpdateCols.contains("SUBJECT_FILE")) {
                this.toUpdateCols.add("SUBJECT_FILE");
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
    public static PmConstructPermitReq newData() {
        PmConstructPermitReq obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmConstructPermitReq insertData() {
        PmConstructPermitReq obj = modelHelper.insertData();
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
    public static PmConstructPermitReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmConstructPermitReq obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<PmConstructPermitReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmConstructPermitReq> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<PmConstructPermitReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmConstructPermitReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(PmConstructPermitReq fromModel, PmConstructPermitReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}