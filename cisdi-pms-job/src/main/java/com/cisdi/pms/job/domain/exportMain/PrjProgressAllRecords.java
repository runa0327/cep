package com.cisdi.pms.job.domain.exportMain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PrjProgressAllRecords {

    @ExcelProperty("项目名称")
    public String projectName;

    @ExcelProperty("项目负责人")
    public String manageUserName;

    @ExcelProperty("整体形象进度")
    public BigDecimal progress;

    @ExcelProperty("本周项目进展")
    public String progressWeek;

    @ExcelProperty("本周问题说明")
    public String progressDescribe;

    @ExcelProperty("备注说明")
    public String progressRemark;

    @ExcelProperty("填报时间")
    public String writeDate;

    @ExcelProperty("是否符合开工条件")
    public String weatherStart;

    @ExcelProperty("是否竣工")
    public String weatherCompleted;
}
