package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title WeekTaskModel
 * @package com.cisdi.pms.job.excel.model
 * @description
 * @date 2023/7/19
 */
@Data
public class WeekTaskModel {

//    public String id;

    @ExcelProperty("任务名称")
    public String title;

    @ExcelProperty("责任岗位人员")
    public String user;

    @ExcelProperty("当前状态")
    public String status;

    @ExcelProperty("是否转办")
    public String izTurn;

    @ExcelProperty("转办时间")
    public String transferTime;

    @ExcelProperty("转办人")
    public String transferUser;

    @ExcelProperty("不涉及原因")
    public String reasonExplain;

    @ExcelProperty("延期申请说明")
    public Integer count;

//    public String projectId;
//    public String projectName;
}
