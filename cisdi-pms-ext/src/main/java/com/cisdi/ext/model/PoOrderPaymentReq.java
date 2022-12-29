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
 * 采购合同付款申请。
 */
public class PoOrderPaymentReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PoOrderPaymentReq> modelHelper = new ModelHelper<>("PO_ORDER_PAYMENT_REQ", new PoOrderPaymentReq());

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

    public static final String ENT_CODE = "PO_ORDER_PAYMENT_REQ";
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
         * 资金需求计划。
         */
        public static final String RELATION_AMOUT_PLAN_REQ_ID = "RELATION_AMOUT_PLAN_REQ_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 资金需求项目名称。
         */
        public static final String AMOUT_PM_PRJ_ID = "AMOUT_PM_PRJ_ID";
        /**
         * 付款依据。
         */
        public static final String PAY_BASIS_ID = "PAY_BASIS_ID";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 附件。
         */
        public static final String ATT_FILE_GROUP_ID = "ATT_FILE_GROUP_ID";
        /**
         * 合同。
         */
        public static final String CONTRACT_ID = "CONTRACT_ID";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 合同金额（万）。
         */
        public static final String CONTRACT_PRICE = "CONTRACT_PRICE";
        /**
         * 支付金额。
         */
        public static final String PAY_AMT = "PAY_AMT";
        /**
         * 合同科目。
         */
        public static final String CONTRACT_SUBJECT_ONE = "CONTRACT_SUBJECT_ONE";
        /**
         * 付款金额（万）。
         */
        public static final String PAY_AMT_ONE = "PAY_AMT_ONE";
        /**
         * 合同科目。
         */
        public static final String CONTRACT_SUBJECT_TWO = "CONTRACT_SUBJECT_TWO";
        /**
         * 付款金额。
         */
        public static final String PAY_AMT_TWO = "PAY_AMT_TWO";
        /**
         * 费用大类。
         */
        public static final String COST_CATEGORY_ID = "COST_CATEGORY_ID";
        /**
         * 付款类型。
         */
        public static final String PAY_TYPE_ID = "PAY_TYPE_ID";
        /**
         * 预算金额。
         */
        public static final String BUDGET_AMT = "BUDGET_AMT";
        /**
         * 备注1。
         */
        public static final String REMARK_ONE = "REMARK_ONE";
        /**
         * 部门。
         */
        public static final String DEPT_NAME = "DEPT_NAME";
        /**
         * 概算金额（万）。
         */
        public static final String ESTIMATE_AMT = "ESTIMATE_AMT";
        /**
         * 财评金额（万）。
         */
        public static final String FINANCIAL_AMT = "FINANCIAL_AMT";
        /**
         * 结算金额（万）。
         */
        public static final String SETTLEMENT_AMT = "SETTLEMENT_AMT";
        /**
         * 费用类型。
         */
        public static final String COST_TYPE_ID = "COST_TYPE_ID";
        /**
         * 费用名称。
         */
        public static final String COST_NAME = "COST_NAME";
        /**
         * 至上期累计付款（万）。
         */
        public static final String LAST_SUM_PAY = "LAST_SUM_PAY";
        /**
         * 本期申请金额（万）。
         */
        public static final String STAGE_APPLY_AMT = "STAGE_APPLY_AMT";
        /**
         * 本期支付金额（万）。
         */
        public static final String STAGE_PAY_AMT_TWO = "STAGE_PAY_AMT_TWO";
        /**
         * 开票金额。
         */
        public static final String INVOICE_AMT = "INVOICE_AMT";
        /**
         * 至本期累计付款。
         */
        public static final String NOW_SUM_PAY = "NOW_SUM_PAY";
        /**
         * 合同付款占比。
         */
        public static final String CONTRACT_PERCENT = "CONTRACT_PERCENT";
        /**
         * 概算付款占比。
         */
        public static final String ESTIMATE_PERCENT = "ESTIMATE_PERCENT";
        /**
         * 财评付款占比。
         */
        public static final String FINANCIAL_PERCENT = "FINANCIAL_PERCENT";
        /**
         * 结算付款占比。
         */
        public static final String SETTLEMENT_PERCENT = "SETTLEMENT_PERCENT";
        /**
         * 收款单位。
         */
        public static final String COLLECTION_DEPT_TWO = "COLLECTION_DEPT_TWO";
        /**
         * 开户行。
         */
        public static final String BANK_OF_DEPOSIT = "BANK_OF_DEPOSIT";
        /**
         * 账号。
         */
        public static final String ACCOUNT_NO = "ACCOUNT_NO";
        /**
         * 收款单。
         */
        public static final String RECEIPT = "RECEIPT";
        /**
         * 专户开户行。
         */
        public static final String SPECIAL_BANK_OF_DEPOSIT = "SPECIAL_BANK_OF_DEPOSIT";
        /**
         * 专户账号。
         */
        public static final String SPECIAL_ACCOUNT_NO = "SPECIAL_ACCOUNT_NO";
        /**
         * 请款事由。
         */
        public static final String APPLY_COST_REASON = "APPLY_COST_REASON";
        /**
         * 主体材料。
         */
        public static final String SUBJECT_FILE = "SUBJECT_FILE";
        /**
         * 保函名称。
         */
        public static final String GUARANTEE_ID = "GUARANTEE_ID";
        /**
         * 保函金额。
         */
        public static final String GUARANTEE_AMT = "GUARANTEE_AMT";
        /**
         * 保函名称。
         */
        public static final String GUARANTEE_NAME = "GUARANTEE_NAME";
        /**
         * 保函结束日期。
         */
        public static final String GUARANTEE_END_DATE = "GUARANTEE_END_DATE";
        /**
         * 保函结果材料。
         */
        public static final String GUARANTEE_RESULT_FILE = "GUARANTEE_RESULT_FILE";
        /**
         * 意见发表人。
         */
        public static final String COMMENT_PUBLISH_USER = "COMMENT_PUBLISH_USER";
        /**
         * 意见发表日期。
         */
        public static final String COMMENT_PUBLISH_DATE = "COMMENT_PUBLISH_DATE";
        /**
         * 意见内容。
         */
        public static final String COMMENT_PUBLISH_CONTENT = "COMMENT_PUBLISH_CONTENT";
        /**
         * 部门意见发表人。
         */
        public static final String DEPT_COMMENT_PUBLISH_USER = "DEPT_COMMENT_PUBLISH_USER";
        /**
         * 部门意见发表日期。
         */
        public static final String DEPT_COMMENT_PUBLISH_DATE = "DEPT_COMMENT_PUBLISH_DATE";
        /**
         * 部门意见。
         */
        public static final String DEPT_MESSAGE = "DEPT_MESSAGE";
        /**
         * 财务意见发表人。
         */
        public static final String FINANCE_PUBLISH_USER = "FINANCE_PUBLISH_USER";
        /**
         * 财务意见发表日期。
         */
        public static final String FINANCE_PUBLISH_DATE = "FINANCE_PUBLISH_DATE";
        /**
         * 财务意见。
         */
        public static final String FINANCE_MESSAGE = "FINANCE_MESSAGE";
        /**
         * 领导意见发表人。
         */
        public static final String LEADER_COMMENT_PUBLISH_USER = "LEADER_COMMENT_PUBLISH_USER";
        /**
         * 领导意见发表日期。
         */
        public static final String LEADER_COMMENT_PUBLISH_DATE = "LEADER_COMMENT_PUBLISH_DATE";
        /**
         * 领导意见。
         */
        public static final String LEADER_MESSAGE = "LEADER_MESSAGE";
        /**
         * 财务领导意见发表人。
         */
        public static final String FINANCE_LEADER_PUBLISH_USER = "FINANCE_LEADER_PUBLISH_USER";
        /**
         * 财务领导意见发表日期。
         */
        public static final String FINANCE_LEADER_PUBLISH_DATE = "FINANCE_LEADER_PUBLISH_DATE";
        /**
         * 财务领导意见。
         */
        public static final String FINANCE_LEADER_MESSAGE = "FINANCE_LEADER_MESSAGE";
        /**
         * 总经理。
         */
        public static final String PRESIDENT = "PRESIDENT";
        /**
         * 总经理意见发表日期。
         */
        public static final String PRESIDENT_DATE = "PRESIDENT_DATE";
        /**
         * 总经理意见。
         */
        public static final String PRESIDENT_MESSAGE = "PRESIDENT_MESSAGE";
        /**
         * 董事长。
         */
        public static final String CHAIRMAN = "CHAIRMAN";
        /**
         * 董事长意见发表日期。
         */
        public static final String CHAIRMAN_DATE = "CHAIRMAN_DATE";
        /**
         * 董事长意见。
         */
        public static final String CHAIRMAN_MESSAGE = "CHAIRMAN_MESSAGE";
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
    public PoOrderPaymentReq setId(String id) {
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
    public PoOrderPaymentReq setVer(Integer ver) {
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
    public PoOrderPaymentReq setTs(LocalDateTime ts) {
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
    public PoOrderPaymentReq setIsPreset(Boolean isPreset) {
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
    public PoOrderPaymentReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PoOrderPaymentReq setLastModiUserId(String lastModiUserId) {
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
    public PoOrderPaymentReq setCode(String code) {
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
    public PoOrderPaymentReq setName(String name) {
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
    public PoOrderPaymentReq setRemark(String remark) {
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
    public PoOrderPaymentReq setLkWfInstId(String lkWfInstId) {
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
     * 资金需求计划。
     */
    private String relationAmoutPlanReqId;

    /**
     * 获取：资金需求计划。
     */
    public String getRelationAmoutPlanReqId() {
        return this.relationAmoutPlanReqId;
    }

    /**
     * 设置：资金需求计划。
     */
    public PoOrderPaymentReq setRelationAmoutPlanReqId(String relationAmoutPlanReqId) {
        if (this.relationAmoutPlanReqId == null && relationAmoutPlanReqId == null) {
            // 均为null，不做处理。
        } else if (this.relationAmoutPlanReqId != null && relationAmoutPlanReqId != null) {
            // 均非null，判定不等，再做处理：
            if (this.relationAmoutPlanReqId.compareTo(relationAmoutPlanReqId) != 0) {
                this.relationAmoutPlanReqId = relationAmoutPlanReqId;
                if (!this.toUpdateCols.contains("RELATION_AMOUT_PLAN_REQ_ID")) {
                    this.toUpdateCols.add("RELATION_AMOUT_PLAN_REQ_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.relationAmoutPlanReqId = relationAmoutPlanReqId;
            if (!this.toUpdateCols.contains("RELATION_AMOUT_PLAN_REQ_ID")) {
                this.toUpdateCols.add("RELATION_AMOUT_PLAN_REQ_ID");
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
    public PoOrderPaymentReq setStatus(String status) {
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
    public PoOrderPaymentReq setAmoutPmPrjId(String amoutPmPrjId) {
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
     * 付款依据。
     */
    private String payBasisId;

    /**
     * 获取：付款依据。
     */
    public String getPayBasisId() {
        return this.payBasisId;
    }

    /**
     * 设置：付款依据。
     */
    public PoOrderPaymentReq setPayBasisId(String payBasisId) {
        if (this.payBasisId == null && payBasisId == null) {
            // 均为null，不做处理。
        } else if (this.payBasisId != null && payBasisId != null) {
            // 均非null，判定不等，再做处理：
            if (this.payBasisId.compareTo(payBasisId) != 0) {
                this.payBasisId = payBasisId;
                if (!this.toUpdateCols.contains("PAY_BASIS_ID")) {
                    this.toUpdateCols.add("PAY_BASIS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.payBasisId = payBasisId;
            if (!this.toUpdateCols.contains("PAY_BASIS_ID")) {
                this.toUpdateCols.add("PAY_BASIS_ID");
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
    public PoOrderPaymentReq setCrtUserId(String crtUserId) {
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
    public PoOrderPaymentReq setCrtDeptId(String crtDeptId) {
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
    public PoOrderPaymentReq setAttFileGroupId(String attFileGroupId) {
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
    public PoOrderPaymentReq setContractId(String contractId) {
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
    public PoOrderPaymentReq setCrtDt(LocalDateTime crtDt) {
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
    public PoOrderPaymentReq setContractPrice(BigDecimal contractPrice) {
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
     * 支付金额。
     */
    private BigDecimal payAmt;

    /**
     * 获取：支付金额。
     */
    public BigDecimal getPayAmt() {
        return this.payAmt;
    }

    /**
     * 设置：支付金额。
     */
    public PoOrderPaymentReq setPayAmt(BigDecimal payAmt) {
        if (this.payAmt == null && payAmt == null) {
            // 均为null，不做处理。
        } else if (this.payAmt != null && payAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.payAmt.compareTo(payAmt) != 0) {
                this.payAmt = payAmt;
                if (!this.toUpdateCols.contains("PAY_AMT")) {
                    this.toUpdateCols.add("PAY_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.payAmt = payAmt;
            if (!this.toUpdateCols.contains("PAY_AMT")) {
                this.toUpdateCols.add("PAY_AMT");
            }
        }
        return this;
    }

    /**
     * 合同科目。
     */
    private String contractSubjectOne;

    /**
     * 获取：合同科目。
     */
    public String getContractSubjectOne() {
        return this.contractSubjectOne;
    }

    /**
     * 设置：合同科目。
     */
    public PoOrderPaymentReq setContractSubjectOne(String contractSubjectOne) {
        if (this.contractSubjectOne == null && contractSubjectOne == null) {
            // 均为null，不做处理。
        } else if (this.contractSubjectOne != null && contractSubjectOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.contractSubjectOne.compareTo(contractSubjectOne) != 0) {
                this.contractSubjectOne = contractSubjectOne;
                if (!this.toUpdateCols.contains("CONTRACT_SUBJECT_ONE")) {
                    this.toUpdateCols.add("CONTRACT_SUBJECT_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contractSubjectOne = contractSubjectOne;
            if (!this.toUpdateCols.contains("CONTRACT_SUBJECT_ONE")) {
                this.toUpdateCols.add("CONTRACT_SUBJECT_ONE");
            }
        }
        return this;
    }

    /**
     * 付款金额（万）。
     */
    private BigDecimal payAmtOne;

    /**
     * 获取：付款金额（万）。
     */
    public BigDecimal getPayAmtOne() {
        return this.payAmtOne;
    }

    /**
     * 设置：付款金额（万）。
     */
    public PoOrderPaymentReq setPayAmtOne(BigDecimal payAmtOne) {
        if (this.payAmtOne == null && payAmtOne == null) {
            // 均为null，不做处理。
        } else if (this.payAmtOne != null && payAmtOne != null) {
            // 均非null，判定不等，再做处理：
            if (this.payAmtOne.compareTo(payAmtOne) != 0) {
                this.payAmtOne = payAmtOne;
                if (!this.toUpdateCols.contains("PAY_AMT_ONE")) {
                    this.toUpdateCols.add("PAY_AMT_ONE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.payAmtOne = payAmtOne;
            if (!this.toUpdateCols.contains("PAY_AMT_ONE")) {
                this.toUpdateCols.add("PAY_AMT_ONE");
            }
        }
        return this;
    }

    /**
     * 合同科目。
     */
    private String contractSubjectTwo;

    /**
     * 获取：合同科目。
     */
    public String getContractSubjectTwo() {
        return this.contractSubjectTwo;
    }

    /**
     * 设置：合同科目。
     */
    public PoOrderPaymentReq setContractSubjectTwo(String contractSubjectTwo) {
        if (this.contractSubjectTwo == null && contractSubjectTwo == null) {
            // 均为null，不做处理。
        } else if (this.contractSubjectTwo != null && contractSubjectTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.contractSubjectTwo.compareTo(contractSubjectTwo) != 0) {
                this.contractSubjectTwo = contractSubjectTwo;
                if (!this.toUpdateCols.contains("CONTRACT_SUBJECT_TWO")) {
                    this.toUpdateCols.add("CONTRACT_SUBJECT_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contractSubjectTwo = contractSubjectTwo;
            if (!this.toUpdateCols.contains("CONTRACT_SUBJECT_TWO")) {
                this.toUpdateCols.add("CONTRACT_SUBJECT_TWO");
            }
        }
        return this;
    }

    /**
     * 付款金额。
     */
    private BigDecimal payAmtTwo;

    /**
     * 获取：付款金额。
     */
    public BigDecimal getPayAmtTwo() {
        return this.payAmtTwo;
    }

    /**
     * 设置：付款金额。
     */
    public PoOrderPaymentReq setPayAmtTwo(BigDecimal payAmtTwo) {
        if (this.payAmtTwo == null && payAmtTwo == null) {
            // 均为null，不做处理。
        } else if (this.payAmtTwo != null && payAmtTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.payAmtTwo.compareTo(payAmtTwo) != 0) {
                this.payAmtTwo = payAmtTwo;
                if (!this.toUpdateCols.contains("PAY_AMT_TWO")) {
                    this.toUpdateCols.add("PAY_AMT_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.payAmtTwo = payAmtTwo;
            if (!this.toUpdateCols.contains("PAY_AMT_TWO")) {
                this.toUpdateCols.add("PAY_AMT_TWO");
            }
        }
        return this;
    }

    /**
     * 费用大类。
     */
    private String costCategoryId;

    /**
     * 获取：费用大类。
     */
    public String getCostCategoryId() {
        return this.costCategoryId;
    }

    /**
     * 设置：费用大类。
     */
    public PoOrderPaymentReq setCostCategoryId(String costCategoryId) {
        if (this.costCategoryId == null && costCategoryId == null) {
            // 均为null，不做处理。
        } else if (this.costCategoryId != null && costCategoryId != null) {
            // 均非null，判定不等，再做处理：
            if (this.costCategoryId.compareTo(costCategoryId) != 0) {
                this.costCategoryId = costCategoryId;
                if (!this.toUpdateCols.contains("COST_CATEGORY_ID")) {
                    this.toUpdateCols.add("COST_CATEGORY_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.costCategoryId = costCategoryId;
            if (!this.toUpdateCols.contains("COST_CATEGORY_ID")) {
                this.toUpdateCols.add("COST_CATEGORY_ID");
            }
        }
        return this;
    }

    /**
     * 付款类型。
     */
    private String payTypeId;

    /**
     * 获取：付款类型。
     */
    public String getPayTypeId() {
        return this.payTypeId;
    }

    /**
     * 设置：付款类型。
     */
    public PoOrderPaymentReq setPayTypeId(String payTypeId) {
        if (this.payTypeId == null && payTypeId == null) {
            // 均为null，不做处理。
        } else if (this.payTypeId != null && payTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.payTypeId.compareTo(payTypeId) != 0) {
                this.payTypeId = payTypeId;
                if (!this.toUpdateCols.contains("PAY_TYPE_ID")) {
                    this.toUpdateCols.add("PAY_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.payTypeId = payTypeId;
            if (!this.toUpdateCols.contains("PAY_TYPE_ID")) {
                this.toUpdateCols.add("PAY_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 预算金额。
     */
    private BigDecimal budgetAmt;

    /**
     * 获取：预算金额。
     */
    public BigDecimal getBudgetAmt() {
        return this.budgetAmt;
    }

    /**
     * 设置：预算金额。
     */
    public PoOrderPaymentReq setBudgetAmt(BigDecimal budgetAmt) {
        if (this.budgetAmt == null && budgetAmt == null) {
            // 均为null，不做处理。
        } else if (this.budgetAmt != null && budgetAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.budgetAmt.compareTo(budgetAmt) != 0) {
                this.budgetAmt = budgetAmt;
                if (!this.toUpdateCols.contains("BUDGET_AMT")) {
                    this.toUpdateCols.add("BUDGET_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.budgetAmt = budgetAmt;
            if (!this.toUpdateCols.contains("BUDGET_AMT")) {
                this.toUpdateCols.add("BUDGET_AMT");
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
    public PoOrderPaymentReq setRemarkOne(String remarkOne) {
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
    public PoOrderPaymentReq setDeptName(String deptName) {
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
     * 概算金额（万）。
     */
    private BigDecimal estimateAmt;

    /**
     * 获取：概算金额（万）。
     */
    public BigDecimal getEstimateAmt() {
        return this.estimateAmt;
    }

    /**
     * 设置：概算金额（万）。
     */
    public PoOrderPaymentReq setEstimateAmt(BigDecimal estimateAmt) {
        if (this.estimateAmt == null && estimateAmt == null) {
            // 均为null，不做处理。
        } else if (this.estimateAmt != null && estimateAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.estimateAmt.compareTo(estimateAmt) != 0) {
                this.estimateAmt = estimateAmt;
                if (!this.toUpdateCols.contains("ESTIMATE_AMT")) {
                    this.toUpdateCols.add("ESTIMATE_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.estimateAmt = estimateAmt;
            if (!this.toUpdateCols.contains("ESTIMATE_AMT")) {
                this.toUpdateCols.add("ESTIMATE_AMT");
            }
        }
        return this;
    }

    /**
     * 财评金额（万）。
     */
    private BigDecimal financialAmt;

    /**
     * 获取：财评金额（万）。
     */
    public BigDecimal getFinancialAmt() {
        return this.financialAmt;
    }

    /**
     * 设置：财评金额（万）。
     */
    public PoOrderPaymentReq setFinancialAmt(BigDecimal financialAmt) {
        if (this.financialAmt == null && financialAmt == null) {
            // 均为null，不做处理。
        } else if (this.financialAmt != null && financialAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.financialAmt.compareTo(financialAmt) != 0) {
                this.financialAmt = financialAmt;
                if (!this.toUpdateCols.contains("FINANCIAL_AMT")) {
                    this.toUpdateCols.add("FINANCIAL_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financialAmt = financialAmt;
            if (!this.toUpdateCols.contains("FINANCIAL_AMT")) {
                this.toUpdateCols.add("FINANCIAL_AMT");
            }
        }
        return this;
    }

    /**
     * 结算金额（万）。
     */
    private BigDecimal settlementAmt;

    /**
     * 获取：结算金额（万）。
     */
    public BigDecimal getSettlementAmt() {
        return this.settlementAmt;
    }

    /**
     * 设置：结算金额（万）。
     */
    public PoOrderPaymentReq setSettlementAmt(BigDecimal settlementAmt) {
        if (this.settlementAmt == null && settlementAmt == null) {
            // 均为null，不做处理。
        } else if (this.settlementAmt != null && settlementAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.settlementAmt.compareTo(settlementAmt) != 0) {
                this.settlementAmt = settlementAmt;
                if (!this.toUpdateCols.contains("SETTLEMENT_AMT")) {
                    this.toUpdateCols.add("SETTLEMENT_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.settlementAmt = settlementAmt;
            if (!this.toUpdateCols.contains("SETTLEMENT_AMT")) {
                this.toUpdateCols.add("SETTLEMENT_AMT");
            }
        }
        return this;
    }

    /**
     * 费用类型。
     */
    private String costTypeId;

    /**
     * 获取：费用类型。
     */
    public String getCostTypeId() {
        return this.costTypeId;
    }

    /**
     * 设置：费用类型。
     */
    public PoOrderPaymentReq setCostTypeId(String costTypeId) {
        if (this.costTypeId == null && costTypeId == null) {
            // 均为null，不做处理。
        } else if (this.costTypeId != null && costTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.costTypeId.compareTo(costTypeId) != 0) {
                this.costTypeId = costTypeId;
                if (!this.toUpdateCols.contains("COST_TYPE_ID")) {
                    this.toUpdateCols.add("COST_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.costTypeId = costTypeId;
            if (!this.toUpdateCols.contains("COST_TYPE_ID")) {
                this.toUpdateCols.add("COST_TYPE_ID");
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
    public PoOrderPaymentReq setCostName(String costName) {
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
     * 至上期累计付款（万）。
     */
    private BigDecimal lastSumPay;

    /**
     * 获取：至上期累计付款（万）。
     */
    public BigDecimal getLastSumPay() {
        return this.lastSumPay;
    }

    /**
     * 设置：至上期累计付款（万）。
     */
    public PoOrderPaymentReq setLastSumPay(BigDecimal lastSumPay) {
        if (this.lastSumPay == null && lastSumPay == null) {
            // 均为null，不做处理。
        } else if (this.lastSumPay != null && lastSumPay != null) {
            // 均非null，判定不等，再做处理：
            if (this.lastSumPay.compareTo(lastSumPay) != 0) {
                this.lastSumPay = lastSumPay;
                if (!this.toUpdateCols.contains("LAST_SUM_PAY")) {
                    this.toUpdateCols.add("LAST_SUM_PAY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.lastSumPay = lastSumPay;
            if (!this.toUpdateCols.contains("LAST_SUM_PAY")) {
                this.toUpdateCols.add("LAST_SUM_PAY");
            }
        }
        return this;
    }

    /**
     * 本期申请金额（万）。
     */
    private BigDecimal stageApplyAmt;

    /**
     * 获取：本期申请金额（万）。
     */
    public BigDecimal getStageApplyAmt() {
        return this.stageApplyAmt;
    }

    /**
     * 设置：本期申请金额（万）。
     */
    public PoOrderPaymentReq setStageApplyAmt(BigDecimal stageApplyAmt) {
        if (this.stageApplyAmt == null && stageApplyAmt == null) {
            // 均为null，不做处理。
        } else if (this.stageApplyAmt != null && stageApplyAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.stageApplyAmt.compareTo(stageApplyAmt) != 0) {
                this.stageApplyAmt = stageApplyAmt;
                if (!this.toUpdateCols.contains("STAGE_APPLY_AMT")) {
                    this.toUpdateCols.add("STAGE_APPLY_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.stageApplyAmt = stageApplyAmt;
            if (!this.toUpdateCols.contains("STAGE_APPLY_AMT")) {
                this.toUpdateCols.add("STAGE_APPLY_AMT");
            }
        }
        return this;
    }

    /**
     * 本期支付金额（万）。
     */
    private BigDecimal stagePayAmtTwo;

    /**
     * 获取：本期支付金额（万）。
     */
    public BigDecimal getStagePayAmtTwo() {
        return this.stagePayAmtTwo;
    }

    /**
     * 设置：本期支付金额（万）。
     */
    public PoOrderPaymentReq setStagePayAmtTwo(BigDecimal stagePayAmtTwo) {
        if (this.stagePayAmtTwo == null && stagePayAmtTwo == null) {
            // 均为null，不做处理。
        } else if (this.stagePayAmtTwo != null && stagePayAmtTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.stagePayAmtTwo.compareTo(stagePayAmtTwo) != 0) {
                this.stagePayAmtTwo = stagePayAmtTwo;
                if (!this.toUpdateCols.contains("STAGE_PAY_AMT_TWO")) {
                    this.toUpdateCols.add("STAGE_PAY_AMT_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.stagePayAmtTwo = stagePayAmtTwo;
            if (!this.toUpdateCols.contains("STAGE_PAY_AMT_TWO")) {
                this.toUpdateCols.add("STAGE_PAY_AMT_TWO");
            }
        }
        return this;
    }

    /**
     * 开票金额。
     */
    private BigDecimal invoiceAmt;

    /**
     * 获取：开票金额。
     */
    public BigDecimal getInvoiceAmt() {
        return this.invoiceAmt;
    }

    /**
     * 设置：开票金额。
     */
    public PoOrderPaymentReq setInvoiceAmt(BigDecimal invoiceAmt) {
        if (this.invoiceAmt == null && invoiceAmt == null) {
            // 均为null，不做处理。
        } else if (this.invoiceAmt != null && invoiceAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.invoiceAmt.compareTo(invoiceAmt) != 0) {
                this.invoiceAmt = invoiceAmt;
                if (!this.toUpdateCols.contains("INVOICE_AMT")) {
                    this.toUpdateCols.add("INVOICE_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.invoiceAmt = invoiceAmt;
            if (!this.toUpdateCols.contains("INVOICE_AMT")) {
                this.toUpdateCols.add("INVOICE_AMT");
            }
        }
        return this;
    }

    /**
     * 至本期累计付款。
     */
    private BigDecimal nowSumPay;

    /**
     * 获取：至本期累计付款。
     */
    public BigDecimal getNowSumPay() {
        return this.nowSumPay;
    }

    /**
     * 设置：至本期累计付款。
     */
    public PoOrderPaymentReq setNowSumPay(BigDecimal nowSumPay) {
        if (this.nowSumPay == null && nowSumPay == null) {
            // 均为null，不做处理。
        } else if (this.nowSumPay != null && nowSumPay != null) {
            // 均非null，判定不等，再做处理：
            if (this.nowSumPay.compareTo(nowSumPay) != 0) {
                this.nowSumPay = nowSumPay;
                if (!this.toUpdateCols.contains("NOW_SUM_PAY")) {
                    this.toUpdateCols.add("NOW_SUM_PAY");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.nowSumPay = nowSumPay;
            if (!this.toUpdateCols.contains("NOW_SUM_PAY")) {
                this.toUpdateCols.add("NOW_SUM_PAY");
            }
        }
        return this;
    }

    /**
     * 合同付款占比。
     */
    private BigDecimal contractPercent;

    /**
     * 获取：合同付款占比。
     */
    public BigDecimal getContractPercent() {
        return this.contractPercent;
    }

    /**
     * 设置：合同付款占比。
     */
    public PoOrderPaymentReq setContractPercent(BigDecimal contractPercent) {
        if (this.contractPercent == null && contractPercent == null) {
            // 均为null，不做处理。
        } else if (this.contractPercent != null && contractPercent != null) {
            // 均非null，判定不等，再做处理：
            if (this.contractPercent.compareTo(contractPercent) != 0) {
                this.contractPercent = contractPercent;
                if (!this.toUpdateCols.contains("CONTRACT_PERCENT")) {
                    this.toUpdateCols.add("CONTRACT_PERCENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.contractPercent = contractPercent;
            if (!this.toUpdateCols.contains("CONTRACT_PERCENT")) {
                this.toUpdateCols.add("CONTRACT_PERCENT");
            }
        }
        return this;
    }

    /**
     * 概算付款占比。
     */
    private BigDecimal estimatePercent;

    /**
     * 获取：概算付款占比。
     */
    public BigDecimal getEstimatePercent() {
        return this.estimatePercent;
    }

    /**
     * 设置：概算付款占比。
     */
    public PoOrderPaymentReq setEstimatePercent(BigDecimal estimatePercent) {
        if (this.estimatePercent == null && estimatePercent == null) {
            // 均为null，不做处理。
        } else if (this.estimatePercent != null && estimatePercent != null) {
            // 均非null，判定不等，再做处理：
            if (this.estimatePercent.compareTo(estimatePercent) != 0) {
                this.estimatePercent = estimatePercent;
                if (!this.toUpdateCols.contains("ESTIMATE_PERCENT")) {
                    this.toUpdateCols.add("ESTIMATE_PERCENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.estimatePercent = estimatePercent;
            if (!this.toUpdateCols.contains("ESTIMATE_PERCENT")) {
                this.toUpdateCols.add("ESTIMATE_PERCENT");
            }
        }
        return this;
    }

    /**
     * 财评付款占比。
     */
    private BigDecimal financialPercent;

    /**
     * 获取：财评付款占比。
     */
    public BigDecimal getFinancialPercent() {
        return this.financialPercent;
    }

    /**
     * 设置：财评付款占比。
     */
    public PoOrderPaymentReq setFinancialPercent(BigDecimal financialPercent) {
        if (this.financialPercent == null && financialPercent == null) {
            // 均为null，不做处理。
        } else if (this.financialPercent != null && financialPercent != null) {
            // 均非null，判定不等，再做处理：
            if (this.financialPercent.compareTo(financialPercent) != 0) {
                this.financialPercent = financialPercent;
                if (!this.toUpdateCols.contains("FINANCIAL_PERCENT")) {
                    this.toUpdateCols.add("FINANCIAL_PERCENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financialPercent = financialPercent;
            if (!this.toUpdateCols.contains("FINANCIAL_PERCENT")) {
                this.toUpdateCols.add("FINANCIAL_PERCENT");
            }
        }
        return this;
    }

    /**
     * 结算付款占比。
     */
    private BigDecimal settlementPercent;

    /**
     * 获取：结算付款占比。
     */
    public BigDecimal getSettlementPercent() {
        return this.settlementPercent;
    }

    /**
     * 设置：结算付款占比。
     */
    public PoOrderPaymentReq setSettlementPercent(BigDecimal settlementPercent) {
        if (this.settlementPercent == null && settlementPercent == null) {
            // 均为null，不做处理。
        } else if (this.settlementPercent != null && settlementPercent != null) {
            // 均非null，判定不等，再做处理：
            if (this.settlementPercent.compareTo(settlementPercent) != 0) {
                this.settlementPercent = settlementPercent;
                if (!this.toUpdateCols.contains("SETTLEMENT_PERCENT")) {
                    this.toUpdateCols.add("SETTLEMENT_PERCENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.settlementPercent = settlementPercent;
            if (!this.toUpdateCols.contains("SETTLEMENT_PERCENT")) {
                this.toUpdateCols.add("SETTLEMENT_PERCENT");
            }
        }
        return this;
    }

    /**
     * 收款单位。
     */
    private String collectionDeptTwo;

    /**
     * 获取：收款单位。
     */
    public String getCollectionDeptTwo() {
        return this.collectionDeptTwo;
    }

    /**
     * 设置：收款单位。
     */
    public PoOrderPaymentReq setCollectionDeptTwo(String collectionDeptTwo) {
        if (this.collectionDeptTwo == null && collectionDeptTwo == null) {
            // 均为null，不做处理。
        } else if (this.collectionDeptTwo != null && collectionDeptTwo != null) {
            // 均非null，判定不等，再做处理：
            if (this.collectionDeptTwo.compareTo(collectionDeptTwo) != 0) {
                this.collectionDeptTwo = collectionDeptTwo;
                if (!this.toUpdateCols.contains("COLLECTION_DEPT_TWO")) {
                    this.toUpdateCols.add("COLLECTION_DEPT_TWO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.collectionDeptTwo = collectionDeptTwo;
            if (!this.toUpdateCols.contains("COLLECTION_DEPT_TWO")) {
                this.toUpdateCols.add("COLLECTION_DEPT_TWO");
            }
        }
        return this;
    }

    /**
     * 开户行。
     */
    private String bankOfDeposit;

    /**
     * 获取：开户行。
     */
    public String getBankOfDeposit() {
        return this.bankOfDeposit;
    }

    /**
     * 设置：开户行。
     */
    public PoOrderPaymentReq setBankOfDeposit(String bankOfDeposit) {
        if (this.bankOfDeposit == null && bankOfDeposit == null) {
            // 均为null，不做处理。
        } else if (this.bankOfDeposit != null && bankOfDeposit != null) {
            // 均非null，判定不等，再做处理：
            if (this.bankOfDeposit.compareTo(bankOfDeposit) != 0) {
                this.bankOfDeposit = bankOfDeposit;
                if (!this.toUpdateCols.contains("BANK_OF_DEPOSIT")) {
                    this.toUpdateCols.add("BANK_OF_DEPOSIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.bankOfDeposit = bankOfDeposit;
            if (!this.toUpdateCols.contains("BANK_OF_DEPOSIT")) {
                this.toUpdateCols.add("BANK_OF_DEPOSIT");
            }
        }
        return this;
    }

    /**
     * 账号。
     */
    private String accountNo;

    /**
     * 获取：账号。
     */
    public String getAccountNo() {
        return this.accountNo;
    }

    /**
     * 设置：账号。
     */
    public PoOrderPaymentReq setAccountNo(String accountNo) {
        if (this.accountNo == null && accountNo == null) {
            // 均为null，不做处理。
        } else if (this.accountNo != null && accountNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.accountNo.compareTo(accountNo) != 0) {
                this.accountNo = accountNo;
                if (!this.toUpdateCols.contains("ACCOUNT_NO")) {
                    this.toUpdateCols.add("ACCOUNT_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.accountNo = accountNo;
            if (!this.toUpdateCols.contains("ACCOUNT_NO")) {
                this.toUpdateCols.add("ACCOUNT_NO");
            }
        }
        return this;
    }

    /**
     * 收款单。
     */
    private String receipt;

    /**
     * 获取：收款单。
     */
    public String getReceipt() {
        return this.receipt;
    }

    /**
     * 设置：收款单。
     */
    public PoOrderPaymentReq setReceipt(String receipt) {
        if (this.receipt == null && receipt == null) {
            // 均为null，不做处理。
        } else if (this.receipt != null && receipt != null) {
            // 均非null，判定不等，再做处理：
            if (this.receipt.compareTo(receipt) != 0) {
                this.receipt = receipt;
                if (!this.toUpdateCols.contains("RECEIPT")) {
                    this.toUpdateCols.add("RECEIPT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.receipt = receipt;
            if (!this.toUpdateCols.contains("RECEIPT")) {
                this.toUpdateCols.add("RECEIPT");
            }
        }
        return this;
    }

    /**
     * 专户开户行。
     */
    private String specialBankOfDeposit;

    /**
     * 获取：专户开户行。
     */
    public String getSpecialBankOfDeposit() {
        return this.specialBankOfDeposit;
    }

    /**
     * 设置：专户开户行。
     */
    public PoOrderPaymentReq setSpecialBankOfDeposit(String specialBankOfDeposit) {
        if (this.specialBankOfDeposit == null && specialBankOfDeposit == null) {
            // 均为null，不做处理。
        } else if (this.specialBankOfDeposit != null && specialBankOfDeposit != null) {
            // 均非null，判定不等，再做处理：
            if (this.specialBankOfDeposit.compareTo(specialBankOfDeposit) != 0) {
                this.specialBankOfDeposit = specialBankOfDeposit;
                if (!this.toUpdateCols.contains("SPECIAL_BANK_OF_DEPOSIT")) {
                    this.toUpdateCols.add("SPECIAL_BANK_OF_DEPOSIT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.specialBankOfDeposit = specialBankOfDeposit;
            if (!this.toUpdateCols.contains("SPECIAL_BANK_OF_DEPOSIT")) {
                this.toUpdateCols.add("SPECIAL_BANK_OF_DEPOSIT");
            }
        }
        return this;
    }

    /**
     * 专户账号。
     */
    private String specialAccountNo;

    /**
     * 获取：专户账号。
     */
    public String getSpecialAccountNo() {
        return this.specialAccountNo;
    }

    /**
     * 设置：专户账号。
     */
    public PoOrderPaymentReq setSpecialAccountNo(String specialAccountNo) {
        if (this.specialAccountNo == null && specialAccountNo == null) {
            // 均为null，不做处理。
        } else if (this.specialAccountNo != null && specialAccountNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.specialAccountNo.compareTo(specialAccountNo) != 0) {
                this.specialAccountNo = specialAccountNo;
                if (!this.toUpdateCols.contains("SPECIAL_ACCOUNT_NO")) {
                    this.toUpdateCols.add("SPECIAL_ACCOUNT_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.specialAccountNo = specialAccountNo;
            if (!this.toUpdateCols.contains("SPECIAL_ACCOUNT_NO")) {
                this.toUpdateCols.add("SPECIAL_ACCOUNT_NO");
            }
        }
        return this;
    }

    /**
     * 请款事由。
     */
    private String applyCostReason;

    /**
     * 获取：请款事由。
     */
    public String getApplyCostReason() {
        return this.applyCostReason;
    }

    /**
     * 设置：请款事由。
     */
    public PoOrderPaymentReq setApplyCostReason(String applyCostReason) {
        if (this.applyCostReason == null && applyCostReason == null) {
            // 均为null，不做处理。
        } else if (this.applyCostReason != null && applyCostReason != null) {
            // 均非null，判定不等，再做处理：
            if (this.applyCostReason.compareTo(applyCostReason) != 0) {
                this.applyCostReason = applyCostReason;
                if (!this.toUpdateCols.contains("APPLY_COST_REASON")) {
                    this.toUpdateCols.add("APPLY_COST_REASON");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.applyCostReason = applyCostReason;
            if (!this.toUpdateCols.contains("APPLY_COST_REASON")) {
                this.toUpdateCols.add("APPLY_COST_REASON");
            }
        }
        return this;
    }

    /**
     * 主体材料。
     */
    private String subjectFile;

    /**
     * 获取：主体材料。
     */
    public String getSubjectFile() {
        return this.subjectFile;
    }

    /**
     * 设置：主体材料。
     */
    public PoOrderPaymentReq setSubjectFile(String subjectFile) {
        if (this.subjectFile == null && subjectFile == null) {
            // 均为null，不做处理。
        } else if (this.subjectFile != null && subjectFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.subjectFile.compareTo(subjectFile) != 0) {
                this.subjectFile = subjectFile;
                if (!this.toUpdateCols.contains("SUBJECT_FILE")) {
                    this.toUpdateCols.add("SUBJECT_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.subjectFile = subjectFile;
            if (!this.toUpdateCols.contains("SUBJECT_FILE")) {
                this.toUpdateCols.add("SUBJECT_FILE");
            }
        }
        return this;
    }

    /**
     * 保函名称。
     */
    private String guaranteeId;

    /**
     * 获取：保函名称。
     */
    public String getGuaranteeId() {
        return this.guaranteeId;
    }

    /**
     * 设置：保函名称。
     */
    public PoOrderPaymentReq setGuaranteeId(String guaranteeId) {
        if (this.guaranteeId == null && guaranteeId == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeId != null && guaranteeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeId.compareTo(guaranteeId) != 0) {
                this.guaranteeId = guaranteeId;
                if (!this.toUpdateCols.contains("GUARANTEE_ID")) {
                    this.toUpdateCols.add("GUARANTEE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeId = guaranteeId;
            if (!this.toUpdateCols.contains("GUARANTEE_ID")) {
                this.toUpdateCols.add("GUARANTEE_ID");
            }
        }
        return this;
    }

    /**
     * 保函金额。
     */
    private BigDecimal guaranteeAmt;

    /**
     * 获取：保函金额。
     */
    public BigDecimal getGuaranteeAmt() {
        return this.guaranteeAmt;
    }

    /**
     * 设置：保函金额。
     */
    public PoOrderPaymentReq setGuaranteeAmt(BigDecimal guaranteeAmt) {
        if (this.guaranteeAmt == null && guaranteeAmt == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeAmt != null && guaranteeAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeAmt.compareTo(guaranteeAmt) != 0) {
                this.guaranteeAmt = guaranteeAmt;
                if (!this.toUpdateCols.contains("GUARANTEE_AMT")) {
                    this.toUpdateCols.add("GUARANTEE_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeAmt = guaranteeAmt;
            if (!this.toUpdateCols.contains("GUARANTEE_AMT")) {
                this.toUpdateCols.add("GUARANTEE_AMT");
            }
        }
        return this;
    }

    /**
     * 保函名称。
     */
    private String guaranteeName;

    /**
     * 获取：保函名称。
     */
    public String getGuaranteeName() {
        return this.guaranteeName;
    }

    /**
     * 设置：保函名称。
     */
    public PoOrderPaymentReq setGuaranteeName(String guaranteeName) {
        if (this.guaranteeName == null && guaranteeName == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeName != null && guaranteeName != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeName.compareTo(guaranteeName) != 0) {
                this.guaranteeName = guaranteeName;
                if (!this.toUpdateCols.contains("GUARANTEE_NAME")) {
                    this.toUpdateCols.add("GUARANTEE_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeName = guaranteeName;
            if (!this.toUpdateCols.contains("GUARANTEE_NAME")) {
                this.toUpdateCols.add("GUARANTEE_NAME");
            }
        }
        return this;
    }

    /**
     * 保函结束日期。
     */
    private LocalDate guaranteeEndDate;

    /**
     * 获取：保函结束日期。
     */
    public LocalDate getGuaranteeEndDate() {
        return this.guaranteeEndDate;
    }

    /**
     * 设置：保函结束日期。
     */
    public PoOrderPaymentReq setGuaranteeEndDate(LocalDate guaranteeEndDate) {
        if (this.guaranteeEndDate == null && guaranteeEndDate == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeEndDate != null && guaranteeEndDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeEndDate.compareTo(guaranteeEndDate) != 0) {
                this.guaranteeEndDate = guaranteeEndDate;
                if (!this.toUpdateCols.contains("GUARANTEE_END_DATE")) {
                    this.toUpdateCols.add("GUARANTEE_END_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeEndDate = guaranteeEndDate;
            if (!this.toUpdateCols.contains("GUARANTEE_END_DATE")) {
                this.toUpdateCols.add("GUARANTEE_END_DATE");
            }
        }
        return this;
    }

    /**
     * 保函结果材料。
     */
    private String guaranteeResultFile;

    /**
     * 获取：保函结果材料。
     */
    public String getGuaranteeResultFile() {
        return this.guaranteeResultFile;
    }

    /**
     * 设置：保函结果材料。
     */
    public PoOrderPaymentReq setGuaranteeResultFile(String guaranteeResultFile) {
        if (this.guaranteeResultFile == null && guaranteeResultFile == null) {
            // 均为null，不做处理。
        } else if (this.guaranteeResultFile != null && guaranteeResultFile != null) {
            // 均非null，判定不等，再做处理：
            if (this.guaranteeResultFile.compareTo(guaranteeResultFile) != 0) {
                this.guaranteeResultFile = guaranteeResultFile;
                if (!this.toUpdateCols.contains("GUARANTEE_RESULT_FILE")) {
                    this.toUpdateCols.add("GUARANTEE_RESULT_FILE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.guaranteeResultFile = guaranteeResultFile;
            if (!this.toUpdateCols.contains("GUARANTEE_RESULT_FILE")) {
                this.toUpdateCols.add("GUARANTEE_RESULT_FILE");
            }
        }
        return this;
    }

    /**
     * 意见发表人。
     */
    private String commentPublishUser;

    /**
     * 获取：意见发表人。
     */
    public String getCommentPublishUser() {
        return this.commentPublishUser;
    }

    /**
     * 设置：意见发表人。
     */
    public PoOrderPaymentReq setCommentPublishUser(String commentPublishUser) {
        if (this.commentPublishUser == null && commentPublishUser == null) {
            // 均为null，不做处理。
        } else if (this.commentPublishUser != null && commentPublishUser != null) {
            // 均非null，判定不等，再做处理：
            if (this.commentPublishUser.compareTo(commentPublishUser) != 0) {
                this.commentPublishUser = commentPublishUser;
                if (!this.toUpdateCols.contains("COMMENT_PUBLISH_USER")) {
                    this.toUpdateCols.add("COMMENT_PUBLISH_USER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.commentPublishUser = commentPublishUser;
            if (!this.toUpdateCols.contains("COMMENT_PUBLISH_USER")) {
                this.toUpdateCols.add("COMMENT_PUBLISH_USER");
            }
        }
        return this;
    }

    /**
     * 意见发表日期。
     */
    private LocalDate commentPublishDate;

    /**
     * 获取：意见发表日期。
     */
    public LocalDate getCommentPublishDate() {
        return this.commentPublishDate;
    }

    /**
     * 设置：意见发表日期。
     */
    public PoOrderPaymentReq setCommentPublishDate(LocalDate commentPublishDate) {
        if (this.commentPublishDate == null && commentPublishDate == null) {
            // 均为null，不做处理。
        } else if (this.commentPublishDate != null && commentPublishDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.commentPublishDate.compareTo(commentPublishDate) != 0) {
                this.commentPublishDate = commentPublishDate;
                if (!this.toUpdateCols.contains("COMMENT_PUBLISH_DATE")) {
                    this.toUpdateCols.add("COMMENT_PUBLISH_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.commentPublishDate = commentPublishDate;
            if (!this.toUpdateCols.contains("COMMENT_PUBLISH_DATE")) {
                this.toUpdateCols.add("COMMENT_PUBLISH_DATE");
            }
        }
        return this;
    }

    /**
     * 意见内容。
     */
    private String commentPublishContent;

    /**
     * 获取：意见内容。
     */
    public String getCommentPublishContent() {
        return this.commentPublishContent;
    }

    /**
     * 设置：意见内容。
     */
    public PoOrderPaymentReq setCommentPublishContent(String commentPublishContent) {
        if (this.commentPublishContent == null && commentPublishContent == null) {
            // 均为null，不做处理。
        } else if (this.commentPublishContent != null && commentPublishContent != null) {
            // 均非null，判定不等，再做处理：
            if (this.commentPublishContent.compareTo(commentPublishContent) != 0) {
                this.commentPublishContent = commentPublishContent;
                if (!this.toUpdateCols.contains("COMMENT_PUBLISH_CONTENT")) {
                    this.toUpdateCols.add("COMMENT_PUBLISH_CONTENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.commentPublishContent = commentPublishContent;
            if (!this.toUpdateCols.contains("COMMENT_PUBLISH_CONTENT")) {
                this.toUpdateCols.add("COMMENT_PUBLISH_CONTENT");
            }
        }
        return this;
    }

    /**
     * 部门意见发表人。
     */
    private String deptCommentPublishUser;

    /**
     * 获取：部门意见发表人。
     */
    public String getDeptCommentPublishUser() {
        return this.deptCommentPublishUser;
    }

    /**
     * 设置：部门意见发表人。
     */
    public PoOrderPaymentReq setDeptCommentPublishUser(String deptCommentPublishUser) {
        if (this.deptCommentPublishUser == null && deptCommentPublishUser == null) {
            // 均为null，不做处理。
        } else if (this.deptCommentPublishUser != null && deptCommentPublishUser != null) {
            // 均非null，判定不等，再做处理：
            if (this.deptCommentPublishUser.compareTo(deptCommentPublishUser) != 0) {
                this.deptCommentPublishUser = deptCommentPublishUser;
                if (!this.toUpdateCols.contains("DEPT_COMMENT_PUBLISH_USER")) {
                    this.toUpdateCols.add("DEPT_COMMENT_PUBLISH_USER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.deptCommentPublishUser = deptCommentPublishUser;
            if (!this.toUpdateCols.contains("DEPT_COMMENT_PUBLISH_USER")) {
                this.toUpdateCols.add("DEPT_COMMENT_PUBLISH_USER");
            }
        }
        return this;
    }

    /**
     * 部门意见发表日期。
     */
    private LocalDate deptCommentPublishDate;

    /**
     * 获取：部门意见发表日期。
     */
    public LocalDate getDeptCommentPublishDate() {
        return this.deptCommentPublishDate;
    }

    /**
     * 设置：部门意见发表日期。
     */
    public PoOrderPaymentReq setDeptCommentPublishDate(LocalDate deptCommentPublishDate) {
        if (this.deptCommentPublishDate == null && deptCommentPublishDate == null) {
            // 均为null，不做处理。
        } else if (this.deptCommentPublishDate != null && deptCommentPublishDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.deptCommentPublishDate.compareTo(deptCommentPublishDate) != 0) {
                this.deptCommentPublishDate = deptCommentPublishDate;
                if (!this.toUpdateCols.contains("DEPT_COMMENT_PUBLISH_DATE")) {
                    this.toUpdateCols.add("DEPT_COMMENT_PUBLISH_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.deptCommentPublishDate = deptCommentPublishDate;
            if (!this.toUpdateCols.contains("DEPT_COMMENT_PUBLISH_DATE")) {
                this.toUpdateCols.add("DEPT_COMMENT_PUBLISH_DATE");
            }
        }
        return this;
    }

    /**
     * 部门意见。
     */
    private String deptMessage;

    /**
     * 获取：部门意见。
     */
    public String getDeptMessage() {
        return this.deptMessage;
    }

    /**
     * 设置：部门意见。
     */
    public PoOrderPaymentReq setDeptMessage(String deptMessage) {
        if (this.deptMessage == null && deptMessage == null) {
            // 均为null，不做处理。
        } else if (this.deptMessage != null && deptMessage != null) {
            // 均非null，判定不等，再做处理：
            if (this.deptMessage.compareTo(deptMessage) != 0) {
                this.deptMessage = deptMessage;
                if (!this.toUpdateCols.contains("DEPT_MESSAGE")) {
                    this.toUpdateCols.add("DEPT_MESSAGE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.deptMessage = deptMessage;
            if (!this.toUpdateCols.contains("DEPT_MESSAGE")) {
                this.toUpdateCols.add("DEPT_MESSAGE");
            }
        }
        return this;
    }

    /**
     * 财务意见发表人。
     */
    private String financePublishUser;

    /**
     * 获取：财务意见发表人。
     */
    public String getFinancePublishUser() {
        return this.financePublishUser;
    }

    /**
     * 设置：财务意见发表人。
     */
    public PoOrderPaymentReq setFinancePublishUser(String financePublishUser) {
        if (this.financePublishUser == null && financePublishUser == null) {
            // 均为null，不做处理。
        } else if (this.financePublishUser != null && financePublishUser != null) {
            // 均非null，判定不等，再做处理：
            if (this.financePublishUser.compareTo(financePublishUser) != 0) {
                this.financePublishUser = financePublishUser;
                if (!this.toUpdateCols.contains("FINANCE_PUBLISH_USER")) {
                    this.toUpdateCols.add("FINANCE_PUBLISH_USER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financePublishUser = financePublishUser;
            if (!this.toUpdateCols.contains("FINANCE_PUBLISH_USER")) {
                this.toUpdateCols.add("FINANCE_PUBLISH_USER");
            }
        }
        return this;
    }

    /**
     * 财务意见发表日期。
     */
    private LocalDate financePublishDate;

    /**
     * 获取：财务意见发表日期。
     */
    public LocalDate getFinancePublishDate() {
        return this.financePublishDate;
    }

    /**
     * 设置：财务意见发表日期。
     */
    public PoOrderPaymentReq setFinancePublishDate(LocalDate financePublishDate) {
        if (this.financePublishDate == null && financePublishDate == null) {
            // 均为null，不做处理。
        } else if (this.financePublishDate != null && financePublishDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.financePublishDate.compareTo(financePublishDate) != 0) {
                this.financePublishDate = financePublishDate;
                if (!this.toUpdateCols.contains("FINANCE_PUBLISH_DATE")) {
                    this.toUpdateCols.add("FINANCE_PUBLISH_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financePublishDate = financePublishDate;
            if (!this.toUpdateCols.contains("FINANCE_PUBLISH_DATE")) {
                this.toUpdateCols.add("FINANCE_PUBLISH_DATE");
            }
        }
        return this;
    }

    /**
     * 财务意见。
     */
    private String financeMessage;

    /**
     * 获取：财务意见。
     */
    public String getFinanceMessage() {
        return this.financeMessage;
    }

    /**
     * 设置：财务意见。
     */
    public PoOrderPaymentReq setFinanceMessage(String financeMessage) {
        if (this.financeMessage == null && financeMessage == null) {
            // 均为null，不做处理。
        } else if (this.financeMessage != null && financeMessage != null) {
            // 均非null，判定不等，再做处理：
            if (this.financeMessage.compareTo(financeMessage) != 0) {
                this.financeMessage = financeMessage;
                if (!this.toUpdateCols.contains("FINANCE_MESSAGE")) {
                    this.toUpdateCols.add("FINANCE_MESSAGE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financeMessage = financeMessage;
            if (!this.toUpdateCols.contains("FINANCE_MESSAGE")) {
                this.toUpdateCols.add("FINANCE_MESSAGE");
            }
        }
        return this;
    }

    /**
     * 领导意见发表人。
     */
    private String leaderCommentPublishUser;

    /**
     * 获取：领导意见发表人。
     */
    public String getLeaderCommentPublishUser() {
        return this.leaderCommentPublishUser;
    }

    /**
     * 设置：领导意见发表人。
     */
    public PoOrderPaymentReq setLeaderCommentPublishUser(String leaderCommentPublishUser) {
        if (this.leaderCommentPublishUser == null && leaderCommentPublishUser == null) {
            // 均为null，不做处理。
        } else if (this.leaderCommentPublishUser != null && leaderCommentPublishUser != null) {
            // 均非null，判定不等，再做处理：
            if (this.leaderCommentPublishUser.compareTo(leaderCommentPublishUser) != 0) {
                this.leaderCommentPublishUser = leaderCommentPublishUser;
                if (!this.toUpdateCols.contains("LEADER_COMMENT_PUBLISH_USER")) {
                    this.toUpdateCols.add("LEADER_COMMENT_PUBLISH_USER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.leaderCommentPublishUser = leaderCommentPublishUser;
            if (!this.toUpdateCols.contains("LEADER_COMMENT_PUBLISH_USER")) {
                this.toUpdateCols.add("LEADER_COMMENT_PUBLISH_USER");
            }
        }
        return this;
    }

    /**
     * 领导意见发表日期。
     */
    private LocalDate leaderCommentPublishDate;

    /**
     * 获取：领导意见发表日期。
     */
    public LocalDate getLeaderCommentPublishDate() {
        return this.leaderCommentPublishDate;
    }

    /**
     * 设置：领导意见发表日期。
     */
    public PoOrderPaymentReq setLeaderCommentPublishDate(LocalDate leaderCommentPublishDate) {
        if (this.leaderCommentPublishDate == null && leaderCommentPublishDate == null) {
            // 均为null，不做处理。
        } else if (this.leaderCommentPublishDate != null && leaderCommentPublishDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.leaderCommentPublishDate.compareTo(leaderCommentPublishDate) != 0) {
                this.leaderCommentPublishDate = leaderCommentPublishDate;
                if (!this.toUpdateCols.contains("LEADER_COMMENT_PUBLISH_DATE")) {
                    this.toUpdateCols.add("LEADER_COMMENT_PUBLISH_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.leaderCommentPublishDate = leaderCommentPublishDate;
            if (!this.toUpdateCols.contains("LEADER_COMMENT_PUBLISH_DATE")) {
                this.toUpdateCols.add("LEADER_COMMENT_PUBLISH_DATE");
            }
        }
        return this;
    }

    /**
     * 领导意见。
     */
    private String leaderMessage;

    /**
     * 获取：领导意见。
     */
    public String getLeaderMessage() {
        return this.leaderMessage;
    }

    /**
     * 设置：领导意见。
     */
    public PoOrderPaymentReq setLeaderMessage(String leaderMessage) {
        if (this.leaderMessage == null && leaderMessage == null) {
            // 均为null，不做处理。
        } else if (this.leaderMessage != null && leaderMessage != null) {
            // 均非null，判定不等，再做处理：
            if (this.leaderMessage.compareTo(leaderMessage) != 0) {
                this.leaderMessage = leaderMessage;
                if (!this.toUpdateCols.contains("LEADER_MESSAGE")) {
                    this.toUpdateCols.add("LEADER_MESSAGE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.leaderMessage = leaderMessage;
            if (!this.toUpdateCols.contains("LEADER_MESSAGE")) {
                this.toUpdateCols.add("LEADER_MESSAGE");
            }
        }
        return this;
    }

    /**
     * 财务领导意见发表人。
     */
    private String financeLeaderPublishUser;

    /**
     * 获取：财务领导意见发表人。
     */
    public String getFinanceLeaderPublishUser() {
        return this.financeLeaderPublishUser;
    }

    /**
     * 设置：财务领导意见发表人。
     */
    public PoOrderPaymentReq setFinanceLeaderPublishUser(String financeLeaderPublishUser) {
        if (this.financeLeaderPublishUser == null && financeLeaderPublishUser == null) {
            // 均为null，不做处理。
        } else if (this.financeLeaderPublishUser != null && financeLeaderPublishUser != null) {
            // 均非null，判定不等，再做处理：
            if (this.financeLeaderPublishUser.compareTo(financeLeaderPublishUser) != 0) {
                this.financeLeaderPublishUser = financeLeaderPublishUser;
                if (!this.toUpdateCols.contains("FINANCE_LEADER_PUBLISH_USER")) {
                    this.toUpdateCols.add("FINANCE_LEADER_PUBLISH_USER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financeLeaderPublishUser = financeLeaderPublishUser;
            if (!this.toUpdateCols.contains("FINANCE_LEADER_PUBLISH_USER")) {
                this.toUpdateCols.add("FINANCE_LEADER_PUBLISH_USER");
            }
        }
        return this;
    }

    /**
     * 财务领导意见发表日期。
     */
    private LocalDate financeLeaderPublishDate;

    /**
     * 获取：财务领导意见发表日期。
     */
    public LocalDate getFinanceLeaderPublishDate() {
        return this.financeLeaderPublishDate;
    }

    /**
     * 设置：财务领导意见发表日期。
     */
    public PoOrderPaymentReq setFinanceLeaderPublishDate(LocalDate financeLeaderPublishDate) {
        if (this.financeLeaderPublishDate == null && financeLeaderPublishDate == null) {
            // 均为null，不做处理。
        } else if (this.financeLeaderPublishDate != null && financeLeaderPublishDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.financeLeaderPublishDate.compareTo(financeLeaderPublishDate) != 0) {
                this.financeLeaderPublishDate = financeLeaderPublishDate;
                if (!this.toUpdateCols.contains("FINANCE_LEADER_PUBLISH_DATE")) {
                    this.toUpdateCols.add("FINANCE_LEADER_PUBLISH_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financeLeaderPublishDate = financeLeaderPublishDate;
            if (!this.toUpdateCols.contains("FINANCE_LEADER_PUBLISH_DATE")) {
                this.toUpdateCols.add("FINANCE_LEADER_PUBLISH_DATE");
            }
        }
        return this;
    }

    /**
     * 财务领导意见。
     */
    private String financeLeaderMessage;

    /**
     * 获取：财务领导意见。
     */
    public String getFinanceLeaderMessage() {
        return this.financeLeaderMessage;
    }

    /**
     * 设置：财务领导意见。
     */
    public PoOrderPaymentReq setFinanceLeaderMessage(String financeLeaderMessage) {
        if (this.financeLeaderMessage == null && financeLeaderMessage == null) {
            // 均为null，不做处理。
        } else if (this.financeLeaderMessage != null && financeLeaderMessage != null) {
            // 均非null，判定不等，再做处理：
            if (this.financeLeaderMessage.compareTo(financeLeaderMessage) != 0) {
                this.financeLeaderMessage = financeLeaderMessage;
                if (!this.toUpdateCols.contains("FINANCE_LEADER_MESSAGE")) {
                    this.toUpdateCols.add("FINANCE_LEADER_MESSAGE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.financeLeaderMessage = financeLeaderMessage;
            if (!this.toUpdateCols.contains("FINANCE_LEADER_MESSAGE")) {
                this.toUpdateCols.add("FINANCE_LEADER_MESSAGE");
            }
        }
        return this;
    }

    /**
     * 总经理。
     */
    private String president;

    /**
     * 获取：总经理。
     */
    public String getPresident() {
        return this.president;
    }

    /**
     * 设置：总经理。
     */
    public PoOrderPaymentReq setPresident(String president) {
        if (this.president == null && president == null) {
            // 均为null，不做处理。
        } else if (this.president != null && president != null) {
            // 均非null，判定不等，再做处理：
            if (this.president.compareTo(president) != 0) {
                this.president = president;
                if (!this.toUpdateCols.contains("PRESIDENT")) {
                    this.toUpdateCols.add("PRESIDENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.president = president;
            if (!this.toUpdateCols.contains("PRESIDENT")) {
                this.toUpdateCols.add("PRESIDENT");
            }
        }
        return this;
    }

    /**
     * 总经理意见发表日期。
     */
    private LocalDate presidentDate;

    /**
     * 获取：总经理意见发表日期。
     */
    public LocalDate getPresidentDate() {
        return this.presidentDate;
    }

    /**
     * 设置：总经理意见发表日期。
     */
    public PoOrderPaymentReq setPresidentDate(LocalDate presidentDate) {
        if (this.presidentDate == null && presidentDate == null) {
            // 均为null，不做处理。
        } else if (this.presidentDate != null && presidentDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.presidentDate.compareTo(presidentDate) != 0) {
                this.presidentDate = presidentDate;
                if (!this.toUpdateCols.contains("PRESIDENT_DATE")) {
                    this.toUpdateCols.add("PRESIDENT_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.presidentDate = presidentDate;
            if (!this.toUpdateCols.contains("PRESIDENT_DATE")) {
                this.toUpdateCols.add("PRESIDENT_DATE");
            }
        }
        return this;
    }

    /**
     * 总经理意见。
     */
    private String presidentMessage;

    /**
     * 获取：总经理意见。
     */
    public String getPresidentMessage() {
        return this.presidentMessage;
    }

    /**
     * 设置：总经理意见。
     */
    public PoOrderPaymentReq setPresidentMessage(String presidentMessage) {
        if (this.presidentMessage == null && presidentMessage == null) {
            // 均为null，不做处理。
        } else if (this.presidentMessage != null && presidentMessage != null) {
            // 均非null，判定不等，再做处理：
            if (this.presidentMessage.compareTo(presidentMessage) != 0) {
                this.presidentMessage = presidentMessage;
                if (!this.toUpdateCols.contains("PRESIDENT_MESSAGE")) {
                    this.toUpdateCols.add("PRESIDENT_MESSAGE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.presidentMessage = presidentMessage;
            if (!this.toUpdateCols.contains("PRESIDENT_MESSAGE")) {
                this.toUpdateCols.add("PRESIDENT_MESSAGE");
            }
        }
        return this;
    }

    /**
     * 董事长。
     */
    private String chairman;

    /**
     * 获取：董事长。
     */
    public String getChairman() {
        return this.chairman;
    }

    /**
     * 设置：董事长。
     */
    public PoOrderPaymentReq setChairman(String chairman) {
        if (this.chairman == null && chairman == null) {
            // 均为null，不做处理。
        } else if (this.chairman != null && chairman != null) {
            // 均非null，判定不等，再做处理：
            if (this.chairman.compareTo(chairman) != 0) {
                this.chairman = chairman;
                if (!this.toUpdateCols.contains("CHAIRMAN")) {
                    this.toUpdateCols.add("CHAIRMAN");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.chairman = chairman;
            if (!this.toUpdateCols.contains("CHAIRMAN")) {
                this.toUpdateCols.add("CHAIRMAN");
            }
        }
        return this;
    }

    /**
     * 董事长意见发表日期。
     */
    private LocalDate chairmanDate;

    /**
     * 获取：董事长意见发表日期。
     */
    public LocalDate getChairmanDate() {
        return this.chairmanDate;
    }

    /**
     * 设置：董事长意见发表日期。
     */
    public PoOrderPaymentReq setChairmanDate(LocalDate chairmanDate) {
        if (this.chairmanDate == null && chairmanDate == null) {
            // 均为null，不做处理。
        } else if (this.chairmanDate != null && chairmanDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.chairmanDate.compareTo(chairmanDate) != 0) {
                this.chairmanDate = chairmanDate;
                if (!this.toUpdateCols.contains("CHAIRMAN_DATE")) {
                    this.toUpdateCols.add("CHAIRMAN_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.chairmanDate = chairmanDate;
            if (!this.toUpdateCols.contains("CHAIRMAN_DATE")) {
                this.toUpdateCols.add("CHAIRMAN_DATE");
            }
        }
        return this;
    }

    /**
     * 董事长意见。
     */
    private String chairmanMessage;

    /**
     * 获取：董事长意见。
     */
    public String getChairmanMessage() {
        return this.chairmanMessage;
    }

    /**
     * 设置：董事长意见。
     */
    public PoOrderPaymentReq setChairmanMessage(String chairmanMessage) {
        if (this.chairmanMessage == null && chairmanMessage == null) {
            // 均为null，不做处理。
        } else if (this.chairmanMessage != null && chairmanMessage != null) {
            // 均非null，判定不等，再做处理：
            if (this.chairmanMessage.compareTo(chairmanMessage) != 0) {
                this.chairmanMessage = chairmanMessage;
                if (!this.toUpdateCols.contains("CHAIRMAN_MESSAGE")) {
                    this.toUpdateCols.add("CHAIRMAN_MESSAGE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.chairmanMessage = chairmanMessage;
            if (!this.toUpdateCols.contains("CHAIRMAN_MESSAGE")) {
                this.toUpdateCols.add("CHAIRMAN_MESSAGE");
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
    public static PoOrderPaymentReq newData() {
        PoOrderPaymentReq obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PoOrderPaymentReq insertData() {
        PoOrderPaymentReq obj = modelHelper.insertData();
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
    public static PoOrderPaymentReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PoOrderPaymentReq obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<PoOrderPaymentReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PoOrderPaymentReq> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<PoOrderPaymentReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PoOrderPaymentReq> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(PoOrderPaymentReq fromModel, PoOrderPaymentReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}