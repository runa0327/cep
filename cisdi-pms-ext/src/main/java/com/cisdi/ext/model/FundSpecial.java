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
 * 专项资金支付明细。
 */
public class FundSpecial {

    /**
     * 模型助手。
     */
    private static final ModelHelper<FundSpecial> modelHelper = new ModelHelper<>("FUND_SPECIAL", new FundSpecial());

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

    public static final String ENT_CODE = "FUND_SPECIAL";
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
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 最后修改日期时间。
         */
        public static final String LAST_MODI_DT = "LAST_MODI_DT";
        /**
         * 最后修改用户。
         */
        public static final String LAST_MODI_USER_ID = "LAST_MODI_USER_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 锁定流程实例。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
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
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 资金落实内来源（视图）。
         */
        public static final String FUND_IMPLEMENTATION_V_ID = "FUND_IMPLEMENTATION_V_ID";
        /**
         * 批复金额。
         */
        public static final String APPROVED_AMOUNT = "APPROVED_AMOUNT";
        /**
         * 累计到位金额。
         */
        public static final String CUM_REACH_AMT = "CUM_REACH_AMT";
        /**
         * 累计支付金额。
         */
        public static final String CUM_PAY_AMT = "CUM_PAY_AMT";
        /**
         * 未到位资金。
         */
        public static final String NOT_REACH_AMT = "NOT_REACH_AMT";
        /**
         * 支付明细码。
         */
        public static final String FUND_PAY_CODE = "FUND_PAY_CODE";
        /**
         * 业主单位。
         */
        public static final String CUSTOMER_UNIT = "CUSTOMER_UNIT";
        /**
         * 项目管理模式。
         */
        public static final String PRJ_MANAGE_MODE_ID = "PRJ_MANAGE_MODE_ID";
        /**
         * 累计到位建设资金。
         */
        public static final String CUM_BUILD_REACH_AMT = "CUM_BUILD_REACH_AMT";
        /**
         * 累计到位征拆资金。
         */
        public static final String CUM_ACQ_REACH_AMT = "CUM_ACQ_REACH_AMT";
        /**
         * 剩余到位资金。
         */
        public static final String SUR_REACH_AMT = "SUR_REACH_AMT";
        /**
         * 到位资金类别。
         */
        public static final String FUND_REACH_CATEGORY = "FUND_REACH_CATEGORY";
        /**
         * 期数。
         */
        public static final String NPER = "NPER";
        /**
         * 费用名称。
         */
        public static final String COST_NAME = "COST_NAME";
        /**
         * 付款单位。
         */
        public static final String PAY_UNIT = "PAY_UNIT";
        /**
         * 收款银行。
         */
        public static final String RECEIPT_BANK = "RECEIPT_BANK";
        /**
         * 收款账户。
         */
        public static final String RECEIPT_ACCOUNT = "RECEIPT_ACCOUNT";
        /**
         * 支付日期。
         */
        public static final String PAY_DATE = "PAY_DATE";
        /**
         * 本期审定应付金额。
         */
        public static final String PAYABLE_AMT = "PAYABLE_AMT";
        /**
         * 本期已付金额。
         */
        public static final String PAID_AMT = "PAID_AMT";
        /**
         * 本期未付金额。
         */
        public static final String UNPAD_AMT = "UNPAD_AMT";
        /**
         * 收款单位。
         */
        public static final String PAYEE = "PAYEE";
        /**
         * 保函情况。
         */
        public static final String GUARANTEE_STATES = "GUARANTEE_STATES";
        /**
         * 审核状态。
         */
        public static final String APPROVAL_STATUS = "APPROVAL_STATUS";
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
    public FundSpecial setId(String id) {
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
    public FundSpecial setVer(Integer ver) {
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
    public FundSpecial setTs(LocalDateTime ts) {
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
    public FundSpecial setIsPreset(Boolean isPreset) {
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
    public FundSpecial setCrtDt(LocalDateTime crtDt) {
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
    public FundSpecial setCrtUserId(String crtUserId) {
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
    public FundSpecial setLastModiDt(LocalDateTime lastModiDt) {
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
    public FundSpecial setLastModiUserId(String lastModiUserId) {
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
    public FundSpecial setStatus(String status) {
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
    public FundSpecial setLkWfInstId(String lkWfInstId) {
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
    public FundSpecial setCode(String code) {
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
    public FundSpecial setName(String name) {
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
    public FundSpecial setRemark(String remark) {
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
    public FundSpecial setPmPrjId(String pmPrjId) {
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
     * 资金落实内来源（视图）。
     */
    private String fundImplementationVId;

    /**
     * 获取：资金落实内来源（视图）。
     */
    public String getFundImplementationVId() {
        return this.fundImplementationVId;
    }

    /**
     * 设置：资金落实内来源（视图）。
     */
    public FundSpecial setFundImplementationVId(String fundImplementationVId) {
        if (this.fundImplementationVId == null && fundImplementationVId == null) {
            // 均为null，不做处理。
        } else if (this.fundImplementationVId != null && fundImplementationVId != null) {
            // 均非null，判定不等，再做处理：
            if (this.fundImplementationVId.compareTo(fundImplementationVId) != 0) {
                this.fundImplementationVId = fundImplementationVId;
                if (!this.toUpdateCols.contains("FUND_IMPLEMENTATION_V_ID")) {
                    this.toUpdateCols.add("FUND_IMPLEMENTATION_V_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fundImplementationVId = fundImplementationVId;
            if (!this.toUpdateCols.contains("FUND_IMPLEMENTATION_V_ID")) {
                this.toUpdateCols.add("FUND_IMPLEMENTATION_V_ID");
            }
        }
        return this;
    }

    /**
     * 批复金额。
     */
    private BigDecimal approvedAmount;

    /**
     * 获取：批复金额。
     */
    public BigDecimal getApprovedAmount() {
        return this.approvedAmount;
    }

    /**
     * 设置：批复金额。
     */
    public FundSpecial setApprovedAmount(BigDecimal approvedAmount) {
        if (this.approvedAmount == null && approvedAmount == null) {
            // 均为null，不做处理。
        } else if (this.approvedAmount != null && approvedAmount != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvedAmount.compareTo(approvedAmount) != 0) {
                this.approvedAmount = approvedAmount;
                if (!this.toUpdateCols.contains("APPROVED_AMOUNT")) {
                    this.toUpdateCols.add("APPROVED_AMOUNT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvedAmount = approvedAmount;
            if (!this.toUpdateCols.contains("APPROVED_AMOUNT")) {
                this.toUpdateCols.add("APPROVED_AMOUNT");
            }
        }
        return this;
    }

    /**
     * 累计到位金额。
     */
    private BigDecimal cumReachAmt;

    /**
     * 获取：累计到位金额。
     */
    public BigDecimal getCumReachAmt() {
        return this.cumReachAmt;
    }

    /**
     * 设置：累计到位金额。
     */
    public FundSpecial setCumReachAmt(BigDecimal cumReachAmt) {
        if (this.cumReachAmt == null && cumReachAmt == null) {
            // 均为null，不做处理。
        } else if (this.cumReachAmt != null && cumReachAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.cumReachAmt.compareTo(cumReachAmt) != 0) {
                this.cumReachAmt = cumReachAmt;
                if (!this.toUpdateCols.contains("CUM_REACH_AMT")) {
                    this.toUpdateCols.add("CUM_REACH_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cumReachAmt = cumReachAmt;
            if (!this.toUpdateCols.contains("CUM_REACH_AMT")) {
                this.toUpdateCols.add("CUM_REACH_AMT");
            }
        }
        return this;
    }

    /**
     * 累计支付金额。
     */
    private BigDecimal cumPayAmt;

    /**
     * 获取：累计支付金额。
     */
    public BigDecimal getCumPayAmt() {
        return this.cumPayAmt;
    }

    /**
     * 设置：累计支付金额。
     */
    public FundSpecial setCumPayAmt(BigDecimal cumPayAmt) {
        if (this.cumPayAmt == null && cumPayAmt == null) {
            // 均为null，不做处理。
        } else if (this.cumPayAmt != null && cumPayAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.cumPayAmt.compareTo(cumPayAmt) != 0) {
                this.cumPayAmt = cumPayAmt;
                if (!this.toUpdateCols.contains("CUM_PAY_AMT")) {
                    this.toUpdateCols.add("CUM_PAY_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cumPayAmt = cumPayAmt;
            if (!this.toUpdateCols.contains("CUM_PAY_AMT")) {
                this.toUpdateCols.add("CUM_PAY_AMT");
            }
        }
        return this;
    }

    /**
     * 未到位资金。
     */
    private BigDecimal notReachAmt;

    /**
     * 获取：未到位资金。
     */
    public BigDecimal getNotReachAmt() {
        return this.notReachAmt;
    }

    /**
     * 设置：未到位资金。
     */
    public FundSpecial setNotReachAmt(BigDecimal notReachAmt) {
        if (this.notReachAmt == null && notReachAmt == null) {
            // 均为null，不做处理。
        } else if (this.notReachAmt != null && notReachAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.notReachAmt.compareTo(notReachAmt) != 0) {
                this.notReachAmt = notReachAmt;
                if (!this.toUpdateCols.contains("NOT_REACH_AMT")) {
                    this.toUpdateCols.add("NOT_REACH_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.notReachAmt = notReachAmt;
            if (!this.toUpdateCols.contains("NOT_REACH_AMT")) {
                this.toUpdateCols.add("NOT_REACH_AMT");
            }
        }
        return this;
    }

    /**
     * 支付明细码。
     */
    private String fundPayCode;

    /**
     * 获取：支付明细码。
     */
    public String getFundPayCode() {
        return this.fundPayCode;
    }

    /**
     * 设置：支付明细码。
     */
    public FundSpecial setFundPayCode(String fundPayCode) {
        if (this.fundPayCode == null && fundPayCode == null) {
            // 均为null，不做处理。
        } else if (this.fundPayCode != null && fundPayCode != null) {
            // 均非null，判定不等，再做处理：
            if (this.fundPayCode.compareTo(fundPayCode) != 0) {
                this.fundPayCode = fundPayCode;
                if (!this.toUpdateCols.contains("FUND_PAY_CODE")) {
                    this.toUpdateCols.add("FUND_PAY_CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fundPayCode = fundPayCode;
            if (!this.toUpdateCols.contains("FUND_PAY_CODE")) {
                this.toUpdateCols.add("FUND_PAY_CODE");
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
    public FundSpecial setCustomerUnit(String customerUnit) {
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
    public FundSpecial setPrjManageModeId(String prjManageModeId) {
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
     * 累计到位建设资金。
     */
    private BigDecimal cumBuildReachAmt;

    /**
     * 获取：累计到位建设资金。
     */
    public BigDecimal getCumBuildReachAmt() {
        return this.cumBuildReachAmt;
    }

    /**
     * 设置：累计到位建设资金。
     */
    public FundSpecial setCumBuildReachAmt(BigDecimal cumBuildReachAmt) {
        if (this.cumBuildReachAmt == null && cumBuildReachAmt == null) {
            // 均为null，不做处理。
        } else if (this.cumBuildReachAmt != null && cumBuildReachAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.cumBuildReachAmt.compareTo(cumBuildReachAmt) != 0) {
                this.cumBuildReachAmt = cumBuildReachAmt;
                if (!this.toUpdateCols.contains("CUM_BUILD_REACH_AMT")) {
                    this.toUpdateCols.add("CUM_BUILD_REACH_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cumBuildReachAmt = cumBuildReachAmt;
            if (!this.toUpdateCols.contains("CUM_BUILD_REACH_AMT")) {
                this.toUpdateCols.add("CUM_BUILD_REACH_AMT");
            }
        }
        return this;
    }

    /**
     * 累计到位征拆资金。
     */
    private BigDecimal cumAcqReachAmt;

    /**
     * 获取：累计到位征拆资金。
     */
    public BigDecimal getCumAcqReachAmt() {
        return this.cumAcqReachAmt;
    }

    /**
     * 设置：累计到位征拆资金。
     */
    public FundSpecial setCumAcqReachAmt(BigDecimal cumAcqReachAmt) {
        if (this.cumAcqReachAmt == null && cumAcqReachAmt == null) {
            // 均为null，不做处理。
        } else if (this.cumAcqReachAmt != null && cumAcqReachAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.cumAcqReachAmt.compareTo(cumAcqReachAmt) != 0) {
                this.cumAcqReachAmt = cumAcqReachAmt;
                if (!this.toUpdateCols.contains("CUM_ACQ_REACH_AMT")) {
                    this.toUpdateCols.add("CUM_ACQ_REACH_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.cumAcqReachAmt = cumAcqReachAmt;
            if (!this.toUpdateCols.contains("CUM_ACQ_REACH_AMT")) {
                this.toUpdateCols.add("CUM_ACQ_REACH_AMT");
            }
        }
        return this;
    }

    /**
     * 剩余到位资金。
     */
    private BigDecimal surReachAmt;

    /**
     * 获取：剩余到位资金。
     */
    public BigDecimal getSurReachAmt() {
        return this.surReachAmt;
    }

    /**
     * 设置：剩余到位资金。
     */
    public FundSpecial setSurReachAmt(BigDecimal surReachAmt) {
        if (this.surReachAmt == null && surReachAmt == null) {
            // 均为null，不做处理。
        } else if (this.surReachAmt != null && surReachAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.surReachAmt.compareTo(surReachAmt) != 0) {
                this.surReachAmt = surReachAmt;
                if (!this.toUpdateCols.contains("SUR_REACH_AMT")) {
                    this.toUpdateCols.add("SUR_REACH_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.surReachAmt = surReachAmt;
            if (!this.toUpdateCols.contains("SUR_REACH_AMT")) {
                this.toUpdateCols.add("SUR_REACH_AMT");
            }
        }
        return this;
    }

    /**
     * 到位资金类别。
     */
    private String fundReachCategory;

    /**
     * 获取：到位资金类别。
     */
    public String getFundReachCategory() {
        return this.fundReachCategory;
    }

    /**
     * 设置：到位资金类别。
     */
    public FundSpecial setFundReachCategory(String fundReachCategory) {
        if (this.fundReachCategory == null && fundReachCategory == null) {
            // 均为null，不做处理。
        } else if (this.fundReachCategory != null && fundReachCategory != null) {
            // 均非null，判定不等，再做处理：
            if (this.fundReachCategory.compareTo(fundReachCategory) != 0) {
                this.fundReachCategory = fundReachCategory;
                if (!this.toUpdateCols.contains("FUND_REACH_CATEGORY")) {
                    this.toUpdateCols.add("FUND_REACH_CATEGORY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fundReachCategory = fundReachCategory;
            if (!this.toUpdateCols.contains("FUND_REACH_CATEGORY")) {
                this.toUpdateCols.add("FUND_REACH_CATEGORY");
            }
        }
        return this;
    }

    /**
     * 期数。
     */
    private String nper;

    /**
     * 获取：期数。
     */
    public String getNper() {
        return this.nper;
    }

    /**
     * 设置：期数。
     */
    public FundSpecial setNper(String nper) {
        if (this.nper == null && nper == null) {
            // 均为null，不做处理。
        } else if (this.nper != null && nper != null) {
            // 均非null，判定不等，再做处理：
            if (this.nper.compareTo(nper) != 0) {
                this.nper = nper;
                if (!this.toUpdateCols.contains("NPER")) {
                    this.toUpdateCols.add("NPER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.nper = nper;
            if (!this.toUpdateCols.contains("NPER")) {
                this.toUpdateCols.add("NPER");
            }
        }
        return this;
    }

    /**
     * 费用名称。
     */
    private String costName;

    /**
     * 获取：费用名称。
     */
    public String getCostName() {
        return this.costName;
    }

    /**
     * 设置：费用名称。
     */
    public FundSpecial setCostName(String costName) {
        if (this.costName == null && costName == null) {
            // 均为null，不做处理。
        } else if (this.costName != null && costName != null) {
            // 均非null，判定不等，再做处理：
            if (this.costName.compareTo(costName) != 0) {
                this.costName = costName;
                if (!this.toUpdateCols.contains("COST_NAME")) {
                    this.toUpdateCols.add("COST_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.costName = costName;
            if (!this.toUpdateCols.contains("COST_NAME")) {
                this.toUpdateCols.add("COST_NAME");
            }
        }
        return this;
    }

    /**
     * 付款单位。
     */
    private String payUnit;

    /**
     * 获取：付款单位。
     */
    public String getPayUnit() {
        return this.payUnit;
    }

    /**
     * 设置：付款单位。
     */
    public FundSpecial setPayUnit(String payUnit) {
        if (this.payUnit == null && payUnit == null) {
            // 均为null，不做处理。
        } else if (this.payUnit != null && payUnit != null) {
            // 均非null，判定不等，再做处理：
            if (this.payUnit.compareTo(payUnit) != 0) {
                this.payUnit = payUnit;
                if (!this.toUpdateCols.contains("PAY_UNIT")) {
                    this.toUpdateCols.add("PAY_UNIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.payUnit = payUnit;
            if (!this.toUpdateCols.contains("PAY_UNIT")) {
                this.toUpdateCols.add("PAY_UNIT");
            }
        }
        return this;
    }

    /**
     * 收款银行。
     */
    private String receiptBank;

    /**
     * 获取：收款银行。
     */
    public String getReceiptBank() {
        return this.receiptBank;
    }

    /**
     * 设置：收款银行。
     */
    public FundSpecial setReceiptBank(String receiptBank) {
        if (this.receiptBank == null && receiptBank == null) {
            // 均为null，不做处理。
        } else if (this.receiptBank != null && receiptBank != null) {
            // 均非null，判定不等，再做处理：
            if (this.receiptBank.compareTo(receiptBank) != 0) {
                this.receiptBank = receiptBank;
                if (!this.toUpdateCols.contains("RECEIPT_BANK")) {
                    this.toUpdateCols.add("RECEIPT_BANK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.receiptBank = receiptBank;
            if (!this.toUpdateCols.contains("RECEIPT_BANK")) {
                this.toUpdateCols.add("RECEIPT_BANK");
            }
        }
        return this;
    }

    /**
     * 收款账户。
     */
    private String receiptAccount;

    /**
     * 获取：收款账户。
     */
    public String getReceiptAccount() {
        return this.receiptAccount;
    }

    /**
     * 设置：收款账户。
     */
    public FundSpecial setReceiptAccount(String receiptAccount) {
        if (this.receiptAccount == null && receiptAccount == null) {
            // 均为null，不做处理。
        } else if (this.receiptAccount != null && receiptAccount != null) {
            // 均非null，判定不等，再做处理：
            if (this.receiptAccount.compareTo(receiptAccount) != 0) {
                this.receiptAccount = receiptAccount;
                if (!this.toUpdateCols.contains("RECEIPT_ACCOUNT")) {
                    this.toUpdateCols.add("RECEIPT_ACCOUNT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.receiptAccount = receiptAccount;
            if (!this.toUpdateCols.contains("RECEIPT_ACCOUNT")) {
                this.toUpdateCols.add("RECEIPT_ACCOUNT");
            }
        }
        return this;
    }

    /**
     * 支付日期。
     */
    private LocalDate payDate;

    /**
     * 获取：支付日期。
     */
    public LocalDate getPayDate() {
        return this.payDate;
    }

    /**
     * 设置：支付日期。
     */
    public FundSpecial setPayDate(LocalDate payDate) {
        if (this.payDate == null && payDate == null) {
            // 均为null，不做处理。
        } else if (this.payDate != null && payDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.payDate.compareTo(payDate) != 0) {
                this.payDate = payDate;
                if (!this.toUpdateCols.contains("PAY_DATE")) {
                    this.toUpdateCols.add("PAY_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.payDate = payDate;
            if (!this.toUpdateCols.contains("PAY_DATE")) {
                this.toUpdateCols.add("PAY_DATE");
            }
        }
        return this;
    }

    /**
     * 本期审定应付金额。
     */
    private BigDecimal payableAmt;

    /**
     * 获取：本期审定应付金额。
     */
    public BigDecimal getPayableAmt() {
        return this.payableAmt;
    }

    /**
     * 设置：本期审定应付金额。
     */
    public FundSpecial setPayableAmt(BigDecimal payableAmt) {
        if (this.payableAmt == null && payableAmt == null) {
            // 均为null，不做处理。
        } else if (this.payableAmt != null && payableAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.payableAmt.compareTo(payableAmt) != 0) {
                this.payableAmt = payableAmt;
                if (!this.toUpdateCols.contains("PAYABLE_AMT")) {
                    this.toUpdateCols.add("PAYABLE_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.payableAmt = payableAmt;
            if (!this.toUpdateCols.contains("PAYABLE_AMT")) {
                this.toUpdateCols.add("PAYABLE_AMT");
            }
        }
        return this;
    }

    /**
     * 本期已付金额。
     */
    private BigDecimal paidAmt;

    /**
     * 获取：本期已付金额。
     */
    public BigDecimal getPaidAmt() {
        return this.paidAmt;
    }

    /**
     * 设置：本期已付金额。
     */
    public FundSpecial setPaidAmt(BigDecimal paidAmt) {
        if (this.paidAmt == null && paidAmt == null) {
            // 均为null，不做处理。
        } else if (this.paidAmt != null && paidAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.paidAmt.compareTo(paidAmt) != 0) {
                this.paidAmt = paidAmt;
                if (!this.toUpdateCols.contains("PAID_AMT")) {
                    this.toUpdateCols.add("PAID_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.paidAmt = paidAmt;
            if (!this.toUpdateCols.contains("PAID_AMT")) {
                this.toUpdateCols.add("PAID_AMT");
            }
        }
        return this;
    }

    /**
     * 本期未付金额。
     */
    private BigDecimal unpadAmt;

    /**
     * 获取：本期未付金额。
     */
    public BigDecimal getUnpadAmt() {
        return this.unpadAmt;
    }

    /**
     * 设置：本期未付金额。
     */
    public FundSpecial setUnpadAmt(BigDecimal unpadAmt) {
        if (this.unpadAmt == null && unpadAmt == null) {
            // 均为null，不做处理。
        } else if (this.unpadAmt != null && unpadAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.unpadAmt.compareTo(unpadAmt) != 0) {
                this.unpadAmt = unpadAmt;
                if (!this.toUpdateCols.contains("UNPAD_AMT")) {
                    this.toUpdateCols.add("UNPAD_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.unpadAmt = unpadAmt;
            if (!this.toUpdateCols.contains("UNPAD_AMT")) {
                this.toUpdateCols.add("UNPAD_AMT");
            }
        }
        return this;
    }

    /**
     * 收款单位。
     */
    private String payee;

    /**
     * 获取：收款单位。
     */
    public String getPayee() {
        return this.payee;
    }

    /**
     * 设置：收款单位。
     */
    public FundSpecial setPayee(String payee) {
        if (this.payee == null && payee == null) {
            // 均为null，不做处理。
        } else if (this.payee != null && payee != null) {
            // 均非null，判定不等，再做处理：
            if (this.payee.compareTo(payee) != 0) {
                this.payee = payee;
                if (!this.toUpdateCols.contains("PAYEE")) {
                    this.toUpdateCols.add("PAYEE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.payee = payee;
            if (!this.toUpdateCols.contains("PAYEE")) {
                this.toUpdateCols.add("PAYEE");
            }
        }
        return this;
    }

    /**
     * 保函情况。
     */
    private String guaranteeStates;

    /**
     * 获取：保函情况。
     */
    public String getGuaranteeStates() {
        return this.guaranteeStates;
    }

    /**
     * 设置：保函情况。
     */
    public FundSpecial setGuaranteeStates(String guaranteeStates) {
        if (this.guaranteeStates == null && guaranteeStates == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeStates != null && guaranteeStates != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeStates.compareTo(guaranteeStates) != 0) {
                this.guaranteeStates = guaranteeStates;
                if (!this.toUpdateCols.contains("GUARANTEE_STATES")) {
                    this.toUpdateCols.add("GUARANTEE_STATES");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeStates = guaranteeStates;
            if (!this.toUpdateCols.contains("GUARANTEE_STATES")) {
                this.toUpdateCols.add("GUARANTEE_STATES");
            }
        }
        return this;
    }

    /**
     * 审核状态。
     */
    private String approvalStatus;

    /**
     * 获取：审核状态。
     */
    public String getApprovalStatus() {
        return this.approvalStatus;
    }

    /**
     * 设置：审核状态。
     */
    public FundSpecial setApprovalStatus(String approvalStatus) {
        if (this.approvalStatus == null && approvalStatus == null) {
            // 均为null，不做处理。
        } else if (this.approvalStatus != null && approvalStatus != null) {
            // 均非null，判定不等，再做处理：
            if (this.approvalStatus.compareTo(approvalStatus) != 0) {
                this.approvalStatus = approvalStatus;
                if (!this.toUpdateCols.contains("APPROVAL_STATUS")) {
                    this.toUpdateCols.add("APPROVAL_STATUS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.approvalStatus = approvalStatus;
            if (!this.toUpdateCols.contains("APPROVAL_STATUS")) {
                this.toUpdateCols.add("APPROVAL_STATUS");
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
    public static FundSpecial newData() {
        FundSpecial obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static FundSpecial insertData() {
        FundSpecial obj = modelHelper.insertData();
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
    public static FundSpecial selectById(String id, List<String> includeCols, List<String> excludeCols) {
        FundSpecial obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<FundSpecial> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<FundSpecial> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<FundSpecial> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<FundSpecial> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(FundSpecial fromModel, FundSpecial toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}