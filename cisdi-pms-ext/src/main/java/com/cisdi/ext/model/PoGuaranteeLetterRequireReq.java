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
 * 新增保函申请。
 */
public class PoGuaranteeLetterRequireReq {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PoGuaranteeLetterRequireReq> modelHelper = new ModelHelper<>("PO_GUARANTEE_LETTER_REQUIRE_REQ", new PoGuaranteeLetterRequireReq());

    // 实体常量：
    // <editor-fold>

    public static final String ENT_CODE = "PO_GUARANTEE_LETTER_REQUIRE_REQ";
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
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 费用类型。
         */
        public static final String PM_EXP_TYPE_ID = "PM_EXP_TYPE_ID";
        /**
         * 创建部门。
         */
        public static final String CRT_DEPT_ID = "CRT_DEPT_ID";
        /**
         * 合同金额。
         */
        public static final String CONTRACT_AMOUNT = "CONTRACT_AMOUNT";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 预付款比例。
         */
        public static final String ADVANCE_CHARGE_PERCENT = "ADVANCE_CHARGE_PERCENT";
        /**
         * 预付款金额。
         */
        public static final String ADVANCE_CHARGE_AMT = "ADVANCE_CHARGE_AMT";
        /**
         * 供应商。
         */
        public static final String SUPPLIER = "SUPPLIER";
        /**
         * 受益人。
         */
        public static final String BENEFICIARY = "BENEFICIARY";
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
         * 附件。
         */
        public static final String ATT_FILE_GROUP_ID = "ATT_FILE_GROUP_ID";
        /**
         * 保函材料。
         */
        public static final String GUARANTEE_FILE = "GUARANTEE_FILE";
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
    public PoGuaranteeLetterRequireReq setId(String id) {
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
    public PoGuaranteeLetterRequireReq setVer(Integer ver) {
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
    public PoGuaranteeLetterRequireReq setTs(LocalDateTime ts) {
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
    public PoGuaranteeLetterRequireReq setIsPreset(Boolean isPreset) {
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
    public PoGuaranteeLetterRequireReq setLastModiDt(LocalDateTime lastModiDt) {
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
    public PoGuaranteeLetterRequireReq setLastModiUserId(String lastModiUserId) {
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
    public PoGuaranteeLetterRequireReq setCode(String code) {
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
    public PoGuaranteeLetterRequireReq setName(String name) {
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
    public PoGuaranteeLetterRequireReq setRemark(String remark) {
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
    public PoGuaranteeLetterRequireReq setPmPrjId(String pmPrjId) {
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
    public PoGuaranteeLetterRequireReq setLkWfInstId(String lkWfInstId) {
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
    public PoGuaranteeLetterRequireReq setContractId(String contractId) {
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
    public PoGuaranteeLetterRequireReq setStatus(String status) {
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
    public PoGuaranteeLetterRequireReq setCrtUserId(String crtUserId) {
        this.crtUserId = crtUserId;
        return this;
    }

    /**
     * 费用类型。
     */
    public String pmExpTypeId;

    /**
     * 获取：费用类型。
     */
    public String getPmExpTypeId() {
        return this.pmExpTypeId;
    }

    /**
     * 设置：费用类型。
     */
    public PoGuaranteeLetterRequireReq setPmExpTypeId(String pmExpTypeId) {
        this.pmExpTypeId = pmExpTypeId;
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
    public PoGuaranteeLetterRequireReq setCrtDeptId(String crtDeptId) {
        this.crtDeptId = crtDeptId;
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
    public PoGuaranteeLetterRequireReq setContractAmount(Double contractAmount) {
        this.contractAmount = contractAmount;
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
    public PoGuaranteeLetterRequireReq setCrtDt(LocalDateTime crtDt) {
        this.crtDt = crtDt;
        return this;
    }

    /**
     * 预付款比例。
     */
    public String advanceChargePercent;

    /**
     * 获取：预付款比例。
     */
    public String getAdvanceChargePercent() {
        return this.advanceChargePercent;
    }

    /**
     * 设置：预付款比例。
     */
    public PoGuaranteeLetterRequireReq setAdvanceChargePercent(String advanceChargePercent) {
        this.advanceChargePercent = advanceChargePercent;
        return this;
    }

    /**
     * 预付款金额。
     */
    public Double advanceChargeAmt;

    /**
     * 获取：预付款金额。
     */
    public Double getAdvanceChargeAmt() {
        return this.advanceChargeAmt;
    }

    /**
     * 设置：预付款金额。
     */
    public PoGuaranteeLetterRequireReq setAdvanceChargeAmt(Double advanceChargeAmt) {
        this.advanceChargeAmt = advanceChargeAmt;
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
    public PoGuaranteeLetterRequireReq setSupplier(String supplier) {
        this.supplier = supplier;
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
    public PoGuaranteeLetterRequireReq setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
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
    public PoGuaranteeLetterRequireReq setGuaranteeLetterTypeId(String guaranteeLetterTypeId) {
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
    public PoGuaranteeLetterRequireReq setGuaranteeMechanism(String guaranteeMechanism) {
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
    public PoGuaranteeLetterRequireReq setGuaranteeCode(String guaranteeCode) {
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
    public PoGuaranteeLetterRequireReq setGuaranteeAmt(Double guaranteeAmt) {
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
    public PoGuaranteeLetterRequireReq setGuaranteeStartDate(LocalDate guaranteeStartDate) {
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
    public PoGuaranteeLetterRequireReq setGuaranteeEndDate(LocalDate guaranteeEndDate) {
        this.guaranteeEndDate = guaranteeEndDate;
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
    public PoGuaranteeLetterRequireReq setAttFileGroupId(String attFileGroupId) {
        this.attFileGroupId = attFileGroupId;
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
    public PoGuaranteeLetterRequireReq setGuaranteeFile(String guaranteeFile) {
        this.guaranteeFile = guaranteeFile;
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
    public PoGuaranteeLetterRequireReq setGuaranteeResultFile(String guaranteeResultFile) {
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
    public PoGuaranteeLetterRequireReq setCommentPublishUser(String commentPublishUser) {
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
    public PoGuaranteeLetterRequireReq setCommentPublishDate(LocalDate commentPublishDate) {
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
    public PoGuaranteeLetterRequireReq setCommentPublishContent(String commentPublishContent) {
        this.commentPublishContent = commentPublishContent;
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
    public PoGuaranteeLetterRequireReq setFinancePublishUser(String financePublishUser) {
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
    public PoGuaranteeLetterRequireReq setFinancePublishDate(LocalDate financePublishDate) {
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
    public PoGuaranteeLetterRequireReq setFinanceMessage(String financeMessage) {
        this.financeMessage = financeMessage;
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
    public static PoGuaranteeLetterRequireReq newData() {
        return modelHelper.newData();
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PoGuaranteeLetterRequireReq insertData() {
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
    public static PoGuaranteeLetterRequireReq selectById(String id, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PoGuaranteeLetterRequireReq> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
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
    public static List<PoGuaranteeLetterRequireReq> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
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
    public static void copyCols(PoGuaranteeLetterRequireReq fromModel, PoGuaranteeLetterRequireReq toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}