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
 * 工程规划许可证申请。
 */
public class PmPrjPlanningPermitReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmPrjPlanningPermitReq> modelHelper = new ModelHelper<>("PM_PRJ_PLANNING_PERMIT_REQ", new PmPrjPlanningPermitReq());

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

    public static final String ENT_CODE = "PM_PRJ_PLANNING_PERMIT_REQ";
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
         * 招采项目备案及归档。
         */
        public static final String PM_BID_KEEP_FILE_REQ_ID = "PM_BID_KEEP_FILE_REQ_ID";
        /**
         * 状态1。
         */
        public static final String STATUS_ONE = "STATUS_ONE";
        /**
         * 业主单位1。
         */
        public static final String CUSTOMER_UNIT_ONE = "CUSTOMER_UNIT_ONE";
        /**
         * 采购经办人。
         */
        public static final String PROCURE_USER_ID = "PROCURE_USER_ID";
        /**
         * 中标单位1。
         */
        public static final String WIN_BID_UNIT_ONE = "WIN_BID_UNIT_ONE";
        /**
         * 联系人1。
         */
        public static final String CONTACTS_ONE = "CONTACTS_ONE";
        /**
         * 联系人电话1。
         */
        public static final String CONTACT_MOBILE_ONE = "CONTACT_MOBILE_ONE";
        /**
         * 计划完成日期。
         */
        public static final String COMPL_PLAN_DATE = "COMPL_PLAN_DATE";
        /**
         * 申请时间。
         */
        public static final String APPLY_TIME = "APPLY_TIME";
        /**
         * 申请人。
         */
        public static final String APPLICANT = "APPLICANT";
        /**
         * 环评申请材料。
         */
        public static final String EIA_REQ_FILE = "EIA_REQ_FILE";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 专家评审计划完成日期。
         */
        public static final String EXPERT_COMPL_PLAN_DATE = "EXPERT_COMPL_PLAN_DATE";
        /**
         * 专家评审实际完成日期。
         */
        public static final String EXPERT_COMPL_ACTUAL_DATE = "EXPERT_COMPL_ACTUAL_DATE";
        /**
         * 评审单位负责人。
         */
        public static final String REVIEW_UNIT_CHIEF = "REVIEW_UNIT_CHIEF";
        /**
         * 评审单位。
         */
        public static final String REVIEW_UNIT = "REVIEW_UNIT";
        /**
         * 评审单位联系方式。
         */
        public static final String REVIEW_UNIT_PHONE = "REVIEW_UNIT_PHONE";
        /**
         * 评审稿文件。
         */
        public static final String REVIEW_REPORT_FILE = "REVIEW_REPORT_FILE";
        /**
         * 评审报告文件。
         */
        public static final String REVIEW_DRAFT_FILE = "REVIEW_DRAFT_FILE";
        /**
         * 修编稿文件。
         */
        public static final String REVISION_FILE = "REVISION_FILE";
        /**
         * 评审及修编说明。
         */
        public static final String REVIEW_AND_REVISION_REMARK = "REVIEW_AND_REVISION_REMARK";
        /**
         * 计划完成日期。
         */
        public static final String COMPL_PLAN_DATE_CHECK = "COMPL_PLAN_DATE_CHECK";
        /**
         * 完成日期。
         */
        public static final String COMPL_DATE = "COMPL_DATE";
        /**
         * 编制负责人。
         */
        public static final String AUTHOR_RESPONSE = "AUTHOR_RESPONSE";
        /**
         * 方案检查ppt文件。
         */
        public static final String PLAN_CHECK_PPT_FILE_GROUP_ID = "PLAN_CHECK_PPT_FILE_GROUP_ID";
        /**
         * 管理局局务预审会会议时间。
         */
        public static final String ADMIN_MEETING_DATE = "ADMIN_MEETING_DATE";
        /**
         * 管理局预审会是否通过。
         */
        public static final String ADMIN_IS_PASS = "ADMIN_IS_PASS";
        /**
         * 管理局预审会市委会意见。
         */
        public static final String ADMIN_COMMITTEE_OPINON_FILE_GROUP_ID = "ADMIN_COMMITTEE_OPINON_FILE_GROUP_ID";
        /**
         * 规委会预审会会议时间。
         */
        public static final String PLAN_MEETING_DATE = "PLAN_MEETING_DATE";
        /**
         * 规委会预审会是否通过。
         */
        public static final String PLAN_IS_PASS = "PLAN_IS_PASS";
        /**
         * 规委会预审会市委会意见。
         */
        public static final String PLAN_COMMITTEE_OPINON_FILE_GROUP_ID = "PLAN_COMMITTEE_OPINON_FILE_GROUP_ID";
        /**
         * 分管副市长预审会会议时间。
         */
        public static final String DEPUTY_MAYOR_MEETING_DATE = "DEPUTY_MAYOR_MEETING_DATE";
        /**
         * 分管副市长预审会是否通过。
         */
        public static final String DEPUTY_MAYOR_IS_PASS = "DEPUTY_MAYOR_IS_PASS";
        /**
         * 分管副市长预审会市委会意见。
         */
        public static final String DEPUTY_MAYOR_COMMITTEE_OPINON_FILE_GROUP_ID = "DEPUTY_MAYOR_COMMITTEE_OPINON_FILE_GROUP_ID";
        /**
         * 分管副市长和市长预审会会议时间。
         */
        public static final String MAYOR_MEETING_DATE = "MAYOR_MEETING_DATE";
        /**
         * 分管副市长和市长预审会是否通过。
         */
        public static final String MAYOR_IS_PASS = "MAYOR_IS_PASS";
        /**
         * 分管副市长和市长预审会市委会意见。
         */
        public static final String MAYOR_COMMITTEE_OPINON_FILE_GROUP_ID = "MAYOR_COMMITTEE_OPINON_FILE_GROUP_ID";
        /**
         * 市规委会会议时间。
         */
        public static final String COMMITTEE_MEETING_DATE = "COMMITTEE_MEETING_DATE";
        /**
         * 市规委会是否通过。
         */
        public static final String COMMITTEE_IS_PASS = "COMMITTEE_IS_PASS";
        /**
         * 市委会市规委会意见。
         */
        public static final String COMMITTEE_OPINON_FILE_GROUP_ID = "COMMITTEE_OPINON_FILE_GROUP_ID";
        /**
         * 工程规划许可证申请时间。
         */
        public static final String PRJ_PLAN_APPLY_TIME = "PRJ_PLAN_APPLY_TIME";
        /**
         * 预计完成日期。
         */
        public static final String PLAN_COMPL_DATE = "PLAN_COMPL_DATE";
        /**
         * 工程规划许可证申请人。
         */
        public static final String PRJ_PLAN_APPLICANT = "PRJ_PLAN_APPLICANT";
        /**
         * 工程规划许可证申请材料。
         */
        public static final String PRJ_PLAN_REQ_FILE = "PRJ_PLAN_REQ_FILE";
        /**
         * 工程规划许可证申请备注。
         */
        public static final String PRJ_PLAN_REQ_REMARK = "PRJ_PLAN_REQ_REMARK";
        /**
         * 下发时间。
         */
        public static final String ISSUE_DATE = "ISSUE_DATE";
        /**
         * 工程规划许可证编号。
         */
        public static final String PRJ_PLAN_REQ_CODE = "PRJ_PLAN_REQ_CODE";
        /**
         * 有效期。
         */
        public static final String VALID_TERM = "VALID_TERM";
        /**
         * 工程规划许可证。
         */
        public static final String PRJ_PLAN_REQ_PERMIT_FILE_GROUP_ID = "PRJ_PLAN_REQ_PERMIT_FILE_GROUP_ID";
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
    public PmPrjPlanningPermitReq setId(String id) {
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
    public PmPrjPlanningPermitReq setVer(Integer ver) {
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
    public PmPrjPlanningPermitReq setTs(LocalDateTime ts) {
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
    public PmPrjPlanningPermitReq setIsPreset(Boolean isPreset) {
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
    public PmPrjPlanningPermitReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmPrjPlanningPermitReq setLastModiUserId(String lastModiUserId) {
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
    public PmPrjPlanningPermitReq setCode(String code) {
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
    public PmPrjPlanningPermitReq setName(String name) {
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
    public PmPrjPlanningPermitReq setLkWfInstId(String lkWfInstId) {
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
    public PmPrjPlanningPermitReq setPmPrjId(String pmPrjId) {
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
    public PmPrjPlanningPermitReq setStatus(String status) {
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
    public PmPrjPlanningPermitReq setPrjCode(String prjCode) {
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
    public PmPrjPlanningPermitReq setCustomerUnit(String customerUnit) {
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
    public PmPrjPlanningPermitReq setCrtUserId(String crtUserId) {
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
    public PmPrjPlanningPermitReq setCrtDeptId(String crtDeptId) {
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
    public PmPrjPlanningPermitReq setPrjManageModeId(String prjManageModeId) {
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
    public PmPrjPlanningPermitReq setBaseLocationId(String baseLocationId) {
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
    public PmPrjPlanningPermitReq setCrtDt(LocalDateTime crtDt) {
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
    public PmPrjPlanningPermitReq setFloorArea(BigDecimal floorArea) {
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
    public PmPrjPlanningPermitReq setProjectTypeId(String projectTypeId) {
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
    public PmPrjPlanningPermitReq setConScaleTypeId(String conScaleTypeId) {
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
    public PmPrjPlanningPermitReq setConScaleQty(BigDecimal conScaleQty) {
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
    public PmPrjPlanningPermitReq setQtyOne(BigDecimal qtyOne) {
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
    public PmPrjPlanningPermitReq setQtyTwo(Integer qtyTwo) {
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
    public PmPrjPlanningPermitReq setQtyThree(Integer qtyThree) {
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
    public PmPrjPlanningPermitReq setConScaleQty2(BigDecimal conScaleQty2) {
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
    public PmPrjPlanningPermitReq setConScaleUomId(String conScaleUomId) {
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
    public PmPrjPlanningPermitReq setBuildYears(BigDecimal buildYears) {
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
    public PmPrjPlanningPermitReq setPrjSituation(String prjSituation) {
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
     * 招采项目备案及归档。
     */
    private String pmBidKeepFileReqId;

    /**
     * 获取：招采项目备案及归档。
     */
    public String getPmBidKeepFileReqId() {
        return this.pmBidKeepFileReqId;
    }

    /**
     * 设置：招采项目备案及归档。
     */
    public PmPrjPlanningPermitReq setPmBidKeepFileReqId(String pmBidKeepFileReqId) {
        if (this.pmBidKeepFileReqId == null && pmBidKeepFileReqId == null) {
            // 均为null，不做处理。
        } else if (this.pmBidKeepFileReqId != null && pmBidKeepFileReqId != null) {
            // 均非null，判定不等，再做处理：
            if (this.pmBidKeepFileReqId.compareTo(pmBidKeepFileReqId) != 0) {
                this.pmBidKeepFileReqId = pmBidKeepFileReqId;
                if (!this.toUpdateCols.contains("PM_BID_KEEP_FILE_REQ_ID")) {
                    this.toUpdateCols.add("PM_BID_KEEP_FILE_REQ_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pmBidKeepFileReqId = pmBidKeepFileReqId;
            if (!this.toUpdateCols.contains("PM_BID_KEEP_FILE_REQ_ID")) {
                this.toUpdateCols.add("PM_BID_KEEP_FILE_REQ_ID");
            }
        }
        return this;
    }

    /**
     * 状态1。
     */
    private String statusOne;

    /**
     * 获取：状态1。
     */
    public String getStatusOne() {
        return this.statusOne;
    }

    /**
     * 设置：状态1。
     */
    public PmPrjPlanningPermitReq setStatusOne(String statusOne) {
        if (this.statusOne == null && statusOne == null) {
            // 均为null，不做处理。
        } else if (this.statusOne != null && statusOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.statusOne.compareTo(statusOne) != 0) {
                this.statusOne = statusOne;
                if (!this.toUpdateCols.contains("STATUS_ONE")) {
                    this.toUpdateCols.add("STATUS_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.statusOne = statusOne;
            if (!this.toUpdateCols.contains("STATUS_ONE")) {
                this.toUpdateCols.add("STATUS_ONE");
            }
        }
        return this;
    }

    /**
     * 业主单位1。
     */
    private String customerUnitOne;

    /**
     * 获取：业主单位1。
     */
    public String getCustomerUnitOne() {
        return this.customerUnitOne;
    }

    /**
     * 设置：业主单位1。
     */
    public PmPrjPlanningPermitReq setCustomerUnitOne(String customerUnitOne) {
        if (this.customerUnitOne == null && customerUnitOne == null) {
            // 均为null，不做处理。
        } else if (this.customerUnitOne != null && customerUnitOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.customerUnitOne.compareTo(customerUnitOne) != 0) {
                this.customerUnitOne = customerUnitOne;
                if (!this.toUpdateCols.contains("CUSTOMER_UNIT_ONE")) {
                    this.toUpdateCols.add("CUSTOMER_UNIT_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.customerUnitOne = customerUnitOne;
            if (!this.toUpdateCols.contains("CUSTOMER_UNIT_ONE")) {
                this.toUpdateCols.add("CUSTOMER_UNIT_ONE");
            }
        }
        return this;
    }

    /**
     * 采购经办人。
     */
    private String procureUserId;

    /**
     * 获取：采购经办人。
     */
    public String getProcureUserId() {
        return this.procureUserId;
    }

    /**
     * 设置：采购经办人。
     */
    public PmPrjPlanningPermitReq setProcureUserId(String procureUserId) {
        if (this.procureUserId == null && procureUserId == null) {
            // 均为null，不做处理。
        } else if (this.procureUserId != null && procureUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.procureUserId.compareTo(procureUserId) != 0) {
                this.procureUserId = procureUserId;
                if (!this.toUpdateCols.contains("PROCURE_USER_ID")) {
                    this.toUpdateCols.add("PROCURE_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.procureUserId = procureUserId;
            if (!this.toUpdateCols.contains("PROCURE_USER_ID")) {
                this.toUpdateCols.add("PROCURE_USER_ID");
            }
        }
        return this;
    }

    /**
     * 中标单位1。
     */
    private String winBidUnitOne;

    /**
     * 获取：中标单位1。
     */
    public String getWinBidUnitOne() {
        return this.winBidUnitOne;
    }

    /**
     * 设置：中标单位1。
     */
    public PmPrjPlanningPermitReq setWinBidUnitOne(String winBidUnitOne) {
        if (this.winBidUnitOne == null && winBidUnitOne == null) {
            // 均为null，不做处理。
        } else if (this.winBidUnitOne != null && winBidUnitOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.winBidUnitOne.compareTo(winBidUnitOne) != 0) {
                this.winBidUnitOne = winBidUnitOne;
                if (!this.toUpdateCols.contains("WIN_BID_UNIT_ONE")) {
                    this.toUpdateCols.add("WIN_BID_UNIT_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.winBidUnitOne = winBidUnitOne;
            if (!this.toUpdateCols.contains("WIN_BID_UNIT_ONE")) {
                this.toUpdateCols.add("WIN_BID_UNIT_ONE");
            }
        }
        return this;
    }

    /**
     * 联系人1。
     */
    private String contactsOne;

    /**
     * 获取：联系人1。
     */
    public String getContactsOne() {
        return this.contactsOne;
    }

    /**
     * 设置：联系人1。
     */
    public PmPrjPlanningPermitReq setContactsOne(String contactsOne) {
        if (this.contactsOne == null && contactsOne == null) {
            // 均为null，不做处理。
        } else if (this.contactsOne != null && contactsOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactsOne.compareTo(contactsOne) != 0) {
                this.contactsOne = contactsOne;
                if (!this.toUpdateCols.contains("CONTACTS_ONE")) {
                    this.toUpdateCols.add("CONTACTS_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactsOne = contactsOne;
            if (!this.toUpdateCols.contains("CONTACTS_ONE")) {
                this.toUpdateCols.add("CONTACTS_ONE");
            }
        }
        return this;
    }

    /**
     * 联系人电话1。
     */
    private String contactMobileOne;

    /**
     * 获取：联系人电话1。
     */
    public String getContactMobileOne() {
        return this.contactMobileOne;
    }

    /**
     * 设置：联系人电话1。
     */
    public PmPrjPlanningPermitReq setContactMobileOne(String contactMobileOne) {
        if (this.contactMobileOne == null && contactMobileOne == null) {
            // 均为null，不做处理。
        } else if (this.contactMobileOne != null && contactMobileOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.contactMobileOne.compareTo(contactMobileOne) != 0) {
                this.contactMobileOne = contactMobileOne;
                if (!this.toUpdateCols.contains("CONTACT_MOBILE_ONE")) {
                    this.toUpdateCols.add("CONTACT_MOBILE_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contactMobileOne = contactMobileOne;
            if (!this.toUpdateCols.contains("CONTACT_MOBILE_ONE")) {
                this.toUpdateCols.add("CONTACT_MOBILE_ONE");
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
    public PmPrjPlanningPermitReq setComplPlanDate(LocalDate complPlanDate) {
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
    public PmPrjPlanningPermitReq setApplyTime(LocalDate applyTime) {
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
    public PmPrjPlanningPermitReq setApplicant(String applicant) {
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
     * 环评申请材料。
     */
    private String eiaReqFile;

    /**
     * 获取：环评申请材料。
     */
    public String getEiaReqFile() {
        return this.eiaReqFile;
    }

    /**
     * 设置：环评申请材料。
     */
    public PmPrjPlanningPermitReq setEiaReqFile(String eiaReqFile) {
        if (this.eiaReqFile == null && eiaReqFile == null) {
            // 均为null，不做处理。
        } else if (this.eiaReqFile != null && eiaReqFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.eiaReqFile.compareTo(eiaReqFile) != 0) {
                this.eiaReqFile = eiaReqFile;
                if (!this.toUpdateCols.contains("EIA_REQ_FILE")) {
                    this.toUpdateCols.add("EIA_REQ_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.eiaReqFile = eiaReqFile;
            if (!this.toUpdateCols.contains("EIA_REQ_FILE")) {
                this.toUpdateCols.add("EIA_REQ_FILE");
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
    public PmPrjPlanningPermitReq setRemark(String remark) {
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
     * 专家评审计划完成日期。
     */
    private LocalDate expertComplPlanDate;

    /**
     * 获取：专家评审计划完成日期。
     */
    public LocalDate getExpertComplPlanDate() {
        return this.expertComplPlanDate;
    }

    /**
     * 设置：专家评审计划完成日期。
     */
    public PmPrjPlanningPermitReq setExpertComplPlanDate(LocalDate expertComplPlanDate) {
        if (this.expertComplPlanDate == null && expertComplPlanDate == null) {
            // 均为null，不做处理。
        } else if (this.expertComplPlanDate != null && expertComplPlanDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.expertComplPlanDate.compareTo(expertComplPlanDate) != 0) {
                this.expertComplPlanDate = expertComplPlanDate;
                if (!this.toUpdateCols.contains("EXPERT_COMPL_PLAN_DATE")) {
                    this.toUpdateCols.add("EXPERT_COMPL_PLAN_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.expertComplPlanDate = expertComplPlanDate;
            if (!this.toUpdateCols.contains("EXPERT_COMPL_PLAN_DATE")) {
                this.toUpdateCols.add("EXPERT_COMPL_PLAN_DATE");
            }
        }
        return this;
    }

    /**
     * 专家评审实际完成日期。
     */
    private LocalDate expertComplActualDate;

    /**
     * 获取：专家评审实际完成日期。
     */
    public LocalDate getExpertComplActualDate() {
        return this.expertComplActualDate;
    }

    /**
     * 设置：专家评审实际完成日期。
     */
    public PmPrjPlanningPermitReq setExpertComplActualDate(LocalDate expertComplActualDate) {
        if (this.expertComplActualDate == null && expertComplActualDate == null) {
            // 均为null，不做处理。
        } else if (this.expertComplActualDate != null && expertComplActualDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.expertComplActualDate.compareTo(expertComplActualDate) != 0) {
                this.expertComplActualDate = expertComplActualDate;
                if (!this.toUpdateCols.contains("EXPERT_COMPL_ACTUAL_DATE")) {
                    this.toUpdateCols.add("EXPERT_COMPL_ACTUAL_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.expertComplActualDate = expertComplActualDate;
            if (!this.toUpdateCols.contains("EXPERT_COMPL_ACTUAL_DATE")) {
                this.toUpdateCols.add("EXPERT_COMPL_ACTUAL_DATE");
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
    public PmPrjPlanningPermitReq setReviewUnitChief(String reviewUnitChief) {
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
     * 评审单位。
     */
    private String reviewUnit;

    /**
     * 获取：评审单位。
     */
    public String getReviewUnit() {
        return this.reviewUnit;
    }

    /**
     * 设置：评审单位。
     */
    public PmPrjPlanningPermitReq setReviewUnit(String reviewUnit) {
        if (this.reviewUnit == null && reviewUnit == null) {
            // 均为null，不做处理。
        } else if (this.reviewUnit != null && reviewUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.reviewUnit.compareTo(reviewUnit) != 0) {
                this.reviewUnit = reviewUnit;
                if (!this.toUpdateCols.contains("REVIEW_UNIT")) {
                    this.toUpdateCols.add("REVIEW_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.reviewUnit = reviewUnit;
            if (!this.toUpdateCols.contains("REVIEW_UNIT")) {
                this.toUpdateCols.add("REVIEW_UNIT");
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
    public PmPrjPlanningPermitReq setReviewUnitPhone(String reviewUnitPhone) {
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
     * 评审稿文件。
     */
    private String reviewReportFile;

    /**
     * 获取：评审稿文件。
     */
    public String getReviewReportFile() {
        return this.reviewReportFile;
    }

    /**
     * 设置：评审稿文件。
     */
    public PmPrjPlanningPermitReq setReviewReportFile(String reviewReportFile) {
        if (this.reviewReportFile == null && reviewReportFile == null) {
            // 均为null，不做处理。
        } else if (this.reviewReportFile != null && reviewReportFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.reviewReportFile.compareTo(reviewReportFile) != 0) {
                this.reviewReportFile = reviewReportFile;
                if (!this.toUpdateCols.contains("REVIEW_REPORT_FILE")) {
                    this.toUpdateCols.add("REVIEW_REPORT_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.reviewReportFile = reviewReportFile;
            if (!this.toUpdateCols.contains("REVIEW_REPORT_FILE")) {
                this.toUpdateCols.add("REVIEW_REPORT_FILE");
            }
        }
        return this;
    }

    /**
     * 评审报告文件。
     */
    private String reviewDraftFile;

    /**
     * 获取：评审报告文件。
     */
    public String getReviewDraftFile() {
        return this.reviewDraftFile;
    }

    /**
     * 设置：评审报告文件。
     */
    public PmPrjPlanningPermitReq setReviewDraftFile(String reviewDraftFile) {
        if (this.reviewDraftFile == null && reviewDraftFile == null) {
            // 均为null，不做处理。
        } else if (this.reviewDraftFile != null && reviewDraftFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.reviewDraftFile.compareTo(reviewDraftFile) != 0) {
                this.reviewDraftFile = reviewDraftFile;
                if (!this.toUpdateCols.contains("REVIEW_DRAFT_FILE")) {
                    this.toUpdateCols.add("REVIEW_DRAFT_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.reviewDraftFile = reviewDraftFile;
            if (!this.toUpdateCols.contains("REVIEW_DRAFT_FILE")) {
                this.toUpdateCols.add("REVIEW_DRAFT_FILE");
            }
        }
        return this;
    }

    /**
     * 修编稿文件。
     */
    private String revisionFile;

    /**
     * 获取：修编稿文件。
     */
    public String getRevisionFile() {
        return this.revisionFile;
    }

    /**
     * 设置：修编稿文件。
     */
    public PmPrjPlanningPermitReq setRevisionFile(String revisionFile) {
        if (this.revisionFile == null && revisionFile == null) {
            // 均为null，不做处理。
        } else if (this.revisionFile != null && revisionFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.revisionFile.compareTo(revisionFile) != 0) {
                this.revisionFile = revisionFile;
                if (!this.toUpdateCols.contains("REVISION_FILE")) {
                    this.toUpdateCols.add("REVISION_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.revisionFile = revisionFile;
            if (!this.toUpdateCols.contains("REVISION_FILE")) {
                this.toUpdateCols.add("REVISION_FILE");
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
    public PmPrjPlanningPermitReq setReviewAndRevisionRemark(String reviewAndRevisionRemark) {
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
     * 计划完成日期。
     */
    private LocalDate complPlanDateCheck;

    /**
     * 获取：计划完成日期。
     */
    public LocalDate getComplPlanDateCheck() {
        return this.complPlanDateCheck;
    }

    /**
     * 设置：计划完成日期。
     */
    public PmPrjPlanningPermitReq setComplPlanDateCheck(LocalDate complPlanDateCheck) {
        if (this.complPlanDateCheck == null && complPlanDateCheck == null) {
            // 均为null，不做处理。
        } else if (this.complPlanDateCheck != null && complPlanDateCheck != null) {
            // 均非null，判定不等，再做处理：
            if (this.complPlanDateCheck.compareTo(complPlanDateCheck) != 0) {
                this.complPlanDateCheck = complPlanDateCheck;
                if (!this.toUpdateCols.contains("COMPL_PLAN_DATE_CHECK")) {
                    this.toUpdateCols.add("COMPL_PLAN_DATE_CHECK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.complPlanDateCheck = complPlanDateCheck;
            if (!this.toUpdateCols.contains("COMPL_PLAN_DATE_CHECK")) {
                this.toUpdateCols.add("COMPL_PLAN_DATE_CHECK");
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
    public PmPrjPlanningPermitReq setComplDate(LocalDate complDate) {
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
     * 编制负责人。
     */
    private String authorResponse;

    /**
     * 获取：编制负责人。
     */
    public String getAuthorResponse() {
        return this.authorResponse;
    }

    /**
     * 设置：编制负责人。
     */
    public PmPrjPlanningPermitReq setAuthorResponse(String authorResponse) {
        if (this.authorResponse == null && authorResponse == null) {
            // 均为null，不做处理。
        } else if (this.authorResponse != null && authorResponse != null) {
            // 均非null，判定不等，再做处理：
            if (this.authorResponse.compareTo(authorResponse) != 0) {
                this.authorResponse = authorResponse;
                if (!this.toUpdateCols.contains("AUTHOR_RESPONSE")) {
                    this.toUpdateCols.add("AUTHOR_RESPONSE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.authorResponse = authorResponse;
            if (!this.toUpdateCols.contains("AUTHOR_RESPONSE")) {
                this.toUpdateCols.add("AUTHOR_RESPONSE");
            }
        }
        return this;
    }

    /**
     * 方案检查ppt文件。
     */
    private String planCheckPptFileGroupId;

    /**
     * 获取：方案检查ppt文件。
     */
    public String getPlanCheckPptFileGroupId() {
        return this.planCheckPptFileGroupId;
    }

    /**
     * 设置：方案检查ppt文件。
     */
    public PmPrjPlanningPermitReq setPlanCheckPptFileGroupId(String planCheckPptFileGroupId) {
        if (this.planCheckPptFileGroupId == null && planCheckPptFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.planCheckPptFileGroupId != null && planCheckPptFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.planCheckPptFileGroupId.compareTo(planCheckPptFileGroupId) != 0) {
                this.planCheckPptFileGroupId = planCheckPptFileGroupId;
                if (!this.toUpdateCols.contains("PLAN_CHECK_PPT_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("PLAN_CHECK_PPT_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planCheckPptFileGroupId = planCheckPptFileGroupId;
            if (!this.toUpdateCols.contains("PLAN_CHECK_PPT_FILE_GROUP_ID")) {
                this.toUpdateCols.add("PLAN_CHECK_PPT_FILE_GROUP_ID");
            }
        }
        return this;
    }

    /**
     * 管理局局务预审会会议时间。
     */
    private LocalDate adminMeetingDate;

    /**
     * 获取：管理局局务预审会会议时间。
     */
    public LocalDate getAdminMeetingDate() {
        return this.adminMeetingDate;
    }

    /**
     * 设置：管理局局务预审会会议时间。
     */
    public PmPrjPlanningPermitReq setAdminMeetingDate(LocalDate adminMeetingDate) {
        if (this.adminMeetingDate == null && adminMeetingDate == null) {
            // 均为null，不做处理。
        } else if (this.adminMeetingDate != null && adminMeetingDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.adminMeetingDate.compareTo(adminMeetingDate) != 0) {
                this.adminMeetingDate = adminMeetingDate;
                if (!this.toUpdateCols.contains("ADMIN_MEETING_DATE")) {
                    this.toUpdateCols.add("ADMIN_MEETING_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adminMeetingDate = adminMeetingDate;
            if (!this.toUpdateCols.contains("ADMIN_MEETING_DATE")) {
                this.toUpdateCols.add("ADMIN_MEETING_DATE");
            }
        }
        return this;
    }

    /**
     * 管理局预审会是否通过。
     */
    private Boolean adminIsPass;

    /**
     * 获取：管理局预审会是否通过。
     */
    public Boolean getAdminIsPass() {
        return this.adminIsPass;
    }

    /**
     * 设置：管理局预审会是否通过。
     */
    public PmPrjPlanningPermitReq setAdminIsPass(Boolean adminIsPass) {
        if (this.adminIsPass == null && adminIsPass == null) {
            // 均为null，不做处理。
        } else if (this.adminIsPass != null && adminIsPass != null) {
            // 均非null，判定不等，再做处理：
            if (this.adminIsPass.compareTo(adminIsPass) != 0) {
                this.adminIsPass = adminIsPass;
                if (!this.toUpdateCols.contains("ADMIN_IS_PASS")) {
                    this.toUpdateCols.add("ADMIN_IS_PASS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adminIsPass = adminIsPass;
            if (!this.toUpdateCols.contains("ADMIN_IS_PASS")) {
                this.toUpdateCols.add("ADMIN_IS_PASS");
            }
        }
        return this;
    }

    /**
     * 管理局预审会市委会意见。
     */
    private String adminCommitteeOpinonFileGroupId;

    /**
     * 获取：管理局预审会市委会意见。
     */
    public String getAdminCommitteeOpinonFileGroupId() {
        return this.adminCommitteeOpinonFileGroupId;
    }

    /**
     * 设置：管理局预审会市委会意见。
     */
    public PmPrjPlanningPermitReq setAdminCommitteeOpinonFileGroupId(String adminCommitteeOpinonFileGroupId) {
        if (this.adminCommitteeOpinonFileGroupId == null && adminCommitteeOpinonFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.adminCommitteeOpinonFileGroupId != null && adminCommitteeOpinonFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adminCommitteeOpinonFileGroupId.compareTo(adminCommitteeOpinonFileGroupId) != 0) {
                this.adminCommitteeOpinonFileGroupId = adminCommitteeOpinonFileGroupId;
                if (!this.toUpdateCols.contains("ADMIN_COMMITTEE_OPINON_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("ADMIN_COMMITTEE_OPINON_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adminCommitteeOpinonFileGroupId = adminCommitteeOpinonFileGroupId;
            if (!this.toUpdateCols.contains("ADMIN_COMMITTEE_OPINON_FILE_GROUP_ID")) {
                this.toUpdateCols.add("ADMIN_COMMITTEE_OPINON_FILE_GROUP_ID");
            }
        }
        return this;
    }

    /**
     * 规委会预审会会议时间。
     */
    private LocalDate planMeetingDate;

    /**
     * 获取：规委会预审会会议时间。
     */
    public LocalDate getPlanMeetingDate() {
        return this.planMeetingDate;
    }

    /**
     * 设置：规委会预审会会议时间。
     */
    public PmPrjPlanningPermitReq setPlanMeetingDate(LocalDate planMeetingDate) {
        if (this.planMeetingDate == null && planMeetingDate == null) {
            // 均为null，不做处理。
        } else if (this.planMeetingDate != null && planMeetingDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.planMeetingDate.compareTo(planMeetingDate) != 0) {
                this.planMeetingDate = planMeetingDate;
                if (!this.toUpdateCols.contains("PLAN_MEETING_DATE")) {
                    this.toUpdateCols.add("PLAN_MEETING_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planMeetingDate = planMeetingDate;
            if (!this.toUpdateCols.contains("PLAN_MEETING_DATE")) {
                this.toUpdateCols.add("PLAN_MEETING_DATE");
            }
        }
        return this;
    }

    /**
     * 规委会预审会是否通过。
     */
    private Boolean planIsPass;

    /**
     * 获取：规委会预审会是否通过。
     */
    public Boolean getPlanIsPass() {
        return this.planIsPass;
    }

    /**
     * 设置：规委会预审会是否通过。
     */
    public PmPrjPlanningPermitReq setPlanIsPass(Boolean planIsPass) {
        if (this.planIsPass == null && planIsPass == null) {
            // 均为null，不做处理。
        } else if (this.planIsPass != null && planIsPass != null) {
            // 均非null，判定不等，再做处理：
            if (this.planIsPass.compareTo(planIsPass) != 0) {
                this.planIsPass = planIsPass;
                if (!this.toUpdateCols.contains("PLAN_IS_PASS")) {
                    this.toUpdateCols.add("PLAN_IS_PASS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planIsPass = planIsPass;
            if (!this.toUpdateCols.contains("PLAN_IS_PASS")) {
                this.toUpdateCols.add("PLAN_IS_PASS");
            }
        }
        return this;
    }

    /**
     * 规委会预审会市委会意见。
     */
    private String planCommitteeOpinonFileGroupId;

    /**
     * 获取：规委会预审会市委会意见。
     */
    public String getPlanCommitteeOpinonFileGroupId() {
        return this.planCommitteeOpinonFileGroupId;
    }

    /**
     * 设置：规委会预审会市委会意见。
     */
    public PmPrjPlanningPermitReq setPlanCommitteeOpinonFileGroupId(String planCommitteeOpinonFileGroupId) {
        if (this.planCommitteeOpinonFileGroupId == null && planCommitteeOpinonFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.planCommitteeOpinonFileGroupId != null && planCommitteeOpinonFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.planCommitteeOpinonFileGroupId.compareTo(planCommitteeOpinonFileGroupId) != 0) {
                this.planCommitteeOpinonFileGroupId = planCommitteeOpinonFileGroupId;
                if (!this.toUpdateCols.contains("PLAN_COMMITTEE_OPINON_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("PLAN_COMMITTEE_OPINON_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.planCommitteeOpinonFileGroupId = planCommitteeOpinonFileGroupId;
            if (!this.toUpdateCols.contains("PLAN_COMMITTEE_OPINON_FILE_GROUP_ID")) {
                this.toUpdateCols.add("PLAN_COMMITTEE_OPINON_FILE_GROUP_ID");
            }
        }
        return this;
    }

    /**
     * 分管副市长预审会会议时间。
     */
    private LocalDate deputyMayorMeetingDate;

    /**
     * 获取：分管副市长预审会会议时间。
     */
    public LocalDate getDeputyMayorMeetingDate() {
        return this.deputyMayorMeetingDate;
    }

    /**
     * 设置：分管副市长预审会会议时间。
     */
    public PmPrjPlanningPermitReq setDeputyMayorMeetingDate(LocalDate deputyMayorMeetingDate) {
        if (this.deputyMayorMeetingDate == null && deputyMayorMeetingDate == null) {
            // 均为null，不做处理。
        } else if (this.deputyMayorMeetingDate != null && deputyMayorMeetingDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.deputyMayorMeetingDate.compareTo(deputyMayorMeetingDate) != 0) {
                this.deputyMayorMeetingDate = deputyMayorMeetingDate;
                if (!this.toUpdateCols.contains("DEPUTY_MAYOR_MEETING_DATE")) {
                    this.toUpdateCols.add("DEPUTY_MAYOR_MEETING_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.deputyMayorMeetingDate = deputyMayorMeetingDate;
            if (!this.toUpdateCols.contains("DEPUTY_MAYOR_MEETING_DATE")) {
                this.toUpdateCols.add("DEPUTY_MAYOR_MEETING_DATE");
            }
        }
        return this;
    }

    /**
     * 分管副市长预审会是否通过。
     */
    private Boolean deputyMayorIsPass;

    /**
     * 获取：分管副市长预审会是否通过。
     */
    public Boolean getDeputyMayorIsPass() {
        return this.deputyMayorIsPass;
    }

    /**
     * 设置：分管副市长预审会是否通过。
     */
    public PmPrjPlanningPermitReq setDeputyMayorIsPass(Boolean deputyMayorIsPass) {
        if (this.deputyMayorIsPass == null && deputyMayorIsPass == null) {
            // 均为null，不做处理。
        } else if (this.deputyMayorIsPass != null && deputyMayorIsPass != null) {
            // 均非null，判定不等，再做处理：
            if (this.deputyMayorIsPass.compareTo(deputyMayorIsPass) != 0) {
                this.deputyMayorIsPass = deputyMayorIsPass;
                if (!this.toUpdateCols.contains("DEPUTY_MAYOR_IS_PASS")) {
                    this.toUpdateCols.add("DEPUTY_MAYOR_IS_PASS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.deputyMayorIsPass = deputyMayorIsPass;
            if (!this.toUpdateCols.contains("DEPUTY_MAYOR_IS_PASS")) {
                this.toUpdateCols.add("DEPUTY_MAYOR_IS_PASS");
            }
        }
        return this;
    }

    /**
     * 分管副市长预审会市委会意见。
     */
    private String deputyMayorCommitteeOpinonFileGroupId;

    /**
     * 获取：分管副市长预审会市委会意见。
     */
    public String getDeputyMayorCommitteeOpinonFileGroupId() {
        return this.deputyMayorCommitteeOpinonFileGroupId;
    }

    /**
     * 设置：分管副市长预审会市委会意见。
     */
    public PmPrjPlanningPermitReq setDeputyMayorCommitteeOpinonFileGroupId(String deputyMayorCommitteeOpinonFileGroupId) {
        if (this.deputyMayorCommitteeOpinonFileGroupId == null && deputyMayorCommitteeOpinonFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.deputyMayorCommitteeOpinonFileGroupId != null && deputyMayorCommitteeOpinonFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.deputyMayorCommitteeOpinonFileGroupId.compareTo(deputyMayorCommitteeOpinonFileGroupId) != 0) {
                this.deputyMayorCommitteeOpinonFileGroupId = deputyMayorCommitteeOpinonFileGroupId;
                if (!this.toUpdateCols.contains("DEPUTY_MAYOR_COMMITTEE_OPINON_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("DEPUTY_MAYOR_COMMITTEE_OPINON_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.deputyMayorCommitteeOpinonFileGroupId = deputyMayorCommitteeOpinonFileGroupId;
            if (!this.toUpdateCols.contains("DEPUTY_MAYOR_COMMITTEE_OPINON_FILE_GROUP_ID")) {
                this.toUpdateCols.add("DEPUTY_MAYOR_COMMITTEE_OPINON_FILE_GROUP_ID");
            }
        }
        return this;
    }

    /**
     * 分管副市长和市长预审会会议时间。
     */
    private LocalDate mayorMeetingDate;

    /**
     * 获取：分管副市长和市长预审会会议时间。
     */
    public LocalDate getMayorMeetingDate() {
        return this.mayorMeetingDate;
    }

    /**
     * 设置：分管副市长和市长预审会会议时间。
     */
    public PmPrjPlanningPermitReq setMayorMeetingDate(LocalDate mayorMeetingDate) {
        if (this.mayorMeetingDate == null && mayorMeetingDate == null) {
            // 均为null，不做处理。
        } else if (this.mayorMeetingDate != null && mayorMeetingDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.mayorMeetingDate.compareTo(mayorMeetingDate) != 0) {
                this.mayorMeetingDate = mayorMeetingDate;
                if (!this.toUpdateCols.contains("MAYOR_MEETING_DATE")) {
                    this.toUpdateCols.add("MAYOR_MEETING_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.mayorMeetingDate = mayorMeetingDate;
            if (!this.toUpdateCols.contains("MAYOR_MEETING_DATE")) {
                this.toUpdateCols.add("MAYOR_MEETING_DATE");
            }
        }
        return this;
    }

    /**
     * 分管副市长和市长预审会是否通过。
     */
    private Boolean mayorIsPass;

    /**
     * 获取：分管副市长和市长预审会是否通过。
     */
    public Boolean getMayorIsPass() {
        return this.mayorIsPass;
    }

    /**
     * 设置：分管副市长和市长预审会是否通过。
     */
    public PmPrjPlanningPermitReq setMayorIsPass(Boolean mayorIsPass) {
        if (this.mayorIsPass == null && mayorIsPass == null) {
            // 均为null，不做处理。
        } else if (this.mayorIsPass != null && mayorIsPass != null) {
            // 均非null，判定不等，再做处理：
            if (this.mayorIsPass.compareTo(mayorIsPass) != 0) {
                this.mayorIsPass = mayorIsPass;
                if (!this.toUpdateCols.contains("MAYOR_IS_PASS")) {
                    this.toUpdateCols.add("MAYOR_IS_PASS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.mayorIsPass = mayorIsPass;
            if (!this.toUpdateCols.contains("MAYOR_IS_PASS")) {
                this.toUpdateCols.add("MAYOR_IS_PASS");
            }
        }
        return this;
    }

    /**
     * 分管副市长和市长预审会市委会意见。
     */
    private String mayorCommitteeOpinonFileGroupId;

    /**
     * 获取：分管副市长和市长预审会市委会意见。
     */
    public String getMayorCommitteeOpinonFileGroupId() {
        return this.mayorCommitteeOpinonFileGroupId;
    }

    /**
     * 设置：分管副市长和市长预审会市委会意见。
     */
    public PmPrjPlanningPermitReq setMayorCommitteeOpinonFileGroupId(String mayorCommitteeOpinonFileGroupId) {
        if (this.mayorCommitteeOpinonFileGroupId == null && mayorCommitteeOpinonFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.mayorCommitteeOpinonFileGroupId != null && mayorCommitteeOpinonFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.mayorCommitteeOpinonFileGroupId.compareTo(mayorCommitteeOpinonFileGroupId) != 0) {
                this.mayorCommitteeOpinonFileGroupId = mayorCommitteeOpinonFileGroupId;
                if (!this.toUpdateCols.contains("MAYOR_COMMITTEE_OPINON_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("MAYOR_COMMITTEE_OPINON_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.mayorCommitteeOpinonFileGroupId = mayorCommitteeOpinonFileGroupId;
            if (!this.toUpdateCols.contains("MAYOR_COMMITTEE_OPINON_FILE_GROUP_ID")) {
                this.toUpdateCols.add("MAYOR_COMMITTEE_OPINON_FILE_GROUP_ID");
            }
        }
        return this;
    }

    /**
     * 市规委会会议时间。
     */
    private LocalDate committeeMeetingDate;

    /**
     * 获取：市规委会会议时间。
     */
    public LocalDate getCommitteeMeetingDate() {
        return this.committeeMeetingDate;
    }

    /**
     * 设置：市规委会会议时间。
     */
    public PmPrjPlanningPermitReq setCommitteeMeetingDate(LocalDate committeeMeetingDate) {
        if (this.committeeMeetingDate == null && committeeMeetingDate == null) {
            // 均为null，不做处理。
        } else if (this.committeeMeetingDate != null && committeeMeetingDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.committeeMeetingDate.compareTo(committeeMeetingDate) != 0) {
                this.committeeMeetingDate = committeeMeetingDate;
                if (!this.toUpdateCols.contains("COMMITTEE_MEETING_DATE")) {
                    this.toUpdateCols.add("COMMITTEE_MEETING_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.committeeMeetingDate = committeeMeetingDate;
            if (!this.toUpdateCols.contains("COMMITTEE_MEETING_DATE")) {
                this.toUpdateCols.add("COMMITTEE_MEETING_DATE");
            }
        }
        return this;
    }

    /**
     * 市规委会是否通过。
     */
    private Boolean committeeIsPass;

    /**
     * 获取：市规委会是否通过。
     */
    public Boolean getCommitteeIsPass() {
        return this.committeeIsPass;
    }

    /**
     * 设置：市规委会是否通过。
     */
    public PmPrjPlanningPermitReq setCommitteeIsPass(Boolean committeeIsPass) {
        if (this.committeeIsPass == null && committeeIsPass == null) {
            // 均为null，不做处理。
        } else if (this.committeeIsPass != null && committeeIsPass != null) {
            // 均非null，判定不等，再做处理：
            if (this.committeeIsPass.compareTo(committeeIsPass) != 0) {
                this.committeeIsPass = committeeIsPass;
                if (!this.toUpdateCols.contains("COMMITTEE_IS_PASS")) {
                    this.toUpdateCols.add("COMMITTEE_IS_PASS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.committeeIsPass = committeeIsPass;
            if (!this.toUpdateCols.contains("COMMITTEE_IS_PASS")) {
                this.toUpdateCols.add("COMMITTEE_IS_PASS");
            }
        }
        return this;
    }

    /**
     * 市委会市规委会意见。
     */
    private String committeeOpinonFileGroupId;

    /**
     * 获取：市委会市规委会意见。
     */
    public String getCommitteeOpinonFileGroupId() {
        return this.committeeOpinonFileGroupId;
    }

    /**
     * 设置：市委会市规委会意见。
     */
    public PmPrjPlanningPermitReq setCommitteeOpinonFileGroupId(String committeeOpinonFileGroupId) {
        if (this.committeeOpinonFileGroupId == null && committeeOpinonFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.committeeOpinonFileGroupId != null && committeeOpinonFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.committeeOpinonFileGroupId.compareTo(committeeOpinonFileGroupId) != 0) {
                this.committeeOpinonFileGroupId = committeeOpinonFileGroupId;
                if (!this.toUpdateCols.contains("COMMITTEE_OPINON_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("COMMITTEE_OPINON_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.committeeOpinonFileGroupId = committeeOpinonFileGroupId;
            if (!this.toUpdateCols.contains("COMMITTEE_OPINON_FILE_GROUP_ID")) {
                this.toUpdateCols.add("COMMITTEE_OPINON_FILE_GROUP_ID");
            }
        }
        return this;
    }

    /**
     * 工程规划许可证申请时间。
     */
    private LocalDate prjPlanApplyTime;

    /**
     * 获取：工程规划许可证申请时间。
     */
    public LocalDate getPrjPlanApplyTime() {
        return this.prjPlanApplyTime;
    }

    /**
     * 设置：工程规划许可证申请时间。
     */
    public PmPrjPlanningPermitReq setPrjPlanApplyTime(LocalDate prjPlanApplyTime) {
        if (this.prjPlanApplyTime == null && prjPlanApplyTime == null) {
            // 均为null，不做处理。
        } else if (this.prjPlanApplyTime != null && prjPlanApplyTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjPlanApplyTime.compareTo(prjPlanApplyTime) != 0) {
                this.prjPlanApplyTime = prjPlanApplyTime;
                if (!this.toUpdateCols.contains("PRJ_PLAN_APPLY_TIME")) {
                    this.toUpdateCols.add("PRJ_PLAN_APPLY_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjPlanApplyTime = prjPlanApplyTime;
            if (!this.toUpdateCols.contains("PRJ_PLAN_APPLY_TIME")) {
                this.toUpdateCols.add("PRJ_PLAN_APPLY_TIME");
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
    public PmPrjPlanningPermitReq setPlanComplDate(LocalDate planComplDate) {
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
     * 工程规划许可证申请人。
     */
    private String prjPlanApplicant;

    /**
     * 获取：工程规划许可证申请人。
     */
    public String getPrjPlanApplicant() {
        return this.prjPlanApplicant;
    }

    /**
     * 设置：工程规划许可证申请人。
     */
    public PmPrjPlanningPermitReq setPrjPlanApplicant(String prjPlanApplicant) {
        if (this.prjPlanApplicant == null && prjPlanApplicant == null) {
            // 均为null，不做处理。
        } else if (this.prjPlanApplicant != null && prjPlanApplicant != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjPlanApplicant.compareTo(prjPlanApplicant) != 0) {
                this.prjPlanApplicant = prjPlanApplicant;
                if (!this.toUpdateCols.contains("PRJ_PLAN_APPLICANT")) {
                    this.toUpdateCols.add("PRJ_PLAN_APPLICANT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjPlanApplicant = prjPlanApplicant;
            if (!this.toUpdateCols.contains("PRJ_PLAN_APPLICANT")) {
                this.toUpdateCols.add("PRJ_PLAN_APPLICANT");
            }
        }
        return this;
    }

    /**
     * 工程规划许可证申请材料。
     */
    private String prjPlanReqFile;

    /**
     * 获取：工程规划许可证申请材料。
     */
    public String getPrjPlanReqFile() {
        return this.prjPlanReqFile;
    }

    /**
     * 设置：工程规划许可证申请材料。
     */
    public PmPrjPlanningPermitReq setPrjPlanReqFile(String prjPlanReqFile) {
        if (this.prjPlanReqFile == null && prjPlanReqFile == null) {
            // 均为null，不做处理。
        } else if (this.prjPlanReqFile != null && prjPlanReqFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjPlanReqFile.compareTo(prjPlanReqFile) != 0) {
                this.prjPlanReqFile = prjPlanReqFile;
                if (!this.toUpdateCols.contains("PRJ_PLAN_REQ_FILE")) {
                    this.toUpdateCols.add("PRJ_PLAN_REQ_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjPlanReqFile = prjPlanReqFile;
            if (!this.toUpdateCols.contains("PRJ_PLAN_REQ_FILE")) {
                this.toUpdateCols.add("PRJ_PLAN_REQ_FILE");
            }
        }
        return this;
    }

    /**
     * 工程规划许可证申请备注。
     */
    private String prjPlanReqRemark;

    /**
     * 获取：工程规划许可证申请备注。
     */
    public String getPrjPlanReqRemark() {
        return this.prjPlanReqRemark;
    }

    /**
     * 设置：工程规划许可证申请备注。
     */
    public PmPrjPlanningPermitReq setPrjPlanReqRemark(String prjPlanReqRemark) {
        if (this.prjPlanReqRemark == null && prjPlanReqRemark == null) {
            // 均为null，不做处理。
        } else if (this.prjPlanReqRemark != null && prjPlanReqRemark != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjPlanReqRemark.compareTo(prjPlanReqRemark) != 0) {
                this.prjPlanReqRemark = prjPlanReqRemark;
                if (!this.toUpdateCols.contains("PRJ_PLAN_REQ_REMARK")) {
                    this.toUpdateCols.add("PRJ_PLAN_REQ_REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjPlanReqRemark = prjPlanReqRemark;
            if (!this.toUpdateCols.contains("PRJ_PLAN_REQ_REMARK")) {
                this.toUpdateCols.add("PRJ_PLAN_REQ_REMARK");
            }
        }
        return this;
    }

    /**
     * 下发时间。
     */
    private LocalDate issueDate;

    /**
     * 获取：下发时间。
     */
    public LocalDate getIssueDate() {
        return this.issueDate;
    }

    /**
     * 设置：下发时间。
     */
    public PmPrjPlanningPermitReq setIssueDate(LocalDate issueDate) {
        if (this.issueDate == null && issueDate == null) {
            // 均为null，不做处理。
        } else if (this.issueDate != null && issueDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.issueDate.compareTo(issueDate) != 0) {
                this.issueDate = issueDate;
                if (!this.toUpdateCols.contains("ISSUE_DATE")) {
                    this.toUpdateCols.add("ISSUE_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.issueDate = issueDate;
            if (!this.toUpdateCols.contains("ISSUE_DATE")) {
                this.toUpdateCols.add("ISSUE_DATE");
            }
        }
        return this;
    }

    /**
     * 工程规划许可证编号。
     */
    private String prjPlanReqCode;

    /**
     * 获取：工程规划许可证编号。
     */
    public String getPrjPlanReqCode() {
        return this.prjPlanReqCode;
    }

    /**
     * 设置：工程规划许可证编号。
     */
    public PmPrjPlanningPermitReq setPrjPlanReqCode(String prjPlanReqCode) {
        if (this.prjPlanReqCode == null && prjPlanReqCode == null) {
            // 均为null，不做处理。
        } else if (this.prjPlanReqCode != null && prjPlanReqCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjPlanReqCode.compareTo(prjPlanReqCode) != 0) {
                this.prjPlanReqCode = prjPlanReqCode;
                if (!this.toUpdateCols.contains("PRJ_PLAN_REQ_CODE")) {
                    this.toUpdateCols.add("PRJ_PLAN_REQ_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjPlanReqCode = prjPlanReqCode;
            if (!this.toUpdateCols.contains("PRJ_PLAN_REQ_CODE")) {
                this.toUpdateCols.add("PRJ_PLAN_REQ_CODE");
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
    public PmPrjPlanningPermitReq setValidTerm(String validTerm) {
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
     * 工程规划许可证。
     */
    private String prjPlanReqPermitFileGroupId;

    /**
     * 获取：工程规划许可证。
     */
    public String getPrjPlanReqPermitFileGroupId() {
        return this.prjPlanReqPermitFileGroupId;
    }

    /**
     * 设置：工程规划许可证。
     */
    public PmPrjPlanningPermitReq setPrjPlanReqPermitFileGroupId(String prjPlanReqPermitFileGroupId) {
        if (this.prjPlanReqPermitFileGroupId == null && prjPlanReqPermitFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.prjPlanReqPermitFileGroupId != null && prjPlanReqPermitFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.prjPlanReqPermitFileGroupId.compareTo(prjPlanReqPermitFileGroupId) != 0) {
                this.prjPlanReqPermitFileGroupId = prjPlanReqPermitFileGroupId;
                if (!this.toUpdateCols.contains("PRJ_PLAN_REQ_PERMIT_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("PRJ_PLAN_REQ_PERMIT_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.prjPlanReqPermitFileGroupId = prjPlanReqPermitFileGroupId;
            if (!this.toUpdateCols.contains("PRJ_PLAN_REQ_PERMIT_FILE_GROUP_ID")) {
                this.toUpdateCols.add("PRJ_PLAN_REQ_PERMIT_FILE_GROUP_ID");
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
    public static PmPrjPlanningPermitReq newData() {
        PmPrjPlanningPermitReq obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmPrjPlanningPermitReq insertData() {
        PmPrjPlanningPermitReq obj = modelHelper.insertData();
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
    public static PmPrjPlanningPermitReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmPrjPlanningPermitReq obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<PmPrjPlanningPermitReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmPrjPlanningPermitReq> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<PmPrjPlanningPermitReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmPrjPlanningPermitReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(PmPrjPlanningPermitReq fromModel, PmPrjPlanningPermitReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}