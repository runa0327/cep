package com.cisdi.pms.job.export.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import lombok.Data;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title FundStatisticalExportModel
 * @package com.cisdi.pms.job.export.model
 * @description
 * @date 2022/9/27
 */
@Data
public class FundStatisticalExportModel {

    @ExcelProperty("资金大类一级")
    @ColumnWidth(30)
    public String categoryName;

    @ExcelProperty("资金大类二级")
    @ColumnWidth(30)
    public String categoryNameSecond;

    @ExcelProperty("资金来源")
    @ColumnWidth(30)
    public String sourceName;

    @ExcelProperty("申报金额（万元）")
    @ColumnWidth(30)
    @ContentStyle(dataFormat = 2)
    public Double declaredAmount;
    //批复金额
    @ExcelProperty("批复金额（万元）")
    @ColumnWidth(30)
    @ContentStyle(dataFormat = 2)
    public Double approvedAmount;
    //批复日期
    @ExcelProperty("批复日期")
    @ColumnWidth(30)
    public String approvedDate;
    //累计到位资金
    @ExcelProperty("累计到位资金（万元）")
    @ColumnWidth(30)
    @ContentStyle(dataFormat = 2)
    public Double cumulativeInPaceAmt;
    //累计征迁到位资金
    @ExcelProperty("累计征迁到位资金（万元）")
    @ColumnWidth(30)
    @ContentStyle(dataFormat = 2)
    public Double cumulativeInPaceAmtZq;
    //累计建设到位资金
    @ExcelProperty("累计建设到位资金（万元）")
    @ColumnWidth(30)
    @ContentStyle(dataFormat = 2)
    public Double cumulativeInPaceAmtJs;
    //累计支付资金
    @ExcelProperty("累计支付资金（万元）")
    @ColumnWidth(30)
    @ContentStyle(dataFormat = 2)
    public Double cumulativePayAmt;
    //到位剩余资金
    @ExcelProperty("到位剩余资金（万元）")
    @ColumnWidth(30)
    @ContentStyle(dataFormat = 2)
    public Double syAmt;
    //未到位资金
    @ExcelProperty("未到位资金（万元）")
    @ColumnWidth(30)
    @ContentStyle(dataFormat = 2)
    public Double unInPlaceAmt;
    //总剩余资金
    @ExcelProperty("总剩余资金（万元）")
    @ColumnWidth(30)
    @ContentStyle(dataFormat = 2)
    public Double totalSyAmt;
    //累计支付率
    @ExcelProperty("累计支付率")
    @ColumnWidth(30)
    public String totalPayRate;
    //备注
    @ExcelProperty("备注")
    @ColumnWidth(30)
    public String remark;
}
