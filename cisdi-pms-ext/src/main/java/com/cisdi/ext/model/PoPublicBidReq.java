package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * 采购公开招标申请。
 */
public class PoPublicBidReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PoPublicBidReq> modelHelper = new ModelHelper<>("PO_PUBLIC_BID_REQ", new PoPublicBidReq());

    // 实体常量：
    // <editor-fold>

    public static final String ENT_CODE = "PO_PUBLIC_BID_REQ";
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
         * 锁定流程实例。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 项目编号。
         */
        public static final String PRJ_CODE = "PRJ_CODE";
        /**
         * 立项批复文号。
         */
        public static final String PRJ_REPLY_NO = "PRJ_REPLY_NO";
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
         * 招标类别。
         */
        public static final String PMS_RELEASE_WAY_ID = "PMS_RELEASE_WAY_ID";
        /**
         * 可研批复资金（万）。
         */
        public static final String FEASIBILITY_APPROVE_FUND = "FEASIBILITY_APPROVE_FUND";
        /**
         * 初概批复资金（万）。
         */
        public static final String ESTIMATE_APPROVE_FUND = "ESTIMATE_APPROVE_FUND";
        /**
         * 财评批复资金（万）。
         */
        public static final String EVALUATION_APPROVE_FUND = "EVALUATION_APPROVE_FUND";
        /**
         * 招标单位。
         */
        public static final String BID_UNIT = "BID_UNIT";
        /**
         * 招标依据。
         */
        public static final String BID_BASIS = "BID_BASIS";
        /**
         * 招标控制价发起。
         */
        public static final String BID_CTL_PRICE_LAUNCH = "BID_CTL_PRICE_LAUNCH";
        /**
         * 服务周期。
         */
        public static final String SERVICE_DAYS = "SERVICE_DAYS";
        /**
         * 招标需求附件。
         */
        public static final String BID_DEMAND_FILE_GROUP_ID = "BID_DEMAND_FILE_GROUP_ID";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 核定招标类别。
         */
        public static final String APPROVE_PMS_RELEASE_WAY_ID = "APPROVE_PMS_RELEASE_WAY_ID";
        /**
         * 招标方式。
         */
        public static final String APPROVE_PURCHASE_TYPE = "APPROVE_PURCHASE_TYPE";
        /**
         * 核定招标控制价。
         */
        public static final String APPROVE_BID_CTL_PRICE = "APPROVE_BID_CTL_PRICE";
        /**
         * 领导审批意见。
         */
        public static final String LEADER_APPROVE_COMMENT = "LEADER_APPROVE_COMMENT";
        /**
         * 领导审批附件。
         */
        public static final String LEADER_APPROVE_FILE_GROUP_ID = "LEADER_APPROVE_FILE_GROUP_ID";
        /**
         * 招标方式回显。
         */
        public static final String APPROVE_PURCHASE_TYPE_ECHO = "APPROVE_PURCHASE_TYPE_ECHO";
        /**
         * 招标控制价回显。
         */
        public static final String BID_CTL_PRICE_LAUNCH_ECHO = "BID_CTL_PRICE_LAUNCH_ECHO";
        /**
         * 招标经办人。
         */
        public static final String BID_USER_ID = "BID_USER_ID";
        /**
         * 招标代理单位。
         */
        public static final String BID_AGENCY = "BID_AGENCY";
        /**
         * 需求发起人。
         */
        public static final String DEMAND_PROMOTER = "DEMAND_PROMOTER";
        /**
         * 招标文件。
         */
        public static final String BID_FILE_GROUP_ID = "BID_FILE_GROUP_ID";
        /**
         * 报名时间。
         */
        public static final String REGISTRATION_DATE = "REGISTRATION_DATE";
        /**
         * 开标时间。
         */
        public static final String BID_OPEN_DATE = "BID_OPEN_DATE";
        /**
         * 招标平台。
         */
        public static final String BID_PLATFORM = "BID_PLATFORM";
        /**
         * 发标信息。
         */
        public static final String BID_ISSUE_INFO = "BID_ISSUE_INFO";
        /**
         * 用章经办人审批意见。
         */
        public static final String SEAL_AGENT_APPROVE_COMMENT = "SEAL_AGENT_APPROVE_COMMENT";
        /**
         * 发标招标文件。
         */
        public static final String BID_ISSUE_FILE_GROUP_ID = "BID_ISSUE_FILE_GROUP_ID";
        /**
         * 用章领导审批意见。
         */
        public static final String SEAL_APPROVE_COMMENT = "SEAL_APPROVE_COMMENT";
        /**
         * 中标单位文本。
         */
        public static final String WIN_BID_UNIT_TXT = "WIN_BID_UNIT_TXT";
        /**
         * 投标报价。
         */
        public static final String TENDER_OFFER = "TENDER_OFFER";
        /**
         * 联系人名称。
         */
        public static final String CONTACT_NAME = "CONTACT_NAME";
        /**
         * 中标联系人手机。
         */
        public static final String CONTACT_MOBILE_WIN = "CONTACT_MOBILE_WIN";
        /**
         * 中标联系人身份证。
         */
        public static final String CONTACT_IDCARD_WIN = "CONTACT_IDCARD_WIN";
        /**
         * 标后附件。
         */
        public static final String BID_AFTER_FILE_GROUP_ID = "BID_AFTER_FILE_GROUP_ID";
        /**
         * 标后领导人审批意见。
         */
        public static final String BID_AFTER_APPROVE_COMMENT = "BID_AFTER_APPROVE_COMMENT";
        /**
         * 中标单位备案。
         */
        public static final String WIN_BID_UNIT_RECORD = "WIN_BID_UNIT_RECORD";
        /**
         * 投标报价备案。
         */
        public static final String TENDER_OFFER_RECORD = "TENDER_OFFER_RECORD";
        /**
         * 是否备案。
         */
        public static final String IS_RECORD = "IS_RECORD";
        /**
         * 联系人名称备案。
         */
        public static final String CONTACT_NAME_RECORD = "CONTACT_NAME_RECORD";
        /**
         * 联系人手机备案。
         */
        public static final String CONTACT_MOBILE_RECORD = "CONTACT_MOBILE_RECORD";
        /**
         * 联系人身份证备案。
         */
        public static final String CONTACT_IDCARD_RECORD = "CONTACT_IDCARD_RECORD";
        /**
         * 中标通知书。
         */
        public static final String BID_WIN_NOTICE_FILE_GROUP_ID = "BID_WIN_NOTICE_FILE_GROUP_ID";
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
    public PoPublicBidReq setId(String id) {
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
    public PoPublicBidReq setVer(Integer ver) {
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
    public PoPublicBidReq setTs(LocalDateTime ts) {
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
    public PoPublicBidReq setIsPreset(Boolean isPreset) {
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
    public PoPublicBidReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PoPublicBidReq setLastModiUserId(String lastModiUserId) {
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
    public PoPublicBidReq setCode(String code) {
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
    public PoPublicBidReq setName(String name) {
        this.name = name;
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
    public PoPublicBidReq setLkWfInstId(String lkWfInstId) {
        this.lkWfInstId = lkWfInstId;
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
    public PoPublicBidReq setStatus(String status) {
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
    public PoPublicBidReq setCrtUserId(String crtUserId) {
        this.crtUserId = crtUserId;
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
    public PoPublicBidReq setCrtDeptId(String crtDeptId) {
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
    public PoPublicBidReq setCrtDt(LocalDateTime crtDt) {
        this.crtDt = crtDt;
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
    public PoPublicBidReq setPmPrjId(String pmPrjId) {
        this.pmPrjId = pmPrjId;
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
    public PoPublicBidReq setPrjCode(String prjCode) {
        this.prjCode = prjCode;
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
    public PoPublicBidReq setPrjReplyNo(String prjReplyNo) {
        this.prjReplyNo = prjReplyNo;
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
    public PoPublicBidReq setPrjSituation(String prjSituation) {
        this.prjSituation = prjSituation;
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
    public PoPublicBidReq setInvestmentSourceId(String investmentSourceId) {
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
    public PoPublicBidReq setCustomerUnit(String customerUnit) {
        this.customerUnit = customerUnit;
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
    public PoPublicBidReq setPmsReleaseWayId(String pmsReleaseWayId) {
        this.pmsReleaseWayId = pmsReleaseWayId;
        return this;
    }

    /**
     * 可研批复资金（万）。
     */
    public Double feasibilityApproveFund;

    /**
     * 获取：可研批复资金（万）。
     */
    public Double getFeasibilityApproveFund() {
        return this.feasibilityApproveFund;
    }

    /**
     * 设置：可研批复资金（万）。
     */
    public PoPublicBidReq setFeasibilityApproveFund(Double feasibilityApproveFund) {
        this.feasibilityApproveFund = feasibilityApproveFund;
        return this;
    }

    /**
     * 初概批复资金（万）。
     */
    public Double estimateApproveFund;

    /**
     * 获取：初概批复资金（万）。
     */
    public Double getEstimateApproveFund() {
        return this.estimateApproveFund;
    }

    /**
     * 设置：初概批复资金（万）。
     */
    public PoPublicBidReq setEstimateApproveFund(Double estimateApproveFund) {
        this.estimateApproveFund = estimateApproveFund;
        return this;
    }

    /**
     * 财评批复资金（万）。
     */
    public Double evaluationApproveFund;

    /**
     * 获取：财评批复资金（万）。
     */
    public Double getEvaluationApproveFund() {
        return this.evaluationApproveFund;
    }

    /**
     * 设置：财评批复资金（万）。
     */
    public PoPublicBidReq setEvaluationApproveFund(Double evaluationApproveFund) {
        this.evaluationApproveFund = evaluationApproveFund;
        return this;
    }

    /**
     * 招标单位。
     */
    public String bidUnit;

    /**
     * 获取：招标单位。
     */
    public String getBidUnit() {
        return this.bidUnit;
    }

    /**
     * 设置：招标单位。
     */
    public PoPublicBidReq setBidUnit(String bidUnit) {
        this.bidUnit = bidUnit;
        return this;
    }

    /**
     * 招标依据。
     */
    public String bidBasis;

    /**
     * 获取：招标依据。
     */
    public String getBidBasis() {
        return this.bidBasis;
    }

    /**
     * 设置：招标依据。
     */
    public PoPublicBidReq setBidBasis(String bidBasis) {
        this.bidBasis = bidBasis;
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
    public PoPublicBidReq setBidCtlPriceLaunch(Double bidCtlPriceLaunch) {
        this.bidCtlPriceLaunch = bidCtlPriceLaunch;
        return this;
    }

    /**
     * 服务周期。
     */
    public Integer serviceDays;

    /**
     * 获取：服务周期。
     */
    public Integer getServiceDays() {
        return this.serviceDays;
    }

    /**
     * 设置：服务周期。
     */
    public PoPublicBidReq setServiceDays(Integer serviceDays) {
        this.serviceDays = serviceDays;
        return this;
    }

    /**
     * 招标需求附件。
     */
    public String bidDemandFileGroupId;

    /**
     * 获取：招标需求附件。
     */
    public String getBidDemandFileGroupId() {
        return this.bidDemandFileGroupId;
    }

    /**
     * 设置：招标需求附件。
     */
    public PoPublicBidReq setBidDemandFileGroupId(String bidDemandFileGroupId) {
        this.bidDemandFileGroupId = bidDemandFileGroupId;
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
    public PoPublicBidReq setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    /**
     * 核定招标类别。
     */
    public String approvePmsReleaseWayId;

    /**
     * 获取：核定招标类别。
     */
    public String getApprovePmsReleaseWayId() {
        return this.approvePmsReleaseWayId;
    }

    /**
     * 设置：核定招标类别。
     */
    public PoPublicBidReq setApprovePmsReleaseWayId(String approvePmsReleaseWayId) {
        this.approvePmsReleaseWayId = approvePmsReleaseWayId;
        return this;
    }

    /**
     * 招标方式。
     */
    public String approvePurchaseType;

    /**
     * 获取：招标方式。
     */
    public String getApprovePurchaseType() {
        return this.approvePurchaseType;
    }

    /**
     * 设置：招标方式。
     */
    public PoPublicBidReq setApprovePurchaseType(String approvePurchaseType) {
        this.approvePurchaseType = approvePurchaseType;
        return this;
    }

    /**
     * 核定招标控制价。
     */
    public Double approveBidCtlPrice;

    /**
     * 获取：核定招标控制价。
     */
    public Double getApproveBidCtlPrice() {
        return this.approveBidCtlPrice;
    }

    /**
     * 设置：核定招标控制价。
     */
    public PoPublicBidReq setApproveBidCtlPrice(Double approveBidCtlPrice) {
        this.approveBidCtlPrice = approveBidCtlPrice;
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
    public PoPublicBidReq setLeaderApproveComment(String leaderApproveComment) {
        this.leaderApproveComment = leaderApproveComment;
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
    public PoPublicBidReq setLeaderApproveFileGroupId(String leaderApproveFileGroupId) {
        this.leaderApproveFileGroupId = leaderApproveFileGroupId;
        return this;
    }

    /**
     * 招标方式回显。
     */
    public String approvePurchaseTypeEcho;

    /**
     * 获取：招标方式回显。
     */
    public String getApprovePurchaseTypeEcho() {
        return this.approvePurchaseTypeEcho;
    }

    /**
     * 设置：招标方式回显。
     */
    public PoPublicBidReq setApprovePurchaseTypeEcho(String approvePurchaseTypeEcho) {
        this.approvePurchaseTypeEcho = approvePurchaseTypeEcho;
        return this;
    }

    /**
     * 招标控制价回显。
     */
    public Double bidCtlPriceLaunchEcho;

    /**
     * 获取：招标控制价回显。
     */
    public Double getBidCtlPriceLaunchEcho() {
        return this.bidCtlPriceLaunchEcho;
    }

    /**
     * 设置：招标控制价回显。
     */
    public PoPublicBidReq setBidCtlPriceLaunchEcho(Double bidCtlPriceLaunchEcho) {
        this.bidCtlPriceLaunchEcho = bidCtlPriceLaunchEcho;
        return this;
    }

    /**
     * 招标经办人。
     */
    public String bidUserId;

    /**
     * 获取：招标经办人。
     */
    public String getBidUserId() {
        return this.bidUserId;
    }

    /**
     * 设置：招标经办人。
     */
    public PoPublicBidReq setBidUserId(String bidUserId) {
        this.bidUserId = bidUserId;
        return this;
    }

    /**
     * 招标代理单位。
     */
    public String bidAgency;

    /**
     * 获取：招标代理单位。
     */
    public String getBidAgency() {
        return this.bidAgency;
    }

    /**
     * 设置：招标代理单位。
     */
    public PoPublicBidReq setBidAgency(String bidAgency) {
        this.bidAgency = bidAgency;
        return this;
    }

    /**
     * 需求发起人。
     */
    public String demandPromoter;

    /**
     * 获取：需求发起人。
     */
    public String getDemandPromoter() {
        return this.demandPromoter;
    }

    /**
     * 设置：需求发起人。
     */
    public PoPublicBidReq setDemandPromoter(String demandPromoter) {
        this.demandPromoter = demandPromoter;
        return this;
    }

    /**
     * 招标文件。
     */
    public String bidFileGroupId;

    /**
     * 获取：招标文件。
     */
    public String getBidFileGroupId() {
        return this.bidFileGroupId;
    }

    /**
     * 设置：招标文件。
     */
    public PoPublicBidReq setBidFileGroupId(String bidFileGroupId) {
        this.bidFileGroupId = bidFileGroupId;
        return this;
    }

    /**
     * 报名时间。
     */
    public LocalDate registrationDate;

    /**
     * 获取：报名时间。
     */
    public LocalDate getRegistrationDate() {
        return this.registrationDate;
    }

    /**
     * 设置：报名时间。
     */
    public PoPublicBidReq setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    /**
     * 开标时间。
     */
    public LocalDate bidOpenDate;

    /**
     * 获取：开标时间。
     */
    public LocalDate getBidOpenDate() {
        return this.bidOpenDate;
    }

    /**
     * 设置：开标时间。
     */
    public PoPublicBidReq setBidOpenDate(LocalDate bidOpenDate) {
        this.bidOpenDate = bidOpenDate;
        return this;
    }

    /**
     * 招标平台。
     */
    public String bidPlatform;

    /**
     * 获取：招标平台。
     */
    public String getBidPlatform() {
        return this.bidPlatform;
    }

    /**
     * 设置：招标平台。
     */
    public PoPublicBidReq setBidPlatform(String bidPlatform) {
        this.bidPlatform = bidPlatform;
        return this;
    }

    /**
     * 发标信息。
     */
    public String bidIssueInfo;

    /**
     * 获取：发标信息。
     */
    public String getBidIssueInfo() {
        return this.bidIssueInfo;
    }

    /**
     * 设置：发标信息。
     */
    public PoPublicBidReq setBidIssueInfo(String bidIssueInfo) {
        this.bidIssueInfo = bidIssueInfo;
        return this;
    }

    /**
     * 用章经办人审批意见。
     */
    public String sealAgentApproveComment;

    /**
     * 获取：用章经办人审批意见。
     */
    public String getSealAgentApproveComment() {
        return this.sealAgentApproveComment;
    }

    /**
     * 设置：用章经办人审批意见。
     */
    public PoPublicBidReq setSealAgentApproveComment(String sealAgentApproveComment) {
        this.sealAgentApproveComment = sealAgentApproveComment;
        return this;
    }

    /**
     * 发标招标文件。
     */
    public String bidIssueFileGroupId;

    /**
     * 获取：发标招标文件。
     */
    public String getBidIssueFileGroupId() {
        return this.bidIssueFileGroupId;
    }

    /**
     * 设置：发标招标文件。
     */
    public PoPublicBidReq setBidIssueFileGroupId(String bidIssueFileGroupId) {
        this.bidIssueFileGroupId = bidIssueFileGroupId;
        return this;
    }

    /**
     * 用章领导审批意见。
     */
    public String sealApproveComment;

    /**
     * 获取：用章领导审批意见。
     */
    public String getSealApproveComment() {
        return this.sealApproveComment;
    }

    /**
     * 设置：用章领导审批意见。
     */
    public PoPublicBidReq setSealApproveComment(String sealApproveComment) {
        this.sealApproveComment = sealApproveComment;
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
    public PoPublicBidReq setWinBidUnitTxt(String winBidUnitTxt) {
        this.winBidUnitTxt = winBidUnitTxt;
        return this;
    }

    /**
     * 投标报价。
     */
    public String tenderOffer;

    /**
     * 获取：投标报价。
     */
    public String getTenderOffer() {
        return this.tenderOffer;
    }

    /**
     * 设置：投标报价。
     */
    public PoPublicBidReq setTenderOffer(String tenderOffer) {
        this.tenderOffer = tenderOffer;
        return this;
    }

    /**
     * 联系人名称。
     */
    public String contactName;

    /**
     * 获取：联系人名称。
     */
    public String getContactName() {
        return this.contactName;
    }

    /**
     * 设置：联系人名称。
     */
    public PoPublicBidReq setContactName(String contactName) {
        this.contactName = contactName;
        return this;
    }

    /**
     * 中标联系人手机。
     */
    public String contactMobileWin;

    /**
     * 获取：中标联系人手机。
     */
    public String getContactMobileWin() {
        return this.contactMobileWin;
    }

    /**
     * 设置：中标联系人手机。
     */
    public PoPublicBidReq setContactMobileWin(String contactMobileWin) {
        this.contactMobileWin = contactMobileWin;
        return this;
    }

    /**
     * 中标联系人身份证。
     */
    public String contactIdcardWin;

    /**
     * 获取：中标联系人身份证。
     */
    public String getContactIdcardWin() {
        return this.contactIdcardWin;
    }

    /**
     * 设置：中标联系人身份证。
     */
    public PoPublicBidReq setContactIdcardWin(String contactIdcardWin) {
        this.contactIdcardWin = contactIdcardWin;
        return this;
    }

    /**
     * 标后附件。
     */
    public String bidAfterFileGroupId;

    /**
     * 获取：标后附件。
     */
    public String getBidAfterFileGroupId() {
        return this.bidAfterFileGroupId;
    }

    /**
     * 设置：标后附件。
     */
    public PoPublicBidReq setBidAfterFileGroupId(String bidAfterFileGroupId) {
        this.bidAfterFileGroupId = bidAfterFileGroupId;
        return this;
    }

    /**
     * 标后领导人审批意见。
     */
    public String bidAfterApproveComment;

    /**
     * 获取：标后领导人审批意见。
     */
    public String getBidAfterApproveComment() {
        return this.bidAfterApproveComment;
    }

    /**
     * 设置：标后领导人审批意见。
     */
    public PoPublicBidReq setBidAfterApproveComment(String bidAfterApproveComment) {
        this.bidAfterApproveComment = bidAfterApproveComment;
        return this;
    }

    /**
     * 中标单位备案。
     */
    public String winBidUnitRecord;

    /**
     * 获取：中标单位备案。
     */
    public String getWinBidUnitRecord() {
        return this.winBidUnitRecord;
    }

    /**
     * 设置：中标单位备案。
     */
    public PoPublicBidReq setWinBidUnitRecord(String winBidUnitRecord) {
        this.winBidUnitRecord = winBidUnitRecord;
        return this;
    }

    /**
     * 投标报价备案。
     */
    public String tenderOfferRecord;

    /**
     * 获取：投标报价备案。
     */
    public String getTenderOfferRecord() {
        return this.tenderOfferRecord;
    }

    /**
     * 设置：投标报价备案。
     */
    public PoPublicBidReq setTenderOfferRecord(String tenderOfferRecord) {
        this.tenderOfferRecord = tenderOfferRecord;
        return this;
    }

    /**
     * 是否备案。
     */
    public Boolean isRecord;

    /**
     * 获取：是否备案。
     */
    public Boolean getIsRecord() {
        return this.isRecord;
    }

    /**
     * 设置：是否备案。
     */
    public PoPublicBidReq setIsRecord(Boolean isRecord) {
        this.isRecord = isRecord;
        return this;
    }

    /**
     * 联系人名称备案。
     */
    public String contactNameRecord;

    /**
     * 获取：联系人名称备案。
     */
    public String getContactNameRecord() {
        return this.contactNameRecord;
    }

    /**
     * 设置：联系人名称备案。
     */
    public PoPublicBidReq setContactNameRecord(String contactNameRecord) {
        this.contactNameRecord = contactNameRecord;
        return this;
    }

    /**
     * 联系人手机备案。
     */
    public String contactMobileRecord;

    /**
     * 获取：联系人手机备案。
     */
    public String getContactMobileRecord() {
        return this.contactMobileRecord;
    }

    /**
     * 设置：联系人手机备案。
     */
    public PoPublicBidReq setContactMobileRecord(String contactMobileRecord) {
        this.contactMobileRecord = contactMobileRecord;
        return this;
    }

    /**
     * 联系人身份证备案。
     */
    public String contactIdcardRecord;

    /**
     * 获取：联系人身份证备案。
     */
    public String getContactIdcardRecord() {
        return this.contactIdcardRecord;
    }

    /**
     * 设置：联系人身份证备案。
     */
    public PoPublicBidReq setContactIdcardRecord(String contactIdcardRecord) {
        this.contactIdcardRecord = contactIdcardRecord;
        return this;
    }

    /**
     * 中标通知书。
     */
    public String bidWinNoticeFileGroupId;

    /**
     * 获取：中标通知书。
     */
    public String getBidWinNoticeFileGroupId() {
        return this.bidWinNoticeFileGroupId;
    }

    /**
     * 设置：中标通知书。
     */
    public PoPublicBidReq setBidWinNoticeFileGroupId(String bidWinNoticeFileGroupId) {
        this.bidWinNoticeFileGroupId = bidWinNoticeFileGroupId;
        return this;
    }

    // </editor-fold>

    // 实例方法：
    // <editor-fold>

    /**
     * 根据ID更新数据。ID自身不会更新。
     *
     * @param includeCols 更新时包含的列，空为包含所有。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @param refreshThis 更新后，是否刷新当前对象。刷新时将刷新所有列。
     */
    public void updateById(List<String> includeCols, List<String> excludeCols, boolean refreshThis) {
        modelHelper.updateById(includeCols, excludeCols, refreshThis, this.id);
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
    public static PoPublicBidReq insertData() {
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
    public static PoPublicBidReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PoPublicBidReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PoPublicBidReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        return modelHelper.selectByWhere(where, includeCols, excludeCols);
    }

    /**
     * 根据ID更新数据。若要更新ID自身，可在keyValueMap里为ID设置新值。
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
     * 根据ID列表更新数据。若要更新ID自身，可在keyValueMap里为ID设置新值。
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
     * 根据Where条件更新数据。若要更新ID自身，可在keyValueMap里为ID设置新值。
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
    public static void copyCols(PoPublicBidReq fromModel, PoPublicBidReq toModel, List<String> includeCols, List<String> excludeCols) {
        ModelHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}