package com.cisdi.ext.model.view.process;

import com.cisdi.ext.model.BasePageEntity;

import java.math.BigDecimal;

/**
 * 保函退还申请
 */
public class PoGuaranteeLetterReturnReqView extends BasePageEntity {

    //id
    private String id;

    // 保函类型id
    private String guaranteeLetterTypeId;

    // 保函类型名称
    private String guaranteeLetterTypeName;

    // 费用类型id
    private String guaranteeCostTypeId;

    // 费用类型名称
    private String guaranteeCostTypeName;

    // 项目id
    private String projectId;

    // 项目名称
    private String projectName;

    // 担保银行/保险公司
    private String guaranteeMechanism;

    // 保函编号/保单号
    private String guaranteeCode;

    //担保金额（元）
    private BigDecimal guaranteeAmt;
    private BigDecimal guaranteeAmtMin;
    private BigDecimal guaranteeAmtMax;

    // 签订日期-保函开立日期
    private String guaranteeStartDate;
    private String guaranteeStartDateMin;
    private String guaranteeStartDateMax;

    // 担保期限-GUARANTEE_END_DATE
    private String guaranteeEndDate;
    private String guaranteeEndDateMax;
    private String guaranteeEndDateMin;

    // 备注
    private String remarkLongOne;

    // 受益人
    private String author;

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

    public String getGuaranteeCostTypeId() {
        return guaranteeCostTypeId;
    }

    public void setGuaranteeCostTypeId(String guaranteeCostTypeId) {
        this.guaranteeCostTypeId = guaranteeCostTypeId;
    }

    public String getGuaranteeCostTypeName() {
        return guaranteeCostTypeName;
    }

    public void setGuaranteeCostTypeName(String guaranteeCostTypeName) {
        this.guaranteeCostTypeName = guaranteeCostTypeName;
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

    public String getGuaranteeStartDateMin() {
        return guaranteeStartDateMin;
    }

    public void setGuaranteeStartDateMin(String guaranteeStartDateMin) {
        this.guaranteeStartDateMin = guaranteeStartDateMin;
    }

    public String getGuaranteeStartDateMax() {
        return guaranteeStartDateMax;
    }

    public void setGuaranteeStartDateMax(String guaranteeStartDateMax) {
        this.guaranteeStartDateMax = guaranteeStartDateMax;
    }

    public String getGuaranteeEndDate() {
        return guaranteeEndDate;
    }

    public void setGuaranteeEndDate(String guaranteeEndDate) {
        this.guaranteeEndDate = guaranteeEndDate;
    }

    public String getGuaranteeEndDateMax() {
        return guaranteeEndDateMax;
    }

    public void setGuaranteeEndDateMax(String guaranteeEndDateMax) {
        this.guaranteeEndDateMax = guaranteeEndDateMax;
    }

    public String getGuaranteeEndDateMin() {
        return guaranteeEndDateMin;
    }

    public void setGuaranteeEndDateMin(String guaranteeEndDateMin) {
        this.guaranteeEndDateMin = guaranteeEndDateMin;
    }

    public String getRemarkLongOne() {
        return remarkLongOne;
    }

    public void setRemarkLongOne(String remarkLongOne) {
        this.remarkLongOne = remarkLongOne;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
