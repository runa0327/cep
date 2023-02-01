package com.cisdi.pms.job.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author dlt
 * @date 2023/2/1 周三
 * 动态临时表模式
 */
@Data
public class DynamicTempModel {
    @ExcelProperty("节点名称")
    private String nameOne;

    @ExcelProperty("日期")
    private String dateOne;

    @ExcelProperty("备注说明")
    private String remarkShort;

    @ExcelProperty("发起人")
    private String promoter;

    @ExcelProperty("数据类型")
    private String type;

}
