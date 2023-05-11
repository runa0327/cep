package com.cisdi.pms.job.domain.exportMain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PrjProgressRecords {

    @ExcelProperty("项目名称")
    public String projectName;

    @ExcelProperty("填报时间")
    public String writeDate;

    @ExcelProperty("整体形象进度")
    public BigDecimal progress;

    @ExcelProperty("累计形象进度（从开工至本周进展）")
    public String progressDescribe;

    @ExcelProperty("本周项目进展")
    public String progressWeek;

    @ExcelProperty("备注说明")
    public String progressRemark;

}
