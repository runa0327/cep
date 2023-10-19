package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title EquipProjectExportModel
 * @package com.cisdi.pms.job.excel.model
 * @description
 * @date 2023/10/9
 */
@Data
public class EquipProjectExportModel {

    @ExcelProperty("项目编码")
    private String code;

    @ExcelProperty("设备项目名称")
    private String name;

    @ExcelProperty("所属工程项目")
    private String owner;

    @ExcelProperty("设备采购预算金额(万)")
    private String amt;

    @ExcelProperty("科研范围内外")
    private String range;

    @ExcelProperty("建设规模及内容")
    private String desc;
}
