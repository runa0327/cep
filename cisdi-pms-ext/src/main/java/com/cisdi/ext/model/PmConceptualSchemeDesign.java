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
 * 概念方案设计管理。
 */
public class PmConceptualSchemeDesign {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmConceptualSchemeDesign> modelHelper = new ModelHelper<>("PM_CONCEPTUAL_SCHEME_DESIGN", new PmConceptualSchemeDesign());

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

    public static final String ENT_CODE = "PM_CONCEPTUAL_SCHEME_DESIGN";
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
         * 数量3。
         */
        public static final String QTY_THREE = "QTY_THREE";
        /**
         * 项目概况。
         */
        public static final String PRJ_SITUATION = "PRJ_SITUATION";
        /**
         * 设计单位1。
         */
        public static final String DESIGN_UNIT_ONE = "DESIGN_UNIT_ONE";
        /**
         * 设计人1。
         */
        public static final String DESIGNER_ONE = "DESIGNER_ONE";
        /**
         * 效果图1。
         */
        public static final String DESIGN_SKETCH_FILE_ONE = "DESIGN_SKETCH_FILE_ONE";
        /**
         * 审批附件1。
         */
        public static final String APPROVE_FILE_ID_ONE = "APPROVE_FILE_ID_ONE";
        /**
         * 备注1。
         */
        public static final String REMARK_ONE = "REMARK_ONE";
        /**
         * 设计单位2。
         */
        public static final String DESIGN_UNIT_TWO = "DESIGN_UNIT_TWO";
        /**
         * 设计人2。
         */
        public static final String DESIGNER_TWO = "DESIGNER_TWO";
        /**
         * 效果图2。
         */
        public static final String DESIGN_SKETCH_FILE_TWO = "DESIGN_SKETCH_FILE_TWO";
        /**
         * 审批附件2。
         */
        public static final String APPROVE_FILE_ID_TWO = "APPROVE_FILE_ID_TWO";
        /**
         * 备注2。
         */
        public static final String REMARK_TWO = "REMARK_TWO";
        /**
         * 设计单位3。
         */
        public static final String DESIGN_UNIT_THREE = "DESIGN_UNIT_THREE";
        /**
         * 设计人3。
         */
        public static final String DESIGNER_THREE = "DESIGNER_THREE";
        /**
         * 效果图3。
         */
        public static final String DESIGN_SKETCH_FILE_THREE = "DESIGN_SKETCH_FILE_THREE";
        /**
         * 审批附件3。
         */
        public static final String APPROVE_FILE_ID_THREE = "APPROVE_FILE_ID_THREE";
        /**
         * 备注3。
         */
        public static final String REMARK_THREE = "REMARK_THREE";
        /**
         * 计划完成日期。
         */
        public static final String COMPL_PLAN_DATE = "COMPL_PLAN_DATE";
        /**
         * 评审实际完成日期。
         */
        public static final String REVIEW_ACTUAL_DATE = "REVIEW_ACTUAL_DATE";
        /**
         * 评审单位负责人。
         */
        public static final String REVIEW_UNIT_CHIEF = "REVIEW_UNIT_CHIEF";
        /**
         * 评审组织单位。
         */
        public static final String REVIEW_ORGANIZATION_UNIT = "REVIEW_ORGANIZATION_UNIT";
        /**
         * 评审单位联系方式。
         */
        public static final String REVIEW_UNIT_PHONE = "REVIEW_UNIT_PHONE";
        /**
         * 审批意见1。
         */
        public static final String APPROVAL_COMMENT_ONE = "APPROVAL_COMMENT_ONE";
        /**
         * 业主名称。
         */
        public static final String OWNER_NAME = "OWNER_NAME";
        /**
         * 对方负责人。
         */
        public static final String OTHER_RESPONSOR = "OTHER_RESPONSOR";
        /**
         * 对方联系方式。
         */
        public static final String OTHER_CONTACT_PHONE = "OTHER_CONTACT_PHONE";
        /**
         * 审批意见2。
         */
        public static final String APPROVAL_COMMENT_TWO = "APPROVAL_COMMENT_TWO";
        /**
         * 部门。
         */
        public static final String DEPT_NAME = "DEPT_NAME";
        /**
         * 代建单位项目负责人。
         */
        public static final String AGENT_BUILD_UNIT_RESPONSE = "AGENT_BUILD_UNIT_RESPONSE";
        /**
         * 对方联系电话号。
         */
        public static final String CONTACT_NUMBER = "CONTACT_NUMBER";
        /**
         * 审批意见3。
         */
        public static final String APPROVAL_COMMENT_THREE = "APPROVAL_COMMENT_THREE";
        /**
         * 建设单位项目负责人。
         */
        public static final String BUILD_UNIT_RESPONSE = "BUILD_UNIT_RESPONSE";
        /**
         * 相对方联系方式。
         */
        public static final String OPPO_SITE_CONTACT = "OPPO_SITE_CONTACT";
        /**
         * 审批附件4。
         */
        public static final String APPROVE_FILE_ID_FOUR = "APPROVE_FILE_ID_FOUR";
        /**
         * 核查备注。
         */
        public static final String CHECK_REMARK = "CHECK_REMARK";
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
    public PmConceptualSchemeDesign setId(String id) {
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
    public PmConceptualSchemeDesign setVer(Integer ver) {
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
    public PmConceptualSchemeDesign setTs(LocalDateTime ts) {
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
    public PmConceptualSchemeDesign setIsPreset(Boolean isPreset) {
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
    public PmConceptualSchemeDesign setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmConceptualSchemeDesign setLastModiUserId(String lastModiUserId) {
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
    public PmConceptualSchemeDesign setCode(String code) {
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
    public PmConceptualSchemeDesign setName(String name) {
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
    public PmConceptualSchemeDesign setRemark(String remark) {
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
    public PmConceptualSchemeDesign setLkWfInstId(String lkWfInstId) {
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
    public PmConceptualSchemeDesign setPmPrjId(String pmPrjId) {
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
    public PmConceptualSchemeDesign setStatus(String status) {
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
    public PmConceptualSchemeDesign setPrjCode(String prjCode) {
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
    public PmConceptualSchemeDesign setCustomerUnit(String customerUnit) {
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
    public PmConceptualSchemeDesign setCrtUserId(String crtUserId) {
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
    public PmConceptualSchemeDesign setCrtDeptId(String crtDeptId) {
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
    public PmConceptualSchemeDesign setPrjManageModeId(String prjManageModeId) {
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
    public PmConceptualSchemeDesign setBaseLocationId(String baseLocationId) {
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
    public PmConceptualSchemeDesign setCrtDt(LocalDateTime crtDt) {
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
    public PmConceptualSchemeDesign setFloorArea(BigDecimal floorArea) {
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
    public PmConceptualSchemeDesign setProjectTypeId(String projectTypeId) {
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
    public PmConceptualSchemeDesign setConScaleTypeId(String conScaleTypeId) {
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
    public PmConceptualSchemeDesign setConScaleQty(BigDecimal conScaleQty) {
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
    public PmConceptualSchemeDesign setQtyOne(BigDecimal qtyOne) {
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
    public PmConceptualSchemeDesign setQtyTwo(Integer qtyTwo) {
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
    public PmConceptualSchemeDesign setConScaleQty2(BigDecimal conScaleQty2) {
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
    public PmConceptualSchemeDesign setConScaleUomId(String conScaleUomId) {
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
    public PmConceptualSchemeDesign setBuildYears(BigDecimal buildYears) {
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
    public PmConceptualSchemeDesign setQtyThree(Integer qtyThree) {
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
    public PmConceptualSchemeDesign setPrjSituation(String prjSituation) {
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
     * 设计单位1。
     */
    private String designUnitOne;

    /**
     * 获取：设计单位1。
     */
    public String getDesignUnitOne() {
        return this.designUnitOne;
    }

    /**
     * 设置：设计单位1。
     */
    public PmConceptualSchemeDesign setDesignUnitOne(String designUnitOne) {
        if (this.designUnitOne == null && designUnitOne == null) {
            // 均为null，不做处理。
        } else if (this.designUnitOne != null && designUnitOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.designUnitOne.compareTo(designUnitOne) != 0) {
                this.designUnitOne = designUnitOne;
                if (!this.toUpdateCols.contains("DESIGN_UNIT_ONE")) {
                    this.toUpdateCols.add("DESIGN_UNIT_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designUnitOne = designUnitOne;
            if (!this.toUpdateCols.contains("DESIGN_UNIT_ONE")) {
                this.toUpdateCols.add("DESIGN_UNIT_ONE");
            }
        }
        return this;
    }

    /**
     * 设计人1。
     */
    private String designerOne;

    /**
     * 获取：设计人1。
     */
    public String getDesignerOne() {
        return this.designerOne;
    }

    /**
     * 设置：设计人1。
     */
    public PmConceptualSchemeDesign setDesignerOne(String designerOne) {
        if (this.designerOne == null && designerOne == null) {
            // 均为null，不做处理。
        } else if (this.designerOne != null && designerOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.designerOne.compareTo(designerOne) != 0) {
                this.designerOne = designerOne;
                if (!this.toUpdateCols.contains("DESIGNER_ONE")) {
                    this.toUpdateCols.add("DESIGNER_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designerOne = designerOne;
            if (!this.toUpdateCols.contains("DESIGNER_ONE")) {
                this.toUpdateCols.add("DESIGNER_ONE");
            }
        }
        return this;
    }

    /**
     * 效果图1。
     */
    private String designSketchFileOne;

    /**
     * 获取：效果图1。
     */
    public String getDesignSketchFileOne() {
        return this.designSketchFileOne;
    }

    /**
     * 设置：效果图1。
     */
    public PmConceptualSchemeDesign setDesignSketchFileOne(String designSketchFileOne) {
        if (this.designSketchFileOne == null && designSketchFileOne == null) {
            // 均为null，不做处理。
        } else if (this.designSketchFileOne != null && designSketchFileOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.designSketchFileOne.compareTo(designSketchFileOne) != 0) {
                this.designSketchFileOne = designSketchFileOne;
                if (!this.toUpdateCols.contains("DESIGN_SKETCH_FILE_ONE")) {
                    this.toUpdateCols.add("DESIGN_SKETCH_FILE_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designSketchFileOne = designSketchFileOne;
            if (!this.toUpdateCols.contains("DESIGN_SKETCH_FILE_ONE")) {
                this.toUpdateCols.add("DESIGN_SKETCH_FILE_ONE");
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
    public PmConceptualSchemeDesign setApproveFileIdOne(String approveFileIdOne) {
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
     * 备注1。
     */
    private String remarkOne;

    /**
     * 获取：备注1。
     */
    public String getRemarkOne() {
        return this.remarkOne;
    }

    /**
     * 设置：备注1。
     */
    public PmConceptualSchemeDesign setRemarkOne(String remarkOne) {
        if (this.remarkOne == null && remarkOne == null) {
            // 均为null，不做处理。
        } else if (this.remarkOne != null && remarkOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.remarkOne.compareTo(remarkOne) != 0) {
                this.remarkOne = remarkOne;
                if (!this.toUpdateCols.contains("REMARK_ONE")) {
                    this.toUpdateCols.add("REMARK_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remarkOne = remarkOne;
            if (!this.toUpdateCols.contains("REMARK_ONE")) {
                this.toUpdateCols.add("REMARK_ONE");
            }
        }
        return this;
    }

    /**
     * 设计单位2。
     */
    private String designUnitTwo;

    /**
     * 获取：设计单位2。
     */
    public String getDesignUnitTwo() {
        return this.designUnitTwo;
    }

    /**
     * 设置：设计单位2。
     */
    public PmConceptualSchemeDesign setDesignUnitTwo(String designUnitTwo) {
        if (this.designUnitTwo == null && designUnitTwo == null) {
            // 均为null，不做处理。
        } else if (this.designUnitTwo != null && designUnitTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.designUnitTwo.compareTo(designUnitTwo) != 0) {
                this.designUnitTwo = designUnitTwo;
                if (!this.toUpdateCols.contains("DESIGN_UNIT_TWO")) {
                    this.toUpdateCols.add("DESIGN_UNIT_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designUnitTwo = designUnitTwo;
            if (!this.toUpdateCols.contains("DESIGN_UNIT_TWO")) {
                this.toUpdateCols.add("DESIGN_UNIT_TWO");
            }
        }
        return this;
    }

    /**
     * 设计人2。
     */
    private String designerTwo;

    /**
     * 获取：设计人2。
     */
    public String getDesignerTwo() {
        return this.designerTwo;
    }

    /**
     * 设置：设计人2。
     */
    public PmConceptualSchemeDesign setDesignerTwo(String designerTwo) {
        if (this.designerTwo == null && designerTwo == null) {
            // 均为null，不做处理。
        } else if (this.designerTwo != null && designerTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.designerTwo.compareTo(designerTwo) != 0) {
                this.designerTwo = designerTwo;
                if (!this.toUpdateCols.contains("DESIGNER_TWO")) {
                    this.toUpdateCols.add("DESIGNER_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designerTwo = designerTwo;
            if (!this.toUpdateCols.contains("DESIGNER_TWO")) {
                this.toUpdateCols.add("DESIGNER_TWO");
            }
        }
        return this;
    }

    /**
     * 效果图2。
     */
    private String designSketchFileTwo;

    /**
     * 获取：效果图2。
     */
    public String getDesignSketchFileTwo() {
        return this.designSketchFileTwo;
    }

    /**
     * 设置：效果图2。
     */
    public PmConceptualSchemeDesign setDesignSketchFileTwo(String designSketchFileTwo) {
        if (this.designSketchFileTwo == null && designSketchFileTwo == null) {
            // 均为null，不做处理。
        } else if (this.designSketchFileTwo != null && designSketchFileTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.designSketchFileTwo.compareTo(designSketchFileTwo) != 0) {
                this.designSketchFileTwo = designSketchFileTwo;
                if (!this.toUpdateCols.contains("DESIGN_SKETCH_FILE_TWO")) {
                    this.toUpdateCols.add("DESIGN_SKETCH_FILE_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designSketchFileTwo = designSketchFileTwo;
            if (!this.toUpdateCols.contains("DESIGN_SKETCH_FILE_TWO")) {
                this.toUpdateCols.add("DESIGN_SKETCH_FILE_TWO");
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
    public PmConceptualSchemeDesign setApproveFileIdTwo(String approveFileIdTwo) {
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
     * 备注2。
     */
    private String remarkTwo;

    /**
     * 获取：备注2。
     */
    public String getRemarkTwo() {
        return this.remarkTwo;
    }

    /**
     * 设置：备注2。
     */
    public PmConceptualSchemeDesign setRemarkTwo(String remarkTwo) {
        if (this.remarkTwo == null && remarkTwo == null) {
            // 均为null，不做处理。
        } else if (this.remarkTwo != null && remarkTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.remarkTwo.compareTo(remarkTwo) != 0) {
                this.remarkTwo = remarkTwo;
                if (!this.toUpdateCols.contains("REMARK_TWO")) {
                    this.toUpdateCols.add("REMARK_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remarkTwo = remarkTwo;
            if (!this.toUpdateCols.contains("REMARK_TWO")) {
                this.toUpdateCols.add("REMARK_TWO");
            }
        }
        return this;
    }

    /**
     * 设计单位3。
     */
    private String designUnitThree;

    /**
     * 获取：设计单位3。
     */
    public String getDesignUnitThree() {
        return this.designUnitThree;
    }

    /**
     * 设置：设计单位3。
     */
    public PmConceptualSchemeDesign setDesignUnitThree(String designUnitThree) {
        if (this.designUnitThree == null && designUnitThree == null) {
            // 均为null，不做处理。
        } else if (this.designUnitThree != null && designUnitThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.designUnitThree.compareTo(designUnitThree) != 0) {
                this.designUnitThree = designUnitThree;
                if (!this.toUpdateCols.contains("DESIGN_UNIT_THREE")) {
                    this.toUpdateCols.add("DESIGN_UNIT_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designUnitThree = designUnitThree;
            if (!this.toUpdateCols.contains("DESIGN_UNIT_THREE")) {
                this.toUpdateCols.add("DESIGN_UNIT_THREE");
            }
        }
        return this;
    }

    /**
     * 设计人3。
     */
    private String designerThree;

    /**
     * 获取：设计人3。
     */
    public String getDesignerThree() {
        return this.designerThree;
    }

    /**
     * 设置：设计人3。
     */
    public PmConceptualSchemeDesign setDesignerThree(String designerThree) {
        if (this.designerThree == null && designerThree == null) {
            // 均为null，不做处理。
        } else if (this.designerThree != null && designerThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.designerThree.compareTo(designerThree) != 0) {
                this.designerThree = designerThree;
                if (!this.toUpdateCols.contains("DESIGNER_THREE")) {
                    this.toUpdateCols.add("DESIGNER_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designerThree = designerThree;
            if (!this.toUpdateCols.contains("DESIGNER_THREE")) {
                this.toUpdateCols.add("DESIGNER_THREE");
            }
        }
        return this;
    }

    /**
     * 效果图3。
     */
    private String designSketchFileThree;

    /**
     * 获取：效果图3。
     */
    public String getDesignSketchFileThree() {
        return this.designSketchFileThree;
    }

    /**
     * 设置：效果图3。
     */
    public PmConceptualSchemeDesign setDesignSketchFileThree(String designSketchFileThree) {
        if (this.designSketchFileThree == null && designSketchFileThree == null) {
            // 均为null，不做处理。
        } else if (this.designSketchFileThree != null && designSketchFileThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.designSketchFileThree.compareTo(designSketchFileThree) != 0) {
                this.designSketchFileThree = designSketchFileThree;
                if (!this.toUpdateCols.contains("DESIGN_SKETCH_FILE_THREE")) {
                    this.toUpdateCols.add("DESIGN_SKETCH_FILE_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designSketchFileThree = designSketchFileThree;
            if (!this.toUpdateCols.contains("DESIGN_SKETCH_FILE_THREE")) {
                this.toUpdateCols.add("DESIGN_SKETCH_FILE_THREE");
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
    public PmConceptualSchemeDesign setApproveFileIdThree(String approveFileIdThree) {
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
     * 备注3。
     */
    private String remarkThree;

    /**
     * 获取：备注3。
     */
    public String getRemarkThree() {
        return this.remarkThree;
    }

    /**
     * 设置：备注3。
     */
    public PmConceptualSchemeDesign setRemarkThree(String remarkThree) {
        if (this.remarkThree == null && remarkThree == null) {
            // 均为null，不做处理。
        } else if (this.remarkThree != null && remarkThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.remarkThree.compareTo(remarkThree) != 0) {
                this.remarkThree = remarkThree;
                if (!this.toUpdateCols.contains("REMARK_THREE")) {
                    this.toUpdateCols.add("REMARK_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remarkThree = remarkThree;
            if (!this.toUpdateCols.contains("REMARK_THREE")) {
                this.toUpdateCols.add("REMARK_THREE");
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
    public PmConceptualSchemeDesign setComplPlanDate(LocalDate complPlanDate) {
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
     * 评审实际完成日期。
     */
    private LocalDate reviewActualDate;

    /**
     * 获取：评审实际完成日期。
     */
    public LocalDate getReviewActualDate() {
        return this.reviewActualDate;
    }

    /**
     * 设置：评审实际完成日期。
     */
    public PmConceptualSchemeDesign setReviewActualDate(LocalDate reviewActualDate) {
        if (this.reviewActualDate == null && reviewActualDate == null) {
            // 均为null，不做处理。
        } else if (this.reviewActualDate != null && reviewActualDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.reviewActualDate.compareTo(reviewActualDate) != 0) {
                this.reviewActualDate = reviewActualDate;
                if (!this.toUpdateCols.contains("REVIEW_ACTUAL_DATE")) {
                    this.toUpdateCols.add("REVIEW_ACTUAL_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.reviewActualDate = reviewActualDate;
            if (!this.toUpdateCols.contains("REVIEW_ACTUAL_DATE")) {
                this.toUpdateCols.add("REVIEW_ACTUAL_DATE");
            }
        }
        return this;
    }

    /**
     * 评审单位负责人。
     */
    private String reviewUnitChief;

    /**
     * 获取：评审单位负责人。
     */
    public String getReviewUnitChief() {
        return this.reviewUnitChief;
    }

    /**
     * 设置：评审单位负责人。
     */
    public PmConceptualSchemeDesign setReviewUnitChief(String reviewUnitChief) {
        if (this.reviewUnitChief == null && reviewUnitChief == null) {
            // 均为null，不做处理。
        } else if (this.reviewUnitChief != null && reviewUnitChief != null) {
            // 均非null，判定不等，再做处理：
            if (this.reviewUnitChief.compareTo(reviewUnitChief) != 0) {
                this.reviewUnitChief = reviewUnitChief;
                if (!this.toUpdateCols.contains("REVIEW_UNIT_CHIEF")) {
                    this.toUpdateCols.add("REVIEW_UNIT_CHIEF");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.reviewUnitChief = reviewUnitChief;
            if (!this.toUpdateCols.contains("REVIEW_UNIT_CHIEF")) {
                this.toUpdateCols.add("REVIEW_UNIT_CHIEF");
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
    public PmConceptualSchemeDesign setReviewOrganizationUnit(String reviewOrganizationUnit) {
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
    public PmConceptualSchemeDesign setReviewUnitPhone(String reviewUnitPhone) {
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
     * 审批意见1。
     */
    private String approvalCommentOne;

    /**
     * 获取：审批意见1。
     */
    public String getApprovalCommentOne() {
        return this.approvalCommentOne;
    }

    /**
     * 设置：审批意见1。
     */
    public PmConceptualSchemeDesign setApprovalCommentOne(String approvalCommentOne) {
        if (this.approvalCommentOne == null && approvalCommentOne == null) {
            // 均为null，不做处理。
        } else if (this.approvalCommentOne != null && approvalCommentOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalCommentOne.compareTo(approvalCommentOne) != 0) {
                this.approvalCommentOne = approvalCommentOne;
                if (!this.toUpdateCols.contains("APPROVAL_COMMENT_ONE")) {
                    this.toUpdateCols.add("APPROVAL_COMMENT_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalCommentOne = approvalCommentOne;
            if (!this.toUpdateCols.contains("APPROVAL_COMMENT_ONE")) {
                this.toUpdateCols.add("APPROVAL_COMMENT_ONE");
            }
        }
        return this;
    }

    /**
     * 业主名称。
     */
    private String ownerName;

    /**
     * 获取：业主名称。
     */
    public String getOwnerName() {
        return this.ownerName;
    }

    /**
     * 设置：业主名称。
     */
    public PmConceptualSchemeDesign setOwnerName(String ownerName) {
        if (this.ownerName == null && ownerName == null) {
            // 均为null，不做处理。
        } else if (this.ownerName != null && ownerName != null) {
            // 均非null，判定不等，再做处理：
            if (this.ownerName.compareTo(ownerName) != 0) {
                this.ownerName = ownerName;
                if (!this.toUpdateCols.contains("OWNER_NAME")) {
                    this.toUpdateCols.add("OWNER_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ownerName = ownerName;
            if (!this.toUpdateCols.contains("OWNER_NAME")) {
                this.toUpdateCols.add("OWNER_NAME");
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
    public PmConceptualSchemeDesign setOtherResponsor(String otherResponsor) {
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
     * 对方联系方式。
     */
    private String otherContactPhone;

    /**
     * 获取：对方联系方式。
     */
    public String getOtherContactPhone() {
        return this.otherContactPhone;
    }

    /**
     * 设置：对方联系方式。
     */
    public PmConceptualSchemeDesign setOtherContactPhone(String otherContactPhone) {
        if (this.otherContactPhone == null && otherContactPhone == null) {
            // 均为null，不做处理。
        } else if (this.otherContactPhone != null && otherContactPhone != null) {
            // 均非null，判定不等，再做处理：
            if (this.otherContactPhone.compareTo(otherContactPhone) != 0) {
                this.otherContactPhone = otherContactPhone;
                if (!this.toUpdateCols.contains("OTHER_CONTACT_PHONE")) {
                    this.toUpdateCols.add("OTHER_CONTACT_PHONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.otherContactPhone = otherContactPhone;
            if (!this.toUpdateCols.contains("OTHER_CONTACT_PHONE")) {
                this.toUpdateCols.add("OTHER_CONTACT_PHONE");
            }
        }
        return this;
    }

    /**
     * 审批意见2。
     */
    private String approvalCommentTwo;

    /**
     * 获取：审批意见2。
     */
    public String getApprovalCommentTwo() {
        return this.approvalCommentTwo;
    }

    /**
     * 设置：审批意见2。
     */
    public PmConceptualSchemeDesign setApprovalCommentTwo(String approvalCommentTwo) {
        if (this.approvalCommentTwo == null && approvalCommentTwo == null) {
            // 均为null，不做处理。
        } else if (this.approvalCommentTwo != null && approvalCommentTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalCommentTwo.compareTo(approvalCommentTwo) != 0) {
                this.approvalCommentTwo = approvalCommentTwo;
                if (!this.toUpdateCols.contains("APPROVAL_COMMENT_TWO")) {
                    this.toUpdateCols.add("APPROVAL_COMMENT_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalCommentTwo = approvalCommentTwo;
            if (!this.toUpdateCols.contains("APPROVAL_COMMENT_TWO")) {
                this.toUpdateCols.add("APPROVAL_COMMENT_TWO");
            }
        }
        return this;
    }

    /**
     * 部门。
     */
    private String deptName;

    /**
     * 获取：部门。
     */
    public String getDeptName() {
        return this.deptName;
    }

    /**
     * 设置：部门。
     */
    public PmConceptualSchemeDesign setDeptName(String deptName) {
        if (this.deptName == null && deptName == null) {
            // 均为null，不做处理。
        } else if (this.deptName != null && deptName != null) {
            // 均非null，判定不等，再做处理：
            if (this.deptName.compareTo(deptName) != 0) {
                this.deptName = deptName;
                if (!this.toUpdateCols.contains("DEPT_NAME")) {
                    this.toUpdateCols.add("DEPT_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.deptName = deptName;
            if (!this.toUpdateCols.contains("DEPT_NAME")) {
                this.toUpdateCols.add("DEPT_NAME");
            }
        }
        return this;
    }

    /**
     * 代建单位项目负责人。
     */
    private String agentBuildUnitResponse;

    /**
     * 获取：代建单位项目负责人。
     */
    public String getAgentBuildUnitResponse() {
        return this.agentBuildUnitResponse;
    }

    /**
     * 设置：代建单位项目负责人。
     */
    public PmConceptualSchemeDesign setAgentBuildUnitResponse(String agentBuildUnitResponse) {
        if (this.agentBuildUnitResponse == null && agentBuildUnitResponse == null) {
            // 均为null，不做处理。
        } else if (this.agentBuildUnitResponse != null && agentBuildUnitResponse != null) {
            // 均非null，判定不等，再做处理：
            if (this.agentBuildUnitResponse.compareTo(agentBuildUnitResponse) != 0) {
                this.agentBuildUnitResponse = agentBuildUnitResponse;
                if (!this.toUpdateCols.contains("AGENT_BUILD_UNIT_RESPONSE")) {
                    this.toUpdateCols.add("AGENT_BUILD_UNIT_RESPONSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.agentBuildUnitResponse = agentBuildUnitResponse;
            if (!this.toUpdateCols.contains("AGENT_BUILD_UNIT_RESPONSE")) {
                this.toUpdateCols.add("AGENT_BUILD_UNIT_RESPONSE");
            }
        }
        return this;
    }

    /**
     * 对方联系电话号。
     */
    private String contactNumber;

    /**
     * 获取：对方联系电话号。
     */
    public String getContactNumber() {
        return this.contactNumber;
    }

    /**
     * 设置：对方联系电话号。
     */
    public PmConceptualSchemeDesign setContactNumber(String contactNumber) {
        if (this.contactNumber == null && contactNumber == null) {
            // 均为null，不做处理。
        } else if (this.contactNumber != null && contactNumber != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactNumber.compareTo(contactNumber) != 0) {
                this.contactNumber = contactNumber;
                if (!this.toUpdateCols.contains("CONTACT_NUMBER")) {
                    this.toUpdateCols.add("CONTACT_NUMBER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactNumber = contactNumber;
            if (!this.toUpdateCols.contains("CONTACT_NUMBER")) {
                this.toUpdateCols.add("CONTACT_NUMBER");
            }
        }
        return this;
    }

    /**
     * 审批意见3。
     */
    private String approvalCommentThree;

    /**
     * 获取：审批意见3。
     */
    public String getApprovalCommentThree() {
        return this.approvalCommentThree;
    }

    /**
     * 设置：审批意见3。
     */
    public PmConceptualSchemeDesign setApprovalCommentThree(String approvalCommentThree) {
        if (this.approvalCommentThree == null && approvalCommentThree == null) {
            // 均为null，不做处理。
        } else if (this.approvalCommentThree != null && approvalCommentThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalCommentThree.compareTo(approvalCommentThree) != 0) {
                this.approvalCommentThree = approvalCommentThree;
                if (!this.toUpdateCols.contains("APPROVAL_COMMENT_THREE")) {
                    this.toUpdateCols.add("APPROVAL_COMMENT_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalCommentThree = approvalCommentThree;
            if (!this.toUpdateCols.contains("APPROVAL_COMMENT_THREE")) {
                this.toUpdateCols.add("APPROVAL_COMMENT_THREE");
            }
        }
        return this;
    }

    /**
     * 建设单位项目负责人。
     */
    private String buildUnitResponse;

    /**
     * 获取：建设单位项目负责人。
     */
    public String getBuildUnitResponse() {
        return this.buildUnitResponse;
    }

    /**
     * 设置：建设单位项目负责人。
     */
    public PmConceptualSchemeDesign setBuildUnitResponse(String buildUnitResponse) {
        if (this.buildUnitResponse == null && buildUnitResponse == null) {
            // 均为null，不做处理。
        } else if (this.buildUnitResponse != null && buildUnitResponse != null) {
            // 均非null，判定不等，再做处理：
            if (this.buildUnitResponse.compareTo(buildUnitResponse) != 0) {
                this.buildUnitResponse = buildUnitResponse;
                if (!this.toUpdateCols.contains("BUILD_UNIT_RESPONSE")) {
                    this.toUpdateCols.add("BUILD_UNIT_RESPONSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.buildUnitResponse = buildUnitResponse;
            if (!this.toUpdateCols.contains("BUILD_UNIT_RESPONSE")) {
                this.toUpdateCols.add("BUILD_UNIT_RESPONSE");
            }
        }
        return this;
    }

    /**
     * 相对方联系方式。
     */
    private String oppoSiteContact;

    /**
     * 获取：相对方联系方式。
     */
    public String getOppoSiteContact() {
        return this.oppoSiteContact;
    }

    /**
     * 设置：相对方联系方式。
     */
    public PmConceptualSchemeDesign setOppoSiteContact(String oppoSiteContact) {
        if (this.oppoSiteContact == null && oppoSiteContact == null) {
            // 均为null，不做处理。
        } else if (this.oppoSiteContact != null && oppoSiteContact != null) {
            // 均非null，判定不等，再做处理：
            if (this.oppoSiteContact.compareTo(oppoSiteContact) != 0) {
                this.oppoSiteContact = oppoSiteContact;
                if (!this.toUpdateCols.contains("OPPO_SITE_CONTACT")) {
                    this.toUpdateCols.add("OPPO_SITE_CONTACT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.oppoSiteContact = oppoSiteContact;
            if (!this.toUpdateCols.contains("OPPO_SITE_CONTACT")) {
                this.toUpdateCols.add("OPPO_SITE_CONTACT");
            }
        }
        return this;
    }

    /**
     * 审批附件4。
     */
    private String approveFileIdFour;

    /**
     * 获取：审批附件4。
     */
    public String getApproveFileIdFour() {
        return this.approveFileIdFour;
    }

    /**
     * 设置：审批附件4。
     */
    public PmConceptualSchemeDesign setApproveFileIdFour(String approveFileIdFour) {
        if (this.approveFileIdFour == null && approveFileIdFour == null) {
            // 均为null，不做处理。
        } else if (this.approveFileIdFour != null && approveFileIdFour != null) {
            // 均非null，判定不等，再做处理：
            if (this.approveFileIdFour.compareTo(approveFileIdFour) != 0) {
                this.approveFileIdFour = approveFileIdFour;
                if (!this.toUpdateCols.contains("APPROVE_FILE_ID_FOUR")) {
                    this.toUpdateCols.add("APPROVE_FILE_ID_FOUR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approveFileIdFour = approveFileIdFour;
            if (!this.toUpdateCols.contains("APPROVE_FILE_ID_FOUR")) {
                this.toUpdateCols.add("APPROVE_FILE_ID_FOUR");
            }
        }
        return this;
    }

    /**
     * 核查备注。
     */
    private String checkRemark;

    /**
     * 获取：核查备注。
     */
    public String getCheckRemark() {
        return this.checkRemark;
    }

    /**
     * 设置：核查备注。
     */
    public PmConceptualSchemeDesign setCheckRemark(String checkRemark) {
        if (this.checkRemark == null && checkRemark == null) {
            // 均为null，不做处理。
        } else if (this.checkRemark != null && checkRemark != null) {
            // 均非null，判定不等，再做处理：
            if (this.checkRemark.compareTo(checkRemark) != 0) {
                this.checkRemark = checkRemark;
                if (!this.toUpdateCols.contains("CHECK_REMARK")) {
                    this.toUpdateCols.add("CHECK_REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.checkRemark = checkRemark;
            if (!this.toUpdateCols.contains("CHECK_REMARK")) {
                this.toUpdateCols.add("CHECK_REMARK");
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
    public static PmConceptualSchemeDesign newData() {
        PmConceptualSchemeDesign obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmConceptualSchemeDesign insertData() {
        PmConceptualSchemeDesign obj = modelHelper.insertData();
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
    public static PmConceptualSchemeDesign selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmConceptualSchemeDesign obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<PmConceptualSchemeDesign> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmConceptualSchemeDesign> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<PmConceptualSchemeDesign> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmConceptualSchemeDesign> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(PmConceptualSchemeDesign fromModel, PmConceptualSchemeDesign toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}