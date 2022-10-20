package com.cisdi.pms.job.export.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import lombok.Data;

/**
 * 资金批复（落实）导出模板
 */
@Data
public class FundImplementationExportModel {

    @ExcelProperty("资金大类")
    @ColumnWidth(30)
    private String categoryName;

    @ExcelProperty("资金来源")
    @ColumnWidth(30)
    private String sourceName;

    @ExcelProperty("项目名称")
    @ColumnWidth(30)
    private String projectName;

    @ExcelProperty("批复金额")
    @ColumnWidth(30)
    @ContentStyle(dataFormat = 2)
    private Double approvedAmount;

    @ExcelProperty("批复日期")
    @ColumnWidth(30)
    private String approvalTime;

    @ExcelProperty("备注")
    @ColumnWidth(30)
    private String remark;


}
