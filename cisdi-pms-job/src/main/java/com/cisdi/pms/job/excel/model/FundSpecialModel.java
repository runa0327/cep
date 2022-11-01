package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title FundSpecialModel
 * @package com.cisdi.pms.job.excel.model
 * @description 专项资金支付明细
 * @date 2022/10/31
 */
@Data
public class FundSpecialModel {

    @ExcelProperty("资金来源")
    private String sourceName;

    @ExcelProperty("项目业主")
    private String projectOwner;

    @ExcelProperty("代管单位")
    private String escrowUnit;

    @ExcelProperty("项目名称")
    private String projectName;

    @ExcelProperty("批复金额")
    private BigDecimal approvedAmount;

    @ExcelProperty("累计到位金额")
    private BigDecimal ljdwAmt;

    @ExcelProperty("其中：累计到位建设资金")
    private BigDecimal jsAmt;

    @ExcelProperty("其中：累计到位征拆资金")
    private BigDecimal zcAmt;

    @ExcelProperty("累计支付金额")
    private BigDecimal payAmt;

    @ExcelProperty("到位剩余资金")
    private BigDecimal syAmt;

    @ExcelProperty("未到位资金")
    private BigDecimal wdwAmt;

    @ExcelProperty("期数")
    private String periods;

    @ExcelProperty("费用名称")
    private String feeName;

    @ExcelProperty("付款单位")
    private String payUnit;

    @ExcelProperty("付款银行")
    private String payBank;

    @ExcelProperty("付款账号")
    private String payAccount;

    @ExcelProperty("支付日期")
    private Date payDate;

    @ExcelProperty("本期审定应付金额")
    private BigDecimal yfAmt;

    @ExcelProperty("本期已付金额")
    private BigDecimal hasPayAmt;

    @ExcelProperty("收款单位")
    private String skUnit;

    @ExcelProperty("保函情况")
    private String bhqk;

    @ExcelProperty("单据号")
    private String billNo;

    @ExcelProperty("审批状态")
    private String status;

    @ExcelProperty("是否为银行贷款项目")
    private String izDk;

}
