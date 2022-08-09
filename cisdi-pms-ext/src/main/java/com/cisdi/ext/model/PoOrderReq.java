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
 * 采购合同签订申请。
 */
public class PoOrderReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PoOrderReq> modelHelper = new ModelHelper<>("PO_ORDER_REQ", new PoOrderReq());

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
         * 代码。
         */
        public static final String CODE = "CODE";
        /**
         * 名称。
         */
        public static final String NAME = "NAME";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 锁定流程实例。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
        /**
         * 项目编号。
         */
        public static final String PRJ_CODE = "PRJ_CODE";
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
         * 合同名称。
         */
        public static final String CONTRACT_NAME = "CONTRACT_NAME";
        /**
         * 合同编号。
         */
        public static final String CONTRACT_CODE = "CONTRACT_CODE";
        /**
         * 合同类型。
         */
        public static final String CONTRACT_CATEGORY_ID = "CONTRACT_CATEGORY_ID";
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
         * 预计总天数。
         */
        public static final String PLAN_TOTAL_DAYS = "PLAN_TOTAL_DAYS";
        /**
         * 合同金额。
         */
        public static final String CONTRACT_PRICE = "CONTRACT_PRICE";
        /**
         * 是否涉及保函。
         */
        public static final String IS_REFER_GUARANTEE = "IS_REFER_GUARANTEE";
        /**
         * 保函类型。
         */
        public static final String GUARANTEE_LETTER_TYPE_ID = "GUARANTEE_LETTER_TYPE_ID";
        /**
         * 相对方联系人。
         */
        public static final String OPPO_SITE_LINK_MAN = "OPPO_SITE_LINK_MAN";
        /**
         * 主体核实日期。
         */
        public static final String SUBJECT_VERIFY_DATE = "SUBJECT_VERIFY_DATE";
        /**
         * 相对方联系方式。
         */
        public static final String OPPO_SITE_CONTACT = "OPPO_SITE_CONTACT";
        /**
         * 主体有效期。
         */
        public static final String SUBJECT_TERM = "SUBJECT_TERM";
        /**
         * 是否模板。
         */
        public static final String IS_TEMPLATE = "IS_TEMPLATE";
        /**
         * 主体材料。
         */
        public static final String SUBJECT_FILE = "SUBJECT_FILE";
        /**
         * 附件。
         */
        public static final String ATT_FILE_GROUP_ID = "ATT_FILE_GROUP_ID";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 批复文件。
         */
        public static final String REPLY_FILE = "REPLY_FILE";
        /**
         * 审核意见。
         */
        public static final String APPROVAL_MESSAGE = "APPROVAL_MESSAGE";
        /**
         * 专家意见文件。
         */
        public static final String EXPERT_FILE = "EXPERT_FILE";
        /**
         * 前期部意见。
         */
        public static final String EARLY_COMMENT = "EARLY_COMMENT";
        /**
         * 修编稿文件。
         */
        public static final String REVISION_FILE = "REVISION_FILE";
        /**
         * 设计部意见。
         */
        public static final String DESIGN_COMMENT = "DESIGN_COMMENT";
        /**
         * 评审报告文件。
         */
        public static final String REVIEW_DRAFT_FILE = "REVIEW_DRAFT_FILE";
        /**
         * 用户意见。
         */
        public static final String USER_COMMENT = "USER_COMMENT";
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
    public PoOrderReq setId(String id) {
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
    public PoOrderReq setVer(Integer ver) {
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
    public PoOrderReq setTs(LocalDateTime ts) {
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
    public PoOrderReq setIsPreset(Boolean isPreset) {
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
    public PoOrderReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PoOrderReq setLastModiUserId(String lastModiUserId) {
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
    public PoOrderReq setCode(String code) {
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
    public PoOrderReq setName(String name) {
        this.name = name;
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
    public PoOrderReq setPmPrjId(String pmPrjId) {
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
    public PoOrderReq setLkWfInstId(String lkWfInstId) {
        this.lkWfInstId = lkWfInstId;
        return this;
    }

    /**
     * 项目编号。
     */
    public String prjCode;

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
        this.prjCode = prjCode;
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
    public PoOrderReq setStatus(String status) {
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
    public PoOrderReq setPrjReplyNo(String prjReplyNo) {
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
    public PoOrderReq setCrtUserId(String crtUserId) {
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
    public PoOrderReq setPrjSituation(String prjSituation) {
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
    public PoOrderReq setCrtDeptId(String crtDeptId) {
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
    public PoOrderReq setCrtDt(LocalDateTime crtDt) {
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
    public PoOrderReq setInvestmentSourceId(String investmentSourceId) {
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
    public PoOrderReq setCustomerUnit(String customerUnit) {
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
    public PoOrderReq setProjectTypeId(String projectTypeId) {
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
    public PoOrderReq setPrjTotalInvest(Double prjTotalInvest) {
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
    public PoOrderReq setProjectAmt(Double projectAmt) {
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
    public PoOrderReq setProjectOtherAmt(Double projectOtherAmt) {
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
    public PoOrderReq setPrepareAmt(Double prepareAmt) {
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
    public PoOrderReq setConstructPeriodInterest(Double constructPeriodInterest) {
        this.constructPeriodInterest = constructPeriodInterest;
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
    public PoOrderReq setContractName(String contractName) {
        this.contractName = contractName;
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
    public PoOrderReq setContractCode(String contractCode) {
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
    public PoOrderReq setContractCategoryId(String contractCategoryId) {
        this.contractCategoryId = contractCategoryId;
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
    public PoOrderReq setBiddingNameId(String biddingNameId) {
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
    public PoOrderReq setPmsReleaseWayId(String pmsReleaseWayId) {
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
    public PoOrderReq setBidCtlPriceLaunch(Double bidCtlPriceLaunch) {
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
    public PoOrderReq setPurchaseType(String purchaseType) {
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
    public PoOrderReq setWinBidUnitTxt(String winBidUnitTxt) {
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
    public PoOrderReq setWinningBidsAmount(String winningBidsAmount) {
        this.winningBidsAmount = winningBidsAmount;
        return this;
    }

    /**
     * 预计总天数。
     */
    public Integer planTotalDays;

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
        this.planTotalDays = planTotalDays;
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
    public PoOrderReq setContractPrice(String contractPrice) {
        this.contractPrice = contractPrice;
        return this;
    }

    /**
     * 是否涉及保函。
     */
    public Boolean isReferGuarantee;

    /**
     * 获取：是否涉及保函。
     */
    public Boolean getIsReferGuarantee() {
        return this.isReferGuarantee;
    }

    /**
     * 设置：是否涉及保函。
     */
    public PoOrderReq setIsReferGuarantee(Boolean isReferGuarantee) {
        this.isReferGuarantee = isReferGuarantee;
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
    public PoOrderReq setGuaranteeLetterTypeId(String guaranteeLetterTypeId) {
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
    public PoOrderReq setOppoSiteLinkMan(String oppoSiteLinkMan) {
        this.oppoSiteLinkMan = oppoSiteLinkMan;
        return this;
    }

    /**
     * 主体核实日期。
     */
    public LocalDate subjectVerifyDate;

    /**
     * 获取：主体核实日期。
     */
    public LocalDate getSubjectVerifyDate() {
        return this.subjectVerifyDate;
    }

    /**
     * 设置：主体核实日期。
     */
    public PoOrderReq setSubjectVerifyDate(LocalDate subjectVerifyDate) {
        this.subjectVerifyDate = subjectVerifyDate;
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
    public PoOrderReq setOppoSiteContact(String oppoSiteContact) {
        this.oppoSiteContact = oppoSiteContact;
        return this;
    }

    /**
     * 主体有效期。
     */
    public String subjectTerm;

    /**
     * 获取：主体有效期。
     */
    public String getSubjectTerm() {
        return this.subjectTerm;
    }

    /**
     * 设置：主体有效期。
     */
    public PoOrderReq setSubjectTerm(String subjectTerm) {
        this.subjectTerm = subjectTerm;
        return this;
    }

    /**
     * 是否模板。
     */
    public Boolean isTemplate;

    /**
     * 获取：是否模板。
     */
    public Boolean getIsTemplate() {
        return this.isTemplate;
    }

    /**
     * 设置：是否模板。
     */
    public PoOrderReq setIsTemplate(Boolean isTemplate) {
        this.isTemplate = isTemplate;
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
    public PoOrderReq setSubjectFile(String subjectFile) {
        this.subjectFile = subjectFile;
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
    public PoOrderReq setAttFileGroupId(String attFileGroupId) {
        this.attFileGroupId = attFileGroupId;
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
    public PoOrderReq setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    /**
     * 批复文件。
     */
    public String replyFile;

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
        this.replyFile = replyFile;
        return this;
    }

    /**
     * 审核意见。
     */
    public String approvalMessage;

    /**
     * 获取：审核意见。
     */
    public String getApprovalMessage() {
        return this.approvalMessage;
    }

    /**
     * 设置：审核意见。
     */
    public PoOrderReq setApprovalMessage(String approvalMessage) {
        this.approvalMessage = approvalMessage;
        return this;
    }

    /**
     * 专家意见文件。
     */
    public String expertFile;

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
        this.expertFile = expertFile;
        return this;
    }

    /**
     * 前期部意见。
     */
    public String earlyComment;

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
        this.earlyComment = earlyComment;
        return this;
    }

    /**
     * 修编稿文件。
     */
    public String revisionFile;

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
        this.revisionFile = revisionFile;
        return this;
    }

    /**
     * 设计部意见。
     */
    public String designComment;

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
        this.designComment = designComment;
        return this;
    }

    /**
     * 评审报告文件。
     */
    public String reviewDraftFile;

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
        this.reviewDraftFile = reviewDraftFile;
        return this;
    }

    /**
     * 用户意见。
     */
    public String userComment;

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
        this.userComment = userComment;
        return this;
    }

    // </editor-fold>

    // 实例方法：
    // <editor-fold>

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
     * 插入数据。
     *
     * @return
     */
    public static PoOrderReq insertData() {
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
    public static PoOrderReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PoOrderReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PoOrderReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
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
    public static void copyCols(PoOrderReq fromModel, PoOrderReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}