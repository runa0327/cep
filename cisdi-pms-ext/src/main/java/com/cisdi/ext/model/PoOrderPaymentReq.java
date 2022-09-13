package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
         * 资金需求计划。
         */
        public static final String RELATION_AMOUT_PLAN_REQ_ID = "RELATION_AMOUT_PLAN_REQ_ID";
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
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 付款依据。
         */
        public static final String PAY_BASIS_ID = "PAY_BASIS_ID";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 支付金额。
         */
        public static final String PAY_AMT = "PAY_AMT";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 附件。
         */
        public static final String ATT_FILE_GROUP_ID = "ATT_FILE_GROUP_ID";
        /**
         * 合同。
         */
        public static final String CONTRACT_ID = "CONTRACT_ID";
        /**
         * 合同金额。
         */
        public static final String CONTRACT_PRICE = "CONTRACT_PRICE";
        /**
         * 合同科目。
         */
        public static final String CONTRACT_SUBJECT_ONE = "CONTRACT_SUBJECT_ONE";
        /**
         * 付款金额。
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
         * 当前期数。
         */
        public static final String NOW_STAGE_ID = "NOW_STAGE_ID";
        /**
         * 本期支付金额。
         */
        public static final String STAGE_PAY_AMT = "STAGE_PAY_AMT";
        /**
         * 收款单位。
         */
        public static final String COLLECTION_DEPT = "COLLECTION_DEPT";
        /**
         * 部门。
         */
        public static final String DEPT_NAME = "DEPT_NAME";
        /**
         * 概算金额。
         */
        public static final String ESTIMATE_AMT = "ESTIMATE_AMT";
        /**
         * 财评金额。
         */
        public static final String FINANCIAL_AMT = "FINANCIAL_AMT";
        /**
         * 结算金额。
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
         * 至上期累计付款。
         */
        public static final String LAST_SUM_PAY = "LAST_SUM_PAY";
        /**
         * 本期申请金额。
         */
        public static final String STAGE_APPLY_AMT = "STAGE_APPLY_AMT";
        /**
         * 本期支付金额。
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
    public String id;

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
        this.id = id;
        return this;
    }

    /**
     * 版本。
     */
    public Integer ver;

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
        this.ver = ver;
        return this;
    }

    /**
     * 时间戳。
     */
    public LocalDateTime ts;

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
        this.ts = ts;
        return this;
    }

    /**
     * 是否预设。
     */
    public Boolean isPreset;

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
        this.isPreset = isPreset;
        return this;
    }

    /**
     * 最后修改日期时间。
     */
    public LocalDateTime lastModiDt;

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
        this.lastModiDt = lastModiDt;
        return this;
    }

    /**
     * 最后修改用户。
     */
    public String lastModiUserId;

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
        this.lastModiUserId = lastModiUserId;
        return this;
    }

    /**
     * 代码。
     */
    public String code;

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
        this.code = code;
        return this;
    }

    /**
     * 名称。
     */
    public String name;

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
        this.name = name;
        return this;
    }

    /**
     * 备注。
     */
    public String remark;

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
        this.remark = remark;
        return this;
    }

    /**
     * 资金需求计划。
     */
    public String relationAmoutPrjId;

    /**
     * 获取：资金需求计划。
     */
    public String getRelationAmoutPrjId() {
        return this.relationAmoutPrjId;
    }

    /**
     * 设置：资金需求计划。
     */
    public PoOrderPaymentReq setRelationAmoutPrjId(String relationAmoutPrjId) {
        this.relationAmoutPrjId = relationAmoutPrjId;
        return this;
    }

    /**
     * 锁定流程实例。
     */
    public String lkWfInstId;

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
        this.lkWfInstId = lkWfInstId;
        return this;
    }

    /**
     * 资金需求项目名称。
     */
    public String amoutPmPrjId;

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
        this.amoutPmPrjId = amoutPmPrjId;
        return this;
    }

    /**
     * 记录状态。
     */
    public String status;

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
        this.status = status;
        return this;
    }

    /**
     * 创建用户。
     */
    public String crtUserId;

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
        this.crtUserId = crtUserId;
        return this;
    }

    /**
     * 付款依据。
     */
    public String payBasisId;

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
        this.payBasisId = payBasisId;
        return this;
    }

    /**
     * 创建部门。
     */
    public String crtDeptId;

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
        this.crtDeptId = crtDeptId;
        return this;
    }

    /**
     * 支付金额。
     */
    public Double payAmt;

    /**
     * 获取：支付金额。
     */
    public Double getPayAmt() {
        return this.payAmt;
    }

    /**
     * 设置：支付金额。
     */
    public PoOrderPaymentReq setPayAmt(Double payAmt) {
        this.payAmt = payAmt;
        return this;
    }

    /**
     * 创建日期时间。
     */
    public LocalDateTime crtDt;

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
        this.crtDt = crtDt;
        return this;
    }

    /**
     * 附件。
     */
    public String attFileGroupId;

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
        this.attFileGroupId = attFileGroupId;
        return this;
    }

    /**
     * 合同。
     */
    public String contractId;

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
        this.contractId = contractId;
        return this;
    }

    /**
     * 合同金额。
     */
    public String contractPrice;

    /**
     * 获取：合同金额。
     */
    public String getContractPrice() {
        return this.contractPrice;
    }

    /**
     * 设置：合同金额。
     */
    public PoOrderPaymentReq setContractPrice(String contractPrice) {
        this.contractPrice = contractPrice;
        return this;
    }

    /**
     * 合同科目。
     */
    public String contractSubjectOne;

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
        this.contractSubjectOne = contractSubjectOne;
        return this;
    }

    /**
     * 付款金额。
     */
    public Double payAmtOne;

    /**
     * 获取：付款金额。
     */
    public Double getPayAmtOne() {
        return this.payAmtOne;
    }

    /**
     * 设置：付款金额。
     */
    public PoOrderPaymentReq setPayAmtOne(Double payAmtOne) {
        this.payAmtOne = payAmtOne;
        return this;
    }

    /**
     * 合同科目。
     */
    public String contractSubjectTwo;

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
        this.contractSubjectTwo = contractSubjectTwo;
        return this;
    }

    /**
     * 付款金额。
     */
    public Double payAmtTwo;

    /**
     * 获取：付款金额。
     */
    public Double getPayAmtTwo() {
        return this.payAmtTwo;
    }

    /**
     * 设置：付款金额。
     */
    public PoOrderPaymentReq setPayAmtTwo(Double payAmtTwo) {
        this.payAmtTwo = payAmtTwo;
        return this;
    }

    /**
     * 费用大类。
     */
    public String costCategoryId;

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
        this.costCategoryId = costCategoryId;
        return this;
    }

    /**
     * 当前期数。
     */
    public String nowStageId;

    /**
     * 获取：当前期数。
     */
    public String getNowStageId() {
        return this.nowStageId;
    }

    /**
     * 设置：当前期数。
     */
    public PoOrderPaymentReq setNowStageId(String nowStageId) {
        this.nowStageId = nowStageId;
        return this;
    }

    /**
     * 本期支付金额。
     */
    public Double stagePayAmt;

    /**
     * 获取：本期支付金额。
     */
    public Double getStagePayAmt() {
        return this.stagePayAmt;
    }

    /**
     * 设置：本期支付金额。
     */
    public PoOrderPaymentReq setStagePayAmt(Double stagePayAmt) {
        this.stagePayAmt = stagePayAmt;
        return this;
    }

    /**
     * 收款单位。
     */
    public String collectionDept;

    /**
     * 获取：收款单位。
     */
    public String getCollectionDept() {
        return this.collectionDept;
    }

    /**
     * 设置：收款单位。
     */
    public PoOrderPaymentReq setCollectionDept(String collectionDept) {
        this.collectionDept = collectionDept;
        return this;
    }

    /**
     * 部门。
     */
    public String deptName;

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
        this.deptName = deptName;
        return this;
    }

    /**
     * 概算金额。
     */
    public Double estimateAmt;

    /**
     * 获取：概算金额。
     */
    public Double getEstimateAmt() {
        return this.estimateAmt;
    }

    /**
     * 设置：概算金额。
     */
    public PoOrderPaymentReq setEstimateAmt(Double estimateAmt) {
        this.estimateAmt = estimateAmt;
        return this;
    }

    /**
     * 财评金额。
     */
    public Double financialAmt;

    /**
     * 获取：财评金额。
     */
    public Double getFinancialAmt() {
        return this.financialAmt;
    }

    /**
     * 设置：财评金额。
     */
    public PoOrderPaymentReq setFinancialAmt(Double financialAmt) {
        this.financialAmt = financialAmt;
        return this;
    }

    /**
     * 结算金额。
     */
    public Double settlementAmt;

    /**
     * 获取：结算金额。
     */
    public Double getSettlementAmt() {
        return this.settlementAmt;
    }

    /**
     * 设置：结算金额。
     */
    public PoOrderPaymentReq setSettlementAmt(Double settlementAmt) {
        this.settlementAmt = settlementAmt;
        return this;
    }

    /**
     * 费用类型。
     */
    public String costTypeId;

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
        this.costTypeId = costTypeId;
        return this;
    }

    /**
     * 费用名称。
     */
    public String costName;

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
        this.costName = costName;
        return this;
    }

    /**
     * 至上期累计付款。
     */
    public Double lastSumPay;

    /**
     * 获取：至上期累计付款。
     */
    public Double getLastSumPay() {
        return this.lastSumPay;
    }

    /**
     * 设置：至上期累计付款。
     */
    public PoOrderPaymentReq setLastSumPay(Double lastSumPay) {
        this.lastSumPay = lastSumPay;
        return this;
    }

    /**
     * 本期申请金额。
     */
    public Double stageApplyAmt;

    /**
     * 获取：本期申请金额。
     */
    public Double getStageApplyAmt() {
        return this.stageApplyAmt;
    }

    /**
     * 设置：本期申请金额。
     */
    public PoOrderPaymentReq setStageApplyAmt(Double stageApplyAmt) {
        this.stageApplyAmt = stageApplyAmt;
        return this;
    }

    /**
     * 本期支付金额。
     */
    public Double stagePayAmtTwo;

    /**
     * 获取：本期支付金额。
     */
    public Double getStagePayAmtTwo() {
        return this.stagePayAmtTwo;
    }

    /**
     * 设置：本期支付金额。
     */
    public PoOrderPaymentReq setStagePayAmtTwo(Double stagePayAmtTwo) {
        this.stagePayAmtTwo = stagePayAmtTwo;
        return this;
    }

    /**
     * 开票金额。
     */
    public Double invoiceAmt;

    /**
     * 获取：开票金额。
     */
    public Double getInvoiceAmt() {
        return this.invoiceAmt;
    }

    /**
     * 设置：开票金额。
     */
    public PoOrderPaymentReq setInvoiceAmt(Double invoiceAmt) {
        this.invoiceAmt = invoiceAmt;
        return this;
    }

    /**
     * 至本期累计付款。
     */
    public Double nowSumPay;

    /**
     * 获取：至本期累计付款。
     */
    public Double getNowSumPay() {
        return this.nowSumPay;
    }

    /**
     * 设置：至本期累计付款。
     */
    public PoOrderPaymentReq setNowSumPay(Double nowSumPay) {
        this.nowSumPay = nowSumPay;
        return this;
    }

    /**
     * 合同付款占比。
     */
    public Double contractPercent;

    /**
     * 获取：合同付款占比。
     */
    public Double getContractPercent() {
        return this.contractPercent;
    }

    /**
     * 设置：合同付款占比。
     */
    public PoOrderPaymentReq setContractPercent(Double contractPercent) {
        this.contractPercent = contractPercent;
        return this;
    }

    /**
     * 概算付款占比。
     */
    public Double estimatePercent;

    /**
     * 获取：概算付款占比。
     */
    public Double getEstimatePercent() {
        return this.estimatePercent;
    }

    /**
     * 设置：概算付款占比。
     */
    public PoOrderPaymentReq setEstimatePercent(Double estimatePercent) {
        this.estimatePercent = estimatePercent;
        return this;
    }

    /**
     * 财评付款占比。
     */
    public Double financialPercent;

    /**
     * 获取：财评付款占比。
     */
    public Double getFinancialPercent() {
        return this.financialPercent;
    }

    /**
     * 设置：财评付款占比。
     */
    public PoOrderPaymentReq setFinancialPercent(Double financialPercent) {
        this.financialPercent = financialPercent;
        return this;
    }

    /**
     * 结算付款占比。
     */
    public Double settlementPercent;

    /**
     * 获取：结算付款占比。
     */
    public Double getSettlementPercent() {
        return this.settlementPercent;
    }

    /**
     * 设置：结算付款占比。
     */
    public PoOrderPaymentReq setSettlementPercent(Double settlementPercent) {
        this.settlementPercent = settlementPercent;
        return this;
    }

    /**
     * 收款单位。
     */
    public String collectionDeptTwo;

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
        this.collectionDeptTwo = collectionDeptTwo;
        return this;
    }

    /**
     * 开户行。
     */
    public String bankOfDeposit;

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
        this.bankOfDeposit = bankOfDeposit;
        return this;
    }

    /**
     * 账号。
     */
    public String accountNo;

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
        this.accountNo = accountNo;
        return this;
    }

    /**
     * 收款单。
     */
    public String receipt;

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
        this.receipt = receipt;
        return this;
    }

    /**
     * 专户开户行。
     */
    public String specialBankOfDeposit;

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
        this.specialBankOfDeposit = specialBankOfDeposit;
        return this;
    }

    /**
     * 专户账号。
     */
    public String specialAccountNo;

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
        this.specialAccountNo = specialAccountNo;
        return this;
    }

    /**
     * 请款事由。
     */
    public String applyCostReason;

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
        this.applyCostReason = applyCostReason;
        return this;
    }

    /**
     * 主体材料。
     */
    public String subjectFile;

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
        this.subjectFile = subjectFile;
        return this;
    }

    /**
     * 保函名称。
     */
    public String guaranteeId;

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
        this.guaranteeId = guaranteeId;
        return this;
    }

    /**
     * 保函金额。
     */
    public Double guaranteeAmt;

    /**
     * 获取：保函金额。
     */
    public Double getGuaranteeAmt() {
        return this.guaranteeAmt;
    }

    /**
     * 设置：保函金额。
     */
    public PoOrderPaymentReq setGuaranteeAmt(Double guaranteeAmt) {
        this.guaranteeAmt = guaranteeAmt;
        return this;
    }

    /**
     * 保函名称。
     */
    public String guaranteeName;

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
        this.guaranteeName = guaranteeName;
        return this;
    }

    /**
     * 保函结束日期。
     */
    public LocalDate guaranteeEndDate;

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
        this.guaranteeEndDate = guaranteeEndDate;
        return this;
    }

    /**
     * 保函结果材料。
     */
    public String guaranteeResultFile;

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
        this.guaranteeResultFile = guaranteeResultFile;
        return this;
    }

    /**
     * 意见发表人。
     */
    public String commentPublishUser;

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
        this.commentPublishUser = commentPublishUser;
        return this;
    }

    /**
     * 意见发表日期。
     */
    public LocalDate commentPublishDate;

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
        this.commentPublishDate = commentPublishDate;
        return this;
    }

    /**
     * 意见内容。
     */
    public String commentPublishContent;

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
        this.commentPublishContent = commentPublishContent;
        return this;
    }

    /**
     * 部门意见发表人。
     */
    public String deptCommentPublishUser;

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
        this.deptCommentPublishUser = deptCommentPublishUser;
        return this;
    }

    /**
     * 部门意见发表日期。
     */
    public LocalDate deptCommentPublishDate;

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
        this.deptCommentPublishDate = deptCommentPublishDate;
        return this;
    }

    /**
     * 部门意见。
     */
    public String deptMessage;

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
        this.deptMessage = deptMessage;
        return this;
    }

    /**
     * 财务意见发表人。
     */
    public String financePublishUser;

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
        this.financePublishUser = financePublishUser;
        return this;
    }

    /**
     * 财务意见发表日期。
     */
    public LocalDate financePublishDate;

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
        this.financePublishDate = financePublishDate;
        return this;
    }

    /**
     * 财务意见。
     */
    public String financeMessage;

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
        this.financeMessage = financeMessage;
        return this;
    }

    /**
     * 领导意见发表人。
     */
    public String leaderCommentPublishUser;

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
        this.leaderCommentPublishUser = leaderCommentPublishUser;
        return this;
    }

    /**
     * 领导意见发表日期。
     */
    public LocalDate leaderCommentPublishDate;

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
        this.leaderCommentPublishDate = leaderCommentPublishDate;
        return this;
    }

    /**
     * 领导意见。
     */
    public String leaderMessage;

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
        this.leaderMessage = leaderMessage;
        return this;
    }

    /**
     * 财务领导意见发表人。
     */
    public String financeLeaderPublishUser;

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
        this.financeLeaderPublishUser = financeLeaderPublishUser;
        return this;
    }

    /**
     * 财务领导意见发表日期。
     */
    public LocalDate financeLeaderPublishDate;

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
        this.financeLeaderPublishDate = financeLeaderPublishDate;
        return this;
    }

    /**
     * 财务领导意见。
     */
    public String financeLeaderMessage;

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
        this.financeLeaderMessage = financeLeaderMessage;
        return this;
    }

    /**
     * 总经理。
     */
    public String president;

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
        this.president = president;
        return this;
    }

    /**
     * 总经理意见发表日期。
     */
    public LocalDate presidentDate;

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
        this.presidentDate = presidentDate;
        return this;
    }

    /**
     * 总经理意见。
     */
    public String presidentMessage;

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
        this.presidentMessage = presidentMessage;
        return this;
    }

    /**
     * 董事长。
     */
    public String chairman;

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
        this.chairman = chairman;
        return this;
    }

    /**
     * 董事长意见发表日期。
     */
    public LocalDate chairmanDate;

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
        this.chairmanDate = chairmanDate;
        return this;
    }

    /**
     * 董事长意见。
     */
    public String chairmanMessage;

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
        this.chairmanMessage = chairmanMessage;
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
    }

    /**
     * 根据ID更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param includeCols 更新时包含的列，空为包含所有。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @param refreshThis 更新后，是否刷新当前对象。刷新时将刷新所有列。
     */
    public void updateById(List<String> includeCols, List<String> excludeCols, boolean refreshThis) {
        modelHelper.updateById(includeCols, excludeCols, refreshThis, this.id, this);
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
        return modelHelper.newData();
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PoOrderPaymentReq insertData() {
        return modelHelper.insertData();
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
        return modelHelper.selectById(id, includeCols, excludeCols);
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
        return modelHelper.selectByIds(ids, includeCols, excludeCols);
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
        return modelHelper.selectByWhere(where, includeCols, excludeCols);
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
