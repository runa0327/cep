package com.cisdi.pms.job.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title FundReachExportModel
 * @package com.cisdi.pms.job.model
 * @description
 * @date 2022/9/27
 */
@Data
public class FundReachExportModel {

    @ExcelProperty("资金大类")
    @ColumnWidth(value = 40)
    private String categoryName;

    @ExcelProperty("资金来源")
    private String sourceName;

    @ExcelProperty("项目名称")
    private String projectName;

    @ExcelProperty("到位次数")
    private String count;

    @ExcelProperty("本次资金到位类别")
    private String typeName;

    @ExcelProperty("本次到位金额")
    private String dwAmt;

    @ExcelProperty("累计到位金额")
    private String totalDwAmt;

    @ExcelProperty("到位日期")
    private String dwDate;

    @ExcelProperty("收款单位")
    private String unit;

    @ExcelProperty("收款银行")
    private String bank;

    @ExcelProperty("收款账户")
    private String account;

    @ExcelProperty("备注")
    private String remark;
}
