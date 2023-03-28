package com.cisdi.pms.job.domain;

import lombok.Data;

/**
 * @author dlt
 * @date 2023/3/21 周二
 */
@Data
public class ProcessModel {

//    @ExcelProperty("流程名称")
    private String processName;

//    @ExcelProperty("上线至今发起数量")
    private String processNum;

//    @ExcelProperty("上线至今结束数量")
    private String endProcessNum;

//    @ExcelProperty("***至今发起数量")
    private String datedProcessNum;

//    @ExcelProperty("***至今结束数量")
    private String datedEndProcessNum;

    //上线至今发起数量总和
//    private String allProcessNum;

    //上线至今结束数量总和
//    private String allEndProcessNum;

    //***至今发起数量总和
//    private String allDatedProcessNum;

    //***至今结束数量总和
//    private String allDatedEndProcessNum;


}
