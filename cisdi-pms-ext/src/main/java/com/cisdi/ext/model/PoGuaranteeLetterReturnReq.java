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
 * 保函退还申请。
 */
public class PoGuaranteeLetterReturnReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PoGuaranteeLetterReturnReq> modelHelper = new ModelHelper<>("PO_GUARANTEE_LETTER_RETURN_REQ", new PoGuaranteeLetterReturnReq());

    // 实体常量：
    // <editor-fold>

    public static final String ENT_CODE = "PO_GUARANTEE_LETTER_RETURN_REQ";
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
         * 保函名称。
         */
        public static final String GUARANTEE_ID = "GUARANTEE_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 供应商。
         */
        public static final String SUPPLIER = "SUPPLIER";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 受益人。
         */
        public static final String BENEFICIARY = "BENEFICIARY";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 保函类型。
         */
        public static final String GUARANTEE_LETTER_TYPE_ID = "GUARANTEE_LETTER_TYPE_ID";
        /**
         * 保函机构。
         */
        public static final String GUARANTEE_MECHANISM = "GUARANTEE_MECHANISM";
        /**
         * 保函编号。
         */
        public static final String GUARANTEE_CODE = "GUARANTEE_CODE";
        /**
         * 保函金额。
         */
        public static final String GUARANTEE_AMT = "GUARANTEE_AMT";
        /**
         * 保函开始日期。
         */
        public static final String GUARANTEE_START_DATE = "GUARANTEE_START_DATE";
        /**
         * 保函结束日期。
         */
        public static final String GUARANTEE_END_DATE = "GUARANTEE_END_DATE";
        /**
         * 保函材料。
         */
        public static final String GUARANTEE_FILE = "GUARANTEE_FILE";
        /**
         * 原因。
         */
        public static final String REASON = "REASON";
        /**
         * 原因说明。
         */
        public static final String REASON_EXPLAIN = "REASON_EXPLAIN";
        /**
         * 部门审批人。
         */
        public static final String DEPT_APPROVAL_USER = "DEPT_APPROVAL_USER";
        /**
         * 部门审批日期。
         */
        public static final String DEPT_APPROVAL_DATE = "DEPT_APPROVAL_DATE";
        /**
         * 部门审批意见。
         */
        public static final String DEPT_APPROVAL_COMMENT = "DEPT_APPROVAL_COMMENT";
        /**
         * 财务审批人1。
         */
        public static final String FINANCE_APPROVAL_USER_ONE = "FINANCE_APPROVAL_USER_ONE";
        /**
         * 财务审批日期1。
         */
        public static final String FINANCE_APPROVAL_DATE_ONE = "FINANCE_APPROVAL_DATE_ONE";
        /**
         * 财务审批意见1。
         */
        public static final String FINANCE_APPROVAL_COMMENT_ONE = "FINANCE_APPROVAL_COMMENT_ONE";
        /**
         * 财务审批人2。
         */
        public static final String FINANCE_APPROVAL_USER_TWO = "FINANCE_APPROVAL_USER_TWO";
        /**
         * 财务审批日期2。
         */
        public static final String FINANCE_APPROVAL_DATE_TWO = "FINANCE_APPROVAL_DATE_TWO";
        /**
         * 财务审批意见2。
         */
        public static final String FINANCE_APPROVAL_COMMENT_TWO = "FINANCE_APPROVAL_COMMENT_TWO";
        /**
         * 财务审批人3。
         */
        public static final String FINANCE_APPROVAL_USER_THREE = "FINANCE_APPROVAL_USER_THREE";
        /**
         * 财务审批日期3。
         */
        public static final String FINANCE_APPROVAL_DATE_THREE = "FINANCE_APPROVAL_DATE_THREE";
        /**
         * 财务审批意见3。
         */
        public static final String FINANCE_APPROVAL_COMMENT_THREE = "FINANCE_APPROVAL_COMMENT_THREE";
        /**
         * 财务审批领导人。
         */
        public static final String FINANCE_LEADER_USER = "FINANCE_LEADER_USER";
        /**
         * 财务领导审批日期。
         */
        public static final String FINANCE_APPROVAL_LEADER_DATE = "FINANCE_APPROVAL_LEADER_DATE";
        /**
         * 财务领导审批意见。
         */
        public static final String FINANCE_APPROVAL_LEADER_COMMENT = "FINANCE_APPROVAL_LEADER_COMMENT";
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
    public PoGuaranteeLetterReturnReq setId(String id) {
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
    public PoGuaranteeLetterReturnReq setVer(Integer ver) {
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
    public PoGuaranteeLetterReturnReq setTs(LocalDateTime ts) {
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
    public PoGuaranteeLetterReturnReq setIsPreset(Boolean isPreset) {
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
    public PoGuaranteeLetterReturnReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PoGuaranteeLetterReturnReq setLastModiUserId(String lastModiUserId) {
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
    public PoGuaranteeLetterReturnReq setCode(String code) {
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
    public PoGuaranteeLetterReturnReq setName(String name) {
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
    public PoGuaranteeLetterReturnReq setRemark(String remark) {
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
    public PoGuaranteeLetterReturnReq setPmPrjId(String pmPrjId) {
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
    public PoGuaranteeLetterReturnReq setLkWfInstId(String lkWfInstId) {
        this.lkWfInstId = lkWfInstId;
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
    public PoGuaranteeLetterReturnReq setGuaranteeId(String guaranteeId) {
        this.guaranteeId = guaranteeId;
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
    public PoGuaranteeLetterReturnReq setStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * 供应商。
     */
    public String supplier;

    /**
     * 获取：供应商。
     */
    public String getSupplier() {
        return this.supplier;
    }

    /**
     * 设置：供应商。
     */
    public PoGuaranteeLetterReturnReq setSupplier(String supplier) {
        this.supplier = supplier;
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
    public PoGuaranteeLetterReturnReq setCrtUserId(String crtUserId) {
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
    public PoGuaranteeLetterReturnReq setCrtDeptId(String crtDeptId) {
        this.crtDeptId = crtDeptId;
        return this;
    }

    /**
     * 受益人。
     */
    public String beneficiary;

    /**
     * 获取：受益人。
     */
    public String getBeneficiary() {
        return this.beneficiary;
    }

    /**
     * 设置：受益人。
     */
    public PoGuaranteeLetterReturnReq setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
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
    public PoGuaranteeLetterReturnReq setCrtDt(LocalDateTime crtDt) {
        this.crtDt = crtDt;
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
    public PoGuaranteeLetterReturnReq setGuaranteeLetterTypeId(String guaranteeLetterTypeId) {
        this.guaranteeLetterTypeId = guaranteeLetterTypeId;
        return this;
    }

    /**
     * 保函机构。
     */
    public String guaranteeMechanism;

    /**
     * 获取：保函机构。
     */
    public String getGuaranteeMechanism() {
        return this.guaranteeMechanism;
    }

    /**
     * 设置：保函机构。
     */
    public PoGuaranteeLetterReturnReq setGuaranteeMechanism(String guaranteeMechanism) {
        this.guaranteeMechanism = guaranteeMechanism;
        return this;
    }

    /**
     * 保函编号。
     */
    public String guaranteeCode;

    /**
     * 获取：保函编号。
     */
    public String getGuaranteeCode() {
        return this.guaranteeCode;
    }

    /**
     * 设置：保函编号。
     */
    public PoGuaranteeLetterReturnReq setGuaranteeCode(String guaranteeCode) {
        this.guaranteeCode = guaranteeCode;
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
    public PoGuaranteeLetterReturnReq setGuaranteeAmt(Double guaranteeAmt) {
        this.guaranteeAmt = guaranteeAmt;
        return this;
    }

    /**
     * 保函开始日期。
     */
    public LocalDate guaranteeStartDate;

    /**
     * 获取：保函开始日期。
     */
    public LocalDate getGuaranteeStartDate() {
        return this.guaranteeStartDate;
    }

    /**
     * 设置：保函开始日期。
     */
    public PoGuaranteeLetterReturnReq setGuaranteeStartDate(LocalDate guaranteeStartDate) {
        this.guaranteeStartDate = guaranteeStartDate;
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
    public PoGuaranteeLetterReturnReq setGuaranteeEndDate(LocalDate guaranteeEndDate) {
        this.guaranteeEndDate = guaranteeEndDate;
        return this;
    }

    /**
     * 保函材料。
     */
    public String guaranteeFile;

    /**
     * 获取：保函材料。
     */
    public String getGuaranteeFile() {
        return this.guaranteeFile;
    }

    /**
     * 设置：保函材料。
     */
    public PoGuaranteeLetterReturnReq setGuaranteeFile(String guaranteeFile) {
        this.guaranteeFile = guaranteeFile;
        return this;
    }

    /**
     * 原因。
     */
    public String reason;

    /**
     * 获取：原因。
     */
    public String getReason() {
        return this.reason;
    }

    /**
     * 设置：原因。
     */
    public PoGuaranteeLetterReturnReq setReason(String reason) {
        this.reason = reason;
        return this;
    }

    /**
     * 原因说明。
     */
    public String reasonExplain;

    /**
     * 获取：原因说明。
     */
    public String getReasonExplain() {
        return this.reasonExplain;
    }

    /**
     * 设置：原因说明。
     */
    public PoGuaranteeLetterReturnReq setReasonExplain(String reasonExplain) {
        this.reasonExplain = reasonExplain;
        return this;
    }

    /**
     * 部门审批人。
     */
    public String deptApprovalUser;

    /**
     * 获取：部门审批人。
     */
    public String getDeptApprovalUser() {
        return this.deptApprovalUser;
    }

    /**
     * 设置：部门审批人。
     */
    public PoGuaranteeLetterReturnReq setDeptApprovalUser(String deptApprovalUser) {
        this.deptApprovalUser = deptApprovalUser;
        return this;
    }

    /**
     * 部门审批日期。
     */
    public LocalDate deptApprovalDate;

    /**
     * 获取：部门审批日期。
     */
    public LocalDate getDeptApprovalDate() {
        return this.deptApprovalDate;
    }

    /**
     * 设置：部门审批日期。
     */
    public PoGuaranteeLetterReturnReq setDeptApprovalDate(LocalDate deptApprovalDate) {
        this.deptApprovalDate = deptApprovalDate;
        return this;
    }

    /**
     * 部门审批意见。
     */
    public String deptApprovalComment;

    /**
     * 获取：部门审批意见。
     */
    public String getDeptApprovalComment() {
        return this.deptApprovalComment;
    }

    /**
     * 设置：部门审批意见。
     */
    public PoGuaranteeLetterReturnReq setDeptApprovalComment(String deptApprovalComment) {
        this.deptApprovalComment = deptApprovalComment;
        return this;
    }

    /**
     * 财务审批人1。
     */
    public String financeApprovalUserOne;

    /**
     * 获取：财务审批人1。
     */
    public String getFinanceApprovalUserOne() {
        return this.financeApprovalUserOne;
    }

    /**
     * 设置：财务审批人1。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalUserOne(String financeApprovalUserOne) {
        this.financeApprovalUserOne = financeApprovalUserOne;
        return this;
    }

    /**
     * 财务审批日期1。
     */
    public LocalDate financeApprovalDateOne;

    /**
     * 获取：财务审批日期1。
     */
    public LocalDate getFinanceApprovalDateOne() {
        return this.financeApprovalDateOne;
    }

    /**
     * 设置：财务审批日期1。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalDateOne(LocalDate financeApprovalDateOne) {
        this.financeApprovalDateOne = financeApprovalDateOne;
        return this;
    }

    /**
     * 财务审批意见1。
     */
    public String financeApprovalCommentOne;

    /**
     * 获取：财务审批意见1。
     */
    public String getFinanceApprovalCommentOne() {
        return this.financeApprovalCommentOne;
    }

    /**
     * 设置：财务审批意见1。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalCommentOne(String financeApprovalCommentOne) {
        this.financeApprovalCommentOne = financeApprovalCommentOne;
        return this;
    }

    /**
     * 财务审批人2。
     */
    public String financeApprovalUserTwo;

    /**
     * 获取：财务审批人2。
     */
    public String getFinanceApprovalUserTwo() {
        return this.financeApprovalUserTwo;
    }

    /**
     * 设置：财务审批人2。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalUserTwo(String financeApprovalUserTwo) {
        this.financeApprovalUserTwo = financeApprovalUserTwo;
        return this;
    }

    /**
     * 财务审批日期2。
     */
    public LocalDate financeApprovalDateTwo;

    /**
     * 获取：财务审批日期2。
     */
    public LocalDate getFinanceApprovalDateTwo() {
        return this.financeApprovalDateTwo;
    }

    /**
     * 设置：财务审批日期2。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalDateTwo(LocalDate financeApprovalDateTwo) {
        this.financeApprovalDateTwo = financeApprovalDateTwo;
        return this;
    }

    /**
     * 财务审批意见2。
     */
    public String financeApprovalCommentTwo;

    /**
     * 获取：财务审批意见2。
     */
    public String getFinanceApprovalCommentTwo() {
        return this.financeApprovalCommentTwo;
    }

    /**
     * 设置：财务审批意见2。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalCommentTwo(String financeApprovalCommentTwo) {
        this.financeApprovalCommentTwo = financeApprovalCommentTwo;
        return this;
    }

    /**
     * 财务审批人3。
     */
    public String financeApprovalUserThree;

    /**
     * 获取：财务审批人3。
     */
    public String getFinanceApprovalUserThree() {
        return this.financeApprovalUserThree;
    }

    /**
     * 设置：财务审批人3。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalUserThree(String financeApprovalUserThree) {
        this.financeApprovalUserThree = financeApprovalUserThree;
        return this;
    }

    /**
     * 财务审批日期3。
     */
    public LocalDate financeApprovalDateThree;

    /**
     * 获取：财务审批日期3。
     */
    public LocalDate getFinanceApprovalDateThree() {
        return this.financeApprovalDateThree;
    }

    /**
     * 设置：财务审批日期3。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalDateThree(LocalDate financeApprovalDateThree) {
        this.financeApprovalDateThree = financeApprovalDateThree;
        return this;
    }

    /**
     * 财务审批意见3。
     */
    public String financeApprovalCommentThree;

    /**
     * 获取：财务审批意见3。
     */
    public String getFinanceApprovalCommentThree() {
        return this.financeApprovalCommentThree;
    }

    /**
     * 设置：财务审批意见3。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalCommentThree(String financeApprovalCommentThree) {
        this.financeApprovalCommentThree = financeApprovalCommentThree;
        return this;
    }

    /**
     * 财务审批领导人。
     */
    public String financeLeaderUser;

    /**
     * 获取：财务审批领导人。
     */
    public String getFinanceLeaderUser() {
        return this.financeLeaderUser;
    }

    /**
     * 设置：财务审批领导人。
     */
    public PoGuaranteeLetterReturnReq setFinanceLeaderUser(String financeLeaderUser) {
        this.financeLeaderUser = financeLeaderUser;
        return this;
    }

    /**
     * 财务领导审批日期。
     */
    public LocalDate financeApprovalLeaderDate;

    /**
     * 获取：财务领导审批日期。
     */
    public LocalDate getFinanceApprovalLeaderDate() {
        return this.financeApprovalLeaderDate;
    }

    /**
     * 设置：财务领导审批日期。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalLeaderDate(LocalDate financeApprovalLeaderDate) {
        this.financeApprovalLeaderDate = financeApprovalLeaderDate;
        return this;
    }

    /**
     * 财务领导审批意见。
     */
    public String financeApprovalLeaderComment;

    /**
     * 获取：财务领导审批意见。
     */
    public String getFinanceApprovalLeaderComment() {
        return this.financeApprovalLeaderComment;
    }

    /**
     * 设置：财务领导审批意见。
     */
    public PoGuaranteeLetterReturnReq setFinanceApprovalLeaderComment(String financeApprovalLeaderComment) {
        this.financeApprovalLeaderComment = financeApprovalLeaderComment;
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
    public static PoGuaranteeLetterReturnReq newData() {
        return modelHelper.newData();
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PoGuaranteeLetterReturnReq insertData() {
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
    public static PoGuaranteeLetterReturnReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PoGuaranteeLetterReturnReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PoGuaranteeLetterReturnReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
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
    public static void copyCols(PoGuaranteeLetterReturnReq fromModel, PoGuaranteeLetterReturnReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}