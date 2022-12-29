package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 报审、报验。
 */
public class ApprovalInspection {

    /**
     * 模型助手。
     */
    private static final ModelHelper<ApprovalInspection> modelHelper = new ModelHelper<>("APPROVAL_INSPECTION", new ApprovalInspection());

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

    public static final String ENT_CODE = "APPROVAL_INSPECTION";
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
         * 报审、报验类型。
         */
        public static final String APPROVAL_INSPECTION_TYPE_ID = "APPROVAL_INSPECTION_TYPE_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 报审、报验名称。
         */
        public static final String APPROVAL_INSPECTION_NAME = "APPROVAL_INSPECTION_NAME";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 项目编号。
         */
        public static final String PRJ_CODE = "PRJ_CODE";
        /**
         * 项目管理模式。
         */
        public static final String PRJ_MANAGE_MODE_ID = "PRJ_MANAGE_MODE_ID";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 立项批复文号。
         */
        public static final String PRJ_REPLY_NO = "PRJ_REPLY_NO";
        /**
         * 投资来源。
         */
        public static final String INVESTMENT_SOURCE_ID = "INVESTMENT_SOURCE_ID";
        /**
         * 业主单位。
         */
        public static final String CUSTOMER_UNIT = "CUSTOMER_UNIT";
        /**
         * 项目类型。
         */
        public static final String PROJECT_TYPE_ID = "PROJECT_TYPE_ID";
        /**
         * 总投资（万）。
         */
        public static final String PRJ_TOTAL_INVEST = "PRJ_TOTAL_INVEST";
        /**
         * 工程费用（万）。
         */
        public static final String PROJECT_AMT = "PROJECT_AMT";
        /**
         * 工程其他费用（万）。
         */
        public static final String PROJECT_OTHER_AMT = "PROJECT_OTHER_AMT";
        /**
         * 预备费（万）。
         */
        public static final String PREPARE_AMT = "PREPARE_AMT";
        /**
         * 建设期利息（万）。
         */
        public static final String CONSTRUCT_PERIOD_INTEREST = "CONSTRUCT_PERIOD_INTEREST";
        /**
         * 项目概况。
         */
        public static final String PRJ_SITUATION = "PRJ_SITUATION";
        /**
         * 附件。
         */
        public static final String ATT_FILE_GROUP_ID = "ATT_FILE_GROUP_ID";
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
    public ApprovalInspection setId(String id) {
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
    public ApprovalInspection setVer(Integer ver) {
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
    public ApprovalInspection setTs(LocalDateTime ts) {
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
    public ApprovalInspection setIsPreset(Boolean isPreset) {
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
    public ApprovalInspection setLastModiDt(LocalDateTime lastModiDt) {
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
    public ApprovalInspection setLastModiUserId(String lastModiUserId) {
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
    public ApprovalInspection setCode(String code) {
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
    public ApprovalInspection setName(String name) {
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
    public ApprovalInspection setRemark(String remark) {
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
    public ApprovalInspection setLkWfInstId(String lkWfInstId) {
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
     * 报审、报验类型。
     */
    private String approvalInspectionTypeId;

    /**
     * 获取：报审、报验类型。
     */
    public String getApprovalInspectionTypeId() {
        return this.approvalInspectionTypeId;
    }

    /**
     * 设置：报审、报验类型。
     */
    public ApprovalInspection setApprovalInspectionTypeId(String approvalInspectionTypeId) {
        if (this.approvalInspectionTypeId == null && approvalInspectionTypeId == null) {
            // 均为null，不做处理。
        } else if (this.approvalInspectionTypeId != null && approvalInspectionTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalInspectionTypeId.compareTo(approvalInspectionTypeId) != 0) {
                this.approvalInspectionTypeId = approvalInspectionTypeId;
                if (!this.toUpdateCols.contains("APPROVAL_INSPECTION_TYPE_ID")) {
                    this.toUpdateCols.add("APPROVAL_INSPECTION_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalInspectionTypeId = approvalInspectionTypeId;
            if (!this.toUpdateCols.contains("APPROVAL_INSPECTION_TYPE_ID")) {
                this.toUpdateCols.add("APPROVAL_INSPECTION_TYPE_ID");
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
    public ApprovalInspection setStatus(String status) {
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
     * 报审、报验名称。
     */
    private String approvalInspectionName;

    /**
     * 获取：报审、报验名称。
     */
    public String getApprovalInspectionName() {
        return this.approvalInspectionName;
    }

    /**
     * 设置：报审、报验名称。
     */
    public ApprovalInspection setApprovalInspectionName(String approvalInspectionName) {
        if (this.approvalInspectionName == null && approvalInspectionName == null) {
            // 均为null，不做处理。
        } else if (this.approvalInspectionName != null && approvalInspectionName != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalInspectionName.compareTo(approvalInspectionName) != 0) {
                this.approvalInspectionName = approvalInspectionName;
                if (!this.toUpdateCols.contains("APPROVAL_INSPECTION_NAME")) {
                    this.toUpdateCols.add("APPROVAL_INSPECTION_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalInspectionName = approvalInspectionName;
            if (!this.toUpdateCols.contains("APPROVAL_INSPECTION_NAME")) {
                this.toUpdateCols.add("APPROVAL_INSPECTION_NAME");
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
    public ApprovalInspection setPmPrjId(String pmPrjId) {
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
    public ApprovalInspection setCrtUserId(String crtUserId) {
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
    public ApprovalInspection setCrtDeptId(String crtDeptId) {
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
    public ApprovalInspection setPrjCode(String prjCode) {
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
    public ApprovalInspection setPrjManageModeId(String prjManageModeId) {
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
    public ApprovalInspection setCrtDt(LocalDateTime crtDt) {
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
    public ApprovalInspection setPrjReplyNo(String prjReplyNo) {
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
    public ApprovalInspection setInvestmentSourceId(String investmentSourceId) {
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
    public ApprovalInspection setCustomerUnit(String customerUnit) {
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
    public ApprovalInspection setProjectTypeId(String projectTypeId) {
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
    public ApprovalInspection setPrjTotalInvest(BigDecimal prjTotalInvest) {
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
    public ApprovalInspection setProjectAmt(BigDecimal projectAmt) {
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
     * 工程其他费用（万）。
     */
    private BigDecimal projectOtherAmt;

    /**
     * 获取：工程其他费用（万）。
     */
    public BigDecimal getProjectOtherAmt() {
        return this.projectOtherAmt;
    }

    /**
     * 设置：工程其他费用（万）。
     */
    public ApprovalInspection setProjectOtherAmt(BigDecimal projectOtherAmt) {
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
     * 预备费（万）。
     */
    private BigDecimal prepareAmt;

    /**
     * 获取：预备费（万）。
     */
    public BigDecimal getPrepareAmt() {
        return this.prepareAmt;
    }

    /**
     * 设置：预备费（万）。
     */
    public ApprovalInspection setPrepareAmt(BigDecimal prepareAmt) {
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
    public ApprovalInspection setConstructPeriodInterest(BigDecimal constructPeriodInterest) {
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
    public ApprovalInspection setPrjSituation(String prjSituation) {
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
    public ApprovalInspection setAttFileGroupId(String attFileGroupId) {
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
    public static ApprovalInspection newData() {
        ApprovalInspection obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static ApprovalInspection insertData() {
        ApprovalInspection obj = modelHelper.insertData();
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
    public static ApprovalInspection selectById(String id, List<String> includeCols, List<String> excludeCols) {
        ApprovalInspection obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<ApprovalInspection> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<ApprovalInspection> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<ApprovalInspection> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<ApprovalInspection> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(ApprovalInspection fromModel, ApprovalInspection toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}