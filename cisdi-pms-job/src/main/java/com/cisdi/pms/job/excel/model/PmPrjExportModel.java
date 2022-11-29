package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * @author dlt
 * @date 2022/11/28 周一
 */
@Data
public class PmPrjExportModel {
    @ExcelProperty("项目名称")
    @ColumnWidth(30)
    private String name;

    @ExcelProperty("项目业主")
    @ColumnWidth(30)
    private String customerUnit;

    @ExcelProperty("项目类型")
    @ColumnWidth(30)
    private String projectType;

    @ExcelProperty("建设地点")
    @ColumnWidth(30)
    private String baseLocation;

    @ExcelProperty("管理单位")
    @ColumnWidth(30)
    private String prjManageMode;

    @ExcelProperty("概算总投资")
    @ColumnWidth(30)
    private String prjTotalInvest;

    @ExcelProperty("项目状态")
    @ColumnWidth(30)
    private String projectPhase;

    @ExcelProperty("进度状态")
    @ColumnWidth(30)
    private String transitionPhase;

    @ExcelProperty("开工/计划开工")
    @ColumnWidth(30)
    private String startDate;

    @ExcelProperty("竣工/计划竣工")
    @ColumnWidth(30)
    private String complDate;

    @ExcelProperty("形象进度/预计形象进度")
    @ColumnWidth(30)
    private String percent;
}
