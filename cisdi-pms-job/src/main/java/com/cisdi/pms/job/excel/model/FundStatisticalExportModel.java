package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title FundStatisticalExportModel
 * @package com.cisdi.pms.job.excel.model
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
    public BigDecimal declaredAmount;
    //批复金额
    @ExcelProperty("批复金额（万元）")
    @ColumnWidth(30)
    @ContentStyle(dataFormat = 2)
    public BigDecimal approvedAmount;
    //批复日期
    @ExcelProperty("批复日期")
    @ColumnWidth(30)
    public String approvedDate;
    //累计到位资金
    @ExcelProperty("累计到位资金（万元）")
    @ColumnWidth(30)
    @ContentStyle(dataFormat = 2)
    public BigDecimal cumulativeInPaceAmt;
    //累计征迁到位资金
    @ExcelProperty("累计征迁到位资金（万元）")
    @ColumnWidth(30)
    @ContentStyle(dataFormat = 2)
    public BigDecimal cumulativeInPaceAmtZq;
    //累计建设到位资金
    @ExcelProperty("累计建设到位资金（万元）")
    @ColumnWidth(30)
    @ContentStyle(dataFormat = 2)
    public BigDecimal cumulativeInPaceAmtJs;
    //累计支付资金
    @ExcelProperty("累计支付资金（万元）")
    @ColumnWidth(30)
    @ContentStyle(dataFormat = 2)
    public BigDecimal cumulativePayAmt;
    //到位剩余资金
    @ExcelProperty("到位剩余资金（万元）")
    @ColumnWidth(30)
    @ContentStyle(dataFormat = 2)
    public BigDecimal syAmt;
    //未到位资金
    @ExcelProperty("未到位资金（万元）")
    @ColumnWidth(30)
    @ContentStyle(dataFormat = 2)
    public BigDecimal unInPlaceAmt;
    //总剩余资金
    @ExcelProperty("总剩余资金（万元）")
    @ColumnWidth(30)
    @ContentStyle(dataFormat = 2)
    public BigDecimal totalSyAmt;
    //累计支付率
    @ExcelProperty("累计支付率")
    @ColumnWidth(30)
    public String totalPayRate;
    //备注
    @ExcelProperty("备注")
    @ColumnWidth(30)
    public String remark;
}
