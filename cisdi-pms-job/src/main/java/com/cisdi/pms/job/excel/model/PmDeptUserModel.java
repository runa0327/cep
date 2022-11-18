package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmDeptUserModel
 * @package com.cisdi.pms.job.excel.model
 * @description
 * @date 2022/11/18
 */
@Data
public class PmDeptUserModel {

    @ExcelProperty("部门名称")
    private String deptName;

    @ExcelProperty("用户名称")
    private String userName;

}
