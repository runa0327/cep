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
 * 补充协议。
 */
public class PoOrderSupplementReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PoOrderSupplementReq> modelHelper = new ModelHelper<>("PO_ORDER_SUPPLEMENT_REQ", new PoOrderSupplementReq());

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

    public static final String ENT_CODE = "PO_ORDER_SUPPLEMENT_REQ";
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
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 业主单位1。
         */
        public static final String CUSTOMER_UNIT_ONE = "CUSTOMER_UNIT_ONE";
        /**
         * 项目(多值)。
         */
        public static final String PM_PRJ_IDS = "PM_PRJ_IDS";
        /**
         * 关联合同。
         */
        public static final String RELATION_CONTRACT_ID = "RELATION_CONTRACT_ID";
        /**
         * 合同编号。
         */
        public static final String CONTRACT_CODE = "CONTRACT_CODE";
        /**
         * 合同类型1。
         */
        public static final String CONTRACT_CATEGORY_ONE_ID = "CONTRACT_CATEGORY_ONE_ID";
        /**
         * 合同名称。
         */
        public static final String CONTRACT_NAME = "CONTRACT_NAME";
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
         * 附件9。
         */
        public static final String FILE_ID_NINTH = "FILE_ID_NINTH";
        /**
         * 附件7。
         */
        public static final String FILE_ID_SEVEN = "FILE_ID_SEVEN";
        /**
         * 审批意见1。
         */
        public static final String APPROVAL_COMMENT_ONE = "APPROVAL_COMMENT_ONE";
        /**
         * 附件6。
         */
        public static final String FILE_ID_SIX = "FILE_ID_SIX";
        /**
         * 审批意见2。
         */
        public static final String APPROVAL_COMMENT_TWO = "APPROVAL_COMMENT_TWO";
        /**
         * 附件2。
         */
        public static final String FILE_ID_TWO = "FILE_ID_TWO";
        /**
         * 审批意见3。
         */
        public static final String APPROVAL_COMMENT_THREE = "APPROVAL_COMMENT_THREE";
        /**
         * 附件3。
         */
        public static final String FILE_ID_THREE = "FILE_ID_THREE";
        /**
         * 是否1。
         */
        public static final String YES_NO_ONE = "YES_NO_ONE";
        /**
         * 备注1。
         */
        public static final String REMARK_ONE = "REMARK_ONE";
        /**
         * 附件4。
         */
        public static final String FILE_ID_FOUR = "FILE_ID_FOUR";
        /**
         * 附件10。
         */
        public static final String FILE_ID_TENTH = "FILE_ID_TENTH";
        /**
         * 采购合同变更申请。
         */
        public static final String PO_ORDER_CHANGE_REQ_ID = "PO_ORDER_CHANGE_REQ_ID";
        /**
         * 备注2。
         */
        public static final String REMARK_TWO = "REMARK_TWO";
        /**
         * 附件8。
         */
        public static final String FILE_ID_EIGHTH = "FILE_ID_EIGHTH";
        /**
         * 附件。
         */
        public static final String ATT_FILE_GROUP_ID = "ATT_FILE_GROUP_ID";
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
         * 合同。
         */
        public static final String CONTRACT_ID = "CONTRACT_ID";
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
         * 采购事项。
         */
        public static final String BUY_MATTER_ID = "BUY_MATTER_ID";
        /**
         * 中标单位1。
         */
        public static final String WIN_BID_UNIT_ONE = "WIN_BID_UNIT_ONE";
        /**
         * 中标待签约额。
         */
        public static final String WINNING_BIDS_AMOUNT = "WINNING_BIDS_AMOUNT";
        /**
         * 合同金额（万）。
         */
        public static final String CONTRACT_PRICE = "CONTRACT_PRICE";
        /**
         * 联系人1。
         */
        public static final String CONTACTS_ONE = "CONTACTS_ONE";
        /**
         * 是否导入的记录。
         */
        public static final String IS_IMPORT = "IS_IMPORT";
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
    public PoOrderSupplementReq setId(String id) {
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
    public PoOrderSupplementReq setVer(Integer ver) {
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
    public PoOrderSupplementReq setTs(LocalDateTime ts) {
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
    public PoOrderSupplementReq setIsPreset(Boolean isPreset) {
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
    public PoOrderSupplementReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PoOrderSupplementReq setLastModiUserId(String lastModiUserId) {
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
    public PoOrderSupplementReq setLkWfInstId(String lkWfInstId) {
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
    public PoOrderSupplementReq setStatus(String status) {
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
    public PoOrderSupplementReq setCode(String code) {
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
    public PoOrderSupplementReq setCrtUserId(String crtUserId) {
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
    public PoOrderSupplementReq setCrtDeptId(String crtDeptId) {
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
    public PoOrderSupplementReq setName(String name) {
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
    public PoOrderSupplementReq setCrtDt(LocalDateTime crtDt) {
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
    public PoOrderSupplementReq setRemark(String remark) {
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
    public PoOrderSupplementReq setCustomerUnitOne(String customerUnitOne) {
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
    public PoOrderSupplementReq setPmPrjIds(String pmPrjIds) {
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
     * 关联合同。
     */
    private String relationContractId;

    /**
     * 获取：关联合同。
     */
    public String getRelationContractId() {
        return this.relationContractId;
    }

    /**
     * 设置：关联合同。
     */
    public PoOrderSupplementReq setRelationContractId(String relationContractId) {
        if (this.relationContractId == null && relationContractId == null) {
            // 均为null，不做处理。
        } else if (this.relationContractId != null && relationContractId != null) {
            // 均非null，判定不等，再做处理：
            if (this.relationContractId.compareTo(relationContractId) != 0) {
                this.relationContractId = relationContractId;
                if (!this.toUpdateCols.contains("RELATION_CONTRACT_ID")) {
                    this.toUpdateCols.add("RELATION_CONTRACT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.relationContractId = relationContractId;
            if (!this.toUpdateCols.contains("RELATION_CONTRACT_ID")) {
                this.toUpdateCols.add("RELATION_CONTRACT_ID");
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
    public PoOrderSupplementReq setContractCode(String contractCode) {
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
    public PoOrderSupplementReq setContractCategoryOneId(String contractCategoryOneId) {
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
    public PoOrderSupplementReq setContractName(String contractName) {
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
    public PoOrderSupplementReq setAmtOne(BigDecimal amtOne) {
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
    public PoOrderSupplementReq setAmtTwo(BigDecimal amtTwo) {
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
    public PoOrderSupplementReq setAmtThree(BigDecimal amtThree) {
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
    public PoOrderSupplementReq setAmtFour(BigDecimal amtFour) {
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
    public PoOrderSupplementReq setPlanTotalDays(Integer planTotalDays) {
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
    public PoOrderSupplementReq setIsReferGuaranteeId(String isReferGuaranteeId) {
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
    public PoOrderSupplementReq setGuaranteeLetterTypeIds(String guaranteeLetterTypeIds) {
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
    public PoOrderSupplementReq setYesNoThree(String yesNoThree) {
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
    public PoOrderSupplementReq setAdUserId(String adUserId) {
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
    public PoOrderSupplementReq setContactMobileOne(String contactMobileOne) {
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
     * 附件9。
     */
    private String fileIdNinth;

    /**
     * 获取：附件9。
     */
    public String getFileIdNinth() {
        return this.fileIdNinth;
    }

    /**
     * 设置：附件9。
     */
    public PoOrderSupplementReq setFileIdNinth(String fileIdNinth) {
        if (this.fileIdNinth == null && fileIdNinth == null) {
            // 均为null，不做处理。
        } else if (this.fileIdNinth != null && fileIdNinth != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdNinth.compareTo(fileIdNinth) != 0) {
                this.fileIdNinth = fileIdNinth;
                if (!this.toUpdateCols.contains("FILE_ID_NINTH")) {
                    this.toUpdateCols.add("FILE_ID_NINTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdNinth = fileIdNinth;
            if (!this.toUpdateCols.contains("FILE_ID_NINTH")) {
                this.toUpdateCols.add("FILE_ID_NINTH");
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
    public PoOrderSupplementReq setFileIdSeven(String fileIdSeven) {
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
    public PoOrderSupplementReq setApprovalCommentOne(String approvalCommentOne) {
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
    public PoOrderSupplementReq setFileIdSix(String fileIdSix) {
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
    public PoOrderSupplementReq setApprovalCommentTwo(String approvalCommentTwo) {
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
    public PoOrderSupplementReq setFileIdTwo(String fileIdTwo) {
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
    public PoOrderSupplementReq setApprovalCommentThree(String approvalCommentThree) {
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
    public PoOrderSupplementReq setFileIdThree(String fileIdThree) {
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
    public PoOrderSupplementReq setYesNoOne(String yesNoOne) {
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
    public PoOrderSupplementReq setRemarkOne(String remarkOne) {
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
    public PoOrderSupplementReq setFileIdFour(String fileIdFour) {
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
     * 附件10。
     */
    private String fileIdTenth;

    /**
     * 获取：附件10。
     */
    public String getFileIdTenth() {
        return this.fileIdTenth;
    }

    /**
     * 设置：附件10。
     */
    public PoOrderSupplementReq setFileIdTenth(String fileIdTenth) {
        if (this.fileIdTenth == null && fileIdTenth == null) {
            // 均为null，不做处理。
        } else if (this.fileIdTenth != null && fileIdTenth != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdTenth.compareTo(fileIdTenth) != 0) {
                this.fileIdTenth = fileIdTenth;
                if (!this.toUpdateCols.contains("FILE_ID_TENTH")) {
                    this.toUpdateCols.add("FILE_ID_TENTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdTenth = fileIdTenth;
            if (!this.toUpdateCols.contains("FILE_ID_TENTH")) {
                this.toUpdateCols.add("FILE_ID_TENTH");
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
    public PoOrderSupplementReq setPoOrderChangeReqId(String poOrderChangeReqId) {
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
    public PoOrderSupplementReq setRemarkTwo(String remarkTwo) {
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
     * 附件8。
     */
    private String fileIdEighth;

    /**
     * 获取：附件8。
     */
    public String getFileIdEighth() {
        return this.fileIdEighth;
    }

    /**
     * 设置：附件8。
     */
    public PoOrderSupplementReq setFileIdEighth(String fileIdEighth) {
        if (this.fileIdEighth == null && fileIdEighth == null) {
            // 均为null，不做处理。
        } else if (this.fileIdEighth != null && fileIdEighth != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileIdEighth.compareTo(fileIdEighth) != 0) {
                this.fileIdEighth = fileIdEighth;
                if (!this.toUpdateCols.contains("FILE_ID_EIGHTH")) {
                    this.toUpdateCols.add("FILE_ID_EIGHTH");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileIdEighth = fileIdEighth;
            if (!this.toUpdateCols.contains("FILE_ID_EIGHTH")) {
                this.toUpdateCols.add("FILE_ID_EIGHTH");
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
    public PoOrderSupplementReq setAttFileGroupId(String attFileGroupId) {
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
    public PoOrderSupplementReq setSignDate(LocalDate signDate) {
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
    public PoOrderSupplementReq setDateFive(LocalDate dateFive) {
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
    public PoOrderSupplementReq setFileIdFive(String fileIdFive) {
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
    public PoOrderSupplementReq setContractId(String contractId) {
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
    public PoOrderSupplementReq setPmBidKeepFileReqId(String pmBidKeepFileReqId) {
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
    public PoOrderSupplementReq setBuyTypeId(String buyTypeId) {
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
    public PoOrderSupplementReq setBidCtlPriceLaunch(BigDecimal bidCtlPriceLaunch) {
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
    public PoOrderSupplementReq setBuyMatterId(String buyMatterId) {
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
    public PoOrderSupplementReq setWinBidUnitOne(String winBidUnitOne) {
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
    public PoOrderSupplementReq setWinningBidsAmount(BigDecimal winningBidsAmount) {
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
    public PoOrderSupplementReq setContractPrice(BigDecimal contractPrice) {
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
    public PoOrderSupplementReq setContactsOne(String contactsOne) {
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
    public PoOrderSupplementReq setIsImport(Boolean isImport) {
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
    public PoOrderSupplementReq setProjectSourceTypeId(String projectSourceTypeId) {
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
    public PoOrderSupplementReq setProjectNameWr(String projectNameWr) {
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
    public PoOrderSupplementReq setPmPrjId(String pmPrjId) {
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
    public PoOrderSupplementReq setReplyNo(String replyNo) {
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
    public PoOrderSupplementReq setPrjSituation(String prjSituation) {
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
    public PoOrderSupplementReq setInvestmentSourceId(String investmentSourceId) {
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
    public PoOrderSupplementReq setCustomerUnit(String customerUnit) {
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
    public PoOrderSupplementReq setProjectTypeId(String projectTypeId) {
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
    public PoOrderSupplementReq setPrjTotalInvest(BigDecimal prjTotalInvest) {
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
    public PoOrderSupplementReq setProjectAmt(BigDecimal projectAmt) {
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
    public PoOrderSupplementReq setProjectOtherAmt(BigDecimal projectOtherAmt) {
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
    public PoOrderSupplementReq setPrepareAmt(BigDecimal prepareAmt) {
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
    public PoOrderSupplementReq setConstructPeriodInterest(BigDecimal constructPeriodInterest) {
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
    public static PoOrderSupplementReq newData() {
        PoOrderSupplementReq obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PoOrderSupplementReq insertData() {
        PoOrderSupplementReq obj = modelHelper.insertData();
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
    public static PoOrderSupplementReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PoOrderSupplementReq obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static PoOrderSupplementReq selectById(String id) {
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
    public static List<PoOrderSupplementReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PoOrderSupplementReq> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PoOrderSupplementReq> selectByIds(List<String> ids) {
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
    public static List<PoOrderSupplementReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PoOrderSupplementReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<PoOrderSupplementReq> selectByWhere(Where where) {
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
    public static PoOrderSupplementReq selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PoOrderSupplementReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用PoOrderSupplementReq.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmptyList(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static PoOrderSupplementReq selectOneByWhere(Where where) {
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
    public static void copyCols(PoOrderSupplementReq fromModel, PoOrderSupplementReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(PoOrderSupplementReq fromModel, PoOrderSupplementReq toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}
