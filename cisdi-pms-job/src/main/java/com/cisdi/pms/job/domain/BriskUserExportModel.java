package com.cisdi.pms.job.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * @author dlt
 * @date 2023/3/14 周二
 */
@Data
public class BriskUserExportModel {
    @ExcelProperty("姓名")
    @ColumnWidth(30)
    //姓名
    public String userName;

    @ExcelProperty("部门")
    @ColumnWidth(30)
    //部门
    public String deptName;

    @ExcelProperty("最近登录时间")
    @ColumnWidth(30)
    //最近登录时间
    public String lastLoginDate;

    @ExcelProperty("累计登录次数")
    @ColumnWidth(30)
    //累计登录次数
    public String loginNum;

    @ExcelProperty("累计发起流程")
    @ColumnWidth(30)
    //累计发起流程
    public String initiateProcessNum;

    @ExcelProperty("累计处理流程")
    @ColumnWidth(30)
    //累计处理流程
    public String handleProcessNum;
}
