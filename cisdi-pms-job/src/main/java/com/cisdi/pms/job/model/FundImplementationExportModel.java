package com.cisdi.pms.job.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 资金批复（落实）导出模板
 */
@Data
public class FundImplementationExportModel {

    @ExcelProperty("资金大类")
    @ColumnWidth(40)
    private String categoryName;

    @ExcelProperty("资金来源")
    @ColumnWidth(40)
    private String sourceName;

    @ExcelProperty("项目名称")
    @ColumnWidth(40)
    private String projectName;

    @ExcelProperty("批复金额")
    @ColumnWidth(40)
    private BigDecimal approvedAmount;

    @ExcelProperty("批复日期")
    @ColumnWidth(40)
    private String approvalTime;

    @ExcelProperty("备注")
    @ColumnWidth(40)
    private String remark;


}
