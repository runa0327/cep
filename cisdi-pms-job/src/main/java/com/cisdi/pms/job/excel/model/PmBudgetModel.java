package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmBudgetModel
 * @package com.cisdi.pms.job.excel.model
 * @description 项目预算
 * @date 2023/2/27
 */
@Data
public class PmBudgetModel {

    @ExcelProperty("项目名称")
    private String projectName;

    @ExcelProperty("类型：立项匡算,可研估算,初设概算,详设预算,已完成,已支付")
    private String type;

    @ExcelProperty("总投资")
    private BigDecimal total;

    @ExcelProperty("工程费用")
    private BigDecimal gcAmt;

    @ExcelProperty("建安费")
    private BigDecimal jaAmt;

    @ExcelProperty("设备费")
    private BigDecimal sbAmt;

    @ExcelProperty("科研设备费")
    private BigDecimal kyAmt;

    @ExcelProperty("工程费用-其他")
    private BigDecimal gcqtAmt;

    @ExcelProperty("工程其他费用")
    private BigDecimal gcqtfAmt;

    @ExcelProperty("土地拆迁费用")
    private BigDecimal tdcqAmt;

    @ExcelProperty("工程其他费用-其他")
    private BigDecimal qtgcqtAmt;

    @ExcelProperty("预备费")
    private BigDecimal ybAmt;

    @ExcelProperty("建设期利息")
    private BigDecimal jslxAmt;

    @ExcelProperty("总投资-其他")
    private BigDecimal qtTotal;

}
