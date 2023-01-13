package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import lombok.Data;

/**
 * 资金支付明细
 * @author hgh
 * @date 2022/10/31
 * @apiNote
 */

@Data
public class FundPaymentImportModel {

    @ExcelProperty("序号")
    @ColumnWidth(value = 30)
    private String serialNumber;

    @ExcelProperty("资金大类")
    @ColumnWidth(value = 30)
    private String fundCategoryFirst;

    @ExcelProperty("资金来源")
    @ColumnWidth(value = 30)
    private String fundImplementationVId;

    @ExcelProperty("到位资金类别")
    @ColumnWidth(value = 30)
    private String fundReachCategory;

    @ExcelProperty("业主单位")
    @ColumnWidth(value = 30)
    private String customerUnit;

    @ExcelProperty("项目名称")
    @ColumnWidth(value = 30)
    private String pmPrjId;

    @ExcelProperty("费用大类")
    @ColumnWidth(value = 30)
    private String costCategoryId;

    @ExcelProperty("期数")
    @ColumnWidth(value = 30)
    private String nper;

    @ExcelProperty("费用明细")
    @ColumnWidth(value = 30)
    @ContentStyle(dataFormat = 2)
    private String feeDetail;

    @ExcelProperty("支付金额")
    @ColumnWidth(value = 30)
    @ContentStyle(dataFormat = 2)
    private String payAmt;

    @ExcelProperty("付款单位")
    @ColumnWidth(value = 30)
    private String payUnit;

    @ExcelProperty("收款单位")
    @ColumnWidth(value = 30)
    private String payee;

    @ExcelProperty("支付银行")
    @ColumnWidth(value = 30)
    private String payBank;

    @ExcelProperty("支付账户")
    @ColumnWidth(value = 30)
    private String payAccount;

    @ExcelProperty("收款银行")
    @ColumnWidth(value = 30)
    private String receiptBank;

    @ExcelProperty("收款账户")
    @ColumnWidth(value = 30)
    private String receiptAccount;

    @ExcelProperty("所属账套")
    @ColumnWidth(value = 30)
    private String accountSet;

    @ExcelProperty("凭证号")
    @ColumnWidth(value = 30)
    private String voucherNum;

    @ExcelProperty("备注")
    @ColumnWidth(value = 30)
    private String remarke;

}
