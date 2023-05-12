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
 * 合同签订(流程)。
 */
public class PoOrderReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PoOrderReq> modelHelper = new ModelHelper<>("PO_ORDER_REQ", new PoOrderReq());

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

    public static final String ENT_CODE = "PO_ORDER_REQ";
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
         * 锁定流程实例。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 代码。
         */
        public static final String CODE = "CODE";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 名称。
         */
        public static final String NAME = "NAME";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 业主单位1。
         */
        public static final String CUSTOMER_UNIT_ONE = "CUSTOMER_UNIT_ONE";
        /**
         * 项目(多值)。
         */
        public static final String PM_PRJ_IDS = "PM_PRJ_IDS";
        /**
         * 合同名称。
         */
        public static final String CONTRACT_NAME = "CONTRACT_NAME";
        /**
         * 合同编号。
         */
        public static final String CONTRACT_CODE = "CONTRACT_CODE";
        /**
         * 合同类型1。
         */
        public static final String CONTRACT_CATEGORY_ONE_ID = "CONTRACT_CATEGORY_ONE_ID";
        /**
         * 金额1。
         */
        public static final String AMT_ONE = "AMT_ONE";
        /**
         * 金额2。
         */
        public static final String AMT_TWO = "AMT_TWO";
        /**
         * 金额3。
         */
        public static final String AMT_THREE = "AMT_THREE";
        /**
         * 金额4。
         */
        public static final String AMT_FOUR = "AMT_FOUR";
        /**
         * 预计总天数。
         */
        public static final String PLAN_TOTAL_DAYS = "PLAN_TOTAL_DAYS";
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
         * 用户。
         */
        public static final String AD_USER_ID = "AD_USER_ID";
        /**
         * 联系人电话1。
         */
        public static final String CONTACT_MOBILE_ONE = "CONTACT_MOBILE_ONE";
        /**
         * 备注1。
         */
        public static final String REMARK_LONG_ONE = "REMARK_LONG_ONE";
        /**
         * 附件。
         */
        public static final String ATT_FILE_GROUP_ID = "ATT_FILE_GROUP_ID";
        /**
         * 采购合同变更申请。
         */
        public static final String PO_ORDER_CHANGE_REQ_ID = "PO_ORDER_CHANGE_REQ_ID";
        /**
         * 附件7。
         */
        public static final String FILE_ID_SEVEN = "FILE_ID_SEVEN";
        /**
         * 附件6。
         */
        public static final String FILE_ID_SIX = "FILE_ID_SIX";
        /**
         * 长备注2。
         */
        public static final String TEXT_REMARK_TWO = "TEXT_REMARK_TWO";
        /**
         * 附件3。
         */
        public static final String FILE_ID_THREE = "FILE_ID_THREE";
        /**
         * 长备注3。
         */
        public static final String TEXT_REMARK_THREE = "TEXT_REMARK_THREE";
        /**
         * 附件2。
         */
        public static final String FILE_ID_TWO = "FILE_ID_TWO";
        /**
         * 长备注4。
         */
        public static final String TEXT_REMARK_FOUR = "TEXT_REMARK_FOUR";
        /**
         * 是否1。
         */
        public static final String YES_NO_ONE = "YES_NO_ONE";
        /**
         * 附件4。
         */
        public static final String FILE_ID_FOUR = "FILE_ID_FOUR";
        /**
         * 附件1。
         */
        public static final String FILE_ID_ONE = "FILE_ID_ONE";
        /**
         * 签订日期。
         */
        public static final String SIGN_DATE = "SIGN_DATE";
        /**
         * 日期5。
         */
        public static final String DATE_FIVE = "DATE_FIVE";
        /**
         * 附件5。
         */
        public static final String FILE_ID_FIVE = "FILE_ID_FIVE";
        /**
         * 项目编号。
         */
        public static final String PRJ_CODE = "PRJ_CODE";
        /**
         * 采购事项。
         */
        public static final String BUY_MATTER_ID = "BUY_MATTER_ID";
        /**
         * 合同金额（万）。
         */
        public static final String CONTRACT_PRICE = "CONTRACT_PRICE";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 审批人1。
         */
        public static final String APPROVAL_USER_ONE = "APPROVAL_USER_ONE";
        /**
         * 审批人2。
         */
        public static final String APPROVAL_USER_TWO = "APPROVAL_USER_TWO";
        /**
         * 审批日期2。
         */
        public static final String APPROVAL_DATE_TWO = "APPROVAL_DATE_TWO";
        /**
         * 长备注5。
         */
        public static final String TEXT_REMARK_FIVE = "TEXT_REMARK_FIVE";
        /**
         * 批复文件。
         */
        public static final String REPLY_FILE = "REPLY_FILE";
        /**
         * 专家意见文件。
         */
        public static final String EXPERT_FILE = "EXPERT_FILE";
        /**
         * 前期部意见。
         */
        public static final String EARLY_COMMENT = "EARLY_COMMENT";
        /**
         * 备注1。
         */
        public static final String REMARK_ONE = "REMARK_ONE";
        /**
         * 修编稿文件。
         */
        public static final String REVISION_FILE = "REVISION_FILE";
        /**
         * 设计部意见。
         */
        public static final String DESIGN_COMMENT = "DESIGN_COMMENT";
        /**
         * 审批日期1。
         */
        public static final String APPROVAL_DATE_ONE = "APPROVAL_DATE_ONE";
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
         * 评审报告文件。
         */
        public static final String REVIEW_DRAFT_FILE = "REVIEW_DRAFT_FILE";
        /**
         * 用户意见。
         */
        public static final String USER_COMMENT = "USER_COMMENT";
        /**
         * 是否已转PDF。
         */
        public static final String IS_PDF_CONVERTED = "IS_PDF_CONVERTED";
        /**
         * 概评金额。
         */
        public static final String ESTIMATED_AMOUNT = "ESTIMATED_AMOUNT";
        /**
         * 财评金额。
         */
        public static final String FINANCIAL_AMOUNT = "FINANCIAL_AMOUNT";
        /**
         * 已支付金额。
         */
        public static final String PAYED_AMT = "PAYED_AMT";
        /**
         * 累计支付比例。
         */
        public static final String CUMULATIVE_PAYED_PERCENT = "CUMULATIVE_PAYED_PERCENT";
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
         * 中标单位1。
         */
        public static final String WIN_BID_UNIT_ONE = "WIN_BID_UNIT_ONE";
        /**
         * 中标待签约额。
         */
        public static final String WINNING_BIDS_AMOUNT = "WINNING_BIDS_AMOUNT";
        /**
         * 是否导入的记录。
         */
        public static final String IS_IMPORT = "IS_IMPORT";
        /**
         * 联系人1。
         */
        public static final String CONTACTS_ONE = "CONTACTS_ONE";
        /**
         * 项目来源类型。
         */
        public static final String PROJECT_SOURCE_TYPE_ID = "PROJECT_SOURCE_TYPE_ID";
        /**
         * 项目名称。
         */
        public static final String PROJECT_NAME_WR = "PROJECT_NAME_WR";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 批复文号。
         */
        public static final String REPLY_NO = "REPLY_NO";
        /**
         * 项目概况。
         */
        public static final String PRJ_SITUATION = "PRJ_SITUATION";
        /**
         * 项目类型。
         */
        public static final String PROJECT_TYPE_ID = "PROJECT_TYPE_ID";
        /**
         * 投资来源。
         */
        public static final String INVESTMENT_SOURCE_ID = "INVESTMENT_SOURCE_ID";
        /**
         * 业主单位。
         */
        public static final String CUSTOMER_UNIT = "CUSTOMER_UNIT";
        /**
         * 总投资（万）。
         */
        public static final String PRJ_TOTAL_INVEST = "PRJ_TOTAL_INVEST";
        /**
         * 工程费用（万）。
         */
        public static final String PROJECT_AMT = "PROJECT_AMT";
        /**
         * 工程其他费用(万)。
         */
        public static final String PROJECT_OTHER_AMT = "PROJECT_OTHER_AMT";
        /**
         * 预备费(万)。
         */
        public static final String PREPARE_AMT = "PREPARE_AMT";
        /**
         * 建设期利息（万）。
         */
        public static final String CONSTRUCT_PERIOD_INTEREST = "CONSTRUCT_PERIOD_INTEREST";
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
    public PoOrderReq setId(String id) {
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
    public PoOrderReq setVer(Integer ver) {
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
    public PoOrderReq setTs(LocalDateTime ts) {
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
    public PoOrderReq setIsPreset(Boolean isPreset) {
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
    public PoOrderReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PoOrderReq setLastModiUserId(String lastModiUserId) {
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
    public PoOrderReq setLkWfInstId(String lkWfInstId) {
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
    public PoOrderReq setStatus(String status) {
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
    public PoOrderReq setCode(String code) {
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
    public PoOrderReq setCrtUserId(String crtUserId) {
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
    public PoOrderReq setCrtDeptId(String crtDeptId) {
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
    public PoOrderReq setName(String name) {
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
    public PoOrderReq setCrtDt(LocalDateTime crtDt) {
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
    public PoOrderReq setCustomerUnitOne(String customerUnitOne) {
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
     * 项目(多值)。
     */
    private String pmPrjIds;

    /**
     * 获取：项目(多值)。
     */
    public String getPmPrjIds() {
        return this.pmPrjIds;
    }

    /**
     * 设置：项目(多值)。
     */
    public PoOrderReq setPmPrjIds(String pmPrjIds) {
        if (this.pmPrjIds == null && pmPrjIds == null) {
            // 均为null，不做处理。
        } else if (this.pmPrjIds != null && pmPrjIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.pmPrjIds.compareTo(pmPrjIds) != 0) {
                this.pmPrjIds = pmPrjIds;
                if (!this.toUpdateCols.contains("PM_PRJ_IDS")) {
                    this.toUpdateCols.add("PM_PRJ_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pmPrjIds = pmPrjIds;
            if (!this.toUpdateCols.contains("PM_PRJ_IDS")) {
                this.toUpdateCols.add("PM_PRJ_IDS");
            }
        }
        return this;
    }

    /**
     * 合同名称。
     */
    private String contractName;

    /**
     * 获取：合同名称。
     */
    public String getContractName() {
        return this.contractName;
    }

    /**
     * 设置：合同名称。
     */
    public PoOrderReq setContractName(String contractName) {
        if (this.contractName == null && contractName == null) {
            // 均为null，不做处理。
        } else if (this.contractName != null && contractName != null) {
            // 均非null，判定不等，再做处理：
            if (this.contractName.compareTo(contractName) != 0) {
                this.contractName = contractName;
                if (!this.toUpdateCols.contains("CONTRACT_NAME")) {
                    this.toUpdateCols.add("CONTRACT_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contractName = contractName;
            if (!this.toUpdateCols.contains("CONTRACT_NAME")) {
                this.toUpdateCols.add("CONTRACT_NAME");
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
    public PoOrderReq setContractCode(String contractCode) {
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
    public PoOrderReq setContractCategoryOneId(String contractCategoryOneId) {
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
     * 金额1。
     */
    private BigDecimal amtOne;

    /**
     * 获取：金额1。
     */
    public BigDecimal getAmtOne() {
        return this.amtOne;
    }

    /**
     * 设置：金额1。
     */
    public PoOrderReq setAmtOne(BigDecimal amtOne) {
        if (this.amtOne == null && amtOne == null) {
            // 均为null，不做处理。
        } else if (this.amtOne != null && amtOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtOne.compareTo(amtOne) != 0) {
                this.amtOne = amtOne;
                if (!this.toUpdateCols.contains("AMT_ONE")) {
                    this.toUpdateCols.add("AMT_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtOne = amtOne;
            if (!this.toUpdateCols.contains("AMT_ONE")) {
                this.toUpdateCols.add("AMT_ONE");
            }
        }
        return this;
    }

    /**
     * 金额2。
     */
    private BigDecimal amtTwo;

    /**
     * 获取：金额2。
     */
    public BigDecimal getAmtTwo() {
        return this.amtTwo;
    }

    /**
     * 设置：金额2。
     */
    public PoOrderReq setAmtTwo(BigDecimal amtTwo) {
        if (this.amtTwo == null && amtTwo == null) {
            // 均为null，不做处理。
        } else if (this.amtTwo != null && amtTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtTwo.compareTo(amtTwo) != 0) {
                this.amtTwo = amtTwo;
                if (!this.toUpdateCols.contains("AMT_TWO")) {
                    this.toUpdateCols.add("AMT_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtTwo = amtTwo;
            if (!this.toUpdateCols.contains("AMT_TWO")) {
                this.toUpdateCols.add("AMT_TWO");
            }
        }
        return this;
    }

    /**
     * 金额3。
     */
    private BigDecimal amtThree;

    /**
     * 获取：金额3。
     */
    public BigDecimal getAmtThree() {
        return this.amtThree;
    }

    /**
     * 设置：金额3。
     */
    public PoOrderReq setAmtThree(BigDecimal amtThree) {
        if (this.amtThree == null && amtThree == null) {
            // 均为null，不做处理。
        } else if (this.amtThree != null && amtThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtThree.compareTo(amtThree) != 0) {
                this.amtThree = amtThree;
                if (!this.toUpdateCols.contains("AMT_THREE")) {
                    this.toUpdateCols.add("AMT_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtThree = amtThree;
            if (!this.toUpdateCols.contains("AMT_THREE")) {
                this.toUpdateCols.add("AMT_THREE");
            }
        }
        return this;
    }

    /**
     * 金额4。
     */
    private BigDecimal amtFour;

    /**
     * 获取：金额4。
     */
    public BigDecimal getAmtFour() {
        return this.amtFour;
    }

    /**
     * 设置：金额4。
     */
    public PoOrderReq setAmtFour(BigDecimal amtFour) {
        if (this.amtFour == null && amtFour == null) {
            // 均为null，不做处理。
        } else if (this.amtFour != null && amtFour != null) {
            // 均非null，判定不等，再做处理：
            if (this.amtFour.compareTo(amtFour) != 0) {
                this.amtFour = amtFour;
                if (!this.toUpdateCols.contains("AMT_FOUR")) {
                    this.toUpdateCols.add("AMT_FOUR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.amtFour = amtFour;
            if (!this.toUpdateCols.contains("AMT_FOUR")) {
                this.toUpdateCols.add("AMT_FOUR");
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
    public PoOrderReq setPlanTotalDays(Integer planTotalDays) {
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
    public PoOrderReq setIsReferGuaranteeId(String isReferGuaranteeId) {
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
    public PoOrderReq setGuaranteeLetterTypeIds(String guaranteeLetterTypeIds) {
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
    public PoOrderReq setYesNoThree(String yesNoThree) {
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
     * 用户。
     */
    private String adUserId;

    /**
     * 获取：用户。
     */
    public String getAdUserId() {
        return this.adUserId;
    }

    /**
     * 设置：用户。
     */
    public PoOrderReq setAdUserId(String adUserId) {
        if (this.adUserId == null && adUserId == null) {
            // 均为null，不做处理。
        } else if (this.adUserId != null && adUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adUserId.compareTo(adUserId) != 0) {
                this.adUserId = adUserId;
                if (!this.toUpdateCols.contains("AD_USER_ID")) {
                    this.toUpdateCols.add("AD_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adUserId = adUserId;
            if (!this.toUpdateCols.contains("AD_USER_ID")) {
                this.toUpdateCols.add("AD_USER_ID");
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
    public PoOrderReq setContactMobileOne(String contactMobileOne) {
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
     * 备注1。
     */
    private String remarkLongOne;

    /**
     * 获取：备注1。
     */
    public String getRemarkLongOne() {
        return this.remarkLongOne;
    }

    /**
     * 设置：备注1。
     */
    public PoOrderReq setRemarkLongOne(String remarkLongOne) {
        if (this.remarkLongOne == null && remarkLongOne == null) {
            // 均为null，不做处理。
        } else if (this.remarkLongOne != null && remarkLongOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.remarkLongOne.compareTo(remarkLongOne) != 0) {
                this.remarkLongOne = remarkLongOne;
                if (!this.toUpdateCols.contains("REMARK_LONG_ONE")) {
                    this.toUpdateCols.add("REMARK_LONG_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remarkLongOne = remarkLongOne;
            if (!this.toUpdateCols.contains("REMARK_LONG_ONE")) {
                this.toUpdateCols.add("REMARK_LONG_ONE");
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
    public PoOrderReq setAttFileGroupId(String attFileGroupId) {
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
     * 采购合同变更申请。
     */
    private String poOrderChangeReqId;

    /**
     * 获取：采购合同变更申请。
     */
    public String getPoOrderChangeReqId() {
        return this.poOrderChangeReqId;
    }

    /**
     * 设置：采购合同变更申请。
     */
    public PoOrderReq setPoOrderChangeReqId(String poOrderChangeReqId) {
        if (this.poOrderChangeReqId == null && poOrderChangeReqId == null) {
            // 均为null，不做处理。
        } else if (this.poOrderChangeReqId != null && poOrderChangeReqId != null) {
            // 均非null，判定不等，再做处理：
            if (this.poOrderChangeReqId.compareTo(poOrderChangeReqId) != 0) {
                this.poOrderChangeReqId = poOrderChangeReqId;
                if (!this.toUpdateCols.contains("PO_ORDER_CHANGE_REQ_ID")) {
                    this.toUpdateCols.add("PO_ORDER_CHANGE_REQ_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.poOrderChangeReqId = poOrderChangeReqId;
            if (!this.toUpdateCols.contains("PO_ORDER_CHANGE_REQ_ID")) {
                this.toUpdateCols.add("PO_ORDER_CHANGE_REQ_ID");
            }
        }
        return this;
    }

    /**
     * 附件7。
     */
    private String fileIdSeven;

    /**
     * 获取：附件7。
     */
    public String getFileIdSeven() {
        return this.fileIdSeven;
    }

    /**
     * 设置：附件7。
     */
    public PoOrderReq setFileIdSeven(String fileIdSeven) {
        if (this.fileIdSeven == null && fileIdSeven == null) {
            // 均为null，不做处理。
        } else if (this.fileIdSeven != null && fileIdSeven != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdSeven.compareTo(fileIdSeven) != 0) {
                this.fileIdSeven = fileIdSeven;
                if (!this.toUpdateCols.contains("FILE_ID_SEVEN")) {
                    this.toUpdateCols.add("FILE_ID_SEVEN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdSeven = fileIdSeven;
            if (!this.toUpdateCols.contains("FILE_ID_SEVEN")) {
                this.toUpdateCols.add("FILE_ID_SEVEN");
            }
        }
        return this;
    }

    /**
     * 附件6。
     */
    private String fileIdSix;

    /**
     * 获取：附件6。
     */
    public String getFileIdSix() {
        return this.fileIdSix;
    }

    /**
     * 设置：附件6。
     */
    public PoOrderReq setFileIdSix(String fileIdSix) {
        if (this.fileIdSix == null && fileIdSix == null) {
            // 均为null，不做处理。
        } else if (this.fileIdSix != null && fileIdSix != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdSix.compareTo(fileIdSix) != 0) {
                this.fileIdSix = fileIdSix;
                if (!this.toUpdateCols.contains("FILE_ID_SIX")) {
                    this.toUpdateCols.add("FILE_ID_SIX");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdSix = fileIdSix;
            if (!this.toUpdateCols.contains("FILE_ID_SIX")) {
                this.toUpdateCols.add("FILE_ID_SIX");
            }
        }
        return this;
    }

    /**
     * 长备注2。
     */
    private String textRemarkTwo;

    /**
     * 获取：长备注2。
     */
    public String getTextRemarkTwo() {
        return this.textRemarkTwo;
    }

    /**
     * 设置：长备注2。
     */
    public PoOrderReq setTextRemarkTwo(String textRemarkTwo) {
        if (this.textRemarkTwo == null && textRemarkTwo == null) {
            // 均为null，不做处理。
        } else if (this.textRemarkTwo != null && textRemarkTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.textRemarkTwo.compareTo(textRemarkTwo) != 0) {
                this.textRemarkTwo = textRemarkTwo;
                if (!this.toUpdateCols.contains("TEXT_REMARK_TWO")) {
                    this.toUpdateCols.add("TEXT_REMARK_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.textRemarkTwo = textRemarkTwo;
            if (!this.toUpdateCols.contains("TEXT_REMARK_TWO")) {
                this.toUpdateCols.add("TEXT_REMARK_TWO");
            }
        }
        return this;
    }

    /**
     * 附件3。
     */
    private String fileIdThree;

    /**
     * 获取：附件3。
     */
    public String getFileIdThree() {
        return this.fileIdThree;
    }

    /**
     * 设置：附件3。
     */
    public PoOrderReq setFileIdThree(String fileIdThree) {
        if (this.fileIdThree == null && fileIdThree == null) {
            // 均为null，不做处理。
        } else if (this.fileIdThree != null && fileIdThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdThree.compareTo(fileIdThree) != 0) {
                this.fileIdThree = fileIdThree;
                if (!this.toUpdateCols.contains("FILE_ID_THREE")) {
                    this.toUpdateCols.add("FILE_ID_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdThree = fileIdThree;
            if (!this.toUpdateCols.contains("FILE_ID_THREE")) {
                this.toUpdateCols.add("FILE_ID_THREE");
            }
        }
        return this;
    }

    /**
     * 长备注3。
     */
    private String textRemarkThree;

    /**
     * 获取：长备注3。
     */
    public String getTextRemarkThree() {
        return this.textRemarkThree;
    }

    /**
     * 设置：长备注3。
     */
    public PoOrderReq setTextRemarkThree(String textRemarkThree) {
        if (this.textRemarkThree == null && textRemarkThree == null) {
            // 均为null，不做处理。
        } else if (this.textRemarkThree != null && textRemarkThree != null) {
            // 均非null，判定不等，再做处理：
            if (this.textRemarkThree.compareTo(textRemarkThree) != 0) {
                this.textRemarkThree = textRemarkThree;
                if (!this.toUpdateCols.contains("TEXT_REMARK_THREE")) {
                    this.toUpdateCols.add("TEXT_REMARK_THREE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.textRemarkThree = textRemarkThree;
            if (!this.toUpdateCols.contains("TEXT_REMARK_THREE")) {
                this.toUpdateCols.add("TEXT_REMARK_THREE");
            }
        }
        return this;
    }

    /**
     * 附件2。
     */
    private String fileIdTwo;

    /**
     * 获取：附件2。
     */
    public String getFileIdTwo() {
        return this.fileIdTwo;
    }

    /**
     * 设置：附件2。
     */
    public PoOrderReq setFileIdTwo(String fileIdTwo) {
        if (this.fileIdTwo == null && fileIdTwo == null) {
            // 均为null，不做处理。
        } else if (this.fileIdTwo != null && fileIdTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdTwo.compareTo(fileIdTwo) != 0) {
                this.fileIdTwo = fileIdTwo;
                if (!this.toUpdateCols.contains("FILE_ID_TWO")) {
                    this.toUpdateCols.add("FILE_ID_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdTwo = fileIdTwo;
            if (!this.toUpdateCols.contains("FILE_ID_TWO")) {
                this.toUpdateCols.add("FILE_ID_TWO");
            }
        }
        return this;
    }

    /**
     * 长备注4。
     */
    private String textRemarkFour;

    /**
     * 获取：长备注4。
     */
    public String getTextRemarkFour() {
        return this.textRemarkFour;
    }

    /**
     * 设置：长备注4。
     */
    public PoOrderReq setTextRemarkFour(String textRemarkFour) {
        if (this.textRemarkFour == null && textRemarkFour == null) {
            // 均为null，不做处理。
        } else if (this.textRemarkFour != null && textRemarkFour != null) {
            // 均非null，判定不等，再做处理：
            if (this.textRemarkFour.compareTo(textRemarkFour) != 0) {
                this.textRemarkFour = textRemarkFour;
                if (!this.toUpdateCols.contains("TEXT_REMARK_FOUR")) {
                    this.toUpdateCols.add("TEXT_REMARK_FOUR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.textRemarkFour = textRemarkFour;
            if (!this.toUpdateCols.contains("TEXT_REMARK_FOUR")) {
                this.toUpdateCols.add("TEXT_REMARK_FOUR");
            }
        }
        return this;
    }

    /**
     * 是否1。
     */
    private String yesNoOne;

    /**
     * 获取：是否1。
     */
    public String getYesNoOne() {
        return this.yesNoOne;
    }

    /**
     * 设置：是否1。
     */
    public PoOrderReq setYesNoOne(String yesNoOne) {
        if (this.yesNoOne == null && yesNoOne == null) {
            // 均为null，不做处理。
        } else if (this.yesNoOne != null && yesNoOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.yesNoOne.compareTo(yesNoOne) != 0) {
                this.yesNoOne = yesNoOne;
                if (!this.toUpdateCols.contains("YES_NO_ONE")) {
                    this.toUpdateCols.add("YES_NO_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.yesNoOne = yesNoOne;
            if (!this.toUpdateCols.contains("YES_NO_ONE")) {
                this.toUpdateCols.add("YES_NO_ONE");
            }
        }
        return this;
    }

    /**
     * 附件4。
     */
    private String fileIdFour;

    /**
     * 获取：附件4。
     */
    public String getFileIdFour() {
        return this.fileIdFour;
    }

    /**
     * 设置：附件4。
     */
    public PoOrderReq setFileIdFour(String fileIdFour) {
        if (this.fileIdFour == null && fileIdFour == null) {
            // 均为null，不做处理。
        } else if (this.fileIdFour != null && fileIdFour != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdFour.compareTo(fileIdFour) != 0) {
                this.fileIdFour = fileIdFour;
                if (!this.toUpdateCols.contains("FILE_ID_FOUR")) {
                    this.toUpdateCols.add("FILE_ID_FOUR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdFour = fileIdFour;
            if (!this.toUpdateCols.contains("FILE_ID_FOUR")) {
                this.toUpdateCols.add("FILE_ID_FOUR");
            }
        }
        return this;
    }

    /**
     * 附件1。
     */
    private String fileIdOne;

    /**
     * 获取：附件1。
     */
    public String getFileIdOne() {
        return this.fileIdOne;
    }

    /**
     * 设置：附件1。
     */
    public PoOrderReq setFileIdOne(String fileIdOne) {
        if (this.fileIdOne == null && fileIdOne == null) {
            // 均为null，不做处理。
        } else if (this.fileIdOne != null && fileIdOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdOne.compareTo(fileIdOne) != 0) {
                this.fileIdOne = fileIdOne;
                if (!this.toUpdateCols.contains("FILE_ID_ONE")) {
                    this.toUpdateCols.add("FILE_ID_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdOne = fileIdOne;
            if (!this.toUpdateCols.contains("FILE_ID_ONE")) {
                this.toUpdateCols.add("FILE_ID_ONE");
            }
        }
        return this;
    }

    /**
     * 签订日期。
     */
    private LocalDate signDate;

    /**
     * 获取：签订日期。
     */
    public LocalDate getSignDate() {
        return this.signDate;
    }

    /**
     * 设置：签订日期。
     */
    public PoOrderReq setSignDate(LocalDate signDate) {
        if (this.signDate == null && signDate == null) {
            // 均为null，不做处理。
        } else if (this.signDate != null && signDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.signDate.compareTo(signDate) != 0) {
                this.signDate = signDate;
                if (!this.toUpdateCols.contains("SIGN_DATE")) {
                    this.toUpdateCols.add("SIGN_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.signDate = signDate;
            if (!this.toUpdateCols.contains("SIGN_DATE")) {
                this.toUpdateCols.add("SIGN_DATE");
            }
        }
        return this;
    }

    /**
     * 日期5。
     */
    private LocalDate dateFive;

    /**
     * 获取：日期5。
     */
    public LocalDate getDateFive() {
        return this.dateFive;
    }

    /**
     * 设置：日期5。
     */
    public PoOrderReq setDateFive(LocalDate dateFive) {
        if (this.dateFive == null && dateFive == null) {
            // 均为null，不做处理。
        } else if (this.dateFive != null && dateFive != null) {
            // 均非null，判定不等，再做处理：
            if (this.dateFive.compareTo(dateFive) != 0) {
                this.dateFive = dateFive;
                if (!this.toUpdateCols.contains("DATE_FIVE")) {
                    this.toUpdateCols.add("DATE_FIVE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dateFive = dateFive;
            if (!this.toUpdateCols.contains("DATE_FIVE")) {
                this.toUpdateCols.add("DATE_FIVE");
            }
        }
        return this;
    }

    /**
     * 附件5。
     */
    private String fileIdFive;

    /**
     * 获取：附件5。
     */
    public String getFileIdFive() {
        return this.fileIdFive;
    }

    /**
     * 设置：附件5。
     */
    public PoOrderReq setFileIdFive(String fileIdFive) {
        if (this.fileIdFive == null && fileIdFive == null) {
            // 均为null，不做处理。
        } else if (this.fileIdFive != null && fileIdFive != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdFive.compareTo(fileIdFive) != 0) {
                this.fileIdFive = fileIdFive;
                if (!this.toUpdateCols.contains("FILE_ID_FIVE")) {
                    this.toUpdateCols.add("FILE_ID_FIVE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdFive = fileIdFive;
            if (!this.toUpdateCols.contains("FILE_ID_FIVE")) {
                this.toUpdateCols.add("FILE_ID_FIVE");
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
    public PoOrderReq setPrjCode(String prjCode) {
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
     * 采购事项。
     */
    private String buyMatterId;

    /**
     * 获取：采购事项。
     */
    public String getBuyMatterId() {
        return this.buyMatterId;
    }

    /**
     * 设置：采购事项。
     */
    public PoOrderReq setBuyMatterId(String buyMatterId) {
        if (this.buyMatterId == null && buyMatterId == null) {
            // 均为null，不做处理。
        } else if (this.buyMatterId != null && buyMatterId != null) {
            // 均非null，判定不等，再做处理：
            if (this.buyMatterId.compareTo(buyMatterId) != 0) {
                this.buyMatterId = buyMatterId;
                if (!this.toUpdateCols.contains("BUY_MATTER_ID")) {
                    this.toUpdateCols.add("BUY_MATTER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.buyMatterId = buyMatterId;
            if (!this.toUpdateCols.contains("BUY_MATTER_ID")) {
                this.toUpdateCols.add("BUY_MATTER_ID");
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
    public PoOrderReq setContractPrice(BigDecimal contractPrice) {
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
    public PoOrderReq setRemark(String remark) {
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
    public PoOrderReq setApprovalUserOne(String approvalUserOne) {
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
    public PoOrderReq setApprovalUserTwo(String approvalUserTwo) {
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
    public PoOrderReq setApprovalDateTwo(LocalDate approvalDateTwo) {
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
     * 长备注5。
     */
    private String textRemarkFive;

    /**
     * 获取：长备注5。
     */
    public String getTextRemarkFive() {
        return this.textRemarkFive;
    }

    /**
     * 设置：长备注5。
     */
    public PoOrderReq setTextRemarkFive(String textRemarkFive) {
        if (this.textRemarkFive == null && textRemarkFive == null) {
            // 均为null，不做处理。
        } else if (this.textRemarkFive != null && textRemarkFive != null) {
            // 均非null，判定不等，再做处理：
            if (this.textRemarkFive.compareTo(textRemarkFive) != 0) {
                this.textRemarkFive = textRemarkFive;
                if (!this.toUpdateCols.contains("TEXT_REMARK_FIVE")) {
                    this.toUpdateCols.add("TEXT_REMARK_FIVE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.textRemarkFive = textRemarkFive;
            if (!this.toUpdateCols.contains("TEXT_REMARK_FIVE")) {
                this.toUpdateCols.add("TEXT_REMARK_FIVE");
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
    public PoOrderReq setReplyFile(String replyFile) {
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
     * 专家意见文件。
     */
    private String expertFile;

    /**
     * 获取：专家意见文件。
     */
    public String getExpertFile() {
        return this.expertFile;
    }

    /**
     * 设置：专家意见文件。
     */
    public PoOrderReq setExpertFile(String expertFile) {
        if (this.expertFile == null && expertFile == null) {
            // 均为null，不做处理。
        } else if (this.expertFile != null && expertFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.expertFile.compareTo(expertFile) != 0) {
                this.expertFile = expertFile;
                if (!this.toUpdateCols.contains("EXPERT_FILE")) {
                    this.toUpdateCols.add("EXPERT_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.expertFile = expertFile;
            if (!this.toUpdateCols.contains("EXPERT_FILE")) {
                this.toUpdateCols.add("EXPERT_FILE");
            }
        }
        return this;
    }

    /**
     * 前期部意见。
     */
    private String earlyComment;

    /**
     * 获取：前期部意见。
     */
    public String getEarlyComment() {
        return this.earlyComment;
    }

    /**
     * 设置：前期部意见。
     */
    public PoOrderReq setEarlyComment(String earlyComment) {
        if (this.earlyComment == null && earlyComment == null) {
            // 均为null，不做处理。
        } else if (this.earlyComment != null && earlyComment != null) {
            // 均非null，判定不等，再做处理：
            if (this.earlyComment.compareTo(earlyComment) != 0) {
                this.earlyComment = earlyComment;
                if (!this.toUpdateCols.contains("EARLY_COMMENT")) {
                    this.toUpdateCols.add("EARLY_COMMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.earlyComment = earlyComment;
            if (!this.toUpdateCols.contains("EARLY_COMMENT")) {
                this.toUpdateCols.add("EARLY_COMMENT");
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
    public PoOrderReq setRemarkOne(String remarkOne) {
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
    public PoOrderReq setRevisionFile(String revisionFile) {
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
     * 设计部意见。
     */
    private String designComment;

    /**
     * 获取：设计部意见。
     */
    public String getDesignComment() {
        return this.designComment;
    }

    /**
     * 设置：设计部意见。
     */
    public PoOrderReq setDesignComment(String designComment) {
        if (this.designComment == null && designComment == null) {
            // 均为null，不做处理。
        } else if (this.designComment != null && designComment != null) {
            // 均非null，判定不等，再做处理：
            if (this.designComment.compareTo(designComment) != 0) {
                this.designComment = designComment;
                if (!this.toUpdateCols.contains("DESIGN_COMMENT")) {
                    this.toUpdateCols.add("DESIGN_COMMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.designComment = designComment;
            if (!this.toUpdateCols.contains("DESIGN_COMMENT")) {
                this.toUpdateCols.add("DESIGN_COMMENT");
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
    public PoOrderReq setApprovalDateOne(LocalDate approvalDateOne) {
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
    public PoOrderReq setApprovalUserFour(String approvalUserFour) {
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
    public PoOrderReq setApprovalDateFour(LocalDate approvalDateFour) {
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
    public PoOrderReq setApproveFileIdOne(String approveFileIdOne) {
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
    public PoOrderReq setApprovalCommentOne(String approvalCommentOne) {
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
    public PoOrderReq setApprovalUserFive(String approvalUserFive) {
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
    public PoOrderReq setApprovalDateFive(LocalDate approvalDateFive) {
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
    public PoOrderReq setReviewDraftFile(String reviewDraftFile) {
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
     * 用户意见。
     */
    private String userComment;

    /**
     * 获取：用户意见。
     */
    public String getUserComment() {
        return this.userComment;
    }

    /**
     * 设置：用户意见。
     */
    public PoOrderReq setUserComment(String userComment) {
        if (this.userComment == null && userComment == null) {
            // 均为null，不做处理。
        } else if (this.userComment != null && userComment != null) {
            // 均非null，判定不等，再做处理：
            if (this.userComment.compareTo(userComment) != 0) {
                this.userComment = userComment;
                if (!this.toUpdateCols.contains("USER_COMMENT")) {
                    this.toUpdateCols.add("USER_COMMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.userComment = userComment;
            if (!this.toUpdateCols.contains("USER_COMMENT")) {
                this.toUpdateCols.add("USER_COMMENT");
            }
        }
        return this;
    }

    /**
     * 是否已转PDF。
     */
    private Boolean isPdfConverted;

    /**
     * 获取：是否已转PDF。
     */
    public Boolean getIsPdfConverted() {
        return this.isPdfConverted;
    }

    /**
     * 设置：是否已转PDF。
     */
    public PoOrderReq setIsPdfConverted(Boolean isPdfConverted) {
        if (this.isPdfConverted == null && isPdfConverted == null) {
            // 均为null，不做处理。
        } else if (this.isPdfConverted != null && isPdfConverted != null) {
            // 均非null，判定不等，再做处理：
            if (this.isPdfConverted.compareTo(isPdfConverted) != 0) {
                this.isPdfConverted = isPdfConverted;
                if (!this.toUpdateCols.contains("IS_PDF_CONVERTED")) {
                    this.toUpdateCols.add("IS_PDF_CONVERTED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isPdfConverted = isPdfConverted;
            if (!this.toUpdateCols.contains("IS_PDF_CONVERTED")) {
                this.toUpdateCols.add("IS_PDF_CONVERTED");
            }
        }
        return this;
    }

    /**
     * 概评金额。
     */
    private BigDecimal estimatedAmount;

    /**
     * 获取：概评金额。
     */
    public BigDecimal getEstimatedAmount() {
        return this.estimatedAmount;
    }

    /**
     * 设置：概评金额。
     */
    public PoOrderReq setEstimatedAmount(BigDecimal estimatedAmount) {
        if (this.estimatedAmount == null && estimatedAmount == null) {
            // 均为null，不做处理。
        } else if (this.estimatedAmount != null && estimatedAmount != null) {
            // 均非null，判定不等，再做处理：
            if (this.estimatedAmount.compareTo(estimatedAmount) != 0) {
                this.estimatedAmount = estimatedAmount;
                if (!this.toUpdateCols.contains("ESTIMATED_AMOUNT")) {
                    this.toUpdateCols.add("ESTIMATED_AMOUNT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.estimatedAmount = estimatedAmount;
            if (!this.toUpdateCols.contains("ESTIMATED_AMOUNT")) {
                this.toUpdateCols.add("ESTIMATED_AMOUNT");
            }
        }
        return this;
    }

    /**
     * 财评金额。
     */
    private BigDecimal financialAmount;

    /**
     * 获取：财评金额。
     */
    public BigDecimal getFinancialAmount() {
        return this.financialAmount;
    }

    /**
     * 设置：财评金额。
     */
    public PoOrderReq setFinancialAmount(BigDecimal financialAmount) {
        if (this.financialAmount == null && financialAmount == null) {
            // 均为null，不做处理。
        } else if (this.financialAmount != null && financialAmount != null) {
            // 均非null，判定不等，再做处理：
            if (this.financialAmount.compareTo(financialAmount) != 0) {
                this.financialAmount = financialAmount;
                if (!this.toUpdateCols.contains("FINANCIAL_AMOUNT")) {
                    this.toUpdateCols.add("FINANCIAL_AMOUNT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financialAmount = financialAmount;
            if (!this.toUpdateCols.contains("FINANCIAL_AMOUNT")) {
                this.toUpdateCols.add("FINANCIAL_AMOUNT");
            }
        }
        return this;
    }

    /**
     * 已支付金额。
     */
    private BigDecimal payedAmt;

    /**
     * 获取：已支付金额。
     */
    public BigDecimal getPayedAmt() {
        return this.payedAmt;
    }

    /**
     * 设置：已支付金额。
     */
    public PoOrderReq setPayedAmt(BigDecimal payedAmt) {
        if (this.payedAmt == null && payedAmt == null) {
            // 均为null，不做处理。
        } else if (this.payedAmt != null && payedAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.payedAmt.compareTo(payedAmt) != 0) {
                this.payedAmt = payedAmt;
                if (!this.toUpdateCols.contains("PAYED_AMT")) {
                    this.toUpdateCols.add("PAYED_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.payedAmt = payedAmt;
            if (!this.toUpdateCols.contains("PAYED_AMT")) {
                this.toUpdateCols.add("PAYED_AMT");
            }
        }
        return this;
    }

    /**
     * 累计支付比例。
     */
    private BigDecimal cumulativePayedPercent;

    /**
     * 获取：累计支付比例。
     */
    public BigDecimal getCumulativePayedPercent() {
        return this.cumulativePayedPercent;
    }

    /**
     * 设置：累计支付比例。
     */
    public PoOrderReq setCumulativePayedPercent(BigDecimal cumulativePayedPercent) {
        if (this.cumulativePayedPercent == null && cumulativePayedPercent == null) {
            // 均为null，不做处理。
        } else if (this.cumulativePayedPercent != null && cumulativePayedPercent != null) {
            // 均非null，判定不等，再做处理：
            if (this.cumulativePayedPercent.compareTo(cumulativePayedPercent) != 0) {
                this.cumulativePayedPercent = cumulativePayedPercent;
                if (!this.toUpdateCols.contains("CUMULATIVE_PAYED_PERCENT")) {
                    this.toUpdateCols.add("CUMULATIVE_PAYED_PERCENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cumulativePayedPercent = cumulativePayedPercent;
            if (!this.toUpdateCols.contains("CUMULATIVE_PAYED_PERCENT")) {
                this.toUpdateCols.add("CUMULATIVE_PAYED_PERCENT");
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
    public PoOrderReq setPmBidKeepFileReqId(String pmBidKeepFileReqId) {
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
    public PoOrderReq setBuyTypeId(String buyTypeId) {
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
    public PoOrderReq setBidCtlPriceLaunch(BigDecimal bidCtlPriceLaunch) {
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
    public PoOrderReq setWinBidUnitOne(String winBidUnitOne) {
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
    public PoOrderReq setWinningBidsAmount(BigDecimal winningBidsAmount) {
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
    public PoOrderReq setIsImport(Boolean isImport) {
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
    public PoOrderReq setContactsOne(String contactsOne) {
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
    public PoOrderReq setProjectSourceTypeId(String projectSourceTypeId) {
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
     * 项目名称。
     */
    private String projectNameWr;

    /**
     * 获取：项目名称。
     */
    public String getProjectNameWr() {
        return this.projectNameWr;
    }

    /**
     * 设置：项目名称。
     */
    public PoOrderReq setProjectNameWr(String projectNameWr) {
        if (this.projectNameWr == null && projectNameWr == null) {
            // 均为null，不做处理。
        } else if (this.projectNameWr != null && projectNameWr != null) {
            // 均非null，判定不等，再做处理：
            if (this.projectNameWr.compareTo(projectNameWr) != 0) {
                this.projectNameWr = projectNameWr;
                if (!this.toUpdateCols.contains("PROJECT_NAME_WR")) {
                    this.toUpdateCols.add("PROJECT_NAME_WR");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.projectNameWr = projectNameWr;
            if (!this.toUpdateCols.contains("PROJECT_NAME_WR")) {
                this.toUpdateCols.add("PROJECT_NAME_WR");
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
    public PoOrderReq setPmPrjId(String pmPrjId) {
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
    public PoOrderReq setReplyNo(String replyNo) {
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
    public PoOrderReq setPrjSituation(String prjSituation) {
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
    public PoOrderReq setProjectTypeId(String projectTypeId) {
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
    public PoOrderReq setInvestmentSourceId(String investmentSourceId) {
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
    public PoOrderReq setCustomerUnit(String customerUnit) {
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
    public PoOrderReq setPrjTotalInvest(BigDecimal prjTotalInvest) {
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
    public PoOrderReq setProjectAmt(BigDecimal projectAmt) {
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
    public PoOrderReq setProjectOtherAmt(BigDecimal projectOtherAmt) {
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
    public PoOrderReq setPrepareAmt(BigDecimal prepareAmt) {
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
    public PoOrderReq setConstructPeriodInterest(BigDecimal constructPeriodInterest) {
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
    public static PoOrderReq newData() {
        PoOrderReq obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PoOrderReq insertData() {
        PoOrderReq obj = modelHelper.insertData();
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
    public static PoOrderReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PoOrderReq obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static PoOrderReq selectById(String id) {
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
    public static List<PoOrderReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PoOrderReq> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PoOrderReq> selectByIds(List<String> ids) {
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
    public static List<PoOrderReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PoOrderReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PoOrderReq> selectByWhere(Where where) {
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
    public static PoOrderReq selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PoOrderReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用PoOrderReq.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmptyList(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static PoOrderReq selectOneByWhere(Where where) {
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
    public static void copyCols(PoOrderReq fromModel, PoOrderReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(PoOrderReq fromModel, PoOrderReq toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}
