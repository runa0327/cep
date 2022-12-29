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
 * 设计任务书。
 */
public class PmDesignAssignmentBook {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PmDesignAssignmentBook> modelHelper = new ModelHelper<>("PM_DESIGN_ASSIGNMENT_BOOK", new PmDesignAssignmentBook());

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

    public static final String ENT_CODE = "PM_DESIGN_ASSIGNMENT_BOOK";
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
         * 资金需求项目名称。
         */
        public static final String AMOUT_PM_PRJ_ID = "AMOUT_PM_PRJ_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 项目编号。
         */
        public static final String PRJ_CODE = "PRJ_CODE";
        /**
         * 批复文号。
         */
        public static final String REPLY_NO = "REPLY_NO";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 项目概况。
         */
        public static final String PRJ_SITUATION = "PRJ_SITUATION";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
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
         * 任务书类型。
         */
        public static final String DESIGN_TASK_TYPE_ID = "DESIGN_TASK_TYPE_ID";
        /**
         * 设计单位1。
         */
        public static final String DESIGNER_UNIT_ONE = "DESIGNER_UNIT_ONE";
        /**
         * 设计人1。
         */
        public static final String DESIGNER_ONE = "DESIGNER_ONE";
        /**
         * 附件。
         */
        public static final String ATT_FILE_GROUP_ID = "ATT_FILE_GROUP_ID";
        /**
         * 备注。
         */
        public static final String REMARK_SHORT = "REMARK_SHORT";
        /**
         * 审批附件1。
         */
        public static final String APPROVE_FILE_ID_ONE = "APPROVE_FILE_ID_ONE";
        /**
         * 审批意见1。
         */
        public static final String APPROVAL_COMMENT_ONE = "APPROVAL_COMMENT_ONE";
        /**
         * 审批附件2。
         */
        public static final String APPROVE_FILE_ID_TWO = "APPROVE_FILE_ID_TWO";
        /**
         * 审批意见2。
         */
        public static final String APPROVAL_COMMENT_TWO = "APPROVAL_COMMENT_TWO";
        /**
         * 指标。
         */
        public static final String INDEX_ID = "INDEX_ID";
        /**
         * 指标金额。
         */
        public static final String TGT_AMT = "TGT_AMT";
        /**
         * 审批附件3。
         */
        public static final String APPROVE_FILE_ID_THREE = "APPROVE_FILE_ID_THREE";
        /**
         * 审批意见3。
         */
        public static final String APPROVAL_COMMENT_THREE = "APPROVAL_COMMENT_THREE";
        /**
         * 审批日期4。
         */
        public static final String APPROVAL_DATE_FOUR = "APPROVAL_DATE_FOUR";
        /**
         * 审批人4。
         */
        public static final String APPROVAL_USER_FOUR = "APPROVAL_USER_FOUR";
        /**
         * 审批意见4。
         */
        public static final String APPROVAL_COMMENT_FOUR = "APPROVAL_COMMENT_FOUR";
        /**
         * 审批日期5。
         */
        public static final String APPROVAL_DATE_FIVE = "APPROVAL_DATE_FIVE";
        /**
         * 审批人5。
         */
        public static final String APPROVAL_USER_FIVE = "APPROVAL_USER_FIVE";
        /**
         * 审批意见5。
         */
        public static final String APPROVAL_COMMENT_FIVE = "APPROVAL_COMMENT_FIVE";
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
    public PmDesignAssignmentBook setId(String id) {
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
    public PmDesignAssignmentBook setVer(Integer ver) {
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
    public PmDesignAssignmentBook setTs(LocalDateTime ts) {
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
    public PmDesignAssignmentBook setIsPreset(Boolean isPreset) {
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
    public PmDesignAssignmentBook setLastModiDt(LocalDateTime lastModiDt) {
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
    public PmDesignAssignmentBook setLastModiUserId(String lastModiUserId) {
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
    public PmDesignAssignmentBook setCode(String code) {
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
    public PmDesignAssignmentBook setName(String name) {
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
    public PmDesignAssignmentBook setRemark(String remark) {
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
    public PmDesignAssignmentBook setLkWfInstId(String lkWfInstId) {
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
     * 资金需求项目名称。
     */
    private String amoutPmPrjId;

    /**
     * 获取：资金需求项目名称。
     */
    public String getAmoutPmPrjId() {
        return this.amoutPmPrjId;
    }

    /**
     * 设置：资金需求项目名称。
     */
    public PmDesignAssignmentBook setAmoutPmPrjId(String amoutPmPrjId) {
        if (this.amoutPmPrjId == null && amoutPmPrjId == null) {
            // 均为null，不做处理。
        } else if (this.amoutPmPrjId != null && amoutPmPrjId != null) {
            // 均非null，判定不等，再做处理：
            if (this.amoutPmPrjId.compareTo(amoutPmPrjId) != 0) {
                this.amoutPmPrjId = amoutPmPrjId;
                if (!this.toUpdateCols.contains("AMOUT_PM_PRJ_ID")) {
                    this.toUpdateCols.add("AMOUT_PM_PRJ_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amoutPmPrjId = amoutPmPrjId;
            if (!this.toUpdateCols.contains("AMOUT_PM_PRJ_ID")) {
                this.toUpdateCols.add("AMOUT_PM_PRJ_ID");
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
    public PmDesignAssignmentBook setStatus(String status) {
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
    public PmDesignAssignmentBook setPrjCode(String prjCode) {
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
    public PmDesignAssignmentBook setReplyNo(String replyNo) {
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
    public PmDesignAssignmentBook setCrtUserId(String crtUserId) {
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
    public PmDesignAssignmentBook setCrtDeptId(String crtDeptId) {
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
    public PmDesignAssignmentBook setPrjSituation(String prjSituation) {
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
    public PmDesignAssignmentBook setCrtDt(LocalDateTime crtDt) {
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
    public PmDesignAssignmentBook setInvestmentSourceId(String investmentSourceId) {
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
    public PmDesignAssignmentBook setCustomerUnit(String customerUnit) {
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
    public PmDesignAssignmentBook setProjectTypeId(String projectTypeId) {
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
    public PmDesignAssignmentBook setPrjTotalInvest(BigDecimal prjTotalInvest) {
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
    public PmDesignAssignmentBook setProjectAmt(BigDecimal projectAmt) {
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
    public PmDesignAssignmentBook setProjectOtherAmt(BigDecimal projectOtherAmt) {
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
    public PmDesignAssignmentBook setPrepareAmt(BigDecimal prepareAmt) {
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
    public PmDesignAssignmentBook setConstructPeriodInterest(BigDecimal constructPeriodInterest) {
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
     * 任务书类型。
     */
    private String designTaskTypeId;

    /**
     * 获取：任务书类型。
     */
    public String getDesignTaskTypeId() {
        return this.designTaskTypeId;
    }

    /**
     * 设置：任务书类型。
     */
    public PmDesignAssignmentBook setDesignTaskTypeId(String designTaskTypeId) {
        if (this.designTaskTypeId == null && designTaskTypeId == null) {
            // 均为null，不做处理。
        } else if (this.designTaskTypeId != null && designTaskTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.designTaskTypeId.compareTo(designTaskTypeId) != 0) {
                this.designTaskTypeId = designTaskTypeId;
                if (!this.toUpdateCols.contains("DESIGN_TASK_TYPE_ID")) {
                    this.toUpdateCols.add("DESIGN_TASK_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designTaskTypeId = designTaskTypeId;
            if (!this.toUpdateCols.contains("DESIGN_TASK_TYPE_ID")) {
                this.toUpdateCols.add("DESIGN_TASK_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 设计单位1。
     */
    private String designerUnitOne;

    /**
     * 获取：设计单位1。
     */
    public String getDesignerUnitOne() {
        return this.designerUnitOne;
    }

    /**
     * 设置：设计单位1。
     */
    public PmDesignAssignmentBook setDesignerUnitOne(String designerUnitOne) {
        if (this.designerUnitOne == null && designerUnitOne == null) {
            // 均为null，不做处理。
        } else if (this.designerUnitOne != null && designerUnitOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.designerUnitOne.compareTo(designerUnitOne) != 0) {
                this.designerUnitOne = designerUnitOne;
                if (!this.toUpdateCols.contains("DESIGNER_UNIT_ONE")) {
                    this.toUpdateCols.add("DESIGNER_UNIT_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designerUnitOne = designerUnitOne;
            if (!this.toUpdateCols.contains("DESIGNER_UNIT_ONE")) {
                this.toUpdateCols.add("DESIGNER_UNIT_ONE");
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
    public PmDesignAssignmentBook setDesignerOne(String designerOne) {
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
    public PmDesignAssignmentBook setAttFileGroupId(String attFileGroupId) {
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
    private String remarkShort;

    /**
     * 获取：备注。
     */
    public String getRemarkShort() {
        return this.remarkShort;
    }

    /**
     * 设置：备注。
     */
    public PmDesignAssignmentBook setRemarkShort(String remarkShort) {
        if (this.remarkShort == null && remarkShort == null) {
            // 均为null，不做处理。
        } else if (this.remarkShort != null && remarkShort != null) {
            // 均非null，判定不等，再做处理：
            if (this.remarkShort.compareTo(remarkShort) != 0) {
                this.remarkShort = remarkShort;
                if (!this.toUpdateCols.contains("REMARK_SHORT")) {
                    this.toUpdateCols.add("REMARK_SHORT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remarkShort = remarkShort;
            if (!this.toUpdateCols.contains("REMARK_SHORT")) {
                this.toUpdateCols.add("REMARK_SHORT");
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
    public PmDesignAssignmentBook setApproveFileIdOne(String approveFileIdOne) {
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
    public PmDesignAssignmentBook setApprovalCommentOne(String approvalCommentOne) {
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
    public PmDesignAssignmentBook setApproveFileIdTwo(String approveFileIdTwo) {
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
    public PmDesignAssignmentBook setApprovalCommentTwo(String approvalCommentTwo) {
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
     * 指标。
     */
    private String indexId;

    /**
     * 获取：指标。
     */
    public String getIndexId() {
        return this.indexId;
    }

    /**
     * 设置：指标。
     */
    public PmDesignAssignmentBook setIndexId(String indexId) {
        if (this.indexId == null && indexId == null) {
            // 均为null，不做处理。
        } else if (this.indexId != null && indexId != null) {
            // 均非null，判定不等，再做处理：
            if (this.indexId.compareTo(indexId) != 0) {
                this.indexId = indexId;
                if (!this.toUpdateCols.contains("INDEX_ID")) {
                    this.toUpdateCols.add("INDEX_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.indexId = indexId;
            if (!this.toUpdateCols.contains("INDEX_ID")) {
                this.toUpdateCols.add("INDEX_ID");
            }
        }
        return this;
    }

    /**
     * 指标金额。
     */
    private BigDecimal tgtAmt;

    /**
     * 获取：指标金额。
     */
    public BigDecimal getTgtAmt() {
        return this.tgtAmt;
    }

    /**
     * 设置：指标金额。
     */
    public PmDesignAssignmentBook setTgtAmt(BigDecimal tgtAmt) {
        if (this.tgtAmt == null && tgtAmt == null) {
            // 均为null，不做处理。
        } else if (this.tgtAmt != null && tgtAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.tgtAmt.compareTo(tgtAmt) != 0) {
                this.tgtAmt = tgtAmt;
                if (!this.toUpdateCols.contains("TGT_AMT")) {
                    this.toUpdateCols.add("TGT_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.tgtAmt = tgtAmt;
            if (!this.toUpdateCols.contains("TGT_AMT")) {
                this.toUpdateCols.add("TGT_AMT");
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
    public PmDesignAssignmentBook setApproveFileIdThree(String approveFileIdThree) {
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
    public PmDesignAssignmentBook setApprovalCommentThree(String approvalCommentThree) {
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
     * 审批日期4。
     */
    private LocalDate approvalDateFour;

    /**
     * 获取：审批日期4。
     */
    public LocalDate getApprovalDateFour() {
        return this.approvalDateFour;
    }

    /**
     * 设置：审批日期4。
     */
    public PmDesignAssignmentBook setApprovalDateFour(LocalDate approvalDateFour) {
        if (this.approvalDateFour == null && approvalDateFour == null) {
            // 均为null，不做处理。
        } else if (this.approvalDateFour != null && approvalDateFour != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalDateFour.compareTo(approvalDateFour) != 0) {
                this.approvalDateFour = approvalDateFour;
                if (!this.toUpdateCols.contains("APPROVAL_DATE_FOUR")) {
                    this.toUpdateCols.add("APPROVAL_DATE_FOUR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalDateFour = approvalDateFour;
            if (!this.toUpdateCols.contains("APPROVAL_DATE_FOUR")) {
                this.toUpdateCols.add("APPROVAL_DATE_FOUR");
            }
        }
        return this;
    }

    /**
     * 审批人4。
     */
    private String approvalUserFour;

    /**
     * 获取：审批人4。
     */
    public String getApprovalUserFour() {
        return this.approvalUserFour;
    }

    /**
     * 设置：审批人4。
     */
    public PmDesignAssignmentBook setApprovalUserFour(String approvalUserFour) {
        if (this.approvalUserFour == null && approvalUserFour == null) {
            // 均为null，不做处理。
        } else if (this.approvalUserFour != null && approvalUserFour != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalUserFour.compareTo(approvalUserFour) != 0) {
                this.approvalUserFour = approvalUserFour;
                if (!this.toUpdateCols.contains("APPROVAL_USER_FOUR")) {
                    this.toUpdateCols.add("APPROVAL_USER_FOUR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalUserFour = approvalUserFour;
            if (!this.toUpdateCols.contains("APPROVAL_USER_FOUR")) {
                this.toUpdateCols.add("APPROVAL_USER_FOUR");
            }
        }
        return this;
    }

    /**
     * 审批意见4。
     */
    private String approvalCommentFour;

    /**
     * 获取：审批意见4。
     */
    public String getApprovalCommentFour() {
        return this.approvalCommentFour;
    }

    /**
     * 设置：审批意见4。
     */
    public PmDesignAssignmentBook setApprovalCommentFour(String approvalCommentFour) {
        if (this.approvalCommentFour == null && approvalCommentFour == null) {
            // 均为null，不做处理。
        } else if (this.approvalCommentFour != null && approvalCommentFour != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalCommentFour.compareTo(approvalCommentFour) != 0) {
                this.approvalCommentFour = approvalCommentFour;
                if (!this.toUpdateCols.contains("APPROVAL_COMMENT_FOUR")) {
                    this.toUpdateCols.add("APPROVAL_COMMENT_FOUR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalCommentFour = approvalCommentFour;
            if (!this.toUpdateCols.contains("APPROVAL_COMMENT_FOUR")) {
                this.toUpdateCols.add("APPROVAL_COMMENT_FOUR");
            }
        }
        return this;
    }

    /**
     * 审批日期5。
     */
    private LocalDate approvalDateFive;

    /**
     * 获取：审批日期5。
     */
    public LocalDate getApprovalDateFive() {
        return this.approvalDateFive;
    }

    /**
     * 设置：审批日期5。
     */
    public PmDesignAssignmentBook setApprovalDateFive(LocalDate approvalDateFive) {
        if (this.approvalDateFive == null && approvalDateFive == null) {
            // 均为null，不做处理。
        } else if (this.approvalDateFive != null && approvalDateFive != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalDateFive.compareTo(approvalDateFive) != 0) {
                this.approvalDateFive = approvalDateFive;
                if (!this.toUpdateCols.contains("APPROVAL_DATE_FIVE")) {
                    this.toUpdateCols.add("APPROVAL_DATE_FIVE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalDateFive = approvalDateFive;
            if (!this.toUpdateCols.contains("APPROVAL_DATE_FIVE")) {
                this.toUpdateCols.add("APPROVAL_DATE_FIVE");
            }
        }
        return this;
    }

    /**
     * 审批人5。
     */
    private String approvalUserFive;

    /**
     * 获取：审批人5。
     */
    public String getApprovalUserFive() {
        return this.approvalUserFive;
    }

    /**
     * 设置：审批人5。
     */
    public PmDesignAssignmentBook setApprovalUserFive(String approvalUserFive) {
        if (this.approvalUserFive == null && approvalUserFive == null) {
            // 均为null，不做处理。
        } else if (this.approvalUserFive != null && approvalUserFive != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalUserFive.compareTo(approvalUserFive) != 0) {
                this.approvalUserFive = approvalUserFive;
                if (!this.toUpdateCols.contains("APPROVAL_USER_FIVE")) {
                    this.toUpdateCols.add("APPROVAL_USER_FIVE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalUserFive = approvalUserFive;
            if (!this.toUpdateCols.contains("APPROVAL_USER_FIVE")) {
                this.toUpdateCols.add("APPROVAL_USER_FIVE");
            }
        }
        return this;
    }

    /**
     * 审批意见5。
     */
    private String approvalCommentFive;

    /**
     * 获取：审批意见5。
     */
    public String getApprovalCommentFive() {
        return this.approvalCommentFive;
    }

    /**
     * 设置：审批意见5。
     */
    public PmDesignAssignmentBook setApprovalCommentFive(String approvalCommentFive) {
        if (this.approvalCommentFive == null && approvalCommentFive == null) {
            // 均为null，不做处理。
        } else if (this.approvalCommentFive != null && approvalCommentFive != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalCommentFive.compareTo(approvalCommentFive) != 0) {
                this.approvalCommentFive = approvalCommentFive;
                if (!this.toUpdateCols.contains("APPROVAL_COMMENT_FIVE")) {
                    this.toUpdateCols.add("APPROVAL_COMMENT_FIVE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalCommentFive = approvalCommentFive;
            if (!this.toUpdateCols.contains("APPROVAL_COMMENT_FIVE")) {
                this.toUpdateCols.add("APPROVAL_COMMENT_FIVE");
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
    public static PmDesignAssignmentBook newData() {
        PmDesignAssignmentBook obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PmDesignAssignmentBook insertData() {
        PmDesignAssignmentBook obj = modelHelper.insertData();
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
    public static PmDesignAssignmentBook selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PmDesignAssignmentBook obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<PmDesignAssignmentBook> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PmDesignAssignmentBook> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<PmDesignAssignmentBook> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PmDesignAssignmentBook> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(PmDesignAssignmentBook fromModel, PmDesignAssignmentBook toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}