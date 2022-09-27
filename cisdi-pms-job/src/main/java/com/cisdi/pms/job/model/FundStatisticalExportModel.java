package com.cisdi.pms.job.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title FundStatisticalExportModel
 * @package com.cisdi.pms.job.model
 * @description
 * @date 2022/9/27
 */
@Data
public class FundStatisticalExportModel {

    @ExcelProperty("资金大类")
    public String categoryName;

    @ExcelProperty("资金来源")
    public String sourceName;

    @ExcelProperty("申报金额")
    public String declaredAmount;
    //批复金额
    @ExcelProperty("批复金额")
    public String approvedAmount;
    //批复日期
    @ExcelProperty("批复日期")
    public String approvedDate;
    //累计到位资金
    @ExcelProperty("累计到位资金")
    public String cumulativeInPaceAmt;
    //累计支付资金
    @ExcelProperty("累计支付资金")
    public String cumulativePayAmt;
    //到位剩余资金
    @ExcelProperty("到位剩余资金")
    public String syAmt;
    //未到位资金
    @ExcelProperty("未到位资金")
    public String unInPlaceAmt;
    //总剩余资金
    @ExcelProperty("总剩余资金")
    public String totalSyAmt;
    //累计支付率
    @ExcelProperty("累计支付率")
    public String totalPayRate;
    //备注
    @ExcelProperty("备注")
    public String remark;
}
