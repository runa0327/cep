package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import lombok.Data;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title FundReachExportModel
 * @package com.cisdi.pms.job.excel.model
 * @description
 * @date 2022/9/27
 */
@Data
public class FundReachExportModel {

    @ExcelProperty("资金大类")
    @ColumnWidth(value = 30)
    private String categoryName;

    @ExcelProperty("资金来源")
    @ColumnWidth(value = 30)
    private String sourceName;

    @ExcelProperty("项目名称")
    @ColumnWidth(value = 30)
    private String projectName;

    @ExcelProperty("到位次数")
    @ColumnWidth(value = 30)
    private String count;

    @ExcelProperty("本次资金到位类别")
    @ColumnWidth(value = 30)
    private String typeName;

    @ExcelProperty("本次到位金额")
    @ColumnWidth(value = 30)
    @ContentStyle(dataFormat = 2)
    private Double dwAmt;

    @ExcelProperty("累计到位金额")
    @ColumnWidth(value = 30)
    @ContentStyle(dataFormat = 2)
    private Double totalDwAmt;

    @ExcelProperty("到位日期")
    @ColumnWidth(value = 30)
    private String dwDate;

    @ExcelProperty("收款单位")
    @ColumnWidth(value = 30)
    private String unit;

    @ExcelProperty("收款银行")
    @ColumnWidth(value = 30)
    private String bank;

    @ExcelProperty("收款账户")
    @ColumnWidth(value = 30)
    private String account;

    @ExcelProperty("备注")
    @ColumnWidth(value = 30)
    private String remark;
}
