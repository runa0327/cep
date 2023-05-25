package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author dlt
 * @date 2023/5/25 周四
 */
@Data
public class PlanOperationModel {
    //项目名称
    @ExcelProperty("项目名称")
    private String prjName;
    //重点项目类型名称
    @ExcelProperty("重点项目类型名称")
    private String keyProjectTypeName;
    //总投资
    @ExcelProperty("总投资")
    private String totalInvest;
    //项目类型名称
    @ExcelProperty("项目类型名称")
    private String prjTypeName;
    //建设地点名称
    @ExcelProperty("建设地点名称")
    private String locationName;
    //管理模式
    @ExcelProperty("管理模式")
    private String prjManageModeName;
    //前期岗用户名称
    @ExcelProperty("前期岗")
    private String earlyUserName;
    //项目状态名
    @ExcelProperty("项目状态名")
    private String prjPhaseName;
    //标签
    @ExcelProperty("标签")
    private String tagNames;
}
