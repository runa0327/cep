package com.cisdi.pms.job.domain.exportMain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class PmProjectProblemReqExcel {

    @ExcelProperty("项目名称")
    public String projectName;

    @ExcelProperty("问题描述")
    public String problemDescribe;

    @ExcelProperty("项目推进问题类型")
    public String projectPushProblemTypeName;

    @ExcelProperty("问题状态")
    public String statusName;

    @ExcelProperty("发起人")
    public String userName;

    @ExcelProperty("发起时间")
    public String startTime;
}
