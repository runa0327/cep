package com.cisdi.pms.job.domain.process;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.cisdi.pms.job.domain.base.BaseCommon;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 新增保函
 */
@Data
@ExcelIgnoreUnannotated
public class PoGuaranteeLetterRequireReq extends BaseCommon {

    // id
    private String id;

    // 流程标题
    @ExcelProperty("流程标题")
    private String processInstanceName;

    // 创建人
    @ExcelProperty("创建人")
    private String processInstanceCreateBy;

    // 所属部门
    @ExcelProperty("所属部门")
    private String deptName;

    // 所属部门id
    private String deptId;

    // 创建时间
    @ExcelProperty("创建时间")
    private String processInstanceCreateDate;

    // 业主单位id
    private String companyId;

    // 业主单位名称
    @ExcelProperty("业主单位名称")
    private String customerUnitName;

    // 项目来源
    @ExcelProperty("项目来源")
    private String projectSource;

    // 项目id
    private String projectId;
    private String projectNameWr;

    // 项目名称
    @ExcelProperty("项目名称")
    private String projectName;

    // 合同来源
    @ExcelProperty("合同来源")
    private String contractSource;

    // 合同名称
    @ExcelProperty("合同名称")
    private String contractName;

    // 合同id
    private String contractId;

    // 费用id
    private String pmExpTypeIds;

    // 费用名称
    @ExcelProperty("费用名称")
    private String feeTypeName;

    // 合同金额
    @ExcelProperty("合同金额")
    private BigDecimal contractAmt;

    // 供应商名称
    @ExcelProperty("供应商名称")
    private String supplier;

    // 受益人
    @ExcelProperty("受益人")
    private String beneficiary;

    // 保函类型id
    private String guaranteeLetterTypeId;

    // 保函类型名称
    @ExcelProperty("保函类型名称")
    private String guaranteeLetterTypeName;

    // 保函开立机构
    @ExcelProperty("保函开立机构")
    private String guaranteeMechanism;

    // 保函编号/单号
    @ExcelProperty("保函编号/单号")
    private String guaranteeCode;

    // 担保金额
    @ExcelProperty("担保金额")
    private BigDecimal guaranteeAmt;
    private BigDecimal guaranteeAmtMin;
    private BigDecimal guaranteeAmtMax;

    // 保函开立日期
    @ExcelProperty("保函开立日期")
    private String guaranteeStartDate;

    // 保函到期类型id
    private String guaranteeEndTypeId;

    // 保函到期类型名称
    @ExcelProperty("保函到期类型名称")
    private String guaranteeEndTypeName;

    // 保函到期日期
    @ExcelProperty("保函到期日期")
    private String guaranteeEndDate;

    // 保函到期说明
    @ExcelProperty("保函到期说明")
    private String guaranteeEndRemark;

    // 保函原件日期
    @ExcelProperty("保函原件日期")
    private String guaranteeFirstDate;

    // 审批意见
    @ExcelProperty("审批意见")
    private String checkRemark;

    // 保函名称
    private String guaranteeName;

}
