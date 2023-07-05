package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProPlanHistoryModel
 * @package com.cisdi.pms.job.excel.model
 * @description
 * @date 2023/7/5
 */
@Data
public class ProPlanHistoryModel {

    @ExcelProperty("项目名称")
    private String projectName;

    @ExcelProperty("节点名称")
    private String nodeName;

    @ExcelProperty("责任人员")
    private String userName;

    @ExcelProperty("计划开始日期")
    private String planBegin;

    @ExcelProperty("计划结束日期")
    private String planEnd;

    @ExcelProperty("实际开始日期")
    private String actualBegin;

    @ExcelProperty("实际完成日期")
    private String actualEnd;

    @ExcelProperty("是否超期")
    private String izOverdue;

    @ExcelProperty("当前状态")
    private String status;
}
