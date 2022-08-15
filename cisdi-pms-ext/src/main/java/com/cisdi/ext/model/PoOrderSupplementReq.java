package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * 采购合同补充协议申请。
 */
public class PoOrderSupplementReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PoOrderSupplementReq> modelHelper = new ModelHelper<>("PO_ORDER_SUPPLEMENT_REQ", new PoOrderSupplementReq());

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
         * 锁定流程实例。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
        /**
         * 合同。
         */
        public static final String CONTRACT_ID = "CONTRACT_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 立项批复文号。
         */
        public static final String PRJ_REPLY_NO = "PRJ_REPLY_NO";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 项目概况。
         */
        public static final String PRJ_SITUATION = "PRJ_SITUATION";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
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
         * 建设期利息。
         */
        public static final String CONSTRUCT_PERIOD_INTEREST = "CONSTRUCT_PERIOD_INTEREST";
        /**
         * 关联合同。
         */
        public static final String RELATION_CONTRACT_ID = "RELATION_CONTRACT_ID";
        /**
         * 合同编号。
         */
        public static final String CONTRACT_CODE = "CONTRACT_CODE";
        /**
         * 合同类型。
         */
        public static final String CONTRACT_CATEGORY_ID = "CONTRACT_CATEGORY_ID";
        /**
         * 合同名称。
         */
        public static final String CONTRACT_NAME = "CONTRACT_NAME";
        /**
         * 招采标流程标题。
         */
        public static final String BIDDING_NAME_ID = "BIDDING_NAME_ID";
        /**
         * 招标类别。
         */
        public static final String PMS_RELEASE_WAY_ID = "PMS_RELEASE_WAY_ID";
        /**
         * 招标控制价发起。
         */
        public static final String BID_CTL_PRICE_LAUNCH = "BID_CTL_PRICE_LAUNCH";
        /**
         * 采购方式。
         */
        public static final String PURCHASE_TYPE = "PURCHASE_TYPE";
        /**
         * 中标单位文本。
         */
        public static final String WIN_BID_UNIT_TXT = "WIN_BID_UNIT_TXT";
        /**
         * 中标待签约额。
         */
        public static final String WINNING_BIDS_AMOUNT = "WINNING_BIDS_AMOUNT";
        /**
         * 合同工期。
         */
        public static final String CONTRACT_DAYS = "CONTRACT_DAYS";
        /**
         * 合同金额。
         */
        public static final String CONTRACT_AMOUNT = "CONTRACT_AMOUNT";
        /**
         * 是否涉及保函下拉。
         */
        public static final String IS_REFER_GUARANTEE_ID = "IS_REFER_GUARANTEE_ID";
        /**
         * 保函类型。
         */
        public static final String GUARANTEE_LETTER_TYPE_ID = "GUARANTEE_LETTER_TYPE_ID";
        /**
         * 相对方联系人。
         */
        public static final String OPPO_SITE_LINK_MAN = "OPPO_SITE_LINK_MAN";
        /**
         * 相对方联系方式。
         */
        public static final String OPPO_SITE_CONTACT = "OPPO_SITE_CONTACT";
        /**
         * 是否标准合同模板。
         */
        public static final String IS_STANDARD_CONTRACT_TEMPLATE_ID = "IS_STANDARD_CONTRACT_TEMPLATE_ID";
        /**
         * 附件。
         */
        public static final String ATT_FILE_GROUP_ID = "ATT_FILE_GROUP_ID";
        /**
         * 补充说明。
         */
        public static final String ADDITIONAL_REMARK = "ADDITIONAL_REMARK";
        /**
         * 部门负责人附件。
         */
        public static final String DEPT_HEAD_FILE_GROUP_ID = "DEPT_HEAD_FILE_GROUP_ID";
        /**
         * 部门负责人审核意见。
         */
        public static final String DEPT_HEAD_APPROVAL_MESSAGE = "DEPT_HEAD_APPROVAL_MESSAGE";
        /**
         * 成本合约部附件。
         */
        public static final String COST_CONTRACT_DEPT_FILE_GROUP_ID = "COST_CONTRACT_DEPT_FILE_GROUP_ID";
        /**
         * 成本合约部审核意见。
         */
        public static final String COST_CONTRACT_APPROVAL_MESSAGE = "COST_CONTRACT_APPROVAL_MESSAGE";
        /**
         * 财务法务附件。
         */
        public static final String FINANCE_LEGAL_CONTRACT_DEPT_FILE_GROUP_ID = "FINANCE_LEGAL_CONTRACT_DEPT_FILE_GROUP_ID";
        /**
         * 财务法务审核意见。
         */
        public static final String FINANCE_LEGAL_APPROVAL_MESSAGE = "FINANCE_LEGAL_APPROVAL_MESSAGE";
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
    public PoOrderSupplementReq setId(String id) {
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
    public PoOrderSupplementReq setVer(Integer ver) {
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
    public PoOrderSupplementReq setTs(LocalDateTime ts) {
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
    public PoOrderSupplementReq setIsPreset(Boolean isPreset) {
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
    public PoOrderSupplementReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PoOrderSupplementReq setLastModiUserId(String lastModiUserId) {
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
    public PoOrderSupplementReq setCode(String code) {
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
    public PoOrderSupplementReq setName(String name) {
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
    public PoOrderSupplementReq setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    /**
     * 项目。
     */
    public String pmPrjId;

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
        this.pmPrjId = pmPrjId;
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
    public PoOrderSupplementReq setLkWfInstId(String lkWfInstId) {
        this.lkWfInstId = lkWfInstId;
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
    public PoOrderSupplementReq setContractId(String contractId) {
        this.contractId = contractId;
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
    public PoOrderSupplementReq setStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * 立项批复文号。
     */
    public String prjReplyNo;

    /**
     * 获取：立项批复文号。
     */
    public String getPrjReplyNo() {
        return this.prjReplyNo;
    }

    /**
     * 设置：立项批复文号。
     */
    public PoOrderSupplementReq setPrjReplyNo(String prjReplyNo) {
        this.prjReplyNo = prjReplyNo;
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
    public PoOrderSupplementReq setCrtUserId(String crtUserId) {
        this.crtUserId = crtUserId;
        return this;
    }

    /**
     * 项目概况。
     */
    public String prjSituation;

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
        this.prjSituation = prjSituation;
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
    public PoOrderSupplementReq setCrtDeptId(String crtDeptId) {
        this.crtDeptId = crtDeptId;
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
    public PoOrderSupplementReq setCrtDt(LocalDateTime crtDt) {
        this.crtDt = crtDt;
        return this;
    }

    /**
     * 投资来源。
     */
    public String investmentSourceId;

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
        this.investmentSourceId = investmentSourceId;
        return this;
    }

    /**
     * 业主单位。
     */
    public String customerUnit;

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
        this.customerUnit = customerUnit;
        return this;
    }

    /**
     * 项目类型。
     */
    public String projectTypeId;

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
        this.projectTypeId = projectTypeId;
        return this;
    }

    /**
     * 总投资（万）。
     */
    public Double prjTotalInvest;

    /**
     * 获取：总投资（万）。
     */
    public Double getPrjTotalInvest() {
        return this.prjTotalInvest;
    }

    /**
     * 设置：总投资（万）。
     */
    public PoOrderSupplementReq setPrjTotalInvest(Double prjTotalInvest) {
        this.prjTotalInvest = prjTotalInvest;
        return this;
    }

    /**
     * 工程费用（万）。
     */
    public Double projectAmt;

    /**
     * 获取：工程费用（万）。
     */
    public Double getProjectAmt() {
        return this.projectAmt;
    }

    /**
     * 设置：工程费用（万）。
     */
    public PoOrderSupplementReq setProjectAmt(Double projectAmt) {
        this.projectAmt = projectAmt;
        return this;
    }

    /**
     * 工程其他费用（万）。
     */
    public Double projectOtherAmt;

    /**
     * 获取：工程其他费用（万）。
     */
    public Double getProjectOtherAmt() {
        return this.projectOtherAmt;
    }

    /**
     * 设置：工程其他费用（万）。
     */
    public PoOrderSupplementReq setProjectOtherAmt(Double projectOtherAmt) {
        this.projectOtherAmt = projectOtherAmt;
        return this;
    }

    /**
     * 预备费（万）。
     */
    public Double prepareAmt;

    /**
     * 获取：预备费（万）。
     */
    public Double getPrepareAmt() {
        return this.prepareAmt;
    }

    /**
     * 设置：预备费（万）。
     */
    public PoOrderSupplementReq setPrepareAmt(Double prepareAmt) {
        this.prepareAmt = prepareAmt;
        return this;
    }

    /**
     * 建设期利息。
     */
    public Double constructPeriodInterest;

    /**
     * 获取：建设期利息。
     */
    public Double getConstructPeriodInterest() {
        return this.constructPeriodInterest;
    }

    /**
     * 设置：建设期利息。
     */
    public PoOrderSupplementReq setConstructPeriodInterest(Double constructPeriodInterest) {
        this.constructPeriodInterest = constructPeriodInterest;
        return this;
    }

    /**
     * 关联合同。
     */
    public String relationContractId;

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
        this.relationContractId = relationContractId;
        return this;
    }

    /**
     * 合同编号。
     */
    public String contractCode;

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
        this.contractCode = contractCode;
        return this;
    }

    /**
     * 合同类型。
     */
    public String contractCategoryId;

    /**
     * 获取：合同类型。
     */
    public String getContractCategoryId() {
        return this.contractCategoryId;
    }

    /**
     * 设置：合同类型。
     */
    public PoOrderSupplementReq setContractCategoryId(String contractCategoryId) {
        this.contractCategoryId = contractCategoryId;
        return this;
    }

    /**
     * 合同名称。
     */
    public String contractName;

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
        this.contractName = contractName;
        return this;
    }

    /**
     * 招采标流程标题。
     */
    public String biddingNameId;

    /**
     * 获取：招采标流程标题。
     */
    public String getBiddingNameId() {
        return this.biddingNameId;
    }

    /**
     * 设置：招采标流程标题。
     */
    public PoOrderSupplementReq setBiddingNameId(String biddingNameId) {
        this.biddingNameId = biddingNameId;
        return this;
    }

    /**
     * 招标类别。
     */
    public String pmsReleaseWayId;

    /**
     * 获取：招标类别。
     */
    public String getPmsReleaseWayId() {
        return this.pmsReleaseWayId;
    }

    /**
     * 设置：招标类别。
     */
    public PoOrderSupplementReq setPmsReleaseWayId(String pmsReleaseWayId) {
        this.pmsReleaseWayId = pmsReleaseWayId;
        return this;
    }

    /**
     * 招标控制价发起。
     */
    public Double bidCtlPriceLaunch;

    /**
     * 获取：招标控制价发起。
     */
    public Double getBidCtlPriceLaunch() {
        return this.bidCtlPriceLaunch;
    }

    /**
     * 设置：招标控制价发起。
     */
    public PoOrderSupplementReq setBidCtlPriceLaunch(Double bidCtlPriceLaunch) {
        this.bidCtlPriceLaunch = bidCtlPriceLaunch;
        return this;
    }

    /**
     * 采购方式。
     */
    public String purchaseType;

    /**
     * 获取：采购方式。
     */
    public String getPurchaseType() {
        return this.purchaseType;
    }

    /**
     * 设置：采购方式。
     */
    public PoOrderSupplementReq setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
        return this;
    }

    /**
     * 中标单位文本。
     */
    public String winBidUnitTxt;

    /**
     * 获取：中标单位文本。
     */
    public String getWinBidUnitTxt() {
        return this.winBidUnitTxt;
    }

    /**
     * 设置：中标单位文本。
     */
    public PoOrderSupplementReq setWinBidUnitTxt(String winBidUnitTxt) {
        this.winBidUnitTxt = winBidUnitTxt;
        return this;
    }

    /**
     * 中标待签约额。
     */
    public String winningBidsAmount;

    /**
     * 获取：中标待签约额。
     */
    public String getWinningBidsAmount() {
        return this.winningBidsAmount;
    }

    /**
     * 设置：中标待签约额。
     */
    public PoOrderSupplementReq setWinningBidsAmount(String winningBidsAmount) {
        this.winningBidsAmount = winningBidsAmount;
        return this;
    }

    /**
     * 合同工期。
     */
    public Integer contractDays;

    /**
     * 获取：合同工期。
     */
    public Integer getContractDays() {
        return this.contractDays;
    }

    /**
     * 设置：合同工期。
     */
    public PoOrderSupplementReq setContractDays(Integer contractDays) {
        this.contractDays = contractDays;
        return this;
    }

    /**
     * 合同金额。
     */
    public Double contractAmount;

    /**
     * 获取：合同金额。
     */
    public Double getContractAmount() {
        return this.contractAmount;
    }

    /**
     * 设置：合同金额。
     */
    public PoOrderSupplementReq setContractAmount(Double contractAmount) {
        this.contractAmount = contractAmount;
        return this;
    }

    /**
     * 是否涉及保函下拉。
     */
    public String isReferGuaranteeId;

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
        this.isReferGuaranteeId = isReferGuaranteeId;
        return this;
    }

    /**
     * 保函类型。
     */
    public String guaranteeLetterTypeId;

    /**
     * 获取：保函类型。
     */
    public String getGuaranteeLetterTypeId() {
        return this.guaranteeLetterTypeId;
    }

    /**
     * 设置：保函类型。
     */
    public PoOrderSupplementReq setGuaranteeLetterTypeId(String guaranteeLetterTypeId) {
        this.guaranteeLetterTypeId = guaranteeLetterTypeId;
        return this;
    }

    /**
     * 相对方联系人。
     */
    public String oppoSiteLinkMan;

    /**
     * 获取：相对方联系人。
     */
    public String getOppoSiteLinkMan() {
        return this.oppoSiteLinkMan;
    }

    /**
     * 设置：相对方联系人。
     */
    public PoOrderSupplementReq setOppoSiteLinkMan(String oppoSiteLinkMan) {
        this.oppoSiteLinkMan = oppoSiteLinkMan;
        return this;
    }

    /**
     * 相对方联系方式。
     */
    public String oppoSiteContact;

    /**
     * 获取：相对方联系方式。
     */
    public String getOppoSiteContact() {
        return this.oppoSiteContact;
    }

    /**
     * 设置：相对方联系方式。
     */
    public PoOrderSupplementReq setOppoSiteContact(String oppoSiteContact) {
        this.oppoSiteContact = oppoSiteContact;
        return this;
    }

    /**
     * 是否标准合同模板。
     */
    public String isStandardContractTemplateId;

    /**
     * 获取：是否标准合同模板。
     */
    public String getIsStandardContractTemplateId() {
        return this.isStandardContractTemplateId;
    }

    /**
     * 设置：是否标准合同模板。
     */
    public PoOrderSupplementReq setIsStandardContractTemplateId(String isStandardContractTemplateId) {
        this.isStandardContractTemplateId = isStandardContractTemplateId;
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
    public PoOrderSupplementReq setAttFileGroupId(String attFileGroupId) {
        this.attFileGroupId = attFileGroupId;
        return this;
    }

    /**
     * 补充说明。
     */
    public String additionalRemark;

    /**
     * 获取：补充说明。
     */
    public String getAdditionalRemark() {
        return this.additionalRemark;
    }

    /**
     * 设置：补充说明。
     */
    public PoOrderSupplementReq setAdditionalRemark(String additionalRemark) {
        this.additionalRemark = additionalRemark;
        return this;
    }

    /**
     * 部门负责人附件。
     */
    public String deptHeadFileGroupId;

    /**
     * 获取：部门负责人附件。
     */
    public String getDeptHeadFileGroupId() {
        return this.deptHeadFileGroupId;
    }

    /**
     * 设置：部门负责人附件。
     */
    public PoOrderSupplementReq setDeptHeadFileGroupId(String deptHeadFileGroupId) {
        this.deptHeadFileGroupId = deptHeadFileGroupId;
        return this;
    }

    /**
     * 部门负责人审核意见。
     */
    public String deptHeadApprovalMessage;

    /**
     * 获取：部门负责人审核意见。
     */
    public String getDeptHeadApprovalMessage() {
        return this.deptHeadApprovalMessage;
    }

    /**
     * 设置：部门负责人审核意见。
     */
    public PoOrderSupplementReq setDeptHeadApprovalMessage(String deptHeadApprovalMessage) {
        this.deptHeadApprovalMessage = deptHeadApprovalMessage;
        return this;
    }

    /**
     * 成本合约部附件。
     */
    public String costContractDeptFileGroupId;

    /**
     * 获取：成本合约部附件。
     */
    public String getCostContractDeptFileGroupId() {
        return this.costContractDeptFileGroupId;
    }

    /**
     * 设置：成本合约部附件。
     */
    public PoOrderSupplementReq setCostContractDeptFileGroupId(String costContractDeptFileGroupId) {
        this.costContractDeptFileGroupId = costContractDeptFileGroupId;
        return this;
    }

    /**
     * 成本合约部审核意见。
     */
    public String costContractApprovalMessage;

    /**
     * 获取：成本合约部审核意见。
     */
    public String getCostContractApprovalMessage() {
        return this.costContractApprovalMessage;
    }

    /**
     * 设置：成本合约部审核意见。
     */
    public PoOrderSupplementReq setCostContractApprovalMessage(String costContractApprovalMessage) {
        this.costContractApprovalMessage = costContractApprovalMessage;
        return this;
    }

    /**
     * 财务法务附件。
     */
    public String financeLegalContractDeptFileGroupId;

    /**
     * 获取：财务法务附件。
     */
    public String getFinanceLegalContractDeptFileGroupId() {
        return this.financeLegalContractDeptFileGroupId;
    }

    /**
     * 设置：财务法务附件。
     */
    public PoOrderSupplementReq setFinanceLegalContractDeptFileGroupId(String financeLegalContractDeptFileGroupId) {
        this.financeLegalContractDeptFileGroupId = financeLegalContractDeptFileGroupId;
        return this;
    }

    /**
     * 财务法务审核意见。
     */
    public String financeLegalApprovalMessage;

    /**
     * 获取：财务法务审核意见。
     */
    public String getFinanceLegalApprovalMessage() {
        return this.financeLegalApprovalMessage;
    }

    /**
     * 设置：财务法务审核意见。
     */
    public PoOrderSupplementReq setFinanceLegalApprovalMessage(String financeLegalApprovalMessage) {
        this.financeLegalApprovalMessage = financeLegalApprovalMessage;
        return this;
    }

    /**
     * 领导审批附件。
     */
    public String leaderApproveFileGroupId;

    /**
     * 获取：领导审批附件。
     */
    public String getLeaderApproveFileGroupId() {
        return this.leaderApproveFileGroupId;
    }

    /**
     * 设置：领导审批附件。
     */
    public PoOrderSupplementReq setLeaderApproveFileGroupId(String leaderApproveFileGroupId) {
        this.leaderApproveFileGroupId = leaderApproveFileGroupId;
        return this;
    }

    /**
     * 领导审批意见。
     */
    public String leaderApproveComment;

    /**
     * 获取：领导审批意见。
     */
    public String getLeaderApproveComment() {
        return this.leaderApproveComment;
    }

    /**
     * 设置：领导审批意见。
     */
    public PoOrderSupplementReq setLeaderApproveComment(String leaderApproveComment) {
        this.leaderApproveComment = leaderApproveComment;
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
    public static PoOrderSupplementReq newData() {
        return modelHelper.newData();
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PoOrderSupplementReq insertData() {
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
    public static PoOrderSupplementReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PoOrderSupplementReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PoOrderSupplementReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
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
    public static void copyCols(PoOrderSupplementReq fromModel, PoOrderSupplementReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}