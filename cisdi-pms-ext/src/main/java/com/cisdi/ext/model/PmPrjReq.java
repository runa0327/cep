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
 * 立项申请审批。
 */
public class PmPrjReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmPrjReq> modelHelper = new ModelHelper<>("PM_PRJ_REQ", new PmPrjReq());

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

    public static final String ENT_CODE = "PM_PRJ_REQ";
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
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 锁定流程实例。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 项目名称。
         */
        public static final String PRJ_NAME = "PRJ_NAME";
        /**
         * 项目编号。
         */
        public static final String PRJ_CODE = "PRJ_CODE";
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
         * 数量2。
         */
        public static final String QTY_TWO = "QTY_TWO";
        /**
         * 建设规模数量2。
         */
        public static final String CON_SCALE_QTY2 = "CON_SCALE_QTY2";
        /**
         * 数量3。
         */
        public static final String QTY_THREE = "QTY_THREE";
        /**
         * 建设规模单位。
         */
        public static final String CON_SCALE_UOM_ID = "CON_SCALE_UOM_ID";
        /**
         * 项目概况。
         */
        public static final String PRJ_SITUATION = "PRJ_SITUATION";
        /**
         * 总投资（万）。
         */
        public static final String PRJ_TOTAL_INVEST = "PRJ_TOTAL_INVEST";
        /**
         * 投资来源。
         */
        public static final String INVESTMENT_SOURCE_ID = "INVESTMENT_SOURCE_ID";
        /**
         * 工程费用（万）。
         */
        public static final String PROJECT_AMT = "PROJECT_AMT";
        /**
         * 建安费（万）。
         */
        public static final String CONSTRUCT_AMT = "CONSTRUCT_AMT";
        /**
         * 设备费（万）。
         */
        public static final String EQUIP_AMT = "EQUIP_AMT";
        /**
         * 科研设备费(万)。
         */
        public static final String EQUIPMENT_COST = "EQUIPMENT_COST";
        /**
         * 工程其他费用(万)。
         */
        public static final String PROJECT_OTHER_AMT = "PROJECT_OTHER_AMT";
        /**
         * 土地拆迁费（万）。
         */
        public static final String LAND_AMT = "LAND_AMT";
        /**
         * 预备费(万)。
         */
        public static final String PREPARE_AMT = "PREPARE_AMT";
        /**
         * 立项申请材料。
         */
        public static final String PRJ_REQ_FILE = "PRJ_REQ_FILE";
        /**
         * 立项申请备注。
         */
        public static final String PRJ_REQ_REMARK = "PRJ_REQ_REMARK";
        /**
         * 计划完成日期。
         */
        public static final String COMPL_PLAN_DATE = "COMPL_PLAN_DATE";
        /**
         * 完成日期。
         */
        public static final String COMPL_DATE = "COMPL_DATE";
        /**
         * 编制人。
         */
        public static final String AUTHOR = "AUTHOR";
        /**
         * 工程项目编码。
         */
        public static final String CONSTRUCTION_PROJECT_CODE = "CONSTRUCTION_PROJECT_CODE";
        /**
         * 盖章的立项申请书。
         */
        public static final String STAMPED_PRJ_REQ_FILE = "STAMPED_PRJ_REQ_FILE";
        /**
         * 计划立项批复时间。
         */
        public static final String PRJ_REPLY_PLAN_DATE = "PRJ_REPLY_PLAN_DATE";
        /**
         * 立项批复日期。
         */
        public static final String PRJ_REPLY_DATE = "PRJ_REPLY_DATE";
        /**
         * 立项批复文号。
         */
        public static final String PRJ_REPLY_NO = "PRJ_REPLY_NO";
        /**
         * 批复文件。
         */
        public static final String REPLY_FILE = "REPLY_FILE";
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
         * 是否导入。
         */
        public static final String IS_OMPORT = "IS_OMPORT";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
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
    public PmPrjReq setId(String id) {
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
    public PmPrjReq setVer(Integer ver) {
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
    public PmPrjReq setTs(LocalDateTime ts) {
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
    public PmPrjReq setIsPreset(Boolean isPreset) {
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
    public PmPrjReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmPrjReq setLastModiUserId(String lastModiUserId) {
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
    public PmPrjReq setCode(String code) {
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
    public PmPrjReq setName(String name) {
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
    public PmPrjReq setRemark(String remark) {
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
    public PmPrjReq setLkWfInstId(String lkWfInstId) {
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
    public PmPrjReq setStatus(String status) {
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
    public PmPrjReq setCrtUserId(String crtUserId) {
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
    public PmPrjReq setCrtDeptId(String crtDeptId) {
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
    public PmPrjReq setCrtDt(LocalDateTime crtDt) {
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
     * 项目名称。
     */
    private String prjName;

    /**
     * 获取：项目名称。
     */
    public String getPrjName() {
        return this.prjName;
    }

    /**
     * 设置：项目名称。
     */
    public PmPrjReq setPrjName(String prjName) {
        if (this.prjName == null && prjName == null) {
            // 均为null，不做处理。
        } else if (this.prjName != null && prjName != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjName.compareTo(prjName) != 0) {
                this.prjName = prjName;
                if (!this.toUpdateCols.contains("PRJ_NAME")) {
                    this.toUpdateCols.add("PRJ_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjName = prjName;
            if (!this.toUpdateCols.contains("PRJ_NAME")) {
                this.toUpdateCols.add("PRJ_NAME");
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
    public PmPrjReq setPrjCode(String prjCode) {
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
    public PmPrjReq setCustomerUnit(String customerUnit) {
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
    public PmPrjReq setPrjManageModeId(String prjManageModeId) {
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
    public PmPrjReq setBaseLocationId(String baseLocationId) {
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
    public PmPrjReq setFloorArea(BigDecimal floorArea) {
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
    public PmPrjReq setProjectTypeId(String projectTypeId) {
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
    public PmPrjReq setConScaleTypeId(String conScaleTypeId) {
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
    public PmPrjReq setConScaleQty(BigDecimal conScaleQty) {
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
    public PmPrjReq setQtyOne(BigDecimal qtyOne) {
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
    public PmPrjReq setQtyTwo(Integer qtyTwo) {
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
    public PmPrjReq setConScaleQty2(BigDecimal conScaleQty2) {
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
    public PmPrjReq setQtyThree(Integer qtyThree) {
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
    public PmPrjReq setConScaleUomId(String conScaleUomId) {
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
    public PmPrjReq setPrjSituation(String prjSituation) {
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
    public PmPrjReq setPrjTotalInvest(BigDecimal prjTotalInvest) {
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
    public PmPrjReq setInvestmentSourceId(String investmentSourceId) {
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
    public PmPrjReq setProjectAmt(BigDecimal projectAmt) {
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
     * 建安费（万）。
     */
    private BigDecimal constructAmt;

    /**
     * 获取：建安费（万）。
     */
    public BigDecimal getConstructAmt() {
        return this.constructAmt;
    }

    /**
     * 设置：建安费（万）。
     */
    public PmPrjReq setConstructAmt(BigDecimal constructAmt) {
        if (this.constructAmt == null && constructAmt == null) {
            // 均为null，不做处理。
        } else if (this.constructAmt != null && constructAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.constructAmt.compareTo(constructAmt) != 0) {
                this.constructAmt = constructAmt;
                if (!this.toUpdateCols.contains("CONSTRUCT_AMT")) {
                    this.toUpdateCols.add("CONSTRUCT_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.constructAmt = constructAmt;
            if (!this.toUpdateCols.contains("CONSTRUCT_AMT")) {
                this.toUpdateCols.add("CONSTRUCT_AMT");
            }
        }
        return this;
    }

    /**
     * 设备费（万）。
     */
    private BigDecimal equipAmt;

    /**
     * 获取：设备费（万）。
     */
    public BigDecimal getEquipAmt() {
        return this.equipAmt;
    }

    /**
     * 设置：设备费（万）。
     */
    public PmPrjReq setEquipAmt(BigDecimal equipAmt) {
        if (this.equipAmt == null && equipAmt == null) {
            // 均为null，不做处理。
        } else if (this.equipAmt != null && equipAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.equipAmt.compareTo(equipAmt) != 0) {
                this.equipAmt = equipAmt;
                if (!this.toUpdateCols.contains("EQUIP_AMT")) {
                    this.toUpdateCols.add("EQUIP_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.equipAmt = equipAmt;
            if (!this.toUpdateCols.contains("EQUIP_AMT")) {
                this.toUpdateCols.add("EQUIP_AMT");
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
    public PmPrjReq setEquipmentCost(BigDecimal equipmentCost) {
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
    public PmPrjReq setProjectOtherAmt(BigDecimal projectOtherAmt) {
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
     * 土地拆迁费（万）。
     */
    private BigDecimal landAmt;

    /**
     * 获取：土地拆迁费（万）。
     */
    public BigDecimal getLandAmt() {
        return this.landAmt;
    }

    /**
     * 设置：土地拆迁费（万）。
     */
    public PmPrjReq setLandAmt(BigDecimal landAmt) {
        if (this.landAmt == null && landAmt == null) {
            // 均为null，不做处理。
        } else if (this.landAmt != null && landAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.landAmt.compareTo(landAmt) != 0) {
                this.landAmt = landAmt;
                if (!this.toUpdateCols.contains("LAND_AMT")) {
                    this.toUpdateCols.add("LAND_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.landAmt = landAmt;
            if (!this.toUpdateCols.contains("LAND_AMT")) {
                this.toUpdateCols.add("LAND_AMT");
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
    public PmPrjReq setPrepareAmt(BigDecimal prepareAmt) {
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
    public PmPrjReq setPrjReqFile(String prjReqFile) {
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
     * 立项申请备注。
     */
    private String prjReqRemark;

    /**
     * 获取：立项申请备注。
     */
    public String getPrjReqRemark() {
        return this.prjReqRemark;
    }

    /**
     * 设置：立项申请备注。
     */
    public PmPrjReq setPrjReqRemark(String prjReqRemark) {
        if (this.prjReqRemark == null && prjReqRemark == null) {
            // 均为null，不做处理。
        } else if (this.prjReqRemark != null && prjReqRemark != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjReqRemark.compareTo(prjReqRemark) != 0) {
                this.prjReqRemark = prjReqRemark;
                if (!this.toUpdateCols.contains("PRJ_REQ_REMARK")) {
                    this.toUpdateCols.add("PRJ_REQ_REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjReqRemark = prjReqRemark;
            if (!this.toUpdateCols.contains("PRJ_REQ_REMARK")) {
                this.toUpdateCols.add("PRJ_REQ_REMARK");
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
    public PmPrjReq setComplPlanDate(LocalDate complPlanDate) {
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
     * 完成日期。
     */
    private LocalDate complDate;

    /**
     * 获取：完成日期。
     */
    public LocalDate getComplDate() {
        return this.complDate;
    }

    /**
     * 设置：完成日期。
     */
    public PmPrjReq setComplDate(LocalDate complDate) {
        if (this.complDate == null && complDate == null) {
            // 均为null，不做处理。
        } else if (this.complDate != null && complDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.complDate.compareTo(complDate) != 0) {
                this.complDate = complDate;
                if (!this.toUpdateCols.contains("COMPL_DATE")) {
                    this.toUpdateCols.add("COMPL_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.complDate = complDate;
            if (!this.toUpdateCols.contains("COMPL_DATE")) {
                this.toUpdateCols.add("COMPL_DATE");
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
    public PmPrjReq setAuthor(String author) {
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
     * 工程项目编码。
     */
    private String constructionProjectCode;

    /**
     * 获取：工程项目编码。
     */
    public String getConstructionProjectCode() {
        return this.constructionProjectCode;
    }

    /**
     * 设置：工程项目编码。
     */
    public PmPrjReq setConstructionProjectCode(String constructionProjectCode) {
        if (this.constructionProjectCode == null && constructionProjectCode == null) {
            // 均为null，不做处理。
        } else if (this.constructionProjectCode != null && constructionProjectCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.constructionProjectCode.compareTo(constructionProjectCode) != 0) {
                this.constructionProjectCode = constructionProjectCode;
                if (!this.toUpdateCols.contains("CONSTRUCTION_PROJECT_CODE")) {
                    this.toUpdateCols.add("CONSTRUCTION_PROJECT_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.constructionProjectCode = constructionProjectCode;
            if (!this.toUpdateCols.contains("CONSTRUCTION_PROJECT_CODE")) {
                this.toUpdateCols.add("CONSTRUCTION_PROJECT_CODE");
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
    public PmPrjReq setStampedPrjReqFile(String stampedPrjReqFile) {
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
     * 计划立项批复时间。
     */
    private LocalDate prjReplyPlanDate;

    /**
     * 获取：计划立项批复时间。
     */
    public LocalDate getPrjReplyPlanDate() {
        return this.prjReplyPlanDate;
    }

    /**
     * 设置：计划立项批复时间。
     */
    public PmPrjReq setPrjReplyPlanDate(LocalDate prjReplyPlanDate) {
        if (this.prjReplyPlanDate == null && prjReplyPlanDate == null) {
            // 均为null，不做处理。
        } else if (this.prjReplyPlanDate != null && prjReplyPlanDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjReplyPlanDate.compareTo(prjReplyPlanDate) != 0) {
                this.prjReplyPlanDate = prjReplyPlanDate;
                if (!this.toUpdateCols.contains("PRJ_REPLY_PLAN_DATE")) {
                    this.toUpdateCols.add("PRJ_REPLY_PLAN_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjReplyPlanDate = prjReplyPlanDate;
            if (!this.toUpdateCols.contains("PRJ_REPLY_PLAN_DATE")) {
                this.toUpdateCols.add("PRJ_REPLY_PLAN_DATE");
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
    public PmPrjReq setPrjReplyDate(LocalDate prjReplyDate) {
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
    public PmPrjReq setPrjReplyNo(String prjReplyNo) {
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
     * 批复文件。
     */
    private String replyFile;

    /**
     * 获取：批复文件。
     */
    public String getReplyFile() {
        return this.replyFile;
    }

    /**
     * 设置：批复文件。
     */
    public PmPrjReq setReplyFile(String replyFile) {
        if (this.replyFile == null && replyFile == null) {
            // 均为null，不做处理。
        } else if (this.replyFile != null && replyFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.replyFile.compareTo(replyFile) != 0) {
                this.replyFile = replyFile;
                if (!this.toUpdateCols.contains("REPLY_FILE")) {
                    this.toUpdateCols.add("REPLY_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.replyFile = replyFile;
            if (!this.toUpdateCols.contains("REPLY_FILE")) {
                this.toUpdateCols.add("REPLY_FILE");
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
    public PmPrjReq setPrjEarlyUserId(String prjEarlyUserId) {
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
    public PmPrjReq setPrjDesignUserId(String prjDesignUserId) {
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
    public PmPrjReq setPrjCostUserId(String prjCostUserId) {
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
     * 是否导入。
     */
    private String isOmport;

    /**
     * 获取：是否导入。
     */
    public String getIsOmport() {
        return this.isOmport;
    }

    /**
     * 设置：是否导入。
     */
    public PmPrjReq setIsOmport(String isOmport) {
        if (this.isOmport == null && isOmport == null) {
            // 均为null，不做处理。
        } else if (this.isOmport != null && isOmport != null) {
            // 均非null，判定不等，再做处理：
            if (this.isOmport.compareTo(isOmport) != 0) {
                this.isOmport = isOmport;
                if (!this.toUpdateCols.contains("IS_OMPORT")) {
                    this.toUpdateCols.add("IS_OMPORT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isOmport = isOmport;
            if (!this.toUpdateCols.contains("IS_OMPORT")) {
                this.toUpdateCols.add("IS_OMPORT");
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
    public PmPrjReq setPmPrjId(String pmPrjId) {
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
    public static PmPrjReq newData() {
        PmPrjReq obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmPrjReq insertData() {
        PmPrjReq obj = modelHelper.insertData();
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
    public static PmPrjReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmPrjReq obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static PmPrjReq selectById(String id) {
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
    public static List<PmPrjReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmPrjReq> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmPrjReq> selectByIds(List<String> ids) {
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
    public static List<PmPrjReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmPrjReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PmPrjReq> selectByWhere(Where where) {
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
    public static PmPrjReq selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmPrjReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用PmPrjReq.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmptyList(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static PmPrjReq selectOneByWhere(Where where) {
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
    public static void copyCols(PmPrjReq fromModel, PmPrjReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(PmPrjReq fromModel, PmPrjReq toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}