package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProjectPostModel
 * @package com.cisdi.pms.job.excel.model
 * @description
 * @date 2023/2/13
 */
@Data
public class ProjectPostModel {

    @ExcelProperty("项目名称")
    private String projectName;

    @ExcelProperty("岗位名称")
    private String postName;
}
