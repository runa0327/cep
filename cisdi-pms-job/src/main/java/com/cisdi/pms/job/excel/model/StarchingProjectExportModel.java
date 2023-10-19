package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title StarchingProjectExportModel
 * @package com.cisdi.pms.job.excel.model
 * @description
 * @date 2023/10/9
 */
@Data
public class StarchingProjectExportModel {

    @ExcelProperty("项目编码")
    private String code;

    @ExcelProperty("项目名称")
    private String name;

    @ExcelProperty("项目业主")
    private String owner;

    @ExcelProperty("项目类型")
    private String type;

    @ExcelProperty("建设地点")
    private String location;
}
