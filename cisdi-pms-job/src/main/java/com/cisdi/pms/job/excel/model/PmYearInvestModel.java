package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmYearInvestModel
 * @package com.cisdi.pms.job.excel.model
 * @description
 * @date 2023/2/27
 */
@Data
public class PmYearInvestModel {
    @ExcelProperty("项目")
    private String projectName;

    @ExcelProperty("年份")
    private Integer year;

    @ExcelProperty("月份")
    private Integer month;

    @ExcelProperty("金额")
    private BigDecimal amt;
}
