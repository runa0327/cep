package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 二类费需求明细。
 */
public class FeeDemandDtl {

    /**
     * 模型助手。
     */
    private static final ModelHelper<FeeDemandDtl> modelHelper = new ModelHelper<>("FEE_DEMAND_DTL", new FeeDemandDtl());

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

    public static final String ENT_CODE = "FEE_DEMAND_DTL";
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
         * 二类费需求。
         */
        public static final String SECOND_CATEGORY_FEE_DEMAND_ID = "SECOND_CATEGORY_FEE_DEMAND_ID";
        /**
         * 资金需求节点。
         */
        public static final String FEE_DEMAND_NODE = "FEE_DEMAND_NODE";
        /**
         * 批复金额。
         */
        public static final String APPROVED_AMOUNT = "APPROVED_AMOUNT";
        /**
         * 可支付比例。
         */
        public static final String PAYABLE_RATIO = "PAYABLE_RATIO";
        /**
         * 可支付金额。
         */
        public static final String PAYABLE_AMOUNT = "PAYABLE_AMOUNT";
        /**
         * 已支付金额。
         */
        public static final String PAID_AMOUNT = "PAID_AMOUNT";
        /**
         * 支付比例。
         */
        public static final String PAYMENT_RATIO = "PAYMENT_RATIO";
        /**
         * 需求金额。
         */
        public static final String REQUIRED_AMOUNT = "REQUIRED_AMOUNT";
        /**
         * 提交时间。
         */
        public static final String SUBMIT_TIME = "SUBMIT_TIME";
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
    public FeeDemandDtl setId(String id) {
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
    public FeeDemandDtl setVer(Integer ver) {
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
    public FeeDemandDtl setTs(LocalDateTime ts) {
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
    public FeeDemandDtl setIsPreset(Boolean isPreset) {
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
    public FeeDemandDtl setCrtDt(LocalDateTime crtDt) {
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
    public FeeDemandDtl setCrtUserId(String crtUserId) {
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
    public FeeDemandDtl setLastModiDt(LocalDateTime lastModiDt) {
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
    public FeeDemandDtl setLastModiUserId(String lastModiUserId) {
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
    public FeeDemandDtl setStatus(String status) {
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
    public FeeDemandDtl setLkWfInstId(String lkWfInstId) {
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
    public FeeDemandDtl setCode(String code) {
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
    public FeeDemandDtl setName(String name) {
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
    public FeeDemandDtl setRemark(String remark) {
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
     * 二类费需求。
     */
    private String secondCategoryFeeDemandId;

    /**
     * 获取：二类费需求。
     */
    public String getSecondCategoryFeeDemandId() {
        return this.secondCategoryFeeDemandId;
    }

    /**
     * 设置：二类费需求。
     */
    public FeeDemandDtl setSecondCategoryFeeDemandId(String secondCategoryFeeDemandId) {
        if (this.secondCategoryFeeDemandId == null && secondCategoryFeeDemandId == null) {
            // 均为null，不做处理。
        } else if (this.secondCategoryFeeDemandId != null && secondCategoryFeeDemandId != null) {
            // 均非null，判定不等，再做处理：
            if (this.secondCategoryFeeDemandId.compareTo(secondCategoryFeeDemandId) != 0) {
                this.secondCategoryFeeDemandId = secondCategoryFeeDemandId;
                if (!this.toUpdateCols.contains("SECOND_CATEGORY_FEE_DEMAND_ID")) {
                    this.toUpdateCols.add("SECOND_CATEGORY_FEE_DEMAND_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.secondCategoryFeeDemandId = secondCategoryFeeDemandId;
            if (!this.toUpdateCols.contains("SECOND_CATEGORY_FEE_DEMAND_ID")) {
                this.toUpdateCols.add("SECOND_CATEGORY_FEE_DEMAND_ID");
            }
        }
        return this;
    }

    /**
     * 资金需求节点。
     */
    private String feeDemandNode;

    /**
     * 获取：资金需求节点。
     */
    public String getFeeDemandNode() {
        return this.feeDemandNode;
    }

    /**
     * 设置：资金需求节点。
     */
    public FeeDemandDtl setFeeDemandNode(String feeDemandNode) {
        if (this.feeDemandNode == null && feeDemandNode == null) {
            // 均为null，不做处理。
        } else if (this.feeDemandNode != null && feeDemandNode != null) {
            // 均非null，判定不等，再做处理：
            if (this.feeDemandNode.compareTo(feeDemandNode) != 0) {
                this.feeDemandNode = feeDemandNode;
                if (!this.toUpdateCols.contains("FEE_DEMAND_NODE")) {
                    this.toUpdateCols.add("FEE_DEMAND_NODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.feeDemandNode = feeDemandNode;
            if (!this.toUpdateCols.contains("FEE_DEMAND_NODE")) {
                this.toUpdateCols.add("FEE_DEMAND_NODE");
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
    public FeeDemandDtl setApprovedAmount(BigDecimal approvedAmount) {
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
     * 可支付比例。
     */
    private BigDecimal payableRatio;

    /**
     * 获取：可支付比例。
     */
    public BigDecimal getPayableRatio() {
        return this.payableRatio;
    }

    /**
     * 设置：可支付比例。
     */
    public FeeDemandDtl setPayableRatio(BigDecimal payableRatio) {
        if (this.payableRatio == null && payableRatio == null) {
            // 均为null，不做处理。
        } else if (this.payableRatio != null && payableRatio != null) {
            // 均非null，判定不等，再做处理：
            if (this.payableRatio.compareTo(payableRatio) != 0) {
                this.payableRatio = payableRatio;
                if (!this.toUpdateCols.contains("PAYABLE_RATIO")) {
                    this.toUpdateCols.add("PAYABLE_RATIO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.payableRatio = payableRatio;
            if (!this.toUpdateCols.contains("PAYABLE_RATIO")) {
                this.toUpdateCols.add("PAYABLE_RATIO");
            }
        }
        return this;
    }

    /**
     * 可支付金额。
     */
    private BigDecimal payableAmount;

    /**
     * 获取：可支付金额。
     */
    public BigDecimal getPayableAmount() {
        return this.payableAmount;
    }

    /**
     * 设置：可支付金额。
     */
    public FeeDemandDtl setPayableAmount(BigDecimal payableAmount) {
        if (this.payableAmount == null && payableAmount == null) {
            // 均为null，不做处理。
        } else if (this.payableAmount != null && payableAmount != null) {
            // 均非null，判定不等，再做处理：
            if (this.payableAmount.compareTo(payableAmount) != 0) {
                this.payableAmount = payableAmount;
                if (!this.toUpdateCols.contains("PAYABLE_AMOUNT")) {
                    this.toUpdateCols.add("PAYABLE_AMOUNT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.payableAmount = payableAmount;
            if (!this.toUpdateCols.contains("PAYABLE_AMOUNT")) {
                this.toUpdateCols.add("PAYABLE_AMOUNT");
            }
        }
        return this;
    }

    /**
     * 已支付金额。
     */
    private BigDecimal paidAmount;

    /**
     * 获取：已支付金额。
     */
    public BigDecimal getPaidAmount() {
        return this.paidAmount;
    }

    /**
     * 设置：已支付金额。
     */
    public FeeDemandDtl setPaidAmount(BigDecimal paidAmount) {
        if (this.paidAmount == null && paidAmount == null) {
            // 均为null，不做处理。
        } else if (this.paidAmount != null && paidAmount != null) {
            // 均非null，判定不等，再做处理：
            if (this.paidAmount.compareTo(paidAmount) != 0) {
                this.paidAmount = paidAmount;
                if (!this.toUpdateCols.contains("PAID_AMOUNT")) {
                    this.toUpdateCols.add("PAID_AMOUNT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.paidAmount = paidAmount;
            if (!this.toUpdateCols.contains("PAID_AMOUNT")) {
                this.toUpdateCols.add("PAID_AMOUNT");
            }
        }
        return this;
    }

    /**
     * 支付比例。
     */
    private BigDecimal paymentRatio;

    /**
     * 获取：支付比例。
     */
    public BigDecimal getPaymentRatio() {
        return this.paymentRatio;
    }

    /**
     * 设置：支付比例。
     */
    public FeeDemandDtl setPaymentRatio(BigDecimal paymentRatio) {
        if (this.paymentRatio == null && paymentRatio == null) {
            // 均为null，不做处理。
        } else if (this.paymentRatio != null && paymentRatio != null) {
            // 均非null，判定不等，再做处理：
            if (this.paymentRatio.compareTo(paymentRatio) != 0) {
                this.paymentRatio = paymentRatio;
                if (!this.toUpdateCols.contains("PAYMENT_RATIO")) {
                    this.toUpdateCols.add("PAYMENT_RATIO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.paymentRatio = paymentRatio;
            if (!this.toUpdateCols.contains("PAYMENT_RATIO")) {
                this.toUpdateCols.add("PAYMENT_RATIO");
            }
        }
        return this;
    }

    /**
     * 需求金额。
     */
    private BigDecimal requiredAmount;

    /**
     * 获取：需求金额。
     */
    public BigDecimal getRequiredAmount() {
        return this.requiredAmount;
    }

    /**
     * 设置：需求金额。
     */
    public FeeDemandDtl setRequiredAmount(BigDecimal requiredAmount) {
        if (this.requiredAmount == null && requiredAmount == null) {
            // 均为null，不做处理。
        } else if (this.requiredAmount != null && requiredAmount != null) {
            // 均非null，判定不等，再做处理：
            if (this.requiredAmount.compareTo(requiredAmount) != 0) {
                this.requiredAmount = requiredAmount;
                if (!this.toUpdateCols.contains("REQUIRED_AMOUNT")) {
                    this.toUpdateCols.add("REQUIRED_AMOUNT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.requiredAmount = requiredAmount;
            if (!this.toUpdateCols.contains("REQUIRED_AMOUNT")) {
                this.toUpdateCols.add("REQUIRED_AMOUNT");
            }
        }
        return this;
    }

    /**
     * 提交时间。
     */
    private LocalDateTime submitTime;

    /**
     * 获取：提交时间。
     */
    public LocalDateTime getSubmitTime() {
        return this.submitTime;
    }

    /**
     * 设置：提交时间。
     */
    public FeeDemandDtl setSubmitTime(LocalDateTime submitTime) {
        if (this.submitTime == null && submitTime == null) {
            // 均为null，不做处理。
        } else if (this.submitTime != null && submitTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.submitTime.compareTo(submitTime) != 0) {
                this.submitTime = submitTime;
                if (!this.toUpdateCols.contains("SUBMIT_TIME")) {
                    this.toUpdateCols.add("SUBMIT_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.submitTime = submitTime;
            if (!this.toUpdateCols.contains("SUBMIT_TIME")) {
                this.toUpdateCols.add("SUBMIT_TIME");
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
    public static FeeDemandDtl newData() {
        FeeDemandDtl obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static FeeDemandDtl insertData() {
        FeeDemandDtl obj = modelHelper.insertData();
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
    public static FeeDemandDtl selectById(String id, List<String> includeCols, List<String> excludeCols) {
        FeeDemandDtl obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static FeeDemandDtl selectById(String id) {
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
    public static List<FeeDemandDtl> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<FeeDemandDtl> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<FeeDemandDtl> selectByIds(List<String> ids) {
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
    public static List<FeeDemandDtl> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<FeeDemandDtl> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<FeeDemandDtl> selectByWhere(Where where) {
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
    public static FeeDemandDtl selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<FeeDemandDtl> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用FeeDemandDtl.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmptyList(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static FeeDemandDtl selectOneByWhere(Where where) {
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
    public static void copyCols(FeeDemandDtl fromModel, FeeDemandDtl toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(FeeDemandDtl fromModel, FeeDemandDtl toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}