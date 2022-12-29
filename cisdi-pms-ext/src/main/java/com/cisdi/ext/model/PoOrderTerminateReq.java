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
 * 采购合同终止申请。
 */
public class PoOrderTerminateReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PoOrderTerminateReq> modelHelper = new ModelHelper<>("PO_ORDER_TERMINATE_REQ", new PoOrderTerminateReq());

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

    public static final String ENT_CODE = "PO_ORDER_TERMINATE_REQ";
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
         * 合同。
         */
        public static final String CONTRACT_ID = "CONTRACT_ID";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 立项批复文号。
         */
        public static final String PRJ_REPLY_NO = "PRJ_REPLY_NO";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 项目概况。
         */
        public static final String PRJ_SITUATION = "PRJ_SITUATION";
        /**
         * 投资来源。
         */
        public static final String INVESTMENT_SOURCE_ID = "INVESTMENT_SOURCE_ID";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
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
         * 合同编号。
         */
        public static final String CONTRACT_CODE = "CONTRACT_CODE";
        /**
         * 合同类型1。
         */
        public static final String CONTRACT_CATEGORY_ONE_ID = "CONTRACT_CATEGORY_ONE_ID";
        /**
         * 招采项目备案及归档。
         */
        public static final String PM_BID_KEEP_FILE_REQ_ID = "PM_BID_KEEP_FILE_REQ_ID";
        /**
         * 采购方式。
         */
        public static final String BUY_TYPE_ID = "BUY_TYPE_ID";
        /**
         * 招标控制价发起。
         */
        public static final String BID_CTL_PRICE_LAUNCH = "BID_CTL_PRICE_LAUNCH";
        /**
         * 采购方式。
         */
        public static final String PURCHASE_TYPE = "PURCHASE_TYPE";
        /**
         * 中标单位1。
         */
        public static final String WIN_BID_UNIT_ONE = "WIN_BID_UNIT_ONE";
        /**
         * 中标待签约额。
         */
        public static final String WINNING_BIDS_AMOUNT = "WINNING_BIDS_AMOUNT";
        /**
         * 预计总天数。
         */
        public static final String PLAN_TOTAL_DAYS = "PLAN_TOTAL_DAYS";
        /**
         * 合同金额（万）。
         */
        public static final String CONTRACT_PRICE = "CONTRACT_PRICE";
        /**
         * 是否涉及保函下拉。
         */
        public static final String IS_REFER_GUARANTEE_ID = "IS_REFER_GUARANTEE_ID";
        /**
         * 保函类型（多选）。
         */
        public static final String GUARANTEE_LETTER_TYPE_IDS = "GUARANTEE_LETTER_TYPE_IDS";
        /**
         * 是否3。
         */
        public static final String YES_NO_THREE = "YES_NO_THREE";
        /**
         * 附件。
         */
        public static final String ATT_FILE_GROUP_ID = "ATT_FILE_GROUP_ID";
        /**
         * 补充说明。
         */
        public static final String ADDITIONAL_REMARK = "ADDITIONAL_REMARK";
        /**
         * 审批人1。
         */
        public static final String APPROVAL_USER_ONE = "APPROVAL_USER_ONE";
        /**
         * 审批日期1。
         */
        public static final String APPROVAL_DATE_ONE = "APPROVAL_DATE_ONE";
        /**
         * 部门负责人附件。
         */
        public static final String DEPT_HEAD_FILE_GROUP_ID = "DEPT_HEAD_FILE_GROUP_ID";
        /**
         * 部门负责人审核意见。
         */
        public static final String DEPT_HEAD_APPROVAL_MESSAGE = "DEPT_HEAD_APPROVAL_MESSAGE";
        /**
         * 审批人2。
         */
        public static final String APPROVAL_USER_TWO = "APPROVAL_USER_TWO";
        /**
         * 审批日期2。
         */
        public static final String APPROVAL_DATE_TWO = "APPROVAL_DATE_TWO";
        /**
         * 成本合约部附件。
         */
        public static final String COST_CONTRACT_DEPT_FILE_GROUP_ID = "COST_CONTRACT_DEPT_FILE_GROUP_ID";
        /**
         * 成本合约部审核意见。
         */
        public static final String COST_CONTRACT_APPROVAL_MESSAGE = "COST_CONTRACT_APPROVAL_MESSAGE";
        /**
         * 审批人3。
         */
        public static final String APPROVAL_USER_THREE = "APPROVAL_USER_THREE";
        /**
         * 审批日期3。
         */
        public static final String APPROVAL_DATE_THREE = "APPROVAL_DATE_THREE";
        /**
         * 财务法务附件。
         */
        public static final String FINANCE_LEGAL_CONTRACT_DEPT_FILE_GROUP_ID = "FINANCE_LEGAL_CONTRACT_DEPT_FILE_GROUP_ID";
        /**
         * 财务法务审核意见。
         */
        public static final String FINANCE_LEGAL_APPROVAL_MESSAGE = "FINANCE_LEGAL_APPROVAL_MESSAGE";
        /**
         * 审批人4。
         */
        public static final String APPROVAL_USER_FOUR = "APPROVAL_USER_FOUR";
        /**
         * 审批日期4。
         */
        public static final String APPROVAL_DATE_FOUR = "APPROVAL_DATE_FOUR";
        /**
         * 审批附件1。
         */
        public static final String APPROVE_FILE_ID_ONE = "APPROVE_FILE_ID_ONE";
        /**
         * 审批意见1。
         */
        public static final String APPROVAL_COMMENT_ONE = "APPROVAL_COMMENT_ONE";
        /**
         * 审批人5。
         */
        public static final String APPROVAL_USER_FIVE = "APPROVAL_USER_FIVE";
        /**
         * 审批日期5。
         */
        public static final String APPROVAL_DATE_FIVE = "APPROVAL_DATE_FIVE";
        /**
         * 领导审批附件。
         */
        public static final String LEADER_APPROVE_FILE_GROUP_ID = "LEADER_APPROVE_FILE_GROUP_ID";
        /**
         * 领导审批意见。
         */
        public static final String LEADER_APPROVE_COMMENT = "LEADER_APPROVE_COMMENT";
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
    public PoOrderTerminateReq setId(String id) {
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
    public PoOrderTerminateReq setVer(Integer ver) {
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
    public PoOrderTerminateReq setTs(LocalDateTime ts) {
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
    public PoOrderTerminateReq setIsPreset(Boolean isPreset) {
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
    public PoOrderTerminateReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PoOrderTerminateReq setLastModiUserId(String lastModiUserId) {
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
    public PoOrderTerminateReq setCode(String code) {
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
    public PoOrderTerminateReq setName(String name) {
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
    public PoOrderTerminateReq setRemark(String remark) {
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
    public PoOrderTerminateReq setLkWfInstId(String lkWfInstId) {
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
    public PoOrderTerminateReq setPmPrjId(String pmPrjId) {
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
    public PoOrderTerminateReq setStatus(String status) {
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
     * 合同。
     */
    private String contractId;

    /**
     * 获取：合同。
     */
    public String getContractId() {
        return this.contractId;
    }

    /**
     * 设置：合同。
     */
    public PoOrderTerminateReq setContractId(String contractId) {
        if (this.contractId == null && contractId == null) {
            // 均为null，不做处理。
        } else if (this.contractId != null && contractId != null) {
            // 均非null，判定不等，再做处理：
            if (this.contractId.compareTo(contractId) != 0) {
                this.contractId = contractId;
                if (!this.toUpdateCols.contains("CONTRACT_ID")) {
                    this.toUpdateCols.add("CONTRACT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contractId = contractId;
            if (!this.toUpdateCols.contains("CONTRACT_ID")) {
                this.toUpdateCols.add("CONTRACT_ID");
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
    public PoOrderTerminateReq setCrtUserId(String crtUserId) {
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
    public PoOrderTerminateReq setPrjReplyNo(String prjReplyNo) {
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
    public PoOrderTerminateReq setCrtDeptId(String crtDeptId) {
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
    public PoOrderTerminateReq setPrjSituation(String prjSituation) {
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
    public PoOrderTerminateReq setInvestmentSourceId(String investmentSourceId) {
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
    public PoOrderTerminateReq setCrtDt(LocalDateTime crtDt) {
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
    public PoOrderTerminateReq setCustomerUnit(String customerUnit) {
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
    public PoOrderTerminateReq setProjectTypeId(String projectTypeId) {
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
    public PoOrderTerminateReq setPrjTotalInvest(BigDecimal prjTotalInvest) {
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
    public PoOrderTerminateReq setProjectAmt(BigDecimal projectAmt) {
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
    public PoOrderTerminateReq setProjectOtherAmt(BigDecimal projectOtherAmt) {
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
    public PoOrderTerminateReq setPrepareAmt(BigDecimal prepareAmt) {
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
    public PoOrderTerminateReq setConstructPeriodInterest(BigDecimal constructPeriodInterest) {
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
     * 合同编号。
     */
    private String contractCode;

    /**
     * 获取：合同编号。
     */
    public String getContractCode() {
        return this.contractCode;
    }

    /**
     * 设置：合同编号。
     */
    public PoOrderTerminateReq setContractCode(String contractCode) {
        if (this.contractCode == null && contractCode == null) {
            // 均为null，不做处理。
        } else if (this.contractCode != null && contractCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.contractCode.compareTo(contractCode) != 0) {
                this.contractCode = contractCode;
                if (!this.toUpdateCols.contains("CONTRACT_CODE")) {
                    this.toUpdateCols.add("CONTRACT_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contractCode = contractCode;
            if (!this.toUpdateCols.contains("CONTRACT_CODE")) {
                this.toUpdateCols.add("CONTRACT_CODE");
            }
        }
        return this;
    }

    /**
     * 合同类型1。
     */
    private String contractCategoryOneId;

    /**
     * 获取：合同类型1。
     */
    public String getContractCategoryOneId() {
        return this.contractCategoryOneId;
    }

    /**
     * 设置：合同类型1。
     */
    public PoOrderTerminateReq setContractCategoryOneId(String contractCategoryOneId) {
        if (this.contractCategoryOneId == null && contractCategoryOneId == null) {
            // 均为null，不做处理。
        } else if (this.contractCategoryOneId != null && contractCategoryOneId != null) {
            // 均非null，判定不等，再做处理：
            if (this.contractCategoryOneId.compareTo(contractCategoryOneId) != 0) {
                this.contractCategoryOneId = contractCategoryOneId;
                if (!this.toUpdateCols.contains("CONTRACT_CATEGORY_ONE_ID")) {
                    this.toUpdateCols.add("CONTRACT_CATEGORY_ONE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contractCategoryOneId = contractCategoryOneId;
            if (!this.toUpdateCols.contains("CONTRACT_CATEGORY_ONE_ID")) {
                this.toUpdateCols.add("CONTRACT_CATEGORY_ONE_ID");
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
    public PoOrderTerminateReq setPmBidKeepFileReqId(String pmBidKeepFileReqId) {
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
     * 采购方式。
     */
    private String buyTypeId;

    /**
     * 获取：采购方式。
     */
    public String getBuyTypeId() {
        return this.buyTypeId;
    }

    /**
     * 设置：采购方式。
     */
    public PoOrderTerminateReq setBuyTypeId(String buyTypeId) {
        if (this.buyTypeId == null && buyTypeId == null) {
            // 均为null，不做处理。
        } else if (this.buyTypeId != null && buyTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.buyTypeId.compareTo(buyTypeId) != 0) {
                this.buyTypeId = buyTypeId;
                if (!this.toUpdateCols.contains("BUY_TYPE_ID")) {
                    this.toUpdateCols.add("BUY_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.buyTypeId = buyTypeId;
            if (!this.toUpdateCols.contains("BUY_TYPE_ID")) {
                this.toUpdateCols.add("BUY_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 招标控制价发起。
     */
    private BigDecimal bidCtlPriceLaunch;

    /**
     * 获取：招标控制价发起。
     */
    public BigDecimal getBidCtlPriceLaunch() {
        return this.bidCtlPriceLaunch;
    }

    /**
     * 设置：招标控制价发起。
     */
    public PoOrderTerminateReq setBidCtlPriceLaunch(BigDecimal bidCtlPriceLaunch) {
        if (this.bidCtlPriceLaunch == null && bidCtlPriceLaunch == null) {
            // 均为null，不做处理。
        } else if (this.bidCtlPriceLaunch != null && bidCtlPriceLaunch != null) {
            // 均非null，判定不等，再做处理：
            if (this.bidCtlPriceLaunch.compareTo(bidCtlPriceLaunch) != 0) {
                this.bidCtlPriceLaunch = bidCtlPriceLaunch;
                if (!this.toUpdateCols.contains("BID_CTL_PRICE_LAUNCH")) {
                    this.toUpdateCols.add("BID_CTL_PRICE_LAUNCH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bidCtlPriceLaunch = bidCtlPriceLaunch;
            if (!this.toUpdateCols.contains("BID_CTL_PRICE_LAUNCH")) {
                this.toUpdateCols.add("BID_CTL_PRICE_LAUNCH");
            }
        }
        return this;
    }

    /**
     * 采购方式。
     */
    private String purchaseType;

    /**
     * 获取：采购方式。
     */
    public String getPurchaseType() {
        return this.purchaseType;
    }

    /**
     * 设置：采购方式。
     */
    public PoOrderTerminateReq setPurchaseType(String purchaseType) {
        if (this.purchaseType == null && purchaseType == null) {
            // 均为null，不做处理。
        } else if (this.purchaseType != null && purchaseType != null) {
            // 均非null，判定不等，再做处理：
            if (this.purchaseType.compareTo(purchaseType) != 0) {
                this.purchaseType = purchaseType;
                if (!this.toUpdateCols.contains("PURCHASE_TYPE")) {
                    this.toUpdateCols.add("PURCHASE_TYPE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.purchaseType = purchaseType;
            if (!this.toUpdateCols.contains("PURCHASE_TYPE")) {
                this.toUpdateCols.add("PURCHASE_TYPE");
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
    public PoOrderTerminateReq setWinBidUnitOne(String winBidUnitOne) {
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
     * 中标待签约额。
     */
    private BigDecimal winningBidsAmount;

    /**
     * 获取：中标待签约额。
     */
    public BigDecimal getWinningBidsAmount() {
        return this.winningBidsAmount;
    }

    /**
     * 设置：中标待签约额。
     */
    public PoOrderTerminateReq setWinningBidsAmount(BigDecimal winningBidsAmount) {
        if (this.winningBidsAmount == null && winningBidsAmount == null) {
            // 均为null，不做处理。
        } else if (this.winningBidsAmount != null && winningBidsAmount != null) {
            // 均非null，判定不等，再做处理：
            if (this.winningBidsAmount.compareTo(winningBidsAmount) != 0) {
                this.winningBidsAmount = winningBidsAmount;
                if (!this.toUpdateCols.contains("WINNING_BIDS_AMOUNT")) {
                    this.toUpdateCols.add("WINNING_BIDS_AMOUNT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.winningBidsAmount = winningBidsAmount;
            if (!this.toUpdateCols.contains("WINNING_BIDS_AMOUNT")) {
                this.toUpdateCols.add("WINNING_BIDS_AMOUNT");
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
    public PoOrderTerminateReq setPlanTotalDays(Integer planTotalDays) {
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
     * 合同金额（万）。
     */
    private BigDecimal contractPrice;

    /**
     * 获取：合同金额（万）。
     */
    public BigDecimal getContractPrice() {
        return this.contractPrice;
    }

    /**
     * 设置：合同金额（万）。
     */
    public PoOrderTerminateReq setContractPrice(BigDecimal contractPrice) {
        if (this.contractPrice == null && contractPrice == null) {
            // 均为null，不做处理。
        } else if (this.contractPrice != null && contractPrice != null) {
            // 均非null，判定不等，再做处理：
            if (this.contractPrice.compareTo(contractPrice) != 0) {
                this.contractPrice = contractPrice;
                if (!this.toUpdateCols.contains("CONTRACT_PRICE")) {
                    this.toUpdateCols.add("CONTRACT_PRICE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contractPrice = contractPrice;
            if (!this.toUpdateCols.contains("CONTRACT_PRICE")) {
                this.toUpdateCols.add("CONTRACT_PRICE");
            }
        }
        return this;
    }

    /**
     * 是否涉及保函下拉。
     */
    private String isReferGuaranteeId;

    /**
     * 获取：是否涉及保函下拉。
     */
    public String getIsReferGuaranteeId() {
        return this.isReferGuaranteeId;
    }

    /**
     * 设置：是否涉及保函下拉。
     */
    public PoOrderTerminateReq setIsReferGuaranteeId(String isReferGuaranteeId) {
        if (this.isReferGuaranteeId == null && isReferGuaranteeId == null) {
            // 均为null，不做处理。
        } else if (this.isReferGuaranteeId != null && isReferGuaranteeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.isReferGuaranteeId.compareTo(isReferGuaranteeId) != 0) {
                this.isReferGuaranteeId = isReferGuaranteeId;
                if (!this.toUpdateCols.contains("IS_REFER_GUARANTEE_ID")) {
                    this.toUpdateCols.add("IS_REFER_GUARANTEE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isReferGuaranteeId = isReferGuaranteeId;
            if (!this.toUpdateCols.contains("IS_REFER_GUARANTEE_ID")) {
                this.toUpdateCols.add("IS_REFER_GUARANTEE_ID");
            }
        }
        return this;
    }

    /**
     * 保函类型（多选）。
     */
    private String guaranteeLetterTypeIds;

    /**
     * 获取：保函类型（多选）。
     */
    public String getGuaranteeLetterTypeIds() {
        return this.guaranteeLetterTypeIds;
    }

    /**
     * 设置：保函类型（多选）。
     */
    public PoOrderTerminateReq setGuaranteeLetterTypeIds(String guaranteeLetterTypeIds) {
        if (this.guaranteeLetterTypeIds == null && guaranteeLetterTypeIds == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeLetterTypeIds != null && guaranteeLetterTypeIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeLetterTypeIds.compareTo(guaranteeLetterTypeIds) != 0) {
                this.guaranteeLetterTypeIds = guaranteeLetterTypeIds;
                if (!this.toUpdateCols.contains("GUARANTEE_LETTER_TYPE_IDS")) {
                    this.toUpdateCols.add("GUARANTEE_LETTER_TYPE_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeLetterTypeIds = guaranteeLetterTypeIds;
            if (!this.toUpdateCols.contains("GUARANTEE_LETTER_TYPE_IDS")) {
                this.toUpdateCols.add("GUARANTEE_LETTER_TYPE_IDS");
            }
        }
        return this;
    }

    /**
     * 是否3。
     */
    private String yesNoThree;

    /**
     * 获取：是否3。
     */
    public String getYesNoThree() {
        return this.yesNoThree;
    }

    /**
     * 设置：是否3。
     */
    public PoOrderTerminateReq setYesNoThree(String yesNoThree) {
        if (this.yesNoThree == null && yesNoThree == null) {
            // 均为null，不做处理。
        } else if (this.yesNoThree != null && yesNoThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.yesNoThree.compareTo(yesNoThree) != 0) {
                this.yesNoThree = yesNoThree;
                if (!this.toUpdateCols.contains("YES_NO_THREE")) {
                    this.toUpdateCols.add("YES_NO_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yesNoThree = yesNoThree;
            if (!this.toUpdateCols.contains("YES_NO_THREE")) {
                this.toUpdateCols.add("YES_NO_THREE");
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
    public PoOrderTerminateReq setAttFileGroupId(String attFileGroupId) {
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
     * 补充说明。
     */
    private String additionalRemark;

    /**
     * 获取：补充说明。
     */
    public String getAdditionalRemark() {
        return this.additionalRemark;
    }

    /**
     * 设置：补充说明。
     */
    public PoOrderTerminateReq setAdditionalRemark(String additionalRemark) {
        if (this.additionalRemark == null && additionalRemark == null) {
            // 均为null，不做处理。
        } else if (this.additionalRemark != null && additionalRemark != null) {
            // 均非null，判定不等，再做处理：
            if (this.additionalRemark.compareTo(additionalRemark) != 0) {
                this.additionalRemark = additionalRemark;
                if (!this.toUpdateCols.contains("ADDITIONAL_REMARK")) {
                    this.toUpdateCols.add("ADDITIONAL_REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.additionalRemark = additionalRemark;
            if (!this.toUpdateCols.contains("ADDITIONAL_REMARK")) {
                this.toUpdateCols.add("ADDITIONAL_REMARK");
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
    public PoOrderTerminateReq setApprovalUserOne(String approvalUserOne) {
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
    public PoOrderTerminateReq setApprovalDateOne(LocalDate approvalDateOne) {
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
     * 部门负责人附件。
     */
    private String deptHeadFileGroupId;

    /**
     * 获取：部门负责人附件。
     */
    public String getDeptHeadFileGroupId() {
        return this.deptHeadFileGroupId;
    }

    /**
     * 设置：部门负责人附件。
     */
    public PoOrderTerminateReq setDeptHeadFileGroupId(String deptHeadFileGroupId) {
        if (this.deptHeadFileGroupId == null && deptHeadFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.deptHeadFileGroupId != null && deptHeadFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.deptHeadFileGroupId.compareTo(deptHeadFileGroupId) != 0) {
                this.deptHeadFileGroupId = deptHeadFileGroupId;
                if (!this.toUpdateCols.contains("DEPT_HEAD_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("DEPT_HEAD_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.deptHeadFileGroupId = deptHeadFileGroupId;
            if (!this.toUpdateCols.contains("DEPT_HEAD_FILE_GROUP_ID")) {
                this.toUpdateCols.add("DEPT_HEAD_FILE_GROUP_ID");
            }
        }
        return this;
    }

    /**
     * 部门负责人审核意见。
     */
    private String deptHeadApprovalMessage;

    /**
     * 获取：部门负责人审核意见。
     */
    public String getDeptHeadApprovalMessage() {
        return this.deptHeadApprovalMessage;
    }

    /**
     * 设置：部门负责人审核意见。
     */
    public PoOrderTerminateReq setDeptHeadApprovalMessage(String deptHeadApprovalMessage) {
        if (this.deptHeadApprovalMessage == null && deptHeadApprovalMessage == null) {
            // 均为null，不做处理。
        } else if (this.deptHeadApprovalMessage != null && deptHeadApprovalMessage != null) {
            // 均非null，判定不等，再做处理：
            if (this.deptHeadApprovalMessage.compareTo(deptHeadApprovalMessage) != 0) {
                this.deptHeadApprovalMessage = deptHeadApprovalMessage;
                if (!this.toUpdateCols.contains("DEPT_HEAD_APPROVAL_MESSAGE")) {
                    this.toUpdateCols.add("DEPT_HEAD_APPROVAL_MESSAGE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.deptHeadApprovalMessage = deptHeadApprovalMessage;
            if (!this.toUpdateCols.contains("DEPT_HEAD_APPROVAL_MESSAGE")) {
                this.toUpdateCols.add("DEPT_HEAD_APPROVAL_MESSAGE");
            }
        }
        return this;
    }

    /**
     * 审批人2。
     */
    private String approvalUserTwo;

    /**
     * 获取：审批人2。
     */
    public String getApprovalUserTwo() {
        return this.approvalUserTwo;
    }

    /**
     * 设置：审批人2。
     */
    public PoOrderTerminateReq setApprovalUserTwo(String approvalUserTwo) {
        if (this.approvalUserTwo == null && approvalUserTwo == null) {
            // 均为null，不做处理。
        } else if (this.approvalUserTwo != null && approvalUserTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalUserTwo.compareTo(approvalUserTwo) != 0) {
                this.approvalUserTwo = approvalUserTwo;
                if (!this.toUpdateCols.contains("APPROVAL_USER_TWO")) {
                    this.toUpdateCols.add("APPROVAL_USER_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalUserTwo = approvalUserTwo;
            if (!this.toUpdateCols.contains("APPROVAL_USER_TWO")) {
                this.toUpdateCols.add("APPROVAL_USER_TWO");
            }
        }
        return this;
    }

    /**
     * 审批日期2。
     */
    private LocalDate approvalDateTwo;

    /**
     * 获取：审批日期2。
     */
    public LocalDate getApprovalDateTwo() {
        return this.approvalDateTwo;
    }

    /**
     * 设置：审批日期2。
     */
    public PoOrderTerminateReq setApprovalDateTwo(LocalDate approvalDateTwo) {
        if (this.approvalDateTwo == null && approvalDateTwo == null) {
            // 均为null，不做处理。
        } else if (this.approvalDateTwo != null && approvalDateTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalDateTwo.compareTo(approvalDateTwo) != 0) {
                this.approvalDateTwo = approvalDateTwo;
                if (!this.toUpdateCols.contains("APPROVAL_DATE_TWO")) {
                    this.toUpdateCols.add("APPROVAL_DATE_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalDateTwo = approvalDateTwo;
            if (!this.toUpdateCols.contains("APPROVAL_DATE_TWO")) {
                this.toUpdateCols.add("APPROVAL_DATE_TWO");
            }
        }
        return this;
    }

    /**
     * 成本合约部附件。
     */
    private String costContractDeptFileGroupId;

    /**
     * 获取：成本合约部附件。
     */
    public String getCostContractDeptFileGroupId() {
        return this.costContractDeptFileGroupId;
    }

    /**
     * 设置：成本合约部附件。
     */
    public PoOrderTerminateReq setCostContractDeptFileGroupId(String costContractDeptFileGroupId) {
        if (this.costContractDeptFileGroupId == null && costContractDeptFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.costContractDeptFileGroupId != null && costContractDeptFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.costContractDeptFileGroupId.compareTo(costContractDeptFileGroupId) != 0) {
                this.costContractDeptFileGroupId = costContractDeptFileGroupId;
                if (!this.toUpdateCols.contains("COST_CONTRACT_DEPT_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("COST_CONTRACT_DEPT_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.costContractDeptFileGroupId = costContractDeptFileGroupId;
            if (!this.toUpdateCols.contains("COST_CONTRACT_DEPT_FILE_GROUP_ID")) {
                this.toUpdateCols.add("COST_CONTRACT_DEPT_FILE_GROUP_ID");
            }
        }
        return this;
    }

    /**
     * 成本合约部审核意见。
     */
    private String costContractApprovalMessage;

    /**
     * 获取：成本合约部审核意见。
     */
    public String getCostContractApprovalMessage() {
        return this.costContractApprovalMessage;
    }

    /**
     * 设置：成本合约部审核意见。
     */
    public PoOrderTerminateReq setCostContractApprovalMessage(String costContractApprovalMessage) {
        if (this.costContractApprovalMessage == null && costContractApprovalMessage == null) {
            // 均为null，不做处理。
        } else if (this.costContractApprovalMessage != null && costContractApprovalMessage != null) {
            // 均非null，判定不等，再做处理：
            if (this.costContractApprovalMessage.compareTo(costContractApprovalMessage) != 0) {
                this.costContractApprovalMessage = costContractApprovalMessage;
                if (!this.toUpdateCols.contains("COST_CONTRACT_APPROVAL_MESSAGE")) {
                    this.toUpdateCols.add("COST_CONTRACT_APPROVAL_MESSAGE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.costContractApprovalMessage = costContractApprovalMessage;
            if (!this.toUpdateCols.contains("COST_CONTRACT_APPROVAL_MESSAGE")) {
                this.toUpdateCols.add("COST_CONTRACT_APPROVAL_MESSAGE");
            }
        }
        return this;
    }

    /**
     * 审批人3。
     */
    private String approvalUserThree;

    /**
     * 获取：审批人3。
     */
    public String getApprovalUserThree() {
        return this.approvalUserThree;
    }

    /**
     * 设置：审批人3。
     */
    public PoOrderTerminateReq setApprovalUserThree(String approvalUserThree) {
        if (this.approvalUserThree == null && approvalUserThree == null) {
            // 均为null，不做处理。
        } else if (this.approvalUserThree != null && approvalUserThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalUserThree.compareTo(approvalUserThree) != 0) {
                this.approvalUserThree = approvalUserThree;
                if (!this.toUpdateCols.contains("APPROVAL_USER_THREE")) {
                    this.toUpdateCols.add("APPROVAL_USER_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalUserThree = approvalUserThree;
            if (!this.toUpdateCols.contains("APPROVAL_USER_THREE")) {
                this.toUpdateCols.add("APPROVAL_USER_THREE");
            }
        }
        return this;
    }

    /**
     * 审批日期3。
     */
    private LocalDate approvalDateThree;

    /**
     * 获取：审批日期3。
     */
    public LocalDate getApprovalDateThree() {
        return this.approvalDateThree;
    }

    /**
     * 设置：审批日期3。
     */
    public PoOrderTerminateReq setApprovalDateThree(LocalDate approvalDateThree) {
        if (this.approvalDateThree == null && approvalDateThree == null) {
            // 均为null，不做处理。
        } else if (this.approvalDateThree != null && approvalDateThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalDateThree.compareTo(approvalDateThree) != 0) {
                this.approvalDateThree = approvalDateThree;
                if (!this.toUpdateCols.contains("APPROVAL_DATE_THREE")) {
                    this.toUpdateCols.add("APPROVAL_DATE_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalDateThree = approvalDateThree;
            if (!this.toUpdateCols.contains("APPROVAL_DATE_THREE")) {
                this.toUpdateCols.add("APPROVAL_DATE_THREE");
            }
        }
        return this;
    }

    /**
     * 财务法务附件。
     */
    private String financeLegalContractDeptFileGroupId;

    /**
     * 获取：财务法务附件。
     */
    public String getFinanceLegalContractDeptFileGroupId() {
        return this.financeLegalContractDeptFileGroupId;
    }

    /**
     * 设置：财务法务附件。
     */
    public PoOrderTerminateReq setFinanceLegalContractDeptFileGroupId(String financeLegalContractDeptFileGroupId) {
        if (this.financeLegalContractDeptFileGroupId == null && financeLegalContractDeptFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.financeLegalContractDeptFileGroupId != null && financeLegalContractDeptFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.financeLegalContractDeptFileGroupId.compareTo(financeLegalContractDeptFileGroupId) != 0) {
                this.financeLegalContractDeptFileGroupId = financeLegalContractDeptFileGroupId;
                if (!this.toUpdateCols.contains("FINANCE_LEGAL_CONTRACT_DEPT_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("FINANCE_LEGAL_CONTRACT_DEPT_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financeLegalContractDeptFileGroupId = financeLegalContractDeptFileGroupId;
            if (!this.toUpdateCols.contains("FINANCE_LEGAL_CONTRACT_DEPT_FILE_GROUP_ID")) {
                this.toUpdateCols.add("FINANCE_LEGAL_CONTRACT_DEPT_FILE_GROUP_ID");
            }
        }
        return this;
    }

    /**
     * 财务法务审核意见。
     */
    private String financeLegalApprovalMessage;

    /**
     * 获取：财务法务审核意见。
     */
    public String getFinanceLegalApprovalMessage() {
        return this.financeLegalApprovalMessage;
    }

    /**
     * 设置：财务法务审核意见。
     */
    public PoOrderTerminateReq setFinanceLegalApprovalMessage(String financeLegalApprovalMessage) {
        if (this.financeLegalApprovalMessage == null && financeLegalApprovalMessage == null) {
            // 均为null，不做处理。
        } else if (this.financeLegalApprovalMessage != null && financeLegalApprovalMessage != null) {
            // 均非null，判定不等，再做处理：
            if (this.financeLegalApprovalMessage.compareTo(financeLegalApprovalMessage) != 0) {
                this.financeLegalApprovalMessage = financeLegalApprovalMessage;
                if (!this.toUpdateCols.contains("FINANCE_LEGAL_APPROVAL_MESSAGE")) {
                    this.toUpdateCols.add("FINANCE_LEGAL_APPROVAL_MESSAGE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financeLegalApprovalMessage = financeLegalApprovalMessage;
            if (!this.toUpdateCols.contains("FINANCE_LEGAL_APPROVAL_MESSAGE")) {
                this.toUpdateCols.add("FINANCE_LEGAL_APPROVAL_MESSAGE");
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
    public PoOrderTerminateReq setApprovalUserFour(String approvalUserFour) {
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
    public PoOrderTerminateReq setApprovalDateFour(LocalDate approvalDateFour) {
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
    public PoOrderTerminateReq setApproveFileIdOne(String approveFileIdOne) {
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
    public PoOrderTerminateReq setApprovalCommentOne(String approvalCommentOne) {
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
    public PoOrderTerminateReq setApprovalUserFive(String approvalUserFive) {
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
    public PoOrderTerminateReq setApprovalDateFive(LocalDate approvalDateFive) {
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
     * 领导审批附件。
     */
    private String leaderApproveFileGroupId;

    /**
     * 获取：领导审批附件。
     */
    public String getLeaderApproveFileGroupId() {
        return this.leaderApproveFileGroupId;
    }

    /**
     * 设置：领导审批附件。
     */
    public PoOrderTerminateReq setLeaderApproveFileGroupId(String leaderApproveFileGroupId) {
        if (this.leaderApproveFileGroupId == null && leaderApproveFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.leaderApproveFileGroupId != null && leaderApproveFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.leaderApproveFileGroupId.compareTo(leaderApproveFileGroupId) != 0) {
                this.leaderApproveFileGroupId = leaderApproveFileGroupId;
                if (!this.toUpdateCols.contains("LEADER_APPROVE_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("LEADER_APPROVE_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.leaderApproveFileGroupId = leaderApproveFileGroupId;
            if (!this.toUpdateCols.contains("LEADER_APPROVE_FILE_GROUP_ID")) {
                this.toUpdateCols.add("LEADER_APPROVE_FILE_GROUP_ID");
            }
        }
        return this;
    }

    /**
     * 领导审批意见。
     */
    private String leaderApproveComment;

    /**
     * 获取：领导审批意见。
     */
    public String getLeaderApproveComment() {
        return this.leaderApproveComment;
    }

    /**
     * 设置：领导审批意见。
     */
    public PoOrderTerminateReq setLeaderApproveComment(String leaderApproveComment) {
        if (this.leaderApproveComment == null && leaderApproveComment == null) {
            // 均为null，不做处理。
        } else if (this.leaderApproveComment != null && leaderApproveComment != null) {
            // 均非null，判定不等，再做处理：
            if (this.leaderApproveComment.compareTo(leaderApproveComment) != 0) {
                this.leaderApproveComment = leaderApproveComment;
                if (!this.toUpdateCols.contains("LEADER_APPROVE_COMMENT")) {
                    this.toUpdateCols.add("LEADER_APPROVE_COMMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.leaderApproveComment = leaderApproveComment;
            if (!this.toUpdateCols.contains("LEADER_APPROVE_COMMENT")) {
                this.toUpdateCols.add("LEADER_APPROVE_COMMENT");
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
    public static PoOrderTerminateReq newData() {
        PoOrderTerminateReq obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PoOrderTerminateReq insertData() {
        PoOrderTerminateReq obj = modelHelper.insertData();
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
    public static PoOrderTerminateReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PoOrderTerminateReq obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<PoOrderTerminateReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PoOrderTerminateReq> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<PoOrderTerminateReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PoOrderTerminateReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(PoOrderTerminateReq fromModel, PoOrderTerminateReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}