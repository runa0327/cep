package com.cisdi.pms.job.domain.process;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PoGuaranteeLetterReturnReq {

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

    // 担保金额中文大写
    private String guaranteeAmtChina;

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

    // 保函申请人
    private String applicant;

    // 申请保函退还原因
    private String reason;

    // 备注
    private String remark;

    //经办部门意见
    private String commentOne;

    //财务金融部会计意见
    private String commentTwo;

    //财务金融部会计意见
    private String commentThree;

    //财务金融部负责人意见
    private String commentFour;

    //财务分管领导意见
    private String commentFive;
}
