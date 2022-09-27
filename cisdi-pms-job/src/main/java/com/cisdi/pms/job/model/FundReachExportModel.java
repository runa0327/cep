package com.cisdi.pms.job.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title FundReachExportModel
 * @package com.cisdi.pms.job.model
 * @description
 * @date 2022/9/27
 */
@Data
public class FundReachExportModel {

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("备注")
    private String remark;
}
