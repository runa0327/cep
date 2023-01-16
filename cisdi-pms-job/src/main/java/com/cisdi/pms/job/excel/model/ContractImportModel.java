package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author dlt
 * @date 2023/1/6 周五
 */
@Data
public class ContractImportModel {

    private String projectId;
    @ExcelProperty("项目")
    private String projectName;

    @ExcelProperty("合同名称")
    private String contractName;

    @ExcelProperty("合同相对方")
    private String winBidUnit;

    @ExcelProperty("签订时间")
    private String signDate;

    //招标类别 采购方式
    @ExcelProperty("中标单位确定方式（公开招标/电子平台/中介服务超市/代管确定）")
    private String buyType;

    @ExcelProperty("概评金额")
    private String estimateAmt;

    @ExcelProperty("财评金额")
    private String financialAmt;

    @ExcelProperty("合同金额")
    private String amtIncludeTax;

    @ExcelProperty("已支付金额（每期支付详情见批注）")
    private String payedAmt;

    @ExcelProperty("累计支付比例（%）")
    private String payedPercent;

    @ExcelProperty("联系人")
    private String linkMan;

    @ExcelProperty("备注")
    private String remark;
}

