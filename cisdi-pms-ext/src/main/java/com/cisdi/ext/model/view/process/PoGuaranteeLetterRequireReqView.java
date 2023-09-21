package com.cisdi.ext.model.view.process;

import com.cisdi.ext.model.BasePageEntity;

import java.math.BigDecimal;

/**
 * 新增保函-台账-视图
 */
public class PoGuaranteeLetterRequireReqView extends BasePageEntity {

    //id
    private String id;

    //保函类型id
    private String guaranteeLetterTypeId;

    // 保函类型名称
    private String guaranteeLetterTypeName;

    //费用类型id
    private String pmExpTypeIds;

    // 费用类型名称
    private String pmExpTypeName;

    // 项目id
    private String projectId;

    // 项目名称
    private String projectName;

    // 供应商完整名称
    private String supplier;

    //担保银行/保险公司
    private String guaranteeMechanism;

    //保函编号/保单号
    private String guaranteeCode;

    //担保金额（元）
    private BigDecimal guaranteeAmt;
    private BigDecimal guaranteeAmtMin;
    private BigDecimal guaranteeAmtMax;

    //签订日期
    private String guaranteeStartDate;

    //担保期限
    private String guaranteeEndDate;

    //备注
    private String remarkOne;

    //受益人
    private String beneficiary;

    // 创建人id
    private String createUserId;

    // 创建人名称
    private String createUserName;

    // 部门
    private String deptId;

    // 部门名称
    private String deptName;

    // 创建日期
    private String createDate;
    private String createDateMin;
    private String createDateMax;

    // 合同签订公司
    private String companyId;
    private String companyName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuaranteeLetterTypeId() {
        return guaranteeLetterTypeId;
    }

    public void setGuaranteeLetterTypeId(String guaranteeLetterTypeId) {
        this.guaranteeLetterTypeId = guaranteeLetterTypeId;
    }

    public String getGuaranteeLetterTypeName() {
        return guaranteeLetterTypeName;
    }

    public void setGuaranteeLetterTypeName(String guaranteeLetterTypeName) {
        this.guaranteeLetterTypeName = guaranteeLetterTypeName;
    }

    public String getPmExpTypeIds() {
        return pmExpTypeIds;
    }

    public void setPmExpTypeIds(String pmExpTypeIds) {
        this.pmExpTypeIds = pmExpTypeIds;
    }

    public String getPmExpTypeName() {
        return pmExpTypeName;
    }

    public void setPmExpTypeName(String pmExpTypeName) {
        this.pmExpTypeName = pmExpTypeName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getGuaranteeMechanism() {
        return guaranteeMechanism;
    }

    public void setGuaranteeMechanism(String guaranteeMechanism) {
        this.guaranteeMechanism = guaranteeMechanism;
    }

    public String getGuaranteeCode() {
        return guaranteeCode;
    }

    public void setGuaranteeCode(String guaranteeCode) {
        this.guaranteeCode = guaranteeCode;
    }

    public BigDecimal getGuaranteeAmt() {
        return guaranteeAmt;
    }

    public void setGuaranteeAmt(BigDecimal guaranteeAmt) {
        this.guaranteeAmt = guaranteeAmt;
    }

    public BigDecimal getGuaranteeAmtMin() {
        return guaranteeAmtMin;
    }

    public void setGuaranteeAmtMin(BigDecimal guaranteeAmtMin) {
        this.guaranteeAmtMin = guaranteeAmtMin;
    }

    public BigDecimal getGuaranteeAmtMax() {
        return guaranteeAmtMax;
    }

    public void setGuaranteeAmtMax(BigDecimal guaranteeAmtMax) {
        this.guaranteeAmtMax = guaranteeAmtMax;
    }

    public String getGuaranteeStartDate() {
        return guaranteeStartDate;
    }

    public void setGuaranteeStartDate(String guaranteeStartDate) {
        this.guaranteeStartDate = guaranteeStartDate;
    }

    public String getGuaranteeEndDate() {
        return guaranteeEndDate;
    }

    public void setGuaranteeEndDate(String guaranteeEndDate) {
        this.guaranteeEndDate = guaranteeEndDate;
    }

    public String getRemarkOne() {
        return remarkOne;
    }

    public void setRemarkOne(String remarkOne) {
        this.remarkOne = remarkOne;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateMin() {
        return createDateMin;
    }

    public void setCreateDateMin(String createDateMin) {
        this.createDateMin = createDateMin;
    }

    public String getCreateDateMax() {
        return createDateMax;
    }

    public void setCreateDateMax(String createDateMax) {
        this.createDateMax = createDateMax;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
