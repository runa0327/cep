package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ScientificResearchEquipmentModel
 * @package com.cisdi.pms.job.excel.model
 * @description
 * @date 2023/9/28
 */
@Data
public class ScientificResearchEquipmentModel {

    @ExcelProperty("序号")
    private Integer serialNumber;

    @ExcelProperty("设备项目名称")
    private String name;

    @ExcelProperty("所属工程项目")
    private String beLongProject;

    @ExcelProperty("设备采购预算金额（万）")
    private String budgetAmount;

    @ExcelProperty("可研范围内外")
    private String researchRange;

    @ExcelProperty("项目文号")
    private String replyNo;

    @ExcelProperty("建设规模与内容")
    private String description;
}
