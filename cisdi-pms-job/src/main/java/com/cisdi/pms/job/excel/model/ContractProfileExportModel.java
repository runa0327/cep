package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ContractProfileExportModel
 * @package com.cisdi.pms.job.excel.model
 * @description
 * @date 2023/9/21
 */
@Data
public class ContractProfileExportModel {

    @ExcelProperty("项目名称")
    @ColumnWidth(value = 30)
    private String projectName;
    @ExcelProperty("合同类型")
    @ColumnWidth(value = 30)
    private String contractType;
    @ExcelProperty("合同事项")
    @ColumnWidth(value = 30)
    private String contractItem;
    @ExcelProperty("资料名称")
    @ColumnWidth(value = 30)
    private String fileName;
}
